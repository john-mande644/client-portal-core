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
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import com.amazonaws.mws.*;
import com.amazonaws.mws.model.*;
import com.amazonaws.mws.mock.MarketplaceWebServiceMock;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;

/**
 *
 * Get Feed Submission Result  Samples
 *
 *
 */
public class GetFeedSubmissionResultSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add a few required parameters, and try the service
     * Get Feed Submission Result functionality
     *
     * @param args unused
     */
    public static void main(String... args) {

        /************************************************************************
         * Access Key ID and Secret Access Key ID, obtained from:
         * http://aws.amazon.com
         ***********************************************************************/

         final String accessKeyId = "AKIAIIFUNYYFO62O6YPQ";
        final String secretAccessKey = "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";

        final String appName = "Amazon Downloader";
        final String appVersion = "1.0";

        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();

        /************************************************************************
         * Uncomment to set the appropriate MWS endpoint.
         ************************************************************************/
        // US
         config.setServiceURL("https://mws.amazonservices.com");
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
         * Setup request parameters and uncomment invoke to try out 
         * sample for Get Feed Submission Result 
         ***********************************************************************/

        /************************************************************************
         * Marketplace and Merchant IDs are required parameters for all 
         * Marketplace Web Service calls.
         ***********************************************************************/
        final String merchantId = "AKR91MEWBO283";

        GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
        request.setMerchant( merchantId );
        request.setMarketplace("ATVPDKIKX0DER");

        request.setFeedSubmissionId( "8480323810" );

        // Note that depending on the size of the feed sent in, and the number of errors and warnings,
        // the result can reach sizes greater than 1GB. For this reason we recommend that you _always_ 
        // program to MWS in a streaming fashion. Otherwise, as your business grows you may silently reach
        // the in-memory size limit and have to re-work your solution.
        //
        try
        {
         OutputStream processingResult = new FileOutputStream( "feedSubmissionResult.xml" );
         request.setFeedSubmissionResultOutputStream( processingResult );

         invokeGetFeedSubmissionResult(service, request);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }



    /**
     * Get Feed Submission Result  request sample
     * retrieves the feed processing report
     *   
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public static void invokeGetFeedSubmissionResult(MarketplaceWebService service, GetFeedSubmissionResultRequest request) {
        try {

            GetFeedSubmissionResultResponse response = service.getFeedSubmissionResult(request);


            System.out.println ("GetFeedSubmissionResult Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetFeedSubmissionResultResponse");
            log.debug("");
            System.out.print("    GetFeedSubmissionResultResult");
            log.debug("");
            System.out.print("            MD5Checksum");
            log.debug("");
            System.out.print("                " + response.getGetFeedSubmissionResultResult().getMD5Checksum());
            log.debug("");
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

            log.debug("Feed Processing Result");
            System.out.println ("=============================================================================");
            log.debug("");
            log.debug( request.getFeedSubmissionResultOutputStream().toString() );
            log.debug(response.getResponseHeaderMetadata());
            log.debug("");
            log.debug("");

        } catch (MarketplaceWebServiceException ex) {
            ex.printStackTrace();
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
