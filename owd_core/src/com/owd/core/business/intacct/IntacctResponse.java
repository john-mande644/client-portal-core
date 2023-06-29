package com.owd.core.business.intacct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 31, 2006
 * Time: 10:25:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntacctResponse {
private final static Logger log =  LogManager.getLogger();


    Document rawResponse = null;

    List responseData = new ArrayList();
    String errorDescription="";

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    boolean ok = true;

    public Document getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Document rawResponse) {
        this.rawResponse = rawResponse;
    }

    public List getResponseData() {
        return responseData;
    }

    public void setResponseData(List responseData) {
        this.responseData = responseData;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
