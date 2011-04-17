package suncertify.db;

public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException(final Exception e) {
	super(e);
    }

}
