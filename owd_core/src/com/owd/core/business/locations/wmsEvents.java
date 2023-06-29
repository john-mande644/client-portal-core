package com.owd.core.business.locations;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Sep 26, 2008
 * Time: 1:31:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class wmsEvents {
private final static Logger log =  LogManager.getLogger();
    public static String catagoryInventory = "inventory";
    public static String catagoryLocation = "location";
    public static String catagoryApplication = "application";
    public static String actionAdd = "add";
    public static String actionRemove = "remove";
    public static String actionChangeAttribune ="change attribune";
    public static String actionMove = "Move";
    public static String appPick = "pick";
    public static String appPutAway ="put away";
    public static String appReceive = "receive";
    public static String appInventoryCount ="inventory count";
        public static String appWeb = "web";
    public static String appSystem ="system";


    private static String insertWmsEventSql = "insert into dbo.wms_events ([user], catagory, [action], application, location_id, parent_id, old_parent_id, inventory_id, qty, Description, ipAddress) values (:user, :catagory, :action, :application, :locationid, :parentid, :oldparentid, :inventoryid, :qty, :Description, :ipAddress)";


    /**
     *
      * @param sess    HibernateSession to use to insert event
     *
     * @param user  Name of person Doing event
     * @param catagory  The catagory of the event, should use static catagory type in class
     * @param action    The action of the event, should use static action type in class
     * @param application   The application of the event, should use application  type in class
     * @param location_id  The id of location used in event
     * @param parent_id    the new parent id of location if event was a move
     * @param old_parent_id the old parent id of a location if event was a move
     * @param inventory_id  Inventory Id 
     * @param qty    Quanity of the inventory for the event
     * @param Description   A description of the above and any notes
     * @param ipAddress The ip of the user making the request
     * @return boolean returns true if the insert was a success
     */
    private static boolean insertWmsEvent(Session sess, String user, String catagory, String action, String application, String location_id, String parent_id, String old_parent_id, String inventory_id, String qty, String Description, String ipAddress){
        boolean success = false;
       try {
           Query q = sess.createSQLQuery(insertWmsEventSql);
         //  log.debug(q.getQueryString());
           q.setParameter("user",user);
           q.setParameter("catagory",catagory);
           q.setParameter("action",action);
           q.setParameter("application",application);
           q.setParameter("locationid",location_id);
           q.setParameter("parentid",parent_id);
           q.setParameter("oldparentid",old_parent_id);
           q.setParameter("inventoryid",inventory_id);
           q.setParameter("qty", qty);
           q.setParameter("Description",Description);
           q.setParameter("ipAddress",ipAddress);
               //    log.debug("done setting parameters");
            int results = q.executeUpdate();
         //  log.debug("ho ho hao");
           if (results >0){
               success = true;
           }


       } catch (Exception e){
           log.debug("Error inserting wmsEvent");
           e.printStackTrace();

       }

                          
        return success;


    }


/**
 *
 * @param sess     HibernateSession to use for insert
 * @param locId    Id of the new location added
 * @param locName  Name of the new location added
 * @param parentId Parent Id of the new location
 * @param user     Id of user who added the location
 * @param app Application add event came from
 * @param ipAddress User ip address
 * @return   boolean returns true if insert worked
 */
  public static boolean addLocationEvent(Session sess, String locId, String locName, String parentId, String user, String app, String ipAddress){
    boolean success = false;
      try{
          String desc = "Added location: '"+locName+"' to parent: "+parentId;

     return insertWmsEvent(sess,user,catagoryLocation,actionAdd,app,locId,parentId,"","","",desc,ipAddress);

      }catch(Exception e){
          e.printStackTrace();

      }


    return success;


}
     public static boolean removeInventoryFromLocationEvent(Session sess,String locId,String locName, String invId, String qty, String user, String app, String ipAddress){
         boolean success = false;
           try{
              String desc = "Removed '"+invId+"' from location: "+locId+" ; Location Name: "+locName;
               return insertWmsEvent(sess,user,catagoryInventory,actionRemove,app,locId,"","",invId,qty,desc,ipAddress);
           } catch (Exception e){
               e.printStackTrace();

           }
         return success;
     }

     public static boolean removeLocationEvent(Session sess, String locId, String locName, String parentId, String user, String app, String ipAddress){
        boolean success =false;
        try{
            String desc = "Removed: '"+locName+"' from parent: "+parentId;

                return insertWmsEvent(sess,user,catagoryLocation,actionRemove,app,locId,parentId,"","","",desc,ipAddress);


        } catch (Exception e){

            e.printStackTrace();
        }


        return success;
    }

    public static void main(String[] args){
        try{
            Session sess =  HibernateSession.currentSession();
        //Query q = sess.createSQLQuery("select * from wms_events");
       //     log.debug(q.list().size());
          // boolean t = addLocationEvent(sess,"1234","Test loc A","1234","Danny",appWeb,"172.16.66.255");
               boolean t = removeLocationEvent(sess,"1234","Test loc A","1234","Danny",appWeb,"172.16.66.255");
         //  boolean t = insertWmsEvent(sess,"Danny","testing","Testing","StillTesting","","","","","","Just an insert test");
       if (t) HibUtils.commit(sess);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    //todo pick

    //todo move
}
