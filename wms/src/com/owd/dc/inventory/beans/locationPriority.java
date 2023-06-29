package com.owd.dc.inventory.beans;

import com.owd.hibernate.generated.OwdLotValue;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 1/22/15
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationPriority {
    private String location;
    private String priority;
    private String barcode;
    private String assignDate;
    private String notes;
    private OwdLotValue lot;

    public OwdLotValue getLot() {
        return lot;
    }

    public void setLot(OwdLotValue lot) {
        this.lot = lot;
    }


    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    //backward compatability for of previous beans

    public String getBarcode() {
        if(null == barcode || ""==barcode){
            return location;
        }
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
