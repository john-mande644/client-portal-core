package com.owd.magento2Api.Services;
import com.owd.magento2Api.Models.CreateAdminAccessTokenRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdminTokenService {

    @POST("rest/all/V1/integration/admin/token")
    public Call<String> createToken(@Body CreateAdminAccessTokenRequest createAdminAccessTokenRequest);
}
