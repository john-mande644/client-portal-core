package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.Source;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface SourceRepository {

    @GET("rest/all/V1/inventory/get-sources-assigned-to-stock-ordered-by-priority/{stock_id}")
    public Call<List<Source>> getSourcesForStocks(@Path("stock_id") int stock_id);

}
