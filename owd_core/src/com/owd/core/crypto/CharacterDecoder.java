/*

 * @(#)CharacterDecoder.java	1.9 95/10/08 Chuck McManis

 *

 *

 * Copyright (c) 1996 Chuck McManis, All Rights Reserved.

 *

 * Permission to use, copy, modify, and distribute this software

 * and its documentation for NON-COMMERCIAL purposes and without

 * fee is hereby granted provided that this copyright notice

 * appears in all copies.

 *

 * CHUCK MCMANIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE

 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING

 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,

 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. CHUCK MCMANIS

 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT

 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.

 */


package com.owd.core.crypto;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


/**
 * This class defines the decoding half of character encoders.
 * <p/>
 * A character decoder is an algorithim for transforming 8 bit
 * <p/>
 * binary data that has been encoded into text by a character
 * <p/>
 * encoder, back into original binary form.
 * <p/>
 * <p/>
 * <p/>
 * The character encoders, in general, have been structured
 * <p/>
 * around a central theme that binary data can be encoded into
 * <p/>
 * text that has the form:
 * <p/>
 * <p/>
 * <p/>
 * <pre>
 * <p/>
 * 	[Buffer Prefix]
 * <p/>
 * 	[Line Prefix][encoded data atoms][Line Suffix]
 * <p/>
 * 	[Buffer Suffix]
 * <p/>
 * </pre>
 * <p/>
 * <p/>
 * <p/>
 * Of course in the simplest encoding schemes, the buffer has no
 * <p/>
 * distinct prefix of suffix, however all have some fixed relationship
 * <p/>
 * between the text in an 'atom' and the binary data itself.
 * <p/>
 * <p/>
 * <p/>
 * In the CharacterEncoder and CharacterDecoder classes, one complete
 * <p/>
 * chunk of data is referred to as a <i>buffer</i>. Encoded buffers
 * <p/>
 * are all text, and decoded buffers (sometimes just referred to as
 * <p/>
 * buffers) are binary octets.
 * <p/>
 * <p/>
 * <p/>
 * To create a custom decoder, you must, at a minimum,  overide three
 * <p/>
 * abstract methods in this class.
 * <p/>
 * <DL>
 * <p/>
 * <DD>bytesPerAtom which tells the decoder how many bytes to
 * <p/>
 * expect from decodeAtom
 * <p/>
 * <DD>decodeAtom which decodes the bytes sent to it as text.
 * <p/>
 * <DD>bytesPerLine which tells the encoder the maximum number of
 * <p/>
 * bytes per line.
 * <p/>
 * </DL>
 * <p/>
 * <p/>
 * <p/>
 * In general, the character decoders return error in the form of a
 * <p/>
 * CEFormatException. The syntax of the detail string is
 * <p/>
 * <pre>
 * <p/>
 * 	DecoderClassName: Error message.
 * <p/>
 * </pre>
 * <p/>
 * <p/>
 * <p/>
 * Several useful decoders have already been written and are
 * <p/>
 * referenced in the See Also list below.
 *
 * @version	08 Oct 1995, 1.9
 * @author	Chuck McManis
 * @see		CEFormatException
 * @see		CharacterEncoder
 * @see		UCDecoder
 * @see		UUDecoder
 * @see		BASE64Decoder
 */


public abstract class CharacterDecoder {
private final static Logger log =  LogManager.getLogger();


    /**
     * Return the number of bytes per atom of decoding
     */

    abstract int bytesPerAtom();


    /**
     * Return the maximum number of bytes that can be encoded per line
     */

    abstract int bytesPerLine();


    /**
     * decode the beginning of the buffer, by default this is a NOP.
     */

    void decodeBufferPrefix(InputStream aStream, OutputStream bStream) throws IOException {
    }


    /**
     * decode the buffer suffix, again by default it is a NOP.
     */

    void decodeBufferSuffix(InputStream aStream, OutputStream bStream) throws IOException {
    }


    /**
     * This method should return, if it knows, the number of bytes
     * <p/>
     * that will be decoded. Many formats such as uuencoding provide
     * <p/>
     * this information. By default we return the maximum bytes that
     * <p/>
     * could have been encoded on the line.
     */

    int decodeLinePrefix(InputStream aStream, OutputStream bStream) throws IOException {

        return (bytesPerLine());

    }


    /**
     * This method post processes the line, if there are error detection
     * <p/>
     * or correction codes in a line, they are generally processed by
     * <p/>
     * this method. The simplest version of this method looks for the
     * <p/>
     * (newline) character.
     */

    void decodeLineSuffix(InputStream aStream, OutputStream bStream) throws IOException {
    }


    /**
     * This method does an actual decode. It takes the decoded bytes and
     * <p/>
     * writes them to the OuputStream. The integer <i>l</i> tells the
     * <p/>
     * method how many bytes are required. This is always <= bytesPerAtom().
     */

    void decodeAtom(InputStream aStream, OutputStream bStream, int l) throws IOException {

        throw new CEStreamExhausted();

    }


    /**
     * This method works around the bizarre semantics of BufferedInputStream's
     * <p/>
     * read method.
     */

    protected int readFully(InputStream in, byte buffer[], int offset, int len)

            throws java.io.IOException {

        for (int i = 0; i < len; i++) {

            int q = in.read();

            if (q == -1)

                return ((i == 0) ? -1 : i);

            buffer[i + offset] = (byte) q;

        }

        return len;

    }


    /**
     * Decode the text from the InputStream and write the decoded
     * <p/>
     * octets to the OutputStream. This method runs until the stream
     * <p/>
     * is exhausted.
     *
     * @throws CEFormatException An error has occured while decoding
     * @throws CEStreamExhausted The input stream is unexpectedly out of data
     */

    public void decodeBuffer(InputStream aStream, OutputStream bStream) throws IOException {

        int i;

        int totalBytes = 0;


        decodeBufferPrefix(aStream, bStream);

        while (true) {

            int length;


            try {

                length = decodeLinePrefix(aStream, bStream);

                ////////log.debug("got length "+length);

                for (i = 0; (i + bytesPerAtom()) < length; i += bytesPerAtom()) {

                    decodeAtom(aStream, bStream, bytesPerAtom());

                    totalBytes += bytesPerAtom();

                }

                if ((i + bytesPerAtom()) == length) {

                    decodeAtom(aStream, bStream, bytesPerAtom());

                    totalBytes += bytesPerAtom();

                } else {

                    decodeAtom(aStream, bStream, length - i);

                    totalBytes += (length - i);

                }

                decodeLineSuffix(aStream, bStream);

            } catch (CEStreamExhausted e) {

                break;

            }

        }

        decodeBufferSuffix(aStream, bStream);

    }


    /**
     * Alternate decode interface that takes a String containing the encoded
     * <p/>
     * buffer and returns a byte array containing the data.
     *
     * @throws CEFormatException An error has occured while decoding
     */

    public byte decodeBuffer(String inputString)[] throws IOException {

        byte inputBuffer[] = new byte[inputString.length()];

        ByteArrayInputStream inStream;

        ByteArrayOutputStream outStream;


        inputString.getBytes(0, inputString.length(), inputBuffer, 0);

        inStream = new ByteArrayInputStream(inputBuffer);

        outStream = new ByteArrayOutputStream();

        decodeBuffer(inStream, outStream);

        return (outStream.toByteArray());

    }


    /**
     * Decode the contents of the inputstream into a buffer.
     */

    public byte decodeBuffer(InputStream in)[] throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        decodeBuffer(in, outStream);

        return (outStream.toByteArray());

    }


    public class CEStreamExhausted extends IOException {


    }


}

