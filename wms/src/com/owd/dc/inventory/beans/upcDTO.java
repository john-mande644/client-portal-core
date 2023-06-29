package com.owd.dc.inventory.beans;

import com.owd.dc.inventory.upcBarcodeUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 18, 2005
 * Time: 10:13:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class upcDTO {
    private String barcode;
    private int invID;


    public int getInvID() {
        return invID;
    }

    public void setInvID(int invID) {
        this.invID = invID;
    }


    public void setbarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getbarcode() {
       return barcode;
    }

}
