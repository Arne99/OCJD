package suncertify.db;

/**
 * 
 * A <code>RecordLocker</code> could reserve (lock) single {@link Record} for
 * modifications to a specific thread. The <code>Record</code> could still be
 * read by everyone else but could just be modified by the owner thread.
 * 
 * @author arnelandwehr
 * 
 */
public interface RecordLocker {

    /**
     * Locks the <code>Record</code> with the given index for the current
     * thread. If the index is not already stored in the database no exception
     * is thrown.
     * 
     * @param index
     *            the index of the <code>Record</code> to lock.
     * @return the lock identifier, must be stored and used later to unlock the
     *         <code>Record</code>.
     * 
     * @see RecordLocker#unlockRecord(int, long)
     */
    long lockRecord(final int index);

    /**
     * Unlocks the <code>Record</code> for the given index if the given lockId
     * is the lockId the <code>Record</code> was locked with.
     * 
     * @param index
     *            the index of the <code>Record</code> to unlock.
     * @param lockId
     *            the id the <code>Record</code> was locked with.
     * @throws SecurityException
     *             if the given lockId does not match the lockId the
     *             <code>Record</code> was locked with.
     */
    void unlockRecord(final int index, long lockId) throws SecurityException;

    /**
     * Checks if the {@link Record} with the given index is already locked by
     * the given lockId and throws an {@link SecurityException} if this is not
     * the case.
     * 
     * @param index
     *            the index of the <code>Record</code> to check.
     * @param lockId
     *            the lockId the <code>Record</code> should be checked for.
     * @throws SecurityException
     *             if the <code>Record</code> is locked with an other lockId.
     */
    void checkRecordOwner(int index, long lockId) throws SecurityException;

}
