package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 29, 2006
 * Time: 9:14:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class countEditBean {
    private String id;
    private String invId;
    private String location;
    private String quanity;
    private String itemId;
    private String who;
    private String posted;
    private String delete;


    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }
}
