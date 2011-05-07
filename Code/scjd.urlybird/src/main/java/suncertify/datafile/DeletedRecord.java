package suncertify.datafile;

import java.util.List;

/**
 * An <code>DeletedRecord</code> is an {@link DataFileRecord} that is already
 * deleted but still stored in the DataFile, it is not valid and must be
 * overlooked by business find or read methods. The storage of an
 * <code>DeletedRecord</code> in an DataFile could be reused by new created
 * valid {@link DataFileRecord}s.
 * 
 * @author arnelandwehr
 * 
 */
class DeletedRecord extends DataFileRecord {

    /**
     * Construct a new <code>DeletedRecord</code>.
     * 
     * @param recordValues
     *            the values of the deleted record, must not be
     *            <code>null</code>.
     * @param index
     *            the index of the record, must be positive.
     */
    DeletedRecord(final List<RecordValue> recordValues, final int index) {
	super(recordValues, index);
    }

    @Override
    public boolean isValid() {
	return false;
    }

    @Override
    public String toString() {
	final String validIdentifier = (isValid()) ? "valid" : "deleted";
	return getClass().getSimpleName() + " " + getAllBusinessValues() + " "
		+ validIdentifier;
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DeletedRecord)) {
	    return false;
	}

	final DeletedRecord record = (DeletedRecord) object;
	return record.index == index && record.values.equals(this.values);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + index;
	result += 31 * result + values.hashCode();
	return result;
    }

}
