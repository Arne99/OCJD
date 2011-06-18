package suncertify.common;


/**
 * An <code>ServiceProvider</code> allows the client to retrieve an
 * {@link RoomOfferService}.
 * 
 * @author arnelandwehr
 */
public interface ServiceProvider {

    /**
     * Getter for a <code>RoomOfferService</code>.
     * 
     * @return a <code>RoomOfferService</code>, never null.
     */
    RoomOfferService getRoomOfferService();

}
