package suncertify.domain;

import java.util.List;
import java.util.Map;

import suncertify.common.roomoffer.RoomOffer;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public interface Dao<T> {

    int create(T t) throws DuplicateKeyException;

    void delete(int index, long lock) throws RecordNotFoundException,
	    SecurityException;

    List<T> find(Map<String, String> criteria);

    long lock(int index) throws RecordNotFoundException;

    RoomOffer read(int index) throws RecordNotFoundException;

    void unlock(int index, long lock) throws RecordNotFoundException,
	    SecurityException;

    void update(final RoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException;

}
