package suncertify.common.roomoffer;

import java.util.Date;

import suncertify.common.Command;

public interface CreateRoomCommand extends Command {

    RoomOffer getRoomOfferToCreate();

    String[] getNewRoomOfferValues();

    Date getBookableDate();

    String getCity();

    boolean isSmokingAllowed();

    String getHotel();

    int getRoomSize();

    Money getPrice();

}
