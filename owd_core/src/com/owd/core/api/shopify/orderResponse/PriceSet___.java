
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSet___ {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney__________ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney__________ presentmentMoney;

    public ShopMoney__________ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney__________ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney__________ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney__________ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
