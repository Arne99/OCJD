package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.util.Arrays;

import suncertify.util.Range;

/**
 * 
 * TODO anpassen A <code>TechnicalColumn</code> represents an
 * {@link DataFileColumn} that could only contain non business relevant values.
 * Examples are:
 * <ul>
 * <li>values that indicates if an record is valid
 * <li>
 * <li>the time a record was created
 * <li>
 * </ul>
 * 
 * @author arnelandwehr
 * 
 */
final class DeletedColumn implements DataFileColumn {

    private final Range range;

    private final String deletedFlag;

    /**
     * The name of the column.
     */
    private final String name;

    /**
     * Constructor.
     * 
     * @param size
     *            the size of the column in bytes, must not be <code>null</code>
     *            .
     * @param name
     *            the name of the column, must not be <code>null</code>.
     */
    public DeletedColumn(final String name, final Range range,
	    final String deletedFlag) {
	super();

	checkMustNotBeSmallerThen(range.getSize(), deletedFlag.length());

	this.name = name;
	this.range = range;
	this.deletedFlag = deletedFlag;

    }

    @Override
    public boolean equals(final Object object) {
	if (this == object) {
	    return true;
	}
	if (!(object instanceof DeletedColumn)) {
	    return false;
	}
	final DeletedColumn deletedColumn = (DeletedColumn) object;
	return deletedColumn.range.equals(this.range)
		&& deletedColumn.name.equals(this.name);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + range.hashCode();
	result = 31 * result + name.hashCode();
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
	return false;
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
    public RecordValue createDefaultValue() {
	return createValue(deletedFlag);
    }

    @Override
    public RecordValue createValue(final String value) {

	checkIsTrue(isValidValue(value), value
		+ "is not an valid value for this column: " + this);
	return new RecordValue(this, value);
    }

    @Override
    public boolean isValidValue(final String value) {

	boolean isValid = value != null;
	isValid = isValid && value.length() <= getSize();

	return isValid;
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + " [ " + "name = " + name
		+ "; range = " + range + "; deletedFlag = " + deletedFlag
		+ " ] ";
    }

    @Override
    public boolean isValueDeletedFlag(final String value) {
	return deletedFlag.equals(value);
    }
}
