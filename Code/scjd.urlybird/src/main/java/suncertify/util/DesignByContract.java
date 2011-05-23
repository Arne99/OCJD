package suncertify.util;

import java.util.Collection;

/**
 * Encapsulates a set of static design by contract utility methods. Mostly for
 * validating critical method parameters.
 * 
 * @author arnelandwehr
 * 
 */
public final class DesignByContract {

    /**
     * Just a helper class, never create one.
     * 
     */
    private DesignByContract() {
	super();
    }

    /**
     * Throws an {@link IllegalArgumentException} if the given param is null.
     * 
     * @param schouldBeNotNull
     *            the param to check.
     * @param paramName
     *            the name of the param.
     */
    public static void checkNotNull(final Object schouldBeNotNull,
	    final String paramName) {
	if (schouldBeNotNull == null) {
	    throw new IllegalArgumentException(paramName + " is null!");
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the given {@link Number} is
     * not greater 0.
     * 
     * @param number
     *            the number to check.
     * @param paramName
     *            the name of the param.
     */
    public static void checkPositiv(final Number number, final String paramName) {
	if (number.intValue() <= 0) {
	    throw new IllegalArgumentException(paramName + " is <= 0 !");
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the given Number is not
     * greater or equals to 0.
     * 
     * @param number
     *            the number to check
     * @param paramName
     *            the name of the param.
     */
    public static void checkNotNegativ(final Number number,
	    final String paramName) {
	if (number.intValue() < 0) {
	    throw new IllegalArgumentException(paramName + " is < 0 !");
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the first given value is
     * smaller than the second given value.
     * 
     * @param notSmaller
     *            the value that should not be smaller
     * @param then
     *            the value that should be greater
     */
    public static void checkMustNotBeSmallerThen(final int notSmaller,
	    final int then) {
	if (notSmaller < then) {
	    throw new IllegalArgumentException();
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the given array is not of
     * the given length.
     * 
     * @param array
     *            the array to check.
     * @param lenght
     *            the required length.
     */
    public static void checkArrayHasLength(final byte[] array, final int lenght) {
	if (array.length != lenght) {
	    throw new IllegalArgumentException(
		    "The array has not the specified length: " + lenght
			    + "  array length is: " + array.length);
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the given
     * {@link Collection} is not of the given size.
     * 
     * @param collection
     *            the collection to check.
     * @param size
     *            the required size.
     */
    public static void checkCollectionHasSize(final Collection<?> collection,
	    final int size) {
	if (collection.size() != size) {
	    throw new IllegalArgumentException(
		    "The collection has not the specified size: " + size
			    + "  collection size is: " + collection.size());
	}
    }

    /**
     * Throws an {@link IllegalArgumentException} if the first value is not
     * greater or equal to the second value.
     * 
     * @param greater
     *            the value that should be greater or equal
     * @param than
     *            the value that should be smaller or equal
     */
    public static void checkMustBeGreaterOrEqualThan(final int greater,
	    final int than) {

	if (greater < than) {
	    throw new IllegalArgumentException(" the first value: " + greater
		    + " is not greater than the second: " + than);
	}
    }
}
