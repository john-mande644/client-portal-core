package com.owd.web.api;

import com.owd.core.UpcBarcodeUtilities;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryAdditionalIds;
import com.owd.hibernate.generated.OwdInventoryRequiredSku;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/28/11
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryCreateRequest implements APIRequestHandler {
    private final static Logger log = LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_INVENTORY_CREATE_REQUEST";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {

            OwdClient owdClient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(client.client_id));
            InventoryStatusResponse response = new InventoryStatusResponse(api_version);

            String sku = XPathAPI.eval(root, "./SKU").toString();
            if (sku == null) {
                throw new APIContentException("Required SKU element not found");
            }
            sku = sku.trim();

            if (sku.length() < 1) {
                throw new APIContentException("Required SKU element empty - must have a minimum length of one character");
            }
            if ((ConnectionManager.InventoryExists(sku, client.client_id))) {
                //throw
                throw new APIContentException("SKU value " + sku + " already exists - use a different SKU value or use OWD_INVENTORY_UPDATE_REQUEST");

            } else {

                String type = XPathAPI.eval(root, "./TYPE").toString();
                if (!("VIRTUAL".equals(type) || "PHYSICAL".equals(type) || "SERIALIZED".equals(type) || "KIT".equals(type))) {
                    throw new APIContentException("TYPE value " + sku + " must be KIT, VIRTUAL, PHYSICAL or SERIALIZED");
                }
                OwdInventory item = InventoryManager.getInitializedOwdInventory(new Integer(client.client_id));

                item.setIsOwdDimensions(false);

                item.setCustomsDesc(XPathAPI.eval(root, "./CUSTOMS_DESC").toString());
                try {
                    item.setCustomsValue(new Float("0" + XPathAPI.eval(root, "./CUSTOMS_VALUE").toString()));
                } catch (NumberFormatException nex) {
                    throw new APIContentException("CUSTOMS_VALUE must be a non-negative numeric value");
                }
                item.setDescription(XPathAPI.eval(root, "./TITLE").toString());

                item.setHarmCode(XPathAPI.eval(root, "./SUPPLIER_SKU").toString());
                if (item.getHarmCode().length() > 50) {
                    throw new APIContentException("SUPPLIER_SKU must be 50 characters or less");
                }

                item.setInventoryNum(sku);

                if (api_version < 2.0) {
                    String facilityProvided = XPathAPI.eval(root, "./DEFAULT_FACILITY_CODE").toString();

                    if (facilityProvided.length() < 1) {
                        facilityProvided = owdClient.getDefaultFacilityCode();
                    }


                    if (!(FacilitiesManager.getActiveFacilityCodes().contains(facilityProvided))) {

                        if (!FacilitiesManager.isClientMultiFacility(owdClient.getClientId())) {
                            throw new APIContentException("DEFAULT_FACILITY_CODE element, if present, must have one of the following values: " + StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
                        }
                    }


                    item.setDefaultFacilityCode(facilityProvided);
                } else {
                    item.setDefaultFacilityCode("DC1");
                }
                String active = XPathAPI.eval(root, "./ACTIVE").toString();
                if ("1".equals(active)) {
                    item.setIsActive(true);

                } else if ("0".equals(active)) {
                    item.setIsActive(false);

                } else if (active.length() > 0) {
                    throw new APIContentException("ACTIVE element value must be 1 or 0, or the element omitted");
                } else {
                    item.setIsActive(true); //default if element missing
                }
                item.setIsAutoInventory((type.equals("VIRTUAL") || type.equals("KIT")) ? 1 : 0);

                item.setItemColor(XPathAPI.eval(root, "./COLOR").toString());
                if (item.getItemColor().length() > 50) {
                    throw new APIContentException("COLOR must be 50 characters or less");
                }
                try {
                    item.setSuppCost(new BigDecimal("0" + XPathAPI.eval(root, "./UNIT_COST").toString()));
                    if (item.getSuppCost().floatValue() < 0.00f) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nex) {
                    throw new APIContentException("UNIT_COST must be a non-negative numeric value");
                }
                item.setItemSize(XPathAPI.eval(root, "./SIZE").toString());
                item.setKeyword(XPathAPI.eval(root, "./KEYWORD").toString());
                item.setLongDesc(XPathAPI.eval(root, "./DESCRIPTION").toString());
                item.setMfrPartNum(XPathAPI.eval(root, "./SUPPLIER").toString());
                if (item.getMfrPartNum().length() > 50) {
                    throw new APIContentException("SUPPLIER must be 50 characters or less");
                }

                item.setGroupName(XPathAPI.eval(root, "./GROUPNAME").toString());
                item.setModifiedBy("XML API");
                item.setNotes(XPathAPI.eval(root, "./NOTES").toString());
                item.setImageUrl(XPathAPI.eval(root, "./IMAGE_URL").toString().trim());

                item.setImageThumbUrl(XPathAPI.eval(root, "./THUMB_URL").toString().trim());
                item.setCatalogUrl(XPathAPI.eval(root, "./WEB_URL").toString().trim());


                if (item.getCatalogUrl().length() > 0 && !API.isValidURL(item.getCatalogUrl())) {
                    throw new Exception("WEB_URL element value is not a valid URL");
                }
                if (item.getImageUrl().length() > 0 && !API.isValidURL(item.getImageUrl())) {
                    throw new Exception("IMAGE_URL element value is not a valid URL");
                }
                if (item.getImageThumbUrl().length() > 0 && !API.isValidURL(item.getImageThumbUrl())) {
                    throw new Exception("THUMB_URL element value is not a valid URL");
                }
                item.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(client.client_id)));
                item.getOwdInventoryOh().setQtyOnHand(type.equals("KIT") || type.equals("VIRTUAL") ? 99999 : 0);
                item.getPackingInstructions().setInstructions(XPathAPI.eval(root, "./PACKING_INSTRUCTIONS").toString());
                try {
                    item.setPrice(new BigDecimal("0" + XPathAPI.eval(root, "./UNIT_PRICE").toString()));

                    if (item.getPrice().floatValue() < 0.00f) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nex) {
                    throw new APIContentException("UNIT_PRICE must be a non-negative numeric value");
                }
                item.setQtyHighAlert(0);
                try {
                    item.setQtyReorder(new Integer("0" + XPathAPI.eval(root, "./REORDER_ALERT_QTY").toString()));
                    if (item.getQtyReorder() < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nex) {
                    throw new APIContentException("REORDER_ALERT_QTY must be a non-negative integer value");
                }

                try {
                    item.setCaseQty(new Integer("0" + XPathAPI.eval(root, "./CASE_QTY").toString()));
                    if (item.getCaseQty() == 1 || item.getCaseQty() < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nex) {
                    throw new APIContentException("CASE_QTY must be greater than 1 or must be zero");
                }

                try {
                    item.setMasterCaseQty(new Integer("0" + XPathAPI.eval(root, "./MASTER_CASE_QTY").toString()));
                    if (item.getMasterCaseQty() < 0 || item.getMasterCaseQty() == 1) {
                        throw new NumberFormatException();
                    }
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


                item.setRequireSerialNumbers(type.equals("SERIALIZED") ? 1 : 0);

//                try {
//                    item.setWeightLbs(new Float("0" + XPathAPI.eval(root, "./WEIGHT").toString()));
//                    if (item.getWeightLbs().compareTo(0.00f) < 0) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException nex) {
//                    throw new APIContentException("WEIGHT must be a non-negative numeric value");
//                }

//                try {
//                    item.setLength(new Float("0" + XPathAPI.eval(root, "./LENGTH").toString()));
//                    if (item.getLength().compareTo(0.00f) < 0) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException nex) {
//                    throw new APIContentException("LENGTH must be a non-negative numeric value");
//                }
//
//                try {
//                    item.setWidth(new Float("0" + XPathAPI.eval(root, "./WIDTH").toString()));
//                    if (item.getWidth().compareTo(0.00f) < 0) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException nex) {
//                    throw new APIContentException("WIDTH must be a non-negative numeric value");
//                }
//
//                try {
//                    item.setHeight(new Float("0" + XPathAPI.eval(root, "./HEIGHT").toString()));
//                    if (item.getHeight().compareTo(0.00f) < 0) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException nex) {
//                    throw new APIContentException("HEIGHT must be a non-negative numeric value");
//                }
//
//                try {
//                    item.setCubicFeet(new Float("0" + XPathAPI.eval(root, "./CUBIC_FEET").toString()));
//                    if (item.getCubicFeet().compareTo(0.00f) < 0) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException nex) {
//                    throw new APIContentException("CUBIC_FEET must be a non-negative numeric value");
//                }
//
//                if (item.getHeight().compareTo(0.00f) == 0 || item.getWidth().compareTo(0.00f) == 0 || item.getLength().compareTo(0.00f) == 0) {
//                    item.setHeight(0.00f);
//                    item.setWidth(0.00f);
//                    item.setLength(0.00f);
//                } else {
//                    item.setCubicFeet((item.getHeight() / 12) * (item.getLength() / 12) * (item.getHeight() / 12));
//                }


                List<OwdInventoryAdditionalIds> additionalIds = new ArrayList<>();
                try {
                    Date created = new Date();
                    Node inventory_barcodes = XPathAPI.selectSingleNode(root, "./INVENTORY_BARCODES");
                    System.out.println("inventory_barcodes");
                    System.out.println(inventory_barcodes);
                    if(inventory_barcodes != null && inventory_barcodes.hasChildNodes()) {
                        Node bc;
                        NodeIterator bc_iter = XPathAPI.selectNodeIterator(inventory_barcodes, "./BARCODE");
                        System.out.println(bc_iter);
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
                            if("".equals(bc_type.trim())) throw new Exception("Barcode TYPE was not set");
                            if("".equals(bc_value.trim())) throw new Exception("Barcode VALUE was not set");

                            if (!bc_type.equals("UPC") && !bc_type.equals("ISBN")) {
                                OwdInventoryAdditionalIds id_record = new OwdInventoryAdditionalIds();
                                id_record.setClientFkey(Integer.parseInt(client.client_id));
                                id_record.setName(bc_type);
                                id_record.setValue(bc_value);
                                id_record.setQty(1);
                                id_record.setIsActive(true);
                                id_record.setCreatedDate(created);
                                additionalIds.add(id_record);
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
                    }
                } catch (Exception ex) {
                    throw new APIContentException(ex.getMessage());
                }


                item.setBulkyPackFeeOverride(0.0f);
                item.setBulkyPickFeeOverride(0.0f);
                item.setIsBulkyPack(false);
                item.setIsBulkyPick(false);
                item.setBulkyPackOverride(false);
                item.setBulkyPickOverride(false);

                String recordedPercent = XPathAPI.eval(root, "./RECORDED_PERCENT").toString();
                if(recordedPercent.length()>0){
                    try{
                        item.setReportedPercent(new Integer(recordedPercent));
                        if(item.getReportedPercent()<0 || item.getReportedPercent()>100){
                            throw new NumberFormatException();
                        }
                    }catch (NumberFormatException nfe){
                        throw new APIContentException("RECORDED_PERCENT needs to be a number from 0 to 100.");
                    }


                }else{
                    item.setReportedPercent(100);
                }


                item.setCreatedBy("XML API");
                item.setCreatedDate(Calendar.getInstance().getTime());
                if (type.equals("KIT")) {

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
                        log.debug("check parent:" + rsku.getOwdInventoryParent() + " component:" + rsku.getOwdInventory());
                    }
                }
                HibernateSession.currentSession().save(item);
                HibernateSession.currentSession().save(item.getOwdInventoryOh());
                HibernateSession.currentSession().save(item.getPackingInstructions());
                for (OwdInventoryRequiredSku rsku : item.getRequiredSkus()) {
                    HibernateSession.currentSession().save(rsku);
                }

                HibernateSession.currentSession().flush();

                HibernateSession.currentSession().refresh(item);

                for (OwdInventoryAdditionalIds additionalId : additionalIds) {
                    additionalId.setInventoryFkey(item.getInventoryId());
                    HibernateSession.currentSession().save(additionalId);
                }


                log.debug("testing:" + testing);
                if (!testing) {
                    HibUtils.commit(HibernateSession.currentSession());
                    int iid = item.getInventoryId();

                    InventoryManager.checkAndSetBulkySkuForOwdInventory(item);

                    HibernateSession.currentSession().evict(item);
                    item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, iid);
                }

                List<OwdInventory> itemList = new ArrayList<OwdInventory>();
                itemList.add(item);
                response.setInventoryList(itemList);

                return response;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        } finally {
            HibUtils.rollback(HibernateSession.currentSession());

        }


    }

}
