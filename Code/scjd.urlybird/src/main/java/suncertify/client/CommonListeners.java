package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * {@link ExitDialogAdapter} implementation that just disposes the given dialog.
 * 
 * @author arnelandwehr
 * 
 */
final class ExitDialog extends ExitDialogAdapter {

    /** the dialog to dispose. */
    private final JDialog dialog;

    /**
     * Constructs an new <code>ExitDialog</code>.
     * 
     * @param dialog
     *            the dialog to dispose.
     */
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

/**
 * Abstract class for listeners that should implements {@link WindowAdapter} and
 * {@link ActionListener}.
 * 
 * @author arnelandwehr
 * 
 */
abstract class ExitDialogAdapter extends WindowAdapter implements
	ActionListener {

}

/**
 * {@link ExitDialogAdapter} that shuts down the application after showing an
 * confirmation dialog.
 * 
 * @author arnelandwehr
 * 
 */
final class ExitApplication extends ExitDialogAdapter {

    @Override
    public void windowClosing(final WindowEvent arg0) {
	askUserForExit();
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
	askUserForExit();
    }

    /**
     * Asks the user with an {@link JOptionPane} confirmation dialog if he
     * really wants to close the application and shuts it down if he agrees.
     */
    private void askUserForExit() {
	final int result = JOptionPane.showConfirmDialog(null,
		"Do you really want to exit and leave UrlyBird?", "Exit?",
		JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    System.exit(0);
	}
    }
}
