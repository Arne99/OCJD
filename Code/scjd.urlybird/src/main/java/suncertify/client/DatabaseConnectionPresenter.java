package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.gui.UrlyBirdProperties.PropertyName;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;
import suncertify.db.DB;
import suncertify.db.DatabaseConnectionException;
import suncertify.db.DatabaseHandler;
import suncertify.db.DatabaseService;
import suncertify.domain.UrlyBirdRoomOfferService;

public class DatabaseConnectionPresenter implements ConnectionPresenter {

    private RoomOfferService roomOfferService;

    private final DatabaseService databaseService;

    private DB database;

    private final UrlyBirdProperties properties = UrlyBirdProperties
	    .getInstance();

    private final DatabaseConnectionPanel panel;

    public DatabaseConnectionPresenter(final DatabaseConnectionPanel panel,
	    final DatabaseService service) {
	super();
	this.panel = panel;
	this.databaseService = service;
    }

    public RoomOfferService connectToDatabaseWithDialog(final JFrame frame) {

	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    final JDialog dialog = new JDialog(frame, "Database", true);
		    initDatabaseDialog(dialog, new ExitApplication());
		}
	    });
	} catch (final InterruptedException e) {
	    e.printStackTrace();
	} catch (final InvocationTargetException e) {
	    e.printStackTrace();
	}

	return roomOfferService;
    }

    @Override
    public final RoomOfferService startConnectionDialog(final JFrame frame,
	    final RoomOfferService service) {

	this.roomOfferService = service;
	final JDialog dialog = new JDialog(frame, "Database", true);
	initDatabaseDialog(dialog, new ExitDialog(dialog));
	return roomOfferService;
    }

    private RoomOfferService initDatabaseDialog(final JDialog dialog,
	    final ExitDialogAdapter exitAdapter) {
	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	dialog.getContentPane().add(panel);
	dialog.pack();
	dialog.addWindowListener(exitAdapter);

	panel.getSelectButton().addActionListener(new ActionListener() {

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

	panel.getDiscardButton().addActionListener(exitAdapter);

	panel.getConnectButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {
		final String path = panel.getDatabaseTextField().getText()
			.trim();
		final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(
			new File(path));
		try {
		    final DatabaseHandler databaseHandler = DataFileService
			    .instance()
			    .getDatabaseHandler(
				    databaseConfiguration.getDatabaseLocation());
		    database = databaseService
			    .connectToDatabase(databaseHandler);
		    roomOfferService = new UrlyBirdRoomOfferService(database);
		} catch (final DatabaseConnectionException connectionException) {
		    JOptionPane.showMessageDialog(null,
			    connectionException.getMessage(),
			    "Connection Failure!", JOptionPane.ERROR_MESSAGE);
		    return;
		} catch (final IOException e) {
		    e.printStackTrace();
		} catch (final UnsupportedDataFileFormatException e) {
		    e.printStackTrace();
		}

		try {
		    properties.setProperty(
			    PropertyName.CLIENT_DATABASE_GUI_PATH, path);
		} catch (final IOException e) {
		    e.printStackTrace();
		}
		dialog.dispose();
	    }
	});

	try {
	    final String path = properties
		    .getProperty(PropertyName.CLIENT_DATABASE_GUI_PATH);
	    panel.getDatabaseTextField().setText(path);
	} catch (final IOException e1) {
	    e1.printStackTrace();
	}

	dialog.setLocationRelativeTo(null);
	dialog.setVisible(true);

	return new UrlyBirdRoomOfferService(database);
    }
}
