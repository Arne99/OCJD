package suncertify.util;

public class Pair<T, V> {

    private final T left;
    private final V rigth;

    public Pair(final T left, final V rigth) {
	super();
	this.left = left;
	this.rigth = rigth;
    }

    public T getLeft() {
	return left;
    }

    public V getRigth() {
	return rigth;
    }

}
