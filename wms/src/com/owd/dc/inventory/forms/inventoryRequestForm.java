package com.owd.dc.inventory.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 22, 2005
 * Time: 3:59:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryRequestForm extends ActionForm {

 public String clientFkey;
    public String whosRequest;
    public String dateCreated;
    public String dateStart;
    public String reference="";
    public String locationToAssign="";
    public String asnId="";

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public String getLocationToAssign() {
        return locationToAssign;
    }

    public void setLocationToAssign(String locationToAssign) {
        this.locationToAssign = locationToAssign;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getWhosRequest() {
        return whosRequest;
    }

    public void setWhosRequest(String whosRequest) {
        this.whosRequest = whosRequest;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
