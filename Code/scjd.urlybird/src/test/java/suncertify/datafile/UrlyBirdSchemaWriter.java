package suncertify.datafile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.Lists;


/**
 * The Class UrlyBirdSchemaWriter.
 */
final class UrlyBirdSchemaWriter {

    /** The identifier. */
    private final int identifier;

    /** The record length. */
    private final int recordLength;

    /** The columns. */
    private final ArrayList<Pair<byte[], Integer>> columns = Lists
	    .newArrayList();

    /**
     * Instantiates a new urly bird schema writer.
     * 
     * @param identifier
     *            the identifier
     * @param recordLength
     *            the record length
     */
    public UrlyBirdSchemaWriter(final int identifier, final int recordLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;

    }

    /**
     * Write schema.
     * 
     * @param identifier
     *            the identifier
     * @param recordLength
     *            the record length
     * @return the urly bird schema writer
     */
    static UrlyBirdSchemaWriter writeSchema(final int identifier,
	    final int recordLength) {

	return new UrlyBirdSchemaWriter(identifier, recordLength);
    }

    /**
     * With column.
     * 
     * @param name
     *            the name
     * @param length
     *            the length
     * @return the urly bird schema writer
     */
    UrlyBirdSchemaWriter withColumn(final String name, final int length) {

	columns.add(new Pair<byte[], Integer>(name.getBytes(), length));
	return this;
    }

    /**
     * To file.
     * 
     * @param file
     *            the file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void toFile(final File file) throws IOException {

	DataOutputStream outputStream = null;
	try {
	    outputStream = new DataOutputStream(new FileOutputStream(file));
	    writeHeaderInformation(outputStream);
	    writeColumnInformation(outputStream);
	} finally {
	    outputStream.close();
	}
    }

    /**
     * Write column information.
     * 
     * @param outputStream
     *            the output stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeColumnInformation(final DataOutputStream outputStream)
	    throws IOException {
	for (final Pair<byte[], Integer> column : columns) {
	    outputStream.writeShort(column.getLeft().length);
	    for (final Byte b : column.getLeft()) {
		outputStream.write(b);
	    }
	    outputStream.writeShort(column.getRigth());
	}
    }

    /**
     * Write header information.
     * 
     * @param outputStream
     *            the output stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeHeaderInformation(final DataOutputStream outputStream)
	    throws IOException {
	outputStream.writeInt(identifier);
	outputStream.writeInt(recordLength);
	outputStream.writeShort(columns.size());
    }

}
