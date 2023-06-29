package com.owd.core.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;


public class FtpBean {
private final static Logger log =  LogManager.getLogger();
    private String server = null;            // server name
    private String user = null;              // user name
    private String password = null;

    private int port = 21;                 // FTP port number default 21
    private boolean passive = true;        // Passive mode transfer
    FTPClient ftp = new FTPClient();

    /**
     * Constructor
     */
    public FtpBean() {
    }

    /**
     * Connect to FTP server and login.
     *
     * @param server   Name of server
     * @param user     User name for login
     * @param password Password for login
     * @throws FtpException if a ftp error occur (eg. Login fail in this case).
     * @throws IOException  if an I/O error occur
     */
    public void ftpConnect(String server, String user, String password) throws IOException, FtpException {

        boolean error = false;
        try {
            int reply;
            ftp.connect(server);
            log.debug("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();
            log.debug("reply code:"+reply);
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            ftp.login(user, password);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);


        } catch(IOException e) {
            error = true;
            e.printStackTrace();
        } finally {


        }
    }

    /**
     * Connect to FTP server with bean preset properties.
     *
     * @throws FtpException if a ftp error occur (eg. Login fail in this case).
     * @throws IOException  if an I/O error occur
     */
    public void ftpConnect() throws IOException, FtpException {
        ftpConnect(getServerName(), getUserName(), getPassword());
    }

    /**
     * Close FTP connection.
     *
     * @throws IOException  if an I/O error occur
     * @throws FtpException if a ftp error occur
     */
    public void close() throws IOException, FtpException {
        if(ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch(IOException ioe) {
                // do nothing
            }
            ftp = new FTPClient();
        }
    }

    /**
     * Delete a file at the FTP server.
     *
     * @param filename Name of the file to be deleted.
     * @throws FtpException if a ftp error occur. (eg. no such file in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void fileDelete(String filename) throws IOException, FtpException {
        if(!ftp.deleteFile(filename))
        {
            throw new FtpException("Unable to delete file "+filename);
        }
    }


    /**
     * Rename a file at the FTP server.
     *
     * @param oldfilename The name of the file to be renamed
     * @param newfilename The new name of the file
     * @throws FtpException if a ftp error occur. (eg. A file named the new file name already in this case.)
     * @throws IOException  if an I/O error occur.
     */
    public void fileRename(String oldfilename, String newfilename) throws IOException, FtpException {
        if(!ftp.rename(oldfilename, newfilename))
        {
            throw new FtpException("Unable to rename file "+oldfilename+" to "+newfilename);
        }
    }

    /**
     * Get an ASCII file from the server and return as String.
     *
     * @param filename  Name of ASCII file to be getted.
     * @param separator The line separator you want in the return String (eg. "\r\n", "\n", "\r")
     * @return The Ascii content of the file. It uses local system line separator.
     * @throws FtpException if a ftp error occur. (eg. no such file in this case)
     * @throws IOException  if an I/O error occur.
     */
    public String getAsciiFile(String filename, String separator) throws IOException, FtpException {

        OutputStream output;

        ftp.setFileType(FTP.ASCII_FILE_TYPE);
        output = new ByteArrayOutputStream();

        ftp.retrieveFile(filename, output);
        String str_content = output.toString();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);


        output.close();

        return str_content;
    }

    /**
     * Save an ascii file to the server.
     * Remark:<br>
     * this method convert the line separator of the String content to <br>
     * NVT-ASCII format line separator "\r\n". Then the ftp daemon will <br>
     * convert the NVT-ASCII format line separator into its system line separator.
     *
     * @param filename  The name of file
     * @param content   The String content of the file
     * @param separator Line separator of the content
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void putAsciiFile(String filename, String content, String separator) throws IOException, FtpException {


        ftp.setFileType(FTP.ASCII_FILE_TYPE);
        InputStream is = new ByteArrayInputStream(content.getBytes());

        ftp.storeFile(filename, is);

        ftp.setFileType(FTP.BINARY_FILE_TYPE);

        is.close();
    }

    /**
     * Get a binary file and return a byte array.
     *
     * @param filename The name of the binary file to be got.
     * @return An array of byte of the content of the binary file.
     * @throws FtpException if a ftp error occur. (eg. No such file in this case)
     * @throws IOException  if an I/O error occur.
     */
    public byte[] getBinaryFile(String filename) throws IOException, FtpException {

        ByteArrayOutputStream output;

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        output = new ByteArrayOutputStream();

        ftp.retrieveFile(filename, output);
        byte[] str_content = output.toByteArray();


        output.close();

        return str_content;

    }





    /**
     * Put a binary file to the server from an array of byte.
     *
     * @param filename The name of file
     * @param content  The byte array to be saved
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void putBinaryFile(String filename, byte[] content) throws IOException, FtpException {

        InputStream is = new ByteArrayInputStream(content);

        ftp.storeFile(filename, is);

        is.close();
    }




    /**
     * Get current directory name.
     *
     * @return The name of the current directory.
     * @throws FtpException if a ftp error occur.
     * @throws IOException  if an I/O error occur.
     */
    public String getDirectory() throws IOException, FtpException {

        String reply = ftp.printWorkingDirectory();
        log.debug(reply);
        int first = reply.indexOf("\"");
        int last = reply.lastIndexOf("\"");
        return reply;
    }

    /**
     * Change directory.
     *
     * @param directory Name of directory
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void setDirectory(String directory) throws IOException, FtpException {
        ftp.changeWorkingDirectory(directory);
    }



    public Vector getFilesFromDirectory(String extension) throws FtpException, IOException {

        Vector fileNames = new Vector();

        log.debug(ftp.listNames());
for(String name:Arrays.asList(ftp.listNames()))
{
    log.debug(name);
    if (extension != null) {
        if (!(name.endsWith(extension)))
        {
            fileNames.add(name);
    }
    }else
    {
        fileNames.add(name);

    }
}


        //log.debug(fileNames);
        return fileNames;

    }

    /**
     * Change to parent directory.
     *
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void toParentDirectory() throws IOException, FtpException {
       ftp.changeToParentDirectory();
    }

    /**
     * Get the content of current directory.
     *
     * @return A list of directories, files and links in the current directory.
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public String getDirectoryContent() throws IOException, FtpException {


        String fileNames = "";

        for(String name:Arrays.asList(ftp.listNames()))
        {

                fileNames = fileNames+"\n";


        }

        return fileNames;

    }


    public FTPFile[] getFileEntries() throws Exception
    {
        return ftp.listFiles();
    }


    /**
     * Make a directory in the server.
     *
     * @param directory The name of directory to be made.
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public boolean changeWorkingDirectory(String directory) throws IOException, FtpException {
        return ftp.changeWorkingDirectory(directory);
    }

    /**
     * Make a directory in the server.
     *
     * @param directory The name of directory to be made.
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public boolean makeDirectory(String directory) throws IOException, FtpException {
        return ftp.makeDirectory(directory);
    }

    /**
     * Remove a directory in the server
     *
     * @param directory The name of directory to be removed
     * @throws FtpException if a ftp error occur. (eg. permission denied in this case)
     * @throws IOException  if an I/O error occur.
     */
    public void removeDirectory(String directory) throws IOException, FtpException {
        ftp.removeDirectory(directory);
    }

    /**
     * Return the port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Set port number if the port number of ftp is not 21
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Return the server name. Return "" if it is not connected to any server.
     */
    public String getServerName() {
        if (server == null)
            setServerName("200.200.200.227");

        return server;
    }

    /**
     * Return the user name. Return "anonymous" as default.
     */
    public String getUserName() {

        if (user == null)
            setUserName("anonymous");

        return user;
    }

    /**
     * Return the password. Return a formatted email address as default.
     */
    public String getPassword() {
        if (password == null)
            setPassword("null@null.com");

        return password;
    }



    /*
     * Set server name and fire property change
     * @param server The name of the server.
     */
    public void setServerName(String server) {
        this.server = server;
    }

    /*
     * Set server username and fire property change
     * @param server The username of the server.
     */
    public void setUserName(String username) {
        this.user = username;
    }

    /*
     * Set server password and fire property change
     * @param server The password of the server.
     */
    public void setPassword(String pass) {
        this.password = pass;
    }

}
