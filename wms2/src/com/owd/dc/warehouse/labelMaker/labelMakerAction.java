package com.owd.dc.warehouse.labelMaker;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.beans.jsonLabelMakerInventoryBean;
import com.owd.dc.beans.jsonResultBean;
import com.owd.dc.inventory.labels.inventoryLabelUtilities;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 19, 2009
 * Time: 9:51:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class labelMakerAction extends ActionSupport {
    private Map<String, String> facilityMap;
    private Map<String, String> printerMap;
    private Map<String, Map<String, String>> printerTabMap;
    private Map<String, String> clientMap;
    private String locId;
    private String locName;
    private Map<String, String> childLocList;
    private Object jsonModel;
    private String printer;
    private Map<String, String> locToPrint;
    private String clientFkey;
    private Integer id;
    private String qty;
    private String defaultLocation;


    public String printInventory() {
        jsonResultBean b = new jsonResultBean();

        try {

            if (id != null && qty != null && printer != null) {
                b = inventoryLabelUtilities.printInventory(id, qty, printer, HibernateSession.currentSession());


            } else {
                b.setError("Something is not set right try again");
            }


        } catch (Exception e) {
            e.printStackTrace();
            b.setError(("error ing out: " + e.getMessage()));
        }

        setJsonModel(b);

        return Action.SUCCESS;
    }

    public String getInventory() {
        jsonLabelMakerInventoryBean JIB;
        try {
            if (clientFkey.length() > 0) {
                JIB = labelMakerUtilities.getInventoryForClient(HibernateSession.currentSession(), clientFkey);
                setJsonModel(JIB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public String execute() {
        try {
            Session sess = HibernateSession.currentSession();
            facilityMap = labelMakerUtilities.loadFacilityMap(sess);
            printerMap = labelMakerUtilities.loadPrinterMap(sess);
            clientMap = labelMakerUtilities.loadClientMap(sess);
            defaultLocation = ActionContext.getContext().getSession().get("owd_default_location").toString();
            printerTabMap = labelMakerUtilities.getPrinterTabs(printerMap);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }


        return Action.SUCCESS;
    }

    public String locationTab() {


        try {
            if (locId != null) {

                Session sess = HibernateSession.currentSession();
                childLocList = com.owd.dc.locations.locationUtilities.getDirectChildMapForIdNoMobile(sess, locId);
            } else {
                addActionError("Blank location Id not allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return Action.SUCCESS;
    }

    public String printDirectChildrenLocations() {
        jsonResultBean b = new jsonResultBean();
        try {
            if (locId != null && printer != null && locName != null) {
                System.out.println(locId);
                System.out.println(printer);
                System.out.println(locName);

                Session sess = HibernateSession.currentSession();
                b = labelMakerUtilities.printDirectChildrenLocation(locId, printer, sess, locName);
            } else {
                b.setError("A null paramter happened");
            }

        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }


        setJsonModel(b);
        return Action.SUCCESS;
    }

    public String printTree() {
        jsonResultBean b = new jsonResultBean();
        try {
            if (locToPrint != null) {
                System.out.println(locToPrint);
                b = labelMakerUtilities.printTreeMap(locToPrint, printer, HibernateSession.currentSession());
            }


        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }
        setJsonModel(b);
        return ActionSupport.SUCCESS;

    }

    public String printRange() {
        jsonResultBean b = new jsonResultBean();
        try {
            if (locToPrint != null) {
                System.out.println(locToPrint);
                b = labelMakerUtilities.printLocationMap(locToPrint, printer, HibernateSession.currentSession());
            }


        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }
        setJsonModel(b);
        return ActionSupport.SUCCESS;
    }

    public String printLocation() {
        jsonResultBean b = new jsonResultBean();
        try {
            if (locId != null && printer != null && locName != null) {
                Session sess = HibernateSession.currentSession();
                b = labelMakerUtilities.printSingleLocation(locId, printer, sess, locName);
            } else {
                b.setError("A null paramter happened");
            }

        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }


        setJsonModel(b);
        return Action.SUCCESS;
    }

    public Map<String, String> getFacilityMap() {
        return facilityMap;
    }

    public void setFacilityMap(Map<String, String> facilityMap) {
        this.facilityMap = facilityMap;
    }

    public Map<String, String> getPrinterMap() {
        return printerMap;
    }

    public void setPrinterMap(Map<String, String> printerMap) {
        this.printerMap = printerMap;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Map<String, String> getChildLocList() {
        return childLocList;
    }

    public void setChildLocList(Map<String, String> childLocList) {
        this.childLocList = childLocList;
    }

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Map<String, String> getLocToPrint() {
        return locToPrint;
    }

    public void setLocToPrint(Map<String, String> locToPrint) {
        this.locToPrint = locToPrint;
    }

    public Map<String, String> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, String> clientMap) {
        this.clientMap = clientMap;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public Map<String, Map<String, String>> getPrinterTabMap() {
        return printerTabMap;
    }

    public void setPrinterTabMap(Map<String, Map<String, String>> printerTabMap) {
        this.printerTabMap = printerTabMap;
    }
}
