package com.owd.dc.setup;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 11, 2005
 * Time: 4:44:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationForm extends ActionForm {
  private String location;

  private String[] list = {"DC-MOB-1","DC-MOB-2","ADMIN-MOB"};
  private String[] visibleList = {"DC 1","DC 2","ADMIN"};

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
