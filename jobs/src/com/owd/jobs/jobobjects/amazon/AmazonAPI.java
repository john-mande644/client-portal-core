package com.owd.jobs.jobobjects.amazon;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.*;
import com.owd.core.*;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.jobs.jobobjects.AbstractIntegrationApi;
import com.owd.jobs.jobobjects.batchimporters.AmazonOrderData;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Sample file that fetches orders created during a given time period.
 */

public class AmazonAPI  extends AbstractIntegrationApi {
private final static Logger log =  LogManager.getLogger();



    /*
     * Add required parameters in OrdersSampleConfig.java before trying out this
     * sample.
     */

    /*****************************************
     * Throttling Limits in Milliseconds
     *****************************************/
    static final long LIST_ORDERS_THROTTLE_LIMIT = 600000L; // 1 call/10 mins

    public AmazonConfig aconfig;

    protected XMLGregorianCalendar startCal;
    protected XMLGregorianCalendar endCal;
    protected boolean testing = false;
    protected String source = "AMAZON";

    protected String template = "";

    List<String> productList = new ArrayList<String>();

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isTesting() {
        return testing;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public XMLGregorianCalendar getStartCal() {
        return startCal;
    }

    public void setStartCal(XMLGregorianCalendar startCal) {
        this.startCal = startCal;
    }

    public XMLGregorianCalendar getEndCal() {
        return endCal;
    }

    public void setEndCal(XMLGregorianCalendar endCal) {
        this.endCal = endCal;
    }

    public AmazonAPI(AmazonConfig config, int clientId) {
        /*********************************************************************
         * Instantiate Http Client Implementation of Marketplace Web Service *
         * Orders
         *********************************************************************/

        this.clientId = clientId;

        aconfig = config;
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(new GregorianCalendar());

        Duration negativeOneDay = df.newDurationDayTime(false, 0, 288, 0, 0);
        start1.add(negativeOneDay);

        setStartCal(start1);
        setEndCal(null);


    }



    public void markOrdersShipped(List<AmazonOrderShipmentInfo> shipments) throws Exception
    {

        AmazonFeeds.markOrderShipped(aconfig, shipments);

    }

    @Override
    public void importCurrentOrders()  {
        try{


            fetchOrders(startCal, endCal);

        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                Mailer.sendMail("Amazon API order import fail for cid "+clientId, OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "donotreply@owd.com");

            } catch (Exception mailex) {
                mailex.printStackTrace();
            }
        }
    }


    /**
     * Fetches all orders created in the given time period and processes them
     * locally. If end is null, it will be ignored (the MWS service will pick an
     * appropriate time, which is now - 2 minutes).
     */
    public void getProductData() throws Exception {


        GetReportListRequest request = new GetReportListRequest();
        request.setMerchant( aconfig.sellerId );
        request.setMaxCount(100);
        request.setAcknowledged(false);
        TypeList typeList = new TypeList();
        typeList.getType().add("_GET_FLAT_FILE_OPEN_LISTINGS_DATA_");
        request.setReportTypeList(typeList);
        //  request.setReportId( "<Report ID>" );
        invokeGetReportList(aconfig.getMarketplaceWebService(), request);
        queueNewProductDataReport();
    }

    /**
     * Fetches all orders created in the given time period and processes them
     * locally. If end is null, it will be ignored (the MWS service will pick an
     * appropriate time, which is now - 2 minutes).
     */
    private void fetchOrders(XMLGregorianCalendar start, XMLGregorianCalendar end) throws Exception {


        GetReportListRequest request = new GetReportListRequest();
        request.setMerchant( aconfig.sellerId );
        request.setMaxCount(100);
        request.setAcknowledged(false);
        TypeList typeList = new TypeList();
        typeList.getType().add("_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_");
     //   typeList.getType().add("_GET_ORDERS_DATA_");
        request.setReportTypeList(typeList);
        request.setAvailableToDate(end);
        request.setAvailableFromDate(start);
      //  request.setReportId( "<Report ID>" );
        invokeGetReportList(aconfig.getMarketplaceWebService(), request);
        queueNewUnshippedOrdersReport();
    }


    public void listFeedRequests()
    {
        GetFeedSubmissionListRequest request = new GetFeedSubmissionListRequest();
        request.setMerchant( aconfig.sellerId );
        request.setMarketplace("ATVPDKIKX0DER");
        try {

            GetFeedSubmissionListResponse response = aconfig.getMarketplaceWebService().getFeedSubmissionList(request);


            System.out.println ("GetFeedSubmissionList Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetFeedSubmissionListResponse");
            log.debug("");
            if (response.isSetGetFeedSubmissionListResult()) {
                System.out.print("        GetFeedSubmissionListResult");
                log.debug("");
                GetFeedSubmissionListResult  getFeedSubmissionListResult = response.getGetFeedSubmissionListResult();
                if (getFeedSubmissionListResult.isSetNextToken()) {
                    System.out.print("            NextToken");
                    log.debug("");
                    System.out.print("                " + getFeedSubmissionListResult.getNextToken());
                    log.debug("");
                }
                if (getFeedSubmissionListResult.isSetHasNext()) {
                    System.out.print("            HasNext");
                    log.debug("");
                    System.out.print("                " + getFeedSubmissionListResult.isHasNext());
                    log.debug("");
                }
                java.util.List<FeedSubmissionInfo> feedSubmissionInfoList = getFeedSubmissionListResult.getFeedSubmissionInfoList();
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
    public void getFeedSubmissionResult(String submissionId) throws Exception {

        GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
        request.setMerchant( aconfig.sellerId );
        request.setMarketplace("ATVPDKIKX0DER");
        request.setFeedSubmissionId(submissionId);

        try {

            GetFeedSubmissionResultResponse response = aconfig.getMarketplaceWebService().getFeedSubmissionResult(request);


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

            log.debug("Caught Exception: " + ex.getMessage());
            log.debug("Response Status Code: " + ex.getStatusCode());
            log.debug("Error Code: " + ex.getErrorCode());
            log.debug("Error Type: " + ex.getErrorType());
            log.debug("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
            log.debug("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());
        }
    }

    public void queueNewUnshippedOrdersReport() throws Exception {

        RequestReportRequest request = new RequestReportRequest();
        request.setMerchant(aconfig.sellerId);
        request.setMarketplaceIdList(aconfig.marketplaceIdListAsIdList);
        request.setReportType("_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_");
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        XMLGregorianCalendar start1 = df
                .newXMLGregorianCalendar(new GregorianCalendar());

        Duration negativeOneDay = df.newDurationDayTime(false, 0, 288, 0, 0);
        start1.add(negativeOneDay);
        request.setStartDate(start1);
        //  request.setReportId( "<Report ID>" );
        try {

            RequestReportResponse response = aconfig.getMarketplaceWebService().requestReport(request);


            System.out.println ("RequestReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    RequestReportResponse");
            log.debug("");
            if (response.isSetRequestReportResult()) {
                System.out.print("        RequestReportResult");
                log.debug("");
                RequestReportResult  requestReportResult = response.getRequestReportResult();
                if (requestReportResult.isSetReportRequestInfo()) {
                    System.out.print("            ReportRequestInfo");
                    log.debug("");
                    ReportRequestInfo  reportRequestInfo = requestReportResult.getReportRequestInfo();
                    if (reportRequestInfo.isSetReportRequestId()) {
                        System.out.print("                ReportRequestId");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportRequestId());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetReportType()) {
                        System.out.print("                ReportType");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportType());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetStartDate()) {
                        System.out.print("                StartDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getStartDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetEndDate()) {
                        System.out.print("                EndDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getEndDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetReportProcessingStatus()) {
                        System.out.print("                ReportProcessingStatus");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportProcessingStatus());
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

    public void queueNewProductDataReport() throws Exception {

        RequestReportRequest request = new RequestReportRequest();
        request.setMerchant(aconfig.sellerId);
        request.setMarketplaceIdList(aconfig.marketplaceIdListAsIdList);
        request.setReportType("_GET_FLAT_FILE_OPEN_LISTINGS_DATA_");
            DatatypeFactory df = null;
            try {
                df = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                log.error(e.getMessage(), e);
            }

        try {

            RequestReportResponse response = aconfig.getMarketplaceWebService().requestReport(request);


            System.out.println ("RequestReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    RequestReportResponse");
            log.debug("");
            if (response.isSetRequestReportResult()) {
                System.out.print("        RequestReportResult");
                log.debug("");
                RequestReportResult  requestReportResult = response.getRequestReportResult();
                if (requestReportResult.isSetReportRequestInfo()) {
                    System.out.print("            ReportRequestInfo");
                    log.debug("");
                    ReportRequestInfo  reportRequestInfo = requestReportResult.getReportRequestInfo();
                    if (reportRequestInfo.isSetReportRequestId()) {
                        System.out.print("                ReportRequestId");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportRequestId());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetReportType()) {
                        System.out.print("                ReportType");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportType());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetStartDate()) {
                        System.out.print("                StartDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getStartDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetEndDate()) {
                        System.out.print("                EndDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getEndDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getSubmittedDate());
                        log.debug("");
                    }
                    if (reportRequestInfo.isSetReportProcessingStatus()) {
                        System.out.print("                ReportProcessingStatus");
                        log.debug("");
                        System.out.print("                    " + reportRequestInfo.getReportProcessingStatus());
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

    /**
     * Get Report List  request sample
     * returns a list of reports; by default the most recent ten reports,
     * regardless of their acknowledgement status
     *
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public  void invokeGetReportList(MarketplaceWebService service, GetReportListRequest request) throws Exception {
        try {

            request.setAcknowledged(false);
            GetReportListResponse response = service.getReportList(request);


            System.out.println ("GetReportList Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportListResponse");
            log.debug("");
            if (response.isSetGetReportListResult()) {
                System.out.print("        GetReportListResult");
                log.debug("");
                GetReportListResult getReportListResult = response.getGetReportListResult();
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
                List<String> rids = new ArrayList<String>();

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

                    if (reportInfoList.isSetReportType()) {
                        log.debug("checking report type "+reportInfoList.getReportType());
                        if(reportInfoList.getReportType().equals("_GET_ORDERS_DATA_"))
                        {
                            log.debug("Getting ORDERS_DATA");
                            GetReportRequest odRequest = new GetReportRequest();
                            odRequest.setMerchant( aconfig.sellerId );

                            odRequest.setReportId( reportInfoList.getReportId());
                            odRequest.setReportOutputStream(new ByteArrayOutputStream());
                          //  invokeGetReport(service,odRequest);

                        }   else  if(reportInfoList.getReportType().equals("_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_"))
                        {
                            log.debug("Getting ACTIONABLE_ORDER_DATA (Flat)");
                            GetReportRequest odRequest = new GetReportRequest();
                            odRequest.setMerchant( aconfig.sellerId );
                            odRequest.setReportOutputStream(new ByteArrayOutputStream());
                            odRequest.setReportId( reportInfoList.getReportId());

                            invokeGetPendingOrdersReport(service, odRequest);
                            rids.add( reportInfoList.getReportId());

                        }  else  if(reportInfoList.getReportType().equals("_GET_FLAT_FILE_OPEN_LISTINGS_DATA_"))
                        {
                            log.debug("Getting OPEN_LISTINGS_DATA (Flat)");
                            GetReportRequest odRequest = new GetReportRequest();
                            odRequest.setMerchant( aconfig.sellerId );
                            odRequest.setReportOutputStream(new ByteArrayOutputStream());
                            odRequest.setReportId( reportInfoList.getReportId());
                            invokeGetProductListingsReport(service, odRequest);
                            rids.add( reportInfoList.getReportId());

                        }

                        log.debug("");
                    }
                }


                if(rids.size()>0)
                {
                AmazonFeeds.acknowledgeReports(aconfig, rids);
                }
            }
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                com.amazonaws.mws.model.ResponseMetadata responseMetadata = response.getResponseMetadata();
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



    public static void main(String... args) {
        log.debug("113-2895881-4597066");

        log.debug("2015-10-07T06:59:59+00:00");
        LocalDateTime localDeadline = LocalDateTime.ofInstant(OffsetDateTime.parse("2015-10-07T06:59:59+00:00").toInstant(), ZoneId.of("America/Chicago")).minusDays(2);
        log.debug(localDeadline);
        LocalDate ld = localDeadline.toLocalDate().minusDays(2);
        log.debug(ld);
        log.debug(ld.minusDays(1));
        log.debug("LOCAL:"+LocalDate.now(ZoneId.of("America/Chicago")));
        while(!(LocalDate.now(ZoneId.of("America/Chicago")).isAfter(ld)))
        {
            log.debug("checkdate:"+ld);
            ld = ld.minusDays(1);
        }

        Instant deadline = OffsetDateTime.parse("2015-10-07T06:59:59+00:00").toInstant();
        Instant now = OffsetDateTime.now().toInstant();

        now = now.plus(4,ChronoUnit.DAYS);
        log.debug(deadline);
        log.debug(now);
        log.debug("DAYS:"+ChronoUnit.DAYS.between(now,deadline));
        log.debug("HOURS:"+ChronoUnit.HOURS.between(now,deadline));

        log.debug("CalcHours = "+hoursBeforeShippingPromiseDateTime("2015-10-07T06:59:59+00:00"));



    }

    public static long hoursBeforeShippingPromiseDateTime(String amazonPromiseDateTimeGmtString)
    {log.debug("Checking Amazon time before shipping promise for string ["+amazonPromiseDateTimeGmtString+"]");

        Instant deadline = OffsetDateTime.parse(amazonPromiseDateTimeGmtString).toInstant();
        Instant now = OffsetDateTime.now().toInstant();

        log.debug("Calculated hours left = "+ChronoUnit.HOURS.between(now, deadline));
        return ChronoUnit.HOURS.between(now, deadline);

    }

    public  void invokeGetProductListingsReport(MarketplaceWebService service, GetReportRequest request)  throws Exception{
        try {

            GetReportResponse response = service.getReport(request);


            System.out.println ("GetReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportResponse");
            log.debug("");
            System.out.print("    GetReportResult");
            log.debug("");
            System.out.print("            MD5Checksum");
            log.debug("");
            System.out.print("                " + response.getGetReportResult().getMD5Checksum());
            log.debug("");
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                " + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug("");

            log.debug("Report");
            System.out.println ("=============================================================================");
            log.debug("");
            log.debug( request.getReportOutputStream().toString() );
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

        DelimitedReader orders = new TabReader(new BufferedReader(new StringReader(request.getReportOutputStream().toString().replaceAll("\"","") )),true);
        for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

        {
//log.debug("got cols="+orders.getRowSize(orderRow)+" for row "+orderRow);
            productList.add(orders.getStrValue(0,orderRow,"NOTAVALIDSKUBYANYMEANS")) ;

            for (int c=0;c < orders.getRowSize(orderRow);c++)

            {
                log.debug(":"+orders.getStrValue(c,orderRow,"xxx")+":");

            }

        }




    }


    public  void invokeGetPendingOrdersReport(MarketplaceWebService service, GetReportRequest request)  throws Exception{
        try {

            GetReportResponse response = service.getReport(request);


            System.out.println ("GetReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportResponse");
            log.debug("");
            System.out.print("    GetReportResult");
            log.debug("");
            System.out.print("            MD5Checksum");
            log.debug("");
            System.out.print("                " + response.getGetReportResult().getMD5Checksum());
            log.debug("");
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                log.debug("");
                ResponseMetadata responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    log.debug("");
                    System.out.print("                " + responseMetadata.getRequestId());
                    log.debug("");
                }
            }
            log.debug("");

            log.debug("Report");
            System.out.println ("=============================================================================");
            log.debug("");
            log.debug( request.getReportOutputStream().toString() );
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

        DelimitedReader orders = new TabReader(new BufferedReader(new StringReader(request.getReportOutputStream().toString().replaceAll("\"","") )),true);
        for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

        {
//log.debug("got cols="+orders.getRowSize(orderRow)+" for row "+orderRow);

            for (int c=0;c < orders.getRowSize(orderRow);c++)

            {
log.debug(":"+orders.getStrValue(c,orderRow,"xxx")+":");

            }

        }
         /*
         order-id = 0;
order-item-id = 1;
purchase-date = 2;
payments-date = 3;
reporting-date = 4;
promise-date = 5;
days-past-promise = 6;
buyer-email = 7;
buyer-name = 8;
buyer-phone-number = 9;
sku = 10;
product-name = 11;
quantity-purchased = 12;
quantity-shipped = 13;
quantity-to-ship = 14;
ship-service-level = 15;
recipient-name = 16;
ship-address-1 = 17;
ship-address-2 = 18;
ship-address-3 = 19;
ship-city = 20;
ship-state = 21;
ship-postal-code = 22;
ship-country = 23;


        Client client = Client.getClientForID(clientID+"");

        ClientPolicy policy = client.getClientPolicy();

          */

        StringBuffer sbx = new StringBuffer();

        List results = processDataFile(orders);

        // log.debug(sb.toString());

        Iterator it = results.iterator();
        while (it.hasNext()) {
            sbx.append("\r\n" + it.next());
        }
        Vector emailsx = new Vector();
        emailsx.add("owditadmin@owd.com");
        //  emailsx.add("alerts@dogeared.com");
        //  emailsx.add("ordererror@galapagosboutique.com");
        //   //log.debug(sbx.toString());
        if (results.size() > 0 && sbx.toString().indexOf("Communications link failure")<0) {
          //    Mailer.postMailMessage("Amazon Store ("+clientId+") Import Status Notification", "The following orders may need attention:\r\n\r\n" + sbx.toString(), emailsx, "do-not-reply@owd.com");
            //alerts@dogeared.com
        } else
        {
            List<String> oids = new ArrayList<String>();

            for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

            {
//log.debug("got cols="+orders.getRowSize(orderRow)+" for row "+orderRow);

                oids.add(orders.getStrValue(0, orderRow, "")) ;

            }
            AmazonFeeds.acknowledgeOrders(aconfig, oids);
        }
        //  if(OrderUtilities.orderRefnumExists())


    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        AmazonOrderData dataHandler = new AmazonOrderData(this);
        dataHandler.init(data);

        log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, clientId+"", OrderXMLDoc.kBackOrderAll, orderIndex);
                //log.debug(resultL);
                if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
                        resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                //log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                //ex.printStackTrace();
                //log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }


        }

        log.debug(dataHandler.getExistingOrdersMap());
        if(dataHandler.getExistingOrdersMap().size()>0)
        {
            StringBuilder sb = new StringBuilder();

            sb.append("\r\nThe following Amazon orders must be reported shipped TODAY. Review and ensure this!\r\n\r\n");

            for(String oid: dataHandler.getExistingOrdersMap().keySet())
            {

                sb.append(oid+"\tGMT:"+dataHandler.getExistingOrdersMap().get(oid)+"\r\n");
            }

            Mailer.sendMail("[Client ID: "+clientId+"] Amazon Late Ship Orders",sb.toString(),"owditadmin@owd.com");

        }



        return resultsList;
    }


    private List processOrder(AmazonOrderData dataHandler, String clientID, String backorderRule, int orderIndex) throws Exception {
        //returns list of two elements - client Order ID and response
        Client client = Client.getClientForID(clientID);


        ClientPolicy policy = client.getClientPolicy();
        List response = new ArrayList();
        //add new
        log.debug("PROCESSING AMAZON "+dataHandler.getOrderReference(orderIndex));
        response.add(dataHandler.getOrderReference(orderIndex));

        try {
            if(OrderUtilities.orderRefnumExists(dataHandler.getOrderReference(orderIndex),clientID))
            {

                log.debug("Adding order to map for checking for late ships");
                String timeStr = dataHandler.getDataReader().getStrValue(5, ((AmazonOrderData.OrderData) dataHandler.getOrderPositionList().get(orderIndex)).rowIndexStart, "");
                if(AmazonAPI.hoursBeforeShippingPromiseDateTime(timeStr)<12)
                {
                    dataHandler.getExistingOrdersMap().put(dataHandler.getOrderReference(orderIndex),
                            dataHandler.getDataReader().getStrValue(5, ((AmazonOrderData.OrderData) dataHandler.getOrderPositionList().get(orderIndex)).rowIndexStart, ""));

                }
               throw new Exception("reference already exists");
            }
            Order order = policy.createInitializedOrder();
            if(getTemplate().length()>0)
            {
                order.template=getTemplate();
            }
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
           // order.backorder_rule = backorderRule;

            //order.is_future_ship=1;
            dataHandler.loadOrder(order, orderIndex);
            //log.debug("after load order");


            order.getShippingInfo().setShipOptions(getActualShipMethod(order, order.getShippingInfo().carr_service ), "Prepaid", "");
            if(!(order.containsPhysicalItem()))
            {
                throw new Exception("reference already exists");
            }

            order.recalculateBalance();
            log.debug("IS PAID:" + order.is_paid);
            if (order.is_paid == 1) {
                //paid for - push it through!
                order.bill_cc_type = "CL";
                order.check_num = "999999";
                order.paid_amount = order.total_order_cost;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;
            } else {
                //not paid - needs payment
                if (order.cc_num.trim().length() > 1) {
                    order.bill_cc_type = "CC";
                    order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                } else {
                    order.bill_cc_type = "CK";
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                    order.is_future_ship = 1;
                }

                order.is_paid = 0;
                order.check_num = "";
                order.paid_amount = 0.00f;
            }



            // order.order_type="AIS Order Import";

            if (order.is_gift.equals("1") && order.gift_message.trim().length() < 1) {
                order.gift_message = "A gift for you";
            }
            doFinalStuffBeforeSavingOrder(order);

            policy.saveNewOrder(order, false);
            String orderNum = order.orderID;
            log.debug("order id=" + order.orderID);
            if (orderNum == null || "".equals(orderNum) || "0".equals(orderNum)) {

                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                        &&
                        (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1  ) {
                    log.debug("reporting error on import");
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                } else {
                    log.debug("clearing duplicate reference");
                 //   PreparedStatement ps = HibernateDacorStoreSession.getPreparedStatement("update s01_Orders set processed=true where id=?");
                 //   ps.setInt(1,Integer.parseInt(dataHandler.getOrderReference(orderIndex)));
                 //   ps.executeUpdate();
                }
            } else {
                log.debug("got valid order import");
             //   PreparedStatement ps = HibernateDacorStoreSession.getPreparedStatement("update s01_Orders set processed=true where id=?");
             //   ps.setInt(1,Integer.parseInt(dataHandler.getOrderReference(orderIndex)));
             //   ps.executeUpdate();

                if (order.is_future_ship == 1) {
                    response.add("[HELD ORDER] " + order.hold_reason);
                }
                if (order.is_backorder == 1) {
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < order.skuList.size(); i++) {
                        LineItem item = (LineItem) order.skuList.elementAt(i);
                        int available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());
                        if (available < 0) available = 0;

                        if ((item.quantity_request) > available) {
                            sb.append("\r\n    SKU:" + item.client_part_no + " Requested:" + (item.quantity_request) + " Available:" + available);
                        }


                    }
                    response.add("[BACKORDER] " + sb);
                }
            }


        } catch (Exception
                ex) {


            ex.printStackTrace();


            response.add("[NOT IMPORTED] " + ex.getMessage());
        } finally {


        }
        return response;
    }


    public void getReportScheduleList()
    {
        GetReportScheduleListRequest request = new GetReportScheduleListRequest();
        request.setMerchant( aconfig.sellerId );

        // @TODO: set request parameters here

         invokeGetReportScheduleList(aconfig.getMarketplaceWebService(), request);
    }

    public  void invokeGetReportScheduleList(MarketplaceWebService service, GetReportScheduleListRequest request) {
        try {

            GetReportScheduleListResponse response = service.getReportScheduleList(request);


            System.out.println ("GetReportScheduleList Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportScheduleListResponse");
            log.debug("");
            if (response.isSetGetReportScheduleListResult()) {
                System.out.print("        GetReportScheduleListResult");
                log.debug("");
                GetReportScheduleListResult  getReportScheduleListResult = response.getGetReportScheduleListResult();
                if (getReportScheduleListResult.isSetNextToken()) {
                    System.out.print("            NextToken");
                    log.debug("");
                    System.out.print("                " + getReportScheduleListResult.getNextToken());
                    log.debug("");
                }
                if (getReportScheduleListResult.isSetHasNext()) {
                    System.out.print("            HasNext");
                    log.debug("");
                    System.out.print("                " + getReportScheduleListResult.isHasNext());
                    log.debug("");
                }
                java.util.List<ReportSchedule> reportScheduleList = getReportScheduleListResult.getReportScheduleList();
                for (ReportSchedule reportSchedule : reportScheduleList) {
                    System.out.print("            ReportSchedule");
                    log.debug("");
                    if (reportSchedule.isSetReportType()) {
                        System.out.print("                ReportType");
                        log.debug("");
                        System.out.print("                    " + reportSchedule.getReportType());
                        log.debug("");
                    }
                    if (reportSchedule.isSetSchedule()) {
                        System.out.print("                Schedule");
                        log.debug("");
                        System.out.print("                    " + reportSchedule.getSchedule());
                        log.debug("");
                    }
                    if (reportSchedule.isSetScheduledDate()) {
                        System.out.print("                ScheduledDate");
                        log.debug("");
                        System.out.print("                    " + reportSchedule.getScheduledDate());
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

    public static class AmazonOrderShipmentInfo {
        String shipMethod;
        String tracking;
        String amazonOrderRef;
        String shippedDateTime;

        List<OwdLineItem> shippedLines = new ArrayList<OwdLineItem>();

        public String getShippedDateTime() {
            return shippedDateTime;
        }

        public void setShippedDateTime(String shippedDateTime) {
            this.shippedDateTime = shippedDateTime;
        }

        public String getShipMethod() {
            return shipMethod;
        }

        public void setShipMethod(String shipMethod) {
            this.shipMethod = shipMethod;
        }

        public String getTracking() {
            return tracking;
        }

        public void setTracking(String tracking) {
            this.tracking = tracking;
        }

        public String getAmazonOrderRef() {
            return amazonOrderRef;
        }

        public void setAmazonOrderRef(String amazonOrderRef) {
            this.amazonOrderRef = amazonOrderRef;
        }

        public List<OwdLineItem> getShippedLines() {
            return shippedLines;
        }

        public void setShippedLines(List<OwdLineItem> shippedLines) {
            this.shippedLines = shippedLines;
        }

        @Override
        public String toString() {
            return "AmazonOrderShipmentInfo{" +
                    "shipMethod='" + shipMethod + '\'' +
                    ", tracking='" + tracking + '\'' +
                    ", amazonOrderRef='" + amazonOrderRef + '\'' +
                    ", shippedLines=" + shippedLines +
                    '}';
        }
    }
}
