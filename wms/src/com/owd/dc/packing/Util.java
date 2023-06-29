package com.owd.dc.packing;

import com.owd.core.ExcelUtils;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.SerialNumberManager;
import com.owd.dc.loginAround;
import com.owd.dc.packing.beans.ClientsPrintableSkus;
import com.owd.dc.packing.beans.PackageBoxBean;
import com.owd.dc.packing.beans.orderSortControl;
import com.owd.dc.packing.beans.PrintableData;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.dc.utilities.shipmethods.shipMethodUtils;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.*;
import com.thoughtworks.xstream.XStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.hibernate.Query;
import org.hibernate.Session;
import com.owd.core.TimeStamp;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Intel
 * User: Danny
 * Date: May 1, 2007
 * Time: 4:35:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
      public static String xmlHead= "<?xml version=\"1.0\"?>\r\n";

    //Login user
     public static String login(String id,String loginIp,String version){
      try{
     //  Session sess = HibernateTimeForceSession.currentSession();
         String name = loginAround.checkLogin(id);
        System.out.println("This is the version: " + version);
          String[] vSplit = version.split("\\.");
          if(vSplit.length==4){
              if(Integer.parseInt(vSplit[0])>=1 ){
                  if(Integer.parseInt(vSplit[1])>=2){
                      if(Integer.parseInt(vSplit[2])>=1){
                         if( Integer.parseInt(vSplit[3])>=44){
                             System.out.println("good version");
                         }else{
                             return Util.errorResponse("Your version of the pack app is old. Please update you app.");
                         }
                      } else{
                          return Util.errorResponse("Your version of the pack app is old. Please update you app.");
                      }


                  }else{
                      return Util.errorResponse("Your version of the pack app is old. Please update you app.");
                  }

              }else{
                  return Util.errorResponse("Your version of the pack app is old. Please update you app.");
              }
          }else{
              return Util.errorResponse("Invalid version reported. Please make sure you are up to date.");
          }

        // ResultSet rs = HibernateTimeForceSession.getResultSet(sess, "select firstname,lastname,active_yn from empMain  (NOLOCK) where cardnumber=" + id);
       if(name.length()>0){
           System.out.println("dong next");
           System.out.println(loginIp);
        //  if(rs.getString(3).equals("0")) return Util.errorResponse("Inactive User, please use valid Id");

        return xmlCreate.loginResponce("",id,name);

       }

       return Util.errorResponse("Invalid Id");

      }catch (Exception ex){
         ex.printStackTrace();

          return Util.errorResponse("Unable to connect to login server, please atttempt later");
      } finally{
          //HibernateTimeForceSession.closeSession();
      }

     }
    //Response XML for login

    //Error XML respose for all
    public static String errorResponse(String error){
       return Util.xmlHead +"<response>\r\n" +
                "<error>"+error+"</error>\r\n" +

                "</response>";
    }
    //Load order
      public static String loadOrder(String orderBarcode){
          return  loadOrder(orderBarcode,"DC1","false");
      }
    public static String loadOrder(String orderBarcode,String Facility,String batch) {
        ResultSet rs = null;
        ResultSet rs1 = null;
       // PreparedStatement pstat = null;
        PreparedStatement ps = null;
        boolean licensePlateScan = false;
        boolean batchingOrder = false;
        if(batch.equalsIgnoreCase("true")){
            batchingOrder = true;
        }
        System.out.println("This is the batching stuff:"+batch+":"+batchingOrder);
        try {
            orderBarcode = orderBarcode.toUpperCase();
            if(orderBarcode.contains("PP:") || orderBarcode.contains("//tote-")){
                licensePlateScan = true;
            }
            String orderNum = orderBarcode.replaceAll("\\*|[rR].*", "");
            System.out.println("Barcode: " + orderBarcode);
            System.out.println("order Id: " + orderNum);
            //run result set


            String sql = "execute dbo.packAppLoadOrder '" + orderNum+"'";


            System.out.println(sql);
            //   Session sess = HibernateSession.currentSession();
            TimeStamp ts1 = new TimeStamp("load order begin");
            TimeStamp ts2 = new TimeStamp("wholethingy");
            // rs = ps.executeQuery();
            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
            ts1.print("Timer One:  end load");
            //parse results
            StringBuffer xml = new StringBuffer();
            if (rs.next()) {
                //Order void test
                if (rs.getInt("is_void") == 1) throw new Exception(orderNum + " Has been Voided " +
                        "Since it's been picked. ");
                if(!rs.getString("facility_code").equals(Facility)){
                    throw new Exception("You are in the wrong warehouse to pack this order. Order is Assigned to "+rs.getString("facility_code"));
                }
                if(licensePlateScan){
                    System.out.println("Move over barcode to test because of license plate");
                    orderBarcode = rs.getString("barcode_when_lp_assigned");
                }

                if (!rs.getString("order_num_barcode").equals(orderBarcode))
                    throw new Exception(orderNum + " Has been reprinted since it has been picked");
                String orderfkey = rs.getString("order_id");
                //check for already packed
                if (rs.getString("shipped") != (null)) throw new Exception(orderNum + " Has Already been Shipped");
                //get shipinfo
                if( InventoryManager.countInProgress(HibernateSession.currentSession(), rs.getInt("client_fkey"))){
                    throw new Exception("Inventory Count in progress for Client, You cannot ship this order");
                }
                boolean VC = false;
                try{
                    if(rs.getString("VCID").length()>0){
                        VC = true;
                    }
                }catch (Exception e){

                }
                String carrService = rs.getString("carr_service");
                String carrRef = rs.getString("carr_service_ref_num");
                String clientFkey = rs.getString("client_fkey");
                String saturday = rs.getString("ss_saturday");
                String zip = rs.getString("ship_zip");
                String verifiedBox = rs.getString("box_fkey");
                String verifiedWeight = rs.getString("weight_lbs");
                String groupName = rs.getString("group_name");
                String licensePlate = rs.getString("license_plate");

                //Load actual order info
                System.out.println("before getting order");
                xml.append(xmlCreate.beginOrder(rs));
                System.out.println("done getting order");
                rs.close();
                //  ps.close();
                System.out.println(orderfkey + " order fkey");
                rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select packType from package_order  (NOLOCK) where is_void = 0 and owd_order_fkey = " + orderfkey);
                if (rs.next()) {
                    System.out.println("got result " + rs.getString(1));
                    String type = rs.getString(1);
                    if(type.equals("3")){

                    }else if(type.equals("4")){
                        //todo check if this order is in an AB shipment group.

                    }else{
                    throw new Exception(orderNum + " Has Already Been Packed:" + orderfkey);
                }

                }
                rs.close();
                boolean oneline = false;
                ts1.print("before selecting Count");
                rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select count(*) from owd_line_item  (NOLOCK) where order_fkey = " + orderfkey);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count == 1) oneline = true;
                }
              ts1.print("just did the count");
                if (oneline) {
                  /*  pstat = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("SELECT     owd_line_item.*, owd_inventory.*, ISNULL(instructions,'') as 'item_pack_notes' ,url as 'item_pack_url'\n" +
                            "FROM         owd_line_item  (NOLOCK) INNER JOIN\n" +
                            "                      owd_inventory  (NOLOCK) LEFT OUTER JOIN special_instructions (NOLOCK) on inventory_id=external_id and activity_type='INVENTORY-PACK' ON owd_line_item.inventory_id = owd_inventory.inventory_id\n" +
                            "WHERE     (owd_line_item.order_fkey = ?) AND (owd_line_item.quantity_actual > 0) AND (owd_line_item.inventory_num NOT LIKE 'KIT-%') AND \n" +
                            "                      (owd_line_item.inventory_num <> 'ITEM') AND (owd_inventory.is_auto_inventory = 0) AND (owd_line_item.inventory_num NOT LIKE 'KITITEM%')");
                    pstat.setString(1, orderfkey);*/
                    rs1 = HibernateSession.getResultSet(HibernateSession.currentSession(),"execute dbo.getPackSingleLine "+orderfkey);
                } else {

                    rs1 = HibernateSession.getResultSet(HibernateSession.currentSession(),"execute dbo.getPackMultiLines "+orderfkey);
                }

                ts1.print("jot the line items");
                if (rs1.next()) {
                    //load First Item
                    xml.append(xmlCreate.lineItem(rs1, HibernateSession.currentSession()));
                    if(batchingOrder && rs1.getInt("lot_tracking")==1){
                        throw new Exception("You cannot batch pack orders with lots. Please clear batch and pack normally");
                    }
                    while (rs1.next()) {
                        System.out.println("Doing Rest");
                        xml.append(xmlCreate.lineItem(rs1, HibernateSession.currentSession()));
                        if(batchingOrder && rs1.getInt("lot_tracking")==1){
                            throw new Exception("You cannot batch pack orders with lots. Please clear batch and pack normally");
                        }
                    }
                } else {
                    //No line items throw error
                    throw new Exception("No Line items to pack");
                }


                xml.append(xmlCreate.finishOrder());
                xml.append(boxUtil.getBoxes(carrService, carrRef, clientFkey, orderfkey,Facility,groupName));
                //xml.append(Util.verified(Util.orderAlreadyVerified(orderfkey)));
                xml.append(Util.verified("none"));

                xml.append(Util.doVerify());
                ts1.print("timer before extra");
                xml.append(Util.getExtraStuff(saturday));
                ts1.print("timer after extra");
                xml.append(getPrintableLineItems(HibernateSession.currentSession(), orderfkey));
                ts1.print("timer after printables");
                xml.append(orderSortControlXML(orderfkey, Facility, carrService, zip));
                xml.append(xmlCreate.getStartDate());
                xml.append(xmlCreate.verifiedShippingInfo(verifiedWeight,verifiedBox));
                try{
                 if(licensePlate.length()>0){
                     xml.append(xmlCreate.licencePlateInfo(licensePlate,orderBarcode));
                 }
                }catch(Exception e){
                    e.getMessage();
                }
                if(VC){
                    System.out.println("Doing VC order");
                    String vendorCompliance = vendorComplianceUtils.getXMLForPackingFromOrderID(orderfkey);
                    if(vendorCompliance.length()>0){
                        xml.append(vendorCompliance);
                    }else{
                        throw new Exception("Unable to load Vendor Compliance Info. Please contact IT");
                    }

                }
                xml.append(xmlCreate.endOrder());

                rs1.close();
                //pstat.close();

            } else {
                //no results from orderNum, not a valid orderNum in the system
                throw new Exception(orderNum + " is not a Valid order Number");
            }
            ts2.print("Timer two: Alldone returning itall");
            return xml.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Util.errorResponse(ex.getMessage());
        } finally {
            try {
                rs1.close();
            } catch (Exception e) {

            }
            try {
                rs.close();
            } catch (Exception e) {

            }
            try {
                ps.close();
            } catch (Exception e) {

            }
            try {
              //  pstat.close();
            } catch (Exception e) {

            }
            //HibernateSession.closeSession();
        }


    }
    public static boolean checkOrderBeingPacked(String orderFkey) throws Exception{
        boolean beingPacked = false;
        String sql = "select packer_ref from package_order where owd_order_fkey = :orderFkey and is_void = 0";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderFkey",orderFkey);
        List results = q.list();

        if(results.size()>0){
            String id = results.get(0).toString();
            sql = "select firstname + lastname from w_logins where cardnumber = :id";
            Query qq = HibernateSession.currentSession().createSQLQuery(sql);
            qq.setParameter("id",id);
            results = qq.list();
            throw new Exception("It looks you are trying to pack an order that is being worked on by"+ results.get(0).toString() +".  Please check with that person. You more than likely have a duplicate order.");
        }

        return beingPacked;
    }
    public static orderSortControl getOrderSortControlFromPackageOrderFkey(String packageOrderFkey) throws Exception {
             String sql = "select owd_order_fkey, facility from package_order where id = :id";
        orderSortControl osc = new orderSortControl();
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",packageOrderFkey);
        List results = q.list();
        if (results.size()>0){
            Object[] data = (Object[]) results.get(0);
            System.out.println("we have the folowing data: "+data[0].toString()+"  "+data[1].toString());
            osc = getOrderSortControlFromOrderID(data[0].toString(),data[1].toString());

        } else{
            throw new Exception("Unable to get id and facility. Ouch");
        }
        return osc;
    }
    public static orderSortControl getOrderSortControlFromOrderID(String orderId,String facility) throws Exception{
             String sql = "select ship_zip, carr_service from owd_order_ship_info where order_fkey = :orderId";
      orderSortControl osc = new orderSortControl();
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        List resutls = q.list();
        if (resutls.size()>0){
           Object row = resutls.get(0);
            Object[] data = (Object[]) row;
            System.out.println("we have the folowing data: "+data[0].toString()+"  "+data[1].toString());
            osc = orderSortControlCalculate(orderId,facility,data[1].toString(),data[0].toString());
        } else{
            throw new Exception("Unable to load order Data for sorting control. Please try again");
        }

        return osc;
    }
    public static String orderSortControlXML(String orderId,String location,String ship,String zip) throws Exception{
             orderSortControl osc = orderSortControlCalculate(orderId,location,ship,zip);

        return xmlCreate.orderSortControl(osc.getBackgroundColor(),osc.getSortSound(),osc.getSortType(),osc.getSortText(),osc.getSortImageUrl());
    }

    public static orderSortControl  orderSortControlCalculate(String orderId,String location,String ship,String zip) throws Exception{
               orderSortControl osc = new orderSortControl();

               String color="";
        String sound="";
        String type="";
        String text="";
        String image="";
        if(shipMethodUtils.isFedexPlaneShipMethod(ship,location)){
            color="LightCoral";
            osc.setSortSound("whip.wav");
            osc.setSortType("FedExPlane");
            osc.setSortText("Please place this order in \n the Blue FedEx Express Tote.");
            osc.setSortImageUrl("http://it.owd.com/images/fedexplane.jpg");

            osc.setBackgroundColor(color);
            return osc;
        }
        if(shipMethodUtils.isRedOrder(orderId,location)){
            color="LightCoral";
            osc.setSortSound(sound);
            osc.setSortType("RED");
            osc.setSortText("This a RED package, \n please sort accordingly!!");
            osc.setSortImageUrl("http://it.owd.com/images/red.jpg");

            osc.setBackgroundColor(color);
            return osc;
           //
        }

      /*  if(zip.startsWith("5")& location.equals("DC1") & ship.contains("USPS") & !ship.contains("Express")){
            System.out.println("Doing local Postal sort");
            color="DeepSkyBlue";
            sound = "yay.wav";
            type="midwestPostal";
            text="USPS Midwest Shipment,\n" +
                    "Please Sort Accordingly.";
            image="http://it.owd.com/images/midwestPostal.jpg";
        }*/

         osc.setBackgroundColor(color);
        osc.setSortSound(sound);
        osc.setSortType(type);
        osc.setSortText(text);
        osc.setSortImageUrl(image);

         return osc;
       // return xmlCreate.orderSortControl(color,sound,type,text,image);
    }

    public static String updatePackageWeight(String barcode, String weight) throws Exception{
        String sql = "update package set weight_lbs = :weight where pack_barcode = :barcode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("weight",weight);
        q.setParameter("barcode",barcode);
        int rows = q.executeUpdate();
        System.out.printf("We updated this many rows: %s%n",rows);
        if (rows==0) return "error updateting weight";
        HibUtils.commit(HibernateSession.currentSession());
        return "success";

    }
    public static String getPrintableLineItems(Session sess, String orderId) throws Exception{
       StringBuffer sb = new StringBuffer();
           sb.append("<printable>");

        String sql = "execute getPrintableLineItems :orderId ;";
         TimeStamp tsnew = new TimeStamp("Start of print");
        Query q = sess.createSQLQuery(sql);

        q.setInteger("orderId",new Integer(orderId));
        List result = q.list();
         tsnew.print("Just loaded the printables");
              if (result.size()>0){
                  for(Object row : result){
                      Object[] data = (Object[])row;
                      sb.append("<printItem>");
                       sb.append("<stock>");
                      sb.append(data[0].toString());
                      sb.append("</stock>");
                      sb.append("<type>");
                      sb.append(data[1].toString());
                      sb.append("</type>");
                      sb.append("<data>");
                  //    SerializableBlob b = (SerializableBlob) data[2];
                                  //  b.getBytes(1l,Integer.parseInt(b.length()+"")).toString();

                      sb.append(data[2].toString());
                      sb.append("</data>");
                      sb.append("<quantity>");
                      sb.append(data[3]);
                      sb.append("</quantity>");
                      sb.append("<name>");
                      sb.append(data[4]);
                      sb.append("</name>");
                      sb.append("</printItem>");



                  }




              }


        sb.append("</printable>");
        return sb.toString();

    }
    public static String getAllPrintableSkus(Session sess) throws Exception{
        ClientsPrintableSkus printSkus = new ClientsPrintableSkus();
       try{
        String sql = "SELECT\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    dbo.owd_inventory_printable.Stock,\n" +
                "    dbo.owd_inventory_printable.type,\n" +
                "    dbo.owd_inventory_printable.imageurl\n" +
                "FROM\n" +
                "    dbo.owd_inventory_printable\n" +
                "INNER JOIN dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_printable.inventory_fkey = dbo.owd_inventory.inventory_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "ORDER BY\n" +
                "    dbo.owd_client.company_name ASC";
        Query q = sess.createSQLQuery(sql);
       System.out.println("lalalala");
        List results = q.list();
           System.out.println("lululululu");
     if(results.size()>0){
        for(Object row : results){
         Object[] data = (Object[]) row;
            PrintableData print = new PrintableData();
            print.setCompanyName(data[0].toString());
            print.setInventoryNum(data[1].toString());
            print.setStock(data[2].toString());
            print.setType(data[3].toString());
       
         print.setData(data[4].toString());


          printSkus.getPrintableItems().add(print);

        }
           }else
           {
           throw new Exception("No results found for printable skus");    
           }
      printSkus.getResults().setMessage("Success");
       }catch(Exception e){
           printSkus.getResults().setMessage("Error");
           printSkus.getResults().setError(e.getMessage());
       }
        XStream x = new XStream();

           x.alias("ClientPrintables",printSkus.getClass());
        x.alias("Printable", PrintableData.class);

        return xmlCreate.xmlHead+x.toXML(printSkus);

    }
    public static String getLineItemAdditionalData(Session sess,String inventoryFkey,String type) throws Exception{
        StringBuffer sb = new StringBuffer();
            String sql = "select name,expression,capturetime,id,hint,imageurl from owd_inventory_data_rules where inventory_fkey = :inventoryFkey order by sort";
       // String sql = "execute getAdditionalLineData :inventoryFkey";
        Query  q = sess.createSQLQuery(sql);
        q.setString("inventoryFkey",inventoryFkey);
        List results = q.list();
        if (results.size()>0){

            sb.append("<additional_data>");
            for(Object row:results){

                Object[] data = (Object[]) row ;
                System.out.println(data[2]);
                Integer capture = (Integer) data[2];
                if(capture == 0){
                  sb.append("<data>");
                    sb.append("<id>");
                    sb.append(data[3]);
                    sb.append("</id>");
                sb.append("<name>");
                sb.append(data[0]);
                sb.append("</name>");
                sb.append("<expression>");
                sb.append(data[1]);
                sb.append("</expression>");
                     sb.append("<hint>");
                sb.append(data[4]);
                sb.append("</hint>");
                    sb.append("<image>");
                  /*  if (data[5] != null) {
                     SerializableBlob b = (SerializableBlob) data[5];
                                  //  b.getBytes(1l,Integer.parseInt(b.length()+"")).toString();

                      sb.append(org.apache.commons.codec.binary.Base64.encodeBase64String(b.getBytes(1l,Integer.parseInt(b.length()+""))));

                    }*/
                      sb.append(data[5]);
                    sb.append("</image><type>");
                    sb.append(type);
                    sb.append("</type>");

                sb.append("</data>");

                }


            }
          sb.append("</additional_data>");

        }else{
            if(type.equals("1")){
                sb.append("<additional_data><data><id>0</id><name>Serial Number</name><expression></expression><hint>Scan Serial Number on Package</hint><image></image><type>1</type></data></additional_data>\n");
            }
        }


        return   sb.toString();
    }

    public static String getAdditionalIds(String inventoryFkey){
        StringBuilder sb = new StringBuilder();
        sb.append("<additional_ids>");

        try{
            String sql = "select value, qty from owd_inventory_additional_ids where is_active = 1 and inventory_fkey = :inventoryFkey";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("inventoryFkey",inventoryFkey);
            List l = q.list();

            for(Object row:l){
                Object[] data = (Object[]) row;
                sb.append("<id>");
                sb.append("<value>");
                sb.append(data[0].toString());
                sb.append("</value>");
                sb.append("<qty>");
                sb.append(data[1].toString());
                sb.append("</qty>");

                sb.append("</id>");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        sb.append("</additional_ids>");
        return sb.toString();

    }
     public static void packSerialNumber(Session sess, OwdOrder order, OwdInventory inv, String serialNum, String linefkey) throws Exception {
         //todo skip creation for is_serial = 1
        int invid = inv.getInventoryId().intValue();
        boolean foundLine = false;
          System.out.println(serialNum +"999999999");
         OwdLineItem li = (OwdLineItem) sess.load(OwdLineItem.class,Integer.decode(linefkey));
         OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess, li.getOwdInventory(), serialNum);

                System.out.println("qty=" + li.getQuantityActual());
                System.out.println(li.getSerials());

                    //foundone!
                    foundLine = true;
                    if (li.getSerials().contains(serial)) {
                        throw new Exception("Duplicate serial number - cannot add " + serialNum + " to line item");
                    }

                    li.getSerials().add(serial);

                    if (serial.getLineItems() == null) {
                        serial.setLineItems(new HashSet());
                    }

                    serial.getLineItems().add(li);
                    sess.save(serial);
                    sess.save(li);
       /* Iterator it = order.getLineitems().iterator();
        while (it.hasNext() && !foundLine) {
            OwdLineItem li = (OwdLineItem) it.next();
            if (li.getOwdInventory().getInventoryId().intValue() == invid) {
                OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess, li.getOwdInventory(), serialNum);

                //  //System.out.println("qty=" + li.getQuantityActual());
                //  //System.out.println(li.getSerials());

                    //foundone!
                    foundLine = true;
                    if (li.getSerials().contains(serial)) {
                        throw new Exception("Duplicate serial number - cannot add " + serialNum + " to line item");
                    }

                    li.getSerials().add(serial);

                    if (serial.getLineItems() == null) {
                        serial.setLineItems(new HashSet());
                    }

                    serial.getLineItems().add(li);
                    sess.save(serial);
                    sess.save(li);



            }

        }*/

        if (!foundLine) {
            throw new Exception("No matching lines found for item!");
        }
    }
    public static void insertAdditionalDataAsSerial(Session sess, String dataRuleId, String inventoryFkey, String data, String OrderFkey,String grouping, String linefkey) throws Exception{
       
            //todo update so it checks duplicate properly
          OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, Integer.decode(OrderFkey));
            OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, Integer.decode(inventoryFkey));
        String serial;
          if(dataRuleId.equals("0")){
              serial = data;
          }else {
              String sql = "select prefix from owd_inventory_data_rules where id = :id";

              Query q = sess.createSQLQuery(sql);
              q.setInteger("id", new Integer(dataRuleId));
              List result = q.list();
              String prefix = result.get(0).toString();
              System.out.println(prefix);
              serial = grouping + "|" + prefix + data;
          }
          

                System.out.println("Serial is a new one");
        if(inv.getRequireSerialNumbers()>1) {
            if (SerialNumberManager.serialExists(inv, serial))
                throw new Exception("Duplicate Serial Data. Contact IT immediately");
        }
        if(checkSerialIsAlreadyUsed(serial,inventoryFkey)) throw new Exception("Duplicate Serial Data. Contact IT immediately");
                SerialNumberManager.loadOrCreateSerialNumber(sess,inv,serial);
        System.out.println("after serial");
               packSerialNumber(sess,order,inv,serial,linefkey);



        





    }

    public static boolean checkSerialIsAlreadyUsed(String serial, String inventoryFkey) throws Exception{
        boolean isFound = false;
            String sql = "execute dbo.sp_checkIfSerialNumberIsAlreadyUsedNew :serial, :inventoryFkey";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("serial","%"+serial);
        q.setParameter("inventoryFkey",inventoryFkey);
        List l = q.list();
        if(l.size()>0){
            isFound = true;

        }


        return isFound;
    }

    public static boolean checkforAndRemoveSerialsForId1(Session sess, String orderfkey) throws Exception{
        boolean good = false;
        String sqlSelectLineItems = "SELECT\n" +
                "    dbo.owd_line_item.line_item_id\n" +
                "FROM\n" +
                "    dbo.owd_line_item\n" +

                "INNER JOIN dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_line_item.order_fkey = :orderfkey\n" +
                "AND dbo.owd_inventory.is_serialized = 1\n" +
                "group by line_item_id";
        Query q = sess.createSQLQuery(sqlSelectLineItems);
        q.setString("orderfkey",orderfkey);
        List results = q.list();

        for (Object row:results){
            String sqlcheck = "select * from owd_line_serial_link where line_fkey = :lineFkey";
            System.out.println("doing remove for lineItem" + row.toString());
            String sqlRemoveLineLink ="delete from owd_line_serial_link where line_fkey = :lineFkey";

            Query qcheck = sess.createSQLQuery(sqlcheck);
            qcheck.setString("lineFkey",row.toString());
            if(qcheck.list().size()>0){




                Query qq = sess.createSQLQuery(sqlRemoveLineLink);
               int res = 0;

                qq.setString("lineFkey",row.toString());
                res = qq.executeUpdate();
                if (res == 0) throw new Exception("Failed to remove serials");

            }else{
                System.out.println("There are no line item serials store, there was bad error skipping this step");
            }
        }




        return good;
    }


    public static boolean checkforAndRemoveSerialsForId2(Session sess, String orderfkey) throws Exception{
        boolean good = false;
         String sqlSelectLineItems = "SELECT\n" +
                 "    dbo.owd_line_item.line_item_id\n" +
                 "FROM\n" +
                 "    dbo.owd_line_item\n" +
                 "INNER JOIN dbo.owd_inventory_data_rules\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.owd_line_item.inventory_id = dbo.owd_inventory_data_rules.inventory_fkey\n" +
                 "    )\n" +
                 "INNER JOIN dbo.owd_inventory\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id\n" +
                 "    )\n" +
                 "WHERE\n" +
                 "    dbo.owd_line_item.order_fkey = :orderfkey\n" +
                 "AND dbo.owd_inventory.is_serialized = 2\n" +
                 "group by line_item_id";
        Query q = sess.createSQLQuery(sqlSelectLineItems);
        q.setString("orderfkey",orderfkey);
        List results = q.list();

        for (Object row:results){
              String sqlcheck = "select * from owd_line_serial_link where line_fkey = :lineFkey";
            System.out.println("doing remove for lineItem" + row.toString());
            String sqlRemoveLineLink ="delete from owd_line_serial_link where line_fkey = :lineFkey";
            String sqlRemoveSerialForLine = "delete from owd_inventory_serial where id in(\n" +
                    "\n" +
                    "SELECT\n" +
                    "    dbo.owd_inventory_serial.id\n" +
                    "FROM\n" +
                    "    dbo.owd_line_serial_link\n" +
                    "INNER JOIN dbo.owd_inventory_serial\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_line_serial_link.serial_fkey = dbo.owd_inventory_serial.id\n" +
                    "    )\n" +
                    "WHERE\n" +
                    "    dbo.owd_line_serial_link.line_fkey = :lineFkey )\n" +
                    ";" ;
            Query qcheck = sess.createSQLQuery(sqlcheck);
            qcheck.setString("lineFkey",row.toString());
            if(qcheck.list().size()>0){


             Query qq = sess.createSQLQuery(sqlRemoveSerialForLine);
            qq.setString("lineFkey",row.toString());
            int res = qq.executeUpdate();
            if (res == 0) throw new Exception("Failed to remove serials");
            qq = sess.createSQLQuery(sqlRemoveLineLink);
            res = 0;

             qq.setString("lineFkey",row.toString());
             res = qq.executeUpdate();
            if (res == 0) throw new Exception("Failed to remove serials");

            }else{
                System.out.println("There are no line item serials store, where was bad error skipping this step");
            }
        }




        return good;
    }


     public static String orderAlreadyVerified(String orderNum) throws Exception {
        System.out.println(orderNum);
        Connection conn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        Statement stmt = conn.createStatement();
       try{
        String sqlQuery = "select verify_by from order_verify_history (NOLOCK) where order_fkey=" + orderNum;
        ResultSet rs1 = stmt.executeQuery(sqlQuery);
        if (rs1.next()) {
            System.out.println("in rs if");


            String name = rs1.getString(1);
            stmt.close();
            rs1.close();

            if (name.length()>1) return name;

            return "none";
        }
        System.out.println("after name");
        rs1.close();
        return "none";
       } catch (Exception e){
           e.printStackTrace();
           return "none";
       }
    }


    public static String verified(String s){
        return "<verified>"+s+"</verified>";
    }
    public static String doVerify(){

        return "<verify>yes</verify>";
    }
    public static String getExtraStuff(String saturday){
        StringBuffer sb = new StringBuffer();
        sb.append("<Extra>");
       if(saturday.equals("1")){
        sb.append("<Eitem>");
        sb.append("<Edata>");
        sb.append("This order is Saturday Delivery.  Please Add Saturday Sticker to each package");

           sb.append("</Edata>");
             sb.append("</Eitem>");
       }
        sb.append("</Extra>");
        return sb.toString();

    }

    public static String clearTote(String tote){
        String sql = "execute clearToteByToteId :tote";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("tote",tote);
            int i = q.executeUpdate();
            if(i==1){
                HibUtils.commit(HibernateSession.currentSession());
            }else{
                throw new Exception("Clear did not work");
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "success";
    }

    public static String getClientAndUnitsString(String orderId){

        String sql = "SELECT\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    SUM(dbo.owd_line_item.quantity_actual) as units\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_line_item\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id)\n" +
                "WHERE\n" +
                "    dbo.owd_order.order_id = :orderId\n" +
                "GROUP BY\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.owd_client.company_name ;";
        String s = "";
            try{
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("orderId",orderId);
                List l = q.list();
                if(l.size()>0){
                    Object row = l.get(0);
                    Object[] data = (Object[]) row;
                    if(data[0].toString().length()>0){
                        s = data[0].toString() + " - " + data[1].toString()+ "\r\n Units: "+ data[2].toString();
                    }else{
                        s = data[1].toString()+ "\r\nUnits: "+ data[2].toString();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        return s;

    }

    public static void processAdditionalPackageAddons(NodeIterator addons, String packageId)throws XPathException {
         try{
             ArrayList<PackageAddons> packageAddons = new ArrayList<>();
             Node ns;
             while ((ns = addons.nextNode()) != null) {

                 String owdBoxTypeFkey = XPathAPI.eval(ns, "./owdBoxTypeFkey").toString();
                 String supplyTrackingFkey = XPathAPI.eval(ns, "./supplyTrackingFkey").toString();
                 PackageAddons addon = new PackageAddons();
                 addon.setPackageFkey(packageId);
                 addon.setOwdBoxTypeFkey(owdBoxTypeFkey.equals("") ? null : Integer.parseInt(owdBoxTypeFkey));
                 addon.setSupplyTrackingFkey(supplyTrackingFkey.equals("") ? null : Integer.parseInt(supplyTrackingFkey));
                 addon.setQuantity(Integer.parseInt(XPathAPI.eval(ns,"./quantity").toString()));
                 packageAddons.add(addon);
             }
             for(PackageAddons addon: packageAddons) {
                 HibernateSession.currentSession().save(addon);
             }
             HibUtils.commit(HibernateSession.currentSession());

         }catch(Exception ex){
             throw new XPathException(ex);
        }
    }

    private final static Logger log = LogManager.getLogger();
    public static void main(String[] args){

        try{
           //  System.out.println(getLineItemAdditionalData(HibernateSession.currentSession(), "36351", "1"));
    // System.out.println(Util.loadOrder("*11135241*"));
          //  System.out.println(Util.loadOrder("*21039635*","DC1","false"));
           // System.out.println(checkSerialIsAlreadyUsed("987","36351"));
            checkforAndRemoveSerialsForId1(HibernateSession.currentSession(),"19339572");
         //  System.out.println(updatePackageWeight("p4403541*11175206*b1","1.65"));
        // orderSortControl osc = getOrderSortControlFromPackageOrderFkey("4938733");
        //   System.out.println(osc.getBackgroundColor());
        /*    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 36351);
            if(SerialNumberManager.serialExists(inv,"123456")) throw new Exception("Duplicate Serial Data. Contact IT immediately");
            log.debug("after serial");
            if(checkSerialIsAlreadyUsed("123456","36351")) throw new Exception("Duplicate Serial Data. Contact IT immediately");
            log.debug(checkSerialIsAlreadyUsed("12345","101505"));
            log.debug("after other check");*/
          /*  String sql = "select prefix from owd_inventory_data_rules where id = :id";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id",31);
            log.debug("Before");
            q.list();
            log.debug("After");*/

           // System.out.println(getClientAndUnitsString("13864877"));

          //  System.out.println(getAdditionalIds("36351"));

          //  Util.loadOrder("*11134888*");
        // System.out.println(getAllPrintableSkus(HibeSession.currentSession()));
        }catch(Exception e){
               e.printStackTrace();
        }
      //  System.out.println(login("51"));
    }
  
    
}
