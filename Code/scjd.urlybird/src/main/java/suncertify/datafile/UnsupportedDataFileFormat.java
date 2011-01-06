package suncertify.datafile;

import java.io.File;
import java.io.IOException;

final class UnsupportedDataFileFormat implements SchemaBuilder {

    @Override
    public DataFileSchema buildSchemaForDataFile(final File file)
	    throws UnsupportedDataFileFormatException {

	throw new UnsupportedDataFileFormatException("");
    }

}
