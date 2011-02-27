package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.gui.UrlyBirdProperties.PropertyName;
import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;

public class DatabaseConnectionPresenter {

    private DatabaseConfiguration dbConfiguration;

    private final UrlyBirdProperties properties = UrlyBirdProperties
	    .getInstance();

    private final class ExitApplication extends WindowAdapter implements
	    ActionListener {

	@Override
	public void windowClosing(final WindowEvent arg0) {
	    askUserForExit();
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
	    askUserForExit();
	}

	private void askUserForExit() {
	    final int result = JOptionPane
		    .showConfirmDialog(
			    null,
			    "Exit?",
			    "Do you really want to exit, without connectiong to a database? The application will be terminated!",
			    JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
		System.exit(0);
	    }
	}
    }

    private final DatabaseConnectionPanel panel;

    private final AdministrationService service;

    public DatabaseConnectionPresenter(final DatabaseConnectionPanel panel,
	    final AdministrationService service) {
	super();
	this.panel = panel;
	this.service = service;
    }

    public DatabaseConfiguration askUserForInitialDatabaseConfiguration() {

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		startDatabaseDialog();
	    }
	});

	while (dbConfiguration == null) {
	    try {
		Thread.sleep(10);
	    } catch (final InterruptedException e) {
		e.printStackTrace();
	    }
	}

	return dbConfiguration;

    }

    private DatabaseConfiguration startDatabaseDialog() {
	final JDialog view = new JDialog(new JFrame(), "Database", true);
	view.getContentPane().add(panel);
	view.pack();
	view.addWindowListener(new ExitApplication());

	panel.getSelectButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent e) {

		final JFileChooser fileChooser = new JFileChooser();

		final int returnVal = fileChooser.showDialog(view,
			"Select Database Path");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    panel.getDatabaseTextField().setText(
			    fileChooser.getSelectedFile().getAbsolutePath());
		}

	    }
	});

	panel.getDatabaseTextField().getDocument()
		.addDocumentListener(new DocumentListener() {

		    @Override
		    public void removeUpdate(final DocumentEvent arg0) {
			panel.getConnectButton()
				.setEnabled(dbPathIsSpecified());
		    }

		    @Override
		    public void insertUpdate(final DocumentEvent arg0) {
			panel.getConnectButton()
				.setEnabled(dbPathIsSpecified());
		    }

		    @Override
		    public void changedUpdate(final DocumentEvent arg0) {
			panel.getConnectButton()
				.setEnabled(dbPathIsSpecified());
		    }

		    private boolean dbPathIsSpecified() {
			return !panel.getDatabaseTextField().getText().trim()
				.equals("");
		    }
		});

	panel.getDiscardButton().addActionListener(new ExitApplication());

	panel.getConnectButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {
		final String path = panel.getDatabaseTextField().getText()
			.trim();
		dbConfiguration = new DatabaseConfiguration(new File(path));
		try {
		    properties.setProperty(
			    PropertyName.CLIENT_DATABASE_GUI_PATH, path);
		} catch (final IOException e) {
		    e.printStackTrace();
		}
		view.dispose();
	    }
	});

	try {
	    final String path = properties
		    .getProperty(PropertyName.CLIENT_DATABASE_GUI_PATH);
	    panel.getDatabaseTextField().setText(path);
	} catch (final IOException e1) {
	    e1.printStackTrace();
	}

	view.setLocationRelativeTo(null);
	view.setVisible(true);

	return dbConfiguration;
    }
}
