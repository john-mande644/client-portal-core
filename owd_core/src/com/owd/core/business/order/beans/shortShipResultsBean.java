package com.owd.core.business.order.beans;

/**
 * Created by danny on 10/25/2019.
 */
public class shortShipResultsBean {

    private Boolean success = false;
    private String message;
    private String url = "";


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
