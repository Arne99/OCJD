package suncertify.client;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.text.PlainDocument;

import suncertify.common.BookRoomCommand;
import suncertify.common.FindRoomCommand;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;

/**
 * An <code>UrlyBirdPresenter</code> controls the main UrlyBird Gui which
 * displays the user the {@link RoomOffer} details and allows him to book or
 * find rooms. The book and find operations are supported by different dialogs
 * that are also controlled by this presenter.
 * 
 * @author arnelandwehr
 * 
 */
public final class UrlyBirdPresenter {

    /**
     * exception logger, global is sufficient here.
     */
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Starts a new book dialog that enables the user to book an selected
     * {@link RoomOffer}.
     * 
     * @author arnelandwehr
     * 
     */
    private final class StartBookDialogListener implements ActionListener {

	/**
	 * Books the {@link RoomOffer}.
	 * 
	 * @author arnelandwehr
	 * 
	 */
	private final class BookTheSelectedRoomListener implements
		ActionListener {

	    /** the book dialog. */
	    private final BookDialog dialog;

	    /**
	     * Constructs a new <code>BookTheSelectedRoomListener</code>.
	     * 
	     * @param dialog
	     *            the book dialog
	     */
	    private BookTheSelectedRoomListener(final BookDialog dialog) {
		this.dialog = dialog;
	    }

	    @Override
	    public void actionPerformed(final ActionEvent arg0) {

		final int selectedViewIndex = roomTable.getSelectedRow();
		final int modelIndex = roomTable
			.convertRowIndexToModel(selectedViewIndex);
		final RoomOffer roomToBook = tableModel
			.getRoomAtIndex(modelIndex);

		try {
		    final RoomOffer bookedRoomOffer = service
			    .bookRoomOffer(new BookRoomCommand(roomToBook,
				    dialog.getCustomerIdTextField().getText()
					    .trim()));
		    tableModel.replaceRoom(bookedRoomOffer);
		    dialog.dispose();
		} catch (final Exception e) {
		    showUserTheError(null, e.getMessage());
		    logger.throwing(getClass().getSimpleName(),
			    "actionPerformed", e);
		}
	    }
	}

	/**
	 * Only allows max eight digits as input.
	 * 
	 * @author arnelandwehr
	 * 
	 */
	private final class AllowsOnly8DigitsAsInputListener extends
		PlainDocument {

	    /** the SUID. */
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void insertString(final int offs, final String str,
		    final AttributeSet a) throws BadLocationException {
		if (offs <= 7 && str.matches("\\d")) {
		    super.insertString(offs, str, a);
		}
	    }
	}

	/** the table that contains all bookable {@link RoomOffer}s . */
	private final JTable roomTable;

	/**
	 * Constructs a new <code>StartBookDialogListener</code>.
	 * 
	 * @param roomTable
	 *            the room table that contains the bookable
	 *            <code>RoomOffers</code>.
	 */
	private StartBookDialogListener(final JTable roomTable) {
	    this.roomTable = roomTable;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final BookDialog dialog = fillGuiWithTableValues();
	    dialog.getBookButton().setEnabled(false);
	    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	    addListeners(dialog);

	    dialog.setVisible(true);
	}

	/**
	 * Adds all listeners to the given book dialog.
	 * 
	 * @param dialog
	 *            the book dialog.
	 */
	private void addListeners(final BookDialog dialog) {
	    final JTextField customerIdTextField = dialog
		    .getCustomerIdTextField();
	    customerIdTextField
		    .setDocument(new AllowsOnly8DigitsAsInputListener());

	    final ToggleBookButtonListener toggleBookButtonListener = new ToggleBookButtonListener(
		    dialog, customerIdTextField);

	    customerIdTextField.getDocument().addDocumentListener(
		    toggleBookButtonListener);

	    dialog.getBookButton().addActionListener(
		    new BookTheSelectedRoomListener(dialog));

	    dialog.getDiscardButton().addActionListener(new ExitDialog(dialog));
	}

	/**
	 * Fills the view with the selected {@link RoomOffer} values.
	 * 
	 * @return the filled book dialog.
	 */
	private BookDialog fillGuiWithTableValues() {
	    final int selectedViewRow = view.getRoomTable().getSelectedRow();
	    final int selectedModelRow = view.getRoomTable()
		    .convertRowIndexToModel(selectedViewRow);

	    final BookDialog dialog = new BookDialog(view.getMainFrame());
	    dialog.setLocationRelativeTo(view.getMainFrame());

	    final JTextField hotelTextField = dialog.getHotelTextField();
	    hotelTextField.setEnabled(false);
	    hotelTextField.setText(tableModel
		    .getHotelNameAtIndex(selectedModelRow));

	    final JTextField locationTextField = dialog.getLocationTextField();
	    locationTextField.setEnabled(false);
	    locationTextField.setText(tableModel
		    .getLocationAtIndex(selectedModelRow));

	    final JTextField smokingTextField = dialog.getSmokingTextField();
	    smokingTextField.setEnabled(false);
	    smokingTextField.setText(tableModel
		    .getSmokingAtIndex(selectedModelRow));

	    final JTextField dateTextField = dialog.getDateTextField();
	    dateTextField.setEnabled(false);
	    dateTextField.setText(tableModel.getDateAtIndex(selectedModelRow));

	    final JTextField priceTextField = dialog.getPriceTextField();
	    priceTextField.setEnabled(false);
	    priceTextField
		    .setText(tableModel.getPriceAtIndex(selectedModelRow));

	    final JTextField idTextField = dialog.getIdTextField();
	    idTextField.setEnabled(false);
	    idTextField.setText(tableModel.getIdAtIndex(selectedModelRow));

	    final JTextField sizeTextField = dialog.getSizeTextField();
	    sizeTextField.setEnabled(false);
	    sizeTextField.setText(tableModel
		    .getRoomSizeAtIndex(selectedModelRow));
	    return dialog;
	}
    }

    /**
     * Enables or disables the book button if a row in the table is selected or
     * not.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ToggleBookButtonIfRowIsSelected implements
	    ListSelectionListener {
	@Override
	public void valueChanged(final ListSelectionEvent e) {
	    view.getBookButton().setEnabled(
		    view.getRoomTable().getSelectedRow() != -1);
	}
    }

    /**
     * Shows a modal dialog to the user that allows him to specify a new
     * connection.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ShowConnectionDialogListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent arg0) {
	    service = serviceProvider.startRoomOfferServiceProviderDialog(
		    view.getMainFrame(), service);
	}
    }

    /**
     * Enables the book button if the customerId TextField is filled or disables
     * it if the field is empty.
     * 
     * @author arnelandwehr
     * 
     */
    private final class ToggleBookButtonListener implements DocumentListener {

	/** the dialog of the book button. */
	private final BookDialog dialog;

	/** the customerid {@link TextField}. */
	private final JTextField customerIdTextField;

	/**
	 * Construct a new {@link ToggleBookButtonListener}.
	 * 
	 * @param dialog
	 *            the dialog of the book button.
	 * @param customerIdTextField
	 *            the customerid {@link TextField}.
	 */
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

	/**
	 * Specifies if the customerid in the customerid {@link TextField} is
	 * correctly filled.
	 * 
	 * @return <code>true</code> if the customerid is filled.
	 */
	private boolean isCustomerIdComplete() {
	    return customerIdTextField.getText().trim().matches("\\d{8}");
	}
    }

    /**
     * Executes a search for all rooms and displays the result in the roomtable.
     * 
     * @author arnelandwehr
     * 
     */
    private final class FindAllRoomsListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    try {
		final List<RoomOffer> rooms = service
			.findRoomOffer(new FindRoomCommand(null, null, false));
		tableModel.replaceAll(rooms);
	    } catch (final Exception e) {
		showUserTheError(view.getMainFrame(), e.getMessage());
		logger.throwing(this.getClass().getSimpleName(),
			"actionPerformed", e);
	    }
	}
    }

    /**
     * Disposes the {@link FindDialog}.
     * 
     * @author arnelandwehr
     * 
     */
    private static final class DisposeFindDialogListener implements
	    ActionListener {

	/** the find dialog to dispose. */
	private final FindDialog dialog;

	/**
	 * Construct a new <code>DisposeFindDialog</code>.
	 * 
	 * @param dialog
	 *            the find dialog to dispose.
	 */
	private DisposeFindDialogListener(final FindDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
	    dialog.dispose();
	}
    }

    /**
     * Opens a modal dialog that enables the user to search for
     * {@link RoomOffer}s that match an specified criteria.
     * 
     * @author arnelandwehr
     */
    private final class FindRoomsListener implements ActionListener {

	/** the find rooms dialog to open. */
	private final FindDialog dialog;

	/**
	 * Construct a new {@link FindRoomsListener}.
	 * 
	 * @param dialog
	 *            the dialog to open.
	 */
	private FindRoomsListener(final FindDialog dialog) {
	    this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent event) {

	    final JTextField hotelTextField = dialog.getHotelTextField();
	    final String hotelCriteria = (hotelTextField.isEnabled()) ? hotelTextField
		    .getText().trim() : null;

	    final JTextField locationTextField = dialog.getLocationTextField();
	    final String locationCriteria = (locationTextField.isEnabled()) ? locationTextField
		    .getText().trim() : null;

	    final FindRoomCommand command = new FindRoomCommand(hotelCriteria,
		    locationCriteria, dialog.getAndRadioButton().isSelected());

	    try {
		final List<RoomOffer> rooms = service.findRoomOffer(command);
		tableModel.replaceAll(rooms);
		dialog.dispose();
	    } catch (final Exception e) {
		showUserTheError(dialog, e.getMessage());
	    }
	}
    }

    /**
     * Starts a dialog which allows the user to specify criteria for a room
     * search and execute the search.
     * 
     * @author arnelandwehr
     * 
     */
    private final class StartFindDialogListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	    final FindDialog dialog = new FindDialog(view.getMainFrame());
	    dialog.setLocationRelativeTo(view.getMainFrame());
	    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    dialog.getFindButton().setEnabled(false);

	    addListenersToFindDialog(dialog);

	    dialog.setVisible(true);

	}

	/**
	 * Adds all Listeners to the {@link FindDialog}.
	 * 
	 * @param dialog
	 *            the {@link FindDialog}.
	 */
	private void addListenersToFindDialog(final FindDialog dialog) {

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
			    new ToggleTextFieldIfCheckBoxIsSelectedListener(
				    dialog.getAnyHotelCheckBox(), dialog
					    .getHotelTextField()));

	    dialog.getAnyLocationCheckBox().addChangeListener(
		    new ToggleTextFieldIfCheckBoxIsSelectedListener(dialog
			    .getAnyLocationCheckBox(), dialog
			    .getLocationTextField()));

	    dialog.getDiscardButton().addActionListener(
		    new DisposeFindDialogListener(dialog));

	    dialog.getFindButton().addActionListener(
		    new FindRoomsListener(dialog));
	}
    }

    /**
     * Enables an button if the text of all given {@link TextField}s contains a
     * non whitespace char, disables the button if any given {@link TextField}
     * has no input.
     * 
     * @author arnelandwehr
     * 
     */
    public static final class ToggleButtonWhenTextChanges implements
	    DocumentListener {

	/** the button to toggle . */
	private final JButton buttonToToggle;

	/** the {@link TextField} to enable/disable the button. */
	private final JTextField[] textFields;

	/**
	 * Constructs a new <code>ToggleButtonWhenTextChanges</code>.
	 * 
	 * @param buttonToToggle
	 *            the button to toggle
	 * @param textFields
	 *            the textfield
	 */
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
	    if (isEveryTextFieldFilled()) {
		buttonToToggle.setEnabled(true);
	    }
	}

	@Override
	public void removeUpdate(final DocumentEvent arg0) {
	    if (!isEveryTextFieldFilled()) {
		buttonToToggle.setEnabled(false);
	    }
	}

	/**
	 * Specifies if every given {@link TextField} contains at least one non
	 * whitespace char.
	 * 
	 * @return <code>true</code> if all <code>TextField</code> contains at
	 *         least one non whitespace char.
	 */
	private boolean isEveryTextFieldFilled() {

	    for (final JTextField field : textFields) {
		if (field.getText().trim().equals("")) {
		    return false;
		}
	    }
	    return true;
	}

    }

    /**
     * Enables the <code>FindButton</code> if the user has specified the search
     * criteria, disables it if the criteria is not completely specified.
     * 
     * @author arnelandwehr
     * 
     */
    private static final class ToggleFindButton implements ActionListener,
	    DocumentListener {

	/** the find dialog. */
	private final FindDialog findDialog;

	/**
	 * Construct a new <code>ToggleFindButton</code>.
	 * 
	 * @param findDialog
	 *            the find dialog.
	 */
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

	/**
	 * Enables the find button if the location criteria and the hotel
	 * criteria is specified or disables it in the opposite case.
	 * 
	 */
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

    /**
     * Enables an TextField if the given {@link Checkbox} is not checked and
     * disables it if the <code>Checkbox</code> is checked.
     * 
     * @author arnelandwehr
     */
    private static final class ToggleTextFieldIfCheckBoxIsSelectedListener
	    implements ChangeListener {

	/** the checkbox that enables or diables the textfield. */
	private final JCheckBox checkBox;

	/** the textfield to enable or disable. */
	private final JTextField textField;

	/**
	 * Construct a new
	 * <code>ToggleTextFieldIfCheckBoxIsSelectedListener</code>.
	 * 
	 * @param checkBox
	 *            the checkbox
	 * @param textField
	 *            the textfield
	 */
	private ToggleTextFieldIfCheckBoxIsSelectedListener(
		final JCheckBox checkBox, final JTextField textField) {
	    this.checkBox = checkBox;
	    this.textField = textField;
	}

	@Override
	public void stateChanged(final ChangeEvent arg0) {
	    textField.setEnabled(!checkBox.isSelected());

	}
    }

    /**
     * Shows an exception message to the user with an modal dialog.
     * 
     * @param parent
     *            the parent of the modal dialog.
     * @param message
     *            the message to display.
     */
    private static void showUserTheError(final Component parent,
	    final String message) {

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		JOptionPane.showMessageDialog(parent, message, "Error",
			JOptionPane.ERROR_MESSAGE);
	    }
	});
    }

    /** the view of this presenter. */
    private final UrlyBirdView view;

    /** the service to read or update {@link RoomOffer}s. */
    private RoomOfferService service;

    /** the table model. */
    private RoomTableModel tableModel;

    /**
     * the provider to get a new service if the user specifies a different
     * connection.
     */
    private final RoomOfferServiceProvider serviceProvider;

    /**
     * Construct a new <code>UrlyBirdPresenter</code>.
     * 
     * @param view
     *            the view to control
     * @param service
     *            the service to read or update {@link RoomOffer}s.
     * @param serviceProvider
     *            the provider to get a new service if the user specifies a
     *            different connection.
     */
    public UrlyBirdPresenter(final UrlyBirdView view,
	    final RoomOfferService service,
	    final RoomOfferServiceProvider serviceProvider) {
	this.view = view;
	this.service = service;
	this.serviceProvider = serviceProvider;
	tableModel = new RoomTableModel();

    }

    /**
     * Starts and initializes the main gui.
     */
    public void startGui() {

	view.init();
	view.getMainFrame().setLocationRelativeTo(null);
	view.getMainFrame().addWindowListener(new ExitApplication());

	final JTable roomTable = view.getRoomTable();
	tableModel = RoomTableModel.initTable(roomTable);

	view.getChangeButton().setEnabled(false);
	view.getBookButton().setEnabled(false);
	view.getNewButton().setEnabled(false);
	view.getDeleteButton().setEnabled(false);

	addListeners();

	view.show();
    }

    /**
     * Adds all Listeners to the view.
     * 
     */
    private void addListeners() {
	view.getConnectionMenuItem().addActionListener(
		new ShowConnectionDialogListener());
	view.getFindButton().addActionListener(new StartFindDialogListener());
	view.getAllButton().addActionListener(new FindAllRoomsListener());
	view.getExitButton().addActionListener(new ExitApplication());
	view.getRoomTable()
		.getSelectionModel()
		.addListSelectionListener(new ToggleBookButtonIfRowIsSelected());

	view.getBookButton().addActionListener(
		new StartBookDialogListener(view.getRoomTable()));
    }
}
