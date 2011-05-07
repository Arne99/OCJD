package suncertify.db;

import java.io.IOException;

/**
 * Could be used to transform the checked {@link IOException} in an unchecked
 * {@link RuntimeException} by wrapping it.
 * 
 * @author arnelandwehr
 * 
 */
public final class DatabaseException extends RuntimeException {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -6621421555578468675L;

    /**
     * Constructs a new wrapper <code>DatabaseException</code> for the given
     * nested {@link IOException}.
     * 
     * @param ioException
     *            the nested <code>IOException</code>, must not be
     *            <code>null</code>.
     */
    public DatabaseException(final IOException ioException) {
	super(ioException);
    }

}
