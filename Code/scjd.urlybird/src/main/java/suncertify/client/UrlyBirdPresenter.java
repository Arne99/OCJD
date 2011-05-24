package suncertify.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import suncertify.common.BookRoomCommand;
import suncertify.common.FindRoomCommand;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;
import suncertify.common.UrlyBirdProperties;

public final class UrlyBirdPresenter {

    private final class ToggleBookButtonListener implements DocumentListener {
	private final BookDialog dialog;
	private final JTextField customerIdTextField;

	private ToggleBookButtonListener(final BookDialog dialog,
		final JTextField customerIdTextField) {
	    this.dialog = dialog;
	    this.customerIdTextField = customerIdTextField;
	}

	@Override
	public void removeUpdate(final DocumentEvent e) {
	    dialog.getBookButton().setEnabled(isCustomerIdComplete());
	}

	@Override
	public void insertUpdate(final DocumentEvent e) {
	    dialog.getBookButton().setEnabled(isCustomerIdComplete());
	}

	@Override
	public void changedUpdate(final DocumentEvent e) {
	    dialog.getBookButton().setEnabled(isCustomerIdComplete());
	}

	private boolean isCustomerIdComplete() {
	    return customerIdTextField.getText().trim().matches("\\d{8}");
	}
    }

    private final class FindAllRooms implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    try {
		final List<RoomOffer> rooms = service
			.findRoomOffer(new FindRoomCommand(null, null, false));
		tableModel.replaceAll(rooms);
	    } catch (final Exception e) {
		showUserTheError(view.getMainFrame(), e.getMessage());
		e.printStackTrace();
	    }
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
		final String locationCriteria = (locationTextField.isEnabled()) ? locationTextField
			.getText().trim() : null;

		final FindRoomCommand command = new FindRoomCommand(
			hotelCriteria, locationCriteria, dialog
				.getAndRadioButton().isSelected());

		try {
		    final List<RoomOffer> rooms = service
			    .findRoomOffer(command);
		    tableModel.replaceAll(rooms);
		    dialog.dispose();
		} catch (final Exception e) {
		    showUserTheError(dialog, e.getMessage());
		}
	    }
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final FindDialog dialog = new FindDialog(view.getMainFrame());
	    dialog.setLocationRelativeTo(view.getMainFrame());

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

    public final static class ToggleButtonWhenTextChanges implements
	    DocumentListener {

	private final JButton buttonToToggle;
	private final JTextField[] textFields;

	ToggleButtonWhenTextChanges(final JButton buttonToToggle,
		final JTextField... textFields) {
	    this.buttonToToggle = buttonToToggle;
	    this.textFields = textFields;
	}

	@Override
	public void changedUpdate(final DocumentEvent arg0) {
	    // nothing to do
	}

	@Override
	public void insertUpdate(final DocumentEvent arg0) {
	    if (isAllTextFilled()) {
		buttonToToggle.setEnabled(true);
	    }
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    if (!isAllTextFilled()) {
		buttonToToggle.setEnabled(false);
	    }
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

    private final UrlyBirdView view;

    private final UrlyBirdProperties properties;

    private RoomOfferService service;

    private RoomTableModel tableModel;

    private final ConnectionPresenter connectionPresenter;

    public UrlyBirdPresenter(final UrlyBirdView view,
	    final UrlyBirdProperties properties,
	    final RoomOfferService service,
	    final ConnectionPresenter connectionPresenter) {
	this.view = view;
	this.properties = properties;
	this.service = service;
	this.connectionPresenter = connectionPresenter;
	tableModel = new RoomTableModel();

    }

    public void startGui() {

	view.init();
	view.getMainFrame().setLocationRelativeTo(null);
	view.getMainFrame().addWindowListener(new ExitApplication());

	final JTable roomTable = view.getRoomTable();
	tableModel = RoomTableModel.initTable(roomTable);

	view.getConnectionMenuItem().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {
		service = connectionPresenter.startConnectionDialog(
			view.getMainFrame(), service);
	    }
	});
	view.getChangeButton().setEnabled(false);
	view.getBookButton().setEnabled(false);
	view.getNewButton().setEnabled(false);
	view.getDeleteButton().setEnabled(false);

	view.getFindButton().addActionListener(new StartFindDialog());
	view.getAllButton().addActionListener(new FindAllRooms());
	view.getExitButton().addActionListener(new ExitApplication());
	view.getRoomTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

		    @Override
		    public void valueChanged(final ListSelectionEvent e) {
			view.getBookButton().setEnabled(
				view.getRoomTable().getSelectedRow() != -1);
		    }
		});

	view.getBookButton().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {

		final int selectedViewRow = view.getRoomTable()
			.getSelectedRow();
		final int selectedModelRow = view.getRoomTable()
			.convertRowIndexToModel(selectedViewRow);

		final BookDialog dialog = new BookDialog(view.getMainFrame());
		dialog.setLocationRelativeTo(view.getMainFrame());

		final JTextField hotelTextField = dialog.getHotelTextField();
		hotelTextField.setEnabled(false);
		hotelTextField.setText(tableModel
			.getHotelNameAtIndex(selectedModelRow));

		final JTextField locationTextField = dialog
			.getLocationTextField();
		locationTextField.setEnabled(false);
		locationTextField.setText(tableModel
			.getLocationAtIndex(selectedModelRow));

		final JTextField smokingTextField = dialog
			.getSmokingTextField();
		smokingTextField.setEnabled(false);
		smokingTextField.setText(tableModel
			.getSmokingAtIndex(selectedModelRow));

		final JTextField dateTextField = dialog.getDateTextField();
		dateTextField.setEnabled(false);
		dateTextField.setText(tableModel
			.getDateAtIndex(selectedModelRow));

		final JTextField priceTextField = dialog.getPriceTextField();
		priceTextField.setEnabled(false);
		priceTextField.setText(tableModel
			.getPriceAtIndex(selectedModelRow));

		final JTextField idTextField = dialog.getIdTextField();
		idTextField.setEnabled(false);
		idTextField.setText(tableModel.getIdAtIndex(selectedModelRow));

		final JTextField sizeTextField = dialog.getSizeTextField();
		sizeTextField.setEnabled(false);
		sizeTextField.setText(tableModel
			.getRoomSizeAtIndex(selectedModelRow));

		final JTextField customerIdTextField = dialog
			.getCustomerIdTextField();
		customerIdTextField.setDocument(new PlainDocument() {

		    @Override
		    public void insertString(final int offs, final String str,
			    final AttributeSet a) throws BadLocationException {
			if (offs <= 7 && str.matches("\\d")) {
			    super.insertString(offs, str, a);
			}
		    }

		});

		final ToggleBookButtonListener toggleBookButtonListener = new ToggleBookButtonListener(
			dialog, customerIdTextField);

		customerIdTextField.getDocument().addDocumentListener(
			toggleBookButtonListener);

		dialog.getBookButton().setEnabled(false);
		dialog.getBookButton().addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(final ActionEvent arg0) {

			final int selectedViewIndex = roomTable
				.getSelectedRow();
			final int modelIndex = roomTable
				.convertRowIndexToModel(selectedViewIndex);
			final RoomOffer roomToBook = tableModel
				.getRoomAtIndex(modelIndex);

			try {
			    final RoomOffer bookedRoomOffer = service
				    .bookRoomOffer(new BookRoomCommand(
					    roomToBook, dialog
						    .getCustomerIdTextField()
						    .getText().trim()));
			    tableModel.replaceRoom(bookedRoomOffer);
			    dialog.dispose();
			} catch (final Exception e) {
			    showUserTheError(null, e.getMessage());
			    e.printStackTrace();
			}
		    }
		});

		dialog.getDiscardButton().addActionListener(
			new ExitDialog(dialog));

		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    }
	});

	view.show();
    }
}
