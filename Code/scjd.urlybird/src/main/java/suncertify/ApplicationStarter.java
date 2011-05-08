package suncertify;

import java.io.IOException;

import javax.swing.JFrame;

import suncertify.admin.gui.ServerAdminGui;
import suncertify.admin.gui.ServerAdminPresenter;
import suncertify.admin.gui.UrlyBirdProperties;
import suncertify.admin.service.AdministrationService;
import suncertify.admin.service.DatabaseConfiguration;
import suncertify.client.DatabaseConnectionPanel;
import suncertify.client.DatabaseConnectionPresenter;
import suncertify.client.ServerConnectionPresenter;
import suncertify.client.UrlyBirdPresenter;
import suncertify.client.UrlyBirdView;
import suncertify.common.RoomOfferService;
import suncertify.db.DB;
import suncertify.db.DatabaseService;
import suncertify.domain.UrlyBirdRoomOfferService;

public class ApplicationStarter {

    private static final String STAND_ALONE_SERVER = "server";
    private static final String STAND_ALONE_CLIENT = "alone";

    public static void main(final String[] args) throws IOException {

	if (args.length == 0) {

	    final ServerConnectionPresenter serverConnectionPresenter = new ServerConnectionPresenter();
	    final JFrame frame = new JFrame();
	    final RoomOfferService service = serverConnectionPresenter
		    .startInitialConnectionDialogToFindService(frame);
	    frame.dispose();

	    new UrlyBirdPresenter(new UrlyBirdView(),
		    UrlyBirdProperties.getInstance(), service,
		    new ServerConnectionPresenter()).startGui();

	}
	if (args.length == 1) {
	    if (args[0].equals(STAND_ALONE_CLIENT)) {

		final DatabaseConnectionPresenter databaseConnectionPresenter = new DatabaseConnectionPresenter(
			new DatabaseConnectionPanel(),
			DatabaseService.instance());
		final JFrame frame = new JFrame();
		final RoomOfferService roomOfferService = databaseConnectionPresenter
			.connectToDatabaseWithDialog(frame);
		frame.dispose();

		new UrlyBirdPresenter(new UrlyBirdView(),
			UrlyBirdProperties.getInstance(), roomOfferService,
			new DatabaseConnectionPresenter(
				new DatabaseConnectionPanel(),
				DatabaseService.instance())).startGui();

	    }
	    if (args[0].equals(STAND_ALONE_SERVER)) {

		final ServerAdminGui view = new ServerAdminGui();
		final ServerAdminPresenter serverAdminPresenter = new ServerAdminPresenter(
			view, new AdministrationService(),
			UrlyBirdProperties.getInstance());
		serverAdminPresenter.startGui();
	    }
	}
    }
}
