package suncertify.common.roomoffer;

import java.util.Collections;
import java.util.List;

import suncertify.common.Command;

public final class CreateRoomCommand implements Command {

    private static final long serialVersionUID = 4786262039648065987L;

    private final List<String> values;

    public CreateRoomCommand(final List<String> values) {
	super();
	this.values = values;
    }

    public List<String> getValues() {
	return Collections.unmodifiableList(values);
    }

}
