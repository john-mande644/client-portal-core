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
 * Get Report List  Samples
 *
 *
 */
public class GetReportListSample {
private final static Logger log =  LogManager.getLogger();

    /**
     * Just add a few required parameters, and try the service
     * Get Report List functionality
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
         * sample for Get Report List 
         ***********************************************************************/

        /************************************************************************
         * Marketplace and Merchant IDs are required parameters for all 
         * Marketplace Web Service calls.
         ***********************************************************************/
        final String merchantId = "<Your Merchant ID>";

        GetReportListRequest request = new GetReportListRequest();
        request.setMerchant( merchantId );

        // @TODO: set request parameters here

        // invokeGetReportList(service, request);

    }



    /**
     * Get Report List  request sample
     * returns a list of reports; by default the most recent ten reports,
     * regardless of their acknowledgement status
     *   
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public static void invokeGetReportList(MarketplaceWebService service, GetReportListRequest request) {
        try {

            GetReportListResponse response = service.getReportList(request);


            System.out.println ("GetReportList Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportListResponse");
            log.debug("");
            if (response.isSetGetReportListResult()) {
                System.out.print("        GetReportListResult");
                log.debug("");
                GetReportListResult  getReportListResult = response.getGetReportListResult();
                if (getReportListResult.isSetNextToken()) {
                    System.out.print("            NextToken");
                    log.debug("");
                    System.out.print("                " + getReportListResult.getNextToken());
                    log.debug("");
                }
                if (getReportListResult.isSetHasNext()) {
                    System.out.print("            HasNext");
                    log.debug("");
                    System.out.print("                " + getReportListResult.isHasNext());
                    log.debug("");
                }
                java.util.List<ReportInfo> reportInfoListList = getReportListResult.getReportInfoList();
                for (ReportInfo reportInfoList : reportInfoListList) {
                    System.out.print("            ReportInfoList");
                    log.debug("");
                    if (reportInfoList.isSetReportId()) {
                        System.out.print("                ReportId");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.getReportId());
                        log.debug("");
                    }
                    if (reportInfoList.isSetReportType()) {
                        System.out.print("                ReportType");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.getReportType());
                        log.debug("");
                    }
                    if (reportInfoList.isSetReportRequestId()) {
                        System.out.print("                ReportRequestId");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.getReportRequestId());
                        log.debug("");
                    }
                    if (reportInfoList.isSetAvailableDate()) {
                        System.out.print("                AvailableDate");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.getAvailableDate());
                        log.debug("");
                    }
                    if (reportInfoList.isSetAcknowledged()) {
                        System.out.print("                Acknowledged");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.isAcknowledged());
                        log.debug("");
                    }
                    if (reportInfoList.isSetAcknowledgedDate()) {
                        System.out.print("                AcknowledgedDate");
                        log.debug("");
                        System.out.print("                    " + reportInfoList.getAcknowledgedDate());
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
