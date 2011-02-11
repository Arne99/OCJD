package suncertify.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.admin.service.ServerState;
import suncertify.common.roomoffer.RemoteUrlyBirdRoomOfferService;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.db.DB;
import suncertify.domain.UrlyBirdRoomOfferService;

public final class ClientServicesImpl extends UnicastRemoteObject implements
	ClientServices {

    public ClientServicesImpl() throws RemoteException {
	super();
    }

    private static final long serialVersionUID = 1L;

    @Override
    public RoomOfferService getRoomOfferService() throws RemoteException {
	final DB database = ServerState.instance().getDatabase();
	return new RemoteUrlyBirdRoomOfferService(new UrlyBirdRoomOfferService(
		database));
    }
}
