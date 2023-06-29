package com.owd.dc.picking.beans;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 24, 2005
 * Time: 1:12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickItemBean {
    private String orderId;
    private String orderNum;
    private int pickItemId;
    private int currentItem;
    private int itemSize;
    private int barcode;
    private int qtyPicked;
    private int qtyToPick;
    private int onHand;
    private String sku;
    private String description;
    private List locList;
    private String locFirst;
    private int verify;
    private int pickStatusId;
    private int clientFkey;
    private String imageUrl;
    private String imageThumb;
    private Boolean redOrder;
    private int caseQty;
    private int masterCaseQty;
    private int qtyLeft;
    private int casePick;
    private int eachPick;
    private String UPC;
    private String ISBN;
    private String license;

    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    public int getQtyLeft() {
        return qtyLeft;
    }

    public void setQtyLeft(int qtyLeft) {
        this.qtyLeft = qtyLeft;
    }

    public int getCasePick() {
        return casePick;
    }

    public void setCasePick(int casePick) {
        this.casePick = casePick;
    }

    public int getEachPick() {
        return eachPick;
    }

    public void setEachPick(int eachPick) {
        this.eachPick = eachPick;
    }

    public int getCaseQty() {
        return caseQty;
    }

    public void setCaseQty(int caseQty) {

        this.caseQty = caseQty;
    }

    public int getMasterCaseQty() {
        return masterCaseQty;
    }

    public void setMasterCaseQty(int masterCaseQty) {
        this.masterCaseQty = masterCaseQty;
    }

    public Boolean getRedOrder() {
        return redOrder;
    }

    public void setRedOrder(Boolean redOrder) {
        this.redOrder = redOrder;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public int getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(int clientFkey) {
        this.clientFkey = clientFkey;
    }

    public int getPickStatusId() {
        return pickStatusId;
    }

    public void setPickStatusId(int pickStatusId) {
        this.pickStatusId = pickStatusId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getQtyPicked() {
        return qtyPicked;
    }

    public void setQtyPicked(int qtyPicked) {
        this.qtyPicked = qtyPicked;
    }

    public int getQtyToPick() {
        return qtyToPick;
    }

    public void setQtyToPick(int qtyToPick) {
        this.qtyToPick = qtyToPick;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getLocList() {
        return locList;
    }

    public void setLocList(List locList) {
        this.locList = locList;
    }

    public String getLocFirst() {
        return locFirst;
    }

    public void setLocFirst(String locFirst) {
        this.locFirst = locFirst;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public int getPickItemId() {
        return pickItemId;
    }

    public void setPickItemId(int pickItemId) {
        this.pickItemId = pickItemId;
    }
}
