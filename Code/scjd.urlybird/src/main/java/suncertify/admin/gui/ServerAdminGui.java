package suncertify.admin.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ServerAdminGui implements ServerConsoleView {

    private static final long serialVersionUID = 4762410272117483890L;

    final JFrame mainFrame = new JFrame();
    final JPanel databasePanel = new JPanel();
    final JLabel databasePathLabel = new JLabel();
    final JFormattedTextField dbPathTextField = new JFormattedTextField();
    final JButton dbPathButton = new JButton();
    final JPanel connectionPanel = new JPanel();
    final JLabel hostLabel = new JLabel();
    final JFormattedTextField hostConnectionTextField = new JFormattedTextField();
    final JLabel portLabel = new JLabel();
    final JFormattedTextField portTextField = new JFormattedTextField();
    final JButton startServerButton = new JButton();
    final JLabel serverIsLabel = new JLabel();
    final JLabel serverStatusLabel = new JLabel();
    final JFileChooser dbPathChooser = new JFileChooser();

    @Override
    public void startGui() {

	mainFrame.setTitle("Server Administration");

	final Container contentPane = mainFrame.getContentPane();
	contentPane.setLayout(new GridBagLayout());
	GridBagConstraints gridBagConstraints;

	databasePanel.setBorder(BorderFactory.createTitledBorder("Database"));
	databasePanel.setLayout(new GridBagLayout());

	databasePathLabel.setText("Path");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.insets = new Insets(52, 26, 0, 0);
	databasePanel.add(databasePathLabel, gridBagConstraints);

	dbPathTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 250;
	gridBagConstraints.insets = new Insets(46, 42, 0, 26);
	databasePanel.add(dbPathTextField, gridBagConstraints);

	dbPathButton.setText("Select");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.insets = new Insets(7, 225, 6, 26);
	databasePanel.add(dbPathButton, gridBagConstraints);

	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 12;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(24, 20, 0, 20);
	contentPane.add(databasePanel, gridBagConstraints);

	connectionPanel.setBorder(BorderFactory
		.createTitledBorder("Connection"));
	connectionPanel.setMinimumSize(new Dimension(360, 150));
	connectionPanel.setLayout(new GridBagLayout());

	hostLabel.setText("Host");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.insets = new Insets(79, 32, 0, 0);
	connectionPanel.add(hostLabel, gridBagConstraints);

	hostConnectionTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(73, 10, 0, 53);
	connectionPanel.add(hostConnectionTextField, gridBagConstraints);

	portLabel.setText("Port");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 33;
	gridBagConstraints.insets = new Insets(14, 32, 0, 0);
	connectionPanel.add(portLabel, gridBagConstraints);

	portTextField.setText("");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.insets = new Insets(8, 10, 19, 53);
	connectionPanel.add(portTextField, gridBagConstraints);

	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 47;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(14, 20, 0, 20);
	contentPane.add(connectionPanel, gridBagConstraints);

	startServerButton.setText("Start Server");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 22;
	gridBagConstraints.ipady = 26;
	gridBagConstraints.insets = new Insets(29, 156, 17, 0);
	contentPane.add(startServerButton, gridBagConstraints);

	serverIsLabel.setFont(new Font(Font.SANS_SERIF, 0, 24));
	serverIsLabel.setText("Server is:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipady = 18;
	gridBagConstraints.insets = new Insets(52, 56, 0, 0);
	contentPane.add(serverIsLabel, gridBagConstraints);

	serverStatusLabel.setFont(new Font(Font.SANS_SERIF, 0, 24));
	serverStatusLabel.setText("Running");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 29;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new Insets(56, 33, 0, 0);
	contentPane.add(serverStatusLabel, gridBagConstraints);

	mainFrame.setPreferredSize(new Dimension(480, 580));
	mainFrame.setMinimumSize(new Dimension(480, 580));
	mainFrame.setLocationRelativeTo(null);
	mainFrame.setResizable(false);
	mainFrame.setVisible(true);

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		startGui();
	    }
	});
    }

    @Override
    public JFrame getMainFrame() {
	return mainFrame;
    }

    @Override
    public JFormattedTextField getDbPathTextField() {
	return dbPathTextField;
    }

    @Override
    public JButton getDbPathButton() {
	return dbPathButton;
    }

    @Override
    public JFormattedTextField getHostConnectionTextField() {
	return hostConnectionTextField;
    }

    @Override
    public JFormattedTextField getPortTextField() {
	return portTextField;
    }

    @Override
    public JButton getStartServerButton() {
	return startServerButton;
    }

    @Override
    public JLabel getServerStatusLabel() {
	return serverStatusLabel;
    }

    @Override
    public JFileChooser getDbPathChooser() {
	return dbPathChooser;
    }

    public static void main(final String[] args) {
	new ServerAdminGui().startGui();
    }
}
