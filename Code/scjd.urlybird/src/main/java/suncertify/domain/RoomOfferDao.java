package suncertify.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import suncertify.common.Money;
import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

final class RoomOfferDao implements DataAccessObject<UrlyBirdRoomOffer> {

    private final DB database;

    private final RoomOfferBuilder builder;

    RoomOfferDao(final DB database, final RoomOfferBuilder builder) {
	this.database = database;
	this.builder = builder;
    }

    @Override
    public UrlyBirdRoomOffer create(final List<String> values)
	    throws DuplicateKeyException, ConstraintViolationException {

	builder.checkValues(values);
	final int index = database.create(values.toArray(new String[values
		.size()]));
	return builder.createRoomOffer(values, index);
    }

    @Override
    public void delete(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.delete(index, lock);
    }

    @Override
    public List<UrlyBirdRoomOffer> find(final List<String> criteria)
	    throws RecordNotFoundException, ConstraintViolationException {
	final int[] indices = database.find(criteria
		.toArray(new String[criteria.size()]));

	final List<UrlyBirdRoomOffer> matchingRooms = new ArrayList<UrlyBirdRoomOffer>(
		indices.length);
	for (final int index : indices) {
	    final UrlyBirdRoomOffer roomOffer = read(index);
	    matchingRooms.add(roomOffer);
	}

	return matchingRooms;
    }

    @Override
    public long lock(final int index) throws RecordNotFoundException {
	return database.lock(index);
    }

    @Override
    public UrlyBirdRoomOffer read(final int index)
	    throws RecordNotFoundException, ConstraintViolationException {
	final String[] values = database.read(index);
	final UrlyBirdRoomOffer roomOffer = builder.createRoomOffer(
		Arrays.asList(values), index);

	return roomOffer;
    }

    @Override
    public void unlock(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.unlock(index, lock);
    }

    @Override
    public void update(final UrlyBirdRoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException {

	database.update(roomOffer.getIndex(), toPersistableArray(roomOffer),
		lock);
    }

    private String[] toPersistableArray(final UrlyBirdRoomOffer roomOffer) {
	final String roomSize = convertIntToPersistableString(roomOffer
		.getRoomSize());
	final String smokingAllowed = convertBooleanToPersistableString(roomOffer
		.isSmokingAllowed());
	final String price = convertMoneyToPersistableString(roomOffer
		.getPrice());
	final String date = convertDateToPersistableString(roomOffer
		.getBookableDate());

	return new String[] { roomOffer.getHotel(), roomOffer.getCity(),
		roomSize, smokingAllowed, price, date,
		roomOffer.getCustomerId() };
    }

    private String convertBooleanToPersistableString(
	    final boolean smokingAllowed) {
	return (smokingAllowed) ? "Y" : "N";
    }

    private String convertDateToPersistableString(final Date bookableDate) {

	return new SimpleDateFormat("yyyy/MM/dd", Locale.US)
		.format(bookableDate);
    }

    private String convertIntToPersistableString(final int roomSize) {
	return "" + roomSize;
    }

    private String convertMoneyToPersistableString(final Money price) {
	final String symbol = price.getCurreny().getSymbol(Locale.US);
	final BigDecimal amount = price.getAmount();
	final String value = amount.setScale(2).toPlainString();
	return symbol + value;
    }

}
