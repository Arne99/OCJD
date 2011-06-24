package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.common.Range;
import suncertify.common.Specification;
import suncertify.db.Record;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * The Class DataFileHandlerTest.
 */
public final class DataFileHandlerTest {

    /** The test file. */
    private File testFile = new File("");

    /** The first column. */
    private final DeletedColumn firstColumn = new DeletedColumn("deleted",
	    new Range(0, 0), "0", "1");

    /** The second column. */
    private final DataFileColumn secondColumn = new BusinessValueColumn(
	    "firstName", new Range(1, 7));

    /** The third column. */
    private final DataFileColumn thirdColumn = new BusinessValueColumn(
	    "secondName", new Range(8, 15));

    /** The fourth column. */
    private final DataFileColumn fourthColumn = new BusinessValueColumn("age",
	    new Range(8, 15));

    /** The first record. */
    private final DataFileRecord firstRecord = new ValidRecord(
	    Lists.newArrayList(new RecordValue(firstColumn, "0"),
		    new RecordValue(secondColumn, "Hans   "), new RecordValue(
			    thirdColumn, "Meier   "), new RecordValue(
			    fourthColumn, "37")), 0);

    /** The second record. */
    private final DataFileRecord secondRecord = new ValidRecord(
	    Lists.newArrayList(new RecordValue(firstColumn, "0"),
		    new RecordValue(secondColumn, "Helmut "), new RecordValue(
			    thirdColumn, "Schultze"), new RecordValue(
			    fourthColumn, "47")), 0);

    /** The schema. */
    private final DataFileSchema schema = mock(DataFileSchema.class);

    /**
     * Sets the up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void setUp() throws IOException {
	testFile = File.createTempFile("test", getClass().getSimpleName());
	DataFileBuilder.setOffset(0).addRecord(firstRecord.getValuesAsBytes())
		.addRecord(secondRecord.getValuesAsBytes())
		.writeToDataFile(testFile);

	when(schema.isValidDataFile(testFile)).thenReturn(true);
	when(schema.getOffset()).thenReturn(0);
	when(schema.createRecord(firstRecord.getValuesAsBytes(), 0))
		.thenReturn(firstRecord);
	when(schema.createRecord(secondRecord.getValuesAsBytes(), 1))
		.thenReturn(secondRecord);
	when(schema.getRecordLength()).thenReturn(
		firstRecord.getValuesAsBytes().length);

    }

    /**
     * Tear down.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @After
    public void tearDown() throws IOException {
	testFile.delete();
    }

    /**
     * Should match every record in the data file against the specification to
     * find the matching records.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldMatchEveryRecordInTheDataFileAgainstTheSpecificationToFindTheMatchingRecords()
	    throws IOException, UnsupportedDataFileFormatException {

	@SuppressWarnings("unchecked")
	final Specification<Record> specification = mock(Specification.class);
	when(specification.isSatisfiedBy(any(ValidRecord.class))).thenReturn(
		true);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertThat(firstRecord, isIn(matchingRecords));
	assertThat(secondRecord, isIn(matchingRecords));
    }

    /**
     * Should return only records that satisfy the specification.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReturnOnlyRecordsThatSatisfyTheSpecification()
	    throws IOException, UnsupportedDataFileFormatException {

	@SuppressWarnings("unchecked")
	final Specification<Record> specification = mock(Specification.class);
	when(specification.isSatisfiedBy(firstRecord)).thenReturn(true);
	when(specification.isSatisfiedBy(secondRecord)).thenReturn(false);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertThat(firstRecord, isIn(matchingRecords));
	assertThat(secondRecord, not(isIn(matchingRecords)));
    }

    /**
     * Should return an empty collection if the specification doesnt match.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReturnAnEmptyCollectionIfTheSpecificationDoesntMatch()
	    throws IOException, UnsupportedDataFileFormatException {

	@SuppressWarnings("unchecked")
	final Specification<Record> specification = mock(Specification.class);
	when(specification.isSatisfiedBy(any(Record.class))).thenReturn(false);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertTrue(matchingRecords.isEmpty());
    }

    /**
     * Should read the first record from the index0.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReadTheFirstRecordFromTheIndex0() throws IOException,
	    UnsupportedDataFileFormatException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(0);

	assertThat(record, is(equalTo(firstRecord)));
    }

    /**
     * Should read the second record from the index1.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReadTheSecondRecordFromTheIndex1() throws IOException,
	    UnsupportedDataFileFormatException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(1);

	assertThat(record, is(equalTo(secondRecord)));
    }

    /**
     * Should return an in valid record if the given index is greater than the
     * number of available records.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReturnAnInValidRecordIfTheGivenIndexIsGreaterThanTheNumberOfAvailableRecords()
	    throws IOException, UnsupportedDataFileFormatException {

	when(schema.createNullRecord(10)).thenReturn(
		new DeletedRecord(Lists.newArrayList(new RecordValue(
			firstColumn, "1")), 10));

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(10);

	assertFalse(record.isValid());
    }

    /**
     * Should write an record to the data file at the given index.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldWriteAnRecordToTheDataFileAtTheGivenIndex()
	    throws IOException, UnsupportedDataFileFormatException {

	final DataFileRecord newRecord = new ValidRecord(Lists.newArrayList(
		new RecordValue(firstColumn, "0"), new RecordValue(
			secondColumn, "Klaus  "), new RecordValue(thirdColumn,
			"Hase    "), new RecordValue(fourthColumn, "23")), 12);

	when(schema.createRecord(newRecord.getAllValues(), 12)).thenReturn(
		newRecord);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	handler.writeRecord(newRecord.getAllValues(), 12);

	when(schema.createRecord(newRecord.getValuesAsBytes(), 12)).thenReturn(
		newRecord);

	final DataFileRecord record = handler.readRecord(12);
	assertThat(record, is(equalTo(newRecord)));
    }

    /**
     * Should mark the record at the given index in the data file as deleted.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldMarkTheRecordAtTheGivenIndexInTheDataFileAsDeleted()
	    throws IOException, UnsupportedDataFileFormatException {

	final DataFileRecord nullRecord = new ValidRecord(Lists.newArrayList(
		new RecordValue(firstColumn, "1"), new RecordValue(
			secondColumn, "       "), new RecordValue(thirdColumn,
			"        "), new RecordValue(fourthColumn, "  ")), 0);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	when(schema.createNullRecord(0)).thenReturn(nullRecord);

	handler.deleteRecord(0);

	when(schema.createRecord(nullRecord.getValuesAsBytes(), 0)).thenReturn(
		nullRecord);
	final DataFileRecord record = handler.readRecord(0);

	assertThat(record, is(equalTo(nullRecord)));
    }

    /**
     * Should return the last index plus one as the first empty index if the
     * file contains no deleted rows.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReturnTheLastIndexPlusOneAsTheFirstEmptyIndexIfTheFileContainsNoDeletedRows()
	    throws IOException, UnsupportedDataFileFormatException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final int emptyIndex = handler.findEmptyIndex();

	assertThat(emptyIndex, is(equalTo(2)));
    }

    /**
     * Should return the index of the first in valid record as an empty index.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldReturnTheIndexOfTheFirstInValidRecordAsAnEmptyIndex()
	    throws IOException, UnsupportedDataFileFormatException {

	final int index = 1;

	final DataFileRecord nullRecord = new DeletedRecord(Lists.newArrayList(
		new RecordValue(firstColumn, "1"), new RecordValue(
			secondColumn, "       "), new RecordValue(thirdColumn,
			"        "), new RecordValue(fourthColumn, "  ")),
		index);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	when(schema.createNullRecord(index)).thenReturn(nullRecord);
	handler.deleteRecord(index);

	when(schema.createRecord(nullRecord.getValuesAsBytes(), index))
		.thenReturn(nullRecord);
	final int emptyIndex = handler.findEmptyIndex();

	assertThat(emptyIndex, is(equalTo(index)));
    }

}
