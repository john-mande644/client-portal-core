package com.owd.jobs.jobobjects.bigcommerce

import com.owd.LogableException
import com.owd.core.managers.InventoryManager
import com.owd.hibernate.generated.OwdInventory
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import groovy.xml.XmlUtil

import com.owd.core.CountryNames
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.AbstractIntegrationApi
import groovy.util.slurpersupport.GPathResult
import groovy.xml.StreamingMarkupBuilder
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.protocol.HttpContext
import org.codehaus.groovy.runtime.DefaultGroovyMethods

import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import java.security.KeyStore

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 4/11/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
class BigCommerceAPI extends AbstractIntegrationApi {

    static List<String> customSslKeystoreDomains;

    static
    {
        customSslKeystoreDomains = new ArrayList<String>()

       // customSslKeystoreDomains.add("choppertown.com")
       // customSslKeystoreDomains.add("marineessentials.com")

    }

    @Override
    boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {
        return super.addLineItem(order, sku, qty, price, linePrice, title, color, size)    //To change body of overridden methods use File | Settings | File Templates.
    }


    protected String apiKey = "";
    protected String password = "";
    protected String shopHost = "";

    private String fulfillmentServiceCode = "";
    private String blankItemSku = "";
    private String otherFulfillmentSku = "";
    private String orderReferencePrefix = "";
    boolean combineOptionForSku = true;
    boolean ignoreSSL = false;
    boolean ignoreSkuError = false;



    String getOrderReferencePrefix() {
        return orderReferencePrefix
    }
    void setCombineOptionsForSku(boolean b){
        this.combineOptionForSku = b;
    }

    void setOrderReferencePrefix(String orderReferencePrefix) {
        this.orderReferencePrefix = orderReferencePrefix
    }
    private int firstOrderId = 0;

    int getFirstOrderId() {
        return firstOrderId
    }

    void setFirstOrderId(int firstOrderId) {
        this.firstOrderId = firstOrderId
    }

    String getFulfillmentServiceCode() {
        return fulfillmentServiceCode
    }

    void setFulfillmentServiceCode(String fulfillmentServiceCode) {
        this.fulfillmentServiceCode = "" + fulfillmentServiceCode
    }

    String getBlankItemSku() {
        return blankItemSku
    }

    void setBlankItemSku(String blankItemSku) {
        this.blankItemSku = "" + blankItemSku
    }

    String getOtherFulfillmentSku() {
        return otherFulfillmentSku
    }

    void setOtherFulfillmentSku(String otherFulfillmentSku) {
        this.otherFulfillmentSku = "" + otherFulfillmentSku
    }

    RESTClient endpoint = null;



    public BigCommerceAPI(String loginx, String keyx, String baseURLx, String orderReferencePrefixx) {
        apiKey = loginx
        password = keyx
        shopHost = baseURLx
        orderReferencePrefix= orderReferencePrefixx


        setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))

        endpoint = new RESTClient(shopHost)





        endpoint.client.addRequestInterceptor(
                { HttpRequest httpRequest, HttpContext httpContext ->
                    httpRequest.addHeader('Authorization', 'Basic ' + ((apiKey + ':' + password).bytes.encodeBase64().toString()))
                } as HttpRequestInterceptor);
        try {   //for unrecognized ssl certs

            boolean useCustomKeystore = false

            for (String domain : customSslKeystoreDomains) {
                if (shopHost.contains(domain)) {
                    useCustomKeystore = true
                }

            }
            if (useCustomKeystore) {
                def keyStore = KeyStore.getInstance(KeyStore.defaultType)

                getClass().getResource("/truststore.jks").withInputStream {
                    keyStore.load(it, "owd7172".toCharArray())
                }

                endpoint.client.connectionManager.schemeRegistry.register(
                        new Scheme("https", new SSLSocketFactory(keyStore), 443))
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void importCurrentOrders() {
        importCurrentOrders(false)
    }

    public static void main(String[] args) {
        //setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))

        BigCommerceAPI api = new BigCommerceAPI("OneWorldDirect","42dc3a6d4c9fef0c976df9128de1fd347a6586f0","https://store-jydkvnsb31.mybigcommerce.com/api/v2/","BC");
        api.CreateInventoryFileOfSkus("471");

        //api.markOrderShipped(20761,10)

    }

    public void markOrderShipped(int orderId) {
        markOrderShipped(orderId,2)

    }
    public void markOrderShipped(int orderId, int ostatus) {
        try {
            GString url = "orders/${orderId}"

            def resp = endpoint.put(
                    path: url,
                    requestContentType: groovyx.net.http.ContentType.JSON,
                    body: [status_id:ostatus]
            )
            println "Marked BC order shipped"
            if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
            // assert ( resp.status instanceof GPathResult )
            println outputFormattedXml(resp.data);
        } catch (Exception ex) {
            if (!("Forbidden".equalsIgnoreCase(ex.getMessage()))) {
                println "BC update order status says: " + ex.getMessage()
                if (!("Bad Request".equalsIgnoreCase(ex.getMessage()))) {
                    throw new Exception(ex)
                } else {
                    //check that shipment exists
                    def respShips = endpoint.get(
                            path: "orders/${orderId}.xml"
                    )
                    println XmlUtil.serialize(respShips.data)
                    int status = 0
                    try {
                        status = Integer.decode(respShips.data?.status_id?.text())

                    } catch (Exception exx) {}
                    if (status != 2 && status !=10) {
                        throw new Exception("Unable to update order status (current:"+status+") for  BigCommerce order id " + orderId)
                    }
                }
            }
        }


    }

    public void reportShipment(int orderId, int addressId, String tracking, String comment, Vector itemVec) {


        try {

            GString url = "orders/${orderId}/shipments.xml"

            println 'addid:' + addressId
            def resp = endpoint.post(
                    path: url,
                    requestContentType: groovyx.net.http.ContentType.XML,
                    body: {
                        mkp.xmlDeclaration()
                        shipment {
                            order_address_id("" + addressId)
                            comments(comment)
                            tracking_number(tracking)
                            items {
                                for (LineItem itemx : (Vector<LineItem>) itemVec) {
                                    try {
                                        println 'itemid:' + itemx.client_ref_num

                                        int itemid = Integer.parseInt(itemx.client_ref_num);
                                        if (itemid > 0 && itemx.quantity_actual > 0) {
                                            item {
                                                order_product_id("" + itemid)
                                                quantity("" + itemx.quantity_actual)
                                            }
                                        }
                                    } catch (Exception ex) {

                                    }
                                }

                            }
                        }
                    }
            )


            if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
            //  assert ( resp.status instanceof GPathResult )
            println outputFormattedXml(resp.data);
        } catch (Exception ex) {
            println "BC says: " + ex.getMessage()
            if (!("Bad Request".equalsIgnoreCase(ex.getMessage()))) {
                throw new Exception(ex)
            } else {
                //check that shipment exists
                def respShips = endpoint.get(
                        path: "orders/${orderId}/shipments/count.xml"
                )
                println respShips
                int count = 0
                try {
                    println XmlUtil.serialize(respShips.data)
                    count = Integer.decode(respShips.data?.count?.text())

                } catch (Exception exx) {}
                if (count < 1) {
                    throw new Exception("Unable to post shipment, no shipments reported, for One World Studios BigCommerce order id " + orderId)
                }
            }
        }
        //  markOrderShipped(orderId)

    }


    public void importCurrentOrders(boolean testing) {

        Client oclient = Client.getClientForID(clientId + "");
        ClientPolicy policy = oclient.getClientPolicy();

        //   List<Object> pmap = new ArrayList<Object>()

        int maxOrderId = 0

        while (maxOrderId >= 0) {
            def resp = endpoint.get(
                    path: "orders.xml",
                   // query: [min_id: (maxOrderId + 1)]  //Awaiting Fulfillment
                     query : [status_id:'11',min_id:(maxOrderId+1)]  //Awaiting Fulfillment
            )
             // println resp.data

            if (resp.status == 204) //no orders returned
            {
                maxOrderId = (-1)
            } else {
                assert (resp.data instanceof GPathResult)

                 println outputFormattedXml(resp.data);
                resp.data.order.each() {
                    println it.products.link.text() + '.xml'
                    int oid = Integer.decode(it.'id'.text())
                    if (oid > maxOrderId) {
                        maxOrderId = oid
                    }

                }
                if (resp.data.order.size() == 0) {
                    maxOrderId = -1
                }

                if (!testing) {
                    resp.data.order.each() {
                        try {
                            println it.id + ':' + Integer.decode(it.'id'.text())
                            println it.status
                            int orderid= Integer.decode(it.'id'.text())

                        //    println outputFormattedXml(it)
                            //  println it.email
                            if (  ((Integer.decode(it.'id'.text()) == 33165) || Integer.decode(it.'id'.text()) >= getFirstOrderId()) &&  (!OrderUtilities.orderRefnumExists(orderReferencePrefix+(it.'id'.text()),clientId+"")) && ((it.'status'.text().equals('Awaiting Fulfillment')))) {
                                println 'status:' + it.'status'.text()
                                Order order = policy.createInitializedOrder();
                                order.order_refnum = orderReferencePrefix+(it.'id'.text())
                                order.po_num = it.id.text()
                                order.order_type = "BIGCOMMERCE"
                                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                                println it.'billing_address'.'first_name'.text() + ' ' + it.'billing_address'.'last_name'.text()

                                // Sean created on 9/18/2019
                                // Harmony Herbal adding Notes to packing slip
                                if(order.clientID=='602'){
                                    order.getShippingInfo().comments = it.'customer_message'.text()
                                }

                                order.getBillingContact().name = it.'billing_address'.'first_name'.text() + ' ' + it.'billing_address'.'last_name'.text()
                                order.getBillingContact().email = it.'billing_address'.email.text()
                                order.getBillingContact().phone = it.'billing_address'.phone.text()
                                order.getBillingAddress().company_name = it.'billing_address'.company.text()
                                order.getBillingAddress().address_one = it.'billing_address'.street_1.text()
                                order.getBillingAddress().address_two = it.'billing_address'.street_2.text()
                                order.getBillingAddress().city = it.'billing_address'.city.text()
                                order.getBillingAddress().state = it.'billing_address'.state.text()
                                order.getBillingAddress().zip = it.'billing_address'.zip.text()
                                order.getBillingAddress().country = it.'billing_address'.'country_iso2'.text()


                                println it.shipping_addresses.link.text() + '.xml'

                                def shiptoResp = endpoint.get(
                                        path: (it.shipping_addresses.link.text() + '.xml').substring(1)
                                        //  body : [],
                                        //  requestContentType : 'application/xml'
                                )
                                //println resp.data
                                assert (shiptoResp.data instanceof GPathResult)
                                println outputFormattedXml(shiptoResp.data);


                                def shipto = shiptoResp.data.address
                                order.getShippingContact().name = shipto.'first_name'.text() + ' ' + shipto.'last_name'.text()
                                println shipto.'first_name'.text() + ' ' + shipto.'last_name'.text()
                                order.getShippingContact().email = shipto.email.text()
                                order.getShippingContact().phone = shipto.phone.text()
                                order.getShippingAddress().company_name = shipto.company.text()
                                order.getShippingAddress().address_one = shipto.street_1.text()
                                order.getShippingAddress().address_two = shipto.street_2.text()
                                order.getShippingAddress().city = shipto.city.text()
                                order.getShippingAddress().state = shipto.'state'.text()
                                order.getShippingAddress().zip = shipto.zip.text()
                                order.getShippingAddress().country = shipto.'country_iso2'.text()

                                order.po_num = shipto.id.text()
                                order.is_paid = 1;
                                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);
                                Float coupon = 0.0f
                                if(it.'coupon_discount'.text() != null){
                                    coupon = Float.parseFloat(it.'coupon_discount'.text());
                                }

                                order.discount = Float.parseFloat('0' + it.'discount_amount'.text()) + coupon
                               // order.discount = order.discount+Float.parseFloat('0' + it.'discount_amount'.text())
                                if(it.'total_tax'.text().equals("")){
                                    order.total_tax_cost = 0.0f;
                                }else{
                                    order.total_tax_cost = Float.parseFloat(it.'total_tax'.text())
                                }


                                order.total_shipping_cost += Float.parseFloat('0' + it.'shipping_cost_ex_tax'.text()) + Float.parseFloat('0' + it.'handling_cost_ex_tax'.text())


                                def productResp = endpoint.get(
                                        path: (it.products.link.text() + '.xml').substring(1)
                                        //  body : [],
                                        //  requestContentType : 'application/xml'
                                )
                                //println resp.data
                                assert (productResp.data instanceof GPathResult)
                              //    println outputFormattedXml(productResp.data);


                                productResp.data.product.each() {
                                    item ->

                                        String sku = item.sku.text();
                                        println sku
                                    //   println XmlUtil.serialize(item)
                                        if(combineOptionForSku) {
                                            item.product_options?.option?.each() { option ->
                                                if (option.display_name?.equals('Size')) {
                                                    String val = option.display_value.text()
                                                    val = val.toLowerCase()

                                                    if (!(LineItem.SKUExists(order.clientID, sku + val))) {
                                                        val = val.toUpperCase()
                                                    }
                                                    if (!(LineItem.SKUExists(order.clientID, sku + val))) {
                                                        val = '-' + val
                                                    }
                                                    if (!(LineItem.SKUExists(order.clientID, sku + val))) {
                                                        val = val.toLowerCase()
                                                    }
                                                    sku = sku + val
                                                }
                                            }
                                        }
                                        String title = item.name.text()

                                        String type = item.'type'.text()
                                        // boolean needsShipping = "true".equals(""+(item.'requires_shipping'?.text()))

                                        if (!('physical'.equals(type))) {
                                            //check SKU
                                            sku = getOtherFulfillmentSku()
                                            title = title + " / DELIVERED SEPARATELY"
                                        }


                                        println 'adding ' + sku + ' / ' + title
                                        println 'price: ' + item.'base_price'.text()
                                        println 'qty: ' + item.quantity.text()



                                        if (addLineItem(order, sku, item.quantity.text(), item.'base_price'.text(), "0.00", title, "", "")) {
                                            LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                            litem.client_ref_num = item.id.text()
                                        }


                                }


                                if (order.getTotalPhysicalUnitQuantity() > 0) {
                                        String actualMethod = getActualShipMethod(order, shipto.shipping_method.text())
                                        println 'Shipping via' + actualMethod
                                        order.getShippingInfo().setShipOptions(actualMethod, "Prepaid", "");


                                    doFinalStuffBeforeSavingOrder(order);
                                    println 'client reference: '+order.order_refnum
                                    policy.saveNewOrder(order, false)

                                    //
                                }else
                                {
                                    markOrderShipped(orderid,10)
                                }
                            }


                        } catch (Exception ex) {
                            if(!ignoreSkuError) {
                                new LogableException(ex.getMessage(), it."id".text(), clientId + "", "Order Import", LogableException.errorTypes.ORDER_IMPORT);
                            }
                            ex.printStackTrace()
                            try {

                                if(!(ex.getMessage().contains("Inventory SKU")))
                                {
                              //  Mailer.sendMail("BigCommerce API order import fail for cid " + clientId, OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "donotreply@owd.com");
                                }
                            } catch (Exception mailex) {
                                mailex.printStackTrace();
                            }
                        }
                    }

                }
            }

        }//end while

    }

    /**
     *  pretty prints the GPathResult NodeChild
     */
    def static outputFormattedXml(node) {
        def xml = new StreamingMarkupBuilder().bind {
//            mkp.declareNamespace("":node.namespaceURI())
            mkp.yield(node)
        }

        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, 'yes')

        // figured this out by looking at Xalan's serializer.jar
        // org/apache/xml/serializer/output_xml.properties
        transformer.setOutputProperty("{http\u003a//xml.apache.org/xalan}indent_amount", "2")
        def result = new StreamResult(new StringWriter())
        transformer.transform(new StreamSource(new ByteArrayInputStream(xml.toString().bytes)), result)

        return result.writer.toString()
    }

    public void syncInventory(String clientid){
        List<String>  notFound = new ArrayList<>();
        boolean hasSkus = true;
        int i = 1;
        try{

            while(hasSkus) {
                System.out.println("Doing page: "+ i);
                def resp = endpoint.get(
                        path: "products.xml",
                        // query: [min_id: (maxOrderId + 1)]  //Awaiting Fulfillment
                        query: [include: 'sku,inventory_level,skus,inventory_tracking',
                        limit:150,
                        page:i]  //Awaiting Fulfillment
                )
                if(resp.status == 204){
                    hasSkus = false;
                    break;

                }

                assert (resp.data instanceof GPathResult)

                println outputFormattedXml(resp.data);
                resp.data.product.each() {
                    println("in product")

                    String id = it.id.text()
                    String sku = it.sku.text()
                    String qty = it.inventory_level.text()
                    String invTracking = it.inventory_tracking.text()

                    checkSkuAndUpdate(id,sku,qty,clientid)

                    if(it.skus.link.text().length()>0 && invTracking.equals("sku")){
                        println("in skus")
                      loopThroughChildSkus(it.skus.link.text(),clientid)
                    }



                }
                    i++;


            }
        }catch (Exception e){
e.printStackTrace()
        }
        println(notFound)


    }
    public void loopThroughChildSkus(String thePath,String clientid){
        println(thePath)
        if(thePath.startsWith("/")){
            thePath = thePath.substring(1,thePath.length());
            println(thePath)
        }

        boolean hasSkus = true;
        int i = 1;
        try{

            while(hasSkus) {

                def resp = endpoint.get(
                        path: thePath,
                        // query: [min_id: (maxOrderId + 1)]  //Awaiting Fulfillment
                         query: [

                        page:i]  //Awaiting Fulfillment
                )

                if (resp.status != 204) {


                    assert (resp.data instanceof GPathResult)

                    println outputFormattedXml(resp.data);
                    resp.data.sku.each() {
                        println("in product")

                        String id = it.id.text()
                        String sku = it.sku.text()
                        String qty = it.inventory_level.text()
                        String productId = it.product_id.text();

                        checkChildSkuAndUpdate(id, sku, qty, clientid, productId)


                    }
                    i++;
                }else{
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace()
        }




    }

    public boolean skuExists(String sku, String clientid){
        boolean exists = false;
        try {
            OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientid + "", sku);
            exists = true;

        }catch (Exception e){

        }

        return exists;
    }

   public void checkSkuAndUpdate(String id, String sku, String qty,String clientid){
            try {


                OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientid + "", sku);
                long oh = item.getOwdInventoryOh().getQtyOnHand();

                if (oh != Long.parseLong(qty)) {
                    println("need to update " + sku + ": from " + qty + " to " + oh);

                    updateInventoryLevel(id,oh)



                }

            } catch (Exception ex) {
                if (ex.getMessage().contains("does not exist") || ex.getMessage().contains("not found")) {
                    println "We don't have: " + sku + " :ID: " + id

                } else {
                    throw ex
                }
            }


        }
    public void checkChildSkuAndUpdate(String id, String sku, String qty,String clientid,String productId){
        try {


            OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientid + "", sku);
            long oh = item.getOwdInventoryOh().getQtyOnHand();

            if (oh != Long.parseLong(qty)) {
                println("need to update " + sku + ": from " + qty + " to " + oh);

                updateInventoryLevel(id,oh,productId)



            }

        } catch (Exception ex) {
            if (ex.getMessage().contains("does not exist") || ex.getMessage().contains("not found")) {
                println "We don't have: " + sku + " :ID: " + id

            } else {
                throw ex
            }
        }


    }


    public void updateInventoryLevel(String skuId, long qty){
        updateInventoryLevel(skuId, qty,"");
    }

    public void updateInventoryLevel(String skuId, long qty,String productId) {
        try {
            GString url
            if(productId.length()>0){
                url = "products/${productId}/skus/${skuId}"
            }else{
                url = "products/${skuId}"
            }

                println(url);

            def resp = endpoint.put(
                    path: url,
                    requestContentType: groovyx.net.http.ContentType.JSON,
                    body: [inventory_level:qty]
            )

            if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
            // assert ( resp.status instanceof GPathResult )
            println outputFormattedXml(resp.data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }






    ////inventory file start
    public void CreateInventoryFileOfSkus(String clientid){
        List<String>  notFound = new ArrayList<>();
        List<BCProductBean> skulist = new ArrayList<>();
        boolean hasSkus = true;
        int i = 1;
        try{

            while(hasSkus) {
                System.out.println("Doing page: "+ i);
                def resp = endpoint.get(
                        path: "products.xml",
                        // query: [min_id: (maxOrderId + 1)]  //Awaiting Fulfillment
                        query: [include: 'sku,inventory_level,skus,inventory_tracking',
                                limit:150,
                                page:i]  //Awaiting Fulfillment
                )
                if(resp.status == 204){
                    hasSkus = false;
                    break;

                }

                assert (resp.data instanceof GPathResult)

                println outputFormattedXml(resp.data);
                resp.data.product.each() {
                    println("in product")

                    String id = it.id.text()
                    String sku = it.sku.text()
                    String qty = it.inventory_level.text()
                    String invTracking = it.inventory_tracking.text()
                    BCProductBean bcp = new BCProductBean();
                    bcp.setSku(sku);
                    bcp.setOwdHas(skuExists(sku,clientid))
                   skulist.add(bcp);

                    if(it.skus.link.text().length()>0 && invTracking.equals("sku")){
                        println("in skus")
                       skulist.addAll(checkChildSkusFile(it.skus.link.text(),clientid,sku));

                    }



                }
                i++;


            }
        }catch (Exception e){
            e.printStackTrace()
        }
        println(notFound)
        outputFile(skulist)


    }
    public void outputFile(List<BCProductBean> skuLists){
        try{
            StringBuilder sb = new StringBuilder();
            for(BCProductBean bcp:skuLists){
                sb.append(bcp.getSku());
                sb.append(",");
                sb.append(bcp.getChildSku());
                sb.append(",");
                sb.append(bcp.isOwdHas());
                sb.append("\r\n");
            }

            BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("D:/gildansku.csv")));
            bwr.write(sb.toString());
            bwr.flush();
            bwr.close();

        }catch (Exception e){
            e.printStackTrace()
        }



    }


    public List<BCProductBean> checkChildSkusFile(String thePath,String clientid,String parentSku){
        println(thePath)
        if(thePath.startsWith("/")){
            thePath = thePath.substring(1,thePath.length());
            println(thePath)
        }
        List<BCProductBean> skuList = new ArrayList<>();
        boolean hasSkus = true;
        int i = 1;
        try{

            while(hasSkus) {

                def resp = endpoint.get(
                        path: thePath,
                        // query: [min_id: (maxOrderId + 1)]  //Awaiting Fulfillment
                        query: [

                                page:i]  //Awaiting Fulfillment
                )

                if (resp.status != 204) {


                    assert (resp.data instanceof GPathResult)

                    println outputFormattedXml(resp.data);
                    resp.data.sku.each() {
                        println("in product")

                        String id = it.id.text()
                        String sku = it.sku.text()
                        String qty = it.inventory_level.text()
                        String productId = it.product_id.text();
                        BCProductBean bcp = new BCProductBean();
                        bcp.setSku(parentSku);
                        bcp.setChildSku(sku);
                        bcp.setOwdHas(skuExists(sku,clientid))
                        //checkChildSkuAndUpdate(id, sku, qty, clientid, productId)
                        skuList.add(bcp);


                    }
                    i++;
                }else{
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace()
        }

        return skuList;





    }
}
