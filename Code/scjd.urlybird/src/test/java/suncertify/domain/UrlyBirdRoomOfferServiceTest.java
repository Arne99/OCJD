package suncertify.domain;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import suncertify.common.ClientCallback;
import suncertify.common.Money;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.DeleteRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.UpdateRoomCommand;
import suncertify.db.RecordNotFoundException;
import suncertify.domain.RoomOfferBuilder.DefaultBuilder;

public class UrlyBirdRoomOfferServiceTest {

    @SuppressWarnings("unchecked")
    final Dao<RoomOffer> dao = mock(Dao.class);
    final RoomOfferBuilder builder = mock(RoomOfferBuilder.class);
    final DefaultBuilder defaultBuilder = mock(DefaultBuilder.class);
    @SuppressWarnings("unchecked")
    final BusinessRule<RoomOffer> isOccupancyIn48Hours = mock(BusinessRule.class);
    @SuppressWarnings("unchecked")
    final BusinessRule<RoomOffer> isRoomBookable = mock(BusinessRule.class);
    final RoomOffer validRoomOffer = new RoomOffer("Hilton", "Hamburg", 2,
	    false, Money.create("12"), new Date(), "", 12);
    final RoomOffer invalidRoomOffer = new RoomOffer("", "Hamburg", 2, false,
	    Money.create("12"), new Date(), "", 12);
    @SuppressWarnings("unchecked")
    final ClientCallback<RoomOffer> clientCallback = mock(ClientCallback.class);

    @Before
    public void setUp() {
	when(builder.copyOf(validRoomOffer)).thenReturn(defaultBuilder);
	when(builder.newRoomOffer()).thenReturn(defaultBuilder);
	when(defaultBuilder.bookedBy(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.bookableAt(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.withPrice(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.withIndex(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.fromHotel(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.fromCity(anyString())).thenReturn(defaultBuilder);
	when(defaultBuilder.smokingAllowed(anyString())).thenReturn(
		defaultBuilder);
	when(defaultBuilder.ofSize(anyString())).thenReturn(defaultBuilder);
    }

    @Test
    public void shouldBookAnValidRoomAndPersistTheChanges() throws Exception {

	final String customerId = "1234";
	final RoomOffer expectedRoomOffer = new RoomOffer(
		validRoomOffer.getHotel(), validRoomOffer.getCity(),
		validRoomOffer.getRoomSize(), false, validRoomOffer.getPrice(),
		validRoomOffer.getBookableDate(), customerId,
		validRoomOffer.getIndex());
	final BookRoomCommand bookRoomCommand = new BookRoomCommand(
		validRoomOffer, customerId);

	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(dao.read(bookRoomCommand.getRoomToBook().getIndex())).thenReturn(
		validRoomOffer);
	when(defaultBuilder.build()).thenReturn(expectedRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	final RoomOffer bookedRoom = roomOfferService
		.bookRoomOffer(bookRoomCommand);

	verify(isRoomBookable).isSatisfiedBy(validRoomOffer);
	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).read(validRoomOffer.getIndex());
	verify(dao).update(eq(expectedRoomOffer), anyLong());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	assertThat(bookedRoom, is(equalTo(expectedRoomOffer)));
    }

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

    @Test(expected = Exception.class)
    public void shouldNotBookAnInvalidRoomAndInformTheClientWithAnException()
	    throws Exception {

	final int index = 1;
	final String customerId = "12";
	final BookRoomCommand command = new BookRoomCommand(validRoomOffer,
		customerId);

	when(dao.read(index)).thenReturn(validRoomOffer);
	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(command);
    }

    @Test
    public void shouldCreateAnValidRoomAndPersistIt() throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		true);

	final RoomOffer createdRoomOffer = roomOfferService
		.createRoomOffer(command);

	verify(dao).create(validRoomOffer);
	assertThat(createdRoomOffer, is(equalTo(validRoomOffer)));
    }

    @Test(expected = Exception.class)
    public void shouldThrowAnExceptionIfTheOccupanyIsNotIn48Hours()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		false);
	when(clientCallback.onWarning(anyString())).thenReturn(true);

	roomOfferService.createRoomOffer(command);

	verify(dao, never()).create(validRoomOffer);
    }

    @Test(expected = Exception.class)
    public void shouldNotCreateTheRoomIfTheGivenRoomIsInvalidAndThrowAnException()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, null);

	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		true);

	roomOfferService.createRoomOffer(command);

	verify(dao, never()).create(validRoomOffer);
    }

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

    @Test(expected = Exception.class)
    public void shouldNotDeleteTheRoomIfTheIndexIsNotFoundInTheDatabaseAndThorwAnException()
	    throws Exception {

	final DeleteRoomCommand command = new DeleteRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	@SuppressWarnings("unchecked")
	final ClientCallback<Integer> deleteCallback = mock(ClientCallback.class);
	doThrow(new RecordNotFoundException("Test")).when(dao).delete(anyInt(),
		anyLong());
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);

	roomOfferService.deleteRoomOffer(command);

	verify(dao).lock(anyInt());
	verify(dao).unlock(anyInt(), anyLong());
	verify(dao).delete(eq(validRoomOffer.getIndex()), anyLong());
    }

    @Test
    public void shouldFindAllMatchingRoomsWithTheDaoAndReturnItToTheClient()
	    throws Exception {

	final ArrayList<String> criteria = Lists.newArrayList(null, null, null,
		null, null, null, null, null);
	final FindRoomCommand command = new FindRoomCommand(criteria.get(0),
		criteria.get(1), true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, null, null, null);

	final List<RoomOffer> result = Lists.newArrayList(validRoomOffer);
	when(dao.find(criteria)).thenReturn(result);

	final List<RoomOffer> foundRooms = roomOfferService
		.findRoomOffer(command);

	assertThat(foundRooms, is(equalTo(result)));
    }

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

    @Test
    public void shouldUpdateTheSpezifiedValidRoomAndPersistTheChanges()
	    throws Exception {

	final ArrayList<String> newValidValues = Lists.newArrayList("Hilton",
		"Hamburg", "2", "N", "12", "12.02.2007", "", "12");

	final UpdateRoomCommand command = new UpdateRoomCommand(newValidValues,
		validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);

	final RoomOffer updatedRoomOffer = roomOfferService
		.updateRoomOffer(command);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
	assertThat(updatedRoomOffer, is(equalTo(validRoomOffer)));
    }

    @Test(expected = Exception.class)
    public void shouldInformTheClientWithAnExceptionIfTheUpdateCausesAnException()
	    throws Exception {

	final ArrayList<String> newValidValues = Lists.newArrayList("Hilton",
		"Hamburg", "2", "N", "12", "12.02.2007", "", "12");

	final UpdateRoomCommand command = new UpdateRoomCommand(newValidValues,
		validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);
	doThrow(new RuntimeException()).when(dao).update(eq(validRoomOffer),
		anyLong());

	roomOfferService.updateRoomOffer(command);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
    }

    @Test(expected = Exception.class)
    public void shouldInformTheClientWithAnExceptionIfTheGivenRoomToUpdateIsNotValid()
	    throws Exception {

	final ArrayList<String> newInvalidValues = Lists.newArrayList("Hilton",
		"", "2", "N", "12", "12.02.2007", "", "12");

	final UpdateRoomCommand command = new UpdateRoomCommand(
		newInvalidValues, validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(dao.read(validRoomOffer.getIndex())).thenReturn(validRoomOffer);
	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));

	roomOfferService.updateRoomOffer(command);
    }
}
