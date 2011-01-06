package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import suncertify.util.Range;

/**
 * Tests for the CLass {@link BusinessColumn}.
 */
public final class BusinessColumnTest {

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final DataFileColumn column = new BusinessColumn("test", new Range(12,
		12));
	assertEquals(column, column);
    }

    /**
     * Should be symmetric equals.
     */
    @Test
    public void shouldBeSymmetricEquals() {

	final String name = "test";
	final Range range = new Range(12, 12);

	final DataFileColumn columnOne = new BusinessColumn(name, range);
	final DataFileColumn columnTwo = new BusinessColumn(name, range);

	assertEquals(columnOne, columnTwo);
	assertEquals(columnTwo, columnOne);
    }

    /**
     * Should be not equals for different columns.
     */
    @Test
    public void shouldBeNotEqualsForDifferentColumns() {

	final Range firstRange = new Range(12, 12);
	final Range secondRange = new Range(12, 12);

	final DataFileColumn columnOne = new BusinessColumn("test", firstRange);
	final DataFileColumn columnTwo = new BusinessColumn("TEST", secondRange);

	assertFalse(columnOne.equals(columnTwo));
    }

    /**
     * Should return the same hash code for equal columns.
     */
    @Test
    public void shouldReturnTheSameHashCodeForEqualColumns() {

	final String name = "test";
	final Range range = new Range(12, 12);

	final DataFileColumn columnOne = new BusinessColumn(name, range);
	final DataFileColumn columnTwo = new BusinessColumn(name, range);

	assertEquals(columnOne, columnTwo);
	assertEquals(columnOne.hashCode(), columnTwo.hashCode());
    }

    /**
     * Should return different hash codes for different columns.
     */
    @Test
    public void shouldReturnDifferentHashCodesForDifferentColumns() {

	final Range firstRange = new Range(12, 12);
	final Range secondRange = new Range(12, 12);

	final DataFileColumn columnOne = new BusinessColumn("test", firstRange);
	final DataFileColumn columnTwo = new BusinessColumn("TEST", secondRange);

	assertFalse(columnOne.equals(columnTwo));
	assertTrue(columnOne.hashCode() != columnTwo.hashCode());
    }

    @Test
    public void shouldCreateAnEmptyStringWithTheSizeOfTheColumnAsTheDefaultValue() {

	final Range range = new Range(0, 10);
	final BusinessColumn column = new BusinessColumn("test", range);

	final RecordValue value = column.createDefaultValue();

	assertThat(value.getValue().length(), is(equalTo(range.getSize())));
	assertThat(value.getValue(), is(equalTo("           ")));
    }

}
