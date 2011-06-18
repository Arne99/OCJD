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
import javax.swing.WindowConstants;

import suncertify.common.RoomOffer;

/**
 * The main UrlyBird view that show the stored {@link RoomOffer}s in a table to
 * the user and allows him to book selected rooms or search for specified ones.
 * 
 * @author arnelandwehr
 * 
 */
public final class UrlyBirdView {

    /** The main frame. */
    private JFrame mainFrame;

    /** The exit button. */
    private JButton exitButton;

    /** The find button. */
    private JButton findButton;

    /** The book button. */
    private JButton bookButton;

    /** The new button. */
    private JButton newButton;

    /** The delete button. */
    private JButton deleteButton;

    /** The change button. */
    private JButton changeButton;

    /** The all button. */
    private JButton allButton;

    /** The urly bird menu. */
    private JMenu urlyBirdMenu;

    /** The edit menu. */
    private JMenu editMenu;

    /** The help menu. */
    private JMenu helpMenu;

    /** The view menu. */
    private JMenu viewMenu;

    /** The menu bar. */
    private JMenuBar menuBar;

    /** The new menu item. */
    private JMenuItem newMenuItem;

    /** The book menu item. */
    private JMenuItem bookMenuItem;

    /** The delete menu item. */
    private JMenuItem deleteMenuItem;

    /** The change menu item. */
    private JMenuItem changeMenuItem;

    /** The all menu item. */
    private JMenuItem allMenuItem;

    /** The find menu item. */
    private JMenuItem findMenuItem;

    /** The connection menu item. */
    private JMenuItem connectionMenuItem;

    /** The about menu item. */
    private JMenuItem aboutMenuItem;

    /** The table scroll pane. */
    private JScrollPane tableScrollPane;

    /** The room table. */
    private JTable roomTable;

    /**
     * Gets the main frame.
     * 
     * @return the main frame
     */
    public JFrame getMainFrame() {
	return mainFrame;
    }

    /**
     * Gets the exit button.
     * 
     * @return the exit button
     */
    public JButton getExitButton() {
	return exitButton;
    }

    /**
     * Gets the find button.
     * 
     * @return the find button
     */
    public JButton getFindButton() {
	return findButton;
    }

    /**
     * Gets the book button.
     * 
     * @return the book button
     */
    public JButton getBookButton() {
	return bookButton;
    }

    /**
     * Gets the new button.
     * 
     * @return the new button
     */
    public JButton getNewButton() {
	return newButton;
    }

    /**
     * Gets the delete button.
     * 
     * @return the delete button
     */
    public JButton getDeleteButton() {
	return deleteButton;
    }

    /**
     * Gets the change button.
     * 
     * @return the change button
     */
    public JButton getChangeButton() {
	return changeButton;
    }

    /**
     * Gets the all button.
     * 
     * @return the all button
     */
    public JButton getAllButton() {
	return allButton;
    }

    /**
     * Gets the urly bird menu.
     * 
     * @return the urly bird menu
     */
    public JMenu getUrlyBirdMenu() {
	return urlyBirdMenu;
    }

    /**
     * Gets the edits the menu.
     * 
     * @return the edits the menu
     */
    public JMenu getEditMenu() {
	return editMenu;
    }

    /**
     * Gets the help menu.
     * 
     * @return the help menu
     */
    public JMenu getHelpMenu() {
	return helpMenu;
    }

    /**
     * Gets the view menu.
     * 
     * @return the view menu
     */
    public JMenu getViewMenu() {
	return viewMenu;
    }

    /**
     * Gets the menu bar.
     * 
     * @return the menu bar
     */
    public JMenuBar getMenuBar() {
	return menuBar;
    }

    /**
     * Gets the new menu item.
     * 
     * @return the new menu item
     */
    public JMenuItem getNewMenuItem() {
	return newMenuItem;
    }

    /**
     * Gets the book menu item.
     * 
     * @return the book menu item
     */
    public JMenuItem getBookMenuItem() {
	return bookMenuItem;
    }

    /**
     * Gets the delete menu item.
     * 
     * @return the delete menu item
     */
    public JMenuItem getDeleteMenuItem() {
	return deleteMenuItem;
    }

    /**
     * Gets the change menu item.
     * 
     * @return the change menu item
     */
    public JMenuItem getChangeMenuItem() {
	return changeMenuItem;
    }

    /**
     * Gets the all menu item.
     * 
     * @return the all menu item
     */
    public JMenuItem getAllMenuItem() {
	return allMenuItem;
    }

    /**
     * Gets the find menu item.
     * 
     * @return the find menu item
     */
    public JMenuItem getFindMenuItem() {
	return findMenuItem;
    }

    /**
     * Gets the connection menu item.
     * 
     * @return the connection menu item
     */
    public JMenuItem getConnectionMenuItem() {
	return connectionMenuItem;
    }

    /**
     * Gets the about menu item.
     * 
     * @return the about menu item
     */
    public JMenuItem getAboutMenuItem() {
	return aboutMenuItem;
    }

    /**
     * Gets the j scroll pane1.
     * 
     * @return the j scroll pane1
     */
    public JScrollPane getjScrollPane1() {
	return tableScrollPane;
    }

    /**
     * Gets the room table.
     * 
     * @return the room table
     */
    public JTable getRoomTable() {
	return roomTable;
    }

    /**
     * Initializes the view.
     */
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
	mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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

    /**
     * Shows the view to the user.
     */
    public void show() {
	mainFrame.setVisible(true);
    }

}
