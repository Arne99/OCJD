package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;
import suncertify.db.RecordMatchingSpecification;
import suncertify.db.RecordNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * Test for the class {@link DataFileAccess}.
 */
public final class DataFileAccessTest {

    /** The data file. */
    private final File dataFile = new File(
	    "/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");

    /** The any file. */
    private File anyFile;

    @Before
    public void setUp() throws IOException {
	anyFile = File.createTempFile("test", "test");
	Files.copy(dataFile, anyFile);
	anyFile.setWritable(true);
	anyFile.setWritable(true);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	if (anyFile != null && anyFile.exists()) {
	    anyFile.delete();
	}
    }

    /**
     * Should create a database handler that could read the first record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadTheFirstRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Record record = handler.readRecord(0);

	final List<String> expectedReccord = Lists.newArrayList("Palace",
		"Smallville", "2", "Y", "$150.00", "2005/07/27", "");

	assertThat(record.getAllBusinessValues(), is(equalTo(expectedReccord)));
    }

    @Test
    public void testTest() throws IOException,
	    UnsupportedDataFileFormatException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Set<Record> records = handler
		.findMatchingRecords(new RecordMatchingSpecification() {

		    @Override
		    public boolean isSatisfiedBy(final Record record) {
			return true;
		    }
		});

	for (final Record record : records) {
	    System.out.println(record);
	}

    }

    /**
     * Should throw an record not found exception the given record index is
     * greater than the number of available records.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    public void shouldReturnAnInvalidRecordIfTheGivenRecordIndexIsGreaterThanTheNumberOfAvailableRecords()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Record record = handler.readRecord(5000);
	assertFalse(record.isValid());
    }

    /**
     * Should create a database handler that could read the10th record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadThe10thRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Record record = handler.readRecord(9);

	final List<String> expectedReccord = Lists.newArrayList("Dew Drop Inn",
		"Pleasantville", "6", "N", "$160.00", "2005/03/04", "");
	assertThat(record.getAllBusinessValues(), is(equalTo(expectedReccord)));
    }

    /**
     * Schould throw an io exception if the data file location is not valid.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = IOException.class)
    public void schouldThrowAnIOExceptionIfTheDataFileLocationIsNotValid()
	    throws IOException, UnsupportedDataFileFormatException {

	final File wrongDataFile = new File("/this/path/does/not/exist");
	DataFileAccess.instance().getDatabaseHandler(wrongDataFile);
    }

    /**
     * Should throw an exception if it gets a file with a not supported
     * identifier.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = UnsupportedDataFileFormatException.class)
    public void shouldThrowAnExceptionIfItGetsAFileWithANotSupportedIdentifier()
	    throws IOException, UnsupportedDataFileFormatException {

	final int invalidIdentifier = -1;
	anyFile = File.createTempFile("test", "DataFileSchemaFactoryTest");
	final DataOutputStream writer = new DataOutputStream(
		new FileOutputStream(anyFile));
	writer.writeInt(invalidIdentifier);

	DataFileAccess.instance().getDatabaseHandler(anyFile);
    }

    /**
     * Should throw an exception if the data file is invalid.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = UnsupportedDataFileFormatException.class)
    public void shouldThrowAnExceptionIfTheDataFileIsInvalid()
	    throws IOException, UnsupportedDataFileFormatException {

	anyFile = File.createTempFile("test", "DataFileAccessServiceTest");
	final FileWriter writer = new FileWriter(anyFile);
	try {
	    writer.write("This is just a random file with text");
	} finally {
	    writer.close();
	}

	DataFileAccess.instance().getDatabaseHandler(anyFile);
    }

    @Test
    public void shouldCreateADatabaseHandlerThatCouldWriteARecordToAnIndex()
	    throws IOException, UnsupportedDataFileFormatException {
	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final List<String> writeRecord = Lists.newArrayList("TEST",
		"Pleasantville", "6", "N", "$160.00", "2005/03/04", "");
	handler.writeRecord(writeRecord, 100);

	final Record readRecord = handler.readRecord(100);
	assertThat(readRecord.getAllBusinessValues(), is(equalTo(writeRecord)));
    }

    @Test
    public void shouldCreateADatabaseHandlerThatCouldDeleteAnValidRecordAtAnIndex()
	    throws IOException, UnsupportedDataFileFormatException {
	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	handler.deleteRecord(6);

	final Record record = handler.readRecord(6);
	assertFalse(record.isValid());
    }

}
