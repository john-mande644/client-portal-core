package com.owd.core.business.order.packing;

public class BoxData {
    int inventoryId;
    int clientId;
    long qty;
    String shipDate;
    String facilityCode;

    public BoxData(int iid, int cid, long aqty, String adate, String fcode)
    {
        inventoryId = iid;
        clientId = cid;
        qty = aqty;
        shipDate = adate;
        facilityCode = fcode;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }
}
