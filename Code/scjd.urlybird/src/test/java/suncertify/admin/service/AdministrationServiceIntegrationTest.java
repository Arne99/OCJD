package suncertify.admin.service;

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

import suncertify.common.ClientServices;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.db.DatabaseConnectionException;
import suncertify.domain.RoomOffer;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public final class AdministrationServiceIntegrationTest {

    File anyFile;
    private AdministrationService service;
    private RoomOfferService roomOfferService;

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

	final ClientServices services = (ClientServices) Naming
		.lookup(serverConfig.getClientServiceName());

	roomOfferService = services.getRoomOfferService();
    }

    @After
    public void tearDown() throws RemoteException, MalformedURLException,
	    NotBoundException {
	if (anyFile != null) {
	    anyFile.delete();
	}
	service.stopServer();
    }

    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanFindRooms()
	    throws Exception {

	final List<RoomOffer> roomOffers = roomOfferService
		.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
			"Pleasantville", true));

	assertThat(roomOffers.size(), is(equalTo(1)));
    }

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

}
