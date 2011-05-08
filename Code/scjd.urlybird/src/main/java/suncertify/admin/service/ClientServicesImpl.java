package suncertify.admin.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.common.RoomOfferService;
import suncertify.common.Services;
import suncertify.db.DB;
import suncertify.domain.UrlyBirdRoomOfferService;

public final class ClientServicesImpl extends UnicastRemoteObject implements
	Services {

    private static volatile ClientServicesImpl INSTANCE;

    public static ClientServicesImpl instance() throws RemoteException {

	if (INSTANCE == null) {
	    INSTANCE = new ClientServicesImpl();
	}
	return INSTANCE;
    }

    private ClientServicesImpl() throws RemoteException {
	super();
    }

    private static final long serialVersionUID = 1L;

    public void disableServices() {
	RemoteRoomOfferServiceProxy.disableAccess();
    }

    public void enableServices() {
	RemoteRoomOfferServiceProxy.enableAccess();
    }

    @Override
    public RoomOfferService getRoomOfferService() throws RemoteException {
	final DB database = ServerState.instance().getDatabase();
	return new RemoteRoomOfferServiceProxy(new UrlyBirdRoomOfferService(
		database));
    }
}
