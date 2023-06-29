package com.owd.jobs.jobobjects.sears

import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.Client
import com.owd.core.business.client.ClientPolicy
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.AbstractIntegrationApi
import com.owd.jobs.jobobjects.utilities.RateShopper
import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil
import groovyx.net.http.RESTClient
import org.codehaus.groovy.runtime.DefaultGroovyMethods


/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 3/28/14
 * Time: 03:19 PM
 * To change this template use File | Settings | File Templates.
 */
class SearsApi extends AbstractIntegrationApi {


    protected static final String kOrderType = "Sears";

    protected String loginId = "";
    protected String password = "";
    protected String clientId = "0";


    def restClient = new RESTClient("https://seller.marketplace.sears.com")

    /**
     *
     * @param loginId
     * @param password
     * @param dateFrom
     * @param dateTo
     * @param baseURLx
     */
    public SearsApi(String clientId, String loginId, String password) {

//        https://seller.marketplace.sears.com/SellerPortal/api/oms/purchaseorder/v4?email=kathleen@marined3.com&password=Vic321&fromdate=2014-03-01&todate=2014-03-26
        this.loginId = loginId
        this.password = password
        this.clientId = clientId

        setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))

    }


    public void importCurrentOrders() {
        importCurrentOrders(false)
    }

    public static void main(String[] args) {
        /*
        https://seller.marketplace.sears.com/SellerPortal/api/oms/purchaseorder/v4?email=kathleen@marined3.com&password=Vic321&fromdate=2014-03-01&todate=2014-03-26
        */
        //setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))
        SearsApi api = new SearsApi("494", "kathleen@marined3.com",
                "Vic321")

       // api.importCurrentOrders(true)  // true for testing mode (orders not saved)
        println api.reportShipment((OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,7249124))
    }

    public void reportShipment(OwdOrder owdOrder) {

        try {
            def lineItemFound = false

            owdOrder.getLineitems().each {
                if (it.quantityActual > 0 && it.custRefnum?.length()>0 ) {
                    lineItemFound = true
                }
            }
            if (!lineItemFound) {
                //No quantity shipped.
                return;
            }


            def (poNumber, poDate) = owdOrder.getPoNum().tokenize( '|' )
            def carrService = owdOrder.getShipinfo().getCarrService().replaceFirst(' ', '|')
            def (shipCarrier, shipMethod) = carrService.tokenize( '|' )

            String postPath = "/SellerPortal/api/oms/asn/v5"


            def resp = restClient.put(
                path: postPath,
                requestContentType: groovyx.net.http.ContentType.XML,
                body: {
                    mkp.xmlDeclaration()
                    'shipment-feed'(xmlns:'http://seller.marketplace.sears.com/oms/v5','xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance',
                            'xsi:schemaLocation':'http://seller.marketplace.sears.com/oms/v5 asn.xsd' ) {
                        'shipment' {
                            'header' {
                                'asn-number'(owdOrder.getOrderNum())
                                'po-number'(poNumber)
                                'po-date'(poDate)
                            }
                            'detail' {
                                'tracking-number'(owdOrder.getTrackingNums())
                                'ship-date'(owdOrder.getShippedDate().format('yyyy-MM-dd'))
                                'shipping-carrier'('USPS')
                                'shipping-method'('PRIORITY')


                                owdOrder.getLineitems().each {

                                    OwdLineItem oli = (OwdLineItem)it;

                                    if(oli.quantityActual > 0 && oli.custRefnum?.length()>0) {

                                        'package-detail' {
                                            'line-number'(oli.custRefnum)
                                            'item-id'(oli.inventoryNum)
                                            'quantity'(oli.quantityActual)
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                query: [email: loginId, password: password]
            )

            if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}

                          //      println XmlUtil.serialize(resp.data)

              //assert ( resp.data instanceof GPathResult )
            println resp.data.getText()
            //XmlUtil.serialize(resp.data)

        } catch (Exception ex) {
            println "Sears says: " + ex.getMessage()

            if (!("Bad Request".equalsIgnoreCase(ex.getMessage()))) {
                throw new Exception(ex)
            } else {
                throw new Exception(ex)
//                //check that shipment exists
//                def respShips = endpoint.get(
//                        path: "orders/${orderId}/shipments/count.xml"
//                )
//                println respShips
//                int count = 0
//                try {
//                    println XmlUtil.serialize(respShips.data)
//                    count = Integer.decode(respShips.data?.count?.text())
//
//                } catch (Exception exx) {
//                }
//                if (count < 1) {
//                    throw new Exception("Unable to post shipment, no shipments reported, for One World Studios Sears order id " + orderId)
//                }
            }
        }
        //  markOrderShipped(orderId)

    }

    public String testReportShipment(OwdOrder owdOrder) {

        try {
            def lineItemFound = false

            owdOrder.getLineitems().each {
                if (it.quantityActual > 0 || it.quantityRequest > 0) {
                    lineItemFound = true
                }
            }

            if (!lineItemFound) {
                return "No quantity shipped."
            }

            def (poNumber, poDate) = owdOrder.getPoNum().tokenize( '|' )
            def carrService = owdOrder.getShipinfo().getCarrService().replaceFirst(' ', '|')
            def (shipCarrier, shipMethod) = carrService.tokenize( '|' )

            println('PO: ' + poNumber + ' PO Date: ' + poDate + ' Ship Carrier: ' + shipCarrier + ' Ship Method: ' + shipMethod)

//            GString url = "orders/" + owdOrder.getOrderId() + "/shipments.xml"

            def xbuilder = new groovy.xml.StreamingMarkupBuilder()
            xbuilder.encoding = 'UTF-8'

            println('test...1')

            def createFulfillmentRequest = {
                mkp.xmlDeclaration()
                'shipment-feed'('xsi:schemaLocation':'http://seller.marketplace.sears.com/SellerPortal/s/oms/asn-v5.xsd ') {
                    'shipment' {
                        'header' {
                            'asn-number'(owdOrder.getOrderNum())
                            'po-number'(poNumber)
                            'po-date'(poDate)
                        }
                        'detail' {
                            'tracking-number'(owdOrder.getTrackingNums())
                            'ship-date'(owdOrder.getShippedDate().format('yyyy-MM-dd'))
                            'shipping-carrier'(shipCarrier)
                            'shipping-method'(shipMethod)


                            owdOrder.getLineitems().each {

                                OwdLineItem oli = (OwdLineItem)it;

                                if(oli.quantityActual > 0 && oli.custRefnum?.length()>0) {

                                    'package-detail' {
                                        'line-number'(oli.custRefnum)
                                        'item-id'(oli.inventoryNum)
                                        'quantity'(oli.quantityActual)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            String putData = xbuilder.bind(createFulfillmentRequest).toString()

            return putData

        } catch (Exception ex) {
            println "BC says: " + ex.getMessage()

            ex.printStackTrace()

        }
        //  markOrderShipped(orderId)

    }

    public void importCurrentOrders(boolean testing) {

        Client oclient = Client.getClientForID(clientId);
        ClientPolicy policy = oclient.getClientPolicy();

        def resp = getPendingOrders(testing)

        println("Content-Type:" + resp.getContentType())

//        println outputFormattedXml(node);


        assert resp.data instanceof GPathResult

        println("Data size: " + resp.data.size())
        println XmlUtil.serialize(resp.data)

        resp.data.'purchase-order'.each() {
//            insertOrders(it, policy)
            println("Purchase Orders...")
            String originalXml = XmlUtil.serialize(it)
            try {
                Order order = policy.createInitializedOrder();

                order.order_refnum = 'SEARS-' + it.'customer-order-confirmation-number'.text()
                if (!OrderUtilities.orderRefnumExists(order.order_refnum, clientId)) {
                    order.po_num = it.'po-number'.text()+'|'+it.'po-date'.text()
                    order.order_type = kOrderType
                    order.clientID = clientId

                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                    order.is_paid = 1;


                    order.getShippingContact().name = it.'shipping-detail'.'ship-to-name'.text()
                    println(order.getShippingContact().name)

                    order.getShippingContact().email = it.'customer-email'.text()

                    order.getShippingContact().phone = it.'shipping-detail'.phone.text()


                    order.getShippingAddress().address_one = it.'shipping-detail'.address.text()

                    order.getShippingAddress().city = it.'shipping-detail'.city.text()
                    order.getShippingAddress().state = it.'shipping-detail'.state.text()
                    order.getShippingAddress().zip = it.'shipping-detail'.zipcode.text()

                    order.getShippingAddress().country = "USA"

                    order.discount = 0.00f;
                    order.total_tax_cost = Float.parseFloat('0' + it.'sales-tax'.text())

                    order.total_shipping_cost += Float.parseFloat('0' + it.'total-shipping-handling'.text()) + Float.parseFloat('0' + it.'total-shipping-handling'.text())

                    it.'po-line'.each() {
                            //                println("Item: ")
                        item ->
                            println('Line item: ' + item.'po-line-header'.'line-number'.text())


                            String sku = item.'po-line-header'.'item-id'.text()
                            String qty = item.'po-line-detail'.'quantity'.text()
                            String name = item.'po-line-header'.'item-name'.text()
                            String priceEach = item.'po-line-header'.'selling-price-each'.text()
                            println sku + " " + qty + " " + name + " " + priceEach

                            if (addLineItem(order, sku, qty, priceEach, "0.00", name, "", "")) {
                                LineItem litem = (LineItem) order.skuList.get(order.skuList.size() - 1)
                                litem.client_ref_num = item.'po-line-header'.'line-number'.text()
                            }

                    }

                    String shipmethod = translateShipMethod(it.'shipping-method'.text())
                    order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "")

                    order.testing = testing
                    String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);

                    if (reference == null) {
                        throw new Exception("Order Error: " + order.last_error)
                    }
                }
            } catch (Exception ex) {
                try {

                    Mailer.sendMail("Sears API order import fail" + clientId, originalXml + "\r\n\r\n" + OWDUtilities.getStackTraceAsString(ex), "casetracker@owd.com", "donotreply@owd.com");
                } catch (Exception mailex) {
                    mailex.printStackTrace();
                }
            }
        }
    }

    private Object getPendingOrders(boolean testing) {
        def today = new Date()

        if (testing) {
            //get all regardless of status
            return restClient.get(path: "/SellerPortal/api/oms/purchaseorder/v4",
                    query: [email: loginId, password: password, fromdate: (today - 7).format("yyyy-MM-dd"), todate: today.format("yyyy-MM-dd")])

        } else {
            return restClient.get(path: "/SellerPortal/api/oms/purchaseorder/v4",
                    query: [email: loginId, password: password, status: 'New', fromdate: (today - 7).format("yyyy-MM-dd"), todate: today.format("yyyy-MM-dd")])
        }
    }

    public String translateShipMethod(String oldMethod) {
        try {

            domStandardMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY");

            return RateShopper.rateShop(order, currMethods);
        } catch (Exception ex) {
            return "USPS Priority Mail"
        }
    }

}
