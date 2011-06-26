package suncertify.datafile;

import static suncertify.common.DesignByContract.checkNotNull;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import suncertify.common.Range;

/**
 * Implementation of a {@link SchemaFactory} for the schema with the identifier
 * <code>257</code>. This is the schema of the DataFile of the <i>UrlyBird</i>
 * application.
 * 
 * The supported format 257 of data in the DataFile is as follows:
 * 
 * <pre>
 * Start of file 
 * - 4 byte numeric:		magic cookie value.Identifies this as a data file 
 * - 4 byte numeric:		total overall length in bytes of each record 
 * - 2 byte numeric:		number of fields in each record Schema description section. 
 * Repeated for each field in a record: 
 * - 2 byte numeric:		length in bytes of field name 
 * - n bytes (defined by previous entry): field name 
 * - 2 byte numeric:		field length in bytes 
 * end of repeating block Data section.
 * </pre>
 * 
 * This class is an implementation of the chain of responsibility pattern.
 * 
 */
final class Format257SchemaFactory implements SchemaFactory {

    /**
     * Constant for the read mode of an {@link RandomAccessFile}.
     */
    private static final String READ_MODE = "r";

    /**
     * Constant for the supported format identifier.
     */
    private static final int SUPPORTED_FORMAT = 257;

    /**
     * Constant for the value of the deleted flag that indicates a valid record.
     */
    private static final String NOT_DELETED_VALUE = "0";

    /**
     * Constant for the value of the deleted flag that indicates a deleted
     * record.
     */
    private static final String IS_DELETED_VALUE = "1";

    /**
     * Constant for the length of the column that contains the deleted flag.
     */
    private static final byte DELETED_COLUMN_LENGTH = 1;

    /**
     * Constant for the name of the column that contains the deleted flag.
     */
    private static final String DELETED_COLUMN_NAME = "Deleted-Flag";

    /** the decoder that transforms bytes to string. */
    private final BytesToStringDecoder decoder;

    /** the successor in the chain of responsibility. */
    private final SchemaFactory successor;

    /**
     * Constructs a new <code>Format257SchemaFactory</code>.
     * 
     * @param successor
     *            could try to build a schema if this factory fails, must not be
     *            <code>null</code>
     * @param decoder
     *            the decoder to transform byte values in string values, must
     *            not be <code>null</code>.
     */
    Format257SchemaFactory(final SchemaFactory successor,
	    final BytesToStringDecoder decoder) {
	super();
	this.successor = successor;
	this.decoder = decoder;
    }

    @Override
    public DataFileSchema createSchemaForDataFile(final File file)
	    throws IOException, UnsupportedDataFileFormatException {

	checkNotNull(file, "file");

	final int formatId = readFormatIdentifierFromFile(file);

	if (formatId != SUPPORTED_FORMAT) {
	    successor.createSchemaForDataFile(file);
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

    /**
     * Reads the format identifier of the given file.
     * 
     * @param file
     *            the schema description file, must not be <code>null</code>.
     * @return the schema identifier.
     * @throws IOException
     *             if an IO problem occurs during the reading of the schema
     *             identifier.
     */
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

    /**
     * Creates a {@link DataFileSchema} from the underlying description file.
     * 
     * @param reader
     *            the schema description file as {@link RandomAccessFile}.
     * @return the created <code>DataFileSchema</code>.
     * @throws IOException
     *             if an IO problem occurs during the reading from the file.
     * @throws UnsupportedDataFileFormatException
     *             if the format of the file is not valid.
     */
    private DataFileSchema extractSchemaWithReader(final RandomAccessFile reader)
	    throws IOException, UnsupportedDataFileFormatException {

	final int dataFileFormatIdentifier = reader.readInt();
	if (SUPPORTED_FORMAT != dataFileFormatIdentifier) {
	    throw new UnsupportedDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatIdentifier
			    + " – Supported identifier " + SUPPORTED_FORMAT);
	}

	// reads the recordLength witch is redundant, so the value must not be
	// stored.
	reader.readInt();
	final int numberOfColumns = reader.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	final DeletedColumn deletedColumn = new DeletedColumn(
		DELETED_COLUMN_NAME, new Range(0, 0), NOT_DELETED_VALUE,
		IS_DELETED_VALUE);
	int startPositionNextColumn = DELETED_COLUMN_LENGTH;
	for (int i = 0; i < numberOfColumns; i++) {

	    final int columnNameLengthInByte = reader.readShort();
	    final byte[] nameBuffer = new byte[columnNameLengthInByte];
	    reader.read(nameBuffer);
	    final String columnName = decoder.decodeBytesToString(nameBuffer);

	    final int columnSize = reader.readShort();
	    final DataFileColumn newColumn = new BusinessValueColumn(
		    columnName, new Range(startPositionNextColumn,
			    startPositionNextColumn + columnSize - 1));
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.getSize();
	}

	return new SchemaWithDeletedColumn(new DataFileHeader(SUPPORTED_FORMAT,
		(int) reader.getFilePointer()), deletedColumn, columns,
		new Utf8Decoder());
    }

}
