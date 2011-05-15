package suncertify.admin.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.admin.service.ServerConfiguration;
import suncertify.common.UrlyBirdProperties;
import suncertify.common.UrlyBirdProperties.PropertyName;

public final class ServerAdminPresenter {

    private final class EnableServerStartButtonIfConfigIsReady implements
	    DocumentListener {

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    System.out.println();
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    enableStartButton();
	}

	private void enableStartButton() {
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
	    enableStartButton();
	}
    }

    private final ServerAdminGui view;
    private final AdministrationService service;
    private final UrlyBirdProperties properties;

    public ServerAdminPresenter(final ServerAdminGui view,
	    final AdministrationService service,
	    final UrlyBirdProperties properties) {
	super();
	this.view = view;
	this.service = service;
	this.properties = properties;
    }

    public void startGui() {

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		view.init();
		initView();

		try {
		    view.getDbPathTextField()
			    .setText(
				    properties
					    .getProperty(PropertyName.ADMIN_GUI_DB_PATH));
		    view.getPortTextField()
			    .setText(
				    properties
					    .getProperty(PropertyName.ADMIN_GUI_PORT));
		    view.getHostConnectionTextField()
			    .setText(
				    properties
					    .getProperty(PropertyName.ADMIN_GUI_HOST));
		} catch (final IOException e) {
		    JOptionPane.showMessageDialog(
			    view.getMainFrame(),
			    "The default values could not be loaded! \n"
				    + e.getCause(), "", ImageObserver.ERROR);
		}
		view.show();
	    }
	});
    }

    private void initView() {
	view.getStartServerButton().setEnabled(false);
	view.getDbPathTextField().setEnabled(false);

	MaskFormatter numericFormatter = null;
	try {
	    numericFormatter = new MaskFormatter("####");
	} catch (final ParseException parseException) {
	    throw new RuntimeException(parseException);
	}
	view.getPortTextField().setFormatterFactory(
		new DefaultFormatterFactory(numericFormatter));

	view.getMainFrame().addWindowListener(new WindowAdapter() {

	    @Override
	    public void windowClosing(final WindowEvent e) {
		try {
		    if (service.isServerRunning()) {
			final int result = JOptionPane.showConfirmDialog(
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
				view.getMainFrame(),
				"Do you really want to exit?", "",
				JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
			    view.getMainFrame().dispose();
			}
		    }
		    properties.setProperty(PropertyName.ADMIN_GUI_DB_PATH, view
			    .getDbPathTextField().getText().trim());
		    properties.setProperty(PropertyName.ADMIN_GUI_HOST, view
			    .getHostConnectionTextField().getText().trim());
		    properties.setProperty(PropertyName.ADMIN_GUI_PORT, view
			    .getPortTextField().getText().trim());
		} catch (final Exception exception) {
		    exception.printStackTrace();
		    showError(exception.getMessage());
		} finally {
		    System.exit(0);
		}
	    }
	});

	view.getDbPathButton().addActionListener(new ActionListener() {

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
	});

	view.getDbPathTextField()
		.getDocument()
		.addDocumentListener(
			new EnableServerStartButtonIfConfigIsReady());
	view.getHostConnectionTextField()
		.getDocument()
		.addDocumentListener(
			new EnableServerStartButtonIfConfigIsReady());
	view.getPortTextField()
		.getDocument()
		.addDocumentListener(
			new EnableServerStartButtonIfConfigIsReady());

	view.getStartServerButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent e) {

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
			service.startStandAloneServer(serverConfiguration,
				databaseConfiguration);
			view.getDbPathButton().setEnabled(false);
			view.getHostConnectionTextField().setEnabled(false);
			view.getPortTextField().setEnabled(false);
			view.getStartServerButton().setText("Stop Server");
			view.getServerStatusLabel().setText("Running");
		    } catch (final Exception e1) {
			e1.printStackTrace();
			showError(e1.getMessage());
		    }
		}
	    }
	});
    }

    private void showError(final String message) {
	JOptionPane.showMessageDialog(view.getMainFrame(), message, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

}
