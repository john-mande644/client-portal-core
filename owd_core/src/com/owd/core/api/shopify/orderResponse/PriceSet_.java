
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSet_ {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney_______ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney_______ presentmentMoney;

    public ShopMoney_______ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney_______ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney_______ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney_______ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
