package suncertify.db;

public interface RecordLocker {

    long lockRecord(final int index);

    void unlockRecord(final int index, long ownerId) throws SecurityException;

    void checkRecordOwner(int index, long ownerId) throws SecurityException;

}
