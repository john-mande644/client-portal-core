package com.owd.OWDShippingAPI.authentication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 * Created by danny on 11/14/2019.
 */
public interface AccessTokenServiceInterface {

    @POST("/connect/token")
    @FormUrlEncoded
    Call<TokenResponse> getToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("scope") String scope, @Field("grant_type") String grant_type);
}
