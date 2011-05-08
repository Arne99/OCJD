package suncertify.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import suncertify.common.roomoffer.RoomOfferService;

public interface Services extends Remote {

     RoomOfferService getRoomOfferService() throws RemoteException;
}
