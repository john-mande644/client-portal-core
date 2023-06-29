package com.owd.core;


import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 * A utility class to handle <tt>multipart/form-data</tt> requests,
 * <p/>
 * the kind of requests that support file uploads.  This class can
 * <p/>
 * receive arbitrarily large files (up to an artificial limit you can set),
 * <p/>
 * and fairly efficiently too.
 * <p/>
 * It cannot handle nested data (multipart content within multipart content)
 * <p/>
 * or internationalized content (such as non Latin-1 filenames).
 * <p/>
 * <p/>
 * <p/>
 * It's used like this:
 * <p/>
 * <blockquote><pre>
 * <p/>
 * MultipartRequest multi = new MultipartRequest(req, ".");
 * <p/>
 * &nbsp;
 * <p/>
 * out.println("Params:");
 * <p/>
 * Enumeration params = multi.getParameterNames();
 * <p/>
 * while (params.hasMoreElements()) {
private final static Logger log =  LogManager.getLogger();
 * <p/>
 *   String name = (String)params.nextElement();
 * <p/>
 *   String value = multi.getParameter(name);
 * <p/>
 *   out.println(name + " = " + value);
 * <p/>
 * }
 * <p/>
 * out.println();
 * <p/>
 * &nbsp;
 * <p/>
 * out.println("Files:");
 * <p/>
 * Enumeration files = multi.getFileNames();
 * <p/>
 * while (files.hasMoreElements()) {
 * <p/>
 *   String name = (String)files.nextElement();
 * <p/>
 *   String filename = multi.getFilesystemName(name);
 * <p/>
 *   String type = multi.getContentType(name);
 * <p/>
 *   File f = multi.getFile(name);
 * <p/>
 *   out.println("name: " + name);
 * <p/>
 *   out.println("filename: " + filename);
 * <p/>
 *   out.println("type: " + type);
 * <p/>
 *   if (f != null) {
 * <p/>
 *     out.println("f.toString(): " + f.toString());
 * <p/>
 *     out.println("f.getName(): " + f.getName());
 * <p/>
 *     out.println("f.exists(): " + f.exists());
 * <p/>
 *     out.println("f.length(): " + f.length());
 * <p/>
 *     out.println();
 * <p/>
 *   }
 * <p/>
 * }
 * <p/>
 * </pre></blockquote>
 * <p/>
 * <p/>
 * <p/>
 * A client can upload files using an HTML form with the following structure.
 * <p/>
 * Note that not all browsers support file uploads.
 * <p/>
 * <blockquote><pre>
 * <p/>
 * &lt;FORM ACTION="/servlet/Handler" METHOD=POST
 * <p/>
 *          ENCTYPE="multipart/form-data"&gt;
 * <p/>
 * What is your name? &lt;INPUT TYPE=TEXT NAME=submitter&gt; &lt;BR&gt;
 * <p/>
 * Which file to upload? &lt;INPUT TYPE=FILE NAME=file&gt; &lt;BR&gt;
 * <p/>
 * &lt;INPUT TYPE=SUBMIT&GT;
 * <p/>
 * &lt;/FORM&gt;
 * <p/>
 * </pre></blockquote>
 * <p/>
 * <p/>
 * <p/>
 * The full file upload specification is contained in experimental RFC 1867,
 * <p/>
 * available at <a href="http://www.ietf.org/rfc/rfc1867.txt">
 * <p/>
 * http://www.ietf.org/rfc/rfc1867.txt</a>.
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998-1999
 * @version 1.0, 98/09/18
 */

public class MultipartRequest {


    private final String NO_FILE = "unknown";


    private HttpServletRequest req;

    private File dir;

    private int maxSize;


    private Hashtable parameters = new Hashtable();  // name - Vector of values

    private Hashtable files = new Hashtable();       // name - UploadedFile


    /**
     * Constructs a new MultipartRequest to handle the specified request,
     * <p/>
     * saving any uploaded files to the given directory, and limiting the
     * <p/>
     * upload size to 1 Megabyte.  If the content is too large, an
     * <p/>
     * IOException is thrown.  This constructor actually parses the
     * <p/>
     * <tt>multipart/form-data</tt> and throws an IOException if there's any
     * <p/>
     * problem reading or parsing the request.
     *
     * @param request       the servlet request
     * @param saveDirectory the directory in which to save any uploaded files
     * @throws java.io.IOException if the uploaded content is larger than 1 Megabyte
     *                             <p/>
     *                             or there's a problem reading or parsing the request
     */

    public MultipartRequest(HttpServletRequest request,

                            String saveDirectory) throws IOException {

        this(request, saveDirectory, 1024 * 1024);

    }


    /**
     * Constructs a new MultipartRequest to handle the specified request,
     * <p/>
     * saving any uploaded files to the given directory, and limiting the
     * <p/>
     * upload size to the specified length.  If the content is too large, an
     * <p/>
     * IOException is thrown.  This constructor actually parses the
     * <p/>
     * <tt>multipart/form-data</tt> and throws an IOException if there's any
     * <p/>
     * problem reading or parsing the request.
     *
     * @param request       the servlet request
     * @param saveDirectory the directory in which to save any uploaded files
     * @param maxPostSize   the maximum size of the POST content
     * @throws java.io.IOException if the uploaded content is larger than
     *                             <p/>
     *                             <tt>maxPostSize</tt> or there's a problem reading or parsing the request
     */

    public MultipartRequest(HttpServletRequest request,

                            String saveDirectory,

                            int maxPostSize) throws IOException {

        // Sanity check values

        if (request == null)

            throw new IllegalArgumentException("request cannot be null");

        if (saveDirectory == null)

            throw new IllegalArgumentException("saveDirectory cannot be null");

        if (maxPostSize <= 0) {

            throw new IllegalArgumentException("maxPostSize must be positive");

        }



        // Save the request, dir, and max size

        req = request;

        dir = new File(saveDirectory);
        dir = new File(dir.getAbsolutePath());


        maxSize = maxPostSize;



        // Check saveDirectory is truly a directory
        //log.debug("Uploading file to: " + dir.getAbsolutePath());


        if (!dir.isDirectory())

            throw new IllegalArgumentException("Not a directory: " + saveDirectory);



        // Check saveDirectory is writable

        if (!dir.canWrite())

            throw new IllegalArgumentException("Not writable: " + saveDirectory);



        // Now parse the request saving data to "parameters" and "files";

        // write the file contents to the saveDirectory

        readRequest();

    }


    /**
     * Constructor with an old signature, kept for backward compatibility.
     * <p/>
     * Without this constructor, a servlet compiled against a previous version
     * <p/>
     * of this class (pre 1.4) would have to be recompiled to link with this
     * <p/>
     * version.  This constructor supports the linking via the old signature.
     * <p/>
     * Callers must simply be careful to pass in an HttpServletRequest.
     */

    public MultipartRequest(ServletRequest request,

                            String saveDirectory) throws IOException {

        this((HttpServletRequest) request, saveDirectory);

    }


    /**
     * Constructor with an old signature, kept for backward compatibility.
     * <p/>
     * Without this constructor, a servlet compiled against a previous version
     * <p/>
     * of this class (pre 1.4) would have to be recompiled to link with this
     * <p/>
     * version.  This constructor supports the linking via the old signature.
     * <p/>
     * Callers must simply be careful to pass in an HttpServletRequest.
     */

    public MultipartRequest(ServletRequest request,

                            String saveDirectory,

                            int maxPostSize) throws IOException {

        this((HttpServletRequest) request, saveDirectory, maxPostSize);

    }


    /**
     * Returns the names of all the parameters as an Enumeration of
     * <p/>
     * Strings.  It returns an empty Enumeration if there are no parameters.
     *
     * @return the names of all the parameters as an Enumeration of Strings
     */

    public Enumeration getParameterNames() {

        return parameters.keys();

    }


    /**
     * Returns the names of all the uploaded files as an Enumeration of
     * <p/>
     * Strings.  It returns an empty Enumeration if there are no uploaded
     * <p/>
     * files.  Each file name is the name specified by the form, not by
     * <p/>
     * the user.
     *
     * @return the names of all the uploaded files as an Enumeration of Strings
     */

    public Enumeration getFileNames() {

        return files.keys();

    }


    /**
     * Returns the value of the named parameter as a String, or null if
     * <p/>
     * the parameter was not sent or was sent without a value.  The value
     * <p/>
     * is guaranteed to be in its normal, decoded form.  If the parameter
     * <p/>
     * has multiple values, only the last one is returned (for backward
     * <p/>
     * compatibility).  For parameters with multiple values, it's possible
     * <p/>
     * the last "value" may be null.
     *
     * @param name the parameter name
     * @return the parameter value
     */

    public String getParameter(String name) {

        try {

            Vector values = (Vector) parameters.get(name);

            if (values == null || values.size() == 0) {

                return null;

            }

            String value = (String) values.elementAt(values.size() - 1);

            return value;

        } catch (Exception e) {

            return null;

        }

    }


    /**
     * Returns the values of the named parameter as a String array, or null if
     * <p/>
     * the parameter was not sent.  The array has one entry for each parameter
     * <p/>
     * field sent.  If any field was sent without a value that entry is stored
     * <p/>
     * in the array as a null.  The values are guaranteed to be in their
     * <p/>
     * normal, decoded form.  A single value is returned as a one-element array.
     *
     * @param name the parameter name
     * @return the parameter values
     */

    public String[] getParameterValues(String name) {

        try {

            Vector values = (Vector) parameters.get(name);

            if (values == null || values.size() == 0) {

                return null;

            }

            String[] valuesArray = new String[values.size()];

            values.copyInto(valuesArray);

            return valuesArray;

        } catch (Exception e) {

            return null;

        }

    }


    /**
     * Returns the filesystem name of the specified file, or null if the
     * <p/>
     * file was not included in the upload.  A filesystem name is the name
     * <p/>
     * specified by the user.  It is also the name under which the file is
     * <p/>
     * actually saved.
     *
     * @param name the file name
     * @return the filesystem name of the file
     */

    public String getFilesystemName(String name) {

        try {

            UploadedFile file = (UploadedFile) files.get(name);

            return file.getFilesystemName();  // may be null

        } catch (Exception e) {

            return null;

        }

    }


    /**
     * Returns the content type of the specified file (as supplied by the
     * <p/>
     * client browser), or null if the file was not included in the upload.
     *
     * @param name the file name
     * @return the content type of the file
     */

    public String getContentType(String name) {

        try {

            UploadedFile file = (UploadedFile) files.get(name);

            return file.getContentType();  // may be null

        } catch (Exception e) {

            return null;

        }

    }


    /**
     * Returns a File object for the specified file saved on the server's
     * <p/>
     * filesystem, or null if the file was not included in the upload.
     *
     * @param name the file name
     * @return a File object for the named file
     */

    public File getFile(String name) {

        try {

            UploadedFile file = (UploadedFile) files.get(name);

            return file.getFile();  // may be null

        } catch (Exception e) {

            return null;

        }

    }


    /**
     * The workhorse method that actually parses the request.  A subclass
     * <p/>
     * can override this method for a better optimized or differently
     * <p/>
     * behaved implementation.
     *
     * @throws java.io.IOException if the uploaded content is larger than
     *                             <p/>
     *                             <tt>maxSize</tt> or there's a problem parsing the request
     */

    protected void readRequest() throws IOException {

        // Check the content length to prevent denial of service attacks

        int length = req.getContentLength();

        if (length > maxSize) {

            throw new IOException("Posted content length of " + length +

                    " exceeds limit of " + maxSize);

        }



        // Check the content type to make sure it's "multipart/form-data"

        // Access header two ways to work around WebSphere oddities

        String type = null;

        String type1 = req.getHeader("Content-Type");

        String type2 = req.getContentType();

        // If one value is null, choose the other value

        if (type1 == null && type2 != null) {

            type = type2;

        } else if (type2 == null && type1 != null) {

            type = type1;

        }

        // If neither value is null, choose the longer value

        else if (type1 != null && type2 != null) {

            type = (type1.length() > type2.length() ? type1 : type2);

        }


        if (type == null ||

                !type.toLowerCase().startsWith("multipart/form-data")) {

            throw new IOException("Posted content type isn't multipart/form-data");

        }



        // Get the boundary string; it's included in the content type.

        // Should look something like "------------------------12012133613061"

        String boundary = extractBoundary(type);

        if (boundary == null) {

            throw new IOException("Separation boundary was not specified");

        }



        // Construct the special input stream we'll read from

        MultipartInputStreamHandler in =

                new MultipartInputStreamHandler(req.getInputStream(), length);



        // Read the first line, should be the first boundary

        String line = in.readLine();

        if (line == null) {

            throw new IOException("Corrupt form data: premature ending");

        }



        // Verify that the line is the boundary

        if (!line.startsWith(boundary)) {

            throw new IOException("Corrupt form data: no leading boundary");

        }



        // Now that we're just beyond the first boundary, loop over each part

        boolean done = false;

        while (!done) {

            done = readNextPart(in, boundary);

        }

    }


    /**
     * A utility method that reads an individual part.  Dispatches to
     * <p/>
     * readParameter() and readAndSaveFile() to do the actual work.  A
     * <p/>
     * subclass can override this method for a better optimized or
     * <p/>
     * differently behaved implementation.
     *
     * @param in       the stream from which to read the part
     * @param boundary the boundary separating parts
     * @return a flag indicating whether this is the last part
     * @throws java.io.IOException if there's a problem reading or parsing the
     *                             <p/>
     *                             request
     */

    protected boolean readNextPart(MultipartInputStreamHandler in,

                                   String boundary) throws IOException {

        // Read the first line, should look like this:

        // content-disposition: form-data; name="field1"; filename="file1.txt"

        String line = in.readLine();

        if (line == null) {

            // No parts left, we're done

            return true;

        } else if (line.length() == 0) {

            // IE4 on Mac sends an empty line at the end; treat that as the end.

            // Thanks to Daniel Lemire and Henri Tourigny for this fix.

            return true;

        }



        // Parse the content-disposition line

        String[] dispInfo = extractDispositionInfo(line);

        String disposition = dispInfo[0];

        String name = dispInfo[1];

        String filename = dispInfo[2];



        // Now onto the next line.  This will either be empty

        // or contain a Content-Type and then an empty line.

        line = in.readLine();

        if (line == null) {

            // No parts left, we're done

            return true;

        }



        // Get the content type, or null if none specified

        String contentType = extractContentType(line);

        if (contentType != null) {

            // Eat the empty line

            line = in.readLine();

            if (line == null || line.length() > 0) {  // line should be empty

                throw new

                        IOException("Malformed line after content type: " + line);

            }

        } else {

            // Assume a default content type

            contentType = "application/octet-stream";

        }



        // Now, finally, we read the content (end after reading the boundary)

        if (filename == null) {

            // This is a parameter, add it to the vector of values

            String value = readParameter(in, boundary);

            if (value.equals("")) {

                value = null;  // treat empty strings like nulls

            }

            Vector existingValues = (Vector) parameters.get(name);

            if (existingValues == null) {

                existingValues = new Vector();

                parameters.put(name, existingValues);

            }

            existingValues.addElement(value);

        } else {

            // This is a file

            readAndSaveFile(in, boundary, filename, contentType);

            if (filename.equals(NO_FILE)) {

                files.put(name, new UploadedFile(null, null, null));

            } else {

                files.put(name,

                        new UploadedFile(dir.toString(), filename, contentType));

            }

        }

        return false;  // there's more to read

    }


    /**
     * A utility method that reads a single part of the multipart request
     * <p/>
     * that represents a parameter.  A subclass can override this method
     * <p/>
     * for a better optimized or differently behaved implementation.
     *
     * @param in       the stream from which to read the parameter information
     * @param boundary the boundary signifying the end of this part
     * @return the parameter value
     * @throws java.io.IOException if there's a problem reading or parsing the
     *                             <p/>
     *                             request
     */

    protected String readParameter(MultipartInputStreamHandler in,

                                   String boundary) throws IOException {

        StringBuffer sbuf = new StringBuffer();

        String line;


        while ((line = in.readLine()) != null) {

            if (line.startsWith(boundary)) break;

            sbuf.append(line + "\r\n");  // add the \r\n in case there are many lines

        }


        if (sbuf.length() == 0) {

            return null;  // nothing read

        }


        sbuf.setLength(sbuf.length() - 2);  // cut off the last line's \r\n

        return sbuf.toString();  // no URL decoding needed

    }


    /**
     * A utility method that reads a single part of the multipart request
     * <p/>
     * that represents a file, and saves the file to the given directory.
     * <p/>
     * A subclass can override this method for a better optimized or
     * <p/>
     * differently behaved implementation.
     *
     * @param in       the stream from which to read the file
     * @param boundary the boundary signifying the end of this part
     * @param filename the name under which to save the uploaded file
     * @throws java.io.IOException if there's a problem reading or parsing the
     *                             <p/>
     *                             request
     */

    protected void readAndSaveFile(MultipartInputStreamHandler in,

                                   String boundary,

                                   String filename,

                                   String contentType) throws IOException {

        OutputStream out = null;

        // A filename of NO_FILE means no file was sent, so just read to the

        // next boundary and ignore the empty contents

        if (filename.equals(NO_FILE)) {

            out = new ByteArrayOutputStream();  // write to nowhere

        }

        // A MacBinary file goes through a decoder

        else if (contentType.equals("application/x-macbinary")) {

            File f = new File(dir + File.separator + filename);

            out = new MacBinaryDecoderOutputStream(new BufferedOutputStream(new FileOutputStream(f), 8 * 1024));

        }

        // A real file's contents are written to disk

        else {

            File f = new File(dir + File.separator + filename);

            out = new BufferedOutputStream(new FileOutputStream(f), 8 * 1024);

        }


        byte[] bbuf = new byte[100 * 1024];  // 100K

        int result;

        String line;



        // ServletInputStream.readLine() has the annoying habit of

        // adding a \r\n to the end of the last line.

        // Since we want a byte-for-byte transfer, we have to cut those chars.

        boolean rnflag = false;

        while ((result = in.readLine(bbuf, 0, bbuf.length)) != -1) {

            // Check for boundary

            if (result > 2 && bbuf[0] == '-' && bbuf[1] == '-') { // quick pre-check

                line = new String(bbuf, 0, result, "ISO-8859-1");

                if (line.startsWith(boundary)) break;

            }

            // Are we supposed to write \r\n for the last iteration?

            if (rnflag) {

                out.write('\r');
                out.write('\n');

                rnflag = false;

            }

            // Write the buffer, postpone any ending \r\n

            if (result >= 2 &&

                    bbuf[result - 2] == '\r' &&

                    bbuf[result - 1] == '\n') {

                out.write(bbuf, 0, result - 2);  // skip the last 2 chars

                rnflag = true;  // make a note to write them on the next iteration

            } else {

                out.write(bbuf, 0, result);

            }

        }

        out.flush();

        out.close();

    }



    // Extracts and returns the boundary token from a line.

    //

    private String extractBoundary(String line) {

        // Use lastIndexOf() because IE 4.01 on Win98 has been known to send the

        // "boundary=" string multiple times.  Thanks to David Wall for this fix.

        int index = line.lastIndexOf("boundary=");

        if (index == -1) {

            return null;

        }

        String boundary = line.substring(index + 9);  // 9 for "boundary="



        // The real boundary is always preceeded by an extra "--"

        boundary = "--" + boundary;


        return boundary;

    }



    // Extracts and returns disposition info from a line, as a String array

    // with elements: disposition, name, filename.  Throws an IOException

    // if the line is malformatted.

    //

    private String[] extractDispositionInfo(String line) throws IOException {

        // Return the line's data as an array: disposition, name, filename

        String[] retval = new String[3];



        // Convert the line to a lowercase string without the ending \r\n

        // Keep the original line for error messages and for variable names.

        String origline = line;

        line = origline.toLowerCase();



        // Get the content disposition, should be "form-data"

        int start = line.indexOf("content-disposition: ");

        int end = line.indexOf(";");

        if (start == -1 || end == -1) {

            throw new IOException("Content disposition corrupt: " + origline);

        }

        String disposition = line.substring(start + 21, end);

        if (!disposition.equals("form-data")) {

            throw new IOException("Invalid content disposition: " + disposition);

        }



        // Get the field name

        start = line.indexOf("name=\"", end);  // start at last semicolon

        end = line.indexOf("\"", start + 7);   // skip name=\"

        if (start == -1 || end == -1) {

            throw new IOException("Content disposition corrupt: " + origline);

        }

        String name = origline.substring(start + 6, end);



        // Get the filename, if given

        String filename = null;

        start = line.indexOf("filename=\"", end + 2);  // start after name

        end = line.indexOf("\"", start + 10);          // skip filename=\"

        if (start != -1 && end != -1) {                // note the !=

            filename = origline.substring(start + 10, end);

            // The filename may contain a full path.  Cut to just the filename.

            int slash =

                    Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

            if (slash > -1) {

                filename = filename.substring(slash + 1);  // past last slash

            }

            if (filename.equals("")) filename = NO_FILE; // sanity check

        }



        // Return a String array: disposition, name, filename

        retval[0] = disposition;

        retval[1] = name;

        retval[2] = filename;

        return retval;

    }



    // Extracts and returns the content type from a line, or null if the

    // line was empty.  Throws an IOException if the line is malformatted.

    //

    private String extractContentType(String line) throws IOException {

        String contentType = null;



        // Convert the line to a lowercase string

        String origline = line;

        line = origline.toLowerCase();



        // Get the content type, if any

        if (line.startsWith("content-type")) {

            int start = line.indexOf(" ");

            if (start == -1) {

                throw new IOException("Content type corrupt: " + origline);

            }

            contentType = line.substring(start + 1);

        } else if (line.length() != 0) {  // no content type, so should be empty

            throw new IOException("Malformed line after disposition: " + origline);

        }


        return contentType;

    }







// A class to hold information about an uploaded file.

//

    class UploadedFile {


        private String dir;

        private String filename;

        private String type;


        UploadedFile(String dir, String filename, String type) {

            this.dir = dir;

            this.filename = filename;

            this.type = type;

        }


        public String getContentType() {

            return type;

        }


        public String getFilesystemName() {

            return filename;

        }


        public File getFile() {

            if (dir == null || filename == null) {

                return null;

            } else {

                return new File(dir + File.separator + filename);

            }

        }

    }





// A class to aid in reading multipart/form-data from a ServletInputStream.

// It keeps track of how many bytes have been read and detects when the

// Content-Length limit has been reached.  This is necessary since some 

// servlet engines are slow to notice the end of stream.

//

// Mac users: The Mac doesn't like class names which exceed 32 characters

// (including the ".class") so while this class is usable from a JAR 

// anywhere, it won't compile on a Mac.

//

    class MultipartInputStreamHandler {


        ServletInputStream in;

        int totalExpected;

        int totalRead = 0;

        byte[] buf = new byte[8 * 1024];


        public MultipartInputStreamHandler(ServletInputStream in,

                                           int totalExpected) {

            this.in = in;

            this.totalExpected = totalExpected;

        }



        // Reads the next line of input.  Returns null to indicate the end

        // of stream.

        //

        public String readLine() throws IOException {

            StringBuffer sbuf = new StringBuffer();

            int result;

            String line;


            do {

                result = this.readLine(buf, 0, buf.length);  // this.readLine() does +=

                if (result != -1) {

                    sbuf.append(new String(buf, 0, result, "ISO-8859-1"));

                }

            } while (result == buf.length);  // loop only if the buffer was filled


            if (sbuf.length() == 0) {

                return null;  // nothing read, must be at the end of stream

            }


            sbuf.setLength(sbuf.length() - 2);  // cut off the trailing \r\n

            return sbuf.toString();

        }



        // A pass-through to ServletInputStream.readLine() that keeps track

        // of how many bytes have been read and stops reading when the

        // Content-Length limit has been reached.

        //

        public int readLine(byte b[], int off, int len) throws IOException {

            if (totalRead >= totalExpected) {

                return -1;

            } else {

                if (len > (totalExpected - totalRead)) {

                    len = totalExpected - totalRead;  // keep from reading off end

                }

                int result = in.readLine(b, off, len);

                if (result > 0) {

                    totalRead += result;

                }

                return result;

            }

        }

    }





// Class to filters MacBinary files to normal files on the fly

// Optimized for speed more than readability

    class MacBinaryDecoderOutputStream extends FilterOutputStream {


        int bytesFiltered = 0;

        int dataForkLength = 0;


        public MacBinaryDecoderOutputStream(OutputStream out) {

            super(out);

        }


        public void write(int b) throws IOException {

            // Bytes 83 through 86 are a long representing the data fork length

            // Check <= 86 first to short circuit early in the common case

            if (bytesFiltered <= 86 && bytesFiltered >= 83) {

                int leftShift = (86 - bytesFiltered) * 8;

                dataForkLength = dataForkLength | (b & 0xff) << leftShift;

            }

            // Bytes 128 up to (128 + dataForkLength - 1) are the data fork

            else if (bytesFiltered < (128 + dataForkLength) && bytesFiltered >= 128) {

                out.write(b);

            }

            bytesFiltered++;

        }


        public void write(byte b[]) throws IOException {

            write(b, 0, b.length);

        }


        public void write(byte b[], int off, int len) throws IOException {

            // If the write is for content past the end of the data fork, ignore

            if (bytesFiltered >= (128 + dataForkLength)) {

                bytesFiltered += len;

            }

            // If the write is entirely within the data fork, write it directly

            else if (bytesFiltered >= 128 &&

                    (bytesFiltered + len) <= (128 + dataForkLength)) {

                out.write(b, off, len);

                bytesFiltered += len;

            }

            // Otherwise, do the write a byte at a time to get the logic above

            else {

                for (int i = 0; i < len; i++) {

                    write(b[off + i]);

                }

            }

        }

    }


}//end main class
