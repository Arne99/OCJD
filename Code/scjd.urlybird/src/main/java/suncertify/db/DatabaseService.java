package suncertify.db;

public class DatabaseService {

    private final static DatabaseService INSTANCE = new DatabaseService();

    public static DatabaseService instance() {
	return INSTANCE;
    }

    public DB getDatabaseConnection(final DatabaseHandler handler) {
	return new Database(handler, SynchronizedRecordLocker.instance());
    }

}
