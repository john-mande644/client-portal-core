package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.RetailPro.TransferUtilities

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/22/12
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
class StoreOrder {



    public static String renderFormattedXml(String xml) {
        if (xml) {
            def stringWriter = new StringWriter()
            def node = new XmlParser().parseText(xml.toString());
            new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
            return stringWriter.toString()
        }
        else {
            return ""
        }
    }

    public static void main(String[] args) {
        processStoreOrders()
    }


    public static void processStoreOrders() {




        Map<String, byte[]> toMap = TransferUtilities.getRetailProFiles("SO_")

        for (String filename: toMap.keySet()) {

            println "FILE:"+filename
            def storeOrder = new XmlSlurper().parseText(new String(toMap.get(filename)))

            storeOrder.SO.each { it ->

                try{
                Order order = new Order("482");

                String soId = it.@so_no.text()
                String shipPartial = it.@ship_partial.text().toString().toUpperCase()
                if("TRUE".equals(shipPartial))
                {
                    order.backorder_rule=OrderXMLDoc.kPartialShip
                }   else
                {
                    order.backorder_rule=OrderXMLDoc.kBackOrderAll
                }

                order.getBillingAddress().address_one= it.CUSTOMER.@address1.text()
                order.getBillingAddress().address_two=it.CUSTOMER.@address2.text()
                String roa = it.CUSTOMER.@address3.text()
               println roa
                order.getBillingAddress().city= roa.substring(0,roa.indexOf(","))
                roa = roa.substring(roa.indexOf(",")+1).trim()
                if(roa.contains(" "))
                {
                    order.getBillingAddress().state=roa.substring(0,roa.indexOf(" "))
                    roa = roa.substring(roa.indexOf(",")+1).trim()
                    order.getBillingAddress().country = roa
                }   else
                {
                    order.getBillingAddress().state=roa
                }
                order.getBillingAddress().zip=it.CUSTOMER.@zip.text()

                order.getShippingAddress().address_one= it.SHIPTO_CUSTOMER.@address1.text()
                              order.getShippingAddress().address_two=it.SHIPTO_CUSTOMER.@address2.text()
                               roa = it.SHIPTO_CUSTOMER.@address3.text()

                              order.getShippingAddress().city= roa.substring(0,roa.indexOf(","))
                              roa = roa.substring(roa.indexOf(",")+1).trim()
                              if(roa.contains(" "))
                              {
                                  order.getShippingAddress().state=roa.substring(0,roa.indexOf(" "))
                                  roa = roa.substring(roa.indexOf(",")+1).trim()
                                  order.getShippingAddress().country = roa
                              }   else
                              {
                                  order.getShippingAddress().state=roa
                              }
                              order.getShippingAddress().zip=it.SHIPTO_CUSTOMER.@zip.text()

                order.getShippingContact().name = it.SHIPTO_CUSTOMER.@first_name.text()+" "+it.SHIPTO_CUSTOMER.@last_name.text()
                order.getBillingContact().name = it.CUSTOMER.@first_name.text()+" "+it.CUSTOMER.@last_name.text()
                order.getShippingContact().phone = it.SHIPTO_CUSTOMER.@phone1.text()
                order.getBillingContact().phone = it.CUSTOMER.@phone1.text()
                order.getBillingAddress().company_name = it.CUSTOMER.@company_name.text()
                order.getShippingAddress().company_name = it.SHIPTO_CUSTOMER.@company_name.text()



                order.order_refnum = "RSO_" + soId
                order.order_type = "RetailPro"
                order.po_num = ""

                it.SO_ITEMS.SO_ITEM.each { item ->
                    //  println item.@qty
                    //  println item.@ECommId

                    String currentSku = item.@ECommID.text().trim()

                        if (LineItem.SKUExists("482", currentSku + "/KIT")) {
                            currentSku = currentSku + "/KIT";
                        }

                          if (!(LineItem.SKUExists("482", currentSku))) {
                        throw new Exception("Unable to import store order " + soId + " due to invalid SKU value " + item.@ECommID.text() )
                    }
                       if(Integer.parseInt(item.@ord_qty.text())>0)
                       {
                        order.addLineItem(currentSku, item.@ord_qty.text(), "0.00", "0.00", "", "", "")
                        // owdOrder.addLineItem("Brazil 90C-6", itemx.Quantity.text(), "0.00", "0.00", "Special Offer", "", "");
                       }

                    }
                    order.is_paid = 1;
                    order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "")
                    order.saveNewOrder(OrderUtilities.kHoldForPayment, true)
                    if (order.last_error.toUpperCase().contains("ORDER REFERENCE ALREADY EXISTS")) {
                        TransferUtilities.moveRetailProFileToDone(filename)
                    }
                    println "id:" + it.@so_no


                }catch(Exception exso)
                {
                    exso.printStackTrace();
                    try{   Mailer.sendMail("Babeland Retail Pro Hourly SO Import Error", ex.getMessage()+"\r\n"+ OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "donotreply@owd.com");    }catch(Exception exx)
                    {}
                }

            }
            }

        }

    }
