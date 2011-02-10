package suncertify.domain;

import static suncertify.util.DesignByContract.*;

import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.db.RecordNotFoundException;

class UrlyBirdRoomOfferService implements RoomOfferService {

    private static final int NOT_LOCKED = -1;

    private final BusinessRule<RoomOffer> isOccupancyIn48Hours;
    private final BusinessRule<RoomOffer> isRoomBookable;
    private final RoomOfferBuilder builder;
    private final Dao<RoomOffer> roomOfferDao;

    UrlyBirdRoomOfferService(final Dao<RoomOffer> roomOfferDao,
	    final RoomOfferBuilder roomOfferBuilder,
	    final BusinessRule<RoomOffer> isOccupancyIn48Hours,
	    final BusinessRule<RoomOffer> isRoomBookable) {
	super();
	this.roomOfferDao = roomOfferDao;
	this.builder = roomOfferBuilder;
	this.isOccupancyIn48Hours = isOccupancyIn48Hours;
	this.isRoomBookable = isRoomBookable;
    }

    public void bookRoomOffer(final BookRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	final int roomOfferIndex = command.getRoomOfferIndex();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(roomOfferIndex);
	    final RoomOffer roomOfferToBook = roomOfferDao.read(roomOfferIndex);
	    if (!isRoomBookable.isSatisfiedBy(roomOfferToBook)) {
		callback.onFailure("");
	    }
	    final RoomOffer bookedRoomOffer = builder.copyOf(roomOfferToBook)
		    .bookedBy(command.getCustomerId()).build();
	    roomOfferDao.update(bookedRoomOffer, lock);
	    callback.onSuccess(bookedRoomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	} finally {
	    unlockQuietly(roomOfferIndex, lock);
	}
    }

    @Override
    public void createRoomOffer(final CreateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final RoomOffer roomOffer = builder.copyOf(command.getRoomOffer())
		    .build();
	    if (!isOccupancyIn48Hours.isSatisfiedBy(roomOffer)
		    && !callback.onWarning("!!!!!")) {
		return;
	    }
	    roomOfferDao.create(roomOffer);
	    callback.onSuccess(roomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }

    @Override
    public void deleteRoomOffer(final DeleteRoomCommand command,
	    final ClientCallback<Integer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	final int index = command.getRoomOfferIndexToDelete();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(index);
	    roomOfferDao.delete(index, lock);
	    callback.onSuccess(index);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	} finally {
	    unlockQuietly(index, lock);
	}

    }

    @Override
    public void updateRoomOffer(final UpdateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	long lock = NOT_LOCKED;
	final RoomOffer unproofedRoomOffer = command.getUpdatedRoomOffer();
	final int updateIndex = unproofedRoomOffer.getIndex();
	try {
	    final RoomOffer proofedRoomOffer = builder.copyOf(unproofedRoomOffer)
		    .build();
	    lock = roomOfferDao.lock(updateIndex);
	    roomOfferDao.update(proofedRoomOffer, lock);
	    callback.onSuccess(proofedRoomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	} finally {
	    unlockQuietly(updateIndex, lock);
	}
    }

    @Override
    public void findRoomOffer(final FindRoomCommand command,
	    final ClientCallback<List<RoomOffer>> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final List<RoomOffer> matchingRoomOffers = roomOfferDao
		    .find(command.getCriteria());
	    callback.onSuccess(matchingRoomOffers);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }

    private void unlockQuietly(final int index, final long lock) {

	if (lock == NOT_LOCKED) {
	    return;
	}

	try {
	    roomOfferDao.unlock(index, lock);
	} catch (final SecurityException e) {
	    e.printStackTrace();
	} catch (final RecordNotFoundException e) {
	    e.printStackTrace();
	}

    }
}
