package suncertify.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",
	    Locale.US);

    public RoomOfferDao(final DB database) {
	this.database = database;
    }

    @Override
    public int create(final RoomOffer roomOffer) throws DuplicateKeyException {
	final String[] values = convertRoomOfferToStringArray(roomOffer);
	return database.create(values);
    }

    @Override
    public void delete(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.delete(index, lock);
    }

    @Override
    public List<RoomOffer> find(final List<String> criteria) {
	database.find(criteria.toArray(new String[] {}));
	return null;
    }

    @Override
    public long lock(final int index) throws RecordNotFoundException {
	return database.lock(index);
    }

    @Override
    public RoomOffer read(final int index) throws RecordNotFoundException {
	final String[] values = database.read(index);
	final String hotel = values[0];
	final String city = values[1];
	final Integer size = convertStringToInteger(values[2]);
	final Boolean smokingAllowed = convertStringToBoolean(values[3]);
	final Money price = convertStringToMoney(values[4]);
	final Date date = convertStringToDate(values[5]);
	final String customerId = values[6];
	final Integer roomIndex = convertStringToInteger(values[7]);

	return new RoomOffer(hotel, city, size, smokingAllowed, price, date,
		customerId, roomIndex);
    }

    @Override
    public void unlock(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.unlock(index, lock);
    }

    @Override
    public void update(final RoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException {

	final String[] values = convertRoomOfferToStringArray(roomOffer);
	database.update(roomOffer.getIndex(), values, lock);
    }

    private String convertBooleanToPersistableString(
	    final boolean smokingAllowed) {
	return (smokingAllowed) ? "Y" : "N";
    }

    private String convertDateToPersistableString(final Date bookableDate) {

	return dateFormat.format(bookableDate);
    }

    private String convertIntToPersistableString(final int roomSize) {
	return "" + roomSize;
    }

    private String convertMoneyToPersistableString(final Money price) {
	final String symbol = price.getCurreny().getSymbol();
	final BigDecimal amount = price.getAmount();
	final double doubleValue = amount.setScale(2).doubleValue();
	return symbol + doubleValue;
    }

    private String[] convertRoomOfferToStringArray(final RoomOffer roomOffer) {

	final String hotel = roomOffer.getHotel();
	final String city = roomOffer.getCity();
	final String roomSize = convertIntToPersistableString(roomOffer
		.getRoomSize());
	final String smokingAllowed = convertBooleanToPersistableString(roomOffer
		.isSmokingAllowed());
	final String price = convertMoneyToPersistableString(roomOffer
		.getPrice());
	final String date = convertDateToPersistableString(roomOffer
		.getBookableDate());
	final String customerId = roomOffer.getCustomerId();

	return new String[] { hotel, city, roomSize, smokingAllowed, price,
		date, customerId };
    }

    private Boolean convertStringToBoolean(final String string) {

	if (string == null) {
	    return null;
	}

	if (string.equals("Y")) {
	    return true;
	}
	if (string.equals("N")) {
	    return false;
	}
	throw new IllegalArgumentException();

    }

    private Date convertStringToDate(final String string) {

	if (string == null) {
	    return null;
	}

	try {
	    return dateFormat.parse(string);
	} catch (final ParseException e) {
	    throw new IllegalArgumentException(e);
	}
    }

    private Integer convertStringToInteger(final String string) {

	if (string == null) {
	    return null;
	}

	return Integer.valueOf(string);
    }

    private Money convertStringToMoney(final String string) {

	if (string == null) {
	    return null;
	}

	final Currency currency = Currency.getInstance(Locale.US);
	return Money.create(string, currency);
    }

}
