package suncertify.db;

/**
 * The <code>DatabaseService</code> is the interface to the db package. Clients
 * could connect with it to the underlying database.
 * 
 * There should be just one <code>DatabaseService</code> so it is a singleton.
 * 
 * @author arnelandwehr
 * 
 */
public class DatabaseService {

    /** the singleton instance. */
    private static final DatabaseService INSTANCE = new DatabaseService();

    /**
     * Returns the singleton instance of the <code>DatabaseService</code>.
     * 
     * @return the instance, never <code>null</code>.
     */
    public static DatabaseService instance() {
	return INSTANCE;
    }

    /**
     * Establishes a connection to the underlying database.
     * 
     * @param databaseHandler
     *            the handler that supports the IO operations for the database.
     * @return the connected database, never <code>null</code>.
     * @throws DatabaseConnectionException
     *             if a problem occurs during the connecting.
     */
    public final DB connectToDatabase(final DatabaseHandler databaseHandler)
	    throws DatabaseConnectionException {

	return new Database(databaseHandler,
		SynchronizedRecordLocker.instance());
    }

}
