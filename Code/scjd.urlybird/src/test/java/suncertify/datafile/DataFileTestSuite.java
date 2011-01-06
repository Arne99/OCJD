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
@Suite.SuiteClasses({ UrlyBirdSchemaFactoryTest.class,
	DataFileHeaderTest.class, BusinessColumnTest.class,
	UrlyBirdSchemaTest.class, DataFileAccessTest.class,
	Utf8DecoderTest.class, UrlyBirdSchemaFactoryTest.class,
	DataFileHandlerTest.class, RecordValueTest.class,
	DeletedColumnTest.class })
public class DataFileTestSuite {

}
