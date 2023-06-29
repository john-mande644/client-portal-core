package com.owd.dc.locations.locationManagement;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.locations.locationInfoBean;
import com.owd.dc.locations.locationUtilities;
import com.owd.dc.locations.newLocationUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 3/12/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationManagementAction extends ActionSupport {
     private locationInfoBean lib;
     private String location;
    private String locAction;
    private String inventoryId;
    private String newParentLocation;

    public String moveLocationComplete(){
        try{
            System.out.println("We are going to comlete the mvoe");
            Session sess = HibernateSession.currentSession();
            if(!newParentLocation.startsWith("//")) throw new Exception("Not a valid location");
             lib = new locationInfoBean(location, sess);
           locationInfoBean parent = new locationInfoBean(newParentLocation,sess);
            boolean good = newLocationUtilities.moveLocation(sess,lib,parent);

            if (good){
                HibUtils.commit(sess);
                newLocationUtilities.updatePickStrings(sess,Integer.parseInt(location.replace("//","")));
                addActionMessage("Moved "+lib.getName()+" to " +parent.getName());
            }else{
                throw new Exception("Move failed, try again or contact IT");
            }
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }



                 return    execute();
    }
     public String moveLocation() {
                 try{
                     if(newLocationUtilities.isMobile(location.replace("//",""),HibernateSession.currentSession())){
                         lib = new locationInfoBean(location, HibernateSession.currentSession());

                     }   else{
                          throw new Exception("This location is not re-locatable with the handheld app");
                     }
                 } catch (Exception e){
                     addActionError(e.getMessage());
                     e.printStackTrace();
                     return "error";
                 }


         return "success";
     }
     public String clearInventory(){
               String sql = "delete from owd_inventory_locations  where location = :location ";
                try{
                     Query q = HibernateSession.currentSession().createSQLQuery(sql);
              q.setString("location",location);
              int results = q.executeUpdate();
              if (results >0){
                  HibUtils.commit(HibernateSession.currentSession());
                  addActionMessage("Successfully Cleared All inventory from  Location");
                    lib = new locationInfoBean(location, HibernateSession.currentSession());
              } else{
                  throw new Exception("Clearing of location did not work");
              }

                  } catch(Exception e){
              e.printStackTrace();
              addActionError("unable to remove location");
                    try{
               lib = new locationInfoBean(location, HibernateSession.currentSession());
                    }catch(Exception ex){

                    }
          }
        return "success";
    }

    public String removeInventory(){
               String sql = "delete from owd_inventory_locations  where location = :location and inventory_fkey = :invFkey";
                try{
                     Query q = HibernateSession.currentSession().createSQLQuery(sql);
              q.setString("location",location);
              q.setString("invFkey",inventoryId);
              int results = q.executeUpdate();
              if (results ==1){
                  HibUtils.commit(HibernateSession.currentSession());
                  addActionMessage("Successfully removed inventory from  Location");
                    lib = new locationInfoBean(location, HibernateSession.currentSession());
              }
                  } catch(Exception e){
              e.printStackTrace();
              addActionError("unable to remove location");

          }
        return "success";
    }

    public String setPrimary(){
           String sql = "update owd_inventory_locations set primary_pick = 1 where location = :location and inventory_fkey = :invFkey";
          try{
              Query q = HibernateSession.currentSession().createSQLQuery(sql);
              q.setString("location",location);
              q.setString("invFkey",inventoryId);
              int results = q.executeUpdate();
              if (results ==1){
                  HibUtils.commit(HibernateSession.currentSession());
                  addActionMessage("Successfully set Primary Pick Location");
                    lib = new locationInfoBean(location, HibernateSession.currentSession());
              }
          } catch(Exception e){
              e.printStackTrace();
              addActionError("unable to reset primary location please contact IT");

          }

        return "success";
    }
    public String execute(){
         if (location.length()>2){
           if (!location.startsWith("//")){
             if(location.startsWith("/")){
                 addActionError("We do not support this type of location yet. Please use the front location utility");

           }  else{
                 addActionError("Please use a valid location");
             }
             return "error";
         }
             try{
         lib = new locationInfoBean(location, HibernateSession.currentSession());

             }catch (Exception e){
                 e.printStackTrace();
                 addActionError("Ubable to load necassary info, try again or contact IT");
             }
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
}
