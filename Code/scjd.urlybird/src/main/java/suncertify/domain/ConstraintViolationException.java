package suncertify.domain;

public class ConstraintViolationException extends Exception {

    public ConstraintViolationException(final String print) {
	super(print);
    }

}
