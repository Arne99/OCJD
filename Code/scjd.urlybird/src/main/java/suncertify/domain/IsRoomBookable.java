package suncertify.domain;

import suncertify.common.roomoffer.BookRoomCommand;

public class IsRoomBookable implements BusinessRule<BookRoomCommand> {

    @Override
    public boolean isSatisfiedBy(final BookRoomCommand command) {
	// TODO Auto-generated method stub
	return false;
    }

}
