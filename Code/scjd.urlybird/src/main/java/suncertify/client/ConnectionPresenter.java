package suncertify.client;

import javax.swing.JFrame;

import suncertify.common.RoomOfferService;

interface ConnectionPresenter {

    RoomOfferService startConnectionDialog(final JFrame frame,
	    RoomOfferService service);

}
