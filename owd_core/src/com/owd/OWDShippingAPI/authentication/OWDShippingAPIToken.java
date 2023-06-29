package com.owd.OWDShippingAPI.authentication;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by danny on 11/14/2019.
 */
public class OWDShippingAPIToken {

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://identity.owd.com")
                .build();

        AccessTokenServiceInterface service = retrofit.create(AccessTokenServiceInterface.class);

        //grant types = client_credentials
        Call<TokenResponse> call = service.getToken("7701e175c636477bb90e9d81b45a39c5", "3e4bc1fe86b14c48bea6ded2eedcd550", "owdShippingAPI", "client_credentials");
        try {
            Response<TokenResponse> response = call.execute();
            System.out.println(response.body().getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String grabNewToken(){
        String token = null;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://identity.owd.com")
                .build();

        AccessTokenServiceInterface service = retrofit.create(AccessTokenServiceInterface.class);

        //grant types = client_credentials
        Call<TokenResponse> call = service.getToken("7701e175c636477bb90e9d81b45a39c5", "3e4bc1fe86b14c48bea6ded2eedcd550", "owdShippingAPI", "client_credentials");
        try {
            Response<TokenResponse> response = call.execute();
            token = response.body().getAccessToken();
            System.out.println(response.body().getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
