package com.owd.jobs.jobobjects.utilities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 16, 2007
 * Time: 10:01:35 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScanInfoBean {
private final static Logger log =  LogManager.getLogger();



    private String date;
    private String time;
    private InputStream file;
    private String page;


public abstract String getFileName();


    public void setBuf(byte[] data)
    {
        buf = data;
    }
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }


    public InputStream getFile() {
        return file;
    }

        byte[] buf = null;

   public byte[] getFileByte() {

       if(buf==null)
       {
       try{
       buf = new byte[getFile().available()];

      getFile().read(buf,1,getFile().available());
     }catch (Exception e){
         e.printStackTrace();
     }
       }
       return buf;
   }
    public void setFile(InputStream file) {
        this.file = file;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

