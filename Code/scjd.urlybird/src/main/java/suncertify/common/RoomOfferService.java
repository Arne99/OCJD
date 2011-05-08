package suncertify.common;

import java.rmi.Remote;
import java.util.List;

import suncertify.domain.UrlyBirdRoomOffer;

public interface RoomOfferService extends Remote {

    RoomOffer createRoomOffer(CreateRoomCommand command) throws Exception;

    UrlyBirdRoomOffer bookRoomOffer(BookRoomCommand command) throws Exception;

    int deleteRoomOffer(DeleteRoomCommand command) throws Exception;

    RoomOffer updateRoomOffer(UpdateRoomCommand command) throws Exception;

    List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception;
}
