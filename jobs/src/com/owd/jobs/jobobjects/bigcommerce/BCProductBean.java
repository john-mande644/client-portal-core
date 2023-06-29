package com.owd.jobs.jobobjects.bigcommerce;

/**
 * Created by danny on 4/3/2017.
 */
public class BCProductBean {
    private String sku;
    private String childSku ="";
    private boolean owdHas;



    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getChildSku() {
        return childSku;
    }

    public void setChildSku(String childSku) {
        this.childSku = childSku;
    }

    public boolean isOwdHas() {
        return owdHas;
    }

    public void setOwdHas(boolean owdHas) {
        this.owdHas = owdHas;
    }
}
