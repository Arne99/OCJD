package suncertify.datafile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DataFileBuilder {

    private final int offset;

    private final List<byte[]> records;

    public DataFileBuilder(final int offset) {
	this.offset = offset;
	records = new ArrayList<byte[]>();
    }

    static DataFileBuilder setOffset(final int offset) {
	return new DataFileBuilder(offset);
    }

    DataFileBuilder addRecord(final byte[] record) {
	records.add(record);
	return this;
    }

    void writeToDataFile(final File dataFile) throws IOException {

	final DataOutputStream writer = new DataOutputStream(
		new FileOutputStream(dataFile));

	final StringBuilder sb = new StringBuilder();
	for (int i = 0; i < offset; i++) {
	    sb.append("X");
	}

	try {
	    writer.writeBytes(sb.toString());
	    for (final byte[] record : records) {
		writer.write(record);
	    }
	} finally {
	    writer.close();
	}
    }

}
