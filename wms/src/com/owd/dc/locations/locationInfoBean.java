package com.owd.dc.locations;

import com.owd.core.TimeStamp;
import com.owd.dc.locations.Utilities.locationInfoLookupUtil;
import com.owd.dc.locations.Utilities.locationManagementUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;


import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 17, 2009
 * Time: 4:27:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationInfoBean {
    private String name;
    private Integer locationType;
    private int id;
    private Map<String,String> parents;
    private List<locAttributeBean> locationAttributes;
    private List<locAttributeBean> inventoryAttributes;
    private Map<String, String> allowedChildren;
    private boolean hasInventory;
    private boolean hasDirectInventory;
    private List inventory;
    private Map<String,String>children;
    private String parentString;
    private String pickString;
    private String formatedPickString;
    private String sortString;
    private List<primaryLocationBean> inventoryPrimaryLocationList;
    private List<String> validParentLocationTypes;
    private boolean mobile;
    private String priority;


    public List<String> getValidParentLocationTypes() throws Exception{
        validParentLocationTypes = new ArrayList<String>();
        String sql = "SELECT\n" +
                "    dbo.w_location_type.description\n" +
                "FROM\n" +
                "    dbo.w_location_allowed_parents\n" +
                "INNER JOIN dbo.w_location_type\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location_allowed_parents.Allowed_Parent = dbo.w_location_type.ixLocType\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.w_location_allowed_parents.ixLocType = :locType and Allowed_Parent < 14;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
         q.setString("locType",locationType.toString());
         List<Object> results = q.list();
            for(Object row:results){
                 validParentLocationTypes.add(row.toString());
            }
        return validParentLocationTypes;
    }

    public void setValidParentLocationTypes(List<String> validParentLocationTypes) {
        this.validParentLocationTypes = validParentLocationTypes;
    }

    public locationInfoBean(String locId, Session sess) throws Exception {
        TimeStamp ts1 = new TimeStamp();
        TimeStamp ts2 = new TimeStamp();
        ts1.print("Starting location info bean");
        if (locId.startsWith("//")){
            locId = locId.replace("//","");
        }
        WLocation loc = null;

        try
        {
        id = Integer.parseInt(locId);

        }catch(NumberFormatException nfe)
        {
            throw new Exception("Invalid location id value : "+locId);
        }
        ts2.print("Going to Load wloc");
        loc = (WLocation) sess.get(WLocation.class,id);
        ts2.print("Done wloc");
        if(loc==null)
        {
            throw new Exception("Location id value not found : "+locId);
        }
         name = loc.getLocationName();
        locationType = loc.getLocationType()  ;

        switch (Integer.parseInt(loc.getPickPriority())){
            case 1: priority = "Primary";
                break;
            case 2: priority = "Secondary";
                break;
            default: priority = "Default priority";
        }





        System.out.println("La la la");
        if(null == loc.getPickString()||loc.getPickString().length()==0){
            System.out.println("Well it appears we have a nullis pickString");
         pickString = newLocationUtilities.getPickString(getParents(),name);
            newLocationUtilities.updatePickStrings(sess,id);
        }else{
            pickString = loc.getPickString();
        }
           mobile =      newLocationUtilities.isMobile(id,sess);
        System.out.println("getting sorg");
        if(null==loc.getSortString()||loc.getSortString().length()==0){
           System.out.println("Zero");
        sortString = newLocationUtilities.getSortString(getParents(),name,mobile);
        }else{
            System.out.println("others");
            sortString = loc.getSortString();
        }
        System.out.println("Getting formated");
        if(null==loc.getFormatedPickString()||loc.getFormatedPickString().length()==0){
        formatedPickString = newLocationUtilities.getFormatedPickString(pickString,mobile);
        }else{
            formatedPickString = loc.getFormatedPickString();
        }
        ts1.print("Done locinfobean");
    }

    public String getParentString(){
        StringBuffer s = new StringBuffer();
             try{
               if ( parents.size()>0){
                   Iterator it = parents.values().iterator();
                  while(it.hasNext()){
                      s.append(it.next());
                      if(it.hasNext()) s.append(", ");
                  }
               }
             }catch(Exception e){
                 e.printStackTrace();
             }

        return s.toString();
    }
    public Map<String, String> getAllowedChildren() throws Exception{
        if(null==allowedChildren){
            allowedChildren = new TreeMap<String,String>();
            locationInfoLookupUtil.loadAllowedChildren(HibernateSession.currentSession(),allowedChildren,locationType+"");
        }
        return allowedChildren;
    }

    public void setAllowedChildren(Map<String, String> allowedChildren) {
        this.allowedChildren = allowedChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> getParents() {
        if(null==parents){
            try{
                System.out.println("We need the parents for some reason or another");
            parents =  newLocationUtilities.getParentMapForId(HibernateSession.currentSession(),id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return parents;
    }

    public void setParents(Map<String, String> parents) {
        this.parents = parents;
    }

    public List<locAttributeBean> getLocationAttributes() throws Exception{
        if(null==locationAttributes){
            loadTheAttributes();
        }
        return locationAttributes;
    }

    public void setLocationAttributes(List<locAttributeBean> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    public List<locAttributeBean> getInventoryAttributes() throws Exception {
        if(null==inventoryAttributes){
            loadTheAttributes();
        }

        return inventoryAttributes;
    }

    public void setInventoryAttributes(List<locAttributeBean> inventoryAttributes) {
        this.inventoryAttributes = inventoryAttributes;
    }

    public boolean getHasInventory() {
        return hasInventory;
    }

    public void setHasInventory(boolean hasInventory) {
        this.hasInventory = hasInventory;
    }

    public List getInventory() {
        return inventory;
    }

    public void setInventory(List inventory) {
        this.inventory = inventory;
    }

    public Map<String, String> getChildren() throws Exception{
        if(null == children){


            children = new TreeMap<String,String>();
            locationManagementUtils.getChildrenMapForParent(HibernateSession.currentSession(),children,""+id);


        }

        return children;
    }

    public void setChildren(Map<String, String> children) {
        this.children = children;
    }

    public boolean getHasDirectInventory() throws Exception {
        newLocationUtilities.locationHasDirectInventory(HibernateSession.currentSession(),""+id);
        return hasDirectInventory;
    }

    public void setHasDirectInventory(boolean hasDirectInventory) {
        this.hasDirectInventory = hasDirectInventory;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public String getPickString() {
        return pickString;
    }

    public void setPickString(String pickString) {
        this.pickString = pickString;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public List<primaryLocationBean> getInventoryPrimaryLocationList() {
        if (null == inventoryPrimaryLocationList || inventoryPrimaryLocationList.size()==0){
            try{
            inventoryPrimaryLocationList = locationInfoLookupUtil.loadLocationListWithPrimaryLocations(HibernateSession.currentSession(),"//"+id);
            }catch (Exception e){
                System.out.println("Weel it failed iguess");
            }
        }
        return inventoryPrimaryLocationList;
    }

    public void setInventoryPrimaryLocationList(List<primaryLocationBean> inventoryPrimaryLocationList) {
        this.inventoryPrimaryLocationList = inventoryPrimaryLocationList;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public String getFormatedPickString() {
        return formatedPickString;
    }

    public void setFormatedPickString(String formatedPickString) {
        this.formatedPickString = formatedPickString;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    private void loadTheAttributes() throws Exception{
        System.out.println("loading the attributes");
        locationAttributes = new ArrayList<locAttributeBean>();
        inventoryAttributes = new ArrayList<locAttributeBean>();
        locationInfoLookupUtil.loadAttributes(HibernateSession.currentSession(),locationAttributes,inventoryAttributes, id+"");
    }
}
