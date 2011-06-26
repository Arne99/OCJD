package suncertify.datafile;

import static suncertify.common.DesignByContract.checkMustNotBeSmallerThen;
import static suncertify.common.DesignByContract.checkNotNull;
import suncertify.common.Range;

/**
 * An <code>BusinessValueColumn</code> represents an {@link DataFileColumn}
 * which could store business relevant data in it. It could contain any
 * <code>String</code> value and the default value is an empty
 * <code>String</code>. Any <code>BusinessValueColumn</code> could be identified
 * by its name and its {@link Range}.
 * 
 * @author arnelandwehr
 * 
 */
final class BusinessValueColumn implements DataFileColumn {

    /** the name of the business column. */
    private final String name;

    /** the start and end point of the column in an DataFile in bytes. */
    private final Range range;

    /**
     * Constructs a new <code>BusinessValueColumn</code> with the given name and
     * {@link Range}.
     * 
     * @param name
     *            the name of the column, must not be <code>null</code>.
     * @param range
     *            the <code>Range</code> of this column in the DataFile, must
     *            not be <code>null</code>.
     */
    BusinessValueColumn(final String name, final Range range) {
	super();

	this.name = name;
	this.range = range;
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + " [ " + "name = " + name
		+ "; range = " + range + " ] ";
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof BusinessValueColumn)) {
	    return false;
	}
	final BusinessValueColumn column = (BusinessValueColumn) object;
	return this.name.equals(column.name) && this.range.equals(column.range);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + name.hashCode();
	result = 31 * result + range.hashCode();
	return result;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public int getSize() {
	return range.getSize();
    }

    @Override
    public int getStartIndex() {
	return range.getStart();
    }

    @Override
    public int getEndIndex() {
	return range.getEnd();
    }

    @Override
    public RecordValue createValue(final String value) {

	checkMustNotBeSmallerThen(range.getSize(), value.length());
	return new RecordValue(this, value);
    }

    @Override
    public RecordValue createDefaultValue() {
	return createValue("");
    }

    @Override
    public boolean containsValuesOfType(final ColumnType type) {

	checkNotNull(type, "type");

	switch (type) {
	case BUSINESS:
	    return true;
	default:
	    return false;
	}
    }
}
