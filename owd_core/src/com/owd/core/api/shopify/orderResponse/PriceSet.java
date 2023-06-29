
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney presentmentMoney;

    public ShopMoney getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
