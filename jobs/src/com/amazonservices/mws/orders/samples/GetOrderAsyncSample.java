/******************************************************************************* 
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
 *  Generated: Sun Feb 06 00:05:37 UTC 2011 
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
import com.amazonservices.mws.orders.model.GetOrderRequest;
import com.amazonservices.mws.orders.model.GetOrderResponse;

/**
 * 
 * Get Order Samples
 * 
 * 
 */
public class GetOrderAsyncSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add few required parameters, and try the service Get Order
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
        GetOrderRequest requestOne = new GetOrderRequest();
        // @TODO: set request parameters here
        requestOne.setSellerId(OrdersSampleConfig.sellerId);

        GetOrderRequest requestTwo = new GetOrderRequest();
        // @TODO: set second request parameters here
        requestTwo.setSellerId(OrdersSampleConfig.sellerId);

        List<GetOrderRequest> requests = new ArrayList<GetOrderRequest>();
        requests.add(requestOne);
        requests.add(requestTwo);

        invokeGetOrder(service, requests);

        executor.shutdown();

    }

    /**
     * Get Order request sample This operation takes up to 50 order ids and
     * returns the corresponding orders.
     * 
     * @param service
     *            instance of MarketplaceWebServiceOrders service
     * @param requests
     *            list of requests to process
     */
    public static void invokeGetOrder(MarketplaceWebServiceOrdersAsync service,
            List<GetOrderRequest> requests) {
        List<Future<GetOrderResponse>> responses = new ArrayList<Future<GetOrderResponse>>();
        for (GetOrderRequest request : requests) {
            responses.add(service.getOrderAsync(request));
        }
        for (Future<GetOrderResponse> future : responses) {
            while (!future.isDone()) {
                Thread.yield();
            }
            try {
                GetOrderResponse response = future.get();
                // Original request corresponding to this response, if needed:
                GetOrderRequest originalRequest = requests.get(responses
                        .indexOf(future));
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
