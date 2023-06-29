package com.owd.dc.warehouse.supplyTracking;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 9/17/12
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONsupplyTrackingbean {
    private String identifier = "inventoryId";
       private String error="";
       private List<supplyTrackingBean> items;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<supplyTrackingBean> getItems() {
        return items;
    }

    public void setItems(List<supplyTrackingBean> items) {
        this.items = items;
    }
}
