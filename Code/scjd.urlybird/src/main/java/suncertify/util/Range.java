package suncertify.util;

import static suncertify.util.DesignByContract.*;

/**
 * A {@link Range} represents an interval of integer values with an fixed start
 * and end number. Every integer number between the start and end point are part
 * of the {@link Range}. Inspired by the groovy Range class. This class is
 * immutable.
 * 
 * @author arnelandwehr
 * 
 */
public final class Range {

    /** the start point. */
    private final int start;
    /** the end point. */
    private final int end;

    /**
     * Constructs a new <code>Range</code> object.
     * 
     * @param start
     *            the start point.
     * @param end
     *            the endpoint.
     */
    public Range(final int start, final int end) {
	super();

	checkMustBeGreaterOrEqualThan(end, start);
	this.start = start;
	this.end = end;
    }

    /**
     * Getter for the smallest value of this <code>Range</code> .
     * 
     * @return the smallest value.
     */
    public int getStart() {
	return start;
    }

    /**
     * Getter for the greatest value in this <code>Range</code>.
     * 
     * @return the greatest value.
     */
    public int getEnd() {
	return end;
    }

    /**
     * Getter for the number of integer values this <code>Range</code> contains.
     * 
     * @return the size.
     */
    public int getSize() {
	return end - start + 1;
    }

    @Override
    public String toString() {
	return getClass().getSimpleName() + " [ " + "start = " + start
		+ "; end = " + end + " ] ";
    }

    @Override
    public boolean equals(final Object object) {
	if (this == object) {
	    return true;
	}
	if (!(object instanceof Range)) {
	    return false;
	}
	final Range range = (Range) object;

	return this.start == range.start && this.end == range.end;
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + start;
	result += 31 * result + end;
	return result;
    }

}
