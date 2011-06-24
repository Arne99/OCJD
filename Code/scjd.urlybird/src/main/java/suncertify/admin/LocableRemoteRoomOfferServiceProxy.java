package suncertify.admin;

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

/**
 * Proxy object for an {@link RoomOfferService} that could lock the service and
 * extends the service with remote functionality.
 * 
 * @author arnelandwehr
 * 
 */
class LocableRemoteRoomOfferServiceProxy extends UnicastRemoteObject implements
	RoomOfferService {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -1158034211648936653L;

    /**
     * the {@link RoomOfferService} delegate of this proxy.
     */
    private final RoomOfferService delegate;

    /**
     * Indicates if the service is actually accessible or not, default is
     * <code>true</code>.
     */
    private static boolean accessible = true;

    /**
     * Construct a new {@link LocableRemoteRoomOfferServiceProxy}.
     * 
     * @param delegate
     *            the {@link RoomOfferService} delegate.
     * @throws RemoteException
     *             if an remote problem occurs.
     */
    LocableRemoteRoomOfferServiceProxy(final RoomOfferService delegate)
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

    /**
     * Htrows an {@link Exception} if the serive is currently not available.
     * 
     * @throws Exception
     *             if the server is currently not available.
     */
    private void checkAccessibility() throws Exception {
	if (!accessible) {
	    throw new Exception(
		    "The service is currently not available. Perhaps the server is not reachable.");
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

    /**
     * Disables the service access.
     */
    static void disableAccess() {
	accessible = false;
    }

    /**
     * Enalbes the service access.
     */
    static void enableAccess() {
	accessible = true;
    }

    @Override
    public RoomOffer bookRoomOffer(final BookRoomCommand command)
	    throws Exception {

	checkAccessibility();
	return delegate.bookRoomOffer(command);
    }

}
