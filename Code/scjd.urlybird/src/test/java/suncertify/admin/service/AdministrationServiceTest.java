package suncertify.admin.service;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.common.ClientCallback;
import suncertify.common.ClientServices;
import suncertify.common.roomoffer.FindRoomCallback;
import suncertify.common.roomoffer.FindRoomCommand;
import suncertify.common.roomoffer.RoomOffer;
import suncertify.common.roomoffer.RoomOfferService;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public final class AdministrationServiceTest {

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
    public void shouldStartAnServerAnBindsTheClientServicesAtTheGivenAdresse()
	    throws Exception {

	final AdministrationService service = new AdministrationService();

	final ServerConfiguration serverConfig = new ServerConfiguration();
	final DatabaseConfiguration dataConfig = new DatabaseConfiguration(
		anyFile);

	service.startStandAloneServer(serverConfig, dataConfig);

	Thread.sleep(2000);

	final ClientServices services = (ClientServices) Naming
		.lookup(serverConfig.getClientServiceName());

	final RoomOfferService roomOfferService = services
		.getRoomOfferService();
	roomOfferService.findRoomOffer(
		new FindRoomCommand(Lists.<String> newArrayList()),
		new Callback());

    }

    private static final class Callback implements FindRoomCallback {

	@Override
	public void onFailure(final String message) {
	    System.out.println(message);
	}

	@Override
	public boolean onWarning(final String message) {
	    // TODO Auto-generated method stub
	    return false;
	}

	@Override
	public void onSuccess(final List<RoomOffer> result) {
	    System.out.println(result);
	}

    }
}
