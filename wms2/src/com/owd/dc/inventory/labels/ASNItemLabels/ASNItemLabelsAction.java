package com.owd.dc.inventory.labels.ASNItemLabels;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/20/14
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASNItemLabelsAction extends ActionSupport {
    private String asnId;
    private Boolean includeUPC;
    private Boolean qtyLeft;
    private List<ASNItemLabelsBean> items;

    public String viewItems() {
        try {
            items = ItemLabelUtils.loadAsnItemsToPrint(asnId, includeUPC, qtyLeft);


        } catch (Exception e) {
            addActionError(e.getMessage());
            e.printStackTrace();
        }

        return "success";
    }

    public String printItems() {
        try {
            items = ItemLabelUtils.loadAsnItemsToPrint(asnId, includeUPC, qtyLeft);
            ItemLabelUtils.printLabels(items, "zebra12.dc1.owd.com");
        } catch (Exception e) {
            addActionError(e.getMessage());
        }
        return "success";
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public Boolean getIncludeUPC() {
        return includeUPC;
    }

    public void setIncludeUPC(Boolean includeUPC) {
        this.includeUPC = includeUPC;
    }

    public Boolean getQtyLeft() {
        return qtyLeft;
    }

    public void setQtyLeft(Boolean qtyLeft) {
        this.qtyLeft = qtyLeft;
    }

    public List<ASNItemLabelsBean> getItems() {
        return items;
    }

    public void setItems(List<ASNItemLabelsBean> items) {
        this.items = items;
    }
}
