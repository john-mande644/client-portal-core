package com.owd.core.business.locations;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class removeLocation {
private final static Logger log =  LogManager.getLogger();

    private static String getChildLocationssql = "exec dbo.get_ChildLocations :parent";
    private static String deleteAttributesql = "delete from w_location_attribute_values where ix_Node = :id";
    private static String deleteLocationsql = "delete from w_location where ixNode = :id";
    private static String getInventoryForLocationsql = "exec dbo.inventoryInLocation :id";
    private static String deleteInventorysql = "delete from w_location_inventory where ixNode = :id and ixInventory = :invId";
    /**
     * 
     * @param parentId  location Id to check if it and it's children can be removed
     * @return boolean Returns true if location and children have no invnetory Records Greater than Zero
     */
    public static boolean isRemovable(String parentId){
        boolean removable = false;
            try{
             Session sess = HibernateSession.currentSession();
                if (!locationUtilities.hasInventoryGreaterThanZero(sess,parentId)){
                    removable = true;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        return removable;

    }
   private static String tr(Object o){
       return o.toString().trim();
   }

    public static boolean remove(Session sess,String locId, String user, String app, String ipAddress) throws Exception{
        boolean removed = false;
          if (isRemovable(locId)){
              //remove all zero inventory
             Query inv = sess.createSQLQuery(getInventoryForLocationsql);
              inv.setParameter("id",locId);
              List invs = inv.list();
              for(Object row : invs.toArray()){

                  Object[] row2 = (Object[])row;
                  if(!tr(row2[2]).equals("0")){
                   throw new Exception("Can not delete "+tr(row2[0])+", it still has inventory assiged to Inventory Id: "+tr(row2[3]));   
                  }
                  if(!deleteInventoryLocationRecord(sess,tr(row2[0]),tr(row2[1]),tr(row2[3]),tr(row2[2]),user,app,ipAddress)){
                      throw new Exception("Unable to delete inventory for "+locId);
                  }



              }



               //get list of all locations for parent, this list will include the parentId
              Query locList =  sess.createSQLQuery(getChildLocationssql);
              locList.setParameter("parent",locId);
              List children = locList.list();

              for(Object row : children.toArray()){
                Object[] row2 = (Object[])row;
                System.out.print(tr(row2[0]));
                System.out.print(row2[1]);
                log.debug(row2[2]);
              if (!deleteLocationRecord(sess,tr(row2[0]),tr(row2[1]),tr(row2[2]),user,app,ipAddress)){
                  throw new Exception("Unable to delete "+tr(row2[1]));
              }
              }
             removed = true;


          } else {
              throw new Exception("This location is unable to be removed because it still has active Inventory");
          }


        return   removed;
    }

    private static boolean deleteInventoryLocationRecord(Session sess,String locId,String locName, String invId, String qty, String user, String app, String ipAddress) throws Exception{
        boolean success = false;
         Query inv = sess.createSQLQuery(deleteInventorysql);
          inv.setParameter("id",locId);
        inv.setParameter("invId",invId);
         int i = inv.executeUpdate();

        if (i >0){
            success = wmsEvents.removeInventoryFromLocationEvent(sess, locId, locName,  invId,  qty,  user,  app, ipAddress);
        }



        return success;


    }

    /**
     *
     * @param sess    HibernateSession to use
     * @param locId   Id of location to remove
     * @param locName Name of location to remove
     * @param parentId Parent id of location to be removed
     * @param user     User id of person deleting location
     * @param app      What application made the request
     * @param ipAddress User ip address
     * @return boolean returns true if record was deleted
     * @throws Exception  errs
     */
    private static boolean deleteLocationRecord(Session sess, String locId, String locName, String parentId, String user, String app, String ipAddress) throws Exception{
        boolean success = false;
        Query attrib = sess.createSQLQuery(deleteAttributesql);
        attrib.setParameter("id",locId);

        attrib.executeUpdate();
        Query loc = sess.createSQLQuery(deleteLocationsql);
        loc.setParameter("id",locId);
        int i = loc.executeUpdate();

        if (i>0){
            if(wmsEvents.removeLocationEvent(sess,locId,locName,parentId,user,app,ipAddress)){
                success = true;
            }
            
        }

        return success;


    }

    public static void main(String[] args){
        try{
             Session sess = HibernateSession.currentSession();
          if(remove(sess,"36439","Danny","test","172.16.66.255")){
              
              com.owd.hibernate.HibUtils.commit(sess);
          }


        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
