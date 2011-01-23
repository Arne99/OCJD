package suncertify.domain;

import java.util.List;

import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.db.DB;

final class AllRoomOffers {

    private final DB database;

    private AllRoomOffers(final DB database) {
	super();
	this.database = database;
    }

    RoomOffer addRoom(final CreateRoomCommand command)
	    throws ConstraintViolationException {

	final RoomOffer roomOffer = command.getRoomOfferToCreate();
	return null;
    }

    int removeRoom(final DeleteRoomCommand command) {
	return -1;
    }

    List<RoomOffer> findRooms(final FindRoomCommand command) {
	// TODO Auto-generated method stub
	return null;
    }

}
