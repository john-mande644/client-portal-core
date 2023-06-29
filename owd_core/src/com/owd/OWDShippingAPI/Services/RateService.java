package com.owd.OWDShippingAPI.Services;

import com.owd.OWDShippingAPI.Models.RateRequest;
import com.owd.OWDShippingAPI.Models.RateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by danny on 11/14/2019.
 */
public interface RateService {

    @POST("/v1/proship/Rate")
    public Call<RateResponse> getRates(@Body RateRequest request);

    @POST("/v1/proship/Rate/Bestway")
    public Call<RateResponse> getBestWayRate(@Body RateRequest request);

}
