package com.owd.dc.picking.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 20, 2006
 * Time: 3:47:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class serialForm extends ActionForm {

    private String serial;
    private String invId;
    private String invNum;
    private String description;
    private String pItemId;
    private String total;
    private String currentNum;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    public String getpItemId() {
        return pItemId;
    }

    public void setpItemId(String pItemId) {
        this.pItemId = pItemId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
