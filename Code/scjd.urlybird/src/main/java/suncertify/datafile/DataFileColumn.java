package suncertify.datafile;

/**
 * An <code>DataFileColumn</code> represents an column in an DataFile with a
 * name and a start and end point in bytes. Any DataFileColumn could contain
 * different types of values. The different types are specified in
 * {@link ColumnType}.
 * 
 * @author arnelandwehr
 * 
 */
public interface DataFileColumn {

    /**
     * Getter for the size of the column (end point - start point).
     * 
     * @return the size of the column, must be positive.
     */
    int getSize();

    /**
     * Checks if the column could contain values of the given {@link ColumnType}
     * .
     * 
     * @param type
     *            the column should contain, must not be <code>null</code>.
     * @return <code>true</code> if the column could contain values of the
     *         specified type.
     */
    boolean containsValuesOfType(ColumnType type);

    /**
     * Getter for the start point in the DataFile in bytes.
     * 
     * @return the position of the beginning of this column, must be positive.
     */
    int getStartIndex();

    /**
     * Transforms the given <code>String</code> into an {@link RecordValue}
     * which could be stored into the column of the DataFile.
     * 
     * @param value
     *            the value which should be persisted into the DataFile, must
     *            not be <code>null</code>.
     * @return the <code>RecordValue</code> which contains the given
     *         <code>String</code> an an persistable value, must not be
     *         <code>null</code>.
     */
    RecordValue createValue(String value);

    /**
     * Getter for the end point in the DataFile in bytes.
     * 
     * @return the position of the end of this column, must be positive.
     */
    int getEndIndex();

    /**
     * Creates persistable {@link RecordValue} for this column if there is no
     * given value.
     * 
     * @return an <code>RecordValue</code> with the default value for this
     *         column.
     */
    RecordValue createDefaultValue();

    /**
     * Getter for the name of this column.
     * 
     * @return the name of this column, must not be <code>null</code>.
     */
    String getName();

}
