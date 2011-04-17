package suncertify.client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UrlyBirdView {

    private JFrame mainFrame;
    private JButton exitButton;
    private JButton findButton;
    private JButton bookButton;
    private JButton newButton;
    private JButton deleteButton;
    private JButton changeButton;
    private JButton allButton;
    private JMenu urlyBirdMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenu viewMenu;
    private JMenuBar menuBar;
    private JMenuItem newMenuItem;
    private JMenuItem bookMenuItem;
    private JMenuItem deleteMenuItem;
    private JMenuItem changeMenuItem;
    private JMenuItem allMenuItem;
    private JMenuItem findMenuItem;
    private JMenuItem connectionMenuItem;
    private JMenuItem aboutMenuItem;
    private JScrollPane tableScrollPane;
    private JTable roomTable;

    public JFrame getMainFrame() {
	return mainFrame;
    }

    public JButton getExitButton() {
	return exitButton;
    }

    public JButton getFindButton() {
	return findButton;
    }

    public JButton getBookButton() {
	return bookButton;
    }

    public JButton getNewButton() {
	return newButton;
    }

    public JButton getDeleteButton() {
	return deleteButton;
    }

    public JButton getChangeButton() {
	return changeButton;
    }

    public JButton getAllButton() {
	return allButton;
    }

    public JMenu getUrlyBirdMenu() {
	return urlyBirdMenu;
    }

    public JMenu getEditMenu() {
	return editMenu;
    }

    public JMenu getHelpMenu() {
	return helpMenu;
    }

    public JMenu getViewMenu() {
	return viewMenu;
    }

    public JMenuBar getMenuBar() {
	return menuBar;
    }

    public JMenuItem getNewMenuItem() {
	return newMenuItem;
    }

    public JMenuItem getBookMenuItem() {
	return bookMenuItem;
    }

    public JMenuItem getDeleteMenuItem() {
	return deleteMenuItem;
    }

    public JMenuItem getChangeMenuItem() {
	return changeMenuItem;
    }

    public JMenuItem getAllMenuItem() {
	return allMenuItem;
    }

    public JMenuItem getFindMenuItem() {
	return findMenuItem;
    }

    public JMenuItem getConnectionMenuItem() {
	return connectionMenuItem;
    }

    public JMenuItem getAboutMenuItem() {
	return aboutMenuItem;
    }

    public JScrollPane getjScrollPane1() {
	return tableScrollPane;
    }

    public JTable getRoomTable() {
	return roomTable;
    }

    public void init() {
	mainFrame = new JFrame("UrlyBird");
	tableScrollPane = new JScrollPane();
	roomTable = new JTable();
	exitButton = new JButton();
	findButton = new JButton();
	bookButton = new JButton();
	newButton = new JButton();
	deleteButton = new JButton();
	changeButton = new JButton();
	allButton = new JButton();
	menuBar = new JMenuBar();
	urlyBirdMenu = new JMenu();
	connectionMenuItem = new JMenuItem();
	aboutMenuItem = new JMenuItem();
	viewMenu = new JMenu();
	allMenuItem = new JMenuItem();
	findMenuItem = new JMenuItem();
	editMenu = new JMenu();
	newMenuItem = new JMenuItem();
	bookMenuItem = new JMenuItem();
	deleteMenuItem = new JMenuItem();
	changeMenuItem = new JMenuItem();
	helpMenu = new JMenu();

	final Container contentPane = mainFrame.getContentPane();
	contentPane.setLayout(new GridBagLayout());

	tableScrollPane.setViewportView(roomTable);

	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.gridwidth = 4;
	gridBagConstraints.gridheight = 5;
	gridBagConstraints.fill = GridBagConstraints.BOTH;
	gridBagConstraints.ipadx = 441;
	gridBagConstraints.ipady = 414;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.weightx = 1.0;
	gridBagConstraints.weighty = 1.0;
	gridBagConstraints.insets = new Insets(18, 30, 30, 0);
	contentPane.add(tableScrollPane, gridBagConstraints);

	exitButton.setText("Exit");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.ipadx = 72;
	gridBagConstraints.ipady = 11;
	gridBagConstraints.anchor = GridBagConstraints.LAST_LINE_END;
	gridBagConstraints.insets = new Insets(141, 32, 30, 47);
	contentPane.add(exitButton, gridBagConstraints);

	findButton.setText("Find ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.ipadx = 32;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(85, 6, 0, 0);
	contentPane.add(findButton, gridBagConstraints);

	bookButton.setText("Book");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.ipadx = 25;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(29, 46, 0, 0);
	contentPane.add(bookButton, gridBagConstraints);

	newButton.setText("New");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.ipadx = 25;
	gridBagConstraints.ipady = 21;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(18, 46, 0, 0);
	contentPane.add(newButton, gridBagConstraints);

	deleteButton.setText("Delete");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.ipadx = 16;
	gridBagConstraints.ipady = 13;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(27, 46, 0, 0);
	contentPane.add(deleteButton, gridBagConstraints);

	changeButton.setText("Change");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.ipadx = 9;
	gridBagConstraints.ipady = 12;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(28, 46, 0, 0);
	contentPane.add(changeButton, gridBagConstraints);

	allButton.setText("All");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.ipadx = 42;
	gridBagConstraints.ipady = 14;
	gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
	gridBagConstraints.insets = new Insets(85, 30, 0, 0);
	contentPane.add(allButton, gridBagConstraints);

	urlyBirdMenu.setText("UrlyBird");

	connectionMenuItem.setText("Connection");
	urlyBirdMenu.add(connectionMenuItem);

	aboutMenuItem.setText("About");
	urlyBirdMenu.add(aboutMenuItem);

	menuBar.add(urlyBirdMenu);

	viewMenu.setText("View");

	allMenuItem.setText("All");
	viewMenu.add(allMenuItem);

	findMenuItem.setText("Find");
	viewMenu.add(findMenuItem);

	menuBar.add(viewMenu);

	editMenu.setText("Edit");

	newMenuItem.setText("New");
	editMenu.add(newMenuItem);

	bookMenuItem.setText("Book");
	editMenu.add(bookMenuItem);

	deleteMenuItem.setText("Delete");
	editMenu.add(deleteMenuItem);

	changeMenuItem.setText("Change");
	editMenu.add(changeMenuItem);

	menuBar.add(editMenu);

	helpMenu.setText("Help");
	menuBar.add(helpMenu);

	mainFrame.setJMenuBar(menuBar);
	mainFrame.pack();
	mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	exitButton.setMnemonic(KeyEvent.VK_E);
	findButton.setMnemonic(KeyEvent.VK_F);
	bookButton.setMnemonic(KeyEvent.VK_B);
	newButton.setMnemonic(KeyEvent.VK_N);
	deleteButton.setMnemonic(KeyEvent.VK_D);
	changeButton.setMnemonic(KeyEvent.VK_C);
	allButton.setMnemonic(KeyEvent.VK_A);
	urlyBirdMenu.setMnemonic(KeyEvent.VK_U);
	editMenu.setMnemonic(KeyEvent.VK_I);
	helpMenu.setMnemonic(KeyEvent.VK_H);
	viewMenu.setMnemonic(KeyEvent.VK_V);

    }

    public void show() {
	mainFrame.setVisible(true);
    }

}
