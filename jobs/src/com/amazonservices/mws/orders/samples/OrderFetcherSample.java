package com.amazonservices.mws.orders.samples;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.orders.model.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonservices.mws.orders.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersException;

/**
 * Sample file that fetches orders created during a given time period.
 */

public class OrderFetcherSample {
private final static Logger log =  LogManager.getLogger();



    /*
     * Add required parameters in OrdersSampleConfig.java before trying out this
     * sample.
     */

    /*****************************************
     * Throttling Limits in Milliseconds
     *****************************************/
    static final long LIST_ORDERS_THROTTLE_LIMIT = 600000L; // 1 call/10 mins

    protected MarketplaceWebServiceOrders service;

    public OrderFetcherSample() {
        /*********************************************************************
         * Instantiate Http Client Implementation of Marketplace Web Service *
         * Orders
         *********************************************************************/
        this.service = new MarketplaceWebServiceOrdersClient(
                OrdersSampleConfig.accessKeyId,
                OrdersSampleConfig.secretAccessKey,
                OrdersSampleConfig.applicationName,
                OrdersSampleConfig.applicationVersion,
                OrdersSampleConfig.config);
    }

    /**
     * Fetches all orders created in the given time period and processes them
     * locally. If end is null, it will be ignored (the MWS service will pick an
     * appropriate time, which is now - 2 minutes).
     */
    public void fetchOrders(XMLGregorianCalendar start, XMLGregorianCalendar end) throws MarketplaceWebServiceOrdersException {
        ListOrdersRequest listOrdersRequest = new ListOrdersRequest();
        listOrdersRequest.setSellerId(OrdersSampleConfig.sellerId);
        if (OrdersSampleConfig.marketplaceIdList != null) {
            listOrdersRequest
                    .setMarketplaceId(OrdersSampleConfig.marketplaceIdList);
        }
        listOrdersRequest.setCreatedAfter(start);
        if (start == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        if (end != null) {
            listOrdersRequest.setCreatedBefore(end);
        }

        try {
            ListOrdersResult listOrdersResult = listOrders(listOrdersRequest);

            if (listOrdersResult != null && listOrdersResult.isSetNextToken()) {
                ListOrdersByNextTokenRequest listOrdersByNextTokenRequest = new ListOrdersByNextTokenRequest();
                listOrdersByNextTokenRequest
                        .setSellerId(OrdersSampleConfig.sellerId);
                String nextToken = listOrdersResult.getNextToken();
                ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;
                while (nextToken != null) {
                    listOrdersByNextTokenRequest.setNextToken(nextToken);
                    listOrdersByNextTokenResult = listOrdersByNextToken(listOrdersByNextTokenRequest);
                    nextToken = listOrdersByNextTokenResult.getNextToken();
                }
            }
        } catch (MarketplaceWebServiceOrdersException ex) {
            log.debug("Caught Exception: " + ex.getMessage());
            log.debug("Response Status Code: " + ex.getStatusCode());
            log.debug("Error Code: " + ex.getErrorCode());
            log.debug("Error Type: " + ex.getErrorType());
            log.debug("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
            throw ex;
        }
    }

    private ListOrdersResult listOrders(ListOrdersRequest request)
            throws MarketplaceWebServiceOrdersException {
        boolean retry;
        ListOrdersResponse listOrdersResponse = null;
        ListOrdersResult listOrdersResult = null;
        do {
            retry = false;
            try {
                listOrdersResponse = service.listOrders(request);

            } catch (MarketplaceWebServiceOrdersException ex) {
                if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
                        && "RequestThrottled".equals(ex.getErrorCode())) {
                    retry = true;
                    requestThrottledExceptionHandler(LIST_ORDERS_THROTTLE_LIMIT);
                } else {
                    throw ex;
                }
            }
            if (listOrdersResponse != null
                    && listOrdersResponse.isSetListOrdersResult()) {
                listOrdersResult = listOrdersResponse.getListOrdersResult();
                if (listOrdersResult.isSetOrders()) {
                    processOrders(listOrdersResult.getOrders());
                }
            }

        } while (retry);
        return listOrdersResult;
    }

    private ListOrdersByNextTokenResult listOrdersByNextToken(
            ListOrdersByNextTokenRequest listOrdersByNextTokenRequest)
            throws MarketplaceWebServiceOrdersException {
        boolean retry;
        ListOrdersByNextTokenResponse listOrdersByNextTokenResponse = null;
        ListOrdersByNextTokenResult listOrdersByNextTokenResult = null;
        do {
            retry = false;
            try {

                listOrdersByNextTokenResponse = service
                        .listOrdersByNextToken(listOrdersByNextTokenRequest);
            } catch (MarketplaceWebServiceOrdersException ex) {
                if (ex.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE
                        && "RequestThrottled".equals(ex.getErrorCode())) {
                    retry = true;
                    requestThrottledExceptionHandler(LIST_ORDERS_THROTTLE_LIMIT);
                } else {
                    throw ex;
                }
            }
            if (listOrdersByNextTokenResponse != null
                    && listOrdersByNextTokenResponse
                            .isSetListOrdersByNextTokenResult()) {
                listOrdersByNextTokenResult = listOrdersByNextTokenResponse
                        .getListOrdersByNextTokenResult();
                if (listOrdersByNextTokenResult.isSetOrders()) {
                    processOrders(listOrdersByNextTokenResult.getOrders());
                }
            }

        } while (retry);
        return listOrdersByNextTokenResult;
    }

    private void requestThrottledExceptionHandler(long throttlingLimit) {
        try {
            log.info("Request throttled. Sleeping for " + throttlingLimit
                    + " milliseconds.");
            Thread.sleep(throttlingLimit);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return;
        }
    }

    /*
     * TODO: Insert your order processing logic here.
     */
    protected void processOrders(OrderList orders) {
        log.debug(orders.toString());
        log.debug("Orders found:"+orders.getOrder().size());
        for(Order order:orders.getOrder())
        {
            log.debug("Shipping:"+order.getShipServiceLevel());
            log.debug("Date:"+order.getPurchaseDate());
            log.debug( order.toXMLFragment());
        }
    }

    public static void main(String... args) {

        OrderFetcherSample orderFetcher = new OrderFetcherSample();
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
        }

        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */
        XMLGregorianCalendar start1 = df
                .newXMLGregorianCalendar(new GregorianCalendar());
        Duration negativeOneDay = df.newDurationDayTime(false, 0, 2400, 0, 0);
        start1.add(negativeOneDay);
        try {
             orderFetcher.fetchOrders(start1, null);
        } catch (MarketplaceWebServiceOrdersException e) {
            log.error(e.getMessage(), e);
        }

        /*
         * Fetch orders for the last quarter (Oct 1st, 2010 to Dec 31st, 2010
         * PST)
         */
        XMLGregorianCalendar start2 = df.newXMLGregorianCalendarDate(2010, 10,
                1, -480);
        XMLGregorianCalendar end2 = df.newXMLGregorianCalendarDate(2011, 01,
                01, -480);
//        try {
//            orderFetcher.fetchOrders(start2, end2);
//        } catch (MarketplaceWebServiceOrdersException e) {            
//            e.printStackTrace();
//        }
    }

}
