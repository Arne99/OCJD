package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

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

    private final String notDeletedFlag;

    private final String isDeletedFlag;

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
    DeletedColumn(final String name, final Range range,
	    final String notdeletedFlag, final String isDeletedFlag) {
	super();

	this.name = name;
	this.range = range;
	this.notDeletedFlag = notdeletedFlag;
	this.isDeletedFlag = isDeletedFlag;

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
		&& deletedColumn.name.equals(this.name)
		&& deletedColumn.isDeletedFlag.equals(this.isDeletedFlag)
		&& deletedColumn.notDeletedFlag.equals(this.notDeletedFlag);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + range.hashCode();
	result = 31 * result + name.hashCode();
	result = 31 * result + isDeletedFlag.hashCode();
	result = 31 * result + notDeletedFlag.hashCode();
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
    public boolean containsValuesOfType(final ColumnType type) {

	checkNotNull(type, "type");

	switch (type) {
	case DELETED:
	    return true;
	default:
	    return false;
	}
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
	return createValue(notDeletedFlag);
    }

    @Override
    public RecordValue createValue(final String value) {

	checkMustNotBeSmallerThen(range.getSize(), value.length());
	return new RecordValue(this, value);
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + " [ " + "name = " + name
		+ "; range = " + range + "; notdeletedFlag = " + notDeletedFlag
		+ "; isDeletedFlag = " + isDeletedFlag + " ] ";
    }

    RecordValue createDeletedValue() {
	return createValue(isDeletedFlag);
    }

    boolean isDeletedValue(final String value) {
	return isDeletedFlag.equals(value);
    }
}
