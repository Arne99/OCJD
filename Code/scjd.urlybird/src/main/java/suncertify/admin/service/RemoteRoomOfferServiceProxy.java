package suncertify.admin.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCommand;

class RemoteRoomOfferServiceProxy extends UnicastRemoteObject implements
	RoomOfferService {

    private static final long serialVersionUID = -1158034211648936653L;

    private final RoomOfferService delegate;

    private static boolean accessible = true;

    RemoteRoomOfferServiceProxy(final RoomOfferService delegate)
	    throws RemoteException {
	super();
	this.delegate = delegate;
    }

    @Override
    public void createRoomOffer(final CreateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) throws RemoteException {

	if (!accessible) {
	    callback.onFailure("");
	    return;
	}
	delegate.createRoomOffer(command, callback);
    }

    @Override
    public void deleteRoomOffer(final DeleteRoomCommand command,
	    final ClientCallback<Integer> callback) throws RemoteException {

	if (!accessible) {
	    callback.onFailure("");
	    return;
	}
	delegate.deleteRoomOffer(command, callback);

    }

    @Override
    public void findRoomOffer(final FindRoomCommand command,
	    final ClientCallback<List<RoomOffer>> callback)
	    throws RemoteException {

	if (!accessible) {
	    callback.onFailure("");
	    return;
	}
	delegate.findRoomOffer(command, callback);

    }

    @Override
    public void updateRoomOffer(final UpdateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) throws RemoteException {

	if (!accessible) {
	    callback.onFailure("");
	    return;
	}
	delegate.updateRoomOffer(command, callback);

    }

    static void disableAccess() {
	accessible = false;
    }

    static void enableAccess() {
	accessible = true;
    }

}
