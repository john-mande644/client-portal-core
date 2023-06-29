package com.owd.dc.warehouse.orderInfo;

import com.owd.hibernate.generated.OwdLineItem;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 29, 2010
 * Time: 9:33:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class packageLineBean {
    private String packQuantity;
    private OwdLineItem  lineItem;


    public float getTotalWeight() {
       return lineItem.getOwdInventory().getWeightLbs() * Float.valueOf(packQuantity);

    }
    public float getWeight(){
        return lineItem.getOwdInventory().getWeightLbs();
    }


    public String getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(String packQuantity) {
        this.packQuantity = packQuantity;
    }

    public OwdLineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(OwdLineItem lineItem) {
        this.lineItem = lineItem;
    }
}
