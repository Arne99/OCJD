package suncertify.common;


public final class DeleteRoomCommand implements Command {

    private static final long serialVersionUID = -5326172801204501197L;

    private final RoomOffer roomOfferToDelete;

    public DeleteRoomCommand(final RoomOffer roomOfferToDelete) {
	super();
	this.roomOfferToDelete = roomOfferToDelete;
    }

    public RoomOffer getRoomOfferToDelete() {
	return roomOfferToDelete;
    }

}