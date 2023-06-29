package com.owd.dc.warehouse.misc;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 1/11/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class printUtils {


    public static void main(String args[]) {
        try {
          //  printMidcoDTAlabel("MA1108FM1794", "1900010361800232", "zebra4.dc1.owd.com");
          //  printSmallPackLabelUserId("p4401761*11175206*b1","231");

           Map<String,String> printers = fillPrinterMap();
            System.out.println(printers);
           // SortedMap<String,String> m = printers;
             System.out.println(printers.keySet());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static SortedMap<String,String> fillPrinterMap() throws Exception{
        System.out.println("Filling printer Map");
        SortedMap<String,String> printers = new TreeMap<String, String>();
         String sql = "select value,display from app_data where project = 'wms' and description = 'labelMaker' and variable = 'printer' and display like '%Small'\n" +
                 "order by display";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List results = q.list();
        for(Object row:results){
            Object[] data = (Object[]) row;
            printers.put(data[1].toString(),data[0].toString());
        }

        return printers;
    }
    private static void printSmallPackLabel(String barcode,String printer) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
         sb.append("^XA");
         sb.append("^MMT");
         sb.append("^PW406");
         sb.append("^LL0203");
         sb.append("^LS0");
         sb.append("^BY1,3,90^FT74,131^BCN,,Y,N");
         sb.append("^FD");
         sb.append(barcode);
        sb.append("^FS");
         sb.append("^PQ1,0,1,Y^XZ");
        sentToPrinter(sb.toString(), printer);
    }
    public static void printSmallPackLabelUserId(String barcode,String userId,String printer) throws Exception{
       /* String sql = "select small_printer from handheld_setup where loginid = :userId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("userId",userId);
        String printer = q.list().get(0).toString();*/
        if(printer.length()>0){
          printSmallPackLabel(barcode,printer);
        } else{
            throw new Exception("No printer set please to into setup");
        }
    }
   public static void printMidcoDTAViaUserId(String serial, String ua, String userId) throws Exception{
               String sql = "select small_printer from handheld_setup where loginid = :userId";
       Query q = HibernateSession.currentSession().createSQLQuery(sql);
       q.setParameter("userId",userId);
       String printer = q.list().get(0).toString();
       if(printer.length()>0){
            printMidcoDTAlabel(serial,ua,printer);
       } else{
           throw new Exception("No printer set please to into setup");
       }
   }
   private static void printMidcoDTAlabel(String serial, String ua, String printer) throws Exception {
        StringBuffer sb = new StringBuffer();

        sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
        sb.append("^XA");
        sb.append("^MMT");
        sb.append("^PW406");
        sb.append("^LL0203");
        sb.append("^LS0");
        sb.append("^FT39,178^A0N,28,28^FH^FDU/A:^FS");
        sb.append("^FT60,89^A0N,28,28^FH^FDS/N:^FS");
        sb.append("^BY1,2,31^FT42,149^BCN,,Y,N");
        sb.append("^FD");
        sb.append(ua);
        sb.append("^FS");
        sb.append("^BY2,3,31^FT42,56^BCN,,Y,N");
        sb.append("^FD");
        sb.append(serial);
        sb.append("^FS");
        sb.append("^PQ1,0,1,Y^XZ");


        sentToPrinter(sb.toString(), printer);

    }

    private static void sentToPrinter(String label, String printer) throws IOException {

        Socket clientSocket = new Socket(printer, 9100);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(label);
        clientSocket.close();
    }
}
