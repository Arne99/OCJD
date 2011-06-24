package suncertify.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import suncertify.common.Specification;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class DataTest {

    final DatabaseHandler databaseHandler = mock(DatabaseHandler.class);
    final RecordLocker recordLocker = mock(RecordLocker.class);
    final Record record = mock(Record.class);

    final Data data = new Data(databaseHandler, recordLocker);

    @Test
    public void shouldCreateANewRecordAndPersistItWithTheDatabaseHandler()
	    throws DuplicateKeyException, IOException {

	data.create(new String[] {});
	verify(databaseHandler).writeRecord(anyList(), anyInt());
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldThrowAnDuplicateKEyExceptionIfTheRecordIsAlreadyStoredInTheDatabase()
	    throws DuplicateKeyException, IOException {

	final HashSet<Record> result = Sets.newHashSet(mock(Record.class));
	when(
		databaseHandler
			.findMatchingRecords((Specification<Record>) anyObject()))
		.thenReturn(result);
	data.create(new String[] {});
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowAnDatabaseExceptionIfAnIoExceptionHappens()
	    throws IOException, DuplicateKeyException {

	doThrow(new IOException()).when(databaseHandler).writeRecord(anyList(),
		anyInt());
	data.create(new String[] {});
    }

    @Test
    public void shouldDeleteTheRecordWithTheGivenIndexWithTheDatabaseHandlerIfTheGivenCookieIndicatesTheOwner()
	    throws RecordNotFoundException, SecurityException, IOException {

	final int index = 10;
	final int cookie = 12;

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(10)).thenReturn(record);

	data.delete(index, cookie);
	verify(databaseHandler).deleteRecord(index);
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowAnSecurityExceptionAtDeleteIfTheGivenCookieIndicatesNotTheOwner()
	    throws SecurityException, RecordNotFoundException {

	final int index = 10;
	final int cookie = 12;

	doThrow(new SecurityException()).when(recordLocker).checkRecordOwner(
		anyInt(), anyLong());

	data.delete(index, cookie);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowAnRecordNotFoundExceptionAtDeleteIfTheDatabaseHandlerCouldNotDeleteTheRecord()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 10;
	final int cookie = 12;

	final Record record = mock(Record.class);
	when(record.isValid()).thenReturn(false);

	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.delete(index, cookie);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThorwAnRecordNotFoundExceptionAtDeleteIfAnIoExceptionOccurs()
	    throws RecordNotFoundException, SecurityException, IOException {

	final int index = 10;
	final int cookie = 12;

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(10)).thenReturn(record);
	doThrow(new IOException()).when(databaseHandler).deleteRecord(10);

	data.delete(index, cookie);
    }

    @Test
    public void shouldUpdateARecordWithTheDatabaseHandler()
	    throws RecordNotFoundException, SecurityException, IOException {

	final int index = 15;
	final int cookie = 33;
	final String[] values = new String[] {};

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(15)).thenReturn(record);

	data.update(index, values, cookie);
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowAnSecurityEceptionIfTheCookieDoesNotBelogToTheRecordOwner()
	    throws RecordNotFoundException, SecurityException {

	final int index = 15;
	final int cookie = 33;
	final String[] values = new String[] {};

	doThrow(new SecurityException()).when(recordLocker).checkRecordOwner(
		index, cookie);

	data.update(index, values, cookie);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheRecordToUpdateIsNotStored()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 15;
	final int cookie = 33;
	final String[] values = new String[] {};

	when(record.isValid()).thenReturn(false);
	when(databaseHandler.readRecord(15)).thenReturn(record);

	data.update(index, values, cookie);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfAnIoExceptionOccurs()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 15;
	final int cookie = 33;
	final String[] values = new String[] {};

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(15)).thenReturn(record);
	doThrow(new IOException()).when(databaseHandler).writeRecord(anyList(),
		anyInt());

	data.update(index, values, cookie);
    }

    @Test
    public void shouldUnlockALockedRecordWithTheRecordLocker()
	    throws RecordNotFoundException, SecurityException, IOException {

	final int index = 15;
	final int cookie = 33;

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.unlock(index, cookie);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheRecordToUnlockDoesNotExist()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 15;
	final int cookie = 33;

	when(record.isValid()).thenReturn(false);
	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.unlock(index, cookie);
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowAnSecurityExceptionIfTheRecordIsLockedByAnOtherOwner()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 15;
	final int cookie = 33;

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(index)).thenReturn(record);
	doThrow(new SecurityException()).when(recordLocker).unlockRecord(index,
		cookie);

	data.unlock(index, cookie);
    }

    @Test
    public void shouldLockARecordWithTheRecordLocker()
	    throws RecordNotFoundException, SecurityException, IOException {

	final int index = 15;

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.lock(index);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheRecordToLockDoesNotExist()
	    throws IOException, RecordNotFoundException, SecurityException {

	final int index = 15;

	when(record.isValid()).thenReturn(false);
	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.lock(index);
    }

    @Test
    public void shouldReadARecordWithTheGiveIndexnAndReturnItAsAnStringArrays()
	    throws RecordNotFoundException, IOException {

	final int index = 29;
	final ArrayList<String> recordValues = Lists.newArrayList("Hallo",
		"Test");

	when(record.isValid()).thenReturn(true);
	when(databaseHandler.readRecord(index)).thenReturn(record);
	when(record.getAllBusinessValues()).thenReturn(recordValues);

	final String[] values = data.read(index);

	assertThat(values, is(equalTo(recordValues.toArray(new String[] {}))));
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheIndexToReadContainsNoRecord()
	    throws RecordNotFoundException, IOException {

	final int index = 29;

	when(record.isValid()).thenReturn(false);
	when(databaseHandler.readRecord(index)).thenReturn(record);

	data.read(index);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowAtReadARecordNotFoundExceptionIfAIoExceptionOccurs()
	    throws RecordNotFoundException, IOException {

	final int index = 29;

	when(databaseHandler.readRecord(index)).thenThrow(new IOException());

	data.read(index);
    }

    @Test
    public void shouldFindAllIndicesThatContainsRecordsThatMatchesTheGivenCriteria()
	    throws IOException {

	final String[] criteria = new String[] {};

	final Record record1 = mock(Record.class);
	final Record record2 = mock(Record.class);

	when(record1.getIndex()).thenReturn(1);
	when(record2.getIndex()).thenReturn(2);

	final HashSet<Record> records = Sets.newHashSet(record1, record2);
	when(databaseHandler.findMatchingRecords((Specification<Record>) any()))
		.thenReturn(records);

	final int[] result = data.find(criteria);

	assertThat(result, is(equalTo(new int[] { 1, 2 })));
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowADatabaseExceptionIfAnIoExceptionOccurs()
	    throws IOException {

	final String[] criteria = new String[] {};

	when(databaseHandler.findMatchingRecords((Specification<Record>) any()))
		.thenThrow(new IOException());

	data.find(criteria);
    }
}
