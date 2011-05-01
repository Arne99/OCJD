package suncertify.datafile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for all Test for the Package datafile.
 * 
 * @author arnelandwehr
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ Format257SchemaBuilderTest.class,
	DataFileHeaderTest.class, BusinessValueColumnTest.class,
	SchemaWithDeletedColumnTest.class, DataFileServiceTest.class,
	Utf8DecoderTest.class, Format257SchemaBuilderTest.class,
	DataFileHandlerTest.class, RecordValueTest.class,
	DeletedColumnTest.class })
public class DataFileTestSuite {

}
