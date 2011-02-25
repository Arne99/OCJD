package suncertify.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.admin.service.ServerConfiguration;
import suncertify.common.ClientCallback;
import suncertify.common.ClientServices;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.domain.RoomOffer;

public final class UrlyBirdPresenter {

    /**
     * 
     * @author arnelandwehr
     * 
     */
    private final class StartFindDialog implements ActionListener {

	private final class FindRooms implements ActionListener {
	    private final FindDialog dialog;

	    private FindRooms(final FindDialog dialog) {
		this.dialog = dialog;
	    }

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {

		final JTextField hotelTextField = dialog.getHotelTextField();
		final String hotelCriteria = (hotelTextField.isEnabled()) ? hotelTextField
			.getText().trim() : null;

		final JTextField locationTextField = dialog
			.getLocationTextField();
		final String locationCriteria = (locationTextField.isEnabled()) ? hotelTextField
			.getText().trim() : null;

		final FindRoomCommand command = new FindRoomCommand(
			hotelCriteria, locationCriteria, dialog
				.getAndRadioButton().isSelected());

		try {
		    service.findRoomOffer(command, new FindCallback());

		} catch (final RemoteException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final FindDialog dialog = new FindDialog(view.getMainFrame());

	    dialog.getFindButton().setEnabled(false);

	    final ToggleFindButton toggleFindButton = new ToggleFindButton(
		    dialog);

	    dialog.getHotelTextField().getDocument()
		    .addDocumentListener(toggleFindButton);

	    dialog.getLocationTextField().getDocument()
		    .addDocumentListener(toggleFindButton);

	    dialog.getAnyHotelCheckBox().addActionListener(toggleFindButton);
	    dialog.getAnyLocationCheckBox().addActionListener(toggleFindButton);
	    dialog.getAnyHotelCheckBox()
		    .addChangeListener(
			    new ToggleTextFieldIfCheckBoxIsSelected(dialog
				    .getAnyHotelCheckBox(), dialog
				    .getHotelTextField()));

	    dialog.getAnyLocationCheckBox().addChangeListener(
		    new ToggleTextFieldIfCheckBoxIsSelected(dialog
			    .getAnyLocationCheckBox(), dialog
			    .getLocationTextField()));

	    dialog.getDiscardButton().addActionListener(
		    new DisposeDialog(dialog));

	    dialog.getFindButton().addActionListener(new FindRooms(dialog));

	    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);

	}
    }

    private final static class DisposeDialog implements ActionListener {
	private final FindDialog dialog;

	private DisposeDialog(final FindDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
	    dialog.dispose();
	}
    }

    private final static class ToggleButtonWhenTextChanges implements
	    DocumentListener {

	private final JButton buttonToToggle;
	private final JTextField[] textFields;

	private ToggleButtonWhenTextChanges(final JButton buttonToToggle,
		final JTextField... textFields) {
	    this.buttonToToggle = buttonToToggle;
	    this.textFields = textFields;
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    if (!isAllTextFilled()) {
		buttonToToggle.setEnabled(false);
	    }
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    if (isAllTextFilled()) {
		buttonToToggle.setEnabled(true);
	    }
	}

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    // nothing to do
	}

	private boolean isAllTextFilled() {

	    for (final JTextField field : textFields) {
		if (field.getText().trim().equals("")) {
		    return false;
		}
	    }
	    return true;
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

	view.getFindButton().addActionListener(new StartFindDialog());

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
		    JOptionPane.showMessageDialog(dialog, e.getCause());
		    return;
		}

		dialog.dispose();
	    }
	});

	dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	dialog.setVisible(true);

    }

    private final static class ToggleFindButton implements ActionListener,
	    DocumentListener {

	private final FindDialog findDialog;

	private ToggleFindButton(final FindDialog findDialog) {
	    super();
	    this.findDialog = findDialog;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
	    toggleFindButton();
	}

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    toggleFindButton();
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    toggleFindButton();
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    toggleFindButton();
	}

	private void toggleFindButton() {
	    final JTextField hotelTextField = findDialog.getHotelTextField();
	    final JCheckBox anyHotelCheckBox = findDialog.getAnyHotelCheckBox();
	    final boolean hotelCriteriaIsSpecified = !hotelTextField.getText()
		    .trim().equals("")
		    || anyHotelCheckBox.isSelected();

	    final JTextField locationTextField = findDialog
		    .getLocationTextField();
	    final JCheckBox anyLocationCheckBox = findDialog
		    .getAnyLocationCheckBox();
	    final boolean locationCriteriaIsSpecified = !locationTextField
		    .getText().trim().equals("")
		    || anyLocationCheckBox.isSelected();

	    findDialog.getFindButton().setEnabled(
		    hotelCriteriaIsSpecified && locationCriteriaIsSpecified);
	}
    }

    private final static boolean askForUserDecision(final Component frame,
	    final String title, final String message) {
	// TODO SwingWorker
	final int result = JOptionPane.showInternalConfirmDialog(frame,
		message, title, JOptionPane.YES_NO_OPTION,
		JOptionPane.INFORMATION_MESSAGE);
	return result == JOptionPane.OK_OPTION;
    }

    private final static void showUserTheError(final Component parent,
	    final String message) {

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		JOptionPane.showMessageDialog(parent, message, "Error",
			JOptionPane.ERROR_MESSAGE);
	    }
	});
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

    private final static class ToggleTextFieldIfCheckBoxIsSelected implements
	    ChangeListener {

	private final JCheckBox checkBox;
	private final JTextField textField;

	private ToggleTextFieldIfCheckBoxIsSelected(final JCheckBox checkBox,
		final JTextField textField) {
	    this.checkBox = checkBox;
	    this.textField = textField;
	}

	@Override
	public void stateChanged(final ChangeEvent arg0) {
	    textField.setEnabled(!checkBox.isSelected());

	}
    }

    private final static class FindCallback implements
	    ClientCallback<List<RoomOffer>> {

	private List<RoomOffer> rooms;

	@Override
	public boolean onWarning(final String message) {
	    return askForUserDecision(null, "", message);
	}

	@Override
	public void onSuccess(final List<RoomOffer> result) {

	    rooms = new ArrayList<RoomOffer>(result);
	}

	@Override
	public void onFailure(final String message) {
	    showUserTheError(null, message);
	}

	public List<RoomOffer> getRooms() {
	    return rooms;
	}

    }

}
