package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.locations.locationInfoBean;
import com.owd.dc.locations.newLocationUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/11/13
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class bulkLocationCheck extends ActionSupport {
    private locationInfoBean lib;
    private String location;
    private String locAction;
    private String inventoryId;
    private String newParentLocation;
    private boolean goodLocation = false;

    public String execute() {

        return "success";
    }

    public String location() {
        try {
            if (!location.startsWith("//")) throw new Exception("not a valid location");
            lib = new locationInfoBean(location, HibernateSession.currentSession());
            System.out.println(lib.getId());
            System.out.println("Lsls ");
            if (lib.isMobile()) {
                goodLocation = true;

            } else {
                goodLocation = false;
                addActionError("This is not a mobile location");
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
        }


        return "success";
    }

    public String set() {
        try {
            Session sess = HibernateSession.currentSession();
            if (!newParentLocation.startsWith("//")) throw new Exception("Not a valid location");
            lib = new locationInfoBean(location, sess);
            locationInfoBean parent = new locationInfoBean(newParentLocation, sess);
            String sql = "select ixParent from w_location where ixNode = :id";
            Query q = sess.createSQLQuery(sql);
            q.setParameter("id", location.replace("//", ""));
            List result = q.list();
            if (result.size() > 0) {
                if (result.get(0).toString().equals(parent.getId())) {
                    goodLocation = false;
                    addActionMessage("Good");
                    return "success";
                }
                //boolean good = newLocationUtilities.moveLocation(sess, lib, parent);
                boolean good = newLocationUtilities.updateParentId(sess,lib.getId()+"",parent.getId()+"");

                if (good) {
                   // HibUtils.commit(sess);
                   // newLocationUtilities.updatePickStrings(sess, Integer.parseInt(location.replace("//", "")));
                    addActionMessage("Moved " + lib.getName() + " to " + parent.getPickString());
                } else {
                    throw new Exception("Move failed, try again or contact IT");
                }

            } else {
                addActionError("Something went wrong me so sorry");
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
        }
        return "success";
    }


    public locationInfoBean getLib() {
        return lib;
    }

    public void setLib(locationInfoBean lib) {
        this.lib = lib;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocAction() {
        return locAction;
    }

    public void setLocAction(String locAction) {
        this.locAction = locAction;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getNewParentLocation() {
        return newParentLocation;
    }

    public void setNewParentLocation(String newParentLocation) {
        this.newParentLocation = newParentLocation;
    }

    public boolean isGoodLocation() {
        return goodLocation;
    }

    public void setGoodLocation(boolean goodLocation) {
        this.goodLocation = goodLocation;
    }
}
