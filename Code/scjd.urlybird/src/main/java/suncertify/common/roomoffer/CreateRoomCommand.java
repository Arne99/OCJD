package suncertify.common.roomoffer;

import suncertify.common.Command;

public final class CreateRoomCommand implements Command {

    private final RoomOffer roomOffer;

    public RoomOffer getRoomOffer() {
	return roomOffer;
    }

    public CreateRoomCommand(final RoomOffer roomOffer) {
	super();
	this.roomOffer = roomOffer;
    }

}
