package com.owd.dc.warehouse.misc.beans;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/5/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class sneakPackItemsBean {
    private String invId;
    private String invNum;
    private String desc;
    private int qty;
    private int qtyPacked;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyPacked() {
        return qtyPacked;
    }

    public void setQtyPacked(int qtyPacked) {
        this.qtyPacked = qtyPacked;
    }
}





