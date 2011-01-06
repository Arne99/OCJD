package suncertify.db;

import java.io.EOFException;

public class RecordNotFoundException extends Exception {

    public RecordNotFoundException(final String message) {
	super(message);
    }

    public RecordNotFoundException(final String message,
	    final Throwable exception) {
	super(message, exception);
    }

}
