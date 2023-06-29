package com.owd.core.business.order.beans;

import java.util.List;

/**
 * Created by danny on 8/5/2016.
 */
public class lineItemPackageData {
    private String sku;
    private int packageQty;
    private List<lotData> lots;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getPackageQty() {
        return packageQty;
    }

    public void setPackageQty(int packageQty) {
        this.packageQty = packageQty;
    }

    public List<lotData> getLots() {
        return lots;
    }

    public void setLots(List<lotData> lots) {
        this.lots = lots;
    }
}
