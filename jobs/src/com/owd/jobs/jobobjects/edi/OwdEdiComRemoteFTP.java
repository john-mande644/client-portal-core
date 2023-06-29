package com.owd.jobs.jobobjects.edi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ftp.FTPManager;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Apr 15, 2010
 * Time: 9:12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class OwdEdiComRemoteFTP
{
private final static Logger log =  LogManager.getLogger();

    private String ftpHost="edi.owd.com";
    private String ftpLogin="edi";
    private String ftpPassword="one7172world";

    private String incoming = "/messages/One_World_Direct/inbox/";
    private String outgoing1 = "/messages/";
    private String outgoing2 = "/outbox/One_World_Direct";
    static             Pattern regex = Pattern.compile("(.*)~ST\\*(.*?)\\*(.*)");


    public OwdEdiComRemoteFTP()
    {
    }

    public static void main(String[] args) throws Exception {

        OwdEdiComRemoteFTP ftp = new OwdEdiComRemoteFTP();
        List<String> files = ftp.listIncomingFiles("DSWater");
        for(String file:files)
        {
          String data = new String(ftp.getDataFile(file,"DSWater"));
          //  log.debug(data);
            String type = getIncomingFileDocType(data);
            if("997".equals(type))
            {
                ftp.archiveIncomingDataFile("DSWater", file,type);
            }

        }
    }

    public static String getIncomingFileDocType(String data)
    {
        Matcher matcher = regex.matcher(data);

        if (matcher.matches()) {
            return matcher.group(2);
        }else
        {
            return "UNKNOWN";
        }
    }


    
     public String putOutboundFile(String partner,byte[] data,String fileName) throws Exception
    {

        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
       ftp.setWriteDirectory(outgoing1+partner+outgoing2);
       ftp.writeFile(fileName,data);
        return fileName;

    }
     public void archiveIncomingDataFile(String partner,String fileName, String type) throws Exception
    {
        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setReadDirectory(incoming + partner + "/");
        byte[] data =  ftp.getFileData(fileName);

        ftp.setWriteDirectory("/archive/" + partner + "/"+type);
        ftp.makeDirectory("/archive/" + partner + "");
        ftp.makeDirectory("/archive/" + partner + "/"+type);
        ftp.writeFile(fileName, data);
        ftp.setWriteDirectory(incoming+partner+"/");
        ftp.deleteFile(fileName);


    }



     public byte[] getDataFile(String fileName,String partner) throws Exception
    {
       FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setReadDirectory(incoming+partner+"/");
       byte[] data =  ftp.getFileData(fileName);
        return data;
    }

     public List<String> listIncomingFiles(String partner)  throws Exception
    {
        FTPManager ftp = new FTPManager(ftpHost, ftpLogin, ftpPassword);
        ftp.setReadDirectory(incoming+partner+"/");
      //  log.debug(incoming+partner+"/");
       // ftp.setWriteDirectory(folderPath.getPath());
        List<String> files = ftp.getFileNames();
        log.debug("ftp found " + files.size() + " files in "+partner+"!");

        return files;
    }
}
