package suncertify.common.roomoffer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.domain.UrlyBirdRoomOfferService;

public class RemoteUrlyBirdRoomOfferService extends UnicastRemoteObject
	implements RoomOfferService {

    private static final long serialVersionUID = 8663235521155155619L;

    private final UrlyBirdRoomOfferService delegate;

    public RemoteUrlyBirdRoomOfferService(
	    final UrlyBirdRoomOfferService delegate) throws RemoteException {
	super();
	this.delegate = delegate;
    }

    public void bookRoomOffer(final BookRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {
	delegate.bookRoomOffer(command, callback);
    }

    @Override
    public void createRoomOffer(final CreateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {
	delegate.createRoomOffer(command, callback);
    }

    @Override
    public void deleteRoomOffer(final DeleteRoomCommand command,
	    final ClientCallback<Integer> callback) {
	delegate.deleteRoomOffer(command, callback);
    }

    @Override
    public void updateRoomOffer(final UpdateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {
	delegate.updateRoomOffer(command, callback);
    }

    @Override
    public void findRoomOffer(final FindRoomCommand command,
	    final ClientCallback<List<RoomOffer>> callback) {
	delegate.findRoomOffer(command, callback);
    }

}
