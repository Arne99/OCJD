package suncertify.db;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;

/**
 * Implementation of the {@link DB} interface, that abstract the format of the
 * real database from the client. So this class could operate with NOSQL,
 * relational and flat file databases if the necessary {@link DatabaseHandler}
 * is provided.
 * 
 * @author arnelandwehr
 * 
 */
public final class Data implements DB {

    /** exception logger, global is sufficient here. */
    private final Logger logger = Logger.getLogger("global");

    /** the handler operates with the real database in the background. */
    private final DatabaseHandler handler;

    /** locks single records for write operations. */
    private final RecordLocker locker;

    /**
     * Constructs a new <code>Database</code>.
     * 
     * @param handler
     *            the handler that operates with the real database in the
     *            back-end, must not be <code>null</code>.
     * @param locker
     *            the locker that locks single records for write operations,
     *            must not be <code>null</code>.
     */
    Data(final DatabaseHandler handler, final RecordLocker locker) {
	super();
	this.handler = handler;
	this.locker = locker;
    }

    /**
     * Public constructor, because it was not clear if a default constructor
     * must be provided.
     * 
     * @throws IOException
     *             if an io problem occurs.
     * @throws UnsupportedDataFileFormatException
     *             if the data file is not of any supported format.
     */
    public Data() throws IOException, UnsupportedDataFileFormatException {

	this(DataFileService.instance().getDatabaseHandler(
		new File("./db-1x1.db")), SynchronizedRecordLocker.instance());
    }

    @Override
    public String[] read(final int recNo) throws RecordNotFoundException {

	Record record = null;
	try {
	    record = handler.readRecord(recNo);
	} catch (final IOException e) {
	    throw new RecordNotFoundException(
		    "The database does not contain any record for the given index: "
			    + recNo, e);
	}
	if (record.isValid()) {
	    final List<String> allBusinessValues = record
		    .getAllBusinessValues();
	    return allBusinessValues.toArray(new String[allBusinessValues
		    .size()]);
	}

	throw new RecordNotFoundException(
		"The database does not contain any record for the given index: "
			+ recNo);
    }

    @Override
    public void update(final int recNo, final String[] data,
	    final long lockCookie) throws RecordNotFoundException,
	    SecurityException {

	locker.checkRecordOwner(recNo, lockCookie);

	try {
	    handler.writeRecord(Arrays.asList(data), recNo);
	} catch (final IOException e) {
	    throw new RecordNotFoundException(
		    "The database does not contain any record for the given index: "
			    + recNo, e);
	}
    }

    @Override
    public void delete(final int recNo, final long lockCookie)
	    throws RecordNotFoundException, SecurityException {

	locker.checkRecordOwner(recNo, lockCookie);
	try {
	    checkIfThereIsARecordToDelete(recNo);
	    handler.deleteRecord(recNo);
	} catch (final IOException e) {
	    // transforms the IOException in a RecordNotFoundException to
	    // fulfill the interface.
	    logger.throwing(getClass().getName(), "delete", e);
	    throw new RecordNotFoundException(
		    "The database could not obtain any record for the given index: "
			    + recNo + " because of: " + e.getMessage(), e);
	}
    }

    /**
     * Checks if a valid record is stored under the given index.
     * 
     * @param recNo
     *            the index to check.
     * @throws IOException
     *             if an io problem occurs during the check.
     * @throws RecordNotFoundException
     *             if no valid record is stored under the given index.
     */
    private void checkIfThereIsARecordToDelete(final int recNo)
	    throws IOException, RecordNotFoundException {
	read(recNo);
    }

    @Override
    public int[] find(final String[] criteria) {

	Set<Record> records = null;
	try {
	    records = handler
		    .findMatchingRecords(new NullAlwaysMatchesAllValuesInValidRecords(
			    Arrays.asList(criteria)));
	} catch (final IOException e) {
	    logger.throwing(getClass().getName(), "find", e);
	    // transforms the checked IO exception in a runtime exception
	    // to fulfill the interface
	    throw new DatabaseException(
		    "The database could not find any record for the given criteria: "
			    + Arrays.toString(criteria) + " because of: "
			    + e.getMessage(), e);
	}

	final int[] recNumbers = new int[records.size()];
	final Iterator<Record> recordIter = records.iterator();
	int counter = 0;
	while (recordIter.hasNext()) {
	    recNumbers[counter] = recordIter.next().getIndex();
	    counter++;
	}
	return recNumbers;
    }

    @Override
    public int create(final String[] data) throws DuplicateKeyException {

	// must be synchronized there must not be an concurrent create between
	// find, findEmptyIndex and create.
	synchronized (this) {
	    try {
		final int[] duplicateRecordIndices = find(data);
		if (duplicateRecordIndices.length != 0) {
		    throw new DuplicateKeyException(
			    "the row to insert into the database is already stored. Data to insert: "
				    + Arrays.toString(data)
				    + " rows with the same data: "
				    + Arrays.toString(duplicateRecordIndices));
		}
		final int emptyIndex = handler.findEmptyIndex();
		handler.writeRecord(Arrays.asList(data), emptyIndex);
		return emptyIndex;
	    } catch (final IOException e) {
		logger.throwing(getClass().getName(), "create", e);
		// transforms the checked IO exception in a runtime exception
		// to fulfill the interface
		throw new DatabaseException(
			"The database could not create any record with the given values: "
				+ Arrays.toString(data) + "because of: "
				+ e.getMessage(), e);
	    }
	}
    }

    @Override
    public long lock(final int recNo) throws RecordNotFoundException {

	read(recNo);
	return locker.lockRecord(recNo);
    }

    @Override
    public void unlock(final int recNo, final long cookie)
	    throws RecordNotFoundException, SecurityException {

	locker.unlockRecord(recNo, cookie);
    }

}
