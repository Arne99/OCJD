package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import suncertify.util.Range;

public class RecordValueTest {

    private static final BusinessColumn ANY_COLUMN = new BusinessColumn("test",
	    new Range(0, 100));

    private static final String ANY_OTHER_VALUE = "ANY_OTHER_VALUE";

    private static final String ANY_VALUE = "Test";

    @Test
    public void shouldHaveTheSameHashCodeThanAnEqualRecordValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN, ANY_VALUE);

	assertThat(firstValue.hashCode(), is(equalTo(secondValue.hashCode())));
    }

    @Test
    public void shouldHaveAnOtherHashCodeThanAnDifferentRecordValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN,
		ANY_OTHER_VALUE);

	assertThat(firstValue.hashCode(),
		is(not(equalTo(secondValue.hashCode()))));
    }

    @Test
    public void shouldAlwaysReturnAnValueWithTheColumnSize() {

	final BusinessColumn columnWithLength10 = new BusinessColumn("name",
		new Range(0, 9));
	final String valueWithLength8 = "12345678";
	final RecordValue recordValue = new RecordValue(columnWithLength10,
		valueWithLength8);

	assertThat(recordValue.getValue(), is(equalTo(valueWithLength8 + "  ")));
    }

    @Test
    public void shouldBeAnBusinessValueIfTheColumnContainsBusinessValues() {

	final DataFileColumn column = mock(DataFileColumn.class);
	when(column.getSize()).thenReturn(ANY_VALUE.length());
	when(column.containsBuissnessValues()).thenReturn(true);

	final RecordValue recordValue = new RecordValue(column, ANY_VALUE);

	assertTrue(recordValue.isBuisnessValue());
    }

    @Test
    public void shouldBeEqualsToItself() {

	final RecordValue value = new RecordValue(ANY_COLUMN, ANY_VALUE);
	assertThat(value, is(equalTo(value)));
    }

    @Test
    public void shouldBeSymmetricEquals() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN, ANY_VALUE);

	assertThat(firstValue, is(equalTo(secondValue)));
	assertThat(secondValue, is(equalTo(firstValue)));
    }

    @Test
    public void shouldBeDifferentToAnRecordValueWithAnDifferentValue() {

	final RecordValue firstValue = new RecordValue(ANY_COLUMN, ANY_VALUE);
	final RecordValue secondValue = new RecordValue(ANY_COLUMN,
		ANY_OTHER_VALUE);

	assertThat(firstValue, is(not(equalTo(secondValue))));
    }

}
