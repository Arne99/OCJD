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
final class ValidRecord implements DataFileRecord {

    /**
     * The values of the record. The order represents the column order in the
     * underlying datafile.
     */
    private final List<RecordValue> values;

    /**
     * the index of the record in the datafile.
     */
    private final int index;

    /**
     * Constructor.
     * 
     * @param values
     *            the values in this record, must be not <code>null</code>.
     * @param index
     *            the index of this record, must be positiv.
     */
    public ValidRecord(final List<RecordValue> values, final int index) {
	super();
	this.values = Collections.unmodifiableList(new ArrayList<RecordValue>(
		values));
	this.index = index;
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
	return record.index == this.index && record.values.equals(this.values);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + index;
	result += 31 * result + values.hashCode();
	return result;
    }

    @Override
    public byte[] getValuesAsBytes() {

	final StringBuilder sb = new StringBuilder();
	for (final RecordValue value : values) {
	    sb.append(value.getValue());
	}

	return sb.toString().getBytes();
    }

    @Override
    public boolean isValid() {

	boolean isValid = true;

	for (final RecordValue value : values) {
	    isValid &= value.isValid();
	    isValid &= !value.isDeletedFlag();
	}

	return isValid;
    }

    @Override
    public int getIndex() {
	return index;
    }

    @Override
    public List<String> getAllBusinessValues() {

	final ArrayList<String> businessValues = new ArrayList<String>();

	for (final RecordValue recordValue : values) {
	    if (recordValue.isBuisnessValue()) {
		final String trimmedValue = recordValue.getValue().trim();
		businessValues.add(trimmedValue);
	    }

	}

	return Collections.unmodifiableList(businessValues);
    }

    @Override
    public List<String> getAllValues() {

	final List<String> allValues = new ArrayList<String>();

	for (final RecordValue recordValue : values) {
	    allValues.add(recordValue.getValue());
	}

	return Collections.unmodifiableList(allValues);
    }
}
