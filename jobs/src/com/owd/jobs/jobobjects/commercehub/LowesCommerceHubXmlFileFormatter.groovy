package com.owd.jobs.jobobjects.commercehub

import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.business.order.OrderUtilities
import com.owd.core.business.order.beans.lineItemExemptions
import com.owd.core.managers.FacilitiesManager
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdFacility
import com.owd.hibernate.generated.OwdLineItem
import groovy.xml.StreamingMarkupBuilder
import org.hibernate.Criteria
import org.hibernate.Query

import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.format.DateTimeFormatter


@groovy.util.logging.Log4j2
class LowesCommerceHubXmlFileFormatter {


    public enum CommerceHubXmlFileType {
        INVENTORY, STATUS_RECEIVED, STATUS_SHIPPED, STATUS_REJECTED, RETURN, INVOICE
    };

    private final Calendar cal
    private final String accountName
    private final String remoteFolder
    private final CommerceHubXmlFileType fileType
    private int clientId;
    private String errorStr
    private String errorDataStr
    private String originalPoXml
    private double stockPercentage = 1.0
    private boolean kIncludeShipFrom = false;
    private boolean UseUPCLookup = false;
    private boolean includeWarehouseBreakout = false;
    private boolean includeUPCValueInInventory = false;
    private boolean includeUnitCost = false;
    private boolean includeVendorHeader = false;
    private boolean includeSsccOnDropship = false;

    private String vendorID;

    private String expectedShipDate;


    private static Map<String, String> outboundShipMap

    static {
        outboundShipMap = new TreeMap<String, String> ( )

        outboundShipMap.put ( "FedEx 2Day", "FEDX_SE" );
        outboundShipMap.put ( "FedEx Express Saver", "FEDX_3D" );
        outboundShipMap.put ( "FedEx Ground", "FEDX_CG" );
        outboundShipMap.put ( "FedEx Home Delivery", "FEDX_09" );
        outboundShipMap.put ( "FedEx Standard Overnight", "FEDX_ND" );
        outboundShipMap.put ( "FedEx Priority Overnight", "FEDX_NM" );
        outboundShipMap.put ( "FedEx SmartPost", "FXSP" );
        outboundShipMap.put ( "LTL", "LTL" );
        outboundShipMap.put ( "UPS 2nd Day Air", "UPSN_SE" );
        outboundShipMap.put ( "UPS 3 Day Select", "UPSN_3D" );
        outboundShipMap.put ( "UPS Ground", "UPSN_CG" );
        outboundShipMap.put ( "UPS Next Day Air", "UPSN_ND" );
        outboundShipMap.put ( "UPS Next Day Air Saver", "UPSN_PM" );
        outboundShipMap.put ( "USPS Priority Mail", "USPS_PB" );
        outboundShipMap.put ( "USPS Parcel Select Nonpresort", "USPS_ST" );
        outboundShipMap.put( "UPS SurePost Less than 1 lb","UPSN_ST_MW");
        outboundShipMap.put( "UPS SurePost 1 lb or Greater","UPSN_ST");
    }
    double getStockPercentage() {
        return stockPercentage
    }

    void setStockPercentage(double stockPercentageGiven) {
        if(stockPercentageGiven<=0.00 || stockPercentageGiven>1.00)
        {
            throw new Exception("stock level percentage must be greater than zero and not greater than 1")
        }
        this.stockPercentage = stockPercentageGiven
    }

    int getClientId() {
        return clientId
    }

    void setClientId(int clientId) {
        this.clientId = clientId
    }

    OrderStatus getCurrentOrderStatus() {
        return currentOrderStatus
    }

    void setCurrentOrderStatus(OrderStatus currentOrderStatus) {
        this.currentOrderStatus = currentOrderStatus
    }
    private OrderStatus currentOrderStatus

    def orderSrc

    def getOrderSrc() {
        return orderSrc
    }

    void setOrderSrc(orderSrc) {
        this.orderSrc = orderSrc
    }

    String batchRef = "";

    String getBatchRef() {
        return batchRef
    }

    void setBatchRef(String batchRef) {
        this.batchRef = batchRef
    }

    String getOriginalPoXml() {
        return originalPoXml
    }

    void setOriginalPoXml(String originalPoXml) {
        this.originalPoXml = originalPoXml
    }

    public LowesCommerceHubXmlFileFormatter(String accountName, String remoteFolder, CommerceHubXmlFileType type) {
        this.remoteFolder = remoteFolder
        this.accountName = accountName

        cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))

        fileType = type

    }

    public String getRemotePath() {
        switch (fileType) {
            case CommerceHubXmlFileType.STATUS_RECEIVED:
                return "/incoming/acknowledgment/"+remoteFolder+"/"
                break;
            case CommerceHubXmlFileType.STATUS_SHIPPED:
            case CommerceHubXmlFileType.STATUS_REJECTED:
                return "/incoming/confirms/"+remoteFolder+"/"
                break;
            case CommerceHubXmlFileType.RETURN:
                return "/incoming/return/"+remoteFolder+"/"
                break;
            case CommerceHubXmlFileType.INVENTORY:
                return "/incoming/inventory/"+remoteFolder+"/"
                break;
            case CommerceHubXmlFileType.INVOICE:
                return "/incoming/invoice/"+remoteFolder+"/"
                break;

            default:
                throw new Exception("Bad filetype : " + fileType)

        }
    }

    public String getFileName() {
        switch (fileType) {
            case CommerceHubXmlFileType.STATUS_RECEIVED:
                return "HUB" + cal.getTimeInMillis() + ".ack"
                break;

            case CommerceHubXmlFileType.STATUS_SHIPPED:
            case CommerceHubXmlFileType.STATUS_REJECTED:
                return "HUB" + cal.getTimeInMillis() + ".confirm"
                break;
            case CommerceHubXmlFileType.RETURN:
                return "HUB" + cal.getTimeInMillis() + ".return"
                break;


            case CommerceHubXmlFileType.INVENTORY:
                return "HUB" + cal.getTimeInMillis() + ".inv"
                break;
            case CommerceHubXmlFileType.INVOICE:
                return "HUB" + cal.getTimeInMillis() + ".invoice"
                break;
            default:
                throw new Exception("Bad filetype : " + fileType)

        }
    }

    public static void main(String[] args) {


    }


    public String getFileData() {


        def builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def fileXml = {
            mkp.xmlDeclaration()

            switch (fileType) {
                case CommerceHubXmlFileType.RETURN:

                    ReturnMessageBatch() {
                        partnerID(name: accountName, roleType: "vendor")

                        hubReturn()
                                {
                                    participatingParty(name: remoteFolder, roleType: "merchant", participationCode: "To:", remoteFolder)
                                    partnerTrxID(getCurrentOrderStatus().OWDorderReference)
                                    partnerTrxDate(cal.getTime().format('yyyyMMdd'))
                                    poNumber(getCurrentOrderStatus().po_num)


                                    for (LineItem line : (Vector<LineItem>) getCurrentOrderStatus().items) {
                                        println '***ref:' + line.client_ref_num
                                        hubAction(transactionItemID: line.line_item_id) {
                                            action('v_return')
                                            actionCode('changed_mind')
                                            merchantLineNumber(line.client_ref_num)
                                            trxQty(line.quantity_request)
                                            returnDetailLink(packageDetailID: 'P'+cal.getTimeInMillis())
                                        }
                                    }
                                    returnDetail(packageDetailID: 'P'+cal.getTimeInMillis()) {
                                        returnDate(cal.getTime().format('yyyyMMdd'))
                                    }
                                }
                        messageCount("1")
                    }
                    break;


                case CommerceHubXmlFileType.STATUS_RECEIVED:
                    // def outFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    def outFormat = new SimpleDateFormat("yyyyMMdd")
                    outFormat.timeZone = TimeZone.getTimeZone('GMT')
                    Map<String, lineItemExemptions> exemptLines = loadExemptionFromOrderId(getCurrentOrderStatus().order_id);


                    PO_Acknowledgements('batch-number': batchRef) {
                        partnerID(accountName)

                        PO_Acknowledgement('ack-type': "initial")
                                {
                                    messageControlNumber('ACK'+cal.getTimeInMillis())
                                    originatingSystemTrxId(trxDate: outFormat.format(cal.getTime()))
                                    participatingParty(role: "merchant", participationCode: "To:", remoteFolder)

                                    poNumber(getCurrentOrderStatus().po_num)
                                    scheduledShipDate(getExpectedShipDate())

                                    for (LineItem line : getCurrentOrderStatus().items) {

                                        if(line.insertedItem == false) {

                                            lineitem_ack() {

                                                poLineNumber(line.client_ref_num)
                                                quantityOpen(line.quantity_request)
                                                if (line.quantity_actual > 0) {
                                                    action() {
                                                        quantity(line.quantity_actual)
                                                        //You have a CHOICE of the next 3 items at this level
                                                        accept("")
                                                        //   cancel(reason: "", "")
                                                        //   seal(trackable: "", "")
                                                    }
                                                }
                                                if (line.quantity_actual != line.quantity_request) {
                                                    if (useUPCLookup) {

                                                        if (exemptLines.containsKey(line.getInventory().barcode_no)) {
                                                            com.owd.core.business.order.beans.lineItemExemptions lie = exemptLines.get(line.getInventory().barcode_no);
                                                            action() {
                                                                quantity(lie.exemptionValue)
                                                                //You have a CHOICE of the next 3 items at this level
                                                                // accept("")
                                                                cancel(reason: lie.exemptionCode, "")
                                                                //   seal(trackable: "", "")
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        messageCount("1")
                    }


                    break;
                case CommerceHubXmlFileType.STATUS_REJECTED:

                    // We only Reject when we have not valid line's. Use Order Xml to Cancel
                    def oSrc = getOrderSrc();

                    //Map<String, String> tagmap = order.getTagMap()
                    log.debug(oSrc.@'transactionID');


                    def outFormat = new SimpleDateFormat("yyyyMMdd")
                    // outFormat.timeZone = TimeZone.getTimeZone('GMT')


                    ConfirmMessageBatch('batchNumber': getBatchRef()) {
                        partnerID('name': accountName, 'roleType': "vendor")

                        hubConfirm('transactionID': oSrc.@'transactionID')
                                {
                                    participatingParty('name': remoteFolder, 'roleType': "merchant", 'participationCode': "To:", remoteFolder)
                                    partnerTrxID(oSrc.@'transactionID')
                                    partnerTrxDate(outFormat.format(cal.getTime()))
                                    poNumber(oSrc.poNumber.text())


                                    oSrc.lineItem.each() { item ->
                                        // println '***ref:' + line.client_ref_num
                                        hubAction(transactionItemID: item.lineItemId.text()) {
                                            action('v_cancel')
                                            actionCode('merchant_request')
                                            merchantLineNumber(item.merchantLineNumber.text())
                                            trxQty(item.qtyOrdered.text())
                                        }
                                    }

                                }
                        messageCount("1")
                    }

                    break;
                case CommerceHubXmlFileType.STATUS_SHIPPED:
                    OrderStatus order = getCurrentOrderStatus()

                    if (includeSsccOnDropship) {
                        OrderUtilities.addSSCCToPackages(Integer.parseInt(order.order_id));
                    }

                    Map<String, String> tagmap = order.getTagMap()
                    def jcpOrder = new XmlSlurper().parseText(tagmap.get('COMMERCEHUB_PO_XML'));

                    Map<String,Float> billedItemCosts = new HashMap<String,Float>();
                    Map<String, lineItemExemptions> exemptLines = loadExemptionFromOrderId(order.order_id)


                    jcpOrder.lineItem.each() { item ->

                        println "LINE SKU: " + item.merchantSKU
                        println "LINE QTY: " + item.qtyOrdered

                        String lineRef = item.merchantLineNumber
                        String priceStr = item.unitCost
                        billedItemCosts.put(lineRef,Float.parseFloat(priceStr))
                    }

                    Float totalInvoiceCost = 0.00f;
                    for(LineItem line:order.items){

                        if(line.insertedItem == false) {
                            if (billedItemCosts.containsKey(line.client_ref_num)) {
                                totalInvoiceCost += billedItemCosts.get(line.client_ref_num)
                            }
                        }
                    }

                    def outFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    outFormat.timeZone = TimeZone.getTimeZone('GMT')
                    def now = new Date()


                    // mkp.yield "\r\n"
                    ConfirmMessageBatch() { //batchNumber: tagmap.get('COMMERCEHUB_BATCHREF')) {
                        partnerID(accountName, roleType: "vendor")

                        hubConfirm()
                                {
                                    participatingParty(roleType: "merchant", participationCode: "To:", remoteFolder)

                                    partnerTrxID(order.OWDorderReference)
                                    partnerTrxDate(now.format('yyyyMMdd'))
                                    poNumber(order.po_num)
                                    // trxBalanceDue(totalInvoiceCost)
                                    // trxData() {
                                     //   vendorsInvoiceNumber(order.OWDorderReference.drop(order.OWDorderReference.size() - 7))
                                    //}


                                    for (com.owd.core.business.order.Package p : (Vector<com.owd.core.business.order.Package>) order.packages) {

                                        Map<OwdLineItem, Integer> itemMap = com.owd.core.business.order.Package.getOwdLineItemListforPackage(p.order_track_id)

                                        log.debug "Got " + itemMap.keySet().size() + " lines for package"
                                        itemMap.keySet().each { owdLineItem ->

                                                println '***ref:' + owdLineItem.getCustRefnum()
                                                if (owdLineItem.getCustRefnum().trim().length() > 0) {
                                                    if (itemMap.get(owdLineItem) > 0) {

                                                        if(owdLineItem.isInsert == 0) {

                                                            hubAction() {
                                                                action('v_ship')
                                                                merchantLineNumber(owdLineItem.getCustRefnum())
                                                                trxQty(itemMap.get(owdLineItem))
                                                                packageDetailLink(packageDetailID: p.order_track_id)
                                                            }
                                                        }
                                                    }
                                                }


                                        }
                                    }

                                    exemptLines.values().each { lie ->
                                        hubAction(transactionItemID: lie.getId()) {
                                            action('v_cancel')
                                            actionCode('merchant_request')
                                            merchantLineNumber(lie.merchant_line_number)
                                            trxQty(lie.getQty())
                                        }

                                    }


                                    for (com.owd.core.business.order.Package pa : order.packages) {

                                        packageDetail(packageDetailID: pa.order_track_id) {
                                            shipDate(now.format('yyyyMMdd'))
                                            String sscc = pa.getSSCCCodeForPackage(pa.order_track_id)
                                            if (sscc.length() > 0) {
                                                containerID("00" + sscc)

                                                List dimensions = pa.getHwdDimensionsOfPackage(Integer.parseInt(pa.order_track_id))
                                                int pDepth = (int)dimensions.get(0);
                                                int pWidth = (int)dimensions.get(1);
                                                int pHeight = (int)dimensions.get(2);

                                                lengthDepth(dimensionUnit: "IN", pDepth)
                                                width(dimensionUnit: "IN", pWidth)
                                                height(dimensionUnit: "IN", pHeight)

                                            }

                                            serviceLevel1(outboundShipMap.get(order.shipping.carr_service))
                                            trackingNumber(pa.tracking_no)
                                            if (kIncludeShipFrom) {
                                                shipFrom(vendorShipID: accountName)
                                            }
                                            shippingWeight(weightUnit: 'LB', pa.weight)

                                        }
                                    }
                                    if (kIncludeShipFrom) {
                                        vendorShipInfo(vendorShipID: accountName) {
                                            OwdFacility facility = FacilitiesManager.getFacilityForCode(order.shipLocation)
                                            name1(facility.getCity() + " " + facility.getFacilityCode())
                                            address1(facility.address)
                                            city(facility.city)
                                            state(facility.state);
                                            country("USA")
                                            postalCode(facility.zip)
                                        }

                                    }
                                }
                        messageCount("1")
                    }
                    break;


                case CommerceHubXmlFileType.INVENTORY:

                    List<List<String>> itemList = new ArrayList<List<String>>();
                    ResultSet rs;

                    if(useUPCLookup){

                        rs = HibernateSession.getResultSet("SELECT\n" +
                                "    dbo.owd_inventory.inventory_num,\n" +
                                "    owd_inventory.inventory_num,\n" +
                                "    owd_inventory.inventory_num,\n" +
                                "    dbo.owd_inventory.upc_code,\n" +
                                "    dbo.owd_inventory_oh.qty_on_hand,\n" +
                                "    dbo.owd_inventory.description,0.0\n" +
                                "FROM\n" +
                                "    dbo.owd_inventory\n" +
                                "INNER JOIN\n" +
                                "    dbo.owd_inventory_oh\n" +
                                "ON\n" +
                                "    (\n" +
                                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_oh.inventory_fkey)\n" +
                                "WHERE\n" +
                                "    dbo.owd_inventory.client_fkey = "+getClientId()+"\n" +
                                "AND dbo.owd_inventory.is_active = 1\n" +
                                "AND dbo.owd_inventory.is_auto_inventory = 0\n" +
                                "AND dbo.owd_inventory.upc_code <> ''");


                    }else{
                        rs = HibernateSession.getResultSet("select jcpref,vendorref,owdsku,upc,isnull(qty_on_hand,0) as qty,i.description,isnull(jcpenney_itemreference.price,0.0) as price from jcpenney_itemreference" +
                                " left outer join owd_inventory i join owd_inventory_oh h on h.inventory_fkey=inventory_id on i.inventory_num=owdsku and jcp_client_fkey = client_fkey where jcp_client_fkey="+getClientId() +" and vendor = '"+remoteFolder+"'");

                    }
                    while(rs.next())
                    {

                        itemList.add(Arrays.asList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
                    }
                    rs.close();


                    int productCount = 0;

                    advice_file() {
                        advice_file_control_number('INV'+cal.getTimeInMillis())
                        if(includeVendorHeader){
                            vendor(vendorID)
                        }
                        vendorMerchID(remoteFolder)

                        for (List<String> itemInfo:itemList) {
                            product() {
                                vendor_SKU(itemInfo.get(0))

                                String quantityOnHand = itemInfo.get(4);
                                qtyonhand(quantityOnHand)

                                if (quantityOnHand == "0")
                                    available("NO")
                                else
                                    available('YES')
                                if (includeUPCValueInInventory) {
                                    UPC(itemInfo.get(3))
                                }
                                description(itemInfo.get(5))

                                if (quantityOnHand == "0") {
                                    LocalDate fdonm = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth())
                                    next_available_date(fdonm.format(DateTimeFormatter.ofPattern('yyyyMMdd')))
                                }

                                if (includeUnitCost) {
                                    unit_cost(itemInfo.get(6))
                                }

                                merchantSKU(itemInfo.get(1))

                                if (includeWarehouseBreakout) {
                                    warehouseBreakout() {
                                        warehouse("warehouse-id": "OWD") {
                                            qtyonhand(itemInfo.get(4))
                                        }
                                    }
                                }
                            }
                            productCount++
                        }
                        advice_file_count(productCount)
                    }




                    break;
                default:
                    throw new Exception("Bad filetype : " + fileType)

            }
        }

        String ogXml = builder.bind(fileXml).toString()

        Node xmlNode = new XmlParser().parseText(ogXml);
        StringWriter xmlOutput = new StringWriter()

        XmlNodePrinter xmlNodePrinter = new XmlNodePrinter(new PrintWriter(xmlOutput), " ")
        xmlNodePrinter.with {
            preserveWhitespace = true
            expandEmptyElements = false
            quote = "'" // Use single quote for attributes
        }
        xmlNodePrinter.print ( xmlNode )

        return "<?xml version='1.0' encoding='UTF-8'?>\r\n" + xmlOutput.toString ( )


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
                lie.setId(data.get("id").toString());
                lines.put(lie.getVendor_sku(), lie);


            }

        }
        return lines;
    }

    boolean getkIncludeShipFrom() {
        return kIncludeShipFrom
    }

    void setkIncludeShipFrom(boolean kIncludeShipFrom) {
        this.kIncludeShipFrom = kIncludeShipFrom
    }

    boolean getUseUPCLookup() {
        return UseUPCLookup
    }

    void setUseUPCLookup(boolean useUPCLookup) {
        UseUPCLookup = useUPCLookup
    }

    boolean getIncludeWarehouseBreakout() {
        return includeWarehouseBreakout
    }

    void setIncludeWarehouseBreakout(boolean includeWarehouseBreakout) {
        this.includeWarehouseBreakout = includeWarehouseBreakout
    }

    boolean getIncludeUPCValueInInventory() {
        return includeUPCValueInInventory
    }

    void setIncludeUPCValueInInventory(boolean includeUPCValueInInventory) {
        this.includeUPCValueInInventory = includeUPCValueInInventory
    }

    boolean getIncludeUnitCost() {
        return includeUnitCost
    }

    void setIncludeUnitCost(boolean includeUnitCost) {
        this.includeUnitCost = includeUnitCost
    }

    boolean getIncludeVendorHeader() {
        return includeVendorHeader
    }

    void setIncludeVendorHeader(boolean includeVendorHeader) {
        this.includeVendorHeader = includeVendorHeader
    }

    void setIncludeSsccOnDropship(boolean includeSscc) {
        this.includeSsccOnDropship = includeSscc;
    }

    String getVendorID() {
        return vendorID
    }

    void setVendorID(String vendorID) {
        this.vendorID = vendorID
    }

    void setExpectedShipDate(String value) {
        this.expectedShipDate = value
    }

    String getExpectedShipDate() {
        return expectedShipDate
    }
}
