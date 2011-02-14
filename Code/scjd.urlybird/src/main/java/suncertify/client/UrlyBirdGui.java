package suncertify.client;

import java.awt.Container;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class UrlyBirdGui {

    private JFrame mainFrame;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton findButton;
    private javax.swing.JButton bookButton;
    private javax.swing.JButton newButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton changeButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton allButton;
    private javax.swing.JMenu urlyBirdMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable roomTable;

    public void init() {
	mainFrame = new JFrame("UrlyBird");
	jScrollPane1 = new javax.swing.JScrollPane();
	roomTable = new javax.swing.JTable();
	exitButton = new javax.swing.JButton();
	findButton = new javax.swing.JButton();
	bookButton = new javax.swing.JButton();
	newButton = new javax.swing.JButton();
	deleteButton = new javax.swing.JButton();
	changeButton = new javax.swing.JButton();
	refreshButton = new javax.swing.JButton();
	allButton = new javax.swing.JButton();
	jMenuBar1 = new javax.swing.JMenuBar();
	urlyBirdMenu = new javax.swing.JMenu();
	jMenuItem7 = new javax.swing.JMenuItem();
	jMenuItem9 = new javax.swing.JMenuItem();
	jMenuItem8 = new javax.swing.JMenuItem();
	jMenu4 = new javax.swing.JMenu();
	jMenuItem5 = new javax.swing.JMenuItem();
	jMenuItem6 = new javax.swing.JMenuItem();
	jMenu2 = new javax.swing.JMenu();
	jMenuItem1 = new javax.swing.JMenuItem();
	jMenuItem2 = new javax.swing.JMenuItem();
	jMenuItem3 = new javax.swing.JMenuItem();
	jMenuItem4 = new javax.swing.JMenuItem();
	jMenu3 = new javax.swing.JMenu();

	final Container contentPane = mainFrame.getContentPane();
	contentPane.setLayout(new java.awt.GridBagLayout());

	roomTable.setModel(new javax.swing.table.DefaultTableModel(
		new Object[][] { { null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null } },
		new String[] { "Title 1", "Title 2", "Title 3", "Title 4",
			"Title 5", "Title 6", "Title 7" }));
	jScrollPane1.setViewportView(roomTable);

	GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 5;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.ipadx = 441;
	gridBagConstraints.ipady = 414;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.weightx = 1.0;
	gridBagConstraints.weighty = 1.0;
	gridBagConstraints.insets = new java.awt.Insets(18, 30, 30, 0);
	contentPane.add(jScrollPane1, gridBagConstraints);

	exitButton.setText("Exit");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 72;
	gridBagConstraints.ipady = 11;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
	gridBagConstraints.insets = new java.awt.Insets(141, 32, 30, 47);
	contentPane.add(exitButton, gridBagConstraints);

	findButton.setText("Find ");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.ipadx = 32;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(85, 6, 0, 0);
	contentPane.add(findButton, gridBagConstraints);

	bookButton.setText("Book");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.ipadx = 25;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(29, 46, 0, 0);
	contentPane.add(bookButton, gridBagConstraints);

	newButton.setText("New");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.ipadx = 25;
	gridBagConstraints.ipady = 21;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(18, 46, 0, 0);
	contentPane.add(newButton, gridBagConstraints);

	deleteButton.setText("Delete");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.ipadx = 16;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(27, 46, 0, 0);
	contentPane.add(deleteButton, gridBagConstraints);

	changeButton.setText("Change");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(28, 46, 0, 0);
	contentPane.add(changeButton, gridBagConstraints);

	refreshButton.setText("Refresh");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = -18;
	gridBagConstraints.ipady = 16;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
	gridBagConstraints.insets = new java.awt.Insets(90, 700, 0, 0);
	contentPane.add(refreshButton, gridBagConstraints);

	allButton.setText("All");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.ipadx = 42;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(85, 30, 0, 0);
	contentPane.add(allButton, gridBagConstraints);

	urlyBirdMenu.setText("UrlyBird");

	jMenuItem7.setText("Connection");
	urlyBirdMenu.add(jMenuItem7);

	jMenuItem9.setText("Database");
	urlyBirdMenu.add(jMenuItem9);

	jMenuItem8.setText("About");
	urlyBirdMenu.add(jMenuItem8);

	jMenuBar1.add(urlyBirdMenu);

	jMenu4.setText("View");

	jMenuItem5.setText("All");
	jMenu4.add(jMenuItem5);

	jMenuItem6.setText("Find");
	jMenu4.add(jMenuItem6);

	jMenuBar1.add(jMenu4);

	jMenu2.setText("Edit");

	jMenuItem1.setText("New");
	jMenu2.add(jMenuItem1);

	jMenuItem2.setText("Book");
	jMenu2.add(jMenuItem2);

	jMenuItem3.setText("Delete");
	jMenu2.add(jMenuItem3);

	jMenuItem4.setText("Change");
	jMenu2.add(jMenuItem4);

	jMenuBar1.add(jMenu2);

	jMenu3.setText("Help");
	jMenuBar1.add(jMenu3);

	mainFrame.setJMenuBar(jMenuBar1);
	mainFrame.pack();
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void show() {
	mainFrame.setVisible(true);
    }

    public static void main(final String[] args) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		final UrlyBirdGui urlyBirdGui = new UrlyBirdGui();
		urlyBirdGui.init();
		urlyBirdGui.show();
	    }
	});
    }
}
