package suncertify.datafile;

/**
 * The Class Pair.
 * 
 * @param <T>
 *            the generic type
 * @param <V>
 *            the value type
 */
public final class Pair<T, V> {

    /** The left. */
    private final T left;

    /** The rigth. */
    private final V rigth;

    /**
     * Instantiates a new pair.
     * 
     * @param left
     *            the left
     * @param rigth
     *            the rigth
     */
    public Pair(final T left, final V rigth) {
	super();
	this.left = left;
	this.rigth = rigth;
    }

    /**
     * Gets the left.
     * 
     * @return the left
     */
    public T getLeft() {
	return left;
    }

    /**
     * Gets the rigth.
     * 
     * @return the rigth
     */
    public V getRigth() {
	return rigth;
    }

}
