package suncertify.db;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the {@link DB} interface, that abstract the format of the
 * real database from the client. So this class could operate with NOSQL,
 * relational and flat file databases if the necessary {@link DatabaseHandler}
 * is provided.
 * 
 * @author arnelandwehr
 * 
 */
public final class Database implements DB {

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
    Database(final DatabaseHandler handler, final RecordLocker locker) {
	super();
	this.handler = handler;
	this.locker = locker;
    }

    @Override
    public String[] read(final int recNo) throws RecordNotFoundException {

	Record record = null;
	try {
	    record = handler.readRecord(recNo);
	} catch (final IOException e) {
	    throw new RecordNotFoundException("", e);
	}
	if (record.isValid()) {
	    final List<String> allBusinessValues = record
		    .getAllBusinessValues();
	    return allBusinessValues.toArray(new String[allBusinessValues
		    .size()]);
	}

	throw new RecordNotFoundException("");
    }

    @Override
    public void update(final int recNo, final String[] data,
	    final long lockCookie) throws RecordNotFoundException,
	    SecurityException {

	locker.checkRecordOwner(recNo, lockCookie);

	try {
	    handler.writeRecord(Arrays.asList(data), recNo);
	} catch (final IOException e) {
	    throw new RecordNotFoundException("", e);
	}
    }

    @Override
    public void delete(final int recNo, final long lockCookie)
	    throws RecordNotFoundException, SecurityException {

	locker.checkRecordOwner(recNo, lockCookie);

	try {
	    handler.deleteRecord(recNo);
	} catch (final IOException e) {
	    e.printStackTrace();
	    throw new RecordNotFoundException("", e);
	}
    }

    @Override
    public int[] find(final String[] criteria) {

	Set<Record> records = null;
	try {
	    records = handler.findMatchingRecords(new NullAlwaysMatches(Arrays
		    .asList(criteria)));
	} catch (final IOException e) {
	    // transforms the checked IO exception in a runtime exception
	    // to fulfill the interface
	    throw new DatabaseException(e);
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

	synchronized (handler) {
	    try {
		if (find(data).length != 0) {
		    throw new DuplicateKeyException();
		}
		final int emptyIndex = handler.findEmptyIndex();
		handler.writeRecord(Arrays.asList(data), emptyIndex);
		return emptyIndex;
	    } catch (final IOException e) {
		// transforms the checked IO exception in a runtime exception
		// to fulfill the interface
		throw new DatabaseException(e);
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
