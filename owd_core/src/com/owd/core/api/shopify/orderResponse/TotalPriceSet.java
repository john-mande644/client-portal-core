
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalPriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney_____ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney_____ presentmentMoney;

    public ShopMoney_____ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney_____ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney_____ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney_____ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
