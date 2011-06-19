package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suncertify.admin.service.AdministrationServiceIntegrationTest;
import suncertify.datafile.DataFileTestSuite;
import suncertify.db.DataConcurrencyTest;
import suncertify.db.DataTest;
import suncertify.db.SynchronizedRecordLockerTest;
import suncertify.domain.DomainTestSuite;

/**
 * The Class AllTestsSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ DataFileTestSuite.class, DomainTestSuite.class,
	AdministrationServiceIntegrationTest.class, DataConcurrencyTest.class,
	SynchronizedRecordLockerTest.class, DataTest.class })
public class AllTestsSuite {

}
