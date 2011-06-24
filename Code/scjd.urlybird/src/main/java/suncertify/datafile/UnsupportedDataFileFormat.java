package suncertify.datafile;

import static suncertify.common.DesignByContract.*;

import java.io.File;

/**
 * This <code>SchemaFactory</code> implementation always throws an
 * {@link UnsupportedDataFileFormatException}. Could be used as the end point of
 * the schema factory chain of responsibility.
 * 
 * @see SchemaFactory
 * @see SchemaWithDeletedColumn
 * @see DataFileService
 * 
 * @author arnelandwehr
 * 
 */
final class UnsupportedDataFileFormat implements SchemaFactory {

    @Override
    public DataFileSchema createSchemaForDataFile(final File file)
	    throws UnsupportedDataFileFormatException {

	checkNotNull(file, "file");

	throw new UnsupportedDataFileFormatException(
		"The specified file is no supported UrlyBird-DataFile. Please choose an valid UrlyBird-DataFile");
    }

}
