
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxLine_ {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("price_set")
    @Expose
    private PriceSet__ priceSet;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public PriceSet__ getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet__ priceSet) {
        this.priceSet = priceSet;
    }

}
