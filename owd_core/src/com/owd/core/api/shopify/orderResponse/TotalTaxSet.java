
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalTaxSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney______ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney______ presentmentMoney;

    public ShopMoney______ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney______ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney______ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney______ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
