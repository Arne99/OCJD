package suncertify.admin.gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface ServerConsoleView {

    void init();

    void show();

    JFormattedTextField getDbPathTextField();

    JButton getDbPathButton();

    JFormattedTextField getHostConnectionTextField();

    JFormattedTextField getPortTextField();

    JButton getStartServerButton();

    JLabel getServerStatusLabel();

    JFileChooser getDbPathChooser();

    JFrame getMainFrame();

}