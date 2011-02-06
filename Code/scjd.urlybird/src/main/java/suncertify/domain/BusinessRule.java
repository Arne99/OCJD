package suncertify.domain;

public interface BusinessRule<T> {

    boolean isSatisfiedBy(T command);

}
