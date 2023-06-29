
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalDiscountsSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney__ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney__ presentmentMoney;

    public ShopMoney__ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney__ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney__ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney__ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
