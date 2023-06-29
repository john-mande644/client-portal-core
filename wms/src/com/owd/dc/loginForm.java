package com.owd.dc;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 3:07:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class loginForm extends ActionForm {
  private String username;
  private String facility;
  private List facList;


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public List getFacList() {
        return facList;
    }

    public void setFacList(List facList) {
        this.facList = facList;
    }
}
