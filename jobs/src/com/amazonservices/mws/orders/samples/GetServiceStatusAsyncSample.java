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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersAsync;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders.model.GetServiceStatusRequest;
import com.amazonservices.mws.orders.model.GetServiceStatusResponse;

/**
 * 
 * Get Service Status Samples
 * 
 * 
 */
public class GetServiceStatusAsyncSample {
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
         * Instantiate Http Client Implementation of Marketplace Web Service
         * Orders
         * 
         * Important! Number of threads in executor should match number of
         * connections for http client.
         * 
         ***********************************************************************/
        OrdersSampleConfig.config.setMaxConnections(100);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        MarketplaceWebServiceOrdersAsync service = new MarketplaceWebServiceOrdersAsyncClient(
                OrdersSampleConfig.accessKeyId,
                OrdersSampleConfig.secretAccessKey,
                OrdersSampleConfig.applicationName,
                OrdersSampleConfig.applicationVersion,
                OrdersSampleConfig.config, executor);

        /************************************************************************
         * Setup requests parameters and invoke parallel processing. Of course
         * in real world application, there will be much more than a couple of
         * requests to process.
         ***********************************************************************/
        GetServiceStatusRequest requestOne = new GetServiceStatusRequest();
        // @TODO: set request parameters here
        requestOne.setSellerId(OrdersSampleConfig.sellerId);

        GetServiceStatusRequest requestTwo = new GetServiceStatusRequest();
        // @TODO: set second request parameters here
        requestTwo.setSellerId(OrdersSampleConfig.sellerId);

        List<GetServiceStatusRequest> requests = new ArrayList<GetServiceStatusRequest>();
        requests.add(requestOne);
        requests.add(requestTwo);

        invokeGetServiceStatus(service, requests);

        executor.shutdown();

    }

    /**
     * Get Service Status request sample Returns the service status of a
     * particular MWS API section. The operation takes no input. All API
     * sections within the API are required to implement this operation.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param requests
     *            list of requests to process
     */
    public static void invokeGetServiceStatus(
            MarketplaceWebServiceOrdersAsync service,
            List<GetServiceStatusRequest> requests) {
        List<Future<GetServiceStatusResponse>> responses = new ArrayList<Future<GetServiceStatusResponse>>();
        for (GetServiceStatusRequest request : requests) {
            responses.add(service.getServiceStatusAsync(request));
        }
        for (Future<GetServiceStatusResponse> future : responses) {
            while (!future.isDone()) {
                Thread.yield();
            }
            try {
                GetServiceStatusResponse response = future.get();
                // Original request corresponding to this response, if needed:
                GetServiceStatusRequest originalRequest = requests
                        .get(responses.indexOf(future));
                log.debug("Response request id: "
                        + response.getResponseMetadata().getRequestId());
            } catch (Exception e) {
                if (e.getCause() instanceof MarketplaceWebServiceOrdersException) {
                    MarketplaceWebServiceOrdersException exception = MarketplaceWebServiceOrdersException.class
                            .cast(e.getCause());
                    log.debug("Caught Exception: "
                            + exception.getMessage());
                    log.debug("Response Status Code: "
                            + exception.getStatusCode());
                    log.debug("Error Code: "
                            + exception.getErrorCode());
                    log.debug("Error Type: "
                            + exception.getErrorType());
                    log.debug("Request ID: "
                            + exception.getRequestId());
                    System.out.print("XML: " + exception.getXML());
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

}
