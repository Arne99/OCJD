package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import suncertify.db.Record;

/**
 * A <code>DataFileRecord</code> represents an {@link Record} in a flat file
 * store. The values are stored as {@link RecordValue}s.
 * 
 * 
 * @author arnelandwehr
 * 
 */
abstract class DataFileRecord implements Record {

    /**
     * The values of the record. The order represents the column order in the
     * underlying DataFile.
     */
    protected final List<RecordValue> values;

    /**
     * the index of the record in the DataFile.
     */
    protected final int index;

    /**
     * Constructor.
     * 
     * @param values
     *            the values in this record, must be not <code>null</code>.
     * @param index
     *            the index of this record, must be positiv.
     */
    public DataFileRecord(final List<RecordValue> values, final int index) {
	super();
	this.values = Collections.unmodifiableList(new ArrayList<RecordValue>(
		values));
	this.index = index;
    }

    /**
     * Transforms all values in this record in one byte array and returns it.
     * 
     * @return the byte array, never <code>null</code>.
     */
    protected byte[] getValuesAsBytes() {

	final StringBuilder sb = new StringBuilder();
	for (final RecordValue value : values) {
	    sb.append(value.getValue());
	}

	return sb.toString().getBytes();
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
		businessValues.add(recordValue.getValue());
	    }

	}
	return Collections.unmodifiableList(businessValues);
    }

    /**
     * Getter for all values in this <code>DataFileRecord</code> in database
     * order.
     * 
     * @return all record values, never <code>null</code>.
     */
    protected List<String> getAllValues() {

	final List<String> allValues = new ArrayList<String>();

	for (final RecordValue recordValue : values) {
	    allValues.add(recordValue.getValue());
	}

	return Collections.unmodifiableList(allValues);
    }

}