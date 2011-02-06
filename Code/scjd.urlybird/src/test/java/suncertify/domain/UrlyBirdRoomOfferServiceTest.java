package suncertify.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import suncertify.common.ClientCallback;
import suncertify.common.roomoffer.BookRoomCommand;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.Money;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.domain.RoomOfferBuilder.DefaultBuilder;

public class UrlyBirdRoomOfferServiceTest {

    @SuppressWarnings("unchecked")
    final Dao<RoomOffer> dao = mock(Dao.class);
    final RoomOfferBuilder builder = new RoomOfferBuilder();
    @SuppressWarnings("unchecked")
    final BusinessRule<CreateRoomCommand> isOccupancyIn48Hours = mock(BusinessRule.class);
    @SuppressWarnings("unchecked")
    final BusinessRule<RoomOffer> isRoomBookable = mock(BusinessRule.class);
    final RoomOffer validRoomOffer = new RoomOffer("Hilton", "Hamburg", 2,
	    false, Money.create("12"), new Date(), "", 12);
    @SuppressWarnings("unchecked")
    final ClientCallback<RoomOffer> clientCallback = mock(ClientCallback.class);

    @Test
    public void shouldBookAnValidRoomAndInformTheClientWithTheOnSuccessMethod()
	    throws ConstraintViolationException {

	final String customerId = "1234";
	final RoomOffer expectedRoomOffer = builder.copyOf(validRoomOffer)
		.bookedBy(customerId).build();
	final UrlyBirdRoomOfferService roomOfferService = new UrlyBirdRoomOfferService(
		dao, builder, null, isRoomBookable);

	final BookRoomCommand bookRoomCommand = new BookRoomCommand(
		validRoomOffer.getIndex(), customerId);
	when(isRoomBookable.isSatisfiedBy(validRoomOffer)).thenReturn(true);
	when(dao.read(bookRoomCommand.getRoomOfferIndex())).thenReturn(
		validRoomOffer);

	roomOfferService.bookRoomOffer(bookRoomCommand, clientCallback);

	verify(isRoomBookable).isSatisfiedBy(validRoomOffer);
	verify(dao).lock(validRoomOffer.getIndex());
	verify(dao).read(validRoomOffer.getIndex());
	verify(dao).update(eq(expectedRoomOffer), anyLong());
	verify(dao).unlock(eq(validRoomOffer.getIndex()), anyLong());
	verify(clientCallback).onSuccess(expectedRoomOffer);
    }
}
