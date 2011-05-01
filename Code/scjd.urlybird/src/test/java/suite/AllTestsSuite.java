package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suncertify.datafile.DataFileTestSuite;
import suncertify.domain.DomainTestSuite;

/**
 * The Class AllTestsSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ DataFileTestSuite.class, DomainTestSuite.class })
public class AllTestsSuite {

}
