package com.owd.jobs.jobobjects.woocommerce

import com.owd.LogableException
import com.owd.core.CountryNames
import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.utilities.RateShopper
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
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
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * Created by stewartbuskirk1 on 6/29/15.
 */
class WooCommerceWordPressAPI {
    private final static Logger log =  LogManager.getLogger();

    int clientId = 0;
    private String key = "";
    private version = "2"
    private String secret = ""

    private rootUrl = ""
    private boolean useProductId = false

    boolean getUseProductId() {
        return useProductId
    }

    void setUseProductId(boolean useProductId) {
        this.useProductId = useProductId
    }

    String getKey() {
        return key
    }

    String getSecret() {
        return secret
    }

    public static void main(String[] args)
    {

       // WooCommerceAPI api = new WooCommerceAPI(390, "https://welltrainedmind.com/", "ck_90e1dfb0d09e8e9864c10d5cd092e246a58a7615", "cs_99a0794901678f3396b2ef7e2fc84ab39edfb62d", "3")

        WooCommerceWordPressAPI api = new WooCommerceWordPressAPI(600, "https://wearehandsome.com/", "ck_fdd87e9d3eb125fdc21f1f35e7921f83529bcf71", "cs_b52ddcce0ddcab0a921c323418dc170eb6f7b229","1",true)

            //  println Integer.parseInt("W12345".substring(1))
           //  api.markOrderShipped(22311)
        api.getSingleOrder("27997");
       // println api.importOrdersPeaceHillPress()
      //  api.listOrders();
      //  api.listProductSkus();

      // println api.markOrderTracking(23742,"9274899998807425017116");
//
      //  println api.markOrderShipped(67211)
    }

    public WooCommerceWordPressAPI(int clientKey, String url, String consumerKey, String consumerSecret, String aversion){
        clientId = clientKey
        key = consumerKey
        secret = consumerSecret
        rootUrl = url
        version = aversion
        useProductId = false



    }

    public WooCommerceWordPressAPI(int clientKey, String url, String consumerKey, String consumerSecret, String aversion, Boolean auseProductId)
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
                path: 'wp-json/wc/v'+version+'/products'   ,

                requestContentType: 'application/json',
                contentType: 'application/json'
        )

                {resp, reader ->

                    reader.each {
                        assert it instanceof LazyMap
                        // println it
                        println(it.sku)
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
                path: 'wp-json/wc/v'+version+'/orders'   ,
                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                query: [status: 'processing','filter[flag_virtual]':'true']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))
                    reader.each {
                        assert it instanceof LazyMap
                        skus.add(it.toString())
                        println(it.view_order_url);
                        println(it.gift_message);


                    }
                }

        return skus
    }

    public void getSingleOrder(String orderRef)
    {
        RESTClient endpoint = getEndpoint()

        List<String> skus = new ArrayList<String>()

        endpoint.get(
                path: 'wp-json/wc/v'+version+'/orders/'+orderRef   ,
                requestContentType: 'application/json',
                contentType: 'application/json'  ,

        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))

                    System.out.println(reader.shipping_lines);
                    System.out.println(reader.shipping_lines[0].method_title);

                    if(null!=reader?.shipping_lines[0]){
                        System.out.println(reader?.shipping_lines[0].method_title);
                    }
                }


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
                    path: 'wp-json/wc/v' + version + '/orders',

                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    query: [status: 'processing', 'filter[flag_virtual]': 'true', "page": p]
            )
                    { resp, reader ->

                        println resp.headers['X-WP-Total']
                        ptotal = Integer.parseInt(""+resp.headers['X-WP-TotalPages'].value)
                        println reader
                        reader.each {

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
                path: 'wp-json/wc/v'+version+'/orders'   ,

                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                query: [status: 'processing', order:'desc']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))

                    reader.each {
                        println "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                        println it.toString()

                        skus.add(it.toString())
                        assert it instanceof LazyMap
                        String s = it.shipping_lines[0].method_title
                        println(s)
                        Float f = Float.parseFloat(it.shipping_lines[0].'total');
                        println f;
                        it.'line_items'?.each()
                                { item ->
                                    println(item.product_id)
                                    println(item.variation_id)
                                    if(item.variation_id ==0){
                                        println("need to do product id")
                                    }
                                }

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
                    path: 'wp-json/wc/v' + version + '/orders',

                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    query: [status: 'processing', "page": p,order:'asc']
            )
                    { resp, reader ->

                        println resp.headers['X-WP-Total']
                        ptotal = Integer.parseInt(""+resp.headers['X-WP-TotalPages'].value)
                        println reader
                        reader.each {

                            importOrder(it)
                            i++
                        }
                    }
            p++
        }
        println "Total Orders Checked: "+i
        return skus
    }


    public void importOrder(def orderData)
    {
        assert orderData instanceof LazyMap
        String orderRef = ""+orderData.'number'
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
            println "W"+orderData.'number' +":"+OrderUtilities.orderRefnumExists("W"+orderData.'number', clientId + "")
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

                    println orderData.'billing'.'first_name' + ' ' + orderData.'billing'.'last_name'

                    order.getBillingContact().name = orderData.'billing'.'first_name' + ' ' + orderData.'billing'.'last_name'
                    order.getBillingContact().email = orderData.'billing'.email
                    order.getBillingContact().phone = orderData.'billing'.phone
                    order.getBillingAddress().company_name = orderData.'billing'.company
                    order.getBillingAddress().address_one = orderData.'billing'.address_1
                    order.getBillingAddress().address_two = orderData.'billing'.address_2
                    order.getBillingAddress().city = orderData.'billing'.city
                    order.getBillingAddress().state = orderData.'billing'.'state'
                    order.getBillingAddress().zip = orderData.'billing'.postcode
                    order.getBillingAddress().country = orderData.'billing'.'country'

                    if(clientId==600){
                        if(orderData.'shipping'.'first_name'.length()>0){
                            order.getShippingContact().name = orderData.'shipping'.'first_name' + ' ' + orderData.'shipping'.'last_name'

                        }else{
                            order.getShippingContact().name = orderData.'billing'.'first_name' + ' ' + orderData.'billing'.'last_name'
                        }
                    }else{

                        order.getShippingContact().name = orderData.'shipping'.'first_name' + ' ' + orderData.'shipping'.'last_name'

                    }

                     println orderData.'shipping'.'first_name' + ' ' + orderData.'shipping'.'last_name'
                    order.getShippingContact().email = orderData.'billing'.email
                    order.getShippingContact().phone = orderData.'billing'.phone
                    order.getShippingAddress().company_name = orderData.'shipping'.company
                    order.getShippingAddress().address_one = orderData.'shipping'.address_1
                    order.getShippingAddress().address_two = orderData.'shipping'.address_2
                    order.getShippingAddress().city = orderData.'shipping'.city
                    order.getShippingAddress().state = orderData.'shipping'.'state'
                    order.getShippingAddress().zip = orderData.'shipping'.postcode
                    order.getShippingAddress().country = orderData.'shipping'.'country'


                    order.is_paid = 1;
                    order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                    order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                    order.discount = 0.00f
                    order.total_tax_cost = 0.00f

                    if(null== orderData.shipping_lines[0]){
                        order.total_shipping_cost = Float.parseFloat("0.0")
                    }   else{
                        order.total_shipping_cost = Float.parseFloat(orderData.shipping_lines[0].'total')
                    }

                    orderData.'line_items'?.each()
                            { item ->


                                String sku
                                if(useProductId){
                                    if(item.variation_id == 0){
                                        println("Doing product Id")
                                        sku = item.product_id;
                                    }else{
                                        println("Doing variation Id")
                                        sku = item.variation_id;
                                    }

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
                                        order.addLineItem(sku, "" + (item.quantity), item.price + "", "0.00", title, "", "");
                                        LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                        litem.client_ref_num = item.id
                                    }
                                }   else
                                {
                                    order.addLineItem(sku, "" + (item.quantity), item.price + "", "0.00", title, "", "");
                                    LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                    litem.client_ref_num = item.id
                                }

                            }


                    String shipMethod = "empty"
                          if(null!=orderData?.shipping_lines[0]){
                              shipMethod = orderData?.shipping_lines[0].method_title
                          }

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

    public void markOrderTracking(int wooOrderId,String trcking,String shipMethod)
    {
       String[] tracks = trcking.split(",");
        for(String ss:tracks) {
            System.out.println(ss);


            RESTClient endpoint = getEndpoint()

            def json

            if(shipMethod.contains("DHL")){

                  json= JsonOutput.toJson([tracking_provider: "DHLExpress",
                           tracking_number:ss])

            }else if(shipMethod.contains("FedEx")){
                json= JsonOutput.toJson([tracking_provider: "FedEx",
                                         tracking_number:ss])
            }else if(shipMethod.contains("USPS")){
                json= JsonOutput.toJson([tracking_provider: "USPS",
                                         tracking_number:ss])
            }else if(shipMethod.contains("UPS")){
                json= JsonOutput.toJson([tracking_provider: "UPS",
                                         tracking_number:ss])
            }else{
                throw new Exception("Unknown shipper for tracking plugin")
            }



            println json
            groovyx.net.http.HttpResponseDecorator resp;

                resp = endpoint.post(
                        path: 'wp-json/wc/v' + version + '/orders/' + wooOrderId+'/shipment-trackings',
                        requestContentType: 'application/json',
                        contentType: 'application/json',
                        body: json
                )


             println resp.getEntity()
                println resp.getStatus()
            println resp.getData()

            if (!(resp.status == 200 || resp.status == 201)) {
                throw new Exception("Bad response status: " + resp.status);
            }
        }

    }
    public void markOrderShipped(int wooOrderId)
    {
        RESTClient endpoint = getEndpoint()

        def json

        json= JsonOutput.toJson([status : 'completed'])



        println json
        groovyx.net.http.HttpResponseDecorator resp;
        if(clientId ==390){
            resp = endpoint.post(
                    path: 'wp-json/wc/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    body: json
            )
        }else {
            resp = endpoint.put(
                    path: 'wp-json/wc/v' + version + '/orders/' + wooOrderId,
                    requestContentType: 'application/json',
                    contentType: 'application/json',
                    body: json.toString()
            )

        }
        println resp.getEntity()
        println resp.status
        println resp.data



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
