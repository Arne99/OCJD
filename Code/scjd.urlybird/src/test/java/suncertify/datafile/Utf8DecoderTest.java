package suncertify.datafile;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.junit.Test;

/**
 * The Class Utf8DecoderTest.
 */
public final class Utf8DecoderTest {

    /**
     * Should transform byte arrays of chars into a string.
     */
    @Test
    public void shouldTransformByteArraysOfCharsIntoAString() {

	final Utf8Decoder decoder = new Utf8Decoder();
	final byte[] bytes = new byte[] { 'H', 'e', 'l', 'l', 'o' };

	final String result = decoder.decodeBytesToString(bytes);

	assertThat(result, is(equalTo("Hello")));
    }

    /**
     * Should not trim the decoded string.
     */
    @Test
    public void shouldNotTrimTheDecodedString() {

	final Utf8Decoder decoder = new Utf8Decoder();
	final byte[] bytes = new byte[] { ' ', 'H', 'e', 'l', 'l', 'o', ' ',
		' ' };

	final String result = decoder.decodeBytesToString(bytes);

	assertThat(result, is(equalTo(" Hello  ")));
    }

    /**
     * Should transform bytes from zero to nine to an string witch represents
     * the number.
     */
    @Test
    public void shouldTransformBytesFromZeroToNineToAnStringWitchRepresentsTheNumber() {

	final Utf8Decoder decoder = new Utf8Decoder();
	final byte[] bytes = new byte[] { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9' };

	final String result = decoder.decodeBytesToString(bytes);

	assertThat(result, is(equalTo("0123456789")));
    }

}
