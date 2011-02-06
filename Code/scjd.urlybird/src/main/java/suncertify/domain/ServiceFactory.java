package suncertify.domain;

import suncertify.common.UrlyBirdServiceFactory;
import suncertify.common.roomoffer.RoomOfferService;

public class ServiceFactory implements UrlyBirdServiceFactory {

    @Override
    public RoomOfferService getRoomOfferService() {
	return new UrlyBirdRoomOfferService(null, null, null, null);
    }

}
