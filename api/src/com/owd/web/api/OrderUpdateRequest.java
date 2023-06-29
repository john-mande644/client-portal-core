package com.owd.web.api;

import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderUpdateRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ORDER_UPDATE_REQUEST";
    public static final String kOrderRef = "clientOrderId";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {

            log.debug("handling order update");
            List<String> orderList = new ArrayList<String>();
            OrderStatusResponse response = new OrderStatusResponse(api_version);


            List orderIDList = ConnectionManager.getOrderKeyForClientID(root.getAttribute(kOrderRef), client.client_id, "FALSE");

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


            if (orderID == null) throw new APIContentException("Order ID not recognized");

            OwdOrder order = null;

            try {
                order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(orderID));
                if(order == null) { throw new Exception("Order ID "+orderID+" not found");}
                if (order.getClientFkey() != Integer.parseInt(client.client_id)) {
                    throw new Exception("No order record for id " + orderID + " found");
                }
                if (order.getOrderStatus().equalsIgnoreCase("VOID")) {

                   throw new Exception("Cannot update voided order");
            }    if (order.getOrderStatus().equalsIgnoreCase("SHIPPED") || (order.getIsShipping()==null || order.getIsShipping()==1)) {

                    throw new Exception("Cannot update shipped order");
                }
                if (order.getOrderStatus().equalsIgnoreCase("UNKNOWN")) {

                    throw new Exception("Cannot update order - a separate process is updating the order. Please try again shortly.");
                }
                if (order.getOrderStatus().equalsIgnoreCase("AT WAREHOUSE")) {

                    throw new Exception("Cannot update order - order has been released to ship. Place the order on hold before attempting to edit it.");
                }

            } catch (NumberFormatException ex) {
                throw new APIContentException("Order reference not recognized");
            } catch (Exception ex) {
                throw new APIContentException(ex.getMessage());
            }

            NodeIterator nl = XPathAPI.selectNodeIterator(root, "*");

            Node n;
            String notes = null;

            while ((n = nl.nextNode()) != null) {

                String value = n.getTextContent() == null ? "" : n.getTextContent();
                value = value.trim();
                log.debug(n.getNodeName() + ":" + value);

                if (n.getNodeName().equalsIgnoreCase("GROUP_NAME")) {      try{               order.setGroupName(value);     }catch(Exception ex){throw new Exception("Error setting GROUP_NAME:"+ex.getMessage());}            } else

                if (n.getNodeName().equalsIgnoreCase("BILL_NAME")) {      try{
                    order.setBillFirstName(OWDUtilities.getFirstNameFromWholeName(value));
                    order.setBillLastName(OWDUtilities.getLastNameFromWholeName(value));
                }catch(Exception ex){throw new Exception("");}            } else

                if (n.getNodeName().equalsIgnoreCase("BILL_COMPANY")) {      try{               order.setBillCompanyName(value);     }catch(Exception ex){throw new Exception("Error setting BILL_COMPANY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_ADDRESS_ONE")) {      try{               order.setBillAddressOne(value);     }catch(Exception ex){throw new Exception("Error setting BILL_ADDRESS_ONE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_ADDRESS_TWO")) {      try{               order.setBillAddressTwo(value);     }catch(Exception ex){throw new Exception("Error setting BILL_ADDRESS_TWO:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_CITY")) {      try{               order.setBillCity(value);     }catch(Exception ex){throw new Exception("Error setting BILL_CITY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_STATE")) {      try{               order.setBillState(value);     }catch(Exception ex){throw new Exception("Error setting BILL_STATE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_POSTCODE")) {      try{               order.setBillZip(value);     }catch(Exception ex){throw new Exception("Error setting BILL_POSTCODE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_COUNTRY")) {      try{               order.setBillCountry(value);
                    log.debug("initial country=" +  order.getBillCountry());
                    if ((order.getBillCountry() == null) || !(CountryNames.exists(order.getBillCountry())))
                        throw new APIContentException("Billing address country name missing or not valid");
                    else
                        order.setBillCountry(CountryNames.getCountryName(order.getBillCountry()));


                    if (order.getBillState().equalsIgnoreCase("VI") && (order.getBillCountry().equals("US")||order.getBillCountry().equals("USA")||order.getBillCountry().equalsIgnoreCase("UNITED STATES"))) {
                        log.debug("SWITCH to VI country");
                        order.setBillCountry(CountryNames.getCountryName("VI"));
                    }
                    if ((order.getBillState().equalsIgnoreCase("PR") || order.getBillState().equalsIgnoreCase("PUERTO RICO"))  &&
                            (order.getBillCountry().equals("US")||order.getBillCountry().equals("USA")||order.getBillCountry().equalsIgnoreCase("UNITED STATES"))) {
                        order.setBillCountry(CountryNames.getCountryName("PR"));
                    }
                    if ("China".equalsIgnoreCase(order.getBillCountry())) {
                        order.setBillCountry(CountryNames.getCountryName("CN"));
                    }

                }catch(Exception ex){throw new Exception("Error setting BILL_COUNTRY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("FACILITY_RULE")) {      try{

                    order.setFacilityPolicy(value);

                    if("CLOSEST".equals(value) || "SPLIT".equals(value))
                    {
                        throw new APIContentException("Error setting FACILITY_RULE, "+value+" is not active for your account; contact OWD for assistance");
                    }  else
                    {
                        order.setFacilityCode(value);
                    }

                    if(!FacilitiesManager.getActiveFacilityCodes().contains(value))
                    {
                        throw new APIContentException("Error setting FACILITY_RULE, "+value+" is not a valid location for your account");

                    }


                }catch(Exception ex){throw new Exception("Error setting BILL_PHONE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_PHONE")) {      try{               order.setBillPhoneNum(value);     }catch(Exception ex){throw new Exception("Error setting BILL_PHONE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_EMAIL")) {      try{               order.setBillEmailAddress(value);     }catch(Exception ex){throw new Exception("Error setting BILL_EMAIL:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("BILL_PO")) {      try{               order.setPoNum(value);     }catch(Exception ex){throw new Exception("Error setting BILL_PO:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_NAME")) {      try{
                    order.getShipinfo().setShipFirstName(OWDUtilities.getFirstNameFromWholeName(value));
                    order.getShipinfo().setShipLastName(OWDUtilities.getLastNameFromWholeName(value));
                }catch(Exception ex){throw new Exception("Error setting SHIP_NAME:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_COMPANY")) {      try{               order.getShipinfo().setShipCompanyName(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_COMPANY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_ADDRESS_ONE")) {      try{               order.getShipinfo().setShipAddressOne(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_ADDRESS_ONE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_ADDRESS_TWO")) {      try{               order.getShipinfo().setShipAddressTwo(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_ADDRESS_TWO:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_CITY")) {      try{               order.getShipinfo().setShipCity(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_CITY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_STATE")) {      try{               order.getShipinfo().setShipState(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_STATE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_POSTCODE")) {      try{               order.getShipinfo().setShipZip(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_POSTCODE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_COUNTRY")) {      try{               order.getShipinfo().setShipCountry(value);
                    log.debug("initial country=" +  order.getShipinfo().getShipCountry());
                    if ((order.getShipinfo().getShipCountry() == null) || !(CountryNames.exists(order.getShipinfo().getShipCountry())))
                        throw new APIContentException("Shipping address country name missing or not valid");
                    else
                        order.getShipinfo().setShipCountry(CountryNames.getCountryName(order.getShipinfo().getShipCountry()));


                    if (order.getShipinfo().getShipState().equalsIgnoreCase("VI") && (order.getShipinfo().getShipCountry().equals("US")||order.getShipinfo().getShipCountry().equals("USA")||order.getShipinfo().getShipCountry().equalsIgnoreCase("UNITED STATES"))) {
                        log.debug("SWITCH to VI country");
                        order.getShipinfo().setShipCountry(CountryNames.getCountryName("VI")); 
                    }
                    if ((order.getShipinfo().getShipState().equalsIgnoreCase("PR") || order.getShipinfo().getShipState().equalsIgnoreCase("PUERTO RICO"))  &&
                    (order.getShipinfo().getShipCountry().equals("US")||order.getShipinfo().getShipCountry().equals("USA")||order.getShipinfo().getShipCountry().equalsIgnoreCase("UNITED STATES"))) {
                        order.getShipinfo().setShipCountry(CountryNames.getCountryName("PR"));
                    }
                    if ("China".equalsIgnoreCase(order.getShipinfo().getShipCountry())) {
                        order.getShipinfo().setShipCountry(CountryNames.getCountryName("CN"));
                    }

                    order.getShipinfo().setShipCountryRefNum(OrderUtilities.getCountryList().getRefForValue(order.getShipinfo().getShipCountry()));
                    
                }catch(Exception ex){throw new Exception("Error setting SHIP_COUNTRY:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_PHONE")) {      try{               order.getShipinfo().setShipPhoneNum(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_PHONE:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIP_EMAIL")) {      try{               order.getShipinfo().setShipEmailAddress(value);     }catch(Exception ex){throw new Exception("Error setting SHIP_EMAIL:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("TOTAL_SHIPHANDLING")) {      try{               order.setShipHandlingFee(new BigDecimal(OWDUtilities.floatFromString(value)));     }catch(Exception ex){throw new Exception("Error setting TOTAL_SHIPHANDLING:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("TOTAL_TAX")) {      try{               order.setTaxAmount(new BigDecimal(OWDUtilities.floatFromString(value)));     }catch(Exception ex){throw new Exception("Error setting TOTAL_TAX:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("TOTAL_DISCOUNT")) {      try{               order.setDiscount(new BigDecimal(Math.abs(OWDUtilities.floatFromString(value)) * -1.00d));     }catch(Exception ex){throw new Exception("Error setting TOTAL_DISCOUNT:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("ORDER_COMMENT")) {      try{               order.getShipinfo().setComments(value);     }catch(Exception ex){throw new Exception("Error setting ORDER_COMMENT:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("ORDER_WAREHOUSENOTES")) {      try{               order.getShipinfo().setWhseNotes(value);     }catch(Exception ex){throw new Exception("Error setting ORDER_WAREHOUSENOTES:"+ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("SHIPPING_METHOD")) {      try{

                        log.debug("checking ship type " + value);

                        String shipmethod = OrderUtilities.getServiceList().getValueForRef(value);

                    if (shipmethod == null) {
                        throw new Exception("No valid ship method code was found. Value read was: " + value + ". See http://service.owd.com/webapps/clienttools/shipmethods.jsp for current valid code list.");

                    }
                        setShipOptions(order.getShipinfo(),shipmethod);
                        HibernateSession.currentSession().update(order.getShipinfo());



                }catch(Exception ex){ex.printStackTrace();throw new Exception(ex.getMessage());}            } else
                if (n.getNodeName().equalsIgnoreCase("ADD_NOTE")) {      try{       notes = value;     }catch(Exception ex){throw new Exception("Error setting ADD_NOTE value:"+ex.getMessage());}            } else

                if (n.getNodeName().equalsIgnoreCase("LINE_ITEMS")) {


                    NodeList lineitems = (NodeList) XPathAPI.selectNodeList(root, "./LINE_ITEMS/LINE_ITEM");


                    Collection<OwdLineItem> c = order.getLineitems();
                    Collection<OwdLineItem> d = new ArrayList<OwdLineItem>();
                    for (OwdLineItem aitem : c) {
                            d.add(aitem);
                    }
                     for (OwdLineItem aitem : d) {
                            log.debug("removing line item id "+aitem.getLineItemId());
                            order.getLineitems().remove(aitem);
                            HibernateSession.currentSession().delete(aitem);

                    }
                    HibernateSession.currentSession().flush();
                    log.debug("line item size after clearing:"+order.getLineitems().size());

                    for (int i = 0; i < lineitems.getLength(); i++) {

                        Element lineitem = (Element) lineitems.item(i);
                        String itemSKU = lineitem.getAttribute("part_reference").toString();
                        String itemDesc = lineitem.getAttribute("description").toString();
                        String itemQtyStr = lineitem.getAttribute("requested").toString();
                        String itemCostStr = lineitem.getAttribute("cost").toString();
                        String itemRefStr = lineitem.getAttribute("line_number").toString();

                         if(itemDesc==null){ itemDesc = "";}
                        if(itemRefStr==null){ itemRefStr = "";}
                        int itemQty = 0;
                        try {
                            itemQty = Integer.parseInt(itemQtyStr);
                        } catch (Exception qex) {
                            throw new Exception("requested attribute value must be an integer greater than or equal to zero");
                        }
                        float itemCost = 0;
                        try {
                            itemCost = Float.parseFloat(itemCostStr);
                        } catch (Exception qex) {
                            throw new Exception("cost attribute value must be a decimal value greater than or equal to zero");
                        }


                        addLineItemToExistingOwdOrder(order, itemSKU, itemDesc, itemQty, OWDUtilities.roundFloat(itemCost),itemRefStr);
                    }
                } else {
                    throw new Exception("XML element name " + n.getNodeName() + " with value " + value + " not recognized. Please correct your XML document and resubmit.");
                }


            }

            if (order.getLineitems().size() < 1) {
                    throw new Exception("At least one line item must be connected to an order when updating!");
                }
            HibernateSession.currentSession().update(order.getShipinfo());


            for (OwdLineItem aitem : order.getLineitems()) {
                HibernateSession.currentSession().saveOrUpdate(aitem);
            }

            HibernateSession.currentSession().flush();
            OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);


            log.debug("testing:" + testing);
            if (!testing) {
                HibernateSession.currentSession().update(order);

                HibUtils.commit(HibernateSession.currentSession());
                int oid = order.getOrderId();

                HibernateSession.currentSession().evict(order);
                order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, oid);
                if(notes!=null && notes.length()>0)
                {
                    Event.addOrderEvent(oid, Event.kEventTypeGeneral, notes.trim(), "XML API");
                }

            }


            response.buildFromOwdOrder(order);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        } finally {
            HibUtils.rollback(HibernateSession.currentSession());

        }

    }


    public void setShipOptions(OwdOrderShipInfo info, String carrier) throws Exception {

        if(carrier.indexOf("(Book)")>=0)
        {
            carrier = carrier.substring(0,carrier.indexOf("(Book)")).trim();
        }
        String carrServiceRef = OrderUtilities.getServiceList().getRefForValue(carrier);



        if (carrServiceRef == null)

            throw new Exception("shipping type code |" + carrier + "| not valid");



        if(OrderUtilities.getServiceList().getValueForRef(carrServiceRef) == null)
        {
            throw new Exception("shipping type code |" + carrier + "| not valid");
        }

        info.setCarrService(OrderUtilities.getServiceList().getValueForRef(carrServiceRef));//carrier;

        info.setCarrServiceRefNum(carrServiceRef);


    }


    public static void addLineItemToExistingOwdOrder(OwdOrder order, String inventoryNum, String description, int qtyAdd, float itemCost,String externalId) throws Exception {
        try {

            int tempIDIndex = 0;

            Vector<OwdLineItem> kitList = new Vector<OwdLineItem>();

            OwdLineItem item = new OwdLineItem();
            item.setInventoryNum(inventoryNum.trim());
            item.setOrder(order);
            item.setCreatedBy("XML API");
            item.setCustomsDesc("");
            item.setDescription(description);
            item.setIsInsert(0);
            item.setIsParent(0);
            item.setItemColor("");
            item.setItemSize("");
            item.setLineItemDisc(0.00);
            item.setLongDesc("");
            item.setParentKey(null);
            item.setPrice(new BigDecimal(OWDUtilities.roundFloat(itemCost)));
            item.setQuantityActual(0);
            item.setQuantityBack(0);
            item.setQuantityRequest(qtyAdd);
            item.setCustRefnum(externalId.trim());
            item.setDecItemValue(new BigDecimal(0.00));
            item.setTotalPrice(new BigDecimal(OWDUtilities.roundFloat(itemCost*qtyAdd)));

            verifyInventory(item, order.getClientFkey());

            kitList.add(item);
            order.getLineitems().add(item);



            //log.debug("checking for kit");
            Map reqItemMap = LineItem.getRequiredItemsForInventoryID(item.getOwdInventory().getInventoryId());
            log.debug("got kit items " + reqItemMap);
            int tempID = 0;
            if (reqItemMap.size() > 0) {

                log.debug("got kit list " + reqItemMap.size());
                //is a kit
                tempIDIndex++;
                tempID = tempIDIndex;
                item.setIsParent(1);

                Object[] keys = reqItemMap.keySet().toArray();
                log.debug("got keys " + keys);
                for (int mapIndex = 0; mapIndex < keys.length; mapIndex++) {
                    log.debug("map index " + mapIndex);
                    Inventory invRecord = Inventory.getInventoryForID("" + ((Integer) (keys[mapIndex])));

                    OwdLineItem kititem = new OwdLineItem();
                    kititem.setInventoryNum(invRecord.inventory_num.trim());
                    kititem.setOrder(order);
                    kititem.setCreatedBy("XML API");
                    kititem.setCustomsDesc("");
                    kititem.setDescription("");
                    kititem.setIsInsert(0);
                    kititem.setIsParent(0);
                    kititem.setItemColor("");
                    kititem.setItemSize("");
                    kititem.setLineItemDisc(0.00);
                    kititem.setLongDesc("");
                    kititem.setParentKey(null);
                    kititem.setPrice(new BigDecimal(0.00));
                    kititem.setQuantityActual(0);
                    kititem.setQuantityBack(0);
                    kititem.setQuantityRequest(
                            ((Integer) reqItemMap.get(((Integer) (keys[mapIndex])))).intValue() * item.getQuantityRequest());
                    kititem.setCustRefnum("");
                    kititem.setDecItemValue(new BigDecimal(0.00));
                    kititem.setTotalPrice(new BigDecimal(0.00));

                    verifyInventory(kititem, order.getClientFkey());

                    kititem.setParentKey(tempID);
                    kitList.add(kititem);
                    order.getLineitems().add(kititem);

                }
                log.debug("kit done");

            }


            Map<Integer, Integer> idMap = new TreeMap<Integer, Integer>();

            if (0 >= kitList.size()) {
                throw new Exception("No valid line items in order - order could not be saved!");
            }
            //add line items
            for (int i = 0; i < kitList.size(); i++) {

                OwdLineItem itemk = ((OwdLineItem) kitList.elementAt(i));
                //((LineItem)skuList.elementAt(i)).updateQuantities(avail,backorder_rule);
                itemk.setOrder(order);


                log.debug("saving line: " + itemk);
                 HibernateSession.currentSession().save(itemk);
                if (itemk.getIsParent() == 1) {
                    idMap.put(new Integer(tempID), new Integer(itemk.getLineItemId()));
                }

            }
            //log.debug("got id map after saving items:"+idMap);

            for (int i = 0; i < kitList.size(); i++) {

                OwdLineItem itemk = ((OwdLineItem) kitList.elementAt(i));
                //log.debug("checking item "+itemk.client_part_no+" for parent");
                if (itemk.getParentKey() != null)

                {
                    //log.debug("found parent for tempID="+itemk.parent_line);
                    itemk.setParentKey((Integer) idMap.get(itemk.getParentKey()));
                    HibernateSession.currentSession().save(itemk);
                }
                HibernateSession.currentSession().flush();


                //items.addAll(kitList);
            }
            HibernateSession.currentSession().flush();

            OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);

            HibernateSession.currentSession().update(order);
            HibernateSession.currentSession().flush();
        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            //Mailer.sendMail("Save order caught error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","do_not_reply@owd.com");
            ex.printStackTrace();

            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception exm) {

                exm.printStackTrace();
            }
            throw ex;
        } finally {

            // HibernateSession.closeSession();

        }
    }

    public static void verifyInventory(OwdLineItem line, int clientId) throws Exception {

        Criteria crit = HibernateSession
                .currentSession()
                .createCriteria(OwdInventory.class);

        crit.createAlias("owdInventoryOh", "onhand");
        //   crit.createAlias("requiredSkus", "component");

        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        crit.add(Restrictions.eq("owdClient.clientId", clientId));
        crit.add(Restrictions.eq("inventoryNum", line.getInventoryNum()));
        OwdInventory inventory = null;
        if(crit.list().size()==1)
        {
            inventory = (OwdInventory) crit.list().get(0);
        }   else
        {
            throw new Exception("SKU value ["+line.getInventoryNum()+"] not recognized");
        }

        if(inventory!=null)
        {
            line.setOwdInventory(inventory);
            if (line.getDescription() == null || ("".equals(line.getDescription())))
                line.setDescription(inventory.getDescription());
            if (line.getItemColor() == null || ("".equals(line.getItemColor())))
              line.setItemColor(inventory.getItemColor());
            if (line.getItemSize() == null || ("".equals(line.getItemSize())))
               line.setItemSize(inventory.getItemSize());

        }


    }


}
