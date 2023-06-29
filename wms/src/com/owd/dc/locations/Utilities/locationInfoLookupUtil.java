package com.owd.dc.locations.Utilities;

import com.owd.dc.locations.locAttributeBean;

import com.owd.dc.locations.newLocationUtilities;
import com.owd.dc.locations.primaryLocationBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 17, 2009
 * Time: 4:28:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationInfoLookupUtil {
        private static String attributesSql = "SELECT      w_location_attributes.att_id, w_location_attributes.name, w_location_attributes.catagory, ISNULL(w_location_attribute_values.[value], '') AS [values]\n" +
                "FROM         w_location_attributes LEFT OUTER JOIN\n" +
                "                      w_location_attribute_values ON w_location_attributes.att_id = w_location_attribute_values.att_id AND w_location_attribute_values.ix_Node = :ixNode\n" +
                "ORDER BY w_location_attributes.name";

   public static String getAllowedChildrensql = "SELECT     w_location_type.ixLocType, w_location_type.description\n" +
           "FROM         w_location_type INNER JOIN\n" +
           "                      w_location_allowed_parents ON w_location_type.ixLocType = w_location_allowed_parents.ixLocType\n" +
           "WHERE     (w_location_allowed_parents.Allowed_Parent = :locType)";

    public static void loadAllowedChildren(Session sess, Map<String,String> children, String locType) throws Exception{

       Query q = sess.createSQLQuery(getAllowedChildrensql);
        q.setParameter("locType",locType);
        List result = q.list();
          if (result.size() > 0){
        for(Object row:result){
            Object[] data = (Object[]) row;
                //data should be  loc type, description
            children.put(data[0].toString(),data[1].toString());

        }

          }


    }

    public static void loadAttributes(Session sess, List<locAttributeBean> locAttribs, List<locAttributeBean> invAttribs, String nodeId) throws Exception{

       // Logger log = Logger.getLogger();

        try{
         // log.debug("Starting load of all attributes");

          Query q = sess.createSQLQuery(attributesSql);
            q.setParameter("ixNode",nodeId);
           List results = q.list();
                   System.out.println(results.size());
            if (results.size()>0){
                    for(Object row:results){
                        Object[] data = (Object[]) row;
                     // should be attId, name, catagory, value
                        locAttributeBean b = new locAttributeBean(data[0].toString(),data[3].toString(),data[1].toString());
                        if (data[2].toString().equals("1")){
                            locAttribs.add(b);
                          //  log.debug("added to locAttribs");
                        }   else{
                            invAttribs.add(b);
                          //  log.debug("aded to invAttribs");
                        }


                    }
            } else{

              //  log.warn("didn't get anything back for loading %s",nodeId);
                throw new Exception("no results");
            }



        }catch(Exception e){
            e.printStackTrace();
         //   log.debug(e.getMessage());
          throw new Exception("error loading attributes");
        }




    }
    public static String getLocationPickString(Session sess, String locId) throws Exception{
        Integer id;
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
        loc = (WLocation) sess.get(WLocation.class,id);

        if(loc==null)
        {
            throw new Exception("Location id value not found : "+locId);
        }
        String name = loc.getLocationName();

       return newLocationUtilities.getPickString(newLocationUtilities.getParentMapForId(sess, locId),name);
    }
    public static List<primaryLocationBean> loadLocationListWithPrimaryLocations(Session sess, String locId){
        List<primaryLocationBean> locations = new ArrayList<primaryLocationBean>();
                 try{
                  String sql = "select inventory_fkey, primary_pick from owd_inventory_locations where location = :location";
                  Query q = sess.createSQLQuery(sql);
                     q.setString("location",locId);
                     List results = q.list();
                     System.out.println(results.size());
                     for (Object row : results){
                         Object[] data = (Object[]) row;
                         primaryLocationBean plb = new primaryLocationBean();
                         plb.setInventoryId(data[0].toString());

                         OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class,Integer.decode(plb.getInventoryId()));
                         plb.setInventoryNum(inv.getInventoryNum());

                         locations.add(plb);
                     }


                 } catch(Exception e){
                     e.printStackTrace();
                 }

        return locations;
    }

    public static void main(String args[]){
        try{
            List<primaryLocationBean> ll = loadLocationListWithPrimaryLocations(HibernateSession.currentSession(),"//36522");
            System.out.println(ll.size());

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
