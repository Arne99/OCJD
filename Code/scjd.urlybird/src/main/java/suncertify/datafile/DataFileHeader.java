package suncertify.datafile;

final class DataFileHeader {
    private final int key;
    private final int headerLength;

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

    int getKey() {
	return key;
    }

    int getHeaderLength() {
	return headerLength;
    }

}