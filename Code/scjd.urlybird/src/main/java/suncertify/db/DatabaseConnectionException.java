package suncertify.db;

/**
 * Exception that is thrown if there happens a problem during the connect
 * operation to a database.
 * 
 * @author arnelandwehr
 * @see DatabaseService
 */
public final class DatabaseConnectionException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 8129041018561890173L;

    /**
     * Constructs a new <code>DatabaseConnectionException</code> with the given
     * {@link Exception} as nested exception.
     * 
     * @param nestedException
     *            the nested exception, must not be <code>null</code>.
     */
    public DatabaseConnectionException(final Exception nestedException) {
	super(nestedException);
    }

}
