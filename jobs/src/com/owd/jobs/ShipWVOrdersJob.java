package com.owd.jobs;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danny on 10/6/2015.
 * This job will lookup Weight Verified orders that are on today's sla and ship them.
 *
 */
public class ShipWVOrdersJob extends OWDStatefulJob{

        public static void main(String args[]){
            try{
              //  shipOrder("9672401");





             //  run();
                 updatePrintedLabels();
                //removeABOrdersFromPrintQueue();
            }catch (Exception e){
                e.printStackTrace();

            }
            System.out.println("The end");
        }
   public void internalExecute(){
       try {
           //load and ship 500 orders
           List<String> orders = loadTopOrdersToShip();

           System.out.println(orders.size());
           for(Object id : orders){
               System.out.println(id);
               shipOrder(id.toString());
           }

           List<String> multiORder = loadTopMultiPieceOrdersToShip();
           for(Object id : multiORder){
               System.out.println(id);
               shipMultiOrder(id.toString());
           }
           //remove printed orders from queue
           removeABOrdersFromPrintQueue();

           //look on ftp server for printed labels and mark shipped
           updatePrintedLabels();



       }catch (Exception e){
            e.printStackTrace();

       }



    }

    private static void removeABOrdersFromPrintQueue() throws Exception{

        String sql = "execute sp_AutoBatchDeleteProcessedOrdersFromPrintQueue";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        int i = q.executeUpdate();
        System.out.println(i);
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }


    }

    private static List<String> loadTopOrdersToShip() throws Exception{
        List<String> orders = new ArrayList<>();
        String sql = "execute sp_loadAutoBatchOrdersReadyForShipment";
        System.out.println("Checking for WV orders to ship");
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List l = q.list();
        if (l.size()>0){

            for(Object o:l){
                orders.add(o.toString());

            }

        }else{
            System.out.println("Found nothing to ship for Auto Batch");
        }






        return orders;

    }
    private static List<String> loadTopMultiPieceOrdersToShip() throws Exception{
        List<String> orders = new ArrayList<>();
        String sql = "execute sp_loadAutoBatchMultiPieceOrdersReadyForShipment";
        System.out.println("Checking for WV orders to ship");
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List l = q.list();
        if (l.size()>0){

            for(Object o:l){
                orders.add(o.toString());

            }

        }else{
            System.out.println("Found nothing to ship for Auto Batch");
        }






        return orders;

    }

    private static List<String> loadOrdersToShip() throws Exception{
        List<String> orders = new ArrayList<>();
        String sql = "SELECT \n" +
                "    dbo.owd_order.order_id\n" +
                "   \n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_packs\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_packs.order_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey and dbo.package_order.is_void = 0 and dbo.package_order.id is null)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_order_ship_holds\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_holds.order_fkey and (is_on_wh_hold = 0 or is_on_wh_hold is null))\n" +
                "WHERE\n" +
                "    dbo.owd_order.is_void = 0\n" +
                "AND dbo.owd_order.order_status = 'At Warehouse'\n" +
                "AND dbo.owd_order.is_shipping = 0 \n" +
                "and AB_shipment = 1";
        System.out.println("Checking for WV orders to ship");
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List l = q.list();
        if (l.size()>0){

            for(Object o:l){
              orders.add(o.toString());

            }

        }






        return orders;

    }
    private static void shipOrder(String orderId) throws Exception{
        String url = "http://it.owd.com/wms/abShipment/packShip.action";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "orderId="+orderId;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());



    }
    private static void shipMultiOrder(String orderId) throws Exception{
        String url = "http://it.owd.com/wms/abShipment/packPreShipPackagePerUnit.action";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "orderId="+orderId;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());



    }
    private static void updatePrintedLabels() throws Exception{
        String url = "http://it.owd.com/wms/abShipment/processPrintedLabels.action";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        // System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());



    }
}
