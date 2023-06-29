package com.owd.web.api;

import com.owd.hibernate.generated.EdiDocs;
import com.owd.hibernate.generated.EdiSpsConfigdata;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.order.*;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.*;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.*;
import com.owd.web.api.client.APISubscriptionCreator;
import com.owd.web.api.client.SymphonyStuff;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public final class OrderCreateRequest implements APIRequestHandler {
    private final static Logger log = LogManager.getLogger();

    //root node name
    public static final String kRootTag = "OWD_ORDER_CREATE_REQUEST";

    //root node attributes
    public static final String kBackorderRule = "backorder_rule";
    public static final String kClientReference = "order_reference";
    public static final String kIsGift = "is_gift";
    public static final String kOrderSource = "order_source";
    public static final String kSalesperson = "salesperson";
    //1.1
    public static final String kIsToBeHeld = "hold_for_release";

    //1.92
    public static final String kAllowDuplicates = "allow_duplicates";

    //1.93
    public static final String kTemplate = "packslip_template";


    //2.0
    public static final String kEdiReference = "edi_reference";

    //2.1
    public static final String kItemPersonalizationJob = "personalization_job";

    public static final String kItemPersonalizationLocation = "personalization_location";

    public static final String kItemPersonalizationTemplate = "personalization_template";

    public static final String kItemPersonalizationJobTagName = "PERSONALIZATION_JOB";

    public static final String kItemPersonalizationLocationTagName = "PERSONALIZATION_LOCATION";

    public static final String kItemPersonalizationTemplateTagName = "PERSONALIZATION_TEMPLATE";

    //2.2
    public static final String kCustomValueNodeName = "CUSTOM_VALUE";
    public static final String kCustomValueName = "name";


    public static final String kGroupName = "group_name";


    //shipto node name
    public static final String kShipNodeName = "SHIPPING_INFO";

    //root node attributes
    public static final String kShipLastName = "last_name";
    public static final String kShipFirstName = "first_name";
    public static final String kShipCompanyName = "company_name";
    public static final String kShipAdd1 = "address_one";
    public static final String kShipAdd2 = "address_two";
    public static final String kShipCity = "city";
    public static final String kShipState = "state";
    public static final String kShipZip = "zip";
    public static final String kShipCountry = "country";
    public static final String kShipPhone = "phone";
    public static final String kShipEmail = "email";
    public static final String kShipAcct = "acct";
    public static final String kShipType = "ship_type";
    public static final String kShipCost = "ship_cost";
    //1.1
    public static final String kShipInsureAmount = "insure_amount";
    public static final String kShipDeclaredValue = "declared_value";
    public static final String kShipCustomsDesc = "customs_desc";

    //1.10
    public static final String kShipTerms = "terms";
    public static final String kCallTagDescription = "call_tag_desc";
    public static final String kCODAmount = "cod_amount";
    public static final String kSaturdayDelivery = "saturday_delivery";
    public static final String kProofOfDelivery = "proof_of_delivery";
    public static final String kAutoDeliveryNote = "autodeliverynote";


    public static final String kIntlShipperNodeName = "INTL";

    //third party billing node attributes
    //1.1
    public static final String kTaxName = "contact";
    public static final String kTaxCompany = "company";
    public static final String kTaxAdd1 = "address_one";
    public static final String kTaxAdd2 = "address_two";
    public static final String kTaxCity = "city";
    public static final String kTaxState = "state";
    public static final String kTaxZip = "zip";
    public static final String kTaxPhone = "phone";
    public static final String kTaxAccount = "acct";
    public static final String kImporterOfRecord = "billing_ref";

    //third party billing node name
    //1.1
    public static final String kThirdPartyShipperNodeName = "THIRD_PARTY_SHIPPER";

    //third party billing node attributes
    //1.1
    public static final String kThirdName = "contact";
    public static final String kThirdCompany = "company";
    public static final String kThirdAdd1 = "address_one";
    public static final String kThirdAdd2 = "address_two";
    public static final String kThirdCity = "city";
    public static final String kThirdState = "state";
    public static final String kThirdZip = "zip";
    public static final String kThirdPhone = "phone";
    public static final String kThirdAcct = "acct";
    public static final String kThirdBillingRef = "billing_ref";

    //best way shipping node name
    //1.1
    public static final String kBestWayNodeName = "BEST_WAY";

    //best way carrier node name
    //1.1
    public static final String kBestWayCarrierNodeName = "CARRIER";


    //billto node name
    public static final String kBillNodeName = "BILLING_INFO";

    //root node attributes
    public static final String kBillLastName = "last_name";
    public static final String kBillFirstName = "first_name";
    public static final String kBillCompanyName = "company_name";
    public static final String kBillAdd1 = "address_one";
    public static final String kBillAdd2 = "address_two";
    public static final String kBillCity = "city";
    public static final String kBillState = "state";
    public static final String kBillZip = "zip";
    public static final String kBillCountry = "country";
    public static final String kBillPhone = "phone";
    public static final String kBillEmail = "email";
    public static final String kBillTax = "tax";
    public static final String kBillTax2 = "tax_amount";
    public static final String kBillTaxPct = "tax_pct";
    public static final String kBillPO = "po_number";
    public static final String kBillPaid = "paid";
    public static final String kBillType = "payment_type";
    public static final String kBillAcct = "acct_no";
    public static final String kBillExpYear = "exp_year";
    public static final String kBillExpMonth = "exp_month";
    public static final String kBillDiscount = "order_discount";

    //1.10
    public static final String kPaidDate = "paid_date";
    public static final String kBillCCAuthAmount = "auth_amount";
    public static final String kBillCCAuthCode = "auth_code";
    public static final String kBillCCAuthTrans = "auth_trans";
    //  public static final String kBillCCAuthType = "auth_type";

    //line items node name
    public static final String kItemsNodeName = "LINE_ITEM";

    //line items node attributes
    public static final String kItemSKU = "part_reference";
    public static final String kItemDesc = "description";
    public static final String kItemCount = "requested";
    public static final String kItemBackorderCount = "backordered";
    public static final String kItemCost = "cost";
    //1.1
    public static final String kItemDeclaredValue = "declared_value";
    public static final String kItemCustomsDesc = "customs_desc";

    public static final String kItemExternalLineRef = "external_line_id";
    public static final String kItemDoNotShipAlone = "is_insert_item";

    //message node name
    public static final String kMessageNodeName = "MESSAGE";
    //comments node name
    public static final String kCommentNodeName = "COMMENTS";
    //warehouse notes node name
    public static final String kNotesNodeName = "WAREHOUSENOTES";

    static final public String kBackOrderAll = "BACKORDER";
    static final public String kPartialShip = "PARTIALSHIP";
    static final public String kHoldBackOrder = "HOLDORDER";
    static final public String kRejectBackOrder = "NOBACKORDER";
    static final public String kIgnoreBackOrder = "IGNOREBACKORDER";

    public static final String kFacilityRule = "facility_rule";

    public static final Object lockObject = new Object();

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        OrderStatusResponse response = new OrderStatusResponse(api_version);

        if ("345".equals(client.client_id)) {  //dogeared, because they use validating parser and won't update
            response.showOrderDetails = false;
        }

        // synchronized (lockObject) {
        {
            ////////log.debug("in OrderCreateRequest Handler");
            ////////log.debug(com.owd.api.client.client_id);


            List subscriptions = new ArrayList();
            List postHandlingPartMaps = new ArrayList();

            String payment_type = "";

            Order order = new Order(client.client_id);
            order.preserveShippingCosts = true;
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();


            order.order_type = root.getAttribute(kOrderSource);
            order.ship_call_date = OWDUtilities.getSQLDateForToday();
            order.backorder_rule = root.getAttribute(kBackorderRule);
            order.ship_operator = root.getAttribute(kSalesperson);


            //bounceback EDI orders for Symphony. See SPSCommerce code in jobs project
            String edi_reference = root.getAttribute(kEdiReference);
            if ("".equals(edi_reference)) {
                edi_reference = null;
            }


            if (edi_reference != null) {
                EdiDocs ediDoc = EDIUtilities.getEdiDocForReference(edi_reference);
                EdiSpsConfigdata ediConfig = EDIUtilities.getConfigForEdiDoc(ediDoc);

                order.addTag(TagUtilities.kEDIPurchaseOrder, ediDoc.getDocData());
                order.addTag(TagUtilities.kVendorComplianceReference, ediConfig.getName());
                try{
                    if(null!= ediConfig.getVendorComplianceFkey()){
                        order.addTag(TagUtilities.kVendorComplianceIDReference,ediConfig.getVendorComplianceFkey()+"");
                    }
                }catch(Exception e){

                }

                order.order_type = order.order_type + ":EDI:";


            }

            OwdClient owdClient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.parseInt(client.client_id));

            if (api_version >= 2.0) {
                order.setFacilityPolicy(root.getAttribute(kFacilityRule));
                order.setFacilityCode(order.getFacilityPolicy());
                //If not an active facility, it can also be CLOSEST for DOM
                if (!FacilitiesManager.getActiveFacilityCodes().contains(order.getFacilityCode())&&!order.getFacilityCode().equalsIgnoreCase("CLOSEST")) {
                    if (FacilitiesManager.isClientMultiFacility(owdClient.getClientId())) {
                        throw new APIContentException("A valid facility_rule must be supplied for multi-facility accounts when using API v.2.0 or higher");
                    }
                }
                order.setFacilityCode(order.getFacilityPolicy());
            } else {
                if (!FacilitiesManager.isClientMultiFacility(owdClient.getClientId())) {
                    order.setFacilityPolicy(owdClient.getDefaultFacilityCode());
                    order.setFacilityCode(owdClient.getDefaultFacilityCode());
                } else {
                    order.setFacilityPolicy("XXX");
                    order.setFacilityCode("XXX");
                }
            }


            order.group_name = root.getAttribute(kGroupName);
            if (order.group_name == null) order.group_name = "";


            String packTemplate = root.getAttribute(kTemplate);
            if (packTemplate == null) packTemplate = "";
            order.template = packTemplate;


            Address billAddress;
            Address shipAddress;

            if (!(order.backorder_rule.equals(kBackOrderAll) || order.backorder_rule.equals(kPartialShip) || order.backorder_rule.equals(kIgnoreBackOrder) || order.backorder_rule.equals(kHoldBackOrder) || order.backorder_rule.equals(kRejectBackOrder)))
                throw new APIContentException("Backorder rule was unspecified or invalid");

            order.order_refnum = root.getAttribute(kClientReference);

            String dupes = root.getAttribute(kAllowDuplicates);
            if (dupes == null) dupes = "";

            if (dupes.equalsIgnoreCase("TRUE")) {
                order.noduplicates = false;
            } else {
                if (order.orderRefnumExists(order.order_refnum)) {
                    throw new APIContentException("Order reference already exists; cannot accept duplicate com.owd.api.client order references - use OWD_ORDER_STATUS_REQUEST to retrieve existing order information");
                }
            }
            if (root.getAttribute(kIsGift).equals("YES"))
                order.is_gift = "1";

            List svcCodes = new ArrayList();
            log.debug("API:billing node");
            NodeList billNodes = root.getElementsByTagName(kBillNodeName);
            if (billNodes != null) {
                if (billNodes.getLength() > 0) {
                    billAddress = new Address(((Element) billNodes.item(0)).getAttribute(kBillCompanyName),
                            ((Element) billNodes.item(0)).getAttribute(kBillAdd1),
                            ((Element) billNodes.item(0)).getAttribute(kBillAdd2),
                            ((Element) billNodes.item(0)).getAttribute(kBillCity),
                            ((Element) billNodes.item(0)).getAttribute(kBillState),
                            ((Element) billNodes.item(0)).getAttribute(kBillZip),
                            ((Element) billNodes.item(0)).getAttribute(kBillCountry));


                    if ((billAddress.country == null) || !(CountryNames.exists(billAddress.country)))
                        throw new APIContentException("Addressee country name missing or not valid");
                    else
                        billAddress.country = CountryNames.getCountryName(billAddress.country);

                    /*
                    if(OrderUtilities.getCountryList().getRefForValue(billAddress.country) == null)
                    {
                        IANACountries iana = new IANACountries();

                        if(OrderUtilities.getCountryList().getRefForValue(iana.getCountryForCode(billAddress.country)) == null)
                            throw new APIContentException("Billing country name missing or invalid");
                        else
                            billAddress.country = iana.getCountryForCode(billAddress.country);
                    }
                    */

                    order.setBillingAddress(billAddress);


                    Contact billContact = new Contact(((Element) billNodes.item(0)).getAttribute(kBillFirstName) + " " + ((Element) billNodes.item(0)).getAttribute(kBillLastName),
                            ((Element) billNodes.item(0)).getAttribute(kBillPhone),
                            "",
                            ((Element) billNodes.item(0)).getAttribute(kBillEmail), "");
                    order.setBillingContact(billContact);


                    float tax = OWDUtilities.floatFromString(((Element) billNodes.item(0)).getAttribute(kBillTax2)) + OWDUtilities.floatFromString(((Element) billNodes.item(0)).getAttribute(kBillTax));
                    if (tax > 0.00) {

                        order.total_tax_cost = tax;

                    } else {
                        order.tax_pct = OWDUtilities.floatFromString(((Element) billNodes.item(0)).getAttribute(kBillTaxPct));
                    }
                    order.discount = Math.abs(OWDUtilities.floatFromString(((Element) billNodes.item(0)).getAttribute(kBillDiscount))) * -1;
                    order.po_num = ((Element) billNodes.item(0)).getAttribute(kBillPO);
                    payment_type = ((Element) billNodes.item(0)).getAttribute(kBillType);
                }
            }
            log.debug("API:shipping node");
            NodeList shipNodes = root.getElementsByTagName(kShipNodeName);
            if (shipNodes != null) {
                if (shipNodes.getLength() > 0) {
                    shipAddress = new Address(((Element) shipNodes.item(0)).getAttribute(kShipCompanyName),
                            ((Element) shipNodes.item(0)).getAttribute(kShipAdd1),
                            ((Element) shipNodes.item(0)).getAttribute(kShipAdd2),
                            ((Element) shipNodes.item(0)).getAttribute(kShipCity),
                            ((Element) shipNodes.item(0)).getAttribute(kShipState),
                            ((Element) shipNodes.item(0)).getAttribute(kShipZip),
                            ((Element) shipNodes.item(0)).getAttribute(kShipCountry));
                    /*
                    if(OrderUtilities.getCountryList().getRefForValue(shipAddress.country) == null)
                    {
                        IANACountries iana = new IANACountries();

                        if(OrderUtilities.getCountryList().getRefForValue(iana.getCountryForCode(shipAddress.country)) == null)
                            throw new APIContentException("Addressee country name missing or invalid");
                        else
                            shipAddress.country = iana.getCountryForCode(shipAddress.country);
                    }
                    */


                    log.debug("initial country=" + shipAddress.country);
                    if (shipAddress.state.equalsIgnoreCase("VI") && shipAddress.isUS()) {
                        log.debug("SWITCH to VI country");
                        shipAddress.country = "VI";
                    }
                    if ((shipAddress.state.equalsIgnoreCase("PR") || shipAddress.state.equalsIgnoreCase("PUERTO RICO"))
                            && shipAddress.isUS()) {
                        shipAddress.country = "PR";
                    }
                    if ("China".equalsIgnoreCase(shipAddress.country)) {
                        shipAddress.country = "CN";
                    }

                    if ((shipAddress.country == null) || !(CountryNames.exists(shipAddress.country)))
                        throw new APIContentException("Addressee country name missing or not valid");
                    else
                        shipAddress.country = CountryNames.getCountryName(shipAddress.country);


                    if (shipAddress.country.equals("USA") || shipAddress.country.equals("CANADA")) {
                        if (shipAddress.state.length() < 2) {
                            throw new APIContentException("US State or Canadian province value missing or invalid");
                        }
                    }
                    if (shipAddress.address_one.length() < 1 && shipAddress.address_two.length() < 1) {
                        throw new APIContentException("Street address information missing");

                    }

                    if ((shipAddress.state.equalsIgnoreCase("AA") || shipAddress.state.equalsIgnoreCase("AE")
                            || shipAddress.state.equalsIgnoreCase("AP")) && !(shipAddress.isUS())) {
                        log.debug("SWITCH to USA country for APO address");
                        shipAddress.country = "US";
                    }
                    order.getShippingInfo().setShippingAddress(shipAddress);


                    billAddress = new Address(shipAddress.company_name,
                            shipAddress.address_one,
                            shipAddress.address_two,
                            shipAddress.city,
                            shipAddress.state,
                            shipAddress.zip,
                            shipAddress.country);


                    if ("China".equalsIgnoreCase(billAddress.country)) {
                        billAddress.country = "CN";
                    }

                    if ((billAddress.country == null) || !(CountryNames.exists(billAddress.country)))
                        throw new APIContentException("Addressee country name missing or not valid");
                    else
                        billAddress.country = CountryNames.getCountryName(billAddress.country);


                    Contact shipContact = new Contact(((Element) shipNodes.item(0)).getAttribute(kShipFirstName) + " " + ((Element) shipNodes.item(0)).getAttribute(kShipLastName),
                            ((Element) shipNodes.item(0)).getAttribute(kShipPhone),
                            "",
                            ((Element) shipNodes.item(0)).getAttribute(kShipEmail), "");
                    order.getShippingInfo().setShippingContact(shipContact);

                    order.total_shipping_cost = OWDUtilities.floatFromString(((Element) shipNodes.item(0)).getAttribute(kShipCost));


                    //   order.getBillingAddress().address_one = OWDUtilities.multiLineToOneLine(order.getBillingAddress().address_one);
                    //   order.getBillingAddress().address_two = OWDUtilities.multiLineToOneLine(order.getBillingAddress().address_two);
                    order.getShippingAddress().address_one = OWDUtilities.multiLineToOneLine(order.getShippingAddress().address_one);
                    order.getShippingAddress().address_two = OWDUtilities.multiLineToOneLine(order.getShippingAddress().address_two);

                    try {
                        NodeList notesNodes = root.getElementsByTagName(kNotesNodeName);
                        if (notesNodes != null) {
                            if (notesNodes.getLength() > 0)
                                order.getShippingInfo().whse_notes = notesNodes.item(0).getFirstChild().getNodeValue();
                        }
                    } catch (Throwable th) {

                    }
                    log.debug("API:items node");
                    NodeList itemNodes = root.getElementsByTagName(kItemsNodeName);
                    if (itemNodes != null) {
                        if (itemNodes.getLength() > 0) {
                            for (int i = 0; i < itemNodes.getLength(); i++) {
                                String forceColor = null;
                                boolean doNotShipAlone = false;
                                String part = ((Element) itemNodes.item(i)).getAttribute(kItemSKU);
                                if ("320".equals(client.client_id.trim())) {
                                    if (part.contains("EE-")) {
                                        order.order_type = order.order_type + "/" + part.substring(3);
                                        forceColor = part.substring(3);
                                        part = "EE";

                                    }
                                }
                                String externalLineReference = ((Element) itemNodes.item(i)).getAttribute(kItemExternalLineRef);
                                if (null == externalLineReference) {
                                    externalLineReference = "";
                                }
                                String externalLineReference2 = ((Element) itemNodes.item(i)).getAttribute("line_number");

                                if (null == externalLineReference2) {
                                    externalLineReference2 = "";
                                }

                                if (externalLineReference2.trim().length() > 0 && externalLineReference.trim().length() == 0)
                                    externalLineReference = externalLineReference2.trim();

                                String doNotShipAloneStr = ((Element) itemNodes.item(i)).getAttribute(kItemDoNotShipAlone);
                                if (null == doNotShipAloneStr) {
                                    doNotShipAloneStr = "";
                                }
                                if (doNotShipAloneStr.equalsIgnoreCase("YES")) {
                                    doNotShipAlone = true;
                                }

                                String oldPart = ((Element) itemNodes.item(i)).getAttribute(kItemSKU);

                                if ("109".equals(client.client_id.trim())) //switch aliases for SRB orders
                                {
                                    ////////log.debug("got 109 alias fix");
                                    if (part.indexOf("/") > 0) {

                                        part = part.substring(0, part.indexOf("/"));
                                    }
                                }


                                String costTest = ((Element) itemNodes.item(i)).getAttribute(kItemCost);
                                try {
                                    float costTestF = new Float(costTest).floatValue();

                                } catch (Exception exxx) {

                                    costTest = "0.00";
                                }
                                boolean forceBackorderQuantity = false;
                                String desc = ((Element) itemNodes.item(i)).getAttribute(kItemDesc);

                                float cost = OWDUtilities.floatFromString(((Element) itemNodes.item(i)).getAttribute(kItemCost));

                                if (cost < (float) 0.00)
                                    throw new APIContentException("Cost less than zero for SKU " + part);
                                int qty = OWDUtilities.intFromString(((Element) itemNodes.item(i)).getAttribute(kItemCount));
                                if (qty < 1)
                                    throw new APIContentException("Quantity requested less than one for SKU " + part);


                                if (client.client_id.equals("311")) {

                                    part = puddleDancerVirtualCheck(part, qty, order, cost, desc, postHandlingPartMaps);
                                }

                                if (part != null) {
                                    part = part.trim();
                                }


                                if (part != null) {
                                    String rawBORequest = ((Element) itemNodes.item(i)).getAttribute(kItemBackorderCount);
                                    if (rawBORequest == null) rawBORequest = "";

                                    int qtyBackorder = OWDUtilities.intFromString(((Element) itemNodes.item(i)).getAttribute(kItemBackorderCount));


                                    if (qtyBackorder >= 0 && rawBORequest.length() > 0) {
                                        forceBackorderQuantity = true;
                                    }

                                    if (LineItem.SKUExists(client.client_id, part.trim())) {
                                        addItemToOrder(itemNodes.item(i), part, client, order, qty, cost, desc, externalLineReference, forceBackorderQuantity, qtyBackorder, doNotShipAlone, forceColor);
                                    } else {
                                        if (client.client_id.equals("494") && ("ME-1234".equals(part.trim()) || "ME -1234".equals(part.trim()))) {

                                        } else {
                                            throw new APIContentException("No inventory found matching SKU " + part + " for com.owd.api.client ID " + client.client_id);
                                        }
                                    }
                                } else {
                                }
                            }

                        } else {
                            throw new APIContentException("No valid line items found in order");
                        }
                    } else {
                        throw new APIContentException("No valid line items found in order");
                    }


                    //all done adding line items, so figure out facility for Symphony
                    if (FacilitiesManager.isClientMultiFacility(owdClient.getClientId())) {


                        if (!FacilitiesManager.getActiveFacilityCodes().contains(order.getFacilityCode())&&!order.getFacilityCode().equalsIgnoreCase("CLOSEST")) {
                            throw new APIContentException("A facility_rule attribute of the OWD_ORDER_CREATE_REQUEST element is required and must have one of the following values: " + StringUtils.join(FacilitiesManager.getActiveFacilityCodes(), ","));
                        }
                    }

                    processPartListForPuddledancer(order, postHandlingPartMaps, testing);

                    log.debug("API:options node");
                    String dvaluestr = ((Element) shipNodes.item(0)).getAttribute(kShipDeclaredValue);
                    if (dvaluestr == null) dvaluestr = "0.00";
                    if (dvaluestr.trim().length() < 1) dvaluestr = "0.00";
                    float dvalue = 0;

                    try {
                        dvalue = new Float(dvaluestr).floatValue();
                    } catch (NumberFormatException nfex) {
                        throw new APIContentException("Declared value " + dvaluestr + " for order not a valid number");
                    }
                    String cdesc = ((Element) shipNodes.item(0)).getAttribute(kShipCustomsDesc);
                    if (cdesc == null) cdesc = "";


                    String ivaluestr = ((Element) shipNodes.item(0)).getAttribute(kShipInsureAmount);
                    if (ivaluestr == null) ivaluestr = "0.00";
                    if (ivaluestr.trim().length() < 1) ivaluestr = "0.00";
                    float ivalue = 0;

                    try {
                        ivalue = new Float(ivaluestr).floatValue();
                    } catch (NumberFormatException nfex) {
                        throw new APIContentException("Insured value " + ivaluestr + " for order not a valid number");
                    }


                    order.getShippingInfo().customs_value = dvaluestr;
                    order.getShippingInfo().customs_desc = cdesc;

                    if (ivalue > (float) 0.00 && (!("314".equals(order.clientID))) && (!("486".equals(order.clientID)))) {
                        order.getShippingInfo().ss_declared_value = "1";
                        order.getShippingInfo().declared_value = ivaluestr;
                    } else {
                        order.getShippingInfo().ss_declared_value = "0";
                        order.getShippingInfo().declared_value = "0.00";
                    }


                    String call_tag_desc = ((Element) shipNodes.item(0)).getAttribute(kCallTagDescription);
                    if (call_tag_desc != null && call_tag_desc.length() > 0) {
                        order.getShippingInfo().ss_call_tag = "1";
                        order.getShippingInfo().call_tag = call_tag_desc;
                    }

                    String cod_amount = ((Element) shipNodes.item(0)).getAttribute(kCODAmount);
                    if (cod_amount == null) {
                        cod_amount = "0.00";
                    }
                    if (cod_amount.length() > 0) {
                        try {

                            float codValue = Float.parseFloat(cod_amount);
                            if (codValue > 0.00f) {
                                throw new APIContentException("COD not supported");
                            }
                        } catch (Exception ex) {

                        }
                    }


                    String saturday_delivery = ((Element) shipNodes.item(0)).getAttribute(kSaturdayDelivery);
                    if (saturday_delivery != null && saturday_delivery.length() > 0) {
                        order.getShippingInfo().ss_saturday = "1";

                    }
                    String proof_of_delivery = ((Element) shipNodes.item(0)).getAttribute(kProofOfDelivery);
                    if (proof_of_delivery != null && proof_of_delivery.length() > 0) {
                        order.getShippingInfo().ss_proof_delivery = "1";

                    }


                    if (order.getShippingAddress().country == null || order.getShippingAddress().isUS()) {
                        order.getShippingAddress().country = AddressManager.getCorrectCountryForUSTerritoryTwoLetterCode(order.getShippingAddress().state, order.getShippingAddress().country);
                    }

                    //best way shipping check
                    if (edi_reference == null) {
                        String shipType = ((Element) shipNodes.item(0)).getAttribute(kShipType);
                        if (shipType == null) shipType = "";
                        if (shipType.trim().toUpperCase().equals("BWY")) {
                            //best way shipping
                            NodeList bwayNodes = root.getElementsByTagName(kBestWayNodeName);
                            log.debug("API:BestWay");


                            if (bwayNodes != null) {
                                if (bwayNodes.getLength() > 0) {
                                    StringBuffer sb = new StringBuffer();
                                    NodeList carrierNodes = ((Element) bwayNodes.item(0)).getElementsByTagName(kBestWayCarrierNodeName);
                                    if (carrierNodes != null) {
                                        if (carrierNodes.getLength() > 0) {
                                            String svcCode = ((Element) carrierNodes.item(0)).getFirstChild().getNodeValue();

                                            log.debug("API adding " + svcCode + " to best way carrier list1");
                                            svcCode = OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef(svcCode)));
                                            log.debug("API adding " + svcCode + " to best way carrier list2");

                                            svcCodes.add(svcCode);
                                            for (int i = 1; i < carrierNodes.getLength(); i++) {
                                                svcCode = ((Element) carrierNodes.item(i)).getFirstChild().getNodeValue();
                                                log.debug("API adding " + svcCode + " to best way carrier list3");
                                                try {
                                                    svcCode = OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef(svcCode)));
                                                }catch(Exception e){
                                                    throw new Exception("Invalid service code: "+svcCode);
                                                }
                                                log.debug("API adding " + svcCode + " to best way carrier list4");
                                                svcCodes.add(svcCode);
                                            }
                                        }
                                    }
                                }
                            }


                            if (svcCodes.size() < 1)
                                throw new Exception("No valid ship method codes were found. You must provide at least one valid ship method code in the BEST_WAY element node when using the BWY ship method");


                            OwdClient owdclient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.decode(order.clientID));

                            OrderRater rater = new OrderRater(order);
                            rater.setOriginCode(FacilitiesManager.getActiveFacilityCodes().contains(order.getFacilityCode()) ? order.getFacilityCode() : (FacilitiesManager.isClientMultiFacility(owdclient.getClientId()) ? "DC1" : owdclient.getDefaultFacilityCode()));
                            rater.setClientId("" + owdclient.getClientId());
                            rater.setCalculateKitWeights(true);


                            boolean addressValidated = false;

                            try {
                                shipType = rater.selectBestWay(svcCodes);
                                order.getShippingInfo().setShipOptions(shipType, "Prepaid", "");
                            } catch (Exception exrate) {

                                try {

                                    AddressManager.validate(order.getShippingInfo().shipAddress);
                                    AddressManager.validate(order.getBillingAddress());
                                    addressValidated = true;

                                    shipType = rater.selectBestWay(svcCodes);
                                    order.getShippingInfo().setShipOptions(shipType, "Prepaid", "");

                                } catch (Exception exx) {
                                    if (addressValidated) {
                                        throw new Exception(exrate.getMessage() + "; address validated");
                                    } else {
                                        throw new Exception(exrate.getMessage() + "; address not validated");
                                    }
                                }

                            }


                            //log.debug("Best Way chose ship method "+shipType);


                        } else {
                            log.debug("checking ship type " + shipType);

                            shipType = OrderUtilities.getServiceList().getValueForRef(shipType);
                        }

                        if (shipType == null) {
                            throw new Exception("No valid ship method code was found. Value read was: " + shipType + ". See http://service.owd.com/webapps/clienttools/shipmethods.jsp for current valid code list.");

                        }
                        log.debug("confirmed ship type " + shipType);

                        if (api_version >= 1.6) {
                            String shipTermsRef = ((Element) shipNodes.item(0)).getAttribute(kShipTerms);
                            if (shipTermsRef == null) shipTermsRef = "SHIPPER";
                            if (shipTermsRef.length() < 1) shipTermsRef = "SHIPPER";

//log.debug("shipping terms ref: "+shipTermsRef);
                            String shipTerms = OrderUtilities.getTermsList().getValueForRef(shipTermsRef);
//log.debug("shipping terms: "+shipTerms);

                            String shipping_acct = ((Element) shipNodes.item(0)).getAttribute(kShipAcct);
//log.debug("shipping account: "+shipping_acct);
                            shipping_acct = shipping_acct == null ? "" : shipping_acct;

                            try {
                                order.getShippingInfo().setShipOptions(shipType, shipTerms, shipping_acct);
                                if (shipping_acct.length() > 0 && shipTermsRef.equalsIgnoreCase("SHIPPER")) {
                                    order.getShippingInfo().carr_freight_terms = "Third Party Billing";
                                }


                            } catch (Exception ex) {
                                ex.printStackTrace();
                                throw new APIContentException(ex.getMessage());

                            }


                        } else {
                            String shipTerm = ((Element) shipNodes.item(0)).getAttribute(kShipTerms);
                            if (shipTerm == null) shipTerm = "Prepaid";
                            if (shipTerm.length() < 1) shipTerm = "Prepaid";

                            String shipTermRef = OrderUtilities.getTermsList().getRefForValue(shipTerm);
//log.debug("shipping terms: "+shipTerm);
                            try {
                                log.debug("confirmed 3 ship type " + shipType);
                                order.getShippingInfo().setShipOptions(shipType, shipTerm, "");

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                throw new APIContentException(ex.getMessage());

                            }
                        }
                        log.debug("confirmed 4 ship type " + shipType);
                        //1.1
                        NodeList shipperNodes = root.getElementsByTagName(kThirdPartyShipperNodeName);
                        if (shipperNodes != null) {
                            if (shipperNodes.getLength() > 0) {
                                order.shippercontact = ((Element) shipperNodes.item(0)).getAttribute(kThirdName);
                                order.shippercompany = ((Element) shipperNodes.item(0)).getAttribute(kThirdCompany);
                                order.shipperaddress_one = ((Element) shipperNodes.item(0)).getAttribute(kThirdAdd1);
                                order.shipperaddress_two = ((Element) shipperNodes.item(0)).getAttribute(kThirdAdd2);
                                order.shippercity = ((Element) shipperNodes.item(0)).getAttribute(kThirdCity);
                                order.shipperstate = ((Element) shipperNodes.item(0)).getAttribute(kThirdState);
                                order.shipperzip = ((Element) shipperNodes.item(0)).getAttribute(kThirdZip);
                                order.shipperphone = ((Element) shipperNodes.item(0)).getAttribute(kThirdPhone);
                                if (api_version < 1.6) {
                                    order.shipperacct = ((Element) shipperNodes.item(0)).getAttribute(kThirdAcct);
                                    order.getShippingInfo().third_party_refnum = ((Element) shipperNodes.item(0)).getAttribute(kThirdAcct);
                                    order.shipperbilling_ref = ((Element) shipperNodes.item(0)).getAttribute(kThirdBillingRef);
                                    try {
                                        if (order.shipperacct != null && order.shipperacct.length() > 0 && order.getShippingInfo().carr_freight_terms_ref_num.equalsIgnoreCase("SHIPPER")) {
                                            order.getShippingInfo().carr_freight_terms = "Third Party Billing";
                                        }


                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        throw new APIContentException(ex.getMessage());

                                    }
                                }


                            }
                        }

                        NodeList intlNodes = root.getElementsByTagName(kIntlShipperNodeName);
                        if (intlNodes != null) {
                            if (intlNodes.getLength() > 0) {
                                order.taxcontact = ((Element) intlNodes.item(0)).getAttribute(kTaxName);
                                order.taxcompany = ((Element) intlNodes.item(0)).getAttribute(kTaxCompany);
                                order.taxaddress_one = ((Element) intlNodes.item(0)).getAttribute(kTaxAdd1);
                                order.taxaddress_two = ((Element) intlNodes.item(0)).getAttribute(kTaxAdd2);
                                order.taxcity = ((Element) intlNodes.item(0)).getAttribute(kTaxCity);
                                order.taxstate = ((Element) intlNodes.item(0)).getAttribute(kTaxState);
                                order.taxzip = ((Element) intlNodes.item(0)).getAttribute(kTaxZip);
                                order.taxphone = ((Element) intlNodes.item(0)).getAttribute(kTaxPhone);
                                order.taxaccount = ((Element) intlNodes.item(0)).getAttribute(kTaxAccount);
                                if (null != ((Element) intlNodes.item(0)).getAttribute(kImporterOfRecord)) {
                                    order.importerOfRecord = "YES".equals(((Element) intlNodes.item(0)).getAttribute(kImporterOfRecord)) ? 1 : 0;
                                } else {
                                    order.importerOfRecord = 0;
                                }


                            }
                        }
                    } else {
                        EDIUtilities.applyEdiShippingMethod(order);
                    }

                    if (api_version >= 2.1) {
                        log.debug("API:custom values (tags) node");
                        NodeList tagNodes = root.getElementsByTagName(kCustomValueNodeName);
                        if (tagNodes != null) {
                            if (tagNodes.getLength() > 0) {
                                for (int i = 0; i < tagNodes.getLength(); i++) {
                                    if(((Element)tagNodes.item(i)).hasAttribute(kCustomValueName)) {

                                        String aname = ((Element) tagNodes.item(i)).getAttribute(kCustomValueName);
                                        if( tagNodes.item(i).getChildNodes().getLength()>0){
                                         String avalue = ((Element) tagNodes.item(i)).getFirstChild().getNodeValue();
                                             if( avalue.length()>0) {
                                             order.addTag(aname.toUpperCase(), avalue);
                                         }

                                        }
                                    }


                                }
                            }
                        }
                    }


                } else {
                    throw new APIContentException(kShipNodeName + " element missing or invalid");
                }
            } else {
                throw new APIContentException(kShipNodeName + " element missing or invalid");
            }

            //ship method node done. Custom corrections here:

            order.recalculateBalance();


            billNodes = root.getElementsByTagName(kBillNodeName);
            if (billNodes != null) {
                if (billNodes.getLength() > 0) {
                    log.debug("API:payment node");
                    //log.debug("payment_type: "+payment_type);
//  order.bill_cc_type=payment_type;
                    if (payment_type.equals("CLIENT")) {
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                        order.is_paid = 1;
                        order.bill_cc_type = "CL";
                        if (null != ((Element) billNodes.item(0)).getAttribute(kBillAcct)) {
                            order.cc_num = ((Element) billNodes.item(0)).getAttribute(kBillAcct).trim();
                            try {
                                if (order.cc_num.length() > 0 && (!(CreditCard.isValid(CreditCard.parseDirtyLong(order.cc_num))))) {
                                    throw new Exception("Credit card number did not pass validity check");
                                }

                                order.cc_exp_mon = OWDUtilities.intFromString(((Element) billNodes.item(0)).getAttribute(kBillExpMonth));
                                order.cc_exp_year = OWDUtilities.intFromString(((Element) billNodes.item(0)).getAttribute(kBillExpYear));
                                order.cc_auth_code = (((Element) billNodes.item(0)).getAttribute(kBillCCAuthCode));
                                order.cc_trans_code = (((Element) billNodes.item(0)).getAttribute(kBillCCAuthTrans));
                                order.cc_auth_amount = OWDUtilities.floatFromString(((Element) billNodes.item(0)).getAttribute(kBillCCAuthAmount));

                            } catch (Exception excc) {
                                excc.printStackTrace();
                                throw new APIContentException("The credit card information provided was invalid. External transactions require auth_code, trans_code, auth_amount, exp_month, exp_year and acct_no attributes to be supplied with valid data.");

                            }

                        }
                    } else if (payment_type.equals("CC")) {
                        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                        order.cc_num = ((Element) billNodes.item(0)).getAttribute(kBillAcct);
                        try {
                            if (!(CreditCard.isValid(CreditCard.parseDirtyLong(order.cc_num)))) {
                                throw new Exception();
                            }
                        } catch (Exception excc) {
                            excc.printStackTrace();
                            throw new APIContentException("The credit card account number provided was invalid");
                        }
                        order.cc_exp_mon = OWDUtilities.intFromString(((Element) billNodes.item(0)).getAttribute(kBillExpMonth));
                        order.cc_exp_year = OWDUtilities.intFromString(((Element) billNodes.item(0)).getAttribute(kBillExpYear));


                        order.bill_cc_type = "CC";
                    } else if (payment_type.equals("CK")) {

                        order.is_future_ship = 1;
                        order.cc_num = ((Element) billNodes.item(0)).getAttribute(kBillAcct);
                        order.bill_cc_type = "CK";
                        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                    } else if (payment_type.equals("PO")) {
                        //order.po_number =  ((Element)billNodes.item(0)).getAttribute(kBillPO);
                        order.is_future_ship = 1;
                        order.bill_cc_type = "PO";
                        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
//order.bill_cc_type = payment_type;
                    } else {
                        throw new APIContentException("payment_type attribute missing or invalid");
                    }


                    String paid_date = ((Element) billNodes.item(0)).getAttribute(kPaidDate);
                    if (paid_date != null) {
                        if (paid_date.length() > 3) {
                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                Date aDate = formatter.parse(paid_date);
                                order.paid_date = formatter.format(aDate);

                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new Exception("Invalid paid_date value");
                            }
                        }
                    }
                } else {
                    throw new APIContentException(kBillNodeName + " element missing or invalid");
                }
            } else {
                throw new APIContentException(kBillNodeName + " element missing or invalid");
            }

            if (order.getBillingAddress().address_one.length() < 1 && order.getBillingAddress().address_two.length() < 1) {
                order.setBillingAddress(order.getShippingAddress());
            }
            if (order.getBillingContact().getName().length() < 1) {
                order.setBillingContact(order.getShippingContact());
            }

            if ("".equals(order.getShippingContact().getPhone().trim())) {
                order.getShippingContact().setPhone(order.getBillingContact().getPhone());
            }


            try {
                NodeList msgNodes = root.getElementsByTagName(kMessageNodeName);
                if (order.is_gift.equals("1") && msgNodes != null) {
                    if (msgNodes.getLength() > 0)
                        order.gift_message = msgNodes.item(0).getFirstChild().getNodeValue();
                }
            } catch (Throwable th) {

            }

            try {
                NodeList commentNodes = root.getElementsByTagName(kCommentNodeName);
                if (commentNodes != null) {
                    if (commentNodes.getLength() > 0) {

                        order.getShippingInfo().comments = commentNodes.item(0).getFirstChild().getNodeValue();
                    }
                }
            } catch (Throwable th) {

            }


            if ("477".equals(client.client_id.trim())) {   //Orili

                if (order.containsSKU("LK-RF") && (order.getQuantityForSKU("LK-RF") < 4)) {
                    order.addLineItem("PACK-50-40POLY", 1, 0.00f, 0.00f, "", "", "", 0, "");
                }

            }




            if ("494".equals(client.client_id)) {    //marine essentials
                order.order_refnum = "LL" + order.order_refnum;
            }


            if ("507".equals(client.client_id)) {   //blue bottle coffee
                boolean hasExpensiveItem = false;

                for (LineItem item : (Vector<LineItem>) order.skuList) {
                    if (item.sku_price > 130.00) {
                        hasExpensiveItem = true;
                        break;
                    }
                }
                if (hasExpensiveItem || (order.getTotalOrderCost() >= 300.00f)) {
                    order.getShippingInfo().ss_declared_value = "1";
                    order.getShippingInfo().declared_value = "" + (order.getTotalOrderCost());
                    //  order.getShippingInfo().ss_proof_delivery = "1";

                }

            }


            try {
                KnowledgeBaseManager.executeRule(order, "OrderInsert");
                log.debug("executed Drools rule for order");

            } catch (Throwable e) {
                System.err.println("Error executing rule for order: " + order.getOrderRefNum() + " - " + e.getLocalizedMessage());
                e.printStackTrace();
                throw new APIRuleException(e.getLocalizedMessage());
            }


            //set in Drools rule to catch rejected orders without throwing Exceptions from rules engine
            if (!(order.getProperty("RULE_ERROR", "OK").equalsIgnoreCase("OK"))) {
                throw new APIContentException(order.getProperty("RULE_ERROR"));
            }



            if ("486".equals(client.client_id.trim())) {  //Luscious Garden
                //if UPS
                if (order.getShippingAddress().isInternational()) {
                    if (order.getShippingInfo().carr_service.contains("Priority") || order.getShippingInfo().carr_service.contains("Express")) {
                        float totalValue = order.total_order_cost;

                        if (order.getShippingInfo().carr_service.contains("Express")) {
                            if (totalValue > 100.00f) {
                                order.getShippingInfo().ss_declared_value = "1";
                                order.getShippingInfo().declared_value = "" + (totalValue - 100.00f);

                            }
                        } else {
                            order.getShippingInfo().ss_declared_value = "1";
                            order.getShippingInfo().declared_value = "" + (totalValue);
                        }
                    }

                    if (!(order.getBillingAddress().isInternational())) {
                        order.is_future_ship = 1;
                        order.addNote("Placed on hold for manual review due to international shipping address");
                    }
                }

            }

            if ("387".equals(client.client_id.trim())) {  //photojojo
                order.postDateHoursDelay = 1;

            }


            if ("158".equals(client.client_id.trim())) //Days of Wonder print templates
            {


                if (order.getShippingInfo().comments.trim().length() > 2) //distributor orders
                {
                    order.prt_pack_reqd = 0;
                    order.prt_invoice_reqd = 0;
                    order.prt_pick_reqd = 1;
                }
            }


            order.testing = testing;


            if ("266".equals(client.client_id.trim())) //algaecal
            {

                order.postDateHoursDelay = 24;


            }

            if ("407".equals(client.client_id.trim())) //kathysmith.com
            {

                if (order.getShippingAddress().isCanada()) {
                    try {
                        Mailer.sendMail("Kathysmith.com Canadian order received", "Order Reference " + order.order_refnum + " was received by OWD for shipment to Canada. This email is the notification you requested.", "jeff@kathysmith.com", "donotreply@owd.com");
                    } catch (Exception ex) {

                    }
                }


            }

            if (client.client_id.trim().equals("489") && order.group_name.equals("fijiwater")){
                if (order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("LANDOLAKES")
                        && order.getShippingAddress().address_one.replaceAll(" ", "").toUpperCase().equals("5940CANDYTUFTPL")
                        ) {
                    order.is_future_ship = 1;
                    order.hold_reason = "fraud check - 5940 CANDYTUFT PL";
                }
            }

            if (client.client_id.trim().equals("320"))//Lifespan
            {
                //lit insert
                if (order.getQuantityForSKU("EE") > 0) {
                    order.addInsertItemIfAvailable("DFLYER", 1);
                }

                if (order.getShippingAddress().address_one.replaceAll(" ", "").toUpperCase().equals("1228MCMILLIN")) {
                    order.is_future_ship = 1;
                    order.hold_reason = "fraud check";
                }

                List<String> fraudName = new ArrayList<String>();
                fraudName.add("SAMMIEADDO");
                fraudName.add("MIRCEACONSTANTINESCU");
                fraudName.add("LUZBEAILLVIDAL");
                fraudName.add("MICHAELORLANDELLA");
                
                        fraudName.add("MOUKINHFAIDH");
                fraudName.add("MOUKIMHFAIDH");
                fraudName.add("RACHELGARCIA");

                if (fraudName.contains(order.getShippingContact().getName().replaceAll(" ", "").toUpperCase())) {
                    order.is_future_ship = 1;
                    order.hold_reason = "fraud check Name List";
                }

                if (order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("MIAMI")
                    || order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("FL")
                    || order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("FLORIDA")) {
                order.is_future_ship = 1;
                order.hold_reason = "fraud check - Miami/Florida";
            }
                if ( order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("NJ")
                        || order.getShippingAddress().city.replaceAll(" ", "").toUpperCase().equals("NEWJERSEY")) {
                    order.is_future_ship = 1;
                    order.hold_reason = "fraud check - New Jersey";
                }
                if (order.getShippingAddress().country.toUpperCase().equals("TUNISIA")){
                    order.is_future_ship = 1;
                    order.hold_reason = "Fraud check - Tunisia";
                }
                if (order.getShippingAddress().country.toUpperCase().equals("INDIA")){
                    order.is_future_ship = 1;
                    order.hold_reason = "Fraud check - India";
                }

                for (LineItem line : (Vector<LineItem>) order.skuList) {
                    if (line.getQuantityRequest() > 5) {
                        order.is_future_ship = 1;
                        order.hold_reason = "fraud check - line quantity greater than 5";
                    }
                }
            }


            if (client.client_id.equals("311")) {   //puddledancer
                order.postDateHoursDelay = 1;
            }

            if (client.client_id.equals("396")) { //Biotanical add sig confirmation request to all shipments
                order.getShippingInfo().ss_proof_delivery = "1";

            }


            //1.1
            if (root.getAttribute(kIsToBeHeld).equals("YES")) {
                order.is_future_ship = 1;
            }


            if ("1".equals(order.is_gift) && order.gift_message.trim().length() < 2) {
                order.gift_message = "A gift for you!";
            }


            String reference = null;

//log.debug("payment type before saving the order: "+order.bill_cc_type);


            if (client.client_id.equals("460"))   //midco
            {
                long dtas = order.getQuantityForSKU("13535");
                if (dtas > 0) {

                    order.addLineItem("Sleeve", "" + dtas, "0.00", "0.00", "", "", "");
                    order.addLineItem("Brochure", "1", "0.00", "0.00", "", "", "");
                    order.addLineItem("USPS", "1", "0.00", "0.00", "", "", "");
                }

            }


            if ("489".equals(order.getClientID()) || "491".equals(order.getClientID())) {
                if (order.getShippingInfo().carr_service.equals("LTL")) {
                    order.is_future_ship = 1;
                    order.addNote("Placed on hold due to LTL ship method");
                }


            }


            if (order.clientID.equals("489") || order.clientID.equals("491")) {
                // SymphonyStuff.applyTemporaryExoPromotionJuly2015(order);
                SymphonyStuff.applySpecialRules(order);
            }



            if (order.is_future_ship == 1) {
                reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, testing);
            } else {
                reference = order.saveNewOrder(OrderUtilities.kRequirePayment, testing);
            }


            if (reference == null || !order.completed) {
                throw new APIProcessException("Order Processing Error: " + order.last_error);
            } else {

                if(!testing) {
                    try {
                        KnowledgeBaseManager.executeRule(order, "OrderPostCreate");
                        log.debug("executed Drools rule for order");

                    } catch (Throwable e) {
                        log.debug("Error executing rule for order: " + order.getOrderRefNum() + " - " + e.getLocalizedMessage());
                        e.printStackTrace();
                        throw new APIRuleException(e.getLocalizedMessage());
                    }

                }
                if (order.isWorkOrder) {


                    String body = "Symphony Order Ref: " + order.getClientOrderReference() + " OWD Ref: " + order.getOrderRefNum() + " Brand: Fiji Water\n\n\n - DC: Palletize order \n\n" +
                            " - DC: Obtain weight/dims and update work order and assign to Tawna" +
                            " - Fetch LTL shipping quote\n\n" +
                            " - Include carrier name, contact info and estimated time in transit with quote\n\n" +
                            " - If shipment approved, also forward PRO# and ETA of shipment\n\n" +
                            " - Forward quote and shipment reports to these 3 email addresses:\n\n -- maxantonio.burger@fijiwater.com\n" +
                            " -- alexandria.vaughan@fijiwater.com\n" +
                            " -- fops@symphonycommerce.com\n\n" +
                            " - Fiji will either approve shipment or cancel and ship from another location\n";

                    PreparedStatement psedi = HibernateSession.getPreparedStatement("insert into case_queue (subject,body,brand,facility) VALUES (?,?,?,?);");
                    psedi.setString(1, "Billable LTL Quote Request for Symphony/Fiji Waters Order Ref: " + order.getClientOrderReference());
                    psedi.setString(2, body);
                    psedi.setString(3, order.getGroupName());
                    psedi.setString(4, order.getFacilityCode());
                    psedi.executeUpdate();
                    psedi.close();
                    if (!testing) {
                        HibUtils.commit(HibernateSession.currentSession());

                    }


                }


                if ("486".equals(order.clientID) && order.is_future_ship == 1 && order.getImportNotes().size() > 0) {
                    CaseTrackerManager.createNewCaseForClient("Luscious Gardens Order Placed On Hold (OWD Ref:" + order.orderNum + ")",
                            "Order held due to order fraud check rule - verify for fraud before releasing", order.clientID, null, CaseTrackerManager.getLoginToken());
                }

                if (order.getImportNotes().size() > 0 && reference != null) {
                    Iterator it = order.getImportNotes().iterator();
                    while (it.hasNext()) {
                        Event.addOrderEvent(new Integer(order.orderID), Event.kEventTypeGeneral, (String) it.next(), "XML API");
                    }
                }

                log.debug("CHECKING PUDDLE BACKMAIL");
                if (order.is_backorder == 1 && reference != null && "311".equals(client.client_id) && MailAddressValidator.isValid(order.getBillingContact().getEmail())) {
                    //puddledance backorder email
                    log.debug("SENDING PUDDLE BACKMAIL");
                    sendPuddleDancerBackorderNotifyEmail(order);
                }

                log.debug("CHECKING LIFESPAN BACKMAIL");
                if (order.is_backorder == 1 && reference != null && "320".equals(client.client_id) && MailAddressValidator.isValid(order.getBillingContact().getEmail())) {
                    //puddledance backorder email
                    log.debug("SENDING LIFESPAN BACKMAIL");
                   // sendLifespanBackorderNotifyEmail(order);
                }

                log.debug("CHECKING LIFESPAN ORDER HOLDS");
                if (order.is_future_ship == 1 && reference != null && "320".equals(client.client_id) && ("" + order.hold_reason).contains("fraud check")) {
                    //puddledance backorder email
                    Mailer.sendMail("OWD Notification of Held Order for Xendurance (" + order.order_refnum + ")", order.hold_reason,
                            "robyn@xendurance.com",
                            "donotreply@owd.com");
                }


                Iterator it = subscriptions.iterator();
                while (it.hasNext()) {
                    APISubscriptionCreator subscription = (APISubscriptionCreator) it.next();
                    if (!testing && subscription.getSubscriptionItems().size() > 0) {
                        subscription.createSubscription(order);
                    }
                }
            }

            response.buildFromOrder(order);

        }
        return response;
    }






    private static void sendPuddleDancerBackorderNotifyEmail(Order order) throws Exception {
        String body =
                "\n" +
                        "Dear " + order.getBillingContact().getName() + ",\n" +
                        "\n" +
                        "Your order number " + order.order_refnum + " for the following products has been received:\n" +
                        "\n";
        for (int l = 0; l < order.skuList.size(); l++) {
            LineItem line = (LineItem) order.skuList.elementAt(l);
            int available = OrderUtilities.getAvailableInventory(line.getInventory().inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());
            body = body + "ITEM: " + line.client_part_no + "(QTY: " + line.quantity_request + ")" + (available < line.quantity_request ? " (OUT OF STOCK)" : "") + "\n";
            body = body + "DESCRIPTION: " + line.description + "\n\n";
        }

        body = body + "As you were notified during the checkout process, some of these products " +
                "don't exist in the desired quantity in our stock.\n" +
                "\n" +
                "By placing your order you agreed that your entire order will be placed on " +
                "hold until the backorder can be fulfilled. Your order will be delivered as " +
                "soon as the items listed as OUT OF STOCK become available again.\n" +
                "\n" +
                "Please contact us immediately if you have any questions or concerns about " +
                "this order.\n\n" +
                "Orders@PuddleDancer.com\n" +
                "\n" +
                "Again, thank you so much for ordering from NonviolentCommunication.com!\n" +
                "\n" +
                "We look forward to serving you in the future.";
        Mailer.sendMail("NonviolentCommunication.com Backordered Product Notice for Order " + order.order_refnum, body, order.getBillingContact().getEmail(), "orders@puddledancer.com");
        Mailer.sendMail("(COPY) NonviolentCommunication.com Backordered Product Notice for Order " + order.order_refnum, "(TO: " + order.getBillingContact().getEmail() + ")\n" + body, "orders@puddledancer.com", "orders@puddledancer.com");

    }

    private static void sendLifespanBackorderNotifyEmail(Order order) throws Exception {

        String body = "";
/*
        for (int l = 0; l < order.skuList.size(); l++) {
            LineItem line = (LineItem) order.skuList.elementAt(l);
            int available = line.getInventory().qty_on_hand;
            body = body + "ITEM: " + line.client_part_no + "(QTY: " + line.quantity_request + ")" + "  DESCRIPTION: " + line.description + "\n";
        }*/
        body = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta name=\"Generator\" content=\"Microsoft Word 15 (filtered medium)\">\n" +
                "<!--[if !mso]><style>v\\:* {behavior:url(#default#VML);}\n" +
                "o\\:* {behavior:url(#default#VML);}\n" +
                "w\\:* {behavior:url(#default#VML);}\n" +
                ".shape {behavior:url(#default#VML);}\n" +
                "</style><![endif]--><style><!--\n" +
                "/* Font Definitions */\n" +
                "@font-face\n" +
                "\t{font-family:\"Cambria Math\";\n" +
                "\tpanose-1:2 4 5 3 5 4 6 3 2 4;}\n" +
                "@font-face\n" +
                "\t{font-family:Calibri;\n" +
                "\tpanose-1:2 15 5 2 2 2 4 3 2 4;}\n" +
                "/* Style Definitions */\n" +
                "p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
                "\t{margin:0in;\n" +
                "\tmargin-bottom:.0001pt;\n" +
                "\tfont-size:12.0pt;\n" +
                "\tfont-family:\"Calibri\",sans-serif;\n" +
                "a:link, span.MsoHyperlink\n" +
                "\t{mso-style-priority:99;\n" +
                "\tcolor:blue;\n" +
                "\ttext-decoration:underline;}\n" +
                "a:visited, span.MsoHyperlinkFollowed\n" +
                "\t{mso-style-priority:99;\n" +
                "\tcolor:purple;\n" +
                "\ttext-decoration:underline;}\n" +
                "span.EmailStyle18\n" +
                "\t{mso-style-type:personal-reply;\n" +
                "\tfont-family:\"Calibri\",sans-serif;\n" +
                "\tcolor:#1F497D;}\n" +
                ".MsoChpDefault\n" +
                "\t{mso-style-type:export-only;\n" +
                "\tfont-family:\"Calibri\",sans-serif;}\n" +
                "@page WordSection1\n" +
                "\t{size:8.5in 11.0in;\n" +
                "\tmargin:1.0in 1.0in 1.0in 1.0in;}\n" +
                "div.WordSection1\n" +
                "\t{page:WordSection1;}\n" +
                "--></style><!--[if gte mso 9]><xml>\n" +
                "<o:shapedefaults v:ext=\"edit\" spidmax=\"1026\" />\n" +
                "</xml><![endif]--><!--[if gte mso 9]><xml>\n" +
                "<o:shapelayout v:ext=\"edit\">\n" +
                "<o:idmap v:ext=\"edit\" data=\"1\" />\n" +
                "</o:shapelayout></xml><![endif]-->\n" +
                "</head>\n" +
                "<body lang=\"EN-US\" link=\"blue\" vlink=\"purple\">\n" +
                "<p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
                "<table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"background:black;padding:11.25pt 0in 11.25pt 0in\">\n" +
                "<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><img border=\"0\" id=\"_x0000_i1025\" src=\"http://cdn-ecomm.dreamingcode.com/public/257/images/logo-257-21684-1.png\"><o:p></o:p></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td valign=\"top\" style=\"background:#F0F0F0;padding:37.5pt 37.5pt 37.5pt 37.5pt\">\n" +
                "<p class=\"MsoNormal\">" +
                "<br>\n" + "\n" +
                "Dear " + order.getBillingContact().getName() + ",<br><br>\n" +
                "\n" +
                "We are doing our best to get your order out the door and running, however we are presently backordered on the following products:<br><br> \n";
        body = body + "<table  cellpadding=\"10\" ><tr><th align=left>Quantity</th><th align=left>Item</th><th align=left>Description</th></tr>";
        for (int l = 0; l < order.skuList.size(); l++) {
            LineItem line = (LineItem) order.skuList.elementAt(l);
            int available = line.getInventory().qty_on_hand;
            if (available < line.quantity_request) {
                body = body + "<tr><td>" + line.quantity_request + "</td><td>" + line.client_part_no + "</td><td> " + line.description + "</td></tr>";
            }
        }

        body = body + "</table> <br>\n" +
                "We are very sorry for this delay and will do everything we can to quickly restock our inventory.<br><br> \n" +
                "\n" +
                "Thanks very much for turning to Xendurance for your sports nutrition needs!<br><br>\n" +
                "\n" +
                "If you have any further questions, please email support@xendurance.com and we will get back to you very soon.<br><br>\n" +
                "\n" +
                "\n" +
                "Sincerely,<br>\n" +
                "The Xendurance Team<br>\n" +
                "<br>\n" +
                "<o:p></o:p></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"background:black;padding:22.5pt 0in 22.5pt 0in\">\n" +
                "<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><a href=\"https://www.facebook.com/pages/Xendurance/75865212958\" target=\"_blank\"><span style=\"text-decoration:none\"><img border=\"0\" id=\"_x0000_i1026\" src=\"http://cdn-ecomm.dreamingcode.com/public/257/images/email_06.jpg-257-23473-1.jpg\"></span></a><a href=\"https://twitter.com/xendurance\" target=\"_blank\"><span style=\"text-decoration:none\"><img border=\"0\" id=\"_x0000_i1027\" src=\"http://cdn-ecomm.dreamingcode.com/public/257/images/email_07.jpg-257-23474-1.jpg\"></span></a><a href=\"http://www.youtube.com/user/Xendurance10\" target=\"_blank\"><span style=\"text-decoration:none\"><img border=\"0\" id=\"_x0000_i1028\" src=\"http://cdn-ecomm.dreamingcode.com/public/257/images/email_03.jpg-257-23472-1.jpg\"></span></a><o:p></o:p></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<p class=\"MsoNormal\"><span style=\"font-size:10.0pt;font-family:&quot;Calibri&quot;,sans-serif\"><img border=\"0\" width=\"1\" height=\"1\" id=\"_x0000_i1029\" src=\"http://email.commerce.dreamingcode.com/wf/open?upn=uY0eif8GKUcAlb-2B0UMl-2FBepAYeEw0qPGAyuyCa6peEjTT2-2BbAS4Yff7tu0-2BFgQdZLETfaJFHq2Jfn8oOqFVrT5ASUvHG9JBBhO5nlPHODMMlDjzTPO3XRPjpHctMq-2FMumSUwsvXTKPVMxAAWId6HepGdlEE5dxxMjIo1-2FooP7KLG01Z9v2V0RRCRVplETWNF6fJA2f8SOS06g19U40yT4uv-2B7exNjXqK5ltB4inydZI-3D\"><o:p></o:p></span></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        Mailer.sendMail("Order Status Update from Xendurance", body, order.getBillingContact().getEmail(), "info@xendurance.com");

    }


    private static void processPartListForPuddledancer(Order order, List<Map> partMapList, boolean testing) {
        try {
            if (!testing) {
                if (partMapList.size() > 0) {

                    String emailbody =
                            "\n\nDear " + order.getBillingContact().getName() + ",\n" +
                                    "\n" +
                                    "  Your order number " + order.order_refnum + " for the following virtual products has been received:\n" +
                                    "\n";
                    for (Map<String, String> partMap : partMapList) {
                        emailbody = emailbody + "  ITEM: " + partMap.get("SKU") + "\n  DESCRIPTION:" + partMap.get("DESC") + "\n";
                    }
                    emailbody = emailbody + "\n" +
                            "  If you have not done so already, please download or register for your virtual product \n" +
                            "  within the next 5 days.\n" +
                            "\n" +
                            "  Use the Detailed Invoice link found in your Order Confirmation Message to \n" +
                            "  access your virtual product.\n" +
                            "\n" +
                            "  Click the Download link found in your Detailed Invoice to Download or Register \n" +
                            "  your product. \n" +
                            "\n" +
                            "\n" +
                            "  Please contact us immediately if you have any questions or concerns about \n" +
                            "  accessing your virtual product.\n" +
                            "\n" +
                            "  Again, thank you so much for ordering from NonviolentCommunication.com! \n" +
                            "  We look forward to serving you again in the future.";
                    //ignore it, but send email

                    log.debug("address:" + "\"" + order.getBillingContact().getName() + "\" <" + order.getBillingContact().getEmail() + ">");
                    try {
                        String[] bccs = new String[1];
                        bccs[0] = "Orders@puddledancer.com";

                        if (!(OrderUtilities.clientOrderReferenceExists(order.order_refnum, order.clientID, false))) {
                            Mailer.sendMail("NonviolentCommunication.com Virtual Product Order Confirmation for Order " + order.order_refnum, emailbody, "\"" + order.getBillingContact().getName() + "\" <" + order.getBillingContact().getEmail() + ">",
                                    null,
                                    bccs,
                                    "Orders@PuddleDancer.com");
                        }


                    } catch (Exception exx) {
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private static String puddleDancerVirtualCheck(String part, int qty, Order order, float cost, String desc, List<Map> partMapList) throws Exception {
        try {
            if (part.startsWith("vir-"))//virtual SKU

            {
                Map partMap = new HashMap();
                partMap.put("SKU", part);
                partMap.put("DESC", desc);

                partMapList.add(partMap);

                return null;
            } else {
                return part;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return part;
        }

    }

    protected void addItemToOrder
            (Node itemNode, String
                    part, Client
                     client, Order
                     order, int qty,
             float cost, String
                     desc, String
                     externalLineReference, boolean forceBackorderQuantity,
             int qtyBackorder, boolean doNotShipAlone, String forceColor) throws Exception {




        String dvaluestr = ((Element) itemNode).getAttribute(kItemDeclaredValue);
        if (dvaluestr == null) dvaluestr = "0.00";
        if (dvaluestr.trim().length() < 1) dvaluestr = "0.00";
        float dvalue = 0;

        try {
            dvalue = new Float(dvaluestr).floatValue();
        } catch (NumberFormatException nfex) {
            throw new APIContentException("Declared value for SKU " + part + " not a valid number");
        }
        String cdesc = ((Element) itemNode).getAttribute(kItemCustomsDesc);
        if (cdesc == null) cdesc = "";


        order.addLineItem(LineItem.getCleanSKU(part), qty, cost, cost * qty, desc, forceColor == null ? "" : forceColor, "", dvalue, cdesc.toUpperCase().replaceAll("MERCHANDISE", ""));
        LineItem item = (LineItem) order.skuList.elementAt(order.skuList.size() - 1);
        item.client_ref_num = externalLineReference;

        String personalizationJob = ((Element) itemNode).getAttribute(kItemPersonalizationJob);

        if (personalizationJob != null && personalizationJob.length() > 0) {
            item.addTag(kItemPersonalizationJobTagName, personalizationJob);
        }

        String personalizationLocation = ((Element) itemNode).getAttribute(kItemPersonalizationLocation);

        if (personalizationLocation != null && personalizationLocation.length() > 0) {
            item.addTag(kItemPersonalizationLocationTagName, personalizationLocation);
        }

        String personalizationTemplate = ((Element) itemNode).getAttribute(kItemPersonalizationTemplate);

        if (personalizationTemplate != null && personalizationTemplate.length() > 0) {
            item.addTag(kItemPersonalizationTemplateTagName, personalizationTemplate);
        }

        if (forceBackorderQuantity) {
            item.quantity_backordered = qtyBackorder;
            item.quantity_request = item.quantity_request - item.quantity_backordered;
            item.force_backorder_quantity = true;

            int currentCount = OrderUtilities.getAvailableInventory(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());

            if (LineItem.getRequiredItemsForInventoryID(Integer.valueOf(item.inventory_fkey)).size() > 0) {
                currentCount = OrderUtilities.getAvailableKitInventory(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());
            }

            if (currentCount < item.quantity_request) {
                throw new APIContentException("Cannot accept backorder quantity of " + item.quantity_backordered + " for SKU " + part + ": Available count = " + currentCount);
            }

        }
        if (doNotShipAlone) {
            item.insertedItem = true;
        }

    }

    public static void main
            (String[] args) throws Exception {

        String test = "123-123-12345.";
        log.debug(test.replaceAll("[\\D]", ""));
    }


}
