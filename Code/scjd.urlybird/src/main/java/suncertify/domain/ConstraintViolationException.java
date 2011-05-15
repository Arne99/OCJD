package suncertify.domain;

/**
 * Is thrown if a constraint of an domain object attribute is not met. For
 * example if the attribute age must be positive and the client want to store a
 * negative value.
 * 
 * @author arnelandwehr
 * 
 */
public final class ConstraintViolationException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 6677944220649967272L;

    /**
     * Constructs a new <code>ConstraintViolationException</code> with the given
     * message.
     * 
     * @param message
     *            the message to be displayed, must not be <code>null</code>.
     */
    public ConstraintViolationException(final String message) {
	super(message);
    }

}
