package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;

final class UnsupportedDataFileFormat implements SchemaBuilder {

    @Override
    public DataFileSchema buildSchemaForDataFile(final File file)
	    throws UnsupportedDataFileFormatException {

	checkNotNull(file, "file");

	throw new UnsupportedDataFileFormatException(
		"The specified file is no supported UrlyBird-DataFile. Please choose an valid UrlyBird-DataFile");
    }

}
