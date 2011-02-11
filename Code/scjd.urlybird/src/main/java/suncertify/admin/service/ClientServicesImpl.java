package suncertify.admin.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.common.ClientServices;
import suncertify.common.roomoffer.RemoteUrlyBirdRoomOfferService;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.db.DB;
import suncertify.domain.UrlyBirdRoomOfferService;

public final class ClientServicesImpl extends UnicastRemoteObject implements
	ClientServices {

    private static ClientServicesImpl INSTANCE;

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
	ServiceAccessProxy.disableAccess();
    }

    public void enableServices() {
	ServiceAccessProxy.enableAccess();
    }

    @Override
    public RoomOfferService getRoomOfferService() throws RemoteException {
	final DB database = ServerState.instance().getDatabase();
	return new ServiceAccessProxy(new RemoteUrlyBirdRoomOfferService(
		new UrlyBirdRoomOfferService(database)));
    }
}
