package com.owd.OWDShippingAPI;

import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.Models.RateRequest;
import com.owd.OWDShippingAPI.Models.RateResponse;
import com.owd.core.csXml.RATEREQUEST;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RatingUtilitiesDump {


    private static StringBuilder data = new StringBuilder();
   private static  RatingUtilities ru = new RatingUtilities();
   private static  Map<String,Map<Double, Map<String, RateResponse>>> allRates = new TreeMap<>();
    public static void doDomestic(){



        Map<Double, Map<String, RateResponse>> upsGround = loadMapForMethod("PS_ALF.ONTRAC.CT","OWD_DC6");
        //System.out.println("BWTI_UPS.UPS.GND");
        allRates.put("PS_ALF.ONTRAC.CT",upsGround);

        /*Map<Double, Map<String, RateResponse>> ups2day = loadMapForMethod("BWTI_UPS.UPS.2DA","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.2DA",ups2day);

        Map<Double, Map<String, RateResponse>> fedExHome = loadMapForMethod("BWTI_FXRS.CAFE.HDL","OWD_DC6");
        //System.out.println("BWTI_UPS.UPS.GND");


        allRates.put("BWTI_FXRS.CAFE.HDL",fedExHome);

        Map<Double, Map<String, RateResponse>> fedExGND = loadMapForMethod("BWTI_FXRS.CAFE.GND","OWD_DC6");
        //System.out.println("BWTI_UPS.UPS.GND");


        allRates.put("BWTI_FXRS.CAFE.GND",fedExGND);

        Map<Double, Map<String, RateResponse>> upsNextday = loadMapForMethod("BWTI_UPS.UPS.NDA","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.NDA",upsNextday);

        Map<Double, Map<String, RateResponse>> upsMIF = loadMapForMethod("BWTI_UPS.UPS.MIF","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.MIF",upsMIF);


        Map<Double, Map<String, RateResponse>> USPSPM = loadMapForMethod("BWTI_USPS.USPS.PM","OWD_DC6");
        allRates.put("BWTI_USPS.USPS.PM",USPSPM);

        Map<Double, Map<String, RateResponse>> USPSFCM = loadMapForMethod("BWTI_USPS.USPS.FCSP","OWD_DC6");
        allRates.put("BWTI_USPS.USPS.FCSP",USPSFCM);*/
        printData(allRates);
    }

    public static void doInternational(){
        Map<Double, Map<String, RateResponse>> FedExPriority = loadMapForMethod("BWTI_FXRS.FXRS.PRI","OWD_DC6");
        allRates.put("BWTI_FXRS.FXRS.PRI",FedExPriority);
        printData(allRates);

    }
    public static void doUPSInternational(){
        Map<Double, Map<String, RateResponse>> WWExpress = loadMapForMethod("BWTI_UPS.UPS.WWX","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.WWX",WWExpress);

        Map<Double, Map<String, RateResponse>> WWExpedited = loadMapForMethod("BWTI_UPS.UPS.WWE","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.WWE",WWExpedited);

        Map<Double, Map<String, RateResponse>> WWSaver = loadMapForMethod("BWTI_UPS.UPS.WWS","OWD_DC6");
        allRates.put("BWTI_UPS.UPS.WWS",WWSaver);
        printData(allRates);
    }
    public static void main(String[] args){
        System.setProperty("com.owd.shippingapi", "development");
        loadData();

      //  doDomestic();
        doUPSInternational();

        




    }

    private static void printUPS(String method, Map<String,Map<Double, Map<String, RateResponse>>> therates){
        boolean first = true;
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().baseNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }
        first = true;
        //accessorials
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print("Res: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().residentialFeeNet);
                System.out.print("|");
                System.out.print("Fuel: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().fuelSurchargeNet);
                System.out.print("|");
                System.out.print("DelArea: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().extendedAreaFeeNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }
    }

    private static void printUSPS(String method,Map<String,Map<Double, Map<String, RateResponse>>> therates){
        boolean first = true;
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().baseNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }
        first = true;
        //accessorials
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print("Res: ");
                // System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().residentialFeeNet);
                System.out.print("|");
                System.out.print("Fuel: ");
                // System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().fuelSurchargeNet);
                System.out.print("|");
                System.out.print("DelArea: ");
                // System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().extendedAreaFeeNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }
    }


    private static void printFedEx(String method,Map<String,Map<Double, Map<String, RateResponse>>> therates){
        boolean first = true;
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().baseNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }
        first = true;
        //accessorials
        for (Double weight : therates.get(method).keySet()) {
            if (first) {
                System.out.print("zone:\t");
                for (String z : therates.get(method).get(weight).keySet()) {
                    System.out.print(z);
                    System.out.print("\t");
                }
                first = false;
                System.out.print("\n");
            }
            System.out.print(weight);
            System.out.print("\t");
            for (String z : therates.get(method).get(weight).keySet()) {
                System.out.print("res: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().residentialFeeNet);
                System.out.print("|");
                System.out.print("Fuel: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().fuelSurchargeNet);
                System.out.print("|");
                System.out.print("DelArea: ");
                System.out.print(therates.get(method).get(weight).get(z).getRates()[0].getDetailedRate().extendedAreaFeeNet);
                System.out.print("\t");

            }
            System.out.print("\n");

        }

    }
    private static void printData(Map<String,Map<Double, Map<String, RateResponse>>> therates){

      for(String method: therates.keySet()) {

          System.out.println(method);

         if(method.contains("UPS")) {
             printUPS(method,therates);
          }
          if(method.contains("USPS")) {
             printUSPS(method,therates);
          }

          if(method.contains("FXRS")) {
              printFedEx(method,therates);
          }
          if(method.contains("ONTRAC")) {
              printUPS(method,therates);
          }

      }


    }

    private static void loadMapForUPSMailInnovations(String method,Map<Double, Map<String, RateResponse>> rates){
        List<Double> weights = new ArrayList<>();
        weights.add(0.0625);
        weights.add(0.25);
        weights.add(0.5);
        weights.add(0.75);
        weights.add(0.9375);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2UPS.setaPackage(p);

                owdDC6Zone2UPS.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2UPS, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void loadMapForONTRACDomestic(String method,Map<Double, Map<String, RateResponse>> rates){
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2ONTRAC.setaPackage(p);

                owdDC6Zone2ONTRAC.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2ONTRAC, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                /*owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void loadMapForUPSDomestic(String method,Map<Double, Map<String, RateResponse>> rates){
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2UPS.setaPackage(p);

                owdDC6Zone2UPS.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2UPS, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void loadMapForUSPSFirstDomestic(String method,Map<Double, Map<String, RateResponse>> rates) {
        List<Double> weights = new ArrayList<>();
        weights.add(0.0625);
        weights.add(0.25);
        weights.add(0.5);
        weights.add(0.75);
        weights.add(0.9375);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2UPS.setaPackage(p);

                owdDC6Zone2UPS.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2UPS, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private static void loadMapForUSPSPMDomestic(String method,Map<Double, Map<String, RateResponse>> rates) {
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2UPS.setaPackage(p);

                owdDC6Zone2UPS.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2UPS, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void loadFedExDomesticRates(String method, Map<Double, Map<String, RateResponse>> rates) {
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Zone2UPS.setaPackage(p);

                owdDC6Zone2UPS.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Zone2UPS, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Zone4UPS.setaPackage(p);
                owdDC6Zone4UPS.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Zone4UPS, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);


                owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private static void loadUPSInternationalRates(String method, Map<Double, Map<String, RateResponse>> rates) {
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        weights.add(3.0);
        weights.add(4.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x5x4");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Australia.setaPackage(p);

                owdDC6Australia.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Australia, false);
                if(null == response.getRates()[0].getError()) {
                    rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(), response);
                }

                owdDC6Canada.setaPackage(p);
                owdDC6Canada.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Canada, false);
                if(null == response4.getRates()[0].getError()) {
                    rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(), response4);
                }

                owdDC6UnitedKingdom.setaPackage(p);
                owdDC6UnitedKingdom.setShipService(m);
                RateResponse response5 = ru.getRateResponse(owdDC6UnitedKingdom, false);
                if(null == response5.getRates()[0].getError()) {
                    rates.get(weight).put(response5.getRates()[0].getDetailedRate().getZone(), response5);
                }


                owdDC6Romania.setaPackage(p);
                owdDC6Romania.setShipService(m);
                RateResponse response6 = ru.getRateResponse(owdDC6Romania, false);
                if(null == response6.getRates()[0].getError()) {
                    rates.get(weight).put(response6.getRates()[0].getDetailedRate().getZone(), response6);
                }

               /* owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private static void loadFedExInternationalRates(String method, Map<Double, Map<String, RateResponse>> rates) {
        List<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        weights.add(3.0);
        weights.add(4.0);
        weights.add(5.0);
        weights.add(10.0);
        weights.add(15.0);
        weights.add(20.0);
        for(Double weight : weights){
            Package p = new Package();
            p.setWeight(weight);
            p.setDimension("6x6x6");
            p.setPackagingType("CUSTOM");
            p.setPackageReference("AAA");
            rates.put(weight,new TreeMap<String, RateResponse>());
            String[] m = new String[1];
            m[0] = method;
            try {
                owdDC6Australia.setaPackage(p);

                owdDC6Australia.setShipService(m);
                RateResponse response = ru.getRateResponse(owdDC6Australia, false);
                rates.get(weight).put(response.getRates()[0].getDetailedRate().getZone(),response);

                owdDC6Canada.setaPackage(p);
                owdDC6Canada.setShipService(m);

                RateResponse response4 = ru.getRateResponse(owdDC6Canada, false);
                rates.get(weight).put(response4.getRates()[0].getDetailedRate().getZone(),response4);

                owdDC6UnitedKingdom.setaPackage(p);
                owdDC6UnitedKingdom.setShipService(m);
                RateResponse response5 = ru.getRateResponse(owdDC6UnitedKingdom, false);
                rates.get(weight).put(response5.getRates()[0].getDetailedRate().getZone(),response5);

                owdDC6Romania.setaPackage(p);
                owdDC6Romania.setShipService(m);
                RateResponse response6 = ru.getRateResponse(owdDC6Romania, false);
                rates.get(weight).put(response6.getRates()[0].getDetailedRate().getZone(),response6);



               /* owdDC6Zone6UPS.setaPackage(p);
                owdDC6Zone6UPS.setShipService(m);

                RateResponse response8 = ru.getRateResponse(owdDC6Zone6UPS, false);
                rates.get(weight).put(response8.getRates()[0].getDetailedRate().getZone(),response8);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static Map<Double, Map<String, RateResponse>> loadMapForMethod(String method,String account){
        Map<Double, Map<String, RateResponse>> rates = new TreeMap<>();

        if(method.contains("ONTRAC")){
            loadMapForONTRACDomestic(method,rates);
        }
        if(method.contains("UPS.MIF")&& account.contains("DC6")){
           loadMapForUPSMailInnovations(method,rates);

        }else if(method.contains("UPS")&& account.contains("DC6")){
            if(method.contains("WW")){
                loadUPSInternationalRates(method,rates);
            }else {
                loadMapForUPSDomestic(method, rates);
            }
        }


        if(method.contains("USPS.FCSP")&& account.contains("DC6")){
           loadMapForUSPSFirstDomestic(method,rates);

        }else if(method.contains("USPS.PM")&& account.contains("DC6")){
           loadMapForUSPSPMDomestic(method,rates);

        }

        if(method.contains("FXRS")&& account.contains("DC6")){
            if(method.equals("BWTI_FXRS.FXRS.PRI")){
                loadFedExInternationalRates(method,rates);
            }else{
                loadFedExDomesticRates(method, rates);
            }


        }

        return rates;

    }




    private static RateRequest owdDC6Zone6UPS = new RateRequest();
    private static RateRequest owdDC6Zone2UPS = new RateRequest();
    private static RateRequest owdDC6Zone4UPS = new RateRequest();
    private static RateRequest owdDC6Australia = new RateRequest();
    private static RateRequest owdDC6Canada = new RateRequest();
    private static RateRequest owdDC6UnitedKingdom = new RateRequest();
    private static RateRequest owdDC6Romania = new RateRequest();
    private static RateRequest owdDC6Zone2ONTRAC = new RateRequest();

    private static void loadData(){
        owdDC6Zone6UPS.setShipToContact("Danny Nickels");
        owdDC6Zone6UPS.setShipToAddress1("10 1st Ave E");
        owdDC6Zone6UPS.setShipToCity("Mobridge");
        owdDC6Zone6UPS.setShipToState("SD");
        owdDC6Zone6UPS.setShipToPostalCode("57601");
        owdDC6Zone6UPS.setShipToCountry("US");
        owdDC6Zone6UPS.setShipTerms("SHIPPER");
        owdDC6Zone6UPS.setShippingAccountName("OWD_DC6");
        owdDC6Zone6UPS.setResidentialAddress(true);

        owdDC6Zone2ONTRAC.setShipToContact("Danny Nickels");
        owdDC6Zone2ONTRAC.setShipToAddress1("3325 Manitou Court");
        owdDC6Zone2ONTRAC.setShipToCity("Mira Loma");
        owdDC6Zone2ONTRAC.setShipToState("CA");
        owdDC6Zone2ONTRAC.setShipToPostalCode("91752");
        owdDC6Zone2ONTRAC.setShipToCountry("US");
        owdDC6Zone2ONTRAC.setShipTerms("SHIPPER");
        owdDC6Zone2ONTRAC.setShippingAccountName("OWD_DC6");
        owdDC6Zone2ONTRAC.setResidentialAddress(true);

        owdDC6Zone2UPS.setShipToContact("Danny Nickels");
        owdDC6Zone2UPS.setShipToAddress1("3325 Manitou Court");
        owdDC6Zone2UPS.setShipToCity("Mira Loma");
        owdDC6Zone2UPS.setShipToState("CA");
        owdDC6Zone2UPS.setShipToPostalCode("91752");
        owdDC6Zone2UPS.setShipToCountry("US");
        owdDC6Zone2UPS.setShipTerms("SHIPPER");
        owdDC6Zone2UPS.setShippingAccountName("OWD_DC6");
        owdDC6Zone2UPS.setResidentialAddress(true);

        owdDC6Zone4UPS.setShipToContact("Danny Nickels");
        owdDC6Zone4UPS.setShipToAddress1("9393 e hunter Ct");
        owdDC6Zone4UPS.setShipToCity("Scottsdale");
        owdDC6Zone4UPS.setShipToState("AZ");
        owdDC6Zone4UPS.setShipToPostalCode("85362");
        owdDC6Zone4UPS.setShipToCountry("US");
        owdDC6Zone4UPS.setShipTerms("SHIPPER");
        owdDC6Zone4UPS.setShippingAccountName("OWD_DC6");
        owdDC6Zone4UPS.setResidentialAddress(true);



        owdDC6Australia.setShipToContact("Danny Nickels");
        owdDC6Australia.setShipToAddress1("89 Wallace Cct");
        owdDC6Australia.setShipToCity("Kirwan");
        owdDC6Australia.setShipToState("QLD");
        owdDC6Australia.setShipToPostalCode("4817");
        owdDC6Australia.setShipToCountry("AU");
        owdDC6Australia.setShipTerms("SHIPPER");
        owdDC6Australia.setShippingAccountName("OWD_DC6");
        owdDC6Australia.setResidentialAddress(true);

        owdDC6Canada.setShipToContact("Danny Nickels");
        owdDC6Canada.setShipToAddress1("39 Bonar Pl");
        owdDC6Canada.setShipToCity("Guelph");
        owdDC6Canada.setShipToState("ON");
        owdDC6Canada.setShipToPostalCode("N1H 8M3");
        owdDC6Canada.setShipToCountry("CA");
        owdDC6Canada.setShipTerms("SHIPPER");
        owdDC6Canada.setShippingAccountName("OWD_DC6");
        owdDC6Canada.setResidentialAddress(true);
        
        owdDC6UnitedKingdom.setShipToContact("Danny Nickels");
        owdDC6UnitedKingdom.setShipToAddress1("Waterloo Rd");
        owdDC6UnitedKingdom.setShipToCity("Southport");
        owdDC6UnitedKingdom.setShipToState("");
        owdDC6UnitedKingdom.setShipToPostalCode("PR8 2LX");
        owdDC6UnitedKingdom.setShipToCountry("GB");
        owdDC6UnitedKingdom.setShipTerms("SHIPPER");
        owdDC6UnitedKingdom.setShippingAccountName("OWD_DC6");
        owdDC6UnitedKingdom.setResidentialAddress(true);

        owdDC6Romania.setShipToContact("Danny Nickels");
        owdDC6Romania.setShipToAddress1("Strada Tignului 50");
        owdDC6Romania.setShipToCity("Timisoara");
        owdDC6Romania.setShipToState("Timis");
        owdDC6Romania.setShipToPostalCode("307200");
        owdDC6Romania.setShipToCountry("RO");
        owdDC6Romania.setShipTerms("SHIPPER");
        owdDC6Romania.setShippingAccountName("OWD_DC6");
        owdDC6Romania.setResidentialAddress(true);
        
        

    }

}
