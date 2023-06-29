
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSet__ {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney_________ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney_________ presentmentMoney;

    public ShopMoney_________ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney_________ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney_________ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney_________ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
