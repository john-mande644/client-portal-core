package com.owd.dc.warehouse.ABShipments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.WMSUtils;
import com.owd.connectship.xml.api.OWDShippingRequest.*;
import com.owd.connectship.xml.api.OWDShippingResponse.*;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.manifest.ShipServerServlet;
import com.owd.dc.manifest.api.ShipRequest;
import com.owd.dc.packing.*;
import com.owd.dc.packing.beans.OrderPackagesBean;
import com.owd.dc.packing.beans.PackageBoxBean;
import com.owd.dc.packing.dcholds.holdUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.thoughtworks.xstream.XStream;
import com.owd.core.business.order.OrderUtilities;

import org.hibernate.Criteria;
import org.hibernate.Query;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by danny on 9/10/2015.
 */
public class ABUtils {


  /*  static Unmarshaller OWDShippingRequestUnmarshaller;

    static {
        try {
            OWDShippingRequestUnmarshaller = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingRequest").createUnmarshaller();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/



    public static void markAllPrintedPackagesShipped() throws Exception{

        try{
            String sql = "SELECT\n" +
                    "    dbo.package_order.owd_order_fkey " +
                    "\n" +
                    "FROM\n" +
                    "    dbo.package_order\n" +
                    "INNER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "WHERE\n" +
                    "    dbo.package_order.is_void = 0\n" +
                    "AND dbo.package.label_printed = 1\n" +
                    "AND dbo.package.shipment_posted = 0 ;";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);

            List l = q.list();
            System.out.println("We have : "+ l.size());

            for(Object row:l){
               System.out.println("doing "+ row.toString());
                updateShipInfoAndPackagePosted(row.toString());



            }



        }catch(Exception e){
            e.printStackTrace();
        }



    }

    public static void main(String args[]) {

       // System.out.println(packAndPreshipOrder(9460167));
       // System.out.println(isWeightVerifiedPacked("9460167"));
       // packAndShipOrderSinglePackage("19490579","584","0.41250");

      System.out.println(materialHandlingShipPackage("p15812636*30914799*b1","1.5","DC1"));

    /*    packageBean pb = new packageBean();
      try {
          loadBoxInfo(pb, "40", "5");
          System.out.println(pb.getBoxCost());

      }catch (Exception e){
          e.printStackTrace();
      }*/

        //  Map<String,String> m = new TreeMap<String, String>();
        //m.put("P1234*12345678*b1", "3");

        try {
         /*   Calendar c = Calendar.getInstance();
            System.out.println(c.getTimeInMillis());
            Unmarshaller OWDShippingRequestUnmarshaller = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingRequest").createUnmarshaller();
            System.out.println(c.getTimeInMillis());*/
            //  resetWVShipment("9460167");
           // System.out.println(isShippingFromPackageBarcode("p7405996*16816974*b1"));
          //  resetWVShipment("9666243");
          //  OrderUtilities.stopShipping("9666243");
         //  System.out.println(packAndPreshipOrder(11776825));

          //  System.out.println(packAndPreshipOrder(11776821));
          //  System.out.println(packAndPreshipOrder(11776827));
          // updateShipInfo(11791895 + "");
/*
List<String> l = new ArrayList();

            for(String s: l){
                updateShipInfoAndPackagePosted(s);
            }
*/

           // markAllPrintedPackagesShipped();

          //  updateTrackingShipDate("12035213");

         // voidWVOrder("11776537");
            //System.out.println(loadShipXml(m,"DC1"));
            //cancelPack("9460167");
        } catch (Exception e) {
            e.getMessage();
        }


    }


    public static boolean isWeightVerifiedPacked(String orderId){
        boolean isWV = false;
            try{
                String sql = "SELECT\n" +
                        "    dbo.package_order.id\n" +
                        "FROM\n" +
                        "    dbo.owd_order_packs\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.package_order\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_order_packs.order_fkey = dbo.package_order.owd_order_fkey)\n" +
                        "WHERE\n" +
                        "    dbo.owd_order_packs.order_fkey = :orderId\n" +
                        "AND dbo.package_order.packType > 2\n" +
                        "AND dbo.package_order.packType < 5 " +
                        "AND is_void = 0" +
                        ";";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("orderId",orderId);
                if(q.list().size()>0){
                    isWV = true;

                }
            }catch (Exception e){
                e.printStackTrace();
            }


        return isWV;

    }
    public static boolean isWeightVerifiedOrder(String orderId){
        boolean isWV = false;
        try{
            String sql = "select id from owd_order_packs where order_fkey = :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            if(q.list().size()>0){
                isWV = true;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isWV;
    }
    public static String packAndPreShipPackagePerUnitOrder(Integer orderId){
        String status = "Error: ";
        String orderNum = "";
        //todo check for valid multipicePerunit order

        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            String facility = order.getFacilityCode();

            //pack
            Map<String, String> packages = packOrderPackagePerUnit(order);
            //Ship

            String shipped = shipOrder(packages, facility);
            if (shipped.startsWith("Error")) {
                cancelPack(orderId.toString());

                throw new Exception(shipped);
            }
            OrderUtilities.startShipping(orderId + "");
        }catch (Exception e){
            status = status + e.getMessage();
            //todo dchold
            System.out.println("This is where we would dchold");
            if(!e.getMessage().contains("DC Hold")&&orderNum.length()>0){
                holdUtils.setShippingHoldForOrderNum(orderNum,"WVShipment",e.getMessage());
            }
            return status;
        }

        return status;
    }

    public static Map<String,String> packOrderPackagePerUnit(OwdOrder order) throws Exception{

        Map<String, String> packages = new TreeMap<String, String>();

        boolean first = true;
        int packageNumber = 1;
        if(order.getLineitems().size()>1) throw new Exception("This order does not qualify for this shipment type. Too many line items");
        OwdLineItem lineItem = (OwdLineItem) order.getLineitems().toArray()[0];

        for(int i = 0;i<lineItem.getQuantityActual();i++){
            packageBean pb = new packageBean();
            pb.setOrderFkey(order.getOrderId().toString());
            pb.setPacker("2");
            pb.setBoxNumber(packageNumber+"");
            pb.setStart(WMSUtils.getDateAMPM());
            pb.setStop(WMSUtils.getDateAMPMAddSeconds(1));
            pb.setFacility(order.getFacilityCode());
            pb.setScannedBarcode(order.getOrderNumBarcode());
            //load box info and packType
            OwdInventory inv = lineItem.getOwdInventory();
            pb.setBoxFkey("1");
            pb.setBoxCost("0");
            pb.setDepth(inv.getLength()+"");
            pb.setWidth(inv.getWidth()+"");
            pb.setHeight(inv.getHeight()+"");
            pb.setWeight(inv.getWeightLbs()+"");
            pb.setPackType("4");

            List<packItemBean> packedLines = new ArrayList<packItemBean>();
            packItemBean pib = new packItemBean();
            pib.setLineKey(lineItem.getLineItemId().toString());
            pib.setQty("1");
            packedLines.add(pib);
            pb.setPackedLines(packedLines);

            System.out.println(pb.getPackType());
            //load line items

            System.out.println(pb.getPackedLines().size());

            System.out.println(pb.getXml());
            String results = multiBox.insertMultiBox(pb.getXmlWithBoxNumber(),first);
            System.out.println(results);
            if (results.startsWith("PKGid:")) {
                packages.put(results.replace("PKGid:", ""), pb.getWeight());
            } else {
                throw new Exception(results);
            }
            packageNumber++;
            first = false;
        }



        return packages;
    }
    public static String packAndPreshipOrder(String orderId) {
        return packAndPreshipOrder(Integer.parseInt(orderId));
    }

    public static String packAndPreshipOrder(Integer orderId) {
        String status = "Error: ";
        String orderNum="";
        try {
            if (!checkPreshipEligible(orderId)) {
                status = status + "This is not a qualifying order";
                return status;
            }
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            orderNum=order.getOrderNum();
            String facility = order.getFacilityCode();
            System.out.println("This is the facility for this order: " + facility);
            Map<String, String> packages = packOrder(order);
            System.out.println(packages.keySet());
            String shipped = shipOrder(packages, facility);
            if (shipped.startsWith("Error")) {
                cancelPack(orderId.toString());

                throw new Exception(shipped);
            }
            OrderUtilities.startShipping(orderId + "");

        } catch (Exception e) {
            status = status + e.getMessage();
            //todo dchold
            System.out.println("This is where we would dchold");
            if(!e.getMessage().contains("DC Hold")&&orderNum.length()>0){
                holdUtils.setShippingHoldForOrderNum(orderNum,"WVShipment",e.getMessage());
            }
            return status;
        }
        status = "Success";

        return status;
    }
    public static String packAndShipOrderSinglePackage(String orderId, String boxType, String weight) {
                return packAndShipOrderSinglePackage(Integer.parseInt(orderId),boxType,weight);

    }
    public static String packAndShipOrderSinglePackage(Integer orderId, String boxType, String weight) {
        String status = "Error: ";
        String orderNum="";
        try {

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            orderNum=order.getOrderNum();
            String facility = order.getFacilityCode();
            System.out.println("This is the facility for this order: " + facility);
            OwdBoxtypes box = (OwdBoxtypes) HibernateSession.currentSession().load(OwdBoxtypes.class,Integer.parseInt(boxType));
            BigDecimal w = BigDecimal.valueOf(Double.parseDouble(weight));
            BigDecimal finalWeight = w.add(box.getWeight());


            Map<String, String> packages = packOrder(order,boxType,finalWeight.toString());
            System.out.println(packages.keySet());
            String shipped = shipOrder(packages, facility);
            if (shipped.startsWith("Error")) {
                cancelPack(orderId.toString());

                throw new Exception(shipped);
            }
           // OrderUtilities.startShipping(orderId + "");
            //Since this is not a preship and label will be generated shortly, post the tracking info
            updateShipInfoAndPackagePosted(orderId+"");
        } catch (Exception e) {
            status = status + e.getMessage();
            //todo dchold
            System.out.println("This is where we would dchold");
            if(!e.getMessage().contains("DC Hold")&&orderNum.length()>0 && !e.getMessage().contains("Error: null")){
                holdUtils.setShippingHoldForOrderNum(orderNum,"WVShipment",e.getMessage());
            }
            return status;
        }
        status = "Success";

        return status;
    }
   public static String voidWVOrder(String orderId){
        String status = "error";

        try{
            //verify this is a qualifying shipment for Weight Verified and then void

            if(OrderUtilities.isShipping(orderId)){
              //  VOIDRESPONSE response = new VOIDRESPONSE();
                VOIDREQUEST request = new VOIDREQUEST();
                List<PackageBoxBean> packages = voidUtil.getPackagesListOrderId(HibernateSession.currentSession(),orderId);


                System.out.println("Loaded packages");
                for(PackageBoxBean p : packages){
                   request.getBARCODE().add(p.getBarcode());
                }
                System.out.println(request.getBARCODE());
                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(orderId));
                request.setLOCATIONCODE(order.getFacilityCode());
                request.setSHIPPER(ManifestingManager.getLiveShipperForLocation(order.getFacilityCode()));
                System.out.println(request.getSHIPPER());
                OWDSHIPPINGREQUEST request1 = new OWDSHIPPINGREQUEST();
                System.out.println("1");
                OWDSHIPPINGRESPONSE responder = new OWDSHIPPINGRESPONSE();
                System.out.println("2");
                request1.setVOIDREQUEST(request);
                request1.setApi_Version("1.0");
                System.out.println("3");

                ShipServerServlet.handleOWDSHIPPINGREQUEST(responder,request1);
                System.out.println("4");
                System.out.println(responder.getError_Type());

                System.out.println(responder.getError_Response());
                if(null != responder.getError_Response()) {
                    status = "Error: " + responder.getError_Response();
                }else{
                    status = "success";
                }



            }else{
                throw new Exception("This is not a qualifying shipment to void this way");
            }





        }catch(Exception e){
            e.printStackTrace();
            status = "Error: "+ e.getMessage();

        }

        return status;
    }
    private static String shipOrder(Map<String, String> packages, String facility) {
        String status = "error";
        try {
            String xml = loadShipXml(packages, facility);

            OWDSHIPPINGREQUEST request;
            Unmarshaller OWDShippingRequestUnmarshaller = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingRequest").createUnmarshaller();


            request = (OWDSHIPPINGREQUEST) OWDShippingRequestUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));

            OWDSHIPPINGRESPONSE responder = new OWDSHIPPINGRESPONSE();
            ShipServerServlet.handleOWDSHIPPINGREQUEST(responder, request);
            System.out.println("The responder");
            System.out.println(responder.getError_Type());

            System.out.println(responder.getError_Response());

            if(null != responder.getError_Response()) {
                status = "Error: " + responder.getError_Response();
            }else{
              SHIPMENT ship =  responder.getSHIPRESPONSE().getSHIPMENT().get(0);


                LABELDATA data = ship.getLABELDATA().get(0);
               // boolean recordLabel = recordLabelStringForMSN(ship.getMSN(),data.getValue());


                status = "success";
            }


        } catch (Exception e) {
            e.printStackTrace();
            status = "Error: " + e.getMessage();
        }

        return status;
    }

    private static boolean recordLabelStringForMSN(String msn, String label) throws  Exception{
        boolean success = false;
        String sql = "update package set label_string = :label" +
                "\n" +
                "where order_track_fkey = (select order_track_id from owd_order_track where msn = :msn)";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("label",label);
        q.setParameter("msn",msn);
        int i = q.executeUpdate();

        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
            success = true;
        }


        return success;
    }


    private static String loadShipXml(Map<String, String> packages, String facility) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<OWDSHIPPINGREQUEST xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" api_version=\"55\" client_id=\"55\" client_authorization=\"51\">");
        sb.append("<SHIPREQUEST LOCATIONCODE=\"");
        sb.append(facility);
        sb.append("\" ");
        sb.append("SHIPPER=\"");
        sb.append(ManifestingManager.getLiveShipperForLocation(facility));
        sb.append("\" ");
        sb.append("LABELPRINTER=\"Zebra.ZebraZ4Mplus\">");
        sb.append("<PACKAGELIST>");
        for (String s : packages.keySet()) {
            sb.append("<PACKAGE>");
            sb.append("<BARCODE>");
            sb.append(s);
            sb.append("</BARCODE>");
            sb.append("<WEIGHT_LBS>");
            sb.append(packages.get(s));
            sb.append("</WEIGHT_LBS></PACKAGE>");

        }
        sb.append("</PACKAGELIST></SHIPREQUEST></OWDSHIPPINGREQUEST>");
        return sb.toString();

    }

    private static void cancelPack(String orderId) throws Exception {

        String results = multiBox.cancelPack(orderId, WMSUtils.getDateAMPM(), "2");
        if (results.startsWith("Error")) {
            throw new Exception(results + " Please notify IT");

        }


    }
    public static void resetWVShipment(String orderId) throws Exception{
        // if package_order packer_ref is still 2 cancel pack.
        try {
            String sql = "select packer_ref from package_order where owd_order_fkey = :orderId and is_void = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List results = q.list();
            if (results.size() > 0) {
                System.out.println("PackerRef: " + results.get(0).toString());
                if (results.get(0).toString().equals("2")) {
                    cancelPack(orderId);
                }else{
                    System.out.println("No longer a wv preship");
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }





    }

    public static Map<String, String> packOrder(OwdOrder order) throws Exception {
        Map<String, String> packages = new TreeMap<String, String>();
        packageBean pb = new packageBean();
        pb.setOrderFkey(order.getOrderId().toString());
        pb.setPacker("2");
        pb.setStart(WMSUtils.getDateAMPM());
        pb.setStop(WMSUtils.getDateAMPMAddSeconds(1));
        pb.setFacility(order.getFacilityCode());
        pb.setScannedBarcode(order.getOrderNumBarcode());
        //load box info and packType
        loadBoxInfo(pb);

        System.out.println(pb.getPackType());
        //load line items
        loadLineItems(pb, order);
        System.out.println(pb.getPackedLines().size());

        System.out.println(pb.getXml());
        String results = singleBox.insertSingleBox(pb.getXml());
        System.out.println(results);
        if (results.startsWith("Success")) {
            packages.put(results.replace("Success|PKGid:", ""), pb.getWeight());
        } else {
            throw new Exception(results);
        }

        return packages;
    }
    public static Map<String, String> packOrder(OwdOrder order,String boxType, String weight) throws Exception {
        Map<String, String> packages = new TreeMap<String, String>();
        packageBean pb = new packageBean();
        pb.setOrderFkey(order.getOrderId().toString());
        pb.setPacker("2");
        pb.setStart(WMSUtils.getDateAMPM());
        pb.setStop(WMSUtils.getDateAMPMAddSeconds(1));
        pb.setFacility(order.getFacilityCode());
        pb.setScannedBarcode(order.getOrderNumBarcode());
        //load box info and packType
        loadBoxInfo(pb,boxType,weight);

        System.out.println(pb.getPackType());
        //load line items
        loadLineItems(pb, order);
        System.out.println(pb.getPackedLines().size());

        System.out.println(pb.getXml());
        String results = singleBox.insertSingleBox(pb.getXml());
        System.out.println(results);
        if (results.startsWith("Success")) {
            packages.put(results.replace("Success|PKGid:", ""), pb.getWeight());
        } else {
            throw new Exception(results);
        }

        return packages;
    }


    private static void loadLineItems(packageBean pb, OwdOrder order) throws Exception {
        List<packItemBean> packedLines = new ArrayList<packItemBean>();
        for (OwdLineItem item : order.getLineitems()) {
            packItemBean pib = new packItemBean();
            pib.setLineKey(item.getLineItemId().toString());
            pib.setQty(item.getQuantityActual().toString());
            packedLines.add(pib);
        }
        pb.setPackedLines(packedLines);
    }

    private static void loadBoxInfo(packageBean pb) throws Exception {
        String sql = "SELECT\n" +
                "    dbo.owd_order_packs.box_fkey,\n" +
                "    dbo.owd_boxtypes.cost,\n" +
                "    dbo.owd_boxtypes.dim_depth,\n" +
                "    dbo.owd_boxtypes.dim_width,\n" +
                "    dbo.owd_boxtypes.dim_height,\n" +
                "    dbo.owd_order_packs.weight_lbs,\n" +
                "    dbo.owd_order_packs.AB_shipment\n" +
                "FROM\n" +
                "    dbo.owd_order_packs\n" +
                "INNER JOIN\n" +
                "    dbo.owd_boxtypes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order_packs.box_fkey = dbo.owd_boxtypes.id)\n" +
                "WHERE\n" +
                "    dbo.owd_order_packs.order_fkey = :orderId ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId", pb.getOrderFkey());
        List results = q.list();
        if (results.size() > 0) {
            Object[] data = (Object[]) results.get(0);
            pb.setBoxFkey(data[0].toString());
            pb.setBoxCost(data[1].toString());
            pb.setDepth(data[2].toString());
            pb.setWidth(data[3].toString());
            pb.setHeight(data[4].toString());
            pb.setWeight(data[5].toString());
            if (data[6].toString().equals("0")) {
                pb.setPackType("3");
            }
            if (data[6].toString().equals("1")) {
                pb.setPackType("4");
            }
        } else {
            throw new Exception("Unable to lookup box info");
        }


    }
    private static void loadBoxInfo(packageBean pb,String boxType, String weight) throws Exception {
        String sql = "SELECT id,\n" +

                "    dbo.owd_boxtypes.cost,\n" +
                "    dbo.owd_boxtypes.dim_depth,\n" +
                "    dbo.owd_boxtypes.dim_width,\n" +
                "    dbo.owd_boxtypes.dim_height\n" +

                "FROM\n" +

                "    dbo.owd_boxtypes\n" +

                " where id  = :Id ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("Id", boxType);
        List results = q.list();
        if (results.size() > 0) {
            Object[] data = (Object[]) results.get(0);
            pb.setBoxFkey(data[0].toString());
            pb.setBoxCost(data[1].toString());
            pb.setDepth(data[2].toString());
            pb.setWidth(data[3].toString());
            pb.setHeight(data[4].toString());
            pb.setWeight(weight);

        } else {

            throw new Exception("Unable to lookup box info");
        }


    }
    public static boolean checkPreshipEligible(Integer orderId) {
        boolean status = false;
        String sql = "SELECT\n" +
                "    isnull(dbo.owd_order_packs.id,0) as wv,\n" +
                "    dbo.owd_order.order_status,\n" +
                "    isnull(dbo.package_order.id,0) as packed\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_order_packs\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_packs.order_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey and dbo.package_order.is_void = 0)\n" +
                "WHERE\n" +
                "    dbo.owd_order.order_id = :orderId\n";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List results = q.list();
            if (results.size() > 0) {
                Object[] data = (Object[]) results.get(0);
                // status needs to be at warehouse, wv needs to not be zero so it is a weight verified order
                // packed needs to be 0 meaning it was never packed
                if (data[1].toString().equals("At Warehouse")) {
                    if (Integer.parseInt(data[0].toString()) > 0) {
                        if (Integer.parseInt(data[2].toString()) == 0) {
                            status = true;
                        }
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }


        return status;
    }

    public static void updateShipInfo(String orderId) throws Exception{
        String sql = "exec update_order_shipment_info :orderId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        int i = q.executeUpdate();
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }
        OrderUtilities.stopShipping(orderId);


    }


    public static void updateTrackingShipDate(String orderId) throws Exception{
        String sql = "update owd_order_track set ship_date = Convert(date,getDate()) where order_fkey = :orderId and is_void = 0";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        int i = q.executeUpdate();
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }
    }

    public static void updateShipInfoAndPackagePosted(String orderId) throws Exception{
        String sql = "update package set shipment_posted = 1 where package_order_fkey = (select id from package_order where owd_order_fkey = :orderId and is_void = 0)";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        int i = q.executeUpdate();
        if (i>0){
            updateTrackingShipDate(orderId);
            updateShipInfo(orderId);
            HibUtils.commit(HibernateSession.currentSession());

        }





    }

    private static SHIPREQUEST loadShipRequest(String packageBarcode, String weight,String locationCode){
        SHIPREQUEST shipRequest = new SHIPREQUEST();
        shipRequest.setLOCATIONCODE(locationCode);
        shipRequest.setSHIPPER(locationCode);
        PACKAGE p = new PACKAGE();
        p.setBARCODE(packageBarcode);
        p.setWEIGHT_LBS(weight);
        PACKAGELIST pl = new PACKAGELIST();
        pl.getPACKAGE().add(p);
        shipRequest.setPACKAGELIST(pl);
        return shipRequest;
    }

    public static String materialHandlingShipPackage(String packageBarcode, String weight,String locationCode){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();


        materialHandlingShipResponse mhResponse = new materialHandlingShipResponse();
        mhResponse.setLpn(packageBarcode);
        //set weight on package
        try{
            String sql = "execute setPackageWeight :packageBarcode,:weight ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("packageBarcode",packageBarcode);
            q.setParameter("weight",weight);
            int i = q.executeUpdate();
            if(i<1){
                throw new Exception("Update failed");
            }
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
           mhResponse.setMessage("Unable to update weight");
            return gson.toJson(mhResponse);
        }

        //create shipmentREquest

        SHIPREQUEST reqeust = loadShipRequest(packageBarcode,weight,locationCode);

        //Ship and get label
        try {
            SHIPRESPONSE response = ShipRequest.handle(reqeust, false, 1.2, "MH");
            if(response.getSHIPMENT().get(0).getLABELDATA().size()==1){
                //get label
                mhResponse.setLabel(response.getSHIPMENT().get(0).getLABELDATA().get(0).getValue());
            }else{
               mhResponse.setMessage("Too few or too many labels");
            }
        }catch (Exception e){
            e.printStackTrace();
            mhResponse.setMessage("Unhandled Error, "+e.getMessage());
        }

        //get Tracking

        String sql = "execute getTrackingFromPackageBarcode :packageBarcode";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("packageBarcode",packageBarcode);
            List l = q.list();
            if(l.size()>0){
                mhResponse.setTracking(l.get(0).toString());
                mhResponse.setSuccess(true);
            }else{
                mhResponse.setMessage("Error finding tracking");
            }

        }catch (Exception e){
            e.printStackTrace();
            mhResponse.setMessage("Unhandled Tracking Error, "+e.getMessage());
        }

        //Convert to json

        return gson.toJson(mhResponse);
    }
}
