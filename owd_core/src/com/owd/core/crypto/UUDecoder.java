/*

 * @(#)UUDecoder.java	1.6 95/10/08 Chuck McManis

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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * This class implements a Berkeley uu character decoder. This decoder
 * <p/>
 * was made famous by the uudecode program.
 * <p/>
 * <p/>
 * <p/>
 * The basic character coding is algorithmic, taking 6 bits of binary
 * <p/>
 * data and adding it to an ASCII ' ' (space) character. This converts
 * <p/>
 * these six bits into a printable representation. Note that it depends
 * <p/>
 * on the ASCII character encoding standard for english. Groups of three
 * <p/>
 * bytes are converted into 4 characters by treating the three bytes
 * <p/>
 * a four 6 bit groups, group 1 is byte 1's most significant six bits,
 * <p/>
 * group 2 is byte 1's least significant two bits plus byte 2's four
 * <p/>
 * most significant bits. etc.
 * <p/>
 * <p/>
 * <p/>
 * In this encoding, the buffer prefix is:
 * <p/>
 * <pre>
 * <p/>
 *     begin [mode] [filename]
 * <p/>
 * </pre>
 * <p/>
 * <p/>
 * <p/>
 * This is followed by one or more lines of the form:
 * <p/>
 * <pre>
 * <p/>
 * 	(len)(data)(data)(data) ...
 * <p/>
 * </pre>
 * <p/>
 * where (len) is the number of bytes on this line. Note that groupings
 * <p/>
 * are always four characters, even if length is not a multiple of three
 * <p/>
 * bytes. When less than three characters are encoded, the values of the
 * <p/>
 * last remaining bytes is undefined and should be ignored.
 * <p/>
 * <p/>
 * <p/>
 * The last line of data in a uuencoded buffer is represented by a single
 * <p/>
 * space character. This is translated by the decoding engine to a line
 * <p/>
 * length of zero. This is immediately followed by a line which contains
 * <p/>
 * the word 'end[newline]'
 * <p/>
 * <p/>
 * <p/>
 * If an error is encountered during decoding this class throws a
 * <p/>
 * CEFormatException. The specific detail messages are:
 * <p/>
 * <p/>
 * <p/>
 * <pre>
 * <p/>
 * 	"UUDecoder: No begin line."
 * <p/>
 * 	"UUDecoder: Malformed begin line."
 * <p/>
 * 	"UUDecoder: Short Buffer."
 * <p/>
 * 	"UUDecoder: Bad Line Length."
 * <p/>
 * 	"UUDecoder: Missing 'end' line."
 * <p/>
 * </pre>
 *
 * @author Chuck McManis
 * @version 1.6, 08 Oct 1995
 * @see		CharacterDecoder
 * @see		UUEncoder
 */

public class UUDecoder extends CharacterDecoder {
private final static Logger log =  LogManager.getLogger();


    /**
     * This string contains the name that was in the buffer being decoded.
     */

    public String bufferName;


    /**
     * Represents UNIX(tm) mode bits. Generally three octal digits
     * <p/>
     * representing read, write, and execute permission of the owner,
     * <p/>
     * group owner, and  others. They should be interpreted as the bit groups:
     * <p/>
     * <pre>
     * <p/>
     * (owner) (group) (others)
     * <p/>
     * 	rwx      rwx     rwx 	(r = read, w = write, x = execute)
     * <p/>
     * </pre>
     */

    public int mode;


    /**
     * UU encoding specifies 3 bytes per atom.
     */

    int bytesPerAtom() {

        return (3);

    }


    /**
     * All UU lines have 45 bytes on them, for line length of 15*4+1 or 61
     * <p/>
     * characters per line.
     */

    int bytesPerLine() {

        return (45);

    }


    /**
     * This is used to decode the atoms
     */

    private byte decoderBuffer[] = new byte[4];


    /**
     * Decode a UU atom. Note that if l is less than 3 we don't write
     * <p/>
     * the extra bits, however the encoder always encodes 4 character
     * <p/>
     * groups even when they are not needed.
     */

    void decodeAtom(InputStream inStream, OutputStream outStream, int l)

            throws IOException {

        int i, c1, c2, c3, c4;

        int a, b, c;

        StringBuffer x = new StringBuffer();


        for (i = 0; i < 4; i++) {

            c1 = inStream.read();

            if (c1 == -1) {

                throw new CEStreamExhausted();

            }

            x.append((char) c1);

            decoderBuffer[i] = (byte) ((c1 - ' ') & 0x3f);

        }

        a = ((decoderBuffer[0] << 2) & 0xfc) | ((decoderBuffer[1] >>> 4) & 3);

        b = ((decoderBuffer[1] << 4) & 0xf0) | ((decoderBuffer[2] >>> 2) & 0xf);

        c = ((decoderBuffer[2] << 6) & 0xc0) | (decoderBuffer[3] & 0x3f);

        outStream.write((byte) (a & 0xff));

        if (l > 1) {

            outStream.write((byte) (b & 0xff));

        }

        if (l > 2) {

            outStream.write((byte) (c & 0xff));

        }

    }


    /**
     * For uuencoded buffers, the data begins with a line of the form:
     * <p/>
     * begin MODE FILENAME
     * <p/>
     * This line always starts in column 1.
     */

    void decodeBufferPrefix(InputStream inStream, OutputStream outStream) throws IOException {

        int c;

        StringBuffer q = new StringBuffer(32);

        String r;

        boolean sawNewLine;



        /*

         * This works by ripping through the buffer until it finds a 'begin'

         * line or the end of the buffer.

         */

        sawNewLine = true;

        while (true) {

            c = inStream.read();

            if (c == -1) {

                throw new CEFormatException("UUDecoder: No begin line.");

            }

            if ((c == 'b') && sawNewLine) {

                c = inStream.read();

                if (c == 'e') {

                    break;

                }

            }

            sawNewLine = (c == '\n') || (c == '\r');

        }



        /*

         * Now we think its begin, (we've seen ^be) so verify it here.

*/

        while ((c != '\n') && (c != '\r')) {

            c = inStream.read();

            if (c == -1) {

                throw new CEFormatException("UUDecoder: No begin line.");

            }

            if ((c != '\n') && (c != '\r')) {

                q.append((char) c);

            }

        }

        r = q.toString();

        if (r.indexOf(' ') != 3) {

            throw new CEFormatException("UUDecoder: Malformed begin line.");

        }

        mode = Integer.parseInt(r.substring(4, 7));

        bufferName = r.substring(r.indexOf(' ', 6) + 1);

    }


    /**
     * In uuencoded buffers, encoded lines start with a character that
     * <p/>
     * represents the number of bytes encoded in this line. The last
     * <p/>
     * line of input is always a line that starts with a single space
     * <p/>
     * character, which would be a zero length line.
     */

    int decodeLinePrefix(InputStream inStream, OutputStream outStream) throws IOException {

        int c;


        c = inStream.read();

        //////log.debug("got prefix "+c+" "+new Character((char)c));

        if (c == 96) {

            c = ' ';

        }

        if (c == ' ') {

            c = inStream.read(); /* discard the trailing <newline> */

            throw new CEStreamExhausted();

        } else if (c == -1) {

            throw new CEFormatException("UUDecoder: Short Buffer.");

        }


        c = (c - ' ') & 0x3f;

        if (c > bytesPerLine()) {

            throw new CEFormatException("UUDecoder: Bad Line Length.");

        }

        return (c);

    }


    /**
     * Find the end of the line for the next operation.
     */

    void decodeLineSuffix(InputStream inStream, OutputStream outStream) throws IOException {

        int c;

        while (true) {

            c = inStream.read();

            if (c == -1) {

                throw new CEStreamExhausted();

            }

            if (c == '\n') {

                break;

            }

        }

    }


    /**
     * UUencoded files have a buffer suffix which consists of the word
     * <p/>
     * end. This line should immediately follow the line with a single
     * <p/>
     * space in it.
     */

    void decodeBufferSuffix(InputStream inStream, OutputStream outStream) throws IOException {

        int c;


        c = inStream.read(decoderBuffer);

        if ((decoderBuffer[0] != 'e') || (decoderBuffer[1] != 'n') ||

                (decoderBuffer[2] != 'd')) {

            // throw new CEFormatException("UUDecoder: Missing 'end' line.");

        }

    }


    public class CEFormatException extends IOException {

        public CEFormatException(String s) {

            super(s);

        }

    }


}
