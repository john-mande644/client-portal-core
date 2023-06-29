
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSet____ {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney____________ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney____________ presentmentMoney;

    public ShopMoney____________ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney____________ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney____________ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney____________ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
