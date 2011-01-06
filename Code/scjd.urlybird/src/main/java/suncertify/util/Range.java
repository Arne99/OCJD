package suncertify.util;

public final class Range {

    private final int start;
    private final int end;

    public Range(final int start, final int end) {
	super();
	this.start = start;
	this.end = end;
    }

    public int getStart() {
	return start;
    }

    public int getEnd() {
	return end;
    }

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
