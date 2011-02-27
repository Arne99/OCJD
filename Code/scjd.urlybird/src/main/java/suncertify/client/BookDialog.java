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
import javax.swing.SwingUtilities;

public class BookDialog extends JDialog {

    private static final long serialVersionUID = 4243521563668095100L;

    private JButton bookButton;
    private JLabel bookRoomLabel;
    private JFormattedTextField customerIdTextField1;
    private JLabel customerLabel;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JButton discardButton;
    private JLabel hotelLabel;
    private JTextField hotelTextField;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JLabel sizeLabel;
    private JTextField sizeTextField;
    private JLabel smokingLabel;
    private JTextField smokingTextField;
    private JPanel mainPanel;

    public BookDialog(final JFrame parentFrame) {
	super(parentFrame, "Book Room", true);
	init();
    }

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
	customerIdTextField1 = new JFormattedTextField();
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

	bookRoomLabel.setFont(new Font("Lucida Grande", 0, 24)); // NOI18N
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

	customerLabel.setFont(new Font("Lucida Grande", 0, 24)); // NOI18N
	customerLabel.setText("for Customer");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 15;
	gridBagConstraints.gridwidth = 23;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(35, 33, 0, 0);
	mainPanel.add(customerLabel, gridBagConstraints);

	customerIdTextField1.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 34;
	gridBagConstraints.gridy = 15;
	gridBagConstraints.gridwidth = 36;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 124;
	gridBagConstraints.ipady = 4;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(38, 18, 0, 0);
	mainPanel.add(customerIdTextField1, gridBagConstraints);

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

    public JButton getBookButton() {
	return bookButton;
    }

    public JFormattedTextField getCustomerIdTextField1() {
	return customerIdTextField1;
    }

    public JTextField getDateTextField() {
	return dateTextField;
    }

    public JButton getDiscardButton() {
	return discardButton;
    }

    public JTextField getHotelTextField() {
	return hotelTextField;
    }

    public JTextField getIdTextField() {
	return idTextField;
    }

    public JTextField getLocationTextField() {
	return locationTextField;
    }

    public JTextField getPriceTextField() {
	return priceTextField;
    }

    public JTextField getSizeTextField() {
	return sizeTextField;
    }

    public JTextField getSmokingTextField() {
	return smokingTextField;
    }

    public JPanel getMainPanel() {
	return mainPanel;
    }

    public static void main(final String[] args) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		final BookDialog dialog = new BookDialog(null);
		dialog.setVisible(true);
	    }
	});
    }

}
