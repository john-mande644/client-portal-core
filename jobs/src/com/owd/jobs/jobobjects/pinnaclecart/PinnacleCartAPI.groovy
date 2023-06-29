package com.owd.jobs.jobobjects.pinnaclecart

import com.owd.core.CountryNames
import com.owd.core.DuplicateOrderException
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.AbstractIntegrationApi
import com.owd.LogableException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 8/6/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
class PinnacleCartAPI extends AbstractIntegrationApi{

    /*

The connect url is
http://www.peacehillpress.net/content/admin/plugins/openapi/index.php

On launch our domain will change from peacehillpress.net to peacehillpress.com

username OneWorldDirect
password OneWorldDirect
security token RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1

https://www.YourSiteName.com/content/admin/plugins/openapi/index.php?
username=<YourApiUserName>&password=<YourApiPassword>&
token=<YourApiToken>&
apiType=<xml|soap|json>&
call=<CallName>&<Arg1>=<Arg1Value>&<Arg2>=<Arg2Value>&<Arg3>=<Arg3Value>...

     */



    protected String apiKey = "";
    protected String password = "";
    protected String token = "";

    RESTClient endpoint = null;
    private String otherFulfillmentSku = "";




    String getOtherFulfillmentSku() {
        return otherFulfillmentSku
    }

    void setOtherFulfillmentSku(String otherFulfillmentSku) {
        this.otherFulfillmentSku = "" + otherFulfillmentSku
    }

    public PinnacleCartAPI(String host, String loginx, String keyx, String baseURLx) {
        apiKey = loginx
        password = keyx
        token = baseURLx


        setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))

        endpoint = new RESTClient(host)




    }

    public void importCurrentOrders() {
        importCurrentOrders(false)
    }

    public static void main(String[] args) {
        //setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))


        PinnacleCartAPI api = new PinnacleCartAPI("https://peacehillpress.com/","OneWorldDirect","OneWorldDirect","RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1")
        api.setClientInfo(390, null);
        api.setOtherFulfillmentSku("DOWNLOAD");
        api.syncSKUsFromCart()
      //  api.showBadSkus()
      //  println "Shipped: "+api.reportOrderShipped(HibernateSession.currentSession().load(OwdOrder.class,6451788))
    }

    public void syncSKUsFromCart()
    {
        Client oclient = Client.getClientForID(clientId + "");
        ClientPolicy policy = oclient.getClientPolicy();

        //   List<Object> pmap = new ArrayList<Object>()


        def resp = endpoint.get(
                contentType: ContentType.XML,
                path: "content/admin/plugins/openapi/index.php",
                query : [username:apiKey,password:password,token:token,apiType:'xml',call:'GetProducts',BatchSize:'100',mode:'search',CatalogSortBy:'title']
                //  https://www.peacehillpress.com/content/admin/plugins/openapi/index.php?apiType=json&call=getOrders&LastOrder=45953&username=OneWorldDirect&password=OneWorldDirect&token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
        )
        //   println resp.data.getText()
        String rawdata = resp.data.getText()
        def jsondata = new JsonSlurper().parseText(rawdata)

        println "SKUs:"+jsondata.size()
        println JsonOutput.prettyPrint(rawdata)
    }

    public void showBadSkus() {

        Client oclient = Client.getClientForID(clientId + "");
        ClientPolicy policy = oclient.getClientPolicy();

        //   List<Object> pmap = new ArrayList<Object>()


        def resp = endpoint.get(
                contentType: ContentType.TEXT,
                path: "content/admin/plugins/openapi/index.php",
                query : [username:apiKey,password:password,token:token,apiType:'json',call:'GetOrdersToShip']
                //  https://www.peacehillpress.com/content/admin/plugins/openapi/index.php?apiType=json&call=getOrders&LastOrder=45953&username=OneWorldDirect&password=OneWorldDirect&token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
        )
        //   println resp.data.getText()
        def jsondata = new JsonSlurper().parseText(resp.data.getText())



            jsondata?.Order?.each() {
                try {

                    //  println it.email
                    if (OrderUtilities.clientOrderReferenceExists(it.OrderNumber, ""+clientId, false))
                    {
                        throw new DuplicateOrderException("");
                    }
                    if (!('digital'.equals(it.ShippingMethodId) || ''.equals(it.ShippingMethodId) || "Yes".equals(""+it.Shipping?.ShippingDigital))) {
                        Order order = policy.createInitializedOrder();
                        order.order_refnum = it.OrderNumber
                        order.po_num = it.OrderId


                        it.OrderItems.each() {
                            item ->

                                String sku = item.SKU;


                                String title = item.Title

                                String type = item.DigitalProduct
                                // boolean needsShipping = "true".equals(""+(item.'requires_shipping'?))

                                if (!('No'.equals(type))) {
                                    //check SKU
                                    sku = getOtherFulfillmentSku()
                                } else if(title.toUpperCase().contains("DOWNLOAD"))
                                {
                                    sku = getOtherFulfillmentSku()

                                }




                                if(!LineItem.SKUExists(clientId+"",sku))
                                {
                                    println order.order_refnum+"/"+order.po_num+":"+sku
                                }




                        }



                    }

                } catch (Exception ex) {
                    ex.printStackTrace()

                    LogableException logex = new LogableException(ex, ex.getMessage(),
                            (String) (it.OrderNumber),
                            clientId+"",
                            this.getClass().getName(),
                            LogableException.errorTypes.ORDER_IMPORT);

                }


        }


        //end while

    }



    public int countCurrentOrders() {


        def resp = endpoint.get(
                contentType: ContentType.TEXT,
                path: "content/admin/plugins/openapi/index.php",
                query : [username:apiKey,password:password,token:token,apiType:'json',call:'GetOrdersToShip']
                //  https://www.peacehillpress.com/content/admin/plugins/openapi/index.php?apiType=json&call=getOrders&LastOrder=45953&username=OneWorldDirect&password=OneWorldDirect&token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
        )
        //   println resp.data.getText()
        def jsondata = new JsonSlurper().parseText(resp.data.getText())


        return jsondata.Order.size()
    }

    public boolean reportOrderShipped(OwdOrder order)
    {
        boolean ok = false;
        /*
        apiType={xml,json}
call=UpdateProductStatus
username=OneWorldDirect
password=OneWorldDirect
token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
ORDERID= the OrderId sent to you (NOT THE OrderNumber)
TRACKINGNUMBER = Carrier shipping tracking number
SHIPPINGMETHOD = {UPS,USPS,FedEx,CanadaPost)
         */
        if(order.getOrderRefnum().startsWith("P") && order.getPoNum().contains("ID: "))
        {
         String carrier = "USPS";
        if (order.getShipinfo().getCarrService().toUpperCase().startsWith("UPS")) {
            carrier = "UPS";
        } else if (order.getShipinfo().getCarrService().toUpperCase().startsWith("FEDEX")) {
            carrier = "FEDEX";
        }

        def resp = endpoint.get(
                contentType: ContentType.TEXT,
                path: "content/admin/plugins/openapi/index.php",
                query : [username:apiKey,password:password,token:token,apiType:'json',call:'UpdateProductStatus',
                        ORDERID:order.getPoNum().replaceAll("ID: ","").trim(),TRACKINGNUMBER:order.getTrackingNums(),SHIPPINGMETHOD:carrier]
                //  https://www.peacehillpress.com/content/admin/plugins/openapi/index.php?apiType=json&call=getOrders&LastOrder=45953&username=OneWorldDirect&password=OneWorldDirect&token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
        )
            String results = resp.data.getText()
            println results
            if(results.equals("\"SUCCESS\""))
            {
                ok = true;
            }
//        def jsondata = new JsonSlurper().parseText(resp.data.getText())


        }

        return ok;

    }
    public void importCurrentOrders(boolean testing) {

        Client oclient = Client.getClientForID(clientId + "");
        ClientPolicy policy = oclient.getClientPolicy();

        //   List<Object> pmap = new ArrayList<Object>()


            def resp = endpoint.get(
                    contentType: ContentType.TEXT,
                    path: "content/admin/plugins/openapi/index.php",
                    query : [username:apiKey,password:password,token:token,apiType:'json',call:'GetOrdersToShip']
                  //  https://www.peacehillpress.com/content/admin/plugins/openapi/index.php?apiType=json&call=getOrders&LastOrder=45953&username=OneWorldDirect&password=OneWorldDirect&token=RGopJPLr3zivgCKeqYbCU3e88B4d4L7yFIBkJfbEERXoDbTrecmSRLf9ElQSq1P1
            )
           //   println resp.data.getText()
            def jsondata = new JsonSlurper().parseText(resp.data.getText())


            jsondata.Order.each() {
              //  println it
               //   println Integer.decode(it.OrderId)
                println (it.OrderNumber)
            }
                if (!testing) {
                    jsondata?.Order?.each() {
                        try {
                            if (OrderUtilities.clientOrderReferenceExists('P'+it.OrderNumber, ""+clientId, false))
                            {
                                throw new DuplicateOrderException("");
                            }
                            //  println it.email
                            if (!('digital'.equals(it.ShippingMethodId) || ''.equals(it.ShippingMethodId) || "Yes".equals(""+it.Shipping?.ShippingDigital))) {
                                println 'Status:' + it.'Status'
                                Order order = policy.createInitializedOrder();
                                order.order_refnum = 'P'+it.OrderNumber
                                println"IMPORTING "+order.order_refnum
                                order.po_num = 'ID: '+it.OrderId
                                order.order_type = "PinnacleCart"
                                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                                println it.'Billing'.'FirstName' + ' ' + it.'Billing'.'LastName'

                                order.getBillingContact().name = it.'Billing'.'FirstName' + ' ' + it.'Billing'.'LastName'
                                order.getBillingContact().email = it.'Billing'.Email
                                order.getBillingContact().phone = it.'Billing'.Phone
                                order.getBillingAddress().company_name = it.'Billing'.Company
                                order.getBillingAddress().address_one = it.'Billing'.Address.Street1
                                order.getBillingAddress().address_two = it.'Billing'.Address.Street2
                                order.getBillingAddress().city = it.'Billing'.Address.City
                                order.getBillingAddress().state = it.'Billing'.Address.State
                                order.getBillingAddress().zip = it.'Billing'.Address.Zip
                                order.getBillingAddress().country = it.'Billing'.Address.Country





                                def shipto = it.Shipping
                                order.getShippingContact().name = shipto.FullName
                                order.getShippingContact().email = ''
                                order.getShippingContact().phone = ''
                                order.getShippingAddress().company_name = shipto.Company
                                order.getShippingAddress().address_one = shipto.Address.Street1
                                order.getShippingAddress().address_two = shipto.Address.Street2
                                order.getShippingAddress().city = shipto.Address.City
                                order.getShippingAddress().state = shipto.Address.State
                                order.getShippingAddress().zip = shipto.Address.Zip
                                order.getShippingAddress().country = shipto.Address.Country

                                order.is_paid = 1;
                                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                                order.discount = Float.parseFloat('0' + it.DiscountAmount)
                                order.total_tax_cost = Float.parseFloat('0' + it.TaxAmount)

                                order.total_shipping_cost += Float.parseFloat('0' + it.ShippingAmount) + Float.parseFloat('0' + it.HandlingAmount)


                                it.OrderItems.each() {
                                    item ->

                                        String sku = item.SKU;
                                        println sku


                                        String title = item.Title

                                        String type = item.DigitalProduct
                                        println type
                                        // boolean needsShipping = "true".equals(""+(item.'requires_shipping'?))

                                        if (!('No'.equals(type))) {
                                            //check SKU
                                            sku = getOtherFulfillmentSku()
                                        } else if(title.toUpperCase().contains("DOWNLOAD"))
                                        {
                                            sku = getOtherFulfillmentSku()

                                        }


                                        println 'adding ' + sku + ' / ' + title
                                        println 'price: ' + item.Price
                                        println 'qty: ' + item.Quantity


                                        if(sku.length()>0) {
                                            if (addLineItem(order, sku, "" + item.Quantity, item.Price, "0.00", (String) title, "", "")) {
                                                LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                                litem.client_ref_num = item.PID
                                            }
                                        }


                                }


                                if (order.getTotalPhysicalUnitQuantity() > 0) {
                                    String actualMethod = getActualShipMethod(order, it.ShippingMethodName)
                                    println 'Shipping via' + actualMethod
                                    order.getShippingInfo().setShipOptions(actualMethod, "Prepaid", "");


                                    doFinalStuffBeforeSavingOrder(order);
                                    println 'client reference: '+order.order_refnum
                                    policy.saveNewOrder(order, false)

                                    //
                                } else
                                {
                                    println "NO PHYSICAL ITEMS"
                                }

                            }

                        } catch (Exception ex) {
                            if(!(ex instanceof DuplicateOrderException))
                            {
                            ex.printStackTrace()

                            LogableException logex = new LogableException(ex, ex.getMessage(),
                                    (String) (it.OrderNumber),
                                        clientId+"",
                                        this.getClass().getName(),
                                        LogableException.errorTypes.ORDER_IMPORT);
                            }
                        }
                    }

                }


        //end while

    }


    String firstOrderId="";

    public String getFirstOrderId()
    {
        return firstOrderId;
    }

    public void setFirstOrderId(String oid)
    {
        firstOrderId=oid;
    }



}


