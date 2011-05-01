package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import suncertify.db.Record;
import suncertify.util.Range;

/**
 * 
 * An <code>DeletedColumn</code> represents an {@link DataFileColumn} which
 * contains an flag that indicates if the {@link Record} is valid or deleted.
 * The flag values ("deleted" or "not deleted") are specified by every
 * DeletedColumn. The default value is "not deleted".
 * 
 * @author arnelandwehr
 * 
 */
final class DeletedColumn implements DataFileColumn {

    /** the range of this column in bytes. */
    private final Range range;

    /** the value for a <b>not deleted</b> <code>Record</code>. */
    private final String notDeletedFlag;

    /** the value for a <b>deleted</b> <code>Record</code>. */
    private final String isDeletedFlag;

    /** The name of the column. */
    private final String name;

    /**
     * Constructs a new {@link DeletedColumn} with the given values that
     * indicates an <b>deleted</b> or <b>not deleted</b> {@link Record}.
     * 
     * @param range
     *            the <code>Range</code> of the column in bytes, must not be
     *            <code>null</code> .
     * @param name
     *            the name of the column, must not be <code>null</code>.
     * @param notdeletedFlag
     *            the value that indicates an <b>not deleted</b>
     *            <code>Record</code>.
     * @param isDeletedFlag
     *            the value that indicates an <b>deleted</b> <code>Record</code>
     *            .
     * 
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

    /**
     * Creates an {@link RecordValue} that specifies the {@link Record} as
     * deleted.
     * 
     * @return the <code>RecordValue</code>, never <code>null</code>.
     */
    RecordValue createDeletedValue() {
	return createValue(isDeletedFlag);
    }

    /**
     * Returns if the given value indicates an deleted {@link Record}.
     * 
     * @param value
     *            the value which could specify an deleted <code>Record</code>.
     * @return <code>true</code> if the value indicates an deleted
     *         <code>Record</code>.
     */
    boolean isDeletedValue(final String value) {
	return isDeletedFlag.equals(value);
    }
}
