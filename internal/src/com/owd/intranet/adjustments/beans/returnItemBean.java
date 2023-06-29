package com.owd.intranet.adjustments.beans;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.intranet.util;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:01:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class returnItemBean {
private final static Logger log =  LogManager.getLogger();
    private String receiveItemId;
private String receiveFkey;
private String inventoryId;
private String inventoryNum;
private String description;
private String itemStatus;
private String isUnusable;
private String quantity;
private String location;
private String createdDate;
private String createdBy;
private String modifiedDate;
private String modifiedBy;
private String returnReason;
private String reported;
private String isVoid;
private List statusList;
private List reasonList;
private String checked;
private String invOnHand;
    private String lotValue;
    private boolean lotControlled;
    private List lotList;

    public List getLotList() {
        return lotList;
    }

    public void setLotList(List lotList) {
        this.lotList = lotList;
    }

    public boolean isLotControlled() {
        return lotControlled;
    }

    public void setLotControlled(boolean lotControlled) {
        this.lotControlled = lotControlled;
    }

    private boolean serialRequired = false;

    public boolean getSerialRequired() {
        return serialRequired;
    }

    public void setSerialRequired(boolean serialRequired) {
        this.serialRequired = serialRequired;
    }

    private String serial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInvOnHand() {
        return invOnHand;
    }

    public void setInvOnHand(String invOnHand) {
        this.invOnHand = invOnHand;
    }

    public String getChecked() {
       
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List getReasonList() {
        if(null==reasonList){
            setReasonList();
        }
        return reasonList;
    }

    public void setReasonList() {
        this.reasonList = util.loadReturnReasons();
    }


    public List getStatusList() {
        if(null==statusList){
            setStatusList();
        }
        return statusList;
    }

    public void setStatusList() {

        this.statusList = util.loadStatus();
    }

    public String getReceiveItemId() {
        return receiveItemId;
    }

    public void setReceiveItemId(String receiveItemId) {
        this.receiveItemId = receiveItemId;
    }

    public String getReceiveFkey() {
        return receiveFkey;
    }

    public void setReceiveFkey(String receiveFkey) {
        this.receiveFkey = receiveFkey;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getUnusable() {
        return isUnusable;
    }

    public void setUnusable(String unusable) {
        isUnusable = unusable;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getReported() {
        return reported;
    }

    public void setReported(String reported) {
        this.reported = reported;
    }

    public String getVoid() {
        return isVoid;
    }

    public void setVoid(String aVoid) {
        isVoid = aVoid;
    }

    public String getLotValue() {
        return lotValue;
    }

    public void setLotValue(String lotValue) {
        this.lotValue = lotValue;
    }


}
