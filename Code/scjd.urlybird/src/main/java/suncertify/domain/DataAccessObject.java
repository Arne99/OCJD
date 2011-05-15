package suncertify.domain;

import java.util.List;
import suncertify.db.DuplicateKeyException;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

/**
 * A <code>DataAccessObject</code> (DAO) is the bridge between the domain layer
 * and the persistence layer. A DAO could load domain objects of a specified
 * type from the database or store it.
 * 
 * @author arnelandwehr
 * 
 * @param <T>
 *            the type of the domain object this <code>DAO</code> can handle.
 */
public interface DataAccessObject<T> {

    /**
     * Creates a new domain object and stores it in the database.
     * 
     * @param values
     *            the values of the domain object, must not be <code>null</code>
     *            .
     * @return the created and stored domain object.
     * @throws DuplicateKeyException
     *             if the domain object is already stored in the database.
     * @throws ConstraintViolationException
     *             if the given values violates any attribute condition of the
     *             domain object to create.
     */
    T create(List<String> values) throws DuplicateKeyException,
	    ConstraintViolationException;

    /**
     * Deletes the {@link Record} with the given index from the database.
     * 
     * @param index
     *            the index of the <code>Record</code> to delete.
     * @param lock
     *            the lockId.
     * @throws RecordNotFoundException
     *             if there is not any stored <code>Record</code> in the
     *             database at the given index.
     * @throws suncertify.db.SecurityException
     *             the the stored <code>Record</code> is locked with a different
     *             lock key than the given one.
     */
    void delete(int index, long lock) throws RecordNotFoundException,
	    suncertify.db.SecurityException;

    /**
     * Returns a <code>List</code> of rdomain objects that match the specified
     * criteria. Field n in the database is described by criteria[n]. A null
     * value in criteria[n] matches any field value. A non-null value in
     * criteria[n] matches any field value that begins with criteria[n]. (For
     * example, "Fred" matches "Fred" or "Freddy".)
     * 
     * @param criteria
     *            the criteria to match, must not be <code>null</code>.
     * @return a <code>List</code> domain objects which matches the specified
     *         criteria, never <code>null</code>.
     * @throws RecordNotFoundException
     *             if there is not any stored <code>Record</code> in the
     *             database at the given index.
     * @throws ConstraintViolationException
     *             if the stored <code>Record</code> is no valid domain object.
     *             In this case the stored <code>Record</code> must be patched
     *             in the database.
     */
    List<T> find(List<String> criteria) throws RecordNotFoundException,
	    ConstraintViolationException;

    /**
     * 
     * @param index
     * @return
     * @throws RecordNotFoundException
     */
    long lock(int index) throws RecordNotFoundException;

    /**
     * 
     * @param index
     * @return
     * @throws RecordNotFoundException
     * @throws ConstraintViolationException
     */
    T read(int index) throws RecordNotFoundException,
	    ConstraintViolationException;

    /**
     * 
     * @param index
     * @param lock
     * @throws RecordNotFoundException
     * @throws suncertify.db.SecurityException
     */
    void unlock(int index, long lock) throws RecordNotFoundException,
	    suncertify.db.SecurityException;

    /**
     * 
     * @param toUpdate
     * @param lock
     * @throws RecordNotFoundException
     * @throws suncertify.db.SecurityException
     */
    void update(final T toUpdate, final long lock)
	    throws RecordNotFoundException, suncertify.db.SecurityException;

}
