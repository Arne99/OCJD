package suncertify.domain;

import java.util.List;
import java.util.Map;

import suncertify.common.RoomOffer;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public interface Dao<T> {

    T create(List<String> values) throws DuplicateKeyException,
	    ConstraintViolationException;

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
