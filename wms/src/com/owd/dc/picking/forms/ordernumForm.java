package com.owd.dc.picking.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 9:35:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ordernumForm extends ActionForm {
    private String ordernumber;
    private String verify;
    private String priority;
    private String license = "";

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


    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }


    public void setordernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getordernumber() {
        return ordernumber;
    }
}
