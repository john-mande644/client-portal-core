
package com.owd.core.api.shopify.orderResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingLine {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("requested_fulfillment_service_id")
    @Expose
    private Object requestedFulfillmentServiceId;
    @SerializedName("delivery_category")
    @Expose
    private Object deliveryCategory;
    @SerializedName("carrier_identifier")
    @Expose
    private Object carrierIdentifier;
    @SerializedName("discounted_price")
    @Expose
    private String discountedPrice;
    @SerializedName("price_set")
    @Expose
    private PriceSet___ priceSet;
    @SerializedName("discounted_price_set")
    @Expose
    private DiscountedPriceSet discountedPriceSet;
    @SerializedName("discount_allocations")
    @Expose
    private List<Object> discountAllocations = null;
    @SerializedName("tax_lines")
    @Expose
    private List<TaxLine__> taxLines = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getRequestedFulfillmentServiceId() {
        return requestedFulfillmentServiceId;
    }

    public void setRequestedFulfillmentServiceId(Object requestedFulfillmentServiceId) {
        this.requestedFulfillmentServiceId = requestedFulfillmentServiceId;
    }

    public Object getDeliveryCategory() {
        return deliveryCategory;
    }

    public void setDeliveryCategory(Object deliveryCategory) {
        this.deliveryCategory = deliveryCategory;
    }

    public Object getCarrierIdentifier() {
        return carrierIdentifier;
    }

    public void setCarrierIdentifier(Object carrierIdentifier) {
        this.carrierIdentifier = carrierIdentifier;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public PriceSet___ getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet___ priceSet) {
        this.priceSet = priceSet;
    }

    public DiscountedPriceSet getDiscountedPriceSet() {
        return discountedPriceSet;
    }

    public void setDiscountedPriceSet(DiscountedPriceSet discountedPriceSet) {
        this.discountedPriceSet = discountedPriceSet;
    }

    public List<Object> getDiscountAllocations() {
        return discountAllocations;
    }

    public void setDiscountAllocations(List<Object> discountAllocations) {
        this.discountAllocations = discountAllocations;
    }

    public List<TaxLine__> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine__> taxLines) {
        this.taxLines = taxLines;
    }

}
