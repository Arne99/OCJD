package suncertify.domain;

import java.util.List;
import java.util.Map;

import suncertify.common.roomoffer.RoomOffer;

public interface Dao<T> {

    int create(T t);

    void delete(int index, long lock);

    List<RoomOffer> find(Map<String, String> criteria);

    long lock(int index);

    RoomOffer read(int index);

    void unlock(int index, long lock);

    void update(final RoomOffer roomOffer, final long lock);

}
