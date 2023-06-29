
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubtotalPriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney____ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney____ presentmentMoney;

    public ShopMoney____ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney____ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney____ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney____ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
