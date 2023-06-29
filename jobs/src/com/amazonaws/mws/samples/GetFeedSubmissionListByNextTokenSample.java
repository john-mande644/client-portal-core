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
import com.amazonaws.mws.mock.MarketplaceWebServiceMock;

/**
 *
 * Get Feed Submission List By Next Token  Samples
 *
 *
 */
public class GetFeedSubmissionListByNextTokenSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add a few required parameters, and try the service
     * Get Feed Submission List By Next Token functionality
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
         * Uncomment to try out Mock Service that simulates Marketplace Web Service 
         * responses without calling Marketplace Web Service  service.
         *
         * Responses are loaded from local XML files. You can tweak XML files to
         * experiment with various outputs during development
         *
         * XML files available under com/amazonaws/mws/mock tree
         *
         ***********************************************************************/
        // MarketplaceWebService service = new MarketplaceWebServiceMock();

        /************************************************************************
         * Setup request parameters and uncomment invoke to try out 
         * sample for Get Feed Submission List By Next Token 
         ***********************************************************************/

        /************************************************************************
         * Marketplace and Merchant IDs are required parameters for all 
         * Marketplace Web Service calls.
         ***********************************************************************/
        final String merchantId = "<Your Merchant ID>";

        GetFeedSubmissionListByNextTokenRequest request = new GetFeedSubmissionListByNextTokenRequest();
        request.setMerchant( merchantId );

        // @TODO: set request parameters here

        // invokeGetFeedSubmissionListByNextToken(service, request);

    }



    /**
     * Get Feed Submission List By Next Token  request sample
     * retrieve the next batch of list items and if there are more items to retrieve
     *   
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public static void invokeGetFeedSubmissionListByNextToken(MarketplaceWebService service, GetFeedSubmissionListByNextTokenRequest request) {
        try {

            GetFeedSubmissionListByNextTokenResponse response = service.getFeedSubmissionListByNextToken(request);


            System.out.println ("GetFeedSubmissionListByNextToken Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetFeedSubmissionListByNextTokenResponse");
            log.debug("");
            if (response.isSetGetFeedSubmissionListByNextTokenResult()) {
                System.out.print("        GetFeedSubmissionListByNextTokenResult");
                log.debug("");
                GetFeedSubmissionListByNextTokenResult  getFeedSubmissionListByNextTokenResult = response.getGetFeedSubmissionListByNextTokenResult();
                if (getFeedSubmissionListByNextTokenResult.isSetNextToken()) {
                    System.out.print("            NextToken");
                    log.debug("");
                    System.out.print("                " + getFeedSubmissionListByNextTokenResult.getNextToken());
                    log.debug("");
                }
                if (getFeedSubmissionListByNextTokenResult.isSetHasNext()) {
                    System.out.print("            HasNext");
                    log.debug("");
                    System.out.print("                " + getFeedSubmissionListByNextTokenResult.isHasNext());
                    log.debug("");
                }
                java.util.List<FeedSubmissionInfo> feedSubmissionInfoList = getFeedSubmissionListByNextTokenResult.getFeedSubmissionInfoList();
                for (FeedSubmissionInfo feedSubmissionInfo : feedSubmissionInfoList) {
                    System.out.print("            FeedSubmissionInfo");
                    log.debug("");
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        System.out.print("                FeedSubmissionId");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getFeedSubmissionId());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedType()) {
                        System.out.print("                FeedType");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getFeedType());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedProcessingStatus()) {
                        System.out.print("                FeedProcessingStatus");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getFeedProcessingStatus());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetStartedProcessingDate()) {
                        System.out.print("                StartedProcessingDate");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getStartedProcessingDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetCompletedProcessingDate()) {
                        System.out.print("                CompletedProcessingDate");
                        log.debug("");
                        System.out.print("                    " + feedSubmissionInfo.getCompletedProcessingDate());
                        log.debug("");
                    }
                }
            } 
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata  responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                " + responseMetadata.getRequestId());
                    log.debug("");
                }
            } 
            log.debug("");
            log.debug(response.getResponseHeaderMetadata());
            log.debug("");


        } catch (MarketplaceWebServiceException ex) {

            log.debug("Caught Exception: " + ex.getMessage());
            log.debug("Response Status Code: " + ex.getStatusCode());
            log.debug("Error Code: " + ex.getErrorCode());
            log.debug("Error Type: " + ex.getErrorType());
            log.debug("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
            log.debug("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());
        }
    }

}
