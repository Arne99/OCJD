package suncertify.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A <code>ServerConnectionPanel</code> provides the user with the needed gui to
 * specify the required parameters for an server connection and a button to
 * establish this connection.
 * 
 * @author arnelandwehr
 * 
 */
public final class ServerConnectionPanel extends JPanel {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -6561677906315526844L;

    /** the button to connect to to the server. */
    private JButton connectButton;

    /** the button to discard the dialog. */
    private JButton discardButton;

    /** the label "connect". */
    private JLabel connectLabel;

    /** the label "host". */
    private JLabel hostLabel;

    /** the label "port". */
    private JLabel portLabel;

    /** the host address text field. */
    private JTextField hostTextField;

    /** the port number text field. */
    private JTextField portTextField;

    /**
     * Constructs a new <code>ServerConnectionPanel</code>.
     */
    public ServerConnectionPanel() {
	init();
    }

    /**
     * Initializes the panel.
     */
    private void init() {
	GridBagConstraints gridBagConstraints;

	connectLabel = new JLabel();
	hostLabel = new JLabel();
	portLabel = new JLabel();
	connectButton = new JButton();
	discardButton = new JButton();
	hostTextField = new JFormattedTextField();
	portTextField = new JFormattedTextField();

	this.setLayout(new GridBagLayout());

	connectLabel.setFont(new Font("Lucida Grande", 0, 24));
	connectLabel.setText("Connect to");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.ipadx = 132;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(33, 54, 0, 0);
	this.add(connectLabel, gridBagConstraints);

	hostLabel.setFont(new Font("Lucida Grande", 0, 14));
	hostLabel.setText("Host");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.insets = new Insets(49, 54, 0, 0);
	this.add(hostLabel, gridBagConstraints);

	portLabel.setFont(new Font("Lucida Grande", 0, 14));
	portLabel.setText("Port");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.insets = new Insets(34, 54, 0, 0);
	this.add(portLabel, gridBagConstraints);

	connectButton.setText("Connect");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(77, 77, 25, 0);
	this.add(connectButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 3;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 16;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(78, 28, 25, 0);
	this.add(discardButton, gridBagConstraints);

	hostTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 7;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.insets = new Insets(44, 14, 0, 20);
	this.add(hostTextField, gridBagConstraints);

	portTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 7;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 245;
	gridBagConstraints.insets = new Insets(29, 11, 0, 20);
	this.add(portTextField, gridBagConstraints);

	setPreferredSize(new Dimension(400, 400));

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
     * @return the discard button
     */
    public JButton getDiscardButton() {
	return discardButton;
    }

    /**
     * Getter for the host text field.
     * 
     * @return the host text field.
     */
    public JTextField getHostTextField() {
	return hostTextField;
    }

    /**
     * The port text field.
     * 
     * @return the port text field.
     */
    public JTextField getPortTextField() {
	return portTextField;
    }

}
