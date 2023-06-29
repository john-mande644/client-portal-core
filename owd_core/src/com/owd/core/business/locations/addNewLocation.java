package com.owd.core.business.locations;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;


public class addNewLocation {
private final static Logger log =  LogManager.getLogger();
 private static String insertParentAttributesSql = "insert into w_location_attribute_values (ix_node, att_id, value, catagory) select :locId, att_id, value, catagory from w_location_attribute_values where ix_node = :parentId and catagory = 1";
 private static String checkForParentAttributesSql = "select * from w_location_attribute_values where ix_node = :parentId and catagory = 1";
  private static String insertDefaultAttributesSql = "insert into w_location_attribute_values (ix_node, att_id, value, catagory) select :locId, att_id, default_value, catagory from w_location_attributes where catagory = 1";
    
 


    /**
     * @param sess HibernateSesion to use
     * @param parentId Id of Location to add the new location to
     * @param locName  Name of the new location to be created
     * @param newLocType Location Type of the new location to be created
     * @param user User id that is adding location
     * @param ipAddress the ip Address of the user adding the location
     * @return Integer returns the id of the newly created record
     *  @throws Exception  errors
     */
    public static Integer addLocation(Session sess,String parentId, String locName, String newLocType,String user,String ipAddress) throws Exception{
        Integer id = 0;

       if (locationUtilities.isValidParent(sess,newLocType,Integer.parseInt(parentId))){

           if (locationUtilities.isUniqueName(sess,parentId,locName,newLocType)){
               WLocation loc = new WLocation();
               loc.setLocationName(locName);
               loc.setLocationType(Integer.parseInt(newLocType));
               loc.setParentId(Integer.parseInt(parentId));
               loc.setPickPriority("0");
               sess.save(loc);
                id = loc.getId();
               if (insertAttributes(sess, id+"",parentId)){

               } else{
                   throw new Exception("Unable to insert attributes");
               }
               if(! wmsEvents.addLocationEvent(sess,id+"",locName,parentId,user,wmsEvents.appWeb,ipAddress)){
                  log.debug("Unable to add event record");
               }



             

            //todo fill out attributes on new location

           } else{
               throw new Exception("This is not a unique name, Names must be unique when creating a new location.");
           }

       } else {
           throw new Exception("Not a valid location type for current location");
       }

        return id;
    }

    /**
     *
     * @param sess    HibernateSession to use for Query
     * @param locId   Id of new location to add Attributes to
     * @param parentId Id of parent location of the locId
     * @return    boolean returns true if the attribues get inserted
     * @throws Exception   throw exception on unique key contraint
     */
    private static boolean insertAttributes(Session sess, String locId, String parentId) throws Exception{
        boolean success = false;
      try{
        Query q = sess.createSQLQuery(checkForParentAttributesSql);
                 q.setParameter("parentId",parentId);
                 List result = q.list();
                if (result.size()>0){
                    log.debug("copy parent attributes");
                    Query insert = sess.createSQLQuery(insertParentAttributesSql);
                    insert.setParameter("locId",locId);
                    insert.setParameter("parentId",parentId);
                    int rows = insert.executeUpdate();
                    if (rows > 0){
                        success = true;
                    }

                }else{
                    log.debug("copy default attributes");
                    Query insert = sess.createSQLQuery(insertDefaultAttributesSql);
                    insert.setParameter("locId",locId);

                    int rows = insert.executeUpdate();
                    if (rows > 0){
                        success = true;
                    }

                }
      }catch (ConstraintViolationException sq){
         if (sq.getCause().getMessage().contains("oneAttPerId")) throw new Exception("Cannot insert duplicate attribues");
         return false;
      }

        return success;
    }

    public static void main(String[] args){
        try{
           Session sess = HibernateSession.currentSession();
             log.debug(addLocation(sess,"36442","Rack 1","8","Danny","172.16.66.255"));


               // log.debug(insertAttributes(sess,"7","10"));
              com.owd.hibernate.HibUtils.commit(sess);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
