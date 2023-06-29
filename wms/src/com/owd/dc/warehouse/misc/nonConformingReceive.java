package com.owd.dc.warehouse.misc;
import java.io.*;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;

/**
 * Created by danny on 11/4/2016.
 */
public class nonConformingReceive extends ActionSupport {


    public String execute(){

        return SUCCESS;

    }

    public String enterForm(){
        System.out.println(filename);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getTotalSpace());

        File f = new File(filename);
        try {


            if (!f.exists()) {
                f.createNewFile();
            }
            FileUtils.copyFile(file,f);


            System.out.println(f.getAbsolutePath());


        }catch (Exception e){
            e.printStackTrace();
        }

        return SUCCESS;
    }

    private File file;
    private String contentType;
    private String filename;

    public void setUpload(File file) {
        this.file = file;
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUploadFileName(String filename) {
        this.filename = filename;
    }


}
