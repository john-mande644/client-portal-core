package com.owd.OWDShippingAPI;

import com.owd.OWDShippingAPI.Models.AddressValidationRequest;
import com.owd.OWDShippingAPI.Models.AddressValidationResponse;
import com.owd.core.business.Address;
import org.junit.Test;
import  com.owd.core.tests.BaseTest;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

/**
 * Created by danny on 12/19/2019.
 */
public class AddressValidationUtilitiesTest extends  BaseTest {



    @Test
    public void loadFromOwdOrderTestCode84(){

        AddressValidationUtilities avu = new AddressValidationUtilities();
        try {
            AddressValidationResponse response = avu.validateAddressFromOrderId(18917424);
            assertEquals("84",response.getAddressScore());


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void loadFromOwdOrderTestCode92(){

        AddressValidationUtilities avu = new AddressValidationUtilities();
        try {
            AddressValidationResponse response = avu.validateAddressFromOrderId(19483695);
            assertEquals("92",response.getAddressScore());


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void validAddressTest(){
        AddressValidationRequest av = new AddressValidationRequest();
        av.setInputContact("TEst");
        av.setInputAddress1("10 1st Ave E");
        av.setInputCity("Mobridge");
        av.setInputState("SD");
        av.setInputPostalCode("57601");
        av.setInputCountry("US");
        av.setInputAddress2("");

        AddressValidationUtilities avu = new AddressValidationUtilities();
        try {
            AddressValidationResponse response = avu.validateAddress(av);

            assertEquals("0",response.getAddressScore());
            assertEquals("57601-2603",response.getOutputPostalCode());

            av.setInputContact("TEst");
            av.setInputAddress1("518 N GUADALUPE AVE UNIT");
            av.setInputCity("Redondo Beach");
            av.setInputState("CA");
            av.setInputPostalCode("90277");
            av.setInputCountry("US");
            av.setInputAddress2("Unit B");

            response = avu.validateAddress(av);
            assertEquals("0",response.getAddressScore());


            av.setInputContact("TEst");
            av.setInputAddress1("VIA PER TREZZO 39M FIRST FLOOR");
            av.setInputCity("Vaprio DAdda");
            av.setInputState("Italia");
            av.setInputPostalCode("20069");
            av.setInputCountry("US");
            av.setInputAddress2("First floor");

            response = avu.validateAddress(av);
            assertEquals("216",response.getAddressScore());

            av.setInputContact("Elisa Longo Borghini");
            av.setInputAddress1("Anna Maria Casone");
            av.setInputCity("Ornavasso");
            av.setInputState("Italy");
            av.setInputPostalCode("28877");
            av.setInputCountry("US");
            av.setInputAddress2("11");

            response = avu.validateAddress(av);
            assertEquals("217",response.getAddressScore());


        }catch (Exception e){
            fail();
        }








    }

    @Test
    public void nonResidentialTest(){
        try{
            AddressValidationUtilities av = new AddressValidationUtilities();
            Address a = av.getValidatedAddressFromOrderId(19544293);
            assertFalse(a.isResidential);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getValidatedAddressFromOrderIdTest(){
        AddressValidationUtilities av = new AddressValidationUtilities();
        try {
            Address a = av.getValidatedAddressFromOrderId(19483695);

            assertFalse(a.getAddress_one().contains("UNIT B"));
            assertTrue(a.isResidential);

            a = av.getValidatedAddressFromOrderId(19582804);

        }catch (Exception e){
            assertEquals("411: Primary number invalid",e.getMessage());
            //fail();
        }



    }

    @Test
    public void validAddressTestBadDirectional(){
        AddressValidationRequest av = new AddressValidationRequest();
        av.setInputContact("hahah");
        av.setInputAddress1("10 1st Ave");
        av.setInputCity("Mobridge");
        av.setInputState("SD");
        av.setInputPostalCode("57601");
        av.setInputCountry("US");
        av.setInputAddress2("");

        AddressValidationUtilities avu = new AddressValidationUtilities();
        try {
            AddressValidationResponse response = avu.validateAddress(av);

            assertEquals("79",response.getAddressScore());
            assertEquals("57601-2603",response.getOutputPostalCode());




        }catch (Exception e){
            fail();
        }








    }
    @Test
    public void validAddressTestBadzip(){
        AddressValidationRequest av = new AddressValidationRequest();
        av.setInputContact("hahah");
        av.setInputAddress1("10 1st Ave E");
        av.setInputCity("Mobridge");
        av.setInputState("SD");
        av.setInputPostalCode("57106");
        av.setInputCountry("US");
        av.setInputAddress2("");

        AddressValidationUtilities avu = new AddressValidationUtilities();
        try {
            AddressValidationResponse response = avu.validateAddress(av);

            assertEquals("25",response.getAddressScore());
            assertEquals("57601-2603",response.getOutputPostalCode());




        }catch (Exception e){
            fail();
        }



    }
}
