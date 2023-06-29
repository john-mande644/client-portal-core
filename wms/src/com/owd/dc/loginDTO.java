package com.owd.dc;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 3:09:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class loginDTO {

    private String username;
    private String facility;
    private List facList;
        public void setusername(String username) {
            this.username = username;
        }

        public String getusername() {
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
