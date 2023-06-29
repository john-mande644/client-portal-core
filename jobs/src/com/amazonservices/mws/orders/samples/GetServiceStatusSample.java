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
import com.amazonservices.mws.orders.model.GetServiceStatusRequest;
import com.amazonservices.mws.orders.model.GetServiceStatusResponse;
import com.amazonservices.mws.orders.model.GetServiceStatusResult;
import com.amazonservices.mws.orders.model.Message;
import com.amazonservices.mws.orders.model.MessageList;
import com.amazonservices.mws.orders.model.ResponseMetadata;

/**
 * 
 * Get Service Status Samples
 * 
 * 
 */
public class GetServiceStatusSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service Get Service Status
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
         * Instantiate Http Client Implementation of Marketplace Web Service
         * Orders API
         ************************************************************************/
        MarketplaceWebServiceOrders service = new MarketplaceWebServiceOrdersClient(
                OrdersSampleConfig.accessKeyId,
                OrdersSampleConfig.secretAccessKey,
                OrdersSampleConfig.applicationName,
                OrdersSampleConfig.applicationVersion,
                OrdersSampleConfig.config);

        /************************************************************************
         * Setup request parameters and uncomment invoke to try out sample for
         * Get Service Status
         ***********************************************************************/
        GetServiceStatusRequest request = new GetServiceStatusRequest();
        // @TODO: set request parameters here
        request.setSellerId(OrdersSampleConfig.sellerId);

        invokeGetServiceStatus(service, request);

    }
    
    /**
     * Get Service Status request sample Returns the service status of a
     * particular MWS API section. The operation takes no input. All API
     * sections within the API are required to implement this operation.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param request
     *            Action to invoke
     */
    public static void invokeGetServiceStatus(
            MarketplaceWebServiceOrders service, GetServiceStatusRequest request) {
        try {

            GetServiceStatusResponse response = service
                    .getServiceStatus(request);

            log.debug("GetServiceStatus Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            log.debug("    GetServiceStatusResponse");
            log.debug("");
            if (response.isSetGetServiceStatusResult()) {
                log.debug("        GetServiceStatusResult");
                log.debug("");
                GetServiceStatusResult getServiceStatusResult = response
                        .getGetServiceStatusResult();
                if (getServiceStatusResult.isSetStatus()) {
                    log.debug("            Status");
                    log.debug("");
                    log.debug("                "
                            + getServiceStatusResult.getStatus().value());
                    log.debug("");
                }
                if (getServiceStatusResult.isSetTimestamp()) {
                    log.debug("            Timestamp");
                    log.debug("");
                    log.debug("                "
                            + getServiceStatusResult.getTimestamp());
                    log.debug("");
                }
                if (getServiceStatusResult.isSetMessageId()) {
                    log.debug("            MessageId");
                    log.debug("");
                    log.debug("                "
                            + getServiceStatusResult.getMessageId());
                    log.debug("");
                }
                if (getServiceStatusResult.isSetMessages()) {
                    log.debug("            Messages");
                    log.debug("");
                    MessageList messages = getServiceStatusResult.getMessages();
                    java.util.List<Message> messageList = messages.getMessage();
                    for (Message message : messageList) {
                        log.debug("                Message");
                        log.debug("");
                        if (message.isSetLocale()) {
                            log.debug("                    Locale");
                            log.debug("");
                            log.debug("                        "
                                    + message.getLocale());
                            log.debug("");
                        }
                        if (message.isSetText()) {
                            log.debug("                    Text");
                            log.debug("");
                            log.debug("                        "
                                    + message.getText());
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
            ex.printStackTrace();
            log.debug("Caught Exception: " + ex.getMessage());
            log.debug("Response Status Code: " + ex.getStatusCode());
            log.debug("Error Code: " + ex.getErrorCode());
            log.debug("Error Type: " + ex.getErrorType());
            log.debug("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
        }
    }

}
