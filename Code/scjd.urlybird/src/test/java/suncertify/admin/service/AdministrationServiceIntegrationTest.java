package suncertify.admin.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.common.BookRoomCommand;
import suncertify.common.CreateRoomCommand;
import suncertify.common.DeleteRoomCommand;
import suncertify.common.FindRoomCommand;
import suncertify.common.RoomOffer;
import suncertify.common.RoomOfferService;
import suncertify.common.Services;
import suncertify.common.UpdateRoomCommand;
import suncertify.db.DatabaseConnectionException;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * The Class AdministrationServiceIntegrationTest.
 */
public final class AdministrationServiceIntegrationTest {

    /** The any file. */
    private File anyFile;

    /** The service. */
    private AdministrationService service;

    /** The room offer service. */
    private RoomOfferService roomOfferService;

    /**
     * Sets the up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws DatabaseConnectionException
     *             the database connection exception
     * @throws NotBoundException
     *             the not bound exception
     */
    @Before
    public void setUp() throws IOException, DatabaseConnectionException,
	    NotBoundException {
	anyFile = File.createTempFile("test", "test");
	Files.copy(
		new File(
			"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db"),
		anyFile);

	service = new AdministrationService();

	final ServerConfiguration serverConfig = new ServerConfiguration();
	final DatabaseConfiguration dataConfig = new DatabaseConfiguration(
		anyFile);

	service.startStandAloneServer(serverConfig, dataConfig);

	final Services services = (Services) Naming.lookup(serverConfig
		.getClientServiceName());

	roomOfferService = services.getRoomOfferService();
    }

    /**
     * Tear down.
     * 
     * @throws RemoteException
     *             the remote exception
     * @throws MalformedURLException
     *             the malformed url exception
     * @throws NotBoundException
     *             the not bound exception
     */
    @After
    public void tearDown() throws RemoteException, MalformedURLException,
	    NotBoundException {
	if (anyFile != null) {
	    anyFile.delete();
	}
	service.stopServer();
    }

    /**
     * Should start an server an binds the client services at the given adresse
     * so that the client can find rooms.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanFindRooms()
	    throws Exception {

	final List<RoomOffer> roomOffers = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffers.size(), is(equalTo(1)));
    }

    /**
     * Should start an server an binds the client services at the given adresse
     * so that the client can create a room.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanCreateARoom()
	    throws Exception {

	roomOfferService.createRoomOffer(new CreateRoomCommand(Lists
		.newArrayList("MyHotel", "MyCity", "4", "Y", "$120.00",
			new SimpleDateFormat("yyyy/MM/dd").format(new Date()),
			"12345678")));

	final List<RoomOffer> roomOffers = roomOfferService
		.findRoomOffer(new FindRoomCommand("MyHotel", "MyCity", true));

	assertThat(roomOffers.size(), is(equalTo(1)));
    }

    /**
     * Should start an server an binds the client services at the given adresse
     * so that the client can delete a room.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanDeleteARoom()
	    throws Exception {

	final List<RoomOffer> roomOffersBefore = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersBefore.size(), is(equalTo(1)));

	roomOfferService.deleteRoomOffer(new DeleteRoomCommand(roomOffersBefore
		.iterator().next()));

	final List<RoomOffer> roomOffersAfter = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersAfter.size(), is(equalTo(0)));
    }

    /**
     * Should start an server an binds the client services at the given adresse
     * so that the client can update a room.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanUpdateARoom()
	    throws Exception {

	final List<RoomOffer> roomOffersBefore = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersBefore.size(), is(equalTo(1)));

	roomOfferService.updateRoomOffer(new UpdateRoomCommand(Lists
		.newArrayList(Lists.newArrayList("MyHotel", "MyCity", "4", "Y",
			"$120.00",
			new SimpleDateFormat("yyyy/MM/dd").format(new Date()),
			"12345678")), roomOffersBefore.iterator().next()));

	final List<RoomOffer> roomOffersAfter = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersAfter.size(), is(equalTo(0)));

	final List<RoomOffer> roomOffersAfter2 = roomOfferService
		.findRoomOffer(new FindRoomCommand("MyHotel", "MyCity", true));

	assertThat(roomOffersAfter2.size(), is(equalTo(1)));
    }

    /**
     * Should start an server an binds the client services at the given adresse
     * so that the client can book a room.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanBookARoom()
	    throws Exception {

	final List<RoomOffer> roomOffersBefore = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersBefore.size(), is(equalTo(1)));

	roomOfferService.bookRoomOffer(new BookRoomCommand(roomOffersBefore
		.iterator().next(), "12345678"));

	final List<RoomOffer> roomOffersAfter = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffersAfter.size(), is(equalTo(1)));

	assertThat(roomOffersAfter.iterator().next().getCustomerId(),
		is(equalTo("12345678")));
    }

}
