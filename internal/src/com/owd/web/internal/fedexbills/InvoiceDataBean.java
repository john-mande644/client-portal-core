package com.owd.web.internal.fedexbills;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 25, 2004
 * Time: 4:19:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceDataBean {
    String invoiceNumber = "";
    int items = 0;
    int unresolvedCount = 0;

    public float getClientIncentive() {
        return clientIncentive;
    }

    public void setClientIncentive(float clientIncentive) {
        this.clientIncentive = clientIncentive;
    }

    float clientIncentive = 0.00f;


    public int getUnresolvedCount() {
        return unresolvedCount;
    }

    public void setUnresolvedCount(int unresolvedCount) {
        this.unresolvedCount = unresolvedCount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    String billDate = "";

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public float getCharges() {
        return charges;
    }

    public void setCharges(float charges) {
        this.charges = charges;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    float charges = 0.00f;
    float incentive = 0.00f;
    String user = "";
    String fileName = "";
    String importDate = "";
}
