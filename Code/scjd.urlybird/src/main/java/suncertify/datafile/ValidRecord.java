package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A <code>ValidRecord</code> represents a regular not deleted
 * {@link ValidRecord} in the underlying datafile. It contains one
 * {@link BusinessValue} for every column in the datafile. The values are stored
 * as simple <code>Strings</code>. Every <code>ValidRecord</code> contains its
 * index as an unique identifier.
 * 
 * @author arnelandwehr
 * 
 */
final class ValidRecord extends DataFileRecord {

    /**
     * Constructor.
     * 
     * @param values
     *            the values in this record, must be not <code>null</code>.
     * @param index
     *            the index of this record, must be positiv.
     */
    ValidRecord(final List<RecordValue> values, final int index) {
	super(values, index);
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
	if (!(object instanceof ValidRecord)) {
	    return false;
	}

	final ValidRecord record = (ValidRecord) object;
	return record.index == index && record.values.equals(this.values);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + index;
	result += 31 * result + values.hashCode();
	return result;
    }

    @Override
    public boolean isValid() {
	return true;
    }

}
