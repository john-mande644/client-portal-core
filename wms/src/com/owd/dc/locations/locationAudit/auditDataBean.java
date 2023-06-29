package com.owd.dc.locations.locationAudit;

/**
 * Created by danny on 5/21/2015.
 */
public class auditDataBean {

    private String facility;
    private int total;
    private int good;
    private int added;
    private int removed;
    private String goodPercent;
    private String addedPercent;
    private String removedPercent;

    public String getGoodPercent() {
       int i = Math.round(new Float(good)/total*100);
        goodPercent = i+"%";

        return goodPercent;
    }

    public void setGoodPercent(String goodPercent) {

        this.goodPercent = goodPercent;
    }

    public String getAddedPercent() {
        int i = Math.round(new Float(added)/total*100);
        addedPercent = i+"%";
        return addedPercent;
    }

    public void setAddedPercent(String addedPercent) {
        this.addedPercent = addedPercent;
    }

    public String getRemovedPercent() {
        int i = Math.round(new Float(removed)/total*100);
        removedPercent = i+"%";
        return removedPercent;
    }

    public void setRemovedPercent(String removedPercent) {

        this.removedPercent = removedPercent;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }
}
