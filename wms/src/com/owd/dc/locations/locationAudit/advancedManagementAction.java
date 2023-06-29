package com.owd.dc.locations.locationAudit;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

/**
 * Created by danny on 5/16/2015.
 */
public class advancedManagementAction extends ActionSupport {
private String currentlocationName;
    private String currentlocationId;
    private Map<String,String> childMap;
    private String ixNode;
    private boolean showDelete = true;
    private String assignedLocations;
    private String reloadIx;
    private WLocation wloc;

    public String execute() {
        showDelete = false;
String facility = ServletActionContext.getRequest().getSession().getAttribute("owd_current_location").toString();
       try {
           if (facility.length() > 0) {
               ixNode = auditUtils.getLocationIdFromFacilityString(facility);
loadVariables(true);
           } else {
               addActionMessage("No facility definec login again");
               return "error";
           }

       }catch (Exception e){
           e.printStackTrace();
           addActionMessage(e.getMessage());
           return "error";

       }
        return "success";
    }

    public String loadLocation(){
        try{
            System.out.println("WE are loading this thing "+ixNode);
        loadVariables(false);

        }catch(Exception e){
            addActionMessage(e.getMessage());
            e.printStackTrace();

        }

return "success";
    }

    public String clearOne(){
        System.out.println("We are in step one of clearing locations");


        return "success";
    }

    public String clearTwo(){
        System.out.println("We are clearing yup yup yup:");
        try {
            String s = auditUtils.deleteAssignedInventoryFromAllChildren(ixNode);
            WLocation loc = (WLocation) HibernateSession.currentSession().load(WLocation.class, Integer.parseInt(ixNode));
            addActionMessage("We cleared all assigned locations under " +loc.getPickString());
            addActionMessage("We removed "+s+" assigned locations");
            loadVariables(false);
        }catch (Exception e){
            e.printStackTrace();
            addActionMessage(e.getMessage());

        }
        return "success";
    }
    public String deleteOne(){
        try{
            WLocation loc = (WLocation)HibernateSession.currentSession().load(WLocation.class, Integer.parseInt(ixNode));
reloadIx = loc.getParentId().toString();
        wloc = loc;
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        return "success";
    }
    public String deleteTwo(){
        try{
            WLocation loc = (WLocation)HibernateSession.currentSession().load(WLocation.class, Integer.parseInt(ixNode));
            reloadIx = loc.getParentId().toString();
            wloc = loc;
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        return "success";
    }

    public String deleteThree(){
        try {
            System.out.println("Do the delete");

            ixNode = reloadIx;
            loadVariables(false);
            addActionMessage("Deleted");
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

return "success";
    }

private void loadVariables(boolean facility) throws Exception{
    if(ixNode.length()>0){
        WLocation loc = (WLocation)HibernateSession.currentSession().load(WLocation.class, Integer.parseInt(ixNode));
        currentlocationId = ixNode;
        currentlocationName = loc.getLocationName();
childMap = auditUtils.loadLocationDirectChildren(ixNode,facility);
        assignedLocations = auditUtils.getNumberOfAssignedItemsInLocationTree(ixNode);

    }


}


    public WLocation getWloc() {
        return wloc;
    }

    public void setWloc(WLocation wloc) {
        this.wloc = wloc;
    }

    public String getReloadIx() {
        return reloadIx;
    }

    public void setReloadIx(String reloadIx) {
        this.reloadIx = reloadIx;
    }

    public String getAssignedLocations() {
        return assignedLocations;
    }

    public void setAssignedLocations(String assignedLocations) {
        this.assignedLocations = assignedLocations;
    }

    public String getCurrentlocationName() {
        return currentlocationName;
    }

    public void setCurrentlocationName(String currentlocationName) {
        this.currentlocationName = currentlocationName;
    }

    public String getCurrentlocationId() {
        return currentlocationId;
    }

    public void setCurrentlocationId(String currentlocationId) {
        this.currentlocationId = currentlocationId;
    }

    public Map<String, String> getChildMap() {
        return childMap;
    }

    public void setChildMap(Map<String, String> childMap) {
        this.childMap = childMap;
    }

    public String getIxNode() {
        return ixNode;
    }

    public void setIxNode(String ixNode) {
        this.ixNode = ixNode;
    }

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }
}
