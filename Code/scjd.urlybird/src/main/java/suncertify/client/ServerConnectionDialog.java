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

public class ServerConnectionDialog extends JDialog {

    private static final long serialVersionUID = -6561677906315526844L;

    private JButton connectButton;
    private JButton discardButton;
    private JLabel connectLabel;
    private JLabel hostLabel;
    private JLabel portLabel;
    private JFormattedTextField hostTextField;
    private JFormattedTextField portTextField;

    private JPanel mainPanel;

    public ServerConnectionDialog(final JFrame parentFrame) {
	super(parentFrame, "Connection", true);
	init();
    }

    private void init() {
	GridBagConstraints gridBagConstraints;

	mainPanel = new JPanel();

	connectLabel = new JLabel();
	hostLabel = new JLabel();
	portLabel = new JLabel();
	connectButton = new JButton();
	discardButton = new JButton();
	hostTextField = new JFormattedTextField();
	portTextField = new JFormattedTextField();

	mainPanel.setLayout(new GridBagLayout());

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
	mainPanel.add(connectLabel, gridBagConstraints);

	hostLabel.setFont(new Font("Lucida Grande", 0, 14));
	hostLabel.setText("Host");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.insets = new Insets(49, 54, 0, 0);
	mainPanel.add(hostLabel, gridBagConstraints);

	portLabel.setFont(new Font("Lucida Grande", 0, 14));
	portLabel.setText("Port");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.insets = new Insets(34, 54, 0, 0);
	mainPanel.add(portLabel, gridBagConstraints);

	connectButton.setText("Connect");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(77, 77, 25, 0);
	mainPanel.add(connectButton, gridBagConstraints);

	discardButton.setText("Discard");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 3;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 16;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(78, 28, 25, 0);
	mainPanel.add(discardButton, gridBagConstraints);

	hostTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 7;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.insets = new Insets(44, 14, 0, 20);
	mainPanel.add(hostTextField, gridBagConstraints);

	portTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 7;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 245;
	gridBagConstraints.insets = new Insets(29, 11, 0, 20);
	mainPanel.add(portTextField, gridBagConstraints);

	this.add(mainPanel);
	this.pack();
    }

    public JButton getConnectButton() {
	return connectButton;
    }

    public JButton getDiscardButton() {
	return discardButton;
    }

    public JLabel getConnectLabel() {
	return connectLabel;
    }

    public JLabel getHostLabel() {
	return hostLabel;
    }

    public JLabel getPortLabel() {
	return portLabel;
    }

    public JFormattedTextField getHostTextField() {
	return hostTextField;
    }

    public JFormattedTextField getPortTextField() {
	return portTextField;
    }

}
