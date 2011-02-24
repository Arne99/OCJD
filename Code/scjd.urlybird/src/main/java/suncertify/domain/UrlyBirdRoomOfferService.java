package suncertify.domain;

import static suncertify.util.DesignByContract.*;

import java.rmi.Remote;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

import suncertify.common.ClientCallback;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.db.DB;
import suncertify.db.RecordNotFoundException;
import suncertify.util.Pair;

public class UrlyBirdRoomOfferService implements RoomOfferService {

    private static final int NOT_LOCKED = -1;
    private static final int HOTEL = 0;
    private static final int CITY = 1;
    private static final int SIZE = 2;
    private static final int SMOKING = 3;
    private static final int PRICE = 4;
    private static final int DATE = 5;
    private static final int CUSTOMER = 6;
    private static final int INDEX = 7;

    private final BusinessRule<RoomOffer> isOccupancyIn48Hours;
    private final BusinessRule<RoomOffer> isRoomBookable;
    private final RoomOfferBuilder builder;
    private final Dao<RoomOffer> roomOfferDao;

    public UrlyBirdRoomOfferService(final DB database) {
	this(new RoomOfferDao(database, new RoomOfferBuilder()),
		new RoomOfferBuilder(), new IsRoomOccupancyIn48Hours(),
		new IsRoomBookable());
    }

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

	final RoomOffer clientRoomToBook = command.getRoomToBook();
	final int roomOfferIndex = clientRoomToBook.getIndex();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(roomOfferIndex);
	    final RoomOffer dbRoomToBook = roomOfferDao.read(roomOfferIndex);

	    checkStaleRoomData(clientRoomToBook, dbRoomToBook);

	    if (!isRoomBookable.isSatisfiedBy(dbRoomToBook)) {
		callback.onFailure("");
	    }
	    final RoomOffer bookedRoomOffer = builder.copyOf(dbRoomToBook)
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
	    final List<String> values = command.getValues();
	    final RoomOffer roomOffer = buildRoomOffer(values);

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

	final RoomOffer clientRoomToDelete = command.getRoomOfferToDelete();
	final int index = clientRoomToDelete.getIndex();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(index);
	    final RoomOffer dbRoomToDelete = roomOfferDao.read(index);

	    checkStaleRoomData(clientRoomToDelete, dbRoomToDelete);

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
	final List<String> newValues = command.getNewValues();
	final RoomOffer clientRoomToUpdate = command.getRoomToUpdate();
	final int index = clientRoomToUpdate.getIndex();
	try {
	    lock = roomOfferDao.lock(index);

	    final RoomOffer dbRoomToUpdate = roomOfferDao.read(index);
	    checkStaleRoomData(clientRoomToUpdate, dbRoomToUpdate);

	    final RoomOffer updatedRoomOffer = buildRoomOffer(newValues);

	    roomOfferDao.update(updatedRoomOffer, lock);
	    callback.onSuccess(updatedRoomOffer);
	} catch (final Exception e) {
	    callback.onFailure(e.getMessage());
	} finally {
	    unlockQuietly(index, lock);
	}
    }

    @Override
    public void findRoomOffer(final FindRoomCommand command,
	    final ClientCallback<List<RoomOffer>> callback) {

	checkNotNull(command, "command");
	checkNotNull(callback, "callback");

	try {
	    final List<RoomOffer> matchingRoomOffers = roomOfferDao.find(Arrays
		    .asList(command.getHotel(), command.getLocation(), null,
			    null, null, null, null, null));

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
	} catch (final suncertify.db.SecurityException e) {
	    e.printStackTrace();
	} catch (final RecordNotFoundException e) {
	    e.printStackTrace();
	}

    }

    private final RoomOffer buildRoomOffer(final List<String> values)
	    throws ConstraintViolationException {
	return builder.newRoomOffer().fromHotel(values.get(HOTEL))
		.fromCity(values.get(CITY)).ofSize(values.get(SIZE))
		.smokingAllowed(values.get(SMOKING))
		.withIndex(values.get(INDEX)).bookedBy(values.get(CUSTOMER))
		.bookableAt(values.get(DATE)).withPrice(values.get(PRICE))
		.build();
    }

    private final void checkStaleRoomData(final RoomOffer clientRoom,
	    final RoomOffer dbRoom) throws ConcurrentModificationException {

	if (!clientRoom.equals(dbRoom)) {
	    throw new ConcurrentModificationException(
		    "Concurrent modification!");
	}
    }
}
