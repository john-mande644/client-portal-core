package com.owd.dc.beans;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 17, 2009
 * Time: 11:09:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class jsonTreeBean {

    private String identifier;
    private String label;

    List<treeBean> items;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<treeBean> getItems() {
        return items;
    }

    public void setItems(List<treeBean> items) {
        this.items = items;
    }
}
