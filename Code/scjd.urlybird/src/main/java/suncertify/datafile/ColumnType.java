package suncertify.datafile;

import suncertify.db.Record;

/**
 * An <code>ColumnType</code> defines the different types of values a
 * {@link DataFileColumn} could contain.
 * 
 * @see DataFileColumn#containsValuesOfType(ColumnType)
 * @author arnelandwehr
 * 
 */
enum ColumnType {

    /**
     * the column contains business relevant values.
     */
    BUSINESS,

    /**
     * the column contains an technical flag that indicates if the
     * {@link Record} is deleted or not.
     */
    DELETED

}
