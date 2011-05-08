package suncertify.db;

/**
 * Is thrown if a specified {@link Record} could not be found in the database.
 * 
 * @see DB
 * 
 * @author arnelandwehr
 * 
 */
public final class RecordNotFoundException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -3664449964979227448L;

    /**
     * Construct a new <code>RecordNotFoundException</code>.
     */
    public RecordNotFoundException() {
	super();
    }

    /**
     * Construct a new <code>RecordNotFoundException</code> with the given
     * message.
     * 
     * @param message
     *            the message of the exception, must not be <code>null</code>.
     */
    public RecordNotFoundException(final String message) {
	super(message);
    }

    /**
     * Construct a new <code>RecordNotFoundException</code> with the given
     * message and nested exception.
     * 
     * @param message
     *            the message of the exception, must not be <code>null</code>.
     * @param exception
     *            the nested exception, must not be <code>null</code>.
     */
    public RecordNotFoundException(final String message,
	    final Throwable exception) {
	super(message, exception);
    }

}
