package suncertify.client;

import javax.swing.JFrame;

import suncertify.common.RoomOfferService;

/**
 * 
 * A <code>RoomOfferServiceProvider</code> could start an dialog that enables
 * the user to get an {@link RoomOfferService}. This could for example be done
 * with an remote service over the network or the simple creation of a new
 * service.
 * 
 * @author arnelandwehr
 * 
 */
interface RoomOfferServiceProvider {

    /**
     * Starts an dialog where a user could define the needed configuration to
     * create a new {@link RoomOfferService}.
     * 
     * @param frame
     *            the parent frame.
     * @param defaultService
     *            the default service if no new {@link RoomOfferService} could
     *            be created. This parameter is optional and could be
     *            <code>null</code>.
     * @return the new created <code>RoomOfferService</code> or the given
     *         default service, so this method could return <code>null</code>.
     */
    RoomOfferService startRoomOfferServiceProviderDialog(final JFrame frame,
	    RoomOfferService defaultService);

}
