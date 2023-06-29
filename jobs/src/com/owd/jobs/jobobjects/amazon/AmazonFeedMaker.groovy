package com.owd.jobs.jobobjects.amazon

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.LineItem

import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/18/12
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
class AmazonFeedMaker {



    public static void main(String[] args)
    {
        println "helloc" ;

    }


    public static String getProductAvailabilityFeedXml(AmazonConfig config, Map<String,Integer> items)
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        int mid = 1;
        def stockLevelFeed =
                {
                    mkp.xmlDeclaration()
                    AmazonEnvelope() {
                        Header() {
                            DocumentVersion('1.01')
                            MerchantIdentifier(config.sellerId)
                        }
                        MessageType('Inventory')
                        items.keySet().each{skuValue ->
                            Message(){
                                MessageID(""+(mid++))
                                OperationType('Update')
                                Inventory()
                                        {
                                            SKU(skuValue)
                                            Quantity(items.get(skuValue))
                                            FulfillmentLatency('2')
                                        }

                            }
                        }
                    }
                }


        def testbuilt = builder.bind(stockLevelFeed)
        return testbuilt.toString()

    }


    public static String getOrderShippedFeedXml(AmazonConfig config, List<AmazonAPI.AmazonOrderShipmentInfo> shipments)
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        int mid = 1;
        def orderFulfillFeed =
            {
                mkp.xmlDeclaration()
                AmazonEnvelope() {
                    Header() {
                        DocumentVersion('1.01')
                        MerchantIdentifier(config.sellerId)
                    }
                    MessageType('OrderFulfillment')
                    shipments.each{shipment->
                    Message(){
                        MessageID(""+(mid++))
                        OrderFulfillment()
                                {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T00:00:00'");
                                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
                                    StringBuffer sb = new StringBuffer( sdf.format(calendar.getTime()) );
                                    AmazonOrderID(shipment.getAmazonOrderRef())
                                    FulfillmentDate(sb.toString())
                                    FulfillmentData()
                                            {
                                                CarrierCode((shipment.getShipMethod().toUpperCase().startsWith("UPS")?"UPS":(shipment.getShipMethod().toUpperCase().startsWith("FEDEX")?"FedEx":"USPS")))
                                                ShippingMethod(shipment.getShipMethod())
                                                ShipperTrackingNumber(shipment.getTracking())


                                            }
                                    shipment.getShippedLines().each{ line ->
                                        if(line.getCustRefnum()?.startsWith("AMAZ:") && line.getCustRefnum()?.length()>5 && line.getQuantityActual()>0)
                                        {
                                            Item()
                                                    {
                                                        AmazonOrderItemCode(line.getCustRefnum()?.substring(5))
                                                        Quantity(line.getQuantityActual())
                                                    }
                                        }
                                    }


                                }

                    }
                    }
                }
            }


        def testbuilt = builder.bind(orderFulfillFeed)
        return testbuilt.toString()

    }
    public static String getOrderShippedFeedXml(AmazonConfig config, String shipMethod, String tracking, String orderRefnum, Vector items)
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        int mid = 1;
        def orderFulfillFeed =
            {
                mkp.xmlDeclaration()
                AmazonEnvelope() {
                    Header() {
                        DocumentVersion('1.01')
                        MerchantIdentifier(config.sellerId)
                    }
                    MessageType('OrderFulfillment')

                        Message(){
                            MessageID(""+(mid++))
                            OrderFulfillment()
                                    {
                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                                        StringBuffer sb = new StringBuffer( sdf.format(calendar.getTime()) );
                                        AmazonOrderID(orderRefnum)
                                        FulfillmentDate(sb.toString())
                                        FulfillmentData()
                                                {
                                                   CarrierCode((shipMethod.startsWith("UPS")?"UPS":"USPS"))
                                                    ShippingMethod(shipMethod)
                                                    ShipperTrackingNumber(tracking)


                                                }
                                        for(int i;i<items.size();i++)
                                        {
                                            LineItem line = (LineItem) items.get(i);
                                            if(line.client_ref_num?.startsWith("AMAZ:") && line.client_ref_num?.length()>5 && line.quantity_actual>0)
                                            {
                                               Item()
                                                       {
                                                           AmazonOrderItemCode(line.client_ref_num?.substring(5))
                                                           Quantity(line.quantity_actual)
                                                       }
                                            }
                                        }


                                    }

                    }
                }
            }


        def testbuilt = builder.bind(orderFulfillFeed)
        return testbuilt.toString()

    }

    public static String getOrderAckFeedXml(AmazonConfig config, List<String> oids)
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        int mid = 1;
        def orderAckFeed =
            {
                mkp.xmlDeclaration()
                AmazonEnvelope() {
                    Header() {
                        DocumentVersion('1.01')
                        MerchantIdentifier(config.sellerId)
                    }
                    MessageType('OrderAcknowledgement')
                    for(String oid:oids)
                    {
                        Message(){
                            MessageID(""+(mid++))
                            OrderAcknowledgement()
                                    {
                                        AmazonOrderID(oid)
                                        StatusCode('Success')

                                    }
                        }
                    }
                }
            }

        return builder.bind(orderAckFeed).toString()

    }
}
