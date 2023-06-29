package com.owd.web.api;

import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.AsnItem;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.Receive;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

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
public class AsnUpdateRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ASN_UPDATE_REQUEST";
    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {

            log.debug("handling asn update");
            List<Asn> asnList = new ArrayList<Asn>();
            AsnStatusResponse response = new AsnStatusResponse(api_version);

            String asnId = XPathAPI.eval(root, "@id").toString();
            Asn asn = null;

            try {
                asn = (Asn) HibernateSession.currentSession().load(Asn.class, Integer.parseInt(asnId));
                if(asn == null) { throw new Exception("Asn ID "+asnId+" not found");}
                if (asn.getClientFkey() != Integer.parseInt(client.client_id)) {
                    throw new Exception("No ASN record for id " + asnId + " found");
                }
                if (asn.getReceives().size() > 0) {
            for (Receive receive : asn.getReceives()) {
               if("PENDING".equalsIgnoreCase(receive.getCreatedBy()))
               {
                   throw new Exception("Cannot update ASN while a receive is in progress - updates will be available after the current receive is completed");
               }
            }
          }
            } catch (NumberFormatException ex) {
                throw new APIContentException("Asn id attribute must be a valid integer value");
            } catch (Exception ex) {
                throw new APIContentException(ex.getMessage());
            }

            NodeIterator nl = XPathAPI.selectNodeIterator(root, "*");

            Node n;

            while ((n = nl.nextNode()) != null) {

                String value = n.getTextContent() == null ? "" : n.getTextContent();
                value = value.trim();
                log.debug(n.getNodeName() + ":" + value);

                if (n.getNodeName().equalsIgnoreCase("CARRIER")) {

                        if(AsnCreateRequest.carrierList.contains(value))
                        {
                            asn.setCarrier(value);
                        }else
                        {
                            throw new APIContentException("CARRIER values must be one of: Unknown, UPS, USPS, FedEx or LTL Truck");
                        }
                } else if (n.getNodeName().equalsIgnoreCase("CARTONS")) {
                    try {
                        asn.setCartonCount(Integer.parseInt(value));
                        if (asn.getCartonCount() < 0) {
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        throw new Exception("carton count must be a positive integer value when provided");
                    }
                } else if (n.getNodeName().equalsIgnoreCase("PALLETS")) {
                    try {
                        asn.setPalletCount(Integer.parseInt(value));
                        if (asn.getPalletCount() < 0) {
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        throw new Exception("pallet count must be a positive integer value when provided");
                    }
                } else if (n.getNodeName().equalsIgnoreCase("PO_REFERENCE")) {
                    asn.setClientPo(value);
                } else if (n.getNodeName().equalsIgnoreCase("REFERENCE")) {
                    asn.setClientRef(value);
                } else if (n.getNodeName().equalsIgnoreCase("GROUPNAME")) {
                    asn.setGroupName(value);
                }  else if (n.getNodeName().equalsIgnoreCase("FACILITY_CODE")) {

                    if(!(FacilitiesManager.getActiveFacilityCodes().contains(value)))
                    {
                        throw new APIContentException("FACILITY_CODE element, if present, must have one of the following values: "+ StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
                    }

                    asn.setFacilityCode(value);
                }
                else if (n.getNodeName().equalsIgnoreCase("STATUS")) {
                    if("COMPLETE".equals(value))
                    {
                        asn.setStatus(1);
                    } else if ("UNRECEIVED".equalsIgnoreCase(value))
                    {
                        if(asn.getReceives().size()==0)
                        {
                            asn.setStatus(0);

                        }   else
                        {
                            throw new APIContentException("STATUS value of UNRECEIVED can only be accepted for asn records with no pending or completed receives");

                        }
                    } else
                    {
                        throw new APIContentException("STATUS value must be either COMPLETE or UNRECEIVED");
                    }
                } else if (n.getNodeName().equalsIgnoreCase("NOTES")) {
                    asn.setNotes(value);
                } else if (n.getNodeName().equalsIgnoreCase("SHIPPER")) {
                    asn.setShipperName(value);
                } else if (n.getNodeName().equalsIgnoreCase("AUTORELEASE")) {

                    if ("1".equals(value)) {
                        asn.setIsAutorelease(1);

                    } else if ("0".equals(value)) {
                        asn.setIsAutorelease(0);

                    } else if (value.length() > 0) {
                        throw new APIContentException("AUTORELEASE element value must be 1 or 0, or the element omitted (default is 0)");
                    } else {
                        asn.setIsAutorelease(0);
                    }

                } else if (n.getNodeName().equalsIgnoreCase("EXPECTED_DATE")) {
                    try {
                        Date dt = df.parse(value);

                        Calendar cal = Calendar.getInstance();
                      //  cal.roll(Calendar.DATE, 1);

                        if (dt.after(cal.getTime())) {
                            asn.setExpectDate(dt);
                        } else {
                            throw new Exception("expected date value must be a valid date in YYYYMMDD format and be in the future");
                        }
                    } catch (Exception ex) {
                        throw new Exception("expected date value must be a valid date in YYYYMMDD format and be in the future");
                    }
                } else if (n.getNodeName().equalsIgnoreCase("ASNITEMS")) {

                    if(asn.getReceives().size()>0)
                    {
                        throw new Exception("Cannot edit list of items in ASN after the first receive has begun - create a new ASN for any additional items");
                    }
                    NodeList asnitems = (NodeList) XPathAPI.selectNodeList(root, "./ASNITEMS/ASNITEM");


                    Collection<AsnItem> c = asn.getAsnItems();
                    Collection<AsnItem> d = new ArrayList<AsnItem>();
                    for (AsnItem aitem : c) {
                            d.add(aitem);
                    }
                     for (AsnItem aitem : d) {
                            log.debug("removing asn item id "+aitem.getId());
                            asn.getAsnItems().remove(aitem);
                            HibernateSession.currentSession().delete(aitem);

                    }
                    HibernateSession.currentSession().flush();
                    log.debug("asn item size after clearing:"+asn.getAsnItems().size());


                    Map<String,OwdInventory> skus = new HashMap<String, OwdInventory>();

                    for (int i = 0; i < asnitems.getLength(); i++) {

                        String sku = XPathAPI.eval((Element) asnitems.item(i), "./ASNITEM_SKU").toString().toUpperCase();

                        skus.put(sku,null);
                    }

                    Criteria crit = HibernateSession.currentSession().createCriteria(OwdInventory.class);

                    crit.setMaxResults(10000);
                    crit.add(Restrictions.eq("owdClient.clientId", asn.getClientFkey()))
                            .add(Restrictions.in("inventoryNum", skus.keySet()))
                            .setProjection(Projections.projectionList()
                                    .add(Projections.property("inventoryNum"), "inventoryNum")
                                    .add(Projections.property("inventoryId"), "inventoryId")
                                    .add(Projections.property("description"), "description"))
                            .setResultTransformer(Transformers.aliasToBean(OwdInventory.class));

                    List list = crit.list();

                    for(OwdInventory item:(List<OwdInventory>)list)
                    {
                        skus.put(item.getInventoryNum().toUpperCase(),item);
                    }


                    for (int i = 0; i < asnitems.getLength(); i++) {

                        Element asnitem = (Element) asnitems.item(i);
                        String itemSKU = XPathAPI.eval(asnitem, "./ASNITEM_SKU").toString();
                        String itemDesc = XPathAPI.eval(asnitem, "./ASNITEM_DESCRIPTION").toString();
                        String itemQtyStr = XPathAPI.eval(asnitem, "./ASNITEM_EXPECTED").toString();


                        int itemQty = 0;
                        try {
                            itemQty = Integer.parseInt(itemQtyStr);
                        } catch (Exception qex) {
                            throw new Exception("ASNITEM_EXPECTED elements must be an integer greater than zero");
                        }
                        AsnCreateRequest.addItemToASN(itemQty, itemSKU, itemDesc, asn,skus);

                    }
                } else {
                    throw new Exception("XML element name " + n.getNodeName() + " with value " + value + " not recognized. Please correct your XML document and resubmit.");
                }


            }

            if (asn.getAsnItems().size() < 1) {
                    throw new Exception("At least one item must be added to an ASN before updating!");
                }

            if(!(FacilitiesManager.getActiveFacilityCodes().contains(asn.getFacilityCode())))
            {
                throw new APIContentException("A FACILITY_CODE element must have one of the following values: "+ StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
            }

            HibernateSession.currentSession().save(asn);

            for (AsnItem aitem : asn.getAsnItems()) {
                HibernateSession.currentSession().save(aitem);
            }


            HibernateSession.currentSession().flush();


            log.debug("testing:" + testing);
            if (!testing) {
                HibUtils.commit(HibernateSession.currentSession());
                int aid = asn.getId();

                HibernateSession.currentSession().evict(asn);
                asn = (Asn) HibernateSession.currentSession().load(Asn.class, aid);
            }


            asnList.add(asn);
            response.setAsnList(asnList);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        } finally {
            HibUtils.rollback(HibernateSession.currentSession());

        }

    }

}
