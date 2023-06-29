package com.owd.jobs.jobobjects.spscommerce

import com.owd.LogableException
import com.owd.core.Mailer
import com.owd.core.TagUtilities
import com.owd.core.business.order.*
import com.owd.core.business.order.beans.lineItemExemptions
import com.owd.core.managers.ConnectionManager
import com.owd.core.managers.FacilitiesManager
import com.owd.core.managers.ManifestingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiDocs
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.EdiSpsConfigdataSkus
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OrderTag
import com.owd.hibernate.generated.OwdFacility
import com.owd.hibernate.generated.OwdId
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.RetailPro.InventoryManager
import com.owd.jobs.jobobjects.symphony.SymphonyAPI
import com.owd.jobs.jobobjects.vendorCompliance.addressUtils
import org.apache.logging.log4j.LoggingException
import org.hibernate.Criteria
import org.hibernate.Query
import org.hibernate.transform.ResultTransformer
import org.hibernate.transform.Transformers

import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 3, 2010
 * Time: 11:24:45 AM
 * To change this template use File | Settings | File Templates.
 */

class SpsCommerceUtilities {


    static receiveDirPath = "/in"
    static sendDirPath = "/out"



    static final Map shipMap = [UPSGround: 'UPS Ground']

    private static Map<String, String> outboundShipMap

    static {
        outboundShipMap = new TreeMap<String, String>()

        outboundShipMap.put("FedEx 2Day", "FEDX");
        outboundShipMap.put("FedEx Express Saver", "FEDX");
        outboundShipMap.put("FedEx Ground", "FEDX");
        outboundShipMap.put("FedEx Home Delivery", "FEDX");
        outboundShipMap.put("FedEx Standard Overnight", "FEDX");
        outboundShipMap.put("FedEx Priority Overnight", "FEDX");
        outboundShipMap.put("FedEx SmartPost", "FXSP");
        outboundShipMap.put("LTL", "LTL");
        outboundShipMap.put("UPS 2nd Day Air", "UPSN");
        outboundShipMap.put("UPS 3 Day Select", "UPSN");
        outboundShipMap.put("UPS Ground", "UPSN");
        outboundShipMap.put("UPS Next Day Air", "UPSN");
        outboundShipMap.put("UPS Next Day Air Saver", "UPSN");
        outboundShipMap.put("USPS Priority Mail", "USPS");
        outboundShipMap.put("USPS Parcel Select Nonpresort", "USPS");
    }

    // receiveDirTestPath("/testin"),
    //  sendDirTestPath("/testout");

    def static int saveIncomingEdiDocForTesting(String docContent, String fileName) {

        return saveIncomingEdiDoc(docContent, fileName, true)
    }

    def static int saveIncomingEdiDoc(String docContent, String fileName) {
        return saveIncomingEdiDoc(docContent, fileName, false)

    }

    def static int saveIncomingEdiDoc(String docContent, String fileName, boolean testing) {

        println(docContent)
        println(fileName)
        println(testing)
        PreparedStatement ps = HibernateSession.getPreparedStatement("insert into edi_docs (docData,sourceFile,docStatus,account) values (?,?,?,'');SELECT @@IDENTITY")
        ps.setString(1, docContent)
        ps.setString(2, fileName)
        ps.setInt(3, (testing ? 2 : 0))

        ps.executeUpdate()

        long newId = 0;




        int iUpdCount = ps.getUpdateCount();
        boolean bMoreResults = true;
        ResultSet rs = null;

        //While there are still more results or update counts
        //available, continue processing resultsets
        while (bMoreResults || iUpdCount != -1) {
            //NOTE: in order for output parameters to be available,
            //all resultsets must be processed

            rs = ps.getResultSet();

            //if rs is not null, we know we can get the results from the SELECT @@IDENTITY
            if (rs != null) {
                rs.next();
                newId = rs.getInt(1);
            }

            //Do something with the results here (not shown)

            //get the next resultset, if there is one
            //this call also implicitly closes the previously obtained ResultSet
            bMoreResults = ps.getMoreResults();
            iUpdCount = ps.getUpdateCount();
        }

        ps.close()
        HibUtils.commit(HibernateSession.currentSession())

        return newId


    }

    def static List processRemotePOs(SPSCommerceRemoteFTP ftp, String path, int clientId) {

        List orders = new ArrayList();


        ftp.listFiles(path).each { filename ->
            if (filename?.startsWith(SPSCommerceRemoteFTP.fileType.PO.toString())) {
                println "loading filename " + filename
                def podata = new XmlParser().parseText(new String(ftp.getDataFile(filename, path)))

                //  Mailer.sendMail("PO received from SPS",new String(SPSCommerceRemoteFTP.getDataFile(filename, path)),"owditadmin@owd.com","edi@owd.com");


                if (podata?.name().toString().equals(("{http://www.spscommerce.com/RSX}Orders"))) {
                    podata.Order.each { po ->


                        StringWriter sw = new StringWriter()
                        new XmlNodePrinter(new PrintWriter(sw)).print(po)
                        println sw.toString()

                        println saveIncomingEdiDoc(sw.toString(), filename)

                        HibUtils.commit(HibernateSession.currentSession())

                        //  orders.add(importPo(po, clientId))
                    }

                    //

                } else if (podata?.name() == "Order") {

                    StringWriter sw = new StringWriter()
                    new XmlNodePrinter(new PrintWriter(sw)).print(podata)
                    println sw.toString()
                    println saveIncomingEdiDoc(sw.toString(), filename)

                    HibUtils.commit(HibernateSession.currentSession())
                    //   ftp.deleteDataFile(filename, path)
                } else {
                    println "Unrecognized root element name " + (podata?.name())
                    Mailer.sendMail("Problem importing SPS PO file", new String(ftp.getDataFile(filename, path)), "owditadmin@owd.com", "edi@owd.com");

                    //ignore for now
                }
                ftp.deleteDataFile(filename, path)
            }
        }

        return orders;

    }

    def static deliverAsn(SPSCommerceRemoteFTP ftp, int orderId, int clientId) {

        String asnData = SpsCommerceUtilities.generateASN(orderId, clientId)
        println asnData
        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
    }

    def static void main(String[] args) {
        //generateInvoice("hello", 1)
        // println storeMap

    //    processPendingPos();
       /* XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)
        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(11851041, 489, true)



        OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode())
        Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())

        println "asn tagmap: " + tagMap
        String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
        def poData = parser.parseText(poNode)
        EdiSpsConfigdata configdata = getEdiConfigData(poData)*/
        /* String ack = SpsCommerceUtilities.generateACK(configdata,poData,471,order)
         println ack*/
       // System.out.println(getUPCFromOwdSku(configdata, "P222189"))
        String asnData = SpsCommerceUtilities.generateASN(13793809,471);
       println asnData;


    }

    public static void processPendingPos() {
        try {
            List<EdiDocs> docsToProcess = HibernateSession.currentSession().createQuery("from EdiDocs where docStatus=0 and account=''").list()
            for (EdiDocs doc : docsToProcess) {
                String notificationEmails = "";
                String errorPO = "";
                try {
                    println doc.getDocData()
                    XmlParser parser = new XmlParser()
                    parser.setTrimWhitespace(true)
                    Node po = parser.parseText(doc.getDocData())

                    doc.setDocStatus(1)
                    // doc.setSourceFile(doc.getSourceFile()+'.dtd.')
                    //   HibernateSession.currentSession().evict(doc);
                    // HibernateSession.currentSession.update(object);
                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    HibUtils.commit(HibernateSession.currentSession())
                    //   HibernateSession.currentSession().flush()
                    EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
                    notificationEmails = config.getErrorNotification();
                    int clientId = config.getClientFkey();
                    Order order;
                    if(config.spsaccount.equals("0XRALLGILDANUSA")){
                        order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, clientId)
                    }else if(config.spsaccount.equals("FYAALLHVMNINC00")){
                        order = SpsCommerceHVMNAmazonUtilities.importPo(po,clientId);
                    }else{
                        order = importPo(po, clientId)
                    }

                    errorPO = order.po_num;
                    boolean canceledOrder = false;
                    if (clientId == 489 || clientId == 491) {  //Symphony
                        SymphonyAPI.postOrderToSymphony(order, doc.getId(), po, false)

                    } else {

                        //next three lines normally happen in API when Symphony posts order
                        order.addTag(TagUtilities.kEDIPurchaseOrder, doc.getDocData());
                        order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
                        try {
                            if (config.getVendorComplianceFkey() > 0) {
                                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        order.order_type = order.order_type + ":EDI:";
                        order.forcePayment = false
                        order.is_paid = 1
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                        //this must match the facility that your test SKU has inventory in
                        //DOES NOT HANDLE MULTIPLE FACILITY CLIENTS YET
                        if(config.spsaccount.equals("FYAALLHVMNINC00")){
                            order.facilityCode = "DC6"
                            order.facilityPolicy ="DC6"
                        }else {
                            order.facilityCode = FacilitiesManager.getLocationCodeForClient(clientId)
                            order.facilityPolicy = FacilitiesManager.getLocationCodeForClient(clientId)
                        }

                        if(config.getSpsaccount().equals("080FWSBUMBLSYMP")){
                            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
                            order.setShippingMethodName("FedEx Ground");
                            order.group_name = "Amazon";
                            order.bill_cc_type = "CK";
                            order.is_paid = 1
                            order.setThirdPartyBilling("715264463");
                            order.setThirdPartyBillingContact("Miranda Osguthorpe","Bumbleride co One World Direct","3325 Manitou Ct","","Mira Loma","CA","91752","6058457172");



                        }

                        //Amazon
                        if (config.getSpsaccount().equals("080ALLGILDANUSA")||config.spsaccount.equals("FYAALLHVMNINC00")) {
                            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
                            order.setShippingMethodName("LTL");
                            order.group_name = "Amazon";
                            order.bill_cc_type = "CK";
                            order.is_paid = 1


                        }
                        //Dicks Sporting Goods Gildan
                        if(config.spsaccount.equals("0XRALLGILDANUSA")){
                            order.backorder_rule = OrderXMLDoc.kRejectBackOrder

                            order.group_name = "Dicks";
                            order.bill_cc_type = "CK";
                            order.is_paid = 1
                            order.companyOverride = "DICK'S Sporting Goods";
                            order.nameOverride = "Returns Department";
                            order.address1Override = "7603 Trade Port Drive";
                            order.cityOverride = "Louisville";
                            order.stateOverride = "KY";
                            order.zipOverride = "40258";

                            order.setThirdPartyBilling("716047598");
                            order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","345 Court Street","","Coraopolis","PA","15108","6058457172");








                        }



                        String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);

                        if (reference == null) {
                            if(order.last_error.contains("No valid line items in order - order could not be saved")){
                                if(config.spsaccount.equals("0XRALLGILDANUSA")||config.spsaccount.equals("080ALLGILDANUSA")){
                                    SpsCommerceDicksSportingGoodsUtilities.deliverCanceledASN(po,471);
                                    canceledOrder = true;
                                }
                            }else {


                                throw new Exception("Error saving order: " + order.last_error);
                            }
                        }
                        //inject work order for gildan Amazon
                        if (config.getSpsaccount().equals("080ALLGILDANUSA")&& !canceledOrder) {
                            order.injectWorkOrder("Gildan/Amzazon work order. OrderNum: " + order.orderNum, "Please poly bag all items according to specs. Then pack out using pack stations. Apply vendor compliance labels to middle long side of box. Assign to Rachel to get routing");
                        }
                        if(config.spsaccount.equals("FYAALLHVMNINC00")){
                            order.injectWorkOrder("HVMN/Amzazon work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                                    "1.  Pull the requested items\n" +
                                    "2.  Overbox each case (if more than 6 cases place on a pallet and DO NOT overbox).\n" +
                                    "3.  After order is picked, pack out using pack stations. \n" +
                                    "4.  Apply vendor compliance labels to middle long side of box.\n" +
                                    "5.  Currently, the shipping method is set to LTL. Assign the work order back Gail to get routing");

                        }

                    }
                    if(canceledOrder){
                        doc.setDocStatus(7)
                    }else{
                        doc.setDocStatus(2)
                    }

                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()

                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())
                    try {
                        if (config.acknowledgmentRequired == 1 && !canceledOrder) {
                            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID), Integer.parseInt(order.getClientID()), true)
                            println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                            String ack ="";
                            if(config.spsaccount.equals("FYAALLHVMNINC00")){
                                ack = SpsCommerceHVMNAmazonUtilities.generateACK(config, po, Integer.parseInt(order.clientID), oorder)
                            }else{
                                ack = SpsCommerceUtilities.generateACK(config, po, Integer.parseInt(order.clientID), oorder)
                            }

                            println ack
                            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();


                            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
                            addTagToOrder(order.orderID, TagUtilities.kEDIPurchaseOrderAcknowledgement, ack);


                        }

                    } catch (Exception e) {
                        throw new LogableException("Unable to properly Acknowledge: " + e.getMessage(), order.orderNum, clientId + "", "EDI ACK", LogableException.errorTypes.ASN_IMPORT);
                    }
                } catch (LogableException lex) {
                    println "We have an error for processing orders that is logged to slack"

                    println lex.errorMessage

                    if(lex.getMessage().contains("Duplicate PO")){
                        doc.setDocStatus(6)
                    }else {
                        doc.setDocStatus(0)
                    }
                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())


                }

                catch (Exception ex) {
                    if(ex.getMessage().contains("Duplicate PO")){
                        doc.setDocStatus(6)
                    }else {
                        doc.setDocStatus(0)
                    }
                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())

                    String message = ex.getMessage()
                    if (message == null) {
                        message = "" + ex.getCause().getMessage()
                    }
                    if (message.contains("Insufficient inventory")) {

                        if(null!= notificationEmails && notificationEmails.length() >3){
                            Vector v = new Vector();
                            v.addAll(notificationEmails.split(","));
                            Mailer.postMailMessage("Inventory Error for PO: "+errorPO,message,v,"edi@owd.com");
                            doc.setDocStatus(3)
                            HibernateSession.currentSession().saveOrUpdate(doc)
                            HibernateSession.currentSession().flush()
                            //  HibernateSession.currentSession().getTransaction().commit()
                            HibUtils.commit(HibernateSession.currentSession())
                        }

                        println message
                    } else if (message.contains("Duplicate:")) {
                        doc.setDocStatus(2)
                        //  HibernateSession.currentSession().evict(doc);

                        HibernateSession.currentSession().saveOrUpdate(doc)
                        HibernateSession.currentSession().flush()
                        //  HibernateSession.currentSession().getTransaction().commit()
                        HibUtils.commit(HibernateSession.currentSession())

                    } else {
                        ex.printStackTrace()

                        if(!ex.getMessage().contains("Duplicate PO")) {
                            Exception exl = new LogableException(ex, "Generic order import error from SPSCommerce:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "489", "EDI order import", LogableException.errorTypes.ORDER_IMPORT);
                        }
                    }
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
    def static String getOwdSkuFromForeinSku(EdiSpsConfigdata config, String skuValue){
        String sku = null;
        println("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")

        ResultSet rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku ='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
        if (rs.next()) {
            sku = rs.getString(1)
        }
        rs.close()

        return sku;
    }
    def static String getOwdSkuFromUpc(EdiSpsConfigdata config, String upcValue) {
        if (upcValue == null || upcValue.length() < 8) {
            return null;
        }
        String sku = null;
        println("Verify Price: " + config.verifyPrice)
        println()
        if (config.verifyPrice == 0) {
            println("We are doing normal upc lookup")

            ResultSet rs = HibernateSession.getResultSet("select inventory_num from owd_inventory where client_fkey=" + config.getClientFkey() + " and upc_code='" + upcValue.trim() + "'")

            if (rs.next()) {
                sku = rs.getString(1)
            } else {
                println("We did not find it")
            }
            rs.close()
        }
        if (null == sku && config.vendorComplianceFkey > 0) {
            println("in the verify price")
            println(config.spsaccount)
            println(config.acknowledgmentRequired);
            println(config.verifyPrice);
            println(config.vendorComplianceFkey);

            println("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            ResultSet rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            if (rs.next()) {
                sku = rs.getString(1)
            }
            rs.close()
        }
        return sku
    }

    def static Boolean verifyPrice(EdiSpsConfigdata config, String owdSku, String price) throws Exception {
        boolean good = false;
        if (owdSku.length() > 0) {
            ResultSet rs = HibernateSession.getResultSet("select verification_price from owd_inventory_sku_maps where client_fkey=" + config.getClientFkey() + " and owd_sku='" + owdSku.trim() + "' and vendor_compliance_fkey = " + config.vendorComplianceFkey)
            if (rs.next()) {
                String vPrice = rs.getString(1)
                println(price)
                println(vPrice)
                if (Float.parseFloat(vPrice) == (Float.parseFloat(price))) good = true;

            } else {
                rs.close()
                throw new Exception("Unable to lookup price for verification")
            }
            rs.close();

        }


        return good;
    }

    def static String getUPCFromOwdSku(EdiSpsConfigdata config, String skuValue) {
        if (skuValue == null || skuValue.length() < 1) {
            return null;
        }
        String upc = null;
        if (config.verifyPrice == 0) {

            println "select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'"
            ResultSet rs = HibernateSession.getResultSet("select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }

        if ((null == upc || upc.length() < 5) && config.vendorComplianceFkey > 0) {

            ResultSet rs = HibernateSession.getResultSet("select upc from owd_inventory_sku_maps where client_fkey=" + config.getClientFkey() + " and owd_sku='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }
        return upc
    }

    def static Map<String, String> makeVendorToOwdSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        for (EdiSpsConfigdataSkus mapper : config.getSkus()) {
            skuMap.put(mapper.getVendorSku(), mapper.getOwdSku())
        }

        return skuMap
    }

    def static Map<String, String> makeOwdToVendorSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        for (EdiSpsConfigdataSkus mapper : config.getSkus()) {
            skuMap.put(mapper.getOwdSku(), mapper.getVendorSku())
        }

        return skuMap
    }


    def static Order importPo(Node podoc, int clientID) throws Exception {


        def order = new Order("" + clientID) //ballpark classics, for now


        EdiSpsConfigdata configdata = getEdiConfigData(podoc)
        Map<String, String> fromVendorSkuMap = makeVendorToOwdSkuMap(configdata)

        //  order.getShippingInfo().carr_service = podoc.Header.OrderHeader.CarrierRouting.text()

        // order.getShippingInfo().setShipOptions(order.getShippingInfo().carr_service,"Third Party Billing","Y2X172");

        //  order.shippercontact = "GSI Commerce";
        //                     order.shippercompany = "GSI Commerce";
        //                     order.shipperaddress_one = "935 First Avenue";
        //                     order.shipperaddress_two = "";
        //                     order.shippercity = "King of Prussia";
        //                    order.shipperstate = "PA";
        //                   order.shipperzip = "19406";
        //                    order.shipperphone = "6104917000";

        order.po_num = podoc.Header.OrderHeader.PurchaseOrderNumber.text()


        def shipto = podoc.Header.Address.find { it.AddressTypeCode.text() == 'ST' }
        println(shipto)
        println("This ship")
        println(shipto.AddressLocationNumber.text)
        println(podoc.Header.Address.AddressLocationNumber.text())

        if (shipto != null) {

            if (shipto.AddressLocationNumber.text().length() > 0 && !(shipto.Address1.text().length() > 0)) {
                println("We are loading the address")

                addressUtils.loadShippingAddressFromReference(order, shipto.AddressLocationNumber.text(), configdata.name)
                println(order.getShippingAddress().address_one)
                println("That was the one")

            } else {


                order.getShippingContact().with {
                    name = "" + shipto.AddressName.text()
                    email = "" + shipto.Contact?.PrimaryEmail?.text()
                    phone = "" + shipto.Contact?.PrimaryPhone?.text()
                }
                order.getShippingAddress().with {
                    address_one = shipto.Address1.text()
                    address_two = shipto.Address2?.text()
                    city = shipto.City.text()
                    state = shipto.State.text()
                    zip = shipto.PostalCode.text()
                    country = shipto.Country.text()
                }
            }
        } else {

            throw new Exception("PO contains no ship to information!")
        }
        def billto = podoc.Header.Address.find { it.AddressTypeCode.text() == 'BT' }

        if (billto != null) {
            order.getBillingContact().with {
                name = billto.AddressName.text()
                email = billto.Contact?.PrimaryEmail?.text()
                phone = billto.Contact?.PrimaryPhone?.text()
            }
            order.getBillingAddress().with {
                address_one = billto.Address1.text()
                address_two = billto.Address2?.text()
                city = billto.City.text()
                state = billto.State.text()
                zip = billto.PostalCode.text()
                country = billto.Country.text()
            }
        } else {
            order.setBillingContact(order.getShippingContact())
            order.setBillingAddress(order.getShippingAddress())
        }

        def lines = podoc.LineItems.LineItem.OrderLine.each {

            boolean caseQty = false;

            if (null != it.OrderQtyUOM?.text() && !("EA".equals("" + it.OrderQtyUOM.text()))) {
                if("CA".equals("" + it.OrderQtyUOM.text())){
                    caseQty = true;
                }else {
                    throw new Exception("unrecognized UOM of " + it.OrderQtyUOM.text());
                }
            }


            String internalSku = getOwdSkuFromUpc(configdata, "" + it.ConsumerPackageCode.text())
            if((configdata.spsaccount.equals("FYAALLHVMNINC00"))){
                internalSku = getOwdSkuFromForeinSku(configdata,it.BuyerPartNumber.text())

            }

            if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {
                internalSku = fromVendorSkuMap.get("" + it.VendorPartNumber.text())
                if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {

                    internalSku = fromVendorSkuMap.get("" + it.ProductID.PartNumber.text())
                    if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {
                        throw new Exception("unrecognized SKU/VendorPartNumber of " + it.VendorPartNumber.text());
                    }
                }
            }
            println("Found sku: " + internalSku)
            if (!(ConnectionManager.InventoryExists(internalSku, configdata.getClientFkey() + ""))) {
                throw new Exception("unrecognized SKU of " + internalSku);

            }

            String itemDescription = it.PartDescription1?.text()
            if (itemDescription == null) {

                it.find { it.ProductOrItemDescription?.ItemDescriptionCode?.text() == '08' }.with
                        { desc ->
                            itemDescription = desc
                        }
            }
            if (itemDescription == null) {
                itemDescription = ""
            }
            //   Do we need to Verify price??
            if (configdata.verifyPrice == 1) {
                String price = it.PurchasePrice?.text();
                //if price does not match add line to exemptions list
                if (!verifyPrice(configdata, internalSku, price)) {

                    def lie = new lineItemExemptions()

                    lie.setVendor_sku(it.ConsumerPackageCode.text())
                    lie.setInventory_num(internalSku);
                    lie.setExemptionCode("price");
                    lie.setExemptionValue("no match");
                    lie.setQty(it.OrderQty.text() + "")
                    lie.setMerchant_line_number(it.LineSequenceNumber.text())

                    order.lineExemptions.add(lie);
                } else {
                    //check for stock and only ship what we have
                    if (configdata.spsaccount.equals("080ALLGILDANUSA")) {
                        OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID,internalSku)


                        int stock = order.getStockLevelForInventoryId(owdInv.getInventoryId(),order.facilityCode)
                        if(stock == 0){
                            println "WE have 0 in stock exempt the whole line"
                            def lie = new lineItemExemptions()

                            lie.setVendor_sku(it.ConsumerPackageCode.text())
                            lie.setInventory_num(internalSku);
                            lie.setExemptionCode("quantity");
                            lie.setExemptionValue("out of stock");
                            lie.setQty(it.OrderQty.text() + "")
                            lie.setMerchant_line_number(it.LineSequenceNumber.text())

                            order.lineExemptions.add(lie);

                        }else{
                            int orderQty = Integer.parseInt(it.OrderQty.text());
                            if(stock>= orderQty){
                                println "We can fullfill";
                                order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                            } else{
                                println "We can partial Ship";
                                order.addLineItem(internalSku, "" + stock, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                                def lie = new lineItemExemptions()

                                lie.setVendor_sku(it.ConsumerPackageCode.text())
                                lie.setInventory_num(internalSku);
                                lie.setExemptionCode("quantity");
                                lie.setExemptionValue("short ship");
                                lie.setQty((orderQty-stock)+"")
                                lie.setMerchant_line_number(it.LineSequenceNumber.text())

                                order.lineExemptions.add(lie);


                            }


                        }



                    } else {
                        if(caseQty){
                            println("We have case qty")
                            OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID,internalSku)
                            int qty = it.OrderQty.text()
                            if(owdInv.caseQty>0){
                                order.addLineItem(internalSku, "" + qty*owdInv.caseQty, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                            }else{
                                throw new Exception("No case Qty set for: "+ internalSku)
                            }

                        }else{
                            order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                        }


                    }

                }


            } else {
                order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
            }

/*
            def upc = it.ConsumerPackageCode.text().trim();

            if (upc.trim().length() < 10) {
                upc = LineItem.getBarcodeForSKU(it.VendorPartNumber.text(), clientID + "");

                while (upc.length() > 12) {
                    upc = upc.substring(1)
                }
                while (upc.length() < 13) {
                    upc = '0' + upc
                }
            }*/

            order.recalculateBalance()
        }


        return order;
    }

    public static EdiSpsConfigdata getEdiConfigData(Node poDoc) {

        String partner = poDoc.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poDoc.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        return getEdiConfigDataSPSAccount(partner)
    }

    public static EdiSpsConfigdata getEdiConfigDataSPSAccount(String partner){
        Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount=? ")
        q.setParameter(0, partner)

        List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata>) q.list()
        if (configs.size() != 1) {
            throw new Exception("SPS EDI import error - wrong number of configs found for " + partner + "/" )
        }

        EdiSpsConfigdata configdata = configs.get(0)
        return configdata
    }

    def static String generateInvoice(int orderId, int clientId) {

        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId, clientId, true)
        assert (order != null)
        println order.poNum
        def ediMap = Eval.me(OrderUtilities.getPrintTemplateRefData(orderId))
        def today = new Date();

        int totalLines = 0;
        double totalValue = 0.00;


        def invoice = builder.Invoice {

            Header {
                InvoiceHeader {
                    TradingPartnerId(ediMap?.get('TradingPartnerId'))
                    InvoiceNumber(order.orderNum)
                    InvoiceDate(String.format('%1$tY-%<tm-%<td', today))
                    PurchaseOrderDate(String.format('%1$tY-%<tm-%<td', order.createdDate))
                    PurchaseOrderNumber(order.poNum)
                    Vendor(ediMap?.get('Vendor'))
                    FOBPayCode('PP')
                    ShipDate(String.format('%1$tY-%<tm-%<td', order.createdDate))
                }
                PaymentTerms {
                    TermsNetDueDate(String.format('%1$tY-%<tm-%<td', today + 45))
                    TermsDescription('Net45 Days')
                }
                Address {
                    AddressTypeCode('ST')
                    AddressName(order.shipinfo.shipFirstName + ' ' + order.shipinfo.shipLastName)
                    Address1(order.shipinfo.shipAddressOne)
                    Address2(order.shipinfo.shipAddressTwo)
                    City(order.shipinfo.shipCity)
                    State(order.shipinfo.shipState)
                    PostalCode(order.shipinfo.shipZip)
                    Country('US')
                }
                /* Reference {
                    ReferenceQual('IA')
                    ReferenceID('24696')
                }
                ChargesAllowances {
                    AllowChrgIndicator('C')
                    AllowChrgCode('G830')
                    AllowChrgAmt('15.00')
                    AllowChrgHandlingDescription('DROP SHIP FEE')
                }
                Miscellaneous {
                    Description1('INTERCHANGE')
                    Description2('021292000')
                }
                Miscellaneous {
                    Description1('FUNCTIONALGROUP')
                    Description2('21292100')
                }
                Miscellaneous {
                    Description1('TRANSACTION')
                    Description2('21292154')
                }*/
            }
            LineItems {

                Set<OwdLineItem> lines = order.getLineitems()
                lines.each { line ->
                    if (!("".equals(line.custRefnum)) && line.getQuantityActual() > 0) {
                        double unitValue = 105.00;
                        if (line.getInventoryNum().equalsIgnoreCase("BPC-GU-YANKEE")
                                || line.getInventoryNum().equalsIgnoreCase("BPC-GU-REDSOX")) {
                            unitValue = 105.00;
                        }
                        totalLines++;
                        totalValue += (line.quantityActual * unitValue);
                        LineItem {
                            InvoiceLine {
                                BuyerPartNumber(line.longDesc.tokenize(":").get(1))
                                //   VendorPartNumber('00812030010047')
                                ConsumerPackageCode(line.longDesc.tokenize(":").get(0))
                                // PartDescription1(line.description)
                                UnitPrice(('' + unitValue))
                                ShipQty(line.quantityActual)
                                ShipQtyUOM('EA')
                            }
                        }
                    }
                }

            }
            Summary {
                Totals {
                    TotalAmount(String.format('%.2f', totalValue))
                    TotalNetSalesAmount(String.format('%.2f', totalValue))
                    TotalLineItemNumber(totalLines)
                }
            }
        }



        return writer.toString()


    }

    def static String generateStockLevels(int clientId) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        int totalItems = 0;
        def stocklevels = builder.InventoryInquiriesAndAdvice
                {
                    Header {
                        HeaderReport {
                            TradingPartnerId('5S2ALLBALLPARKC')
                            Vendor('26634')
                            DocumentId('1106')
                            TsetHandlingCode('00')
                            ReportTypeCode('PI')
                            Date1(String.format('%1$tY-%<tm-%<td', new Date()))

                        }
                        Address {
                            AddressTypeCode('VN')
                            AddressName('Ballpark Classics')
                        }

                        /* Miscellaneous {
                            Qualifier1('INTERCHANGE')
                            Description1('000006963')
                        }
                        Miscellaneous {
                            Qualifier1('FUNCTIONALGROUP')
                            Description1('1135')
                        }
                        Miscellaneous {
                            Qualifier1('TRANSACTION')
                            Description1('0001')
                        }*/
                    }
                    LineItems {

                        def ArrayList fields = ['qty_on_hand', 'upc_code', 'inventory_num', 'is_active']
                        Inventory.getItemsForClientIDx(clientId + '', fields, true).each { item ->
                            def upc = item.get('upc_code')
                            println upc + "," + item.get('qty_on_hand') + "," + item.get('is_active')
                            if (item.get('upc_code')?.length() >= 12) {
                                totalItems++
                                LineItem {
                                    InventoryLine {
                                        //fix for bad left-side padding of upc with zeroes
                                        while (upc.length() > 12) {
                                            upc = upc.substring(1)
                                        }
                                        while (upc.length() < 13) {
                                            upc = '0' + upc
                                        }
                                        VendorPartNumber(item.get('inventory_num'))
                                        ConsumerPackageCode(upc)
                                    }
                                    /* Pricing {
                                        PriceTypeIDCode('DSP')
                                        UnitPrice('180')
                                    }*/
                                    InventoryLineQuantities {
                                        InventoryLineQuantity {
                                            QuantityLine {
                                                QuantityQualifier('33')
                                                Quantity1((item.get('is_active') == "1" ? item.get('qty_on_hand') : 0))
                                                QtyUOM('EA')
                                            }

                                        }

                                    }
                                }
                            }
                        }
                    }

                    Summary {
                        TotalLineItemNumber(totalItems)
                    }
                }
        return writer.toString()
    }

    def static String generateASN(int orderId, int clientId) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId, clientId, true)

        assert (order != null)
        assert ('Shipped'.equalsIgnoreCase(order.getOrderStatus()))


        OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode())
        Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())

        if(tagMap.get(TagUtilities.kVendorComplianceReference).equals("Dicks")){
            println("Doing Dicks asn");
            return SpsCommerceDicksSportingGoodsUtilities.generateASN(orderId,clientId);
        }

        println "asn tagmap: " + tagMap
        String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)


        def poData = parser.parseText(poNode)


        String partner = poData.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poData.Header.OrderHeader.Vendor.text()
        if(partner.equals("FYAALLHVMNINC00")){
            println("Doing HVMN");
            return SpsCommerceHVMNAmazonUtilities.generateASN(orderId,clientId);
        }

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        /*   Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount='"+partner.trim()+"' and Vendorid='"+vendor.trim()+"'")
           //  q.setString(0,partner)
           //   q.setString(1,vendor)
           List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata> ) q.list()
           println configs.size()+" configs found!"
           if(configs.size()!=1)
           {
               throw new Exception("SPS EDI import error - wrong number  of configs found for "+partner+"/"+vendor)
           }

           EdiSpsConfigdata configdata = configs.get(0)*/
        EdiSpsConfigdata configdata = getEdiConfigData(poData)
        Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)


        println order.poNum
        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;

        def asn = builder.Shipments(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    Shipment {


                        Header {
                            ShipmentHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                ShipmentIdentification(order.orderNum)
                                ShipDate(String.format('%1$tY-%<tm-%<td', order.shippedDate))
                                TsetPurposeCode('00') //OK
                                ShipNoticeDate(String.format('%1$tY-%<tm-%<td', today))
                                ShipNoticeTime(String.format('%1$tH:%<tM:%<tS', today))
                                ASNStructureCode('0001')
                                String tracking = com.owd.core.business.order.Package.getPackagesForOrderId(orderId).get(0).tracking_no;
                                if(tracking.contains(":")){
                                    tracking = tracking.substring(tracking.indexOf(":")+1);
                                    tracking = tracking.replaceAll(" ","");
                                    tracking = tracking.replaceAll("-","");
                                }
                                if (configdata.spsaccount.equals("080ALLGILDANUSA")||configdata.spsaccount.equals("FYAALLHVMNINC00")) {

                                    BillOfLadingNumber(tracking);
                                } else {
                                    if(configdata.getVendorComplianceFkey()==2 && order.shipinfo.carrService.equals("LTL")){
                                        BillOfLadingNumber(tracking);
                                    } else{
                                        BillOfLadingNumber('')
                                    }

                                }
                                CarrierProNumber('')
                                CurrentScheduledDeliveryDate(String.format('%1$tY-%<tm-%<td', today + 3))

                                // ShipmentQtyPackingCode('CTN25')
                                // ShipmentLadingQuantity("" + order.packagesShipped) // divide qty by 3 plus 1
                                // CarrierAlphaCode(order.shipinfo.carrService.contains("USPS") ? 'USPS' : 'UPSN')  //lookup
                            }

                            if (configdata.vendorComplianceFkey == 1) {


                                def refs = poData.Header.Reference?.each { ref ->

                                    Reference {
                                        ReferenceQual(ref.ReferenceQual?.text().trim())
                                        ReferenceID(ref.ReferenceID?.text().trim())
                                        //  Description(ref.Description?.text())
                                    }
                                }

                                if (clientId == 471 || clientId == 576) {

                                    Reference {
                                        ReferenceQual('BX')

                                        ReferenceID(getAmazonARNFromTags(order.getTags()))
                                    }
                                }
                            }
                            else if  (configdata.vendorComplianceFkey == 16) {

                                def refs = poData.Header.Reference?.each { ref ->

                                    Reference {
                                        ReferenceQual(ref.ReferenceQual?.text().trim())
                                        ReferenceID(ref.ReferenceID?.text().trim())
                                        //  Description(ref.Description?.text())
                                    }
                                }

                                if(clientId == 634 ) {
                                    Reference {
                                        ReferenceQual('BX')

                                        ReferenceID(getZapposDNFromTags(order.getTags()))
                                    }
                                }
                            }

                            Address {
                                AddressTypeCode('ST')
                                AddressName(order.shipinfo.shipFirstName + ' ' + order.shipinfo.shipLastName)
                                AddressAlternateName(order.shipinfo.shipCompanyName)
                                Address1(order.shipinfo.shipAddressOne)
                                Address2(order.shipinfo.shipAddressTwo)
                                City(order.shipinfo.shipCity)
                                State(order.shipinfo.shipState)
                                PostalCode(order.shipinfo.shipZip)
                                Country(order.shipinfo.shipCountry)

                                def shipto = poData.Header.Address.find { it.AddressTypeCode.text().equals('ST') }
                                if (shipto != null) {

                                    LocationCodeQualifier("" + shipto.LocationCodeQualifier?.text())
                                    AddressLocationNumber("" + shipto.AddressLocationNumber?.text())

                                }

                            }
                            Address {
                                AddressTypeCode('SF')
                                AddressName("Fulfillment Operations")
                                AddressAlternateName("One World Direct")
                                Address1(shipFrom.address)
                                Address2("")
                                City(shipFrom.city)
                                State(shipFrom.state)
                                PostalCode(shipFrom.zip)
                                Country("US")
                                if ("CHKBW".equalsIgnoreCase(vendor) || "BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1) {
                                    //chilitech - Amazon DC shipment

                                    LocationCodeQualifier("ZZ")
                                    if (poData.Header.OrderHeader.Vendor.text().length() > 0) {
                                        AddressLocationNumber("" + poData.Header.OrderHeader.Vendor.text().trim())
                                    } else {
                                        AddressLocationNumber(order.getFacilityCode())
                                    }
                                }
                                if(configdata.getVendorComplianceFkey()==2){
                                    LocationCodeQualifier("92")
                                    AddressLocationNumber(configdata.getVendorid())
                                }
                            }
                            /*  Miscellaneous {
                                                Qualifier1('TD502')
                                                Description1('2')
                                                Qualifier2('1')
                                            }
                            */
                            String scacCode = poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
                            if (scacCode.length() < 2) {
                                scacCode = outboundShipMap.get(order.shipinfo.carrService)
                                if (scacCode == null) {
                                    scacCode = ""
                                }
                            }
                            CarrierInformation {
                                StatusCode('CL')
                                if ("BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1) {
                                    //BumbleRide Amazon DC
                                    CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL') ? 'LT' : 'ZZ')
                                } else {
                                    CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL') ? 'L' : 'M')
                                }

                                CarrierAlphaCode(scacCode)
                                if ("TRZL".equalsIgnoreCase(vendor)) { //chilitech - Amazon DC shipment
                                    if (order.shipinfo.carrService.toUpperCase().contains("GROUND")) {
                                        CarrierRouting("FDEG")
                                    } else {
                                        CarrierRouting("FEDH")
                                    }

                                }


                            }

                            QuantityAndWeight {
                                if ("TRZL".equalsIgnoreCase(vendor) || "BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1) {
                                    //chilitech - Amazon DC shipment
                                    PackingMedium('CTN')
                                } else {
                                    PackingMedium('BOX')
                                }
                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')

                            }

                            FOBRelatedInstruction {

                                if (configdata.getSpsaccount().equals("080ALLGILDANUSA")) {
                                    //set PP for Gildan Amazon orders
                                    FOBPayCode('CC')
                                } else {
                                    FOBPayCode('TP')
                                }
                            }
                        }
                        OrderLevel {
                            OrderHeader {
                                InternalOrderNumber(order.orderRefnum)
                                InvoiceNumber(poData.Header.OrderHeader.InvoiceNumber ? poData.Header.OrderHeader.InvoiceNumber?.text().trim() : order.orderNum)
                                PurchaseOrderNumber(poData.Header.OrderHeader.PurchaseOrderNumber?.text().trim())
                                ReleaseNumber(poData.Header.OrderHeader.ReleaseNumber?.text().trim())
                                PurchaseOrderDate(poData.Header.OrderHeader.PurchaseOrderDate?.text().trim())
                                Vendor(poData.Header.OrderHeader.Vendor?.text().trim())
                                CustomerOrderNumber(poData.Header.OrderHeader.CustomerOrderNumber?.text().trim())
                            }
                            QuantityAndWeight {
                                if ("TRZL".equalsIgnoreCase(vendor) || "BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1) {
                                    //toysrus dropship
                                    PackingMedium('CTN')
                                } else {
                                    PackingMedium('BOX')
                                }
                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')
                            }
                            /*CarrierInformation {
                                StatusCode('CL')
                                CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL')?'L':'M')
                                CarrierAlphaCode(podoc.Header.CarrierInformation.CarrierAlphaCode.text())
                            }*/

                            def refs = poData.Header.Reference?.each { ref ->
                                Reference {
                                    ReferenceQual(ref.ReferenceQual?.text().trim())
                                    ReferenceID(ref.ReferenceID?.text().trim())
                                    //  Description(ref.Description?.text())
                                }
                            }

                            def adds = poData.Header.Address?.each { add ->
                                Address {
                                    AddressTypeCode(add.AddressTypeCode?.text().trim())
                                    AddressName(add.AddressName?.text().trim())
                                    AddressAlternateName(add.AddressAlternateName?.text().trim())
                                    Address1(add.Address1?.text().trim())
                                    Address2(add.Address2?.text().trim())
                                    City(add.City?.text().trim())
                                    State(add.State?.text().trim())
                                    PostalCode(add.PostalCode?.text().trim())
                                    Country(add.Country?.text().trim())
                                    AddressLocationNumber(add.AddressLocationNumber?.text().trim())

                                }
                            }

                            def cons = poData.Header.Contact?.each { con ->
                                Contact {
                                    ContactName(con.ContactName?.text().trim())
                                    PrimaryPhone(con.PrimaryPhone?.text().trim())
                                    PrimaryFax(con.PrimaryFax?.text().trim())
                                    PrimaryEmail(con.PrimaryEmail?.text().trim())
                                }
                            }


                            com.owd.core.business.order.Package.getPackagesForOrderId(orderId).each { pack ->

                                PackLevel {
                                    Pack {
                                        PackLevelType('P')
                                        if (configdata.getName().equals('Brookstone')) {
                                            ShippingSerialID(ManifestingManager.getSSCCBarcode())
                                        }
                                        if ("BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1 || configdata.getVendorComplianceFkey() == 2) {
                                            ShippingSerialID("00" + com.owd.core.business.order.Package.getSSCCCodeForPackage(pack.order_track_id));

                                        }
                                        String tracking = pack.tracking_no;
                                        if(tracking.contains(":")){
                                            tracking = tracking.substring(tracking.indexOf(":")+1);
                                        }
                                        CarrierPackageID(tracking)
                                    }
                                    PhysicalDetails {
                                        if ("TRZL".equalsIgnoreCase(vendor) || "BUMAQ".equalsIgnoreCase(vendor) || configdata.getVendorComplianceFkey() == 1) {
                                            //toysrus dropship
                                            PackingMedium('CTN')
                                        } else {
                                            PackingMedium('BOX')
                                        }
                                        WeightQualifier('G')
                                        PackWeight(pack.weight)
                                        PackWeightUOM('LB')
                                        if (!("TRZL".equalsIgnoreCase(vendor))) { //if not toysrus dropship

                                            PackUOM('CTN')
                                            PackSize('1')
                                        }
                                    }

                                    /* if("BUMAQ".equalsIgnoreCase(vendor)){
                                         //amazon need to get SSCC in Marks and Numbers Section
                                         MarksAndNumbersCollection{
                                             MarksAndNumbersQualifier1('UC');
                                             MarksAndNumbers1(com.owd.core.business.order.Package.getSSCCCodeForPackage(pack.order_track_id));
                                             MarksAndNumbersQualifier2('CA');
                                             MarksAndNumbers2(pack.tracking_no);
                                         }

                                     }*/

                                    if (configdata.spsaccount.equals("080ALLGILDANUSA")) {

                                        println "Getting line items for order_track_id " + pack.order_track_id

                                        List<Map> items = com.owd.core.business.order.Package.getEDILineItemsForPackage(pack.order_track_id,true)


                                        println "Got " + items.size() + " lines for package"
                                        if (items.size()==0){
                                            throw new LogableException("Unable to load sku's from tracking id: "+ pack.order_track_id,order.orderNum,clientId+"","Shipment confirmation",LogableException.errorTypes.SHIPMENT_NOTIFICATION);
                                        }
                                        items.each { itemMap ->
                                            ItemLevel {
                                                println itemMap
                                                String vendorSku = fromVendorSkuMap.get(itemMap.get('SKU'))
                                                println vendorSku
                                                if (vendorSku == null || vendorSku.length() < 1) {
                                                    String upc = com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities.getUPCFromOwdSku(configdata, "" + itemMap.get('SKU'))
                                                    println "got upc " + upc + " for " + itemMap.get('SKU')
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                       // println "checking CPC " + it.ConsumerPackageCode.text()
                                                        it.ConsumerPackageCode.text().trim().equals(upc)
                                                    }

                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                VendorPartNumber(poLineItem.ProductID.PartNumber ? poLineItem.ProductID.PartNumber.text().trim() : '')
                                                            } else {
                                                                VendorPartNumber(poLineItem.VendorPartNumber ? poLineItem.VendorPartNumber.text().trim() : '')
                                                            }
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : upc)
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(poLineItem.OrderQtyUOM?.text().trim())
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                ItemStatusCode('AC')
                                                            }
                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }

                                                } else {
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                        if ("TRZL".equalsIgnoreCase(vendor)) {
                                                            it.ProductID.PartNumber.text().trim().equals(vendorSku)
                                                        } else {
                                                            it.VendorPartNumber.text().trim().equals(vendorSku)
                                                        }

                                                    }


                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            VendorPartNumber((vendorSku))
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : itemMap.get('BARCODE'))
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(poLineItem.OrderQtyUOM?.text().trim())
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                ItemStatusCode('AC')
                                                            }
                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                    } else {
                                        ItemLevel {
                                            println "Getting line items for order_track_id " + pack.order_track_id

                                            List<Map> items = com.owd.core.business.order.Package.getEDILineItemsForPackage(pack.order_track_id)


                                            println "Got " + items.size() + " lines for package"
                                            items.each { itemMap ->

                                                println itemMap
                                                String vendorSku = fromVendorSkuMap.get(itemMap.get('SKU'))
                                                println vendorSku
                                                if (vendorSku == null || vendorSku.length() < 1) {
                                                    String upc = com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities.getUPCFromOwdSku(configdata, "" + itemMap.get('SKU'))
                                                    println "got upc " + upc + " for " + itemMap.get('SKU')
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                      //  println "checking CPC " + it.ConsumerPackageCode.text()
                                                        it.ConsumerPackageCode.text().trim().equals(upc)
                                                    }

                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                VendorPartNumber(poLineItem.ProductID.PartNumber ? poLineItem.ProductID.PartNumber.text().trim() : '')
                                                            } else {
                                                                VendorPartNumber(poLineItem.VendorPartNumber ? poLineItem.VendorPartNumber.text().trim() : '')
                                                            }
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : upc)
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(poLineItem.OrderQtyUOM?.text().trim())
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                ItemStatusCode('AC')
                                                            }
                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }

                                                } else {
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                        if ("TRZL".equalsIgnoreCase(vendor)) {
                                                            it.ProductID.PartNumber.text().trim().equals(vendorSku)
                                                        } else {
                                                            it.VendorPartNumber.text().trim().equals(vendorSku)
                                                        }

                                                    }


                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            VendorPartNumber((vendorSku))
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : itemMap.get('BARCODE'))
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(poLineItem.OrderQtyUOM?.text().trim())
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                ItemStatusCode('AC')
                                                            }
                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }
                                                }
                                                /* Miscellaneous {
                                                Qualifier1('PID01')
                                                Description1('F')
                                                Qualifier2('1')
                                            }
                                            Miscellaneous {
                                                Qualifier1('PID02')
                                                Description1('91')
                                                Qualifier2('1')
                                            }
                                            Miscellaneous {
                                                Qualifier1('PID05')
                                                Description1('One Size')
                                                Qualifier2('1')
                                            }*/

                                            }

                                        }
                                    }
                                }


                            }


                        }
                        Summary {
                            TotalLineItems(totalLines)
                            TotalQuantity(totalQuantity)
                        }

                    }

                }

        // throw new Exception("fix line number lookup from PO!!!")
        return writer.toString()

    }

    public static void addTagToOrder(String id, String name, String value) {
        try {
            String sql = "insert into owd_tags (external_id, type, name, [value]) values(:externalId, :type, :name, :thevalue)"
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("externalId", id);
            q.setParameter("type", "ORDER");
            q.setParameter("name", name);
            q.setParameter("thevalue", value);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    def static String generateACK(EdiSpsConfigdata configdata, Node poData, int clientId, OwdOrder order) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)





        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)






        String partner = poData.Header.OrderHeader.TradingPartnerId.text()
        // String vendor = poData.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        // println "SPSVendor:"+vendor


        Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)
        Map<String, lineItemExemptions> exemptLines = loadExemptionFromOrderId(order.getOrderId()+"")

        //println order.poNum
        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;

        def asn = builder.OrderAcks(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    OrderAck {


                        Header {
                            OrderHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                PurchaseOrderNumber(poData.Header.OrderHeader.PurchaseOrderNumber.text())
                                TsetPurposeCode('00') //OK
                                PurchaseOrderDate(String.format('%1$tY-%<tm-%<td', today))

                                if(exemptLines.size()>0){
                                    AcknowledgementType("AC")
                                }else {
                                    AcknowledgementType("AD")
                                }
                                // ShipmentQtyPackingCode('CTN25')
                                // ShipmentLadingQuantity("" + order.packagesShipped) // divide qty by 3 plus 1
                                // CarrierAlphaCode(order.shipinfo.carrService.contains("USPS") ? 'USPS' : 'UPSN')  //lookup
                            }


                        }

                        LineItems {
                            poData.LineItems.LineItem.OrderLine.each { line ->
                                println(line)
                                // println(it.get("LineSequenceNumber").text())
                                LineItem {
                                    OrderLine {

                                        LineSequenceNumber(line.LineSequenceNumber.text())
                                        BuyerPartNumber(line.BuyerPartNumber.text())
                                        ConsumerPackageCode(line.ConsumerPackageCode.text())
                                        OrderQty(line.OrderQty.text())
                                        OrderQtyUOM(line.OrderQtyUOM.text())
                                        PurchasePrice(line.PurchasePrice.text())
                                        PurchasePriceBasis("NT")

                                    }
                                    LineItemAcknowledgement {
                                        //check for exempt lines
                                        if(exemptLines.containsKey(line.ConsumerPackageCode.text())){
                                           def lie = exemptLines.get(line.ConsumerPackageCode.text())
                                            //determine type of exemption
                                            if(lie.exemptionCode.equals("quantity")&&lie.exemptionValue.equals("short ship")){
                                                ItemStatusCode("IQ")
                                                ItemScheduleQty(Integer.parseInt(line.OrderQty.text())-Integer.parseInt(lie.qty))
                                                ItemScheduleQualifier("068")
                                                ItemScheduleUOM("EA")
                                                ItemScheduleDate(String.format('%1$tY-%<tm-%<td', order.shipinfo.getScheduledShipDate()))

                                            }else{
                                                ItemStatusCode("IR")
                                                ItemScheduleQty(line.OrderQty.text())

                                                ItemScheduleUOM("EA")

                                            }


                                        }else{


                                        ItemStatusCode("IA")
                                        ItemScheduleQty(line.OrderQty.text())
                                        ItemScheduleQualifier("068")
                                        ItemScheduleUOM("EA")
                                        ItemScheduleDate(String.format('%1$tY-%<tm-%<td', order.shipinfo.getScheduledShipDate()))
                                        }

                                    }
                                    Date {
                                        DateTimeQualifier1("067")
                                        def earlyDate = poData.Header.Date.find {
                                            it.DateTimeQualifier1.text() == '064'
                                        }
                                        Date1(earlyDate.Date1.text())
                                    }
                                }


                            }
                        }
                        Summary {
                            TotalLineItemNumber(poData.Summary.TotalLineItemNumber.text())
                            int total = 0;
                            for (OwdLineItem item : order.lineitems) {
                                total += item.getQuantityActual();
                            }
                            TotalQuantity(total)


                        }

                    }

                }

        // throw new Exception("fix line number lookup from PO!!!")
        return writer.toString()

    }

    def static Map<String,lineItemExemptions> loadExemptionFromOrderId(String orderId){
        Map<String,lineItemExemptions> lines = new TreeMap<>()
        String sql = "select * from owd_line_item_exemptions where order_fkey = :orderId"
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("orderId",orderId);
        List l = q.list();
        if(l.size()>0) {

            for (Object row : l) {
                Map data = (Map) row;
                def lie = new lineItemExemptions()
                lie.setOrder_fkey(data.get("order_fkey").toString())
                lie.setInventory_num(data.get("inventory_num").toString())
                lie.setVendor_sku(data.get("vendor_sku").toString());
                lie.setExemptionCode(data.get("exemptionCode").toString())
                lie.setExemptionValue(data.get("exemptionValue").toString())
                lie.setQty(data.get("qty").toString())
                lie.setMerchant_line_number(data.get("merchant_line_number").toString())
                lines.put(lie.getVendor_sku(), lie);


            }

        }
        return lines;
    }


    def static String getAmazonARNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIAmazonARN)){
                return tag.getTagValue();

            }

        }
        return ""
    }

    def static String getZapposDNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIZapposDN)){
                return tag.getTagValue();

            }

        }
        return ""
    }




}
