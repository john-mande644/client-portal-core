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
import com.amazonservices.mws.orders.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders.model.ListOrdersByNextTokenResponse;
import com.amazonservices.mws.orders.model.ListOrdersByNextTokenResult;
import com.amazonservices.mws.orders.model.Money;
import com.amazonservices.mws.orders.model.Order;
import com.amazonservices.mws.orders.model.OrderList;
import com.amazonservices.mws.orders.model.PaymentExecutionDetailItem;
import com.amazonservices.mws.orders.model.PaymentExecutionDetailItemList;
import com.amazonservices.mws.orders.model.ResponseMetadata;

/**
 * 
 * List Orders By Next Token Samples
 * 
 * 
 */
public class ListOrdersByNextTokenSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service List Orders By Next
     * Token functionality
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
         * List Orders By Next Token
         ***********************************************************************/
        ListOrdersByNextTokenRequest request = new ListOrdersByNextTokenRequest();

        // @TODO: set request parameters here
        
        //invokeListOrdersByNextToken(service, request);

    }

    /**
     * List Orders By Next Token request sample If ListOrders returns a
     * nextToken, thus indicating that there are more orders than returned that
     * matched the given filter criteria, ListOrdersByNextToken can be used to
     * retrieve those other orders using that nextToken.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param request
     *            Action to invoke
     */
    public static void invokeListOrdersByNextToken(
            MarketplaceWebServiceOrders service,
            ListOrdersByNextTokenRequest request) {
        try {

            ListOrdersByNextTokenResponse response = service
                    .listOrdersByNextToken(request);

            log.debug("ListOrdersByNextToken Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            log.debug("    ListOrdersByNextTokenResponse");
            log.debug("");
            if (response.isSetListOrdersByNextTokenResult()) {
                log.debug("        ListOrdersByNextTokenResult");
                log.debug("");
                ListOrdersByNextTokenResult  listOrdersByNextTokenResult = response.getListOrdersByNextTokenResult();
                if (listOrdersByNextTokenResult.isSetNextToken()) {
                    log.debug("            NextToken");
                    log.debug("");
                    log.debug("                " + listOrdersByNextTokenResult.getNextToken());
                    log.debug("");
                }
                if (listOrdersByNextTokenResult.isSetCreatedBefore()) {
                    log.debug("            CreatedBefore");
                    log.debug("");
                    log.debug("                " + listOrdersByNextTokenResult.getCreatedBefore());
                    log.debug("");
                }
                if (listOrdersByNextTokenResult.isSetLastUpdatedBefore()) {
                    log.debug("            LastUpdatedBefore");
                    log.debug("");
                    log.debug("                " + listOrdersByNextTokenResult.getLastUpdatedBefore());
                    log.debug("");
                }
                if (listOrdersByNextTokenResult.isSetOrders()) {
                    log.debug("            Orders");
                    log.debug("");
                    OrderList  orders = listOrdersByNextTokenResult.getOrders();
                    java.util.List<Order> orderList = orders.getOrder();
                    for (Order order : orderList) {
                        log.debug("                Order");
                        log.debug("");
                        if (order.isSetAmazonOrderId()) {
                            log.debug("                    AmazonOrderId");
                            log.debug("");
                            log.debug("                        " + order.getAmazonOrderId());
                            log.debug("");
                        }
                        if (order.isSetSellerOrderId()) {
                            log.debug("                    SellerOrderId");
                            log.debug("");
                            log.debug("                        " + order.getSellerOrderId());
                            log.debug("");
                        }
                        if (order.isSetPurchaseDate()) {
                            log.debug("                    PurchaseDate");
                            log.debug("");
                            log.debug("                        " + order.getPurchaseDate());
                            log.debug("");
                        }
                        if (order.isSetLastUpdateDate()) {
                            log.debug("                    LastUpdateDate");
                            log.debug("");
                            log.debug("                        " + order.getLastUpdateDate());
                            log.debug("");
                        }
                        if (order.isSetOrderStatus()) {
                            log.debug("                    OrderStatus");
                            log.debug("");
                            log.debug("                        " + order.getOrderStatus().value());
                            log.debug("");
                        }
                        if (order.isSetFulfillmentChannel()) {
                            log.debug("                    FulfillmentChannel");
                            log.debug("");
                            log.debug("                        " + order.getFulfillmentChannel().value());
                            log.debug("");
                        }
                        if (order.isSetSalesChannel()) {
                            log.debug("                    SalesChannel");
                            log.debug("");
                            log.debug("                        " + order.getSalesChannel());
                            log.debug("");
                        }
                        if (order.isSetOrderChannel()) {
                            log.debug("                    OrderChannel");
                            log.debug("");
                            log.debug("                        " + order.getOrderChannel());
                            log.debug("");
                        }
                        if (order.isSetShipServiceLevel()) {
                            log.debug("                    ShipServiceLevel");
                            log.debug("");
                            log.debug("                        " + order.getShipServiceLevel());
                            log.debug("");
                        }
                        if (order.isSetShippingAddress()) {
                            log.debug("                    ShippingAddress");
                            log.debug("");
                            Address  shippingAddress = order.getShippingAddress();
                            if (shippingAddress.isSetName()) {
                                log.debug("                        Name");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getName());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine1()) {
                                log.debug("                        AddressLine1");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getAddressLine1());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine2()) {
                                log.debug("                        AddressLine2");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getAddressLine2());
                                log.debug("");
                            }
                            if (shippingAddress.isSetAddressLine3()) {
                                log.debug("                        AddressLine3");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getAddressLine3());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCity()) {
                                log.debug("                        City");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getCity());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCounty()) {
                                log.debug("                        County");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getCounty());
                                log.debug("");
                            }
                            if (shippingAddress.isSetDistrict()) {
                                log.debug("                        District");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getDistrict());
                                log.debug("");
                            }
                            if (shippingAddress.isSetStateOrRegion()) {
                                log.debug("                        StateOrRegion");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getStateOrRegion());
                                log.debug("");
                            }
                            if (shippingAddress.isSetPostalCode()) {
                                log.debug("                        PostalCode");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getPostalCode());
                                log.debug("");
                            }
                            if (shippingAddress.isSetCountryCode()) {
                                log.debug("                        CountryCode");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getCountryCode());
                                log.debug("");
                            }
                            if (shippingAddress.isSetPhone()) {
                                log.debug("                        Phone");
                                log.debug("");
                                log.debug("                            " + shippingAddress.getPhone());
                                log.debug("");
                            }
                        } 
                        if (order.isSetOrderTotal()) {
                            log.debug("                    OrderTotal");
                            log.debug("");
                            Money  orderTotal = order.getOrderTotal();
                            if (orderTotal.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + orderTotal.getCurrencyCode());
                                log.debug("");
                            }
                            if (orderTotal.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + orderTotal.getAmount());
                                log.debug("");
                            }
                        } 
                        if (order.isSetNumberOfItemsShipped()) {
                            log.debug("                    NumberOfItemsShipped");
                            log.debug("");
                            log.debug("                        " + order.getNumberOfItemsShipped());
                            log.debug("");
                        }
                        if (order.isSetNumberOfItemsUnshipped()) {
                            log.debug("                    NumberOfItemsUnshipped");
                            log.debug("");
                            log.debug("                        " + order.getNumberOfItemsUnshipped());
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
