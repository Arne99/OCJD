package suncertify.common.roomoffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import suncertify.common.Command;
import suncertify.domain.Criteria;

public final class FindRoomCommand implements Command, Criteria<RoomOffer> {

    private static final long serialVersionUID = 6794005890219300147L;

    private final List<String> criteria;

    public FindRoomCommand(final List<String> criteria) {
	super();
	this.criteria = new ArrayList<String>(criteria);
    }

    public List<String> getCriteria() {
	return Collections.unmodifiableList(criteria);
    }
}
