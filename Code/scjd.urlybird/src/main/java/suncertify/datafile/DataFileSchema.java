package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class DataFileSchema implements DataFileMetaData {

    private final DataFileHeader header;
    private final ArrayList<DataFileColumn> columns;
    private final BytesToStringDecoder decoder;

    DataFileSchema(final DataFileHeader header,
	    final Collection<DataFileColumn> columns,
	    final BytesToStringDecoder decoder) {
	this.header = header;
	this.decoder = decoder;
	this.columns = new ArrayList<DataFileColumn>(columns);
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DataFileSchema)) {
	    return false;
	}
	final DataFileSchema schema = (DataFileSchema) object;
	return this.header.equals(schema.header)
		&& this.columns.equals(schema.columns)
		&& this.decoder.equals(schema.decoder);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.header.hashCode();
	result = 31 * result + this.columns.hashCode();
	result = 31 * result + this.decoder.hashCode();
	return result;
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "header = " + header + "; columns = "
		+ columns + "; decoder = " + decoder + " ] ";
    }

    private boolean fileContainsOnlyCompleteRecords(final File file) {

	return (file.length() - getOffset()) % getRecordLength() == 0;
    }

    private boolean fileHasValidIdentifier(final File file) throws IOException {

	boolean hasValidIdentifier = false;
	final DataInputStream reader = new DataInputStream(new FileInputStream(
		file));

	try {
	    final int fileIdentifier = reader.readInt();
	    hasValidIdentifier = fileIdentifier == header.getKey();
	} finally {
	    reader.close();
	}

	return hasValidIdentifier;
    }

    @Override
    public int getOffset() {
	return header.getHeaderLength();
    }

    @Override
    public int getRecordLength() {
	return columns.get(columns.size() - 1).getEndIndex() + 1;
    }

    boolean isValidDataFile(final File file) throws IOException {

	boolean isValid = true;

	isValid = isValid && file.exists() && file.isFile();
	isValid = isValid && fileHasValidIdentifier(file);
	isValid = isValid && fileContainsOnlyCompleteRecords(file);

	return isValid;
    }

    @Override
    public ValidRecord createRecord(final byte[] recordBuffer, final int index) {

	final List<RecordValue> values = new ArrayList<RecordValue>();

	for (final DataFileColumn column : columns) {
	    final byte[] rangeCopy = Arrays.copyOfRange(recordBuffer,
		    column.getStartIndex(), column.getEndIndex() + 1);
	    final String value = decoder.decodeBytesToString(rangeCopy);
	    final RecordValue recordValue = column.createValue(value);
	    values.add(recordValue);
	}

	return new ValidRecord(values, index);
    }

    @Override
    public DataFileRecord createRecord(final List<String> values,
	    final int index) {

	final List<RecordValue> recordValues = new ArrayList<RecordValue>();

	final Iterator<String> valueIter = values.iterator();
	for (final DataFileColumn column : columns) {
	    if (column.containsBuissnessValues()) {
		final RecordValue value = column.createValue(valueIter.next());
		recordValues.add(value);
	    } else {
		final RecordValue value = column.createDefaultValue();
		recordValues.add(value);
	    }
	}

	return new ValidRecord(recordValues, index);
    }

    @Override
    public DataFileRecord createNullRecord(final int index) {

	final List<RecordValue> recordValues = new ArrayList<RecordValue>();

	for (final DataFileColumn column : columns) {
	    final RecordValue value = column.createDefaultValue();
	    recordValues.add(value);
	}

	return new ValidRecord(recordValues, index);
    }

    @Override
    public void checkValidRecord(final Collection<String> values) {

    }
}
