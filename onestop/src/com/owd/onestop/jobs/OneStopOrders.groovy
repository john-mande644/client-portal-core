package com.owd.onestop.jobs

import com.owd.core.CountryNames
import com.owd.core.Mailer
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.onestop.test.UnableToImportOrderException
import com.owd.onestop.*

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/14/12
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
class OneStopOrders {

    static Map<String, String> shipMethodMap = new TreeMap<String, String>();

       static {
           shipMethodMap.put("3", "UPS 2nd Day Air");
           shipMethodMap.put("33", "UPS 3 Day Select");
           shipMethodMap.put("1", "UPS Ground");
           shipMethodMap.put("14", "UPS Worldwide Expedited");
       //    shipMethodMap.put("UPS INTL SAVER", "UPS Worldwide Expedited");
       //    shipMethodMap.put("UPS NEXT DAY", "UPS Next Day Air");
           shipMethodMap.put("4", "UPS Next Day Air Saver");
           shipMethodMap.put("39", "USPS Priority Mail");
           shipMethodMap.put("5", "Picked Up");
       }

    static List<String> chargerSkus = new ArrayList<String>();

    static {

   /*     chargerSkus.add("01 461 01");
        chargerSkus.add("01 476 02");
        chargerSkus.add("01 476 03");
        chargerSkus.add("01 501 00");
        chargerSkus.add("01 502 00");
        chargerSkus.add("01 503 00");
        chargerSkus.add("01 504 00");
        chargerSkus.add("01 505 00");
        chargerSkus.add("01 593 00");
        chargerSkus.add("01 622 00");
        chargerSkus.add("04 043 00");
        chargerSkus.add("05 947 01");
        chargerSkus.add("05 947 02");
        chargerSkus.add("05 947 03");
        chargerSkus.add("05 947 04");*/
    }



    public static void main(String[] args)
    {

        importPendingOrders();

    }
    public static void importOneOrder(String orderDetailsXml,ClientPolicy policy) throws UnableToImportOrderException
    {
           def orderDetails = new XmlSlurper().parseText(orderDetailsXml)
                    println orderDetails.OrderInfo.OrderID+":"+orderDetails.OrderItem.size()

            if(orderDetails.OrderInfo.StatusName.equals('Shipped'))
        {
            return;
        }
        if(orderDetails.OrderInfo.StatusName.equals('Canceled'))
        {
            return;
        }

                 Order order = policy.createInitializedOrder()
                 order.order_refnum=orderDetails.OrderInfo.OrderID
        if(OrderUtilities.clientOrderReferenceExists(order.order_refnum,"482",false))
        {
            return;
        }
                 order.order_type="OneStop"
        order.total_shipping_cost=Float.parseFloat(orderDetails.OrderInfo.Shipping.text()+"")
        println "SHIPPING:"+orderDetails.OrderInfo.Shipping.text()
        println "TAX:"+orderDetails.OrderInfo.Tax.text()
        order.total_tax_cost=Float.parseFloat(orderDetails.OrderInfo.Tax.text()+"")
        order.discount=Math.abs(Float.parseFloat(orderDetails.OrderInfo.PromoCodeAmountApplied.text()+""))

        String shipMethod = orderDetails.OrderInfo.ShipChoice.text()
        if(shipMethodMap.containsKey(shipMethod))
        {
            order.getShippingInfo().setShipOptions(shipMethodMap.get(shipMethod),"Prepaid","")
        }   else
        {
            throw new UnableToImportOrderException("Ship method code value "+shipMethod+" not recognized");
        }


        order.getBillingContact().name = orderDetails.BillingAddress.FirstName.text()+" "+ orderDetails.BillingAddress.LastName.text()
        order.getBillingContact().email = orderDetails.BillingAddress.EMail.text()
        order.getBillingContact().phone = orderDetails.BillingAddress.PhoneNumber.text()
        order.getBillingAddress().address_one = orderDetails.BillingAddress.AddressLine1.text()
        order.getBillingAddress().address_two = orderDetails.BillingAddress.AddressLine2.text()
        order.getBillingAddress().city = orderDetails.BillingAddress.City.text()
        order.getBillingAddress().state = orderDetails.BillingAddress.State.text()
        order.getBillingAddress().zip = orderDetails.BillingAddress.PostalCode.text()
        order.getBillingAddress().country = orderDetails.BillingAddress.ISOCode.text()
         order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

        order.getShippingContact().name = orderDetails.ShippingAddress.FirstName.text()+" "+ orderDetails.ShippingAddress.LastName.text()
        order.getShippingContact().email = orderDetails.ShippingAddress.EMail.text()
        order.getShippingContact().phone = orderDetails.ShippingAddress.PhoneNumber.text()
        order.getShippingAddress().address_one = orderDetails.ShippingAddress.AddressLine1.text()
        order.getShippingAddress().address_two = orderDetails.ShippingAddress.AddressLine2.text()
        order.getShippingAddress().city = orderDetails.ShippingAddress.City.text()
        order.getShippingAddress().state = orderDetails.ShippingAddress.State.text()
        order.getShippingAddress().zip = orderDetails.ShippingAddress.PostalCode.text()
        order.getShippingAddress().country = orderDetails.ShippingAddress.ISOCode.text()
         order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);

        println order.getShippingAddress()

                 boolean gotItems = false;
                 orderDetails.OrderItem.each
                         {

                             String sku = it.UPC.text().trim()
                              if (LineItem.SKUExists("482", sku + "/KIT")) {
                            sku = sku + "/KIT";
                        }
                             gotItems = true;
                             //    println "->"+it.UPC+":"+it.ModelName.text().trim()+":"+it.Quantity
                             if (sku.length() == 0) {
                                 //    println "BAD SKU"
                                 throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to missing SKU ("+sku+")value for item ID " + it.RecordID)
                             }
                             if (!(LineItem.SKUExists("482", sku))) {
                                 if("-1".equals(sku) && it.Description.text().contains("Gift Certificate"))
                                 {
                                     markOrderAsReceived(orderDetails.OrderInfo.OrderID.toString())

                                 }
                                 throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to invalid SKU value " + it.UPC + " for item ID " + it.RecordID)
                             }
                             order.addLineItem(sku, ""+it.Quantity.text()?.trim(),
                                     ""+it.UnitCost.text()?.trim(), "0.00", ""+it.ModelName.text()?.trim(), ""+it.SizeName.text()?.trim(), ""+it.ColorName.text()?.trim());

                         }
                 if(!gotItems)
                 {
                     throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to no order items seen")

                 }

        
        int chargersToAdd = 0;
        for(LineItem item:order.skuList)
        {
            if(chargerSkus.contains(item.client_part_no))
            {
                chargersToAdd += item.quantity_request;
            }
        }
        if(chargersToAdd>0)
        {
            order.addLineItem("15 201 00", ""+chargersToAdd,
                    "0.00", "0.00", "", "", "");
        }

        Calendar c = new GregorianCalendar(2016, Calendar.JANUARY, 6);
        Date today =  new Date()

        if(today >= c.getTime())
        {
            order.addInsertItemIfAvailable("1004",1);
        }

        System.out.println("saving order " + order.order_refnum + " with item list size=" + order.skuList.size());
                                order.bill_cc_type="CL";
                                order.payment_status=OrderXMLDoc.kPaymentStatusClientManaged;
        policy.saveNewOrder(order, false);

        String reference = order.orderNum;
         System.out.println("OWD reference=:"+order.orderNum+":");
            if ("".equals(reference.trim())) {
                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                    System.out.println("reporting error on import "+order.last_payment_error + " " + order.last_error);
                    throw new UnableToImportOrderException("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                } else {
                    markOrderAsReceived(orderDetails.OrderInfo.OrderID.toString())
                    throw new UnableToImportOrderException("The order could not be completed due to a duplicate order reference.");

                }
            }
                 println "Order " + orderDetails.OrderInfo.OrderID + " good!"
                 markOrderAsReceived(orderDetails.OrderInfo.OrderID.toString())
    }


    public static void importPendingOrders() throws Exception {

        Client client = Client.getClientForID("482");

        ClientPolicy policy = client.getClientPolicy();

        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();

        String pendingOrdersIds = getPendingOrdersXML()
         def pendingOrders = new XmlSlurper().parseText(pendingOrdersIds)

         pendingOrders.OrderID.each {
                println "OrderID:"+it;

             importOneStopOrder(it.toString(),policy,caller)
             /*
 <OrderDetails>
   <OrderInfo>
     <OrderID>5001603</OrderID>
     <OrderDate>11/9/2011 1:14:21 PM</OrderDate>
     <StatusCode>1</StatusCode>
     <StatusName>Placed</StatusName>
     <TotalPieces>9</TotalPieces>
     <UserIPAddress>10.10.99.37</UserIPAddress>
     <ShipDate>11/10/2011 12:00:00 AM</ShipDate>
     <ShipChoice>30</ShipChoice>
     <ShipChoiceName>FedEx Ground International US to Puerto Rico</ShipChoiceName>
     <IsShippingDomestic>False</IsShippingDomestic>
     <Grandtotal>134.9500</Grandtotal>
     <PromoCodeAmountApplied>0</PromoCodeAmountApplied>
     <ChargedAmount>134.9500</ChargedAmount>
     <SubTotal>104.0000</SubTotal>
     <Tax>0.0000</Tax>
     <Shipping>30.9500</Shipping>
     <TotalLineItems>3</TotalLineItems>
     <IsGiftBox>False</IsGiftBox>
   </OrderInfo>
   <OrderItem>
     <OrderID>5001603</OrderID>
     <CategoryID>56</CategoryID>
     <CategoryName>Condoms &amp; Lubricants -&gt; Personal Lubricants</CategoryName>
     <ProductID>1674</ProductID>
     <MFGSKU>07 210 00</MFGSKU>
     <ModelName>BabeLube</ModelName>
     <Description>&lt;i&gt;Improved formula!&lt;/i&gt; You asked for it, and you got it: try the newest generation of the classic BabeLube, now made without parabens while retaining the classic gel consistency, non-irritating formula, and non-sticky slickness. Plus, it's glycerin-free, non-staining, gluten-free, great for sensitive skin, has very minimal taste/odor, and is ideal for use with any sex toy material. You'll also find that it works fabulously for both vaginal and anal penetration! What more could you ask for?</Description>
     <SizeID>941</SizeID>
     <SizeName>NA</SizeName>
     <SizeMFGSKU />
     <ColorID>671</ColorID>
     <ColorName>16oz</ColorName>
     <ColorMFGSKU>07 210 16</ColorMFGSKU>
     <UnitCost>22.0000</UnitCost>
     <Quantity>2</Quantity>
     <ExtendedAmount>44.0000</ExtendedAmount>
     <RecordID>1953564</RecordID>
     <UPC>07 210 16</UPC>
     <UpcHash>U(a72101*QR</UpcHash>
     <HideHyperlinks>False</HideHyperlinks>
     <UPCList>1798359:07 210 16</UPCList>
     <AllowReturn>True</AllowReturn>
     <LineItemTax>0.0000</LineItemTax>
   </OrderItem>
   <OrderItem>
     <OrderID>5001603</OrderID>
     <CategoryID>56</CategoryID>
     <CategoryName>Condoms &amp; Lubricants -&gt; Personal Lubricants</CategoryName>
     <ProductID>2924</ProductID>
     <MFGSKU>09 116 00</MFGSKU>
     <ModelName>Get Naked Dice</ModelName>
     <Description>Tumble your way into bed with the Get Naked Dice. The weighty chrome-plated metal is a shinier, sexier version of our pink &lt;a href="http://store.babeland.com/sexy-games/dirty-dice"&gt;Dirty Dice&lt;/a&gt;, and makes for easy rolling right on the bed. One side gives an instruction and the other tells you where to do it: Lick Breasts. Includes black velveteen carrying pouch. &lt;ul&gt;&lt;li&gt;Size: Two 1-square-inch dice&lt;/li&gt;&lt;li&gt;Material: Aluminum&lt;/li&gt;&lt;/ul&gt;</Description>
     <SizeID>805</SizeID>
     <SizeName>NA</SizeName>
     <SizeMFGSKU />
     <ColorID>1715</ColorID>
     <ColorName>Get Naked Dice</ColorName>
     <ColorMFGSKU />
     <UnitCost>12.0000</UnitCost>
     <Quantity>3</Quantity>
     <ExtendedAmount>36.0000</ExtendedAmount>
     <RecordID>1953567</RecordID>
     <UPC />
     <UpcHash>U(a</UpcHash>
     <HideHyperlinks>False</HideHyperlinks>
     <UPCList>1799401:</UPCList>
     <AllowReturn>True</AllowReturn>
     <LineItemTax>0.0000</LineItemTax>
   </OrderItem>
   <OrderItem>
     <OrderID>5001603</OrderID>
     <CategoryID>56</CategoryID>
     <CategoryName>Condoms &amp; Lubricants -&gt; Personal Lubricants</CategoryName>
     <ProductID>2954</ProductID>
     <MFGSKU>09 313 00</MFGSKU>
     <ModelName>Slip Not</ModelName>
     <Description>Nothing ruins a rockin' good time like a dildo pulling free from its harness. Fix that problem with the Slip Not. Simply slide it onto a dildo-especially one with a small base-before you put it in the harness to keep the dil secure and to add some extra cushioning in case of a bumpy ride. Shortens the length of the dildo by 1/2".</Description>
     <SizeID>279</SizeID>
     <SizeName>NA</SizeName>
     <SizeMFGSKU />
     <ColorID>1745</ColorID>
     <ColorName>Slip Not</ColorName>
     <ColorMFGSKU />
     <UnitCost>6.0000</UnitCost>
     <Quantity>4</Quantity>
     <ExtendedAmount>24.0000</ExtendedAmount>
     <RecordID>1953570</RecordID>
     <UPC />
     <UpcHash>U(a</UpcHash>
     <HideHyperlinks>False</HideHyperlinks>
     <UPCList>1799431:</UPCList>
     <AllowReturn>True</AllowReturn>
     <LineItemTax>0.0000</LineItemTax>
   </OrderItem>
   <BillingAddress>
     <AddressID>251769</AddressID>
     <FirstName>Vera</FirstName>
     <LastName>Stewart</LastName>
     <AddressLine1>176 Cumberland Street</AddressLine1>
     <AddressLine2 />
     <City>Sydney</City>
     <State xml:space="preserve">  </State>
     <StateName>INTERNATIONAL</StateName>
     <PostalCode>NSW 2000</PostalCode>
     <CountryName>Puerto Rico</CountryName>
     <CountryCode>182</CountryCode>
     <ISOCode>PR</ISOCode>
     <Other />
     <EMail>jacqui@onestop.com</EMail>
     <PhoneNumber>+61 2 9250 6000 ?</PhoneNumber>
   </BillingAddress>
   <ShippingAddress>
     <AddressID>251772</AddressID>
     <FirstName>Vera</FirstName>
     <LastName>Stewart</LastName>
     <AddressLine1>176 Cumberland Street</AddressLine1>
     <AddressLine2 />
     <City>Sydney</City>
     <State xml:space="preserve">  </State>
     <StateName>INTERNATIONAL</StateName>
     <PostalCode>NSW 2000</PostalCode>
     <CountryName>Puerto Rico</CountryName>
     <CountryCode>182</CountryCode>
     <ISOCode>PR</ISOCode>
     <Other />
     <PhoneNumber>+61 2 9250 6000 ?</PhoneNumber>
   </ShippingAddress>
 </OrderDetails>
              */

         }


     }

    public static void importOneStopOrder(String id, ClientPolicy policy,IOrderProcessing caller) {

        String orderDetailXml = "";
        try {
            OrderDetailsRequest odr = new OrderDetailsRequest();
            odr.setClientDetails(OneStopService.cd)
            odr.setOrderId(Integer.parseInt(id))

            OrderDetailsResponse odresp = caller.getOrderDetails(odr)
            //     println "ORDER DETAILS ----"
            print odresp.getOrderDataXml()
            orderDetailXml = odresp.getOrderDataXml()

            importOneOrder(orderDetailXml, policy)


        } catch (UnableToImportOrderException ex) {
            println ex.getMessage()
            try {
                Mailer.sendMail("Import Error for OneStop", ex.getStackTrace() + "\r\n\r\n" + orderDetailXml, "owditadmin@owd.com");
            } catch (Exception exm) {

            }


        } catch (Exception ex) {

            try {
                Mailer.sendMail("Generic Import Error for OneStop", ex.getMessage(), "owditadmin@owd.com");
            } catch (Exception exm) {

            }
        }
    }

    public static String getPendingOrdersXML()
    {
          IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();


         UnprocessedOrderRequest orderRequest = new UnprocessedOrderRequest();

         orderRequest.setClientDetails(OneStopService.cd);
         //  orderRequest.setOrderStatus( StatusCodes.Null);
         OrderDetailsResponse response = caller.getNextUnprocessedOrder(orderRequest);
         System.out.println("status:"+response.operationResponseStatus.processStatusMessage+":"+response.operationResponseStatus.responseStatusMessage);
           System.out.println(response.getOrderDataXml());
         /*
         <UnprocessedOrder>
   <OrderID>5000103</OrderID>
   <OrderID>5000203</OrderID>
   <OrderID>5000303</OrderID>
   <OrderID>5000403</OrderID>
   <OrderID>5000503</OrderID>
   <OrderID>5000603</OrderID>
   <OrderID>5000703</OrderID>
   <OrderID>5000803</OrderID>
   <OrderID>5000903</OrderID>
   <OrderID>5001003</OrderID>
   <OrderID>5001103</OrderID>
   <OrderID>5001203</OrderID>
   <OrderID>5001303</OrderID>
   <OrderID>5001403</OrderID>
   <OrderID>5001503</OrderID>
   <OrderID>5001603</OrderID>
   <OrderID>5002203</OrderID>
 </UnprocessedOrder>
          */
         return response.getOrderDataXml()
    }

    public static void markOrderAsReceived(String remoteOrderId)
    {
        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();

        UpdateOrderStatusRequest uosr = new UpdateOrderStatusRequest();
                           uosr.setClientDetails(OneStopService.cd)
                           uosr.setOrderId(Integer.parseInt(remoteOrderId))
                           uosr.setOrderStatus(StatusCodes.StatusCode_InProgress)
                           MarkOrderAsRetrievedResponse morr = caller.markOrderAsRetrieved(uosr)
                           println "MarkRetrievedResponse for "+remoteOrderId+":"
                           println morr.operationResponseStatus.processStatusCode
                           println morr.operationResponseStatus.processStatusMessage
                           println morr.operationResponseStatus.responseStatusCode
                           println morr.operationResponseStatus.responseStatusMessage

    }

}
