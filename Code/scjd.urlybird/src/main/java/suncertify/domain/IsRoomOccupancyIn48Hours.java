package suncertify.domain;

import java.util.Calendar;
import java.util.Date;

public class IsRoomOccupancyIn48Hours implements BusinessRule<Date> {

    @Override
    public boolean isSatisfiedBy(final Date dateToBook) {

	final Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_MONTH, -2);

	return (calendar.before(dateToBook));
    }

}
