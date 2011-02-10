package suncertify.common;

import java.util.List;

import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.common.roomoffer.UpdateRoomCommand;

public interface RoomOfferService {

    void createRoomOffer(CreateRoomCommand command,
	    ClientCallback<RoomOffer> callback);

    void deleteRoomOffer(DeleteRoomCommand command,
	    ClientCallback<Integer> callback);

    void updateRoomOffer(UpdateRoomCommand command,
	    ClientCallback<RoomOffer> callback);

    void findRoomOffer(FindRoomCommand command,
	    ClientCallback<List<RoomOffer>> callback);
}
