package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.google.common.io.Files;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.admin.service.ServerConfiguration;
import suncertify.common.ClientCallback;
import suncertify.common.ClientServices;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.domain.RoomOffer;

public class UrlyBirdPresenter {

    private final class ToggleConnectionButton implements DocumentListener {
	private final ServerConnectionDialog dialog;

	private ToggleConnectionButton(final ServerConnectionDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    if (!isConnectionSpecified(dialog)) {
		dialog.getConnectButton().setEnabled(false);
	    }
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    if (isConnectionSpecified(dialog)) {
		dialog.getConnectButton().setEnabled(true);
	    }
	}

	private boolean isConnectionSpecified(
		final ServerConnectionDialog dialog) {
	    return !dialog.getHostTextField().getText().trim().equals("")
		    && !dialog.getPortTextField().getText().trim().equals("");
	}

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    // nothing to do here
	}
    }

    private final UrlyBirdView view;

    private final UrlyBirdProperties properties;

    private RoomOfferService service;

    private final ClientMode mode;

    private UrlyBirdPresenter(final UrlyBirdView view,
	    final UrlyBirdProperties properties, final ClientMode mode) {
	this.view = view;
	this.properties = properties;
	this.mode = mode;

    }

    public void startGui() {

	view.init();
	if (service == null) {
	    startConnectionDialogToFindService();
	}

	view.getFindButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {

		final FindDialog dialog = new FindDialog(view.getMainFrame());

		dialog.getFindButton().setEnabled(false);

		dialog.getHotelTextField().getDocument()
			.addDocumentListener(new DocumentListener() {

			    @Override
			    public void removeUpdate(final DocumentEvent arg0) {
				if (!isFindConfigured(dialog)) {
				    dialog.getFindButton().setEnabled(false);
				}
			    }

			    private boolean isFindConfigured(
				    final FindDialog dialog) {
				return !dialog.getHotelTextField().getText()
					.trim().equals("")
					&& !dialog.getLocationTextField()
						.getText().trim().equals("");
			    }

			    @Override
			    public void insertUpdate(final DocumentEvent arg0) {
				if (isFindConfigured(dialog)) {
				    dialog.getFindButton().setEnabled(true);
				}
			    }

			    @Override
			    public void changedUpdate(final DocumentEvent arg0) {
				// nothing to do
			    }
			});

		dialog.getDiscardButton().addActionListener(
			new ActionListener() {

			    @Override
			    public void actionPerformed(final ActionEvent arg0) {
				dialog.dispose();
			    }
			});

		dialog.getFindButton().addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(final ActionEvent arg0) {

			final String hotelName = dialog.getHotelTextField()
				.getText().trim();
			final String location = dialog.getLocationTextField()
				.getText().trim();

			final FindRoomCommand command = new FindRoomCommand(
				hotelName, location, dialog.getAndRadioButton()
					.isSelected());

			try {
			    service.findRoomOffer(command,
				    new ClientCallback<List<RoomOffer>>() {

					@Override
					public boolean onWarning(
						final String message) {
					    return true;
					}

					@Override
					public void onSuccess(
						final List<RoomOffer> result) {
					    // view.getRoomTable().setModel(
					    // new DefaultTableModel(arg0,
					    // arg1));

					}

					@Override
					public void onFailure(
						final String message) {
					    // TODO Auto-generated method stub

					}
				    });
			} catch (final RemoteException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		});

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

	    }
	});

	view.show();
    }

    private void startConnectionDialogToFindService() {

	final ServerConnectionDialog dialog = new ServerConnectionDialog(
		view.getMainFrame());

	dialog.getConnectButton().setEnabled(false);
	dialog.getDiscardButton().setEnabled(service != null);

	MaskFormatter numericFormatter = null;
	try {
	    numericFormatter = new MaskFormatter("####");
	} catch (final ParseException parseException) {
	    throw new RuntimeException(parseException);
	}
	dialog.getPortTextField().setFormatterFactory(
		new DefaultFormatterFactory(numericFormatter));

	dialog.getHostTextField().getDocument()
		.addDocumentListener(new ToggleConnectionButton(dialog));
	dialog.getPortTextField().getDocument()
		.addDocumentListener(new ToggleConnectionButton(dialog));

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
		    JOptionPane.showMessageDialog(dialog, e.getCause());
		    return;
		}

		dialog.dispose();
	    }
	});

	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	dialog.setVisible(true);

    }

    public static void main(final String[] args) throws Exception {

	final File anyFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");

	final AdministrationService service = new AdministrationService();

	final ServerConfiguration serverConfig = new ServerConfiguration();
	final DatabaseConfiguration dataConfig = new DatabaseConfiguration(
		anyFile);

	service.startStandAloneServer(serverConfig, dataConfig);

	final UrlyBirdPresenter urlyBirdPresenter = new UrlyBirdPresenter(
		new UrlyBirdView(), null, null);
	SwingUtilities.invokeAndWait(new Runnable() {

	    @Override
	    public void run() {
		urlyBirdPresenter.startGui();
	    }
	});
    }

}
