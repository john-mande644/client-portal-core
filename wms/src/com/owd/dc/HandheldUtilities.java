package com.owd.dc;

import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.dc.inventory.beans.locationPriority;
import com.owd.dc.locations.Utilities.locationManagementUtils;
import com.owd.dc.locations.locationInfoBean;
import com.owd.dc.locations.newLocationUtilities;
import com.owd.hibernate.*;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.setup.buttons;
import com.owd.dc.locations.LocationBarcodeUtilities;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.ReceiveStatus;
import com.owd.hibernate.generated.ReceiveStatusItems;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jul 20, 2005
 * Time: 9:58:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class HandheldUtilities {
    public static String findurl = "findSku";
    public static String pickurl = "picpick";
   public String getFindUrl(){
       return findurl;
   }

    public static boolean canTeleportByName(String name){
        boolean teleport = false;
                        try{
                            String sql = "select canTeleport from handheld_setup where name= :name";
                            Query q = HibernateSession.currentSession().createSQLQuery(sql);
                            q.setParameter("name",name);
                            List Results = q.list();
                            if(Results.get(0).toString().equals("1")) teleport = true;


                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("Unable to determine teleport abilites. Using false");
                        }

        return teleport;
    }
    public static List getSkuFromLocaiton(String loc) {
        List skus = new ArrayList();

        String selectskufromlocation = "SELECT DISTINCT owd_inventory_locations.inventory_fkey, owd_inventory.inventory_num\n" +
                "FROM owd_inventory_locations (NOLOCK)  INNER JOIN\n" +
                "owd_inventory  (NOLOCK) ON owd_inventory_locations.inventory_fkey = owd_inventory.inventory_id\n" +
                "WHERE (owd_inventory_locations.location ='" + loc + "')";
        try {
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),selectskufromlocation);
            while (rs.next()) {
                String[] skuid = new String[2];
                skuid[0] = rs.getString(1);
                System.out.println(rs.getString(1));
                skuid[1] = rs.getString(2);
                System.out.println(rs.getString(2));
                skus.add(skuid);

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());

        }
        return skus;
    }
    public static String getIdFromSku(String sku, String clientId) throws Exception{
        String sql = "SELECT     inventory_id\n" +
                "FROM         owd_inventory (NOLOCK) \n" +
                "WHERE     (inventory_num = '"+sku+"') AND (client_fkey = "+clientId+")";
       ResultSet rs = HibernateSession.getResultSet(sql);
        if(rs.next()){
        return rs.getString(1);
        }
         return sku;
    }
    public static void loadAsnFromID(int asnId, HttpServletRequest req) throws Exception {
        ReceiveStatus rcvstat = null;
        String getAsn = "select id, client_fkey  from dbo.asn  (NOLOCK) where id =" + asnId;
        try {
            ResultSet rs = HibernateSession.getResultSet(getAsn);
            if (rs.next()) {
                rcvstat = new ReceiveStatus();
                rcvstat.setAsnFkey(Integer.parseInt(rs.getString(1)));
                rcvstat.setAgent(req.getAttribute("loginName").toString());
                rcvstat.setStartTime(new Date());
                rcvstat.setClientFkey(Integer.parseInt(rs.getString(2)));
                rcvstat.setCurrentRcvItem(0);
                HibernateSession.currentSession().save(rcvstat);
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                HibernateSession.currentSession().refresh(rcvstat);
            } else
                throw new Exception("Asn Does not exist for entered id" + asnId);
            String getItems = "select id, inventory_fkey, inventory_num, title, expected, received  from dbo.asn_items  (NOLOCK) where asn_fkey =" + asnId +" order by inventory_fkey" ;
            rs = HibernateSession.getResultSet(getItems);
            while (rs.next()) {
                ReceiveStatusItems rcvitem = new ReceiveStatusItems();
                rcvitem.setAsnItemFkey(Integer.parseInt(rs.getString(1)));
                rcvitem.setInventoryFkey(Integer.parseInt(rs.getString(2)));
                rcvitem.setInventoryNum(rs.getString(3));
                rcvitem.setTitle(rs.getString(4));
                rcvitem.setExpected(Integer.parseInt(rs.getString(5)));
                rcvitem.setReceived(Integer.parseInt(rs.getString(6)));
                rcvitem.setQtyDamaged(0);
                rcvitem.setReceiveStatus(rcvstat);
                rcvitem.setIsReceived(0);
                HibernateSession.currentSession().save(rcvitem);
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelRcv(int rcvId) {
        try {
            ReceiveStatus rcvstatus = (ReceiveStatus) HibernateSession.currentSession().load(ReceiveStatus.class, new Integer(rcvId));
            Iterator it = rcvstatus.getReceiveStatusItems().iterator();
            while (it.hasNext()) {
                ReceiveStatusItems item = (ReceiveStatusItems) it.next();
                item.setReceiveStatus(null);
                HibernateSession.currentSession().delete(item);
            }
            HibernateSession.currentSession().delete(rcvstatus);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // sets cookies for remembering which client you are doing locations for for Multiple barcodes for sku's/clients
    public static void setRememberMulti(String clientFkey, String name, HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie2 = new Cookie("remfkey", clientFkey);
        //  resp.addCookie(cookie);
        resp.addCookie(cookie2);
        Cookie cookie3 = new Cookie("remname", name);
        resp.addCookie(cookie3);
        req.setAttribute("remember", clientFkey);
        req.setAttribute("rememberclientname", name);
    }

    public static skuBean getSkuInfo(String sku,String facility) throws Exception {
        skuBean info = new skuBean();

        try {
            int mynt = 0;
            try {
                mynt = Integer.decode(sku).intValue();
            } catch (NumberFormatException nfex) {
                //  System.out.println(sku);
                throw new Exception(sku + " is Invalid.  Skus must be numeric!!!");
            }
            List<locationPriority> locList = LocationBarcodeUtilities.getLocationsForInventoryIDList(mynt, HibernateSession.currentSession(),facility);

            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(mynt));

            info.setClientName(inv.getOwdClient().getCompanyName());
            info.setInventoryNum(inv.getInventoryNum());
            info.setInventoryId(inv.getInventoryId().toString());
            info.setDescription(inv.getDescription());
            info.setQuanityOnHand(OrderUtilities.getAvailableInventory(inv.getInventoryId().toString(), FacilitiesManager.getFacilityForCode(facility).getId()));
           // info.setQuanityOnHand(inv.getOwdInventoryOh().getQtyOnHand().intValue());
            info.setSize(inv.getItemSize());
            info.setColor(inv.getItemColor());
            info.setLocations(locList);
           // info.setImageUrl(inv.getImageUrl());
           // info.setImageThumb(inv.getImageThumbUrl());
            info.setImageThumb(getThumbUrlTag(inv.getImageThumbUrl(),inv.getImageUrl(),inv.getInventoryId().toString(),findurl));
            info.setImageUrl(getImageUrlTag(inv.getImageUrl()));
            System.out.println(info.getImageUrl()+"  :  thumb: "+info.getImageThumb());
            if(inv.getLotTrackingRequired()==1){
                info.setLotControlled(true);
                info.setLotControlledType("Fully Controlled");
            }
        } catch (ObjectNotFoundException oe) {
            throw new Exception("Invalid Sku: No Record exists!!!");
        }
        return info;
    }
    public static String getImageUrlTag(String image){
       if (image!=null && image.length()>1) {
        if(image.startsWith("<img")){
            return image;
        }
        return "<img src=\""+image+"\">";
       }
        return "";
    }
    public static String getThumbUrlTag(String thumb, String image,String sku,String what){
        String url = "";
        String tag ="";
        boolean  hasimage = false;
        boolean thimg=false;
        String var = "";
        if(what.equals(findurl)) var = "picsku";
        //set has image test
        if(image!=null && image.length()>1)hasimage=true;


        if(thumb!=null && thumb.length()>1){
          thimg = thumb.startsWith("<img");
          if(thimg){
              tag = thumb;
          } else{
              tag = "<img src=\""+thumb+"\" border=\"0\">";
          }
         //if there is an image create a link on the pick
          if (hasimage){
              if(what.equals(findurl)){
                  //this is the find url
              url = "<a href=\""+what+"?"+var+"="+sku+"\">"+tag+"</a>";
              }else{
                  //this is the url for picking
                url = "<a href=\""+what+"\">"+tag+"</a>";
              }
          } else{
              url = tag;
          }
        }

       return url;
    }
    public static buttons getMetaButtons(String id, String name) {
        buttons info = new buttons();
        System.out.println("1");
        String selectButtons = "SELECT t5, t6, t7, t8, red, green, printer, small_printer FROM  handheld_setup (NOLOCK) WHERE loginId =" + id;
        try {
            System.out.println("2");
            ResultSet rs = HibernateSession.getResultSet(selectButtons);
            if (rs.next()) {
                System.out.println("Found login entry");
                info.setT5(rs.getString(1));
                info.setT6(rs.getString(2));
                info.setT7(rs.getString(3));
                info.setT8(rs.getString(4));
                info.setRed(rs.getString(5));
                info.setGreen(rs.getString(6));
                info.setPrinter(rs.getString(7));
                info.setSmallPrinter(rs.getString(8));
                System.out.println(rs.getString(7));


            } else {
                System.out.println("No login found creating default");
                info = loadDefault();
                setButtonsInDatabase(id, info, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = loadDefault();
        }
        return info;
    }

    public static buttons loadDefault() {
        buttons info = new buttons();
        info.setT5("find");
        info.setT6("assign");
        info.setT7("upc");
        info.setT8("doinventory");
        info.setRed("remove");
        info.setGreen("pick");
        return info;
    }

    public static void setButtonsInDatabase(String id, buttons info, String name) throws Exception {
        String sql = "insert into dbo.handheld_setup (loginId, t5, t6, t7, t8, red, green, name) values (?,?,?,?,?,?,?,?)";
        Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        info = loadDefault();
        java.sql.PreparedStatement stmt = cxn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(id));
        stmt.setString(2, info.getT5());
        stmt.setString(3, info.getT6());
        stmt.setString(4, info.getT7());
        stmt.setString(5, info.getT8());
        stmt.setString(6, info.getRed());
        stmt.setString(7, info.getGreen());
        stmt.setString(8, name);
        int rows = stmt.executeUpdate();
        cxn.commit();
        cxn.close();
    }
       
    public static void updateButtonsInDatabase(buttons info, String name,String id,String facility) throws Exception {
        String checksql = "select loginId from handheld_setup where name =:name";
        Session sess = HibernateSession.currentSession();
        Query q = sess.createSQLQuery(checksql);
        q.setParameter("name",name);
        if(q.list().size()>0){
            String sql = "update dbo.handheld_setup set  printer =:printer , small_printer =:smallPrinter, palletTag = :pallet, location = :facility where name= :loginname";
            Query qq = sess.createSQLQuery(sql);
            qq.setParameter("printer", info.getStoredPrinter());
            qq.setParameter("smallPrinter",info.getStoredSmallPrinter());
            qq.setParameter("loginname",name);
            qq.setParameter("pallet",info.getStoredPallet());
            qq.setParameter("facility",facility);
            if (qq.executeUpdate()==0) throw new Exception("UPdateing of data failed");
            HibUtils.commit(sess);
        } else{
            String sql = "insert into handheld_setup(loginId,name,printer,small_printer,palletTag,location) values(:id,:loginname,:printer,:small_printer,:pallet,:facility)";
            Query qq = sess.createSQLQuery(sql);
            qq.setParameter("id",id);
            qq.setParameter("loginname",name);
            qq.setParameter("printer",info.getStoredPrinter());
            qq.setParameter("small_printer",info.getStoredSmallPrinter());
            qq.setParameter("pallet",info.getStoredPallet());
            qq.setParameter("facility",facility);
            if(qq.executeUpdate()==0) throw new Exception("Insert of new data failed for printer setup");
            HibUtils.commit(sess);

        }



    }
    public static void main(String[] args){
        try{
            HibernateSession.currentSession();
//System.out.println(getSmallPrinterForUser("Jaci Jacobs"));
            newLocationUtilities.updatePickStrings(HibernateSession.currentSession(),45957);
  //          System.out.println(getPrinterForUser("51"));
         //   List locList = LocationBarcodeUtilities.getLocationsForInventoryID(36351, HibernateSession.currentSession());
          //  System.out.print(locList);
              //System.out.println(locationAssignedToValidParent( HibernateSession.currentSession(),"//36504"));
             // System.out.println(canTeleportByName("Danny Nickels"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getStoredFacilityForName(String name) throws Exception{
        String facility = "DC1";
               String sql = "select location from handheld_setup where name = :name";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("name",name);
        List results = q.list();
        if (results.size()>0){
            try{
            facility = results.get(0).toString();
            }catch (Exception e){

            }
        }



       return facility;
    }
    public static String getPalletTagPrinterForUser(String name) throws Exception{
        String printer = "PALLETTAG";
        String sql = "select pallettag from handheld_setup where name = :name";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setString("name",name);
        List result = q.list();

               if(result.size()>0){
                   printer = result.get(0).toString();

               } else throw new Exception("No printer found for " +name);


        return printer;
    }
    public static String getSmallPrinterForUser(String name) throws Exception {
        String printer = "";
             String qry = "select small_printer from handheld_setup where name = :id" ;
        Query q = HibernateSession.currentSession().createSQLQuery(qry);
        q.setParameter("id",name);
        List result = q.list();

        if(result.size()>0){
            printer = result.get(0).toString();

        } else throw new Exception("No printer found for " +name);


        return printer;
    }

    public static String getPrinterForUser(String name) throws Exception {
        String printer = "";
             String qry = "select printer from handheld_setup where name = :id" ;
        Query q = HibernateSession.currentSession().createSQLQuery(qry);
        q.setParameter("id",name);
        List result = q.list();

        if(result.size()>0){
            printer = result.get(0).toString();

        } else throw new Exception("No printer found for " +name);


        return printer;
    }
    public static void removeLocation(String loc, String inventoryId) throws Exception {
        Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        String sql = "delete from owd_inventory_locations where location = ? "
                + "and inventory_fkey = ?";
        PreparedStatement stmt = cxn.prepareStatement(sql);
        stmt.setString(1, loc.trim());
        stmt.setInt(2, Integer.decode(inventoryId).intValue());

        int rowsUpdated = stmt.executeUpdate();



        cxn.commit();
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(inventoryId));
        //if front location equals loc being removed replace with last scan in history
        if (inv.getFrontLocation().equals(loc)) {
            String locationsql = "select location, assign_date from owd_inventory_locations  (NOLOCK) where inventory_fkey = '" + inventoryId + "' ORDER BY assign_date DESC";
            String location = null;
            ResultSet rs = HibernateSession.getResultSet(locationsql);
            if (rs.next() == true) {
                location = rs.getString(1);
            } else {
                location = "UNKNOWN";
            }
            Connection cxn1 = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            sql = "update owd_inventory set front_location = ?"
                    + ", modified_by = 'System' where inventory_id =?";
            PreparedStatement stmt1 = cxn1.prepareStatement(sql);

            stmt1.setString(1, location);
            stmt1.setInt(2, Integer.decode(inventoryId).intValue());

            int rowsUpdated1 = stmt1.executeUpdate();
            cxn1.commit();
            System.out.println("Front location match");
        }


    }
  public static boolean locationAssignedToValidParent(org.hibernate.Session sess, String locId){
      boolean valid = false;
         String psql = "select ixParent from w_location where ixNode = :id";
      Query q = sess.createSQLQuery(psql);
      q.setParameter("id", locId.replace("//",""));
      System.out.println("using this id" + locId.replace("//",""));
       String parentId = q.list().get(0).toString();
      System.out.println(parentId);
      String locTypeSql = "select ixLocType from w_location where ixNode = :parent";
      q = sess.createSQLQuery(locTypeSql);
      q.setParameter("parent",parentId);
      String loctype = q.list().get(0).toString();
      System.out.println("This is our locType" + loctype) ;
      if( Integer.parseInt(loctype)>5){
          return true;
      }


      return valid;
  }

 public static void assignLocation(String loc, int sku, String name,String notes,String lotId) throws Exception {
        boolean setfront = false;

     if(lotId.length()>0){
         locationInfoBean lib;
         int validAssign = locationManagementUtils.canWeAssignThisLotIdToThisLocation(lotId,sku,loc);
         lib = new locationInfoBean(loc.replace("//",""),HibernateSession.currentSession());
         if(validAssign== locationManagementUtils.assignAlready){

             throw new Exception("This Lot is already assigned to this location: "+lib.getFormatedPickString());
         }
         if(validAssign == locationManagementUtils.assignBad) {
             throw new Exception("You are unable to assign this lot to this location: "+ lib.getFormatedPickString());
         }
     }

            System.out.println("Assign location start");
            //check if other locations exist for sku, if not set front location of inventory record
            String sqllochist = "select location from owd_inventory_locations  (NOLOCK) where inventory_fkey= " + sku + " and  location <> 'UNKNOWN'  and location NOT LIKE '/B%'";
            ResultSet rs = HibernateSession.getResultSet(sqllochist);
            if (rs.next() == false) {
                setfront = true;
            }

             assignSkuToLocation(name,loc,sku,setfront,notes,lotId);
       /* Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        //set front location if P or only location
        if (setfront) {
           insertIntoFrontLocation(cxn,name,loc,sku);
        } else {
         //insert into inventory_lcoations directly
           insertIntoLocations(cxn,name,loc,sku,primary);

        }*/
 /* if (setfront == true) {
           String sql = "select location from owd_inventory_locations  (NOLOCK) where inventory_fkey= " + sku + " and  location = '" + loc + "'";
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
            if (!rs.next()) {
                System.out.println("location in front, not set in locations setting now");
             insertIntoLocations(cxn,name,loc,sku,primary);
            }else{
                if (primary){
                    System.out.println("We have a primary location that needs to be updated");
                   String sql1 = "update owd_inventory_locations set primary_pick = 1 where inventory_fkey = :invFkey and location = :loc";
                    Query q = HibernateSession.currentSession().createSQLQuery(sql1);
                    q.setParameter("invFkey",sku);
                    q.setParameter("loc",loc);
                    q.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                }
            }
        }*/

    }
    private static void assignSkuToLocation(String name,String loc,int invId,boolean setFront,String notes,String lotId) throws Exception{
        Session sess = HibernateSession.currentSession();
        if(setFront){
            System.out.println("We are going to be updating the front location");
            OwdInventory inv = (OwdInventory)sess.load(OwdInventory.class,invId);
            inv.setFrontLocation(loc);
            sess.save(inv);
        }
       //todo check if it is already assigned here. prevent duplicates
         String dupCheck = "select id from owd_inventory_locations where location = :location and inventory_fkey = :fkey";
        Query qq = sess.createSQLQuery(dupCheck);
        qq.setParameter("location", loc);
        qq.setParameter("fkey",invId);
        List dup = qq.list();
        if(dup.size()>0){
            System.out.println("No insert we have a dup");
           return;
        }
        System.out.println("Check for pickString");
        WLocation wloc = (WLocation) sess.load(WLocation.class,Integer.parseInt(loc.replace("//","")));
        if (null == wloc.getPickString() || wloc.getPickString().equals("")){
            newLocationUtilities.updatePickStrings(sess,wloc.getId());
        }


        System.out.println("now we get to assign some tings");
        String sql = "insert into dbo.owd_inventory_locations ( location, assigned_by, inventory_fkey, assign_date,notes,lot_fkey) values ( :location, :username, :invId, getDate(), :notes,:lotValue)";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("location",loc);
        q.setParameter("username",name);
        System.out.println(invId);
        q.setParameter("invId",invId);
        q.setParameter("notes",notes);
        if(lotId.length()>0){
            q.setParameter("lotValue",lotId);
        }   else{
            q.setParameter("lotValue",null);

        }
        System.out.println(q.getNamedParameters());
        System.out.println(q.getQueryString());

        if(q.executeUpdate()==0){
            throw new Exception("Unable to insert data");

        }
        HibUtils.commit(sess);

    }
   /* public static void insertIntoLocations(Connection cxn, String name, String loc, int sku,boolean primary) throws Exception {
       System.out.println("setting location directly");
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        Date date = cal.getTime();
        String p = "0";
        if (primary) p = "1";
        System.out.println("primarry" + primary + " this is the p: "+p);
        String sql = "insert into dbo.owd_inventory_locations ( location, assigned_by, inventory_fkey, assign_date,primary_pick) values ('" + loc.trim() + "','" +
                name + "','" + (sku) + "','" + sdf.format(date) + "','"+p+"')";
        PreparedStatement stmt = cxn.prepareStatement(sql);
        int rowsUpdated = stmt.executeUpdate();

     //   if (rowsUpdated != 1)
     //       throw new Exception("Could not update database - location not assigned");

        cxn.commit();
    }

    public static void insertIntoFrontLocation(Connection cxn, String name, String loc, int sku) throws Exception {
        System.out.println("setting front location ");
       String sql = "update owd_inventory set front_location = ?"
                    + ", modified_by = ? where inventory_id =?";
        PreparedStatement stmt = cxn.prepareStatement(sql);

            System.out.println("settingfront parameters");
            stmt.setString(1, loc.trim());
            stmt.setString(2, (String) name);
            stmt.setInt(3, (sku));

        int rowsUpdated = stmt.executeUpdate();

   //     if (rowsUpdated != 1)
    //        throw new Exception("Could not update database - location not assigned");

        cxn.commit();

    }*/
    public static OwdInventory loadInv(Integer id) throws Exception{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  id);
        return inv;
    }

    public static boolean hasUPC(String sku, int clinetFkey){
        boolean upc = false;
         if(clinetFkey==319)return false;
        try{
            OwdInventory inv = loadInv(Integer.valueOf(sku));

            if((inv.getIsbnCode()!=null && inv.getUpcCode().length()>5)|| (inv.getUpcCode()!=null&& inv.getIsbnCode().length()>5))upc=true;

        }catch(Exception e){

        }
        return upc;
    }

   
}
