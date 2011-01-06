package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import suncertify.util.Range;

final class BusinessColumn implements DataFileColumn {

    private final String name;
    private final Range range;

    public BusinessColumn(final String name, final Range range) {
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
	if (!(object instanceof BusinessColumn)) {
	    return false;
	}
	final BusinessColumn column = (BusinessColumn) object;
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
    public boolean containsBuissnessValues() {
	return true;
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

	checkIsTrue(isValidValue(value), value
		+ "is not an valid value for this column: " + this);
	return new RecordValue(this, value);
    }

    @Override
    public RecordValue createDefaultValue() {
	return createValue("");
    }

    @Override
    public boolean isValidValue(final String value) {

	boolean isValid = value != null;
	isValid = isValid && value.length() <= getSize();

	return isValid;
    }

    @Override
    public boolean isValueDeletedFlag(final String value) {
	return false;
    }

}
