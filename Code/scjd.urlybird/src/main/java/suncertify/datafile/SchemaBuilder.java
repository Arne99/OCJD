package suncertify.datafile;

import java.io.File;
import java.io.IOException;

interface SchemaBuilder {

    /**
     * Creates a new DataFileSchema object.
     * 
     * @param input
     *            the input
     * @return the data schema
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     * @throws InvalidDataFileFormatException
     *             the unsupported data source exception
     */
    public abstract DataFileSchema buildSchemaForDataFile(final File file)
	    throws IOException, UnsupportedDataFileFormatException;

}