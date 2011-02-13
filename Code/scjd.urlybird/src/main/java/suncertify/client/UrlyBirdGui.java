package suncertify.client;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UrlyBirdGui {

    private JFrame mainFrame;
    private JPanel jPanel1;
    private JLabel jLabel1;
    private JTextField jTextField1;
    private JButton jButton2;
    private JPanel jPanel2;
    private JLabel jLabel2;
    private JTextField jTextField2;
    private JLabel jLabel3;
    private JTextField jTextField3;
    private JButton jButton1;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JMenuBar jMenuBar1;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;

    public void init() {
	mainFrame = new JFrame("UrlyBird");
	jPanel1 = new javax.swing.JPanel();
	jLabel1 = new javax.swing.JLabel();
	jTextField1 = new javax.swing.JTextField();
	jButton2 = new javax.swing.JButton();
	jPanel2 = new javax.swing.JPanel();
	jLabel2 = new javax.swing.JLabel();
	jTextField2 = new javax.swing.JTextField();
	jLabel3 = new javax.swing.JLabel();
	jTextField3 = new javax.swing.JTextField();
	jButton1 = new javax.swing.JButton();
	jLabel4 = new javax.swing.JLabel();
	jLabel5 = new javax.swing.JLabel();
	jMenuBar1 = new javax.swing.JMenuBar();
	jMenu1 = new javax.swing.JMenu();
	jMenu2 = new javax.swing.JMenu();
	jMenuItem1 = new javax.swing.JMenuItem();
	jMenuItem2 = new javax.swing.JMenuItem();

	java.awt.GridBagConstraints gridBagConstraints;
	final Container contentPane = mainFrame.getContentPane();
	contentPane.setLayout(new java.awt.GridBagLayout());

	jPanel1.setBorder(javax.swing.BorderFactory
		.createTitledBorder("Database"));
	jPanel1.setLayout(new java.awt.GridBagLayout());

	jLabel1.setText("Path");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.insets = new java.awt.Insets(52, 26, 0, 0);
	jPanel1.add(jLabel1, gridBagConstraints);

	jTextField1.setText("jTextField1");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 250;
	gridBagConstraints.insets = new java.awt.Insets(46, 42, 0, 26);
	jPanel1.add(jTextField1, gridBagConstraints);

	jButton2.setText("Select");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.insets = new java.awt.Insets(7, 225, 6, 26);
	jPanel1.add(jButton2, gridBagConstraints);

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 12;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(24, 20, 0, 20);
	contentPane.add(jPanel1, gridBagConstraints);

	jPanel2.setBorder(javax.swing.BorderFactory
		.createTitledBorder("Connection"));
	jPanel2.setMinimumSize(new java.awt.Dimension(360, 150));
	jPanel2.setLayout(new java.awt.GridBagLayout());

	jLabel2.setText("Host");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.insets = new java.awt.Insets(79, 32, 0, 0);
	jPanel2.add(jLabel2, gridBagConstraints);

	jTextField2.setText("jTextField2");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(73, 10, 0, 53);
	jPanel2.add(jTextField2, gridBagConstraints);

	jLabel3.setText("Port");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 33;
	gridBagConstraints.insets = new java.awt.Insets(14, 32, 0, 0);
	jPanel2.add(jLabel3, gridBagConstraints);

	jTextField3.setText("jTextField3");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 242;
	gridBagConstraints.insets = new java.awt.Insets(8, 10, 19, 53);
	jPanel2.add(jTextField3, gridBagConstraints);

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.ipadx = 47;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new java.awt.Insets(14, 20, 0, 20);
	contentPane.add(jPanel2, gridBagConstraints);

	jButton1.setText("Start Server");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 22;
	gridBagConstraints.ipady = 26;
	gridBagConstraints.insets = new java.awt.Insets(29, 156, 17, 0);
	contentPane.add(jButton1, gridBagConstraints);

	jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 24));
	jLabel4.setText("Server is:");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipady = 18;
	gridBagConstraints.insets = new java.awt.Insets(52, 56, 0, 0);
	contentPane.add(jLabel4, gridBagConstraints);

	jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 24));
	jLabel5.setText("Running");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 29;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new java.awt.Insets(56, 33, 0, 0);
	contentPane.add(jLabel5, gridBagConstraints);

	jMenu1.setText("Exit");
	jMenuBar1.add(jMenu1);

	jMenu2.setText("Preferences");

	jMenuItem2.setText("Load");
	jMenu2.add(jMenuItem2);

	jMenuItem1.setText("Save");
	jMenu2.add(jMenuItem1);

	jMenuBar1.add(jMenu2);

	mainFrame.setJMenuBar(jMenuBar1);
    }

    public void show() {
	mainFrame.setVisible(true);
    }

}
