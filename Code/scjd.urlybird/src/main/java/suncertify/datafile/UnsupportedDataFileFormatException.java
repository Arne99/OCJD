package suncertify.datafile;

/**
 * An <code>UnsupportedDataFileFormatException</code> occurs if the
 * <code>File</code>, that should be used as an datastore for the UrlyBird
 * application, is not an valid DataFile.
 * 
 * @author arnelandwehr
 * 
 */
class UnsupportedDataFileFormatException extends Exception {

    /**
     * Random SerialVersionUid.
     */
    private static final long serialVersionUID = -6958147326260949483L;

    /**
     * Constructor.
     * 
     * @param message
     *            the message to be shown if the exception is thrown.
     */
    UnsupportedDataFileFormatException(final String message) {
	super(message);
    }

}
