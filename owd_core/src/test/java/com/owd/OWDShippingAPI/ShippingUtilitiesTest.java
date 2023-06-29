package com.owd.OWDShippingAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.Models.ShipShipment;
import com.owd.OWDShippingAPI.Models.shipServiceModel;
import com.owd.core.tests.BaseTest;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by danny on 11/16/2019.
 */
public class ShippingUtilitiesTest  extends BaseTest {


    @Test
    public void getIANACountryTest(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        try {
            String country = shippingUtilities.getIANACountry("CANADA", "Mobridge");
            assertTrue(country.equals("CA"));

             country = shippingUtilities.getIANACountry("USA", "Mobridge");
            assertTrue(country.equals("US"));
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void  LoadDataFromPackagesLyftNo3rdParty(){
        //System.setProperty("com.owd.shippingapi", "development");

        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p14261968*28956132*b1");
        p.setWeight(.99);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC6");
            // assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            //assertEquals("QVC Inc",shipment.getReturnContact());
            assertEquals("JAX",shipment.getShipToCompany());
            assertEquals("Jerry Rzewnicki",shipment.getShipToContact());
            assertEquals("SHIPPER", shipment.getShipTerms());
            assertNull(shipment.getThirdPartyBilling());


        }catch ( Exception e){
            e.printStackTrace();

        }
    }


    @Test
    public void  LoadDataFromPackagesTestNoContact(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13832865*28395023*b1");
        p.setWeight(1.66);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC6");
            // assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            //assertEquals("QVC Inc",shipment.getReturnContact());
        assertEquals("Jack Stevenson",shipment.getShipToCompany());
            assertEquals("Jack Stevenson",shipment.getShipToContact());


        }catch ( Exception e){
            e.printStackTrace();

        }
    }

    @Test
    public void  LoadMultiPieceDataFromPackagesTestThirdParty(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p14068014*28660578*b1");
        p.setWeight(45.0);
        List<Package> l = new ArrayList<>();
        l.add(p);
        Package p2 = new Package();
        p2.setPackageReference("p14068014*28660578*b2");
        p2.setWeight(10.18);
        l.add(p2);
        Package p3 = new Package();
        p3.setPackageReference("p14068014*28660578*b3");
        p3.setWeight(3.23);
        l.add(p3);

        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC6");
            // assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            //assertEquals("QVC Inc",shipment.getReturnContact());

            assertEquals("OWD_DC6",shipment.getShippingAccountName());
            assertEquals("THIRD_PARTY",shipment.getShipTerms());

        }catch ( Exception e){
            e.printStackTrace();

        }
    }

    @Test
    public void SuffixTest(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p14324820*29032235*b1");
        p.setWeight(0.99);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            // assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            //assertEquals("QVC Inc",shipment.getReturnContact());

            assertEquals("LYFT_DC6",shipment.getShippingAccountName());
            System.out.println(shipment.getClientReference());
            assertTrue(shipment.getClientReference().contains("AMP"));


        }catch ( Exception e){
            e.printStackTrace();

        }
    }

    @Test
    public void  LoadDataFromPackagesTes2t(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13800530*28340434*b1");
        p.setWeight(0.24);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
           // assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            //assertEquals("QVC Inc",shipment.getReturnContact());

            assertEquals("OWD_DC7",shipment.getShippingAccountName());

        }catch ( Exception e){
            e.printStackTrace();

        }
    }
    @Test
    public void  LoadDataFromPackagesTest2(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p15050047*29914387*b1");
        p.setWeight(3.9);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            shippingUtilities.CheckShipmentForErrors(shipment);
           /* assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            assertEquals("QVC Inc",shipment.getReturnContact());

            assertEquals("OWD_DC7",shipment.getShippingAccountName());*/

        }catch ( Exception e){
            e.printStackTrace();

        }
    }
    @Test
    public void  LoadDataFromPackagesTest(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13708262*28178020*b1");
        p.setWeight(1.5);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            assertEquals("QVC Drop Ship Test Order",shipment.getShipToContact());
            assertEquals("QVC Inc",shipment.getReturnContact());

            assertEquals("OWD_DC7",shipment.getShippingAccountName());

        }catch ( Exception e){
            e.printStackTrace();

        }
    }
    @Test
    public void TestOversize(){
        List<Double> dims = new ArrayList<>();
        dims.add(2d);
        dims.add(3d);
        dims.add(4d);
        ShippingUtilities su = new ShippingUtilities();
        try {
            assertFalse(su.oversize(dims));
            dims.clear();
            dims.add(12d);
            dims.add(12d);
            dims.add(12d);
            assertTrue(su.oversize(dims));

        }catch (Exception e){
            fail();
        }
    }


    @Test
    public void  LoadDataFromPackagesTestThirdPartyAndGroup(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13768366*28279777*b1");
        p.setWeight(1.5);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            assertEquals("975W8X",shipment.getThirdPartyBilling().getAccount());
            assertEquals("HomeDepot",shipment.getGroupName());
        }catch ( Exception e){
            e.printStackTrace();

        }
    }


    @Test
     public void  LoadDataFromPackagesTestSaturday(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13717774*28192981R3*b1");
        p.setWeight(1.5);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            assertTrue(shipment.getDeliveryOptions().isSaturdayDelivery());
        }catch ( Exception e){
            e.printStackTrace();

        }
    }

    @Test
    public void printList(){
        Map<String,shipServiceModel> proShipServiceToOwd = new HashMap<>();
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.FOV",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.FO","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.PRI",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.PRI","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.STD",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.STD","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.2DA",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.2DA","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.EXP",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.EXP","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.CAFE.GND",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.CAFE.HDL",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.FHD","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.PRII",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.IPRI","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.FXRS.2DAI",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.IECO","TANDATA_FEDEXFSMS.FEDEX"));
        proShipServiceToOwd.put("BWTI_FXRS.SP.SM",new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.SP_PS","TANDATA_FEDEXFSMS.FEDEX."));

        proShipServiceToOwd.put("BWTI_UPS.UPS.NAM",new shipServiceModel("CONNECTSHIP_UPS.UPS.NAM","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.NDA",new shipServiceModel("CONNECTSHIP_UPS.UPS.NDA","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.NDS",new shipServiceModel("CONNECTSHIP_UPS.UPS.NDS","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.2AM",new shipServiceModel("CONNECTSHIP_UPS.UPS.2AM","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.2DA",new shipServiceModel("CONNECTSHIP_UPS.UPS.2DA","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.3DS",new shipServiceModel("CONNECTSHIP_UPS.UPS.3DA","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.GND",new shipServiceModel("CONNECTSHIP_UPS.UPS.GND","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.WWE",new shipServiceModel("CONNECTSHIP_UPS.UPS.EPD","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.WWX",new shipServiceModel("CONNECTSHIP_UPS.UPS.EXP","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.WWXP",new shipServiceModel("CONNECTSHIP_UPS.UPS.EXPPLS","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.WWS",new shipServiceModel("CONNECTSHIP_UPS.UPS.EXPSVR","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.SC",new shipServiceModel("CONNECTSHIP_UPS.UPS.STD","CONNECTSHIP_UPS.UPS"));

        proShipServiceToOwd.put("BWTI_USPS.USPS.FCSP",new shipServiceModel("TANDATA_USPS.USPS.FIRST","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.PRIORITY","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.EXPPC",new shipServiceModel("TANDATA_USPS.USPS.EXPR","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PS",new shipServiceModel("TANDATA_USPS.USPS.PS_NONPRESORT","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.FCSP",new shipServiceModel("TANDATA_USPS.USPS.I_FIRST","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.PRIORITY_CUBIC","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.EXPPC",new shipServiceModel("TANDATA_USPS.USPS.I_EXP_DMND","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.MM",new shipServiceModel("TANDATA_USPS.USPS.SPCL","TANDATA_USPS.USPS"));

        proShipServiceToOwd.put("BWTI_DHL.DHL.WWP",new shipServiceModel("CONNECTSHIP_DHL.DHL.WPX","CONNECTSHIP_DHL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM81",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM82",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM631",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM631",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM36",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM83",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLGMI.PLY",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PC_PRI","CONNECTSHIP_DHLGLOBALMAIL.DHL"));

        proShipServiceToOwd.put("BWTI_UPS.UPS.SPP",new shipServiceModel("CONNECTSHIP_UPS.UPS.SPPS","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.SPS",new shipServiceModel("CONNECTSHIP_UPS.UPS.SPSTD","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.MIE",new shipServiceModel("BWTI_UPS.UPS.MIE","CONNECTSHIP_UPS.UPS"));

        proShipServiceToOwd.put("CONNECTSHIP_GLOBAL.APC.PRIDDPDC",new shipServiceModel("CONNECTSHIP_GLOBAL.APC.PRIDDPDC","CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("CONNECTSHIP_GLOBAL.APC.PRIDDUDC",new shipServiceModel("CONNECTSHIP_GLOBAL.APC.PRIDDUDC","CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("COM_OWD_FLATRATE_INTL_PRIDDP",new shipServiceModel( "CONNECTSHIP_GLOBAL.APC.PRIDDPDC", "CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("COM_OWD_FLATRATE_INTL_PRIDDU",new shipServiceModel( "CONNECTSHIP_GLOBAL.APCPRIDDUDC", "CONNECTSHIP_GLOBAL.APC"));

        proShipServiceToOwd.put("PS_ALF.ONTRAC.CT",new shipServiceModel("ONTRAC.GROUND","ONTRAC"));

        for(shipServiceModel m : proShipServiceToOwd.values()){
            System.out.print("'"+m.getMethodCode()+"',");
        }

    }

    @Test
    public void proShipToOWDLookupTest(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        try {
            shipServiceModel method = shippingUtilities.getOwdShipCodesFromProShipCodes("BWTI_USPS.USPS.FCSP", 1,"US");
            assertEquals("TANDATA_USPS.USPS.FIRST",method.getMethodCode());

            shipServiceModel method2 = shippingUtilities.getOwdShipCodesFromProShipCodes("BWTI_FXRS.FXRS.2DAI",1,"US");
            assertEquals("TANDATA_FEDEXFSMS.FEDEX.IECO", method2.getMethodCode());

            shipServiceModel method3 = shippingUtilities.getOwdShipCodesFromProShipCodes("BWTI_USPS.USPS.EXPPC",1,"US");
            assertEquals("TANDATA_USPS.USPS.EXPR", method3.getMethodCode());
            assertEquals("TANDATA_USPS.USPS", method3.getCarrierCode());

            shipServiceModel method4 = shippingUtilities.getOwdShipCodesFromProShipCodes("BWTI_USPS.USPS.EXPPC",1,"CANADA");
            assertEquals("TANDATA_USPS.USPS.I_EXP_DMND", method4.getMethodCode());
            assertEquals("TANDATA_USPS.USPS", method4.getCarrierCode());
        }catch (Exception e){
            fail();
        }

    }
    @Test
    public void lookupFlatRateServiceCodeTest(){

        ShippingUtilities shippingUtilities = new ShippingUtilities();
        List<Double> dims = new ArrayList<>();
        dims.add(2d);
        dims.add(3d);
        dims.add(4d);
        try {
            String method = shippingUtilities.lookupFlatRateServiceCode("COM_OWD_FLATRATE_GROUND", 55, "", "DC1", dims);
            assertEquals("BWDC1_GROUND", method);
        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void lookupFlatRateServiceCodeOVERSIZETest(){

        ShippingUtilities shippingUtilities = new ShippingUtilities();
        List<Double> dims = new ArrayList<>();
        dims.add(12d);
        dims.add(13d);
        dims.add(14d);
        try {
            String method = shippingUtilities.lookupFlatRateServiceCode("COM_OWD_FLATRATE_GROUND", 55, "", "DC1", dims);
            assertEquals("BWDC1_GROUND_LARGE", method);
        }catch (Exception e){
            fail();
        }

    }
    @Test
    public void  LoadDataFromPackagesTestSignatureAndThirdParty(){
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        Package p = new Package();
        p.setPackageReference("p13728383*28209789*b1");
        p.setWeight(1.5);
        List<Package> l = new ArrayList<>();
        l.add(p);
        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC7");
            assertTrue(shipment.getDeliveryOptions().isSignatureRequired());
            assertTrue(shippingUtilities.isInternational(shipment));
            assertEquals("THIRD_PARTY",shipment.getShipTerms());
            assertTrue(shipment.getPackages().get(0).getLineInfo().size()>0);
            GsonBuilder builder = new GsonBuilder();
             builder.setPrettyPrinting();
            Gson gson = builder.create();
            System.out.println(gson.toJson(shipment));
        }catch ( Exception e){
            e.printStackTrace();
            fail();
        }
    }

    // Sean 2020/01/17
    @Test
    public void  getSuffixTest(){
        HashMap<String, Object> m = new HashMap<String, Object>();

        setSuffix(m, "inventory_num", "LYFT150");
        assertEquals("-AMP", ShippingUtilities.getSuffix("529", m));

        setSuffix(m, "inventory_num", "LYFT126-KITMS");
        assertEquals("-1KJkt", ShippingUtilities.getSuffix("529", m));

//        System.out.println(ShippingUtilities.getSuffix("529", m));
    }

    private static void setSuffix(Map map, String key, Object val) {
        if (val == null) {
            map.remove(key);
        } else {
            map.put(key, val);
        }
    }


    @Test
    public void getCustomDimWeightForClientByPackageTest(){
        System.setProperty("com.owd.environment", "test");
        try{
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,55);
            Integer originalDim = client.getDimFactor();
            client.setDimFactor(0);
            HibernateSession.currentSession().saveOrUpdate(client);
            HibUtils.commit(HibernateSession.currentSession());

            Integer weight = ShippingUtilities.getCustomDimWeightForClientByPackage("p15910384*31064795*b1","");
            assertEquals(weight,(Integer)0);

            client.setDimFactor(193);
            HibernateSession.currentSession().saveOrUpdate(client);
            HibUtils.commit(HibernateSession.currentSession());

            Integer weight2 = ShippingUtilities.getCustomDimWeightForClientByPackage("p15910384*31064795*b1","");
            assertEquals((Integer)14,weight2);


            client.setDimFactor(225);
            HibernateSession.currentSession().saveOrUpdate(client);
            HibUtils.commit(HibernateSession.currentSession());

            Integer weight3 = ShippingUtilities.getCustomDimWeightForClientByPackage("p15910384*31064795*b1","");
            assertEquals((Integer)12,weight3);

            client.setDimFactor(originalDim);
            HibernateSession.currentSession().saveOrUpdate(client);
            HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }



    // Sean 02/28/2020
    @Test
    public void calcOrderTotal() {
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        List<Package> l = new ArrayList<>();

        for (int i = 0; i<3; i++){
            Package p1 = new Package();
            p1.setPackageReference("p14272279*28913690*b"+(i+1));
            p1.setWeight(1.5);
            l.add(p1);
        }

        try {
            ShipShipment shipment = shippingUtilities.LoadDataFromPackages(l, "DC6");
            assertEquals(3600.0, ShippingUtilities.calcOrderTotal(shipment), 0);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
