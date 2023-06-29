package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


public class OrderReleaseRequest implements APIRequestHandler
{
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ORDER_RELEASE_REQUEST";


    //root node attributes

    public static final String kOrderRef = "clientOrderId";
    public static final String kPartialShip = "partialShipAllowed";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception
    {

        OrderStatusResponse response = new OrderStatusResponse(api_version);

        List orderIDList = ConnectionManager.getActiveOrderKeyForClientID(root.getAttribute(kOrderRef), client.client_id, "FALSE");

        log.debug("got first list size=" + orderIDList.size() + " for " + root.getAttribute(kOrderRef) + " and " + client.client_id);
        if (orderIDList.size() < 1)
        {

            String orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef), client.client_id);
            log.debug("got order ID " + orderID + " for " + root.getAttribute(kOrderRef) + " and " + client.client_id);

            if (orderID != null)
            {
                orderIDList.add(orderID);
            } else
            {
                throw new APIContentException("Order ID not recognized");
            }
        }
        log.debug("got orderlist size=" + orderIDList.size());
        if (orderIDList.size() > 1)
        {
            throw new APIContentException("Multiple orders returned by search; an unambiguous order reference must be used instead");
        }
        String orderID = (String) orderIDList.get(0);


        if (orderID == null)
        {
            orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef), client.client_id);
        }


        if (orderID == null)

            throw new APIContentException("Order ID not recognized");


        OrderStatus order = new OrderStatus(orderID);
        if ((order.is_void))
        {
            throw new APIContentException("This order has already been voided");
        } else
        {
            if (order.is_shipped)
            {
                throw new APIContentException("This order has already been shipped");
            } else
            {
                if (order.is_posted)
                {
                    throw new APIContentException("This order has already been released and begun fulfillment. Please contact OWD directly if you need to modify or cancel this order.");
                } else
                {

                    if (testing)
                    {
                        throw new APIContentException("Order Release API has no testing mode available");
                    } else
                    {
                        /*  //make map of requested shipping items and quantities
                                                Map<String, Integer> releaseMap = new TreeMap<String, Integer>();

                                                NodeList itemNodes = XPathAPI.selectNodeList(root, "./RELEASE_ITEM");
                                                if (itemNodes != null)
                                                {
                                                    if (itemNodes.getLength() > 0)
                                                    {
                                                        for (int i = 0; i < itemNodes.getLength(); i++)
                                                        {
                                                            Node item = itemNodes.item(i);
                                                            try
                                                            {
                                                                String sku = XPathAPI.eval(item, "@sku").toString().trim();
                                                                String qtyStr = XPathAPI.eval(item, "@quantity").toString().trim();

                                                                if (releaseMap.containsKey(sku))
                                                                {
                                                                    throw new APIContentException("Item sku " + sku + " appears more than once in REQUEST_ITEM values. Each sku must be unique in a single request.");
                                                                }
                                                                releaseMap.put(sku, Integer.parseInt(qtyStr));

                                                            } catch (Exception ex)
                                                            {
                                                                throw new APIContentException("Unable to parse item line " + (i + 1) + " successfully = please check your values");
                                                            }
                                                        }
                                                    }
                                                }

                                               for (int i = 0; i < order.items.size(); i++)
                                                {
                                                    //update line item quantities
                                                    LineItem item = (LineItem) order.items.elementAt(i);
                                                    Integer releaseQuantity = releaseMap.get(item.client_part_no);
                                                    if(releaseQuantity==null)
                                                    {
                                                        releaseQuantity = 0;
                                                    }

                                                    if (item.parent_line == null && releaseMap.containsKey(item.client_part_no) )
                                                    {
                                                        item.force_backorder_quantity = true;

                                                        item.quantity_backordered = item.quantity_request;
                                                        item.quantity_request = 0;


                                                    }

                                                }
                        */

                        Map skuMap = OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), order.items, OrderXMLDoc.kPartialShip, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId() );

                        boolean hasBackorder = false;

                        for (int i = 0; i < order.items.size(); i++)
                        {
                            if (((LineItem) order.items.get(i)).quantity_backordered > 0)
                            {
                                hasBackorder = true;
                            }
                        }

                        if ("BACKORDER".equals(root.getAttribute(kPartialShip)))
                        {
                            if (hasBackorder)
                            {
                                Connection cxn =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

                                String esql = "update owd_order set  is_future_ship = 0, is_backorder=1, backorder_order_num = \"\" where order_id = " + order.order_id;
                                Statement stmt = cxn.createStatement();

                                int rowsUpdated = stmt.executeUpdate(esql);

                                if (rowsUpdated < 1)
                                    throw new Exception("Order not updated; could not release hold");

                                HibUtils.commit(HibernateSession.currentSession());
                                Event.addOrderEvent(new Integer(order.order_id).intValue(), Event.kEventTypeHandling, "Order hold removed by order release API", "API");

                            } else
                            {
                                String backorderRef = OrderUtilities.shipExistingOrder(order);

                                HibUtils.commit(HibernateSession.currentSession());

                                Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, "Existing order released for shipping by XML API", "API");


                            }
                        } else if ("TRUE".equals(root.getAttribute(kPartialShip)))
                        {
                            String backorderRef = OrderUtilities.shipExistingOrder(order);

                            HibUtils.commit(HibernateSession.currentSession());

                            Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, "Existing order partially released for shipping by XML API; backorder " + backorderRef + " created", "API");

                        } else
                        {
                            if (hasBackorder)
                            {
                                for (int i = 0; i < order.items.size(); i++)
                                {
                                    if (((LineItem) order.items.get(i)).quantity_backordered > 0)
                                    {
                                        throw new APIContentException("Request cancelled because of insufficient inventory for item " + ((LineItem) order.items.get(i)).client_part_no + ". Resubmit request with partialShipAllowed attribute set to \"TRUE\" to release order with available items.");
                                    }
                                }
                            } else
                            {
                                //assume default "FALSE"
                                String backorderRef = OrderUtilities.shipExistingOrder(order);

                                HibUtils.commit(HibernateSession.currentSession());

                                Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, "Existing order released for shipping by XML API", "API");
                            }


                        }
                        order = new OrderStatus(orderID);
                    }
                }
            }
        }


        response.buildFromOrderStatus(order);


        return response;

    }

}
