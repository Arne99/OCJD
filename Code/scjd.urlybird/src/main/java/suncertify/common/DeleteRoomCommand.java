package suncertify.common;

/**
 * The <code>DeleteRoomCommand</code> is a message to the server that an client
 * want to delete a specified {@link RoomOffer}. It encapsulates the there for
 * required informations, like the <code>RoomOffer</code> to delete.
 * 
 * 
 * @author arnelandwehr
 * 
 */
public final class DeleteRoomCommand implements Command {

    /**
     * the SUID.
     * 
     */
    private static final long serialVersionUID = -5326172801204501197L;

    /** the <code>RoomOffer</code> to delete. */
    private final RoomOffer roomOfferToDelete;

    /**
     * Constructs a new <code>DeleteRoomCommand</code>.
     * 
     * @param roomOfferToDelete
     *            the <code>RoomOffer</code> to delete, must not be
     *            <code>null</code>.
     */
    public DeleteRoomCommand(final RoomOffer roomOfferToDelete) {
	super();
	this.roomOfferToDelete = roomOfferToDelete;
    }

    /**
     * Getter for the <code>RoomOffer</code> to delete.
     * 
     * @return the <code>RoomOffer</code> to delete.
     */
    public RoomOffer getRoomOfferToDelete() {
	return roomOfferToDelete;
    }

}
