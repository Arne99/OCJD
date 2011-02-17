package suncertify.client;

import javax.swing.JFrame;

public interface UrlyBirdView {

    public abstract JFrame getMainFrame();

    public abstract void setMainFrame(JFrame mainFrame);

    public abstract javax.swing.JButton getExitButton();

    public abstract void setExitButton(javax.swing.JButton exitButton);

    public abstract javax.swing.JButton getFindButton();

    public abstract void setFindButton(javax.swing.JButton findButton);

    public abstract javax.swing.JButton getBookButton();

    public abstract void setBookButton(javax.swing.JButton bookButton);

    public abstract javax.swing.JButton getNewButton();

    public abstract void setNewButton(javax.swing.JButton newButton);

    public abstract javax.swing.JButton getDeleteButton();

    public abstract void setDeleteButton(javax.swing.JButton deleteButton);

    public abstract javax.swing.JButton getChangeButton();

    public abstract void setChangeButton(javax.swing.JButton changeButton);

    public abstract javax.swing.JButton getAllButton();

    public abstract void setAllButton(javax.swing.JButton allButton);

    public abstract javax.swing.JMenu getUrlyBirdMenu();

    public abstract void setUrlyBirdMenu(javax.swing.JMenu urlyBirdMenu);

    public abstract javax.swing.JMenu getEditMenu();

    public abstract void setEditMenu(javax.swing.JMenu editMenu);

    public abstract javax.swing.JMenu getHelpMenu();

    public abstract void setHelpMenu(javax.swing.JMenu helpMenu);

    public abstract javax.swing.JMenu getViewMenu();

    public abstract void setViewMenu(javax.swing.JMenu viewMenu);

    public abstract javax.swing.JMenuBar getMenuBar();

    public abstract void setMenuBar(javax.swing.JMenuBar menuBar);

    public abstract javax.swing.JMenuItem getNewMenuItem();

    public abstract void setNewMenuItem(javax.swing.JMenuItem newMenuItem);

    public abstract javax.swing.JMenuItem getBookMenuItem();

    public abstract void setBookMenuItem(javax.swing.JMenuItem bookMenuItem);

    public abstract javax.swing.JMenuItem getDeleteMenuItem();

    public abstract void setDeleteMenuItem(javax.swing.JMenuItem deleteMenuItem);

    public abstract javax.swing.JMenuItem getChangeMenuItem();

    public abstract void setChangeMenuItem(javax.swing.JMenuItem changeMenuItem);

    public abstract javax.swing.JMenuItem getAllMenuItem();

    public abstract void setAllMenuItem(javax.swing.JMenuItem allMenuItem);

    public abstract javax.swing.JMenuItem getFindMenuItem();

    public abstract void setFindMenuItem(javax.swing.JMenuItem findMenuItem);

    public abstract javax.swing.JMenuItem getConnectionMenuItem();

    public abstract void setConnectionMenuItem(
	    javax.swing.JMenuItem connectionMenuItem);

    public abstract javax.swing.JMenuItem getAboutMenuItem();

    public abstract void setAboutMenuItem(javax.swing.JMenuItem aboutMenuItem);

    public abstract javax.swing.JMenuItem getDatabaseMenuItem();

    public abstract void setDatabaseMenuItem(
	    javax.swing.JMenuItem databaseMenuItem);

    public abstract javax.swing.JScrollPane getjScrollPane1();

    public abstract void setjScrollPane1(javax.swing.JScrollPane jScrollPane1);

    public abstract javax.swing.JTable getRoomTable();

    public abstract void setRoomTable(javax.swing.JTable roomTable);

    public abstract void init();

    public abstract void show();

}