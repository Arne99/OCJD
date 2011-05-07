package suncertify.datafile;

/**
 * A <code>DataFileHeader</code> contains the header information of the
 * {@link DataFileSchema}. These are:
 * <ul>
 * <li>the unique format identifier</li>
 * <li>the length of the header in bytes</li>
 * </ul>
 * 
 * Every header is an immutable value object.
 * 
 * @author arnelandwehr
 * 
 */
final class DataFileHeader {

    /** the unique identifier for one special DataFileSchema. */
    private final int key;

    /** the length of the header in bytes. */
    private final int headerLength;

    /**
     * Construct a new <code>DataFileHeader</code>.
     * 
     * @param key
     *            the unique identifier for one special DataFileSchema.
     * @param headerLength
     *            the length of the header in bytes, must be positive.
     */
    DataFileHeader(final int key, final int headerLength) {
	this.key = key;
	this.headerLength = headerLength;
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DataFileHeader)) {
	    return false;
	}
	final DataFileHeader header = (DataFileHeader) object;
	return this.key == header.key
		&& this.headerLength == header.headerLength;
    }

    @Override
    public String toString() {
	return "DataFileHeader" + " [ " + "key = " + key + "; headerLength = "
		+ headerLength + " ] ";
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.key;
	result = 31 * result + this.headerLength;
	return result;
    }

    /**
     * Getter for the key.
     * 
     * @return the key.
     */
    int getKey() {
	return key;
    }

    /**
     * Getter for the length of the header in bytes.
     * 
     * @return the header length.
     */
    int getHeaderLength() {
	return headerLength;
    }

}