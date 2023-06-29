package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Address
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
class TransferOrder {

    static Map<String, Address> storeAddresses = new TreeMap<String, Address>()
    static {
        storeAddresses.put("SEA", new Address("Babeland Seattle", "707 E. Pike St", "", "Seattle", "WA", "98122", "USA"))
        storeAddresses.put("RIV", new Address("Babeland Rivington", "94 Rivington St", "", "New York", "NY", "10002", "USA"))
        storeAddresses.put("MER", new Address("Babeland SoHo", "43 Mercer St", "", "New York", "NY", "10013", "USA"))
        storeAddresses.put("BKN", new Address("Babeland Brooklyn", "462 Bergen St", "", "Brooklyn", "NY", "11217", "USA"))


    }


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
        processTransferOrders()
    }


    public static void processTransferOrders() {




        Map<String, byte[]> toMap = TransferUtilities.getRetailProFiles("TO_")

        for (String filename: toMap.keySet()) {

            println "Processingfile "+filename
            def transferOrder = new XmlSlurper().parseText(new String(toMap.get(filename)))

            // def xmlString = new StreamingMarkupBuilder().bindNode(item).toString()
               println  renderFormattedXml(new String(toMap.get(filename)))
            transferOrder.TORD.each { it ->


                Order order = new Order("482");

                String toId = it.@to_no
                String toStore = it.TORD_ITEMS.TORD_ITEM[0].TORD_QTYS.TORD_QTY[0].@to_store.text().toUpperCase()
                println "ID:" + toId
                println "Store:" + toStore
                if(storeAddresses.get(toStore)==null)
                {

                }   else
                {
                order.setBillingAddress(storeAddresses.get(toStore))
                order.getShippingInfo().shipAddress = (storeAddresses.get(toStore))
                order.getShippingContact().name = order.getShippingAddress().company_name
                order.getBillingContact().name = order.getBillingAddress().company_name
                order.getBillingAddress().company_name = "STORE TRANSFER"
                order.getShippingAddress().company_name = "STORE TRANSFER"
                order.order_refnum = "RTO_" + toId
                order.order_type = "RetailPro"
                order.po_num = toStore




                it.TORD_ITEMS.TORD_ITEM.each { item ->


                    String currentSku = item.@ECommId.text().trim()

                       if (LineItem.SKUExists("482", currentSku + "/KIT")) {
                            currentSku = currentSku + "/KIT";
                        }

                          if (!(LineItem.SKUExists("482", currentSku))) {
                        throw new Exception("Unable to import transfer order " + soId + " due to invalid SKU value " + item.@ECommID.text() )
                    }

                       if(Integer.parseInt(item.@qty.text())>0)
                       {
                        order.addLineItem(currentSku, item.@qty.text(), "0.00", "0.00", "", "", "")
                        // owdOrder.addLineItem("Brazil 90C-6", itemx.Quantity.text(), "0.00", "0.00", "Special Offer", "", "");
                       }

                    }
                    order.is_paid = 1;
                    order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "")
                    order.backorder_rule = OrderXMLDoc.kPartialShip;
                    order.saveNewOrder(OrderUtilities.kHoldForPayment, true)
                    if (order.last_error.toUpperCase().contains("ORDER REFERENCE ALREADY EXISTS")) {
                        TransferUtilities.moveRetailProFileToDone(filename)
                    }
                    println "id:" + it.@to_no

            }
                }


            }

        }

    }
