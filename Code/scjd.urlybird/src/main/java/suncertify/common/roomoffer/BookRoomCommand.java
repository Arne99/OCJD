package suncertify.common.roomoffer;

import java.io.Serializable;

import suncertify.common.Command;

public final class BookRoomCommand implements Command, Serializable {

    private static final long serialVersionUID = -225024283571144227L;

    private final int indexToBook;

    private final String customerId;

    public BookRoomCommand(final int index, final String customerId) {
	super();
	this.indexToBook = index;
	this.customerId = customerId;
    }

    public String getCustomerId() {
	return customerId;
    }

    public int getRoomOfferIndex() {
	return indexToBook;
    }

}
