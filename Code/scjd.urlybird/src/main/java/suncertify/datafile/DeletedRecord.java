package suncertify.datafile;

import java.util.List;

class DeletedRecord extends DataFileRecord {

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
