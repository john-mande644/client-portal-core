package com.owd.dc.setup;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 6, 2006
 * Time: 11:59:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class emailForm extends ActionForm {
    private String stockout;
    private String hold;

    public String getStockout() {
        return stockout;
    }

    public void setStockout(String stockout) {
        this.stockout = stockout;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }
}
