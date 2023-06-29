package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 6, 2006
 * Time: 11:48:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class historyBean {
    private String location;
    private String readableLocation;
    private String assignDate;
    private String user;
    private String removedate;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReadableLocation() {
        return readableLocation;
    }

    public void setReadableLocation(String readableLocation) {
        this.readableLocation = readableLocation;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRemovedate() {
        return removedate;
    }

    public void setRemovedate(String removedate) {
        this.removedate = removedate;
    }
}
