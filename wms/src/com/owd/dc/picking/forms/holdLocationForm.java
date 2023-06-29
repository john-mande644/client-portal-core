package com.owd.dc.picking.forms;

import org.apache.struts.action.ActionForm;

public class holdLocationForm extends ActionForm {

    public String hold_location;
    public int pickStatusId;


    public String getHold_location() {
        return hold_location;
    }

    public void setHold_location(String hold_location) {
        this.hold_location = hold_location;
    }

    public int getPickStatusId() {
        return pickStatusId;
    }

    public void setPickStatusId(int pickStatusId) {
        this.pickStatusId = pickStatusId;
    }
}
