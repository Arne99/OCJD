package suncertify.domain;

import suncertify.common.roomoffer.CreateRoomCommand;

public class IsRoomOccupancyIn48Hours implements
	BusinessRule<CreateRoomCommand> {

    @Override
    public boolean isSatisfiedBy(final CreateRoomCommand command) {
	// TODO Auto-generated method stub
	return false;
    }

}
