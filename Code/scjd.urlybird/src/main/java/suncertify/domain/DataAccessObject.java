package suncertify.domain;

import java.util.List;
import suncertify.db.DuplicateKeyException;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

/**
 * A <code>DataAccessObject</code> (DAO) is the bridge between the domain layer
 * and the persistence layer. A DAO could load domain objects of a specified
 * type from the database an store it finally there.
 * 
 * @author arnelandwehr
 * 
 * @param <T>
 *            the type of the domain object this dao can handle.
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
     *            the lockId
     * @throws RecordNotFoundException
     * @throws suncertify.db.SecurityException
     */
    void delete(int index, long lock) throws RecordNotFoundException,
	    suncertify.db.SecurityException;

    List<T> find(List<String> criteria) throws RecordNotFoundException,
	    ConstraintViolationException;

    long lock(int index) throws RecordNotFoundException;

    T read(int index) throws RecordNotFoundException,
	    ConstraintViolationException;

    void unlock(int index, long lock) throws RecordNotFoundException,
	    suncertify.db.SecurityException;

    void update(final T toUpdate, final long lock)
	    throws RecordNotFoundException, suncertify.db.SecurityException;

}
