package com.owd.magento2api;
import com.owd.magento2Api.Magento2ApiServiceGenerator;
import com.owd.magento2Api.Models.Currency;
import com.owd.magento2Api.Services.CurrencyRepository;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import static org.junit.Assert.assertTrue;

public class CurrencyRepositoryTest {

    @Test
    public void testGetCurrency(){

        try{

            Magento2ApiServiceGenerator magento2ApiServiceGenerator = new Magento2ApiServiceGenerator("");
            CurrencyRepository sService = magento2ApiServiceGenerator.createService(CurrencyRepository.class,null);

            Call<Currency> callSync = sService.getCurrency();
            Response<Currency> response = callSync.execute();
            System.out.println(response.code());
            Currency currency = response.body();

            assertTrue(currency.getBase_currency_code().toLowerCase().equals("aud"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
