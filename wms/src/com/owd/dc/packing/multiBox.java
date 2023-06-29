package com.owd.dc.packing;

import com.owd.WMSUtils;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPathException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.hibernate.engine.spi.SessionImplementor;


import org.hibernate.Session;
import com.owd.hibernate.HibernateSession;
import com.owd.core.business.order.Package;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 10, 2007
 * Time: 9:38:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class multiBox {

      public static String insertMultiBox (String xml, boolean first) throws Exception{
     String barcode = "";
       packageBean pb = new packageBean();
          System.out.println("*** Multi xml");
          System.out.println(xml);
        // Create a factory object for creating DOM parsers
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
// Ask the document for a list of all <sect1> tags it contains
NodeList sections = document.getElementsByTagName("order");
          Node owdPack = XPathAPI.selectSingleNode(document,"/OWDPack");
          pb.setDunnageFactor(XPathAPI.eval(owdPack,"./dunnageFactor").toString());
          pb.setPackType(XPathAPI.eval(owdPack,"./packType").toString());
          if(pb.getPackType().length()==0){
              pb.setPackType("0");
          }
// Loop through those <sect1> elements one at a time, and extract the
// content of their <h3> tags.
      //System.out.println(document.getElementById("orderfkey").getNodeValue());
int numSections = sections.getLength();
for(int i = 0; i < numSections; i++) {
    Element section = (Element)sections.item(i);  // A <sect1>
    //System.out.println(section.getNodeValue());
   NodeList l = section.getChildNodes();
    /*for(int j =0;j<l.getDepth()-1;j++){


       System.out.println(l.item(j).getFirstChild().getNodeValue());
    }*/

    pb.setOrderFkey(l.item(0).getFirstChild().getNodeValue());
    pb.setPacker(l.item(1).getFirstChild().getNodeValue());
    pb.setStart(l.item(2).getFirstChild().getNodeValue());
    pb.setStop(l.item(3).getFirstChild().getNodeValue());
    pb.setScannedBarcode(l.item(4).getFirstChild().getNodeValue());
    pb.setFacility(l.item(5).getFirstChild().getNodeValue());
     System.out.println(l.item(0).getFirstChild().getNodeValue());
    System.out.println(l.item(1).getFirstChild().getNodeValue());
    System.out.println(l.item(2).getFirstChild().getNodeValue());
    System.out.println(l.item(3).getFirstChild().getNodeValue());
   NodeList b = l.item(6).getChildNodes();
   pb.setBoxFkey(b.item(0).getFirstChild().getNodeValue());
    pb.setBoxCost(b.item(1).getFirstChild().getNodeValue());
    pb.setDepth(b.item(2).getFirstChild().getNodeValue());
    pb.setHeight(b.item(3).getFirstChild().getNodeValue());
    pb.setWidth(b.item(4).getFirstChild().getNodeValue());
    pb.setBoxNumber(b.item(5).getFirstChild().getNodeValue());
    pb.setWeight(b.item(6).getFirstChild().getNodeValue());

    System.out.println(b.item(0).getFirstChild().getNodeValue());
     System.out.println(b.item(1).getFirstChild().getNodeValue());
     System.out.println(b.item(2).getFirstChild().getNodeValue());
     System.out.println(b.item(3).getFirstChild().getNodeValue());
     System.out.println(b.item(4).getFirstChild().getNodeValue());


  //  NodeList pack = document.getElementsByTagName("order/items");
    NodeList pack = l.item(7).getChildNodes();
    System.out.println(pack.getLength()+": length of pack nodes");
    List packageItems = new ArrayList();
    for(int j = 0;j < pack.getLength() ;j++){
        packItemBean pib = new packItemBean();
        NodeList items = pack.item(j).getChildNodes();

        System.out.println(items.getLength());
        pib.setLineKey(items.item(0).getFirstChild().getNodeValue());
        System.out.println(items.item(0).getFirstChild().getNodeValue());
        pib.setQty(items.item(1).getFirstChild().getNodeValue());
        packageItems.add(pib);
}
    pb.setPackedLines(packageItems);
}

     try{
        Session sess = HibernateSession.currentSession();

      if(first){
          Util.checkOrderBeingPacked(pb.getOrderFkey()+"");

        String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs,facility,packType) values (" +
            "?,?,?,?,?,?,?)";
        PreparedStatement ps = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
        ps.setInt(1,pb.getOrderFkey());
        ps.setString(2,pb.getPacker());
        ps.setString(3,pb.GetStart());
        // ps.setString(4,pb.GetStop());
         // ps.setDate(3,pb.getStart());
        ps.setString(4,WMSUtils.getDateAMPM());
        ps.setInt(5,1);
          ps.setString(6,pb.getFacility());
          ps.setInt(7,Integer.parseInt(pb.getPackType()));
       int rows = ps.executeUpdate();
         System.out.println(rows);
        // if(rows<1){
          //bad insert throw error
         //   System.out.println("insert failded");
         //    throw new Exception("No insert for order package record");
    //  }

      }
         Boolean vcOrder = vendorComplianceUtils.isVendorComplianceOrder(pb.getOrderFkey());
           //always do individual packages
           ResultSet rs = HibernateSession.getResultSet(sess,"select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey ="+pb.getOrderFkey());
         String packageId = new String();
        if(rs.next()){
            int packageOId = rs.getInt((1));
            System.out.println(packageOId);
          if(!first){
            System.out.println("update packs");
          PreparedStatement ps = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("update dbo.package_order set " +

                  "num_packs = ?, pack_end = ? where " +
                  "id = ?");
          ps.setString(1,pb.getBoxNumber());
              ps.setString(2,WMSUtils.getDateAMPM());
              ps.setInt(3, packageOId);
            int r = ps.executeUpdate();
              System.out.println(r);

          }

            //create barcode
            barcode = "p"+packageOId+pb.getScannedBarcode()+"b"+pb.getBoxNumber();

            //found id now insert box
           PreparedStatement pst = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                   "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,SSCC,dunnage_factor )values" +
                   "(?,?,?,?,?,?,?,?,?,?)");
           pst.setInt(1,pb.getBoxFkey());
            pst.setInt(2,packageOId);
            pst.setFloat(3,pb.getBoxCost());
            pst.setFloat(4,pb.getDepth());
            pst.setFloat(5,pb.getWidth());
            pst.setFloat(6,pb.getHeight());
            pst.setFloat(7,Float.parseFloat(pb.getWeight()));
            pst.setString(8,barcode);
            if(vcOrder){
                pst.setString(9, ManifestingManager.getSSCCBarcode());
            }else{
                pst.setString(9,"");
            }
            pst.setString(10,pb.getDunnageFactor());
            int rows2 = pst.executeUpdate() ;


            if(pb.getPackedLines().size()>0){
                ResultSet rs1 = null;

                try{ rs1 = HibernateSession.getResultSet(sess,"select id from package  (NOLOCK) where pack_barcode = '"+barcode+"'");
                  rs1.next();
                    packageId = rs1.getString(1);
                    if(null == packageId){
                        throw new Exception("Unable to get get id for line iserts");
                    }

                    Iterator it = pb.getPackedLines().iterator();
                    while(it.hasNext()){

                        packItemBean pib = (packItemBean) it.next();
                           System.out.println("setting pack for "+pib.getLineKey());
                        PreparedStatement pre = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                        pre.setString(1,packageId);
                        pre.setString(2,pib.getLineKey());
                        pre.setString(3,pib.getQty());
                        int rw = pre.executeUpdate();
                        if(rw<1) throw new Exception ("Unable to insert line ");
                          System.out.println("Doine Setting reoderd");

                    }

                    NodeIterator addons = XPathAPI.selectNodeIterator(owdPack, "./packageAddons/addon");
                    try{
                        Util.processAdditionalPackageAddons(addons,packageId);
                    }catch(XPathException ex){
                        ex.printStackTrace();
                    }

              }catch (Exception e){
                    e.printStackTrace();
                    throw new Exception (e.getMessage());
                }

              finally{
                    rs1.close();

              }


            }

        }else{
           System.out.println("Bad insert no findyyy");
            throw new Exception("No insert for order package record");
        }
            Node pack = XPathAPI.selectSingleNode(document,"/OWDPack");
         NodeIterator lotInfoItems = XPathAPI.selectNodeIterator(pack,"./LotInfo/LotInfoItem");

         lotUtils.insertOwdLotPackageLine(lotInfoItems,packageId);
                  NodeIterator additems = XPathAPI.selectNodeIterator(pack,"./AdditionalInfo/InfoItem");
       Node ns ;
         while((ns = additems.nextNode())!=null){

             Util.insertAdditionalDataAsSerial(sess,XPathAPI.eval(ns,"infofkey").toString(),XPathAPI.eval(ns,"inventoryFkey").toString(),
                   XPathAPI.eval(ns,"data").toString(), pb.getOrderFkey()+"",XPathAPI.eval(ns,"grouping").toString(),XPathAPI.eval(ns,"linefkey").toString() );

                   System.out.println(XPathAPI.eval(ns,"linefkey").toString());
                   System.out.println(XPathAPI.eval(ns,"infofkey").toString());
                   System.out.println(XPathAPI.eval(ns,"data").toString());
             System.out.println(XPathAPI.eval(ns,"inventoryFkey").toString());

              }


         sess.flush();
         com.owd.hibernate.HibUtils.commit(sess);
          return "PKGid:"+barcode;
     }catch (Exception ex){
          ex.printStackTrace();
         return ex.getMessage();
    } finally{
      //HibernateSession.closeSession();
    }

    }
  public static String cancelPack(String id, String time, String who){
        try{
        Session sess = HibernateSession.currentSession();
            ResultSet rs = HibernateSession.getResultSet(sess,"select id, packs_shipped from package_order  (NOLOCK) where is_void =0 and owd_order_fkey ="+id);
        if(rs.next()){
            //check for shipped
            if(Integer.parseInt(rs.getString(2))>0){
                System.out.println("shipped so return it");
                return orderStatusUtils.getOrderStatusXML(id);
            }
            String pid = rs.getString(1);
            PreparedStatement ps = HibernateSession.getPreparedStatement("update dbo.package_order set \n" +
                    "\n" +
                    "is_void = 1, \n" +
                    "void_time = \""+time+"\", \n" +
                    "void_by = "+who+" \n" +
                    " where \n" +
                    "id = "+pid);
            int row = ps.executeUpdate();
            if(row>0){


                Util.checkforAndRemoveSerialsForId2(HibernateSession.currentSession(),id);
                Util.checkforAndRemoveSerialsForId1(HibernateSession.currentSession(),id);
                Package.removeLotDataForOrder(Integer.parseInt(id));


                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

            }
           System.out.println("Cancel pack "+row+" for " +id);
        }



        }catch (Exception ex){
            ex.printStackTrace();
            return ex.getMessage();

        }finally{
            //HibernateSession.closeSession();
        }
        return "Success";

    }
    public static void main(String[] args){
   try{
           // System.out.println(cancelPack("10252164","2014-05-05-","51"));
       OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 10674848);
       /*
String s = "<OWDPack><order><orderfkey>3736437</orderfkey><packer>51</packer><start>7/6/2010 2:23:02 PM</start><stop>7/6/2010 2:23:40 PM</stop><barcode>*7872939*</barcode><box><fkey>48</fkey><cost>0.1900</cost><depth>7.5000</depth><width>12.0000</width><height>0.8000</height></box><items><line><fkey>8382149</fkey><qty>1</qty></line></items></order><AdditionalInfo><InfoItem><linefkey>8382149</linefkey><infofkey>2</infofkey><data>12BE00000000</data><inventoryFkey>121672</inventoryFkey></InfoItem><InfoItem><linefkey>8382149</linefkey><infofkey>1</infofkey><data>M00000000000</data><inventoryFkey>121672</inventoryFkey></InfoItem></AdditionalInfo></OWDPack>";

       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
               factory.setNamespaceAware(false);
               factory.setValidating(false);

// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
       DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
       Document document = parser.parse(new ByteArrayInputStream(s.getBytes()));

       Node pack = XPathAPI.selectSingleNode(document,"/OWDPack");
                  NodeIterator additems = XPathAPI.selectNodeIterator(pack,"./AdditionalInfo/InfoItem");
       Node ns ;

       while((ns = additems.nextNode())!=null){
         System.out.println(ns.getTextContent());
           System.out.println(XPathAPI.eval(ns,"linefkey").toString());
           System.out.println(XPathAPI.eval(ns,"infofkey").toString());
           System.out.println(XPathAPI.eval(ns,"data").toString());
           System.out.println(XPathAPI.eval(ns,"inventoryFkey").toString());
      }
*/



    //System.out.println(insertMultiBox(s,false));
    // System.out.println(multiBox.cancelPack("1142458","5/10/2007 9:34:37 AM","51"));
   }catch (Exception ex){
       ex.printStackTrace();
   }
    }
}
