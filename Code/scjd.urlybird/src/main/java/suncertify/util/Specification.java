package suncertify.util;

/**
 * 
 * 
 * @author arnelandwehr
 * 
 */
public interface Specification<T> {

    boolean isSatisfiedBy(T record);

}
