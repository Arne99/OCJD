package suncertify.common.roomoffer;

import java.util.Date;

import suncertify.common.Money;
import suncertify.common.PresentationObject;

public final class RoomOffer implements PresentationObject {

    private static final long serialVersionUID = 6482035761421384445L;

    private final String hotel;

    private final String city;

    private final int roomSize;

    private final boolean smokingAllowed;

    private final Money price;

    private final Date bookableDate;

    private final String customerId;

    private final int index;

    public String getHotel() {
	return hotel;
    }

    public String getCity() {
	return city;
    }

    public int getRoomSize() {
	return roomSize;
    }

    public boolean isSmokingAllowed() {
	return smokingAllowed;
    }

    public Money getPrice() {
	return price;
    }

    public Date getBookableDate() {
	return bookableDate;
    }

    public String getCustomerId() {
	return customerId;
    }

    public int getIndex() {
	return index;
    }

    public RoomOffer(final String hotel, final String city, final int roomSize,
	    final boolean smokingAllowed, final Money price,
	    final Date bookableDate, final String customerId, final int index) {
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
	if (!(object instanceof RoomOffer)) {
	    return false;
	}
	final RoomOffer roomOffer = (RoomOffer) object;
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
