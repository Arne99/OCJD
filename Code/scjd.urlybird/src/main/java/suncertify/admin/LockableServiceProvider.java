package suncertify.admin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.common.RoomOfferService;
import suncertify.common.ServiceProvider;
import suncertify.db.DB;
import suncertify.domain.UrlyBirdRoomOfferService;

/**
 * Implementation of {@link ServiceProvider} with the opportunity to lock all
 * services to stop every interaction between the clients and the server.
 * 
 * This is necessary if the server is shutting down but the client could still
 * call service methods with their instances of the remote services.
 * 
 * This class must be an singleton to be able to lock every service.
 * 
 * @author arnelandwehr
 * 
 */
public final class LockableServiceProvider extends UnicastRemoteObject
	implements ServiceProvider {

    /** thread safe singleton instance. */
    private static volatile LockableServiceProvider instance;

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance.
     * @throws RemoteException
     *             if an remote problem happens.
     */
    public static LockableServiceProvider instance() throws RemoteException {

	if (instance == null) {
	    instance = new LockableServiceProvider();
	}
	return instance;
    }

    /**
     * Private constructor, never use.
     * 
     * @throws RemoteException
     *             if an remote problem happens.
     */
    private LockableServiceProvider() throws RemoteException {
	super();
    }

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Locks every service and stops the interaction between client and server.
     */
    public void disableServices() {
	LocableRemoteRoomOfferServiceProxy.disableAccess();
    }

    /**
     * Enables all services.
     */
    public void enableServices() {
	LocableRemoteRoomOfferServiceProxy.enableAccess();
    }

    @Override
    public RoomOfferService getRoomOfferService() throws RemoteException {
	final DB database = ServerState.instance().getDatabase();
	return new LocableRemoteRoomOfferServiceProxy(
		new UrlyBirdRoomOfferService(database));
    }
}
