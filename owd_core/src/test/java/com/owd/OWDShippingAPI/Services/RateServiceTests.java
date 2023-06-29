

package com.owd.OWDShippingAPI.Services;

import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Created by danny on 11/14/2019.
 */
public class RateServiceTests {

    @Test
    public void RateTest(){

        try{
            RateRequest request = new RateRequest();
            request.setShipToContact("Danny Nickels");
            request.setShipToAddress1("10 1st Ave E");
            request.setShipToCity("Mobridge");
            request.setShipToState("SD");
            request.setShipToPostalCode("57601");
            request.setShipToCountry("US");
            Package p = new Package();
            p.setWeight(1.5);
            p.setDimension("6x6x8");
            p.setPackagingType("CUSTOM");
            request.setaPackage(p);
            request.setShipService(new String[]{"BW_GROUND"});
            request.setShippingAccountName("OWD_DC1");
            request.setShipTerms("SHIPPER");


            String token= OWDShippingAPIToken.grabNewToken();
                    RateService rService = OWDShippingAPIServiceGenerator.createService(RateService.class,token);
            Call<RateResponse> callSync = rService.getRates(request);

            Response<RateResponse> response = callSync.execute();
            System.out.println(response.code());
            RateResponse rResponse = response.body();

           for(Rate r : rResponse.getRates()){
               System.out.println(r.getShipService());
               System.out.println(r.getListTotal());
           }
            assertTrue(rResponse.getRates().length>1);

            Call<RateResponse> bestWaySync = rService.getBestWayRate(request);
            Response<RateResponse> bestResponse = bestWaySync.execute();
            System.out.println(bestResponse.code());
            RateResponse bestRateResponse = bestResponse.body();

            for(Rate r : bestRateResponse.getRates()){
                System.out.println(r.getShipService());
                System.out.println(r.getListTotal());
            }
            assertTrue(bestRateResponse.getRates().length==1);



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
