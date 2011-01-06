package suncertify.db;

import java.io.IOException;

public class DatabaseException extends RuntimeException {

    public DatabaseException(final IOException e) {
	super(e);
    }

}
