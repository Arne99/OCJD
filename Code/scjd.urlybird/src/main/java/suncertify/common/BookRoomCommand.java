package suncertify.common;

/**
 * The <code>BookRoomCommand</code> is a message to the server that an client
 * want to book an specified room. It encapsulates the there for required
 * informations, like the customer id and the {@link RoomOffer} he wants to
 * book.
 * 
 * @author arnelandwehr
 * 
 */
public final class BookRoomCommand implements Command {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -225024283571144227L;

    /** the id of the customer who wants to book the rooom. */
    private final String customerId;

    /** the room the customer wants to book. */
    private final RoomOffer roomToBook;

    /**
     * Construct a new <code>BookRoomCommand</code>.
     * 
     * @param roomToBook
     *            the room to book.
     * @param customerId
     *            the id of the customer who wants to book the room.
     */
    public BookRoomCommand(final RoomOffer roomToBook, final String customerId) {
	super();
	this.roomToBook = roomToBook;
	this.customerId = customerId;
    }

    /**
     * Getter for the customer id.
     * 
     * @return the customer id.
     */
    public String getCustomerId() {
	return customerId;
    }

    /**
     * Getter for the room to book.
     * 
     * @return the room to book.
     */
    public RoomOffer getRoomToBook() {
	return roomToBook;
    }

}
