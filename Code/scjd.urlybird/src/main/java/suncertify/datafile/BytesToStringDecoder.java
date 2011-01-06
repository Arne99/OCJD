package suncertify.datafile;

/**
 * An <code>BytesToStringDecoder</code> implements an strategy to transform an
 * array of bytes into an equivalent string.
 * 
 * @author arnelandwehr
 * 
 */
interface BytesToStringDecoder {

    /**
     * Transforms bytes into an equivalent string.
     * 
     * @param bytes
     *            the bytes to be transformed, must not be <code>null</code>.
     * @return the string representation of the bytes, never <code>null</code>
     *         but maybe empty.
     */
    String decodeBytesToString(byte... bytes);

}
