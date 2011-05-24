package suncertify.common;

/**
 * The <code>FindRoomCommand</code> is a message to the server that an client
 * wants to get all {@link RoomOffer}s that match an specified criteria. It
 * encapsulates the there for required informations, like the criteria
 * informations to match.
 * 
 * 
 * @author arnelandwehr
 * 
 */
public final class FindRoomCommand implements Command {

    /**
     * The SUID.
     * 
     */
    private static final long serialVersionUID = 6794005890219300147L;

    /** the name of the hotel the rooms must match. */
    private final String hotel;

    /** the name of the location (city) the rooms must match. */
    private final String location;

    /**
     * true = the location and the city must match; false = the location or the
     * city must match.
     */
    private final boolean and;

    /**
     * Constructs a new <code>FindRoomCommand</code>.
     * 
     * @param hotel
     *            the name (or only the first part of it!) of the hotel the
     *            rooms should belong to.
     * @param location
     *            the location/city (or only the first part of it!) the rooms
     *            should be located at.
     * @param and
     *            <code>true</code> => hotel <b>and</b> location must be
     *            matched; <code>false</code> => hotel <b>or</b> location must
     *            be matched.
     */
    public FindRoomCommand(final String hotel, final String location,
	    final boolean and) {
	super();
	this.hotel = hotel;
	this.location = location;
	this.and = and;
    }

    /**
     * Getter for the hotel name.
     * 
     * @return the hotel name.
     */
    public String getHotel() {
	return hotel;
    }

    /**
     * Getter for the location.
     * 
     * @return the location.
     */
    public String getLocation() {
	return location;
    }

    /**
     * Getter for the logical concatenation of the search parameters.
     * 
     * @return the logical concatenation.
     */
    public boolean isAnd() {
	return and;
    }

}
