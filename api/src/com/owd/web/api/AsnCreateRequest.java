package com.owd.web.api;


import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.TagUtilities;
import com.owd.core.business.*;
import com.owd.core.business.asn.*;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.hibernate.Criteria;
import org.apache.xpath.XPathAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class AsnCreateRequest implements APIRequestHandler

{
private final static Logger log =  LogManager.getLogger();

    //root node name

    public static final String kRootTag = "OWD_ASN_CREATE_REQUEST";

    //ro   public static final String status = "status";
    public static final String kCarrier = "carrier";
    public static final String kCartonCount = "cartons_expected";
    public static final String kPalletCount = "pallets_expected";
    public static final String kClientPo = "asn_po_number";
    public static final String kClientRef = "asn_reference";
    public static final String kNotes = "notes";
    public static final String kShipperName = "shipper";
    public static final String kIsAutorelease = "auto_release";
    public static final String kExpectDate = "expect_date";
    public static final String kGroupName = "group_name";

    //asn items (ASN_ITEM)
    public static final String kItemTag = "ASNITEM";
    public static final String kSKU = "sku";
    public static final String kQty = "count";
    public static final String kDescription = "description";
    public static final String kTitle = "title";
    public static final String kCreateIfNew = "create_if_new";


    //2.2
    public static final String kCustomValueNodeName = "CUSTOM_VALUE";
    public static final String kCustomValueName = "name";


    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");
    protected static List<String> carrierList = new ArrayList<String>();

    static {
        carrierList.add("Unknown");
        carrierList.add("UPS");
        carrierList.add("USPS");
        carrierList.add("LTL Truck");
        carrierList.add("FedEx");
    }

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {

        try {
            OwdClient owdClient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(client.client_id));


            AsnStatusResponse response = new AsnStatusResponse(api_version);

            Asn asn = ASNFactory.getNewAsn(new Integer(client.client_id).intValue());
            asn.setIsAutorelease(0);
            asn.setFacilityCode(owdClient.getDefaultFacilityCode());



            try {


                //add limits (AND logic for all)
                log.debug("got root:" + root.getNodeName());


                //got a valid order to update!
                NodeIterator nl = XPathAPI.selectNodeIterator(root, "*");

                Node n;
                while ((n = nl.nextNode()) != null) {

                    String value = n.getTextContent() == null ? "" : n.getTextContent();
                    value = value.trim();
                    log.debug(n.getNodeName() + ":" + value);

                    if (n.getNodeName().equalsIgnoreCase("CARRIER")) {

                        if(carrierList.contains(value))
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
                        //    cal.roll(Calendar.DATE, 1);

                            if (dt.after(cal.getTime())) {
                                asn.setExpectDate(dt);
                            } else {
                                throw new Exception("expected date value must be a valid date in YYYYMMDD format and be in the future");
                            }
                        } catch (Exception ex) {
                            throw new Exception("expected date value must be a valid date in YYYYMMDD format and be in the future");
                        }
                    } else if (n.getNodeName().equalsIgnoreCase("ASNITEMS")) {

                        NodeList asnitems = (NodeList) XPathAPI.selectNodeList(root, "./ASNITEMS/ASNITEM");


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
                            addItemToASN(itemQty, itemSKU, itemDesc, asn, skus);

                        }
                    } else if (n.getNodeName().equalsIgnoreCase(kCustomValueNodeName)) {

                       //handled later
                    }  else
                    {
                        throw new Exception("XML element name " + n.getNodeName() + " with value " + value + " not recognized. Please correct your XML document and resubmit.");
                    }


                }

                if (asn.getAsnItems().size() < 1) {
                    throw new Exception("At least one item must be added to an ASN before saving!");
                }
                asn.setCreatedBy("XML API");
                asn.setCreatedDate(Calendar.getInstance().getTime());

                if(!(FacilitiesManager.getActiveFacilityCodes().contains(asn.getFacilityCode())))
                {

                    if(FacilitiesManager.isClientMultiFacility(owdClient.getClientId()))
                    {
                      /*  if(api_version<2.0)
                        {
                        StringBuffer sb = new StringBuffer();
                        sb.append("select isnull(default_facility_code,'DC1') from owd_inventory where client_fkey="+owdClient.getClientId()+" and inventory_num in (");
                        int index = 0;
                        for (AsnItem ai:asn.getAsnItems())
                        {
                            sb.append((index>0?",":"")+"'"+ai.getInventoryNum()+"'");
                            index++;
                        }
                        sb.append(") group by isnull(default_facility_code,'DC1') order by count(*);");
                        log.debug(sb.toString());
                        List<String[]> data = HibernateSession.currentSession().createSQLQuery(sb.toString()).list();
                        String fcode="DC1";

                        if(data.size()>0)
                        {
                            log.debug(data);
                            log.debug(data.get(0));
                           asn.setFacilityCode(""+data.get(0));
                        }

                    }*/
                        if(!FacilitiesManager.getActiveFacilityCodes().contains(asn.getFacilityCode()))
                        {
                        throw new APIContentException("A FACILITY_CODE element is required and must have one of the following values: "+ StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
                    }
                    }
                }


                if(api_version>=2.1) {
                    log.debug("API:custom values (tags) node");
                    NodeList tagNodes = root.getElementsByTagName(kCustomValueNodeName);
                    if (tagNodes != null) {
                        if (tagNodes.getLength() > 0) {
                            for (int i = 0; i < tagNodes.getLength(); i++) {
                                String aname = ((Element) tagNodes.item(i)).getAttribute(kCustomValueName);
                                String avalue = ((Element) tagNodes.item(i)).getFirstChild().getNodeValue();
                                if(asn.getTags()==null)
                                {
                                    asn.setTags(new ArrayList<AsnTag>());
                                }
                                AsnTag asnTag = new AsnTag();
                                asnTag.setTagName(aname.toUpperCase());
                                asnTag.setTagValue(avalue);
                                asnTag.setAsn(asn);
                                TagUtilities.validateTag(asnTag);
                                for (AsnTag tag:asn.getTags())
                                {
                                    if(tag.getTagName().toUpperCase().equals(asnTag.getTagName().toUpperCase()))
                                    {
                                        throw new APIContentException("Duplicate Custom Value name provided - names must be unique : "+ asnTag.getTagName());

                                    }
                                }
                                asn.getTags().add(asnTag);
                              //  HibernateSession.currentSession().save(asnTag);

                            }
                        }
                    }
                }


                HibernateSession.currentSession().save(asn);

                for (AsnItem aitem : asn.getAsnItems()) {
                    HibernateSession.currentSession().save(aitem);
                }
                for (AsnTag aitem : asn.getTags()) {
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

                List<Asn> asnList = new ArrayList<Asn>();
                asnList.add(asn);
                response.setAsnList(asnList);

            } catch (Exception ex) {
                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());

                throw ex;

            }

            return response;
        } catch (Exception ex) {
            //ex.printStackTrace();
            throw ex;

        }
    }


    public static void addItemToASN(int quantity_expected, String addSKU, String desc, Asn asn, Map<String,OwdInventory> skus) throws Exception {
        try {


            OwdInventory inventoryItem = null;
            log.debug("testing sku "+addSKU);
            if(skus.containsKey(addSKU.toUpperCase()))
            {
               inventoryItem = skus.get(addSKU.toUpperCase());
            }else
            {
                throw new Exception("SKU " + addSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");

            }

            if(inventoryItem == null)
            {
                throw new Exception("SKU " + addSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");
            }
            log.debug(inventoryItem);
            log.debug(inventoryItem.getInventoryNum());

            if (quantity_expected < 1) throw new Exception("SKU quantity must be a whole number greater than zero");

            boolean foundItem = false;
            if (addSKU.length() < 1) throw new Exception("You must provide a valid SKU to add an item.");


            AsnItem item = new AsnItem();
            item.setReceived(0);
            item.setIsBlind(0);
            item.setExpected(0);
            Iterator it = asn.getAsnItems().iterator();
            log.debug("adding sku "+addSKU+" to asnitemlist size "+asn.getAsnItems().size());
            while (it.hasNext() && !foundItem) {
                AsnItem itx = (AsnItem) it.next();

                if ((itx).getInventoryNum().equals(addSKU)) {
                    foundItem = true;
                    item = itx;
                    item.setExpected(quantity_expected + item.getExpected());
                }

            }

            if (!foundItem) {
                item.setAsn(asn);
                item.setInventoryNum(inventoryItem.getInventoryNum());
                item.setExpected(quantity_expected);

                item.setDescription(desc);
                item.setNote("");


                item.setTitle(inventoryItem.getDescription() == null ? "" : inventoryItem.getDescription());
                item.setInventoryFkey(inventoryItem.getInventoryId().intValue());
                asn.getAsnItems().add(item);
            }
        } catch (Exception ex) {

            throw ex;
        } finally {
            //  HibernateSession.closeSession();
        }
    }


}
