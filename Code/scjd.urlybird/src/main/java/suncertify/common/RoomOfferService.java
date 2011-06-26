package suncertify.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

/**
 * Interface between the client and the server for all business methods that
 * belongs to {@link RoomOffer}s. The implementation of this interface is on the
 * server side and could be queried for by the client with the
 * {@link ServiceProvider} interface.
 * 
 * Every method in this interface takes an specified {@link Command} object as
 * input parameter. These <code>Commands</code> encapsulates the required
 * business information that the client must provide the server with. So the
 * interface is stable even if the needed business parameters change.
 * 
 * 
 * @author arnelandwehr
 * 
 */
public interface RoomOfferService extends Remote, Serializable {

    /**
     * Creates a new {@link RoomOffer} and stores it in the database.
     * 
     * @param command
     *            the required business informations, must not be
     *            <code>null</code>.
     * @return the new created and stored <code>RoomOffer</code>.
     * @throws Exception
     *             if an exception occurs during the creating or storing of the
     *             new <code>RoomOffer</code>. This exception informs the client
     *             and could be caught and processed there.
     */
    RoomOffer createRoomOffer(CreateRoomCommand command) throws Exception;

    /**
     * Books a {@link RoomOffer} for an specified customer and stores the change
     * in the database.
     * 
     * @param command
     *            the required business informations, must not be
     *            <code>null</code>.
     * @return the now booked <code>RoomOffer</code>.
     * @throws Exception
     *             if an exception occurs during the booking or storing of the
     *             <code>RoomOffer</code>. This exception informs the client and
     *             could be caught and processed there.
     */
    RoomOffer bookRoomOffer(BookRoomCommand command) throws Exception;

    /**
     * Deletes a {@link RoomOffer} and removes it from the database.
     * 
     * @param command
     *            the required business informations, must not be
     *            <code>null</code>.
     * @return the unique identifier of the deleted <code>RoomOffer</code>.
     * @throws Exception
     *             if an exception occurs during the deleting of the
     *             <code>RoomOffer</code>. This exception informs the client and
     *             could be caught and processed there.
     */
    int deleteRoomOffer(DeleteRoomCommand command) throws Exception;

    /**
     * Updates the attributes of a {@link RoomOffer} and stores the changes in
     * the database.
     * 
     * @param command
     *            the required business informations, must not be
     *            <code>null</code>.
     * @return the updated <code>RoomOffer</code>, never <code>null</code>.
     * @throws Exception
     *             if an exception occurs during the updating or storing of the
     *             <code>RoomOffer</code>. This exception informs the client and
     *             could be caught and processed there.
     */
    RoomOffer updateRoomOffer(UpdateRoomCommand command) throws Exception;

    /**
     * Queries for all {@link RoomOffer}s that matches the in the given command
     * specified criteria.
     * 
     * @param command
     *            the required query informations, must not be <code>null</code>
     *            .
     * @return a list of all <code>RoomOffers</code> that matches the given
     *         criteria.
     * @throws Exception
     *             if an exception occurs during the finding of the
     *             <code>RoomOffers</code>. This exception informs the client
     *             and could be caught and processed there.
     */
    List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception;
}
