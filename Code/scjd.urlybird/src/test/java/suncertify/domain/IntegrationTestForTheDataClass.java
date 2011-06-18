package suncertify.domain;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import suncertify.datafile.DataFileService;
import suncertify.datafile.UnsupportedDataFileFormatException;
import suncertify.db.DB;
import suncertify.db.Data;
import suncertify.db.DatabaseConnectionException;
import suncertify.db.DatabaseService;

/**
 * The <code>DataClassTest</code> tests the main functionalities of the.
 * 
 * {@link Data} class. In order to simulate several clients trying to use it and
 * exercise the locking mechanism, it also has several inner classes that extend
 * the {@link Thread} class, where each class represents one client requesting
 * one operation, and mainly requesting updating and deletion of records. The
 * <code>FindingRecordsThread</code> exercises two functionalities: finding
 * records and reading records.
 * 
 * @author Roberto Perillo
 * @version 1.0 05/11/2008
 */
public final class IntegrationTestForTheDataClass {

    /** The data. */
    private static DB data = null;
    static {
	File anyFile = null;
	try {
	    anyFile = File.createTempFile("test", "test");
	} catch (final IOException e1) {
	    e1.printStackTrace();
	}
	try {
	    Files.copy(
		    new File(
			    "/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db"),
		    anyFile);
	} catch (final IOException e1) {
	    e1.printStackTrace();
	}
	try {
	    data = DatabaseService.instance().connectToDatabase(
		    DataFileService.instance().getDatabaseHandler(anyFile));
	} catch (final DatabaseConnectionException e) {
	    e.printStackTrace();
	} catch (final IOException e) {
	    e.printStackTrace();
	} catch (final UnsupportedDataFileFormatException e) {
	    e.printStackTrace();
	}
    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
	new IntegrationTestForTheDataClass().startTests();
    }

    /**
     * Start tests.
     */
    public void startTests() {
	try {

	    /*
	     * Practically, it is not necessary to execute this loop more than 1
	     * time, but if you want, you can increase the controller variable,
	     * so it is executed as many times as you want
	     */
	    for (int i = 0; i < 500; i++) {
		final Thread updatingRandom = new UpdatingRandomRecordThread();
		updatingRandom.start();
		final Thread updatingRecord1 = new UpdatingRecord1Thread();
		updatingRecord1.start();
		final Thread creatingRecord = new CreatingRecordThread();
		creatingRecord.start();
		final Thread deletingRecord = new DeletingRecord1Thread();
		deletingRecord.start();
		final Thread findingRecords = new FindingRecordsThread();
		findingRecords.start();
	    }
	} catch (final Exception e) {
	    System.out.println(e.getMessage());
	}

    }

    /**
     * The Class UpdatingRandomRecordThread.
     */
    private class UpdatingRandomRecordThread extends Thread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

	    final int recNo = (int) (Math.random() * 50);
	    try {
		System.out.println(Thread.currentThread().getId()
			+ " trying to lock record #" + recNo
			+ " on UpdatingRandomRecordThread");

		/*
		 * The generated record number may not exist in the database, so
		 * a RecordNotFoundException must be thrown by the lock method.
		 * Since the database records are in a cache, it is not
		 * necessary to put the unlock instruction in a finally block,
		 * because an exception can only occur when calling the lock
		 * method (not when calling the update/delete methods),
		 * therefore it is not necessary to call the unlock method in a
		 * finally block, but you can customize this code according to
		 * your reality
		 */
		final long lock = data.lock(recNo);
		System.out.println(Thread.currentThread().getId()
			+ " trying to update record #" + recNo
			+ " on UpdatingRandomRecordThread");

		/*
		 * An exception cannot occur here, otherwise, the unlock
		 * instruction will not be reached, and the record will be
		 * locked forever. In this case, I created a class called
		 * RoomRetriever, which transforms from Room to String array,
		 * and vice-versa, but it could also be done this way:
		 * 
		 * data.update(recNo, new String[] {"Palace", "Smallville", "2",
		 * "Y", "$150.00", "2005/07/27", null});
		 */
		data.update(recNo, new String[] { "Palace", "Smallville", "2",
			"Y", "$150.00", "2005/07/06", "12345678" }, lock);
		System.out.println(Thread.currentThread().getId()
			+ " trying to unlock record #" + recNo
			+ " on UpdatingRandomRecordThread");
		data.unlock(recNo, lock);
	    } catch (final Exception e) {
		System.out.println(e);
	    }
	}
    }

    /**
     * The Class UpdatingRecord1Thread.
     */
    private class UpdatingRecord1Thread extends Thread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

	    try {
		System.out.println(Thread.currentThread().getId()
			+ " trying to lock record #1 on"
			+ " UpdatingRecord1Thread");
		final long lock = data.lock(1);
		System.out.println(Thread.currentThread().getId()
			+ " trying to update record #1 on"
			+ " UpdatingRecord1Thread");
		data.update(1, new String[] { "Caste", "Digitopolis", "2", "N",
			"$90.00", "2004/01/17", "88006644" }, lock);
		System.out.println(Thread.currentThread().getId()
			+ " trying to unlock record #1 on"
			+ "UpdatingRecord1Thread");

		/*
		 * In order to see the deadlock, this instruction can be
		 * commented, and the other Threads, waiting to update/delete
		 * record #1 will wait forever and the deadlock will occur
		 */
		data.unlock(1, lock);
	    } catch (final Exception e) {
		System.out.println(e);
	    }
	}
    }

    /**
     * The Class CreatingRecordThread.
     */
    private class CreatingRecordThread extends Thread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

	    try {
		System.out.println(Thread.currentThread().getId()
			+ " trying to create a record");
		data.create(new String[] { "Elephant Inn", "EmeraldCity", "6",
			"N", "$120.00", "2003/06/10", "" });
	    } catch (final Exception e) {
		System.out.println(e);
	    }
	}
    }

    /**
     * The Class DeletingRecord1Thread.
     */
    private class DeletingRecord1Thread extends Thread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
	    try {
		System.out.println(Thread.currentThread().getId()
			+ " trying to lock record #1 on "
			+ "DeletingRecord1Thread");
		final long lock = data.lock(1);
		System.out.println(Thread.currentThread().getId()
			+ " trying to delete record #1 on "
			+ "DeletingRecord1Thread");
		data.delete(1, lock);
		System.out.println(Thread.currentThread().getId()
			+ " trying to unlock record #1 on "
			+ "DeletingRecord1Thread");
		data.unlock(1, lock);
	    } catch (final Exception e) {
		System.out.println(e);
	    }
	}
    }

    /**
     * The Class FindingRecordsThread.
     */
    private class FindingRecordsThread extends Thread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
	    try {
		System.out.println(Thread.currentThread().getId()
			+ " trying to find records");
		final String[] criteria = { "Palace", "Smallville", null, null,
			null, null, null };
		final int[] results = data.find(criteria);

		for (int i = 0; i < results.length; i++) {
		    System.out.println(results.length + " results found.");
		    try {
			final String message = Thread.currentThread().getId()
				+ " going to read record #" + results[i]
				+ " in FindingRecordsThread - still "
				+ ((results.length - 1) - i) + " to go.";
			System.out.println(message);
			final String[] room = data.read(results[i]);
			System.out.println("Hotel (FindingRecordsThread): "
				+ room[0]);
			System.out.println("Has next? "
				+ (i < (results.length - 1)));
		    } catch (final Exception e) {
			/*
			 * In case a record was found during the execution of
			 * the find method, but deleted before the execution of
			 * the read instruction, a RecordNotFoundException will
			 * occur, which would be normal then
			 */
			System.out.println("Exception in "
				+ "FindingRecordsThread - " + e);
		    }
		}
		System.out.println("Exiting for loop");
	    } catch (final Exception e) {
		System.out.println(e);
	    }
	}
    }
}