package suncertify.common.roomoffer;

import java.util.ArrayList;
import java.util.List;

import suncertify.common.Command;
import suncertify.domain.RoomOffer;

public final class UpdateRoomCommand implements Command {

    private static final long serialVersionUID = 1367931798192867975L;

    private final List<String> newValues;

    private final RoomOffer roomToUpdate;

    public UpdateRoomCommand(final List<String> newValues,
	    final RoomOffer roomToUpdate) {
	super();
	this.newValues = new ArrayList<String>(newValues);
	this.roomToUpdate = roomToUpdate;
    }

    public List<String> getNewValues() {
	return newValues;
    }

    public RoomOffer getRoomToUpdate() {
	return roomToUpdate;
    }

}
