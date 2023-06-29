package com.owd.dc.packing;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 11, 2009
 * Time: 10:36:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultBean {
    private String message = "";
    private String error = "";
    private String sortSound;
    private String sortType;
    private String sortText;
    private String sortImageUrl;
    private boolean vcOrder;
    private String orderNum = "";

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isVcOrder() {
        return vcOrder;
    }

    public void setVcOrder(boolean vcOrder) {
        this.vcOrder = vcOrder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSortSound() {
        return sortSound;
    }

    public void setSortSound(String sortSound) {
        this.sortSound = sortSound;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortText() {
        return sortText;
    }

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    public String getSortImageUrl() {
        return sortImageUrl;
    }

    public void setSortImageUrl(String sortImageUrl) {
        this.sortImageUrl = sortImageUrl;
    }
}
