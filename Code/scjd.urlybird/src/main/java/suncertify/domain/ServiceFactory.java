package suncertify.domain;

import suncertify.common.RoomOfferService;
import suncertify.common.UrlyBirdServiceFactory;

public class ServiceFactory implements UrlyBirdServiceFactory {

    @Override
    public RoomOfferService getRoomOfferService() {
	return new UrlyBirdRoomOfferService(null, null, null, null);
    }

}
