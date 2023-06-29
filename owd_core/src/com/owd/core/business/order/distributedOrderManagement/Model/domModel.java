package com.owd.core.business.order.distributedOrderManagement.Model;

import java.util.List;

public class domModel {

    private String zip;
    private Integer client_fkey;
    private Integer skuCount;
    private List<domSku> skus;

    public List<domSku> getSkus() {
        return skus;
    }

    public void setSkus(List<domSku> skus) {
        this.skus = skus;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getClient_fkey() {
        return client_fkey;
    }

    public void setClient_fkey(Integer client_fkey) {
        this.client_fkey = client_fkey;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }
}
