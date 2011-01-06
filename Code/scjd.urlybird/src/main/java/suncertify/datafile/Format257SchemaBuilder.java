package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import suncertify.util.Range;

/**
 */
final class Format257SchemaBuilder implements SchemaBuilder {

    private static final String READ_MODE = "r";

    private static final int SUPPORTED_FORMAT = 257;

    private static final String DELETED_COLUMN_VALUE = "1";

    private static final byte DELETED_COLUMN_LENGTH = 1;

    private static final String DELETED_COLUMN_NAME = "Deleted-Flag";

    private final BytesToStringDecoder decoder;

    private final SchemaBuilder successor;

    Format257SchemaBuilder(final SchemaBuilder successor,
	    final BytesToStringDecoder decoder) {
	super();
	this.successor = successor;
	this.decoder = decoder;
    }

    @Override
    public final DataFileSchema buildSchemaForDataFile(final File file)
	    throws IOException, UnsupportedDataFileFormatException {

	final int formatId = readFormatIdentifierFromFile(file);

	if (formatId != SUPPORTED_FORMAT) {
	    successor.buildSchemaForDataFile(file);
	}

	final RandomAccessFile reader = new RandomAccessFile(file, READ_MODE);
	DataFileSchema schema = null;
	try {
	    schema = extractSchemaWithReader(reader);
	} finally {
	    reader.close();
	}

	return schema;
    }

    private int readFormatIdentifierFromFile(final File file)
	    throws IOException {

	DataInputStream input = null;
	try {
	    input = new DataInputStream(new FileInputStream(file));
	    return input.readInt();
	} finally {
	    if (input != null) {
		input.close();
	    }
	}

    }

    private DataFileSchema extractSchemaWithReader(final RandomAccessFile reader)
	    throws IOException, UnsupportedDataFileFormatException {

	final int dataFileFormatIdentifier = reader.readInt();
	if (SUPPORTED_FORMAT != dataFileFormatIdentifier) {
	    throw new UnsupportedDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatIdentifier
			    + " Ð Supported identifier " + SUPPORTED_FORMAT);
	}

	// reads the recordLength witch is redundant
	reader.readInt();
	final int numberOfColumns = reader.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	columns.add(new DeletedColumn(DELETED_COLUMN_NAME, new Range(0, 0),
		DELETED_COLUMN_VALUE));
	int startPositionNextColumn = DELETED_COLUMN_LENGTH;
	for (int i = 0; i < numberOfColumns; i++) {

	    final int columnNameLengthInByte = reader.readShort();
	    final byte[] nameBuffer = new byte[columnNameLengthInByte];
	    reader.read(nameBuffer);
	    final String columnName = decoder.decodeBytesToString(nameBuffer);

	    final int columnSize = reader.readShort();
	    final BusinessColumn newColumn = new BusinessColumn(columnName,
		    new Range(startPositionNextColumn, startPositionNextColumn
			    + columnSize - 1));
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.getSize();
	}

	return new DataFileSchema(new DataFileHeader(SUPPORTED_FORMAT,
		(int) reader.getFilePointer(), DELETED_COLUMN_VALUE), columns,
		new Utf8Decoder());
    }

}
