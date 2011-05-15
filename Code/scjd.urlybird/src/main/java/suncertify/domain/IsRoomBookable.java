package suncertify.domain;

import suncertify.common.RoomOffer;
import suncertify.util.Specification;

/**
 * Business rule that specifies if a {@link RoomOffer} is bookable or not. A
 * <code>RoomOffer</code> could be booked if it is not already booked by an
 * other customer.
 * 
 * @author arnelandwehr
 * 
 */
final class IsRoomBookable implements Specification<RoomOffer> {

    @Override
    public boolean isSatisfiedBy(final RoomOffer roomOffer) {

	return roomOffer.getCustomerId() == null
		|| roomOffer.getCustomerId().equals("");
    }

}
