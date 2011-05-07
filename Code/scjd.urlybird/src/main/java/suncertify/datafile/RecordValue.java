package suncertify.datafile;

/**
 * A {@link RecordValue} represents one value of a <code>DataFileRecord</code>
 * in a DataFile that belong to a exact one {@link DataFileColumn}.
 * 
 * @author arnelandwehr
 * 
 */
class RecordValue {

    /** the <code>DataFileCoumn</code> this values belongs to. */
    private final DataFileColumn column;

    /** the stored value. */
    private final String value;

    /**
     * Constructs a new <code>RecordValue</code> for the given
     * {@link DataFileColumn} and value.
     * 
     * @param dataFileColumn
     *            the column of this <code>RecordValue</code>, must not be
     *            <code>null</code>.
     * @param value
     *            the stored value, must not be null and its length must be
     *            positive and smaller than the column size.
     */
    RecordValue(final DataFileColumn dataFileColumn, final String value) {

	this.column = dataFileColumn;
	this.value = expandValueToColumnSize(value);
    }

    /**
     * Right pad the given values with white spaces until the value length is
     * equal to the column size.
     * 
     * @param value
     *            the value to expand.
     * @return the stretched value.
     */
    private String expandValueToColumnSize(final String value) {

	if (value.length() == column.getSize()) {
	    return value;
	}

	final StringBuilder sb = new StringBuilder(value);
	for (int i = value.length(); i < column.getSize(); i++) {
	    sb.append(" ");
	}
	return sb.toString();
    }

    /**
     * Getter for the value .
     * 
     * @return the value, never <code>null</code>.
     */
    String getValue() {
	return value;
    }

    /**
     * Specifies if this <code>RecordValue</code> stores a business value
     * (prices etc.) or a technical value (deleted or not).
     * 
     * @return <code>true</code> if this <code>RecordValue</code> stores a
     *         business value.
     * @see ColumnType
     */
    boolean isBuisnessValue() {
	return column.containsValuesOfType(ColumnType.BUSINESS);
    }

    @Override
    public boolean equals(final Object object) {

	if (this == object) {
	    return true;
	}
	if (!(object instanceof RecordValue)) {
	    return false;
	}
	final RecordValue value = (RecordValue) object;

	return this.column.equals(value.column)
		&& this.value.equals(value.value);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + column.hashCode();
	result += 31 * result + value.hashCode();
	return result;
    }

    @Override
    public String toString() {
	return getClass().getSimpleName() + " [ " + "column = " + column
		+ "; value = " + value + " ] ";
    }

}