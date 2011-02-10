package suncertify.common.roomoffer;

public final class DeleteRoomCommand {

    private final RoomOffer roomOfferToDelete;

    public DeleteRoomCommand(final RoomOffer roomOfferToDelete) {
	super();
	this.roomOfferToDelete = roomOfferToDelete;
    }

    public int getRoomOfferIndexToDelete() {
	return roomOfferToDelete.getIndex();
    }

}
