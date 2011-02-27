package suncertify.admin.service;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.common.ClientCallback;
import suncertify.common.ClientServices;
import suncertify.common.roomoffer.CreateRoomCommand;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOfferService;
import suncertify.domain.RoomOffer;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public final class AdministrationServiceIntegrationTest {

    File anyFile;

    @Before
    public void setUp() throws IOException {
	anyFile = File.createTempFile("test", "test");
	Files.copy(
		new File(
			"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db"),
		anyFile);
    }

    @After
    public void tearDown() {
	if (anyFile != null) {
	    anyFile.delete();
	}
    }

    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanFindRooms()
	    throws Exception {

	final AdministrationService service = new AdministrationService();

	final ServerConfiguration serverConfig = new ServerConfiguration();
	final DatabaseConfiguration dataConfig = new DatabaseConfiguration(
		anyFile);

	service.startStandAloneServer(serverConfig, dataConfig);

	final ClientServices services = (ClientServices) Naming
		.lookup(serverConfig.getClientServiceName());

	final RoomOfferService roomOfferService = services
		.getRoomOfferService();
	roomOfferService.findRoomOffer(new FindRoomCommand("Dew Drop Inn",
		"Pleasantville", true));

	service.stopServer();
    }

    @Test
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresseSoThatTheClientCanCreateARoom()
	    throws Exception {

	final AdministrationService service = new AdministrationService();

	final ServerConfiguration serverConfig = new ServerConfiguration();
	final DatabaseConfiguration dataConfig = new DatabaseConfiguration(
		anyFile);

	service.startStandAloneServer(serverConfig, dataConfig);

	final ClientServices services = (ClientServices) Naming
		.lookup(serverConfig.getClientServiceName());

	final RoomOfferService roomOfferService = services
		.getRoomOfferService();
	roomOfferService.createRoomOffer(new CreateRoomCommand(Lists
		.newArrayList("MyHotel")));

	service.stopServer();
    }

}
