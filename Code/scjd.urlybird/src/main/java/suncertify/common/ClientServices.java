package suncertify.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import suncertify.common.roomoffer.RoomOfferService;

public interface ClientServices extends Remote {

    public RoomOfferService getRoomOfferService() throws RemoteException;
}
