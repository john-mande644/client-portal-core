package com.owd.dc.warehouse.statusScroller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/27/14
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONscrollerMessageBean {
    private String error;

    private List<scrollerMessageBean> data;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<scrollerMessageBean> getData() {
        return data;
    }

    public void setData(List<scrollerMessageBean> data) {
        this.data = data;
    }
}
