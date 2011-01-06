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
import suncertify.db.RecordMatchingSpecification;

/**
 * The Class DataFileAccessHandler.
 */
class DataFileHandler implements DatabaseHandler {

    private static final String READ_WRITE_ACCESS = "rw";
    private static final String READ_ACCESS = "r";

    private final DataFileMetaData schema;
    private final File file;

    DataFileHandler(final File file, final DataFileMetaData schema)
	    throws IOException {

	schema.isValidDataFile(file);
	this.file = file;
	this.schema = schema;
    }

    @Override
    public Set<Record> findMatchingRecords(
	    final RecordMatchingSpecification specification) throws IOException {

	checkNotNull(specification, "queryRecord");

	final Set<Record> matchingRecords = new HashSet<Record>();
	final List<DataFileRecord> DataFileRecords = getAllDataFileRecords();
	for (final DataFileRecord record : DataFileRecords) {
	    if (specification.isSatisfiedBy(record)) {
		matchingRecords.add(record);
	    }
	}

	return Collections.unmodifiableSet(matchingRecords);
    }

    private List<DataFileRecord> getAllDataFileRecords() throws IOException {

	final List<DataFileRecord> DataFileRecords = new ArrayList<DataFileRecord>();
	final List<DataFileRecord> allRecords = getAllRecords();
	for (final DataFileRecord record : allRecords) {
	    if (record.isValid()) {
		DataFileRecords.add(record);
	    }
	}
	return DataFileRecords;
    }

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

	byte[] recordBuffer = new byte[] {};
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
