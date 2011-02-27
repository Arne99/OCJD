package suncertify.common.roomoffer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.domain.RoomOffer;
import suncertify.util.Pair;

public interface RoomOfferService extends Remote {

    RoomOffer createRoomOffer(CreateRoomCommand command) throws Exception;

    RoomOffer bookRoomOffer(BookRoomCommand command) throws Exception;

    int deleteRoomOffer(DeleteRoomCommand command) throws Exception;

    RoomOffer updateRoomOffer(UpdateRoomCommand command) throws Exception;

    List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception;
}
