package com.owd.dc.warehouse.packSlipRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 2/24/15
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class printErrorBean {
  private int addressTotal;
    private int addressNew;
    private int createdTotal;
    private int createNew;
    private Map<String, facilityPrintInfo> facilities = new TreeMap<String, facilityPrintInfo>();

    public int getAddressTotal() {
        return addressTotal;
    }

    public void setAddressTotal(int addressTotal) {
        this.addressTotal = addressTotal;
    }

    public int getAddressNew() {
        return addressNew;
    }

    public void setAddressNew(int addressNew) {
        this.addressNew = addressNew;
    }

    public int getCreatedTotal() {
        return createdTotal;
    }

    public void setCreatedTotal(int createdTotal) {
        this.createdTotal = createdTotal;
    }

    public int getCreateNew() {
        return createNew;
    }

    public void setCreateNew(int createNew) {
        this.createNew = createNew;
    }

    public Map<String, facilityPrintInfo> getFacilities() {
        return facilities;
    }

    public void setFacilities(Map<String, facilityPrintInfo> facilities) {
        this.facilities = facilities;
    }
}
