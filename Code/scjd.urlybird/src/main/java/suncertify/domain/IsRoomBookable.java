package suncertify.domain;

import suncertify.common.RoomOffer;

public class IsRoomBookable implements BusinessRule<RoomOffer> {

    @Override
    public boolean isSatisfiedBy(final RoomOffer roomOffer) {

	return roomOffer.getCustomerId() == null
		|| roomOffer.getCustomerId().equals("");
    }

}
