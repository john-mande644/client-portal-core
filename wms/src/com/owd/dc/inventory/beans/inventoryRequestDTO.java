package com.owd.dc.inventory.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 22, 2005
 * Time: 9:20:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryRequestDTO {
    public int clientFkey;
    public String whosRequest;
    public String dateCreated;
    public String dateStart;
    public String reference;
    public String locationToAssign;
    public int asnId;

    public int getAsnId() {
        return asnId;
    }

    public void setAsnId(int asnId) {
        this.asnId = asnId;
    }

    public int getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(int clientFkey) {
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

    public String getLocationToAssign() {
        return locationToAssign;
    }

    public void setLocationToAssign(String locationToAssign) {
        this.locationToAssign = locationToAssign;
    }
}
