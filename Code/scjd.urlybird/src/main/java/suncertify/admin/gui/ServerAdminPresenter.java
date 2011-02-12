package suncertify.admin.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.admin.service.ServerConfiguration;

public final class ServerAdminPresenter {

    private final class EnableServerStartButtonIfConfigIsReady implements
	    ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {

	    final JTextField dbPathTextField = view.getDbPathTextField();
	    final JTextField portTextField = view.getPortTextField();
	    final JTextField hostConnectionTextField = view
		    .getHostConnectionTextField();

	    if (!dbPathTextField.getText().equals("")
		    && !portTextField.getText().equals("")
		    && !hostConnectionTextField.getText().equals("")) {
		view.getStartServerButton().setEnabled(true);
	    } else {
		view.getStartServerButton().setEnabled(false);
	    }

	}
    }

    private final ServerConsoleView view;
    private final AdministrationService service;

    private ServerAdminPresenter(final ServerConsoleView view,
	    final AdministrationService service) {
	super();
	this.view = view;
	this.service = service;
    }

    public void startGui() {

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
		    service.stopServer();
		} catch (final Exception exception) {
		    exception.printStackTrace();
		    showError(exception.getMessage());
		}
		view.getMainFrame().dispose();
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

	view.getDbPathTextField().addActionListener(
		new EnableServerStartButtonIfConfigIsReady());
	view.getHostConnectionTextField().addActionListener(
		new EnableServerStartButtonIfConfigIsReady());
	view.getPortTextField().addActionListener(
		new EnableServerStartButtonIfConfigIsReady());

	view.getStartServerButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final ServerConfiguration serverConfiguration = new ServerConfiguration(
			Integer.valueOf(view.getPortTextField().getText()),
			view.getHostConnectionTextField().getText());
		final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(
			new File(view.getDbPathTextField().getText()));
		try {
		    service.startStandAloneServer(serverConfiguration,
			    databaseConfiguration);
		} catch (final Exception e1) {
		    e1.printStackTrace();
		    showError(e1.getMessage());
		}
	    }
	});

	view.startGui();
    }

    private void showError(final String message) {
	JOptionPane.showMessageDialog(view.getMainFrame(), message, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

    public static void main(final String[] args) {
	final ServerAdminPresenter presenter = new ServerAdminPresenter(
		new ServerAdminGui(), new AdministrationService());

	presenter.startGui();
    }
}
