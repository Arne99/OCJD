package suncertify.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

public class RoomOfferDao implements Dao<RoomOffer> {

    private final DB database;

    private final RoomOfferBuilder builder;

    public RoomOfferDao(final DB database, final RoomOfferBuilder builder) {
	this.database = database;
	this.builder = builder;
    }

    @Override
    public RoomOffer create(final List<String> values)
	    throws DuplicateKeyException, ConstraintViolationException {

	builder.checkValues(values);
	final int index = database.create(values.toArray(new String[] {}));
	return builder.createRoomOffer(values, index);
    }

    @Override
    public void delete(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.delete(index, lock);
    }

    @Override
    public List<RoomOffer> find(final List<String> criteria)
	    throws RecordNotFoundException, ConstraintViolationException {
	final int[] indices = database.find(criteria.toArray(new String[] {}));

	final List<RoomOffer> matchingRooms = new ArrayList<RoomOffer>();
	for (final int index : indices) {
	    final RoomOffer roomOffer = read(index);
	    matchingRooms.add(roomOffer);
	}

	return matchingRooms;
    }

    @Override
    public long lock(final int index) throws RecordNotFoundException {
	return database.lock(index);
    }

    @Override
    public RoomOffer read(final int index) throws RecordNotFoundException,
	    ConstraintViolationException {
	final String[] values = database.read(index);
	final RoomOffer roomOffer = builder.createRoomOffer(
		Arrays.asList(values), index);

	return roomOffer;
    }

    @Override
    public void unlock(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.unlock(index, lock);
    }

    @Override
    public void update(final RoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException {

	database.update(roomOffer.getIndex(), roomOffer.toArray(), lock);
    }
}
