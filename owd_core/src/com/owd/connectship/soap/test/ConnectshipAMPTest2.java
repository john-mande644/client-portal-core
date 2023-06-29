package com.owd.connectship.soap.test;

import com.owd.connectship.soap.*;
import com.owd.core.csXml.OrderRater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.handler.HandlerResolver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/14/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectshipAMPTest2 {
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

     /*   ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"select distinct order_id from owd_order o join  owd_order_ship_info s on order_id=order_fkey \n" +
                "where client_fkey=483 and  o.shipped_on>='2012-12-1' and o.shipped_on<'2013-1-1' and o.shipped_weight>=1 and o.shipped_weight<50\n" +
                "and ship_country='USA'");

        StringBuffer sb = new StringBuffer();

        while(rs.next())
        {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,rs.getInt(1));

         float smartrate =  getSmartRate( "DC1", order.getShipinfo().getShipCity(), order.getShipinfo().getShipState(), order.getShipinfo().getShipZip(),
                 order.getShipinfo().getShipCountryRefNum(), true, order.getShippedWeight().floatValue()) ;

        sb.append(order.getShippedWeight().floatValue() + "\t" + order.getShipinfo().getShipZip() + "\t" + order.getShipinfo().getCarrService() + "\t" + order.getShippedCost().floatValue() + "\t" + smartrate + "\t" + (order.getShippedCost().floatValue() - smartrate) + "\r\n");
        }
        log.debug(sb.toString());*/
       // test();

        countires();
      //  purgeEndOfDay();
    }
    public static void purgeEndOfDay() throws Exception{



        java.util.List<String> shippers = new ArrayList<>();
        shippers.add("OWD_PROD_DC6");
        shippers.add("OWD_PROD_DC7");
        shippers.add("LYFT");
        shippers.add("OWD");
        shippers.add("TEST");
        shippers.add("SNY");

        for(String ship: shippers) {

            java.util.List<String> carriers = getCarrierList(ship);
            log.debug(carriers);

            for(String car: carriers) {

                log.debug("Doing " + car + " for: "+ship);
                java.util.List<String> files = getDeleteableShipFileList(car, ship);
                log.debug(files);
                log.debug(files.size());


                deleteFiles(car, ship, files);

            }

        }



    }


    public static java.util.List<String> deleteFiles(String carrier, String shipper, java.util.List<String> files){
        java.util.List<String> bad = new ArrayList<>();

        StringList sl = new StringList();
        sl.getItem().addAll(files);

        DeleteShipFilesRequest dr = new DeleteShipFilesRequest();
        dr.setShipper(shipper);
        dr.setCarrier(carrier);
        dr.setShipFiles(sl);

        try {
            DeleteShipFilesResponse response = amp.deleteShipFiles(dr);
            log.debug(response.getResult().getMessage());
            log.debug(response.getResult().getResultData());
        }catch (Exception e){
            e.printStackTrace();

        }





      return bad;
    }

    public static java.util.List<String> getDeleteableShipFileList(String carrier, String shipper){
        java.util.List<String> files = new ArrayList<>();

        ListShipFilesRequest sfr = new ListShipFilesRequest();
        sfr.setShipper(shipper);
        sfr.setCarrier(carrier);
        sfr.setDeletable(true);


        try{
            ListShipFilesResponse response = amp.listShipFiles(sfr);
            for(ShipFile c: response.getResult().getResultData().getItem()){

                for(DictionaryItem item: c.getAttributes().getItem()){
                    if(item.getKey().equals("SHIPDATE")){
                        if(!item.getValue().toString().startsWith("2018-11")){

                            if(!item.getValue().toString().startsWith("2018-10")) {
                                files.add(c.getSymbol());
                            }

                        }

                    }
                }


            }



        }catch (Exception e){
            e.printStackTrace();
        }



        return files;
    }

    public static java.util.List<String> getCarrierList(String shipper){
      ListCarriersRequest cr = new ListCarriersRequest();

        cr.setShipper(shipper);
        java.util.List<String> theCarriers = new ArrayList<>();

        try {
            ListCarriersResponse carriers = amp.listCarriers(cr);
           for(Identity c: carriers.getResult().getResultData().getItem()){
               theCarriers.add(c.getSymbol());
           }

        }catch (Exception e){

        }

        return theCarriers;

    }
    public static void countires() throws Exception{
        ListCountriesResponse c = amp.listCountries(new ListCountriesRequest());

        for(Identity country:c.getResult().getResultData().getItem()){
            log.debug(country.getName() + "  :  " + country.getSymbol());
        }
    }
    public static void test() throws Exception{

        ListShippersResponse shippers = amp.listShippers(new ListShippersRequest());


        for(Identity shipper:shippers.getResult().getResultData().getItem())
        {
            log.debug(shipper.getName()+":"+shipper.getSymbol());
        }



        ListServicesRequest servReq = new ListServicesRequest();
        servReq.setShipper("SNY");

        ListServicesResponse services = amp.listServices(servReq);
        ServiceList serviceList = new ServiceList();
        for(Identity service:services.getResult().getResultData().getItem())
        {
           // log.debug(service.getName()+":"+service.getSymbol());
            //  if(service.getName().contains("SmartPost Parcel Select"))
            //  {
          //  serviceList.getService().add(service.getSymbol());
            //  }


        }
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

    //    serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");   //standard
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");    //parcel

        StringBuffer rateMap = new StringBuffer();

        rateMap.append(getRateMap(serviceList, "DC1", "San Francisco", "CA", "94115", "US", true, 1.8f, 10, 8, 4));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,1.8f, 10, 8, 4));
      //  rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,2.1f,10,6,4));
      //  rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,2.1f,10,6,4));
        rateMap.append(getRateMap(serviceList,"DC1","San Fran","CA","94115","US",true,2.2f,11,7.5f,5));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,2.2f,11,7.5f,5));
      //  rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,4.1f,12,10,4));
      //  rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,4.1f,12,10,4));
        rateMap.append(getRateMap(serviceList,"DC1","San Fran","CA","94115","US",true,2.2f*100f,11,7.5f,5));
        rateMap.append(getRateMap(serviceList,"DC1","New York","NY","10001","US",true,2.2f*100f,11,7.5f,5));
    //    rateMap.append(getRateMap(serviceList,"DC1","Dallas","TX","75229","US",true,6.1f,18,10,4));
    //    rateMap.append(getRateMap(serviceList,"DC1","Seattle","WA","98134","US",true,6.1f,18,10,4));
        log.debug(rateMap);
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
                  //  log.debug(r.getService().getName() + "  " + r.getMessage()+"  Total: " + r.getResultData().getTotal().getValue());
                    if(r.getPackageResults().getItem().get(0).getCode()==0){
                        if(rateMap.keySet().size()<9)
                        {
                            log.debug("ZONE:"+r.getPackageResults().getItem().get(0).getResultData().getZone());
                            log.debug("***Fuel="+r.getPackageResults().getItem().get(0).getResultData().getFuelSurcharge()+":Proof="+
                                    r.getPackageResults().getItem().get(0).getResultData().getProofFee()+":ResFee="+
                                    r.getPackageResults().getItem().get(0).getResultData().getResidentialDeliveryFee()+":DAS="+
                                    r.getPackageResults().getItem().get(0).getResultData().getExtendedAreaFee());
                            log.debug("Base:"+r.getPackageResults().getItem().get(0).getResultData().getApportionedBase().getValue());
                            log.debug("Discount:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedDiscount().getValue());
                            log.debug("Special:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedSpecial().getValue());
                            log.debug("AppTotal:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedTotal().getValue());
                            log.debug("Total:"+r.getResultData().getTotal().getValue());


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
        buf.append("\r\n");

        return buf.toString();
    }


    public static float getSmartRate( String locationCode, String city, String state, String zip, String countryCode, boolean isResidential, float weight_lbs)  throws Exception
    {

        Map<String,Double> rateMap = new TreeMap<String,Double>();

        RateRequest rateReq = new RateRequest();

        ServiceList serviceList = new ServiceList();
        serviceList.getService().add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");    //parcel

        //
        //    serviceList.getService().addAll(serviceCodes);
        rateReq.setServices(serviceList);

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
        log.debug(city);
        consignee.setStateProvince(state);
        consignee.setPostalCode(zip);
        consignee.setCountryCode("US");
        consignee.setPhone("605-845-7172");
        if(isResidential)
        {
            consignee.setResidential(true);
        }



        rateReq.getDefaults().setConsignee(consignee);



        // create a new package list
        DataDictionaryList packages = new DataDictionaryList();

        // create first package dictionary
        DataDictionary pkg = new DataDictionary();
        Weight wgt = new Weight();
        wgt.setAmount(new BigDecimal(weight_lbs));
        wgt.setUnit("lb");
        pkg.setWeight(wgt);
        log.debug(weight_lbs);
      /*  Dimensions dim = new Dimensions();
        dim.setHeight(new BigDecimal(w));
        dim.setWidth(new BigDecimal(d));
        dim.setLength(new BigDecimal(h));

        pkg.setDimension(dim);
       */ // pkg.setService();
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
                //  log.debug(r.getService().getName() + "  " + r.getMessage()+"  Total: " + r.getResultData().getTotal().getValue());
                if(r.getPackageResults().getItem().get(0).getCode()==0){
                    if(rateMap.keySet().size()<9)
                    {

                        log.debug("***Fuel="+r.getPackageResults().getItem().get(0).getResultData().getFuelSurcharge()+":Proof="+
                                r.getPackageResults().getItem().get(0).getResultData().getProofFee()+":ResFee="+
                                r.getPackageResults().getItem().get(0).getResultData().getResidentialDeliveryFee()+":DAS="+
                                r.getPackageResults().getItem().get(0).getResultData().getExtendedAreaFee());
                        log.debug("Base:"+r.getPackageResults().getItem().get(0).getResultData().getApportionedBase().getValue());
                        log.debug("Discount:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedDiscount().getValue());
                        log.debug("Special:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedSpecial().getValue());
                        log.debug("AppTotal:" + r.getPackageResults().getItem().get(0).getResultData().getApportionedTotal().getValue());
                        log.debug("Total:"+r.getResultData().getTotal().getValue());

                        return r.getResultData().getTotal().getAmount().floatValue() ;

                    }
                }else{
                    throw new Exception("Not rateable:"+r.getPackageResults().getItem().get(0).getMessage()) ;
                }
            }
        }

        return 0.00f;
    }

}
