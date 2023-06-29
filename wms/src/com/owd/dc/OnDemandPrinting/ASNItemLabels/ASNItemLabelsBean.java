package com.owd.dc.OnDemandPrinting.ASNItemLabels;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/20/14
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASNItemLabelsBean {
    private String AsnItemId;
    private String inventoryId;
    private String inventoryNum;
    private String description;
    private String countToPrint;


    public String getAsnItemId() {
        return AsnItemId;
    }

    public void setAsnItemId(String asnItemId) {
        AsnItemId = asnItemId;
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

    public String getCountToPrint() {
        return countToPrint;
    }

    public void setCountToPrint(String countToPrint) {
        this.countToPrint = countToPrint;
    }
}
