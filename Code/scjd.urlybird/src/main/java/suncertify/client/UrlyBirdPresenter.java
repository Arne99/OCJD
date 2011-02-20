package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.common.roomoffer.RoomOfferService;

public class UrlyBirdPresenter {

    private final class ServerConnectionAction extends AbstractAction {

	public ServerConnectionAction(final String text) {
	    super(text);
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final JDialog connectionDialog = new JDialog(view.getMainFrame(),
		    true);
	    final ServerConnectionPanel serverConnectionPanel = new ServerConnectionPanel();

	    final JButton connectButton = serverConnectionPanel
		    .getConnectButton();
	    connectButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(final ActionEvent arg0) {

		}
	    });

	    connectionDialog.add(serverConnectionPanel);

	}

    }

    private final UrlyBirdView view;

    private final UrlyBirdProperties properties;

    private final RoomOfferService service;

    private final ClientMode mode;

    private UrlyBirdPresenter(final UrlyBirdView view,
	    final RoomOfferService service,
	    final UrlyBirdProperties properties, final ClientMode mode) {
	super();
	this.view = view;
	this.properties = properties;
	this.service = service;
	this.mode = mode;
    }

    public void startGui() {

	view.getConnectionMenuItem().addActionListener(
		new ServerConnectionAction("Connection"));

	// enable or disable

	switch (mode) {
	case CLIENT_SERVER: {

	    break;
	}
	case LOCAL: {
	    break;
	}

	}

	// addListener

	// setVisible

    }
}
