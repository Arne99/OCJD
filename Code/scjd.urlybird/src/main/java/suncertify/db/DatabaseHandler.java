package suncertify.db;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * @author arnelandwehr
 * 
 */
public interface DatabaseHandler {

    void deleteRecord(int index) throws IOException;

    Set<Record> findMatchingRecords(
	    final RecordMatchingSpecification specification) throws IOException;

    Record readRecord(int index) throws IOException;

    void writeRecord(List<String> record, int index) throws IOException;

    int findEmptyIndex() throws IOException;
}
