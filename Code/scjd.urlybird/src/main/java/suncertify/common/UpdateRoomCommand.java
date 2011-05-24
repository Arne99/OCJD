package suncertify.common;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>UpdateRoomCommand</code> is a message to the server that an client
 * want to update an existing {@link RoomOffer} an store the changes. It
 * encapsulates the there for required informations, like the new attribute
 * values of the <code>RoomOffer</code> and the <code>RoomOffer</code> to
 * update.
 * 
 * @author arnelandwehr
 * 
 */
public final class UpdateRoomCommand implements Command {

    /**
     * The SUID.
     */
    private static final long serialVersionUID = 1367931798192867975L;

    /**
     * the list of new attribute values.
     */
    private final List<String> newValues;

    /**
     * the <code>RoomOffer</code> to update.
     */
    private final RoomOffer roomToUpdate;

    /**
     * Constructs a new <code>UpdateRoomCommand</code>.
     * 
     * @param newValues
     *            the new values, must not be <code>null</code>
     * @param roomToUpdate
     *            the <code>RoomOffer</code> to update.
     */
    public UpdateRoomCommand(final List<String> newValues,
	    final RoomOffer roomToUpdate) {
	super();
	this.newValues = new ArrayList<String>(newValues);
	this.roomToUpdate = roomToUpdate;
    }

    /**
     * Getter for the list of the new attribute values.
     * 
     * @return the new attribute values.
     */
    public List<String> getNewValues() {
	return newValues;
    }

    /**
     * Getter for the <code>RoomOffer</code> to update.
     * 
     * @return the <code>RoomOffer</code> to update.
     */
    public RoomOffer getRoomToUpdate() {
	return roomToUpdate;
    }

}
