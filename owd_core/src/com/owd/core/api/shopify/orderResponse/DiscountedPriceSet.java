
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountedPriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney___________ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney___________ presentmentMoney;

    public ShopMoney___________ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney___________ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney___________ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney___________ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
