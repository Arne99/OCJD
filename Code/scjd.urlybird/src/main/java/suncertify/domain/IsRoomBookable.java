package suncertify.domain;

import suncertify.common.RoomOffer;
import suncertify.util.Specification;

public class IsRoomBookable implements Specification<RoomOffer> {

    @Override
    public boolean isSatisfiedBy(final RoomOffer roomOffer) {

	return roomOffer.getCustomerId() == null
		|| roomOffer.getCustomerId().equals("");
    }

}
