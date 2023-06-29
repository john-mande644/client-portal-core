package com.owd.jobs.jobobjects.amazon

import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse

/**
 * Created by stewartbuskirk1 on 1/7/16.
 */
class AmazonShippingServiceUtils {

    static Map getServiceCostMap(GetEligibleShippingServicesResponse amazonOffers){

         Map<String,String> offerMap = new HashMap();
        def offers = new XmlSlurper().parseText(amazonOffers.toXML());
        offers.GetEligibleShippingServicesResult.ShippingServiceList.ShippingService.each { offer ->

            offerMap.put(offer.ShippingServiceName.text(),offer.Rate.Amount.text());
        }
        return offerMap
    }
}
