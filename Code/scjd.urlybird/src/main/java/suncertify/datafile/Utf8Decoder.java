package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

/**
 * An <code>Utf8Decoder</code> could be used to transform an array of UTF8
 * encoded bytes into an string. This means that:
 * <ul>
 * <li>every <code>byte</code> is transformed into an 8 byte encoded
 * <code>char</code>.</li>
 * <li><code>bytes</code> between 0 and 9 are transformed into the equivalent
 * <code>int</code>.</li>
 * </ul>
 * 
 * This class is an implementation of the strategy pattern, it is stateless and
 * immutable.
 * 
 * @author arnelandwehr
 * 
 */
final class Utf8Decoder implements BytesToStringDecoder {

    @Override
    public String decodeBytesToString(final byte... bytes) {

	checkNotNull(bytes, "bytes");

	final StringBuilder builder = new StringBuilder();
	for (final byte b : bytes) {
	    if (b <= 9) {
		builder.append("" + b);
	    } else {
		builder.append((char) b);
	    }
	}
	return builder.toString();
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof Utf8Decoder)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	return 10;
    }

}
