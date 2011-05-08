package suncertify.db;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link RecordLocker} implementation for an multi-threaded environment which
 * is synchronized internally.
 * 
 * Singleton because there should be just one locker to guarantee an secure
 * environment.
 * 
 * @author arnelandwehr
 * 
 */
class SynchronizedRecordLocker implements RecordLocker {

    /** the singleton instance. */
    private static final SynchronizedRecordLocker INSTANCE = new SynchronizedRecordLocker(
	    new HashMap<Integer, Long>());

    /**
     * Getter for the singleton instance of the <code>RecordLocker</code>.
     * 
     * @return the singleton instance.
     */
    public static SynchronizedRecordLocker instance() {
	return INSTANCE;
    }

    /**
     * the {@link Map} with the locks the locker holds. Keys are the
     * {@link Record} indices and values are the threadIds (
     * {@link Thread#getId()})
     */
    private final Map<Integer, Long> lockTable;

    /** the mutes that provides synchronized access to the {@link #lockTable}. */
    private final String mutex = new String("MUTEX");

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
	    final long newOwnerId = Thread.currentThread().getId();
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

    /**
     * Checks if the given index is already locked by an other threadID than the
     * given.
     * 
     * @param index
     *            the index to check.
     * @param newOwnerId
     *            the threadId to check for.
     * @return <code>true</code> if the index is already locked by an other
     *         threadId than the given one.
     */
    private boolean isRecordLockedByAnOtherThread(final int index,
	    final long newOwnerId) {

	synchronized (mutex) {
	    return lockTable.containsKey(index)
		    && lockTable.get(index) != newOwnerId;
	}
    }
}
