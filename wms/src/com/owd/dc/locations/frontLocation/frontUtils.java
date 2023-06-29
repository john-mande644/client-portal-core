package com.owd.dc.locations.frontLocation;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 22, 2010
 * Time: 4:41:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class frontUtils {

    public  static frontLocationBean loadSkuInfo(Session sess, String invId) throws Exception{
        System.out.println(invId);
      frontLocationBean b = new frontLocationBean();

        OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(invId));

        b.setSku( inv.getInventoryNum());
        b.setDescription(inv.getDescription());
        b.setFrontLocation(inv.getFrontLocation());
        String sql;
        sql = "select location,max(assign_date) as 'assign_date' from owd_inventory (NOLOCK) left outer join owd_inventory_locations (NOLOCK) on inventory_id=inventory_fkey and left(location,1)='/' where inventory_id = :invId group by location";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("invId",invId);
        List results = q.list();

        List<String> locs = new ArrayList<String>();
        for (Object row:results){
             Object[] data = (Object[])row;
            locs.add((String)data[0]);

        }

        b.setOtherLocations(locs);
        b.setFrontInList(b.getOtherLocations().contains(b.getFrontLocation()));
         if (b.isFrontInList()){
             b.getOtherLocations().remove(b.getFrontLocation());
             

        }

      return b;
        
    }

    public static void setNewFront(Session sess, String invFkey, String location) throws Exception{
          String sql = "delete from owd_inventory_locations where  inventory_fkey = :invFkey and location = :location";

         try{
           OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(invFkey));
           inv.setFrontLocation(location);
           Query q = sess.createSQLQuery(sql);
             q.setParameter("invFkey",invFkey);
             q.setParameter("location",location);
             int results = q.executeUpdate();
             System.out.println(results);
             /*if (results<1) {
                HibUtils.rollback(sess);
                 throw new Exception("Unable to update location data, please try again");

             }*/
             HibUtils.commit(sess);



         } catch (Exception e){
             e.printStackTrace();
             throw new Exception(e.getMessage());
         }
    }
    public static void removeLocation(Session sess, String invFkey, String location) throws Exception{

           System.out.println("removing location because it is bad lala");
                    String sql = "delete from owd_inventory_locations where location = :location "
                            + "and inventory_fkey = :invFkey";

        try{

            Query q = sess.createSQLQuery(sql);
             q.setParameter("invFkey",invFkey);
             q.setParameter("location",location);
             int results = q.executeUpdate();
            HibUtils.commit(sess);
            
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
