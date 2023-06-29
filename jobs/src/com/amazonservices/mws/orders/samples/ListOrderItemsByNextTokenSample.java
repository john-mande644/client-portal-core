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
import com.amazonservices.mws.orders.model.ListOrderItemsByNextTokenRequest;
import com.amazonservices.mws.orders.model.ListOrderItemsByNextTokenResponse;
import com.amazonservices.mws.orders.model.ListOrderItemsByNextTokenResult;
import com.amazonservices.mws.orders.model.Money;
import com.amazonservices.mws.orders.model.OrderItem;
import com.amazonservices.mws.orders.model.OrderItemList;
import com.amazonservices.mws.orders.model.PromotionIdList;
import com.amazonservices.mws.orders.model.ResponseMetadata;

/**
 * 
 * List Order Items By Next Token Samples
 * 
 * 
 */
public class ListOrderItemsByNextTokenSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service List Order Items By
     * Next Token functionality
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
         * List Order Items By Next Token
         ***********************************************************************/
        ListOrderItemsByNextTokenRequest request = new ListOrderItemsByNextTokenRequest();

        // @TODO: set request parameters here
        request.setSellerId(OrdersSampleConfig.sellerId);
        
        //invokeListOrderItemsByNextToken(service, request);

    }

    /**
     * List Order Items By Next Token request sample If ListOrderItems cannot
     * return all the order items in one go, it will provide a nextToken. That
     * nextToken can be used with this operation to retrive the next batch of
     * items for that order.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param request
     *            Action to invoke
     */
    public static void invokeListOrderItemsByNextToken(
            MarketplaceWebServiceOrders service,
            ListOrderItemsByNextTokenRequest request) {
        try {

            ListOrderItemsByNextTokenResponse response = service
                    .listOrderItemsByNextToken(request);

            log.debug("ListOrderItemsByNextToken Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            log.debug("    ListOrderItemsByNextTokenResponse");
            log.debug("");
            if (response.isSetListOrderItemsByNextTokenResult()) {
                log.debug("        ListOrderItemsByNextTokenResult");
                log.debug("");
                ListOrderItemsByNextTokenResult  listOrderItemsByNextTokenResult = response.getListOrderItemsByNextTokenResult();
                if (listOrderItemsByNextTokenResult.isSetNextToken()) {
                    log.debug("            NextToken");
                    log.debug("");
                    log.debug("                " + listOrderItemsByNextTokenResult.getNextToken());
                    log.debug("");
                }
                if (listOrderItemsByNextTokenResult.isSetAmazonOrderId()) {
                    log.debug("            AmazonOrderId");
                    log.debug("");
                    log.debug("                " + listOrderItemsByNextTokenResult.getAmazonOrderId());
                    log.debug("");
                }
                if (listOrderItemsByNextTokenResult.isSetOrderItems()) {
                    log.debug("            OrderItems");
                    log.debug("");
                    OrderItemList  orderItems = listOrderItemsByNextTokenResult.getOrderItems();
                    java.util.List<OrderItem> orderItemList = orderItems.getOrderItem();
                    for (OrderItem orderItem : orderItemList) {
                        log.debug("                OrderItem");
                        log.debug("");
                        if (orderItem.isSetASIN()) {
                            log.debug("                    ASIN");
                            log.debug("");
                            log.debug("                        " + orderItem.getASIN());
                            log.debug("");
                        }
                        if (orderItem.isSetSellerSKU()) {
                            log.debug("                    SellerSKU");
                            log.debug("");
                            log.debug("                        " + orderItem.getSellerSKU());
                            log.debug("");
                        }
                        if (orderItem.isSetOrderItemId()) {
                            log.debug("                    OrderItemId");
                            log.debug("");
                            log.debug("                        " + orderItem.getOrderItemId());
                            log.debug("");
                        }
                        if (orderItem.isSetTitle()) {
                            log.debug("                    Title");
                            log.debug("");
                            log.debug("                        " + orderItem.getTitle());
                            log.debug("");
                        }
                        if (orderItem.isSetQuantityOrdered()) {
                            log.debug("                    QuantityOrdered");
                            log.debug("");
                            log.debug("                        " + orderItem.getQuantityOrdered());
                            log.debug("");
                        }
                        if (orderItem.isSetQuantityShipped()) {
                            log.debug("                    QuantityShipped");
                            log.debug("");
                            log.debug("                        " + orderItem.getQuantityShipped());
                            log.debug("");
                        }
                        if (orderItem.isSetItemPrice()) {
                            log.debug("                    ItemPrice");
                            log.debug("");
                            Money  itemPrice = orderItem.getItemPrice();
                            if (itemPrice.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + itemPrice.getCurrencyCode());
                                log.debug("");
                            }
                            if (itemPrice.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + itemPrice.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetShippingPrice()) {
                            log.debug("                    ShippingPrice");
                            log.debug("");
                            Money  shippingPrice = orderItem.getShippingPrice();
                            if (shippingPrice.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + shippingPrice.getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingPrice.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + shippingPrice.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetGiftWrapPrice()) {
                            log.debug("                    GiftWrapPrice");
                            log.debug("");
                            Money  giftWrapPrice = orderItem.getGiftWrapPrice();
                            if (giftWrapPrice.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + giftWrapPrice.getCurrencyCode());
                                log.debug("");
                            }
                            if (giftWrapPrice.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + giftWrapPrice.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetItemTax()) {
                            log.debug("                    ItemTax");
                            log.debug("");
                            Money  itemTax = orderItem.getItemTax();
                            if (itemTax.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + itemTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (itemTax.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + itemTax.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetShippingTax()) {
                            log.debug("                    ShippingTax");
                            log.debug("");
                            Money  shippingTax = orderItem.getShippingTax();
                            if (shippingTax.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + shippingTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingTax.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + shippingTax.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetGiftWrapTax()) {
                            log.debug("                    GiftWrapTax");
                            log.debug("");
                            Money  giftWrapTax = orderItem.getGiftWrapTax();
                            if (giftWrapTax.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + giftWrapTax.getCurrencyCode());
                                log.debug("");
                            }
                            if (giftWrapTax.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + giftWrapTax.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetShippingDiscount()) {
                            log.debug("                    ShippingDiscount");
                            log.debug("");
                            Money  shippingDiscount = orderItem.getShippingDiscount();
                            if (shippingDiscount.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + shippingDiscount.getCurrencyCode());
                                log.debug("");
                            }
                            if (shippingDiscount.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + shippingDiscount.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetPromotionDiscount()) {
                            log.debug("                    PromotionDiscount");
                            log.debug("");
                            Money  promotionDiscount = orderItem.getPromotionDiscount();
                            if (promotionDiscount.isSetCurrencyCode()) {
                                log.debug("                        CurrencyCode");
                                log.debug("");
                                log.debug("                            " + promotionDiscount.getCurrencyCode());
                                log.debug("");
                            }
                            if (promotionDiscount.isSetAmount()) {
                                log.debug("                        Amount");
                                log.debug("");
                                log.debug("                            " + promotionDiscount.getAmount());
                                log.debug("");
                            }
                        } 
                        if (orderItem.isSetPromotionIds()) {
                            log.debug("                    PromotionIds");
                            log.debug("");
                            PromotionIdList  promotionIds = orderItem.getPromotionIds();
                            java.util.List<String> promotionIdList  =  promotionIds.getPromotionId();
                            for (String promotionId : promotionIdList) { 
                                log.debug("                        PromotionId");
                                    log.debug("");
                                log.debug("                            " + promotionId);
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
