package suncertify.client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FindDialog {

    private JPanel panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;

    public void showModalDialog(final JFrame mainFrame) {

	final JDialog dialog = new JDialog(mainFrame, true);
	initPanel();
	dialog.add(panel);
	dialog.repaint();
	dialog.setVisible(true);
    }

    public void initPanel() {

	jButton1 = new javax.swing.JButton();
	jButton2 = new javax.swing.JButton();
	jLabel1 = new javax.swing.JLabel();
	jLabel2 = new javax.swing.JLabel();
	jRadioButton1 = new javax.swing.JRadioButton();
	jRadioButton2 = new javax.swing.JRadioButton();
	jTextField1 = new javax.swing.JTextField();
	jTextField2 = new javax.swing.JTextField();
	jCheckBox1 = new javax.swing.JCheckBox();
	jCheckBox2 = new javax.swing.JCheckBox();
	jLabel3 = new javax.swing.JLabel();

	panel.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints;

	jButton1.setText("Find");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.ipadx = 23;
	gridBagConstraints.ipady = 15;
	gridBagConstraints.insets = new java.awt.Insets(43, 10, 0, 0);
	panel.add(jButton1, gridBagConstraints);

	jButton2.setText("Discard");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 5;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 5;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new java.awt.Insets(42, 15, 36, 0);
	panel.add(jButton2, gridBagConstraints);

	jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
	jLabel1.setText("Hotel");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.insets = new java.awt.Insets(34, 29, 0, 0);
	panel.add(jLabel1, gridBagConstraints);

	jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
	jLabel2.setText("Location");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.insets = new java.awt.Insets(47, 20, 0, 0);
	panel.add(jLabel2, gridBagConstraints);

	jRadioButton1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
	jRadioButton1.setText("and");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.ipadx = 18;
	gridBagConstraints.ipady = 10;
	gridBagConstraints.insets = new java.awt.Insets(18, 66, 0, 0);
	panel.add(jRadioButton1, gridBagConstraints);

	jRadioButton2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
	jRadioButton2.setText("or");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 15;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.insets = new java.awt.Insets(0, 66, 0, 0);
	panel.add(jRadioButton2, gridBagConstraints);

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.insets = new java.awt.Insets(31, 10, 0, 0);
	panel.add(jTextField1, gridBagConstraints);

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 3;
	gridBagConstraints.ipadx = 157;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.insets = new java.awt.Insets(42, 10, 0, 0);
	panel.add(jTextField2, gridBagConstraints);

	jCheckBox1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
	jCheckBox1.setText("any");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 9;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.gridwidth = 6;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 44;
	gridBagConstraints.ipady = 9;
	gridBagConstraints.insets = new java.awt.Insets(31, 6, 0, 0);
	panel.add(jCheckBox1, gridBagConstraints);

	jCheckBox2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
	jCheckBox2.setText("any");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 9;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 6;
	gridBagConstraints.gridheight = 2;
	gridBagConstraints.ipadx = 44;
	gridBagConstraints.ipady = 11;
	gridBagConstraints.insets = new java.awt.Insets(42, 6, 0, 0);
	panel.add(jCheckBox2, gridBagConstraints);

	jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
	jLabel3.setText("Find Rooms from:");
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 16;
	gridBagConstraints.ipadx = 190;
	gridBagConstraints.ipady = 17;
	gridBagConstraints.insets = new java.awt.Insets(12, 29, 0, 0);
	panel.add(jLabel3, gridBagConstraints);
    }
}
