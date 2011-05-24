package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

final class ExitDialog extends ExitDialogAdapter {

    private final JDialog dialog;

    ExitDialog(final JDialog dialog) {
	super();
	this.dialog = dialog;
    }

    @Override
    public void windowClosing(final WindowEvent event) {
	dialog.dispose();
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
	dialog.dispose();
    }

}

abstract class ExitDialogAdapter extends WindowAdapter implements
	ActionListener {

}

final class ExitApplication extends ExitDialogAdapter {

    @Override
    public void windowClosing(final WindowEvent arg0) {
	askUserForExit();
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
	askUserForExit();
    }

    private void askUserForExit() {
	final int result = JOptionPane
		.showConfirmDialog(
			null,
			"Do you really want to exit, without connectiong to a database? The application will be terminated!",
			"Exit?", JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    System.exit(0);
	}
    }
}
