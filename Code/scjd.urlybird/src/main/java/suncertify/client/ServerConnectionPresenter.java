package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Naming;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import suncertify.admin.ServerConfiguration;
import suncertify.client.UrlyBirdPresenter.ToggleButtonWhenTextChanges;
import suncertify.common.RoomOfferService;
import suncertify.common.ServiceProvider;
import suncertify.common.UrlyBirdProperties;
import suncertify.common.UrlyBirdProperties.PropertyName;

/**
 * An <code>ServerConnectionPresenter</code> controls an Gui which allows an
 * user to configure an establish an server connection.The presenter creates an
 * remote {@link RoomOfferService} and provides it to the client.
 * 
 * @author arnelandwehr
 * 
 */
public final class ServerConnectionPresenter implements
	RoomOfferServiceProvider {

    /**
     * Establishes a connection to the server with the specified parameters and
     * fetches a remote {@link RoomOfferService} from it.
     * 
     * @author arnelandwehr
     * 
     */
    private final class GetServiceFromServerListener implements ActionListener {

	/** the dialog . */
	private final JDialog dialog;

	/** the connection panel. */
	private final ServerConnectionPanel connectionPanel;

	/**
	 * Construct a new <code>GetServiceFromServerListener</code>.
	 * 
	 * @param dialog
	 *            the dialog.
	 * @param connectionPanel
	 *            the connection panel
	 */
	private GetServiceFromServerListener(final JDialog dialog,
		final ServerConnectionPanel connectionPanel) {
	    this.dialog = dialog;
	    this.connectionPanel = connectionPanel;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final String host = connectionPanel.getHostTextField().getText()
		    .trim();
	    final int port = Integer.parseInt(connectionPanel
		    .getPortTextField().getText().trim());

	    final ServerConfiguration serverConfiguration = new ServerConfiguration(
		    port, host);
	    try {
		final ServiceProvider services = (ServiceProvider) Naming
			.lookup(serverConfiguration.getHostNameWithPort() + "/"
				+ serverConfiguration.getClientServiceName());

		service = services.getRoomOfferService();

	    } catch (final Exception e) {
		logger.throwing(getClass().getSimpleName(), "actionPerformed",
			e);
		JOptionPane.showMessageDialog(null, e.getCause());
		return;
	    }

	    try {
		properties.setProperty(PropertyName.CLIENT_CONNECTION_GUI_HOST,
			host);
		properties.setProperty(PropertyName.CLIENT_CONNECTION_GUI_PORT,
			port + "");
	    } catch (final IOException e) {
		logger.throwing(getClass().getSimpleName(), "actionPerformed",
			e);
	    }
	    dialog.dispose();
	}
    }

    /**
     * Allows only digits as input.
     * 
     * @author arnelandwehr
     * 
     */
    private final class AllowsOnlyDigitsAsInputListener extends PlainDocument {
	/** the SUID. */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(final int arg0, final String arg1,
		final AttributeSet arg2) throws BadLocationException {
	    if (!Pattern.matches("\\d*", arg1)) {
		return;
	    }

	    super.insertString(arg0, arg1, arg2);
	}
    }

    /** exception logger, global is sufficient here. */
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /** the properties to store or retrieve the last user input. */
    private final UrlyBirdProperties properties = UrlyBirdProperties
	    .getInstance();

    /**
     * the {@link RoomOfferService}, could be the given default or the new
     * created one.
     */
    private RoomOfferService service = null;

    /**
     * Starts an modal server connection dialog where the user must specify a
     * connection and establish it.
     * 
     * @param frame
     *            the parent frame.
     * @return the created remote {@link RoomOfferService}, never
     *         <code>null</code>.
     */
    public RoomOfferService startInitialConnectionDialogToFindService(
	    final JFrame frame) {

	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    final JDialog dialog = new JDialog(frame,
			    "Server Connection", true);
		    showDialog(dialog, frame, new ExitApplication());
		}
	    });
	} catch (final InterruptedException e) {
	    logger.throwing(getClass().getSimpleName(),
		    "startInitialConnectionDialogToFindService", e);
	} catch (final InvocationTargetException e) {
	    logger.throwing(getClass().getSimpleName(),
		    "startInitialConnectionDialogToFindService", e);
	}
	return service;
    }

    /**
     * Initializes and shows the modal server connection dialog.
     * 
     * @param dialog
     *            the dialog to initialize
     * @param frame
     *            the parent frame
     * @param exitDialog
     *            the dialog that appears if the user wants to exit.
     * @return the created {@link RoomOfferService}.
     */
    private RoomOfferService showDialog(final JDialog dialog,
	    final JFrame frame, final ExitDialogAdapter exitDialog) {

	final ServerConnectionPanel connectionPanel = new ServerConnectionPanel();
	dialog.add(connectionPanel);
	dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	dialog.pack();

	connectionPanel.getConnectButton().setEnabled(false);

	addListener(dialog, exitDialog, connectionPanel);

	loadLastUserInput(connectionPanel);

	dialog.setLocationRelativeTo(null);
	dialog.setVisible(true);
	return service;
    }

    /**
     * Adds the needed listeners to the dialog.
     * 
     * @param dialog
     *            the dialog
     * @param exitDialog
     *            the exit dialog
     * @param connectionPanel
     *            the connection panel
     */
    private void addListener(final JDialog dialog,
	    final ExitDialogAdapter exitDialog,
	    final ServerConnectionPanel connectionPanel) {
	connectionPanel.getPortTextField().setDocument(
		new AllowsOnlyDigitsAsInputListener());

	connectionPanel
		.getHostTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleButtonWhenTextChanges(connectionPanel
				.getConnectButton(), connectionPanel
				.getHostTextField(), connectionPanel
				.getPortTextField()));
	connectionPanel
		.getPortTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleButtonWhenTextChanges(connectionPanel
				.getConnectButton(), connectionPanel
				.getHostTextField(), connectionPanel
				.getPortTextField()));

	connectionPanel.getConnectButton().addActionListener(
		new GetServiceFromServerListener(dialog, connectionPanel));

	connectionPanel.getDiscardButton().addActionListener(exitDialog);
	dialog.addWindowListener(exitDialog);
    }

    /**
     * Loads the last user input for the connection fields from the property
     * file and fills the specified fields with it.
     * 
     * @param connectionPanel
     *            the connection panel
     */
    private void loadLastUserInput(final ServerConnectionPanel connectionPanel) {
	try {
	    final String host = properties
		    .getProperty(PropertyName.CLIENT_CONNECTION_GUI_HOST);
	    connectionPanel.getHostTextField().setText(host);
	    final String port = properties
		    .getProperty(PropertyName.CLIENT_CONNECTION_GUI_PORT);
	    connectionPanel.getPortTextField().setText(port);
	} catch (final IOException e1) {
	    logger.throwing(getClass().getSimpleName(), "actionPerformed", e1);
	}
    }

    @Override
    public RoomOfferService startRoomOfferServiceProviderDialog(
	    final JFrame frame, final RoomOfferService service) {

	this.service = service;
	final JDialog dialog = new JDialog(frame, "Server Connection", true);
	showDialog(dialog, frame, new ExitDialog(dialog));
	return service;
    }

}
