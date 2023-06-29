package com.owd.connectship.soap.test;

import com.owd.connectship.soap.*;
import com.owd.core.OWDUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.ManifestingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.handler.HandlerResolver;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/14/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectshipAMPTest {
private final static Logger log =  LogManager.getLogger();

    static CoreXmlPort amp;

    static {
        AMPServices smp = new AMPServices();
        // Following two
        HandlerResolver myHanlderResolver = new MyHandlerResolver();
        smp.setHandlerResolver(myHanlderResolver);
        log.debug("myHandlerResolver has been set.");
        amp = smp.getAMPSoapService();

    }
    public static void main(String[] args)  throws Exception
    {
        StringBuffer rateMap = new StringBuffer();
        ServiceList serviceList = new ServiceList();
        serviceList.getService().add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND");
        serviceList.getService().add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND");

        serviceList.getService().add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP");
        serviceList.getService().add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP");

        rateMap.append(getRateMap(serviceList, "DC6", "San Francisco", "CA", "94115", "US", true, 0.5f, 6, 6, 4));


        System.out.println(rateMap.toString());
/*
        serviceList.getService().add("TANDATA_USPS.USPS.PRIORITY");
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.GND");
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.FHD");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.GND");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.EXPPLS");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.EXP");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.EPD");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.STD");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.EXPSVR");
        serviceList.getService().add("TANDATA_USPS.USPS.I_PRIORITY");
        serviceList.getService().add("TANDATA_USPS.USPS.I_FIRST");*/
      //  serviceList = new ServiceList();
      /*  Map<String,String>  serviceMap = OrderRater.getRateableServicesMapByShipper("SNY");
      //  log.debug(serviceMap);
        serviceList.getService().addAll(serviceMap.keySet());
        */
     /*   serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");   //standard
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");    //parcel
        serviceList.getService().add("TANDATA_USPS.USPS.PRIORITY");
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.GND");
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.FHD");
        serviceList.getService().add("CONNECTSHIP_UPS.UPS.GND");


        StringBuffer rateMap = new StringBuffer();

        rateMap.append(getRateMap(serviceList, "DC1", "San Francisco", "CA", "94115", "US", true, 0.5f, 6, 6, 4));

        rateMap.append(getRateMap(serviceList, "DC5", "San Francisco", "CA", "94115", "US", true, 2, 6, 6, 4));

        rateMap.append(getRateMap(serviceList, "DC5", "San Francisco", "CA", "94115", "US", true, 0.5f, 6, 6, 4));

        rateMap.append(getRateMap(serviceList, "DC1", "San Francisco", "CA", "94115", "US", true, 2, 6, 6, 4));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,2,6,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,2,6,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,2,6,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","Vancouver","BC","V5K 0A1","CA",true,2,6,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","Toronto","ON","M4B 1B4","CA",true,2,6,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","San Fran","CA","94115","US",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","Vancouver","BC","V5K 0A1","CA",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","Toronto","ON","M4B 1B4","CA",true,4,7,7,7));
        rateMap.append(getRateMap(serviceList,"DC1","San Fran","CA","94115","US",true,6,10,6,6));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,6,10,6,6));
        rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,6,10,6,6));
        rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,6,10,6,6));
        rateMap.append(getRateMap(serviceList,"DC1","Vancouver","BC","V5K 0A1","CA",true,6,10,6,6));
        rateMap.append(getRateMap(serviceList,"DC1","Toronto","ON","M4B 1B4","CA",true,6,10,6,6));*/
       // log.debug(rateMap);
    }

    public static String getRateMap(ServiceList serviceCodes, String locationCode, String city, String state, String zip, String countryCode, boolean isResidential, float weight_lbs, float h, float w, float d)  throws Exception
    {

        Map<String,Double> rateMap = new TreeMap<String,Double>();

        RateRequest rateReq = new RateRequest();

       // ServiceList serviceList = new ServiceList();
     //    serviceList.getService().addAll(serviceCodes);
        rateReq.setServices(serviceCodes);

        DataDictionary defaults = new DataDictionary();
        rateReq.setDefaults(defaults);
        rateReq.getDefaults().setShipper(OrderRater.getShipperForLocation(locationCode));
        rateReq.getDefaults().setPackaging("CUSTOM");
        rateReq.getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        defaults.setTerms("SHIPPER");
        // create consignee NameAddress

        NameAddress facilityAddress = new NameAddress();
        facilityAddress.setCompany("Test Co.");
        facilityAddress.setContact("Test Contact");
        facilityAddress.setAddress1("10 1st Ave E");
        facilityAddress.setCity("Mobridge");
        facilityAddress.setStateProvince("SD");
        facilityAddress.setPostalCode("57601");
        facilityAddress.setCountryCode("US");
        facilityAddress.setPhone("605-845-7172");
        facilityAddress.setResidential(false);

        NameAddress consignee = new NameAddress();
        consignee.setCompany("Test Co.");
        consignee.setContact("Test Contact");
        consignee.setAddress1("1234 West Dr.");
        consignee.setCity(city);
        consignee.setStateProvince(state);
        consignee.setPostalCode(zip);
        consignee.setCountryCode(countryCode);
        consignee.setPhone("605-845-7172");
        if(isResidential)
        {
            consignee.setResidential(true);
        }



        rateReq.getDefaults().setConsignee(consignee);
      //  rateReq.getDefaults().setOriginAddress(facilityAddress);



        // create a new package list
        DataDictionaryList packages = new DataDictionaryList();

        // create first package dictionary
        DataDictionary pkg = new DataDictionary();
        Weight wgt = new Weight();
        wgt.setAmount(new BigDecimal(weight_lbs));
        wgt.setUnit("lb");
        pkg.setWeight(wgt);
        Dimensions dim = new Dimensions();
        dim.setHeight(new BigDecimal(w));
        dim.setWidth(new BigDecimal(d));
        dim.setLength(new BigDecimal(h));

        pkg.setDimension(dim);
        // pkg.setService();
           pkg.setProof(true);
        //  pkg.setProofRequireSignature(true);

        pkg.setDispositionMethod("1");


        if (!(countryCode.equals("US"))) {



            pkg.setDescription("Rating item only");

            pkg.setSedMethod("0");

            pkg.setUltimateDestinationCountry(consignee.getCountrySymbol());


          //  intl.setDOCUMENTSONLY(isDocuments ? "TRUE" : "FALSE");

            Money val = new Money();
            val.setValue("10.00");
            pkg.setDeclaredValueCustoms(val);




            CommodityList contents = new CommodityList();

             Commodity item = new Commodity();

            item.setProductCode("test");

            item.setQuantity(1);

            item.setUnitWeight(wgt);

            item.setUnitValue(val);

            item.setDescription("rating item");

            item.setQuantityUnitMeasure("PC");


            item.setOriginCountry("UNITED_STATES");

            contents.getItem().add(item);

            pkg.setCommodityContents(contents);




            /*
            if(defatt.getTERMS().equals("SHIPPER"))
            {

                String country_symbol =cons.getCOUNTRYSYMBOL().toUpperCase();

                if(country_symbol.equalsIgnoreCase("PUERTO_RICO") || country_symbol.equalsIgnoreCase("PUERTO RICO")
                        || country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") || country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS"))
                {
                    ;//don't change billing method to DDU for those countries
                }
                else
                {
                    defatt.setTERMS("DDU");
                }

            }
            */

        }

        // add first package to list
        packages.getItem().add(pkg);
        rateReq.setPackages(packages);
        rateReq.setSortType("rate");

        RateResponse resp = amp.rate(rateReq);


            // get result
            RateResult result = resp.getResult();

            //  log.debug("Code = " + result.getCode());
            //   log.debug("Message = " + result.getMessage());

            // display rate and transit time for each service
            for (ProcessResult r : result.getResultData().getItem()) {
                if(true)//r.getService().getName().contains("SmartPost Parcel Select") )
                {
                    log.debug(r.getService().getName() + "  " + r.getMessage() + "  Total: " + r.getResultData().getTotal().getValue());
                    if(r.getPackageResults().getItem().get(0).getCode()==0){
                        if(rateMap.keySet().size()<9)
                        {

                            log.debug("***Fuel=" + r.getPackageResults().getItem().get(0).getResultData().getFuelSurcharge() + ":Proof=" +
                                    r.getPackageResults().getItem().get(0).getResultData().getProofFee() + ":ResFee=" +
                                    r.getPackageResults().getItem().get(0).getResultData().getResidentialDeliveryFee() + ":DAS=" +
                                    r.getPackageResults().getItem().get(0).getResultData().getExtendedAreaFee());
                            log.debug("Base:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedBase().getValue());
                            log.debug("Discount:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedDiscount().getValue());
                            log.debug("Special:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedSpecial().getValue());
                            log.debug("AppTotal:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedTotal().getValue());
                            log.debug("Total:" + r.getResultData().getTotal().getValue());


                            rateMap.put(r.getService().getName(), r.getResultData().getTotal().getAmount().doubleValue());
                        }
                        try{
                            log.debug("  Commitment: " + r.getPackageResults().getItem().get(0).getResultData().getTimeInTransit().getName());
                        }catch(Exception exx){}
                    }
                }
            }

        StringBuffer buf = new StringBuffer();
        buf.append(weight_lbs+"\t"+h+"\t"+w+"\t"+d+"\t"+city+"\t"+zip+"\t"+countryCode);
        if(rateMap.keySet().size()>0)
        {
            buf.append("\t"+rateMap.keySet().toArray()[0]+"\t"+rateMap.get(rateMap.keySet().toArray()[0]));

        }
        if(rateMap.keySet().size()>1)
        {
            buf.append("\t"+rateMap.keySet().toArray()[1]+"\t"+rateMap.get(rateMap.keySet().toArray()[1]));

        }
        if(rateMap.keySet().size()>2)
        {
            buf.append("\t"+rateMap.keySet().toArray()[2]+"\t"+rateMap.get(rateMap.keySet().toArray()[2]));

        }
        if(rateMap.keySet().size()>3)
        {
            buf.append("\t"+rateMap.keySet().toArray()[3]+"\t"+rateMap.get(rateMap.keySet().toArray()[3]));

        }
        if(rateMap.keySet().size()>4)
        {
            buf.append("\t"+rateMap.keySet().toArray()[4]+"\t"+rateMap.get(rateMap.keySet().toArray()[4]));

        }
        buf.append("\r\n");

        return buf.toString();
    }
}
