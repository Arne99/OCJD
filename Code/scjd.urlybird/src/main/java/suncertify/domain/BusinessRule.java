package suncertify.domain;

import suncertify.common.Command;

public interface BusinessRule<T extends Command> {

    boolean isSatisfiedBy(T command);

}
