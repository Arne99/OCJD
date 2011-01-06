package suncertify.db;

import java.util.HashMap;

class UrlyBirdRecordLocker implements RecordLocker {

    private static final UrlyBirdRecordLocker INSTANCE = new UrlyBirdRecordLocker();

    static UrlyBirdRecordLocker instance() {
	return INSTANCE;
    }

    HashMap<Integer, Long> lockTable = new HashMap<Integer, Long>();

    private final String mutex = new String("MUTEX");

    private UrlyBirdRecordLocker() {
	super();
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
