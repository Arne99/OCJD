package suncertify.domain;

import static suncertify.util.DesignByContract.*;

import java.util.List;

import suncertify.common.roomoffer.BookRoomCallback;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCallback;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCallback;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCallback;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCallback;
import suncertify.common.roomoffer.UpdateRoomCommand;

class UrlyBirdRoomOfferService implements RoomOfferService {

    private final AllRoomOffers roomOffers;
    private final IsRoomOccupancyIn48Hours isOccupancyIn48Hours;
    private final IsRoomBookable isRoomBookable;

    private UrlyBirdRoomOfferService(final AllRoomOffers roomOffers,
	    final IsRoomOccupancyIn48Hours isOccupancyIn48Hours,
	    final IsRoomBookable isRoomBookable) {
	super();
	this.roomOffers = roomOffers;
	this.isOccupancyIn48Hours = isOccupancyIn48Hours;
	this.isRoomBookable = isRoomBookable;
    }

    public void bookRoomOffer(final BookRoomCommand command,
	    final BookRoomCallback callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	if (!isRoomBookable.isSatisfiedBy(command)) {
	    callback.onFailure("");
	}

    }

    @Override
    public void createRoomOffer(final CreateRoomCommand command,
	    final CreateRoomCallback callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	if (isOccupancyIn48Hours.isFalse(command)
		&& !callback.onWarning("!!!!!")) {
	    return;
	}

	try {
	    final RoomOffer roomOffer = roomOffers.addRoom(command);
	    callback.onSuccess(roomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }

    @Override
    public void deleteRoomOffer(final DeleteRoomCommand command,
	    final DeleteRoomCallback callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final Integer deletedIndex = roomOffers.removeRoom(command);
	    callback.onSuccess(deletedIndex);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }

    @Override
    public void updateRoomOffer(final UpdateRoomCommand command,
	    final UpdateRoomCallback callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final RoomOffer roomOffer = roomOffers
		    .addRoom(new CreateRoomCommand() {

			@Override
			public RoomOffer getRoomOfferToCreate() {
			    return command.getUpdatedRoomOffer();
			}
		    });
	    callback.onSuccess(roomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}
    }

    @Override
    public void findRoomOffer(final FindRoomCommand command,
	    final FindRoomCallback callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final List<RoomOffer> matchingRooms = roomOffers.findRooms(command);
	    callback.onSuccess(matchingRooms);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }
}
