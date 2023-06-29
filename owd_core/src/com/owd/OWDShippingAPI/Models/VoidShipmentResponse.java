package com.owd.OWDShippingAPI.Models;

import java.util.List;

/**
 * Created by danny on 11/14/2019.
 */
public class VoidShipmentResponse {

    private List<Voidedid> voidedIds;

    public List<Voidedid> getVoidedIds() {
        return voidedIds;
    }

    public void setVoidedIds(List<Voidedid> voidedIds) {
        this.voidedIds = voidedIds;
    }
}
