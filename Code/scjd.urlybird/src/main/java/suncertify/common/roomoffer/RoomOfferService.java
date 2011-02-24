package suncertify.common.roomoffer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.domain.RoomOffer;
import suncertify.util.Pair;

public interface RoomOfferService extends Remote {

    void createRoomOffer(CreateRoomCommand command,
	    ClientCallback<RoomOffer> callback) throws RemoteException;

    void deleteRoomOffer(DeleteRoomCommand command,
	    ClientCallback<Integer> callback) throws RemoteException;

    void updateRoomOffer(UpdateRoomCommand command,
	    ClientCallback<RoomOffer> callback) throws RemoteException;

    void findRoomOffer(final FindRoomCommand command,
	    ClientCallback<List<RoomOffer>> callback) throws RemoteException;
}
