// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package suncertify.admin;

/**
 * Fully generated.
 * 
 */
@SuppressWarnings("deprecation")
public final class LockableServiceProvider_Skel implements
	java.rmi.server.Skeleton {
    private static final java.rmi.server.Operation[] operations = { new java.rmi.server.Operation(
	    "suncertify.common.RoomOfferService getRoomOfferService()") };

    private static final long interfaceHash = 8825923882540309865L;

    @Override
    public java.rmi.server.Operation[] getOperations() {
	return operations.clone();
    }

    @Override
    public void dispatch(final java.rmi.Remote obj,
	    final java.rmi.server.RemoteCall call, int opnum, final long hash)
	    throws java.lang.Exception {
	if (opnum < 0) {
	    if (hash == -2859605693618835423L) {
		opnum = 0;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash) {
		throw new java.rmi.server.SkeletonMismatchException(
			"interface hash mismatch");
	    }
	}

	final suncertify.admin.LockableServiceProvider server = (suncertify.admin.LockableServiceProvider) obj;
	switch (opnum) {
	case 0: // getRoomOfferService()
	{
	    call.releaseInputStream();
	    final suncertify.common.RoomOfferService $result = server
		    .getRoomOfferService();
	    try {
		final java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (final java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return",
			e);
	    }
	    break;
	}

	default:
	    throw new java.rmi.UnmarshalException("invalid method number");
	}
    }
}
