
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxLine__ {

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
    private PriceSet____ priceSet;

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

    public PriceSet____ getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet____ priceSet) {
        this.priceSet = priceSet;
    }

}
