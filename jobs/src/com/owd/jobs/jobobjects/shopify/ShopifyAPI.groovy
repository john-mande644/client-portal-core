package com.owd.jobs.jobobjects.shopify

import groovy.xml.XmlUtil
import org.apache.commons.httpclient.cookie.CookiePolicy
import org.apache.http.client.params.ClientPNames
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException
import com.owd.core.CountryNames
import com.owd.core.OWDUtilities
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.InventoryManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.AbstractIntegrationApi
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.NodeChild
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.BufferedHttpEntity
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/14/12
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
class ShopifyAPI extends AbstractIntegrationApi {
    static Logger log = LogManager.getLogger(this.name)

    private String apiKey = "";
    private String password = "";
    private String shopHost = "";
    private String locationId = "";
    private boolean useOrderName = false;
    private List<String> preSaleSkus = new ArrayList<>();
    //setting this with tag to search for as key, and group name to assign

    private Map<String,String> tagToGroupNameMap = new HashMap<>();

    Map<String, String> getTagToGroupNameMap() {
        return tagToGroupNameMap
    }

    void setTagToGroupNameMap(Map<String, String> tagToGroupNameMap) {
        this.tagToGroupNameMap = tagToGroupNameMap
    }
    private String fulfillmentServiceCode = "";
    private String blankItemSku = "";
    private String otherFulfillmentSku = "";

    private String orderReferencePrefix="";

    private boolean ignoreUnrecognizedSKUs = false;

    private int firstOrderId = 0;

    private String orderType = "Shopify";

    private Map<String, Long> importedItemMap = new HashMap<String, Long>();

    Map<String, Long> getImportedItemMap() {
        return importedItemMap
    }

    void setImportedItemMap(Map<String, Long> importedItemMap) {
        this.importedItemMap = importedItemMap
    }

    String getOrderType() {
        return orderType
    }

    void setOrderType(String orderType) {
        this.orderType = orderType
    }

    boolean getIgnoreUnrecognizedSKUs() {
        return ignoreUnrecognizedSKUs
    }

    void setIgnoreUnrecognizedSKUs(boolean ignoreUnrecognizedSKUs) {
        this.ignoreUnrecognizedSKUs = ignoreUnrecognizedSKUs
    }

    String getOrderReferencePrefix() {
        return orderReferencePrefix
    }

    void setOrderReferencePrefix(String orderReferencePrefix) {
        this.orderReferencePrefix = orderReferencePrefix
    }

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
        this.fulfillmentServiceCode = ""+fulfillmentServiceCode
    }

    String getBlankItemSku() {
        return blankItemSku
    }

    void setBlankItemSku(String blankItemSku) {
        this.blankItemSku = ""+blankItemSku
    }

    String getOtherFulfillmentSku() {
        return otherFulfillmentSku
    }
    boolean sendLocationWithInventory = false;
    boolean forceAllSkuSync = false;

    void setOtherFulfillmentSku(String otherFulfillmentSku) {
        this.otherFulfillmentSku = ""+otherFulfillmentSku
    }

    public ShopifyAPI(String apiKey, String password, String shopHost,String lid) {
        this.apiKey = apiKey;
        this.password = password;
        this.shopHost = shopHost
        this.locationId = lid

        setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))
    }

    List<String> getPreSaleSkus() {
        return preSaleSkus
    }

    void setPreSaleSkus(List<String> preSaleSkus) {
        this.preSaleSkus = preSaleSkus
    }

    protected void addToImportedItemMap(String sku, long quantity)
    {
        if(!(importedItemMap.containsKey(sku)))
        {
          importedItemMap.put(sku,0);
        }
        importedItemMap.put(sku,importedItemMap.get(sku)+quantity)
    }

    boolean getUseOrderName() {
        return useOrderName
    }

    void setUseOrderName(boolean useOrderName) {
        this.useOrderName = useOrderName
    }

    static void main(String[] args) {


        ShopifyAPI api = new ShopifyAPI("70ceff34de1447fa3e6d3ba2c40b2c3c",
                "89f51d678f851fa79975b91f1048880a", "the-new-parallel.myshopify.com","22442824");
        api.printListOfLocations();
        //   api.setSendLocationWithInventory(true);

        api.updateInventoryLevelForItemAndLocation("12900649238591",0);

        //  api.setClientInfo(551, null);
     //   api.getProducts()

     //  Map skuMap = api.updateInventoryLevels()

       // println   skuMap.get('match')
//        println   skuMap.get('owd')
 //       println   skuMap.get('shopify')

         /*  api.setFirstOrderId(0);
          api.shipMethodMap = new TreeMap<String, List<String>>();
          api.shipMethodMap.put("Standard (Approx. 3 - 6 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail", "UPS Ground"));
          api.shipMethodMap.put("International Shipping (Approx. 6-13 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail International"));

          api.setOtherFulfillmentSku("SEPARATE");*/
        // api.setFulfillmentServiceCode("stewart");
     //   api.updateInventoryLevels();

    }

    public void createSku()
    {


    }
    public List getSkuList() throws Exception {
        DefaultHttpClient client = new DefaultHttpClient()
        List<String> shopifySkus=new ArrayList<String>()
        long cycleMillis = 1000    //not 500 due to internal variant update call
        long lastCall = System.currentTimeMillis()

        try {
            URIBuilder builder = new URIBuilder();
            int pages = Math.ceil(getProductCount()/250.00f)

            for(int i=1;i<=pages;i++) {
                builder.setScheme("https").setHost(shopHost).setPath("/admin/products.xml")
                        .setParameter("limit", "250")
                        .setParameter("page", "" + i)



                URI uri = builder.build();

                HttpGet httpget = new HttpGet(uri)
                httpget.addHeader("Accept", "text/xml")
                client.getCredentialsProvider().setCredentials(
                        new AuthScope(shopHost, AuthScope.ANY_PORT),
                        new UsernamePasswordCredentials(apiKey, password));

                while ((System.currentTimeMillis() - lastCall) < cycleMillis) {
                    int sleepMillis = cycleMillis - (System.currentTimeMillis() - lastCall)
                    if (sleepMillis > 0) {
                        sleep(sleepMillis)
                    }

                }
                log.debug("executing request " + httpget.getRequestLine());
                HttpResponse response = client.execute(httpget);
                HttpEntity entity = response.getEntity();

                log.debug("----------------------------------------");
                log.debug(response.getStatusLine());




                if (entity != null) {
                    if (entity != null) {
                        entity = new BufferedHttpEntity(entity);
                    }
                    String xmlstr = EntityUtils.toString(entity)

                    def xml = new XmlSlurper().parseText(xmlstr)

                    int prods = 0
                    xml.product.each() { product ->

                        //     println  'Product:'+product.title
                        product.variants.variant.each() { variant ->

                            String sku = variant.sku

                            shopifySkus.add(sku)


                            prods++

                        }
                    }
                    println "prods found: " + prods


                }
            }
        }catch(Exception ex)
        {
            println ex.getMessage()
            throw ex
        }
        return shopifySkus

    }


    public Map getCollectionsMap() throws Exception {
        DefaultHttpClient client = new DefaultHttpClient()
        Map<String,String> collectionMap=new HashMap<String,String>()


        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost(shopHost).setPath("/admin/custom_collections.xml")
            //  .setParameter("limit","250")

            URI uri = builder.build();

            HttpGet httpget = new HttpGet(uri)
            httpget.addHeader("Accept", "text/xml")
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(shopHost, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(apiKey, password));


            log.debug("executing request " + httpget.getRequestLine());
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            log.debug("----------------------------------------");
            log.debug(response.getStatusLine());




            if (entity != null) {
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                String xmlstr = EntityUtils.toString(entity)

                println xmlstr

                def xml = new XmlSlurper().parseText(xmlstr)

                int prods = 0
                xml.custom_collection.each() { it ->

                    //     println  'Product:'+product.title
                    println  it.title
                    collectionMap.put(it.id,it.title)


                }
              //  println "prods found: " + prods


            }

        }catch(Exception ex)
        {
            println ex.getMessage()
            throw ex
        }
        return collectionMap

    }

    public Map getProducts(){
        Map<String,String> productMap=new HashMap<String,String>()

        DefaultHttpClient client = new DefaultHttpClient()
        long cycleMillis = 1000    //not 500 due to internal variant update call
        long lastCall = System.currentTimeMillis()

        try {
            URIBuilder builder = new URIBuilder();
            int pages = Math.ceil(getProductCount()/250.00f)

            for(int i=1;i<=pages;i++) {
                builder.setScheme("https").setHost(shopHost).setPath("/admin/products.xml")
                        .setParameter("limit", "250")
                        .setParameter("page", "" + i)


                URI uri = builder.build();

                HttpGet httpget = new HttpGet(uri)
                httpget.addHeader("Accept", "text/xml")
                client.getCredentialsProvider().setCredentials(
                        new AuthScope(shopHost, AuthScope.ANY_PORT),
                        new UsernamePasswordCredentials(apiKey, password));

                while ((System.currentTimeMillis() - lastCall) < cycleMillis) {
                    int sleepMillis = cycleMillis - (System.currentTimeMillis() - lastCall)
                    if (sleepMillis > 0) {
                        sleep(sleepMillis)
                    }

                }
                log.debug("executing request " + httpget.getRequestLine());
                HttpResponse response = client.execute(httpget);
                HttpEntity entity = response.getEntity();

                log.debug("----------------------------------------");
                log.debug(response.getStatusLine());




                if (entity != null) {
                    if (entity != null) {
                        entity = new BufferedHttpEntity(entity);
                    }
                    String xmlstr = EntityUtils.toString(entity)

                    println xmlstr

                    def xml = new XmlSlurper().parseText(xmlstr)

                    xml.products.each() { it ->

                        //     println  'Product:'+product.title
                        println it.title
                        productMap.put(it.id, it.title)


                    }
                    //  println "prods found: " + prods


                }
            }

        }catch(Exception ex)
        {
            println ex.getMessage()
            throw ex
        }
        return productMap
    }



    public Map getInventoryStatusMap() throws Exception {
        DefaultHttpClient client = new DefaultHttpClient()
        long cycleMillis = 1000    //not 500 due to internal variant update call
        long lastCall = System.currentTimeMillis()
        Map skuMap = new HashMap()
        List<String> matchingSkus = new ArrayList<String>()
        List<String> shopifySkus = new ArrayList<String>()
        List<String> owdSkus = new ArrayList<String>()

        List<String> owdList = InventoryManager.getSkusForClient(clientId)
        println 'SKUs at OWD: ' + owdList.size()
        skuMap.put('match', matchingSkus)
        skuMap.put('shopify', shopifySkus)
        skuMap.put('owd', owdSkus)


        try {
            URIBuilder builder = new URIBuilder();
            int pages = Math.ceil(getProductCount()/250.00f)
            int prods = 0
            for(int i=1;i<=pages;i++) {
                builder.setScheme("https").setHost(shopHost).setPath("/admin/products.xml")
                        .setParameter("limit", "250")
                        .setParameter("page", "" + i)

                URI uri = builder.build();

                HttpGet httpget = new HttpGet(uri)
                httpget.addHeader("Accept", "text/xml")
                client.getCredentialsProvider().setCredentials(
                        new AuthScope(shopHost, AuthScope.ANY_PORT),
                        new UsernamePasswordCredentials(apiKey, password));

                while ((System.currentTimeMillis() - lastCall) < cycleMillis) {
                    int sleepMillis = cycleMillis - (System.currentTimeMillis() - lastCall)
                    if (sleepMillis > 0) {
                        sleep(sleepMillis)
                    }

                }
                lastCall = System.currentTimeMillis()
                log.debug("executing request " + httpget.getRequestLine());
                HttpResponse response = client.execute(httpget);
                HttpEntity entity = response.getEntity();

                log.debug("----------------------------------------");
                log.debug(response.getStatusLine());


                if (entity != null) {
                    if (entity != null) {
                        entity = new BufferedHttpEntity(entity);
                    }
                    String xmlstr = EntityUtils.toString(entity)

                    def xml = new XmlSlurper().parseText(xmlstr)


                    xml.product.each() { product ->

                             println  'Product:'+product.title
                        product.variants.variant.each() { variant ->

                            String sku = variant.sku

                            //  String mgmt = variant."inventory-management".text()
                            //  String qty = variant."inventory-quantity"
                            println sku + ':' + product.title

                            if (owdList.contains(sku)) {
                                matchingSkus.add(sku)
                            } else {
                                shopifySkus.add(sku + ':' + product.title)
                            }

                            prods++

                        }
                    }
                    println "prods found: " + prods
                    for (String osku : owdList) {
                        if (!(matchingSkus.contains(osku))) {
                            owdSkus.add(osku)
                        }
                    }
                }
            }
                return skuMap


        }catch(Exception ex)
        {
            println ex.getMessage()
        }

    }


    public int getProductCount()
    {
        DefaultHttpClient client = new DefaultHttpClient()


        URIBuilder builder2 = new URIBuilder();
        builder2.setScheme("https").setHost(shopHost).setPath("/admin/products/count.json")

        URI uri2 = builder2.build();

        HttpGet httpget = new HttpGet(uri2)
        httpget.addHeader("Accept", "application/json")
        client.getCredentialsProvider().setCredentials(
                new AuthScope(shopHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(apiKey, password));


     //   StringEntity myEntity = new StringEntity(json,
     //           ContentType.create("application/json", "UTF-8"));
     //   httpget.setEntity(myEntity)
        log.debug("executing request " + httpget.getRequestLine());
        HttpResponse response2 = client.execute(httpget);
        HttpEntity entity2 = response2.getEntity();


        String outstr ="";
                log.debug("----------------------------------------");
        log.debug(response2.getStatusLine());
        if (entity2 != null) {
            if (entity2 != null) {
                entity2 = new BufferedHttpEntity(entity2);
            }
             outstr = EntityUtils.toString(entity2)



        }

        if(response2.getStatusLine().getStatusCode()>300)
        {
            throw new Exception("Failed: "+response2.getStatusLine()+" :: "+outstr)
        }


        def json = new JsonSlurper().parseText(outstr)

        println outstr

        return json.count
    }


public void updateInventoryLevels() throws Exception {
    System.out.println(locationId)
          updateInventoryLevels(new HashMap<String,Long>())
}

    public void updateInventoryLevels(Map<String,Long> reserved) throws Exception {
        System.out.println(locationId)

        DefaultHttpClient client = new DefaultHttpClient()
        long cycleMillis = 1000    //not 500 due to internal variant update call
        long lastCall = System.currentTimeMillis()

        try {
            URIBuilder builder = new URIBuilder();
            int pages = Math.ceil(getProductCount()/250.00f)

            for(int i=1;i<=pages;i++) {
                builder.setScheme("https").setHost(shopHost).setPath("/admin/products.xml")
                        .setParameter("limit", "250")
                        .setParameter("page",""+i)

                URI uri = builder.build();

                HttpGet httpget = new HttpGet(uri)
                httpget.addHeader("Accept", "text/xml")
                client.getCredentialsProvider().setCredentials(
                        new AuthScope(shopHost, AuthScope.ANY_PORT),
                        new UsernamePasswordCredentials(apiKey, password));


                log.debug("executing request " + httpget.getRequestLine());
                while ((System.currentTimeMillis() - lastCall) < cycleMillis) {
                    int sleepMillis = cycleMillis - (System.currentTimeMillis() - lastCall)
                    if (sleepMillis > 0) {
                        sleep(sleepMillis)
                    }

                }
                lastCall = System.currentTimeMillis()
                HttpResponse response = client.execute(httpget);
                HttpEntity entity = response.getEntity();

                log.debug("----------------------------------------");
                log.debug(response.getStatusLine());
                if (entity != null) {
                    if (entity != null) {
                        entity = new BufferedHttpEntity(entity);
                    }
                    String xmlstr = EntityUtils.toString(entity)

                    def xml = new XmlSlurper().parseText(xmlstr)

                    xml.product.each() { product ->

                        println 'Product:' + product.title
                        product.variants.variant.each() { variant ->

                            String sku = variant.sku
                            String mgmt = variant."inventory-management".text()
                            String qty = variant."inventory-quantity"
                            println sku + ':' + mgmt + ':' + qty

                            if (mgmt.equals('shopify')& !preSaleSkus.contains(sku)) {
                                try {

                                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientId + "", sku);
                                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                                    if(reserved.containsKey(sku)) {
                                        oh -= reserved.get(sku)
                                        if(oh<0) {
                                            oh = 0
                                        }
                                    }
                                    println "UPDATE:" + oh
                                    if (oh != Long.parseLong(qty)||forceAllSkuSync) {
                                        println "TRIGGER " + qty + "->" + oh

                                        if(sendLocationWithInventory){
                                            updateInventoryLevelForItemAndLocation(variant."inventory-item-id".text(),oh)
                                        }else {
                                            updateInventoryLevelForVariant(variant, oh, Long.parseLong(qty))
                                        }
                                    }
                                } catch (Exception ex) {
                                    if (ex.getMessage().contains("does not exist") || ex.getMessage().contains("not found")||ex.getMessage().contains("Error updating qty for id:")) {
                                        println ex.getMessage()
                                    } else {
                                        throw ex
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }catch(Exception ex)
        {
            println ex.getMessage()
        }

    }

    public void updateInventoryLevelForItemAndLocation(String inventoryId, long oh) throws Exception
    {

        DefaultHttpClient client = new DefaultHttpClient()

        String json =
                "{\"location_id\": "+locationId+", \"inventory_item_id\": "+inventoryId+", \"available\": "+oh+",\"disconnect_if_necessary\": true}";


        println json

        URIBuilder builder2 = new URIBuilder();
        builder2.setScheme("https").setHost(shopHost).setPath("/admin/inventory_levels/set.json")

        URI uri2 = builder2.build();

        HttpPost httpput = new HttpPost(uri2)
       // httpput.addHeader("Accept", "application/json")
        client.getCredentialsProvider().setCredentials(
                new AuthScope(shopHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(apiKey, password));


        StringEntity myEntity = new StringEntity(json,
                ContentType.create("application/json", "UTF-8"));
        httpput.setEntity(myEntity)
        log.debug("executing request " + httpput.getRequestLine());
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.IGNORE_COOKIES);

        HttpResponse response2 = client.execute(httpput);
        HttpEntity entity2 = response2.getEntity();

        log.debug("----------------------------------------");
        log.debug(response2.getStatusLine());
        if(!response2.getStatusLine().toString().contains("200")){
            String outstr="Shopify inventory error";
            if (entity2 != null) {
                if (entity2 != null) {
                    entity2 = new BufferedHttpEntity(entity2);
                }
                 outstr = EntityUtils.toString(entity2)

                println outstr


            }
            if(!outstr.contains("This item can't be disconnected from its location because it has unfulfilled orders.")) {
                new LogableException(outstr, inventoryId, clientId + "", "Inventory UPdate", LogableException.errorTypes.UNDEFINED_ERROR);
            }

            throw new Exception("Error updating qty for id: "+inventoryId);
        }
        if (entity2 != null) {
            if (entity2 != null) {
                entity2 = new BufferedHttpEntity(entity2);
            }
            String outstr = EntityUtils.toString(entity2)

            println outstr


        }
    }

    public void updateInventoryLevelForVariant(NodeChild variant, long oh, long qty)
    {

        DefaultHttpClient client = new DefaultHttpClient()

        String json =
                "{\"variant\":{\"id\":"+variant.id+",\"inventory_quantity\":"+oh+",\"old_inventory_quantity\":"+qty+"}}"


        println json

        URIBuilder builder2 = new URIBuilder();
        builder2.setScheme("https").setHost(shopHost).setPath("/admin/variants/"+variant.id+".json")

        URI uri2 = builder2.build();

        HttpPut httpput = new HttpPut(uri2)
        httpput.addHeader("Accept", "application/json")
        client.getCredentialsProvider().setCredentials(
                new AuthScope(shopHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(apiKey, password));


        StringEntity myEntity = new StringEntity(json,
                ContentType.create("application/json", "UTF-8"));
        httpput.setEntity(myEntity)
        log.debug("executing request " + httpput.getRequestLine());
        HttpResponse response2 = client.execute(httpput);
        HttpEntity entity2 = response2.getEntity();

        log.debug("----------------------------------------");
        log.debug(response2.getStatusLine());
        if (entity2 != null) {
            if (entity2 != null) {
                entity2 = new BufferedHttpEntity(entity2);
            }
            String outstr = EntityUtils.toString(entity2)

            println outstr


        }
    }


    public boolean reportShipment(int orderId, String tracking, boolean sendCustomerEmail)
    {
       /*
        <?xml version="1.0" encoding="UTF-8"?>
<fulfillment>
  <tracking-number nil="true"></tracking-number>
  <notify-customer type="boolean">true</notify-customer>
  <line-items type="array">
    <line-item>
      <id type="integer">466157049</id>
    </line-item>
  </line-items>
</fulfillment>
         */

        boolean  success = false;

        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId)

        DefaultHttpClient client = new DefaultHttpClient()
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost(shopHost).setPath("/admin/orders/"+order.getPoNum()+"/fulfillments.xml")

            URI uri = builder.build();

            HttpPost httpput = new HttpPost(uri)
           // httpput.addHeader("Accept", "text/xml")


            client.getCredentialsProvider().setCredentials(
                    new AuthScope(shopHost, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(apiKey, password));


            int reportableItems = 0;
            for (OwdLineItem item: order.getLineitems()) {
                if(item.getQuantityActual()>0)
                {
                    reportableItems++;
                }
            }
            if(reportableItems>0)
            {
            def xbuilder = new groovy.xml.StreamingMarkupBuilder()
            xbuilder.encoding = 'UTF-8'
            def createFulfillmentRequest =
                {
                    mkp.xmlDeclaration()
                    fulfillment() {

                        if(locationId.length()>0){
                            'location-id'(locationId)
                        }
                        'tracking-number'(tracking)
                        String trackingCompany = getTrackingCompany(order.getShipinfo().carrService,order);
                        'tracking-company'(trackingCompany)
                        if(getTrackingCompany(order.getShipinfo().carrService,order).equals("BPD")){
                             tracking_url("http://bluepackage.com/tracking/results.php?track="+tracking)

                        }
                        if(getTrackingCompany(order.getShipinfo().carrService,order).equals("OSM")){

                            tracking_url("http://osmart.osmworldwide.us/tracking/results?trackingNumbers="+tracking)
                        }


                         'notify-customer'(type:'boolean',sendCustomerEmail?'true':'false')
                            'line-items'(type:'array')
                                    {
                                        order.getLineitems().forEach() {  item ->
                                            if(!(item.getCustRefnum().equals("")) && item.getQuantityActual()>0)
                                            'line-item'()
                                                    {
                                                        'id'(type:'integer',item.getCustRefnum())
                                                    }
                                        }


                                    }
                        }
                    }



            String putdata = xbuilder.bind(createFulfillmentRequest).toString()
                println putdata

                StringEntity myEntity = new StringEntity(putdata,
                        ContentType.create("text/xml", "UTF-8"));


               client.getParams().setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.IGNORE_COOKIES);
            httpput.setEntity(myEntity)
            log.debug("executing request " + httpput.getRequestLine());

            HttpResponse response = client.execute(httpput);
            HttpEntity entity = response.getEntity();

            log.debug("----------------------------------------");
            log.debug(response.getStatusLine());
                if(response.getStatusLine().toString().contains("200")||response.getStatusLine().toString().contains("201")){
                    //tracking successfully sent to shopify
                    success = true;
                }
            if (entity != null) {
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                String xmlstr=EntityUtils.toString(entity)
                println xmlstr
                def xml = new XmlSlurper().parseText(xmlstr)

                //return false //if error
            }

            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
            new LogableException(ex,ex.getMessage(),order.orderNum,order.getClient().clientId+"","Tracking update",LogableException.errorTypes.SHIPMENT_NOTIFICATION);

           throw ex;
        }
        return success;
    }

    public boolean reportArbitraryShipment(String poNum, String tracking, boolean sendCustomerEmail,List<String> itemIds)
    {
        /*
         <?xml version="1.0" encoding="UTF-8"?>
 <fulfillment>
   <tracking-number nil="true"></tracking-number>
   <notify-customer type="boolean">true</notify-customer>
   <line-items type="array">
     <line-item>
       <id type="integer">466157049</id>
     </line-item>
   </line-items>
 </fulfillment>
          */



        DefaultHttpClient client = new DefaultHttpClient()
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost(shopHost).setPath("/admin/orders/"+poNum+"/fulfillments.xml")

            URI uri = builder.build();

            HttpPost httpput = new HttpPost(uri)
            httpput.addHeader("Accept", "text/xml")
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(shopHost, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(apiKey, password));



                def xbuilder = new groovy.xml.StreamingMarkupBuilder()
                xbuilder.encoding = 'UTF-8'
                def createFulfillmentRequest =
                        {
                            mkp.xmlDeclaration()
                            fulfillment() {
                                'tracking-number'(tracking)
                                'notify-customer'(type:'boolean',sendCustomerEmail?'true':'false')
                                'line-items'(type:'array')
                                        {
                                            for (String iid:itemIds) {
                                                'line-item'()
                                                        {
                                                            'id'(type: 'integer', iid)
                                                        }


                                            }
                                        }
                            }
                        }



                String putdata = xbuilder.bind(createFulfillmentRequest).toString()
                println putdata

                StringEntity myEntity = new StringEntity(putdata,
                        ContentType.create("text/xml", "UTF-8"));
                httpput.setEntity(myEntity)
                log.debug("executing request " + httpput.getRequestLine());
                HttpResponse response = client.execute(httpput);
                HttpEntity entity = response.getEntity();

                log.debug("----------------------------------------");
                log.debug(response.getStatusLine());
                if (entity != null) {
                    if (entity != null) {
                        entity = new BufferedHttpEntity(entity);
                    }
                    String xmlstr=EntityUtils.toString(entity)
                    println xmlstr
                    def xml = new XmlSlurper().parseText(xmlstr)

                    //return false //if error
                }



        }catch(Exception ex)
        {

        }
    }


    public void importCurrentOrders() {
           importCurrentOrders(false)
    }

    public boolean isShippedSeparately(String sku)
    {
        return false;
    }


    public static final String kStatusTypePaid = "paid"
    public static final String kStatusTypePartialRefund = "partially_refunded"
    public static final String kStatusTypePartialPaid = "partially_paid"

    public void importCurrentOrders(String statusType, boolean testing) {

        int currPage = 1
        while(importCurrentOrdersForPage(statusType, testing, currPage))
        {
            currPage++
        }


    }
    public boolean checkFulfillmentStatus(String status){

     return  !(status.equals('fulfilled'));

    }

    public boolean importCurrentOrdersForPage(String statusType, boolean testing, int pageNumber) {
        Client oclient = Client.getClientForID(clientId + "");
        ClientPolicy policy = oclient.getClientPolicy();

        DefaultHttpClient client = new DefaultHttpClient()
        String currorderref = "TS:" + Calendar.getInstance().getTimeInMillis()
        boolean needsNextPage = false

        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost(shopHost).setPath("/admin/orders.xml")
                    .setParameter("fulfillment_status", "unshipped,partial")
                    .setParameter("financial_status", statusType)
                    .setParameter("status", "open")
                    .setParameter("limit", "250")
                    .setParameter("order","created_at ASC")
                    .setParameter("page", ""+pageNumber)

            URI uri = builder.build();

            HttpGet httpget = new HttpGet(uri)
            httpget.addHeader("Accept", "text/xml")
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(shopHost, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(apiKey, password));


            log.debug("executing request " + httpget.getRequestLine());
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            log.debug("----------------------------------------");
            log.debug(response.getStatusLine());
            if (entity != null) {
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                String xmlstr = EntityUtils.toString(entity)
              //  println xmlstr
                def xml = new XmlSlurper(true,false).parseText(xmlstr)
                if (!testing) {

                    println "orders found: " + xml.order.collect().size()

                    if((xml.order.collect().size())==250)
                    {
                        needsNextPage = true;
                    }
                    //      println it.'order-number'.text()
                    //  }
                    xml.order.collect().each() {
                        try {
                            println it.name;
                            println it.'order-number'.text();

                            //  println it.email
                            String orderRef = "";
                            if(useOrderName){
                                orderRef = it.'name'.text();
                                orderRef = orderRef.replace("#","");

                            }else{
                                orderRef = getOrderReferencePrefix() + it.'order-number'.text();
                            }


                            if (Integer.decode(it.'order-number'.text()) >= getFirstOrderId() && !(OrderUtilities.orderRefnumExists(orderRef, clientId + "")) && (checkFulfillmentStatus(it.'fulfillment-status'.text()))) {
                                println 'status:' + it.'fulfillment-status'.text()
                                Order order = policy.createInitializedOrder();

                                //check for order tags, then order map. If Map key is in the tags assign group name to order
                                if(it.tags.text().length()>0){

                                for(String tagKey: tagToGroupNameMap.keySet()){
                                    if(it.tags.text().contains(tagKey)){
                                        order.group_name = tagToGroupNameMap.get(tagKey);

                                    }

                                }

                                }

                                order.order_refnum = orderRef
                                currorderref = orderRef
                                order.po_num = it.id.text()
                                order.order_type = getOrderType()
                                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                                if ((it.note?.text() + "").length() > 0) {
                                    order.is_gift = "1"
                                    order.gift_message = it.note.text()
                                }

                                println it.'billing-address'.'first-name'.text() + ' ' + it.'billing-address'.'last-name'.text()

                                order.getBillingContact().name = it.'billing-address'.'first-name'.text() + ' ' + it.'billing-address'.'last-name'.text()
                                order.getBillingContact().email = it.email.text()
                                order.getBillingContact().phone = it.'billing-address'.phone.text()
                                order.getBillingAddress().company_name = it.'billing-address'.company.text()
                                order.getBillingAddress().address_one = it.'billing-address'.address1.text()
                                order.getBillingAddress().address_two = it.'billing-address'.address2.text()
                                order.getBillingAddress().city = it.'billing-address'.city.text()
                                order.getBillingAddress().state = it.'billing-address'.'province-code' ? it.'billing-address'.'province-code'.text() : it.'billing-address'.province.text()
                                order.getBillingAddress().zip = it.'billing-address'.zip.text()
                                order.getBillingAddress().country = it.'billing-address'.'country-code'.text()


                                order.getShippingContact().name = it.'shipping-address'.'first-name'.text() + ' ' + it.'shipping-address'.'last-name'.text()
                                println it.'shipping-address'.'first-name'.text() + ' ' + it.'shipping-address'.'last-name'.text()
                                order.getShippingContact().email = it.email.text()
                                order.getShippingContact().phone = it.'shipping-address'.phone.text()
                                order.getShippingAddress().company_name = it.'shipping-address'.company.text()
                                order.getShippingAddress().address_one = it.'shipping-address'.address1.text()
                                order.getShippingAddress().address_two = it.'shipping-address'.address2.text()
                                order.getShippingAddress().city = it.'shipping-address'.city.text()
                                order.getShippingAddress().state = it.'shipping-address'.'province-code' ? it.'shipping-address'.'province-code'.text() : it.'shipping-address'.province.text()
                                order.getShippingAddress().zip = it.'shipping-address'.zip.text()

                                if(order.getShippingAddress().state.equals("GU")&& it.'shipping-address'.'country-code'.text().equals("US")){
                                    order.getShippingAddress().country = "GUAM"
                                }else if(order.getShippingAddress().state.equals("PR")&& it.'shipping-address'.'country-code'.text().equals("US")){
                                    order.getShippingAddress().country = "PUERTO RICO"
                                }else
                                {
                                    order.getShippingAddress().country = it.'shipping-address'.'country-code'.text()
                                }



                                order.is_paid = 1;
                                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                                order.discount = Float.parseFloat('0' + it.'total-discounts'.text())
                                order.total_tax_cost = Float.parseFloat('0' + it.'total-tax'.text())

                                it.'shipping-lines'?.'shipping-line'?.each()
                                        { shipLine ->
                                            order.total_shipping_cost += Float.parseFloat('0' + shipLine.price.text())
                                        }

                                int realItems = 0;

                                it.'line-items'?.'line-item'?.each()
                                        { item ->

                                            String sku = item.sku.text();

                                            if (sku.equals("WF-V")) {
                                                sku = "WF V"
                                            }

                                            if (sku.equals("THINKINGGREEN")) {
                                                sku = "THINKKINGGREEN"
                                            }
                                            if (sku.equals("WF-NV")) {
                                                sku = "WF NV"
                                            }
                                            if (sku.equals("SWISSBRASS-1")) {
                                                sku = "SWISSBRASS"
                                            }
                                            if (sku.equals("HSCPACKABLEDUFFBLK-1")) {
                                                sku = "HSCPACKABLEDUFFBLK"
                                            }
                                            String title = item.title.text()
                                            String variantTitle = "" + (item.'variant-title'?.text())
                                            if (variantTitle.length() > 0) {
                                                title = title + ' - ' + variantTitle
                                            }
                                            String fulfilledByCode = item.'fulfillment-service'.text()
                                            boolean needsShipping = "true".equals("" + (item.'requires-shipping'?.text()))

                                            if (fulfilledByCode.equalsIgnoreCase(getFulfillmentServiceCode()) || "".equals(getFulfillmentServiceCode())) {
                                                //check SKU
                                                if (!needsShipping) {
                                                    sku = getOtherFulfillmentSku()
                                                    title = title + " / DELIVERED SEPARATELY"
                                                }
                                            } else {
                                                //not fulfilled by OWD
                                                if (!("".equals(getOtherFulfillmentSku()))) {
                                                    //set to add virtual item for other fulfillment
                                                    sku = getOtherFulfillmentSku()
                                                    title = title + " / DELIVERED SEPARATELY"
                                                }
                                            }

                                            if (isShippedSeparately(sku)) {
                                                sku = getOtherFulfillmentSku()
                                                title = title + " / DELIVERED SEPARATELY"
                                            }

                                            println 'adding ' + sku + ' / ' + title

                                            println 'fulfilled by: ' + item.'fulfillment-service'.text()
                                            println 'price: ' + item.price.text()
                                            println 'qty: ' + item.quantity.text()

                                            try {
                                                order.addLineItem(sku, item.quantity.text(), item.price.text(), "0.00", title, "", "");
                                                LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                                litem.client_ref_num = item.id.text()
                                                if((clientId==551 || clientId==607) && !order.getShippingAddress().country.equals("USA")) {
                                                    litem.dec_item_value = Float.parseFloat(item.price.text())
                                                }
                                            } catch (Exception ex) {
                                                if (!ignoreUnrecognizedSKUs) {
                                                    throw ex;
                                                }
                                            }
                                        }


                                String shipMethod = it?.'shipping-lines'?.'shipping-line'?.title?.text()
                                println 'Ship via:' + shipMethod
                                if (order.getTotalPhysicalUnitQuantity() > 0) {
                                    if(shipMethod.toUpperCase().contains("SIGNATURE CONFIRMATION")){
                                        order.setSignatureRequired(true);
                                    }
                                    if(shipMethod.toUpperCase().contains("SATURDAY")){
                                        order.shippingInfo.ss_saturday = "1"

                                    }
                                    if(shipMethod.toLowerCase().contains("duties & taxes")){
                                        order.setForceFedexTaxBillingToConsignee(1);
                                    }
                                    String actualMethod = getActualShipMethod(order, shipMethod)
                                    println 'Shipping via' + actualMethod
                                    order.getShippingInfo().setShipOptions(actualMethod, "Prepaid", "");

                                    doVendorComplianceStuffBeforeSavingOrder(order,XmlUtil.serialize(it));

                                    doFinalStuffBeforeSavingOrder(order);
                                    if(!(OrderUtilities.orderRefnumExists(order.order_refnum, clientId + ""))) {
                                        policy.saveNewOrder(order, false)
                                        //  }
                                        //
                                        for (LineItem line : (Vector<LineItem>) order.skuList) {
                                            addToImportedItemMap(line.client_part_no, line.quantity_request + line.quantity_backordered)
                                        }
                                    }
                                }

                            }else{

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace()
                            try {
                                //throw new LogableException(new Exception(), "No onestop run for 90 minutes - restart jobs webapp!",currorderref, "482", "Shopify API", LogableException.errorTypes.ORDER_IMPORT)

                                throw new LogableException(ex, ex.getMessage(), currorderref, clientId + "", "Shopify API", LogableException.errorTypes.ORDER_IMPORT)
                            } catch (Exception mailex) {
                                mailex.printStackTrace();
                            }
                        }
                    }
                }
                log.debug("Response content length: " + entity.getContentLength());
                println OWDUtilities.parseISToString(entity.getContent())
                int i =0;
               /* xml.order.collect().reverseEach() {
                    try {
                  if(!OrderUtilities.orderRefnumExists(getOrderReferencePrefix() + it.'order-number'.text(), clientId + "")){
                      println("We need to import his order"  + getOrderReferencePrefix() + it.'order-number'.text())
                      i++;
                  }
                    }catch (Exception e){

                    }
                    println(i);
                }*/

            }
            EntityUtils.consume(entity);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            client.getConnectionManager().shutdown();
        }

        return needsNextPage
    }

    public void printListOfLocations(){

        DefaultHttpClient client = new DefaultHttpClient()
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost(shopHost).setPath("/admin/locations.json")


            URI uri = builder.build();

            HttpGet httpget = new HttpGet(uri)
          //  httpget.addHeader("Accept", "text/xml")
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(shopHost, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(apiKey, password));


            log.debug("executing request " + httpget.getRequestLine());
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            log.debug("----------------------------------------");
            log.debug(response.getStatusLine());
            Set<String> shippingMethod = new TreeSet<>()
            if (entity != null) {
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                String xmlstr = EntityUtils.toString(entity)
                println xmlstr
              /*  def xml = new XmlSlurper().parseText(xmlstr)

                //      println it.'order-number'.text()
                //  }
                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx")

                xml.'shipping-zone'.each{ zone->

                    zone.'price-based-shipping-rates'.'price-based-shipping-rate'.each{ rate ->
                        //  println(rate.name.text())
                        shippingMethod.add(rate.name.text())


                    }
                    zone.'weight_based_shipping_rates'.'weight_based_shipping_rate'.each{ rate ->
                        //  println(rate.name.text())
                        shippingMethod.add(rate.name.text())


                    }
                    zone.'carrier-shipping-rate-providers'.'carrier-shipping-rate-provider'.each{carrier->
                        carrier.'service-filter'.children().each{ filter ->
                            // println(filter.name())
                            shippingMethod.add(filter.name())

                        }
                    }


                }
                println("****************")
                for(String s:shippingMethod){
                    println(s)

                }*/
            }

        }catch(Exception e){
            e.printStackTrace()
        }
    }



public void printListOfShipmethods(){

    DefaultHttpClient client = new DefaultHttpClient()
    try {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost(shopHost).setPath("/admin/shipping_zones.xml")


        URI uri = builder.build();

        HttpGet httpget = new HttpGet(uri)
        httpget.addHeader("Accept", "text/xml")
        client.getCredentialsProvider().setCredentials(
                new AuthScope(shopHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(apiKey, password));


        log.debug("executing request " + httpget.getRequestLine());
        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();

        log.debug("----------------------------------------");
        log.debug(response.getStatusLine());
        Set<String> shippingMethod = new TreeSet<>()
        if (entity != null) {
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
            }
            String xmlstr = EntityUtils.toString(entity)
            println xmlstr
            def xml = new XmlSlurper().parseText(xmlstr)

            //      println it.'order-number'.text()
            //  }
            println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx")

            xml.'shipping-zone'.each{ zone->

                zone.'price-based-shipping-rates'.'price-based-shipping-rate'.each{ rate ->
                  //  println(rate.name.text())
                    shippingMethod.add(rate.name.text())


                }
                zone.'weight_based_shipping_rates'.'weight_based_shipping_rate'.each{ rate ->
                    //  println(rate.name.text())
                    shippingMethod.add(rate.name.text())


                }
                zone.'carrier-shipping-rate-providers'.'carrier-shipping-rate-provider'.each{carrier->
                    carrier.'service-filter'.children().each{ filter ->
                       // println(filter.name())
                        shippingMethod.add(filter.name())

                    }
                }


            }
            println("****************")
            for(String s:shippingMethod){
                println(s)

            }
        }

    }catch(Exception e){
    e.printStackTrace()
    }
 }

    public String getTrackingCompany(String shipmethod, OwdOrder order){
        shipmethod = shipmethod.toUpperCase();
        if(order.shipinfo.carrServiceRefNum.contains("FLATRATE")){
            shipmethod = OrderUtilities.getMethodUsedForFlatRate(order.getOrderId()).toUpperCase();
        }

        if(shipmethod.contains("USPS")){
            return "USPS";
        }
        if(shipmethod.contains("OSM")){
            return "OSM";
        }
        if(shipmethod.contains("BPD")){
            return "BPD";
        }
        if(shipmethod.contains("UPS")){
            return "UPS";
        }
        if(shipmethod.contains("FEDEX")){
            return "FedEx";
        }
        if(shipmethod.contains("DHL EXPRESS")){
            return "DHL Express";
        }
        if(shipmethod.contains("DHL SMARTMAIL")){
            return "DHL eCommerce";
        }

        return shipmethod;

    }

}
