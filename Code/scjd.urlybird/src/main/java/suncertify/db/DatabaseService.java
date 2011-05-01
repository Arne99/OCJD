package suncertify.db;

import java.io.IOException;

import suncertify.admin.service.DatabaseConfiguration;
import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;

public class DatabaseService {

    private final static DatabaseService INSTANCE = new DatabaseService();

    public static DatabaseService instance() {
	return INSTANCE;
    }

    public DB connectToDatabase(final DatabaseConfiguration dataConfig)
	    throws DatabaseConnectionException {

	DatabaseHandler databaseHandler = null;
	try {
	    databaseHandler = DataFileService.instance().getDatabaseHandler(
		    dataConfig.getDatabaseLocation());
	} catch (final IOException e) {
	    throw new DatabaseConnectionException(e);
	} catch (final UnsupportedDataFileFormatException e) {
	    throw new DatabaseConnectionException(e);
	}

	return new Database(databaseHandler,
		SynchronizedRecordLocker.instance());
    }

}
