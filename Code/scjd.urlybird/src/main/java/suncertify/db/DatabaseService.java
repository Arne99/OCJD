package suncertify.db;

public class DatabaseService {

    private final static DatabaseService INSTANCE = new DatabaseService();

    public static DatabaseService instance() {
	return INSTANCE;
    }

    public DB connectToDatabase(final DatabaseHandler databaseHandler)
	    throws DatabaseConnectionException {

	return new Database(databaseHandler,
		SynchronizedRecordLocker.instance());
    }

}
