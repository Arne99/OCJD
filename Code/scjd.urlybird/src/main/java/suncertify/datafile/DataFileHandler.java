package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;
import suncertify.util.Specification;

/**
 * A <code>DataFileHandler</code> is a {@link DatabaseHandler} for DataFiles. It
 * guarantees write and read operations to <code>Records</code> in a flat file.
 */
class DataFileHandler implements DatabaseHandler {

    /**
     * constant for a read and write access to {@link RandomAccessFile}. @see
     * {@link RandomAccessFile#RandomAccessFile(File, String)}
     */
    private static final String READ_WRITE_ACCESS = "rw";

    /**
     * constant for a read access to {@link RandomAccessFile}. @see
     * {@link RandomAccessFile#RandomAccessFile(File, String)}
     */
    private static final String READ_ACCESS = "r";

    /**
     * the schema that is supported by this <code>DataFileHandler</code>.
     */
    private final DataFileSchema schema;

    /**
     * the <code>File</code> this handler guarantees access to.
     */
    private final File file;

    /**
     * Construct a new <code>DataFileHandler</code> for the given file that must
     * support the given {@link DataFileSchema}.
     * 
     * @param file
     *            the file to read from and write to.
     * @param schema
     *            the schema of the file.
     * @throws IOException
     *             if any IO problem occurs.
     * @throws UnsupportedDataFileFormatException
     *             if the data in the given file is not in the format of the
     *             given schema.
     */
    DataFileHandler(final File file, final DataFileSchema schema)
	    throws IOException, UnsupportedDataFileFormatException {

	if (!schema.isValidDataFile(file)) {
	    throw new UnsupportedDataFileFormatException("the given file "
		    + file.getAbsolutePath()
		    + " does not contain a supported schema");
	}

	this.file = file;
	this.schema = schema;
    }

    @Override
    public Set<Record> findMatchingRecords(
	    final Specification<Record> specification) throws IOException {

	checkNotNull(specification, "queryRecord");

	final Set<Record> matchingRecords = new HashSet<Record>();
	final List<DataFileRecord> dataFileRecords = getAllValidDataFileRecords();
	for (final DataFileRecord record : dataFileRecords) {
	    if (specification.isSatisfiedBy(record)) {
		matchingRecords.add(record);
	    }
	}

	return Collections.unmodifiableSet(matchingRecords);
    }

    /**
     * Reads all valid {@link Record}s from the DataFile. Valid is a Record if
     * it is not marked as deleted.
     * 
     * @return all valid <code>Records</code> in the DataFile, never
     *         <code>null</code>.
     * @throws IOException
     *             if an IO problem happens during the reading.
     */
    private List<DataFileRecord> getAllValidDataFileRecords()
	    throws IOException {

	final List<DataFileRecord> dataFileRecords = new ArrayList<DataFileRecord>();
	final List<DataFileRecord> allRecords = getAllRecords();
	for (final DataFileRecord record : allRecords) {
	    if (record.isValid()) {
		dataFileRecords.add(record);
	    }
	}
	return dataFileRecords;
    }

    /**
     * Reads all {@link Record}s from the DataFile.
     * 
     * @return all <code>Records</code> in the DataFile, never <code>null</code>
     * @throws IOException
     *             if an IO problem happens during the reading
     */
    private List<DataFileRecord> getAllRecords() throws IOException {

	final List<DataFileRecord> records = new ArrayList<DataFileRecord>();
	final int indexOfLastRecord = getIndexOfLastRecord();
	for (int i = 0; i <= indexOfLastRecord; i++) {
	    final DataFileRecord record = readRecord(i);
	    records.add(record);
	}
	return records;

    }

    @Override
    public DataFileRecord readRecord(final int index) throws IOException {

	checkNotNegativ(index, "index");

	if (getIndexOfLastRecord() < index) {
	    return schema.createNullRecord(index);
	}

	final RandomAccessFile reader = new RandomAccessFile(file, READ_ACCESS);

	byte[] recordBuffer = null;
	try {
	    final int offset = schema.getOffset()
		    + (index * schema.getRecordLength());
	    recordBuffer = new byte[schema.getRecordLength()];
	    reader.seek(offset);
	    reader.readFully(recordBuffer);
	} finally {
	    reader.close();
	}
	return schema.createRecord(recordBuffer, index);
    }

    /**
     * Returns the index of the last stored {@link Record} in the DataFile.
     * Every index behind this must be empty.
     * 
     * @return the (zero based) index of the last stored <code>Record</code> in
     *         the DataFile.
     * @throws IOException
     *             if an IO problem happens during the reading
     */
    private int getIndexOfLastRecord() throws IOException {

	final RandomAccessFile reader = new RandomAccessFile(file, READ_ACCESS);
	int lastIndex = 0;
	try {
	    lastIndex = (int) (reader.length() - schema.getOffset())
		    / schema.getRecordLength() - 1;
	} finally {
	    reader.close();
	}
	return lastIndex;
    }

    @Override
    public void writeRecord(final List<String> values, final int index)
	    throws IOException {

	checkNotNull(values, "values");
	checkNotNegativ(index, "index");

	final DataFileRecord record = schema.createRecord(values, index);
	writeRecord(record);
    }

    /**
     * Writes the given {@link DataFileRecord} to the DataFile at the position
     * of the index of the record.
     * 
     * @param record
     *            the record that should be written to the DataFile, must not be
     *            <code>null</code>.
     * @throws IOException
     *             if an IO problems happens.
     */
    private void writeRecord(final DataFileRecord record) throws IOException {

	final RandomAccessFile writer = new RandomAccessFile(file,
		READ_WRITE_ACCESS);
	try {
	    writer.seek(schema.getOffset()
		    + (schema.getRecordLength() * record.getIndex()));
	    writer.write(record.getValuesAsBytes());
	} finally {
	    writer.close();
	}
    }

    @Override
    public void deleteRecord(final int index) throws IOException {

	checkNotNegativ(index, "index");
	writeRecord(schema.createNullRecord(index));
    }

    @Override
    public int findEmptyIndex() throws IOException {

	final List<DataFileRecord> records = getAllRecords();
	for (final DataFileRecord record : records) {
	    if (!record.isValid()) {
		return record.getIndex();
	    }
	}

	return getIndexOfLastRecord() + 1;
    }

}
