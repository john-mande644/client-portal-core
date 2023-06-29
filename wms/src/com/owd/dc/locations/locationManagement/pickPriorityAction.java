package com.owd.dc.locations.locationManagement;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/11/14
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickPriorityAction extends ActionSupport{
    private String locationScan;
    private String priority;
    private static final Map<String,String> priorityText;
    static{
        Map<String,String> map = new TreeMap<String, String>();
        map.put("1","Primary");
        map.put("2","Secondary");
        map.put("3","Reset To Default");
        priorityText = map;
    }

   public String pickPrioritySet(){
       System.out.println("This is the priority :"+priority);

       return "success";
   }

   public String pickPriorityLocationScan(){
       if(!locationScan.startsWith("//")){
           addActionError("Please Scan Valid Location");
           return "success";
       }
       try{
        pickPriorityUtils.updatePriorityForParentAndChildren(locationScan,priority);
           WLocation wloc = (WLocation) HibernateSession.currentSession().load(WLocation.class,Integer.parseInt(locationScan.replaceAll("//","")));

           addActionMessage("Set "+priorityText.get(priority)+ " pick priority for "+wloc.getPickString()+ " and child locations.");
       }catch(Exception e){
           e.printStackTrace();
           addActionError(e.getMessage());

       }
                     System.out.println(locationScan);
       return"success";
   }
    public String getLocationScan() {
        return locationScan;
    }

    public void setLocationScan(String locationScan) {
        this.locationScan = locationScan;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public static Map<String, String> getPriorityText() {
        return priorityText;
    }



}
