package com.owd.core.business.order.zoneUtilities;

/**
 * Created by danny on 6/30/2018.
 */
public class shipMethodUpdate {

    private boolean change;
    private String method;
    private String code;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isChange() {

        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }
}
