package com.owd.dc.inventory.beans;

import com.owd.hibernate.generated.OwdLotValue;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 20, 2005
 * Time: 8:25:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class skuBean {
    private String clientName;
    private String inventoryNum;
    private String inventoryId;
    private String description;
    private String size;
    private String color;
    private int quanityOnHand;
    private List<locationPriority> locations;
      private String imageUrl;
    private String imageThumb;
    private boolean lotControlled = false;
    private String lotControlledType = "";

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }


    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuanityOnHand() {
        return quanityOnHand;
    }

    public void setQuanityOnHand(int quanityOnHand) {
        this.quanityOnHand = quanityOnHand;
    }

    public List<locationPriority> getLocations() {
        return locations;
    }

    public void setLocations(List<locationPriority> locations) {
        this.locations = locations;
    }

    public boolean isLotControlled() {
        return lotControlled;
    }

    public void setLotControlled(boolean lotControlled) {
        this.lotControlled = lotControlled;
    }

    public String getLotControlledType() {
        return lotControlledType;
    }

    public void setLotControlledType(String lotControlledType) {
        this.lotControlledType = lotControlledType;
    }
}
