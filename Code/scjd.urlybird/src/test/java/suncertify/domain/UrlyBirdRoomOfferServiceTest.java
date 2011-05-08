package suncertify.domain;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import suncertify.common.Money;
import suncertify.common.RoomOffer;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.db.RecordNotFoundException;

/**
 * The Class UrlyBirdRoomOfferServiceTest.
 */
public final class UrlyBirdRoomOfferServiceTest {

    /** The dao. */
    @SuppressWarnings("unchecked")
    private final Dao<UrlyBirdRoomOffer> dao = mock(Dao.class);

    /** The builder. */
    private final RoomOfferBuilder builder = mock(RoomOfferBuilder.class);

    /** The is occupancy in48 hours. */
    @SuppressWarnings("unchecked")
    private final BusinessRule<Date> isOccupancyIn48Hours = mock(BusinessRule.class);

    /** The is room bookable. */
    @SuppressWarnings("unchecked")
    private final BusinessRule<RoomOffer> isRoomBookable = mock(BusinessRule.class);

    /** The valid room offer. */
    private final UrlyBirdRoomOffer validRoomOffer = new UrlyBirdRoomOffer(
	    "Hilton", "Hamburg", 2, false, Money.create("12"), new Date(), "",
	    12);

    /** The valid room offer values. */
    private final String[] validRoomOfferValues = new String[] { "Hilton",
	    "Hamburg", "2", "N", "$120,00",
	    new SimpleDateFormat("yyyy/MM/dd").format(new Date()), "" };

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
    }

    /**
     * Should book an valid room and persist the changes.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldBookAnValidRoomAndPersistTheChanges() throws Exception {

	final String customerId = "1234";
	final UrlyBirdRoomOffer expectedRoomOffer = new UrlyBirdRoomOffer(
		validRoomOffer.getHotel(), validRoomOffer.getCity(),
		validRoomOffer.getRoomSize(), false, validRoomOffer.getPrice(),
		validRoomOffer.getBookableDate(), customerId,
		validRoomOffer.getIndex());
	final BookRoomCommand bookRoomCommand = new BookRoomCommand(
		validRoomOffer, customerId);

	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);
	when(builder.createRoomOfferWithNewCustomer(validRoomOffer, customerId))
		.thenReturn(expectedRoomOffer);
	when(dao.read(bookRoomCommand.getRoomToBook().getIndex())).thenReturn(
		validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	final UrlyBirdRoomOffer bookedRoom = roomOfferService
		.bookRoomOffer(bookRoomCommand);

	verify(isRoomBookable).isSatisfiedBy(validRoomOffer);
	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).read(validRoomOffer.getIndex());
	verify(dao).update(eq(expectedRoomOffer), anyLong());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	assertThat(bookedRoom, is(equalTo(expectedRoomOffer)));
    }

    /**
     * Should not book an already booked room and inform the client with the an
     * exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldNotBookAnAlreadyBookedRoomAndInformTheClientWithTheAnException()
	    throws Exception {

	final int index = 1;
	final BookRoomCommand command = new BookRoomCommand(validRoomOffer,
		"12");
	when(dao.read(index)).thenReturn(validRoomOffer);
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(false);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(command);
    }

    /**
     * Should not book an invalid room and inform the client with an exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldNotBookAnInvalidRoomAndInformTheClientWithAnException()
	    throws Exception {

	final int index = 1;
	final String customerId = "12";
	final BookRoomCommand command = new BookRoomCommand(validRoomOffer,
		customerId);

	when(dao.read(index)).thenReturn(validRoomOffer);
	when(
		builder.createRoomOffer(Arrays.asList(validRoomOfferValues),
			index)).thenThrow(
		new ConstraintViolationException("Test"));
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(command);
    }

    /**
     * Should create an valid room and persist it.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldCreateAnValidRoomAndPersistIt() throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOfferValues));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(isOccupancyIn48Hours.isSatisfiedBy((Date) any())).thenReturn(true);
	when(dao.create(Arrays.asList(validRoomOfferValues))).thenReturn(
		validRoomOffer);

	final UrlyBirdRoomOffer createdRoomOffer = roomOfferService
		.createRoomOffer(command);

	verify(dao).create(Arrays.asList(validRoomOfferValues));
	assertThat(createdRoomOffer, is(equalTo(validRoomOffer)));
    }

    /**
     * Should throw an exception if the occupany is not in48 hours.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldThrowAnExceptionIfTheOccupanyIsNotIn48Hours()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOfferValues));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(isOccupancyIn48Hours.isSatisfiedBy((Date) any()))
		.thenReturn(false);

	roomOfferService.createRoomOffer(command);

	verify(dao, never()).create(Arrays.asList(validRoomOfferValues));
    }

    /**
     * Should not create the room if the given room is invalid and throw an
     * exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldNotCreateTheRoomIfTheGivenRoomIsInvalidAndThrowAnException()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOfferValues));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, null);

	doThrow(new ConstraintViolationException("")).when(dao).create(
		Arrays.asList(validRoomOfferValues));
	when(isOccupancyIn48Hours.isSatisfiedBy((Date) any())).thenReturn(true);

	roomOfferService.createRoomOffer(command);

	verify(dao, never()).create(Arrays.asList(validRoomOfferValues));
    }

    /**
     * Should delete the room if the index is found in the database.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldDeleteTheRoomIfTheIndexIsFoundInTheDatabase()
	    throws Exception {

	final DeleteRoomCommand command = new DeleteRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);

	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);

	final int deletedRoomIndex = roomOfferService.deleteRoomOffer(command);

	verify(dao).lock(anyInt());
	verify(dao).unlock(anyInt(), anyLong());
	verify(dao).delete(eq(validRoomOffer.getIndex()), anyLong());
	assertThat(deletedRoomIndex, is(equalTo(deletedRoomIndex)));
    }

    /**
     * Should not delete the room if the index is not found in the database and
     * thorw an exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldNotDeleteTheRoomIfTheIndexIsNotFoundInTheDatabaseAndThorwAnException()
	    throws Exception {

	final DeleteRoomCommand command = new DeleteRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	doThrow(new RecordNotFoundException("Test")).when(dao).delete(anyInt(),
		anyLong());
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);

	roomOfferService.deleteRoomOffer(command);

	verify(dao).lock(anyInt());
	verify(dao).unlock(anyInt(), anyLong());
	verify(dao).delete(eq(validRoomOffer.getIndex()), anyLong());
    }

    /**
     * Should find all matching rooms with the dao and return it to the client.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldFindAllMatchingRoomsWithTheDaoAndReturnItToTheClient()
	    throws Exception {

	final ArrayList<String> criteria = Lists.newArrayList(null, null, null,
		null, null, null, null, null);
	final FindRoomCommand command = new FindRoomCommand(criteria.get(0),
		criteria.get(1), true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, null, null, null);

	final List<UrlyBirdRoomOffer> result = Lists
		.newArrayList(validRoomOffer);
	when(dao.find(criteria)).thenReturn(result);

	final List<UrlyBirdRoomOffer> foundRooms = roomOfferService
		.findRoomOffer(command);

	assertThat(foundRooms, is(equalTo(result)));
    }

    /**
     * Should inform the client with an exception if the find causes an
     * exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldInformTheClientWithAnExceptionIfTheFindCausesAnException()
	    throws Exception {

	final ArrayList<String> criteria = Lists.newArrayList(null, null, null,
		null, null, null, null, null);
	final FindRoomCommand command = new FindRoomCommand(criteria.get(0),
		criteria.get(1), true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, null, null, null);

	doThrow(new RuntimeException()).when(dao).find(criteria);

	roomOfferService.findRoomOffer(command);
    }

    /**
     * Should update the spezified valid room and persist the changes.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldUpdateTheSpezifiedValidRoomAndPersistTheChanges()
	    throws Exception {

	final ArrayList<String> newValidValues = Lists.newArrayList("Hilton",
		"Hamburg", "2", "N", "12", "12.02.2007", "");

	final UpdateRoomCommand command = new UpdateRoomCommand(newValidValues,
		validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);
	when(builder.createRoomOffer(newValidValues, validRoomOffer.getIndex()))
		.thenReturn(validRoomOffer);

	final UrlyBirdRoomOffer updatedRoomOffer = roomOfferService
		.updateRoomOffer(command);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
	assertThat(updatedRoomOffer, is(equalTo(validRoomOffer)));
    }

    /**
     * Should inform the client with an exception if the update causes an
     * exception.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = Exception.class)
    public void shouldInformTheClientWithAnExceptionIfTheUpdateCausesAnException()
	    throws Exception {

	final ArrayList<String> newValidValues = Lists.newArrayList("Hilton",
		"Hamburg", "2", "N", "12", "12.02.2007", "");

	final UpdateRoomCommand command = new UpdateRoomCommand(newValidValues,
		validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);
	when(builder.createRoomOffer(newValidValues, validRoomOffer.getIndex()))
		.thenReturn(validRoomOffer);
	doThrow(new RuntimeException()).when(dao).update(eq(validRoomOffer),
		anyLong());

	roomOfferService.updateRoomOffer(command);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
    }

}
