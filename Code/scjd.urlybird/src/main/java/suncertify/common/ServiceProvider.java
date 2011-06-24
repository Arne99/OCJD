package suncertify.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Gateway for the client to all services the server offers. An implementation
 * of this interface is stored under a specific address in the naming service
 * and could be located there by the client.
 * 
 * @author arnelandwehr
 * 
 */
public interface ServiceProvider extends Remote {

    /**
     * Return a {@link RoomOfferService}.
     * 
     * @return a <code>RoomOfferService</code>, never <code>null</code>.
     * @throws RemoteException
     *             if an remote problem occurs.
     */
    RoomOfferService getRoomOfferService() throws RemoteException;
}
