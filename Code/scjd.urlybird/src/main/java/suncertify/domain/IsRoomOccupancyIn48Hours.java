package suncertify.domain;

import java.util.Calendar;
import java.util.Date;

import suncertify.common.Specification;

/**
 * Business rule that specifies if the occupancy of the given room is in the
 * next 48 hours.
 * 
 * @author arnelandwehr
 * 
 */
final class IsRoomOccupancyIn48Hours implements Specification<Date> {

    @Override
    public boolean isSatisfiedBy(final Date dateToBook) {

	final Calendar inTwoDays = Calendar.getInstance();
	inTwoDays.add(Calendar.DAY_OF_MONTH, +2);
	inTwoDays.set(Calendar.HOUR_OF_DAY, 0);
	inTwoDays.set(Calendar.MINUTE, 0);
	inTwoDays.set(Calendar.SECOND, 0);
	inTwoDays.set(Calendar.MILLISECOND, 0);

	final Calendar today = Calendar.getInstance();
	today.set(Calendar.HOUR_OF_DAY, 0);
	today.set(Calendar.MINUTE, 0);
	today.set(Calendar.SECOND, 0);
	today.set(Calendar.MILLISECOND, 0);

	return (inTwoDays.getTime().after(dateToBook) && (today.getTime()
		.equals(dateToBook) || today.getTime().before(dateToBook)));
    }
}
