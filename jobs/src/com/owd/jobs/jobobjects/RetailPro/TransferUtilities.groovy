package com.owd.jobs.jobobjects.RetailPro

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ftp.FTPManager

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/20/12
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
class TransferUtilities {
    private final static Logger log =  LogManager.getLogger();

    static final String ftpHost="ftp.owd.com"
    static final String ftpLogin="babeland_rpro"
    static final String ftpPass="babeland7172rpro"


    public static void main (String[] args) throws Exception
     {
           getRetailProFiles("SO_");
     }


    public static void putRetailProFile(String fileName, String contents, boolean allowOverwrite)
    {

        FTPManager ftp = new FTPManager(ftpHost,ftpLogin,ftpPass);
        ftp.setReadDirectory("/to_rdi");
        ftp.setWriteDirectory("/to_rdi");
        Vector files = new Vector();//ftp.getFileNames();
        log.debug("ftp found " + files.size() + " files!");

        boolean pullFound = true
        if(!allowOverwrite)
        {
        for (int i = 0; i < files.size(); i++) {
            log.debug("FTP found file " + i + " " + files.get(i));
            if (files.get(i).toString().equalsIgnoreCase(fileName)) {
                log.debug("Not OK to place files");
                pullFound = false;
                break;
            }
        }
        }
        if (pullFound) {
            println "Writing..."
            ftp.writeFile(fileName, contents.getBytes());

    }
    }

            public static Map<String,byte[]> getRetailProFiles(String prefix)
    {

        Map<String,byte[]> fileMap = new TreeMap<String,byte[]>();

        FTPManager ftp = new FTPManager(ftpHost,ftpLogin,ftpPass);
        ftp.setReadDirectory("/to_owd");
        ftp.setWriteDirectory("/to_owd/done");

        Vector files = ftp.getFileNames();
        log.debug("ftp found " + files.size() + " files!");

        for (int i = 0; i < files.size(); i++) {
            log.debug("FTP found file " + i + " " + files.get(i));
            if (files.get(i).toString().startsWith(prefix)) {
                log.debug("got "+prefix+" file "+files.get(i).toString());
                fileMap.put(files.get(i).toString(),ftp.getFileData(files.get(i).toString()));

            }
        }

      //  log.debug(fileMap);
        return fileMap;
    }

    public static Map<String,byte[]> moveRetailProFileToDone(String fileName)
 {

     FTPManager ftp = new FTPManager(ftpHost,ftpLogin,ftpPass);

     ftp.moveFile("/to_owd/"+fileName,"/to_owd/done/"+fileName);

 }


}


