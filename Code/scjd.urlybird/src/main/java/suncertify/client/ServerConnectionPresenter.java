package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.Naming;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.gui.UrlyBirdProperties.PropertyName;
import suncertify.admin.service.ServerConfiguration;
import suncertify.client.UrlyBirdPresenter.ToggleButtonWhenTextChanges;
import suncertify.common.ClientServices;
import suncertify.common.roomoffer.RoomOfferService;

public class ServerConnectionPresenter {

    private final UrlyBirdProperties properties = UrlyBirdProperties
	    .getInstance();

    private RoomOfferService service = null;

    public RoomOfferService startInitialConnectionDialogToFindService() {

	// SwingUtils
	final ServerConnectionDialog dialog = new ServerConnectionDialog(null);

	dialog.getConnectButton().setEnabled(false);

	MaskFormatter numericFormatter = null;
	try {
	    numericFormatter = new MaskFormatter("####");
	} catch (final ParseException parseException) {
	    throw new RuntimeException(parseException);
	}
	dialog.getPortTextField().setFormatterFactory(
		new DefaultFormatterFactory(numericFormatter));

	dialog.getHostTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleButtonWhenTextChanges(dialog
				.getConnectButton(), dialog.getHostTextField(),
				dialog.getPortTextField()));
	dialog.getPortTextField()
		.getDocument()
		.addDocumentListener(
			new ToggleButtonWhenTextChanges(dialog
				.getConnectButton(), dialog.getHostTextField(),
				dialog.getPortTextField()));

	dialog.getConnectButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {

		final String host = dialog.getHostTextField().getText().trim();
		final int port = Integer.parseInt(dialog.getPortTextField()
			.getText().trim());

		final ServerConfiguration serverConfiguration = new ServerConfiguration(
			port, host);
		try {
		    final ClientServices services = (ClientServices) Naming
			    .lookup(serverConfiguration.getHostNameWithPort()
				    + "/"
				    + serverConfiguration
					    .getClientServiceName());

		    service = services.getRoomOfferService();

		} catch (final Exception e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null, e.getCause());
		    return;
		}

		try {
		    properties.setProperty(
			    PropertyName.CLIENT_CONNECTION_GUI_HOST, host);
		    properties.setProperty(
			    PropertyName.CLIENT_CONNECTION_GUI_PORT, port + "");
		} catch (final IOException e) {
		    e.printStackTrace();
		}
		dialog.dispose();
	    }
	});

	try {
	    final String host = properties
		    .getProperty(PropertyName.CLIENT_CONNECTION_GUI_HOST);
	    dialog.getHostTextField().setText(host);
	    final String port = properties
		    .getProperty(PropertyName.CLIENT_CONNECTION_GUI_PORT);
	    dialog.getPortTextField().setText(port);
	} catch (final IOException e1) {
	    e1.printStackTrace();
	}

	dialog.setLocationRelativeTo(null);
	dialog.setVisible(true);

	// TODO Das mist
	while (service == null) {
	    try {
		Thread.sleep(10);
	    } catch (final InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	return service;
    }

}
