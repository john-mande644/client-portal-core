package com.owd.onestop.test

import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.onestop.OrderDetailsResponse
import com.owd.onestop.UnprocessedOrderRequest
import com.owd.onestop.IOrderProcessing
import com.owd.onestop.OrderProcessingLocator
import com.owd.onestop.OrderProcessing
import com.owd.onestop.ClientDetails
import com.owd.onestop.OrderDetailsRequest
import com.owd.core.business.order.Inventory
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.OrderUtilities
import com.owd.core.business.order.OrderStatus
import com.owd.onestop.SettlementDetails
import com.owd.onestop.SettlementOperationResponse
import com.owd.onestop.InventoryDetails
import com.owd.onestop.InventoryOperationResponse
import com.owd.hibernate.generated.OwdLineItem
import com.owd.onestop.UpdateOrderStatusRequest
import com.owd.onestop.MarkOrderAsRetrievedResponse
import com.owd.onestop.StatusCodes
import com.owd.onestop.jobs.OneStopOrders
import com.owd.onestop.jobs.OneStopService
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import com.owd.onestop.ReturnDetails
import com.owd.onestop.ReturnOperationResponse
import com.owd.hibernate.generated.OwdInventory
import org.apache.axis.EngineConfiguration
import org.apache.axis.configuration.EngineConfigurationFactoryFinder
import org.apache.axis.configuration.SimpleProvider
import org.apache.axis.transport.http.CommonsHTTPSender
import org.hibernate.criterion.Expression
import com.owd.hibernate.HibernateSession

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/21/11
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
class OneStopTest {


    public static void importPendingOrders() throws Exception {

      //  Client client = Client.getClientForID("482");

      //  ClientPolicy policy = client.getClientPolicy();

        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();

        String pendingOrdersIds = OneStopOrders.getPendingOrdersXML()
        def pendingOrders = new XmlSlurper().parseText(pendingOrdersIds)
    }

    static ClientDetails cd;
    static OrderProcessing service;

    static {
        cd = new ClientDetails();
        cd.setClientId("91");
        cd.setClinetPassword("Alpha123");
        cd.setClinetUsername("owd@onestop.com");

        EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        def mike = new SimpleProvider(engine);
        CommonsHTTPSender sender = new CommonsHTTPSender();
        sender.httpChunkStream = false;
        mike.deployTransport("http", sender);

        service = new OrderProcessingLocator(mike);
    }
    public static void main(String[] args) throws Exception {
      //  testOrderImport()
       // testInventoryUpdate()
        for(int i=0; i<100;i++)
        {
            try{
                println   "Start"
                importPendingOrders()
            }catch(Exception ex)
            {
                ex.printStackTrace()
            }
            sleep(1000)
        }

    }

    public static void testInventoryUpdate() {
        IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
        InventoryDetails sd = new InventoryDetails();
        sd.setClientDetails(cd)


        List<OwdInventory> items = HibernateSession.currentSession().createCriteria(OwdInventory.class).add(Expression.eq("owdClient.clientId",new Integer(482))).list()

        System.out.println(items);
        Map<String,String> testItemMap = new TreeMap<String, String>()
        int count = 0;
        items.each {  item ->
            println item.inventoryNum
            count++
         //   if(count<700)
        //    {
            if(item.isAutoInventory==0)
            {
        testItemMap.put(item.inventoryNum.endsWith("/KIT")?item.inventoryNum.substring(0,item.inventoryNum.indexOf("/KIT")):item.inventoryNum,item.inventoryNum.endsWith("/KIT")?"1000":((item.getOwdInventoryOh().qtyOnHand).toString()))
            }
        //    }
        }
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def itemxmlrequest =
        {
            /* <xs:element name="InventoryInfo" msdata:IsDataSet="false" msdata:UseCurrentLocale="false">
    <xs:complexType>
      <xs:choice minOccurs="0" maxOccurs="unbounded">
        <xs:element name="InventoryItems ">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="UPC" type="xs:string" minOccurs="1" />
              <xs:element name="Quantity" type="xs:integer" minOccurs="1" />
              <xs:element name="UpdateDate" type="xs:dateTime" minOccurs="0" />
            </xs:sequence>
          </xs:complexType>
        </xs:element>*/

        InventoryInfo() {
            for(String sku:testItemMap.keySet())
            {
            InventoryItems()
                    {
                        UPC(sku)
                        Quantity(testItemMap.get(sku))
                    }
            }
        }


        }

        String itemupdatexml = builder.bind(itemxmlrequest).toString()
        println itemupdatexml

       sd.setInventoryDataXml(itemupdatexml)

        InventoryOperationResponse response = caller.inventoryIncrementAction(sd);

        println "processCode:" + response.operationResponseStatus.processStatusCode
        println "processMessage:" + response.operationResponseStatus.processStatusMessage
        println "responseCode:" + response.operationResponseStatus.responseStatusCode
        println "responseMessage:" + response.operationResponseStatus.responseStatusMessage

    }

    public static void testShipmentUpdate(String orderDetailsXml) {

        def orderDetails = new XmlSlurper().parseText(orderDetailsXml)


        IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
        SettlementDetails sd = new SettlementDetails();
        sd.setClientDetails(cd)

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'

        String orderId = orderDetails.OrderInfo.OrderID.text()
        def shipxml =
        {
            mkp.xmlDeclaration()
            OrderDetails() {
                OrderInfo() {
                    OrderID(orderId)
                    ShipmentDetail() {
                        Package() {
                            TrackingNumber("1ZE587150332392489")
                            ShipName("UPS Ground")
                            ShipType("13")
                            ShipDateTime(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                            ShipItemDetails()
                                    {
                                        orderDetails.OrderItem.each {
                                           String sku = it.UPC.text().trim()
                                           String shipped = it.Quantity.text().trim()
                                            ShipItem() {
                                                UPC(sku)
                                                QuantityShipped(shipped)
                                            }
                                        }

                                    }
                        }
                    }
                }

            }

        }
        String xmlSent = builder.bind(shipxml).toString()
        print xmlSent
        sd.setPaymentAndShippingDataXml(xmlSent)

        SettlementOperationResponse response = caller.postSettlement(sd);

        println "SettlementOperationResponse:"
                          println response.operationResponseStatus.processStatusCode
                          println response.operationResponseStatus.processStatusMessage
                          println response.operationResponseStatus.responseStatusCode
                          println response.operationResponseStatus.responseStatusMessage

    }


    public static void testReturnAllOKReport(String orderDetailsXml) {

              def orderDetails = new XmlSlurper().parseText(orderDetailsXml)

              IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
              ReturnDetails sd = new ReturnDetails();
              sd.setClientDetails(cd)

              def builder = new groovy.xml.StreamingMarkupBuilder()
              builder.encoding = 'UTF-8'

              String orderId = orderDetails.OrderInfo.OrderID.text()
              def shipxml =
              {
                  mkp.xmlDeclaration()
                  OrderDetails() {
                      OrderInfo() {
                          OrderID(orderId)
                          ReturnDetails() {
                              Restockable() {
                                              orderDetails.OrderItem.each {
                                                 String sku = it.UPC.text().trim()
                                                 String shipped = it.Quantity.text().trim()
                                                  ReturnItem() {
                                                      UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                  }
                                              }

                                          }
                              }
                          }
                      }



              }
              String xmlSent = builder.bind(shipxml).toString()
              print xmlSent
              sd.setReturnDataXml(xmlSent)

              ReturnOperationResponse response = caller.postReturns(sd);

              println "ReturnOperationResponse:"
                                println response.operationResponseStatus.processStatusCode
                                println response.operationResponseStatus.processStatusMessage
                                println response.operationResponseStatus.responseStatusCode
                                println response.operationResponseStatus.responseStatusMessage

          }

       public static void testReturnOneOKReport(String orderDetailsXml) {

                  def orderDetails = new XmlSlurper().parseText(orderDetailsXml)

                  IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
                  ReturnDetails sd = new ReturnDetails();
                  sd.setClientDetails(cd)

                  def builder = new groovy.xml.StreamingMarkupBuilder()
                  builder.encoding = 'UTF-8'

                  String orderId = orderDetails.OrderInfo.OrderID.text()
                  def shipxml =
                  {
                      mkp.xmlDeclaration()
                      OrderDetails() {
                          OrderInfo() {
                              OrderID(orderId)
                              ReturnDetails() {
                                  Restockable() {
                                      int itemsProcessed = 0;
                                                  orderDetails.OrderItem.each {
                                                      itemsProcessed++;
                                                      if(itemsProcessed==1)
                                                      {
                                                     String sku = it.UPC.text().trim()
                                                     String shipped = it.Quantity.text().trim()
                                                      ReturnItem() {
                                                          UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                      }
                                                      }
                                                  }

                                              }
                                  }
                              }
                          }



                  }
                  String xmlSent = builder.bind(shipxml).toString()
                  print xmlSent
                  sd.setReturnDataXml(xmlSent)

                  ReturnOperationResponse response = caller.postReturns(sd);

                  println "ReturnOperationResponse:"
                                    println response.operationResponseStatus.processStatusCode
                                    println response.operationResponseStatus.processStatusMessage
                                    println response.operationResponseStatus.responseStatusCode
                                    println response.operationResponseStatus.responseStatusMessage

              }

   public static void testReturnOneNewItemReport(String orderDetailsXml) {

                  def orderDetails = new XmlSlurper().parseText(orderDetailsXml)

                  IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
                  ReturnDetails sd = new ReturnDetails();
                  sd.setClientDetails(cd)

                  def builder = new groovy.xml.StreamingMarkupBuilder()
                  builder.encoding = 'UTF-8'

                  String orderId = orderDetails.OrderInfo.OrderID.text()
                  def shipxml =
                  {
                      mkp.xmlDeclaration()
                      OrderDetails() {
                          OrderInfo() {
                              OrderID(orderId)
                              ReturnDetails() {
                                  NotRestockable() {
                                                     String sku = "01 613 03"
                                                     String shipped = "1"
                                                      ReturnItem() {
                                                          UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                      }
                                                  }

                                              }
                                  }
                              }




                  }
                  String xmlSent = builder.bind(shipxml).toString()
                  print xmlSent
                  sd.setReturnDataXml(xmlSent)

                  ReturnOperationResponse response = caller.postReturns(sd);

                  println "ReturnOperationResponse:"
                                    println response.operationResponseStatus.processStatusCode
                                    println response.operationResponseStatus.processStatusMessage
                                    println response.operationResponseStatus.responseStatusCode
                                    println response.operationResponseStatus.responseStatusMessage

              }

   public static void testReturnOneGoodOneBadReport(String orderDetailsXml) {

                  def orderDetails = new XmlSlurper().parseText(orderDetailsXml)

                  IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
                  ReturnDetails sd = new ReturnDetails();
                  sd.setClientDetails(cd)

                  def builder = new groovy.xml.StreamingMarkupBuilder()
                  builder.encoding = 'UTF-8'

                  String orderId = orderDetails.OrderInfo.OrderID.text()
                  def shipxml =
                  {
                      mkp.xmlDeclaration()
                      OrderDetails() {
                          OrderInfo() {
                              OrderID(orderId)
                              ReturnDetails() {
                                  Restockable() {
                                      int itemsProcessed = 0;
                                                                                 orderDetails.OrderItem.each {
                                                                                     itemsProcessed++;
                                                                                     if(itemsProcessed==1)
                                                                                     {
                                                                                    String sku = it.UPC.text().trim()
                                                                                    String shipped = it.Quantity.text().trim()
                                                                                     ReturnItem() {
                                                                                         UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                                                     }
                                                                                     }
                                                                                 }


                                              }
                                   NotRestockable() {
                                       int itemsProcessed = 0;
                                                                                  orderDetails.OrderItem.each {
                                                                                      itemsProcessed++;
                                                                                      if(itemsProcessed==2)
                                                                                      {
                                                                                     String sku = it.UPC.text().trim()
                                                                                     String shipped = it.Quantity.text().trim()
                                                                                      ReturnItem() {
                                                                                          UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                                                      }
                                                                                      }
                                                                                  }


                                              }
                                  }
                              }
                          }



                  }
                  String xmlSent = builder.bind(shipxml).toString()
                  print xmlSent
                  sd.setReturnDataXml(xmlSent)

                  ReturnOperationResponse response = caller.postReturns(sd);

                  println "ReturnOperationResponse:"
                                    println response.operationResponseStatus.processStatusCode
                                    println response.operationResponseStatus.processStatusMessage
                                    println response.operationResponseStatus.responseStatusCode
                                    println response.operationResponseStatus.responseStatusMessage

              }

       public static void testReturnOneBadReport(String orderDetailsXml) {

                      def orderDetails = new XmlSlurper().parseText(orderDetailsXml)

                      IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
                      ReturnDetails sd = new ReturnDetails();
                      sd.setClientDetails(cd)

                      def builder = new groovy.xml.StreamingMarkupBuilder()
                      builder.encoding = 'UTF-8'

                      String orderId = orderDetails.OrderInfo.OrderID.text()
                      def shipxml =
                      {
                          mkp.xmlDeclaration()
                          OrderDetails() {
                              OrderInfo() {
                                  OrderID(orderId)
                                  ReturnDetails() {
                                   NotRestockable() {
                                       int itemsProcessed = 0;
                                                                                  orderDetails.OrderItem.each {
                                                                                      itemsProcessed++;
                                                                                      if(itemsProcessed==1)
                                                                                      {
                                                                                     String sku = it.UPC.text().trim()
                                                                                     String shipped = it.Quantity.text().trim()
                                                                                      ReturnItem() {
                                                                                          UPC(sku)
                                                      ReasonDesc("return notes")
                                                      CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                                      Quantity(shipped)
                                                                                      }
                                                                                      }
                                                                                  }


                                              }
                                      }
                                  }
                              }



                      }
                      String xmlSent = builder.bind(shipxml).toString()
                      print xmlSent
                      sd.setReturnDataXml(xmlSent)

                      ReturnOperationResponse response = caller.postReturns(sd);

                      println "ReturnOperationResponse:"
                                        println response.operationResponseStatus.processStatusCode
                                        println response.operationResponseStatus.processStatusMessage
                                        println response.operationResponseStatus.responseStatusCode
                                        println response.operationResponseStatus.responseStatusMessage

                  }


    public static void testOrderImport() throws Exception {
        IOrderProcessing caller = service.getBasicHttpBinding_IOrderProcessing();
        UnprocessedOrderRequest orderRequest = new UnprocessedOrderRequest();

        orderRequest.setClientDetails(cd);
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
        print response.getOrderDataXml()
        def pendingOrders = new XmlSlurper().parseText(response.getOrderDataXml())
        int ordersRetrieved = 0;
        pendingOrders.OrderID.each {
            //   println "OrderID:"+it;

            try {
                OrderDetailsRequest odr = new OrderDetailsRequest();
                odr.setClientDetails(cd)
                odr.setOrderId(Integer.parseInt(it.toString()))

                OrderDetailsResponse odresp = caller.getOrderDetails(odr)
                //     println "ORDER DETAILS ----"

                def orderDetails = new XmlSlurper().parseText(odresp.getOrderDataXml())
                //   println orderDetails.OrderInfo.OrderID+":"+orderDetails.OrderItem.size()
                print odresp.getOrderDataXml()
                boolean gotItems = false;
                orderDetails.OrderItem.each
                        {
                            gotItems = true;
                            //    println "->"+it.UPC+":"+it.ModelName.text().trim()+":"+it.Quantity
                            if (it.UPC.text().length() == 0) {
                                //    println "BAD SKU"
                                throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to missing SKU value for item ID " + it.RecordID)
                            }
                            if (!(LineItem.SKUExists("482", it.UPC.text().trim()))) {
                                throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to invalid SKU value " + it.UPC + " for item ID " + it.RecordID)
                            }
                        }
                if(!gotItems)
                {
                    throw new UnableToImportOrderException("Unable to import order " + orderDetails.OrderInfo.OrderID + " due to no order items seen")

                }
                println "Order " + orderDetails.OrderInfo.OrderID + " good!"
                ordersRetrieved++;

                if(ordersRetrieved>=1)
                {
                  UpdateOrderStatusRequest uosr = new UpdateOrderStatusRequest();
                    uosr.setClientDetails(cd)
                    uosr.setOrderId(Integer.parseInt(orderDetails.OrderInfo.OrderID.toString()))
                    uosr.setOrderStatus(StatusCodes.StatusCode_InProgress)
                    MarkOrderAsRetrievedResponse morr = caller.markOrderAsRetrieved(uosr)
                    println "MarkRetrievedResponse:"
                    println morr.operationResponseStatus.processStatusCode
                    println morr.operationResponseStatus.processStatusMessage
                    println morr.operationResponseStatus.responseStatusCode
                    println morr.operationResponseStatus.responseStatusMessage

                    OneStopTest.testShipmentUpdate(odresp.getOrderDataXml())

               //    OneStopTest.testReturnOneOKReport(odresp.getOrderDataXml())
                //    OneStopTest.testReturnAllOKReport(odresp.getOrderDataXml())
                //    OneStopTest.testReturnOneBadReport(odresp.getOrderDataXml())
                //    OneStopTest.testReturnOneGoodOneBadReport(odresp.getOrderDataXml())
               //     OneStopTest.testReturnOneNewItemReport(odresp.getOrderDataXml())


                }
            } catch (UnableToImportOrderException ex) {
                println ex.getMessage()
               /* if(ex.getMessage().contains("invalid"))
                {
                    UpdateOrderStatusRequest uosr = new UpdateOrderStatusRequest();
                    uosr.setClientDetails(cd)
                    uosr.setOrderId(Integer.parseInt(it.toString()))
                    uosr.setOrderStatus(StatusCodes.StatusCode_InProgress)
                    MarkOrderAsRetrievedResponse morr = caller.markOrderAsRetrieved(uosr)
                    println "MarkRetrievedResponse:"
                    println morr.operationResponseStatus.processStatusCode
                    println morr.operationResponseStatus.processStatusMessage
                    println morr.operationResponseStatus.responseStatusCode
                    println morr.operationResponseStatus.responseStatusMessage

                }*/

            }
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
    <PhoneNumber>+61 2 9250 6000 ‎</PhoneNumber>
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
    <PhoneNumber>+61 2 9250 6000 ‎</PhoneNumber>
  </ShippingAddress>
</OrderDetails>
             */

        }


    }


}

public class UnableToImportOrderException extends Exception {
    public UnableToImportOrderException(String message) {
        super(message)
    }
}