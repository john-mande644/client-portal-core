package com.owd.OWDShippingAPI.Services;

import com.amazonaws.util.Base64;
import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import org.apache.xmlbeans.XmlBase64Binary;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by danny on 11/14/2019.
 */
public class ShipServiceTests {


    @Test
    public void VoidTest(){

        try{
            VoidShipment v = new VoidShipment();
            List<String> voids = new ArrayList<>();
            voids.add("BUPO.9205590100070400000123");
            v.setVoidIds(voids);
            String token= OWDShippingAPIToken.grabNewToken();
            VoidService sService = OWDShippingAPIServiceGenerator.createService(VoidService.class,token);

            Call<VoidShipmentResponse> callSync = sService.voidShipments(v);
            Response<VoidShipmentResponse> response = callSync.execute();
            System.out.println(response.code());
            VoidShipmentResponse shipment = response.body();

            assertTrue(shipment.getVoidedIds().get(0).isSuccess());

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void ShipmentTest(){

        try{
            ShipShipment request = new ShipShipment();
            request.setShipToContact("Danny Nickels");
            request.setShipToAddress1("10 1st Ave E");
            request.setShipToCity("Mobridge");
            request.setShipToState("SD");
            request.setShipToPostalCode("57601");
            request.setShipToPhone("605-845-7172");
            request.setShipDate("11/18/2019");
            request.setShipToCountry("US");
            com.owd.OWDShippingAPI.Models.Package p = new Package();
            p.setWeight(1.5);
            p.setDimension("6x6x8");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("p1");
            request.setPackages(new ArrayList<Package>());
            request.getPackages().add(p);
            request.setShipService("BW_GROUND");
            request.setShippingAccountName("OWD_DC1");
            request.setShipTerms("SHIPPER");


            String token= OWDShippingAPIToken.grabNewToken();
            ShipService sService = OWDShippingAPIServiceGenerator.createService(ShipService.class,token);

            Call<ShipShipmentResponse> callSync = sService.sendShipment(request);
            Response<ShipShipmentResponse> response = callSync.execute();
            System.out.println(response.code());
            ShipShipmentResponse shipment = response.body();

            assertTrue(shipment.getPackages().size()==1);


            System.out.println(new String(Base64.decode(shipment.getPackages().get(0).getLabel().get(0))));



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
