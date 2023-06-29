package com.owd.dc.packing.AutoBatchPrinting.beans;

import com.thoughtworks.xstream.XStream;

/**
 * Created by danny on 2/12/2017.
 */
public class successBean {

    private String action;
    private String message;
    private boolean error;
    private String errorMessage;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public XStream getXStream(){
        XStream x = new XStream();
        x.alias("successBean", successBean.class);



        return x;
    }

    public String getXML(){
        return getXStream().toXML(this);

    }
}
