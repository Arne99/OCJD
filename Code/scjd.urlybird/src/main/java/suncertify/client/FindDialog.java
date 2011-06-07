package suncertify.client;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import suncertify.common.RoomOffer;

/**
 * The <code>FindDialog</code> provides a view for the user to find
 * {@link RoomOffer} that matches a combination of hotel and/or city name.
 * 
 * @author arnelandwehr
 * 
 */
public final class FindDialog extends JDialog {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -4765527763438947184L;

    /** the button to start the search. */
    private JButton findButton;

    /** the button to clos the dialog. */
    private JButton discardButton;

    /** specifies if any hotel name is searched. */
    private JCheckBox anyHotelCheckBox;

    /** specifies if any ciyt is searched. */
    private JCheckBox anyLocationCheckBox;

    /** the label \"hotel\". */
    private JLabel hotelLabel;

    /** the label \"location\". */
    private JLabel locationLabel;

    /** the label \"Find\". */
    private JLabel findRoomsLabel;

    /** specifies the "and" option in the search. */
    private JRadioButton andRadioButton;

    /** specifies the "on" option in the search. */
    private JRadioButton orRadioButton;

    /** the field with the hotel name. */
    private JTextField hotelTextField;

    /** the field with the location name. */
    private JTextField locationTextField;

    /** button group for \"and\" and "\or\" button . */
    private ButtonGroup buttonGroup;

    /** the main panel of this dialog. */
    private JPanel mainPanel;

    /**
     * Constructs and shows a new modal <code>FindDialog</code>.
     * 
     * @param parentFrame
     *            the parent frame, must not be <code>null</code>.
     */
    public FindDialog(final JFrame parentFrame) {
	super(parentFrame, "Find Rooms", true);
	init();
    }

    /**
     * Initializes the view and shows it.
     */
    public void init() {

	mainPanel = new JPanel();
	findButton = new JButton();
	discardButton = new JButton();
	hotelLabel = new JLabel();
	locationLabel = new JLabel();
	andRadioButton = new JRadioButton();
	orRadioButton = new JRadioButton();
	hotelTextField = new JTextField();
	locationTextField = new JTextField();
	anyHotelCheckBox = new JCheckBox();
	anyLocationCheckBox = new JCheckBox();
	findRoomsLabel = new JLabel();
	buttonGroup = new ButtonGroup();

	mainPanel.setLayout(new GridBagLayout());
	GridBagConstraints gridBagConstraints;

	findButton.setText("Find");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.ipadx = 23;
	gridBagConstraints.ipady = 15;
	gridBagConstraints.insets = new Insets(43, 10, 0, 0);
	mainPanel.add(findButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 5;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new Insets(42, 15, 36, 0);
	mainPanel.add(discardButton, gridBagConstraints);

	hotelLabel.setFont(new Font("Lucida Grande", 0, 24));
	hotelLabel.setText("Hotel");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.insets = new Insets(34, 29, 0, 0);
	mainPanel.add(hotelLabel, gridBagConstraints);

	locationLabel.setFont(new Font("Lucida Grande", 0, 24));
	locationLabel.setText("Location");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.insets = new Insets(47, 20, 0, 0);
	mainPanel.add(locationLabel, gridBagConstraints);

	andRadioButton.setFont(new Font("Lucida Grande", 0, 18));
	andRadioButton.setText("and");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 18;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new Insets(18, 66, 0, 0);
	mainPanel.add(andRadioButton, gridBagConstraints);

	orRadioButton.setFont(new Font("Lucida Grande", 0, 18));
	orRadioButton.setText("or");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 15;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.insets = new Insets(0, 66, 0, 0);
	mainPanel.add(orRadioButton, gridBagConstraints);

	hotelTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.insets = new Insets(31, 10, 0, 0);
	mainPanel.add(hotelTextField, gridBagConstraints);

	locationTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.insets = new Insets(42, 10, 0, 0);
	mainPanel.add(locationTextField, gridBagConstraints);

	anyHotelCheckBox.setFont(new Font("Lucida Grande", 0, 18));
	anyHotelCheckBox.setText("any");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 9;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 6;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 44;
	gridBagConstraints.ipady = 9;
	gridBagConstraints.insets = new Insets(31, 6, 0, 0);
	mainPanel.add(anyHotelCheckBox, gridBagConstraints);

	anyLocationCheckBox.setFont(new Font("Lucida Grande", 0, 18));
	anyLocationCheckBox.setText("any");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 9;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 6;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 44;
	gridBagConstraints.ipady = 11;
	gridBagConstraints.insets = new Insets(42, 6, 0, 0);
	mainPanel.add(anyLocationCheckBox, gridBagConstraints);

	findRoomsLabel.setFont(new Font("Lucida Grande", 0, 24));
	findRoomsLabel.setText("Find Rooms from:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 16;
	gridBagConstraints.ipadx = 190;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new Insets(12, 29, 0, 0);
	mainPanel.add(findRoomsLabel, gridBagConstraints);

	buttonGroup.add(andRadioButton);
	buttonGroup.add(orRadioButton);

	andRadioButton.setSelected(true);

	this.add(mainPanel);
	this.pack();

	findButton.setMnemonic(KeyEvent.VK_F);
	discardButton.setMnemonic(KeyEvent.VK_D);
    }

    /**
     * Getter for the find button.
     * 
     * @return the find button.
     */
    public JButton getFindButton() {
	return findButton;
    }

    /**
     * Getter for the discard button.
     * 
     * @return the discard button.
     */
    public JButton getDiscardButton() {
	return discardButton;
    }

    /**
     * Getter for the any hotel check box.
     * 
     * @return the any hotel check box.
     */
    public JCheckBox getAnyHotelCheckBox() {
	return anyHotelCheckBox;
    }

    /**
     * Getter for the any location check box.
     * 
     * @return the any location text check box.
     */
    public JCheckBox getAnyLocationCheckBox() {
	return anyLocationCheckBox;
    }

    /**
     * Getter for the \"and\" radio button.
     * 
     * @return the \"and\" radio button.
     */
    public JRadioButton getAndRadioButton() {
	return andRadioButton;
    }

    /**
     * Getter for the \"or\" radio button.
     * 
     * @return the \"or\" radio button.
     */
    public JRadioButton getOrRadioButton() {
	return orRadioButton;
    }

    /**
     * Getter for the hotel text field.
     * 
     * @return the hotel text field.
     */
    public JTextField getHotelTextField() {
	return hotelTextField;
    }

    /**
     * Getter for the location text field.
     * 
     * @return the location text field.
     */
    public JTextField getLocationTextField() {
	return locationTextField;
    }

}
