/******************************************************************************* 
 *  Copyright 2008-2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *    __  _    _  ___ 
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 * 
 *  Marketplace Web Service Orders Java Library
 *  API Version: 2011-01-01
 *  Generated: Wed Jan 26 00:20:38 UTC 2011 
 * 
 */

package com.amazonservices.mws.orders.samples;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders.model.Address;
import com.amazonservices.mws.orders.model.ListOrdersRequest;
import com.amazonservices.mws.orders.model.ListOrdersResponse;
import com.amazonservices.mws.orders.model.ListOrdersResult;
import com.amazonservices.mws.orders.model.Money;
import com.amazonservices.mws.orders.model.Order;
import com.amazonservices.mws.orders.model.OrderList;
import com.amazonservices.mws.orders.model.PaymentExecutionDetailItem;
import com.amazonservices.mws.orders.model.PaymentExecutionDetailItemList;
import com.amazonservices.mws.orders.model.ResponseMetadata;

/**
 * 
 * List Orders Samples
 * 
 * 
 */
public class ListOrdersSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service List Orders
     * functionality
     * 
     * @param args
     *            unused
     */
    public static void main(String... args) {

        /*
         * Add required parameters in OrdersSampleConfig.java before trying out
         * this sample.
         */

        /************************************************************************
         * Instantiate Http Client Implementation of Marketplace Web Service
         * Orders
         ***********************************************************************/
        MarketplaceWebServiceOrders service = new MarketplaceWebServiceOrdersClient(
                OrdersSampleConfig.accessKeyId,
                OrdersSampleConfig.secretAccessKey,
                OrdersSampleConfig.applicationName,
                OrdersSampleConfig.applicationVersion,
                OrdersSampleConfig.config);

        /************************************************************************
         * Uncomment to try out Mock Service that simulates Marketplace Web
         * Service Orders responses without calling Marketplace Web Service
         * Orders service.
         * 
         * Responses are loaded from local XML files. You can tweak XML files to
         * experiment with various outputs during development
         * 
         * XML files available under com/amazonservices/mws/mock tree
         * 
         ***********************************************************************/
        // MarketplaceWebServiceOrders service = new
        // MarketplaceWebServiceOrdersMock();

        /************************************************************************
         * Setup request parameters and uncomment invoke to try out sample for
         * List Orders
         ***********************************************************************/
        ListOrdersRequest request = new ListOrdersRequest();

        // @TODO: set request parameters here
        // request.setSellerId(OrdersSampleConfig.sellerId);
        // request.setMarketplaceId(OrdersSampleConfig.marketplaceIdList);

        /*
         * Setting a start date of January 01, 2011 GMT. TODO: Customize
         * according to your requirements.
         */

        //invokeListOrders(service, request);

    }

    /**
     * List Orders request sample ListOrders can be used to find orders that
     * meet the specified criteria.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param request
     *            Action to invoke
     */
    public static void invokeListOrders(MarketplaceWebServiceOrders service,
            ListOrdersRequest request) {
        try {

            ListOrdersResponse response = service.listOrders(request);

            log.debug("ListOrders Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            log.debug("    ListOrdersResponse");
            log.debug("");
            if (response.isSetListOrdersResult()) {
                log.debug("        ListOrdersResult");
                log.debug("");
                ListOrdersResult listOrdersResult = response
                        .getListOrdersResult();
                if (listOrdersResult.isSetNextToken()) {
                    log.debug("            NextToken");
                    log.debug("");
                    log.debug("                "
                            + listOrdersResult.getNextToken());
                    log.debug("");
                }
                if (listOrdersResult.isSetCreatedBefore()) {
                    log.debug("            CreatedBefore");
                    log.debug("");
                    log.debug("                "
                            + listOrdersResult.getCreatedBefore());
                    log.debug("");
                }
                if (listOrdersResult.isSetLastUpdatedBefore()) {
                    log.debug("            LastUpdatedBefore");
                    log.debug("");
                    log.debug("                "
                            + listOrdersResult.getLastUpdatedBefore());
                    log.debug("");
                }
                if (listOrdersResult.isSetOrders()) {
                    log.debug("            Orders");
                    log.debug("");
                    OrderList orders = listOrdersResult.getOrders();
                    java.util.List<Order> orderList = orders.getOrder();
                    for (Order order : orderList) {
                        log.debug("                Order");
                        log.debug("");
                        if (order.isSetAmazonOrderId()) {
                            System.out
                                    .println("                    AmazonOrderId");
                            log.debug("");
                            log.debug("                        "
                                    + order.getAmazonOrderId());
                            log.debug("");
                        }
                        if (order.isSetSellerOrderId()) {
                            System.out
                                    .println("                    SellerOrderId");
                            log.debug("");
                            log.debug("                        "
                                    + order.getSellerOrderId());
                            log.debug("");
                        }
                        if (order.isSetPurchaseDate()) {
                            System.out
                                    .println("                    PurchaseDate");
                            log.debug("");
                            log.debug("                        "
                                    + order.getPurchaseDate());
                            log.debug("");
                        }
                        if (order.isSetLastUpdateDate()) {
                            System.out
                                    .println("                    LastUpdateDate");
                            log.debug("");
                            log.debug("                        "
                                    + order.getLastUpdateDate());
                            log.debug("");
                        }
                        if (order.isSetOrderStatus()) {
                            System.out
                                    .println("                    OrderStatus");
                            log.debug("");
                            log.debug("                        "
                                    + order.getOrderStatus().value());
                            log.debug("");
                        }
                        if (order.isSetFulfillmentChannel()) {
                            System.out
                                    .println("                    FulfillmentChannel");
                            log.debug("");
                            log.debug("                        "
                                    + order.getFulfillmentChannel().value());
                            log.debug("");
                        }
                        if (order.isSetSalesChannel()) {
                            System.out
                                    .println("                    SalesChannel");
                            log.debug("");
                            log.debug("                        "
                                    + order.getSalesChannel());
                            log.debug("");
                        }
                        if (order.isSetOrderChannel()) {
                            System.out
                                    .println("                    OrderChannel");
                            log.debug("");
                            log.debug("                        "
                                    + order.getOrderChannel());
                            log.debug("");
                        }
                        if (order.isSetShipServiceLevel()) {
                            System.out
                                    .println("                    ShipServiceLevel");
                            log.debug("");
                            log.debug("                        "
                                    + order.getShipServiceLevel());
                            log.debug("");
                        }
                        if (order.isSetShippingAddress()) {
                            System.out
                                    .println("                    ShippingAddress");
                            log.debug("");
                            Address shippingAddress = order
                                    .getShippingAddress();
                            if (shippingAddress.isSetName()) {
                                System.out
                                        .println("                        Name");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress.getName());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine1()) {
                                System.out
                                        .println("                        AddressLine1");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getAddressLine1());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine2()) {
                                System.out
                                        .println("                        AddressLine2");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getAddressLine2());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine3()) {
                                System.out
                                        .println("                        AddressLine3");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getAddressLine3());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCity()) {
                                System.out
                                        .println("                        City");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress.getCity());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCounty()) {
                                System.out
                                        .println("                        County");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress.getCounty());
                                log.debug("");
                            }
                            if (shippingAddress.isSetDistrict()) {
                                System.out
                                        .println("                        District");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress.getDistrict());
                                log.debug("");
                            }
                            if (shippingAddress.isSetStateOrRegion()) {
                                System.out
                                        .println("                        StateOrRegion");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getStateOrRegion());
                                log.debug("");
                            }
                            if (shippingAddress.isSetPostalCode()) {
                                System.out
                                        .println("                        PostalCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getPostalCode());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCountryCode()) {
                                System.out
                                        .println("                        CountryCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress
                                                        .getCountryCode());
                                log.debug("");
                            }
                            if (shippingAddress.isSetPhone()) {
                                System.out
                                        .println("                        Phone");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingAddress.getPhone());
                                log.debug("");
                            }
                        }
                        if (order.isSetOrderTotal()) {
                            System.out
                                    .println("                    OrderTotal");
                            log.debug("");
                            Money orderTotal = order.getOrderTotal();
                            if (orderTotal.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + orderTotal.getCurrencyCode());
                                log.debug("");
                            }
                            if (orderTotal.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + orderTotal.getAmount());
                                log.debug("");
                            }
                        }
                        if (order.isSetNumberOfItemsShipped()) {
                            System.out
                                    .println("                    NumberOfItemsShipped");
                            log.debug("");
                            log.debug("                        "
                                    + order.getNumberOfItemsShipped());
                            log.debug("");
                        }
                        if (order.isSetNumberOfItemsUnshipped()) {
                            System.out
                                    .println("                    NumberOfItemsUnshipped");
                            log.debug("");
                            log.debug("                        "
                                    + order.getNumberOfItemsUnshipped());
                            log.debug("");
                        }
                        if (order.isSetPaymentExecutionDetail()) {
                            log.debug("                    PaymentExecutionDetail");
                            log.debug("");
                            PaymentExecutionDetailItemList  paymentExecutionDetail = order.getPaymentExecutionDetail();
                            java.util.List<PaymentExecutionDetailItem> paymentExecutionDetailItemList = paymentExecutionDetail.getPaymentExecutionDetailItem();
                            for (PaymentExecutionDetailItem paymentExecutionDetailItem : paymentExecutionDetailItemList) {
                                log.debug("                        PaymentExecutionDetailItem");
                                log.debug("");
                                if (paymentExecutionDetailItem.isSetPayment()) {
                                    log.debug("                            Payment");
                                    log.debug("");
                                    Money  payment = paymentExecutionDetailItem.getPayment();
                                    if (payment.isSetCurrencyCode()) {
                                        log.debug("                                CurrencyCode");
                                        log.debug("");
                                        log.debug("                                    " + payment.getCurrencyCode());
                                        log.debug("");
                                    }
                                    if (payment.isSetAmount()) {
                                        log.debug("                                Amount");
                                        log.debug("");
                                        log.debug("                                    " + payment.getAmount());
                                        log.debug("");
                                    }
                                } 
                                if (paymentExecutionDetailItem.isSetSubPaymentMethod()) {
                                    log.debug("                            SubPaymentMethod");
                                    log.debug("");
                                    log.debug("                                " + paymentExecutionDetailItem.getSubPaymentMethod());
                                    log.debug("");
                                }
                            }
                        } 
                        if (order.isSetPaymentMethod()) {
                            log.debug("                    PaymentMethod");
                            log.debug("");
                            log.debug("                        " + order.getPaymentMethod().value());
                            log.debug("");
                        }
                        if (order.isSetMarketplaceId()) {
                            log.debug("                    MarketplaceId");
                            log.debug("");
                            log.debug("                        " + order.getMarketplaceId());
                            log.debug("");
                        }
                        if (order.isSetBuyerEmail()) {
                            log.debug("                    BuyerEmail");
                            log.debug("");
                            log.debug("                        " + order.getBuyerEmail());
                            log.debug("");
                        }
                        if (order.isSetBuyerName()) {
                            log.debug("                    BuyerName");
                            log.debug("");
                            log.debug("                        " + order.getBuyerName());
                            log.debug("");
                        }
                        if (order.isSetShipmentServiceLevelCategory()) {
                            log.debug("                    ShipmentServiceLevelCategory");
                            log.debug("");
                            log.debug("                        " + order.getShipmentServiceLevelCategory());
                            log.debug("");
                        }
                    }
                }
            }
            if (response.isSetResponseMetadata()) {
                log.debug("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response
                        .getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    log.debug("            RequestId");
                    log.debug("");
                    log.debug("                "
                            + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug("");

        } catch (MarketplaceWebServiceOrdersException ex) {

            log.debug("Caught Exception: " + ex.getMessage());
            log.debug("Response Status Code: " + ex.getStatusCode());
            log.debug("Error Code: " + ex.getErrorCode());
            log.debug("Error Type: " + ex.getErrorType());
            log.debug("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
        }
    }

}
