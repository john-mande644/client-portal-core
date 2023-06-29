package com.owd.dc.picking.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 9:34:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ordernumDTO {
    private String ordernumber;
    private int verify;
    private String priority = "1";
    private String license;


    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOrdernumber2() {
        return ordernumber2;
    }

    public void setOrdernumber2(String ordernumber2) {
        this.ordernumber2 = ordernumber2;
    }

    private String ordernumber2;


    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }


    public void setordernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getordernumber() {
        return ordernumber;
    }
}
