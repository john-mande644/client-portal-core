package com.owd.dc.packing;

import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.List;

import org.hibernate.Query;

public class packageBarcodeLookup {
    public static void main(String[] args){
        try{
            //System.out.println(getShippedStatus("1358992"));
            HibernateSession.currentSession();
            System.out.println("Start");
            System.out.println(getPackageOrderId("19126071"));

        } catch(Exception e){
            e.printStackTrace();

        }
    }
    //lookup all package barcodes for and order and return barcode and package number in an xml document.
    private static boolean getShippedStatus(String packageId){

        boolean shipped = false;

        try{
         String sql = "select shipped_on  from owd_order where order_id = (select owd_order_fkey from package_order where id = :packageId)";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("packageId",packageId);
             List result = q.list();
            if ( null == result.get(0) ){




            }   else{
                shipped = true;
            }

        } catch(Exception e){
            e.printStackTrace();

        }

        return shipped;
    }
    public static String getPackedBarcodes(String barcode){
          String xml = new String();
        try {
            String packageId = getPackageOrderId(barcode);
            Map<Integer,String> barcodes = getPackageBarcodeListFromId(packageId);
            Boolean shipped = getShippedStatus(packageId);
            xml = getPackagesXml(barcodes,shipped);

        } catch (Exception e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            StringBuffer sb = new StringBuffer();
           sb.append("<?xml version=\"1.0\"?>\r\n");
       sb.append("<PackedOrderLabels>");
              sb.append("<response>");
            sb.append(e.getMessage());
            sb.append("</response>");
            sb.append("<numberOfPackages>");
       sb.append("0");
       sb.append("</numberOfPackages>");

            sb.append("<Packages></Packages>");
            sb.append("</PackedOrderLabels>");
           xml = sb.toString();
        }
           return xml;
    }



   public static String getPackagesXml(Map<Integer,String> barcodes,boolean shipped){
       StringBuffer sb = new StringBuffer();
       sb.append("<?xml version=\"1.0\"?>\r\n");
       sb.append("<PackedOrderLabels>");
       sb.append("<response>Good</response>");
       sb.append("<numberOfPackages>");
       sb.append(barcodes.size());
       sb.append("</numberOfPackages>");
       sb.append("<shipped>");
       sb.append(shipped);
       sb.append("</shipped>");
       sb.append("<Packages>");
       for(Map.Entry<Integer, String> e:barcodes.entrySet()){
         sb.append("<box>");
          sb.append("<boxNumber>");
          sb.append(e.getKey());
          sb.append("</boxNumber>");
          sb.append("<barcode>");
          sb.append(e.getValue());
          sb.append("</barcode>");
          sb.append("</box>");
      }
      sb.append("</Packages>");
       sb.append("</PackedOrderLabels>");





    return sb.toString();
   }
    public static Map<Integer,String> getPackageBarcodeListFromId(String packageId) throws Exception{
        String sql =   "select pack_barcode from package  (NOLOCK) where package_order_fkey = "+packageId;
      Map<Integer,String> packages = new TreeMap<Integer,String>();
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);

        while(rs.next()){
        String b = rs.getString(1);
            packages.put(getBoxNumberFromPackageBarcode(b),b);



    }
       rs.close();
        if(packages.size()==0){
            throw new Exception("No Packages Found for this id: "+packageId);
        }
        
        return packages;

    }


    public static String getPackageIdFromPackageBarcode(String barcode) throws Exception{
        if(barcode.contains("p")){
              return barcode.substring(barcode.indexOf("p")+1,barcode.indexOf("*"));
        } else{
            throw new Exception(barcode+ " is not a valid package barcode");
        }

    }

    public static Integer getBoxNumberFromPackageBarcode(String barcode) throws Exception{
        if(barcode.contains("b")){
            String s =  barcode.substring(barcode.indexOf("b")+1);
           
            return new Integer(s);
        }   else{
            throw new Exception(barcode+ " is not a valid package barcode");

        }
    }

    public static String getPackageOrderId(String barcode) throws Exception{
        if(barcode.contains("p")){
            return getPackageIdFromPackageBarcode(barcode);
        } else if(barcode.length()>10){
           String sqlq = "SELECT\n" +
                   "    dbo.package_order.id\n" +
                   "FROM\n" +
                   "    dbo.package\n" +
                   "INNER JOIN dbo.package_order\n" +
                   "ON\n" +
                   "    (\n" +
                   "        dbo.package.package_order_fkey = dbo.package_order.id\n" +
                   "    )\n" +
                   "INNER JOIN dbo.owd_order_track\n" +
                   "ON\n" +
                   "    (\n" +
                   "        dbo.package.order_track_fkey = dbo.owd_order_track.order_track_id\n" +
                   "    )\n" +
                   "WHERE\n" +
                   "    dbo.owd_order_track.tracking_no = :track ;";
              Query q = HibernateSession.currentSession().createSQLQuery(sqlq);
            q.setString("track",barcode);
            List results = q.list();
            if(results.size()>0){
                return results.get(0).toString();
            }   else{
                throw new Exception("Unable to locate proper data for tracking number" + barcode);
            }
            


        }else{
             String orderNum = barcode.replaceAll("\\*|[rR].*","");
           String sql = "execute sp_getPackageOrderIdFromOrderNum "+orderNum;
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);
            if(rs.next()){
                String s = rs.getString(1);
                rs.close();
               return s; 

            }else{
                throw new Exception("No package ID found for "+barcode);
            }

        }

    }
}
