package com.owd.jobs.jobobjects.sftp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ipworksssh.DirEntry;
import ipworksssh.DirEntryList;
import ipworksssh.Sftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 9/22/14.
 */
public class WalmartSftpClient extends Sftp {
private final static Logger log =  LogManager.getLogger();


    String sftpHost = "";
    String sftpUser = "";
    String sftpPass = "";

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


        //qa
        WalmartSftpClient s = new WalmartSftpClient("161.170.240.133","gildan","je8z2PSMwj");

        List<String> orders = s.listOrderFiles();
         for (String file:orders)
         {
             log.debug(file);
             log.debug(new String(s.getOrderData(file)));
         }
    }

    public WalmartSftpClient(String host,  String user, String pass)
    {
        sftpHost = host;
        sftpUser = user;
        sftpPass = pass;

    }


    public synchronized String getOrderData(String filename) throws Exception
    {


        try{
            logon();
            log.debug("logged on");
            setRemotePath("/outgoing/orders/walmart/");
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
           // setRemotePath("/outgoing/orders/walmart/");
deleteFile(filename);


        }catch(Exception ex)
        {
            throw ex;
        }
        finally {
            SSHLogoff();
        }

    }

    public void writeOrderPOAck(String batchRef, String data, String extension) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/acknowledgment/walmart/");
            setRemoteFile("HUB"+batchRef+"."+extension);
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
            setRemotePath("/incoming/fa/walmart/");
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

    public void writeOrderShipped(String batchRef, String data) throws Exception
    {

        try{
            logon();
            log.debug("logged on");
            setRemotePath("/incoming/fa/walmart/");
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
    public  List<String> listOrderFiles() throws Exception
    {
        List<String> files = new ArrayList<String>();


        try{
         logon();
        log.debug("logged on");
        setRemotePath("/outgoing/orders/walmart/");
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
