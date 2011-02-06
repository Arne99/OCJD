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

class UrlyBirdRoomOfferService implements RoomOfferService {

    private final BusinessRule<CreateRoomCommand> isOccupancyIn48Hours;
    private final BusinessRule<RoomOffer> isRoomBookable;
    private final RoomOfferBuilder builder;
    private final Dao<RoomOffer> roomOfferDao;

    UrlyBirdRoomOfferService(final Dao<RoomOffer> roomOfferDao,
	    final RoomOfferBuilder roomOfferBuilder,
	    final BusinessRule<CreateRoomCommand> isOccupancyIn48Hours,
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
	long lock = -1;
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
	} catch (final ConstraintViolationException e) {
	    callback.onFailure(e.getMessage());
	} finally {
	    if (lock != -1) {
		roomOfferDao.unlock(roomOfferIndex, lock);
	    }
	}
    }

    @Override
    public void createRoomOffer(final CreateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	if (!isOccupancyIn48Hours.isSatisfiedBy(command)
		&& !callback.onWarning("!!!!!")) {
	    return;
	}

	try {
	    final RoomOffer roomOffer = builder.newRoomOffer()
		    .bookableAt(command.getBookableDate())
		    .fromCity(command.getCity())
		    .smokingAllowed(command.isSmokingAllowed())
		    .fromHotel(command.getHotel())
		    .ofSize(command.getRoomSize())
		    .withPrice(command.getPrice()).build();

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

	try {
	    final int roomOfferIdToDelete = command.getRoomOfferIdToDelete();
	    final long lock = roomOfferDao.lock(roomOfferIdToDelete);
	    roomOfferDao.delete(roomOfferIdToDelete, lock);
	    callback.onSuccess(roomOfferIdToDelete);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	}

    }

    @Override
    public void updateRoomOffer(final UpdateRoomCommand command,
	    final ClientCallback<RoomOffer> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final RoomOffer roomOffer = command.getUpdatedRoomOffer();
	    final long lock = roomOfferDao.lock(roomOffer.getIndex());
	    roomOfferDao.update(roomOffer, lock);
	    callback.onSuccess(roomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
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
}
