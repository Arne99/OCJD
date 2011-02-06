package suncertify.domain;

import java.util.Date;

import suncertify.common.roomoffer.Money;
import suncertify.common.roomoffer.RoomOffer;

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

    }

    interface DefaultBuilder {

	DefaultBuilder fromHotel(final String hotel);

	DefaultBuilder ofSize(final int size);

	DefaultBuilder smokingAllowed(boolean isSmokingAllowed);

	DefaultBuilder fromCity(final String city);

	DefaultBuilder withPrice(final Money price);

	DefaultBuilder bookableAt(final Date date);

	DefaultBuilder withIndex(final int index);

	DefaultBuilder bookedBy(final String customerId);

	RoomOffer build() throws ConstraintViolationException;
    }
}
