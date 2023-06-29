package com.owd.jobs.jobobjects.symphony

import com.owd.LogableException
import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities
import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.apache.commons.io.IOUtils
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

/**
 * Created by stewartbuskirk1 on 6/3/15.
 */
class SymphonyAPI {

    final static String testLogin="owd123@owd.com"
    final static String testPass="123"
    final static String prodLogin="owd123@owd.com"
    final static String prodPass="123"

    public static  RESTClient getNewTestEndpoint()
    {
        def ep = new RESTClient("https://manage.symphonycommerce.com")
        ep.auth.basic testLogin, testPass
        ep.client.addRequestInterceptor(
                { HttpRequest httpRequest, HttpContext httpContext ->
                    httpRequest.addHeader('Authorization', 'Basic ' + ((testLogin + ':' + testPass).bytes.encodeBase64().toString()))
                } as HttpRequestInterceptor);
         ep.ignoreSSLIssues();
        ep.defaultRequestHeaders.'Accept' =  "application/json"
        ep.defaultRequestHeaders.'Content-Type' =  "application/json"
        return ep;
    }
    public static  RESTClient getNewProductionEndpoint()
    {
        def ep = new RESTClient("https://manage.symphonycommerce.com")
        ep.ignoreSSLIssues();
        ep.client.addRequestInterceptor(
                { HttpRequest httpRequest, HttpContext httpContext ->
                    httpRequest.addHeader('Authorization', 'Basic ' + ((prodLogin + ':' + prodPass).bytes.encodeBase64().toString()))
                } as HttpRequestInterceptor);

        ep.defaultRequestHeaders.'Accept' =  "application/json"
        ep.defaultRequestHeaders.'Content-Type' =  "application/json"
        return ep;
    }

    public static void main(String[] args) {
        createOrder(null)
    }


    public static void postOrderToSymphony(Order order, int docId, Node po, boolean testing) throws Exception
    {
        String jsondata = createOrder(order,docId)
        println jsondata
        RESTClient ep = null
        if(testing)
        {
            ep = getNewTestEndpoint()
        }   else {
            ep = getNewProductionEndpoint()
        }

        EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)

      //  println jsondata

        ep.handler.success = { resp, reader ->

            println reader.toString()
          //  println IOUtils.toString((Reader)reader)
            println resp.toString()
            if ((resp.getStatus()<300) && (!(reader.toString().startsWith("{error=")))) {
                //ok
            } else {
                if((""+(reader.get("message"))).contains("not have enough inventory") ) {
                    Exception ex = new Exception("Insufficient inventory: "+(reader.get("message")))
                    throw ex;
                }  else if((""+(reader.get("message"))).contains("already exists for Source") ) {
                    Exception ex = new Exception("Duplicate: "+(reader.get("message")))
                    throw ex;
                }  else{
                    throw new LogableException((reader.toString()),
                            jsondata,
                            489 + '',
                            "Symphony API create order fail",
                            LogableException.errorTypes.UNDEFINED_ERROR);
                }
            }
        }
        ep.handler.failure = ep.handler.success

        String path = "/org/"+(testing?"kittyprod":config.getClientAcct())+"/api/v3/orders"
        println "path:"+path
            ep.post(path: path,
                requestContentType: 'application/json',
                contentType: ContentType.JSON,
                headers: ["Accept": 'application/json'] ,
                body: jsondata)


    }
    public static String createOrder(Order order, int docId) {

        def json = new JsonBuilder()


        List<Float> taxList = new ArrayList<Float>();
        List<Float> shipList = new ArrayList<Float>();
        taxList.add((order.total_tax_cost * 100).round())
        shipList.add((order.total_shipping_cost * 100).round())

        json {

            importRef 'OWD.EDI.SPS.850.'+docId
            importSource 'OWD_EDI'

            purchasingChannel(

                    name: 'EDI'

            )

            //  updatedAt '2014-04-04T12:13:34-04:00'
            //  createdAt '2013-09-20T23:19:57-04:00'
            //  authorizationCode 'shopify_order#None'
            //   internalNotes ''
            purchaseOrder order.po_num
            customer(
                    email: order.getBillingContact().email.equals("")?"donotreply@owd.com":order.getBillingContact().email
            )
            customerWebInfo(
                    browserIp: '96.2.236.131',
                    email: order.getBillingContact().email.equals("")?"donotreply@owd.com":order.getBillingContact().email

            )


            orderFinancial(



                    json {
                        taxesApplicable taxList.collect { taxAmt ->
                            [
                                    "display": 'tax',
                                    "value" : taxAmt
                            ]
                        }
                        shippingAndHandling shipList.collect { shipAmt ->
                            [
                                    "display": 'shipping',
                                    "value" : shipAmt
                            ]
                        }

                        creditsApplied 0
                        //   totalProduCAPrice
                        totalTax ((order.total_tax_cost * 100).round())
                        totalDiscount 0
                        totalShippingCost ((order.total_shipping_cost * 100).round())
                        totalHandlingCost 0
                        total((order.total_order_cost * 100).round())
                    }
            )

            shippingAddress(
                    firstName: OWDUtilities.getFirstNameFromWholeName(order.getShippingContact().name),
                    lastName: OWDUtilities.getLastNameFromWholeName(order.getShippingContact().name),
                    street1: order.getShippingAddress().address_one,
                    street2: order.getShippingAddress().address_two,
                    city: order.getShippingAddress().city,
                    state: order.getShippingAddress().state,
                    country: order.getShippingAddress().country,
                    zip: order.getShippingAddress().zip,
                    email: order.getShippingContact().email,
                    phone: order.getShippingContact().phone,
                    company: order.getShippingAddress().company_name
            )
            billingProfileUsed(
                    firstName: OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().name),
                    lastName: OWDUtilities.getLastNameFromWholeName(order.getBillingContact().name),
                    street1: order.getBillingAddress().address_one,
                    street2: order.getBillingAddress().address_two,
                    city: order.getBillingAddress().city,
                    state: order.getBillingAddress().state,
                    country: order.getBillingAddress().country,
                    zip: order.getBillingAddress().zip,
                    email: order.getBillingContact().email,
                    phone: order.getBillingContact().phone,
                    company: order.getBillingAddress().company_name
            )
            //      shippingOption(
            //              name: 'Standard'
            //      )
            shippingMethod 'BIGBOX'
            // originalCart(
            //          json {
            //              gift(
            //                      message: 'this is a gift message'
            //              )
            //          }
            //   )


            lineItems order.skuList.collect { litem ->
                [
                        "memberPrice" : (((LineItem) litem).quantityRequest*(((LineItem) litem).sku_price*100.00)).round(),
                        "quantity": ((LineItem) litem).quantityRequest,
                        "variant" : [
                                globalSku: ((LineItem) litem).client_part_no
                        ]
                ]
            }

            allowSplit false
        }


        return (json.toPrettyString())

    }
}
