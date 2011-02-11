package suncertify.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import suncertify.common.Money;
import suncertify.common.roomoffer.RoomOffer;
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
    public int create(final RoomOffer roomOffer) throws DuplicateKeyException {
	return database.create(roomOffer.toArray());
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
	final RoomOffer roomOffer = builder.newRoomOffer().fromHotel(values[0])
		.fromCity(values[1]).ofSize(values[2])
		.smokingAllowed(values[3]).withPrice(values[4])
		.bookableAt(values[5]).bookedBy(values[6]).build();

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
