package suncertify.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import suncertify.common.UrlyBirdProperties;
import suncertify.common.UrlyBirdProperties.PropertyName;

/**
 * Presenter for the {@link ServerAdminView}. Initializes the gui and contains
 * the gui logic in form of Listeners.
 * 
 * Follows the MVP pattern by Martin Fowler, for a better testability.
 * 
 * @author arnelandwehr
 * 
 */
public final class ServerAdminPresenter {

    /**
     * The start of the gui as an {@link Runnable} for the event dispatcher
     * thread.
     * 
     * @author arnelandwehr
     * 
     */
    private final class StartGuiRunnable implements Runnable {
	@Override
	public void run() {
	    view.init();
	    addListener();

	    try {
		fillFieldsWithLastInput();
	    } catch (final IOException e) {
		logger.throwing(getClass().getName(), "startGui", e);
		JOptionPane.showMessageDialog(
			view.getMainFrame(),
			"The default values could not be loaded! \n"
				+ e.getCause(), "", ImageObserver.ERROR);
	    }
	    view.getMainFrame().setLocationRelativeTo(null);
	    view.show();
	}

	/**
	 * Retrieves the last stored input of the user from the property file
	 * and files the fields with it.
	 * 
	 * @throws IOException
	 *             if an io problem occurs during the retrieving.
	 */
	private void fillFieldsWithLastInput() throws IOException {
	    view.getDbPathTextField().setText(
		    properties.getProperty(PropertyName.ADMIN_GUI_DB_PATH));
	    view.getPortTextField().setText(
		    properties.getProperty(PropertyName.ADMIN_GUI_PORT));
	    view.getHostConnectionTextField().setText(
		    properties.getProperty(PropertyName.ADMIN_GUI_HOST));
	}
    }

    /**
     * Closes the Application after shwoing the user an confirmation dialog.
     * 
     * @author arnelandwehr
     * 
     */
    private final class CloseApplicationListener extends WindowAdapter {

	@Override
	public void windowClosing(final WindowEvent e) {
	    try {
		if (service.isServerRunning()) {
		    final int result = JOptionPane
			    .showConfirmDialog(
				    view.getMainFrame(),
				    "Do you really want to exit? The running server will be shut down!",
				    "", JOptionPane.YES_NO_OPTION);
		    if (result == JOptionPane.YES_OPTION) {
			service.stopServer();
			view.getMainFrame().dispose();
		    } else {
			return;
		    }
		} else {
		    final int result = JOptionPane.showConfirmDialog(
			    view.getMainFrame(), "Do you really want to exit?",
			    "", JOptionPane.YES_NO_OPTION);
		    if (result == JOptionPane.YES_OPTION) {
			view.getMainFrame().dispose();
		    }
		}
		saveTheLastInput();
	    } catch (final Exception exception) {
		logger.throwing(getClass().getName(), "windowClosing",
			exception);
		showError(exception.getMessage());
	    } finally {
		System.exit(0);
	    }
	}

	/**
	 * Saves the last input of the user in a property file.
	 * 
	 * @throws IOException
	 *             if an io problem occurs during the saving.
	 */
	private void saveTheLastInput() throws IOException {
	    properties.setProperty(PropertyName.ADMIN_GUI_DB_PATH, view
		    .getDbPathTextField().getText().trim());
	    properties.setProperty(PropertyName.ADMIN_GUI_HOST, view
		    .getHostConnectionTextField().getText().trim());
	    properties.setProperty(PropertyName.ADMIN_GUI_PORT, view
		    .getPortTextField().getText().trim());
	}
    }

    /**
     * Starts the server if it is stopped or stops it if it is running.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ToggleServerListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent event) {

	    if (service.isServerRunning()) {
		try {
		    service.stopServer();
		    view.getDbPathButton().setEnabled(true);
		    view.getHostConnectionTextField().setEnabled(true);
		    view.getPortTextField().setEnabled(true);
		    view.getStartServerButton().setText("Start Server");
		    view.getServerStatusLabel().setText("Stopped");
		} catch (final Exception exception) {
		    showError(exception.getMessage());
		}
	    } else {
		final ServerConfiguration serverConfiguration = new ServerConfiguration(
			Integer.valueOf(view.getPortTextField().getText()),
			view.getHostConnectionTextField().getText());
		final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(
			new File(view.getDbPathTextField().getText()));
		try {
		    service.startServer(serverConfiguration,
			    databaseConfiguration);
		    view.getDbPathButton().setEnabled(false);
		    view.getHostConnectionTextField().setEnabled(false);
		    view.getPortTextField().setEnabled(false);
		    view.getStartServerButton().setText("Stop Server");
		    view.getServerStatusLabel().setText("Running");
		} catch (final Exception exception) {
		    logger.throwing(getClass().getName(), "actionPerformed",
			    exception);
		    showError(exception.getMessage());
		}
	    }
	}
    }

    /**
     * Opens a file chooser to specifiy the database path an stored the result
     * in the path text field.
     * 
     * @author arnelandwehr
     * 
     */
    private final class OpenTheFileChooserListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
	    final int returnVal = view.getDbPathChooser().showDialog(
		    view.getMainFrame(), "Select");
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		view.getDbPathTextField().setText(
			view.getDbPathChooser().getSelectedFile()
				.getAbsolutePath());
	    }

	}
    }

    /**
     * Plain Document that allows maximal4 digits as input.
     * 
     * @author arnelandwehr
     * 
     */
    private final class Maximal4DigitsAllowedDocumentListener extends
	    PlainDocument {

	/**
	 * the SUID.
	 */
	private static final long serialVersionUID = 8484820699506180583L;

	@Override
	public void insertString(final int arg0, final String arg1,
		final AttributeSet arg2) throws BadLocationException {
	    if (!arg1.matches("\\d")) {
		return;
	    }
	    if (arg0 >= 4) {
		return;
	    }

	    super.insertString(arg0, arg1, arg2);
	}
    }

    /**
     * {@link DocumentListener} that enables the start button if db path, host
     * address and port are specified, or disables it if this is not the case.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ToggleServerStartButtonIfConfigIsReady implements
	    DocumentListener {

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    // nothing to do
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    toggleStartButton();
	}

	/**
	 * Enables or disables the start button.
	 */
	private void toggleStartButton() {
	    final JTextField dbPathTextField = view.getDbPathTextField();
	    final JTextField portTextField = view.getPortTextField();
	    final JTextField hostConnectionTextField = view
		    .getHostConnectionTextField();

	    if (!dbPathTextField.getText().trim().equals("")
		    && !portTextField.getText().trim().equals("")
		    && !hostConnectionTextField.getText().trim().equals("")) {
		view.getStartServerButton().setEnabled(true);
	    } else {
		view.getStartServerButton().setEnabled(false);
	    }
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    toggleStartButton();
	}
    }

    /** exception logger, global is sufficient here. */
    private final Logger logger = Logger.getLogger("global");

    /** the view. */
    private final ServerAdminView view;

    /** the service to start or stop the server. */
    private final AdministrationService service;

    /** the property file. */
    private final UrlyBirdProperties properties;

    /**
     * Constructs a new {@link ServerAdminPresenter}.
     * 
     * @param view
     *            the by this presenter controlled view.
     * @param service
     *            the service to start or stop the server.
     * @param properties
     *            the properties to load or store the user input.
     */
    public ServerAdminPresenter(final ServerAdminView view,
	    final AdministrationService service,
	    final UrlyBirdProperties properties) {
	super();
	this.view = view;
	this.service = service;
	this.properties = properties;
    }

    /**
     * Initializes the gui, adds the needed listener and shows it to the user.
     */
    public void startGui() {
	SwingUtilities.invokeLater(new StartGuiRunnable());
    }

    /**
     * Adds the listener to the gui that controls the user input.
     */
    private void addListener() {
	view.getStartServerButton().setEnabled(false);
	view.getDbPathTextField().setEnabled(false);

	view.getPortTextField().setDocument(
		new Maximal4DigitsAllowedDocumentListener());

	view.getMainFrame().addWindowListener(new CloseApplicationListener());

	view.getDbPathButton().addActionListener(
		new OpenTheFileChooserListener());

	view.getDbPathTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleServerStartButtonIfConfigIsReady());
	view.getHostConnectionTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleServerStartButtonIfConfigIsReady());
	view.getPortTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleServerStartButtonIfConfigIsReady());

	view.getStartServerButton().addActionListener(
		new ToggleServerListener());
    }

    /**
     * Shows an error to the user with an {@link JOptionPane}.
     * 
     * @param message
     *            the error message that should be displyed.
     */
    private void showError(final String message) {
	JOptionPane.showMessageDialog(view.getMainFrame(), message, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

}
