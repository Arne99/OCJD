package suncertify.admin.service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import suncertify.common.ClientServices;
import suncertify.common.ClientServicesImpl;
import suncertify.datafile.DataFileAccess;
import suncertify.db.DB;
import suncertify.db.DatabaseHandler;
import suncertify.db.DatabaseService;

public final class AdministrationService {

    private enum RunningState {
	RUNNING, RUNNING_EMBEDDED, NOT_RUNNING
    }

    private RunningState state = RunningState.NOT_RUNNING;

    public void startStandAloneServer(final ServerConfiguration serverConfig,
	    final DatabaseConfiguration dataConfig) throws Exception {

	final DB db = connectToDatabase(dataConfig);
	ServerState.instance().setDatabase(db);

	LocateRegistry.createRegistry(serverConfig.getPort());
	Naming.bind(
		serverConfig.getHostNameWithPort() + "/"
			+ serverConfig.getClientServiceName(),
		new ClientServicesImpl());
	state = RunningState.RUNNING;
    }

    private DB connectToDatabase(final DatabaseConfiguration dataConfig)
	    throws Exception {
	final DatabaseHandler databaseHandler = DataFileAccess.instance()
		.getDatabaseHandler(dataConfig.getDatabaseLocation());
	final DB db = DatabaseService.instance().getDatabaseConnection(
		databaseHandler);
	return db;
    }

    public void stopServer() {
	// Database stoppen
	// Unbind Naming Service
    }

    public boolean isServerRunning() {
	return state != RunningState.NOT_RUNNING;
    }

    ClientServices startEmbeddedServer(final DatabaseConfiguration dataConfig)
	    throws Exception {
	final DB database = connectToDatabase(dataConfig);
	return new ClientServicesImpl();
    }

}
