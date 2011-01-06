package suncertify.db;

public interface RecordMatchingSpecification {

    boolean isSatisfiedBy(Record record);

}
