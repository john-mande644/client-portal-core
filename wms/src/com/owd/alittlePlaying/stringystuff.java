package com.owd.alittlePlaying;

/**
 * Created by danny on 9/22/2016.
 */
public class stringystuff {

    public static void main(String[] args){
        String s = "(605)-845.7172)";
        System.out.println(s.replaceAll("[\\(\\)-\\.]",""));

        String track = "9.9.9.9";
        String[] tracks = track.split("\\.");
        for(String ss:tracks){
            System.out.println(ss);
        }

        track = "123456,1234567";
        tracks = track.split(",");
        for(String ss:tracks){
            System.out.println(ss);
        }

         s = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"true\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"HOLDORDER\" facility_rule=\"DC1\" " +
                "order_reference=\"ruleship21\">" +
                "<SHIPPING_INFO  address_one=\"#114  6951  72nd Street\" address_two=\"\" city=\"Delta\" company_name=\"PJM Distributions Inc\" " +
                "country=\"US\" first_name=\"David\" last_name=\"Peterson\" phone=\"800-530-3930\" ship_type=\"UPS.2DA\" state=\"NY\" zip=\"90185\">" +
                "</SHIPPING_INFO>" +
                "<BILLING_INFO paid=\"YES\" payment_type=\"CLIENT\"/>" +
                "<LINE_ITEM customs_desc=\"Testing\" declared_value=\"0.01\" description=\"testing\" part_reference=\"Ebay Items\" requested=\"1\" backordered=\"1\"/>" +
                "<MESSAGE/>" +
                "</OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        System.out.println(s);
    }
}
