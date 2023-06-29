package com.owd.OWDShippingAPI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Models.Package;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by danny on 1/14/2020.
 */
public class RatingUtilitiesTests {
    private final static Logger log = LogManager.getLogger();

    @Test
    public void RateTest(){
        System.setProperty("com.owd.shippingapi", "self");
        RateRequest request = new RateRequest();
        request.setShipToContact("Danny Nickels");
        request.setShipToAddress1("10 1st Ave E");
        request.setShipToCity("Mobridge");
        request.setShipToState("SD");
        request.setShipToPostalCode("57601");
        request.setShipToCountry("US");
        com.owd.OWDShippingAPI.Models.Package p = new Package();
        p.setWeight(1.5);
        p.setDimension("6x6x8");
        p.setPackagingType("CUSTOM");
        request.setaPackage(p);
        request.setShipService(new String[]{"BWDC1_GROUND"});
        request.setShippingAccountName("OWD_DC1");
        request.setShipTerms("SHIPPER");

        try{
            RatingUtilities ru = new RatingUtilities();
            RateResponse response = ru.getRateResponse(request,true);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            log.debug(gson.toJson(response));
            assertTrue(response.getRates().length ==1);
            log.debug("------------bestway above--------");

            RateResponse rr = ru.getRateResponse(request,false);
            assertTrue(rr.getRates().length>1);
            log.debug(gson.toJson(rr));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
