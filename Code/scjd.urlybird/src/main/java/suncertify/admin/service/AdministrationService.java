package suncertify.admin.service;

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

public final class AdministrationService {

    private enum RunningState {
	RUNNING, RUNNING_EMBEDDED, STOPPED
    }

    private RunningState state = RunningState.STOPPED;

    public void startStandAloneServer(final ServerConfiguration serverConfig,
	    final DatabaseConfiguration dataConfig)
	    throws DatabaseConnectionException, RemoteException,
	    MalformedURLException {

	if (isServerRunning()) {
	    throw new IllegalStateException();
	}

	final DB db = connectToDatabase(dataConfig);
	ServerState.instance().setDatabase(db);
	ServerState.instance().setConfig(serverConfig);

	// Bug 4457683
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
		ClientServicesImpl.instance());
	state = RunningState.RUNNING;
	ClientServicesImpl.instance().enableServices();
    }

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

    public void stopServer() throws RemoteException, MalformedURLException,
	    NotBoundException {

	final ServerConfiguration config = ServerState.instance()
		.getConfiguration();
	Naming.unbind(config.getHostNameWithPort() + "/"
		+ config.getClientServiceName());
	state = RunningState.STOPPED;
	ClientServicesImpl.instance().disableServices();
    }

    public boolean isServerRunning() {
	return !state.equals(RunningState.STOPPED);
    }

}
