package suncertify.client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UrlyBirdGui implements UrlyBirdView {

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

    @Override
    public JFrame getMainFrame() {
	return mainFrame;
    }

    @Override
    public void setMainFrame(final JFrame mainFrame) {
	this.mainFrame = mainFrame;
    }

    @Override
    public JButton getExitButton() {
	return exitButton;
    }

    @Override
    public void setExitButton(final JButton exitButton) {
	this.exitButton = exitButton;
    }

    @Override
    public JButton getFindButton() {
	return findButton;
    }

    @Override
    public void setFindButton(final JButton findButton) {
	this.findButton = findButton;
    }

    @Override
    public JButton getBookButton() {
	return bookButton;
    }

    @Override
    public void setBookButton(final JButton bookButton) {
	this.bookButton = bookButton;
    }

    @Override
    public JButton getNewButton() {
	return newButton;
    }

    @Override
    public void setNewButton(final JButton newButton) {
	this.newButton = newButton;
    }

    @Override
    public JButton getDeleteButton() {
	return deleteButton;
    }

    @Override
    public void setDeleteButton(final JButton deleteButton) {
	this.deleteButton = deleteButton;
    }

    @Override
    public JButton getChangeButton() {
	return changeButton;
    }

    @Override
    public void setChangeButton(final JButton changeButton) {
	this.changeButton = changeButton;
    }

    @Override
    public JButton getAllButton() {
	return allButton;
    }

    @Override
    public void setAllButton(final JButton allButton) {
	this.allButton = allButton;
    }

    @Override
    public JMenu getUrlyBirdMenu() {
	return urlyBirdMenu;
    }

    @Override
    public void setUrlyBirdMenu(final JMenu urlyBirdMenu) {
	this.urlyBirdMenu = urlyBirdMenu;
    }

    @Override
    public JMenu getEditMenu() {
	return editMenu;
    }

    @Override
    public void setEditMenu(final JMenu editMenu) {
	this.editMenu = editMenu;
    }

    @Override
    public JMenu getHelpMenu() {
	return helpMenu;
    }

    @Override
    public void setHelpMenu(final JMenu helpMenu) {
	this.helpMenu = helpMenu;
    }

    @Override
    public JMenu getViewMenu() {
	return viewMenu;
    }

    @Override
    public void setViewMenu(final JMenu viewMenu) {
	this.viewMenu = viewMenu;
    }

    @Override
    public JMenuBar getMenuBar() {
	return menuBar;
    }

    @Override
    public void setMenuBar(final JMenuBar menuBar) {
	this.menuBar = menuBar;
    }

    @Override
    public JMenuItem getNewMenuItem() {
	return newMenuItem;
    }

    @Override
    public void setNewMenuItem(final JMenuItem newMenuItem) {
	this.newMenuItem = newMenuItem;
    }

    @Override
    public JMenuItem getBookMenuItem() {
	return bookMenuItem;
    }

    @Override
    public void setBookMenuItem(final JMenuItem bookMenuItem) {
	this.bookMenuItem = bookMenuItem;
    }

    @Override
    public JMenuItem getDeleteMenuItem() {
	return deleteMenuItem;
    }

    @Override
    public void setDeleteMenuItem(final JMenuItem deleteMenuItem) {
	this.deleteMenuItem = deleteMenuItem;
    }

    @Override
    public JMenuItem getChangeMenuItem() {
	return changeMenuItem;
    }

    @Override
    public void setChangeMenuItem(final JMenuItem changeMenuItem) {
	this.changeMenuItem = changeMenuItem;
    }

    @Override
    public JMenuItem getAllMenuItem() {
	return allMenuItem;
    }

    @Override
    public void setAllMenuItem(final JMenuItem allMenuItem) {
	this.allMenuItem = allMenuItem;
    }

    @Override
    public JMenuItem getFindMenuItem() {
	return findMenuItem;
    }

    @Override
    public void setFindMenuItem(final JMenuItem findMenuItem) {
	this.findMenuItem = findMenuItem;
    }

    @Override
    public JMenuItem getConnectionMenuItem() {
	return connectionMenuItem;
    }

    @Override
    public void setConnectionMenuItem(final JMenuItem connectionMenuItem) {
	this.connectionMenuItem = connectionMenuItem;
    }

    @Override
    public JMenuItem getAboutMenuItem() {
	return aboutMenuItem;
    }

    @Override
    public void setAboutMenuItem(final JMenuItem aboutMenuItem) {
	this.aboutMenuItem = aboutMenuItem;
    }

    @Override
    public JMenuItem getDatabaseMenuItem() {
	return databaseMenuItem;
    }

    @Override
    public void setDatabaseMenuItem(final JMenuItem databaseMenuItem) {
	this.databaseMenuItem = databaseMenuItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.client.UrlyBirdView#getjScrollPane1()
     */
    @Override
    public JScrollPane getjScrollPane1() {
	return jScrollPane1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.client.UrlyBirdView#setjScrollPane1(JScrollPane)
     */
    @Override
    public void setjScrollPane1(final JScrollPane jScrollPane1) {
	this.jScrollPane1 = jScrollPane1;
    }

    @Override
    public JTable getRoomTable() {
	return roomTable;
    }

    @Override
    public void setRoomTable(final JTable roomTable) {
	this.roomTable = roomTable;
    }

    private JMenuItem changeMenuItem;
    private JMenuItem allMenuItem;
    private JMenuItem findMenuItem;
    private JMenuItem connectionMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem databaseMenuItem;
    private JScrollPane jScrollPane1;
    private JTable roomTable;

    @Override
    public void init() {
	mainFrame = new JFrame("UrlyBird");
	jScrollPane1 = new JScrollPane();
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
	databaseMenuItem = new JMenuItem();
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

	roomTable.setModel(new DefaultTableModel(new Object[][] {
		{ null, null, null, null, null, null, null },
		{ null, null, null, null, null, null, null },
		{ null, null, null, null, null, null, null },
		{ null, null, null, null, null, null, null } }, new String[] {
		"Title 1", "Title 2", "Title 3", "Title 4", "Title 5",
		"Title 6", "Title 7" }));
	jScrollPane1.setViewportView(roomTable);

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
	contentPane.add(jScrollPane1, gridBagConstraints);

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

	databaseMenuItem.setText("Database");
	urlyBirdMenu.add(databaseMenuItem);

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
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
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
