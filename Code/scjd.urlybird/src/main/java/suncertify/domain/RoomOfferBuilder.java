package suncertify.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import suncertify.common.Money;

class RoomOfferBuilder {

    RoomOfferBuilder() {
	super();
    }

    public DefaultBuilder copyOf(final RoomOffer roomOffer) {
	return new InnerBuilder(roomOffer, new RoomOfferValidator());
    }

    public DefaultBuilder newRoomOffer() {
	return new InnerBuilder(new RoomOfferValidator());
    }

    private static class InnerBuilder implements DefaultBuilder {

	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",
		Locale.US);

	private String hotel;

	private String city;

	private int roomSize;

	private boolean smokingAllowed;

	private Money price;

	private Date bookableDate;

	private String customerId;

	private int index;

	private final RoomOfferValidator validator;

	private InnerBuilder(final RoomOffer offerToCopy,
		final RoomOfferValidator validator) {

	    this.hotel = offerToCopy.getHotel();

	    this.city = offerToCopy.getCity();

	    this.roomSize = offerToCopy.getRoomSize();

	    this.smokingAllowed = offerToCopy.isSmokingAllowed();

	    this.price = offerToCopy.getPrice();

	    this.bookableDate = new Date(offerToCopy.getBookableDate()
		    .getTime());

	    this.customerId = offerToCopy.getCustomerId();

	    this.index = offerToCopy.getIndex();
	    this.validator = validator;
	}

	public InnerBuilder(final RoomOfferValidator roomOfferValidator) {
	    validator = roomOfferValidator;
	}

	@Override
	public DefaultBuilder fromHotel(final String hotel) {
	    this.hotel = hotel;
	    return this;
	}

	@Override
	public DefaultBuilder ofSize(final int size) {
	    this.roomSize = size;
	    return this;
	}

	@Override
	public DefaultBuilder fromCity(final String city) {
	    this.city = city;
	    return this;
	}

	@Override
	public DefaultBuilder smokingAllowed(final boolean isSmokingAllowed) {
	    this.smokingAllowed = isSmokingAllowed;
	    return this;
	}

	@Override
	public DefaultBuilder withPrice(final Money price) {
	    this.price = price;
	    return this;
	}

	@Override
	public DefaultBuilder bookableAt(final Date date) {
	    this.bookableDate = new Date(date.getTime());
	    return this;
	}

	@Override
	public DefaultBuilder bookedBy(final String customerId) {
	    this.customerId = customerId;
	    return this;
	}

	@Override
	public DefaultBuilder withIndex(final int index) {
	    this.index = index;
	    return this;
	}

	@Override
	public RoomOffer build() throws ConstraintViolationException {

	    final RoomOffer roomOffer = new RoomOffer(hotel, city, roomSize,
		    smokingAllowed, price, bookableDate, customerId, index);
	    validator.validate(roomOffer);

	    return roomOffer;
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

	@Override
	public DefaultBuilder ofSize(final String size) {
	    this.roomSize = convertStringToInteger(size);
	    return this;
	}

	@Override
	public DefaultBuilder smokingAllowed(final String isSmokingAllowed) {
	    this.smokingAllowed = convertStringToBoolean(isSmokingAllowed);
	    return this;
	}

	@Override
	public DefaultBuilder withPrice(final String price) {
	    this.price = convertStringToMoney(price);
	    return this;
	}

	@Override
	public DefaultBuilder bookableAt(final String date) {
	    this.bookableDate = convertStringToDate(date);
	    return this;
	}

	@Override
	public DefaultBuilder withIndex(final String index) {
	    this.index = convertStringToInteger(index);
	    return this;
	}

    }

    interface DefaultBuilder {

	DefaultBuilder fromHotel(final String hotel);

	DefaultBuilder ofSize(final int size);

	DefaultBuilder ofSize(final String size);

	DefaultBuilder smokingAllowed(boolean isSmokingAllowed);

	DefaultBuilder smokingAllowed(String isSmokingAllowed);

	DefaultBuilder fromCity(final String city);

	DefaultBuilder withPrice(final Money price);

	DefaultBuilder withPrice(final String price);

	DefaultBuilder bookableAt(final Date date);

	DefaultBuilder bookableAt(final String date);

	DefaultBuilder withIndex(final int index);

	DefaultBuilder withIndex(final String index);

	DefaultBuilder bookedBy(final String customerId);

	RoomOffer build() throws ConstraintViolationException;
    }
}
