package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;

import suncertify.db.DatabaseHandler;

public final class DataFileAccess {

    private static final DataFileAccess INSTANCE = new DataFileAccess();

    public static final DataFileAccess instance() {
	return INSTANCE;
    }

    public DatabaseHandler getDatabaseHandler(final File dataFile)
	    throws IOException, UnsupportedDataFileFormatException {

	checkNotNull(dataFile, "dataFile");

	final BytesToStringDecoder decoder = new Utf8Decoder();
	final SchemaBuilder schemaBuilder = new Format257SchemaBuilder(
		new UnsupportedDataFileFormat(), decoder);

	final DataFileMetaData schema = schemaBuilder
		.buildSchemaForDataFile(dataFile);

	return new DataFileHandler(dataFile, schema);
    }

    public void closeDatabaseConnection() {
	throw new UnsupportedOperationException();
    }
}
