package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.db.Record;
import suncertify.db.RecordMatchingSpecification;
import suncertify.util.Range;

import com.google.common.collect.Lists;

public final class DataFileHandlerTest {

    private File testFile = new File("");

    final DeletedColumn firstColumn = new DeletedColumn("deleted", new Range(0,
	    0), "1");

    final DataFileColumn secondColumn = new BusinessColumn("firstName",
	    new Range(1, 7));
    final DataFileColumn thirdColumn = new BusinessColumn("secondName",
	    new Range(8, 15));
    final DataFileColumn fourthColumn = new BusinessColumn("age", new Range(8,
	    15));

    final DataFileRecord firstRecord = new ValidRecord(Lists.newArrayList(
	    new RecordValue(firstColumn, "0"), new RecordValue(secondColumn,
		    "Hans   "), new RecordValue(thirdColumn, "Meier   "),
	    new RecordValue(fourthColumn, "37")), 0);

    final DataFileRecord secondRecord = new ValidRecord(Lists.newArrayList(
	    new RecordValue(firstColumn, "0"), new RecordValue(secondColumn,
		    "Helmut "), new RecordValue(thirdColumn, "Schultze"),
	    new RecordValue(fourthColumn, "47")), 0);

    final DataFileMetaData schema = mock(DataFileMetaData.class);

    @Before
    public void setUp() throws IOException {
	testFile = File.createTempFile("test", getClass().getSimpleName());
	DataFileBuilder.setOffset(0).addRecord(firstRecord.getValuesAsBytes())
		.addRecord(secondRecord.getValuesAsBytes())
		.writeToDataFile(testFile);

	when(schema.getOffset()).thenReturn(0);
	when(schema.createRecord(firstRecord.getValuesAsBytes(), 0))
		.thenReturn(firstRecord);
	when(schema.createRecord(secondRecord.getValuesAsBytes(), 1))
		.thenReturn(secondRecord);
	when(schema.getRecordLength()).thenReturn(
		firstRecord.getValuesAsBytes().length);

    }

    @After
    public void tearDown() throws IOException {
	testFile.delete();
    }

    @Test
    public void shouldMatchEveryRecordInTheDataFileAgainstTheSpecificationToFindTheMatchingRecords()
	    throws IOException {

	final RecordMatchingSpecification specification = mock(RecordMatchingSpecification.class);
	when(specification.isSatisfiedBy(any(ValidRecord.class))).thenReturn(
		true);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertThat(firstRecord, isIn(matchingRecords));
	assertThat(secondRecord, isIn(matchingRecords));
    }

    @Test
    public void shouldReturnOnlyRecordsThatSatisfyTheSpecification()
	    throws IOException {

	final RecordMatchingSpecification specification = mock(RecordMatchingSpecification.class);
	when(specification.isSatisfiedBy(firstRecord)).thenReturn(true);
	when(specification.isSatisfiedBy(secondRecord)).thenReturn(false);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertThat(firstRecord, isIn(matchingRecords));
	assertThat(secondRecord, not(isIn(matchingRecords)));
    }

    @Test
    public void shouldReturnAnEmptyCollectionIfTheSpecificationDoesntMatch()
	    throws IOException {

	final RecordMatchingSpecification specification = mock(RecordMatchingSpecification.class);
	when(specification.isSatisfiedBy(any(Record.class))).thenReturn(false);

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final Set<Record> matchingRecords = handler
		.findMatchingRecords(specification);

	assertTrue(matchingRecords.isEmpty());
    }

    @Test
    public void shouldReadTheFirstRecordFromTheIndex0() throws IOException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(0);

	assertThat(record, is(equalTo(firstRecord)));
    }

    @Test
    public void shouldReadTheSecondRecordFromTheIndex1() throws IOException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(1);

	assertThat(record, is(equalTo(secondRecord)));
    }

    @Test
    public void shouldReturnAnInValidRecordIfTheGivenIndexIsGreaterThanTheNumberOfAvailableRecords()
	    throws IOException {

	when(schema.createNullRecord(10)).thenReturn(
		new ValidRecord(Lists.newArrayList(new RecordValue(firstColumn,
			"1")), 10));

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final DataFileRecord record = handler.readRecord(10);

	assertFalse(record.isValid());
    }

    @Test
    public void shouldWriteAnRecordToTheDataFileAtTheGivenIndex()
	    throws IOException {

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

    @Test
    public void shouldMarkTheRecordAtTheGivenIndexInTheDataFileAsDeleted()
	    throws IOException {

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

    @Test
    public void shouldReturnTheLastIndexPlusOneAsTheFirstEmptyIndexIfTheFileContainsNoDeletedRows()
	    throws IOException {

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	final int emptyIndex = handler.findEmptyIndex();

	assertThat(emptyIndex, is(equalTo(2)));
    }

    @Test
    public void shouldReturnTheIndexOfTheFirstInvalidRecordAsAnEmptyIndex()
	    throws IOException {

	final int index = 1;

	final DataFileRecord nullRecord = new ValidRecord(Lists.newArrayList(
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

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void shouldCheckIfTheValuesToBeWrittenViolatesTheSchema()
	    throws IOException {

	doThrow(new IllegalArgumentException()).when(schema).checkValidRecord(
		any(Collection.class));

	final DataFileHandler handler = new DataFileHandler(testFile, schema);
	handler.writeRecord(Lists.<String> newArrayList(), 0);

    }
}
