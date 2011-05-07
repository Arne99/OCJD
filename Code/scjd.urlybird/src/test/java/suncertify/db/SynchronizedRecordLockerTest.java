package suncertify.db;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * The Class SynchronizedRecordLockerTest.
 */
public final class SynchronizedRecordLockerTest {

    /**
     * Should do nothing if it checks the record owner and no one ownes the
     * record.
     * 
     * @throws SecurityException
     *             the security exception
     */
    @Test
    public void shouldDoNothingIfItChecksTheRecordOwnerAndNoOneOwnesTheRecord()
	    throws SecurityException {

	final SynchronizedRecordLocker recordLocker = new SynchronizedRecordLocker(
		Maps.<Integer, Long> newHashMap());
	final int anyIndex = 0;
	final long anyOwner = 0;
	recordLocker.checkRecordOwner(anyIndex, anyOwner);
    }

    /**
     * Should throw an security exception if the record is already locke by an
     * other owner.
     * 
     * @throws SecurityException
     *             the security exception
     */
    @Test(expected = SecurityException.class)
    public void shouldThrowAnSecurityExceptionIfTheRecordIsAlreadyLockeByAnOtherOwner()
	    throws SecurityException {

	final int lockedRecord = 1;
	final long recordOwner = 234L;
	final ImmutableMap<Integer, Long> mapWithLockedRecord = ImmutableMap
		.of(lockedRecord, recordOwner);
	final SynchronizedRecordLocker recordLocker = new SynchronizedRecordLocker(
		mapWithLockedRecord);
	recordLocker.checkRecordOwner(lockedRecord, 235L);
    }

}
