package com.owd.dc.packing;

import com.owd.WMSUtils;
import com.owd.core.TimeStamp;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLotPackageLine;
import com.owd.hibernate.generated.PackageAddons;
import org.apache.xpath.XPathAPI;

import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathException;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 10, 2007
 * Time: 9:31:45 AM
 * To change this template use File | Settings | File T
 */
public class singleBox {

    public static String insertSingleBox(String xml) throws Exception {
        TimeStamp tsPack = new TimeStamp("Single Box: Start");
        TimeStamp tsAll = new TimeStamp("Whole Pack Time");
        String barcode = new String();
        packageBean pb = new packageBean();
        // Create a factory object for creating DOM parsers
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);

// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
        DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
        Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
//get the order node
        Node owdPack = XPathAPI.selectSingleNode(document,"/OWDPack");
        System.out.println(XPathAPI.eval(owdPack,"./packType").toString());
        String packType = XPathAPI.eval(owdPack,"./packType").toString();
        if(packType.equalsIgnoreCase("True")){
           pb.setPackType("1");
        } else{
            if(packType.equals("3")|| packType.equals("4")) {
                pb.setPackType(packType);
            }else {
           pb.setPackType("0");
        }
        }
        pb.setDunnageFactor(XPathAPI.eval(owdPack,"./dunnageFactor").toString());

        Node order = XPathAPI.selectSingleNode(document, "/OWDPack/order");

        //set order info
        pb.setOrderFkey(XPathAPI.eval(order, "./orderfkey").toString());
        //check for weight verified order, if found then update info and return package id
        if(ABUtils.isWeightVerifiedPacked(pb.getOrderFkey()+"")){
          String pkgid = updatePackageOrderInfo(XPathAPI.eval(order, "./packer").toString(),XPathAPI.eval(order, "./start").toString(),pb.getOrderFkey()+"");
            //todo update order ship info
            ABUtils.updateShipInfo(pb.getOrderFkey()+"");


            return "Success|SHPid:" + pkgid;
        }

        pb.setPacker(XPathAPI.eval(order, "./packer").toString());
        pb.setStart(XPathAPI.eval(order, "./start").toString());
        pb.setStop(XPathAPI.eval(order, "./stop").toString());
        pb.setScannedBarcode(XPathAPI.eval(order, "./barcode").toString());
        pb.setFacility(XPathAPI.eval(order, "./facility").toString());

        System.out.println(pb.getOrderFkey());
        System.out.println(pb.getStart());
        System.out.println(pb.getStop());
        System.out.println(pb.getFacility());


        pb.setBoxFkey(XPathAPI.eval(order, "./box/fkey").toString());
        pb.setBoxCost(XPathAPI.eval(order, "./box/cost").toString());
        pb.setDepth(XPathAPI.eval(order, "./box/depth").toString());
        pb.setHeight(XPathAPI.eval(order, "./box/height").toString());
        pb.setWidth(XPathAPI.eval(order, "./box/width").toString());
        pb.setWeight(XPathAPI.eval(order, "./box/weight").toString());


        NodeIterator pack = XPathAPI.selectNodeIterator(order, "./items/line");
        //loop through items
        List packageItems = new ArrayList();
        Node ns;
        while ((ns = pack.nextNode()) != null) {
            packItemBean pib = new packItemBean();


            pib.setLineKey(XPathAPI.eval(ns, "./fkey").toString());

            pib.setQty(XPathAPI.eval(ns, "./qty").toString());
            packageItems.add(pib);
        }
        pb.setPackedLines(packageItems);


        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
//todo check for already packed
            Util.checkOrderBeingPacked(pb.getOrderFkey()+"");
            tsPack.print("Single Box: xml done " + pb.getOrderFkey());
            Boolean vcOrder = vendorComplianceUtils.isVendorComplianceOrder(pb.getOrderFkey());


            String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs,facility,packType) values (" +
                    "?,?,?,?,?,?,?)";
            ps = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
            System.out.println(pb.GetStop());

            String stop = WMSUtils.getDateAMPM();
System.out.println("Date isues");
            System.out.println(pb.GetStart());
            System.out.println(stop);
            ps.setInt(1, pb.getOrderFkey());
            ps.setString(2, pb.getPacker());
            ps.setString(3, pb.GetStart());
           // ps.setString(4, pb.getStop());
            // ps.setDate(3,pb.getStart());
            //ps.setDate(4,pb.getStop());
            ps.setString(4, stop);
            ps.setInt(5, 1);
            ps.setString(6, pb.getFacility());
            ps.setString(7,pb.getPackType());

            int rows = ps.executeUpdate();
            tsPack.print("Single Box: insert package_order Done " + pb.getOrderFkey());
            System.out.println(rows);

            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey =" + pb.getOrderFkey());
            tsPack.print("Single Box: Lookup id from package_order " + pb.getOrderFkey());
            String packageId = new String();
            if (rs.next()) {
                int packageOId = rs.getInt((1));
                System.out.println(packageOId);
                //found idnow insert box
                pst = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,SSCC,dunnage_factor )values" +
                        "(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, pb.getBoxFkey());
                pst.setInt(2, packageOId);
                pst.setFloat(3, pb.getBoxCost());
                pst.setFloat(4, pb.getDepth());
                pst.setFloat(5, pb.getWidth());
                pst.setFloat(6, pb.getHeight());
                System.out.println("This is the float" + Float.parseFloat(pb.getWeight()) + "    the bean  " + pb.getWeight());
                pst.setFloat(7, Float.parseFloat(pb.getWeight()));
                pst.setString(8, "p" + packageOId + pb.getScannedBarcode() + "b1");
                if(vcOrder){
                    pst.setString(9, ManifestingManager.getSSCCBarcode());
                }else{
                    pst.setString(9,"");
                }
                pst.setString(10,pb.getDunnageFactor());

                int rows2 = pst.executeUpdate();
                tsPack.print("Single Box: Inserted package done " + pb.getOrderFkey());
                barcode = "p" + packageOId + pb.getScannedBarcode() + "b1";

                if (pb.getPackedLines().size() > 0) {
                    ResultSet rs1 = null;

                    try {
                        rs1 = HibernateSession.getResultSet(HibernateSession.currentSession(), "select id from package  (NOLOCK) where pack_barcode = '" + barcode + "'");
                        rs1.next();
                        tsPack.print("Single Box: Lookup id from package " + pb.getOrderFkey());
                        packageId = rs1.getString(1);
                        if (null == packageId) {
                            throw new Exception("Unable to get get id for line iserts");
                        }

                        Iterator it = pb.getPackedLines().iterator();
                        while (it.hasNext()) {

                            packItemBean pib = (packItemBean) it.next();
                            System.out.println("setting pack for " + pib.getLineKey());
                            PreparedStatement pre = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                    "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                            pre.setString(1, packageId);
                            pre.setString(2, pib.getLineKey());
                            pre.setString(3, pib.getQty());
                            int rw = pre.executeUpdate();
                            tsPack.print("Single Box: Inserted line item for package " + pb.getOrderFkey() + " line fkey: " + pib.getLineKey());
                            System.out.println("Doine Setting reoderd");

                        }

                        // loop through additional pack items and store them in package_addons table

                        NodeIterator addons = XPathAPI.selectNodeIterator(owdPack, "./packageAddons/addon");
                        try{
                            Util.processAdditionalPackageAddons(addons,packageId);
                        }catch(XPathException ex){
                            ex.printStackTrace();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception(e.getMessage());
                    } finally {
                        rs1.close();

                    }


                }

            } else {
                System.out.println("Bad insert no findyyy");
                throw new Exception("No insert for order package record");
            }

            Node packk = XPathAPI.selectSingleNode(document, "/OWDPack");
            NodeIterator lotInfoItems = XPathAPI.selectNodeIterator(packk,"./LotInfo/LotInfoItem");
            Node lotNode;
           lotUtils.insertOwdLotPackageLine(lotInfoItems,packageId);


            NodeIterator additems = XPathAPI.selectNodeIterator(packk, "./AdditionalInfo/InfoItem");
            Node nss;

            List<String> serialsSeen = new ArrayList<String>();
            boolean haveAddInfo = false;
            while ((ns = additems.nextNode()) != null) {
                haveAddInfo = true;
                String serialData = XPathAPI.eval(ns, "data").toString();
                String serialKey = XPathAPI.eval(ns, "infofkey").toString();
                String inventoryId = XPathAPI.eval(ns, "inventoryFkey").toString();
                String groupId = XPathAPI.eval(ns, "grouping").toString();
                String linefkey = XPathAPI.eval(ns, "linefkey").toString();
                if (serialData.length() < 1) {
                    throw new Exception("Blank serial number value detected for type: " + serialKey);
                }
                if (serialsSeen.contains(serialData)) {
                    throw new Exception("Duplicate serial number value detected Please Contact IT immediately: " + serialData);
                }
                serialsSeen.add(serialData);
                tsPack.print("Single Box: Before Additional DAta" + pb.getOrderFkey());
                try {
                    Util.insertAdditionalDataAsSerial(HibernateSession.currentSession(), serialKey, inventoryId, serialData, pb.getOrderFkey() + "", groupId, linefkey);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.getMessage().contains("Duplicate Serial Data.")) throw new Exception(e.getMessage());
                    throw new Exception("Unable to create serials, contact Danny immediately");

                }


                tsPack.print("Single Box: After Additional DAta" + pb.getOrderFkey());
            }


       /*  while((nss = additems.nextNode())!=null){

             System.out.println(XPathAPI.eval(nss,"linefkey").toString());
                   System.out.println(XPathAPI.eval(nss,"infofkey").toString());
                   System.out.println(XPathAPI.eval(nss,"data").toString());
             System.out.println(XPathAPI.eval(nss,"inventoryFkey").toString());
             System.out.println(pb.getOrderFkey());

           try{
             Util.insertAdditionalDataAsSerial(HibernateSession.currentSession(),XPathAPI.eval(nss,"infofkey").toString(),XPathAPI.eval(nss,"inventoryFkey").toString(),

                   XPathAPI.eval(nss,"data").toString(), pb.getOrderFkey()+"",XPathAPI.eval(nss,"grouping").toString() );
           }catch(Exception e){
              e.printStackTrace();
               throw new Exception("Unable to create serials, contact Danny immediately");

           }




              }*/

            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            if (haveAddInfo) {
                Query q = HibernateSession.currentSession().createSQLQuery("execute dbo.sp_pack_serial_test :orderId");
                q.setString("orderId", pb.getOrderFkey() + "");

                List results = q.list();
                System.out.println("size of resutls: " + results.size());
                System.out.println(results.get(0).toString());
                if (results.get(0).toString().equals("0") == false) {
                    throw new Exception("Major error with serials, contact IT immediately with this order, pack will need to be voided");

                }

            }
            tsAll.print("Single Box all done " + pb.getOrderFkey());
            return "Success|PKGid:" + barcode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        } finally {
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
           // HibernateSession.closeSession();
        }

    }


    public static void main(String[] args) {
        try {

            String s = "<OWDPack>" +
                    "<order>" +
                    "<orderfkey>2130829</orderfkey>" +
                    "<packer>51</packer>" +
                    "<start>8/24/2007 10:45:50 AM</start>" +
                    "<stop>8/24/2007 10:46:45 AM</stop>" +
                    "<barcode>*5791122*</barcode>" +
                    "<box>" +
                    "<fkey>71</fkey>" +
                    "<cost>0</cost>" +
                    "<depth>1</depth>" +
                    "<width>2</width>" +
                    "<height>3</height>" +
                    "</box>" +
                    "<items>" +
                    "<line>" +
                    "<fkey>4396645</fkey>" +
                    "<qty>1</qty>" +
                    "</line>" +
                    "</items>" +
                    "</order>" +
                    "</OWDPack>";
            singleBox.insertSingleBox(s);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String updatePackageOrderInfo(String user, String start, String orderFkey) throws Exception{
            String sql = "update package_order set packer_ref = :user, pack_start = :start, pack_end = :stop where owd_order_fkey = :orderFkey and is_void = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("user",user);
        q.setParameter("start",start);
        q.setParameter("orderFkey",orderFkey);
        q.setParameter("stop", WMSUtils.getDateAMPM());
        int i = q.executeUpdate();
        if(i==0){
            throw new Exception("unable to update pack_order info");
        }
        HibUtils.commit(HibernateSession.currentSession());

        String sql2 = "SELECT\n" +
                "    dbo.package.pack_barcode\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                "WHERE\n" +
                "    dbo.package_order.owd_order_fkey = :orderFkey\n" +
                "AND dbo.package_order.is_void = 0 ;";
        q = HibernateSession.currentSession().createSQLQuery(sql2);
        q.setParameter("orderFkey",orderFkey);
        List l = q.list();

        return l.get(0).toString();

    }

}