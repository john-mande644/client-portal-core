package com.owd.web.internal.intravexbills;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 25, 2004
 * Time: 4:19:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceDataGroupTypeSummary {
    String transactionCode = "";
    int itemsInGroup = 0;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public int getItemsInGroup() {
        return itemsInGroup;
    }

    public void setItemsInGroup(int itemsInGroup) {
        this.itemsInGroup = itemsInGroup;
    }

    public float getSumNetCharges() {
        return sumNetCharges;
    }

    public void setSumNetCharges(float sumNetCharges) {
        this.sumNetCharges = sumNetCharges;
    }

    public float getSumIncentives() {
        return sumIncentives;
    }

    public void setSumIncentives(float sumIncentives) {
        this.sumIncentives = sumIncentives;
    }

    public float getSumTotalBill() {
        return sumTotalBill;
    }

    public void setSumTotalBill(float sumTotalBill) {
        this.sumTotalBill = sumTotalBill;
    }

    public float getSumRatedCost() {
        return sumRatedCost;
    }

    public void setSumRatedCost(float sumRatedCost) {
        this.sumRatedCost = sumRatedCost;
    }

    float sumNetCharges = 0.00f;
    float sumIncentives = 0.00f;
    float sumTotalBill = 0.00f;
    float sumRatedCost = 0.00f;
}
