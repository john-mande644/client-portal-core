package com.owd.jobs.jobobjects.commercehub;

import ipworksssh.DirEntry;
import ipworksssh.DirEntryList;
import ipworksssh.Sftp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 9/22/14.
 */
public class CommerceHubSftpClient extends Sftp {
private final static Logger log =  LogManager.getLogger();


    String sftpHost = "";
    String sftpUser = "";
    String sftpPass = "";
    String remoteAccountFolderName = "";

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

    public static void main(String[] args) throws Exception {

        //prod
    //    WalmartSftpClient s = new WalmartSftpClient("161.170.244.51","gildan","v38wVpaM");

        CommerceHubSftpClient s = new CommerceHubSftpClient("gildanusa.sftp.commercehub.com", "gildanusa", "Bell!Improve$Already5","jcpenney");

        //qa
      //  CommerceHubSftpClient s = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
    //    s.logon();
        log.debug("logged on");
      //  s.setRemotePath("/outgoing/orders/"+"gildanusa"+"/");
       // s.listDirectory();
        System.out.println( s.getRemotePath() );
        List<String> orders = s.listOrderFiles();
         for (String file:orders)
         {
             log.debug(file);
             log.debug(new String(s.getOrderData(file)));
         }
    }

    public CommerceHubSftpClient(String host, String user, String pass, String remoteFolder) throws Exception
    {
        sftpHost = host;
        sftpUser = user;
        sftpPass = pass;
        remoteAccountFolderName = remoteFolder;
        setSSHAuthMode(2);
    }


    public synchronized String getOrderData(String filename) throws Exception
    {


        try{
            logon();
            log.debug("logged on");
            setRemotePath("/outgoing/orders/"+remoteAccountFolderName+"/");
            listDirectory();
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
                return getTransferData(filename);
            }   else
            {
                throw new Exception("SFTP transfer failed - timeout waiting for data");
            }


        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            removeFromTransferList(filename);
            transferCompleted = true;
            SSHLogoff();
        }



    }

    public void deleteOrderFile(String filename) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/outgoing/orders/"+remoteAccountFolderName+"/");
deleteFile(filename);


        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }

    public void writeOrderPOAck(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/acknowledgment/"+remoteAccountFolderName+"/");
            setRemoteFile("HUB"+batchRef+".ack");
            setUploadStream(new ByteArrayInputStream(data.getBytes()));
            upload();



        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }
    public void writeOrderAck(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/fa/"+remoteAccountFolderName+"/");
            setRemoteFile(batchRef+".fa");
            setUploadStream(new ByteArrayInputStream(data.getBytes()));
            upload();

        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }

    public void writeInventoryAdvice(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/inventory/"+remoteAccountFolderName+"/");
            setRemoteFile(batchRef+".inv");
            setUploadStream(new ByteArrayInputStream(data.getBytes()));
            upload();
        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }

    public void writeReturnEvent(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/return/"+remoteAccountFolderName+"/");
            setRemoteFile(batchRef+".return");
            setUploadStream(new ByteArrayInputStream(data.getBytes()));
            upload();
        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }

    public void writeOrderShipped(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/confirms/"+remoteAccountFolderName+"/");
            setRemoteFile(batchRef+".confirm");
            setUploadStream(new ByteArrayInputStream(data.getBytes()));
            upload();



        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }
    public  List<String> listOrderFiles() throws Exception
    {
        List<String> files = new ArrayList<String>();


        try{
         logon();
        log.debug("logged on");
        setRemotePath("/outgoing/orders/"+remoteAccountFolderName+"/");
        listDirectory();
        DirEntryList dirs = getDirList();
        for(DirEntry dir:dirs)
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
            SSHLogoff();
        }
        return files;

    }

    private void logon() throws Exception
    {
        setSSHUser(sftpUser);
        setSSHPassword(sftpPass);
        log.debug("host::login::password -> "+sftpHost+"::"+sftpUser+"::"+sftpPass);
        addSftpEventListener(new ipworksssh.SftpEventListener() {
            public void dirList(ipworksssh.SftpDirListEvent e) {
                log.debug(e.dirEntry);
            }

            public void connected(ipworksssh.SftpConnectedEvent e) {
                log.debug("connected");
            }

            public void connectionStatus(ipworksssh.SftpConnectionStatusEvent e) {
                log.debug("status:" + e.connectionEvent + ":" + e.description);
            }

            public void disconnected(ipworksssh.SftpDisconnectedEvent e) {
                log.debug("disconnected");
            }

            public void endTransfer(ipworksssh.SftpEndTransferEvent e) {
                log.debug("transfer ended");
                transferCompleted = true;
            }

            public void error(ipworksssh.SftpErrorEvent e) {
                log.debug("Error " + e.errorCode + ": " + e.description);
            }

            public void SSHCustomAuth(ipworksssh.SftpSSHCustomAuthEvent e) {
                log.debug(e.toString());
            }

            public void SSHKeyboardInteractive(ipworksssh.SftpSSHKeyboardInteractiveEvent e) {
                log.debug(e.prompt+"::"+e.response);
            }

            public void SSHServerAuthentication(ipworksssh.SftpSSHServerAuthenticationEvent e) {
                log.debug("accepting cert");
                e.accept = true;

                return;
            }

            public void SSHStatus(ipworksssh.SftpSSHStatusEvent e) {
                log.debug(e.message);
            }

            public void startTransfer(ipworksssh.SftpStartTransferEvent e) {
                log.debug("Starting txr");
            }

            public void transfer(ipworksssh.SftpTransferEvent e) {
                log.debug("transferring "+e.bytesTransferred+" bytes from "+e.remoteFile);
            }
        });
        log.debug("logging on");
        SSHLogon(sftpHost, 22);



    }



}
