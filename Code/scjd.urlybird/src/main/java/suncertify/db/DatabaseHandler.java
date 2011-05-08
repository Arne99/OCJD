package suncertify.db;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import suncertify.util.Specification;

/**
 * A <code>DatabaseHandler</code> supports read and write operations on a record
 * store. A record store could be every possible database like rational, NOSQL
 * oder flat file data stores. Every Record must be identifiable by an unique
 * index.
 * 
 * To change the database for example from a flat file store to an SQL database
 * like MYSQL you have just to implement the needed {@link DatabaseHandler}. The
 * {@link DB} and {@link Database} classes must not be touched.
 * 
 * @author arnelandwehr
 * 
 */
public interface DatabaseHandler {

    /**
     * Deletes the {@link Record} with the given index. Throws no exception if
     * the given index does not fit to a stored <code>Record</code>.
     * 
     * @param index
     *            the identifier of the <code>Record</code> to delete.
     * @throws IOException
     *             if an IO-problem occurs during the writing.
     */
    void deleteRecord(int index) throws IOException;

    /**
     * Finds every {@link Record} that is specified by the given
     * {@link Specification}.
     * 
     * @param specification
     *            identifies every {@link Record} that the client wants to find,
     *            must not be <code>null</code>.
     * @return all matching {@link Record} without duplicates, never
     *         <code>null</code>.
     * @throws IOException
     *             if an IO-problem occurs during the reading.
     */
    Set<Record> findMatchingRecords(
	    final Specification specification) throws IOException;

    /**
     * Reads a record from the database. Returns an array where each element is
     * a record value. If no valid <code>Record</code> is stored for the given
     * index an invalid Record is returned.
     * 
     * @see Record#isValid()
     * 
     * @param index
     *            the identifier of the record to return, must be positive.
     * @return the read <code>Record</code>, never <code>null</code>.
     * @throws IOException
     *             if an IO-problem occurs during the reading.
     */
    Record readRecord(int index) throws IOException;

    /**
     * Writes the given values as an Record with the given index as identifier.
     * 
     * @param record
     *            the values that should be stored as an {@link Record} in the
     *            database.
     * @param index
     *            the identifier of the {@link Record} to store.
     * @throws IOException
     *             if an IO-problem occurs during the writing.
     */
    void writeRecord(List<String> record, int index) throws IOException;

    /**
     * Returns an unused index as an available {@link Record} identifier.
     * 
     * @return an unused identifier.
     * @throws IOException
     *             if an IO-problem occurs during the reading.
     */
    int findEmptyIndex() throws IOException;
}
