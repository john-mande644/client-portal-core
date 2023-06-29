package com.owd.OWDShippingAPI.Services;

import com.owd.OWDShippingAPI.Models.RateRequest;
import com.owd.OWDShippingAPI.Models.RateResponse;
import com.owd.OWDShippingAPI.Models.VoidShipment;
import com.owd.OWDShippingAPI.Models.VoidShipmentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by danny on 11/14/2019.
 */
public interface VoidService {


    @POST("/v1/proship/Void")
    public Call<VoidShipmentResponse> voidShipments(@Body VoidShipment request);
}
