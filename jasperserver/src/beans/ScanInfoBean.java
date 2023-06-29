package beans;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/10/14
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScanInfoBean {
    private String date;
    private String time;
    private InputStream file;
    private String page;
    private byte[] pdf;


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

                getFile().read(buf,0,getFile().available());
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

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}

