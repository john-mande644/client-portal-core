package com.owd.jobs.jobobjects.clients;


import com.owd.core.email.mailgun.MailgunEmail;
import com.owd.core.email.mailgun.MailgunService;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import okhttp3.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by danny on 8/29/2019.
 */
public class StanleyBlackAndDeckerService {


    public static String loadEmailData(OwdOrder order)throws Exception{

        NumberFormat currencyFormatter =
                NumberFormat.getCurrencyInstance(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat orderDate = new SimpleDateFormat("EEEE, MMMM dd, yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("{\"customerName\":\"");
        sb.append(order.getShipinfo().getShipFirstName()+ " " + order.getShipinfo().getShipLastName());
        sb.append("\",\"orderRefNum\":\"");
        sb.append(order.getOrderRefnum());
        sb.append("\",\"shippingDate\":\"");
        sb.append(sdf.format(order.getShippedDate()));
        sb.append("\",\"tracking\":\"");
        sb.append(order.getTrackingNums());
        sb.append("\",\"orderDate\":\"");
        sb.append(orderDate.format(order.getActualOrderDate()));
        sb.append("\",\"shipName\":\"");
        sb.append(order.getShipinfo().getShipFirstName()+ " " + order.getShipinfo().getShipLastName());
        sb.append("\",\"shipAddressOne\":\"");
        sb.append(order.getShipinfo().getShipAddressOne());
        sb.append("\",\"shipAddressTwo\":\"");
        sb.append(order.getShipinfo().getShipAddressTwo());
        sb.append("\",\"shipCity\":\"");
        sb.append(order.getShipinfo().getShipCity());
        sb.append("\",\"shipState\":\"");
        sb.append(order.getShipinfo().getShipState());
        sb.append("\",\"shipZip\":\"");
        sb.append(order.getShipinfo().getShipZip());
        sb.append("\",\"shipCountry\":\"");
        sb.append(order.getShipinfo().getShipCountry());
        sb.append("\",\"billName\":\"");
        sb.append(order.getBillFirstName()+ " " + order.getBillLastName());
        sb.append("\",\"billAddressOne\":\"");
        sb.append(order.getBillAddressOne());
        sb.append("\",\"billAddressTwo\":\"");
        sb.append(order.getBillAddressTwo());
        sb.append("\",\"billCity\":\"");
        sb.append(order.getBillCity());
        sb.append("\",\"billState\":\"");
        sb.append(order.getBillState());
        sb.append("\",\"billZip\":\"");
        sb.append(order.getBillZip());
        sb.append("\",\"billCountry\":\"");
        sb.append(order.getBillCountry());
        sb.append("\",\"lines\":[");
        int i = order.getLineitems().size();
        BigDecimal skuPriceTotal = BigDecimal.ZERO;
        for(OwdLineItem item:order.getLineitems()){
            sb.append("{\"inventoryNum\":\"");
            sb.append(item.getDescription());
            sb.append("\",\"unitPrice\":\"");
            sb.append(currencyFormatter.format(item.getPrice()));
            sb.append("\",\"qty\":\"");
            sb.append(item.getQuantityActual());
            sb.append("\",\"totalPrice\":\"");
            sb.append(currencyFormatter.format(item.getTotalPrice()));
            sb.append("\"}");
            if(i>1){
                sb.append(",");
                i--;
            }
            skuPriceTotal = skuPriceTotal.add(item.getTotalPrice());
        }

        sb.append("]");
        sb.append(",\"skuPriceTotal\":\"");
        sb.append(currencyFormatter.format(skuPriceTotal));
        sb.append("\",\"taxes\":\"");
        sb.append(currencyFormatter.format(order.getTaxAmount()));
        sb.append("\",\"discount\":\"");
        sb.append(currencyFormatter.format(order.getDiscount().abs()));
        sb.append("\",\"shippingHandling\":\"");
        sb.append(currencyFormatter.format(order.getShipHandlingFee()));
        sb.append("\",\"subtotal\":\"");
        sb.append(currencyFormatter.format(order.getOrderSubTotal()));
        sb.append("\",\"orderTotal\":\"");
        sb.append(currencyFormatter.format(order.getOrderTotal()));
        sb.append("\"}");


        return sb.toString();
    }
    public static void sendEmail(Integer orderId) throws Exception{
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        String data = loadEmailData(order);
        MailgunService mailgunService = new MailgunService(HttpUrl.parse("https://api.mailgun.net/v3"),"mg.owd.com","key-0d8978fb7af03e785e3e5cebc2a07fac");
        byte[] pic = getImage();



        MailgunEmail email =   new MailgunEmail.Builder()
                .from(new MailgunEmail.Contact("Pria Shipping","do-not-reply@owd.com"))
                .addTo(new MailgunEmail.Contact(order.getShipinfo().getShipFirstName()+ " "+order.getShipinfo().getShipLastName(), order.getShipinfo().getShipEmailAddress()))
                .subject("Order " + order.getOrderRefnum() + " Has Shipped")
                .template(getEmailTemplate(order))
                .addExtraHeader(new MailgunEmail.Header("X-Mailgun-Variables", data))
                .addInline(new MailgunEmail.Attachment("pria.png",
                        RequestBody.create(
                                MediaType.parse("image/png"), pic
                        )))
                .build();
        boolean success = mailgunService.sendMail(email);
        System.out.println(success ? "MESSAGE SENT" : "FAILED TO SEND");

       /* Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-0d8978fb7af03e785e3e5cebc2a07fac"));
        WebResource webResource = client.resource("https://api.mailgun.net/v3/mg.owd.com/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "Mailgun Sandbox <postmaster@mg.owd.com>");
        formData.add("to", "Danny Nickels <dnickels@owd.com>");
        formData.add("subject", "Hello Danny Nickels");
        formData.add("template", "stanleyshipping");
        formData.add("h:X-Mailgun-Variables", "{"test": "test"}");*/

    }

    /**
     * Sean Chen 7/20/2020 case 831789
     * @return
     */
    public static String getEmailTemplate(OwdOrder order) {
        String templateName = "";
        if (isReturnOrder(order)){
            templateName = "sbd_return_confirmation";
        }else {
            templateName = "stanleyshipping";
        }
        return templateName;
    }

    public static Boolean isReturnOrder(OwdOrder order){
        Boolean hasReturnSku = false;
        List<String> returnSkus = new ArrayList(){
            {
                add("BOTTOMPULPMOLD");
                add("TOPPULPMOLD");
                add("UNITCARTON");
            }
        };
        for(int i=0; i<returnSkus.size();i++){
            if (getOrderLineItems(order).contains(returnSkus.get(i))){
                hasReturnSku = true;
            }
        }
        return hasReturnSku;
    }

    public static String getOrderLineItems(OwdOrder order){
        StringBuilder sb = new StringBuilder();
        int i = order.getLineitems().size();

        for(OwdLineItem item:order.getLineitems()){
            sb.append("{\"inventoryNum\":\"");
            sb.append(item.getInventoryNum());
            sb.append("\"}");
            if(i>1){
                sb.append(",");
                i--;
            }
        }
        return sb.toString();
    }

    public static byte[] getImage()throws Exception{

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://owdmailimage.s3-us-west-2.amazonaws.com/pria.png").build();
        Response response = client.newCall(request).execute();

        return response.body().bytes();
    }

    public static void main(String[] args){
       // System.setProperty("com.owd.environment","test");
        try {
//            postStanleyTrackingAndSerials("18910760");
           // sendEmail(18910760);
           // System.out.println(getImage().length);
//            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,18023310);

            //System.out.println(loadEmailData(order));

//            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20669725);

            // 2020/7/22 case 831789 Test
//            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20771684);
//            System.out.println(isReturnOrder(order));
//            System.out.println(getEmailTemplate(order));

//            System.out.println(getOrderLineItems(order));

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static boolean postStanleyTrackingAndSerials(String orderId) throws Exception{
        boolean success = false;
        try {
            OwdOrder o = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));


            List<String> serials = new ArrayList<>();
            for(OwdLineItem i : o.getLineitems()){
                for(OwdInventorySerial serial: i.getSerials()){
                    System.out.println("Adding Serial: "+ serial.getSerialNumber());
                    serials.add(serial.getSerialNumber());
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("{\"notes\": \"Serial Number=");
            sb.append(String.join(",", serials));
            sb.append("|tracking number=");
            sb.append(o.getTrackingNums());
            sb.append("\",\"reason\": \"Shipped\"," +
                    "  \"requestedStatus\": \"Shipped\"}");
            System.out.println(sb.toString());
            String ocorderId = "";
            String ShipmentId = "";
            for(OwdTag tag: o.getTags()){
                if(tag.getTagName().equalsIgnoreCase("OCCORDERID")){
                    ocorderId = tag.getTagValue();
                }
                if(tag.getTagName().equalsIgnoreCase("SHIPMENTID")){
                    ShipmentId = tag.getTagValue();
                }
            }

            if(ocorderId.length()==0){
                throw new Exception("Could not load OCCORDERID for order: "+o.getOrderNum());

            }
            if(ShipmentId.length()==0){
                throw new Exception("Could not load SHIPMENTID for order: "+o.getOrderNum());

            }
            System.out.println(ocorderId);
            System.out.println(ShipmentId);

            postDataToAPI(sb.toString(),ocorderId,ShipmentId);





        }catch (Exception e){
            e.printStackTrace();
        }




        return success;
    }



    private static boolean postDataToAPI(String data, String orderId, String shipmentId){
        boolean success = false;
try {
    //testurl
    //String theURl = "https://ocscm.qalow.pri.us.sbd.orckestra.cloud/api/orders/UnitedStates/"+orderId+"/shipments/"+shipmentId+"/fulfillmentState";

    //production Url
    String theURl = "https://ocscm.prdlow.pri.us.sbd.orckestra.cloud/api/orders/UnitedStates/" + orderId +"/shipments/"+shipmentId+"/fulfillmentState";

    System.out.println(theURl);
    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, data);
    Request request = new Request.Builder()
            .url(theURl)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Auth", "DEINF1m6gmtFxYBVnX1yNf8LW1yJX2VcsR4iSdTJH8F/QtXMxFPaeePdP710ppcRqXAzpZP5JSubw6Lri4SjDRw4USAqGM+ypkrBpWfb1PQ=")
            .addHeader("Accept", "*/*")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Connection", "keep-alive")
            .addHeader("cache-control", "no-cache")
            .build();


    Response response = client.newCall(request).execute();
    System.out.println(response.code());
    System.out.println(response.body().string());
    if(response.code() == 201){
        success = true;
    }


}catch (Exception e){
e.printStackTrace();
}

        return success;
    }


}
