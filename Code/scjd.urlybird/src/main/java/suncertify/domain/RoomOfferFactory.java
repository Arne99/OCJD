package suncertify.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import suncertify.common.Money;
import suncertify.common.RoomOffer;
import suncertify.db.DB;

/**
 * Creates and validates new {@link UrlyBirdRoomOffer}s from a list of
 * <code>Strings</code>. Before every creation the given values are validated.
 * For every invalid parameter an {@link ConstraintViolationException} is
 * thrown.
 * 
 * @author arnelandwehr
 * 
 */
class RoomOfferFactory {

    /**
     * An enum of all attributes a {@link RoomOffer} has. Every enum member
     * contains the required position of the attribute in the parameter list (
     * {@link RoomOfferFactory#createRoomOffer(List, int)}) and an regular
     * expression for the validation checks (
     * {@link RoomOfferFactory#checkValues(List)}).
     * 
     * @author arnelandwehr
     * 
     */
    enum ROOM_OFFER_ATTRIBUTE {

	/**
	 * the {@link RoomOffer} attribute "hotel".
	 */
	HOTEL(0, ".{1,64}"),

	/**
	 * the {@link RoomOffer} attribute "city".
	 */
	CITY(1, ".{1,64}"),

	/**
	 * the {@link RoomOffer} attribute "size".
	 */
	SIZE(2, "\\d{1,4}"),

	/**
	 * the {@link RoomOffer} attribute "isSmokingAllowed".
	 */
	SMOKING(3, "[YNyn]"),

	/**
	 * the {@link RoomOffer} attribute "Price".
	 */
	PRICE(4, ".\\d{2,4}\\.\\d{2}"),

	/**
	 * the {@link RoomOffer} attribute "bookableDate".
	 */
	DATE(5, "\\d{4}/\\d{2}/\\d{2}"),

	/**
	 * the {@link RoomOffer} attribute "customerID".
	 */
	CUSTOMER(6, ".?|\\d{8}");

	/** the required position of the attribute in a list of attributes. */
	private final int index;

	/** the required format of the attribute. */
	private final String allowedFormat;

	/**
	 * Private enum constructor, cannot be used.
	 * 
	 * @param index
	 *            the required position of the attribute in a list of
	 *            attributes.
	 * @param allowedFormat
	 *            the required format of the attribute.
	 */
	private ROOM_OFFER_ATTRIBUTE(final int index, final String allowedFormat) {
	    this.index = index;
	    this.allowedFormat = allowedFormat;
	}

    }

    /**
     * the required date format.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
	    "yyyy/MM/dd", Locale.US);

    /**
     * Creates a new {@link UrlyBirdRoomOffer} with given values as attributes
     * and the given index as unique identifier.
     * 
     * @param values
     *            every values must represent an required attribute of an
     *            {@link UrlyBirdRoomOffer} in the right order. The required
     *            attributes, their order and their format could be obtained by
     *            the enum {@link ROOM_OFFER_ATTRIBUTE}. If the given values are
     *            a valid list of attributes could be checked by
     *            {@link #checkValues(List)}.
     * @param index
     *            the unique identifier of the {@link UrlyBirdRoomOffer}. A
     *            {@link RoomOffer} with an duplicate identifier counld not be
     *            stored in the database {@link DB#create(String[])}
     * @return the new created {@link UrlyBirdRoomOffer}, never
     *         <code>null</code>.
     * @throws ConstraintViolationException
     *             if not any {@link ROOM_OFFER_ATTRIBUTE} is represented by an
     *             valid given value.
     */
    UrlyBirdRoomOffer createRoomOffer(final List<String> values, final int index)
	    throws ConstraintViolationException {

	checkValues(values);

	final String hotelName = values.get(ROOM_OFFER_ATTRIBUTE.HOTEL.index);
	final String cityName = values.get(ROOM_OFFER_ATTRIBUTE.CITY.index);
	final int roomSize = convertStringToInteger(values
		.get(ROOM_OFFER_ATTRIBUTE.SIZE.index));
	final boolean smokingAllowed = convertStringToBoolean(values
		.get(ROOM_OFFER_ATTRIBUTE.SMOKING.index));
	final Money price = convertStringToMoney(values
		.get(ROOM_OFFER_ATTRIBUTE.PRICE.index));
	final Date bookableDate = convertStringToDate(values
		.get(ROOM_OFFER_ATTRIBUTE.DATE.index));
	final String customerId = values
		.get(ROOM_OFFER_ATTRIBUTE.CUSTOMER.index);

	return new UrlyBirdRoomOffer(hotelName, cityName, roomSize,
		smokingAllowed, price, bookableDate, customerId, index);
    }

    /**
     * Creates an copy of the given {@link RoomOffer} an replaces the old
     * cutomerId with the new given one.
     * 
     * @param oldRoom
     *            the <code>RoomOffer</code> to copy, must not be
     *            <code>null</code>.
     * @param customerId
     *            the id of the customer the <code>RoomOffer</code> to create
     *            should be booked by.
     * @return an exact copy of the given room, except the replaced customer ID.
     * @throws ConstraintViolationException
     *             if the given customer id is not a valid customer id (
     *             {@link ROOM_OFFER_ATTRIBUTE#CUSTOMER}).
     */
    UrlyBirdRoomOffer copyRoomOfferWithNewCustomer(final RoomOffer oldRoom,
	    final String customerId) throws ConstraintViolationException {

	checkValue(ROOM_OFFER_ATTRIBUTE.CUSTOMER, customerId);

	return new UrlyBirdRoomOffer(oldRoom.getHotel(), oldRoom.getCity(),
		oldRoom.getRoomSize(), oldRoom.isSmokingAllowed(),
		oldRoom.getPrice(), oldRoom.getBookableDate(), customerId,
		oldRoom.getIndex());
    }

    /**
     * Checks if the given list of values represents an valid list of
     * {@link UrlyBirdRoomOffer} attributes. This is the case if:
     * <ul>
     * <li>the size of the values list is equal to the list of needed attributes
     * stored in {@link ROOM_OFFER_ATTRIBUTE}</li>
     * <li>the value at the position n fulfills the format of the
     * {@link ROOM_OFFER_ATTRIBUTE} with index n</li>
     * </ul>
     * 
     * @param values
     *            the list of values to check.
     * @throws ConstraintViolationException
     *             if not any {@link ROOM_OFFER_ATTRIBUTE} is represented by an
     *             valid given value.
     */
    void checkValues(final List<String> values)
	    throws ConstraintViolationException {

	if (values.size() < ROOM_OFFER_ATTRIBUTE.values().length) {
	    throw new ConstraintViolationException("ERROR");
	}

	for (final ROOM_OFFER_ATTRIBUTE attribute : ROOM_OFFER_ATTRIBUTE
		.values()) {
	    final String value = values.get(attribute.index);
	    checkValue(attribute, value);
	}
    };

    /**
     * Checks if the given value is valid for the given
     * {@link ROOM_OFFER_ATTRIBUTE}.
     * 
     * @param attribute
     *            the {@link UrlyBirdRoomOffer} attribute to check against.
     * @param value
     *            the value to check.
     * @throws ConstraintViolationException
     *             if the given value is not valid for the given attribute.
     */
    private void checkValue(final ROOM_OFFER_ATTRIBUTE attribute,
	    final String value) throws ConstraintViolationException {
	if (value == null || !value.matches(attribute.allowedFormat)) {
	    throw new ConstraintViolationException("the value: '" + value
		    + "' for the attribute '" + attribute
		    + "' is not of the specified format: '"
		    + attribute.allowedFormat + "' ");
	}

    }

    /**
     * Extracts the value that represents the bookable date value of an
     * {@link RoomOffer} from the given list and transforms it to an
     * {@link Date}.
     * 
     * @param values
     *            the values that represents all attributes of an
     *            <code>RoomOffer</code>, must not be <code>null</code>.
     * @return the bookable date.
     * @throws ConstraintViolationException
     *             if not any {@link ROOM_OFFER_ATTRIBUTE} is represented by an
     *             valid given value.
     */
    Date getBookableDateFromValues(final List<String> values)
	    throws ConstraintViolationException {

	checkValues(values);
	final String date = values.get(ROOM_OFFER_ATTRIBUTE.DATE.index);
	return convertStringToDate(date);
    }

    /**
     * Converts an <code>String</code> (must be "Y" or "N") to an
     * <code>boolean</code>.
     * 
     * @param string
     *            the <code>String</code> to convert, must be "Y" or "N" or an
     *            {@link IllegalArgumentException} is thrown.
     * @return true if the value of the <code>String</code> is "Y".
     */
    private boolean convertStringToBoolean(final String string) {

	if (string.equals("Y")) {
	    return true;
	}
	if (string.equals("N")) {
	    return false;
	}
	throw new IllegalArgumentException(
		" The String that should represent an boolean must have the value \"Y\" or \"N\", given value is: "
			+ string);

    }

    /**
     * Converts an <code>String</code> to an {@link Date}.
     * 
     * @param string
     *            the <code>String</code> to convert, must be of the form
     *            {@link #DATE_FORMAT}.
     * @return the created <code>Date</code>.
     */
    private Date convertStringToDate(final String string) {

	if (string == null) {
	    return null;
	}

	try {
	    return DATE_FORMAT.parse(string);
	} catch (final ParseException e) {
	    throw new IllegalArgumentException(e);
	}
    }

    /**
     * Converts the given <code>String</code> to an <code>Integer</code>.
     * 
     * @param string
     *            the <code>String</code> to convert or <code>null</code>.
     * @return an Integer or <code>null</code> if the given <code>String</code>
     *         is null.
     */
    private Integer convertStringToInteger(final String string) {

	if (string == null) {
	    return null;
	}

	return Integer.valueOf(string);
    }

    /**
     * Converts the given <code>String</code> to a {@link Money} object.
     * 
     * @param string
     *            the <code>String</code> to convert, must be of the form
     *            xxxx,xx$.
     * @return the created {@link Money} object.
     */
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
