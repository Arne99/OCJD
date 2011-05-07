package suncertify.datafile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import suncertify.db.Record;

/**
 * An <code>DataFileSchema</code> contains the needed meta information about the
 * structure of a DataFile. It is specific for every DataFile and could be used
 * to create {@link DataFileRecord}s. It consists of a number of
 * {@link DataFileColumn}s.
 * 
 * @author arnelandwehr
 */
interface DataFileSchema {

    /**
     * The returned offset is the number of bytes at the beginning of the
     * DataFile that does not contain stored values.
     * 
     * @return the offset of the DataFile, must be positive.
     */
    int getOffset();

    /**
     * The length of a {@link DataFileRecord} in this DataFile. Every
     * {@link Record} in one DataFile has the same length.
     * 
     * @return the record length, must be positive.
     */
    int getRecordLength();

    /**
     * Creates a {@link DataFileRecord} from the given bytes and index.
     * 
     * @param recordBuffer
     *            the values that should be transformed to an
     *            <code>DataFileRecord</code>, must not be <code>null</code>.
     * @param index
     *            the index of the record in the DataFile, must be positive.
     * @return the created <code>DataFileRecord</code> with the given index as
     *         identifier and the given bytes as values for the
     *         <code>DataFileColumn</code>s, never <code>null</code>.
     */
    DataFileRecord createRecord(final byte[] recordBuffer, final int index);

    /**
     * Creates a {@link DataFileRecord} from the given values and the given
     * index.
     * 
     * @param values
     *            the values that should be transformed to an
     *            <code>DataFileRecord</code>, must not be <code>null</code>.
     * @param index
     *            the index of the record in the DataFile, must be positive.
     * @return the created <code>DataFileRecord</code> with the given index as
     *         identifier and the given values as <code>DataFileColumn</code>
     *         values, never <code>null</code>.
     */
    DataFileRecord createRecord(final List<String> values, final int index);

    /**
     * Creates an empty {@link DataFileRecord} with the given index as
     * identifier. An empty record contains no values. It could be used to
     * delete existing records.
     * 
     * @param index
     *            the index of the empty record.
     * @return the empty record, never <code>null</code>.
     */
    DataFileRecord createNullRecord(int index);

    /**
     * Specifies if the given {@link File} represents a valid DataFile for this
     * schema.
     * 
     * @param dataFile
     *            the File to proof, must not be <code>null</code>
     * @return <code>true</code> if the file is valid for this schema.
     * @throws IOException
     *             if an IO-problem occurs during the validation.
     */
    boolean isValidDataFile(File dataFile) throws IOException;
}