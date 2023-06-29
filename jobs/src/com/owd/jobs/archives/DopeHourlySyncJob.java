package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.CountryNames;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.managers.InventoryManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import com.owd.jobs.jobobjects.amazon.AmazonFeeds;
import groovy.util.slurpersupport.GPathResult;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.apparelmagic.ApparelMagicApi;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class DopeHourlySyncJob extends OWDStatefulJob  {
protected final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

            //run();

        try{
            ShopifyAPI api = new ShopifyAPI("5deb2a0bca1ea2cb0cd847a40a572120",
                    "5cf111c642613a720f603e2ebee552f2", "dopetemplate.myshopify.com","41392899");
          //  ApparelMagicApi amApi = new ApparelMagicApi(551,"https://dope.app.apparelmagic.com","42a98c9cf1dffbd86f907689dd768b1f92208d1c","81fcb72f5d182afbd70cbf4438e22cb62ce3dbac");
            //  println   api.getHashedRequest(null)
          //  amApi.setShopify( api);
          //  amApi.syncAmSkusToOwd(false,16500,17000);
            api.printListOfLocations();

            api.setClientInfo(551, null);

            api.setSendLocationWithInventory(true);
            api.updateInventoryLevels();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void internalExecute() {

        log.debug("starting DopeHourlySyncJob");

        ShopifyAPI api = new ShopifyAPI("5deb2a0bca1ea2cb0cd847a40a572120",
                "5cf111c642613a720f603e2ebee552f2", "dopetemplate.myshopify.com","41392899") {

            @Override
            public void doVendorComplianceStuffBeforeSavingOrder(Order order, Object OrderObject) throws Exception{




                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = null;
                    builder = builderFactory.newDocumentBuilder();
                    Document document = builder.parse(new ByteArrayInputStream(OrderObject.toString().getBytes(Charset.forName("UTF-8"))));
                    XPath xPath =  XPathFactory.newInstance().newXPath();

                    String tags = "";
                    String tagsExp = "/order/tags";
                    tags = xPath.compile(tagsExp).evaluate(document);

                    if(tags.toLowerCase().contains("pacsun")) {
                     //This is a pacsun order. Pull out needed values and Shipping method and set proper template
                        String retailOrderNumber = "";
                        String purchaseORderNumber = "";
                        String shipMethod = "";

                        String retailExp = "/order/note-attributes/note-attribute[name='Retailer Order Number']/value";
                        NodeList nodeList = (NodeList) xPath.compile(retailExp).evaluate(document, XPathConstants.NODESET);

                        if(nodeList.getLength()>0){
                            retailOrderNumber = nodeList.item(0).getFirstChild().getNodeValue();

                        }else{
                            throw new Exception("Unable to find Retailer Order Number for Pacsun order");
                        }

                        String poExp = "/order/note-attributes/note-attribute[name='Purchase Order Number']/value";
                        nodeList = (NodeList) xPath.compile(poExp).evaluate(document, XPathConstants.NODESET);

                        if(nodeList.getLength()>0){
                            purchaseORderNumber = nodeList.item(0).getFirstChild().getNodeValue();

                        }else{
                            throw new Exception("Unable to find Purchase Order Number for Pacsun order");
                        }

                        String methodExp = "/order/note-attributes/note-attribute[name='Requested Shipping Method']/value";
                        nodeList = (NodeList) xPath.compile(methodExp).evaluate(document, XPathConstants.NODESET);

                        if(nodeList.getLength()>0){
                            shipMethod = nodeList.item(0).getFirstChild().getNodeValue();

                        }else{
                            throw new Exception("Unable to find ShipMethod for Pacsun order");
                        }

                        order.order_refnum = purchaseORderNumber +" ("+order.order_refnum+")";
                        order.getShippingInfo().whse_notes = retailOrderNumber;

                        if(shipMethod.contains("SmartPost")){
                            order.setShippingMethodName("FedEx SmartPost Parcel Select");
                        }else if(shipMethod.contains("Overnight")){
                            order.setShippingMethodName("FedEx Standard Overnight");
                            order.setThirdPartyBilling("217974801");
                        }else if (shipMethod.contains("day")||shipMethod.equalsIgnoreCase("(FedEx) 2Day")){
                            order.setShippingMethodName("FedEx 2Day");
                            order.setThirdPartyBilling("217974801");
                        }else if( shipMethod.contains("Express")){
                            order.setShippingMethodName("FedEx Express Saver");
                            order.setThirdPartyBilling("217974801");
                        }else{
                            throw new Exception("Unable to translate PacSun Ship Method: "+shipMethod);
                        }

                        order.setTemplate("pacsun");
                        order.updateShipNameOverride("PACSUN");
                        order.updateShipCompanyOverride("PACSUN");
                        order.setGroupName("pacsun");




                    }







            }

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {


                if(!order.template.equalsIgnoreCase("pacsun")) {
                    order.addInsertItemIfAvailable("DSMU-0914-X714-BLK-0",1);
                    order.addInsertItemIfAvailable("DSMU-0914-X714-WHT-0",1);
                    order.template = "dopeshopify";
                }

                //Added per Steve case 664908
                order.backorder_rule = OrderXMLDoc.kPartialShip;
            }


            @Override
            public boolean isShippedSeparately(String sku)
            {
                if(sku.startsWith("*")) {
                    return true;
                }

                return false;
            }

            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                //force first class for karmaloop orders
                if(order.getBillingContact().getName().toUpperCase().contains("KARMALOOP")){
                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY","CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD",
                            "TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS.I_FIRST","CONNECTSHIP_DHL.DHL.WPX"));
                }



                if(oldMethod.toUpperCase().contains("BOO.ST")){
                    return "Economy";
                }
                if(oldMethod.toUpperCase().contains("DHL EXPRESS")){
                    return "International Economy";
                }

                if(oldMethod.toUpperCase().contains("FEDEX STANDARD OVERNIGHT"))
                {
                    order.setSignatureRequired(true);
                    return "Overnight";
                }  else if(oldMethod.toUpperCase().contains("FEDEX 2ND DAY AIR")||oldMethod.toUpperCase().contains("FEDEX 2"))
                {
                    order.setSignatureRequired(true);
                    return "2 day";

                }  else if(oldMethod.toUpperCase().contains("FEDEX PRIORITY OVERNIGHT"))
                {
                    order.setSignatureRequired(true);
                    return "Overnight";

                }else if(oldMethod.toUpperCase().contains("FEDEX GROUND"))
                {
                    return "Ground";

                }else if(oldMethod.toUpperCase().contains("FIRST-CLASS PACKAGE INTERNATIONAL"))
                {
                    return "International Economy";

                } else if(oldMethod.toUpperCase().contains("PRIORITY MAIL INTERNATIONAL"))
                {
                    return "International Standard";

                }  else if(oldMethod.toUpperCase().contains("FEDEX INTERNATIONAL ECONOMY"))
                {
                    return "International Economy";
                    //Express Shipping
                }  else if(oldMethod.equalsIgnoreCase("Free shipping")||oldMethod.equalsIgnoreCase("Shipping")||oldMethod.equals("")||oldMethod.equalsIgnoreCase("ShippingShipping")){

                    return "Ground";

                }else if(oldMethod.equalsIgnoreCase("Ground w/ Signature Confirmation")){
                    order.setSignatureRequired(true);
                    return "Ground";

                }else if(oldMethod.equalsIgnoreCase("International Economy (tracking not available)")){
                    return "International Economy";
                }else if(oldMethod.equalsIgnoreCase("International Priority")){
                    return "International Standard";
                }else if(oldMethod.equalsIgnoreCase("Standard (5-7 business days)")||
                        oldMethod.equalsIgnoreCase("TheDrop Shipping")||
                        oldMethod.equalsIgnoreCase("WaxiStash Shipping")){
                    return "Ground";

                }else{

                    return oldMethod;
                }

            }
        };

        api.setClientInfo(551, null);
        api.setFirstOrderId(15028);



        api.setOtherFulfillmentSku("SEPARATE");
        api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");
        ApparelMagicApi amApi = new ApparelMagicApi(551,"https://dope.app.apparelmagic.com","42a98c9cf1dffbd86f907689dd768b1f92208d1c","81fcb72f5d182afbd70cbf4438e22cb62ce3dbac");
        //  println   api.getHashedRequest(null)
        amApi.setShopify( api);


        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
       int hour = calendar.get(Calendar.HOUR_OF_DAY);
       if(hour == 8||hour == 18){
           amApi.syncAmSkusToOwd(false);
           amApi.syncAmSkusToOwd(false,16500,17000);

       }else {
           log.debug("Not updating existing sku's");
           amApi.syncAmSkusToOwd(false);
           amApi.syncAmSkusToOwd(false,16500,17000);

       }


        try {
            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
            api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);
             AmazonAPI amazon = downloadAmazonOrders();
            amazon.getProductData();



            Map<String, Integer> updateItems = new HashMap<String,Integer>();
            Map<String, Long> reserved = amApi.getSkuAllocatedMap();
            for(String sku:amazon.getProductList())
            {
                log.debug("checking Amazon SKU "+sku);
                try {
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(551 + "", sku);
                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                    if (reserved.containsKey(sku)) {
                        oh -= reserved.get(sku);
                    }
                    oh = (oh-(oh%2))/2;
                    if (oh < 0) {
                        oh = 0;
                    }
                    updateItems.put(sku,(int)oh);
                    System.out.println("Added "+sku+":"+oh);
                }catch(Exception ex)
                {

                }
            }
            System.out.println(updateItems);
            AmazonFeeds.updateProductStockLevels(amazon.aconfig, updateItems);

        }catch(Exception ex)
        {
            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Error Dope Sync:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + 551, "Dope Hourly Sync", LogableException.errorTypes.ORDER_IMPORT);

        }

       //     Map<String, Long> shopifyOrderedItemsMap = api.getImportedItemMap();


        try {
          /*  api.setSendLocationWithInventory(true);
            api.updateInventoryLevels();*/

        }catch(Exception ex)
        {
            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Error Dope Stock Sync:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + 551, "Dope Hourly Sync", LogableException.errorTypes.ORDER_IMPORT);

        }



    }

    private static AmazonAPI downloadAmazonOrders()
    {

        AmazonConfig config;

        config = new AmazonConfig("AKIAIPEKPYDFHON7ZV4Q",
                "KZhgdc0MP336CPP0S15Yo8xdamiBOPS753KiDFB2",
                "Amazon Downloader",
                "1.0",
                "A2XI8UVY7Q9DJN","https://mws.amazonservices.com/");

        config.addMarketPlaceId("ATVPDKIKX0DER");

        AmazonAPI fetcher = new AmazonAPI(config,551) ;
        fetcher.setTemplate("dopeshopify");

        Map<String,List<String>> shipMap = new TreeMap<String,List<String>>();
        shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY",
                "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY"));
        fetcher.setShipMethodMap(shipMap);


        try {

            fetcher.importCurrentOrders();


        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

         return fetcher;

        //config.addMarketPlaceId("ATVPDKIKX0DER");

        //merchant token A2XI8UVY7Q9DJN

        /*
         Seller account identifiers for DOPE Official
Seller ID: 	A2XI8UVY7Q9DJN

Marketplace ID: 	A2EUQ1WTGCTBG2 (Amazon.ca)
	ATVPDKIKX0DER (Amazon.com)
	A1AM78C64UM0Y8 (Amazon.com.mx)

Developer account identifier and credentials
Developer Account Number: 	4646-9793-3996
AWS Access Key ID: 	AKIAIPEKPYDFHON7ZV4Q
Secret Key: 	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
         */




    }
}
