package com.owd.jobs.jobobjects.spscommerce;

import com.owd.jobs.jobobjects.spscommerce.beans.SPSCommerceRemoteFtpResponse;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.ftp.FTPManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

import ipworks.DirEntry;
import ipworks.DirEntryList;
import ipworks.Ftp;
import ipworks.FtpPITrailEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Apr 15, 2010
 * Time: 9:12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class SPSCommerceRemoteFTP extends Ftp
{
private final static Logger log =  LogManager.getLogger();

    private String ftpLogin = "oneworldir";
    private String ftpPassword = "57xM431f7c";
    private String ftpHost = "ftp.spscommerce.net";



    private boolean transferCompleted = false;
    private Map<String,ByteArrayOutputStream> transferList = new TreeMap<String,ByteArrayOutputStream>();

    public void addToTransferList(String name)
    {
        transferList.put(name, new ByteArrayOutputStream());
    }
    public void removeFromTransferList(String name)
    {
        transferList.remove(name);
    }

    private String getTransferData(String name)
    {
        return transferList.get(name).toString();
    }

    private byte[] getTransferBinaryData(String name)
    {
        return transferList.get(name).toByteArray();
    }

    
    public   SPSCommerceRemoteFTP (String login, String pass, String host)
    {
        super();
        setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());

        ftpLogin=login;
        ftpPassword=pass;
        ftpHost=host;
        try {
            login();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public   SPSCommerceRemoteFTP ()
    {
        super();
        setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());

        try {
            login();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        log.debug(getRuntimeLicense());
    }

    public enum FolderPath{
        receiveDirPath("/out"),
        sendDirPath("/in"),
        sendDirSymPath("/symphony/in"),
        receiveDirTestPath("/testout"),
        receiveDirSymPath("/symphony/out"),

        sendDirTestPath("/testin");

        private String path;
        FolderPath(String iPath)
        {
            this.path = iPath;
        }

        public String getPath(){return path;}
    }

    public enum fileType{PO,IN,SH,IB,PR}

    private void login() throws Exception {
        setUser(ftpLogin);
        setPassword(ftpPassword);

        setRemoteHost(ftpHost);
        setPassive(false);
        //log.debug(getRuntimeLicense());
        addFtpEventListener(new ipworks.FtpEventListener() {
            public void dirList(ipworks.FtpDirListEvent e) {
                log.debug(e.dirEntry);
            }

            @Override
            public void PITrail(FtpPITrailEvent ftpPITrailEvent) {
               // log.debug(ftpPITrailEvent.message);
            }

            public void connectionStatus(ipworks.FtpConnectionStatusEvent e) {
                log.debug("status:" + e.connectionEvent + ":" + e.description);
            }



            public void endTransfer(ipworks.FtpEndTransferEvent e) {
                log.debug("transfer ended");
                transferCompleted = true;
            }

            public void error(ipworks.FtpErrorEvent e) {
                log.debug("Error " + e.errorCode + ": " + e.description);
            }


            public void startTransfer(ipworks.FtpStartTransferEvent e) {
                log.debug("Starting txr");
            }

            public void transfer(ipworks.FtpTransferEvent e) {
                log.debug("transferring " + e.bytesTransferred + " bytes from " + e.getSource());
            }
        });


    }

    public SPSCommerceRemoteFtpResponse putDataFile(fileType type, byte[] data, FolderPath folderPath) throws Exception {
        log.debug(folderPath.path);
        log.debug(type.toString());
        String fileName = type.toString()+ Calendar.getInstance().getTimeInMillis()+".owd";
        FTPClient client = new FTPClient();
        int replyCode = 0;

        try{
            log.debug("saving "+fileName+" to "+folderPath.path);
            client.connect(ftpHost);
            client.login(ftpLogin, ftpPassword);
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory("in");

            System.out.println(client.storeFile(fileName, new ByteArrayInputStream(data)));
            return new SPSCommerceRemoteFtpResponse(fileName, client.getReplyString(), client.getReplyCode());
        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            try {
                client.logout();
            } catch (Exception e) {
                System.out.println("ftp logoff error");
            }
        }
    }






     public void deleteDataFile(String name,String folderPath) throws Exception
    {
        try{
            logon();
            log.debug("deleting "+name+" from "+folderPath);
            setRemotePath(folderPath);
            deleteFile(name);


        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            try {
                logoff();
            }catch (Exception e){
                System.out.println("ftp logoff error");
            }
        }

    }



    public synchronized byte[] getDataFile(String filename,String folderPath) throws Exception
    {


        try{
            logon();
            log.debug("getting "+filename+" from "+folderPath);
            setRemotePath(folderPath);
         //   listDirectory();
            setRemoteFile(filename);
            transferCompleted = false;
            addToTransferList(filename);
            setLocalFile("");
            setDownloadStream(transferList.get(filename));
            download();
            long startTxr = Calendar.getInstance().getTimeInMillis();
            while (!transferCompleted && (Calendar.getInstance().getTimeInMillis()-startTxr)<30000)
            {
                Thread.sleep(500);

            }
            if(transferCompleted)
            {
                return getTransferBinaryData(filename);
            }   else
            {
                throw new Exception("FTP transfer failed - timeout waiting for data");
            }


        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            removeFromTransferList(filename);
            transferCompleted = true;
            try {
                logoff();
            }catch (Exception e){
                System.out.println("ftp logoff error");
            }
        }



    }

     public List<String> listFiles(String folderPath)  throws Exception
    {
        log.debug("getting file list for remote directory "+folderPath);
        List<String> files = new ArrayList<String>();


        try{
            logon();
            log.debug("logged on");
            setRemotePath(folderPath);
            listDirectory();
            ipworks.DirEntryList dirs = getDirList();
            for(ipworks.DirEntry dir:(List<ipworks.DirEntry>)dirs)
            {
                //log.debug((dir.getIsDir()?"D:":"")+dir.getFileName());
                if(!(dir.getIsDir()) )
                {
                    files.add(dir.getFileName());
                }
            }

        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            try {
                logoff();
            }catch  (Exception e){
                e.printStackTrace();
            }
        }
        return files;

    }
}
