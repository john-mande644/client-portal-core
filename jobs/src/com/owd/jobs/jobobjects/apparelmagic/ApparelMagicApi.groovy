package com.owd.jobs.jobobjects.apparelmagic

import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdInventory
import org.apache.commons.lang.StringUtils
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException
import com.owd.core.business.OwdApiClient
import com.owd.core.managers.InventoryManager
import com.owd.jobs.jobobjects.shopify.ShopifyAPI
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.commons.collections.MapUtils
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit;

/**
 * Created by stewartbuskirk1 on 7/23/15.
 */
class ApparelMagicApi {
    private String pub_key
    private String private_key
    private int clientId
    private String host;
    private ShopifyAPI shopify;


    private ApparelMagicApi() {

    }

    public ApparelMagicApi(int clientId, String host, String publicKey, String privateKey) {
        this.pub_key = publicKey
        this.private_key = privateKey
        this.clientId = clientId
        this.host = host
    }

    ShopifyAPI getShopify() {
        return shopify
    }

    void setShopify(ShopifyAPI shopify) {
        this.shopify = shopify
        this.shopify.setClientId(getClientId())
    }

    String getHost() {
        return host
    }

    void setHost(String host) {
        this.host = host
    }

    int getClientId() {
        return clientId
    }

    void setClientId(int clientId) {
        this.clientId = clientId
    }

    public RESTClient getEndPoint() {
        def ep = new RESTClient(getHost())
        /*ep.client.addRequestInterceptor(
                { HttpRequest httpRequest, HttpContext httpContext ->
                    httpRequest.addHeader('Authorization', 'Basic ' + ((prodLogin + ':' + prodPass).bytes.encodeBase64().toString()))
                } as HttpRequestInterceptor);*/

        ep.defaultRequestHeaders.'Accept' = "application/json"
        ep.defaultRequestHeaders.'Content-Type' = "application/json"

        ep.handler.failure = { resp, data ->
            resp.setData(data)
            String headers = ""
            resp.headers.each {
                headers = headers + "${it.name} : ${it.value}\n"
            }
            //  throw new HttpResponseException(resp.getStatus(),"HTTP call failed. Status code: ${resp.getStatus()}\n${headers}\n"+
            //          "Response: "+(resp as HttpResponseDecorator).getData())
            println headers
            println data
            return resp
        }

        return ep;
    }

    public Map postInventoryAdjustment() {
        List filterList = new ArrayList();

        Map filterMap = new HashMap()

        Map headerMap = new HashMap()
        Map itemMap = new LinkedHashMap()
        List itemList = new ArrayList()

        headerMap.put('vendor_name', 'OWD Adjustment')

        itemMap.put('sku_id', '342676656')
        itemMap.put('qty', '-40')

        itemList.add(itemMap)
        filterMap.put('header', headerMap)
        filterMap.put('items', itemList)
        filterList.add(filterMap);


        String reqMap = getPHPDataString(filterMap)

        println reqMap
        //  println builder.toPrettyString()
        getEndPoint().post(path: "/api/receivers/",
                contentType: "application/x-www-form-urlencoded",
                body: reqMap,
                queryString: reqMap) { resp, reader ->

            println reader
            //   println JsonOutput.prettyPrint(JsonOutput.toJson(reader))
            return reader
/*            reader.response.each{ it ->
                if(it.active.equals("1") && it.sku_alt.length()>0) { */
        }


    }

    public static void main(String[] args) {
      /*  ApparelMagicApi api = new ApparelMagicApi(607, "https://mnml.app.apparelmagic.com", "4fd33e4ed613620701716fabd3b158e03490a106", "e2862efb5efe8eaa9161f4ee189c414bd478f28d")
*/
        ApparelMagicApi amApi = new ApparelMagicApi(551,"https://dope.app.apparelmagic.com","42a98c9cf1dffbd86f907689dd768b1f92208d1c","81fcb72f5d182afbd70cbf4438e22cb62ce3dbac");

       /*  def reader = amApi.getPickTicketJson(8055)
        println reader;

         reader = amApi.getPickTicketJson(8058)
        println reader;
*/

        def reader = amApi.getShipMethods();
        println reader;

         JsonOutput.prettyPrint(reader)



        //  println   api.getHashedRequest(null)
        // api.setShopify( new ShopifyAPI("5deb2a0bca1ea2cb0cd847a40a572120",
        //       "5cf111c642613a720f603e2ebee552f2", "dopetemplate.myshopify.com"))

        /* def reader =  api.getVendors()
         reader.response.each { it ->
                 println it.vendor_name+":"+it.vendor_id

         }*/

        //   api.postInventoryAdjustment()
        //  println JsonOutput.prettyPrint(
        //    def     JsonOutput.toJson(api.getInventory())
        //  )
        /*  def results =  api.getInventoryForSku("U2015-X500-WHT-0")
          println     '1'+results*/
        // def r = JsonOutput.toJson(results)

        /* results.response.each { it ->
             println     it.sku_id+":"+it.qty_inventory+":"+it.qty_avail_sell

         }*/
      //  String[] filter = ["M","L"]
      //  api.syncAmSkusToOwd(true,filter );
        /* boolean loop = true;
         int lowerId = 1000;
         int upperId = 1499;
         while(loop) {
             ArrayList<Object> filterList = new ArrayList<>();
             Map m = new HashMap();
             *//* m.put("field","active");
         m.put("operator","=");
         m.put("value","");*//*
             m.put("field", "sku_id");
             m.put("operator", ">=");
             m.put("value", lowerId+"");
             m.put("include_type", "AND")
             filterList.add(m);
             Map mm = new HashMap();
             mm.put("field", "sku_id");
             mm.put("operator", "<");
             mm.put("value", upperId+"");
             mm.put("include_type", "AND")
             filterList.add(mm);
             boolean hit = false;
             String reqMap = api.getPHPRequestString(filterList);
             def builder = new JsonBuilder()
             builder(reqMap)
             System.out.println("Builder");
             println builder.toPrettyString()
             api.getEndPoint().get(path: "/api/inventory",
                     contentType: ContentType.JSON,
                     queryString: reqMap) { resp, reader ->

                 // println JsonOutput.prettyPrint(JsonOutput.toJson(reader))
                 System.out.println(JsonOutput.toJson(reader).length());
                 // ExecutorService exec = Executors.newFixedThreadPool(3);
                 System.out.println("before reader.response");
                 reader.response.each { it ->

                     println it.sku_alt + ":" + it.sku_id;
                     hit = true;

                 }
             }

             if(hit){
                 lowerId = lowerId + 500;
                 upperId = upperId + 500;
             }else{
                 loop = false;
             }


         }*/

        //   filterList.add(filterMap);
        // println api.getPHPRequestString(1438964846,'xxxx','xxxxx',filterList)
    }

    public Map getInventoryForSku(String sku) {

        List filterList = new ArrayList();

        Map filterMap = new HashMap()
        filterMap.put('field', 'sku_alt')
        filterMap.put('value', sku)
        filterMap.put('operator', '=')
        filterList.add(filterMap);


        String reqMap = getPHPRequestString(filterList)
        System.out.println(reqMap);


        //   println reqMap
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/inventory/",
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

                println JsonOutput.prettyPrint(JsonOutput.toJson(reader)).toString()
            return reader
/*            reader.response.each{ it ->
                if(it.active.equals("1") && it.sku_alt.length()>0) { */
        }

    }


    public Map<String, Long> getSkuAllocatedMap() {

        Map allocMap = new HashMap();

        List filterList = new ArrayList();

        Map filterMap = new HashMap()
        filterMap.put('field', 'qty_alloc')
        filterMap.put('value', '0')
        filterMap.put('operator', '!=')
        filterList.add(filterMap);


        String reqMap = getPHPRequestString(filterList)

        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/inventory/",
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

            // println JsonOutput.prettyPrint(JsonOutput.toJson(reader)).substring(0,999)

            reader.response.each { it ->
                if (it.active.equals("1") && it.sku_alt.length() > 0) {
                    //  println it.sku_alt
                    //  println JsonOutput.prettyPrint(JsonOutput.toJson(it))
                    // def jsonSlurper = new JsonSlurper()
                    /* def product = getProductJson(Integer.parseInt(it.product_id));
                     //  println product.class
                     println product.response.description[0]
                     println product.response.category[0]
                     println product.response.content[0]
                     println product.response.group[0]
                     println product.response.is_product[0]
                     println product.response.origin[0]
                     println product.response.retail_price[0]
                     println product.response.style_number[0]
                     println product.response.tariff_code[0]
                     println product.response.weight[0]*/
                    //  println it.upc_display
                    //  println it.attr_2
                    //  println it.attr_2_name
                    //  println it.size
                    //  println it.cost
                    String vendorName = ''

                    println it.qty_alloc
                    int alloc = Math.round(Double.parseDouble(it.qty_alloc + ''))
                    if (alloc > 0) {
                        allocMap.put(it.sku_alt, alloc)
                    }

                }
            }
            if (!(reader.toString().contains("error"))) {
                //ok
                println reader.toString()
            } else {
                /*     throw new LogableException((reader.toString()),
                             jsondata,
                             489 + '',
                             "Symphony API create order fail",
                             LogableException.errorTypes.UNDEFINED_ERROR);*/
            }
        }

        return allocMap
    }


    public void updateShopifyInventoryLevels() {
        if (getShopify() == null) {
            throw new Exception("Shoppify API not available for stock sync");
        }

        Map allocatedSkus = getSkuAllocatedMap()

    }

    int lowerId =0 ;
    int upperId = 0;

    public void syncAmSkusToOwd(boolean updateSkus,int lId, int uId){
        lowerId = lId;
        upperId = uId;
        syncAmSkusToOwd(updateSkus, new String[0],false);

    }
    public void syncAmSkusToOwd(boolean updateSkus) {
        syncAmSkusToOwd(updateSkus, new String[0],false);
    }

    public void syncAmSkusToOwd(boolean updateSkus, String[] beginFilter,boolean updateCountryOnly) {
       // System.out.println("Getting skus");
        List<String> owdSkus = InventoryManager.getSkusForClient(getClientId())
        // List<String> shopifySkus = getShopify().getSkuList()
      //  System.out.println("loaded");


        boolean loop = true;
       if(lowerId==0) lowerId = 1000;
       if(upperId==0) upperId = 1499;
        //Map<String,String> csv = new HashMap<>();

        while (loop) {
            System.out.println("Doing ids "+lowerId + "-" + upperId);
            ArrayList<Object> filterList = new ArrayList<>();
            Map m = new HashMap();
            /* m.put("field","active");
        m.put("operator","=");
        m.put("value","");*/
            m.put("field", "sku_id");
            m.put("operator", ">=");
            m.put("value", lowerId + "");
            m.put("include_type", "AND")
            filterList.add(m);
            Map mm = new HashMap();
            mm.put("field", "sku_id");
            mm.put("operator", "<");
            mm.put("value", upperId + "");
            mm.put("include_type", "AND")
            filterList.add(mm);
            boolean hit = false;

            String reqMap = getPHPRequestString(filterList)

            //  println "reqMap="+reqMap

            def builder = new JsonBuilder()
            builder(reqMap)
            System.out.println("Builder");
           // println builder.toPrettyString()
            getEndPoint().get(path: "/api/inventory",
                    contentType: ContentType.JSON,
                    queryString: reqMap) { resp, reader ->

                println JsonOutput.prettyPrint(JsonOutput.toJson(reader)).substring(0, 35)
                ExecutorService exec = Executors.newFixedThreadPool(3);
                System.out.println("before reader.response");
                reader.response.each { it ->
                    hit = true;
                    if (it.active.equals("1") && it.sku_alt?.length() > 0) {
                        println it.sku_alt
                      //  csv.put(it.sku_alt,it.sku_id);
                        if ((beginFilter.length > 0 && StringUtils.startsWithAny(it.sku_alt,beginFilter)) || beginFilter.length == 0) {
                            //  println JsonOutput.prettyPrint(JsonOutput.toJson(it))
                            // def jsonSlurper = new JsonSlurper()
                            //  println product.class
                            //  println product.response.description[0]
                            //  println product.response.category[0]
                            //   println product.response.content[0]
                            //   println product.response.group[0]
                            //   println product.response.is_product[0]
                            //    println product.response.origin[0]
                            //   println product.response.retail_price[0]
                            //   println product.response.style_number[0]
                            //   println product.response.tariff_code[0]
                            //   println product.response.weight[0]
                            //   println it.upc_display
                            //   println it.attr_2
                            //   println it.attr_2_name
                            //   println it.size
                            //   println it.cost
                            String vendorName = ''


                            if (!(owdSkus.contains(it.sku_alt))) {
                                def product = getProductJson(Integer.parseInt(it.product_id));

                                if (product.response.vendor_id[0] != null) {
                                    try {
                                        def vendor = getVendorJson(Integer.parseInt(product.response.vendor_id[0]))
                                        vendorName = vendor.response.vendor_name[0]
                                        if (vendorName.size() > 50) {
                                            vendorName = vendorName.substring(0, 49);
                                        }
                                    } catch (Exception ex) {

                                    }
                                }

                                println "OWD:Creating " + it.sku_alt
                                try {
                                    OwdApiClient.createPhysicalSku(clientId, it.sku_alt, product.response.description[0] + '-' + it.attr_2 + '-' + it.size,
                                            Float.parseFloat(product.response.retail_price[0]), vendorName,
                                            0.0f,
                                            product.response.tariff_code[0] + '/Made in ' + getOriginCountry(product.response.origin[0].toString()) + '/Apparel/' + product.response.content[0],
                                            Float.parseFloat(it.cost),
                                            product.response.category[0], it.attr_2_name, it.size, false);
                                } catch (Exception ex) {
                                    if (!(ex.getMessage().contains("already exists"))) {
                                       try{
                                           throw new LogableException("ApparelMagicSyncError: " + e.getMessage(), it.sku_alt, clientId + "", "SkuCreation", LogableException.errorTypes.UNDEFINED_ERROR);
                                       }catch (Exception e){

                                       }

                                    }
                                }


                            } else {
                                if (updateSkus) {
                                    exec.submit(new skuCheckTask(it, this, clientId + "",updateCountryOnly));
                                }

                            }


                        } else {
                            println("Doesn't fit filter");

                        }
                    }
                }

                exec.shutdown();
                println("wating executor");
                exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                println("done");
                if (!(reader.toString().contains("error"))) {
                    //ok

                } else {
                    /*     throw new LogableException((reader.toString()),
                                 jsondata,
                                 489 + '',
                                 "Symphony API create order fail",
                                 LogableException.errorTypes.UNDEFINED_ERROR);*/
                }
            }

            //Check to see if we had sku's and increment
            if (hit) {
                lowerId = lowerId + 500;
                upperId = upperId + 500;
            } else {
                loop = false;
            }

        }
       /* File f = new File("apparel.csv");
        System.out.println(f.getAbsolutePath());
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(f));

            for(String key:csv.keySet()){
                writer.append(key+","+csv.get(key)+"\r\n");


            }
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }*/


    }

    private String getOriginCountry(String country){
        if(country.equals("CN")) return "China";
        if(country.equals("IN")) return "India";

        return "USA";
    }
    private Map getProductJson(int id) {
        String reqMap = getPHPRequestString(new ArrayList())

        // println "getting product "+id
        //  def builder = new JsonBuilder()
        //   builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/products/" + id,
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }

    private Map getInventory() {
        String reqMap = getPHPRequestString(new ArrayList())

        // println "getting product "+id
        //  def builder = new JsonBuilder()
        //   builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/inventory/",
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }

    private Map getVendors() {
        String reqMap = getPHPRequestString(new ArrayList())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/vendors/",
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }

    private Map getVendorJson(int id) {
        String reqMap = getPHPRequestString(new ArrayList())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/vendors/" + id,
                contentType: ContentType.JSON,
                queryString: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }

    private Map getShipMethods() {
        Map reqMap = getHashedRequestMap(new HashMap())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/ship_methods/",
                contentType: ContentType.JSON,
                query: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }

    private Map getPickTicketJson(int id) {
        Map reqMap = getHashedRequestMap(new HashMap())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/pick_tickets/" + id,
                contentType: ContentType.JSON,
                query: reqMap) { resp, reader ->

            //   println reader.class
            return reader

        }


    }


    private Map getOrderJson(int id) {
        Map reqMap = getHashedRequestMap(new HashMap())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/orders/" + id,
                contentType: ContentType.JSON,
                query: reqMap) { resp, reader ->

           // println JsonOutput.prettyPrint(JsonOutput.toJson(reader))//.substring(0,999)

            //   println reader.class
            return reader

        }


    }

    private Map getOrdersForOwdWarehouse() {
        //  Map reqMap = getHashedRequestMap(new HashMap())

        Map filterMap = new HashMap()
        filterMap.put('field', 'orders.warehouse_id')
        filterMap.put('value', '1002')
        filterMap.put('operator', '=')

        List filterList = new ArrayList();
        filterList.add(filterMap)
        //  def builder = new JsonBuilder()
        //   builder(reqMap)
        //  println builder.toPrettyString()
        String q = getPHPRequestString(filterList)
        getEndPoint().get(path: "/api/orders/",
                contentType: ContentType.JSON,
                queryString: q) { resp, reader ->

            // println JsonOutput.prettyPrint(JsonOutput.toJson(reader)).substring(0,9999)
            reader.response.each { it ->
                println it.order_id + ':' + it.warehouse_id

                //  println getPickTicketsForOrderid(Integer.parseInt(it.order_id))
                //  println 'status:'+it.status
                //  println it.date+':'+it.date_due+':'+it.date_start
                //  println it.qty+':'+it.qty_alloc+':'+it.qty_shipped
                //  it.order_items.each() { item ->
                //      println 'item:'+item.sku_id
                //      println 'item:'+it.qty+':'+it.qty_alloc+':'+it.qty_open
                //   }
            }
            //   println reader.class
            return reader

        }


    }


    private Map getPickTicketsForOrderid(int id) {
        Map filterMap = new HashMap()
        filterMap.put('field', 'pick_tickets.order_id')
        filterMap.put('value', '' + id)
        // filterMap.put('operator','=')

        List filterList = new ArrayList()
        filterList.add(filterMap)
        // println "getting product "+id
        //   def builder = new JsonBuilder()
        //   builder(reqMap)
        //    println builder.toPrettyString()

       // println getPHPRequestString(filterList)

        getEndPoint().get(path: "/api/pick_tickets/",
                contentType: ContentType.JSON,
                queryString: getPHPRequestString(filterList)) { resp, reader ->

         //   println JsonOutput.prettyPrint(JsonOutput.toJson(reader))//.substring(0,999)

            //   println reader.class
            return reader

        }


    }

    private Map getAllPickTickets() {
        Map filterMap = new HashMap()
        filterMap.put('field', 'pick_tickets.order_id')
        filterMap.put('value', '1001')
        // filterMap.put('operator','=')

        // println "getting product "+id
        //   def builder = new JsonBuilder()
        //   builder(reqMap)
        //    println builder.toPrettyString()

       // println getPHPRequestString(new ArrayList())

        getEndPoint().get(path: "/api/pick_tickets/",
                contentType: ContentType.JSON,
                queryString: getPHPRequestString(filterMap)) { resp, reader ->

          //  println JsonOutput.prettyPrint(JsonOutput.toJson(reader))//.substring(0,999)

            //   println reader.class
            return reader

        }


    }

    private Map getAllWarehouses() {
        Map reqMap = getHashedRequestMap(new HashMap())

        // println "getting product "+id
        def builder = new JsonBuilder()
        builder(reqMap)
        //  println builder.toPrettyString()
        getEndPoint().get(path: "/api/warehouses/",
                contentType: ContentType.JSON,
                query: reqMap) { resp, reader ->

            println JsonOutput.prettyPrint(JsonOutput.toJson(reader))//.substring(0,999)

            //   println reader.class
            return reader

        }


    }

    private String getPHPDataString(Long time, String apiKey, String privateKey, Map filterList) {

        Map<String, Object> m = [time: '' + time, api_key: apiKey]


        if (filterList.size() > 0) {
            m.put('0', filterList)//builder.toString());
        }
        //println JsonOutput.toJson(m).toString()
        println URLEncoder.encode(JsonOutput.toJson(m).toString())

        String hash = calculateRFC2104HMAC(URLEncoder.encode(JsonOutput.toJson(m).toString()), privateKey)
        m.put('hash', hash)
        //  println m
        return PhpSimulator.httpBuildQuery(m, 'UTF-8')

        // strBuilder.append(",{\"hash\":\""+calculateRFC2104HMAC(jsonRequest,key)+"\"}}")
    }

    private String getPHPRequestString(Long time, String apiKey, String privateKey, List filterList) {

        Map<String, Object> m = [api_key: apiKey, time: '' + time]


        if (filterList.size() > 0) {
            m.put('parameters', filterList)//builder.toString());
        }
      //  println JsonOutput.toJson(m).toString()
        //  println URLEncoder.encode(JsonOutput.toJson(m).toString())

        String hash = calculateRFC2104HMAC(URLEncoder.encode(JsonOutput.toJson(m).toString()), privateKey)
        m.put('hash', hash)
        //  println m
        return PhpSimulator.httpBuildQuery(m, 'UTF-8')

        // strBuilder.append(",{\"hash\":\""+calculateRFC2104HMAC(jsonRequest,key)+"\"}}")
    }

    private String getPHPDataString(Map filterList) {


        return getPHPDataString((System.currentTimeMillis() / 1000L).longValue(), pub_key, private_key, filterList)

    }

    private String getPHPRequestString(List filterList) {


        return getPHPRequestString((System.currentTimeMillis() / 1000L).longValue(), pub_key, private_key, filterList)

    }

    private Map getHashedRequestMap(Map jsonRequest) {

        def m = [time: '' + ((int) (System.currentTimeMillis() / 1000L)), api_key: pub_key]


        if (jsonRequest?.size() > 0) {
            def builder = new JsonBuilder()
            builder(jsonRequest);
           // println builder.toString()
            m.put('0', jsonRequest)//builder.toString());
        }
        // println builder.toString()
        //  println builder.toPrettyString()
        // println URLEncoder.encode(builder.toString(),'UTF-8')
        def builder = new JsonBuilder()
        builder(m)
        String hash = calculateRFC2104HMAC(URLEncoder.encode(builder.toString(), 'UTF-8'), private_key)
        m.put('hash', hash)
        //  println m
        return m

        // strBuilder.append(",{\"hash\":\""+calculateRFC2104HMAC(jsonRequest,key)+"\"}}")
    }

/**
 * Computes RFC 2104-compliant HMAC signature.
 * * @param data
 * The data to be signed.
 * @param key
 * The signing key.
 * @return
 * The Base64-encoded RFC 2104-compliant HMAC signature.
 * @throws
 * java.security.SignatureException when signature generation fails
 */
    public static String calculateRFC2104HMAC(String data, String key)
            throws SignatureException {
        String result;
        try {

// get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

// get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

// compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());
            //   println rawHmac
// base64-encode the hmac
            result = org.codehaus.groovy.runtime.EncodingGroovyMethods.encodeHex(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}
