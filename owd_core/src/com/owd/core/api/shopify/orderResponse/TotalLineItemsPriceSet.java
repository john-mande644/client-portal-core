
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalLineItemsPriceSet {

    @SerializedName("shop_money")
    @Expose
    private ShopMoney_ shopMoney;
    @SerializedName("presentment_money")
    @Expose
    private PresentmentMoney_ presentmentMoney;

    public ShopMoney_ getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney_ shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney_ getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney_ presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}
