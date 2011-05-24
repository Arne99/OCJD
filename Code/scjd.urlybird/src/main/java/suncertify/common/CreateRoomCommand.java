package suncertify.common;

import java.util.Collections;
import java.util.List;

/**
 * The <code>CreateRoomCommand</code> is a message to the server that an client
 * want to create a new {@link RoomOffer} an store it. It encapsulates the there
 * for required informations, like the attribute values of the
 * <code>RoomOffer</code> to create.
 * 
 * @author arnelandwehr
 * 
 */
public final class CreateRoomCommand implements Command {

    /**
     * the SUID.
     * 
     */
    private static final long serialVersionUID = 4786262039648065987L;

    /** the values for the attributes of the new {@link RoomOffer}. */
    private final List<String> values;

    /**
     * Constrcut a new <code>CreateRoomCommand</code>.
     * 
     * @param values
     *            the attribute values, must not be <code>null</code>.
     */
    public CreateRoomCommand(final List<String> values) {
	super();
	this.values = values;
    }

    /**
     * Getter for the attribute values.
     * 
     * @return the attribute values.
     */
    public List<String> getValues() {
	return Collections.unmodifiableList(values);
    }

}
