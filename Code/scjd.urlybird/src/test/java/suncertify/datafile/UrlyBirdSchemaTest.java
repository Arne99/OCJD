package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import suncertify.util.Range;

import com.google.common.collect.Lists;

/**
 * Tests for the Class {@link UrlyBirdSchema}.
 */
public final class UrlyBirdSchemaTest {

    /** The test file. */
    private File testFile;

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	if (testFile != null) {
	    testFile.delete();
	}
    }

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(0, 12)));
	    }
	};

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(12,
		1234, "1"), columns, new Utf8Decoder());
	assertThat(schema, is(equalTo(schema)));
    }

    /**
     * Schould be symmetric equal.
     */
    @Test
    public void schouldBeSymmetricEqual() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn(null, new Range(0, 12)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());

	assertThat(schemaOne, is(equalTo(schemaTwo)));
	assertThat(schemaTwo, is(equalTo(schemaOne)));
    }

    /**
     * Should be not equal to a schema with a different data file header.
     */
    @Test
    public void shouldBeNotEqualToASchemaWithADifferentDataFileHeader() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(9, 12)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		11, 123, "2"), columns, new Utf8Decoder());

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
    }

    /**
     * Should be not equal to a schema with different columns.
     */
    @Test
    public void shouldBeNotEqualToASchemaWithDifferentColumns() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columnsSchemaOne = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(0, 20)));
	    }
	};

	@SuppressWarnings("serial")
	final List<DataFileColumn> columnsSchemaTwo = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("olleH", new Range(0, 20)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columnsSchemaOne, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		11, 123, "1"), columnsSchemaTwo, new Utf8Decoder());

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
    }

    /**
     * Should be not equal to a schema with a different decoder.
     */
    @Test
    public void shouldBeNotEqualToASchemaWithADifferentDecoder() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(0, 20)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "2"), columns, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		11, 123, "2"), columns, new BytesToStringDecoder() {

	    @Override
	    public String decodeBytesToString(final byte... bytes) {
		return null;
	    }
	});

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
    }

    /**
     * Should have the same hash code with an equal schema.
     */
    @Test
    public void shouldHaveTheSameHashCodeWithAnEqualSchema() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(0, 20)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());

	assertThat(schemaOne, is(equalTo(schemaTwo)));
	assertThat(schemaTwo.hashCode(), is(equalTo(schemaOne.hashCode())));
    }

    /**
     * Should have an different hash code than a different schema.
     */
    @Test
    public void shouldHaveAnDifferentHashCodeThanADifferentSchema() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new BusinessColumn("Hello", new Range(0, 20)));
	    }
	};

	final DataFileSchema schemaOne = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());
	final DataFileSchema schemaTwo = new DataFileSchema(new DataFileHeader(
		12, 1234, "2"), columns, new Utf8Decoder());

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
	assertThat(schemaOne.hashCode(), is(not(equalTo(schemaTwo.hashCode()))));
    }

    /**
     * Should be able to create a not valid record.
     */
    @Test
    public void shouldBeAbleToCreateANotValidRecord() {

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new DeletedColumn("deleted", new Range(0, 0), "1"));
		add(new BusinessColumn("Hello", new Range(1, 20)));
	    }
	};

	final DataFileMetaData schema = new DataFileSchema(new DataFileHeader(
		12, 1234, "1"), columns, new Utf8Decoder());

	final DataFileRecord createdRecord = schema.createNullRecord(12);

	assertFalse(createdRecord.isValid());
	assertThat(createdRecord.getIndex(), is(equalTo(12)));
    }

    @Test
    public void shouldCreateAnValidRecordFromAnArrayOfBytesIfTheDeletedFlagIsFalseAndTheTheSizeOfTheArrayIsTheRecordLength() {

	final String deletedColumnName = "deleted";
	final String isDeletedValue = "deleted";

	@SuppressWarnings("serial")
	final List<DataFileColumn> columns = new ArrayList<DataFileColumn>() {
	    {
		add(new DeletedColumn(deletedColumnName, new Range(0,
			isDeletedValue.length() - 1), "test"));
		add(new BusinessColumn("TestColumn", new Range(
			isDeletedValue.length(), 12)));
	    }
	};

	final DataFileMetaData schema = new DataFileSchema(new DataFileHeader(
		12, 1234, isDeletedValue), columns, new Utf8Decoder());

	final String testRecord = "12345deVALID";

	schema.createRecord(testRecord.getBytes(), 1);
    }

    /**
     * Should recognize that a not existing file can't be an valid data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldRecognizeThatANotExistingFileCantBeAnValidDataFile()
	    throws IOException {

	final File testFile = new File("./nothing/exists/here");

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(12,
		1234, "1"), Lists.<DataFileColumn> newArrayList(),
		new Utf8Decoder());

	final boolean isValid = schema.isValidDataFile(testFile);

	assertFalse(isValid);
    }

    /**
     * Should recognize that a dir can't be an valid data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldRecognizeThatADirCantBeAnValidDataFile()
	    throws IOException {

	testFile = new File("./shouldRecognizeThatADirCantBeAnValidDataFile");
	testFile.mkdir();

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(12,
		1234, "1"), Lists.<DataFileColumn> newArrayList(),
		new Utf8Decoder());

	final boolean isValid = schema.isValidDataFile(testFile);

	assertFalse(isValid);
    }

}
