package com.owd.dc.receiving.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 23, 2005
 * Time: 3:20:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class asnDTO {
   private String receivedBy;
    private int minutes;
    private int carton;
    private int pallet;
    private String notes;
      private String wiId;

    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getCarton() {
        return carton;
    }

    public void setCarton(int carton) {
        this.carton = carton;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
