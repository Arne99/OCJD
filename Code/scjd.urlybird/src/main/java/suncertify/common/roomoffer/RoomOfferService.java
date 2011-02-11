package suncertify.common.roomoffer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import suncertify.common.ClientCallback;

public interface RoomOfferService extends Remote {

    void createRoomOffer(CreateRoomCommand command,
	    ClientCallback<RoomOffer> callback) throws RemoteException;

    void deleteRoomOffer(DeleteRoomCommand command,
	    ClientCallback<Integer> callback) throws RemoteException;

    void updateRoomOffer(UpdateRoomCommand command,
	    ClientCallback<RoomOffer> callback) throws RemoteException;

    void findRoomOffer(FindRoomCommand command,
	    ClientCallback<List<RoomOffer>> callback) throws RemoteException;
}
