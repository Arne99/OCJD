package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import suncertify.util.Range;

/**
 * The Class DeletedColumnTest.
 */
public final class DeletedColumnTest {

    /** The Constant ANY_NAME. */
    private static final String ANY_NAME = "Test";

    /** The Constant ANY_NOT_DELETED_FLAG. */
    private static final String ANY_NOT_DELETED_FLAG = "0";

    /** The Constant ANY_DELETED_FLAG. */
    private static final String ANY_DELETED_FLAG = "1";

    /** The Constant ANY_RANGE. */
    private static final Range ANY_RANGE = new Range(0, 10);

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	assertThat(column, is(equalTo(column)));
    }

    /**
     * Should be symmetrical equals.
     */
    @Test
    public void shouldBeSymmetricalEquals() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	assertThat(columnOne, is(equalTo(columnTwo)));
    }

    /**
     * Should be different to an column with an different name.
     */
    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentName() {

	final DeletedColumn columnOne = new DeletedColumn("first", ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    /**
     * Should be different to an column with an different range.
     */
    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentRange() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, new Range(
		10, 12), ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn("second", new Range(
		10, 14), ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    /**
     * Should be different to an column with an different deleted flag.
     */
    @Test
    public void shouldBeDifferentToAnColumnWithAnDifferentDeletedFlag() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		"0", "1");
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		"0", "2");

	assertThat(columnOne, is(not(equalTo(columnTwo))));
    }

    /**
     * Should have the same hash code than an equals column.
     */
    @Test
    public void shouldHaveTheSameHashCodeThanAnEqualsColumn() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	final DeletedColumn columnTwo = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);
	assertThat(columnOne.hashCode(), is(equalTo(columnTwo.hashCode())));
    }

    /**
     * Should have an different hash code than a different column.
     */
    @Test
    public void shouldHaveAnDifferentHashCodeThanADifferentColumn() {

	final DeletedColumn columnOne = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, "2");
	final DeletedColumn columnTwo = new DeletedColumn("second", ANY_RANGE,
		ANY_NOT_DELETED_FLAG, "3");

	assertThat(columnOne.hashCode(), is(not(equalTo(columnTwo.hashCode()))));
    }

    /**
     * Should never contain business values.
     */
    @Test
    public void shouldNeverContainBusinessValues() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME, ANY_RANGE,
		ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);

	assertFalse(column.containsValuesOfType(ColumnType.BUSINESS));
    }

    /**
     * Should always have the not deleted flag as its default value.
     */
    @Test
    public void shouldAlwaysHaveTheNotDeletedFlagAsItsDefaultValue() {

	final DeletedColumn column = new DeletedColumn(ANY_NAME,
		new Range(0, 0), ANY_NOT_DELETED_FLAG, ANY_DELETED_FLAG);

	final RecordValue defaultValue = column.createDefaultValue();
	assertThat(defaultValue.getValue(), is(equalTo(ANY_NOT_DELETED_FLAG)));
    }
}
