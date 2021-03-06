package suncertify.db;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * The Class DatabaseTest.
 */
public final class DataConcurrencyTest {

    /** The Constant PATH. */
    private static final String PATH = "/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Database/db-1x1.db";

    /** The test db. */
    private File testDb;

    /** The exception happened. */
    private volatile boolean exceptionHappened;

    /** The database handler. */
    private DatabaseHandler databaseHandler;

    /** The locker. */
    private SynchronizedRecordLocker locker;

    /** The db. */
    private Data db;

    /**
     * Sets the up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Before
    public void setUp() throws IOException, UnsupportedDataFileFormatException {

	testDb = File.createTempFile("Database", "Test");
	Files.copy(new File(PATH), testDb);

	databaseHandler = DataFileService.instance().getDatabaseHandler(testDb);
	locker = new SynchronizedRecordLocker(new HashMap<Integer, Long>());
	db = new Data(databaseHandler, locker);

    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	testDb.delete();
    }

    /**
     * Should be thread safe for concurrent reads on the same row.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void shouldBeThreadSafeForConcurrentReadsOnTheSameRow()
	    throws IOException, UnsupportedDataFileFormatException,
	    InterruptedException {

	final Runnable readFirstRecord = new Runnable() {

	    @Override
	    public void run() {
		for (int i = 0; i <= 100; i++) {
		    try {
			final String[] read = db.read(0);
			final String[] trimmedRead = trimStrings(read);
			if (!Arrays.equals(trimmedRead, new String[] {
				"Palace", "Smallville", "2", "Y", "$150.00",
				"2005/07/27", "" })) {
			    exceptionHappened = true;
			}
			Thread.yield();
		    } catch (final Exception e) {
			exceptionHappened = true;
		    }
		}
	    }
	};

	excecuteConcurrent(readFirstRecord);
	assertFalse(exceptionHappened);
    }

    /**
     * Should be thread safe for concurrent reads and finds.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void shouldBeThreadSafeForConcurrentReadsAndFinds()
	    throws InterruptedException {

	final Runnable readAndFiendThe10thRecord = new Runnable() {

	    @Override
	    public void run() {
		for (int i = 0; i <= 100; i++) {
		    try {
			final String[] read = db.read(10);
			final String[] trimmedRead = trimStrings(read);
			Thread.yield();
			final int[] find = db.find(new String[] { read[0],
				null, null, null, read[4], null, null });
			if (!Arrays.equals(trimmedRead, new String[] {
				"Dew Drop Inn", "Digitopolis", "4", "N",
				"$190.00", "2005/09/17", "" })
				|| !Arrays.equals(find, new int[] { 10 })) {
			    exceptionHappened = true;
			}
			Thread.yield();
		    } catch (final Exception e) {
			exceptionHappened = true;
		    }
		}
	    }
	};

	excecuteConcurrent(readAndFiendThe10thRecord);
	assertFalse(exceptionHappened);
    }

    /**
     * Should be thread safe for concurrent writes and reades on one record.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void shouldBeThreadSafeForConcurrentWritesAndReadesOnOneRecord()
	    throws InterruptedException {

	final Runnable updateAndReadTheFirstRecord = new Runnable() {

	    @Override
	    public void run() {
		for (int i = 0; i <= 100; i++) {
		    long lock = 0;
		    try {
			lock = db.lock(0);
			Thread.yield();
			final String[] updatedData = new String[] { "T", "T",
				"T", "T", "T", "T", "T" };
			db.update(0, updatedData, lock);
			Thread.yield();
			final String[] read = db.read(0);
			final String[] trimmedRead = trimStrings(read);
			if (!Arrays.equals(trimmedRead, new String[] { "T",
				"T", "T", "T", "T", "T", "T" })) {
			    exceptionHappened = true;
			}
		    } catch (final Exception e) {
			exceptionHappened = true;
		    } finally {
			try {
			    db.unlock(0, lock);
			} catch (final Exception e) {
			    exceptionHappened = true;
			}
		    }
		}

	    }

	};

	excecuteConcurrent(updateAndReadTheFirstRecord);
	assertFalse(exceptionHappened);

    }

    /**
     * Excecute concurrent.
     * 
     * @param task
     *            the task
     * @throws InterruptedException
     *             the interrupted exception
     */
    private void excecuteConcurrent(final Runnable task)
	    throws InterruptedException {

	final List<Thread> threads = new ArrayList<Thread>();

	for (int i = 0; i <= 10; i++) {
	    final Thread thread = new Thread(task);
	    threads.add(thread);
	    thread.start();
	}

	for (final Thread thread : threads) {
	    thread.join();
	}
    }

    private String[] trimStrings(final String[] read) {
	final List<String> trimmed = Lists.newArrayList();
	for (final String string : read) {
	    trimmed.add(string.trim());
	}
	return trimmed.toArray(new String[] {});
    }

}
