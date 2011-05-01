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

import com.google.common.io.Files;

public class DatabaseTest {

    private final static String PATH = "/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Database/db-1x1.db";
    private File testDb;

    private volatile boolean exceptionHappened;

    private DatabaseHandler databaseHandler;
    private SynchronizedRecordLocker locker;
    private Database db;

    @Before
    public void setUp() throws IOException, UnsupportedDataFileFormatException {

	testDb = File.createTempFile("Database", "Test");
	Files.copy(new File(PATH), testDb);

	databaseHandler = DataFileService.instance().getDatabaseHandler(testDb);
	locker = new SynchronizedRecordLocker(new HashMap<Integer, Long>());
	db = new Database(databaseHandler, locker);

    }

    @After
    public void tearDown() {
	testDb.delete();
    }

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
			if (!Arrays.equals(read, new String[] { "Palace",
				"Smallville", "2", "Y", "$150.00",
				"2005/07/27", "", "0" })) {
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

    @Test
    public void shouldBeThreadSafeForConcurrentReadsAndFinds()
	    throws InterruptedException {

	final Runnable readAndFiendThe10thRecord = new Runnable() {

	    @Override
	    public void run() {
		for (int i = 0; i <= 100; i++) {
		    try {
			final String[] read = db.read(10);
			Thread.yield();
			final int[] find = db.find(new String[] { read[0],
				null, null, null, read[4], null, null, null });
			if (!Arrays.equals(read, new String[] { "Dew Drop Inn",
				"Digitopolis", "4", "N", "$190.00",
				"2005/09/17", "", "10" })
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
			if (!Arrays.equals(read, new String[] { "T", "T", "T",
				"T", "T", "T", "T", "0" })) {
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

}
