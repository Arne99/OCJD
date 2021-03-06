package suncertify.db;

/**
 * A Database that stores data in indexed records with simple string values for
 * the different columns. Data could be read from the database and written to
 * it. Every write operations requires that the record must first be locked and
 * finally unlocked.
 * 
 */
public interface DB {

    /**
     * Reads a record from the database. Returns an array where each element is
     * a record value.
     * 
     * @param recNo
     *            the Number of the record to return, must be positive.
     * @return the record at the at the position {@code recNo} as an array of
     *         strings. Each element contains one record value.
     * @throws RecordNotFoundException
     *             if the underlying database doesn't contain any record at the
     *             position {@code recNo}.
     */
    String[] read(int recNo) throws RecordNotFoundException;

    /**
     * Modifies the fields of a record. The new value for field n appears in
     * data[n]. Throws SecurityException if the record is locked with a cookie
     * other than lockCookie.
     * 
     * @param recNo
     *            the Number of the record to modify, must be positive.
     * @param data
     *            the new values for the record to modify, must not be
     *            <code>null</code>.
     * @param lockCookie
     *            the cookie to lock the record.
     * @throws RecordNotFoundException
     *             if the underlying database doesn't contain any record at the
     *             position {@code recNo}.
     * @throws SecurityException
     *             if the record is locked with a cookie other than lockCookie.
     */
    void update(int recNo, String[] data, long lockCookie)
	    throws RecordNotFoundException, SecurityException;

    /**
     * Deletes a record, making the record number and associated disk storage
     * available for reuse. Throws SecurityException if the record is locked
     * with a cookie other than lockCookie.
     * 
     * @param recNo
     *            the Number of the record to delete, must be positive.
     * @param lockCookie
     *            the cookie to lock the record.
     * @throws RecordNotFoundException
     *             if the underlying database doesn't contain any record at the
     *             position {@code recNo}.
     * @throws SecurityException
     *             if the record is locked with a cookie other than lockCookie.
     */
    void delete(int recNo, long lockCookie) throws RecordNotFoundException,
	    SecurityException;

    /**
     * Returns an array of record numbers that match the specified criteria.
     * Field n in the database is described by criteria[n]. A null value in
     * criteria[n] matches any field value. A non-null value in criteria[n]
     * matches any field value that begins with criteria[n]. (For example,
     * "Fred" matches "Fred" or "Freddy".)
     * 
     * @param criteria
     *            the criteria to match, must not be <code>null</code>.
     * @return an int array of record number which matches the specified
     *         criteria, never <code>null</code>.
     * 
     */
    int[] find(String[] criteria);

    /**
     * Creates a new record in the database (possibly reusing a deleted entry).
     * Inserts the given data, and returns the record number of the new record.
     * 
     * @param data
     *            the data of the record to create, must not <code>null</code>.
     * @return the index of created record.
     * @throws DuplicateKeyException
     *             if the unique identifier of the record is already stored in
     *             the database.
     * 
     */
    int create(String[] data) throws DuplicateKeyException;

    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * Returned value is a cookie that must be used when the record is unlocked,
     * updated, or deleted. If the specified record is already locked by a
     * different client, the current thread gives up the CPU and consumes no CPU
     * cycles until the record is unlocked.
     * 
     * @param recNo
     *            the index of the record that is to lock.
     * @return the cookie.
     * @throws RecordNotFoundException
     *             if no record with the given index is stored in the database.
     */
    long lock(int recNo) throws RecordNotFoundException;

    /**
     * Releases the lock on a record. Cookie must be the cookie returned when
     * the record was locked; otherwise throws SecurityException.
     * 
     * @param recNo
     *            the index of the record that is to lock.
     * @param cookie
     *            the lock cookie.
     * @throws RecordNotFoundException
     *             if no record with the given index is stored in the database.
     * @throws SecurityException
     *             if the given cookie is not the returned cookie when the
     *             record was locked.
     */
    void unlock(int recNo, long cookie) throws RecordNotFoundException,
	    SecurityException;
}