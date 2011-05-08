package suncertify.admin.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import suncertify.common.BookRoomCommand;
import suncertify.common.CreateRoomCommand;
import suncertify.common.DeleteRoomCommand;
import suncertify.common.FindRoomCommand;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;
import suncertify.common.UpdateRoomCommand;
import suncertify.domain.UrlyBirdRoomOffer;

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
    public UrlyBirdRoomOffer bookRoomOffer(final BookRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.bookRoomOffer(command);
    }

}
