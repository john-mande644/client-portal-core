package com.owd.jobs.jobobjects.woocommerce

import groovy.json.JsonOutput

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException
import com.owd.core.CountryNames

import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.utilities.RateShopper
import groovy.json.JsonBuilder
import groovy.json.internal.LazyMap
import groovyx.net.http.RESTClient

import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.client.HttpClient
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.AllowAllHostnameVerifier
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.protocol.HttpContext


import java.security.cert.CertificateException
import java.security.cert.X509Certificate


/**
 * Created by stewartbuskirk1 on 6/29/15.
 */
class WooCommerceAPI {
    private final static Logger log =  LogManager.getLogger();

    int clientId = 0;
    private String key = "";
    private version = "2"
    private String secret = ""

    private rootUrl = ""
    private boolean useProductId = false
    private boolean ignoreUnknownSku = false;

    boolean getUseProductId() {
        return useProductId
    }

    void setUseProductId(boolean useProductId) {
        this.useProductId = useProductId
    }

    boolean getIgnoreUnknownSku() {
        return ignoreUnknownSku
    }

    void setIgnoreUnknownSku(boolean ignoreUnknownSku) {
        this.ignoreUnknownSku = ignoreUnknownSku
    }

    String getKey() {
        return key
    }

    String getSecret() {
        return secret
    }

    public static void main(String[] args)
    {
        System.out.println("hello");

       // WooCommerceAPI api = new WooCommerceAPI(390, "https://welltrainedmind.com/", "ck_90e1dfb0d09e8e9864c10d5cd092e246a58a7615", "cs_99a0794901678f3396b2ef7e2fc84ab39edfb62d", "3")
            log.debug("testing1")
      //  WooCommerceAPI api = new WooCommerceAPI(600, "https://wearehandsome.com/", "ck_fdd87e9d3eb125fdc21f1f35e7921f83529bcf71", "cs_b52ddcce0ddcab0a921c323418dc170eb6f7b229","2",true)
        WooCommerceAPI api = new WooCommerceAPI(432, "https://ezultrasound.com/", "ck_2b620107b2cbe9d174e73719fcf6505237adcc71", "cs_5682bf087095a13cb85b0def6ed108d70e8bcbdb","3",false);
       System.out.println("loaded api");

        api.listProductSkus();

            //  println Integer.parseInt("W12345".substring(1))
            // api.markOrderShipped(4525)
       // println api.importOrdersPeaceHillPress()
       // api.listOrdersPeaceHillPress();
      // println api.markOrderTracking(23742,"9274899998807425017116");

      //  println api.markOrderShipped(67211)
    }

    public WooCommerceAPI (int clientKey, String url, String consumerKey, String consumerSecret, String aversion){
        clientId = clientKey
        key = consumerKey
        secret = consumerSecret
        rootUrl = url
        version = aversion
        useProductId = false



    }

    public WooCommerceAPI (int clientKey, String url, String consumerKey, String consumerSecret, String aversion, Boolean auseProductId)
    {
        clientId = clientKey
        key = consumerKey
        secret = consumerSecret
        rootUrl = url
        version = aversion
        useProductId = auseProductId

    }

    RESTClient getEndpoint()
    {
        RESTClient endpoint = new RESTClient(rootUrl)
        endpoint.ignoreSSLIssues()
        endpoint.setClient(getTestHttpClient())
        endpoint.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        println "Authorization:"+'Basic ' + ((key+':'+secret).bytes.encodeBase64().toString())
                        httpRequest.addHeader('Authorization', 'Basic ' + ((key+':'+secret).bytes.encodeBase64().toString()))
                        httpRequest.setHeader('Accept', 'application/json')
                        //   httpRequest.setHeader('Accept-Encoding', '')
                        httpRequest.setHeader('Content-Type', 'application/json')
                    }
                });

        return endpoint;
    }

    public static HttpClient getTestHttpClient() {
        try {
            SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy(){
                @Override
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            },new AllowAllHostnameVerifier());
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, sf));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(ccm);
        } catch (Exception e) {
            e.printStackTrace()
            return new DefaultHttpClient();
        }
    }

    public  void assignShipMethod(Order order, String shipMethod){
        boolean isBestWay = false;

       if (shipMethod.toUpperCase().contains("MEDIA MAIL")) {
            shipMethod = "USPS Media Mail Single-Piece";
        } else if ((shipMethod.toUpperCase().contains("FIRST-CLASS") || shipMethod.toUpperCase().contains("FIRST CLASS")) && order.getShippingAddress().isUS()) {
            shipMethod = "USPS First-Class Mail";
        } else if ((shipMethod.toUpperCase().contains("FIRST-CLASS") || shipMethod.toUpperCase().contains("FIRST CLASS"))) {
            shipMethod = "USPS First-Class Mail International";
        }  else if (shipMethod.toUpperCase().contains("PRIORITY") && shipMethod.toUpperCase().contains("EXPRESS") && order.getShippingAddress().isUS()) {
           shipMethod = "USPS Priority Mail Express";
       } else if (shipMethod.toUpperCase().contains("PRIORITY") && order.getShippingAddress().isUS()) {
            shipMethod = "USPS Priority Mail";
        } else if (shipMethod.toUpperCase().contains("PRIORITY")) {
            shipMethod = "USPS Priority Mail International";
        } else if (shipMethod.toUpperCase().contains("STANDARD POST")) {
           shipMethod = "USPS Parcel Select Nonpresort";
       } else if (shipMethod.toUpperCase().contains("ECONOMY")) {
            isBestWay = true;
        } else {
            isBestWay = true;
        }


        if (!(order.orderRefnumExists(order.order_refnum))) {
            if (isBestWay) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

                shipMethod = RateShopper.rateShop(order, alternateShipMethodList);
            }
            order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");

        } else {
            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
        }


    }

    public List<String> listProductSkus()
    {
        RESTClient endpoint = getEndpoint()


        List<String> skus = new ArrayList<String>()

        endpoint.get(
                path: 'wc-api/v'+version+'/products'   ,

                requestContentType: 'application/json',
                contentType: 'application/json'
        )

                {resp, reader ->

                    reader.products.each {

                        // println it
                        skus.add(it.title+':'+it.sku)

                    }
                }

        return skus
    }

    public List<String> listOrdersPeaceHillPress()
    {
        RESTClient endpoint = getEndpoint()

        List<String> skus = new ArrayList<String>()

        endpoint.get(
                path: 'wc-api/v'+version+'/orders'   ,
                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                query: [status: 'processing','filter[flag_virtual]':'true']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))
                    reader.orders.each {

                        skus.add(it.toString())
                        println(it.view_order_url);
                        println(it.gift_message);


                    }
                }

        return skus
    }

    public List<String> importOrdersPeaceHillPress()
    {
        RESTClient endpoint = getEndpoint()

        List<String> skus = new ArrayList<String>()

        int p = 1
        int ptotal = 1
        int i =0

        while(p <= ptotal) {
            endpoint.get(
                    path: 'wc-api/v' + version + '/orders',

                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    query: [status: 'processing', 'filter[flag_virtual]': 'true', "page": p, "consumer_key":key, "consumer_secret":secret]
            )
                    { resp, reader ->

                        println resp.headers['X-WC-Total']
                        ptotal = Integer.parseInt(""+resp.headers['X-WC-TotalPages'].value)
                        println reader
                        reader.orders.each {

                            importOrder(it)
                            i++
                        }
                    }
            p++
        }
        println "Total Orders Checked: "+i
        return skus
    }

    public List<String> listOrders()
    {
        RESTClient endpoint = getEndpoint()

        List<String> skus = new ArrayList<String>()

        endpoint.get(
                path: 'wc-api/v'+version+'/orders'   ,

                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                query: [status: 'processing']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))
                    reader.orders.each {

                        skus.add(it.toString())

                    }
                }

        return skus
    }

    public List<String> importOrders()
    {
        RESTClient endpoint = getEndpoint()

        List<String> skus = new ArrayList<String>()

        int p = 1
        int ptotal = 1
        int i =0

        while(p <= ptotal) {
            endpoint.get(
                    path: 'wc-api/v' + version + '/orders',

                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    query: [status: 'processing', "page": p]
            )
                    { resp, reader ->

                        println resp.headers['X-WC-Total']
                        ptotal = Integer.parseInt(""+resp.headers['X-WC-TotalPages'].value)
                        println reader
                        reader.orders.each {

                            importOrder(it)
                            i++
                        }
                    }
            p++
        }
        println "Total Orders Checked: "+i
        return skus
    }


    public void importOrder(LazyMap orderData)
    {

        String orderRef = ""+orderData.'order_number'
        if(orderRef.startsWith("w"))
        {
            orderRef  = orderRef.toUpperCase()
        }
        else {
            orderRef = "W"+orderRef
        }
        try
        {
            println orderData
            println "W"+orderData.'order_number' +":"+OrderUtilities.orderRefnumExists("W"+orderData.'order_number', clientId + "")
            println  orderData.'status'
                if (!(OrderUtilities.orderRefnumExists(orderRef, clientId + "")) && ((orderData.'status'.equals('processing')))) {
                    println 'status:' + orderData.'status'
                    Order order = new Order("" + clientId)
                    order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                    order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                    order.preserveShippingCosts = true;
                    order.order_refnum = orderRef
                    order.po_num = orderData.'id'+""
                    //  order.po_num = it.id
                    order.order_type = "WooCommerce"
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                      if(orderData.gift_message?.length()>0)
                  {
                      order.is_gift="1"
                      order.gift_message=orderData.gift_message
                  }

                    println orderData.'billing_address'.'first_name' + ' ' + orderData.'billing_address'.'last_name'

                    order.getBillingContact().name = orderData.'billing_address'.'first_name' + ' ' + orderData.'billing_address'.'last_name'
                    order.getBillingContact().email = orderData.'billing_address'.email
                    order.getBillingContact().phone = orderData.'billing_address'.phone
                    order.getBillingAddress().company_name = orderData.'billing_address'.company
                    order.getBillingAddress().address_one = orderData.'billing_address'.address_1
                    order.getBillingAddress().address_two = orderData.'billing_address'.address_2
                    order.getBillingAddress().city = orderData.'billing_address'.city
                    order.getBillingAddress().state = orderData.'billing_address'.'state'
                    order.getBillingAddress().zip = orderData.'billing_address'.postcode
                    order.getBillingAddress().country = orderData.'billing_address'.'country'

                    if(clientId==600){
                        if(orderData.'shipping_address'.'first_name'.length()>0){
                            order.getShippingContact().name = orderData.'shipping_address'.'first_name' + ' ' + orderData.'shipping_address'.'last_name'

                        }else{
                            order.getShippingContact().name = orderData.'billing_address'.'first_name' + ' ' + orderData.'billing_address'.'last_name'
                        }
                    }else{

                        order.getShippingContact().name = orderData.'shipping_address'.'first_name' + ' ' + orderData.'shipping_address'.'last_name'

                    }

                     println orderData.'shipping_address'.'first_name' + ' ' + orderData.'shipping_address'.'last_name'
                    order.getShippingContact().email = orderData.'billing_address'.email
                    order.getShippingContact().phone = orderData.'billing_address'.phone
                    order.getShippingAddress().company_name = orderData.'shipping_address'.company
                    order.getShippingAddress().address_one = orderData.'shipping_address'.address_1
                    order.getShippingAddress().address_two = orderData.'shipping_address'.address_2
                    order.getShippingAddress().city = orderData.'shipping_address'.city
                    order.getShippingAddress().state = orderData.'shipping_address'.'state'
                    order.getShippingAddress().zip = orderData.'shipping_address'.postcode
                    order.getShippingAddress().country = orderData.'shipping_address'.'country'


                    order.is_paid = 1;
                    order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                    order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                    order.discount = Float.parseFloat(orderData.'total_discount'.toString())
                    order.total_tax_cost = Float.parseFloat(orderData.'total_tax'.toString())


                    order.total_shipping_cost = Float.parseFloat(orderData.'total_shipping'.toString())




                    orderData.'line_items'?.each()
                            { item ->


                                String sku
                                if(useProductId){
                                    println("Doing product Id")
                                    sku = item.product_id;
                                }else{
                                    sku = item.sku;
                                }

                                if (sku.equals('AUDIOGUIDE-SPANISH')) {
                                    sku = "AUDIOGUIDE"
                                }

                                String title = ""+item.name


                                println 'adding ' + sku + ' / ' + title
                                println 'qty: ' + item.quantity

                                if(skipItemString!=null){
                                    if (!(title.contains(skipItemString))) {
                                        try {
                                            order.addLineItem(sku, "" + (item.quantity), (Float.parseFloat(item.'subtotal'.toString()) / Integer.parseInt(item.'quantity'.toString())) + "", "0.00", title, "", "");
                                            LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                            litem.client_ref_num = item.id
                                        }catch (Exception e){
                                            if(!ignoreUnknownSku){
                                                throw e;
                                            }
                                        }
                                    }
                                }   else
                                {
                                    try {
                                        order.addLineItem(sku, "" + (item.quantity), (Float.parseFloat(item.'subtotal'.toString()) / Integer.parseInt(item.'quantity'.toString())) + "", "0.00", title, "", "");
                                        LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                        litem.client_ref_num = item.id
                                    }catch (Exception e){
                                        if(!ignoreUnknownSku){
                                            throw e;
                                        }
                                    }
                                }

                            }


                    String shipMethod = orderData?.'shipping_methods'
                    println 'Ship via:' + shipMethod
                    assignShipMethod(order, shipMethod)
                    doFinalStuffBeforeSavingOrder(order)
                    if (order.getTotalPhysicalUnitQuantity() > 0) {

                        String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);
                        log.debug("reference=" + reference);

                        if (reference == null) {
                            if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                                    &&
                                    (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1) {
                                log.debug("reporting error on import");
                                throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                            } else {
                                //duplicate
                            }
                        } else {
                            log.debug("got valid order import");

                        }

                    }

                }

        }catch(Exception ex)
        {
            ex.printStackTrace()
            try {
                throw new LogableException(ex, ex.getMessage(),
                        orderRef,
                        clientId+"",
                        this.getClass().getName(),
                        LogableException.errorTypes.ORDER_IMPORT);
            } catch (Exception mailex) {
                mailex.printStackTrace();
            }
        }


    }

    public void markOrderTracking(int wooOrderId,String trcking)
    {
        RESTClient endpoint = getEndpoint()

        def json = new JsonBuilder()


        json {
            order_meta([shipping_tracking_id : trcking])
        }

        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp;
        if(clientId ==390){
            resp = endpoint.post(
                    path: 'wc-api/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    body: json.toString()
            )
        }else {
            resp = endpoint.put(
                    path: 'wc-api/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    body: json.toString()
            )

        }
        // println resp.getEntity().
        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}

    }
    public void markOrderShipped(int wooOrderId)
    {
        RESTClient endpoint = getEndpoint()

        def json = new JsonBuilder()


        json {
            order([status : 'completed'])
        }

        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp;
        if(clientId ==390||clientId == 432){
            json {
                order([status : 'completed'])
            }
            resp = endpoint.post(
                    path: 'wc-api/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    query:["consumer_key":key, "consumer_secret":secret],
                    body: json.toString()
            )
        }else {
            resp = endpoint.put(
                    path: 'wc-api/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    body: json.toString()
            )

        }
       // println resp.getEntity().
        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}

    }

    String skipItemString = null;

    public void setSkipItemsWithNameContainingString(String downloadable) {

        skipItemString = downloadable;
    }

    String firstOrderId = "0";
    public void setFirstOrderId(String s) {
        firstOrderId = s;
    }
    public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
    {

    }
}
