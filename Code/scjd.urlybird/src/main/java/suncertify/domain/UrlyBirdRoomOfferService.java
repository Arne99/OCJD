package suncertify.domain;

import static suncertify.util.DesignByContract.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import suncertify.common.BookRoomCommand;
import suncertify.common.CreateRoomCommand;
import suncertify.common.DeleteRoomCommand;
import suncertify.common.FindRoomCommand;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;
import suncertify.common.UpdateRoomCommand;
import suncertify.db.DB;
import suncertify.db.RecordNotFoundException;
import suncertify.util.Specification;

/**
 * The <code>UrlyBirdRoomOfferService</code> includes the main domain logic for
 * the UrlyBird-Application. It fulfills the {@link RoomOfferService} interface
 * for an concurrent environment.
 * 
 * @author arnelandwehr
 * 
 */
public final class UrlyBirdRoomOfferService implements RoomOfferService {

    /** logger for the thrown exceptions, global is sufficient here. */
    private final Logger logger = Logger.getLogger("global");

    /** identifier for an unlocked state. */
    private static final int NOT_LOCKED = -1;

    /**
     * business rule: the occupancy of ever new {@link RoomOffer} must be in the
     * next 48 hours.
     */
    private final Specification<Date> isOccupancyIn48Hours;

    /**
     * business rule: specifies if a {@link RoomOffer} is already bookable by an
     * customer.
     */
    private final Specification<RoomOffer> isRoomBookable;

    /** factory for {@link RoomOffer}s. */
    private final RoomOfferFactory factory;

    /**
     * DAO for {@link RoomOffer}s that hides the database layer from the domain
     * layer.
     */
    private final DataAccessObject<UrlyBirdRoomOffer> roomOfferDao;

    /**
     * Construct a new <code>UrlyBirdRoomOfferService</code>.
     * 
     * @param database
     *            the backend database.
     */
    public UrlyBirdRoomOfferService(final DB database) {
	this(new RoomOfferDao(database, new RoomOfferFactory()),
		new RoomOfferFactory(), new IsRoomOccupancyIn48Hours(),
		new IsRoomBookable());
    }

    /**
     * Construct a new <code>UrlyBirdRoomOfferService</code>.
     * 
     * @param roomOfferDao
     *            the dao
     * @param roomOfferBuilder
     *            the factory
     * @param isOccupancyIn48Hours
     *            the 48 hours business rule
     * @param isRoomBookable
     *            the bookable business rule
     */
    UrlyBirdRoomOfferService(
	    final DataAccessObject<UrlyBirdRoomOffer> roomOfferDao,
	    final RoomOfferFactory roomOfferBuilder,
	    final Specification<Date> isOccupancyIn48Hours,
	    final Specification<RoomOffer> isRoomBookable) {
	super();
	this.roomOfferDao = roomOfferDao;
	this.factory = roomOfferBuilder;
	this.isOccupancyIn48Hours = isOccupancyIn48Hours;
	this.isRoomBookable = isRoomBookable;
    }

    @Override
    public UrlyBirdRoomOffer bookRoomOffer(final BookRoomCommand command)
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
		throw new Exception("the room to book: " + clientRoomToBook
			+ " is already booked");
	    }

	    final UrlyBirdRoomOffer bookedRoomOffer = factory
		    .copyRoomOfferWithNewCustomer(clientRoomToBook,
			    command.getCustomerId());
	    roomOfferDao.update(bookedRoomOffer, lock);
	    return bookedRoomOffer;
	} catch (final Exception e) {
	    logger.throwing(getClass().getName(), "bookRoomOffer", e);
	    throw new Exception(e);
	} finally {
	    unlockQuietly(roomOfferIndex, lock);
	}
    }

    @Override
    public UrlyBirdRoomOffer createRoomOffer(final CreateRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	final List<String> values = command.getValues();
	final Date date = factory.getBookableDateFromValues(values);
	if (!isOccupancyIn48Hours.isSatisfiedBy(date)) {
	    throw new Exception(
		    "the occupany of the new room is not in the next 48 hours, the room can not be added");
	}

	UrlyBirdRoomOffer roomOffer = null;
	try {
	    roomOffer = roomOfferDao.create(values);
	} catch (final Exception e) {
	    logger.throwing(getClass().getName(), "createRoomOffer", e);
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
	    logger.throwing(getClass().getName(), "deleteRoomOffer", e);
	    throw new Exception(e);
	} finally {
	    unlockQuietly(index, lock);
	}

    }

    @Override
    public UrlyBirdRoomOffer updateRoomOffer(final UpdateRoomCommand command)
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

	    final UrlyBirdRoomOffer updatedRoomOffer = factory.createRoomOffer(
		    values, index);

	    roomOfferDao.update(updatedRoomOffer, lock);
	    return updatedRoomOffer;
	} catch (final Exception e) {
	    logger.throwing(getClass().getName(), "updateRoomOffer", e);
	    throw new Exception(e);
	} finally {
	    unlockQuietly(index, lock);
	}
    }

    @Override
    public List<RoomOffer> findRoomOffer(final FindRoomCommand command)
	    throws Exception {

	checkNotNull(command, "command");

	// TODO this could give stale data
	try {
	    if (command.isAnd()) {
		return new ArrayList<RoomOffer>(roomOfferDao.find(Arrays
			.asList(command.getHotel(), command.getLocation(),
				null, null, null, null, null)));

	    } else {
		final ArrayList<UrlyBirdRoomOffer> matchingHotels = new ArrayList<UrlyBirdRoomOffer>(
			roomOfferDao.find(Arrays.asList(command.getHotel(),
				null, null, null, null, null, null)));
		final ArrayList<UrlyBirdRoomOffer> matchingLocations = new ArrayList<UrlyBirdRoomOffer>(
			roomOfferDao.find(Arrays.asList(null,
				command.getLocation(), null, null, null, null,
				null)));

		final Set<UrlyBirdRoomOffer> union = new HashSet<UrlyBirdRoomOffer>();
		union.addAll(matchingHotels);
		union.addAll(matchingLocations);
		return new ArrayList<RoomOffer>(union);
	    }
	} catch (final Exception e) {
	    logger.throwing(getClass().getName(), "findRoomOffer", e);
	    throw new Exception(e);
	}
    }

    /**
     * Unlocks the room at the given index with the given lock without throwing
     * any checked exceptions. The checked exceptions could not reasonably be
     * handled at this layer. This method is inspired by commons-io
     * closeQuietly().
     * 
     * @param index
     *            the index of the room to unlock.
     * @param lock
     *            the lock the room was locked with.
     */
    private void unlockQuietly(final int index, final long lock) {

	if (lock == NOT_LOCKED) {
	    return;
	}

	try {
	    roomOfferDao.unlock(index, lock);
	} catch (final suncertify.db.SecurityException e) {
	    logger.throwing(getClass().getName(), "unlockQuietly", e);
	} catch (final RecordNotFoundException e) {
	    logger.throwing(getClass().getName(), "unlockQuietly", e);
	}

    }

    /**
     * Checks if the given clientRoom was changed in the meantime by another
     * client and throws an {@link ConcurrentModificationException} in this
     * case. This method guards against the lost update problem in an concurrent
     * multi-client environment.
     * 
     * @param clientRoom
     *            the given {@link RoomOffer} from the client, which could be
     *            stale data.
     * @param dbRoom
     *            the actually in the database store room.
     * @throws ConcurrentUpdateException
     *             if the actually stored room in the database is not equal to
     *             the clientRoom.
     */
    private void checkStaleRoomData(final RoomOffer clientRoom,
	    final RoomOffer dbRoom) throws ConcurrentUpdateException {

	if (!clientRoom.equals(dbRoom)) {
	    throw new ConcurrentModificationException(
		    "The room with the id:"
			    + clientRoom.getIndex()
			    + " was concurrently updated by another client. Please refresh your data view and try your changes again.");
	}
    }
}
