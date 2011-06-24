package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import suncertify.common.Range;

/**
 * The Class RecordValueTest.
 */
public final class RecordValueTest {

    /** The Constant ANY_COLUMN. */
    private static final DataFileColumn ANY_COLUMN = new BusinessValueColumn(
	    "test", new Range(0, 100));

    /** The Constant ANY_OTHER_VALUE. */
    private static final String ANY_OTHER_VALUE = "ANY_OTHER_VALUE";

    /** The Constant ANY_VALUE. */
    private static final String ANY_VALUE = "Test";

    /**
     * Should have the same hash code than an equal record value.
     */
    @Test
    public void shouldHaveTheSameHashCodeThanAnEqualRecordValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN, ANY_VALUE);

	assertThat(firstValue.hashCode(), is(equalTo(secondValue.hashCode())));
    }

    /**
     * Should have an other hash code than an different record value.
     */
    @Test
    public void shouldHaveAnOtherHashCodeThanAnDifferentRecordValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN,
		ANY_OTHER_VALUE);

	assertThat(firstValue.hashCode(),
		is(not(equalTo(secondValue.hashCode()))));
    }

    /**
     * Should always return an value with the column size.
     */
    @Test
    public void shouldAlwaysReturnAnValueWithTheColumnSize() {

	final DataFileColumn columnWithLength10 = new BusinessValueColumn(
		"name", new Range(0, 9));
	final String valueWithLength8 = "12345678";
	final RecordValue recordValue = new RecordValue(columnWithLength10,
		valueWithLength8);

	assertThat(recordValue.getValue(), is(equalTo(valueWithLength8 + "  ")));
    }

    /**
     * Should be an business value if the column contains business values.
     */
    @Test
    public void shouldBeAnBusinessValueIfTheColumnContainsBusinessValues() {

	final DataFileColumn column = mock(DataFileColumn.class);
	when(column.getSize()).thenReturn(ANY_VALUE.length());
	when(column.containsValuesOfType(ColumnType.BUSINESS)).thenReturn(true);

	final RecordValue recordValue = new RecordValue(column, ANY_VALUE);

	assertTrue(recordValue.isBuisnessValue());
    }

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final RecordValue value = new RecordValue(ANY_COLUMN, ANY_VALUE);
	assertThat(value, is(equalTo(value)));
    }

    /**
     * Should be symmetric equals.
     */
    @Test
    public void shouldBeSymmetricEquals() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN, ANY_VALUE);

	assertThat(firstValue, is(equalTo(secondValue)));
	assertThat(secondValue, is(equalTo(firstValue)));
    }

    /**
     * Should be different to an record value with an different value.
     */
    @Test
    public void shouldBeDifferentToAnRecordValueWithAnDifferentValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN,
		ANY_OTHER_VALUE);

	assertThat(firstValue, is(not(equalTo(secondValue))));
    }

}
