package com.owd.onestop.jobs

import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import org.hibernate.SQLQuery
import com.owd.onestop.IOrderProcessing
import com.owd.onestop.SettlementDetails
import com.owd.onestop.test.DateToXsdDatetimeFormatter
import com.owd.onestop.SettlementOperationResponse
import com.owd.core.Mailer
import com.owd.core.OWDUtilities

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/15/12
 * Time: 12:37 AM
 * To change this template use File | Settings | File Templates.
 */
class OneStopShipments {


     static Map<String, String> shipMethodMap = new TreeMap<String, String>();

       static {
           shipMethodMap.put( "UPS 2nd Day Air","3");
           shipMethodMap.put("UPS 3 Day Select","33");
           shipMethodMap.put( "UPS Ground","1");
           shipMethodMap.put("UPS Worldwide Expedited","14");
       //    shipMethodMap.put("UPS INTL SAVER", "UPS Worldwide Expedited");
       //    shipMethodMap.put("UPS NEXT DAY", "UPS Next Day Air");
           shipMethodMap.put("UPS Next Day Air Saver","4");
           shipMethodMap.put("UPS Next Day Air","4");
           shipMethodMap.put( "USPS Priority Mail","39");
           shipMethodMap.put("Picked Up","5");
       }


    public static void main(String[] args)
    {
        reportShipmentsToOneStop()
    }
    public static void reportShipmentsToOneStop()
    {


        try {

            SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=1 from owd_order_track t join owd_order o on order_id=order_fkey and t.is_void=0 and o.order_status='Shipped'  and order_type='OneStop' and client_fkey=482 and t.reported=0;");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            List<Object[]> data = HibernateSession.currentSession().createSQLQuery("select distinct order_id,order_refnum," +
                    " carr_service,o.tracking_nums\n" +
                    " from owd_order o join owd_order_track t on t.order_fkey=order_id and t.is_void=0 join owd_order_ship_info s on order_id=s.order_fkey\n" +
                    " where client_fkey=482 and order_status='Shipped' and order_type='OneStop'\n" +
                    " and t.reported=1\n" +
                    " order by order_id").list();

          //  List<OneStopShipment> shipments = new ArrayList<OneStopShipment>();
            for(Object[] shipmentdata:data)
            {

                OneStopShipment oss = new OneStopShipment();
                try
                {
                    oss.id = shipmentdata[0]

                oss.reference = shipmentdata[1]
                oss.shipMethod = shipmentdata[2]
                oss.tracking = shipmentdata[3]
                 List<Object[]> itemdata = HibernateSession.currentSession().createSQLQuery("select inventory_num,quantity_actual \n" +
                    " from owd_line_item where quantity_actual>0 and inventory_num<>'1004' and order_fkey="+oss.id).list();
                for(Object[] idata:itemdata)
                {
                    if(oss.items.get(""+idata[0])!=null)
                    {
                        oss.items.put(idata[0]+"",""+(Integer.parseInt(""+idata[1])+Integer.parseInt(oss.items.get(""+idata[0]))))

                    } else
                    {
                        oss.items.put(idata[0]+"",""+(Integer.parseInt(""+idata[1])))

                    }
                }
                //shipments.add(oss)
                println oss
                sendShipmentUpdate(oss)
                markShipmentAsReported(oss)
                }catch(Exception ex)
                {
                    println "Unable to report shipment for order id "+shipmentdata[0]+", oss="+oss
                     try{
                 Mailer.sendMail("Report Shipments Error for OneStop", "Unable to report shipment for order id "+shipmentdata[0]+", oss="+oss+"\r\n\r\n"+OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
                 }catch(Exception exm)
                 {

                 }
                }
                println "done"
            }

        }catch(Exception ex)
        {
           ex.printStackTrace();
        }
    }



    public static void markShipmentAsReported(OneStopShipment oss) {

               SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 from owd_order_track t join owd_order o on order_id=order_fkey and order_fkey="+oss.id+" and t.is_void=0 and o.order_status='Shipped'  and order_type='OneStop' and client_fkey=482 and t.reported=1;");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
    }
    public static void sendShipmentUpdate(OneStopShipment oss)  throws Exception{

      //  def orderDetails = new XmlSlurper().parseText(orderDetailsXml)
      //

        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();
        SettlementDetails sd = new SettlementDetails();
        sd.setClientDetails(OneStopService.cd)

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'


        def shipxml =
        {
            mkp.xmlDeclaration()
            OrderDetails() {
                OrderInfo() {
                    OrderID(oss.reference)
                    ShipmentDetail() {
                        Package() {
                            TrackingNumber(oss.tracking)
                            ShipName(shipMethodMap.keySet().contains(oss.shipMethod)?oss.shipMethod:"UPS International")
                            ShipType(shipMethodMap.keySet().contains(oss.shipMethod)?shipMethodMap.get(oss.shipMethod):"14")
                            ShipDateTime(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                            ShipItemDetails()
                                    {
                                        oss.items.keySet().each {
                                           String sku = (""+it).trim()
                                           String shipped = oss.items.get(it).trim()
                                            ShipItem() {
                                                UPC(sku.replace("/KIT",""))
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
            if(!((""+response.operationResponseStatus.responseStatusCode).equals("0")))
            {
               throw new Exception("Error response ("+response.operationResponseStatus.responseStatusMessage+") reporting shipment to OneStop for oss:"+oss)
            }
    }

}

public class OneStopShipment
{
    String id
    String reference
    String tracking =""
    String shipMethod
    String shipMethodCode=""
    Map<String,String> items = new TreeMap<String, String>()


    public String toString ( ) {
    return "OneStopShipment{" +
    "id='" + id + '\'' +
    ", reference='" + reference + '\'' +
    ", tracking='" + tracking + '\'' +
    ", shipMethod='" + shipMethod + '\'' +
    ", shipMethodCode='" + shipMethodCode + '\'' +
    ", items=" + items +
    '}' ;
    }}
