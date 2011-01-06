package suncertify.util;

import java.util.Collection;

public final class DesignByContract {

    private DesignByContract() {
	super();
    }

    public static void checkNotNull(final Object schouldBeNotNull,
	    final String paramName) {
	if (schouldBeNotNull == null) {
	    throw new IllegalArgumentException(paramName + " is null!");
	}
    }

    public static void checkPositiv(final Number number, final String paramName) {
	if (number.intValue() <= 0) {
	    throw new IllegalArgumentException(paramName + " is <= 0 !");
	}
    }

    public static void checkNotNegativ(final Number number,
	    final String paramName) {
	if (number.intValue() < 0) {
	    throw new IllegalArgumentException(paramName + " is < 0 !");
	}
    }

    public static void checkMustNotBeSmallerThen(final int notSmaller,
	    final int then) {
	if (notSmaller < then) {
	    throw new IllegalArgumentException();
	}
    }

    public static void checkIsTrue(final boolean condition, final String message) {
	if (!condition) {
	    throw new IllegalArgumentException(message);
	}
    }

    public static void checkArrayHasLength(final byte[] array, final int lenght) {
	if (array.length != lenght) {
	    throw new IllegalArgumentException(
		    "The array has not the specified length: " + lenght
			    + "  array length is: " + array.length);
	}
    }

    public static void checkCollectionHasSize(final Collection<?> collection,
	    final int size) {
	if (collection.size() != size) {
	    throw new IllegalArgumentException(
		    "The collection has not the specified size: " + size
			    + "  collection size is: " + collection.size());
	}
    }
}
