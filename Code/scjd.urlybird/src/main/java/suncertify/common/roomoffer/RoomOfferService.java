package suncertify.common.roomoffer;

public interface RoomOfferService {

    void createRoomOffer(CreateRoomCommand command, CreateRoomCallback callback);

    void deleteRoomOffer(DeleteRoomCommand command, DeleteRoomCallback callback);

    void updateRoomOffer(UpdateRoomCommand command, UpdateRoomCallback callback);

    void findRoomOffer(FindRoomCommand command, FindRoomCallback callback);
}
