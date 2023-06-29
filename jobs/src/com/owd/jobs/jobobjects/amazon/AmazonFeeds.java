package com.owd.jobs.jobobjects.amazon;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.*;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/18/12
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonFeeds {
private final static Logger log =  LogManager.getLogger();



    public static void main(String[] args)
    {
        log.debug("hello World");
        List<String> oids = new ArrayList<String>();
        oids.add("hello");
        log.debug("");
    }

    public static void updateProductStockLevels(AmazonConfig config, Map<String,Integer> stockLevels) throws Exception
    {
        SubmitFeedRequest request = new SubmitFeedRequest();
        request.setMerchant(config.sellerId);
        request.setMarketplaceIdList(config.marketplaceIdListAsIdList);

        request.setFeedType("_POST_INVENTORY_AVAILABILITY_DATA_");
        //  request.setContentType(ContentType.valueOf("test/xml"));
        String xml = AmazonFeedMaker.getProductAvailabilityFeedXml(config, stockLevels);
        log.debug(xml);
        request.setFeedContent(new StringInputStream(xml)) ;
        request.setContentMD5(computeContentMD5HeaderValue(new StringInputStream(xml)));

        try {

            SubmitFeedResponse response = config.getMarketplaceWebService().submitFeed(request);

            log.debug("SubmitFeed Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            System.out.print("    SubmitFeedResponse");
            log.debug("");
            if (response.isSetSubmitFeedResult()) {
                System.out.print("        SubmitFeedResult");
                log.debug("");
                SubmitFeedResult submitFeedResult = response
                        .getSubmitFeedResult();
                if (submitFeedResult.isSetFeedSubmissionInfo()) {
                    System.out.print("            FeedSubmissionInfo");
                    log.debug("");
                    FeedSubmissionInfo feedSubmissionInfo = submitFeedResult
                            .getFeedSubmissionInfo();
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        System.out.print("                FeedSubmissionId");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedSubmissionId());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedType()) {
                        System.out.print("                FeedType");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedType());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedProcessingStatus()) {
                        System.out
                                .print("                FeedProcessingStatus");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedProcessingStatus());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetStartedProcessingDate()) {
                        System.out
                                .print("                StartedProcessingDate");
                        log.debug("");
                        System.out
                                .print("                    "
                                        + feedSubmissionInfo
                                        .getStartedProcessingDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetCompletedProcessingDate()) {
                        System.out
                                .print("                CompletedProcessingDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo
                                .getCompletedProcessingDate());
                        log.debug("");
                    }
                }
            }
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response
                        .getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                "
                            + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug(response.getResponseHeaderMetadata());
            log.debug("");
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

    public static void markOrderShipped(AmazonConfig config, List<AmazonAPI.AmazonOrderShipmentInfo> shipments) throws Exception
    {
        SubmitFeedRequest request = new SubmitFeedRequest();
        request.setMerchant(config.sellerId);
        request.setMarketplaceIdList(config.marketplaceIdListAsIdList);

        request.setFeedType("_POST_ORDER_FULFILLMENT_DATA_");
        //  request.setContentType(ContentType.valueOf("test/xml"));
        String xml = AmazonFeedMaker.getOrderShippedFeedXml(config, shipments);
        log.debug(xml);
        request.setFeedContent(new StringInputStream(xml)) ;
        request.setContentMD5(computeContentMD5HeaderValue(new StringInputStream(xml)));

        try {

            SubmitFeedResponse response = config.getMarketplaceWebService().submitFeed(request);

            log.debug("SubmitFeed Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            System.out.print("    SubmitFeedResponse");
            log.debug("");
            if (response.isSetSubmitFeedResult()) {
                System.out.print("        SubmitFeedResult");
                log.debug("");
                SubmitFeedResult submitFeedResult = response
                        .getSubmitFeedResult();
                if (submitFeedResult.isSetFeedSubmissionInfo()) {
                    System.out.print("            FeedSubmissionInfo");
                    log.debug("");
                    FeedSubmissionInfo feedSubmissionInfo = submitFeedResult
                            .getFeedSubmissionInfo();
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        System.out.print("                FeedSubmissionId");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedSubmissionId());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedType()) {
                        System.out.print("                FeedType");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedType());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedProcessingStatus()) {
                        System.out
                                .print("                FeedProcessingStatus");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedProcessingStatus());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetStartedProcessingDate()) {
                        System.out
                                .print("                StartedProcessingDate");
                        log.debug("");
                        System.out
                                .print("                    "
                                        + feedSubmissionInfo
                                        .getStartedProcessingDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetCompletedProcessingDate()) {
                        System.out
                                .print("                CompletedProcessingDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo
                                .getCompletedProcessingDate());
                        log.debug("");
                    }
                }
            }
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response
                        .getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                "
                            + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug(response.getResponseHeaderMetadata());
            log.debug("");
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


    public static void acknowledgeOrders(AmazonConfig config, List<String> reportIds) throws Exception
    {
        SubmitFeedRequest request = new SubmitFeedRequest();
        request.setMerchant(config.sellerId);
        request.setMarketplaceIdList(config.marketplaceIdListAsIdList);

        request.setFeedType("_POST_ORDER_ACKNOWLEDGEMENT_DATA_");
        //  request.setContentType(ContentType.valueOf("test/xml"));
        String xml = AmazonFeedMaker.getOrderAckFeedXml(config, reportIds);
        log.debug(xml);
        request.setFeedContent(new StringInputStream(xml)) ;
        request.setContentMD5(computeContentMD5HeaderValue(new StringInputStream(xml)));

        try {

            SubmitFeedResponse response = config.getMarketplaceWebService().submitFeed(request);

            log.debug("SubmitFeed Action Response");
            System.out
                    .println("=============================================================================");
            log.debug("");

            System.out.print("    SubmitFeedResponse");
            log.debug("");
            if (response.isSetSubmitFeedResult()) {
                System.out.print("        SubmitFeedResult");
                log.debug("");
                SubmitFeedResult submitFeedResult = response
                        .getSubmitFeedResult();
                if (submitFeedResult.isSetFeedSubmissionInfo()) {
                    System.out.print("            FeedSubmissionInfo");
                    log.debug("");
                    FeedSubmissionInfo feedSubmissionInfo = submitFeedResult
                            .getFeedSubmissionInfo();
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        System.out.print("                FeedSubmissionId");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedSubmissionId());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedType()) {
                        System.out.print("                FeedType");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedType());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetFeedProcessingStatus()) {
                        System.out
                                .print("                FeedProcessingStatus");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedProcessingStatus());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetStartedProcessingDate()) {
                        System.out
                                .print("                StartedProcessingDate");
                        log.debug("");
                        System.out
                                .print("                    "
                                        + feedSubmissionInfo
                                        .getStartedProcessingDate());
                        log.debug("");
                    }
                    if (feedSubmissionInfo.isSetCompletedProcessingDate()) {
                        System.out
                                .print("                CompletedProcessingDate");
                        log.debug("");
                        System.out.print("                    "
                                + feedSubmissionInfo
                                .getCompletedProcessingDate());
                        log.debug("");
                    }
                }
            }
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response
                        .getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                "
                            + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug(response.getResponseHeaderMetadata());
            log.debug("");
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

    public static void acknowledgeReports(AmazonConfig config, List<String> reportIds) throws Exception
    {

        UpdateReportAcknowledgementsRequest request = new UpdateReportAcknowledgementsRequest();
        request.setMerchant( config.sellerId);

        IdList ackList = new IdList();

        ackList.setId(reportIds)  ;

        request.setReportIdList(ackList) ;
        // invokeUpdateReportAcknowledgements(service, request);
        UpdateReportAcknowledgementsResponse response = config.getMarketplaceWebService().updateReportAcknowledgements(request);

        System.out.println ("UpdateReportAcknowledgements Action Response");
        System.out.println ("=============================================================================");
        System.out.println ();

        System.out.print("    UpdateReportAcknowledgementsResponse");
        log.debug("");
        if (response.isSetUpdateReportAcknowledgementsResult()) {
            System.out.print("        UpdateReportAcknowledgementsResult");
            log.debug("");
            UpdateReportAcknowledgementsResult  updateReportAcknowledgementsResult = response.getUpdateReportAcknowledgementsResult();
            if (updateReportAcknowledgementsResult.isSetCount()) {
                System.out.print("            Count");
                log.debug("");
                System.out.print("                " + updateReportAcknowledgementsResult.getCount());
                log.debug("");
            }
            java.util.List<ReportInfo> reportInfoList = updateReportAcknowledgementsResult.getReportInfoList();
            for (ReportInfo reportInfo : reportInfoList) {
                System.out.print("            ReportInfo");
                log.debug("");
                if (reportInfo.isSetReportId()) {
                    System.out.print("                ReportId");
                    log.debug("");
                    System.out.print("                    " + reportInfo.getReportId());
                    log.debug("");
                }
                if (reportInfo.isSetReportType()) {
                    System.out.print("                ReportType");
                    log.debug("");
                    System.out.print("                    " + reportInfo.getReportType());
                    log.debug("");
                }
                if (reportInfo.isSetReportRequestId()) {
                    System.out.print("                ReportRequestId");
                    log.debug("");
                    System.out.print("                    " + reportInfo.getReportRequestId());
                    log.debug("");
                }
                if (reportInfo.isSetAvailableDate()) {
                    System.out.print("                AvailableDate");
                    log.debug("");
                    System.out.print("                    " + reportInfo.getAvailableDate());
                    log.debug("");
                }
                if (reportInfo.isSetAcknowledged()) {
                    System.out.print("                Acknowledged");
                    log.debug("");
                    System.out.print("                    " + reportInfo.isAcknowledged());
                    log.debug("");
                }
                if (reportInfo.isSetAcknowledgedDate()) {
                    System.out.print("                AcknowledgedDate");
                    log.debug("");
                    System.out.print("                    " + reportInfo.getAcknowledgedDate());
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
    }

    public static String computeContentMD5HeaderValue( InputStream fis )
            throws IOException, NoSuchAlgorithmException {
        DigestInputStream dis = new DigestInputStream( fis,
                MessageDigest.getInstance("MD5"));
        byte[] buffer = new byte[8192];
        while( dis.read( buffer ) > 0 );
        String md5Content = new String( org.apache.commons.codec.binary.Base64.encodeBase64(dis.getMessageDigest().digest())
        );
// Effectively resets the stream to be beginning of the file via a
        return md5Content;
    }

}

