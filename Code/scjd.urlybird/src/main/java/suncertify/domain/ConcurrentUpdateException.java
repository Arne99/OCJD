package suncertify.domain;

/**
 * Could be thrown if an concurrent update of an persisted object is detected.
 * This guard against the lost update problem in an concurrent environment.
 * 
 * @author arnelandwehr
 * 
 */
final class ConcurrentUpdateException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -8129513421208904913L;

    /**
     * Constructs a new <code>ConcurrentUpdateException</code>.
     * 
     * @param message
     *            the exception message, must not be <code>null</code>.
     */
    public ConcurrentUpdateException(final String message) {
	super(message);
    }

}
