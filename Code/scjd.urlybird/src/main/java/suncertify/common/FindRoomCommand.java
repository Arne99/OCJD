package suncertify.common;


public final class FindRoomCommand implements Command {

    private static final long serialVersionUID = 6794005890219300147L;
    private final String hotel;
    private final String location;
    private final boolean and;

    public FindRoomCommand(final String hotel, final String location,
	    final boolean and) {
	super();
	this.hotel = hotel;
	this.location = location;
	this.and = and;
    }

    public String getHotel() {
	return hotel;
    }

    public String getLocation() {
	return location;
    }

    public boolean isAnd() {
	return and;
    }

}
