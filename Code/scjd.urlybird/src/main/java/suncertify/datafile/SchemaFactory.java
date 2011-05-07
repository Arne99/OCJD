package suncertify.datafile;

import java.io.File;
import java.io.IOException;

/**
 * A <code>SchemaFactory</code> reads the schema information from a given
 * DataFile description file an creates with this data a {@link DataFileSchema}.
 * This <code>DataFileSchema</code> could be used by an {@link DataFileHandler}
 * to read and write to every DataFile that contains data with exact this
 * schema.
 * 
 * Every implementation of this must follow the chain of responsibility pattern.
 * So if a specific <code>SchemaFactory</code> can not create a schema of the
 * given schema description file, the given successor could try it.
 * 
 * The chain of SchemaFactories is connected by the {@link DataFileService}.
 * 
 * @author arnelandwehr
 * 
 */
interface SchemaFactory {

    /**
     * Creates a new DataFileSchema object.
     * 
     * @param file
     *            the input file, must contain the schema informations.
     * @return the data schema, never <code>null</code>.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data source exception
     */
    DataFileSchema createSchemaForDataFile(final File file) throws IOException,
	    UnsupportedDataFileFormatException;

}