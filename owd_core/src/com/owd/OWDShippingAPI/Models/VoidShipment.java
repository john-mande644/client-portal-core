package com.owd.OWDShippingAPI.Models;

import java.util.List;

/**
 * Created by danny on 11/14/2019.
 */
public class VoidShipment {

    private List<String> voidIds;

    public List<String> getVoidIds() {
        return voidIds;
    }

    public void setVoidIds(List<String> voidIds) {
        this.voidIds = voidIds;
    }
}
