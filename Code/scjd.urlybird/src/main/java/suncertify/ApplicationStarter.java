package suncertify;

import java.io.IOException;
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
import suncertify.common.roomoffer.RoomOfferService;

public class ApplicationStarter {

    private static final String STAND_ALONE_SERVER = "server";
    private static final String EMBEDDED_SERVER = "alone";

    public static void main(final String[] args) throws IOException {

	final AdministrationService administrationService = new AdministrationService();

	if (args.length == 0) {

	    final ServerConnectionPresenter serverConnectionPresenter = new ServerConnectionPresenter();
	    final RoomOfferService service = serverConnectionPresenter
		    .startInitialConnectionDialogToFindService();

	    new UrlyBirdPresenter(new UrlyBirdView(),
		    UrlyBirdProperties.getInstance(), service).startGui();

	}
	if (args.length == 1) {
	    if (args[0].equals(EMBEDDED_SERVER)) {

		final DatabaseConnectionPresenter databaseConnectionPresenter = new DatabaseConnectionPresenter(
			new DatabaseConnectionPanel(), administrationService);
		final DatabaseConfiguration databaseConfiguration = databaseConnectionPresenter
			.askUserForInitialDatabaseConfiguration();

		RoomOfferService roomOfferService = null;
		try {
		    roomOfferService = administrationService
			    .startEmbeddedServer(databaseConfiguration);
		} catch (final Exception e) {
		    e.printStackTrace();
		}
		new UrlyBirdPresenter(new UrlyBirdView(),
			UrlyBirdProperties.getInstance(), roomOfferService)
			.startGui();

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
