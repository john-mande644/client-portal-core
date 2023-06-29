package com.owd.dc.packing;

import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.core.business.Address;

/**
 * Created by IntelliJ IDEA.
 * User: Da
 * Date: May 13, 2007
 * Time: 4:59:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class batchBox {

      public static String insertSingleBox (String xml) throws Exception{
       packageBean pb = new packageBean();
        // Create a factory object for creating DOM parsers
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
// Ask the document for a list of all <sect1> tags it contains
NodeList sections = document.getElementsByTagName("order");
// Loop through those <sect1> elements one at a time, and extract the
// content of their <h3> tags.
      //System.out.println(document.getElementById("orderfkey").getNodeValue());
int numSections = sections.getLength();
          try{
for(int i = 0; i < numSections; i++) {
    Element section = (Element)sections.item(i);  // A <sect1>
    //System.out.println(section.getNodeValue());
   NodeList l = section.getChildNodes();
    /*for(int j =0;j<l.getDepth()-1;j++){


       System.out.println(l.item(j).getFirstChild().getNodeValue());
    }*/


    pb.setPacker(l.item(1).getFirstChild().getNodeValue());
    pb.setStart(l.item(2).getFirstChild().getNodeValue());
    pb.setStop(l.item(3).getFirstChild().getNodeValue());
    pb.setScannedBarcode(l.item(4).getFirstChild().getNodeValue());

   NodeList b = l.item(5).getChildNodes();
   pb.setBoxFkey(b.item(0).getFirstChild().getNodeValue());
    pb.setBoxCost(b.item(1).getFirstChild().getNodeValue());
    pb.setDepth(b.item(2).getFirstChild().getNodeValue());
    pb.setHeight(b.item(3).getFirstChild().getNodeValue());
    pb.setWidth(b.item(4).getFirstChild().getNodeValue());

    System.out.println(b.item(0).getFirstChild().getNodeValue());
     System.out.println(b.item(1).getFirstChild().getNodeValue());
     System.out.println(b.item(2).getFirstChild().getNodeValue());
     System.out.println(b.item(3).getFirstChild().getNodeValue());
     System.out.println(b.item(4).getFirstChild().getNodeValue());
}


      String s = insertRecord(pb);

          return "Success|PKGid:"+s + getHasCustoms(pb.getScannedBarcode());
     }catch (Exception ex){

          ex.printStackTrace();
              if(ex.getMessage().contains("Violation of UNIQUE KEY constraint")){
                  return "Order has already been packed";
              }
         return ex.getMessage();
    } finally{
      //HibernateSession.closeSession();
    }

    }
    private static String getHasCustoms(String orderNum){
        String s = "";
        try{
             String query = "select ship_state, ship_country from owd_order_ship_info where order_fkey = (select order_id from owd_order where order_num = :ordernum)";
              Query q = HibernateSession.currentSession().createSQLQuery(query);
            q.setParameter("ordernum",orderNum.replaceAll("\\*|[rR].*",""));
            List results = q.list();
            if(results.size()>0){
                Object data = results.get(0);
               Object[] row = (Object[]) data;
                Address a = new Address(null,null,null,null,row[0].toString(),null,row[1].toString());
                s = CustomsForm(a);
            }
            
        } catch(Exception e){
            e.printStackTrace();

        }
        return s;
    }
    private static String CustomsForm(Address a){
        if ( a.isInternational() || a.isAPO()){
            return ":Customs";
        }
          return "";
    }
    public static void main(String[] args){
   try{

String s = "" +
        "<?xml version=\"1.0\"?><OWDPack>" +
        "<order>" +
        "<orderfkey>1142458</orderfkey>" +
        "<packer>51</packer>" +
        "<start>5/10/2007 9:35:37 AM</start>" +
        "<stop>5/10/2007 9:35:43 AM</stop>" +
        "<barcode>*4781770*</barcode>" +
        "<box>" +
        "<fkey>31</fkey>" +
        "<cost>0.39</cost>" +
        "<depth>11.5</depth>" +
        "<width>5.75</width>" +
        "<height>3.00</height>" +
        "</box>" +
        "</order>" +
        "</OWDPack>";
//batchBox.insertSingleBox(s);
  System.out.println(getHasCustoms("7245045"));
   }catch (Exception ex){
       ex.printStackTrace();
   }
    }

    public static String insertRecord(packageBean pb) throws Exception{


           String barcode;
         String orderNum = pb.getScannedBarcode().replaceAll("\\*|[rR].*","");

         
        Session sess = HibernateSession.currentSession();
       ResultSet rs3 = HibernateSession.getResultSet(sess,"select order_id, order_num_barcode, is_void from owd_order  (NOLOCK) where order_num_barcode like '*"+orderNum+"%'");
       if(rs3.next()){
           if(rs3.getInt("is_void")==0){
               if(rs3.getString(2).equals(pb.getScannedBarcode())){
                 pb.setOrderFkey(rs3.getString(1));
               }  else{
                   throw new Exception("Order has been reprinted");
               }

           }else{
                throw new Exception("Order has been void");
           }


       }else{
            throw new Exception("Order number not found");
       }
       System.out.println("ready to insert");

        
         String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs) values (" +
            "?,?,?,?,?)";

        PreparedStatement ps = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
        ps.setInt(1,pb.getOrderFkey());
        ps.setString(2,pb.getPacker());
        ps.setString(3,pb.GetStart());
         ps.setString(4,pb.GetStop());

        ps.setInt(5,1);
       int rows = ps.executeUpdate();
         System.out.println(rows);

           ResultSet rs = HibernateSession.getResultSet(sess,"select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey ="+pb.getOrderFkey());
        if(rs.next()){
            int packageOId = rs.getInt((1));
            System.out.println(packageOId);
            //found idnow insert box
           PreparedStatement pst = ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                   "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode )values" +
                   "(?,?,?,?,?,?,?,?)");
           pst.setInt(1,pb.getBoxFkey());
            pst.setInt(2,packageOId);
            pst.setFloat(3,pb.getBoxCost());
            pst.setFloat(4,pb.getDepth());
            pst.setFloat(5,pb.getWidth());
            pst.setFloat(6,pb.getHeight());
            pst.setFloat(7,0.0f);
            pst.setString(8,"p"+packageOId+pb.getScannedBarcode()+"b1");
            int rows2 = pst.executeUpdate() ;

            barcode = "p"+packageOId+pb.getScannedBarcode()+"b1";

            ResultSet rs89 = HibernateSession.getResultSet(sess,"select id from package (NOLOCK) where pack_barcode = '"+barcode+"'");
            if(rs89.next()){
                String packageId = rs89.getString(1);
                OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, pb.getOrderFkey());
                for(OwdLineItem item: order.getLineitems()){

                     PreparedStatement pre = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                        pre.setString(1,packageId);
                        pre.setString(2,item.getLineItemId().toString());
                        pre.setString(3,item.getQuantityActual().toString());
                        int rw = pre.executeUpdate();
                }
            }
        }else{
           System.out.println("Bad insert no findyyy");
            throw new Exception("No insert for order package record");
        }



         sess.flush();
         com.owd.hibernate.HibUtils.commit(sess);
          return barcode;
    }

}
