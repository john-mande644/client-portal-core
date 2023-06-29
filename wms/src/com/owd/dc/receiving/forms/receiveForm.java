package com.owd.dc.receiving.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 16, 2006
 * Time: 8:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveForm extends ActionForm{
    private String cartonCount;
    private String asnId;
    private String rcvId;
    private String invId = "";
    private String receiveItemId;
    private String count;
    private String serialNumber;
    private String vCount;
    private String vSerialNumber;
    private String damaged;
    private String weight;
    private String action;
   private String labelCount;
    private String doLabelPrint;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(String cartonCount) {
        this.cartonCount = cartonCount;
    }

    public String getDoLabelPrint() {
        return doLabelPrint;
    }

    public void setDoLabelPrint(String doLabelPrint) {
        this.doLabelPrint = doLabelPrint;
    }

    public String getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(String labelCount) {
        this.labelCount = labelCount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getvSerialNumber() {
        return vSerialNumber;
    }

    public void setvSerialNumber(String vSerialNumber) {
        this.vSerialNumber = vSerialNumber;
    }


    public String getvCount() {
        return vCount;
    }

    public void setvCount(String vCount) {
        this.vCount = vCount;
    }

    public String getReceiveItemId() {
        return receiveItemId;
    }

    public void setReceiveItemId(String receiveItemId) {
        this.receiveItemId = receiveItemId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public String getRcvId() {
        return rcvId;
    }

    public void setRcvId(String rcvId) {
        this.rcvId = rcvId;
    }
}
