package com.owd.magento2Api.Services;

import com.owd.magento2Api.Models.WebSite;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface WebsiteRepository {
    @GET("rest/all/V1/store/websites")
    public Call<List<WebSite>> getWebSites();
}
