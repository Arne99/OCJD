package suncertify.db;

import java.util.HashMap;
import java.util.Map;

class SynchronizedRecordLocker implements RecordLocker {

    private static final SynchronizedRecordLocker INSTANCE = new SynchronizedRecordLocker(
	    new HashMap<Integer, Long>());

    public static SynchronizedRecordLocker instance() {
	return INSTANCE;
    }

    /**
     * For testing only! Never use in production code!
     * 
     * @param lockTable
     *            the {@link Map} with the already known locks.
     */
    SynchronizedRecordLocker(final Map<Integer, Long> lockTable) {
	super();
	this.lockTable = lockTable;
    }

    private final Map<Integer, Long> lockTable;

    private final String mutex = new String("MUTEX");

    @Override
    public void checkRecordOwner(final int index, final long id)
	    throws SecurityException {

	if (isRecordLockedByAnOtherThread(index, id)) {
	    throw new SecurityException();
	}
    }

    @Override
    public long lockRecord(final int index) {

	synchronized (mutex) {
	    final long newOwnerId = createOwnerIdForCurrentThread();
	    while (isRecordLockedByAnOtherThread(index, newOwnerId)) {
		try {
		    mutex.wait();
		} catch (final InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    lockTable.put(index, newOwnerId);
	    return newOwnerId;
	}
    }

    @Override
    public void unlockRecord(final int index, final long id)
	    throws SecurityException {

	synchronized (mutex) {
	    checkRecordOwner(index, id);
	    lockTable.remove(index);
	    mutex.notifyAll();
	}
    }

    private long createOwnerIdForCurrentThread() {
	return Thread.currentThread().getId();
    }

    private boolean isRecordLockedByAnOtherThread(final int index,
	    final long newOwnerId) {

	synchronized (mutex) {
	    return lockTable.containsKey(index)
		    && lockTable.get(index) != newOwnerId;
	}
    }
}
