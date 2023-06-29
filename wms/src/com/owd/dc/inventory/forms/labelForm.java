package com.owd.dc.inventory.forms;

import org.apache.struts.action.ActionForm;
import com.owd.dc.inventory.upcBarcodeUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 20, 2005
 * Time: 11:01:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class labelForm extends ActionForm{
    private String sku="";
    private String numlabels="";
    private String action="";

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNumlabels() {
        return numlabels;
    }

    public void setNumlabels(String numlabels) {
        this.numlabels = numlabels;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
