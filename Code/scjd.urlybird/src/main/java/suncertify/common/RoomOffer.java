package suncertify.common;

import java.io.Serializable;
import java.util.Date;

import suncertify.common.Money;

public interface RoomOffer extends Serializable {

    String getHotel();

    String getCity();

    int getRoomSize();

    boolean isSmokingAllowed();

    Money getPrice();

    Date getBookableDate();

    String getCustomerId();

    int getIndex();

}
