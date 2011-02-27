package suncertify.admin.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.domain.RoomOffer;
import suncertify.util.Pair;

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
    public RoomOffer createRoomOffer(final CreateRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.createRoomOffer(command);
    }

    private void checkAccessibility() throws Exception {
	if (!accessible) {
	    throw new Exception("");
	}

    }

    @Override
    public int deleteRoomOffer(final DeleteRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.deleteRoomOffer(command);
    }

    @Override
    public List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.findRoomOffer(command);
    }

    @Override
    public RoomOffer updateRoomOffer(final UpdateRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.updateRoomOffer(command);
    }

    static void disableAccess() {
	accessible = false;
    }

    static void enableAccess() {
	accessible = true;
    }

    @Override
    public RoomOffer bookRoomOffer(final BookRoomCommand command) throws Exception {

	checkAccessibility();
	return delegate.bookRoomOffer(command);
    }

}
