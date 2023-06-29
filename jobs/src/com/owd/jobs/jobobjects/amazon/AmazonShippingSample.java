package com.owd.jobs.jobobjects.amazon;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentService;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceException;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.*;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.samples.GetEligibleShippingServicesSample;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.samples.MWSMerchantFulfillmentServiceSampleConfig;
import groovy.xml.XmlUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by stewartbuskirk1 on 1/7/16.
 */
public class AmazonShippingSample {

    /**
     * Call the service, log response and exceptions.
     *
     * @param client
     * @param request
     *
     * @return The response.
     */
    public static GetEligibleShippingServicesResponse invokeGetEligibleShippingServices(
            MWSMerchantFulfillmentService client,
            GetEligibleShippingServicesRequest request) {
        try {
            // Call the service.
            GetEligibleShippingServicesResponse response = client.getEligibleShippingServices(request);
            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            // We recommend logging every the request id and timestamp of every call.
            System.out.println("Response:");
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseXml = response.toXML();
            System.out.println(responseXml);
            return response;
        } catch (MWSMerchantFulfillmentServiceException ex) {
            // Exception properties are important for diagnostics.
            System.out.println("Service Exception:");
            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
            if(rhmd != null) {
                System.out.println("RequestId: "+rhmd.getRequestId());
                System.out.println("Timestamp: "+rhmd.getTimestamp());
            }
            System.out.println("Message: "+ex.getMessage());
            System.out.println("StatusCode: "+ex.getStatusCode());
            System.out.println("ErrorCode: "+ex.getErrorCode());
            System.out.println("ErrorType: "+ex.getErrorType());
            throw ex;
        }
    }

    /**
     *  Command line entry point.
     */
    public static void main(String[] args) {

       AmazonConfig config;


            config = new AmazonConfig("AKIAJKASVZKV4YSO4FBQ",
                    "d8xthdJHAG7msAanK85Zdt5kfOAfJd8LVZ5nPYah",
                    "Amazon Downloader",
                    "1.0",
                    "AB6F1F9GXJFZN","https://mws.amazonservices.com/");

            config.addMarketPlaceId("ATVPDKIKX0DER");

        MWSMerchantFulfillmentServiceClient client =config.getClient();


        // Create a request.
        GetEligibleShippingServicesRequest request = new GetEligibleShippingServicesRequest();
        String sellerId = "AB6F1F9GXJFZN";
        request.setSellerId(sellerId);
        String mwsAuthToken = "d8xthdJHAG7msAanK85Zdt5kfOAfJd8LVZ5nPYah";
        request.setMWSAuthToken(mwsAuthToken);
        ShipmentRequestDetails shipmentRequestDetails = new ShipmentRequestDetails();
        shipmentRequestDetails.setAmazonOrderId("111-9960476-9794606");
        Item item = new Item();
        item.setOrderItemId("24628813421026");
        item.setQuantity(1);
        shipmentRequestDetails.getItemList().add(item);
        Address add = new Address();
        add.setAddressLine1("1915 10th Avenue West");
        add.setCity("Mobridge");
        add.setPostalCode("57601");
        add.setStateOrProvinceCode("SD");
        add.setCountryCode("US");
        add.setName("One World Direct");
        add.setEmail("donotreply@owd.com");
        add.setPhone("6058457172");
        shipmentRequestDetails.setShipFromAddress(add);
        PackageDimensions pd = new PackageDimensions();
        pd.setUnit("inches");
        pd.setWidth(BigDecimal.valueOf(4.0));
        pd.setLength(BigDecimal.valueOf(4.0));
        pd.setHeight(BigDecimal.valueOf(2.0));
        shipmentRequestDetails.setPackageDimensions(pd);
        Weight wgt = new Weight();
        wgt.setUnit("oz");
        wgt.setValue(BigDecimal.valueOf(16.0));
        shipmentRequestDetails.setWeight(wgt);
        ShippingServiceOptions sso = new ShippingServiceOptions();
        sso.setCarrierWillPickUp(false);
        sso.setDeliveryExperience("DeliveryConfirmationWithoutSignature");
         shipmentRequestDetails.setShippingServiceOptions(sso);
        request.setShipmentRequestDetails(shipmentRequestDetails);

        // Make the call.
        Map<String,String> offerMap = AmazonShippingServiceUtils.getServiceCostMap(GetEligibleShippingServicesSample.invokeGetEligibleShippingServices(client, request));
        System.out.println(offerMap);
        for(String key:offerMap.keySet()){
            System.out.println(key+":"+offerMap.get(key));
        }
    }


}
