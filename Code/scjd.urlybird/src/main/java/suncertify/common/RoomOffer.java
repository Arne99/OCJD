package suncertify.common;

import java.io.Serializable;
import java.util.Date;

/**
 * An RoomOffer represents an bookable room that is offered by the UrlyBird
 * company to their customers. Every room belongs to an specified hotel and is
 * only offered for an specified date for a fixed price.
 * 
 * If a RoomOffer is taken by an customer the room is booked and the customer id
 * is associated with this <code>RoomOffer</code>.
 * 
 * Every <code>RoomOffer</code> has a unique identifier the <i>index</i>.
 * 
 * @author arnelandwehr
 * 
 */
public interface RoomOffer extends Serializable {

    /**
     * Getter for the name of the hotel of this <code>RoomOffer</code>.
     * 
     * @return the name of the hotel, never <code>null</code>.
     */
    String getHotel();

    /**
     * Getter for the name of the city (location) the hotel of this
     * <code>RoomOffer</code> is located.
     * 
     * @return the name of the city, never <code>null</code>.
     */
    String getCity();

    /**
     * Getter of the size of the room.
     * 
     * @return the size of the room, must be positive.
     */
    int getRoomSize();

    /**
     * Indicates if it is a smoking or non smoking room.
     * 
     * @return <code>true</code> if smoking is allowed.
     */
    boolean isSmokingAllowed();

    /**
     * The price of the room at the bookable date.
     * 
     * @return the price, never <code>null</code>.
     */
    Money getPrice();

    /**
     * The date at which this room is bookable.
     * 
     * @return the bookable date, never <code>null</code>.
     */
    Date getBookableDate();

    /**
     * The id of the customer.
     * 
     * @return the customer id, never <code>null</code>.
     */
    String getCustomerId();

    /**
     * The unique identifier of this <code>RoomOffer</code>.
     * 
     * @return the unique identifier.
     */
    int getIndex();

}
