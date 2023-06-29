package com.owd.dc.warehouse.vendorCompliance.labelsMaker;

/**
 * Created by danny on 10/19/2016.
 */
public class skuBean {

    private String mapId;
    private String owdId;
    private String owdSku;
    private String vendorSku;
    private String vendorDesc;


    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getOwdId() {
        return owdId;
    }

    public void setOwdId(String owdId) {
        this.owdId = owdId;
    }

    public String getOwdSku() {
        return owdSku;
    }

    public void setOwdSku(String owdSku) {
        this.owdSku = owdSku;
    }

    public String getVendorSku() {
        return vendorSku;
    }

    public void setVendorSku(String vendorSku) {
        this.vendorSku = vendorSku;
    }

    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }
}
