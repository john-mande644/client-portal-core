package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.PostSourceItemsRequest;
import com.owd.magento2Api.Models.SearchSourceItemResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SourceItemRepository {

    @GET("rest/foh/V1/inventory/source-items")
    public Call<ResponseBody> getSourceItemsResponse(@Query("searchCriteria[filter_groups][0][filters][0][field]") String field,
                                             @Query("searchCriteria[filter_groups][0][filters][0][value]") String value,
                                             @Query("searchCriteria[filter_groups][0][filters][0][condition_type]") String type);
    @GET("rest/foh/V1/inventory/source-items")

    public Call<SearchSourceItemResponse> getSourceItems(@Query("searchCriteria[filter_groups][0][filters][0][field]") String field,
                                                         @Query("searchCriteria[filter_groups][0][filters][0][value]") String value,
                                                         @Query("searchCriteria[filter_groups][0][filters][0][condition_type]") String type);

    @POST("rest/foh/V1/inventory/source-items")
    public Call<ResponseBody> postSourceItems(@Body PostSourceItemsRequest postSourceItemsRequest);

}
