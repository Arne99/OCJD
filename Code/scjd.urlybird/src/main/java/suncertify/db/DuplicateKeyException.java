package suncertify.db;

/**
 * Is thrown if a {@link Record} with an already stored identifier should be
 * stored in the database.
 * 
 * @author arnelandwehr
 * 
 */
public final class DuplicateKeyException extends Exception {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 1515201185600998483L;

    /**
     * Construct a new {@link DuplicateKeyException}.
     */
    public DuplicateKeyException() {
	super();
    }

}
