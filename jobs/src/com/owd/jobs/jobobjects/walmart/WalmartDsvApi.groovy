package com.owd.jobs.jobobjects.walmart

import com.owd.LogableException
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.order.*
import com.owd.core.managers.FacilitiesManager
import com.owd.core.managers.InventoryManager
import com.owd.core.managers.ManifestingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.SftpConnector
import com.owd.jobs.jobobjects.networksolutions.PackageType
import com.owd.jobs.jobobjects.sftp.WalmartSftpClient
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat

/**
 * Created by stewartbuskirk1 on 9/22/14.
 */
@groovy.util.logging.Log4j2
class WalmartDsvApi {


    String sftpHost = "";
    String sftpUser = "";
    String sftpPass = "";
    String oldsftpHost = "";
    String oldsftpUser = "";
    String oldsftpPass = "";
    boolean oldSend = false;
    int clientId = 112;   //default to test client. Also auto-creates missing SKUs in test account when importing orders
    String accountId;
    String accountName;
    
    public SftpConnector s = null;

    private String upsAcct = "213W31";   //from Walmart
    private String fedexAcct = "554194346"; //from Walmart

    private Map<String, String> walmartItemToOwdSkuMap = null;

    static Map<String, String> inboundShipMap;
    static Map<String, String> outboundShipMap;


    static {

        inboundShipMap = new TreeMap<String, String>()
        outboundShipMap = new TreeMap<String, String>()


        inboundShipMap.put("01", "USPS Parcel Select Nonpresort");
        inboundShipMap.put("02", "UPS Ground");
        inboundShipMap.put("05", "USPS Priority Mail Express");
        inboundShipMap.put("09", "UPS 2nd Day Air");
        inboundShipMap.put("10", "USPS First-Class Mail");
        inboundShipMap.put("13", "UPS 2nd Day Air");
        inboundShipMap.put("16", "UPS Next Day Air");
        inboundShipMap.put("19", "FedEx Express Saver");
        inboundShipMap.put("20", "FedEx Ground");
        inboundShipMap.put("21", "FedEx Priority Overnight");
        inboundShipMap.put("22", "FedEx 2Day");
        inboundShipMap.put("23", "FedEx Priority Overnight");
        inboundShipMap.put("24", "FedEx Standard Overnight");
        inboundShipMap.put("26", "UPS Next Day Air Saver");
        inboundShipMap.put("29", "UPS 3 Day Select");
        inboundShipMap.put("30", "USPS Priority Mail");
        inboundShipMap.put("31", "USPS Priority Mail");
        inboundShipMap.put("38", "UPS Ground");
        inboundShipMap.put("65", "FedEx SmartPost Parcel Select");
        inboundShipMap.put("66", "FedEx SmartPost Parcel Select");
        inboundShipMap.put("67", "FedEx Home Delivery");
        inboundShipMap.put("79", "UPS Ground");

        outboundShipMap.put("USPS Parcel Select Nonpresort", "01");
        outboundShipMap.put("UPS Ground", "02");
        outboundShipMap.put("USPS Priority Mail Express", "05");
        outboundShipMap.put("UPS 2nd Day Air", "09");
        outboundShipMap.put("USPS First-Class Mail", "10");
        outboundShipMap.put("UPS Next Day Air", "16");
        outboundShipMap.put("FedEx Express Saver", "19");
        outboundShipMap.put("FedEx Ground", "20");
        outboundShipMap.put("FedEx 2Day", "22");
        outboundShipMap.put("FedEx Priority Overnight", "23");
        outboundShipMap.put("FedEx Standard Overnight", "24");
        outboundShipMap.put("UPS Next Day Air Saver", "26");
        outboundShipMap.put("UPS 3 Day Select", "29");
        outboundShipMap.put("USPS Priority Mail", "30");
        outboundShipMap.put("FedEx SmartPost Parcel Select", "65");
        outboundShipMap.put("FedEx SmartPost Standard Mail", "66");
        outboundShipMap.put("FedEx Home Delivery", "67");

    }

    public static void main(String[] args) {


        WalmartDsvApi api = new WalmartDsvApi("ebob.walmart.com", "gildan", "v38wVpaM", "272112", "Gildan")
        api.setClientId(471)
        // s.resendFunctionalAcknowledgment(1)


     //  api.processOrderFiles();
      //  println os.getTagMap().get('WALMARTDSV_PO_XML')
           api.confirmOrderShipment(new OrderStatus(""+10277544));
          api.confirmOrderShipment(new OrderStatus(""+10278003));

    }

    public WalmartDsvApi(String host, String user, String pass, String accountId, String accountName) {
        sftpHost = host
        sftpUser = user
        sftpPass = pass

        this.accountId = accountId
        this.accountName = accountName

        s = new SftpConnector(sftpHost, sftpUser, sftpPass, "","aes128-ctr,aes128-cbc");




    }


    private Map<String, String> getWalmartItemToOwdSkuMap() {
        if(walmartItemToOwdSkuMap==null) {
            walmartItemToOwdSkuMap = new HashMap<String, String>()
            ResultSet rs = HibernateSession.getResultSet("select wmref,owdsku from walmart_itemreference" +
                    " join owd_inventory i on i.inventory_num=owdsku and client_fkey="+getClientId()+" and client_fkey=walmart_client_fkey and ISNULL(wmref,'')<>'' ");
            while (rs.next()) {
                walmartItemToOwdSkuMap.put(rs.getString(1), rs.getString(2));
            }
            rs.close();
        }
        return walmartItemToOwdSkuMap
    }



    public String pushInventoryFile() {

        WalmartDsvXmlFileFormatter invFileSource = new WalmartDsvXmlFileFormatter(accountId, accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.INVENTORY)

        invFileSource.setStockPercentageForWalmart(0.80)
        invFileSource.setClientId(471)

        return sendFileToWalmart(invFileSource)

    }

    public void reportFileNotHandled(String name, String data) {
        Mailer.sendMail("Walmart XML non-PO file (" + name + ")", name+"\r\n\r\n"+data, "owditadmin@owd.com")
    }

    public void retrieveOrderFiles() {


        try {

            Map<String, String> orders = getOrderFileDataMap();
            for (String file : orders.keySet()) {
                try {
                    log.debug(file);
                    String orderData = orders.get(file)

                    log.debug(orderData);

                    if (!(file.contains("WMI_Order_Req"))) {

                        if(!file.contains("testconnectivities")) {

                            if (orderData.length() > 0) {
                                orderData = XmlUtil.serialize(orderData)
                            }
                            reportFileNotHandled(file, orderData)
                        }
                        log.debug "deleting " + file
                        deleteIncomingFile(file)

                    } else {
                        // s.importOrderFile(orders.get(file));
                        def walmartOrder = new XmlSlurper().parseText(orderData);

                        String batchRef = walmartOrder.WMIFILEHEADER.'@FILEID'
                        log.debug 'Batch:' + batchRef

                        log.debug getClientId()
                        PreparedStatement ps = HibernateSession.getPreparedStatement("insert into walmart_edi_incoming (source,data,status,batchnum) VALUES (?,?,?,?)")
                        ps.setString(1, "walmartdsv" + getClientId())
                        ps.setString(2, orders.get(file))
                        ps.setInt(3, 0)
                        ps.setString(4, batchRef)
                        int rowsAffected = ps.executeUpdate()
                        HibUtils.commit(HibernateSession.currentSession())

                        if (rowsAffected == 1) {
                            HibernateSession.closePreparedStatement()
                            String fxnalAckData = acknowledgeOrderFile(orderData)
                            ps = HibernateSession.getPreparedStatement("update walmart_edi_incoming set faAckData=?, status=1 where batchnum=?")
                            ps.setString(1, fxnalAckData)
                            ps.setString(2, batchRef)
                            rowsAffected = ps.executeUpdate()
                            HibUtils.commit(HibernateSession.currentSession())
                            log.debug "deleting " + file
                            deleteIncomingFile(file)
                        } else {
                            throw new Exception("Error importing file " + file)
                        }
                    }
                } catch (Exception ex){
                    ex.printStackTrace()
                    Exception exl = new LogableException(ex, "Error importing file "+file+" from Walmart.com:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), "Walmart.com", LogableException.errorTypes.ORDER_IMPORT)

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            Exception exl = new LogableException(ex, "Error importing file(s) from Walmart.com:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), "Walmart.com", LogableException.errorTypes.ORDER_IMPORT)
            throw ex
        } finally {
            HibernateSession.closePreparedStatement()
        }
    }

    public void setClientId(int id) {
        clientId = id
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

    String getFedexAcct() {
        return fedexAcct
    }

    void setFedexAcct(String fedexAcct) {
        this.fedexAcct = fedexAcct
    }


    public Map<String, String> getOrderFileDataMap() {
        Map<String, String> orders = new TreeMap<String, String>()

        s.setRemotePath("/home/" + sftpUser + "/ftp/inbound/")

        List<String> neworders = s.getFileNames("/home/" + sftpUser + "/ftp/inbound/")
        for (String file : neworders) {
            if(file.startsWith("WMI_Confirm"))
            {
                deleteIncomingFile(file)
            } else {
                log.debug("GGetting file: "+ file);
                orders.put(file, s.getFileData(file).toString())
                log.debug("GGot file: "+ file);
            }
           /* if(orders.size()==15){
                break;
            }*/
        }

        return orders

    }

    public void deleteIncomingFile(String filename) throws Exception {
        s.setRemotePath("/home/" + sftpUser + "/ftp/inbound/")
        s.deleteFile(filename)
    }


    private String sendFileToWalmart(WalmartDsvXmlFileFormatter fileSource)
    {
        //Un-Comment if you need to get a file to send to walmart
       /* try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/walmart.txt"));
            writer.write(fileSource.getFileData().toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace()
        }*/
        if(oldSend){
            log.debug("Transfer to old")
           SftpConnector ss = new SftpConnector(oldsftpHost, oldsftpUser, oldsftpPass, "");
            ss.setRemotePath("/staging/")
            log.debug fileSource.getFileName()
            log.debug fileSource.getFileData()
           ss.stageFileDataAndMove(fileSource.getFileName(), fileSource.getFileData().getBytes(),"/inbound/" + fileSource.getFileName())

           // ss.renameFile("/inbound/" + fileSource.getFileName())
            return fileSource.getFileData()
        }else {
            s.setRemotePath("/staging/")
            log.debug fileSource.getFileName()
            log.debug fileSource.getFileData()
           // s.putFileData(fileSource.getFileName(), fileSource.getFileData().getBytes())
            s.stageFileDataAndMove(fileSource.getFileName(), fileSource.getFileData().getBytes(),"/inbound/" + fileSource.getFileName())

            //s.renameFile("../inbound/" + fileSource.getFileName())
            return fileSource.getFileData()
        }
    }

    public String acknowledgeOrderFile(String fileData) throws Exception {

        WalmartDsvXmlFileFormatter fileSource = new WalmartDsvXmlFileFormatter(accountId, accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.CONFIRMATION)

        fileSource.setOriginalPoXml(fileData)

        return sendFileToWalmart(fileSource)


    }

    public String confirmOrderShipment(OrderStatus os) {

        WalmartDsvXmlFileFormatter confirmFileSource = new WalmartDsvXmlFileFormatter(accountId, accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_SHIPPED)

        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))

        return sendFileToWalmart(confirmFileSource)

    }


    public String cancelIncomingOrder(Order order){

        WalmartDsvXmlFileFormatter statusFileSource = new WalmartDsvXmlFileFormatter(accountId, accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.CANCEL)

        statusFileSource.setOriginalPoXml(order.getTagValue('WALMARTDSV_PO_XML'))

        return sendFileToWalmart(statusFileSource)

    }

    public String confirmOrderReceipt(OrderStatus os) {

        WalmartDsvXmlFileFormatter statusFileSource = new WalmartDsvXmlFileFormatter(accountId, accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_RECEIVED)

        statusFileSource.setCurrentOrderStatus(os)
        statusFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))

        println os.getTagMap().get('WALMARTDSV_PO_XML')
        return sendFileToWalmart(statusFileSource)

    }

    public void importOrderFile(String orderFileData) {
        def walmartOrder = new XmlSlurper().parseText(orderFileData);

        try {
            String batchRef = walmartOrder.WMIFILEHEADER.'@FILEID'
            log.debug 'Batch:' + batchRef
            walmartOrder.WMIORDERREQUEST.OR_ORDER.each { orderSrc ->

                try {
                    log.debug 'transid:' + orderSrc.'@REQUESTNUMBER'
                    log.debug 'orderid:' + orderSrc.'@ORDERNUMBER'
                    Order order = new Order(clientId + "")
                    order.order_refnum = orderSrc.'@ORDERNUMBER'
                    order.po_num = orderSrc.'@REQUESTNUMBER'
                    order.order_type = "WALMART.COM"
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                    order.is_paid = 1
                    order.backorder_rule = OrderXMLDoc.kRejectBackOrder
                    order.bill_cc_type = "CK";
                    order.is_paid = 1
                    order.group_name = "walmartdsv"
                    order.discount = 0.00f
                    order.template = "wcm"

                    order.addTag("WALMARTDSV_BATCHREF", batchRef)
                    order.addTag("WALMARTDSV_PO_XML", XmlUtil.serialize(orderSrc))

                    if (!(orderSrc.OR_SHIPTOSTORE.isEmpty())) {
                        order.addTag("WALMARTDSV_S2S", "1")
                       // order.getShippingInfo().whse_notes = ManifestingManager.getWalmartSSCCBarcodeWithVendorId(accountId)
                        order.addTag("COM.OWD.COMPLIANCE.ID","5");
                        order.group_name = "walmartdsvs2s";
                        order.template = "wcms2s";
                    }

                    if(!(OrderUtilities.orderRefnumAndPoExists(order.order_refnum,order.po_num,""+getClientId()))) {

                        order.noduplicates = false; //because walmart sends the same order number with multiple po values in separate events
                        log.debug 'shipname=' + orderSrc.OR_SHIPPING.OR_POSTAL.'@NAME'
                        order.getShippingContact().setName(orderSrc.OR_SHIPPING.OR_POSTAL.'@NAME'.text())
                        order.getShippingContact().setPhone(orderSrc.OR_SHIPPING.OR_PHONE.'@PRIMARY'.text())
                        order.getShippingAddress().setCompany_name('')
                        order.getShippingAddress().setAddress_one(orderSrc.OR_SHIPPING.OR_POSTAL.'@ADDRESS1'.text())
                        order.getShippingAddress().setAddress_two(orderSrc.OR_SHIPPING.OR_POSTAL.'@ADDRESS2'.text())
                        order.getShippingAddress().setCity(orderSrc.OR_SHIPPING.OR_POSTAL.'@CITY'.text())
                        order.getShippingAddress().setState(orderSrc.OR_SHIPPING.OR_POSTAL.'@STATE'.text())
                        order.getShippingAddress().setZip(orderSrc.OR_SHIPPING.OR_POSTAL.'@POSTALCODE'.text())
                        order.getShippingAddress().setCountry(orderSrc.OR_SHIPPING.OR_POSTAL.'@COUNTRY'.text())

                        log.debug order.getShippingAddress()
                        order.getBillingContact().setName(orderSrc.OR_BILLING.OR_POSTAL.'@NAME'.text())
                        order.getBillingContact().setEmail(orderSrc.OR_BILLING.OR_EMAIL.text())
                        order.getBillingContact().setPhone(orderSrc.OR_BILLING.OR_POSTAL.'@PRIMARY'.text())
                        order.getBillingAddress().setCompany_name('')
                        order.getBillingAddress().setAddress_one(orderSrc.OR_BILLING.OR_POSTAL.'@ADDRESS1'.text())
                        order.getBillingAddress().setAddress_two(orderSrc.OR_BILLING.OR_POSTAL.'@ADDRESS2'.text())
                        order.getBillingAddress().setCity(orderSrc.OR_BILLING.OR_POSTAL.'@CITY'.text())
                        order.getBillingAddress().setState(orderSrc.OR_BILLING.OR_POSTAL.'@STATE'.text())
                        order.getBillingAddress().setZip(orderSrc.OR_BILLING.OR_POSTAL.'@POSTALCODE'.text())
                        order.getBillingAddress().setCountry(orderSrc.OR_BILLING.OR_POSTAL.'@COUNTRY'.text())

                        log.debug 'Shipmethod:' + orderSrc.OR_SHIPPING.'@CARRIERMETHODCODE'
                        String shipmethodw = orderSrc.OR_SHIPPING.'@CARRIERMETHODCODE'

                        String shipmethod = inboundShipMap.get(shipmethodw)
                        if (shipmethod == null) {
                            String shiptype = orderSrc.OR_SHIPPING.'@METHODCODE'
                            if ("MS".equals(shiptype) || "MI".equals(shiptype)) {
                                shipmethod = "UPS Ground"
                            } else if ("MP".equals(shiptype)) {
                                shipmethod = "UPS 2nd Day Air"
                            } else if ("MX".equals(shiptype)) {
                                shipmethod = "UPS Next Day Air Saver"
                            } else if ("MA".equals(shiptype)) {
                                shipmethod = "UPS Ground"
                            } else {

                                throw new Exception("No ship method found for code " + shipmethodw)
                            }
                        }
                        if (shipmethod.toUpperCase().startsWith("UPS") && upsAcct != null) {
                            order.getShippingInfo().setShipOptions(shipmethod, "Third Party Billing", upsAcct)

                        } else if (shipmethod.toUpperCase().startsWith("FEDEX") && fedexAcct != null) {
                            if(shipmethod.contains("SmartPost")){
                                order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "")
                            }else {
                                order.getShippingInfo().setShipOptions(shipmethod, "Third Party Billing", fedexAcct)
                            }
                        } else {
                            order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "")
                        }

                        if(order.getShippingAddress().isPOAPO() || order.getShippingAddress().isPOAPONew()) {
                            order.addNote("Updating to Priority for APO or PO Box");
                            order.getShippingInfo().setShipOptions('USPS Priority Mail', "Prepaid", "")

                        }
                        order.getShippingInfo().comments = orderSrc.OR_RETURNS.'@TCNUMBER'



                        orderSrc.OR_ORDERLINE.each() { item ->

                            String sku = item.OR_ITEM.'@LEGACYITEMID'
                            if(sku.length()==0){
                                sku = item.OR_ITEM.'@ITEMNUMBER'
                            }
                            String lineRef = item.'@LINENUMBER'
                            String itemcolor = ""
                            String itemsize = ""
                            String upc = item.OR_ITEM.'@UPC'



                            if (!(LineItem.SKUExists("" + clientId, sku)) && getClientId() == 112) {   //test client account
                                Inventory i = InventoryManager.createInventoryItemForClient("" + clientId, sku, item.OR_ITEM.'@DESCRIPTION'.text(), "0.00", false, "", "", "");
                                InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(i.inventory_id, clientId, 999, OWDUtilities.getSQLDateTimeForToday(), sku + ':' + OWDUtilities.getSQLDateTimeForToday(), FacilitiesManager.getLocationCodeForClient(clientId), HibernateSession.currentSession())
                                HibUtils.commit(HibernateSession.currentSession())

                            } else {
                                if (getWalmartItemToOwdSkuMap().containsKey(sku)) {
                                    sku = getWalmartItemToOwdSkuMap().get(sku)
                                } else {
                                    throw new Exception("walmart item id " + sku + " not recognized")
                                }
                            }
                            String title = item.OR_ITEM.'@DESCRIPTION'

                            order.total_tax_cost += OWDUtilities.roundFloat(Float.parseFloat(item.OR_PRICE.'@TAX'.text()))


                            order.total_shipping_cost += OWDUtilities.roundFloat(Float.parseFloat(item.OR_PRICE.'@SHIPPING'.text()))

                            order.addLineItem(sku, item.OR_ITEM.'@QUANTITY'.text(), item.OR_PRICE.'@RETAIL'.text(), "0.00", title, item.OR_PRICE.'@TAX'.text(), item.OR_PRICE.'@SHIPPING'.text(), upc)
                            ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = lineRef
                            ((LineItem) (order.skuList.get(order.skuList.size() - 1))).long_desc = item.OR_ITEM.'@ITEMNUMBER'

                        }
                        String reference = null;


                        reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);

                        if (reference == null) {

                            if (!(order.last_error.contains("already exists"))) {
                                if (order.last_error.contains("Insufficient inventory")) {
                                    cancelIncomingOrder(order)
                                }else {
                                    throw new Exception("Unable to save new Walmart order : " + order.last_error);
                                }
                            } else {
                                log.debug "order already exists at OWD"
                            }
                        } else {
                            confirmOrderReceipt(new OrderStatus(order.orderID))
                        }
                    }
                } catch (Exception ex) {
                    Exception exl = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), "Walmart.com", LogableException.errorTypes.ORDER_IMPORT)

                    ex.printStackTrace()
                } finally {
                    HibernateSession.closePreparedStatement()

                }
            }


        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }



    public void processOrderFiles() {


        try {
            ResultSet rs = HibernateSession.getResultSet("select id, data from walmart_edi_incoming where status=1 and source='walmartdsv" + getClientId() + "';")
            Map<Integer, String> importMap = new HashMap<Integer, String>()



            while (rs.next()) {
                importMap.put(rs.getInt(1), rs.getString(2))
            }
            rs.close()

            for (Integer recid : importMap.keySet()) {
                String orderFileData = importMap.get(recid)

                importOrderFile(orderFileData)

                PreparedStatement ps = HibernateSession.getPreparedStatement("update walmart_edi_incoming set status=2 where id=?")
                ps.setInt(1, recid)
                int rowsAffected = ps.executeUpdate()
                HibUtils.commit(HibernateSession.currentSession())
                ps.close()
            }
        }catch(Exception ex)
        {
            Exception exl = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + getClientId(), "Walmart.com", LogableException.errorTypes.ORDER_IMPORT)

        }
    }


    }
