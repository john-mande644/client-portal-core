
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalShippingPriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney___ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney___ presentmentMoney;

    public ShopMoney___ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney___ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney___ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney___ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
