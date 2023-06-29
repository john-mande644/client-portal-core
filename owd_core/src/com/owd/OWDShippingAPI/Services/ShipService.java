package com.owd.OWDShippingAPI.Services;


import com.owd.OWDShippingAPI.Models.ShipShipment;
import com.owd.OWDShippingAPI.Models.ShipShipmentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by danny on 11/14/2019.
 */
public interface ShipService {


    @POST("/v1/proship/Shipment")
    public Call<ShipShipmentResponse> sendShipment(@Body ShipShipment request);
}
