package suncertify.common.roomoffer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FindRoomCommand {

    private final Map<String, String> criteria;

    public FindRoomCommand(final Map<String, String> criteria) {
	super();
	this.criteria = new HashMap<String, String>(criteria);
    }

    public Map<String, String> getCriteria() {
	return Collections.unmodifiableMap(criteria);
    }

}
