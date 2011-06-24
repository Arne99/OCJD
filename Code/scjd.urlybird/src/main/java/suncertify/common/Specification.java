package suncertify.common;

/**
 * An {@link Specification} is a fixed rule that could be fulfilled by an object
 * or not. This interface is the parent for every business rule. inspired by
 * domain driven design by Eric Evans.
 * 
 * @param <T>
 *            the type of the object this specification could proof.
 * 
 * @author arnelandwehr
 * 
 */
public interface Specification<T> {

    /**
     * Proofs if this specification is hold by the given object.
     * 
     * @param myObject
     *            the object to check.
     * @return <code>true</code> if the object holds the specification.
     */
    boolean isSatisfiedBy(T myObject);

}
