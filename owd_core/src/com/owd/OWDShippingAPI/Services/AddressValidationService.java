package com.owd.OWDShippingAPI.Services;

import com.owd.OWDShippingAPI.Models.AddressValidationRequest;
import com.owd.OWDShippingAPI.Models.AddressValidationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by danny on 12/19/2019.
 */
public interface AddressValidationService {

    @POST("/v1/proship/AddressValidation")
    public Call<AddressValidationResponse> getValidation(@Body AddressValidationRequest request);
}
