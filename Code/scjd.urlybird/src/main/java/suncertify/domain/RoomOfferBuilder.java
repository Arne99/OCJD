package suncertify.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import suncertify.common.Money;
import suncertify.common.RoomOffer;

class RoomOfferBuilder {

    enum PERSISTED_ATTRIBUTE {

	HOTEL(0, "[\\w\\s]{1,64}"),
	CITY(1, "\\w{1,64}"),
	SIZE(2, "\\d{1,4}"),
	SMOKING(3, "[YNyn]"),
	PRICE(4, ".\\d{3,4}\\.\\d{2}"),
	DATE(5, "\\d{4}/\\d{2}/\\d{2}"),
	CUSTOMER(6, ".?|\\d{8}");

	private int index;
	private String allowedFormat;

	private PERSISTED_ATTRIBUTE(final int index, final String allowedFormat) {
	    this.index = index;
	    this.allowedFormat = allowedFormat;
	}

    }

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat(
	    "yyyy/MM/dd", Locale.US);

    RoomOfferBuilder() {
	super();
    }

    UrlyBirdRoomOffer createRoomOffer(final List<String> values, final int index)
	    throws ConstraintViolationException {

	checkValues(values);

	final String hotelName = values.get(PERSISTED_ATTRIBUTE.HOTEL.index);
	final String cityName = values.get(PERSISTED_ATTRIBUTE.CITY.index);
	final int roomSize = convertStringToInteger(values
		.get(PERSISTED_ATTRIBUTE.SIZE.index));
	final boolean smokingAllowed = convertStringToBoolean(values
		.get(PERSISTED_ATTRIBUTE.SMOKING.index));
	final Money price = convertStringToMoney(values
		.get(PERSISTED_ATTRIBUTE.PRICE.index));
	final Date bookableDate = convertStringToDate(values
		.get(PERSISTED_ATTRIBUTE.DATE.index));
	final String customerId = values
		.get(PERSISTED_ATTRIBUTE.CUSTOMER.index);

	return new UrlyBirdRoomOffer(hotelName, cityName, roomSize, smokingAllowed,
		price, bookableDate, customerId, index);
    }

    UrlyBirdRoomOffer createRoomOfferWithNewCustomer(final RoomOffer oldRoom,
	    final String customerId) throws ConstraintViolationException {

	checkValue(PERSISTED_ATTRIBUTE.CUSTOMER, customerId);

	return new UrlyBirdRoomOffer(oldRoom.getHotel(), oldRoom.getCity(),
		oldRoom.getRoomSize(), oldRoom.isSmokingAllowed(),
		oldRoom.getPrice(), oldRoom.getBookableDate(), customerId,
		oldRoom.getIndex());
    }

    void checkValues(final List<String> values)
	    throws ConstraintViolationException {

	if (values.size() < PERSISTED_ATTRIBUTE.values().length) {
	    throw new ConstraintViolationException("ERROR");
	}

	for (final PERSISTED_ATTRIBUTE attribute : PERSISTED_ATTRIBUTE.values()) {
	    final String value = values.get(attribute.index);
	    checkValue(attribute, value);
	}
    };

    private void checkValue(final PERSISTED_ATTRIBUTE attribute,
	    final String value) throws ConstraintViolationException {
	if (value == null || !value.matches(attribute.allowedFormat)) {
	    throw new ConstraintViolationException("the value: '" + value
		    + "' for the attribute '" + attribute
		    + "' is not of the specified format: '"
		    + attribute.allowedFormat + "' ");
	}

    }

    Date getDateFromValues(final List<String> values)
	    throws ConstraintViolationException {

	checkValues(values);
	final String date = values.get(PERSISTED_ATTRIBUTE.DATE.index);
	return convertStringToDate(date);
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
	final int length = currency.getSymbol(Locale.US).length();
	final String amount = string.substring(length, string.length());
	return Money.create(amount, currency);
    }

}
