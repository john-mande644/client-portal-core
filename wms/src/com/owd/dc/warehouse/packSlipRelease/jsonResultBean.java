package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 7, 2009
 * Time: 11:20:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class jsonResultBean {

    private String message="";
    private String error="";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
