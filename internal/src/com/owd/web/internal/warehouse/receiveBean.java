package com.owd.web.internal.warehouse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 13, 2006
 * Time: 11:41:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveBean {
private final static Logger log =  LogManager.getLogger();
    private String asnId;
    private Integer rcvId;
    private String date;
    private String time;
    private InputStream file;
    private String page;


public String getFileName(){
    return  getRcvId() + "_" + getTime() + "_" + getPage() + ".pdf";
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
   public byte[] getFileByte() {
      byte[] buf = null;
       try{
       buf = new byte[getFile().available()];

      getFile().read(buf,1,getFile().available());
     }catch (Exception e){

     }
       return buf;
   }
    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public Integer getRcvId() {
        return rcvId;
    }

    public void setRcvId(Integer rcvId) {
        this.rcvId = rcvId;
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
