package com.owd.jobs.jobobjects.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danny on 9/18/2017.
 */
public class OWDShippingTask implements Runnable{

    private String OrderId;
    private String boxId;
    private String packageWeight;


   public OWDShippingTask(String orderId, String boxType, String weight){

        OrderId = orderId;
        boxId = boxType;
        packageWeight = weight;

    }

    public void run(){

        try {

            shipOrder(OrderId, boxId, packageWeight);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void shipOrder(String orderId,String boxType,String weight) throws Exception{
       // String url = "http://it.owd.com/wms/abShipment/packShip.action";
        String url = "http://it.owd.com/wms/abShipment/packShipWithBoxAndWeight.action";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "orderId="+orderId+"&boxType="+boxType+"&weight="+weight;
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

}
