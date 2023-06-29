package com.owd.dc.inventorycount;

import org.apache.struts.action.ActionForm;
import com.owd.dc.inventory.upcBarcodeUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 30, 2005
 * Time: 11:59:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class wCountForm extends ActionForm{
    private String requestId ="";
    private String inventoryId;
    private String location;
    private String quanity;
    private String invLocFkey ="";
    private String verifyInventoryId;
    private String UPC;
    private String ISBN;
    private String barcode;
    private String prevId;
    private String verified;

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getVerifyInventoryId() {
        return verifyInventoryId;
    }

    public void setVerifyInventoryId(String verifyInventoryId) {
        this.verifyInventoryId = verifyInventoryId;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getInvLocFkey() {
        return invLocFkey;
    }

    public void setInvLocFkey(String invLocFkey) {
        this.invLocFkey = invLocFkey;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
