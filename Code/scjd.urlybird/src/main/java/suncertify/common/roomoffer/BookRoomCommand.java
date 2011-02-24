package suncertify.common.roomoffer;

import java.io.Serializable;

import suncertify.common.Command;
import suncertify.domain.RoomOffer;

public final class BookRoomCommand implements Command {

    private static final long serialVersionUID = -225024283571144227L;

    private final String customerId;

    private final RoomOffer roomToBook;

    public BookRoomCommand(final RoomOffer roomToBook, final String customerId) {
	super();
	this.roomToBook = roomToBook;
	this.customerId = customerId;
    }

    public String getCustomerId() {
	return customerId;
    }

    public RoomOffer getRoomToBook() {
	return roomToBook;
    }

}
