package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.SearchStocksResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockRepository {
    @GET("rest/foh/V1/inventory/stocks")
    public Call<SearchStocksResponse> getStocksByFilter(@Query("searchCriteria[filter_groups][0][filters][0][field]") String field,
                                                        @Query("searchCriteria[filter_groups][0][filters][0][value]") String value,
                                                        @Query("searchCriteria[sortOrders][0][field]") String sortOrders,
                                                        @Query("searchCriteria[sortOrders][0][direction]") String sortDirection);

}
