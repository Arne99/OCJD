package suncertify.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A <code>DatabaseConnectionPanel</code> provides the user with the needed gui
 * to specify the required parameters for an database connection and a button to
 * establish this connection.
 * 
 * @author arnelandwehr
 * 
 */
public final class DatabaseConnectionPanel extends JPanel {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 558949008623701892L;

    /** the button to select an database path with an file chooser. */
    private JButton selectButton;

    /** the button to connect to an database. */
    private JButton connectButton;

    /** the button to discard the dialog. */
    private JButton discardButton;

    /** the label "database". */
    private JLabel databaseLabel;

    /** the label "database path". */
    private JLabel pathLabel;

    /** the text filed to which stores the database path. */
    private JTextField databaseTextField;

    /**
     * Constructs a new <code>DatabaseConnectionPanel</code> an initalizes it.
     * 
     */
    public DatabaseConnectionPanel() {
	init();
    }

    /**
     * Initializes a <code>DatabaseConnectionPanel</code>.
     */
    public void init() {

	GridBagConstraints gridBagConstraints;

	databaseLabel = new JLabel();
	databaseTextField = new JTextField();
	selectButton = new JButton();
	pathLabel = new JLabel();
	connectButton = new JButton();
	discardButton = new JButton();

	setLayout(new GridBagLayout());

	databaseLabel.setFont(new Font("Lucida Grande", 0, 24));
	databaseLabel.setText("Database");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.ipadx = 154;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new Insets(39, 57, 0, 0);
	add(databaseLabel, gridBagConstraints);

	databaseTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 7;
	gridBagConstraints.ipadx = 273;
	gridBagConstraints.insets = new Insets(6, 57, 0, 34);
	add(databaseTextField, gridBagConstraints);

	selectButton.setText("Select");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.insets = new Insets(9, 89, 0, 34);
	add(selectButton, gridBagConstraints);

	pathLabel.setText("Database Path");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.insets = new Insets(73, 57, 0, 0);
	add(pathLabel, gridBagConstraints);

	connectButton.setText("Connect");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.insets = new Insets(71, 69, 0, 0);
	add(connectButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 12;
	gridBagConstraints.ipady = 15;
	gridBagConstraints.insets = new Insets(70, 31, 38, 0);
	add(discardButton, gridBagConstraints);

	setPreferredSize(new Dimension(400, 400));

	connectButton.setMnemonic(KeyEvent.VK_C);
	discardButton.setMnemonic(KeyEvent.VK_D);
	selectButton.setMnemonic(KeyEvent.VK_S);

	databaseTextField.setToolTipText("The Path to the DataFile");
    }

    /**
     * Getter for the select button.
     * 
     * @return the select button.
     */
    public JButton getSelectButton() {
	return selectButton;
    }

    /**
     * Getter for the connect button.
     * 
     * @return the connect button.
     */
    public JButton getConnectButton() {
	return connectButton;
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
     * Getter for the database text field.
     * 
     * @return the database text field.
     */
    public JTextField getDatabaseTextField() {
	return databaseTextField;
    }

}
