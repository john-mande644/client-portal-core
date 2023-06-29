package com.owd.dc.inventory.forms;

import org.apache.struts.action.ActionForm;
import com.owd.dc.inventory.upcBarcodeUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 18, 2005
 * Time: 10:12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class upcForm extends ActionForm {
  private String barcode="";
  private String invID="";

    public String getInvID() {
        return invID;
    }

    public void setInvID(String invID) {
        this.invID = invID;
    }

    public String getBarcode() {
          return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }




}
