package com.owd.jobs.jobobjects.commercehub

import com.owd.LogableException
import com.owd.core.OWDUtilities
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.business.order.OrderUtilities
import com.owd.core.business.order.beans.lineItemExemptions
import com.owd.core.managers.FacilitiesManager
import com.owd.core.managers.InventoryManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdInventory
import com.owd.jobs.SftpConnector
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase
import groovy.xml.XmlUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.sql.PreparedStatement
import java.sql.ResultSet
/**
 * Created by dannynickels on 6/19/19.
 */
class CommerceHubApiBase {
    private final static Logger log = LogManager.getLogger();

    private String sftpHost = "";
    private String sftpUser = "";
    private String sftpPass = "";
    private String sFtpRemoteFolder = "";
    private SftpConnector commerceHubRemoteFtp = null;
    private int clientId = 112;

    private String vendorName = ""
    private String merchantName = ""

    private String upsAcct = "2E0732";
    private String fedexAcct = "";
    private String groupName = "";
    private String packingSlipTemplate = "";
    private boolean useUPCLookup = false;
    private boolean sendRejectOrder = false;
    private boolean useVendorSku = false;
    private boolean trimUPC = false;
    private boolean sendAcknowledgement = false;

    private BigDecimal InvoiceTaxPercent = BigDecimal.ZERO;
    private int InvoiceDiscDaysDue = 0;
    private int InvoiceNetDaysDue = 0;
    private BigDecimal InvoiceDiscPercent = BigDecimal.ZERO;

    private String backOrderRule = OrderXMLDoc.kRejectBackOrder;

    static Map<String, String> inboundShipMap;
    static List<String> signatureRequiredShipMethodList;

    private Map<String, String> commerceHubItemToOwdSkuMap = null;

    def Map<String, String> getCommerceHubItemToOwdSkuMap() {

        if (commerceHubItemToOwdSkuMap == null) {
            if (useUPCLookup) {
                commerceHubItemToOwdSkuMap = new HashMap<String, String>()
                ResultSet rs = HibernateSession.getResultSet("select upc_code,inventory_num from owd_inventory" +
                        " where upc_code <> '' and client_fkey=" + getClientId());
                while (rs.next()) {
                    commerceHubItemToOwdSkuMap.put(rs.getString(1), rs.getString(2));
                }
                rs.close();
            } else {

                commerceHubItemToOwdSkuMap = new HashMap<String, String>()
                ResultSet rs = HibernateSession.getResultSet("select vendorref,owdsku from jcpenney_itemreference" +
                        " join owd_inventory i on i.inventory_num=owdsku and client_fkey=" + getClientId() + " and client_fkey=jcp_client_fkey ");
                while (rs.next()) {
                    commerceHubItemToOwdSkuMap.put(rs.getString(1), rs.getString(2));
                }
                rs.close();
            }
        }
        return commerceHubItemToOwdSkuMap
    }

    static {
        signatureRequiredShipMethodList = new ArrayList<String>()
        signatureRequiredShipMethodList.add("UPSET_SE");
    }

    static {

        inboundShipMap = new TreeMap<String, String>()

        inboundShipMap.put("FEDX_SE", "FedEx 2Day");
        inboundShipMap.put("FDEG_SE", "FedEx 2Day");
        inboundShipMap.put("FDE_SC", "FedEx 2Day");
        inboundShipMap.put("FX2D", "FedEx 2Day");
        inboundShipMap.put("FEDX_3D", "FedEx Express Saver");
        inboundShipMap.put("FEDX_CG", "FedEx Ground");
        inboundShipMap.put("FDEG_CG", "FedEx Ground");
        inboundShipMap.put("FDEG", "FedEx Ground");
        inboundShipMap.put("FEDEXG", "FedEx Ground");
        inboundShipMap.put("FDXG", "FedEx Ground");
        inboundShipMap.put("FEDX_09", "FedEx Home Delivery");
        inboundShipMap.put("FEDX_ND", "FedEx Standard Overnight");
        inboundShipMap.put("FDEG_ND", "FedEx Standard Overnight");
        inboundShipMap.put("FXND", "FedEx Standard Overnight");
        inboundShipMap.put("FDEX", "FedEx Standard Overnight");
        inboundShipMap.put("FEDX_NM", "FedEx Priority Overnight");
        inboundShipMap.put("FXSP", "FedEx SmartPost Parcel Select");
        inboundShipMap.put("LTL", "LTL (service unspecified)");
        inboundShipMap.put("HJCS", "LTL (service unspecified)");
        inboundShipMap.put("PBEQ", "LTL (service unspecified)");
        inboundShipMap.put("UPSN_SE", "UPS 2nd Day Air");
        inboundShipMap.put("UB", "UPS 2nd Day Air");
        inboundShipMap.put("UPSN_SC", "UPS 2nd Day Air");

        inboundShipMap.put("UPSN_3D", "UPS 3 Day Select");
        inboundShipMap.put("UPS3", "UPS 3 Day Select");
        inboundShipMap.put("UG", "UPS Ground");
        inboundShipMap.put("UPSG", "UPS Ground");
        inboundShipMap.put("UPSN_ND", "UPS Next Day Air");
        inboundShipMap.put("UPND", "UPS Next Day Air");
        inboundShipMap.put("UR", "UPS Next Day Air");
        inboundShipMap.put("UPS1", "UPS Next Day Air");
        inboundShipMap.put("UPSN_NM", "UPS Next Day Air");
        inboundShipMap.put("UPSN_PM", "UPS Next Day Air Saver");
        inboundShipMap.put("USPS_PB", "USPS Priority Mail");
        inboundShipMap.put("USPS_ST", "USPS Parcel Select Nonpresort");

        // Sean 2020/03/16 case 768424
        inboundShipMap.put("UPSN_ST_MW", "UPS SurePost Less than 1 lb");
        inboundShipMap.put("UPSN_ST", "UPS SurePost 1 lb or Greater");

        // Matthew 2021/08/31 case 1022639
        inboundShipMap.put("UPSET_SE", "UPS 2nd Day Air")
    }

    // Constructor
    public CommerceHubApiBase() {

    }

    public static void main(String[] args) {
        CommerceHubApiBase api = new CommerceHubApiBase();
    }

    public void retrieveOrderFiles() {
        try {
            Map<String, String> orders = getOrderFileDataMap();

            for (String file : orders.keySet()) {
                log.debug(file);
                String orderData = orders.get(file)

                log.debug(orderData);

                def commercehubOrder = new XmlSlurper().parseText(orderData);

                String batchRef = commercehubOrder.'@batchNumber'
                println 'Batch:' + batchRef

                println getClientId()
                PreparedStatement ps = HibernateSession.getPreparedStatement("insert into walmart_edi_incoming (source,data,status,batchnum) VALUES (?,?,?,?)")
                ps.setString(1, sFtpRemoteFolder + ":" + getClientId())
                ps.setString(2, orders.get(file))
                ps.setInt(3, 0)
                ps.setString(4, sFtpRemoteFolder + ":" + batchRef)
                int rowsAffected = ps.executeUpdate()
                HibUtils.commit(HibernateSession.currentSession())

                if (rowsAffected == 1) {
                    HibernateSession.closePreparedStatement()
                    String fxnalAckData = ""
                    ps = HibernateSession.getPreparedStatement("update walmart_edi_incoming set faAckData=?, status=1 where batchnum=?")
                    ps.setString(1, fxnalAckData)
                    ps.setString(2, sFtpRemoteFolder + ":" + batchRef)
                    rowsAffected = ps.executeUpdate()
                    HibUtils.commit(HibernateSession.currentSession())

                    deleteOrderFile(file)
                } else {
                    throw new Exception("Error importing file " + file)
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            Exception exl = new LogableException(ex, "Error importing file(s) from commercehub:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), "Walmart.com", LogableException.errorTypes.ORDER_IMPORT)
            throw ex
        } finally {
            HibernateSession.closePreparedStatement()
        }
    }

    int getClientId() {
        return clientId
    }

    String getUpsAcct() {
        return upsAcct
    }

    void setUpsAcct(String upsAcct) {
        this.upsAcct = upsAcct
    }

    public configure(String host, String user, String pass, String remoteFolder, String vendor, String merchant, int clientAccountId) {
        sftpHost = host
        sftpUser = user
        sftpPass = pass
        sFtpRemoteFolder = remoteFolder
        clientId = clientAccountId
        vendorName = vendor
        merchantName = merchant
        commerceHubRemoteFtp = new SftpConnector(sftpHost, sftpUser, sftpPass, remoteFolder);
    }

    public Map<String, String> getOrderFileDataMap() {
        Map<String, String> orders = new TreeMap<String, String>()

        commerceHubRemoteFtp.setRemotePath("/outgoing/orders/" + sFtpRemoteFolder + "/")

        List<String> neworders = commerceHubRemoteFtp.getFileNames("/outgoing/orders/" + sFtpRemoteFolder + "/")
        for (String file : neworders) {
            orders.put(file, commerceHubRemoteFtp.getFileData(file).toString())
        }

        return orders
    }

    public void deleteOrderFile(String filename) throws Exception {
        commerceHubRemoteFtp.setRemotePath("/outgoing/orders/" + sFtpRemoteFolder + "/")
        commerceHubRemoteFtp.deleteFile(filename)
    }

    public void processOrderFiles() {
        Client client = Client.getClientForID("" + clientId);
        ClientPolicy policy = client.getClientPolicy();
        ResultSet rs = HibernateSession.getResultSet("select id, data from walmart_edi_incoming where status=1 and source='" + sFtpRemoteFolder + ":" + getClientId() + "';")
        Map<Integer, String> batchMap = new HashMap<Integer, String>();

        while (rs.next()) {
            batchMap.put(rs.getInt(1), rs.getString(2))
        }

        rs.close()

        for (Integer recid : batchMap.keySet()) {
            String orderFileData = batchMap.get(recid)

            importOrderBatchFile(orderFileData)

            PreparedStatement ps = HibernateSession.getPreparedStatement("update walmart_edi_incoming set status=2 where id=?")
            ps.setInt(1, recid)
            int rowsAffected = ps.executeUpdate()
            HibUtils.commit(HibernateSession.currentSession())

            ps.close()
        }
    }

    public void importOrderBatchFile(String orderFileData) {
        def walmartOrder = new XmlSlurper().parseText(orderFileData);

        String batchRef = walmartOrder.'@batchNumber'
        println 'Batch:' + batchRef
        walmartOrder.hubOrder.each { orderSrc ->
            importOrderNode(orderSrc, batchRef, false)
        }
    }

    public int importOrderNode(orderSrc, String batchRef, boolean testing) {
        try {

            println 'transid:' + orderSrc.'@transactionID'
            println 'orderid:' + orderSrc.orderId

            String comments = ""
            Order order = new Order("" + getClientId())

            order.testing = testing

            order.addTag("COMMERCEHUB_BATCHREF", batchRef)
            order.addTag("COMMERCEHUB_CONTROL", batchRef)
            order.addTag("COMMERCEHUB_PO_XML", XmlUtil.serialize(orderSrc))
            order.order_refnum = sFtpRemoteFolder + ':' + orderSrc.poNumber   // order and shipment id
            order.po_num = orderSrc.poNumber
            order.order_type = sFtpRemoteFolder.toUpperCase()
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1
            order.backorder_rule = getBackOrderRule();
            order.group_name = groupName;

            if (order.po_num.contains("_")) {
                comments = order.po_num.substring(0, order.po_num.indexOf("_"))
            }

            //check for Direct to Store orders D2S
            String merchendiceTypecode = orderSrc.poHdrData.merchandiseTypeCode?.text();

            //TODO add vendor complaince to orders as needed
            /* if(merchendiceTypecode.equals("D2S")){

                 order.addTag(TagUtilities.kVendorComplianceIDReference,"3");

             }*/

            if (packingSlipTemplate.length() > 0) {
                order.template = packingSlipTemplate
            }
            String template = getPackingSlipFromTemplateFields(orderSrc)
            if (template.length() > 0) {
                order.template = template
            }

            String shiptoId = orderSrc.shipTo.@personPlaceID
            println 'stid:' + shiptoId
            def shipto = orderSrc.depthFirst().find {
                it.name() == 'personPlace' && it.@personPlaceID == shiptoId
            }

            println shipto
            println 'shipname=' + shipto.name1.text()

            if (shipto.name2.size() > 0) {
                order.getShippingContact().setName(shipto.name1.text() + " " + shipto.name2.text())
            } else {
                order.getShippingContact().setName(shipto.name1.text())
            }


            order.getShippingContact().setPhone(shipto.dayPhone.text())
            if (!shipto.personPlaceData.companyName.isEmpty()) {
                order.getShippingAddress().setCompany_name(shipto.personPlaceData.companyName.text())
            } else {
                order.getShippingAddress().setCompany_name('')
            }

            order.getShippingAddress().setAddress_one(shipto.address1.text())
            order.getShippingAddress().setAddress_two(shipto.address2.text())
            order.getShippingAddress().setCity(shipto.city.text())
            order.getShippingAddress().setState(shipto.state.text())
            order.getShippingAddress().setZip(shipto.postalCode.text())
            order.getShippingAddress().setCountry(shipto.country.text())

            def shiptoData = shipto.depthFirst().find {
                it.name() == 'personPlaceData'
            }

            if (shiptoData != null && shiptoData.attnLine != "") {
                order.getShippingAddress().setCompany_name(shiptoData.attnLine.text())
            }

            println order.getShippingAddress()

            loadBillingInfo(order, orderSrc)

            order.is_paid = 1
            order.discount = 0.00f   //Float.parseFloat('0' + it.'total_discounts'.text())
            println 'Shipmethod:' + orderSrc.shippingCode.text().trim()
            String shipmethodw = orderSrc.shippingCode.text().trim()
            if (shipmethodw.length() == 0) {
                shipmethodw = orderSrc.lineItem[0].shippingCode.text().trim()
            }
            shipmethodw = shipmethodw.replaceAll("_R5", "")

            String shipMethod =  getShipMethod(shipmethodw);
            order.setShippingMethodName(shipMethod)
            loadThirdPartyBillingInfo(order)
            order.setSignatureRequired(signatureRequiredShipMethodList.contains(shipmethodw))

            order.getShippingInfo().comments = shipto.personPlaceData?.ReceiptID?.text()
            order.getShippingInfo().whse_notes = batchRef

            String locCode = getFacilityCode(getClientId());
            order.setFacilityCode(locCode)
            order.setFacilityPolicy(locCode)
            orderSrc.lineItem.each() { item ->

                println "LINE SKU: " + item.merchantSKU
                println "LINE QTY: " + item.qtyOrdered

                String sku = item.merchantSKU
                String lineRef = item.merchantLineNumber
                String itemcolor = ""
                String itemsize = ""
                String upc = item.UPC
                String title = item.description

                comments = comments + OWDUtilities.padLeft(lineRef, 3, '0' as char);

                if (item.poLineData?.giftMessage.size() > 0) {
                    String message = item.poLineData.giftMessage

                    title = title + "\r\n" + message.replaceAll("[\\.]", "\r\n")
                }

                log.debug("SKU:" + sku)
                String searchSku = sku;

                if (useUPCLookup) {
                    sku = item.UPC;
                    if (trimUPC && sku.length() == 14 && sku.startsWith("00")) {
                        sku = sku.substring(2, sku.length());
                    }
                    //Walmart does not send check Digit and adds 00 to the front
                    if (trimUPC && sku.length() == 13 && sku.startsWith("00")) {
                        sku = addCheckDigit(sku.substring(2, sku.length()));
                    }

                    log.debug("UPC: " + sku)
                    searchSku = sku;
                    if (getCommerceHubItemToOwdSkuMap().containsKey(sku)) {
                        sku = getCommerceHubItemToOwdSkuMap().get(sku)
                    } else {
                        throw new Exception(sFtpRemoteFolder + "item id " + sku + " not recognized")
                    }
                } else {
                    if (useVendorSku) {
                        sku = item.vendorSKU
                    } else {
                        if (getCommerceHubItemToOwdSkuMap().containsKey(sku)) {
                            sku = getCommerceHubItemToOwdSkuMap().get(sku)
                        } else {
                            throw new Exception(sFtpRemoteFolder + "item id " + sku + " not recognized")
                        }
                    }
                }

                OwdInventory owdInv = InventoryManager.getOwdInventoryForClientAndSku(order.clientID, sku)


                int stock = order.getStockLevelForInventoryId(owdInv.getInventoryId(), order.facilityCode)

                int qtyOrdered = Integer.parseInt(item.qtyOrdered.text())
                int qtyToPost = 0;
                //Only do Exemptions when they vendor does not allow backorders
                boolean doExemptions = true;
                if (order.backorder_rule.equals(OrderXMLDoc.kPartialShip) || order.backorder_rule.equals(OrderXMLDoc.kBackOrderAll)) {
                    doExemptions = false;
                }
                if ((stock == 0) && doExemptions) {
                    println "WE have 0 in stock exempt the whole line"
                    def lie = new lineItemExemptions()

                    lie.setVendor_sku(searchSku)
                    lie.setInventory_num(sku);
                    lie.setExemptionCode("quantity");
                    lie.setExemptionValue("out of stock");
                    lie.setQty(qtyOrdered + "");
                    lie.setMerchant_line_number(lineRef)

                    order.lineExemptions.add(lie);

                } else if ((stock < qtyOrdered) && doExemptions) {
                    def lie = new lineItemExemptions()

                    lie.setVendor_sku(searchSku)
                    lie.setInventory_num(sku);
                    lie.setExemptionCode("quantity");
                    lie.setExemptionValue("short ship");
                    lie.setQty((qtyOrdered - stock) + "")
                    lie.setMerchant_line_number(lineRef)

                    order.lineExemptions.add(lie);
                    qtyToPost = stock;

                } else {
                    qtyToPost = qtyOrdered;
                }


                if (qtyToPost > 0) {
                    if (item.lineTax.size() > 0) {
                        order.total_tax_cost += OWDUtilities.roundFloat(Float.parseFloat("" + item.lineTax.text()))
                        itemcolor = item.lineTax.text()

                    }
                    if (item.lineShipping.size() > 0) {
                        order.total_shipping_cost += OWDUtilities.roundFloat(Float.parseFloat("" + item.lineShipping.text()))
                        itemsize = item.lineShipping.text()
                    }
                    float itemUnitCost = OWDUtilities.roundFloat(Float.parseFloat(item.unitCost.text()))

                    order.addLineItem(sku, "" + qtyToPost + "", itemUnitCost + "", "0.00", title, itemcolor, itemsize, upc)
                    ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = lineRef
                    ((LineItem) (order.skuList.get(order.skuList.size() - 1))).long_desc = item.merchantSKU
                }


            }

            if (comments.length() == 0) {
                order.getShippingInfo().comments = comments;
            }

            saveOrderTagsAsNeeded(order, orderSrc);
            doFinalStuffBeforeSavingOrder(order, orderSrc)


            String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);

            if (!reference) {
                log.debug("order import failed!")
                log.debug(order.last_error)
                log.debug(order.orderID);
                // OrderStatus status = new OrderStatus(order);
                if (order.last_error.contains("No valid line items in order") && sendRejectOrder) {
                    rejectAndRecordOrder(orderSrc, batchRef);
                } else {
                    throw new Exception("Unable to save new CommerceHub order : " + order.last_error);
                }
            } else {
                if (sendAcknowledgement) {
                    System.out.println("Sending acknowledgement");
                    OrderStatus os = new OrderStatus(order.orderID + "");
                    System.out.println(acknowledgeOrder(os, batchRef));
                }
            }

            return Integer.parseInt(order.orderID)
        } catch (Exception ex) {
            Exception exl = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), sFtpRemoteFolder, LogableException.errorTypes.ORDER_IMPORT)
            ex.printStackTrace()
        } finally {
            HibernateSession.closePreparedStatement()
        }

        return 0
    }

    protected String getFacilityCode(int clientId) {
        return FacilitiesManager.getLocationCodeForClient(clientId);
    }

    public void loadBillingInfo(Order order, orderSrc) {
        String billtoId = orderSrc.customer.@personPlaceID
        println 'billid:' + billtoId
        if (billtoId.length() == 0) {
            billtoId = orderSrc.billTo.@personPlaceID
        }
        //If no billing ID then set billing to shipping
        if (billtoId.length() == 0) {

            order.getBillingContact().setName(order.getShippingContact().getName());
            order.getBillingContact().setPhone(order.getShippingContact().getPhone());
            order.getBillingAddress().setCompany_name(order.getShippingAddress().getCompany_name())
            order.getBillingAddress().setAddress_one(order.getShippingAddress().getAddress_one())
            order.getBillingAddress().setAddress_two(order.getShippingAddress().getAddress_two())
            order.getBillingAddress().setCity(order.getShippingAddress().getCity())
            order.getBillingAddress().setState(order.getShippingAddress().getState())
            order.getBillingAddress().setZip(order.getShippingAddress().getZip())
            order.getBillingAddress().setCountry(order.getShippingAddress().getCountry())

            return
        }
        def billto = orderSrc.depthFirst().find {
            it.name() == 'personPlace' && it.@personPlaceID == billtoId
        }

        if (billto.name2.size() > 0) {
            order.getBillingContact().setName(billto.name1.text() + " " + billto.name2.text())
        } else {
            order.getBillingContact().setName(billto.name1.text())
        }
        order.getBillingContact().setPhone(billto.dayPhone.text())
        if (!billto.personPlaceData.companyName.isEmpty()) {
            order.getBillingAddress().setCompany_name(billto.personPlaceData.companyName.text())
        } else {
            order.getBillingAddress().setCompany_name('')
        }

        order.getBillingAddress().setAddress_one(billto.address1.text())
        order.getBillingAddress().setAddress_two(billto.address2.text())
        order.getBillingAddress().setCity(billto.city.text())
        order.getBillingAddress().setState(billto.state.text())
        order.getBillingAddress().setZip(billto.postalCode.text())
        order.getBillingAddress().setCountry(billto.country.text())

        def billtoData = billto.depthFirst().find {
            it.name() == 'personPlaceData'
        }

        if (billtoData != null && billtoData.attnLine != "") {
            order.getBillingAddress().setCompany_name(billtoData.attnLine.text())
        }
    }

    public String getInventoryFile() {
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        return invFileSource.getFileData();


    }

    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    public String acknowledgeOrder(OrderStatus os, String batchRef) {

        CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_RECEIVED)
        confirmFileSource.setBatchRef(batchRef)
        confirmFileSource.setUseUPCLookup(useUPCLookup);
        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())


        return sendFileToCommerceHub(confirmFileSource)

    }

    public String confirmOrderShipment(OrderStatus os) {

        CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED)

        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())

        return sendFileToCommerceHub(confirmFileSource)

    }

    public String rejectAndRecordOrder(orderSrc, String BatchRef) {
        //todo record data into table
        println(rejectOrder(orderSrc, BatchRef))
    }

    public String rejectOrder(orderSrc, String BatchRef) {
        CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
        //OrderStatus os = new OrderStatus(orderId+"");
        confirmFileSource.setBatchRef(BatchRef);
        confirmFileSource.setOrderSrc(orderSrc);
        confirmFileSource.setClientId(clientId);
        // System.out.println(confirmFileSource.getFileData());
        return sendFileToCommerceHub(confirmFileSource);

    }

    public String pushReturnFile(orderSrc, OrderStatus os) {
        CommerceHubXmlFileFormatter returnFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.RETURN)

        returnFileSource.setCurrentOrderStatus(os)
        returnFileSource.setOrderSrc(orderSrc);
        returnFileSource.setClientId(clientId);

        return sendFileToCommerceHub(returnFileSource);
    }

    protected String sendFileToCommerceHub(CommerceHubXmlFileFormatter fileSource) {
        commerceHubRemoteFtp.setRemotePath(fileSource.getRemotePath())
        log.debug fileSource.getFileName()
        log.debug fileSource.getFileData()
        log.debug fileSource.getRemotePath()
        commerceHubRemoteFtp.putFileData(fileSource.getFileName(), fileSource.getFileData().getBytes())
        return fileSource.getFileData()
    }

    protected String sendFileToCommerceHub(LowesCommerceHubXmlFileFormatter fileSource) {
        commerceHubRemoteFtp.setRemotePath(fileSource.getRemotePath())
        log.debug fileSource.getFileName()
        log.debug fileSource.getFileData()
        log.debug fileSource.getRemotePath()
        commerceHubRemoteFtp.putFileData(fileSource.getFileName(), fileSource.getFileData().getBytes())
        return fileSource.getFileData()
    }

    public String getPackingSlipFromTemplateFields(def orderSrc) {

        return "";
    }

    public String sendInvoiceToCommerceHub(int orderId, CommerceHubInvoiceBase invoice) {

        CommerceHubXmlFileFormatter fileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVOICE);

        try {
            invoice.setTaxPercent(InvoiceTaxPercent);
            invoice.setDiscDaysDue(InvoiceDiscDaysDue);
            invoice.setNetDaysDue(InvoiceNetDaysDue);
            invoice.setDiscPercent(invoiceDiscPercent);
            invoice.loadInvoiceFromOrderId(orderId);


            String s = invoice.getInvoiceXMLData();
            commerceHubRemoteFtp.setRemotePath(fileSource.getRemotePath())
            commerceHubRemoteFtp.putFileData(fileSource.getFileName(), s.getBytes())
            return s;


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    String getGroupName() {
        return groupName
    }

    void setGroupName(String groupName) {
        this.groupName = groupName
    }

    String getPackingSlipTemplate() {
        return packingSlipTemplate
    }

    void setPackingSlipTemplate(String packingSlipTemplate) {
        this.packingSlipTemplate = packingSlipTemplate
    }

    String getFedexAcct() {
        return fedexAcct
    }

    void setFedexAcct(String fedexAcct) {
        this.fedexAcct = fedexAcct
    }

    String getsFtpRemoteFolder() {
        return sFtpRemoteFolder
    }

    String getSftpHost() {
        return sftpHost
    }

    String getSftpUser() {
        return sftpUser
    }

    String getSftpPass() {
        return sftpPass
    }

    SftpConnector getCommerceHubRemoteFtp() {
        return commerceHubRemoteFtp
    }

    String getVendorName() {
        return vendorName
    }

    String getMerchantName() {
        return merchantName
    }

    boolean getSendAcknowledgement() {
        return sendAcknowledgement
    }

    void setSendAcknowledgement(boolean sendAcknowledgement) {
        this.sendAcknowledgement = sendAcknowledgement
    }

    boolean getUseUPCLookup() {
        return useUPCLookup
    }

    void setUseUPCLookup(boolean useUPCLookup) {
        this.useUPCLookup = useUPCLookup
    }

    boolean getTrimUPC() {
        return trimUPC
    }

    void setTrimUPC(boolean trimUPC) {
        this.trimUPC = trimUPC
    }

    boolean getUseVendorSku() {
        return useVendorSku
    }

    void setUseVendorSku(boolean useVendorSku) {
        this.useVendorSku = useVendorSku
    }

    static Map<String, String> getInboundShipMap() {
        return inboundShipMap
    }

    boolean getSendRejectOrder() {
        return sendRejectOrder
    }

    void setSendRejectOrder(boolean sendRejectOrder) {
        this.sendRejectOrder = sendRejectOrder
    }

    String getBackOrderRule() {
        return backOrderRule
    }

    void setBackOrderRule(String backOrderRule) {
        this.backOrderRule = backOrderRule
    }

    BigDecimal getInvoiceTaxPercent() {
        return InvoiceTaxPercent
    }

    void setInvoiceTaxPercent(BigDecimal invoiceTaxPercent) {
        InvoiceTaxPercent = invoiceTaxPercent
    }

    int getInvoiceDiscDaysDue() {
        return InvoiceDiscDaysDue
    }

    void setInvoiceDiscDaysDue(int invoiceDiscDaysDue) {
        InvoiceDiscDaysDue = invoiceDiscDaysDue
    }

    int getInvoiceNetDaysDue() {
        return InvoiceNetDaysDue
    }

    void setInvoiceNetDaysDue(int invoiceNetDaysDue) {
        InvoiceNetDaysDue = invoiceNetDaysDue
    }

    BigDecimal getInvoiceDiscPercent() {
        return InvoiceDiscPercent
    }

    void setInvoiceDiscPercent(BigDecimal invoiceDiscPercent) {
        InvoiceDiscPercent = invoiceDiscPercent
    }

    void loadThirdPartyBillingInfo(Order order) {
        //override in custom class for vendor as needed
    }

    String getShipMethod(String shipMethod) {
        if (inboundShipMap.containsKey(shipMethod)) {
            return inboundShipMap.get(shipMethod);
        }

        return "";
    }

    void saveOrderTagsAsNeeded(Order order, def orderSrc) {
        //override in custom class as needed.
    }

    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
        //override in custom class as needed
    }

    def String addCheckDigit(String userNumber) {

        /*    //get input from user
            Scanner sc = new Scanner(System.in);
            log.debug("Please enter 11 digit UPC");
            String userNumber = sc.next();*/


        int step6;

        char[] chars = userNumber.chars
        int odd = 0
        int even = 0


        for (int pos = 0; pos < (userNumber.length()); pos++) {
            if ((1 + pos) % 2 == 0) {
                //even
                even += Integer.parseInt(String.valueOf(chars[pos]))
            } else {
                //odd
                odd += Integer.parseInt(String.valueOf(chars[pos]))
            }
        }

        //STEP 6:  determine check digit
        step6 = (10 - ((even + (3 * odd)) % 10));
        if (step6 == 10) {
            step6 = 0;
        }
        return userNumber + step6
    }

}