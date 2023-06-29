package com.owd.jobs

import com.owd.core.OWDUtilities
import ipworksssh.DirEntry
import ipworksssh.Sftp
import ipworksssh.SftpConnectedEvent
import ipworksssh.SftpConnectionStatusEvent
import ipworksssh.SftpDirListEvent
import ipworksssh.SftpDisconnectedEvent
import ipworksssh.SftpEndTransferEvent
import ipworksssh.SftpErrorEvent
import ipworksssh.SftpEventListener
import ipworksssh.SftpSSHCustomAuthEvent
import ipworksssh.SftpSSHKeyboardInteractiveEvent
import ipworksssh.SftpSSHServerAuthenticationEvent
import ipworksssh.SftpSSHStatusEvent
import ipworksssh.SftpStartTransferEvent
import ipworksssh.SftpTransferEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created by stewartbuskirk1 on 9/28/15.
 */
class SftpConnector {
    private final static Logger log =  LogManager.getLogger();

    private String host = "";
    private String login = "";
    private String pass = "";
    private String remotePath = "";


    protected Sftp ftp = new Sftp();



    private SftpConnector() {

    }

    public static void main(String[] args) {
        Sftp f = new Sftp()
        println f.getRuntimeLicense()
    }

    public SftpConnector(String ahost, String alogin, String apass, String aRemotePath) {
        ftp.setRuntimeLicense("31484A395641315355425241315355423353384132323435000000000000000000000000000000004D32434E50364655000035445947304E5252325443550000");
        ftp.setSSHHost(ahost);
        ftp.setSSHUser(alogin);
        ftp.setSSHPassword(apass);
        ftp.setRemotePath(aRemotePath);


    }
    public SftpConnector(String ahost, String alogin, String apass, String aRemotePath, String encryption) {
        ftp.setRuntimeLicense("31484A395641315355425241315355423353384132323435000000000000000000000000000000004D32434E50364655000035445947304E5252325443550000");
        ftp.setSSHHost(ahost);
        ftp.setSSHUser(alogin);
        ftp.setSSHPassword(apass);
        ftp.setRemotePath(aRemotePath);
        ftp.setSSHEncryptionAlgorithms(encryption);
        ftp.setTimeout(15000);

    }

    public void setRemotePath(String newPath)
    {
        ftp.setRemotePath(newPath)
    }
    public void renameFile(String newPath)
    {

        try {

            //login
            logon();
            String oldPath = ftp.getRemotePath();

            ftp.renameFile(newPath)

            ftp.setRemotePath(oldPath);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {

                ftp.SSHLogoff()
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deleteFile(String fileName)
    {

        try {

            //login
            logon();
           // String oldPath = ftp.getRemotePath();

            ftp.deleteFile(fileName)

          //  ftp.setRemotePath(oldPath);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {

                ftp.SSHLogoff()
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<String> getFileNames(String remotePath) {

        List<String> fileNames = new ArrayList<String>();

        try {


            //login

            logon();
            // get a list of all .txt files
             ftp.setRemotePath(remotePath);

            ftp.listDirectory();

            DirEntry[] h = new DirEntry[ftp.getDirList().size()];


            ;
            //   Collections.reverse(Arrays.asList(h));
            //step through each file
            for (DirEntry e : ftp.getDirList()) {
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

                ftp.SSHLogoff()

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return fileNames
    }


    public void putFileData(String fileName,byte[] data) throws Exception {

        try {

            logon();
            println "writing "+fileName+ " for "+data.length+" bytes"
            ftp.setRemoteFile(fileName)
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ftp.setUploadStream(byteArrayInputStream);
            ftp.upload();

            long startMillis = Calendar.getInstance().getTimeInMillis();
            while(!(ftp.isIdle()))
            {
                println "checking idle"
                Thread.sleep(2000L);
                if((Calendar.getInstance().getTimeInMillis()-startMillis)>30000L){
                    break;
                }
            }
            println "final size = "+ftp.getFileAttributes().size
            if(ftp.getFileAttributes().size<data.length)
            {
                throw new Exception("Failed writing file "+fileName+" to host "+ftp.getSSHHost()) ;
            }
        } finally {
            try {
                ftp.SSHLogoff()

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void stageFileDataAndMove(String fileName,byte[] data,String newfileName) throws Exception {

        try {

            logon();
            println "writing "+fileName+ " for "+data.length+" bytes"
            ftp.setRemoteFile(fileName)
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ftp.setUploadStream(byteArrayInputStream);
            ftp.upload();

            long startMillis = Calendar.getInstance().getTimeInMillis();
            while(!(ftp.isIdle()))
            {
                println "checking idle"
                Thread.sleep(2000L);
                if((Calendar.getInstance().getTimeInMillis()-startMillis)>30000L){
                    break;
                }
            }
            println "final size = "+ftp.getFileAttributes().size
            if(ftp.getFileAttributes().size<data.length)
            {
                throw new Exception("Failed writing file "+fileName+" to host "+ftp.getSSHHost()) ;
            }
            println("Re-naming the file now");
            println(ftp.getRemotePath());
            println(ftp.getRemoteFile());
            ftp.renameFile(newfileName);
        } finally {
            try {
                ftp.SSHLogoff()

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ByteArrayOutputStream getFileData(String fileName) {
        ByteArrayOutputStream file = null;

        try {

            logon();

                ftp.setRemoteFile(fileName);
                long originalSize = ftp.getFileAttributes().size;
                Thread.sleep(5000L);
                ftp.setRemoteFile(fileName);
                long currentSize = ftp.getFileAttributes().size;
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
                ftp.SSHLogoff()

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void logon() throws Exception
    {
       // ftp.setSSHUser(login);
       // ftp.setSSHPassword(pass);
        println "host::login -> "+host+"::"+login;
        ftp.addSftpEventListener(new SftpEventListener() {
            public void dirList(SftpDirListEvent e) {
                println (e.dirEntry);
            }

            public void connected(SftpConnectedEvent e) {
                println ("connected");
            }

            public void connectionStatus(SftpConnectionStatusEvent e) {
                println ("status:" + e.connectionEvent + ":" + e.description);
            }

            public void disconnected(SftpDisconnectedEvent e) {
                println ("disconnected");
            }

            public void endTransfer(SftpEndTransferEvent e) {
                println ("transfer ended");
              //  transferCompleted = true;
            }

            public void error(SftpErrorEvent e) {
                println ("Error " + e.errorCode + ": " + e.description);
            }

            public void SSHCustomAuth(SftpSSHCustomAuthEvent e) {
                log.debug(e.toString());
            }

            public void SSHKeyboardInteractive(SftpSSHKeyboardInteractiveEvent e) {
                println (e.prompt+"::"+e.response);
            }

            public void SSHServerAuthentication(SftpSSHServerAuthenticationEvent e) {
                println ("accepting cert");
                e.accept = true;

                return;
            }

            public void SSHStatus(SftpSSHStatusEvent e) {
                println (e.message);
            }

            public void startTransfer(SftpStartTransferEvent e) {
                println ("Starting txr");
            }

            public void transfer(SftpTransferEvent e) {
                println ("transferring "+e.bytesTransferred+" bytes from "+e.remoteFile);
            }
        });
        println ("logging on "+host);
        ftp.SSHLogon(ftp.getSSHHost(),22);



    }
}
