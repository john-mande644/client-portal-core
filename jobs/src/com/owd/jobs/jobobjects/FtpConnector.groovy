package com.owd.jobs.jobobjects

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException
import com.owd.core.DelimitedReader
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.TabReader
import ipworks.DirEntry
import ipworks.Ftp

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by stewartbuskirk1 on 9/28/15.
 */
@groovy.util.logging.Log4j2
class FtpConnector {

    private String host = "";
    private String login = "";
    private String pass = "";
    private String remotePath = "";


    protected Ftp ftp = new Ftp();

    private FtpConnector() {

    }

    public FtpConnector(String ahost, String alogin, String apass, String aRemotePath) {
        ftp.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
        ftp.setRemoteHost(ahost);
        ftp.setUser(alogin);
        ftp.setPassword(apass);
        ftp.setRemotePath(aRemotePath);
        ftp.setTransferMode(Ftp.tmBinary);
        ftp.setPassive(true);
    }

    public void setRemotePath(String newPath)
    {
        ftp.setRemotePath(newPath)
    }

    public String getRemotePath()
    {
       return  ftp.getRemotePath()
    }

    public void renameFile(String newPath)
    {

        try {

            //login
            ftp.logon();
            String oldPath = ftp.getRemotePath();

            ftp.renameFile(newPath)

            ftp.setRemotePath(oldPath);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                ftp.logoff();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<String> getFileNames() {

        List<String> fileNames = new ArrayList<String>();

        try {


            //login

            ftp.logon();
            // get a list of all .txt files
            // ftp.setRemoteFile("*.txt");

            ftp.listDirectoryLong();

            DirEntry[] h = new DirEntry[ftp.getDirList().size()];


            ftp.getDirList().toArray(h);
            //   Collections.reverse(Arrays.asList(h));
            //step through each file
            for (DirEntry e : h) {
                // make sure we are dealing with a file

                log.debug("" + e.getFileName());

                if (!e.getIsDir()) {

                    fileNames.add(e.getFileName())
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                ftp.logoff();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return fileNames
    }

    public void putFileData(String fileName,String remotePath, byte[] data) throws Exception {
        String oldDirectory = ftp.getRemotePath();
        ftp.setRemotePath(remotePath);
       try {

           putFileData(fileName, data);
       }catch(Exception ex)
       {
           throw ex;
       }            finally
       {
           ftp.setRemotePath(oldDirectory);
       }

    }

    public void putFileData(String fileName,byte[] data) throws Exception {

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ftp.setUploadStream(byteArrayInputStream);
            ftp.setRemoteFile(fileName)

            ftp.upload();




            long startMillis = Calendar.getInstance().getTimeInMillis();
            while(!(ftp.isIdle()))
            {
                Thread.sleep(2000L);
                if((Calendar.getInstance().getTimeInMillis()-startMillis)>30000L){
                    break;
                }
            }
            if(ftp.getFileSize()!=data.length)
            {
                throw new Exception("Failed writing file "+fileName+" to host "+ftp.getRemoteHost()) ;
            }
        } finally {
            try {
                ftp.logoff();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ByteArrayOutputStream getFileData(String fileName) {
        ByteArrayOutputStream file = null;

        try {

            //login
            ftp.logon();

                ftp.setRemoteFile(fileName);
                long originalSize = ftp.getFileSize();
                Thread.sleep(5000L);
                ftp.setRemoteFile(fileName);
                long currentSize = ftp.getFileSize();
                log.debug("current=" + currentSize);

                if (currentSize == (originalSize)) {

                    file = new ByteArrayOutputStream();
                    ftp.setDownloadStream(file);
                    ftp.download();


                }

            long startMillis = Calendar.getInstance().getTimeInMillis();
            while(!(ftp.isIdle()))
            {
                Thread.sleep(2000L);
                if((Calendar.getInstance().getTimeInMillis()-startMillis)>30000L){
                 break;
            }
            }

            if (file == null) {
                throw new Exception("Failure to download file from Ftp")
            }
            return file;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                ftp.logoff();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
