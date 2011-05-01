package suncertify.datafile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

interface DataFileSchema {

    int getOffset();

    int getRecordLength();

    DataFileRecord createRecord(final byte[] recordBuffer, final int index);

    DataFileRecord createRecord(final List<String> values, final int index);

    DataFileRecord createNullRecord(int index);

    boolean isValidDataFile(File dataFile) throws IOException;
}