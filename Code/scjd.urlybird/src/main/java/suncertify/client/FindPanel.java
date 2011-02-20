package suncertify.client;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FindPanel extends JPanel {

    private static final long serialVersionUID = -4765527763438947184L;

    private JButton findButton;
    private JButton discardButton;
    private JCheckBox anyHotelCheckBox;
    private JCheckBox anyLocationCheckBox;
    private JLabel hotelLabel;
    private JLabel locationLabel;
    private JLabel findRoomsLabel;
    private JRadioButton andRadioButton;
    private JRadioButton orRadioButton;
    private JTextField hotelTextField;
    private JTextField locationTextField;

    public FindPanel() {
	init();
    }

    public void init() {

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

	this.setLayout(new GridBagLayout());
	GridBagConstraints gridBagConstraints;

	findButton.setText("Find");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.ipadx = 23;
	gridBagConstraints.ipady = 15;
	gridBagConstraints.insets = new Insets(43, 10, 0, 0);
	this.add(findButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 5;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new Insets(42, 15, 36, 0);
	this.add(discardButton, gridBagConstraints);

	hotelLabel.setFont(new Font("Lucida Grande", 0, 24));
	hotelLabel.setText("Hotel");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.insets = new Insets(34, 29, 0, 0);
	this.add(hotelLabel, gridBagConstraints);

	locationLabel.setFont(new Font("Lucida Grande", 0, 24));
	locationLabel.setText("Location");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.insets = new Insets(47, 20, 0, 0);
	this.add(locationLabel, gridBagConstraints);

	andRadioButton.setFont(new Font("Lucida Grande", 0, 18));
	andRadioButton.setText("and");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 18;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new Insets(18, 66, 0, 0);
	this.add(andRadioButton, gridBagConstraints);

	orRadioButton.setFont(new Font("Lucida Grande", 0, 18));
	orRadioButton.setText("or");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 15;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.insets = new Insets(0, 66, 0, 0);
	this.add(orRadioButton, gridBagConstraints);

	hotelTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.insets = new Insets(31, 10, 0, 0);
	this.add(hotelTextField, gridBagConstraints);

	locationTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.insets = new Insets(42, 10, 0, 0);
	this.add(locationTextField, gridBagConstraints);

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
	this.add(anyHotelCheckBox, gridBagConstraints);

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
	this.add(anyLocationCheckBox, gridBagConstraints);

	findRoomsLabel.setFont(new Font("Lucida Grande", 0, 24));
	findRoomsLabel.setText("Find Rooms from:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 16;
	gridBagConstraints.ipadx = 190;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new Insets(12, 29, 0, 0);
	this.add(findRoomsLabel, gridBagConstraints);
    }

    public JButton getFindButton() {
	return findButton;
    }

    public JButton getDiscardButton() {
	return discardButton;
    }

    public JCheckBox getAnyHotelCheckBox() {
	return anyHotelCheckBox;
    }

    public JCheckBox getAnyLocationCheckBox() {
	return anyLocationCheckBox;
    }

    public JLabel getHotelLabel() {
	return hotelLabel;
    }

    public JLabel getLocationLabel() {
	return locationLabel;
    }

    public JLabel getFindRoomsLabel() {
	return findRoomsLabel;
    }

    public JRadioButton getAndRadioButton() {
	return andRadioButton;
    }

    public JRadioButton getOrRadioButton() {
	return orRadioButton;
    }

    public JTextField getHotelTextField() {
	return hotelTextField;
    }

    public JTextField getLocationTextField() {
	return locationTextField;
    }

}
