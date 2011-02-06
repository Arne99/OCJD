package suncertify.common.roomoffer;

import java.util.List;

import suncertify.common.ClientCallback;

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
