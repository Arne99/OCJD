package suncertify.domain;

import java.io.Serializable;
import java.util.Date;
import suncertify.common.Money;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;

/**
 * Server side representation of an {@link RoomOffer}. Immutable value class
 * that could be transfered to the client. Every <code>UrlyBirdRoomOffer</code>
 * could be created by the {@link RoomOfferFactory} and stored and loaded from
 * the database with an {@link RoomOfferDao}. The business logic to work with
 * <code>UrlyBirdRoomOffers</code> is located in the {@link RoomOfferService}.
 * 
 * 
 * @author arnelandwehr
 * 
 */
final class UrlyBirdRoomOffer implements Serializable, RoomOffer {

    /** the SUID. */
    private static final long serialVersionUID = 6482035761421384445L;

    /** the name of the hotel to this RoomOffer. */
    private final String hotel;

    /** the name of the city where this RoomOffer is located. */
    private final String city;

    /** the room size. */
    private final int roomSize;

    /** if smoking is allowed in this room. */
    private final boolean smokingAllowed;

    /** the price of this room. */
    private final Money price;

    /** the date at which the room is bookable. */
    private final Date bookableDate;

    /** the id of the customer who booked this room. */
    private final String customerId;

    /** the identifier of this {@link RoomOffer}. */
    private final int index;

    @Override
    public String getHotel() {
	return hotel;
    }

    @Override
    public String getCity() {
	return city;
    }

    @Override
    public int getRoomSize() {
	return roomSize;
    }

    @Override
    public boolean isSmokingAllowed() {
	return smokingAllowed;
    }

    @Override
    public Money getPrice() {
	return price;
    }

    @Override
    public Date getBookableDate() {
	return bookableDate;
    }

    @Override
    public String getCustomerId() {
	return customerId;
    }

    @Override
    public int getIndex() {
	return index;
    }

    /**
     * Constructs a new <code>UrlyBirdRoomOffer</code>.
     * 
     * @param hotel
     *            the name of the hotel, must not be <code>null</code>.
     * @param city
     *            the city of the hotel, must not be <code>null</code>.
     * @param roomSize
     *            the size of the room, must be positive.
     * @param smokingAllowed
     *            if smoking is allowed in this room.
     * @param price
     *            the price of this room, must not be <code>null</code>.
     * @param bookableDate
     *            the date at which this room is bookable, must not be
     *            <code>null</code>.
     * @param customerId
     *            the id of the customer who booked this room, could be
     *            <code>null</code>.
     * @param index
     *            the unique id of this <code>RoomOffer</code>.
     */
    UrlyBirdRoomOffer(final String hotel, final String city,
	    final int roomSize, final boolean smokingAllowed,
	    final Money price, final Date bookableDate,
	    final String customerId, final int index) {
	super();
	this.hotel = hotel;
	this.city = city;
	this.roomSize = roomSize;
	this.smokingAllowed = smokingAllowed;
	this.price = price;
	this.bookableDate = bookableDate;
	this.customerId = customerId;
	this.index = index;
    }

    @Override
    public boolean equals(final Object object) {
	if (this == object) {
	    return true;
	}
	if (!(object instanceof UrlyBirdRoomOffer)) {
	    return false;
	}
	final UrlyBirdRoomOffer roomOffer = (UrlyBirdRoomOffer) object;
	return roomOffer.hotel.equals(this.hotel)
		&& roomOffer.city.equals(this.city)
		&& roomOffer.roomSize == this.roomSize
		&& roomOffer.smokingAllowed == this.smokingAllowed
		&& roomOffer.price.equals(this.price)
		&& roomOffer.bookableDate.equals(this.bookableDate)
		&& roomOffer.customerId.equals(this.customerId);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + hotel.hashCode();
	result = 31 * result + city.hashCode();
	result = 31 * result + roomSize;
	result = 31 * result + ((smokingAllowed) ? 1 : 0);
	result = 31 * result + price.hashCode();
	result = 31 * result + bookableDate.hashCode();
	result = 31 * result + customerId.hashCode();
	result = 31 * result + index;
	return result;
    }

    @Override
    public String toString() {

	return getClass().getSimpleName() + " [ " + "hotel = " + hotel
		+ "; city = " + city + "; roomSize = " + roomSize
		+ "; smokingAllowed = " + smokingAllowed + "; price = " + price
		+ "; bookableDate = " + bookableDate + "; customerId = "
		+ customerId + "; index = " + index + " ] ";
    }

}
