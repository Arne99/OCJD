package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import suncertify.util.Range;

public class DeletedColumnTest {

    private static final String ANY_NAME = "Test";
    private static final String ANY_DELETED_FLAG = "1";
    private static final Range ANY_RANGE = new Range(0, 10);

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfTheGivenDeletedFlagLengthIsGreaterThanThenTheSizeOfTheColumn() {

	final Range rangeWithLength1 = new Range(0, 0);
	final String deletedFlagWithLength2 = "12";

	new DeletedColumn(ANY_NAME, rangeWithLength1, deletedFlagWithLength2);
    }

    @Test
    public void shouldBeEqualsToItself() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);
	assertThat(column, is(equalTo(column)));
    }

    @Test
    public void shouldBeSymmetricalEquals() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);
	assertThat(columnOne, is(equalTo(columnTwo)));
    }

    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentName() {

	final DeletedColumn columnOne = new DeletedColumn("first", ANY_RANGE,
		ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		ANY_DELETED_FLAG);

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentRange() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, new Range(
		10, 12), ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn("second", new Range(
		10, 14), ANY_DELETED_FLAG);

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentDeletedFlag() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		"0");
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		"1");

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    @Test
    public void shouldHaveTheSameHashCodeThanAnEqualsColumn() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);
	assertThat(columnOne.hashCode(), is(equalTo(columnTwo.hashCode())));
    }

    @Test
    public void shouldHaveAnDifferentHashCodeThanADifferentColumn() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		"0");
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		"1");

	assertThat(columnOne.hashCode(), is(not(equalTo(columnTwo.hashCode()))));
    }

    @Test
    public void shouldNeverContainBusinessValues() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_DELETED_FLAG);

	assertFalse(column.containsBuissnessValues());
    }

    @Test
    public void shouldAlwaysHaveTheDeletedFlagAsItsDefaultValue() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME,
		new Range(0, 0), "1");

	final RecordValue defaultValue = column.createDefaultValue();
	assertThat(defaultValue.getValue(), is(equalTo(ANY_DELETED_FLAG)));
    }

    @Test
    public void shouldSayThatAValueIsValidIfItIsDifferentToTheDeletedFlag() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME,
		new Range(0, 0), "1");
	assertTrue(column.isValidValue("0"));
    }

}
