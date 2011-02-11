package suncertify.common;

import java.rmi.Remote;

import suncertify.db.DB;
import suncertify.domain.UrlyBirdRoomOfferService;

public final class ClientServices implements Remote {

    private final DB database;

    public ClientServices(final DB database) {
	super();
	this.database = database;
    }

    Remote getRoomOfferService() {
	return new UrlyBirdRoomOfferService(database);
    }
}
