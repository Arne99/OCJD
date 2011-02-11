package suncertify.domain;

import java.util.List;
import java.util.Map;

import suncertify.common.roomoffer.RoomOffer;
import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class RoomOfferDao implements Dao<RoomOffer> {

    public RoomOfferDao(final DB database) {
	// TODO Auto-generated constructor stub
    }

    @Override
    public int create(final RoomOffer t) throws DuplicateKeyException {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void delete(final int index, final long lock) throws RecordNotFoundException,
	    SecurityException {
	// TODO Auto-generated method stub

    }

    @Override
    public List<RoomOffer> find(final Map<String, String> criteria) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long lock(final int index) throws RecordNotFoundException {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public RoomOffer read(final int index) throws RecordNotFoundException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void unlock(final int index, final long lock) throws RecordNotFoundException,
	    SecurityException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(final RoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException {
	// TODO Auto-generated method stub

    }

}
