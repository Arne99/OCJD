package suncertify.common;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Services extends Remote {

     RoomOfferService getRoomOfferService() throws RemoteException;
}
