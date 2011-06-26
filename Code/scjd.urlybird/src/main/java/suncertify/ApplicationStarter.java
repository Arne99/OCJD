package suncertify;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;

import suncertify.admin.AdministrationService;
import suncertify.admin.ServerAdminView;
import suncertify.admin.ServerAdminPresenter;
import suncertify.client.DatabaseConnectionPanel;
import suncertify.client.DatabaseConnectionPresenter;
import suncertify.client.ServerConnectionPresenter;
import suncertify.client.UrlyBirdPresenter;
import suncertify.client.UrlyBirdView;
import suncertify.common.RoomOfferService;
import suncertify.common.UrlyBirdProperties;
import suncertify.db.DatabaseService;

/**
 * Entry point and main method for the UrlyBird application. This class is just
 * a holder for the main method and could not be instantiated.
 * 
 * @author arnelandwehr
 * 
 */
final class ApplicationStarter {

    /**
     * Contains all modes in which the application could be launched.
     * 
     * @author arnelandwehr
     * 
     */
    private enum START_MODE {

	/**
	 * In the server mode only the server is started.
	 */
	SERVER("server"),

	/**
	 * In the client mode inly the client is started.
	 */
	CLIENT(""),

	/**
	 * In the alone mode and client with a database connection and without a
	 * server is started.
	 */
	STAND_ALONE_CLIENT("alone"),

	/**
	 * Dummy mode for an irregular command line parameter.
	 */
	ILLEGAL_MODE("");

	/** the associated command line parameter. */
	private final String cliParam;

	/**
	 * private constructor never use.
	 * 
	 * @param cliParam
	 *            the command line parameter
	 */
	private START_MODE(final String cliParam) {
	    this.cliParam = cliParam;
	}
    }

    /**
     * private constructor, never use.
     */
    private ApplicationStarter() {
	super();
    }

    /**
     * The UrlyBird application main method. The following command line
     * arguments are supported:
     * <ul>
     * <li>blanc string "" => starts the client application</li>
     * <li>"alone" => starts the stand alone application without server and
     * networking</li>
     * <li>"server" => starts the server application</li>
     * </ul>
     * 
     * Every other command line parameter will not be excepted and terminate the
     * application.
     * 
     * @param args
     *            the input parameter.
     * @throws IOException
     *             if an io problem occurs during the bootstrapping
     */
    public static void main(final String[] args) throws IOException {

	final Logger globalLogger = LogManager.getLogManager().getLogger(
		Logger.GLOBAL_LOGGER_NAME);
	globalLogger.addHandler(new FileHandler(System.getProperty("user.dir")
		+ "/error.log"));
	globalLogger.setLevel(Level.FINEST);

	final START_MODE mode = getStartMode(args);

	switch (mode) {
	case CLIENT:
	    startClient();
	    break;
	case SERVER:
	    startServer();
	    break;
	case STAND_ALONE_CLIENT:
	    startStandAloneClient();
	    break;
	case ILLEGAL_MODE:
	    System.err.println("Illegal command line argument.");
	    System.err.println("Only the following commands are supported:");
	    System.err
		    .println("\"\" (blanc string) => starts the client application");
	    System.err
		    .println("\"alone\"           => starts the stand alone application without server and networking");
	    System.err
		    .println("\"server\"          => starts the server application");
	    System.exit(1);
	default:
	    throw new IllegalStateException(
		    "This state should never be reached!");
	}
    }

    /**
     * Starts the standalone client.
     */
    private static void startStandAloneClient() {

	final DatabaseConnectionPresenter databaseConnectionPresenter = new DatabaseConnectionPresenter(
		new DatabaseConnectionPanel(), DatabaseService.instance());
	final JFrame frame = new JFrame();
	frame.setLocationRelativeTo(null);
	final RoomOfferService roomOfferService = databaseConnectionPresenter
		.connectToDatabaseWithDialog(frame);
	frame.dispose();

	new UrlyBirdPresenter(new UrlyBirdView(), roomOfferService,
		new DatabaseConnectionPresenter(new DatabaseConnectionPanel(),
			DatabaseService.instance())).startGui();
    }

    /**
     * Starts the server.
     */
    private static void startServer() {
	final ServerAdminView view = new ServerAdminView();
	final ServerAdminPresenter serverAdminPresenter = new ServerAdminPresenter(
		view, new AdministrationService(),
		UrlyBirdProperties.getInstance());
	serverAdminPresenter.startGui();
    }

    /**
     * Starts the client.
     */
    private static void startClient() {

	final ServerConnectionPresenter serverConnectionPresenter = new ServerConnectionPresenter();
	final JFrame frame = new JFrame();
	frame.setLocationRelativeTo(null);
	final RoomOfferService service = serverConnectionPresenter
		.startInitialConnectionDialogToFindService(frame);
	frame.dispose();

	new UrlyBirdPresenter(new UrlyBirdView(), service,
		new ServerConnectionPresenter()).startGui();
    }

    /**
     * Returns the associated {@link START_MODE} to the given command line
     * arguments.
     * 
     * @param args
     *            command line arguments
     * @return the associated {@link START_MODE}.
     */
    private static START_MODE getStartMode(final String[] args) {

	if (args.length > 1) {
	    return START_MODE.ILLEGAL_MODE;
	} else if (args.length == 0) {
	    return START_MODE.CLIENT;
	}

	final String argument = args[0];

	if (argument.equals(START_MODE.SERVER.cliParam)) {
	    return START_MODE.SERVER;
	} else if (argument.equals(START_MODE.STAND_ALONE_CLIENT.cliParam)) {
	    return START_MODE.STAND_ALONE_CLIENT;
	}

	return START_MODE.ILLEGAL_MODE;
    }
}
