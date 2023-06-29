package com.owd.magento2Api;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Magento2ApiServiceGenerator {

    private String baseUrl;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;

    public Magento2ApiServiceGenerator(String baseUrl) {
        this.baseUrl = baseUrl;
        this.builder  = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        httpClient = new OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS);
    }

    public <S> S createService(Class<S> serviceClass,final String token) {
        if (token != null){
            httpClient.interceptors().clear();

            Magento2ApiServiceGenerator.AuthorizationInterceptor ai = new Magento2ApiServiceGenerator.AuthorizationInterceptor();
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
