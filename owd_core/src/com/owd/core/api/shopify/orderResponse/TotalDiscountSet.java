
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalDiscountSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney________ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney________ presentmentMoney;

    public ShopMoney________ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney________ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney________ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney________ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
