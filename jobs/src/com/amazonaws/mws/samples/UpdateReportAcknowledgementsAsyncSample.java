/******************************************************************************* 
 *  Copyright 2009 Amazon Services.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 *  Generated: Wed Feb 18 13:28:48 PST 2009 
 * 
 */



package com.amazonaws.mws.samples;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.ArrayList;
import com.amazonaws.mws.*;
import com.amazonaws.mws.model.*;
import java.util.concurrent.Future;

/**
 *
 * Update Report Acknowledgements  Samples
 *
 *
 */
public class UpdateReportAcknowledgementsAsyncSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add a few required parameters, and try the service
     * Update Report Acknowledgements functionality
     *
     * @param args unused
     */
    public static void main(String... args) {

        /************************************************************************
         * Access Key ID and Secret Access Key ID, obtained from:
         * http://aws.amazon.com
         ***********************************************************************/
        final String accessKeyId = "<Your Access Key ID>";
        final String secretAccessKey = "<Your Secret Access Key>";
        final String appName = "<Your Application or Company Name>";
        final String appVersion = "<Your Application Version or Build Number or Release Date>";


        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();

        /************************************************************************
         * Uncomment to set the appropriate MWS endpoint.
         ************************************************************************/
        // US
        // config.setServiceURL("https://mws.amazonservices.com");
        // UK
        // config.setServiceURL("https://mws.amazonservices.co.uk");
        // Germany
        // config.setServiceURL("https://mws.amazonservices.de");
        // France
        // config.setServiceURL("https://mws.amazonservices.fr");
        // Italy
        // config.setServiceURL("https://mws.amazonservices.it");
        // Japan
        // config.setServiceURL("https://mws.amazonservices.jp");
        // China
        // config.setServiceURL("https://mws.amazonservices.com.cn");
        // Canada
        // config.setServiceURL("https://mws.amazonservices.ca");
        // India
        // config.setServiceURL("https://mws.amazonservices.in");

        /************************************************************************
         * The argument (35) set below is the number of threads client should
         * spawn for processing.
         ***********************************************************************/

        config.setMaxAsyncThreads(35);

        /************************************************************************
         * You can also try advanced configuration options. Available options are:
         *
         *  - Signature Version
         *  - Proxy Host and Proxy Port
         *  - User Agent String to be sent to Marketplace Web Service
         *
         ***********************************************************************/

        /************************************************************************
         * Instantiate Http Client Implementation of Marketplace Web Service        
         ***********************************************************************/

        MarketplaceWebService service = new MarketplaceWebServiceClient(
                accessKeyId, secretAccessKey, appName, appVersion, config);

        /************************************************************************
         * Setup requests parameters and invoke parallel processing. Of course
         * in real world application, there will be much more than a couple of
         * requests to process.
         ***********************************************************************/

        /************************************************************************
         * Marketplace and Merchant IDs are required parameters for all 
         * Marketplace Web Service calls.
         ***********************************************************************/
        final String merchantId = "<Your Merchant ID>";

        UpdateReportAcknowledgementsRequest requestOne = new UpdateReportAcknowledgementsRequest();
        requestOne.setMerchant( merchantId );

        // @TODO: set request parameters here

        UpdateReportAcknowledgementsRequest requestTwo = new UpdateReportAcknowledgementsRequest();
        requestTwo.setMerchant( merchantId );

        // @TODO: set second request parameters here

        List<UpdateReportAcknowledgementsRequest> requests = new ArrayList<UpdateReportAcknowledgementsRequest>();
        requests.add(requestOne);
        requests.add(requestTwo);

        // invokeUpdateReportAcknowledgements(service, requests);

    }



    /**
     * Update Report Acknowledgements request sample
     * The UpdateReportAcknowledgements operation updates the acknowledged status of one or more reports.
     *   
     * @param service instance of MarketplaceWebService service
     * @param requests list of requests to process
     */
    public static void invokeUpdateReportAcknowledgements(MarketplaceWebService service, List<UpdateReportAcknowledgementsRequest> requests) {
        List<Future<UpdateReportAcknowledgementsResponse>> responses = new ArrayList<Future<UpdateReportAcknowledgementsResponse>>();
        for (UpdateReportAcknowledgementsRequest request : requests) {
            responses.add(service.updateReportAcknowledgementsAsync(request));
        }
        for (Future<UpdateReportAcknowledgementsResponse> future : responses) {
            while (!future.isDone()) {
                Thread.yield();
            }
            try {
                UpdateReportAcknowledgementsResponse response = future.get();
                // Original request corresponding to this response, if needed:
                UpdateReportAcknowledgementsRequest originalRequest = requests.get(responses.indexOf(future));
                log.debug("Response request id: " + response.getResponseMetadata().getRequestId());
                log.debug(response.getResponseHeaderMetadata());
                log.debug("");
            } catch (Exception e) {
                if (e.getCause() instanceof MarketplaceWebServiceException) {
                    MarketplaceWebServiceException exception = MarketplaceWebServiceException.class.cast(e.getCause());
                    log.debug("Caught Exception: " + exception.getMessage());
                    log.debug("Response Status Code: " + exception.getStatusCode());
                    log.debug("Error Code: " + exception.getErrorCode());
                    log.debug("Error Type: " + exception.getErrorType());
                    log.debug("Request ID: " + exception.getRequestId());
                    System.out.print("XML: " + exception.getXML());
                    log.debug("ResponseHeaderMetadata: " + exception.getResponseHeaderMetadata());
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

}
