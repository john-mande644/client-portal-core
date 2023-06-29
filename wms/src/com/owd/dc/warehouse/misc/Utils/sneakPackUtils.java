package com.owd.dc.warehouse.misc.Utils;

import com.opensymphony.xwork2.ActionContext;
import com.owd.WMSUtils;
import com.owd.core.TimeStamp;
import com.owd.dc.packing.packItemBean;
import com.owd.dc.packing.packageBean;
import com.owd.dc.warehouse.misc.beans.sneakPackItemsBean;
import com.owd.dc.warehouse.misc.printUtils;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OwdBoxtypes;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/5/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class sneakPackUtils {
     public static void main(String[] args){
         try{
           System.out.println(loadOrderIntoDatabase("*15046467*", "51", HibernateSession.currentSession(),new TreeMap()));


         /*   List<sneakPackItemsBean> items = loadPackItemsBean("51");
             System.out.println(items.size());
             System.out.println(items.get(0).getInvId());*/

           //  System.out.println(updateBox("231","51",HibernateSession.currentSession()));
           // System.out.println(checkDonePacking("231"));
            // System.out.println(packItem("121672","51",HibernateSession.currentSession()));
            // System.out.println(checkDonePacking("51"));
            // System.out.println(checkPackingOrder("231"));
             // System.out.println(packTheOrder("51",HibernateSession.currentSession()));
           //  printPackageLabel("51");
            // removePackedData("51");
            // System.out.println(hasBoxBeenSelected("51"));
           // spBoxReturn spb = getSneakPeekBoxToUse("51");
             //System.out.println(spb.getId());
             //System.out.println(spb.getName());
            //  System.out.println(hasBoxBeenSelected("216"));
            //  System.out.println(getBoxNameByPickerId("51"));
         } catch(Exception e){
             System.out.println("Baddy");
            System.out.println(e.getMessage());
         }

     }
    public static String hasBoxBeenSelected(String pickerId) throws Exception{
         String box = "nobox";
                     String sql = "select boxId from sneakPack where pickerId = :pickerId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("pickerId",pickerId);
        List results = q.list();
        try{
        if(results.size()>0){
            System.out.printf("This is what we got : %s :%n",results.get(0).toString());
            box = getNameForSPBoxId(results.get(0).toString());
            System.out.println(box);
        }
        }catch (Exception e){
               e.printStackTrace();
        }
        return box;
    }
    public static void removePackedData(String pickerId) throws Exception {
        String sql = "delete from sneakPack where pickerId = :pickerId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        int rows = q.executeUpdate();
        if (rows<=0) throw new Exception("Deleting failed contact IT");
        HibUtils.commit(HibernateSession.currentSession());


    }
    public static void printPackageLabel(String pickerId,String printer) throws Exception{
            String sql ="SELECT\n" +
                    "    dbo.package.pack_barcode\n" +
                    "FROM\n" +
                    "    dbo.sneakPack\n" +
                    "INNER JOIN dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.sneakPack.orderFkey = dbo.package_order.owd_order_fkey\n" +
                    "    )\n" +
                    "INNER JOIN dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey\n" +
                    "    )\n" +
                    "WHERE\n" +
                    "    dbo.sneakPack.pickerId = :pickerId and is_void = 0;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        String barcode = q.list().get(0).toString();
        printUtils.printSmallPackLabelUserId(barcode,pickerId,printer);
    }
    public static boolean checkPackingOrder(String pickerId) throws Exception {
        boolean packing = false;
                     String sql = "select id from sneakPack where pickerId = :pickerId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        List results = q.list();
        if(results.size()>0){
            packing = true;
        }
        return packing;
    }
    public static boolean checkDonePacking(String pickerId) throws Exception {
        boolean done = false;
             String sql = "select id from sneakPack where pickerId = :pickerId and qty - qtyPacked >0";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        List results = q.list();
        if(results.size()==0){
            done = true;
        }


        return done;

    }
    public static boolean packItem(String invId,String pickerId,Session sess) throws Exception{

        boolean good = false;
            //check for barcode-isbn
        if(invId.length()>11){
            String upcsql = "select invId from sneakPack where pickerId = :pickerId and (UPC = :UPC);";
            Query uq = HibernateSession.currentSession().createSQLQuery(upcsql);
            uq.setParameter("pickerId",pickerId);
            uq.setParameter("UPC",invId);
            //uq.setParameter("ISBN",invId);
            List theResults = uq.list();
            if(theResults.size()>0){
                System.out.println("settinng invid");

                invId = theResults.get(0).toString();
                System.out.println(invId)     ;
            } else{
                throw new Exception("UPC code not recognized in this order");
            }

        }

                   String sql = "select id,qty,qtyPacked from sneakPack where pickerId = :pickerId and invId = :invId";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        q.setParameter("invId",invId);
        List results = q.list();
        if(results.size()>0){
            Object[] data = (Object[]) results.get(0);
            String id = data[0].toString();
            int qty = Integer.parseInt(data[1].toString());
            int qtyPacked = Integer.parseInt(data[2].toString());
            if(qty == qtyPacked){
                throw new Exception("Everything is already packed for this item");

            }
            if(qty-qtyPacked>=1){
                  String usql = "update sneakPack set qtyPacked = :qtyPacked where id = :id";
                Query qq = sess.createSQLQuery(usql);
                qq.setParameter("qtyPacked",qtyPacked+1);
                qq.setParameter("id",id);
                int i = qq.executeUpdate();
                if(i<=0) throw new Exception("The update of the qty didn't work, please try again");
                HibUtils.commit(sess);
                good = true;
            } else{
                throw new Exception("The math must have been new. Please find some help");
            }

        } else{

           throw new Exception("Scan "+invId+" not found for this order ");
        }

       return good;
    }
    public static boolean updateBox(String boxId,String pickerId,Session sess) throws Exception{
        boolean good = false;
           String sql = "update sneakPack set boxId = :boxId where pickerId = :pickerId";
           Query q = sess.createSQLQuery(sql);
        q.setParameter("boxId",boxId);
        q.setParameter("pickerId",pickerId);
        int rows = q.executeUpdate();

        if(rows<=0){
          throw new Exception("Error updateing the database with the box info");

        }
         HibUtils.commit(sess);
         good = true;

        return good;
    }
    public static List<sneakPackItemsBean> loadPackItemsBean(String pickerId) throws Exception{
         List<sneakPackItemsBean> items = new ArrayList<sneakPackItemsBean>();
                String sql = "select invId,invNum,description,qty,qtyPacked from sneakPack where pickerId = :pickerId order by qtyPacked";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        List results = q.list();
        if(results.size()==0){
            throw new Exception("0 line items returned");
        }
      for(Object row : results){
          Object[] data = (Object[]) row;
          sneakPackItemsBean spb = new sneakPackItemsBean();
          spb.setInvId(data[0].toString());
          spb.setInvNum(data[1].toString());
          spb.setDesc(data[2].toString());
          spb.setQty(Integer.parseInt(data[3].toString()));
          spb.setQtyPacked(Integer.parseInt(data[4].toString()));
          items.add(spb);
      }


        return items;
    }
    public static String getShipMethod(String pickerId) throws Exception{
        String sql = "SELECT\n" +
                "    dbo.owd_order_ship_info.carr_service\n" +
                "FROM\n" +
                "    dbo.sneakPack\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.sneakPack.orderFkey = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.sneakPack.pickerId = :pickerId ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        return q.list().get(0).toString();



    }
    public static String getOrderFkeyViaPickerId(String pickerId) throws Exception{
        String sql = "select orderFkey from sneakPack where pickerId = :pickerId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);

       List result = q.list();
        if (result.size()>0){
            return result.get(0).toString();
        }

         throw new Exception("Unable to get order for your login");
    }
    public static String loadOrderIntoDatabase(String barcodeScan, String pickerId,Session sess,Map<String,String> boxMap){
        String result = "success";
        ResultSet rs = null;

                     try{
                           System.out.println("here is the picker id: " + pickerId);
                        String checkAlready = "select id from sneakPack where pickerId = :pickerId";
                          Query ca = sess.createSQLQuery(checkAlready);
                         ca.setParameter("pickerId",pickerId);
                         List carows = ca.list();
                         if(carows.size()>0) throw new Exception("Already packing");

                         String orderNum = barcodeScan.replaceAll("\\*|[rR].*", "");
                         System.out.println("Barcode: " + barcodeScan);
                         System.out.println("order Id: " + orderNum);
                         String sql = "execute dbo.packAppLoadOrder " + orderNum;
                         rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
                if(rs.next()){
                         if (rs.getInt("is_void") == 1) throw new Exception(orderNum + " Has been Voided " +
                                 "Since it's been picked. ");
                         if (!rs.getString("order_num_barcode").equals(barcodeScan))
                             throw new Exception(orderNum + " Has been reprinted since it has been picked");
                         String orderfkey = rs.getString("order_id");
                         if (rs.getString("shipped") != (null)) throw new Exception(orderNum + " Has Already been Shipped");
                         rs.close();
                         System.out.println("hello");
                         //  ps.close();
                         System.out.println(orderfkey + " order fkey");
                         rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select * from package_order  (NOLOCK) where is_void = 0 and owd_order_fkey = " + orderfkey);
                         if (rs.next()) {
                             System.out.println("got result " + rs.getString(1));
                             throw new Exception(orderNum + " Has Already Been Packed:" + orderfkey);
                         }
                         rs.close();

                         //todo, should be good now we need to load into database;

                          String sqlpack = "insert into sneakPack (orderFkey, clientFkey, pickerId, invId, invNum,description,UPC,ISBN,qty,lineItemId) \n" +
                                  "\n" +
                                  "\n" +
                                  "SELECT\n" +
                                  "order_fkey,client_fkey,:pickerId,owd_inventory.inventory_id,owd_inventory.inventory_num,owd_inventory.description,UPC_CODE, ISBN_CODE,\n" +
                                  "quantity_actual,line_item_id\n" +
                                  "    \n" +
                                  "    \n" +
                                  "FROM\n" +
                                  "    owd_line_item (NOLOCK)\n" +
                                  "INNER JOIN owd_inventory (NOLOCK)\n" +
                                  "LEFT OUTER JOIN special_instructions (NOLOCK)\n" +
                                  "ON\n" +
                                  "    inventory_id=external_id\n" +
                                  "AND activity_type='INVENTORY-PACK'\n" +
                                  "ON\n" +
                                  "    owd_line_item.inventory_id = owd_inventory.inventory_id\n" +
                                  "WHERE\n" +
                                  "    (\n" +
                                  "        owd_line_item.order_fkey = :orderFkey\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_line_item.quantity_actual > 0\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_line_item.inventory_num NOT LIKE 'KIT-%'\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_line_item.inventory_num <> 'ITEM'\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_inventory.is_auto_inventory = 0\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_line_item.inventory_num NOT LIKE 'KITITEM%'\n" +
                                  "    )\n" +
                                  "AND\n" +
                                  "    (\n" +
                                  "        owd_inventory.is_auto_inventory = 0\n" +
                                  "    )";
                             System.out.println("We are going to parameter to insert into sneakpack");
                         Query q = sess.createSQLQuery(sqlpack);
                         q.setParameter("orderFkey",orderfkey);
                         q.setParameter("pickerId",pickerId);
                         int i = q.executeUpdate();
                         if(i>0){
                             HibUtils.commit(sess);
                             System.out.println("getting boxes");
                             spBoxReturn spBox = getSneakPeekBoxToUse(pickerId, WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext()),boxMap);
                             System.out.println("Boxes returnred");
                              if (spBox.getId().equals("0")){
                                 return "boxes";
                              }
                             updateBox(spBox.getId(),pickerId,sess);
                              HibUtils.commit(sess);
                         } else {
                             throw new Exception("For some reason the insert didn't work");
                         }

                         }else{
                    throw new Exception(orderNum + " is not a Valid order Number");
                         }
                     } catch (Exception e){
                         return e.getMessage();
                     } finally{
                         try{
                             rs.close();
                         } catch (Exception e){

                         }
                     }
       return result;
    }
    public static String getFingerprintByPackerId(String pickerId) throws Exception {
        String sql = "SELECT\n" +
                "    dbo.w_order_attributes.fingerprint\n" +
                "FROM\n" +
                "    dbo.sneakPack\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.sneakPack.orderFkey = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.sneakPack.pickerId = :pickerId ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        return q.list().get(0).toString();
    }

    public static boolean packTheOrder(String pickerId,Session sess) throws Exception{
        boolean good = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        TimeStamp tsPack = new TimeStamp("Single Box: Start");
        TimeStamp tsAll = new TimeStamp("Whole Pack Time");
        String barcode = new String();
      //  packageBean pb = new packageBean();


           String info = "select orderFkey,starttime,boxId from sneakPack where pickerId = :pickerId";
        Query q = sess.createSQLQuery(info);
        q.setParameter("pickerId",pickerId);
        List results = q.list();
        if(results.size()>0){
            Object[] data = (Object[]) results.get(0);
            int orderFkey = Integer.parseInt(data[0].toString());
            String start = data[1].toString();
            String boxId = data[2].toString();


            tsPack.print("Single Box: xml done " + orderFkey);

            String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs,facility,packType) values (" +
                    "?,?,?,getDate(),?,?,?)";
            ps = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);


            ps.setInt(1, orderFkey);
            ps.setString(2,pickerId);
            ps.setString(3,start);
           // ps.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
            // ps.setDate(3,pb.getStart());
            //ps.setDate(4,pb.getStop());
            ps.setInt(4, 1);
            ps.setString(5, "DC1");
            ps.setString(6,"2");

            int rows = ps.executeUpdate();
            tsPack.print("Single Box: insert package_order Done " + orderFkey);
            System.out.println(rows);

            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey =" + orderFkey);
            tsPack.print("Single Box: Lookup id from package_order " + orderFkey);
            if (rs.next()) {
                int packageOId = rs.getInt((1));
                System.out.println(packageOId);
                //found idnow insert box
                OwdBoxtypes box = (OwdBoxtypes) sess.load(OwdBoxtypes.class,Integer.parseInt(boxId));
                System.out.println("loaded box");
                pst = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode )values" +
                        "(?,?,?,?,?,?,?,?)");
                pst.setInt(1,box.getId());
                pst.setInt(2, packageOId);
                pst.setBigDecimal(3, box.getCost());
                pst.setBigDecimal(4, box.getDepth());
                pst.setBigDecimal(5, box.getWidth());
                pst.setBigDecimal(6, box.getHeight());
                //System.out.println("This is the float" + Float.parseFloat(pb.getWeight()) + "    the bean  " + pb.getWeight());
                pst.setFloat(7,0.00f);
                String getbarcode = "select order_num_barcode from owd_order where order_id = :orderFkey";
                Query gbq = sess.createSQLQuery(getbarcode);
                gbq.setParameter("orderFkey",orderFkey);
                String scannedBarcode = gbq.list().get(0).toString();
                pst.setString(8, "p" + packageOId + scannedBarcode + "b1");

                int rows2 = pst.executeUpdate();
                tsPack.print("Single Box: Inserted package done " + orderFkey);
                barcode = "p" + packageOId + scannedBarcode + "b1";
               String getPackedLines = "select lineItemId,qtyPacked from sneakPack where orderFkey = :orderFkey";
                Query lines = sess.createSQLQuery(getPackedLines);
                lines.setParameter("orderFkey",orderFkey);
                List lineitems = lines.list();

                if(lineitems.size()>0) {
                    ResultSet rs1 = null;
                    String packageId = new String();
                    try {
                        rs1 = HibernateSession.getResultSet(HibernateSession.currentSession(), "select id from package  (NOLOCK) where pack_barcode = '" + barcode + "'");
                        rs1.next();
                        tsPack.print("Single Box: Lookup id from package " + orderFkey);
                        packageId = rs1.getString(1);
                        if (null == packageId) {
                            throw new Exception("Unable to get get id for line iserts");
                        }

                       // Iterator it = pb.getPackedLines().iterator();
                        for(Object row:lineitems) {
                            Object[] lineitem = (Object[]) row;
                              String lineId = lineitem[0].toString();
                            String qtyPacked = lineitem[1].toString();

                            System.out.println("setting pack for " + lineId);
                            PreparedStatement pre = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                    "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                            pre.setString(1, packageId);
                            pre.setString(2, lineId);
                            pre.setString(3, qtyPacked);
                            int rw = pre.executeUpdate();
                            tsPack.print("Single Box: Inserted line item for package " + orderFkey + " line fkey: " + lineId);
                            System.out.println("Doine Setting reoderd");

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception(e.getMessage());
                    } finally {
                        rs1.close();
                        try {
                            rs.close();
                        } catch (Exception e) {

                        }
                        try {
                            ps.close();
                        } catch (Exception e) {

                        }
                        try {
                            pst.close();
                        } catch (Exception e) {

                        }
                    }


                }
               HibUtils.commit(HibernateSession.currentSession());
                good = true;
            } else {
                System.out.println("Bad insert no findyyy");
                throw new Exception("No insert for order package record");
            }
        }else{
            throw new Exception("We were unable to find order for your id");
        }



       return good;
    }

    private static spBoxReturn getSneakPeekBoxToUse(String pickerId,String facility,Map<String,String>boxMap) throws Exception {
        spBoxReturn spb = new spBoxReturn();

          String sql = "SELECT\n" +
                  "    SUM(dbo.sneakPack.qty),\n" +
                  "    dbo.owd_order.group_name,\n" +
                  "    dbo.w_order_attributes.fingerprint," +
                  "owd_order.order_id\n" +
                  "FROM\n" +
                  "    dbo.sneakPack\n" +
                  "INNER JOIN dbo.owd_order\n" +
                  "ON\n" +
                  "    (\n" +
                  "        dbo.sneakPack.orderFkey = dbo.owd_order.order_id\n" +
                  "    )\n" +
                  "INNER JOIN dbo.w_order_attributes\n" +
                  "ON\n" +
                  "    (\n" +
                  "        dbo.sneakPack.orderFkey = dbo.w_order_attributes.order_fkey\n" +
                  "    )\n" +
                  "WHERE\n" +
                  "    dbo.sneakPack.pickerId = :pickerId and invNum <> 'P161583'\n" +
                  "GROUP BY\n" +
                  "    dbo.owd_order.group_name,\n" +
                  "    dbo.w_order_attributes.fingerprint, owd_order.order_id ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("pickerId",pickerId);
        List results = q.list();
        if (results.size()<= 0) throw new Exception("WE did not find anything go get a box for");

        Object[] data = (Object[]) results.get(0);
        if(data[1].toString().equals("diamondcandles")){


        int candels = Integer.parseInt(data[0].toString());
        if (facility.equals("DC7")){
            switch (candels){
                case 1:
                    spb.setId("338");
                    spb.setName("DC7-SP-1064");
                    break;
                case 2:
                    spb.setId("339");
                    spb.setName("DC7-SP-10124");
                    break;
                case 3:case 4:
                    spb.setId("340");
                    spb.setName("DC7-SP-10184");
                    break;
                case 5:
                    spb.setId("341");
                    spb.setName("DC7-SP-10244");
                    break;
                case 6:case 7 :
                    spb.setId("342");
                    spb.setName("DC7-SP-10304");

                    break;
                case 8:
                    spb.setId("343");
                    spb.setName("DC7-SP-10364");
                    break;
                default: throw new Exception("More candels than we can handle do something else");
            }


        }else if(facility.equals("DC6")){
            switch (candels){
                case 1:
                    spb.setId("344");
                    spb.setName("DC6-SP-1064");
                    break;
                case 2:
                    spb.setId("345");
                    spb.setName("DC6-SP-10124");
                    break;
                case 3:case 4:
                    spb.setId("346");
                    spb.setName("DC6-SP-10184");
                    break;
                case 5:
                    spb.setId("347");
                    spb.setName("DC6-SP-10244");
                    break;
                case 6:case 7 :
                    spb.setId("348");
                    spb.setName("DC6-SP-10304");

                    break;
                case 8:
                    spb.setId("349");
                    spb.setName("DC6-SP-10364");
                    break;
                default: throw new Exception("More candels than we can handle do something else");
            }
        }   else{
          switch (candels){
              case 1:
                spb.setId("231");
                  spb.setName("SP-1064");
                  break;
              case 2:
                  spb.setId("232");
                  spb.setName("SP-10124");
                  break;
              case 3:case 4:
                  spb.setId("233");
                  spb.setName("SP-10184");
                  break;
              case 5:
                  spb.setId("234");
                  spb.setName("SP-10244");
                  break;
              case 6:case 7 :
                  spb.setId("235");
                  spb.setName("SP-10304");

                  break;
              case 8:
                  spb.setId("236");
                  spb.setName("SP-10364");
                  break;
              default: throw new Exception("More candels than we can handle do something else");


          }
        }

        } else{
           //lookup box via fingerprint
          String boxsql = ("execute getBoxesMostUsed :orderId");
          Query qq = HibernateSession.currentSession().createSQLQuery(boxsql);
            qq.setParameter("orderId",data[3].toString());
            List boxes = qq.list();
            if(boxes.size()>0){
                 Object[] box = (Object[]) boxes.get(0);
                spb.setId(box[14].toString());

            } else{
                //Check the current map for box Id
                System.out.println("We are checking map for fingerprint");
               String sqlfin = "select fingerprint from w_order_attributes where order_fkey = :orderId";
               Query fin = HibernateSession.currentSession().createSQLQuery(sqlfin);
                fin.setParameter("orderId",data[3].toString());
                List res = fin.list();
                if(res.size()>0){
                    if(boxMap.containsKey(res.get(0).toString())){
                        spb.setId(boxMap.get(res.get(0).toString()));

                    } else{
                        spb.setId("0");
                    }
                }else{
                    spb.setId("0");
                }

            }

        }


        return spb;

    }
    public static String getBoxNameByPickerId(String pickerId) throws Exception{
        String s = "";
           s = hasBoxBeenSelected(pickerId);
        if (s.equals("nobox")) throw new Exception("nothing to see here folks");

        return s;
    }
    public static String getNameForSPBoxId(String id) throws Exception{
        String s = "";
        if(id.equals("0")){
           return "select box";
        }
        String sql = "select dbo.owd_boxtypes.name from owd_boxtypes where id = :theid";
        Query q =  HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("theid",id);
        List box = q.list();

        s = box.get(0).toString();

       return s;

    }
    final static class spBoxReturn{
        private String id;
        private String name;

        String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }
}
