package suncertify.domain;

import static suncertify.util.DesignByContract.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.db.DB;
import suncertify.db.RecordNotFoundException;

public class UrlyBirdRoomOfferService implements RoomOfferService {

    private static final int NOT_LOCKED = -1;

    private final BusinessRule<Date> isOccupancyIn48Hours;
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
	    final BusinessRule<Date> isOccupancyIn48Hours,
	    final BusinessRule<RoomOffer> isRoomBookable) {
	super();
	this.roomOfferDao = roomOfferDao;
	this.builder = roomOfferBuilder;
	this.isOccupancyIn48Hours = isOccupancyIn48Hours;
	this.isRoomBookable = isRoomBookable;
    }

    @Override
    public RoomOffer bookRoomOffer(final BookRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	final RoomOffer clientRoomToBook = command.getRoomToBook();
	final int roomOfferIndex = clientRoomToBook.getIndex();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(roomOfferIndex);
	    final RoomOffer dbRoomToBook = roomOfferDao.read(roomOfferIndex);

	    checkStaleRoomData(clientRoomToBook, dbRoomToBook);

	    if (!isRoomBookable.isSatisfiedBy(dbRoomToBook)) {
		throw new Exception();
	    }

	    final RoomOffer bookedRoomOffer = builder
		    .createRoomOfferWithNewCustomer(clientRoomToBook,
			    command.getCustomerId());
	    roomOfferDao.update(bookedRoomOffer, lock);
	    return bookedRoomOffer;
	} catch (final Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    unlockQuietly(roomOfferIndex, lock);
	}
    }

    @Override
    public RoomOffer createRoomOffer(final CreateRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	final List<String> values = command.getValues();
	final Date date = builder.getDateFromValues(values);
	if (!isOccupancyIn48Hours.isSatisfiedBy(date)) {
	    throw new Exception("");
	}

	RoomOffer roomOffer = null;
	try {
	    roomOffer = roomOfferDao.create(values);
	} catch (final Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	}

	return roomOffer;

    }

    @Override
    public int deleteRoomOffer(final DeleteRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	final RoomOffer clientRoomToDelete = command.getRoomOfferToDelete();
	final int index = clientRoomToDelete.getIndex();
	long lock = NOT_LOCKED;
	try {
	    lock = roomOfferDao.lock(index);
	    final RoomOffer dbRoomToDelete = roomOfferDao.read(index);

	    checkStaleRoomData(clientRoomToDelete, dbRoomToDelete);

	    roomOfferDao.delete(index, lock);
	    return index;
	} catch (final Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    unlockQuietly(index, lock);
	}

    }

    @Override
    public RoomOffer updateRoomOffer(final UpdateRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	long lock = NOT_LOCKED;
	final List<String> values = command.getNewValues();
	final RoomOffer clientRoomToUpdate = command.getRoomToUpdate();
	final int index = clientRoomToUpdate.getIndex();
	try {
	    lock = roomOfferDao.lock(index);

	    final RoomOffer dbRoomToUpdate = roomOfferDao.read(index);
	    checkStaleRoomData(clientRoomToUpdate, dbRoomToUpdate);

	    final RoomOffer updatedRoomOffer = builder.createRoomOffer(values,
		    index);

	    roomOfferDao.update(updatedRoomOffer, lock);
	    return updatedRoomOffer;
	} catch (final Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    unlockQuietly(index, lock);
	}
    }

    @Override
    public List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	try {
	    if (command.isAnd()) {
		return new ArrayList<RoomOffer>(roomOfferDao.find(Arrays
			.asList(command.getHotel(), command.getLocation(),
				null, null, null, null, null, null)));

	    } else {
		final ArrayList<RoomOffer> matchingHotels = new ArrayList<RoomOffer>(
			roomOfferDao.find(Arrays.asList(command.getHotel(),
				null, null, null, null, null, null, null)));
		final ArrayList<RoomOffer> matchingLocations = new ArrayList<RoomOffer>(
			roomOfferDao.find(Arrays.asList(null,
				command.getLocation(), null, null, null, null,
				null, null)));

		final Set<RoomOffer> union = new HashSet<RoomOffer>();
		union.addAll(matchingHotels);
		union.addAll(matchingLocations);
		return new ArrayList<RoomOffer>(union);
	    }
	} catch (final Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
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

    private final void checkStaleRoomData(final RoomOffer clientRoom,
	    final RoomOffer dbRoom) throws ConcurrentModificationException {

	if (!clientRoom.equals(dbRoom)) {
	    throw new ConcurrentModificationException(
		    "Concurrent modification!");
	}
    }
}
