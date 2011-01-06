package suncertify.datafile;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for the Class DataFileHeaderTest.
 */
public final class DataFileHeaderTest {

    /**
     * Should be equals to iteself.
     */
    @Test
    public void shouldBeEqualsToIteself() {

	final DataFileHeader header = new DataFileHeader(12, 12);
	assertTrue(header.equals(header));
    }

    /**
     * Should be symmetric equals.
     */
    @Test
    public void shouldBeSymmetricEquals() {

	final int key = 13;
	final int headerLength = 12;

	final DataFileHeader headerOne = new DataFileHeader(key, headerLength);
	final DataFileHeader headerTwo = new DataFileHeader(key, headerLength);

	assertTrue(headerOne.equals(headerTwo));
	assertTrue(headerTwo.equals(headerOne));
    }

    /**
     * Should return the same hash code for equal headers.
     */
    @Test
    public void shouldReturnTheSameHashCodeForEqualHeaders() {

	final int key = 13;
	final int headerLength = 12;

	final DataFileHeader headerOne = new DataFileHeader(key, headerLength);
	final DataFileHeader headerTwo = new DataFileHeader(key, headerLength);

	assertTrue(headerOne.equals(headerTwo));
	assertEquals(headerOne.hashCode(), headerTwo.hashCode());
    }

    @Test
    public void shouldReturnDifferentHashCodesForHeadersWithDifferentHeaderLength() {

	final DataFileHeader headerOne = new DataFileHeader(13, 12);
	final DataFileHeader headerTwo = new DataFileHeader(13, 11);

	assertFalse(headerOne.equals(headerTwo));
	assertTrue(headerOne.hashCode() != headerTwo.hashCode());
    }

}
