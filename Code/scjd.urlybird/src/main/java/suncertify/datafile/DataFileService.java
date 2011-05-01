package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;

import suncertify.db.DatabaseHandler;

/**
 * A <code>DataFileService</code> is a port to the DataFile package and assures
 * access to an {@link DatabaseHandler} for DataFiles. Every handler has atomic
 * read an write operations so it could be used in an multi-threaded
 * environment. A <code>DataFileService</code> is a singleton.
 * 
 * @author arnelandwehr
 * 
 */
public final class DataFileService {

    /** the single instance. */
    private static final DataFileService INSTANCE = new DataFileService();

    /**
     * Factory method that return an <code>DataFileService</code>.
     * 
     * @return the <code>DataFileService</code> instance, never
     *         <code>null</code>.
     */
    public static DataFileService instance() {
	return INSTANCE;
    }

    /**
     * Creates an {@link DatabaseHandler} for the given {@link File}. The
     * <code>DatabaseHandler</code> could read and write to the file.
     * 
     * @param dataFile
     *            the file that should be used as an database, must be of an
     *            supported format. Supported formats are all DataFile formats
     *            that are indicated with the key
     *            <ul>
     *            <li>257</li>
     *            </ul>
     * @return an <code>DatabaseHandler</code> for the given file, never
     *         <code>null</code>.
     * @throws IOException
     *             if an IO problem occurs.
     * @throws UnsupportedDataFileFormatException
     *             if the given file is not of a supported format.
     */
    public DatabaseHandler getDatabaseHandler(final File dataFile)
	    throws IOException, UnsupportedDataFileFormatException {

	checkNotNull(dataFile, "dataFile");

	final BytesToStringDecoder decoder = new Utf8Decoder();
	final SchemaBuilder schemaBuilder = new Format257SchemaBuilder(
		new UnsupportedDataFileFormat(), decoder);

	final DataFileSchema schema = schemaBuilder
		.buildSchemaForDataFile(dataFile);

	return new DataFileHandler(dataFile, schema);
    }

}
