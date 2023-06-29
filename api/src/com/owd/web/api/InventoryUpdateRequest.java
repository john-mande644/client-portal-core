package com.owd.web.api;

import com.owd.core.UpcBarcodeUtilities;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryAdditionalIds;
import com.owd.hibernate.generated.OwdInventoryRequiredSku;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryUpdateRequest implements APIRequestHandler {
    private final static Logger log = LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_INVENTORY_UPDATE_REQUEST";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {


            List<OwdInventory> itemList = new ArrayList<OwdInventory>();
            InventoryStatusResponse response = new InventoryStatusResponse(api_version);

            String sku = XPathAPI.eval(root, "@sku").toString();
            OwdInventory item = null;
            int update_dimensions = 0;
            Boolean update_volume = false;
            try {
                item = InventoryManager.getOwdInventoryForClientAndSku(client.client_id, sku);
                if (item == null) {
                    throw new Exception("No item found matching SKU : " + sku);
                }
            } catch (Exception ex) {
                throw new APIContentException(ex.getMessage());
            }

            if(item.getCubicFeet() == null){ item.setCubicFeet(0.00f);}
            if(item.getHeight() == null){ item.setHeight(0.00f);}
            if(item.getWidth() == null){ item.setWidth(0.00f);}
            if(item.getLength() == null){ item.setLength(0.00f);}
            if(item.getIsOwdDimensions() == null) {item.setIsOwdDimensions(false);}
            NodeIterator nl = XPathAPI.selectNodeIterator(root, "*");

            Node n;
            while ((n = nl.nextNode()) != null) {
                // Serialize the found nodes to System.out
                String value = n.getTextContent() == null ? "" : n.getTextContent();
                value = value.trim();
                log.debug(n.getNodeName() + ":" + value);

                if ("CUSTOMS_DESC".equals(n.getNodeName())) {
                    item.setCustomsDesc(value);
                }
                if ("CUSTOMS_VALUE".equals(n.getNodeName())) {
                    try {
                        item.setCustomsValue(new Float("0" + XPathAPI.eval(root, "./CUSTOMS_VALUE").toString()));
                    } catch (NumberFormatException nex) {
                        throw new APIContentException("CUSTOMS_VALUE must be a non-negative numeric value");
                    }
                }
                if ("TITLE".equals(n.getNodeName())) {
                    item.setDescription(value);
                }
                if ("SUPPLIER_SKU".equals(n.getNodeName())) {
                    item.setHarmCode(value);
                }
                if ("GROUPNAME".equals(n.getNodeName())) {
                    item.setGroupName(value);
                }
                if ("ACTIVE".equals(n.getNodeName())) {

                    if ("1".equals(value)) {
                        item.setIsActive(true);

                    } else if ("0".equals(value)) {
                        item.setIsActive(false);

                    } else {
                        throw new APIContentException("ACTIVE element value must be 1 or 0, or the element omitted");
                    }
                }
                if ("COMPONENTS".equals(n.getNodeName())) {
                    //check for outstanding line items
                    if (InventoryManager.getPendingLineItemCountForInventoryId(item.getInventoryId()) > 0) {
                        throw new APIProcessException("Kit components cannot be modified while unshipped, unvoided orders exist - wait until all current orders using this kit item are shipped or voided to modify the component list for this item");
                    }

                    if (item.getIsAutoInventory() == 1 && item.getRequiredSkus().size() > 0) {
                        Collection<OwdInventoryRequiredSku> c = item.getRequiredSkus();
                        Collection<OwdInventoryRequiredSku> d = new ArrayList<OwdInventoryRequiredSku>();
                        for (OwdInventoryRequiredSku rsku : c) {
                            d.add(rsku);
                        }
                        for (OwdInventoryRequiredSku rsku : d) {
                            log.debug("removing required sku id " + rsku.getId());
                            item.getRequiredSkus().remove(rsku);
                            HibernateSession.currentSession().delete(rsku);

                        }


                        NodeList kititems = (NodeList) XPathAPI.selectNodeList(root, "./COMPONENTS/COMPONENT");

                        for (int i = 0; i < kititems.getLength(); i++) {

                            Element kititem = (Element) kititems.item(i);
                            String ksku = XPathAPI.eval(kititem, "./COMPONENT_SKU").toString();
                            if (ksku.equals(item.getInventoryNum())) {
                                throw new APIContentException("Kit SKUs cannot have themselves as a component SKU");
                            }
                            OwdInventory kitItem = InventoryManager.getOwdInventoryForClientAndSku(client.client_id, ksku);
                            if (kitItem.getIsAutoInventory() == 1) {
                                throw new APIContentException("Component SKUs may not be virtual or kit items");
                            }
                            String kqty = XPathAPI.eval(kititem, "./COMPONENT_QTY").toString();
                            try {
                                Integer tint = new Integer(kqty);
                                if (tint < 1) {
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException num) {
                                throw new APIContentException("Component SKU " + ksku + " quantity must be positive whole number");
                            }
                            log.debug("KIT ITEM : " + ksku + "-" + kqty);
                            OwdInventoryRequiredSku rsku = new OwdInventoryRequiredSku();
                            rsku.setQty(new Integer(kqty));
                            rsku.setOwdInventoryParent(item);
                            item.getRequiredSkus().add(rsku);
                            rsku.setOwdInventory(kitItem);
                            HibernateSession.currentSession().save(rsku);
                        }
                    } else {
                        throw new APIContentException("COMPONENTS elements may only be provided for items of type KIT");
                    }


                }
                if ("COLOR".equals(n.getNodeName())) {
                    item.setItemColor(value);
                }
                if ("UNIT_COST".equals(n.getNodeName())) {
                    try {
                        item.setSuppCost(new BigDecimal(value));
                        if (item.getSuppCost().floatValue() < 0.00f) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException nex) {
                        throw new APIContentException("UNIT_COST must be a non-negative numeric value");
                    }
                }
                if ("SIZE".equals(n.getNodeName())) {
                    item.setItemSize(value);
                }
                if ("KEYWORD".equals(n.getNodeName())) {
                    item.setKeyword(value);
                }
                if ("DESCRIPTION".equals(n.getNodeName())) {
                    item.setLongDesc(value);
                }
                if ("SUPPLIER".equals(n.getNodeName())) {
                    item.setMfrPartNum(value);
                }
                if ("NOTES".equals(n.getNodeName())) {
                    item.setNotes(value);
                }
                if ("IMAGE_URL".equals(n.getNodeName())) {
                    if (value != null) {
                        item.setImageUrl(value);
                        if (item.getImageUrl().length() > 0 && !API.isValidURL(item.getImageUrl())) {
                            throw new Exception("IMAGE_URL element value is not a valid URL");
                        }
                    }
                }
                if ("THUMB_URL".equals(n.getNodeName())) {
                    if (value != null) {
                        item.setImageThumbUrl(value);

                        if (item.getImageThumbUrl().length() > 0 && !API.isValidURL(item.getImageThumbUrl())) {
                            throw new Exception("THUMB_URL element value is not a valid URL");
                        }
                    }

                }
                if ("WEB_URL".equals(n.getNodeName())) {
                    if (value != null) {
                        item.setCatalogUrl(value);
                        if (item.getCatalogUrl().length() > 0 && !API.isValidURL(item.getCatalogUrl())) {
                            throw new Exception("WEB_URL element value is not a valid URL");
                        }
                    }
                }


                if ("PACKING_INSTRUCTIONS".equals(n.getNodeName())) {
                    item.getPackingInstructions().setInstructions(value);
                }
                if ("UNIT_PRICE".equals(n.getNodeName())) {
                    try {
                        item.setPrice(new BigDecimal(value));

                        if (item.getPrice().floatValue() < 0.00f) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException nex) {
                        throw new APIContentException("UNIT_PRICE must be a non-negative numeric value");
                    }
                }
                if ("REORDER_ALERT_QTY".equals(n.getNodeName())) {
                    item.setQtyHighAlert(0);
                    try {
                        item.setQtyReorder(new Integer(value));
                        if (item.getQtyReorder() < 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException nex) {
                        throw new APIContentException("REORDER_ALERT_QTY must be a non-negative integer value");
                    }
                }


//                if ("WEIGHT".equals(n.getNodeName())) {
//                    try {
//                        item.setWeightLbs(new Float(value));
//                        if (item.getWeightLbs() < 0.00f) {
//                            throw new NumberFormatException();
//                        }
//                    } catch (NumberFormatException nex) {
//                        throw new APIContentException("WEIGHT must be a non-negative numeric value");
//                    }
//                }

//                if ("LENGTH".equals(n.getNodeName())) {
//                        update_dimensions++;
//                        try {
//                            item.setLength(new Float(value));
//                            if (item.getLength().compareTo(0.00f) < 0) {
//                                throw new NumberFormatException();
//                            }
//                        } catch (NumberFormatException nex) {
//                            throw new APIContentException("LENGTH must be a non-negative numeric value");
//                        }
//                }
//
//                if ("WIDTH".equals(n.getNodeName())) {
//                        update_dimensions++;
//                        try {
//                            item.setWidth(new Float(value));
//                            if (item.getWidth().compareTo(0.00f) < 0) {
//                                throw new NumberFormatException();
//                            }
//                        } catch (NumberFormatException nex) {
//                            throw new APIContentException("WIDTH must be a non-negative numeric value");
//                        }
//                }
//
//                if ("HEIGHT".equals(n.getNodeName())) {
//                        update_dimensions++;
//                        try {
//                            item.setHeight(new Float(value));
//                            if (item.getHeight().compareTo(0.00f) < 0){
//                                throw new NumberFormatException();
//                            }
//                        } catch (NumberFormatException nex) {
//                            throw new APIContentException("HEIGHT must be a non-negative numeric value");
//                        }
//                }
//
//                if ("CUBIC_FEET".equals(n.getNodeName())) {
//                        try {
//                            item.setCubicFeet(new Float(value));
//                            if (item.getCubicFeet().compareTo(0.00f) < 0) {
//                                throw new NumberFormatException();
//                            }
//                        } catch (NumberFormatException nex) {
//                            throw new APIContentException("CUBIC_FEET must be a non-negative numeric value");
//                        }
//                }

                if ("DEFAULT_FACILITY_CODE".equals(n.getNodeName())) {

                    String facilityProvided = value;


                    if (!(FacilitiesManager.getActiveFacilityCodes().contains(facilityProvided))) {
                        throw new APIContentException("DEFAULT_FACILITY_CODE element, if present, must have one of the following values: " + StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
                    }

                    item.setDefaultFacilityCode(facilityProvided);

                }

                if (item.getCaseQty() == null) {
                    item.setCaseQty(0);
                }
                if (item.getMasterCaseQty() == null) {
                    item.setMasterCaseQty(0);
                }
                if ("CASE_QTY".equals(n.getNodeName())) {
                    try {
                        item.setCaseQty(new Integer(value));
                        if (item.getCaseQty() == 1 || item.getCaseQty() < 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException nex) {
                        throw new APIContentException("CASE_QTY must be greater than 1 or must be zero");
                    }
                }

                if ("MASTER_CASE_QTY".equals(n.getNodeName())) {

                    try {
                        item.setMasterCaseQty(new Integer(value));
                        if (item.getMasterCaseQty() < 0 || item.getMasterCaseQty() == 1) {
                            throw new NumberFormatException();
                        }

                    } catch (NumberFormatException nex) {
                        throw new APIContentException("MASTER_CASE_QTY must be zero, or must be greater than 1, greater than the CASE_QTY value, and an even multiple of the CASE_QTY value");
                    }
                }

                if("RECORDED_PERCENT".equals(n.getNodeName())) {
                    try {
                        item.setReportedPercent(new Integer(value));
                        if (item.getReportedPercent() < 0 || item.getReportedPercent() > 100) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException nfe) {
                        throw new APIContentException("RECORDED_PERCENT needs to be a number from 0 to 100.");
                    }

                }




                /*
                    <inventory_barcodes>
                        <barcode>
                            <type>UPC</type>
                            <value>1234567890</value>
                        </barcode>
                        <barcode>
                            <type>DSKU</type>
                            <value>D12345680293</value>
                        </barcode>
                    </inventory_barocde>
                 */
                if ("INVENTORY_BARCODES".equals(n.getNodeName())) {
                    log.debug("found barcode");
                    try {
                        Node bc;
                        NodeIterator bc_iter = XPathAPI.selectNodeIterator(n, "./BARCODE");
                        log.debug("created iterator");
                        Criteria crit = HibernateSession.currentSession().createCriteria(OwdInventoryAdditionalIds.class);
                        //          crit.add(Restrictions.eq("name",bc_type));
                        crit.add(Restrictions.eq("clientFkey", Integer.parseInt(client.client_id)));
                        crit.add(Restrictions.eq("inventoryFkey", item.getInventoryId()));
                            /* barcode wuantity not yet enabled  if enabled before 7/17/2021 DNickels Owes EJackman a flat of beer after that date vice versa
                            crit.add(Restrictions.eq("quantity",bc_quantity));
                            */
                        List<OwdInventoryAdditionalIds> existing_records = crit.list();
                        while ((bc = bc_iter.nextNode()) != null) {
                            String bc_type = "", bc_value = "";
                            int bc_quantity = 1;
                            System.out.println("Has next");
                            NodeList children = bc.getChildNodes();
                            for (int x = 0; x < children.getLength(); x++) {
                                Node child = children.item(x);
                                if ("TYPE".equals(child.getNodeName())) {
                                    bc_type = child.getTextContent();
                                }
                                if ("VALUE".equals(child.getNodeName())) {
                                    bc_value = child.getTextContent();
                                }
                            /* barcode Quantity not yet enabled if enabled before 7/17/2021 DNickels Owes eJackman a flat of beer after that date vice versa
                            if("QUANTITY".equals(child.getNodeName())){
                                try {
                                    bc_quantity = Integer.parseInt(child.getTextContent());
                                }catch(Exception ex){
                                    throw new APIContentException("INVENTORY_BARCODE>BARCODE>QUANTITY nust be a number");
                                }
                            }
                            */
                            }


                            List<OwdInventoryAdditionalIds> updated_records;
                            if (!bc_type.equals("UPC") && !bc_type.equals("ISBN")) {

                                //get existing id or create new
                                OwdInventoryAdditionalIds id_record;
                                int found = -1;
                                for (int x = 0; x < existing_records.size();x++) {
                                    if (existing_records.get(x).getName().equals(bc_type)) {
                                        found = x;
                                        break;
                                    }
                                }

                                if (found < 0) {
                                    id_record = new OwdInventoryAdditionalIds();
                                    id_record.setQty(1);
                                    id_record.setCreatedDate(new Date());
                                    id_record.setClientFkey(Integer.parseInt(client.client_id));
                                    id_record.setName(bc_type);
                                    id_record.setInventoryFkey(item.getInventoryId());
                                    id_record.setIsActive(true);

                                }else{
                                    id_record = existing_records.remove(found);
                                }
                                id_record.setValue(bc_value);
                                HibernateSession.currentSession().saveOrUpdate(id_record);
                                HibUtils.commit(HibernateSession.currentSession());
                            } else if (bc_type.equals("UPC")) {
                                if (UpcBarcodeUtilities.barcodeCheck(bc_value).equals("UPC")) {
                                    item.setUpcCode(bc_value);
                                }
                            } else if (bc_type.equals("ISBN")) {
                                if (UpcBarcodeUtilities.barcodeCheck((bc_value)).equals("ISBN")) {
                                    item.setIsbnCode(bc_value);
                                }
                            }


                        }
                        if(existing_records!=null) {
                            for (OwdInventoryAdditionalIds rec : existing_records) {
                                HibernateSession.currentSession().delete(rec);
                                HibUtils.commit(HibernateSession.currentSession());
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new APIContentException("INVENTORY_BARCODES is an array of BARCODE objects which contain a TYPE and VALUE");
                    }
                }


                try {

                    if ((item.getMasterCaseQty() > 0 && (item.getMasterCaseQty() == item.getCaseQty()))) {
                        throw new NumberFormatException();
                    }

                    if (item.getCaseQty() > 0 && item.getMasterCaseQty() > 0) {
                        if (item.getMasterCaseQty() % item.getCaseQty() != 0) {
                            throw new NumberFormatException();
                        }
                    }
                } catch (NumberFormatException nex) {
                    throw new APIContentException("MASTER_CASE_QTY must be zero, or must be greater than 1, greater than the CASE_QTY value, and an even multiple of the CASE_QTY value");
                }


            }

            // Validate Length Width and Height are set
//            if(update_dimensions > 0 && update_dimensions < 3){
//                throw new APIContentException("HEIGHT WEIGHT or LENGTH not provided");
//            }
//
//            if(update_dimensions > 0) {
//                item.setCubicFeet((item.getHeight()/12) * (item.getWidth()/12) * (item.getLength()/12));
//                InventoryManager.checkAndSetBulkySkuForOwdInventory(item);
//            }

            item.setModifiedBy("XML API");
            item.setModifiedDate(Calendar.getInstance().getTime());
            HibernateSession.currentSession().save(item);
            HibernateSession.currentSession().save(item.getOwdInventoryOh());
            HibernateSession.currentSession().save(item.getPackingInstructions());


            HibernateSession.currentSession().flush();


            log.debug("testing:" + testing);
            if (!testing) {
                HibUtils.commit(HibernateSession.currentSession());
                int iid = item.getInventoryId();

                HibernateSession.currentSession().evict(item);
                item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, iid);
            }


            itemList.add(item);
            response.setInventoryList(itemList);

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
