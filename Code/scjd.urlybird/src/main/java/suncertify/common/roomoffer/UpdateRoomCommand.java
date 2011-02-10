package suncertify.common.roomoffer;

public final class UpdateRoomCommand {

    private final RoomOffer updatedRoomOffer;

    public UpdateRoomCommand(final RoomOffer updatedRoomOffer) {
	super();
	this.updatedRoomOffer = updatedRoomOffer;
    }

    public RoomOffer getUpdatedRoomOffer() {
	return updatedRoomOffer;
    }

}
