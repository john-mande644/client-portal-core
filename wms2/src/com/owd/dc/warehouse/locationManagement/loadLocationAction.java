package com.owd.dc.warehouse.locationManagement;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.beans.locationInfoBean;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 16, 2009
 * Time: 4:03:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadLocationAction extends ActionSupport {
    private String locId;
    private locationInfoBean locInfo;

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public locationInfoBean getLocInfo() {
        return locInfo;
    }

    public void setLocInfo(locationInfoBean locInfo) {
        this.locInfo = locInfo;
    }

    public String execute() {
        System.out.println("hello");
        try {
            Session sess = HibernateSession.currentSession();
            Logger log = LogManager.getLogger();
            locInfo = new locationInfoBean(locId, sess);
            log.debug("executing loadLocationAction");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return Action.SUCCESS;
    }
}
