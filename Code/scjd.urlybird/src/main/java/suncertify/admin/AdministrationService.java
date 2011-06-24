package suncertify.admin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;
import suncertify.db.DB;
import suncertify.db.DatabaseConnectionException;
import suncertify.db.DatabaseHandler;
import suncertify.db.DatabaseService;

/**
 * The <code>AdministrationService</code> provides methods to administrate the
 * server. The server could be started and stopped.
 * 
 * @author arnelandwehr
 * 
 */
public final class AdministrationService {

    /**
     * States o the server.
     * 
     * @author arnelandwehr
     * 
     */
    private enum RunningState {
	/**
	 * the server is actually running.
	 */
	RUNNING,

	/**
	 * the server is actually not running.
	 */
	STOPPED
    }

    /** the actual state of the server. Initial is stopped. */
    private RunningState state = RunningState.STOPPED;

    /**
     * Starts the server with the given configuration.
     * 
     * @param serverConfig
     *            the configuration for the server, like port and address.
     * @param dataConfig
     *            the configuration for the database the server should use.
     * @throws DatabaseConnectionException
     *             if the server cannot connect to the database.
     * @throws RemoteException
     *             if an remote problem occurs.
     * @throws MalformedURLException
     *             if the by the server configuration transmitted url is not
     *             valid.
     */
    public void startServer(final ServerConfiguration serverConfig,
	    final DatabaseConfiguration dataConfig)
	    throws DatabaseConnectionException, RemoteException,
	    MalformedURLException {

	if (isServerRunning()) {
	    throw new IllegalStateException();
	}

	final DB db = connectToDatabase(dataConfig);
	ServerState.instance().setDatabase(db);
	ServerState.instance().setConfig(serverConfig);

	// JVM Bug 4457683
	try {
	    final Registry registry = LocateRegistry.getRegistry(serverConfig
		    .getPort());
	    registry.list();
	} catch (final Exception e) {
	    LocateRegistry.createRegistry(serverConfig.getPort());
	}
	Naming.rebind(
		serverConfig.getHostNameWithPort() + "/"
			+ serverConfig.getClientServiceName(),
		LockableServiceProvider.instance());
	state = RunningState.RUNNING;
	LockableServiceProvider.instance().enableServices();
    }

    /**
     * Connects the server to the database.
     * 
     * @param dataConfig
     *            the database connection configuration parameters
     * @return the now connected database.
     * @throws DatabaseConnectionException
     *             if the server cannot connect to the database
     */
    private DB connectToDatabase(final DatabaseConfiguration dataConfig)
	    throws DatabaseConnectionException {

	DatabaseHandler databaseHandler;
	try {
	    databaseHandler = DataFileService.instance().getDatabaseHandler(
		    dataConfig.getDatabaseLocation());
	} catch (final IOException e) {
	    throw new DatabaseConnectionException(e);
	} catch (final UnsupportedDataFileFormatException e) {
	    throw new DatabaseConnectionException(e);
	}

	final DB db = DatabaseService.instance().connectToDatabase(
		databaseHandler);
	return db;
    }

    /**
     * Stops a running server.
     * 
     * @throws RemoteException
     *             if an remote problem occurs.
     * @throws MalformedURLException
     *             if the by the server configuration transmitted url is not
     *             valid.
     * @throws NotBoundException
     *             if the server was not bound in the registry.
     */
    public void stopServer() throws RemoteException, MalformedURLException,
	    NotBoundException {

	final ServerConfiguration config = ServerState.instance()
		.getConfiguration();
	Naming.unbind(config.getHostNameWithPort() + "/"
		+ config.getClientServiceName());
	state = RunningState.STOPPED;
	LockableServiceProvider.instance().disableServices();
    }

    /**
     * Indicates if the server is actually running.
     * 
     * @return <code>true</code> if the server is running.
     */
    public boolean isServerRunning() {
	return !state.equals(RunningState.STOPPED);
    }

}
