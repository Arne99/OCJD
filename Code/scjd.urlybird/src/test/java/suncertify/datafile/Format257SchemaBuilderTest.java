package suncertify.datafile;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.util.Range;

import com.google.common.collect.Lists;

/**
 * The Tests for the Class DataFileSchemaFactory.
 */
public final class Format257SchemaBuilderTest {

    /** The Constant SUPPORTED_IDENTIFIER. */
    private static final int SUPPORTED_IDENTIFIER = 257;

    private File testFile;

    @Before
    public void setUp() throws IOException {
	testFile = File.createTempFile("test", "DataFileSchemaFactoryTest");
    }

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
	final SchemaBuilder factory = new Format257SchemaBuilder(null, decoder);
	final DataFileSchema schema = factory
		.buildSchemaForDataFile(testFile);

	final DeletedColumn deletedColumn = new DeletedColumn("Deleted-Flag",
		new Range(0, 0), "0", "1");
	final DataFileColumn businessColumnOne = new BusinessValueColumn("name",
		new Range(1, 160));
	final DataFileColumn businessColumnTwo = new BusinessValueColumn("age",
		new Range(161, 200));

	final DataFileSchema expectedSchema = new SchemaWithDeletedColumn(
		new DataFileHeader(SUPPORTED_IDENTIFIER, 25), deletedColumn,
		Lists.newArrayList(businessColumnOne, businessColumnTwo),
		new Utf8Decoder());

	assertThat(schema, is(equalTo(expectedSchema)));
    }

    @Test
    public void shouldCreateASchemaWithZeroBusinessAndOneDeletedColumnIfTheDataFileContainsOnlyAHeader()
	    throws IOException, UnsupportedDataFileFormatException {

	UrlyBirdSchemaWriter.writeSchema(SUPPORTED_IDENTIFIER, 0).toFile(
		testFile);

	final SchemaBuilder factory = new Format257SchemaBuilder(null, null);
	final DataFileSchema schema = factory
		.buildSchemaForDataFile(testFile);

	final DeletedColumn deletedColumn = new DeletedColumn("Deleted-Flag",
		new Range(0, 0), "0", "1");

	final DataFileSchema expectedSchema = new SchemaWithDeletedColumn(
		new DataFileHeader(SUPPORTED_IDENTIFIER, 10), deletedColumn,
		Lists.<DataFileColumn> newArrayList(), new Utf8Decoder());

	assertThat(schema, is(equalTo(expectedSchema)));
    }
}
