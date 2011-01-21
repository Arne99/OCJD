package suncertify.db;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class SynchronizedRecordLockerTest {

    @Test
    public void shouldDoNothingIfItChecksTheRecordOwnerAndNoOneOwnesTheRecord()
	    throws SecurityException {

	final SynchronizedRecordLocker recordLocker = new SynchronizedRecordLocker(
		Maps.<Integer, Long> newHashMap());
	final int anyIndex = 0;
	final long anyOwner = 0;
	recordLocker.checkRecordOwner(anyIndex, anyOwner);
    }

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
