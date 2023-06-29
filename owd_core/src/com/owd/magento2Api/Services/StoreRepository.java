package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.Store;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface StoreRepository {
    @GET("rest/all/V1/store/storeViews")
    public Call<List<Store>> getStoresList();
}
