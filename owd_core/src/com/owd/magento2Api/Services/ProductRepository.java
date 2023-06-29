package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.Product;
import com.owd.magento2Api.Models.SearchProductResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ProductRepository {

    @GET("rest/all/V1/products/{sku}")
    public Call<ResponseBody> getProductBySKU(@Path("sku") String productSku);

    @GET("rest/all/V1/products")
    public Call<SearchProductResponse> getProducts(@Query("searchCriteria[sortOrders][0][field]") String sortOrders,
                                                   @Query("searchCriteria[sortOrders][0][direction]") String sortDirection,
                                                   @Query("searchCriteria[pageSize]") int pageSize,
                                                   @Query("searchCriteria[currentPage]") int currentPage);
}
