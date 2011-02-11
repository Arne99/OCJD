package suncertify.db;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Database implements DB {

    private final DatabaseHandler handler;

    private final RecordLocker locker;

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
	    return record.getAllBusinessValues().toArray(new String[] {});
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
