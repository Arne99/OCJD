package suncertify.datafile;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.common.Range;

import com.google.common.collect.Lists;

/**
 * The Tests for the Class DataFileSchemaFactory.
 */
public final class Format257SchemaFactoryTest {

    /** The Constant SUPPORTED_IDENTIFIER. */
    private static final int SUPPORTED_IDENTIFIER = 257;

    /** The test file. */
    private File testFile;

    /**
     * Sets the up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void setUp() throws IOException {
	testFile = File.createTempFile("test", "DataFileSchemaFactoryTest");
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
     * Should create a schema with two columns if the data file contains two
     * field descriptions.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateASchemaWithTwoColumnsIfTheDataFileContainsTwoFieldDescriptions()
	    throws IOException, UnsupportedDataFileFormatException {

	UrlyBirdSchemaWriter.writeSchema(SUPPORTED_IDENTIFIER, 201)
		.withColumn("name", 160).withColumn("age", 40).toFile(testFile);

	final BytesToStringDecoder decoder = mock(BytesToStringDecoder.class);
	when(decoder.decodeBytesToString((byte[]) anyVararg())).thenReturn(
		"name", "age");
	final SchemaFactory factory = new Format257SchemaFactory(null, decoder);
	final DataFileSchema schema = factory.createSchemaForDataFile(testFile);

	final DeletedColumn deletedColumn = new DeletedColumn("Deleted-Flag",
		new Range(0, 0), "0", "1");
	final DataFileColumn businessColumnOne = new BusinessValueColumn(
		"name", new Range(1, 160));
	final DataFileColumn businessColumnTwo = new BusinessValueColumn("age",
		new Range(161, 200));

	final DataFileSchema expectedSchema = new SchemaWithDeletedColumn(
		new DataFileHeader(SUPPORTED_IDENTIFIER, 25), deletedColumn,
		Lists.newArrayList(businessColumnOne, businessColumnTwo),
		new Utf8Decoder());

	assertThat(schema, is(equalTo(expectedSchema)));
    }

    /**
     * Should create a schema with zero business and one deleted column if the
     * data file contains only a header.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateASchemaWithZeroBusinessAndOneDeletedColumnIfTheDataFileContainsOnlyAHeader()
	    throws IOException, UnsupportedDataFileFormatException {

	UrlyBirdSchemaWriter.writeSchema(SUPPORTED_IDENTIFIER, 0).toFile(
		testFile);

	final SchemaFactory factory = new Format257SchemaFactory(null, null);
	final DataFileSchema schema = factory.createSchemaForDataFile(testFile);

	final DeletedColumn deletedColumn = new DeletedColumn("Deleted-Flag",
		new Range(0, 0), "0", "1");

	final DataFileSchema expectedSchema = new SchemaWithDeletedColumn(
		new DataFileHeader(SUPPORTED_IDENTIFIER, 10), deletedColumn,
		Lists.<DataFileColumn> newArrayList(), new Utf8Decoder());

	assertThat(schema, is(equalTo(expectedSchema)));
    }
}
