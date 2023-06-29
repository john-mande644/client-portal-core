package com.owd.jobs.jobobjects.commercehub

import com.owd.LogableException
import com.owd.core.OWDUtilities
import com.owd.core.TagUtilities
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.Inventory
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.FacilitiesManager
import com.owd.core.managers.InventoryManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.hibernate.generated.OwdReceive
import com.owd.jobs.SftpConnector
import com.owd.jobs.jobobjects.commercehub.CommerceHubSftpClient
import com.owd.jobs.jobobjects.walmart.WalmartDsvXmlFileFormatter
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.nio.file.Files
import java.nio.file.Paths
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat

/**
 * Created by stewartbuskirk1 on 9/22/14.
 */
class CommerceHubApi {

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


    static Map<String, String> inboundShipMap;

    private Map<String, String> commerceHubItemToOwdSkuMap = null;

    private Map<String, String> getCommerceHubItemToOwdSkuMap() {
        if(commerceHubItemToOwdSkuMap==null) {
            commerceHubItemToOwdSkuMap = new HashMap<String, String>()
            ResultSet rs = HibernateSession.getResultSet("select vendorref,owdsku from jcpenney_itemreference" +
                    " join owd_inventory i on i.inventory_num=owdsku and client_fkey="+getClientId()+" and client_fkey=jcp_client_fkey ");
            while (rs.next()) {
                commerceHubItemToOwdSkuMap.put(rs.getString(1), rs.getString(2));
            }
            rs.close();
        }
        return commerceHubItemToOwdSkuMap
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
        inboundShipMap.put("FXSP", "FedEx SmartPost");
        inboundShipMap.put("LTL", "LTL (service unspecified)");
        inboundShipMap.put("HJCS", "LTL (service unspecified)");
        inboundShipMap.put("PBEQ", "LTL (service unspecified)");
        inboundShipMap.put("UPSN_SE", "UPS 2nd Day Air");
        inboundShipMap.put("UB", "UPS 2nd Day Air");
        inboundShipMap.put("UPSN_SC", "UPS 2nd Day Air");
        inboundShipMap.put("UPSN_3D", "UPS 3 Day Select");
        inboundShipMap.put("UPS3", "UPS 3 Day Select");
        inboundShipMap.put("UPSN_CG", "UPS Ground");
        inboundShipMap.put("UNSP_CG", "UPS Ground");
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
    }

    public static void main(String[] args) {
     //   System.setProperty("com.owd.environment","test");
      //  CommerceHubApi s = new CommerceHubApi("gildanusa.sftp.commercehub.com", "gildanusa", "Bell!Improve\$Already5","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",471);
       // String poData = new String(Files.readAllBytes(Paths.get((new File("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\commercehub\\testdata\\307171494.neworders")).getPath())));
       //System.out.println(poData);
       // s.importOrderBatchFile(poData);


        // s.resendFunctionalAcknowledgment(1)

       // println s.pushInventoryFile();
        // s.orderShipped(new OrderStatus("7987121"),'300568280')

       CommerceHubApi api = new CommerceHubApi("gildanusa.sftp.commercehub.com", "gildanusa", "Bell!Improve\$Already5","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",471);

        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.sftpUser, api.sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED)
        OrderStatus os = new OrderStatus("13955157");
        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(api.getClientId())
        System.out.println(confirmFileSource.fileData);


/*



        api.confirmOrderShipment(new OrderStatus(10179468 + ""));
        api.confirmOrderShipment(new OrderStatus(10179469 + ""));
        api.confirmOrderShipment(new OrderStatus(10179471 + ""));
        api.confirmOrderShipment(new OrderStatus(10179472 + ""));
        api.confirmOrderShipment(new OrderStatus(10179473 + ""));*/
    }

    public void retrieveOrderFiles() {


        try {

            Map<String, String> orders = getOrderFileDataMap();
            for (String file : orders.keySet()) {
                log.debug(file);
                String orderData = orders.get(file)

                log.debug(orderData);
                // s.importOrderFile(orders.get(file));
                def commercehubOrder = new XmlSlurper().parseText(orderData);

                String batchRef = commercehubOrder.'@batchNumber'
                println 'Batch:' + batchRef

                println getClientId()
                PreparedStatement ps = HibernateSession.getPreparedStatement("insert into walmart_edi_incoming (source,data,status,batchnum) VALUES (?,?,?,?)")
                ps.setString(1, sFtpRemoteFolder + ":" + getClientId())
                ps.setString(2, orders.get(file))
                ps.setInt(3, 0)
                ps.setString(4, sFtpRemoteFolder + ":"+batchRef)
                int rowsAffected = ps.executeUpdate()
                HibUtils.commit(HibernateSession.currentSession())




                if (rowsAffected == 1) {
                    HibernateSession.closePreparedStatement()
                    String fxnalAckData = ""//getFunctionalAcknowledgment(orderData)
                   // commerceHubRemoteFtp.writeOrderAck(batchRef, fxnalAckData)
                    ps = HibernateSession.getPreparedStatement("update walmart_edi_incoming set faAckData=?, status=1 where batchnum=?")
                    ps.setString(1, fxnalAckData)
                    ps.setString(2, sFtpRemoteFolder + ":"+batchRef)
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


    public CommerceHubApi(String host, String user, String pass, String remoteFolder, String vendor, String merchant, int clientAccountId) {
        sftpHost = host
        sftpUser = user
        sftpPass = pass
        sFtpRemoteFolder = remoteFolder
        clientId = clientAccountId
        vendorName = vendor
        merchantName = merchant
        commerceHubRemoteFtp =  new SftpConnector(sftpHost, sftpUser, sftpPass, remoteFolder);

    }

    public Map<String, String> getOrderFileDataMap() {
        Map<String, String> orders = new TreeMap<String, String>()

        commerceHubRemoteFtp.setRemotePath("/outgoing/orders/"+sFtpRemoteFolder+"/")

        List<String> neworders = commerceHubRemoteFtp.getFileNames("/outgoing/orders/"+sFtpRemoteFolder+"/")
        for (String file : neworders) {
            orders.put(file, commerceHubRemoteFtp.getFileData(file).toString())
        }

        return orders

    }

    public void deleteOrderFile(String filename) throws Exception {
        commerceHubRemoteFtp.setRemotePath("/outgoing/orders/"+sFtpRemoteFolder+"/")
        commerceHubRemoteFtp.deleteFile(filename)
    }


    public void processOrderFiles() {

        Client client = Client.getClientForID("" + clientId);
        ClientPolicy policy = client.getClientPolicy();


        ResultSet rs = HibernateSession.getResultSet("select id, data from walmart_edi_incoming where status=1 and source='"+sFtpRemoteFolder + ":" + getClientId() + "';")

        Map<Integer,String> batchMap = new HashMap<Integer,String>();

        while (rs.next()) {
             batchMap.put(rs.getInt(1),rs.getString(2))
        }

        rs.close()

        for(Integer recid:batchMap.keySet()) {

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
            // println XmlUtil.serialize(orderSrc)
            importOrderNode(orderSrc, batchRef, false)
        }
    }

    private int importOrderNode(orderSrc, String batchRef, boolean testing) {
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
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.group_name="jcpenney"

            if(order.po_num.contains("_")) {
                comments = order.po_num.substring(0,order.po_num.indexOf("_"))
            }

            //check for Direct to Store orders D2S
            String merchendiceTypecode = orderSrc.poHdrData.merchandiseTypeCode?.text();

            if(merchendiceTypecode.equals("D2S")){

                order.addTag(TagUtilities.kVendorComplianceIDReference,"3");

            }




            order.template = "jcp"

            String shiptoId = orderSrc.shipTo.@personPlaceID
            println 'stid:' + shiptoId
            def shipto = orderSrc.depthFirst().find {
                it.name() == 'personPlace' && it.@personPlaceID == shiptoId
            }
            String billtoId = orderSrc.customer.@personPlaceID
            println 'billid:' + billtoId
            def billto = orderSrc.depthFirst().find {
                it.name() == 'personPlace' && it.@personPlaceID == billtoId
            }

            println shipto

            println 'shipname=' + shipto.name1.text()
            order.getShippingContact().setName(shipto.name1.text())
            order.getShippingContact().setPhone(shipto.dayPhone.text())
            order.getShippingAddress().setCompany_name('')
            order.getShippingAddress().setAddress_one(shipto.address1.text())
            order.getShippingAddress().setAddress_two(shipto.address2.text())
            order.getShippingAddress().setCity(shipto.city.text())
            order.getShippingAddress().setState(shipto.state.text())
            order.getShippingAddress().setZip(shipto.postalCode.text())
            order.getShippingAddress().setCountry(shipto.country.text())

            println order.getShippingAddress()
            order.getBillingContact().setName(billto.name1.text())
            order.getBillingContact().setPhone(billto.dayPhone.text())
            order.getBillingAddress().setCompany_name('')
            order.getBillingAddress().setAddress_one(billto.address1.text())
            order.getBillingAddress().setAddress_two(billto.address2.text())
            order.getBillingAddress().setCity(billto.city.text())
            order.getBillingAddress().setState(billto.state.text())
            order.getBillingAddress().setZip(billto.postal_code.text())
            order.getBillingAddress().setCountry(billto.country.text())

            order.is_paid = 1

            order.discount = 0.00f   //Float.parseFloat('0' + it.'total_discounts'.text())
            println 'Shipmethod:' + orderSrc.shippingCode.text().trim()
            String shipmethodw = orderSrc.shippingCode.text().trim()
            shipmethodw = shipmethodw.replaceAll("_R5", "")

            String shipmethod = inboundShipMap.get(shipmethodw)

            if (shipmethod.startsWith("UPS") && upsAcct != null) {
                order.getShippingInfo().setShipOptions(shipmethod, "Third Party Billing", upsAcct)

            } else if (shipmethod.startsWith("Fedex") && fedexAcct != null) {
throw new Exception("Non-UPS ship method for JCPenney")

            }
            order.getShippingInfo().comments = shipto.personPlaceData?.ReceiptID?.text()
            order.getShippingInfo().whse_notes = batchRef

            String locCode = FacilitiesManager.getLocationCodeForClient(getClientId())
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

                comments = comments+ OWDUtilities.padLeft(lineRef,3,'0' as char);

                if (item.poLineData?.giftMessage.size() > 0) {
                    String message = item.poLineData.giftMessage

                    title = title + "\r\n" + message.replaceAll("[\\.]", "\r\n")
                }

                log.debug("SKU:" + sku)
                if (!(LineItem.SKUExists("" + clientId, sku)) && getClientId() == 112) {
                    Inventory i = InventoryManager.createInventoryItemForClient("" + clientId, sku, item.description.text(), "0.00", false, "", "", "");
                    InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(i.inventory_id, clientId, 999, OWDUtilities.getSQLDateTimeForToday(), sku + ':' + OWDUtilities.getSQLDateTimeForToday(), locCode, HibernateSession.currentSession())
                    HibUtils.commit(HibernateSession.currentSession())
                    //    public static Inventory createInventoryItemForClient(String clientID, String sku, String title, String unitPrice,boolean isVirtual, String color, String size, String notes ) throws Exception
                    //        public static void postInventoryAdjustmentAsNewAbsoluteValue(int inventoryID, int clientID, int newCount, String referenceDateStr, String transactionRef, Session session) throws Exception

                }  else {
                    if (getCommerceHubItemToOwdSkuMap().containsKey(sku)) {
                        sku = getCommerceHubItemToOwdSkuMap().get(sku)
                    } else {
                        throw new Exception("walmart item id " + sku + " not recognized")
                    }
                }

                if (item.lineTax.size() > 0) {
                    order.total_tax_cost += OWDUtilities.roundFloat(Float.parseFloat("" + item.lineTax.text()))
                    itemcolor = item.lineTax.text()

                }
                if (item.lineShipping.size() > 0) {
                    order.total_shipping_cost += OWDUtilities.roundFloat(Float.parseFloat("" + item.lineShipping.text()))
                    itemsize = item.lineShipping.text()
                }
                order.addLineItem(sku, ""+item.qtyOrdered.text() + "", "0.00", "0.00", title, itemcolor, itemsize, upc)
                ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = lineRef
                ((LineItem) (order.skuList.get(order.skuList.size() - 1))).long_desc = item.merchantSKU


            }

            order.getShippingInfo().comments = comments;

            String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);

            if (!reference) {
                log.debug("order import failed!")
                log.debug(order.last_error)
                throw new Exception("Unable to save new CommerceHub order : " + order.last_error);
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

    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

       // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    public String confirmOrderShipment(OrderStatus os) {

        CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED)

        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())

        return sendFileToCommerceHub(confirmFileSource)

    }

    private String sendFileToCommerceHub(CommerceHubXmlFileFormatter fileSource)
    {
        commerceHubRemoteFtp.setRemotePath(fileSource.getRemotePath())
        log.debug fileSource.getFileName()
        log.debug fileSource.getFileData()
        log.debug fileSource.getRemotePath()
        commerceHubRemoteFtp.putFileData(fileSource.getFileName(), fileSource.getFileData().getBytes())
        return fileSource.getFileData()
    }




}