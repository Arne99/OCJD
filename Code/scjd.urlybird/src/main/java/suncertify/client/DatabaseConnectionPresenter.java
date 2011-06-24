package suncertify.client;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import suncertify.admin.DatabaseConfiguration;
import suncertify.common.RoomOfferService;
import suncertify.common.UrlyBirdProperties;
import suncertify.common.UrlyBirdProperties.PropertyName;
import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;
import suncertify.db.DB;
import suncertify.db.DatabaseConnectionException;
import suncertify.db.DatabaseHandler;
import suncertify.db.DatabaseService;
import suncertify.domain.UrlyBirdRoomOfferService;

/**
 * An <code>DatabaseConnectionPresenter</code> controls an Gui which allows an
 * user to configure an establish an database connection.The presenter creates
 * an local {@link RoomOfferService} and provides it to the client.
 * 
 * @author arnelandwehr
 * 
 */
public final class DatabaseConnectionPresenter implements
	RoomOfferServiceProvider {

    /**
     * Establishes an connection to the database that is specified by the path
     * from the db path <code>TextField</code>.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ConnectToDatabaseListener implements ActionListener {

	/** the dialog the listener operates on. */
	private final JDialog dialog;

	/**
	 * Constructs a new <code>ConnectToDatabaseListener</code>.
	 * 
	 * @param dialog
	 *            the dialog the listener operates on, must not be
	 *            <code>null</code>.
	 */
	private ConnectToDatabaseListener(final JDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final String path = panel.getDatabaseTextField().getText().trim();
	    final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(
		    new File(path));

	    try {
		final DatabaseHandler databaseHandler = DataFileService
			.instance().getDatabaseHandler(
				databaseConfiguration.getDatabaseLocation());
		database = databaseService.connectToDatabase(databaseHandler);
		roomOfferService = new UrlyBirdRoomOfferService(database);
	    } catch (final DatabaseConnectionException connectionException) {
		JOptionPane.showMessageDialog(null,
			connectionException.getMessage(),
			"Connection Failure!", JOptionPane.ERROR_MESSAGE);
		return;
	    } catch (final IOException e) {
		logger.throwing(getClass().getSimpleName(), "actionPerformed",
			e);
		JOptionPane.showMessageDialog(null, e.getMessage(),
			"Connection Failure!", JOptionPane.ERROR_MESSAGE);
		return;
	    } catch (final UnsupportedDataFileFormatException e) {
		JOptionPane.showMessageDialog(null, e.getMessage(),
			"Connection Failure!", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    try {
		properties.setProperty(PropertyName.CLIENT_DATABASE_GUI_PATH,
			path);
	    } catch (final IOException e) {
		logger.throwing(getClass().getName(), "actionPerformed", e);
	    }
	    dialog.dispose();
	}
    }

    /**
     * Enables the connect button if the database path <code>Textfield</code> is
     * filled or disables it if the field is blank.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ToggleConnectButton implements DocumentListener {
	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    panel.getConnectButton().setEnabled(dbPathIsSpecified());
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    panel.getConnectButton().setEnabled(dbPathIsSpecified());
	}

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    panel.getConnectButton().setEnabled(dbPathIsSpecified());
	}

	/**
	 * Specifies if the database path <code>TextField</code> is filled.
	 * 
	 * @return <code>true</code> if the field contains at least one symbol.
	 */
	private boolean dbPathIsSpecified() {
	    return !panel.getDatabaseTextField().getText().trim().equals("");
	}
    }

    /**
     * Opens a {@link JFileChooser} to allow the user to specify the database
     * path.
     * 
     * @author arnelandwehr
     * 
     */
    private final class SelectDatabasePathWithFileChooserListener implements
	    ActionListener {

	/** the dialog this listener operates on. */
	private final JDialog dialog;

	/**
	 * Constructs a new
	 * <code>SelectDatabasePathWithFileChooserListener</code>.
	 * 
	 * @param dialog
	 *            the dialog this listener operates on.
	 */
	private SelectDatabasePathWithFileChooserListener(final JDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

	    final JFileChooser fileChooser = new JFileChooser();

	    final int returnVal = fileChooser.showDialog(dialog,
		    "Select Database Path");
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		panel.getDatabaseTextField().setText(
			fileChooser.getSelectedFile().getAbsolutePath());
	    }

	}
    }

    /** exception logger, global is sufficient here. */
    private final Logger logger = Logger.getLogger("global");

    /** constant for the name of the dialog. */
    private static final String DIALOG_NAME = "Database";

    /** the {@link RoomOfferService} that is provided by this presenter. */
    private RoomOfferService roomOfferService;

    /**
     * the {@link DatabaseService} that is needed to create the
     * {@link RoomOfferService}.
     */
    private final DatabaseService databaseService;

    /**
     * the {@link DB} that is created with the {@link DatabaseService} and the
     * user configuration.
     */
    private DB database;

    /** the properties the store and load the user input. */
    private final UrlyBirdProperties properties = UrlyBirdProperties
	    .getInstance();

    /** the view that enables the user to configure the database connection. */
    private final DatabaseConnectionPanel panel;

    /**
     * Construct a new <code>DatabaseConnectionPresenter</code>.
     * 
     * @param panel
     *            view that enables the user to configure the database
     *            connection, must not be <code>null</code>.
     * @param service
     *            the default service that is provided by this presenter if the
     *            user does not specify a new database connection, could be
     *            <code>null</code>.
     */
    public DatabaseConnectionPresenter(final DatabaseConnectionPanel panel,
	    final DatabaseService service) {
	super();
	this.panel = panel;
	this.databaseService = service;
    }

    /**
     * Shows an modal dialog to allow an user to configure a new database and
     * connect to it. The presenter creates a new {@link RoomOfferService} for
     * this database connection and returns it. If the user does not specify a
     * new database the default <code>RoomOfferService</code> s.
     * {@link #DatabaseConnectionPresenter(DatabaseConnectionPanel, DatabaseService)}
     * is returned.
     * 
     * @param frame
     *            the parent {@link Frame} of the modal dialog, must not be
     *            <code>null</code>.
     * @return the valid <code>RoomOfferService</code>.
     */
    public RoomOfferService connectToDatabaseWithDialog(final JFrame frame) {

	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    final JDialog dialog = new JDialog(frame, DIALOG_NAME, true);
		    initDatabaseDialog(dialog, new ExitApplication());
		}
	    });
	} catch (final InterruptedException e) {
	    logger.throwing(getClass().getName(),
		    "connectToDatabaseWithDialog", e);
	} catch (final InvocationTargetException e) {
	    logger.throwing(getClass().getName(),
		    "connectToDatabaseWithDialog", e);
	}

	return roomOfferService;
    }

    @Override
    public RoomOfferService startRoomOfferServiceProviderDialog(
	    final JFrame frame, final RoomOfferService service) {

	this.roomOfferService = service;
	final JDialog dialog = new JDialog(frame, DIALOG_NAME, true);
	initDatabaseDialog(dialog, new ExitDialog(dialog));
	return roomOfferService;
    }

    /**
     * Initializes the database connection dialog, adds the needed listeners and
     * creates a new {@link RoomOfferService} if the user connects to a new
     * database.
     * 
     * @param dialog
     *            the dialog to initialize.
     * @param exitAdapter
     *            the {@link ExitDialogAdapter} to close the dialog.
     * @return the created {@link RoomOfferService}.
     */
    private RoomOfferService initDatabaseDialog(final JDialog dialog,
	    final ExitDialogAdapter exitAdapter) {

	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	dialog.getContentPane().add(panel);
	dialog.pack();
	dialog.addWindowListener(exitAdapter);

	panel.getConnectButton().setEnabled(false);

	panel.getSelectButton().addActionListener(
		new SelectDatabasePathWithFileChooserListener(dialog));

	panel.getDatabaseTextField().getDocument()
		.addDocumentListener(new ToggleConnectButton());

	panel.getDiscardButton().addActionListener(exitAdapter);

	panel.getConnectButton().addActionListener(
		new ConnectToDatabaseListener(dialog));

	try {
	    final String path = properties
		    .getProperty(PropertyName.CLIENT_DATABASE_GUI_PATH);
	    panel.getDatabaseTextField().setText(path);
	} catch (final IOException exception) {
	    logger.throwing(getClass().getSimpleName(), "initDatabaseDialog",
		    exception);
	}

	dialog.setLocationRelativeTo(null);
	dialog.setVisible(true);

	return new UrlyBirdRoomOfferService(database);
    }
}
