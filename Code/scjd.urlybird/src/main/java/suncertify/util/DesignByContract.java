package suncertify.util;

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

}
