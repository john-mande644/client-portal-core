package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.InvontoryStock;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InventoryStockRepository {

    @GET("rest/foh/V1/stockItems/{productSku}")
    public Call<InvontoryStock> getStock(@Path("productSku") String productSku);


    @GET("rest/all/V1/stockItems/{productSku}")
    public Call<ResponseBody> getStockResponseBody(@Path("productSku") String productSku);
}
