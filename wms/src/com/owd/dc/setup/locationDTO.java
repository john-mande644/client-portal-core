package com.owd.dc.setup;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 11, 2005
 * Time: 4:44:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationDTO {
    private String location;

  private String[] list;
  private String[] visibleList ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    public String[] getVisibleList() {
        return visibleList;
    }

    public void setVisibleList(String[] visibleList) {
        this.visibleList = visibleList;
    }
}
