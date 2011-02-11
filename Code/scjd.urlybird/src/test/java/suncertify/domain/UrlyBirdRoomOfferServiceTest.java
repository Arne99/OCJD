package suncertify.domain;

import static org.mockito.Mockito.*;

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
import suncertify.common.roomoffer.RoomOffer;
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
    public void shouldBookAnValidRoomAndInformTheClientWithTheOnSuccessMethod()
	    throws Exception {

	final String customerId = "1234";
	final RoomOffer expectedRoomOffer = new RoomOffer(
		validRoomOffer.getHotel(), validRoomOffer.getCity(),
		validRoomOffer.getRoomSize(), false, validRoomOffer.getPrice(),
		validRoomOffer.getBookableDate(), customerId,
		validRoomOffer.getIndex());
	final BookRoomCommand bookRoomCommand = new BookRoomCommand(
		validRoomOffer.getIndex(), customerId);

	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(dao.read(bookRoomCommand.getRoomOfferIndex())).thenReturn(
		validRoomOffer);
	when(defaultBuilder.build()).thenReturn(expectedRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(bookRoomCommand, clientCallback);

	verify(isRoomBookable).isSatisfiedBy(validRoomOffer);
	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).read(validRoomOffer.getIndex());
	verify(dao).update(eq(expectedRoomOffer), anyLong());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(clientCallback).onSuccess(expectedRoomOffer);
    }

    @Test
    public void shouldNotBookAnAlreadyBookedRoomAndInformTheClientWithTheOnFailureMethod()
	    throws Exception {

	final int index = 1;
	final BookRoomCommand command = new BookRoomCommand(index, "12");
	when(dao.read(index)).thenReturn(validRoomOffer);
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(false);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(command, clientCallback);

	verify(clientCallback).onFailure(anyString());
    }

    @Test
    public void shouldNotBookAnInvalidRoomAndInformTheClientWithTheOnFailureMethod()
	    throws Exception {

	final int index = 1;
	final String customerId = "12";
	final BookRoomCommand command = new BookRoomCommand(index, customerId);

	when(dao.read(index)).thenReturn(validRoomOffer);
	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	roomOfferService.bookRoomOffer(command, clientCallback);

	verify(clientCallback).onFailure(anyString());
    }

    @Test
    public void shouldCreateAnValidRoomAndInformTheClientWithTheOnSuccessMethod()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		true);

	roomOfferService.createRoomOffer(command, clientCallback);

	verify(dao).create(validRoomOffer);
	verify(clientCallback).onSuccess(validRoomOffer);
    }

    @Test
    public void shouldCallTheOnWarningCallbackMethodIfTheOccupanyIsNotIn48HoursAndCreateTheRoomIfTheCallbackReturnsTrue()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		false);
	when(clientCallback.onWarning(anyString())).thenReturn(true);

	roomOfferService.createRoomOffer(command, clientCallback);

	verify(dao).create(validRoomOffer);
	verify(clientCallback).onWarning(anyString());
	verify(clientCallback).onSuccess(validRoomOffer);
    }

    @Test
    public void shouldCallTheOnWarningCallbackMethodIfTheOccupanyIsNotIn48HoursAndMustNotCrateARoomIfTheCallbackReturnsFalse()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, isRoomBookable);

	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		false);
	when(clientCallback.onWarning(anyString())).thenReturn(false);

	roomOfferService.createRoomOffer(command, clientCallback);

	verify(dao, never()).create(validRoomOffer);
	verify(clientCallback).onWarning(anyString());
	verify(clientCallback, never()).onSuccess(validRoomOffer);
    }

    @Test
    public void shouldNotCreateTheRoomIfTheGivenRoomIsInvalidAndInformTheClientWithTheOnFailureMethod()
	    throws Exception {

	final CreateRoomCommand command = new CreateRoomCommand(
		Arrays.asList(validRoomOffer.toArray()));

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, isOccupancyIn48Hours, null);

	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));
	when(isOccupancyIn48Hours.isSatisfiedBy(validRoomOffer)).thenReturn(
		true);

	roomOfferService.createRoomOffer(command, clientCallback);

	verify(dao, never()).create(validRoomOffer);
	verify(clientCallback, never()).onSuccess(validRoomOffer);
	verify(clientCallback).onFailure(anyString());
    }

    @Test
    public void shouldDeleteTheRoomIfTheIndexIsFoundInTheDatabase()
	    throws Exception {

	final DeleteRoomCommand command = new DeleteRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	@SuppressWarnings("unchecked")
	final ClientCallback<Integer> deleteCallback = mock(ClientCallback.class);

	roomOfferService.deleteRoomOffer(command, deleteCallback);

	verify(dao).lock(anyInt());
	verify(dao).unlock(anyInt(), anyLong());
	verify(dao).delete(eq(validRoomOffer.getIndex()), anyLong());
	verify(deleteCallback).onSuccess(validRoomOffer.getIndex());
    }

    @Test
    public void shouldNotDeleteTheRoomIfTheIndexIsNotFoundInTheDatabaseAndInformTheClientWithTheOnFailureMethod()
	    throws Exception {

	final DeleteRoomCommand command = new DeleteRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	@SuppressWarnings("unchecked")
	final ClientCallback<Integer> deleteCallback = mock(ClientCallback.class);
	doThrow(new RecordNotFoundException("Test")).when(dao).delete(anyInt(),
		anyLong());

	roomOfferService.deleteRoomOffer(command, deleteCallback);

	verify(dao).lock(anyInt());
	verify(dao).unlock(anyInt(), anyLong());
	verify(dao).delete(eq(validRoomOffer.getIndex()), anyLong());
	verify(deleteCallback).onFailure(anyString());
    }

    @Test
    public void shouldFindAllMatchingRoomsWithTheDaoAndReturnItToTheClientWithTheOnSuccessMethod()
	    throws RecordNotFoundException, ConstraintViolationException {

	final ArrayList<String> criteria = Lists.newArrayList();
	final FindRoomCommand command = new FindRoomCommand(criteria);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, null, null, null);
	@SuppressWarnings("unchecked")
	final ClientCallback<List<RoomOffer>> callback = mock(ClientCallback.class);

	final ArrayList<RoomOffer> result = Lists.newArrayList(validRoomOffer);
	when(dao.find(criteria)).thenReturn(result);

	roomOfferService.findRoomOffer(command, callback);

	verify(callback).onSuccess(result);
    }

    @Test
    public void shouldInformTheClientWithTheOnFailureMethodIfTheFindCausesAnException()
	    throws RecordNotFoundException, ConstraintViolationException {

	final ArrayList<String> criteria = Lists.newArrayList();
	final FindRoomCommand command = new FindRoomCommand(criteria);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, null, null, null);
	@SuppressWarnings("unchecked")
	final ClientCallback<List<RoomOffer>> callback = mock(ClientCallback.class);

	doThrow(new RuntimeException()).when(dao).find(criteria);

	roomOfferService.findRoomOffer(command, callback);

	verify(callback).onFailure(anyString());
    }

    @Test
    public void shouldUpdateTheSpezifiedValidRoomAndInformTheClientWithTheOnSuccessMethod()
	    throws Exception {

	final UpdateRoomCommand command = new UpdateRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);

	roomOfferService.updateRoomOffer(command, clientCallback);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
	verify(clientCallback).onSuccess(validRoomOffer);
    }

    @Test
    public void shouldInformTheClientWithTheOnFailureMethodIfTheUpdateCausesAnException()
	    throws Exception {

	final UpdateRoomCommand command = new UpdateRoomCommand(validRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(defaultBuilder.build()).thenReturn(validRoomOffer);
	doThrow(new RuntimeException()).when(dao).update(eq(validRoomOffer),
		anyLong());

	roomOfferService.updateRoomOffer(command, clientCallback);

	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(dao).update(eq(validRoomOffer), anyLong());
	verify(clientCallback).onFailure(anyString());
    }

    @Test
    public void shouldInformTheClientWithTheOnFailureMethodIfTheGivenRoomIsNotValid()
	    throws Exception {

	final UpdateRoomCommand command = new UpdateRoomCommand(
		invalidRoomOffer);

	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, null);
	when(defaultBuilder.build()).thenThrow(
		new ConstraintViolationException("Test"));

	roomOfferService.updateRoomOffer(command, clientCallback);

	verify(builder).copyOf(invalidRoomOffer);
	verify(clientCallback).onFailure(anyString());
    }
}
