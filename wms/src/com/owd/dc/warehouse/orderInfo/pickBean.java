package com.owd.dc.warehouse.orderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 17, 2011
 * Time: 2:58:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickBean {
    private String pickBy;
    private String nubmerPicks;
    private String startTime;
    private String endTime;
    private String pickTime;
    private String replenish;
    private List<pickItemsBean> pickItems = new ArrayList<pickItemsBean>();


    public String getPickBy() {
        return pickBy;
    }

    public void setPickBy(String pickBy) {
        this.pickBy = pickBy;
    }

    public String getNubmerPicks() {
        return nubmerPicks;
    }

    public void setNubmerPicks(String nubmerPicks) {
        this.nubmerPicks = nubmerPicks;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getReplenish() {
        return replenish;
    }

    public void setReplenish(String replenish) {
        this.replenish = replenish;
    }

    public List<pickItemsBean> getPickItems() {
        return pickItems;
    }

    public void setPickItems(List<pickItemsBean> pickItems) {
        this.pickItems = pickItems;
    }
}
