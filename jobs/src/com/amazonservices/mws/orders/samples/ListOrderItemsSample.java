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
import com.amazonservices.mws.orders.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders.model.ListOrderItemsResult;
import com.amazonservices.mws.orders.model.Money;
import com.amazonservices.mws.orders.model.OrderItem;
import com.amazonservices.mws.orders.model.OrderItemList;
import com.amazonservices.mws.orders.model.PromotionIdList;
import com.amazonservices.mws.orders.model.ResponseMetadata;

/**
 * 
 * List Order Items Samples
 * 
 * 
 */
public class ListOrderItemsSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service List Order Items
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
         * List Order Items
         ***********************************************************************/
        ListOrderItemsRequest request = new ListOrderItemsRequest();

        // @TODO: set request parameters here
        request.setSellerId(OrdersSampleConfig.sellerId);

        // invokeListOrderItems(service, request);

    }

    /**
     * List Order Items request sample This operation can be used to list the
     * items of the order indicated by the given order id (only a single Amazon
     * order id is allowed).
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param request
     *            Action to invoke
     */
    public static void invokeListOrderItems(
            MarketplaceWebServiceOrders service, ListOrderItemsRequest request) {
        try {

            ListOrderItemsResponse response = service.listOrderItems(request);

            log.debug("ListOrderItems Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            log.debug("    ListOrderItemsResponse");
            log.debug("");
            if (response.isSetListOrderItemsResult()) {
                log.debug("        ListOrderItemsResult");
                log.debug("");
                ListOrderItemsResult listOrderItemsResult = response
                        .getListOrderItemsResult();
                if (listOrderItemsResult.isSetNextToken()) {
                    log.debug("            NextToken");
                    log.debug("");
                    log.debug("                "
                            + listOrderItemsResult.getNextToken());
                    log.debug("");
                }
                if (listOrderItemsResult.isSetAmazonOrderId()) {
                    log.debug("            AmazonOrderId");
                    log.debug("");
                    log.debug("                "
                            + listOrderItemsResult.getAmazonOrderId());
                    log.debug("");
                }
                if (listOrderItemsResult.isSetOrderItems()) {
                    log.debug("            OrderItems");
                    log.debug("");
                    OrderItemList orderItems = listOrderItemsResult
                            .getOrderItems();
                    java.util.List<OrderItem> orderItemList = orderItems
                            .getOrderItem();
                    for (OrderItem orderItem : orderItemList) {
                        log.debug("                OrderItem");
                        log.debug("");
                        if (orderItem.isSetASIN()) {
                            log.debug("                    ASIN");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getASIN());
                            log.debug("");
                        }
                        if (orderItem.isSetSellerSKU()) {
                            log.debug("                    SellerSKU");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getSellerSKU());
                            log.debug("");
                        }
                        if (orderItem.isSetTitle()) {
                            log.debug("                    Title");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getTitle());
                            log.debug("");
                        }
                        if (orderItem.isSetQuantityOrdered()) {
                            System.out
                                    .println("                    QuantityOrdered");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getQuantityOrdered());
                            log.debug("");
                        }
                        if (orderItem.isSetQuantityShipped()) {
                            System.out
                                    .println("                    QuantityShipped");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getQuantityShipped());
                            log.debug("");
                        }
                        if (orderItem.isSetGiftMessageText()) {
                            System.out
                                    .println("                    GiftMessageText");
                            log.debug("");
                            log.debug("                        "
                                    + orderItem.getGiftMessageText());
                            log.debug("");
                        }
                        if (orderItem.isSetItemPrice()) {
                            log.debug("                    ItemPrice");
                            log.debug("");
                            Money itemPrice = orderItem.getItemPrice();
                            if (itemPrice.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + itemPrice.getCurrencyCode());
                                log.debug("");
                            }
                            if (itemPrice.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + itemPrice.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetShippingPrice()) {
                            System.out
                                    .println("                    ShippingPrice");
                            log.debug("");
                            Money shippingPrice = orderItem.getShippingPrice();
                            if (shippingPrice.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingPrice
                                                        .getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingPrice.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingPrice.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetGiftWrapPrice()) {
                            System.out
                                    .println("                    GiftWrapPrice");
                            log.debug("");
                            Money giftWrapPrice = orderItem.getGiftWrapPrice();
                            if (giftWrapPrice.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + giftWrapPrice
                                                        .getCurrencyCode());
                                log.debug("");
                            }
                            if (giftWrapPrice.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + giftWrapPrice.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetItemTax()) {
                            log.debug("                    ItemTax");
                            log.debug("");
                            Money itemTax = orderItem.getItemTax();
                            if (itemTax.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + itemTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (itemTax.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + itemTax.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetShippingTax()) {
                            System.out
                                    .println("                    ShippingTax");
                            log.debug("");
                            Money shippingTax = orderItem.getShippingTax();
                            if (shippingTax.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingTax.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingTax.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetGiftWrapTax()) {
                            System.out
                                    .println("                    GiftWrapTax");
                            log.debug("");
                            Money giftWrapTax = orderItem.getGiftWrapTax();
                            if (giftWrapTax.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + giftWrapTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (giftWrapTax.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + giftWrapTax.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetShippingDiscount()) {
                            System.out
                                    .println("                    ShippingDiscount");
                            log.debug("");
                            Money shippingDiscount = orderItem
                                    .getShippingDiscount();
                            if (shippingDiscount.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingDiscount
                                                        .getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingDiscount.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + shippingDiscount.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetPromotionDiscount()) {
                            System.out
                                    .println("                    PromotionDiscount");
                            log.debug("");
                            Money promotionDiscount = orderItem
                                    .getPromotionDiscount();
                            if (promotionDiscount.isSetCurrencyCode()) {
                                System.out
                                        .println("                        CurrencyCode");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + promotionDiscount
                                                        .getCurrencyCode());
                                log.debug("");
                            }
                            if (promotionDiscount.isSetAmount()) {
                                System.out
                                        .println("                        Amount");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + promotionDiscount.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetPromotionIds()) {
                            System.out
                                    .println("                    PromotionIds");
                            log.debug("");
                            PromotionIdList promotionIds = orderItem
                                    .getPromotionIds();
                            java.util.List<String> promotionIdList = promotionIds
                                    .getPromotionId();
                            for (String promotionId : promotionIdList) {
                                System.out
                                        .println("                        PromotionId");
                                log.debug("");
                                System.out
                                        .println("                            "
                                                + promotionId);
                            }
                        }
                        if (orderItem.isSetCODFee()) {
                            log.debug("                    CODFee");
                            log.debug("");
                            Money  CODFee = orderItem.getCODFee();
                            if (CODFee.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + CODFee.getCurrencyCode());
                                log.debug("");
                            }
                            if (CODFee.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + CODFee.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetCODFeeDiscount()) {
                            log.debug("                    CODFeeDiscount");
                            log.debug("");
                            Money  CODFeeDiscount = orderItem.getCODFeeDiscount();
                            if (CODFeeDiscount.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + CODFeeDiscount.getCurrencyCode());
                                log.debug("");
                            }
                            if (CODFeeDiscount.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + CODFeeDiscount.getAmount());
                                log.debug("");
                            }
                        }
                        if (orderItem.isSetGiftMessageText()) {
                            log.debug("                    GiftMessageText");
                            log.debug("");
                            log.debug("                        " + orderItem.getGiftMessageText());
                            log.debug("");
                        }
                        if (orderItem.isSetGiftWrapLevel()) {
                            log.debug("                    GiftWrapLevel");
                            log.debug("");
                            log.debug("                        " + orderItem.getGiftWrapLevel());
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
