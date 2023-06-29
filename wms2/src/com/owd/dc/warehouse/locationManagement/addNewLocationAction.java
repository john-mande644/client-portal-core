package com.owd.dc.warehouse.locationManagement;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.beans.jsonNewLocationBean;
import com.owd.dc.beans.jsonResultBean;
import com.owd.dc.locations.addNewLocation;
import com.owd.dc.locations.locationUtilities;
import com.owd.dc.locations.printLocationLabel;
import com.owd.dc.warehouse.labelMaker.labelMakerUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 4, 2009
 * Time: 3:47:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class addNewLocationAction extends ActionSupport {
    private String action;
    private String parentId;
    private String locType;
    private String name;
    private Integer qty;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private Object jsonModel;

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String addMobile() {
        jsonResultBean b = new jsonResultBean();
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println(request.getRemoteAddr());
        try {
            List<String> newLocations = addNewLocation.addNewMobileLocations(HibernateSession.currentSession(), parentId, locType, "danny", request.getRemoteAddr(), qty);

            HibUtils.commit(HibernateSession.currentSession());
            String printer = labelMakerUtilities.getLargePrinterForMobileViaParent(HibernateSession.currentSession(), parentId);
            for (String loc : newLocations) {
                printLocationLabel.printLocationById(loc, printer, HibernateSession.currentSession());


            }

            b.setMessage("Well it worked");
        } catch (Exception e) {
            b.setError(e.getMessage());

        }

        setJsonModel(b);
        return Action.SUCCESS;
    }

    public String execute() {
        try {
            System.out.println(action);

            System.out.println("hello");
            jsonNewLocationBean locb = new jsonNewLocationBean();
            try {

                if (action.equals("error")) {
                    locb.setError("Error this did not work so good");
                } else {
                    if (name.length() == 0) throw new Exception("Cannot have an empty location Name");
                    Session sess = HibernateSession.currentSession();
                    name = locationUtilities.getLocationName(sess, name, locType);
                    System.out.println("This is the nameer  " + name);
                    int id = addNewLocation.addLocation(sess, parentId, name, locType, "Danny", "192.168.10.2");


                    HibUtils.commit(sess);
                    locb.setMessage("Added " + name);
                    locb.setNewId(id + "");
                    locb.setNewName(name);

                }
            } catch (Exception e) {
                e.printStackTrace();
                locb.setError(e.getMessage());
            }

            System.out.println("jjjjjj");
            setJsonModel(locb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }
}
