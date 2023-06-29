package com.owd.OWDShippingAPI.Services;

import okhttp3.*;
import okhttp3.Authenticator;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 11/14/2019.
 */
public class OWDShippingAPIServiceGenerator {



   private static final String BASE_URL = getBaseUrl();


   private static String getBaseUrl(){

       String environment = null;
       Properties env = System.getProperties();
       for (Object envName : env.keySet()) {
           if(envName.toString().contains("com.owd"))
           {
               System.out.format("%s=%s%n",
                       envName,
                       env.get(envName));
           }
       }
       environment = System.getProperty("com.owd.shippingapi");
       if (null==environment){
           environment = "production";
       }

       switch(environment){

           case "self":
               System.out.println("Self Shipping API --------shipping-----------");
               return "http://localhost:5009/";
           case "development":
               System.out.println("DEVELOPMENT Shipping API --------shipping-----------");
               return "http://dev.shippingapi.owd.com/";

           default:
               System.out.println("PRODUCTION Shipping API --------shipping-----------");
               return "https://shippingapi1.owd.com/";
       }



   }



    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder().readTimeout(45, TimeUnit.SECONDS);

    public static <S> S createService(Class<S> serviceClass,final String token) {
        if (token != null){
            httpClient.interceptors().clear();

            AuthorizationInterceptor ai = new AuthorizationInterceptor();
            ai.token = "Bearer "+token;
            httpClient.addInterceptor(ai);
            builder.client(httpClient.build());

            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }



   static class AuthorizationInterceptor implements Interceptor {
        public String token;
        @Override public Response intercept(Interceptor.Chain chain)throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(request);
        }
    }
}
