package suncertify.datafile;

final class DataFileHeader {
    private final int key;
    private final int headerLength;
    private final String isDeletedFlag;

    DataFileHeader(final int key, final int headerLength,
	    final String deletedFlag) {
	this.key = key;
	this.headerLength = headerLength;
	this.isDeletedFlag = deletedFlag;
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
		&& this.headerLength == header.headerLength
		&& this.isDeletedFlag.equals(header.isDeletedFlag);
    }

    @Override
    public String toString() {
	return "DataFileHeader" + " [ " + "key = " + key + "; headerLength = "
		+ headerLength + "; isDeletedFlag = " + isDeletedFlag + " ] ";
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.key;
	result = 31 * result + this.headerLength;
	result = 31 * result + this.isDeletedFlag.hashCode();
	return result;
    }

    int getKey() {
	return key;
    }

    int getHeaderLength() {
	return headerLength;
    }

    String getDeletedFlag() {
	return isDeletedFlag;
    }
}