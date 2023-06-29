package com.owd.dc.locations.frontLocation;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 22, 2010
 * Time: 5:04:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class frontLocationBean {
    private String sku;
    private String description;
    private String frontLocation;
    private List<String> otherLocations;
    private boolean frontInList ;

    public boolean isFrontInList() {
        return frontInList;
    }

    public void setFrontInList(boolean frontInList) {
        this.frontInList = frontInList;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrontLocation() {
        return frontLocation;
    }

    public void setFrontLocation(String frontLocation) {
        this.frontLocation = frontLocation;
    }

    public List<String> getOtherLocations() {
        return otherLocations;
    }

    public void setOtherLocations(List<String> otherLocations) {
        this.otherLocations = otherLocations;
    }
}
