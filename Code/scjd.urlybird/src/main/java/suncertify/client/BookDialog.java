package suncertify.client;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import suncertify.common.RoomOffer;

/**
 * The <code>BookDialog</code> provides a view for the user to book a
 * {@link RoomOffer}.
 * 
 * @author arnelandwehr
 * 
 */
public final class BookDialog extends JDialog {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 4243521563668095100L;

    /** the button to book a room. */
    private JButton bookButton;

    /** the label "book". */
    private JLabel bookRoomLabel;

    /** the customer id test field . */
    private JFormattedTextField customerIdTextField;

    /** the label "customer". */
    private JLabel customerLabel;

    /** the label "date". */
    private JLabel dateLabel;

    /** the date text field. */
    private JTextField dateTextField;

    /** the button to discard the dialog . */
    private JButton discardButton;

    /** the label "hotel". */
    private JLabel hotelLabel;

    /** the hotel name text field. */
    private JTextField hotelTextField;

    /** the label "id". */
    private JLabel idLabel;

    /** the text field for the id. */
    private JTextField idTextField;

    /** the label "location". */
    private JLabel locationLabel;

    /** the text field for the location. */
    private JTextField locationTextField;

    /** the label "price". */
    private JLabel priceLabel;

    /** the text field for the price. */
    private JTextField priceTextField;

    /** the label "size". */
    private JLabel sizeLabel;

    /** the text field for the size. */
    private JTextField sizeTextField;

    /** the label "smoking". */
    private JLabel smokingLabel;

    /** the smoking text field. */
    private JTextField smokingTextField;

    /** the main panel. */
    private JPanel mainPanel;

    /**
     * Creates a new modal <code>BookDialog</code>.
     * 
     * @param parentFrame
     *            the parent frame.
     */
    public BookDialog(final JFrame parentFrame) {
	super(parentFrame, "Book Room", true);
	init();
    }

    /**
     * Initializes the <code>BookDialog</code>.
     */
    private void init() {
	GridBagConstraints gridBagConstraints;

	mainPanel = new JPanel();
	bookButton = new JButton();
	discardButton = new JButton();
	bookRoomLabel = new JLabel();
	idLabel = new JLabel();
	hotelLabel = new JLabel();
	locationLabel = new JLabel();
	sizeLabel = new JLabel();
	smokingLabel = new JLabel();
	priceLabel = new JLabel();
	dateLabel = new JLabel();
	customerLabel = new JLabel();
	customerIdTextField = new JFormattedTextField();
	idTextField = new JTextField();
	locationTextField = new JTextField();
	sizeTextField = new JTextField();
	priceTextField = new JTextField();
	smokingTextField = new JTextField();
	dateTextField = new JTextField();
	hotelTextField = new JTextField();

	mainPanel.setLayout(new GridBagLayout());

	bookButton.setText("Book");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 5;
	gridBagConstraints.gridy = 17;
	gridBagConstraints.gridwidth = 12;
	gridBagConstraints.ipadx = 33;
	gridBagConstraints.ipady = 16;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(35, 6, 28, 0);
	mainPanel.add(bookButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 34;
	gridBagConstraints.gridy = 17;
	gridBagConstraints.gridwidth = 39;
	gridBagConstraints.ipadx = 23;
	gridBagConstraints.ipady = 16;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(35, 32, 28, 0);
	mainPanel.add(discardButton, gridBagConstraints);

	bookRoomLabel.setFont(new Font("Lucida Grande", 0, 24));
	bookRoomLabel.setText("Book Room with ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 35;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(20, 36, 0, 0);
	mainPanel.add(bookRoomLabel, gridBagConstraints);

	idLabel.setText("ID");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(idLabel, gridBagConstraints);

	hotelLabel.setText("Hotel");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(hotelLabel, gridBagConstraints);

	locationLabel.setText("Location");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 6;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(locationLabel, gridBagConstraints);

	sizeLabel.setText("Size");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 7;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(sizeLabel, gridBagConstraints);

	smokingLabel.setText("Smoking");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 11;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(smokingLabel, gridBagConstraints);

	priceLabel.setText("Price");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 11;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 36, 0, 0);
	mainPanel.add(priceLabel, gridBagConstraints);

	dateLabel.setText("Date");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 13;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(28, 36, 0, 0);
	mainPanel.add(dateLabel, gridBagConstraints);

	customerLabel.setFont(new Font("Lucida Grande", 0, 24));
	customerLabel.setText("for Customer");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 15;
	gridBagConstraints.gridwidth = 23;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(35, 33, 0, 0);
	mainPanel.add(customerLabel, gridBagConstraints);

	customerIdTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 34;
	gridBagConstraints.gridy = 15;
	gridBagConstraints.gridwidth = 36;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 124;
	gridBagConstraints.ipady = 4;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(38, 18, 0, 0);
	mainPanel.add(customerIdTextField, gridBagConstraints);

	idTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 225;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 17, 0, 37);
	mainPanel.add(idTextField, gridBagConstraints);

	locationTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 225;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 17, 0, 37);
	mainPanel.add(locationTextField, gridBagConstraints);

	sizeTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 7;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 224;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 18, 0, 37);
	mainPanel.add(sizeTextField, gridBagConstraints);

	priceTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 11;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 224;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 18, 0, 37);
	mainPanel.add(priceTextField, gridBagConstraints);

	smokingTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 224;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 18, 0, 37);
	mainPanel.add(smokingTextField, gridBagConstraints);

	dateTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 13;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 224;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(22, 18, 0, 37);
	mainPanel.add(dateTextField, gridBagConstraints);

	hotelTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 16;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 96;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 225;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 17, 0, 37);
	mainPanel.add(hotelTextField, gridBagConstraints);

	add(mainPanel);
	pack();
    }

    /**
     * Getter for the book button.
     * 
     * @return the book button.
     */
    public JButton getBookButton() {
	return bookButton;
    }

    /**
     * Getter for the customer id text field.
     * 
     * @return the customer id text field.
     */
    public JTextField getCustomerIdTextField() {
	return customerIdTextField;
    }

    /**
     * Getter for the date text field.
     * 
     * @return the date text field.
     */
    public JTextField getDateTextField() {
	return dateTextField;
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
     * Getter for the hotel text field.
     * 
     * @return the hotel text field.
     */
    public JTextField getHotelTextField() {
	return hotelTextField;
    }

    /**
     * Getter for the customer id text field.
     * 
     * @return the customer id text field.
     */
    public JTextField getIdTextField() {
	return idTextField;
    }

    /**
     * Getter for the location text field.
     * 
     * @return the location text field.
     */
    public JTextField getLocationTextField() {
	return locationTextField;
    }

    /**
     * Getter for the price text field.
     * 
     * @return the price text field.
     */
    public JTextField getPriceTextField() {
	return priceTextField;
    }

    /**
     * Getter for the size text field.
     * 
     * @return the size text field.
     */
    public JTextField getSizeTextField() {
	return sizeTextField;
    }

    /**
     * Getter for the smoking text field.
     * 
     * @return the smoking text field.
     */
    public JTextField getSmokingTextField() {
	return smokingTextField;
    }

    /**
     * Getter for the main panel
     * 
     * @return the main panel.
     */
    public JPanel getMainPanel() {
	return mainPanel;
    }

}
