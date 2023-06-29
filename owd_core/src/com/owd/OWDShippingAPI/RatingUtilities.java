package com.owd.OWDShippingAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.OWDShippingAPI.Models.RateRequest;
import com.owd.OWDShippingAPI.Models.RateResponse;
import com.owd.OWDShippingAPI.Services.OWDShippingAPIServiceGenerator;
import com.owd.OWDShippingAPI.Services.RateService;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by danny on 1/14/2020.
 */
public class RatingUtilities {
    private final static Logger log = LogManager.getLogger();



    public RateResponse getRateResponse(RateRequest request, boolean bestway) throws Exception {

        if(bestway){
            return sendBestWayRateRequest(request);
        }
        return sendRateRequest(request);
    }


    private RateResponse sendBestWayRateRequest(RateRequest request) throws Exception{
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        log.debug(gson.toJson(request));
        String token= OWDShippingAPIToken.grabNewToken();
        RateService sService = OWDShippingAPIServiceGenerator.createService(RateService.class, token);

        Call<RateResponse> callSync = sService.getBestWayRate(request);
        Response< RateResponse> response = callSync.execute();
        log.debug(response.code());
        RateResponse rateResponse;
        if(response.code() == 200) {
            rateResponse = response.body();

            log.debug(gson.toJson(rateResponse));


        }else{
            log.debug(response.message());
            //todo better error handling with proper messages
            throw new Exception(response.message());
        }

        return rateResponse;
    }
    private RateResponse sendRateRequest(RateRequest request) throws Exception{
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        log.debug(gson.toJson(request));
        String token= OWDShippingAPIToken.grabNewToken();
       RateService sService = OWDShippingAPIServiceGenerator.createService(RateService.class, token);

        Call<RateResponse> callSync = sService.getRates(request);
        Response< RateResponse> response = callSync.execute();
        log.debug(response.code());
        RateResponse rateResponse;
        if(response.code() == 200) {
            rateResponse = response.body();

            log.debug(gson.toJson(rateResponse));


        }else{
            log.debug(response.message());
            //todo better error handling with proper messages
            throw new Exception(response.message());
        }

        return rateResponse;
    }
}
