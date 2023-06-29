package com.owd.jobs.jobobjects

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ftp.FTPManager
import com.owd.jobs.jobobjects.pgp.PGPUtils
import com.owd.jobs.jobobjects.pgp.WalmartPgp

/**
 * Created by stewartbuskirk1 on 9/16/14.
 */
class WalmartApi {

      String ftpHost = ""
    String ftpLogin = ""
    String ftpPassword = ""


    public static void main(String[] args) {
         WalmartApi wm = new WalmartApi()
        wm.setFtpHost('ihub1.commercehub.com')
        wm.setFtpLogin('gildanftp')
        wm.setFtpPassword('huf9f9hui6')

        wm.processNewOrders()

    }


    public void processNewOrders()
    {
        List<String> files = listFiles("/walmart/outgoing/orders/")
        for(String filename:files){
            byte[] data = getDataFile(filename,"/walmart/outgoing/orders/")
            println new String(data)

            println new String(PGPUtils.decrypt(data, new ByteArrayInputStream( WalmartPgp.owdPrivateKey.getBytes()), "badhorse57601".toCharArray()))
        }

    }



    public String putDataFile(String type,byte[] data,String folderPath, String fileName) throws Exception
    {
        log.debug(folderPath);

        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setWriteDirectory(folderPath);
        ftp.writeFile(fileName,data);
        return fileName;

    }
    public void deleteDataFile(String name,String folderPath) throws Exception
    {
        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setWriteDirectory(folderPath);
        ftp.deleteFile(name);

    }



    public byte[] getDataFile(String fileName,String folderPath) throws Exception
    {
        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setReadDirectory(folderPath);
        byte[] data =  ftp.getFileData(fileName);
        return data;
    }

    public List<String> listFiles(String folderPath)  throws Exception
    {
        log.debug("getting file list for remote directory "+folderPath);
        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setReadDirectory(folderPath);
        // ftp.setWriteDirectory(folderPath.getPath());
        List<String> files = ftp.getFileNames();
        log.debug("ftp found " + files.size() + " files in "+folderPath+"!");

        return files;
    }
}
