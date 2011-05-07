package suncertify.datafile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class DataFileBuilder.
 */
class DataFileBuilder {

    /** The offset. */
    private final int offset;

    /** The records. */
    private final List<byte[]> records;

    /**
     * Instantiates a new data file builder.
     *
     * @param offset the offset
     */
    public DataFileBuilder(final int offset) {
	this.offset = offset;
	records = new ArrayList<byte[]>();
    }

    /**
     * Sets the offset.
     *
     * @param offset the offset
     * @return the data file builder
     */
    static DataFileBuilder setOffset(final int offset) {
	return new DataFileBuilder(offset);
    }

    /**
     * Adds the record.
     *
     * @param record the record
     * @return the data file builder
     */
    DataFileBuilder addRecord(final byte[] record) {
	records.add(record);
	return this;
    }

    /**
     * Write to data file.
     *
     * @param dataFile the data file
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
