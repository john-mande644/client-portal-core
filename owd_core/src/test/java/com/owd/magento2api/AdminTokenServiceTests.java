package com.owd.magento2api;

import com.owd.magento2Api.Magento2ApiServiceGenerator;
import com.owd.magento2Api.Models.CreateAdminAccessTokenRequest;
import com.owd.magento2Api.Services.AdminTokenService;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertNotNull;

public class AdminTokenServiceTests {

    //CANNOT BE TESTED WITH THE CURRENT ENVIRONMENT
    @Test
    public void testTokenService(){

        try{
            Magento2ApiServiceGenerator magento2ApiServiceGenerator = new Magento2ApiServiceGenerator("");
            AdminTokenService sService = magento2ApiServiceGenerator.createService(AdminTokenService.class,null);

            CreateAdminAccessTokenRequest request = new CreateAdminAccessTokenRequest();
            request.setUsername("test");
            request.setPassword("test");
            Call<String> callSync = sService.createToken(request);
            Response<String> response = callSync.execute();
            System.out.println(response.code());
            String token = response.body();
            System.out.println(token);

           // assertNotNull(token);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
