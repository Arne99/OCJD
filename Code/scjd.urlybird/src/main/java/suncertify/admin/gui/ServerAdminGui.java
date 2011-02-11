package suncertify.admin.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ServerAdminGui extends JFrame {

    private static final long serialVersionUID = 4762410272117483890L;

    public ServerAdminGui(final String name) {
	super(name);
    }

    public static void start() {
	final ServerAdminGui serverAdminGui = new ServerAdminGui(
		"Server Administration");
	serverAdminGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	final Container contentPane = serverAdminGui.getContentPane();
	contentPane.setLayout(new GridBagLayout());
	final GridBagConstraints c = new GridBagConstraints();

	final JLabel dbLocationText = new JLabel("DB Location: ");
	c.gridx = 0;
	c.gridy = 0;
	contentPane.add(dbLocationText, c);

	final JTextField dbPathField = new JTextField(20);
	c.gridx = 1;
	c.gridy = 0;
	c.gridwidth = GridBagConstraints.RELATIVE;
	contentPane.add(dbPathField, c);

	final JButton dbPathButton = new JButton("Select");
	c.gridx = 20;
	c.gridy = 0;
	contentPane.add(dbPathButton, c);

	dbPathField.setEnabled(false);

	final JFileChooser dbPathChooser = new JFileChooser();
	dbPathButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final int returnVal = dbPathChooser.showDialog(contentPane,
			"Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    dbPathField.setText(dbPathChooser.getSelectedFile()
			    .getAbsolutePath());
		}

	    }
	});

	serverAdminGui.setLocationRelativeTo(null);
	serverAdminGui.setPreferredSize(new Dimension(500, 100));
	serverAdminGui.pack();
	serverAdminGui.setVisible(true);
    }

    public static void main(final String[] args) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		ServerAdminGui.start();
	    }
	});
    }
}
