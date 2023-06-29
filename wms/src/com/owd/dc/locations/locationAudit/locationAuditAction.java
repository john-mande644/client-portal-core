package com.owd.dc.locations.locationAudit;

import com.opensymphony.xwork2.ActionSupport;

import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/6/14
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationAuditAction extends ActionSupport  {
    private String employeeId;
    private String ixNode;
    private String inventoryId;
    private List<String> invList;
    private List<String> scannedList;
    private Map theCookies;
    private String locationString;
    private String empty="false";
    private List<auditDataBean> today;
    private List<auditDataBean> weekly;
    private List<auditDataBean> monthly;


public String numbers(){
    try{
       today = auditUtils.todaysNumbers();
        weekly = auditUtils.weeksNumbers();
        monthly = auditUtils.monthNumbers();

    }catch(Exception e){
        e.printStackTrace();
        addActionError(e.getMessage());
    }

    return "success";
}
    public String execute() {
       try {
           for (Cookie c : ServletActionContext.getRequest().getCookies()) {
               if (c.getName().equals("tcid")) {

                   employeeId = c.getValue();
                   System.out.println("Setting empoleeid to " + c.getValue());
               }


           }

           addActionMessage("Your last location scanned was: " + auditUtils.getLastLocationAuditForFacilityAndID(ServletActionContext.getRequest().getSession().getAttribute("owd_current_location").toString(), employeeId));
           addActionMessage("Last Scanned location in facility is " + auditUtils.getLastLocationAuditForFacility(ServletActionContext.getRequest().getSession().getAttribute("owd_current_location").toString()));
       }catch (Exception e){
           e.printStackTrace();
           addActionError(e.getMessage());
       }


        return "success";


    }


    public String scanItem() {
        HttpSession session = ServletActionContext.getRequest().getSession();

        try {

            if (inventoryId.startsWith("//")) {


                System.out.println("Location scan");
                if (null == ixNode) {
                    System.out.println("Initial Scan, load items");

                } else {
                    invList = (List<String>) session.getAttribute("invList");



                    System.out.println("Done with location clean up");
                    for (String inv : invList) {
                        System.out.println("Removing id of " + inv);
                        auditUtils.removeAssignedLocation(ixNode, employeeId, inv, session.getAttribute("owd_current_location").toString());
                       addActionMessage("Removed id of " + inv);
                    }
                    System.out.println("Cleared now load new location");
                    if(empty.equals("true")){
                        System.out.println("Empty location scanned properly");
                        auditUtils.insertAuditRecordNeutral(ixNode, employeeId, "", session.getAttribute("owd_current_location").toString());
                       addActionMessage("Empty Location Scanned Empty");
                    }

                }
                if(inventoryId.equals("//done")){
                    return "success";
                }
                invList = auditUtils.getInventoryIdsInLocation(inventoryId);
                if(invList.size()==0) {
                    empty = "true";
                }else{
                    empty = "false";
                }
                session.setAttribute("invList", invList);
                scannedList = new ArrayList<String>();
                session.setAttribute("scannedList",scannedList);
                ixNode= inventoryId.replace("//","");
                System.out.print(ixNode);
                WLocation wloc = (WLocation) HibernateSession.currentSession().load(WLocation.class,Integer.parseInt(ixNode));
                locationString = wloc.getPickString();
                addActionMessage("There are "+invList.size()+ " known items.");
                return "nextItem";

            } else {
                String msku = upcBarcodeUtilities.getSku(inventoryId, 0);
                if(msku.equals("Multi")){
                    throw new Exception("This barcode is assinged to multiple items sorry this isn't going to work");
                }else{
                    inventoryId = msku;
                }

                System.out.println("This is a possible inventory scan act apropriately");
                invList = (List<String>) session.getAttribute("invList");
                if(null == session.getAttribute("scannedList")){
                    scannedList = new ArrayList<String>();
                }else{
                    scannedList = (ArrayList<String>) session.getAttribute("scannedList");
                }

                if(scannedList.contains(inventoryId)== false) {
                    if (invList.contains(inventoryId)) {
                        System.out.println("WE have a good scan record neutral and remove");
                        invList.remove(inventoryId);
                        session.setAttribute("invList", invList);


                        auditUtils.insertAuditRecordNeutral(ixNode, employeeId, inventoryId, session.getAttribute("owd_current_location").toString());
                        addActionMessage("Already Existed");
                        System.out.println("done inserting record");

                    } else {
                        System.out.println("WE have found a new item, add and record");
                        addActionMessage("Assigned");
                        auditUtils.assignInventoryToLocation(ixNode, employeeId, inventoryId, session.getAttribute("owd_current_location").toString());
                    }

                    scannedList.add(inventoryId);
                    session.setAttribute("scannedList",scannedList);
                }else{
                    addActionMessage("Already scanned that item");
                }

            }

            addActionMessage("There are "+invList.size()+ " known items left to scan.");
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            if(null == ixNode){
                return "success";

            }
        }

        return "nextItem";
    }


    public List<auditDataBean> getToday() {
        return today;
    }

    public void setToday(List<auditDataBean> today) {
        this.today = today;
    }

    public List<auditDataBean> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<auditDataBean> weekly) {
        this.weekly = weekly;
    }

    public List<auditDataBean> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<auditDataBean> monthly) {
        this.monthly = monthly;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public void setCookiesMap(Map coo) {
        theCookies = coo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIxNode() {
        return ixNode;
    }

    public void setIxNode(String ixNode) {
        this.ixNode = ixNode;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public List<String> getInvList() {
        return invList;
    }

    public void setInvList(List<String> invList) {
        this.invList = invList;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
}
