package com.owd.dc.inventorycount;

import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.*;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WInvCounts;
import com.owd.hibernate.generated.WInvLocations;
import com.owd.hibernate.generated.WInvRequest;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 28, 2005
 * Time: 3:43:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class wInventoryUtilities {

    public static void main(String[] args){
             try {
                 System.out.println(isInventoryAdmin(51,HibernateSession.currentSession()));
             } catch (Exception e){
                 
             }

    }
    public static String getNameFromCookies(HttpServletRequest request){
      String s = "";
        for(Cookie c : request.getCookies()){
            if ("tcname".equals(c.getName())){
               s =(c.getValue());
            }
        }
        return s;
    }
    public static int getIdFromCookies(HttpServletRequest request){
      int id = 0;
       for(Cookie c : request.getCookies()){
               if ("tcid".equals(c.getName())){
                    id = Integer.parseInt(c.getValue());
               }
       }
        return id;

    }
    public static boolean isInventoryAdmin(int id , Session sess){
        boolean admin = false;
        try{

                   System.out.println("Doing lookup for Id" + id);
                  String sql = "select inventory_Admin from handheld_setup where loginId = :id";
                   Query q = sess.createSQLQuery(sql);
                   q.setInteger("id",id);
                   List results = q.list();
                   System.out.println(results.size());
                   if (results.size()>0){
                       Object data = results.get(0);
                       System.out.println(data);
                       System.out.println(data.toString());
                       if (  Integer.parseInt(data.toString()) == 1){

                          admin = true;
                       }
                   }







        } catch (Exception e){

        e.printStackTrace();
        }



        return admin;

    }
    public static String getFkeyForCountLocation(Session sess, String location, String countId, String inventoryId)throws Exception{
           System.out.printf("Doing lookup for location: %s and Count id: %s",location,countId);
        String sql = " select id from w_inv_locations where location = :location and inv_request_fkey = :countId and inventory_id = :invId";
        Query q = sess.createSQLQuery(sql);
        q.setString("location",location);
        q.setString("countId",countId);
        q.setString("invId",inventoryId);
        List results = q.list();
        if (results.size()>0){
            Object row = results.get(0);
            return row.toString();

        } else{
System.out.println("Going to do a new w_inv_location because we don't have a valid one");
          WInvRequest wi = (WInvRequest)sess.get(WInvRequest.class, Integer.parseInt(countId));


            return  addWcount(Integer.parseInt(inventoryId),wi,location,3,0,1).toString();
            //throw new Exception("Unable to load get fkey for "+location);
        }


        
    }

    //adds new w_inv_location record to database
    public static Integer addWcount(int Sku, WInvRequest wInv, String location, Integer originalScan, Integer remove, Integer done) throws Exception {

        WInvLocations wloc = new WInvLocations();
        if (Sku != 0) {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(Sku));
            wloc.setInventoryId(new Integer(Sku));
            wloc.setInventoryNum(inv.getInventoryNum());
        }
        wloc.setLocation(location);
         if (location.startsWith("//")){
            locationInfoBean lib = new locationInfoBean(location,HibernateSession.currentSession());
            wloc.setLocationDisplay(lib.getPickString());
        }else{
            wloc.setLocationDisplay(location);
        }
        wloc.setWinvreques(wInv);
        wloc.setDone(done);
        wloc.setRemove(remove);
        wloc.setOriginalScan(originalScan);
        HibernateSession.currentSession().save(wloc);
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        return wloc.getId();
    }

    public static String addWcountWithId(int Sku, WInvRequest wInv, String location, Integer originalScan) throws Exception {
        WInvLocations wloc = new WInvLocations();
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(Sku));
        wloc.setInventoryNum(inv.getInventoryNum());
        wloc.setInventoryId(new Integer(Sku));
        wloc.setLocation(location);
        if (location.startsWith("//")){
            locationInfoBean lib = new locationInfoBean(location,HibernateSession.currentSession());
            wloc.setLocationDisplay(lib.getPickString());
        }else{
            wloc.setLocationDisplay(location);
        }

        wloc.setWinvreques(wInv);
        wloc.setRemove(new Integer(0));
        wloc.setDone(new Integer(0));
        wloc.setOriginalScan(originalScan);
        HibernateSession.currentSession().save(wloc);
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        return wloc.getId().toString();
    }

    //checks sku for all locations and adds records to w_inv_location database when apropriate
    public static void addAdditionalLocations(int Sku, WInvRequest wInv, String location) throws Exception {
        String query = "SELECT location\n" +
                "FROM owd_inventory_locations (NOLOCK) \n" +
                "WHERE (inventory_fkey = " + Sku + ") AND (location <> 'UNKNOWN') AND (location <> '" + location + "')";

        ResultSet rs = HibernateSession.getResultSet(query);
        List locations = new ArrayList();
        while (rs.next()) {
            locations.add(rs.getString(1));
            System.out.println("added " + rs.getString(1));
        }

        for (int i = 0; i < locations.size(); i++) {
            if (!checkLocationAlreadyAdded(Sku, wInv.getId(), locations.get(i).toString())) {
                System.out.println("no match");
               String master= checkMasterLocation(wInv.getId(), locations.get(i).toString());
                if(!master.equals("CLOSED")){
                addWcount(Sku, wInv, locations.get(i).toString(), new Integer(1),new Integer(0), new Integer(0));
                }else{
                addWcount(Sku, wInv, locations.get(i).toString(), new Integer(3), new Integer(1), new Integer(1));
                }
            } else {
                System.out.println("Match, will not create");
            }
        }
    }

    public static String  checkMasterLocation(Integer id, String loc)throws Exception{
          String result = new String();
             String query = "SELECT     id, done\n" +
                     "FROM         w_inv_locations (NOLOCK) \n" +
                     "WHERE     (inv_request_fkey = "+id+") AND (location = '"+loc+"') AND (inventory_id IS NULL)";
        ResultSet rs = HibernateSession.getResultSet(query);
        if(rs.next()){
            System.out.println("rs.next");
            if(rs.getInt(2)==1) {
                result="CLOSED";
            } else{
                result="OPEN";
            }
        } else{
            WInvRequest wReq = (WInvRequest)HibernateSession.currentSession().load(WInvRequest.class, id);
            addWcount(0, wReq, loc, new Integer(1), new Integer(0), new Integer(0));
            result="CREATED";
        }
       System.out.println(result);
          return result;
    }


    public static boolean checkLocationAlreadyAdded(int Sku, Integer id, String location) throws Exception {
        boolean present = false;
        String query = null;
        if (Sku == 0) {
            query = "SELECT     location\n" +
                    "FROM         w_inv_locations (NOLOCK) \n" +
                    "WHERE     (inv_request_fkey = " + id + ") AND (location = '" + location + "')\n" +
                    "GROUP BY location";
        } else {
            query = "SELECT   location\n" +
                    "FROM         w_inv_locations (NOLOCK) \n" +
                    "WHERE     (inv_request_fkey = " + id + ") AND (inventory_id = " + Sku + ")";
        }
        ResultSet rs = HibernateSession.getResultSet(query);
        if (rs.next()) {
            if (rs.getString(1).equals(location)) {
                present = true;
            }
        }
        return present;
    }

    public static List getOpenRequests() throws Exception {
        List requests = new ArrayList();
        String query = "SELECT     w_inv_request.id, owd_client.company_name, w_inv_request.description\n" +
                "FROM         w_inv_request  (NOLOCK) INNER JOIN\n" +
                "                      owd_client  (NOLOCK) ON w_inv_request.client_fkey = owd_client.client_id\n" +
                "WHERE     (w_inv_request.done = 0)";
        ResultSet rs = HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()) {
            wRequestBean wb = new wRequestBean();
            System.out.println(rs.getString(1));
            wb.setId(rs.getString(1));
            wb.setClient(rs.getString(2));
            wb.setDescription(rs.getString(3));
            requests.add(i, wb);
            i++;
        }
        System.out.println(requests.size());
        return requests;

    }

    public static ResultSet getRequestById(String id) throws Exception {
        String query = "SELECT     w_inv_request.id, owd_client.company_name, w_inv_request.done\n" +
                "FROM         w_inv_request  (NOLOCK) INNER JOIN\n" +
                "                      owd_client  (NOLOCK) ON w_inv_request.client_fkey = owd_client.client_id\n" +
                "WHERE     (w_inv_request.id = " + id + ")";
        ResultSet rs = HibernateSession.getResultSet(query);
        return rs;
    }

    public static List getLocationsForRequest(String id) throws Exception {
        List locations = new ArrayList();
        String query = "SELECT  top 25   locationDisplay\n" +
                "FROM         w_inv_locations (NOLOCK) \n" +
                "WHERE     (inv_request_fkey = " + id + ") and done = 0\n" +
                "GROUP BY locationDisplay\n" +
                "ORDER BY locationDisplay";
        ResultSet rs = HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()) {
            wRequestBean wb = new wRequestBean();
            wb.setLocation(rs.getString(1));
            locations.add(i, wb);
            i++;
        }
        return locations;
    }

    public static List getSkusInLocation(String location, int requestId) throws Exception {
        List skus = new ArrayList();
        String query = "SELECT     inventory_num \n" +
                "FROM         w_inv_locations (NOLOCK) \n" +
                "WHERE   (done = 0) and  (inv_request_fkey = " + requestId + ") AND (location = '" + location + "')";
        ResultSet rs = HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()) {

            skus.add(i,rs.getString(1));
            i++;
        }
        if (skus.size() == 0) {
            query = "select id from w_inv_locations  (NOLOCK) where location = '" + location + "'";
            rs = HibernateSession.getResultSet(query);
            if (rs.next()) {
                throw new Exception("Location Already Counted");
            }
        }
        if(skus.size()>0){
            System.out.println(">0");
            skus = getCountedSkusInLocation(location, requestId,skus);
            System.out.println(">0");
        }
        return skus;
    }
   public static List getCountedSkusInLocation(String location, int requestId, List skus)throws Exception{
       List s = new ArrayList();
       System.out.println(requestId+" :"+location);
       String query = "SELECT     inventory_num \n" +
                "FROM         w_inv_locations (NOLOCK) \n" +
                "WHERE   (done = 1) and  (inv_request_fkey = " + requestId + ") AND (location = '" + location + "') ";
       ResultSet rs = HibernateSession.getResultSet(query);
       List counted = new ArrayList();
       int i=0;
       while(rs.next()){
           System.out.println(rs.getString(1));
           counted.add(i,rs.getString(1));
           i++;
       }
       System.out.println("after while");

       if(skus.size()>=counted.size()){
             System.out.println("inrs");
           for(int w=0; w<skus.size();w++){
                wSkuBean ws = new wSkuBean();
           try{
               ws.setUncounted(skus.get(w).toString());
           }catch(Exception e){
               ws.setUncounted("");
           }
            try{
                ws.setCounted(counted.get(w).toString());
            }catch(Exception e){
                ws.setCounted("");
            }
            s.add(w,ws);
           }
       }

       if(skus.size()<counted.size()){
             System.out.println("inrs1234");
           for(int w=0; w<counted.size();w++){
                wSkuBean ws = new wSkuBean();
             //  System.out.println("doing stuff"+counted.get(w));
            ws.setCounted(counted.get(w).toString());

            try{
                 ws.setUncounted(skus.get(w).toString());
            }catch(Exception e){
                e.printStackTrace();
                ws.setUncounted("");
            }
             //  System.out.println(ws.getCounted() + ws.getUncounted() + w);

               s.add(w,ws);

           }
       }
       return s;
   }
    public static boolean verifyClientSku(int clientFkey, String inventoryId) throws Exception {
        boolean verified = false;

        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(inventoryId));
        System.out.println(inv.getOwdClient().getClientId() + ":" + clientFkey);
        if (inv.getOwdClient().getClientId().intValue() == clientFkey) {
           System.out.println("match");
            verified = true;
        }
        return verified;
    }

    public static String getInvLocationFkey(wCountDTO wcount) throws Exception {
        String id = null;
        String query = "SELECT     id, done\n" +
                "FROM         w_inv_locations (NOLOCK) \n" +
                "WHERE     (inv_request_fkey = " + wcount.getRequestId() + ") AND (location = '" +
                wcount.getLocation() + "') AND (inventory_id = " + wcount.getInventoryId() + ")";
        ResultSet rs = HibernateSession.getResultSet(query);
        if (rs.next()) {
            id = rs.getString(1);
        } else {
            WInvRequest wInv = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(wcount.getRequestId()));
            id = addWcountWithId(Integer.parseInt(wcount.getInventoryId()), wInv, wcount.getLocation(), new Integer(2));
         System.out.println("afterId");
            addAdditionalLocations(Integer.parseInt(wcount.getInventoryId()), wInv, wcount.getLocation());
        }
        return id;
    }

    public static String insertCount(wCountDTO wcount, String name) throws Exception {
        WInvLocations wloc = (WInvLocations) HibernateSession.currentSession().load(WInvLocations.class, new Integer(wcount.getInvLocFkey()));
        WInvCounts wICount = new WInvCounts();
        wICount.setInvLocFkey(wcount.getInvLocFkey());
        wICount.setByWho(name);
        wICount.setInventoryId(Integer.parseInt(wcount.getInventoryId()));
        wICount.setLocation(wcount.getLocation());
        wICount.setQuanity(Integer.parseInt(wcount.getQuanity()));
        wICount.setUPC(wcount.getUPC());
        wICount.setISBN(wcount.getISBN());
        HibernateSession.currentSession().save(wICount);
        HibernateSession.currentSession().flush();
        wloc.setDone(new Integer(1));
        HibernateSession.currentSession().update(wloc);
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        return wICount.getId().toString();
    }

    public static String verifyBarcode(wCountDTO wcount) throws Exception {
        String result = null;
        String bv = upcBarcodeUtilities.barcodeCheck(wcount.getBarcode());
        System.out.println(bv);
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wcount.getInventoryId()));
        System.out.println("got inv");
        if (bv.equals("UPC")) {
            if (inv.getUpcCode().equals(wcount.getBarcode())) {
                result = "assigned";
                return result;
            } else {
                result = "UPC";
                return result;
            }
        }
        if (bv.equals("ISBN")) {
            if (inv.getIsbnCode().equals(wcount.getBarcode())) {
                result = "assigned";
                return result;
            } else {
                result = "ISBN";
                return result;
            }

        }

        result = "Invalid";

        return result;
    }

    public static invBean setInvBean(OwdInventory inv) {

        invBean ib = new invBean();
        ib.setDescription(inv.getDescription());
        ib.setInventoryId(inv.getInventoryId().toString());
        ib.setInventoryNum(inv.getInventoryNum());
        return ib;

    }

    public static void checkCloseLocations(wCountDTO wcount) throws Exception {

        String query = "SELECT     id\n" +
                "FROM         w_inv_locations (NOLOCK) \n" +
                "WHERE     (location = '" + wcount.getLocation() + "') AND (inv_request_fkey = " + wcount.getRequestId() + ") AND (done = 0)";

        ResultSet rs = HibernateSession.getResultSet(query);
        List list = new ArrayList();
        String id = null;
        while (rs.next()) {
            id = rs.getString(1);
            list.add(rs.getString(1));
        }

        if (list.size() == 1) {

            String update = "UPDATE    w_inv_locations\n" +
                    "SET  done = 1\n" +
                    "WHERE     (id = " + id + ")";
            System.out.println(update);
            Connection conn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(update);
            stmt.close();
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());


        } else if (list.size() >= 2) {
            throw new Exception("All skus must be Counted before you exit Shelf");
        }


    }

    public static void doneWithLocations(wCountDTO wcount) throws Exception{
      String query = "SELECT     id\n" +
              "FROM         w_inv_locations (NOLOCK) \n" +
              "WHERE     (inv_request_fkey = "+wcount.getRequestId()+") AND (location='"+wcount.getLocation()+"') AND (done = 0)";
       // System.out.println(query);
      ResultSet rs = HibernateSession.getResultSet(query);
      while(rs.next()){
          WInvLocations wloc = (WInvLocations) HibernateSession.currentSession().load(WInvLocations.class, Integer.valueOf(rs.getString(1)));
          System.out.println(wloc.getId()) ;
          wloc.setDone(new Integer(1));
          if(wloc.getInventoryId()!=null){
          wloc.setRemove(new Integer(1));
          }
          HibernateSession.currentSession().saveOrUpdate(wloc);
              HibernateSession.currentSession().flush();
              com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

      }
    }
   public static int checkForCurrentCount(String inventoryId, String location, int requestId) throws Exception{
       int quanity = 0;
        String query = "SELECT     SUM(quanity) AS Expr1\n" +
                "FROM         w_inv_counts (NOLOCK) \n" +
                "WHERE     (inventory_id = "+inventoryId+") AND (location = '"+location+"') AND (inv_loc_fkey IN\n" +
                "                          (SELECT     id\n" +
                "                            FROM          w_inv_locations (NOLOCK) \n" +
                "                            WHERE      inv_request_fkey = "+requestId+"))";
     //  System.out.println(query);
       ResultSet rs = HibernateSession.getResultSet(query);
     // System.out.println("after rs");
     try{ while(rs.next()){
         //  System.out.println("in rs");
          System.out.println(rs.getString(1));
           if(!rs.getString(1).equals(null)) {
               System.out.println("error");
           quanity = rs.getInt(1);            } else
           {
               //System.out.println("la ti da");
           }
       }
        System.out.println("Quanity");
     }catch (NullPointerException e){
         //    e.printStackTrace();
     }
       return quanity;
   }
    public static WInvLocations getWInvLocFromLocation(Session session, String reqId, String loc) throws Exception{
        WInvLocations wi= null;

        Criteria crit = session.createCriteria(WInvLocations.class);
                   crit.setMaxResults(100);
                     System.out.println("1");
                 WInvRequest wq = (WInvRequest) session.load(WInvRequest.class, Integer.valueOf(reqId));
                   crit.add(Expression.eq("winvreques", wq));
         System.out.println("2");
                   crit.add(Expression.eq("location", loc));
         System.out.println("3");

                  crit.add(Expression.isNull("inventoryId"));
         System.out.println("4");
                   List orderList = crit.list();
        System.out.println(orderList.size());
                if(orderList.size()>0) {
                     System.out.println("5");
              wi = (WInvLocations) orderList.get(0);
                     System.out.println("6");
                } else{
                throw new Exception("Unable to Load ");

                }
        return wi;

    }
}

