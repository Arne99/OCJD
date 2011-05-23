package suncertify.db;

/**
 * Is thrown if the security conditions of the {@link DB} are violated. This
 * could for example happen if a client want to write to a specific index with
 * the lock n but the index is locked with lock x.
 * 
 * @author arnelandwehr
 * 
 */
public final class SecurityException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 2688631587518151738L;

    /**
     * Constructs a new <code>SecurityException</code>.
     */
    public SecurityException() {
	this("");
    }

    /**
     * Construct a new <code>SecurityException</code>.
     * 
     * @param message
     *            the exception message.
     */
    public SecurityException(final String message) {
	super(message);
    }
}
