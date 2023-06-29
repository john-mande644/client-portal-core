
package com.owd.core.api.shopify.orderResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("variant_id")
    @Expose
    private Long variantId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("variant_title")
    @Expose
    private String variantTitle;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("fulfillment_service")
    @Expose
    private String fulfillmentService;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("requires_shipping")
    @Expose
    private Boolean requiresShipping;
    @SerializedName("taxable")
    @Expose
    private Boolean taxable;
    @SerializedName("gift_card")
    @Expose
    private Boolean giftCard;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("variant_inventory_management")
    @Expose
    private String variantInventoryManagement;
    @SerializedName("properties")
    @Expose
    private List<Object> properties = null;
    @SerializedName("product_exists")
    @Expose
    private Boolean productExists;
    @SerializedName("fulfillable_quantity")
    @Expose
    private Integer fulfillableQuantity;
    @SerializedName("grams")
    @Expose
    private Integer grams;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("fulfillment_status")
    @Expose
    private Object fulfillmentStatus;
    @SerializedName("price_set")
    @Expose
    private PriceSet_ priceSet;
    @SerializedName("total_discount_set")
    @Expose
    private TotalDiscountSet totalDiscountSet;
    @SerializedName("discount_allocations")
    @Expose
    private List<Object> discountAllocations = null;
    @SerializedName("admin_graphql_api_id")
    @Expose
    private String adminGraphqlApiId;
    @SerializedName("tax_lines")
    @Expose
    private List<TaxLine_> taxLines = null;
    @SerializedName("origin_location")
    @Expose
    private OriginLocation originLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getVariantTitle() {
        return variantTitle;
    }

    public void setVariantTitle(String variantTitle) {
        this.variantTitle = variantTitle;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(Boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public Boolean getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(Boolean giftCard) {
        this.giftCard = giftCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariantInventoryManagement() {
        return variantInventoryManagement;
    }

    public void setVariantInventoryManagement(String variantInventoryManagement) {
        this.variantInventoryManagement = variantInventoryManagement;
    }

    public List<Object> getProperties() {
        return properties;
    }

    public void setProperties(List<Object> properties) {
        this.properties = properties;
    }

    public Boolean getProductExists() {
        return productExists;
    }

    public void setProductExists(Boolean productExists) {
        this.productExists = productExists;
    }

    public Integer getFulfillableQuantity() {
        return fulfillableQuantity;
    }

    public void setFulfillableQuantity(Integer fulfillableQuantity) {
        this.fulfillableQuantity = fulfillableQuantity;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Object getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(Object fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public PriceSet_ getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet_ priceSet) {
        this.priceSet = priceSet;
    }

    public TotalDiscountSet getTotalDiscountSet() {
        return totalDiscountSet;
    }

    public void setTotalDiscountSet(TotalDiscountSet totalDiscountSet) {
        this.totalDiscountSet = totalDiscountSet;
    }

    public List<Object> getDiscountAllocations() {
        return discountAllocations;
    }

    public void setDiscountAllocations(List<Object> discountAllocations) {
        this.discountAllocations = discountAllocations;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }

    public List<TaxLine_> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine_> taxLines) {
        this.taxLines = taxLines;
    }

    public OriginLocation getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(OriginLocation originLocation) {
        this.originLocation = originLocation;
    }

}
