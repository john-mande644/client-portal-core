package com.owd.magento2Api.Services;
import com.owd.magento2Api.Models.Currency;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyRepository {

    @GET("/rest/all/V1/directory/currency")
    public Call<Currency> getCurrency();
}