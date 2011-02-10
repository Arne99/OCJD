package suncertify.datafile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

interface DataFileMetaData {

    public abstract int getOffset();

    public abstract int getRecordLength();

    public abstract DataFileRecord createRecord(final byte[] recordBuffer,
	    final int index);

    public abstract DataFileRecord createRecord(final List<String> values,
	    final int index);

    DataFileRecord createNullRecord(int index);

    boolean isValidDataFile(File dataFile) throws IOException;
}