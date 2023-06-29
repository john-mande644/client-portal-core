package com.owd.jobs.jobobjects.retailops

import com.owd.core.business.order.beans.lineItemPackageData
import com.owd.hibernate.generated.OwdLineItem
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DuplicateOrderException
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.Client
import com.owd.core.business.OwdApiClient
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.jobs.jobobjects.AbstractIntegrationApi
import com.owd.LogableException
import org.apache.xpath.XPathAPI
import groovy.json.JsonBuilder
import groovyx.net.http.RESTClient
import org.apache.commons.collections.MapUtils
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext
import com.owd.core.business.order.Package
import org.hibernate.type.LongType
import org.hibernate.type.StringType
import org.w3c.dom.Document

import java.sql.PreparedStatement
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.sql.ResultSet

/**
 * Created by stewartbuskirk1 on 5/19/14.
 */
class RetailOpsApi extends AbstractIntegrationApi {
    private final static Logger log =  LogManager.getLogger();

    def String vendorId = '4'
    def String authCode = 'LIV0MLxL-t8RUD3o7cqzGMZZwFwZkZGqY5nMEsFJf'
    int clientId = 55
    boolean testing = false
    private boolean importPrices = false;
    private boolean channelNametoGroupName = false;
    static Map<Integer, String> methodMap = new HashMap<Integer, String>();


    def final endpoint = new RESTClient("https://api.gudtech.com")
    def final ackEndpoint = new RESTClient("https://api.gudtech.com")
    def static final updateInventoryUrl = "/product/externalsku/update~2.json" //Inventory Advise
    def static final getShipmentUrl = "/fulfillment/shipment/fetch~1.json"  //Shipment Retrieval
    def static final ackShipmentUrl = "/fulfillment/shipment/marktransmitted~1.json" //Shipment Acknowledgement
    def static final getShipMethodsUrl = "/constant/ship/carrierclass/fetch~1.json"  //Retrieve Ship Carrier Class
    def static final reportShipmentUrl = "/fulfillment/shipment/complete~1.json"     //Shipment Completion
    def static final getAsnUrl = "/fulfillment/asn/fetch~1.json"     //ASN retrieval
    def static final ackAsnUrl = "/fulfillment/asn/acknowledge~1.json"     //ASN retrieval
    def static final getSkusUrl = "/product/sku/get~1.json"     //ASN retrieval
    def static final reportReciptUrl = "/inventory/externallot/create~1.json"     //Report receipt
    def static final productSearchUrl = "/product/sku/search~1.json" //product Search
    def static final getOrderItemsUrl = "/Customer/Order/Item/list~1.json" //Get Items on order for prices

    static {
        //  methodMap.put(1, "UPS Ground");    // free
        //  methodMap.put(2, "UPS Ground");    //standard
        methodMap.put(5, "FedEx 2Day");
        methodMap.put(6, "FedEx Express Saver");
        methodMap.put(7, "FedEx Ground");
        methodMap.put(8, "FedEx Home Delivery");
        methodMap.put(10, "FedEx International Economy");
        methodMap.put(113, "FedEx Ground");
        methodMap.put(9, "FedEx International Priority");
        methodMap.put(3, "FedEx Priority Overnight");
        methodMap.put(71, "FedEx SmartPost Parcel Select");
        methodMap.put(74, "FedEx SmartPost Standard Mail");
        methodMap.put(4, "FedEx Standard Overnight");
        methodMap.put(114, "LTL");
        methodMap.put(60, "UPS 2nd Day Air");
        methodMap.put(59, "UPS 2nd Day Air A.M.");
        methodMap.put(62, "UPS Ground");
        methodMap.put(57, "UPS Next Day Air");
        methodMap.put(58, "UPS Next Day Air Saver");
        methodMap.put(67, "UPS Standard Canada");
        methodMap.put(63, "UPS Worldwide Expedited");
        methodMap.put(64, "UPS Worldwide Express Plus");
        methodMap.put(65, "UPS Worldwide Express");
        methodMap.put(66, "UPS Worldwide Express Saver");
        methodMap.put(38, "USPS Bnd Prt Mtr Single Piece");
        methodMap.put(50, "USPS Priority Mail Express");
        methodMap.put(39, "USPS First-Class Mail");
        methodMap.put(37, "USPS Media Mail Single-Piece");
        methodMap.put(34, "USPS Priority Mail");
        methodMap.put(35, "USPS Parcel Select Nonpresort");
    }

    //for testing, uses test IDs
    RetailOpsApi() {
        setHeaders()
    }

    RetailOpsApi(int clientid, String vendorid, String authcode) {
        vendorId = vendorid
        clientId = clientid
        authCode = authcode
        setHeaders()
    }


    public  RESTClient getNewEndpoint()
    {
        def ep = new RESTClient("https://api.gudtech.com")
        ep.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        httpRequest.setHeader('apikey', authCode)
                    }
                });

         return ep;
    }

    public def getOrderItemList(String orderId){
        endpoint.post(path: getOrderItemsUrl,
                body: [order_id          : orderId,

                       with_returns       : 1],
                requestContentType: 'application/json'
        ) { resp, reader ->

            println reader;

            return reader;
        }

    }

    public static void main(String[] args) {


        def tester = new RetailOpsApi(626, "1975", "u9OLdvwM-Nulz3XwDKWn7yqY1XRxL3HEzZ0yQ2LTH")
        tester.reportShipment(new OrderStatus(("21884242")));




        // def tester = new RetailOpsApi();    //test account
      //  tester.importCurrentOrders();
      //  tester.reportShipment(new OrderStatus("16231110"));
        //  tester.getAsns(false);

         // println tester.getShipMethods()
      //  tester.searchProducts();

        //  tester.acknowledgeOrder("123")

        //  tester.acknowledgeOrder(321)
      //  tester.updateInventory()

    }

    private void setHeaders() {
        endpoint.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        httpRequest.setHeader('apikey', authCode)
                    }
                });

        ackEndpoint.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        httpRequest.setHeader('apikey', authCode)
                    }
                });
    }

    public String searchProducts() {
        endpoint.post(path: searchProducts(),
                requestContentType: 'application/json',
                body: [status:"active"]) { resp, reader ->

            println reader

            //todo determine intersection with owd ship method names

         /*   for (Map ship : reader.records) {
                println ship
            }*/
        }
    }

    public String getShipMethods() {
        endpoint.post(path: getShipMethodsUrl,
                requestContentType: 'application/json',
                body: [all: '1']) { resp, reader ->

            println reader

            //todo determine intersection with owd ship method names

            for (Map ship : reader.records) {
                println ship.id + ':' + ship.name
            }
        }
    }


    public void updateInventory() {

        def idata = HibernateSession.currentSession().createSQLQuery("select i.client_fkey, i.inventory_id as iid, i.inventory_num as sku,(case when qty_on_hand<0 then 0 else qty_on_hand END) as omoh\n" +
                "\n" +
                "                from owd_inventory i (NOLOCK)  join owd_inventory_oh oh (NOLOCK) on oh.inventory_fkey=i.inventory_id  \n" +
                "               \n" +
                "                " +
                "                                                                     where i.client_fkey=" + clientId + " and is_auto_inventory = 0\n" +
                "                                   group by i.client_fkey, i.inventory_id, i.inventory_num,qty_on_hand\n" +
                "                       order by i.inventory_num")
                .addScalar("sku", new StringType())
                .addScalar("omoh", new LongType())
                .list().collate(1000);
        //must collate into 1000-item groups due to max upload limitations on server side

        idata.each() { listgroup ->

            def json = new JsonBuilder()

            json {
                records(listgroup.collect { [vendor_id: vendorId, vpc: it[0], quantity: (it[1] < 0 ? 0 : it[1])] })
            }

            println(body: json.toPrettyString())

            if (json.toString()) {
                endpoint.post(path: updateInventoryUrl,
                        requestContentType: 'application/json',
                        body: json.toString()) { resp, reader ->

                    println reader
                }
            }
        }

    }
    public void updateInventoryTest() {

       List<Object[]> listgroup = new ArrayList<Object[]>()

        Object[] objAy = new Object[2];
        objAy[0]=""
        objAy[1]=123
        //must collate into 1000-item groups due to max upload limitations on server side


            def json = new JsonBuilder()

            json {
                records(listgroup.collect { [vendor_id: vendorId, vpc: it[0], quantity: (it[1] < 0 ? 0 : it[1])] })
            }

            println(body: json.toPrettyString())

            if (json.toString()) {
                endpoint.post(path: updateInventoryUrl,
                        requestContentType: 'application/json',
                        body: json.toString()) { resp, reader ->

                    println reader
                }
            }


    }


    public void acknowledgeOrder(String shipmentId, int estimateShipDays, int latestShipDays) {

        def now = new Date()

        def json = new JsonBuilder()

        def outFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
// Set the timezone for the output
        outFormat.timeZone = java.util.TimeZone.getTimeZone('GMT')



        json {
            records([id                 : shipmentId,
                     estimated_ship_date: outFormat.format(now + estimateShipDays),
                     latest_ship_date   : outFormat.format(now + latestShipDays)])
        }

        println json.toPrettyString()

        ackEndpoint.post(path: ackShipmentUrl,
                requestContentType: 'application/json',
                body: json.toString()) { resp, reader ->

            println reader
            if (reader.success == 1 || reader.toString().contains("ready") || reader.toString().contains("status Canceled") || reader.toString().contains("ineligible for this action")) {
                //ok
            } else {
                LogableException le = new LogableException((reader.toString()),
                        json.toPrettyString(),
                        clientId + '',
                        "Retail Ops ack order fail",
                        LogableException.errorTypes.UNDEFINED_ERROR);
            }
        }


    }

    public void acknowledgeAsn(String asnId) {

        def now = new Date()
        def json = new JsonBuilder()

        json {
            id([asnId])
        }

        println json.toPrettyString()

        ackEndpoint.post(path: ackAsnUrl,
                requestContentType: 'application/json',
                body: json.toString()) { resp, reader ->

            println reader

        }


    }


    public String reportShipment(OrderStatus ship) {

        def json = new JsonBuilder()

        Map<Package,List<lineItemPackageData> > packMap = new HashMap<Package, List<lineItemPackageData>>();

        List<Package> goodPacks = new ArrayList<Package>()

        int totalLines = 0
        ship.packages.toList().collect()
                {
                  //  println it
                    if (((Package) it).is_void.equals("0")) {
                        goodPacks.add(((Package) it))
                        packMap.put((Package) it, Package.getLineItemDataMapforPackage(Integer.parseInt(((Package) it).order_track_id)))
                    }
                }



        json(
                [id              : ship.po_num,
                 cancel_remainder: '1',
                 packages        : goodPacks.collect() { pack ->
                     [
                             ship_class_id  : translateOwdShipMethod(ship.shipping.carr_service), tracking_number: pack.tracking_no,
                             unit_count     : packMap.get(pack).collect { item ->
                                                    item.packageQty
                                                }.sum(),
                             weight         : pack.weight,
                             cost           : pack.total_billed,
                             items          : packMap.get(pack).collect { item ->
                                 [vpc: item.sku, quantity: item.packageQty]
                             }
                     ]
                 }

                ]
        )

      //  println json.toPrettyString()

        endpoint.post(path: reportShipmentUrl,
                requestContentType: 'application/json',
                body: json.toString()) { resp, reader ->

          //  println reader
            if (reader.ERRORCODE != null) {
                if (reader.ERRORCODE.equals("null") || reader.ERROR.contains("retired") || reader.ERROR.contains("status: shipped")) {
                    return "COMPLETE"
                }
               // println "SHIP:" + ship.orderReference + "-" + reader.ERROR + " (" + reader.ERRORCODE + ")"
                LogableException le = new LogableException((reader.toString()),
                        json.toPrettyString(),
                        clientId + '',
                        "Retail Ops report shipment",
                        LogableException.errorTypes.SHIPMENT_NOTIFICATION);

                return "ERROR:" + ship.orderReference + "-" + reader.ERROR + " (" + reader.ERRORCODE + ")"

            } else {
                return "COMPLETE"
            }

        }

        //  return "";
    }

    public String voidOrder(OrderStatus ship) {

        if(ship.getStatusString().equalsIgnoreCase("VOID")) {
            def json = new JsonBuilder()



            json(
                    [id              : ship.po_num,
                     cancel_remainder: '1',
                     packages        : []


                    ]
            )

              println json.toPrettyString()

            endpoint.post(path: reportShipmentUrl,
                    requestContentType: 'application/json',
                    body: json.toString()) { resp, reader ->

                //  println reader
                if (reader.ERRORCODE != null) {
                    if (reader.ERRORCODE.equals("null") || reader.ERROR.contains("retired") || reader.ERROR.contains("status: shipped")) {
                        return "COMPLETE"
                    }
                    // println "SHIP:" + ship.orderReference + "-" + reader.ERROR + " (" + reader.ERRORCODE + ")"
                    LogableException le = new LogableException((reader.toString()),
                            json.toPrettyString(),
                            clientId + '',
                            "Retail Ops report shipment",
                            LogableException.errorTypes.SHIPMENT_NOTIFICATION);

                    return "ERROR:" + ship.orderReference + "-" + reader.ERROR + " (" + reader.ERRORCODE + ")"

                } else {
                    return "COMPLETE"
                }

            }
        }else
        {
            throw new Exception(" OWD order must be in Void status to send void message to RetailOps")
        }

        //  return "";
    }

    public void reportReceipts() {
        System.out.println("hello motto");

        try {
        ResultSet rs = HibernateSession.getResultSet("select  receive_item_id,substring(transaction_num,  PATINDEX('OWDRCV%',ISNULL(transaction_num,''))+7,999) as rid,r.inventory_num,r.is_void,ri.qty_receive,asn.client_ref,asn.client_po,ai.description\n" +
                "\n" +
                "                    FROM\n" +
                "                        owd_inventory i\n" +
                "                    JOIN\n" +
                "                        owd_receive_item r\n" +
                "                        join receive_item ri \n" +
                "                                join asn_items ai on ai.id=asn_item_fkey \n" +
                "                        on ri.inventory_fkey=r.inventory_id and ri.receive_fkey=(select convert(bigint,substring(transaction_num,  PATINDEX('OWDRCV-%',ISNULL(transaction_num,''))+7,999)) from owd_receive rr where rr.receive_id=r.receive_fkey and isnumeric(substring(transaction_num, PATINDEX('OWDRCV-%',ISNULL(transaction_num,''))+7,999))=1 )\n" +
                "                    JOIN\n" +
                "                        owd_receive\n" +
                "                                join receive \n" +
                "                                        join asn on asn.id=receive.asn_fkey\n" +
                "                                on isnumeric(substring(transaction_num,  PATINDEX('OWDRCV-%',ISNULL(transaction_num,''))+7,999))=1 and receive.id=convert(bigint,substring(transaction_num,  PATINDEX('OWDRCV-%',ISNULL(transaction_num,''))+7,999))\n" +
                "                    ON\n" +
                "                        r.receive_fkey=receive_id and type='receive'\n" +
                "                    ON\n" +
                "                        i.inventory_id=r.inventory_id\n" +
                "                    AND r.sys_reported=0\n" +
                "                    AND  (\n" +
                "                            PATINDEX('OWDRCV-%',ISNULL(transaction_num,''))=1\n" +
                "                        AND quantity>0)\n" +
                "                    WHERE\n" +
                "                        i.client_fkey="+clientId+" and  type='Receive'\n" +
                "                    order by receive_id desc")


            PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_receive_item set sys_reported=1 where receive_item_id=?")

            while (rs.next()) {
                String itemdesc = rs.getString('description')
                int itemid = 0
                if (itemdesc.contains(':')) {
                    itemid = Integer.parseInt(itemdesc.substring(0, itemdesc.indexOf(':')))
                }
                String ponum = rs.getString('client_po')
                String asnRef = rs.getString('client_ref')

                String skustr = rs.getString('inventory_num')
                int units = rs.getInt('qty_receive')

                boolean clearPO = false
                if (units > 0) {
                    def json = new JsonBuilder()

                    json {


                        records(
                                [[po               : (asnRef.startsWith("RO:") ? ponum : null),
                                  asn_item_id      : (0 == itemid ? null : itemid),
                                  sku              : skustr,
                                  vpc              : skustr,
                                  received_quantity: units]]

                        )

                        vendor_id(vendorId)
                    }

                    println json.toPrettyString()

                    ackEndpoint.post(path: reportReciptUrl,
                            requestContentType: 'application/json',
                            body: json.toString()) { resp, reader ->

                        println reader



                        if (reader.success == 1 || reader.ERROR.contains("invalid sku")) {
                            ps.setInt(1, rs.getInt('receive_item_id'))
                            ps.executeUpdate()
                            HibUtils.commit(HibernateSession.currentSession())
                        } else if (reader.ERROR.contains("invalid po") || reader.ERROR.contains("for field idx")) {
                            clearPO = true;
                        } else {
                            LogableException le = new LogableException((reader.toString()),
                                    json.toPrettyString(),
                                    clientId + '',
                                    "Retail Ops report receipt",
                                    LogableException.errorTypes.UNDEFINED_ERROR);
                        }
                    }

                    if (clearPO) {
                        json {


                            records(
                                    [[po               : (null),
                                      asn_item_id      : (0 == itemid ? null : itemid),
                                      sku              : skustr,
                                      vpc              : skustr,
                                      received_quantity: units]]

                            )

                            vendor_id(vendorId)
                        }

                        println json.toPrettyString()

                        ackEndpoint.post(path: reportReciptUrl,
                                requestContentType: 'application/json',
                                body: json.toString()) { resp, reader ->

                            println reader



                            if (reader.success == 1 || reader.ERROR.contains("invalid sku")) {
                                ps.setInt(1, rs.getInt('receive_item_id'))
                                ps.executeUpdate()
                                HibUtils.commit(HibernateSession.currentSession())
                            } else {
                                LogableException le = new LogableException((reader.toString()),
                                        json.toPrettyString(),
                                        clientId + '',
                                        "Retail Ops report receipt",
                                        LogableException.errorTypes.UNDEFINED_ERROR);
                            }
                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace()
            LogableException ex = new LogableException(e,"error Receipt Sync","",clientId+"","Retail OPs Receipt sync", LogableException.errorTypes.UNDEFINED_ERROR)
        }


    }


    public void syncSkus() {

        ResultSet rs = HibernateSession.getResultSet("select inventory_num, inventory_id from owd_inventory join owd_inventory_oh oh on oh.inventory_fkey=inventory_id\n" +
                "where client_fkey=525 \n" +
                "and qty_on_hand>0 and image_url is null order by inventory_id desc")
        ExecutorService exec = Executors.newFixedThreadPool(10); // 5 threads



        while(rs.next()) {
            println rs.getString(1)
            exec.submit(new SyncSkusTask(rs.getInt(2),rs.getString(1),this));
        }
        exec.shutdown();
        exec.awaitTermination(10, TimeUnit.MINUTES);


    }

    public void getAsns(boolean testFlag,List<String> additionalEmails,int startId) {

        def today = new Date()

        endpoint.post(path: getAsnUrl,
                body: [vendor_id: vendorId, limit: 999, start: startId],
                requestContentType: 'application/json'
        ) { resp, reader ->

            println reader.records.size() + ' asns found!'
            println reader.toString()


            reader.records.each() { asnSrc ->

                MapUtils.debugPrint(System.out, "ASN", asnSrc)
                String asnId = ''
                String asnPo = ''
                String asnType = ''
                try {
                    asnId = asnSrc.id
                    asnPo = asnSrc.po
                    asnType = asnSrc.type
                    println 'Type:' + asnType

                    List<String> badSkus = new ArrayList<String>()
                    boolean hasAnItem = false

                    def builder = new groovy.xml.StreamingMarkupBuilder()
                    builder.encoding = 'UTF-8'
                    def kitItemCreateRequest =
                            {
                                mkp.xmlDeclaration()
                                OWD_API_REQUEST(api_version: '2.0', client_authorization: OWDUtilities.encryptData("" + clientId), client_id: clientId, testing: testFlag ? 'TRUE' : 'FALSE') {
                                    OWD_ASN_CREATE_REQUEST() {
                                        REFERENCE('RO:' + asnSrc.id)
                                        PO_REFERENCE(asnSrc.po)
                                        SHIPPER(asnSrc.sender)
                                        EXPECTED_DATE((today + 7).format("yyyyMMdd"))
                                        CARRIER('Unknown')
                                        AUTORELEASE('1')
                                        NOTES('')
                                        //CARTONS()
                                        //PALLETS()
                                        ASNITEMS() {
                                            asnSrc.items.each() { item ->
                                                println "SKU:" + item.recipient_product_code
                                                if (!(item.recipient_product_code.equals("null") || item.recipient_product_code == null || item.recipient_product_code.equals(""))) {
                                                    if (!(LineItem.SKUExists(clientId + "", item.recipient_product_code))) {
                                                        badSkus.add(item.toString())
                                                    } else {


                                                        hasAnItem = true
                                                        ASNITEM() {
                                                            ASNITEM_SKU(item.recipient_product_code)
                                                            ASNITEM_EXPECTED(item.quantity)
                                                            ASNITEM_DESCRIPTION(item.id + ':' + item.sender_product_code + ':' + item.description)
                                                        }
                                                    }

                                                } else {
                                                    badSkus.add(item.toString())
                                                }
                                            }
                                        }
                                    }

                                }
                            }


                    println "sending"
                    println builder.bind(kitItemCreateRequest).toString()
                    if (badSkus.size() > 0) {
                        Vector<String> emails = new Vector<String>()
                        emails.add("owditadmin@owd.com")
                        for (String email:additionalEmails){
                            emails.add(email);

                        }
                        StringBuffer sb = new StringBuffer()
                        sb.append("\r\n\r\nASN/PO reference " + asnSrc.po + " (RetailOps ID: " + asnSrc.id + ") contained items with missing or unrecognized SKUs.")
                        if (!hasAnItem) {
                            sb.append("\r\n\r\nNo recognizable SKUs were found in this ASN. It has been acknowledged to RetailOps, but no ASN was created at OWD.")

                        } else {

                            sb.append("\r\n\r\nThis ASN has been created at OWD without the items listed below.")
                        }
                        sb.append("\r\n\r\nThe following items were not imported to OWD:\r\n\r\n")
                        for (String item : badSkus) {
                            sb.append(item + "\r\n")
                        }
                        Mailer.sendMail("Missing SKU on ASN import to OWD (" + asnSrc.po + ")", sb.toString(), emails.toArray(), "donotreply@owd.com")
                    }
                    if (hasAnItem) {
                        Document response = OwdApiClient.getServerAPIResponse(builder.bind(kitItemCreateRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
                        // OwdApiClient.verifyAPIResponse((Document) response, response.getDocumentElement());
                        OwdApiClient.prettyPrint((org.w3c.dom.Node) (response.getDocumentElement()), System.out);
                        if (!(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS"))) {
                            throw new Exception(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue())
                        }
                    }
                    acknowledgeAsn(asnId)

                } catch (Exception ex) {
                    LogableException le = new LogableException(ex.getMessage(),
                            asnId + '/' + asnPo,
                            clientId + '',
                            this.getClass().getName(),
                            LogableException.errorTypes.ASN_IMPORT);
                }

            }

        }


    }

    @Override
    public void importCurrentOrders() {

        Client client = Client.getClientForID("" + clientId);
        ClientPolicy policy = client.getClientPolicy();

        endpoint.post(path: getShipmentUrl,
                body: [vendor_id          : vendorId, status: 'ready',
                       with_ship_address  : 1,
                       with_order         : 1,
                       with_ship_service  : 1,
                       with_shipment_items: 1,
                       with_product       : 1,
                       limit              : 999, start: 0],
                requestContentType: 'application/json'
        ) { resp, reader ->

            println reader.records.size() + ' orders found!'
            println reader.toString()


            reader.records.each() { orderSrc ->

                String shipmethod = "";
                try {
                    Order order = policy.createInitializedOrder()

                    boolean hasLTLItem = false;

//                    order.order_refnum = orderReferencePrefix+(it.'id'.text())
                    println "ID: " + orderSrc.order_id
                    println "Ship Add 1: " + orderSrc.ship_address.address1
                    if(channelNametoGroupName){
                        String channelName = orderSrc.order.channel_name
                        if(channelName.length()>0){
                            order.group_name = channelName;

                        }
                    }

                    String gift_message = orderSrc.order?.gift_message
                    if (gift_message != null) {
                        if (gift_message.trim().length() > 1) {
                            order.is_gift = "1"
                            order.gift_message = gift_message


                        }
                    }


                    order.order_refnum = 'R' + orderSrc.order_id + '-' + orderSrc.id   // order and shipment id
                    order.po_num = orderSrc.id
                    order.order_type = "RETAILOPS"
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                    order.is_paid = 1
                    order.backorder_rule = OrderXMLDoc.kBackOrderAll

                    order.getShippingContact().setName(orderSrc.ship_address.addressee)
                    order.getShippingContact().setPhone(orderSrc.ship_address.phone_number)
                    order.getShippingAddress().setCompany_name(orderSrc.ship_address.company)
                    order.getShippingAddress().setAddress_one(orderSrc.ship_address.address1)
                    order.getShippingAddress().setAddress_two(orderSrc.ship_address.address2)
                    order.getShippingAddress().setCity(orderSrc.ship_address.city)
                    order.getShippingAddress().setState((orderSrc.ship_address.state_abbr != null && orderSrc.ship_address.state_abbr.length() > 0 ? orderSrc.ship_address.state_abbr : orderSrc.ship_address.state_name))
                    order.getShippingAddress().setZip(orderSrc.ship_address.postal_code)
                    order.getShippingAddress().setCountry(orderSrc.ship_address.country_name)

                    order.getBillingContact().setName(orderSrc.ship_address.addressee)
                    order.getBillingContact().setPhone(orderSrc.ship_address.phone_number)
                    order.getBillingAddress().setCompany_name(orderSrc.ship_address.company)
                    order.getBillingAddress().setAddress_one(orderSrc.ship_address.address1)
                    order.getBillingAddress().setAddress_two(orderSrc.ship_address.address2)
                    order.getBillingAddress().setCity(orderSrc.ship_address.city)
                    order.getBillingAddress().setState((orderSrc.ship_address.state_abbr != null && orderSrc.ship_address.state_abbr.length() > 0 ? orderSrc.ship_address.state_abbr : orderSrc.ship_address.state_name))
                    order.getBillingAddress().setZip(orderSrc.ship_address.postal_code)
                    order.getBillingAddress().setCountry(orderSrc.ship_address.country_name)

                    order.is_paid = 1

                    order.discount = 0.00f   //Float.parseFloat('0' + it.'total_discounts'.text())

                    if (importPrices) {

                        println(orderSrc.order_id);
                    def itemList = getOrderItemList(orderSrc.order_id.toString());

                        //loop through items on the shipment
                        orderSrc.shipment_items.each() { item ->

                            //loop through the items on the order
                            itemList.records.each() {
                        OrderItem ->

                            if(item.order_item_id == OrderItem.id) {
                                int qty = Integer.parseInt(item.quantity.toString());
                                if (qty > 0) {
                                    String sku = item.vpc;
                                    order.addLineItem(sku, qty, Float.parseFloat(OrderItem.unit_price.toString()), Float.parseFloat(OrderItem.extended_total.toString()), OrderItem.product_name.toString(), "", "")
                                }
                            }

                    }
                            }
                }else {

                        orderSrc.shipment_items.each() { item ->
                            println "LINE SKU: " + item.vpc
                            println "LINE QTY: " + item.quantity

                            String sku = item.vpc

                            order.addLineItem(sku, item.quantity + "", "0.00", "0.00", "", "", "", "")

                            item.product?.attributes?.each() { att ->

                                println "got attribute value " + att.name + ":" + att.val

                                String val = att?.val
                                if (val == null) {
                                    val = ""
                                }
                                println "check val " + val
                                if ("LTL".equalsIgnoreCase(val) || val.toUpperCase().contains("WHITE GLOVE")) {
                                    hasLTLItem = true;
                                }

                                if ("Package".equalsIgnoreCase(att?.val)) {
                                    println "Got val of Package!"
                                }
                            }
                        }
                    }

                    order.getShippingInfo().setShipOptions(translateRetailOpsShipMethod(orderSrc.ship_service.name, hasLTLItem,order), "Prepaid", "")
                    shipmethod = translateRetailOpsShipMethod(orderSrc.ship_service.name, hasLTLItem,order);



                    if (!testing) {
                        doFinalStuffBeforeSavingOrder(order);
                        policy.saveNewOrder(order, false)
                        if (!("0".equals(order.orderID)) || order.last_error.contains("reference already exists")) {
                            if(order.getShippingInfo().carr_service_ref_num.contains("LTL"))
                            {
                                acknowledgeOrder(orderSrc.id.toString(),3,5)

                            }   else
                            {
                                acknowledgeOrder(orderSrc.id.toString(),1,4)

                            }
                        } else {
                            Exception exx = new LogableException(new Exception(), order.last_error, orderSrc.id, "" + clientId, this.getClass().getName(), LogableException.errorTypes.ORDER_IMPORT)

                        }
                    }
                } catch (DuplicateOrderException ex) {

                    if(shipmethod.contains("LTL"))
                    {
                        acknowledgeOrder(orderSrc.id.toString(),3,5)

                    }   else
                    {
                        acknowledgeOrder(orderSrc.id.toString(),1,4)

                    }

                } catch (Exception ex) {
                    ex.printStackTrace()
                    //    public LogableException(Exception exception, String errorMessage, String orderNum, String clientId, String requestType, errorTypes errorType) {

                    Exception exx = new LogableException(ex, ex.getMessage(), orderSrc.id.toString(), "" + clientId, this.getClass().getName(), LogableException.errorTypes.ORDER_IMPORT)
                }
            }

        }
    }

    public int translateOwdShipMethod(String owdMethod) {

        println owdMethod
        if (!owdMethod) {
            return 62;
        }
        if (owdMethod.toUpperCase().equals("UPS GROUND")) {
            return 62;
        }

        if (owdMethod.toUpperCase().equals("FEDEX HOME DELIVERY")) {
            return 8;
        }

        if (owdMethod.toUpperCase().equals("FEDEX GROUND")) {
            // case 904303 change from 113 to 2210
            // changing to 2101 per Tracey, 2210 was incorrect
            // case 1116027 changed from 2101 to 2201 per Tracey
            // return 2201;
            // Change to 113 per Tracey on 03/11/22
            return 113;
        }
        def methodId = methodMap.find { it.value == owdMethod }?.key
        if(methodId==null)
        {
            methodId = 62;
        }
        return methodId;
    }

    public String translateRetailOpsShipMethod(String retailOpsName, boolean hasLTLItems, Order order) throws Exception{

        if (retailOpsName.trim().equalsIgnoreCase('LTL') || retailOpsName.trim().equalsIgnoreCase('WHITE GLOVE') || hasLTLItems) {
            return 'LTL'
        } else {
            return 'FedEx Home Delivery'
        }
    }



    boolean getImportPrices() {
        return importPrices
    }

    void setImportPrices(boolean importPrices) {
        this.importPrices = importPrices
    }

    boolean getChannelNametoGroupName() {
        return channelNametoGroupName
    }

    void setChannelNametoGroupName(boolean channelNametoGroupName) {
        this.channelNametoGroupName = channelNametoGroupName
    }
}
