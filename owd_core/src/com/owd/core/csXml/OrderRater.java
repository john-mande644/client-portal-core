package com.owd.core.csXml;


import com.owd.connectship.soap.*;
import com.owd.connectship.soap.test.MyHandlerResolver;
import com.owd.core.IDynamicShippingCart;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.ChoiceListManager;
import com.owd.core.business.PackageRate;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.handler.HandlerResolver;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class OrderRater {
private final static Logger log =  LogManager.getLogger();


    public List<PackageRate> theResponse= new ArrayList<PackageRate>();
    private boolean signatureRequired = false;
    private boolean calculateKitWeights = false;

    private OrderStatus order;

    static private Map<String,String> shipperRaterMap = new TreeMap<String, String>();
    static private Map<String,String> locationRaterMap = new TreeMap<String, String>();

    private List<String> scsList = null;

    public double insuredAmount = 0.00;
    private float forceWeight = (float) 0.0;
    private double fedexDiscountRate = 0.00;
    boolean isDocuments = true;
    double calculatedPackageWeight = 0.00;

    static CoreXmlPort amp;

    public double getFedexDiscountRate() {
        return fedexDiscountRate;
    }

    public void setFedexDiscountRate(double fedexDiscountRate) {
        this.fedexDiscountRate = fedexDiscountRate;
    }

    private String clientId = null;
    OwdClient client = null;
    /* static initializer */

    static {

        AMPServices smp = new AMPServices();
        // Following two
        HandlerResolver myHandlerResolver = new MyHandlerResolver();

        //uncomment next line to echo SOAP requests/responses to console
        //   smp.setHandlerResolver(myHandlerResolver);
        //   log.debug("myHandlerResolver has been set.");

        amp = smp.getAMPSoapService();

        updateServiceMaps();

        shipperRaterMap.put("DC1","OWD");
        shipperRaterMap.put("DC6","OWD_PROD_DC6");
        shipperRaterMap.put("DC7","OWD_PROD_DC7");


        locationRaterMap.put("OWD","DC1");
        locationRaterMap.put("OWD_PROD_DC6","DC6");
        locationRaterMap.put("OWD_PROD_DC7","DC7");

    }

    public static Map getShipperMap()
    {
        return shipperRaterMap;
    }

    public static String getShipperForLocation(String location)
    {
        return (String) shipperRaterMap.get(location);
    }

    public static String getLocationForShipper(String shipper)
    {
        return (String) locationRaterMap.get(shipper);
    }


    public OrderRater() {


    }

    public double getCalculatedPackageWeight() {
        return calculatedPackageWeight;
    }

    public void setCalculatedPackageWeight(double calculatedPackageWeight) {
        this.calculatedPackageWeight = calculatedPackageWeight;
    }

    public synchronized void setScsList(List<String> scsList)  throws Exception
    {
        if(scsList == null)
        {
            this.scsList = null;
        }

        this.scsList=new ArrayList<String>();


        for (String code: (List<String>) scsList != null ? (List<String>) scsList : new ArrayList<String>())
        {
            String newCode = getNewServiceCode(code);
              if((!(this.scsList.contains(newCode))))
               {
            this.scsList.add(newCode);

               }
        }

    }

    public OrderRater(OrderStatus status) throws Exception {

        if (status == null)

            throw new Exception("Cannot create rater - order object not available");

        order = status;

    }

    public OrderRater(Order anOrder) throws Exception {

        if (anOrder == null)
            throw new Exception("Cannot create rater - order object not available");

        OrderStatus status = new OrderStatus();
        status.client_id = anOrder.clientID;
        status.items = anOrder.skuList;
        status.billAddress = anOrder.getBillingAddress();
        status.billContact = anOrder.getBillingContact();
        status.shipping = anOrder.getShippingInfo();
        status.shipperAddress = anOrder.getShippingInfo().shipAddress;
        status.shipperContact = anOrder.getShippingInfo().shipContact;
        status.shipping.ss_saturday=anOrder.getShippingInfo().ss_saturday;
        setClientId(anOrder.getClientID());
        order = status;
        setCalculateKitWeights(true);      //it's a pre-save order
    }

    public boolean isCalculateKitWeights() {
        return calculateKitWeights;
    }

    public void setCalculateKitWeights(boolean calculateKitWeights) {
        this.calculateKitWeights = calculateKitWeights;
    }

    public void rate(float forcedWeight,String theLocation) throws Exception {

        forceWeight = forcedWeight;

        rate(theLocation);

    }

    public void rate(List<String> codeList) throws Exception {

        setScsList(codeList);

        doRate();


    }

    public void rate(String theLocation) throws Exception {

        setScsList(getServiceCodes(theLocation));

        log.debug(getServiceCodes(theLocation));

        doRate(OrderRater.getShipperForLocation(theLocation));


    }


    private float forcePackagingWeight = 0.00f;
    private boolean forcePackageZeroWeight = false;

    public boolean isForcePackageZeroWeight() {
        return forcePackageZeroWeight;
    }

    public void setForcePackageZeroWeight(boolean forcePackageZeroWeight) {
        this.forcePackageZeroWeight = forcePackageZeroWeight;
    }

    public float getForcePackagingWeight()
    {
        return forcePackagingWeight;
    }

    public void setForcePackagingWeight(float forcePWeight)
    {
        forcePackagingWeight = forcePWeight;
    }

    private void doRate() throws Exception
    {
        doRate(getDefaultShipper());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;

        try{
            OwdClient tclient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.parseInt(this.clientId));
             if(tclient.getClientId()>0)
             {
                 client = tclient;
             }
        }   catch(Exception ex)
        {

        }
    }

    private void doRate(String shipper) throws Exception {


        log.debug("rating for " + shipper);
        log.debug("dorate");

        RateRequest rateReq = new RateRequest();

        ServiceList standardServiceList = new ServiceList();
        ServiceList proofRequiredServiceList = new ServiceList();
        ServiceList proofNoSigRequiredServiceList = new ServiceList();
        ServiceList dduServiceList = new ServiceList();


        // standardServiceList.getService().addAll(scsList);
        //  rateReq.setServices(standardServiceList);

        DataDictionary defaults = new DataDictionary();
        rateReq.setDefaults(defaults);
        rateReq.getDefaults().setShipper(shipper);
        rateReq.getDefaults().setPackaging("CUSTOM");

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        Calendar now = GregorianCalendar.getInstance();


        while (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            now.add(Calendar.DATE, 1);

        }


        rateReq.getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)now));

        defaults.setTerms("SHIPPER");
        // create consignee NameAddress

     /*   NameAddress facilityAddress = new NameAddress();
        facilityAddress.setCompany("Test Co.");
        facilityAddress.setContact("Test Contact");
        facilityAddress.setAddress1("10 1st Ave E");
        facilityAddress.setCity("Mobridge");
        facilityAddress.setStateProvince("SD");
        facilityAddress.setPostalCode("57601");
        facilityAddress.setCountryCode("US");
        facilityAddress.setPhone("605-845-7172");
        facilityAddress.setResidential(false);
*/
        NameAddress consignee = new NameAddress();
        consignee.setCompany("Test Co.");
        consignee.setContact("Test Contact");
        consignee.setAddress1(order.shipping.shipAddress.address_one);
        consignee.setCity(order.shipping.shipAddress.city);
        consignee.setStateProvince(order.shipping.shipAddress.state.length() > 0 ? order.shipping.shipAddress.state : ".");
        consignee.setPostalCode(order.shipping.shipAddress.zip);



        log.debug("dorate3");
        if (order.shipping.shipAddress.country.toUpperCase().contains("CHINA")) {
            consignee.setCountrySymbol("CHINA");
        } else {
            //log.debug("getting country for "+order.shipping.shipAddress.country);
            consignee.setCountrySymbol(ChoiceListManager.getChoiceListManager().getList("Country ID").getRefForValue(order.shipping.shipAddress.country));
            //getCountryCode(order.shipping.shipAddress.country));//order.shipperAddress.country);
            //log.debug("got country symbol "+cons.getCOUNTRYSYMBOL());
        }

        if(consignee.getCountrySymbol()==null)
        {
            consignee.setCountrySymbol(order.shipping.shipAddress.country);
        }

        if (order.shipping.shipAddress.company_name.trim().length() < 2) {
            consignee.setResidential(true);
            ////log.debug("residential rate");

        } else {
            consignee.setResidential(false);
            ////log.debug("commercial rate");
        }



        consignee.setPhone("605-845-7172");

        rateReq.getDefaults().setConsignee(consignee);
        //   rateReq.getDefaults().setOriginAddress(facilityAddress);



        // create a new package list
        DataDictionaryList packages = new DataDictionaryList();

        // create first package dictionary
        DataDictionary pkg = new DataDictionary();
        Weight wgt = new Weight();
        float pkgweight = getPackageWeight();
        setCalculatedPackageWeight(pkgweight);

        wgt.setAmount(new BigDecimal(pkgweight));
        wgt.setUnit("lb");
        pkg.setWeight(wgt);

        //no dimensional support yet
        /*
        Dimensions dim = new Dimensions();
        dim.setHeight(new BigDecimal(w));
        dim.setWidth(new BigDecimal(d));
        dim.setLength(new BigDecimal(h));

        pkg.setDimension(dim);
        */
        // pkg.setService();
        pkg.setProof(true);
        if(signatureRequired) {
              pkg.setProofRequireSignature(true);
        }
        pkg.setDispositionMethod("1");


        if (!(consignee.getCountrySymbol().equals("UNITED_STATES"))) {



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

        if("1".equals(order.shipping.ss_saturday))
        {
            rateReq.getDefaults().setSaturdayDelivery(true);
        }


        log.debug("Rater got past intl");
        if(insuredAmount>0)
        {
            Money insuredValue = new Money();
            insuredValue.setAmount(new BigDecimal(insuredAmount));
            rateReq.getDefaults().setDeclaredValueAmount(insuredValue);
        }

        log.debug("Initial Weight Lbs: " + wgt.getAmount());
        double ratedPackages = 1.00;
        if(wgt.getAmount().doubleValue()>40.00)
        {
            double totalWeight = wgt.getAmount().doubleValue();
            while(totalWeight>40.00)
            {
                ratedPackages++;
                totalWeight -= 40.00;
            }
            wgt.setAmount(BigDecimal.valueOf(40.00));
        }
        log.debug("Final Weight Lbs: " + wgt.getAmount() + " for " + ratedPackages + " package(s)");



        // add first package to list
        packages.getItem().add(pkg);
        rateReq.setPackages(packages);
        rateReq.setSortType("rate");


        for(String svcCode:scsList)
        {
            log.debug(svcCode);
            if(svcCode.equals("TANDATA_FEDEXFSMS.FEDEX.SP_PS"))
            {
                proofNoSigRequiredServiceList.getService().add(svcCode);

            }  else  if (!(consignee.getCountrySymbol().equals("UNITED_STATES")) && (svcCode.startsWith("CONNECTSHIP_UPS") || svcCode.startsWith("TANDATA_FEDEX")|| svcCode.startsWith("CONNECTSHIP_DHL"))) {
                dduServiceList.getService().add(svcCode);

            }   else
            {
                standardServiceList.getService().add(svcCode);
            }
        }
        System.out.println("SigRequired:   "+signatureRequired);
        if(signatureRequired){
            System.out.println("SigRequired:   "+signatureRequired);
            pkg.setProof(true);
            pkg.setProofRequireSignature(true);
        }else{
            pkg.setProofRequireSignature(false);
            pkg.setProof(false);
        }

        List<PackageRate> rates = getRatedPackages(rateReq,standardServiceList,ratedPackages);
        pkg.setProof(true);
        if(signatureRequired){
            pkg.setProofRequireSignature(true);
        }else{
            pkg.setProofRequireSignature(false);
        }

        rates.addAll(getRatedPackages(rateReq, proofNoSigRequiredServiceList, ratedPackages));
        pkg.setProofRequireSignature(true);
        rates.addAll(getRatedPackages(rateReq, proofRequiredServiceList, ratedPackages));
        pkg.setProof(false);
        pkg.setProofRequireSignature(false);
        rateReq.getDefaults().setTerms("DDU");
        pkg.setDispositionMethod("0");
        rates.addAll(getRatedPackages(rateReq, dduServiceList, ratedPackages));
        //add errors

        sortByRate(rates);

        theResponse = rates;

    }

    List<PackageRate> getRatedPackages(RateRequest req, ServiceList serviceCodes, double ratedPackages) throws Exception
    {

        List<PackageRate> rates = new ArrayList<PackageRate>();
        log.debug(serviceCodes.getService());
        req.setServices(serviceCodes);
        if(serviceCodes.getService().size()<1)
        {
            return rates;
        }
        else
        {
            // get result
            log.debug("ZIP:" + req.getDefaults().getConsignee().getPostalCode());
            RateResult result = amp.rate(req).getResult();

              log.debug("Code = " + result.getCode());
               log.debug("Message = " + result.getMessage());
            log.debug(result.getResultData().getItem().size());
            // display rate and transit time for each service
            for (ProcessResult r : result.getResultData().getItem()) {
                log.debug(r.getCode() + ":" + r.getMessage());
                try{
                log.debug(r.getService().getName() + "  " + r.getMessage() + "  Total: " + r.getResultData().getTotal().getValue());
                if(r.getPackageResults().getItem().get(0).getCode()==0){

                    PackageRate pr = new PackageRate();

                    if(r.getResultData().getTotal().getAmount().doubleValue()>0.00)
                    {
                    DataDictionary  rd = r.getPackageResults().getItem().get(0).getResultData();
                    pr.setFuelSurcharge(rd.getFuelSurcharge()==null?0.00:rd.getFuelSurcharge().getAmount().doubleValue());
                    pr.setResidentialCharge(rd.getResidentialDeliveryFee()==null?0.00:rd.getResidentialDeliveryFee().getAmount().doubleValue());
                    pr.setBaseCharge(rd.getApportionedBase().getAmount().doubleValue());
                        pr.setOriginalDiscount(rd.getApportionedDiscount() == null ? 0.00 : rd.getApportionedDiscount().getAmount().doubleValue());
                    pr.setOtherCharge((client==null?0.10d:client.getUspsPriorityExpressPackFee().doubleValue())+rd.getApportionedSpecial().getAmount().doubleValue()-pr.getFuelSurcharge()-pr.getResidentialCharge());
                        pr.setFinalDiscount(0.00);
                    if(pr.getFinalDiscount()<0.00)
                    {
                        pr.setFinalDiscount(0.00);
                    }
                    pr.setFinalRate((pr.getBaseCharge()+pr.getFuelSurcharge()+pr.getResidentialCharge()-pr.getFinalDiscount())*ratedPackages);
                    pr.setOriginalRate(rd.getApportionedTotal().getAmount().doubleValue());
                    pr.setMethodName(r.getService().getName().replaceAll("\\(","").replaceAll("\\)",""));
                    pr.setMethodCode(r.getService().getSymbol());
                    }else{
                        pr.setErrorCode(-1);
                        pr.setErrorMessage("Unable to rate address for this service");
                        pr.setMethodName(r.getService().getName());
                        pr.setMethodCode(r.getService().getSymbol());
                    }
                    rates.add(pr);
                    log.debug(pr.toString());
                    try{
                      //  log.debug("  Commitment: " + r.getPackageResults().getItem().get(0).getResultData().getTimeInTransit().getName());
                    }catch(Exception exx){}
                }else
                {
                    PackageRate pr = new PackageRate();

                    pr.setErrorCode(r.getPackageResults().getItem().get(0).getCode());
                    pr.setErrorMessage(r.getPackageResults().getItem().get(0).getMessage());
                    pr.setMethodName(r.getService().getName());
                    pr.setMethodCode(r.getService().getSymbol());
                    rates.add(pr);

                }

            }catch(Exception exx)
                {
                    exx.printStackTrace();
                }
            }
            return rates;
        }

    }

    private float getPackageWeight() throws Exception {
        float pkgweight=0.0000f;

        if (order.order_id.equals("")) {
            for (int i = 0; i < order.items.size(); i++) {


                if (((float) ((LineItem) order.items.get(i)).quantity_request) > 0) {

                    Inventory inv = ((LineItem) order.items.get(i)).getInventory();


                    if (inv.is_documents == 0) isDocuments = false;


                    float unitwgt = calculateKitWeights?Inventory.getKittedWeight(((LineItem) order.items.get(i)).inventory_fkey):Inventory.getWeight(((LineItem) order.items.get(i)).inventory_fkey);

                    float itmwgt = OWDUtilities.roundFloat(((float) ((LineItem) order.items.get(i)).quantity_request) * unitwgt, 2);


                    pkgweight += itmwgt;


                }


            }
        } else {
            for (int i = 0; i < order.items.size(); i++) {


                if (((float) ((LineItem) order.items.get(i)).quantity_actual) > 0) {


                    Inventory inv = ((LineItem) order.items.get(i)).getInventory();

                    if (inv.is_documents == 0) isDocuments = false;


                    float unitwgt = Inventory.getWeight(((LineItem) order.items.get(i)).inventory_fkey);

                    float itmwgt = OWDUtilities.roundFloat(((float) ((LineItem) order.items.get(i)).quantity_actual) * unitwgt, 2);


                    pkgweight += itmwgt;


                }


            }
        }

        log.debug("rater got past items");
        //force zero weights to be 0.20 lbs
        if (pkgweight <= 0.0f) pkgweight = 0.2f;


        //heuristics for packaging/dunnage weight


        //estimate 20% more for packaging
        float packagingLbs = pkgweight * 0.2f;

        //cap packaging addon at 1 lb
        if(packagingLbs>1.00) { packagingLbs = 1.00f;}

        log.debug("checking Packaging weight " + forcePackagingWeight);

        if(forcePackagingWeight>=0.01f||forcePackageZeroWeight)
        {
            log.debug("Setting 0");
            packagingLbs = forcePackagingWeight;
        }
        log.debug("final packaginglbs=" + packagingLbs);
        //don't add packaging if it kicks the total weight over a max package weight boundary
        if((pkgweight+packagingLbs)>40.00 &&  (pkgweight % 40.00) > ((pkgweight+packagingLbs) % 40.00))
        {
            //do not add packaging weight because it would kick the total weight over the 40 lb threshold
        } else
        {
            pkgweight += packagingLbs;
        }
        return pkgweight;
    }





    public String selectBestWay(List shipMethods, String fallbackMethodName,boolean signatureRequired) throws Exception {
        try
        {
            return selectBestWay(shipMethods, 0.00, 0.00,signatureRequired);
        } catch (Exception ex)
        {
            return fallbackMethodName;
        }
    }
    public String selectBestWay(List shipMethods)throws Exception{
        return selectBestWay(shipMethods, 0.00, 0.00,false);
    }
    public String selectBestWay(List shipMethods,boolean signatureRequired) throws Exception {
        return selectBestWay(shipMethods, 0.00, 0.00,signatureRequired);
    }

    public String selectBestWay(List shipMethods, String preferredShipMethodCode, double minimumAdvantageToUpgradeShipping,boolean sigRequired) throws Exception {
        //shipMethods should be list of tandata-recognized ship method codes
        setScsList(shipMethods);
        System.out.println("The sig:     "+ sigRequired);
        signatureRequired = sigRequired;

        doRate();

        int ratedServices = theResponse.size();


        Double preferredMethodCost = null;
        Double leastExpensiveCost = null;
        String leastExpensiveMethodName = null;
        String preferredMethodName = null;

        for (PackageRate currShipment:theResponse) {
            //starting with least expensive method, find least expensive method and the cost of the preferred method.
            //if valid rate for preferred method is not found      , use least expensive method
            //if no valid methods found for list, return error



            if (currShipment.getErrorCode()==0) {

                String name = currShipment.getMethodName().replaceAll("\\ \\(Book\\)", "").replaceAll("\\ \\(US\\)","").replaceAll("[()]", "");
                if(!(order.shipperAddress.isPOAPO()) ||( (name.contains("USPS") || name.toUpperCase().contains("SMARTPOST"))))
                {


                    if(currShipment.getMethodCode().equals(preferredShipMethodCode))
                    {
                        if(leastExpensiveMethodName != null)
                        {
                            preferredMethodCost = currShipment.getFinalRate();
                            preferredMethodName = name;
                            if(leastExpensiveCost==null)
                            {
                                leastExpensiveCost = preferredMethodCost;
                                leastExpensiveMethodName = name;
                            }
                        }   else
                        {
                            return name;
                        }
                    }else
                    {
                        if(leastExpensiveMethodName == null)
                        {
                            leastExpensiveMethodName = name;
                            leastExpensiveCost = currShipment.getFinalRate();
                        }
                    }


                }

            }else {
                // //log.debug("Order Rater rate error: " + currShipment.getERROR().getERRORDESCRIPTION());

            }

        }

        //  log.debug("min="+leastExpensiveCost);
        if(preferredMethodCost==null && leastExpensiveCost == null)
        {
            //could not get valid price
            throw new Exception("Best Way service returned no valid shipping methods for requested shipment");

        }else if((preferredMethodCost!=null) &&((preferredMethodCost-leastExpensiveCost) < (minimumAdvantageToUpgradeShipping)))
        {
            return preferredMethodName;
        }else
        {
            return leastExpensiveMethodName;
        }

    }

    public String selectBestWay(List shipMethods, double USPSsurcharge, double NonUSPSsurcharge,boolean sigRequired) throws Exception {
        //shipMethods should be list of tandata-recognized ship method codes
        log.debug("adding scslist:" + shipMethods);

        setScsList(shipMethods);
        signatureRequired = sigRequired;

        doRate();

        for (PackageRate currShipment:theResponse) {

            if (currShipment.getMethodName().toUpperCase().contains("FEDEX") && getFedexDiscountRate()>0.00 && getFedexDiscountRate()<1.00) {
                currShipment.setFinalRate(OWDUtilities.roundDouble(currShipment.getFinalRate()-(currShipment.getFinalRate()*getFedexDiscountRate()),2));

            }

            if (currShipment.getMethodName().contains("USPS")) {
                currShipment.setOtherCharge(currShipment.getOtherCharge()+USPSsurcharge);

                currShipment.setFinalRate(currShipment.getFinalRate() + USPSsurcharge);
            } else {
                currShipment.setOtherCharge(currShipment.getOtherCharge()+NonUSPSsurcharge);

                currShipment.setFinalRate(currShipment.getFinalRate() + NonUSPSsurcharge);
            }

        }

        sortByRate(theResponse);

        for (PackageRate currShipment:theResponse) {

            if (currShipment.getErrorCode()==0) {
                String name = currShipment.getMethodName();

                name = name.replaceAll("\\ \\(Book\\)", "").replaceAll("\\ \\(US\\)","").replaceAll("[()]", "");
                //All USPS methods are OK!
                if (name.contains("USPS") || name.toUpperCase().contains("SMARTPOST")) {
                    //  //log.debug("found USPS method "+name);
                    return name;
                } else {
                    //if not USPS, don't allow PO or APO addresses
                    //  //log.debug("found non-USPS method "+name);
                    if (!(order.shipperAddress.isPOAPO())) {
                        //  //log.debug("returning non-USPS method "+name);
                        return name;

                    } else {
                        //    //log.debug("found PO/APO non-USPS method "+name);
                    }
                }
            } else {
                // //log.debug("Order Rater rate error: " + currShipment.getERROR().getERRORDESCRIPTION());

            }
        }

        throw new Exception("Best Way service returned no valid shipping methods for requested shipment");
    }

    public List<ShipOption> getShippingOptionListMethodsAndRates(String clientID, float surcharge,String theLocation) throws Exception {
        //shipMethods should be list of tandata-recognized ship method codes

        List<ShipOption> options = new ArrayList<ShipOption>();

        setScsList(getServiceCodes(theLocation));


        doRate();

        for (PackageRate currShipment:theResponse) {

            if (currShipment.getErrorCode()==0) {
                String name = currShipment.getMethodName();
                if(name.indexOf("(")>0)
                {
                    name = name.substring(0,name.indexOf("(")).trim();
                }
                //All USPS methods are OK!
                if (name.indexOf("USPS") >= 0 || name.toUpperCase().contains("SMARTPOST")) {
                    ShipOption option = new ShipOption();
                    option.setMethodName(name);
                    option.setShipCost((currShipment.getFinalRate()+surcharge));
                    options.add(option);
                } else {
                    //if not USPS, don't allow PO or APO addresses
                    if (!(order.shipperAddress.isPOAPO()))
                    {
                        ShipOption option = new ShipOption();
                        option.setMethodName(name);
                        option.setShipCost((currShipment.getFinalRate()+surcharge));
                        options.add(option);
                    }
                }
            } else {
                //log.debug("Order Rater rate error: " + currShipment.getERROR().getERRORDESCRIPTION());

            }
        }
        if(options.size()<1)
            throw new Exception("There are no valid shipping methods for requested shipment. Please check your shipping address for correctness.");
        return options;
    }


    public class ShipOption
    {
        String methodName;
        double shipCost;
        double orderTotal;

        public double getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(double orderTotal) {
            this.orderTotal = orderTotal;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public double getShipCost() {
            return shipCost;
        }

        public void setShipCost(double shipCost) {
            this.shipCost = shipCost;
        }
    }

    public void setCartMethodsAndRatesWithSurcharge(IDynamicShippingCart cart, float surcharge) throws Exception {
        //shipMethods should be list of tandata-recognized ship method codes


        setScsList(cart.getMethods());


        doRate();

        for (PackageRate currShipment:theResponse) {

            if (currShipment.getErrorCode()==0) {
                String name = currShipment.getMethodName();
                if(name.indexOf("(")>0)
                {
                    name = name.substring(0,name.indexOf("(")).trim();
                }
                //All USPS methods are OK!
                if (name.contains("USPS") || name.toUpperCase().contains("SMARTPOST")) {
                    cart.addShipping(name, getOldServiceCode(currShipment.getMethodCode()),""+(currShipment.getFinalRate()+surcharge));
                } else {
                    //if not USPS, don't allow PO or APO addresses
                    if (!(order.shipperAddress.isPOAPO()))
                    {
                        cart.addShipping(name, getOldServiceCode(currShipment.getMethodCode()),""+(currShipment.getFinalRate()+surcharge));
                    }
                }
            } else {
                //log.debug("Order Rater rate error: " + currShipment.getERROR().getERRORDESCRIPTION());

            }
        }
        if(cart.getShipCosts().size()<1)
            throw new Exception("There are no valid shipping methods for requested shipment. Please check your shipping address for correctness.");
    }



    public void setModelBottleCartMethodsAndRates(IDynamicShippingCart cart) throws Exception {
        //shipMethods should be list of tandata-recognized ship method codes

        try
        {
            log.debug("miab cart rating");
            setScsList(cart.getMethods());

            log.debug("got method list:" + scsList.size());
            Vector skus = cart.getOrder().skuList;
            int totalUnits = 0;
            Iterator itsku = skus.iterator();
            while(itsku.hasNext())
            {
                totalUnits += ((LineItem)itsku.next()).quantity_request;
            }
            if(totalUnits==0)totalUnits = 1;

            doRate();

            double firstRate = 0.00;
            for(PackageRate currShipment:theResponse)
            {
                if (currShipment.getErrorCode()==0) {
                    String name = currShipment.getMethodName();
                    if (name.indexOf("(") > 0) {
                        name = name.substring(0, name.indexOf("(")).trim();
                    }
                    //All USPS methods are OK!
                    if (cart.getOrder().getShippingAddress().isUS()) {
                        cart.addShipping(name, getOldServiceCode(currShipment.getMethodCode()), "" + (currShipment.getFinalRate()  - firstRate + 1.10f+(1.00*(totalUnits-1))));
                        if (firstRate == 0.00)
                            firstRate = currShipment.getFinalRate() + 1.10f+(1.00*(totalUnits-1));
                    } else {
                        //if not USPS, don't allow PO or APO addresses

                        cart.addShipping(name, getOldServiceCode(currShipment.getMethodCode()), "" + (currShipment.getFinalRate() - firstRate + 3.00f));
                        if (firstRate == 0.00)
                            firstRate = currShipment.getFinalRate()  + 3.00f;

                    }
                } else {
                    log.debug("Order Rater rate error: " + currShipment.getErrorMessage());

                }
            }
            log.debug("at end of rating miab, shipcosts found=" + cart.getShipCosts());
            if (cart.getShipCosts().size() < 1)
                throw new Exception("There are no valid shipping methods for requested shipment. Please check your shipping address for correctness.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }            catch(Throwable t)
        {
            t.printStackTrace();
        }
    }


    public void setOrderBestRateAndMethod(Order order,List<String> methodCodes) throws Exception {
        //shipMethods pulled from valid client methods

        setScsList(methodCodes);


        doRate();

        order.total_shipping_cost = 0.00f;

        double firstRate = 0.00;
        for(int i=0;i<theResponse.size();i++) {

            PackageRate currShipment = theResponse.get(i);
            if (currShipment.getErrorCode()==0) {
                String name = currShipment.getMethodName();
                //All USPS methods are OK!
                if (name.indexOf("USPS") >= 0 || name.toUpperCase().indexOf("SMARTPOST") >= 0) {
                    order.getShippingInfo().setShipOptions(name, "Prepaid", "");
                    order.total_shipping_cost = (float) currShipment.getFinalRate();

                } else {
                    //if not USPS, don't allow PO or APO addresses
                    if (!(order.getShippingAddress().isPOAPO())) {

                        order.getShippingInfo().setShipOptions(name, "Prepaid", "");
                        order.total_shipping_cost =  (float) currShipment.getFinalRate();
                    }
                }
            } else {
                // //log.debug("Order Rater rate error: " + currShipment.getERROR().getERRORDESCRIPTION());

            }
            if (order.total_shipping_cost > 0) i = theResponse.size();
        }
        if (order.total_shipping_cost == 0)
            throw new Exception("There are no valid shipping methods for requested shipment. Please check your shipping address for correctness.");
    }


    public static void checkAddressForMethod(OwdOrderShipInfo info){
        //skip methods osm ltl
        try {
            OrderStatus order = new OrderStatus("14500297");
            OrderRater rater = new OrderRater(order);
            List<String> codes = new ArrayList<String>();
            codes.add(order.shipping.carr_service_ref_num);
            rater.rate(codes );
            List<PackageRate> resp1 = rater.theResponse;
            log.debug("Before the Rate");
            for(PackageRate p:resp1)
            {
                if(p.getErrorCode()==0)
                {
                    log.debug((p.getOriginalDiscount() > 0 ? "DIS:" : "STD:") + p.getMethodName() + ":" + p.getFinalRate());
                    if(p.getOriginalDiscount()>0)
                    {
                        log.debug(p);
                    }
                } else

                {
                    log.debug("ERR:" + p.getMethodName() + ":" + p.getErrorMessage());
                }
            }

        } catch (Exception ex) {
            log.debug("got error exception from checkAddress");
            ex.printStackTrace();
            log.debug("printed error exception from checkAddress");
        }       finally
        {
            // HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("choices");
            System.out.println(ChoiceListManager.getChoiceListManager().getList("Service").getValues());
            OrderStatus order = new OrderStatus("14500297");
            OrderRater rater = new OrderRater(order);
            List<String> codes = new ArrayList<String>();
            codes.add(order.shipping.carr_service_ref_num);
            rater.rate(codes );
            List<PackageRate> resp1 = rater.theResponse;
            log.debug("Before the Rate");
            for(PackageRate p:resp1)
            {
                if(p.getErrorCode()==0)
                {
                    log.debug((p.getOriginalDiscount() > 0 ? "DIS:" : "STD:") + p.getMethodName() + ":" + p.getFinalRate());
                    if(p.getOriginalDiscount()>0)
                    {
                        log.debug(p);
                    }
                } else

                {
                    log.debug("ERR:" + p.getMethodName() + ":" + p.getErrorMessage());
                }
            }

          /*     log.debug(getCountryCode("BRUNEI DARUSSALAM"));
            log.debug(getCountryCode("BRUNEI"));
            log.debug(getCountryCode("BRUNEI_DARUSSALAM"));*/
        //    List<PackageRate> resp1 = OrderRater.getRates("123 main", "toronto", "ON",
        //            "M6G 2K5", "CANADA" +
        //            "", 0.8f, false, OrderRater.getShipperForLocation("DC1"));


           /* List<PackageRate> resp1 =  rater.getRates("185 Dulce Nombre de Maria Drive", "Hagatna", "GU",
                    "96910", "GUAM" +
                    "", 0.5f, false, OrderRater.getShipperForLocation("DC1"));*/
          //  System.out.println(OrderRater.getRateableServicesMapByLocation("DC1"));

          /*  List<PackageRate> resp2 =  OrderRater.getRates("", "", "",
                    "98274", "UNITED_STATES" +
                    "", 1.0f, false, OrderRater.getShipperForLocation("DC1"));

*/
          /* for(PackageRate p:resp1)
           {
             if(p.getErrorCode()==0)
             {
                 log.debug((p.getOriginalDiscount() > 0 ? "DIS:" : "STD:") + p.getMethodName() + ":" + p.getFinalRate());
                 if(p.getOriginalDiscount()>0)
                 {
                     log.debug(p);
                 }
             } else

             {
                 log.debug("ERR:" + p.getMethodName() + ":" + p.getErrorMessage());
             }
           }*/
       /*     for(PackageRate p:resp2)
            {
                if(p.getErrorCode()==0)
                {
                    log.debug((p.getOriginalDiscount()>0?"DIS:":"STD:")+p.getMethodName()+":"+p.getFinalRate());
                    if(p.getOriginalDiscount()>0)
                    {
                        log.debug(p);
                    }
                } else

                {
                    log.debug("ERR:"+p.getMethodName()+":"+p.getErrorMessage());
                }
            }*/
            //   Object o = getRateableServicesMapByLocation("DC1");


      /*      Address sadd = new Address();
            sadd.state="NY";
            sadd.city= "HIGHLAND MLS";
            sadd.address_one= "po box 31";
            sadd.zip="10930";
            sadd.country="USA";

          //    OrderRater.checkAddress(sadd,"\tTANDATA_FEDEXFSMS.FEDEX.SP_PS");
            log.debug(OWDUtilities.encryptData("514") );
            //   AddressManager.isAddressValid(sadd,"OWD.1ST.PRIORITY");
*/

        } catch (Exception ex) {
            log.debug("got error exception from checkAddress");
            ex.printStackTrace();
            log.debug("printed error exception from checkAddress");
        }       finally
        {
            // HibernateSession.closeSession();
        }

    }


    public static void checkAddress(Address address, String shipMethodCode) throws Exception {

        checkAddress(address,shipMethodCode,"DC1");
    }
    public static void checkAddress(Address address, String shipMethodCode, String location) throws Exception {

        //   log.debug("OR checkaddr address:"+address);
          log.debug("OR checkaddr shipcode:" + shipMethodCode);
        log.debug("OR checkaddr newshipcode:" + getNewServiceCode(shipMethodCode).toUpperCase());

        if (address.isPONew()){
            if((!(getNewServiceCode(shipMethodCode).contains("USPS"))) && (!(getNewServiceCode(shipMethodCode).toUpperCase().contains("FEDEX.SP_")))&&(!(getNewServiceCode(shipMethodCode).contains("OSM")))
                    &&(!(getNewServiceCode(shipMethodCode).contains("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS")))&&(!(getNewServiceCode(shipMethodCode).contains("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP")))
                    &&(!(getNewServiceCode(shipMethodCode).contains("COM_OWD_FLAT")))){
                throw new Exception("ERROR: PO Box shipments can only go via USPS ship methods or Final Mile");
            }
        }

        OrderRater rater = new OrderRater();


        List<PackageRate> theResponse = rater.getRates(address.address_one, address.city, address.state, address.zip, address.country, 0.75f, address.company_name.length() > 1, getShipperForLocation(location));

        int ratedServices = theResponse.size();
        log.debug("rated " + ratedServices);
        if(ratedServices<1)
        {

            throw new Exception("Unable to verify address - no valid rates returned");

        }
        String packError = "";
        for (PackageRate currShipment:theResponse) {

            if (currShipment.getErrorCode()==0) {
                return;
            } else
            {
                packError = currShipment.getErrorMessage();
            }
        }
        throw new Exception(packError);


    }

    public String originCode = null;

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public  String getDefaultShipper()
    {
        if(originCode==null)
        {
            return (String) getShipperMap().get("DC1");
        }else
        {
            return (String) getShipperMap().get(originCode);
        }
    }



    public  List<PackageRate> getRates(String address1, String city, String state, String zip, String country, float weight_lbs, boolean isCommercial) throws Exception {

        return getRates(address1, city, state, zip, country, weight_lbs, isCommercial,false,getShipperForLocation("DC1"),0,null);
    }


    public  List<PackageRate> getRates(String address1, String city, String state, String zip, String country, float weight_lbs, boolean isCommercial, boolean saturdayDelivery) throws Exception {

        return getRates(address1,city,state,zip,country,weight_lbs,isCommercial,saturdayDelivery,getShipperForLocation("DC1"),0,null);
    }

    public  List<PackageRate> getRates(String address1, String city, String state, String zip, String country, float weight_lbs, boolean isCommercial, String shipper) throws Exception {

        return getRates(address1, city, state, zip, country, weight_lbs, isCommercial,false,shipper,0,null);
    }


    public  List<PackageRate> getRates(String address1, String city, String state, String zip, String country, float weight_lbs, boolean isCommercial, boolean saturdayDelivery, String shipper, int clientID, List<String> serviceCodes) throws Exception {





        RateRequest rateReq = new RateRequest();

        ServiceList standardServiceList = new ServiceList();
        ServiceList proofRequiredServiceList = new ServiceList();
        ServiceList proofNoSigRequiredServiceList = new ServiceList();
        ServiceList dduServiceList = new ServiceList();


        // standardServiceList.getService().addAll(scsList);
        //  rateReq.setServices(standardServiceList);

        DataDictionary defaults = new DataDictionary();
        rateReq.setDefaults(defaults);
        rateReq.getDefaults().setShipper(shipper);
        rateReq.getDefaults().setPackaging("CUSTOM");

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        Calendar now = GregorianCalendar.getInstance();


        while (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            now.add(Calendar.DATE, 1);

        }


        rateReq.getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)now));

        defaults.setTerms("SHIPPER");
        // create consignee NameAddress

     /*   NameAddress facilityAddress = new NameAddress();
        facilityAddress.setCompany("Test Co.");
        facilityAddress.setContact("Test Contact");
        facilityAddress.setAddress1("10 1st Ave E");
        facilityAddress.setCity("Mobridge");
        facilityAddress.setStateProvince("SD");
        facilityAddress.setPostalCode("57601");
        facilityAddress.setCountryCode("US");
        facilityAddress.setPhone("605-845-7172");
        facilityAddress.setResidential(false);
*/
        NameAddress consignee = new NameAddress();
        consignee.setCompany("Test Co.");
        consignee.setContact("Test Contact");
        consignee.setAddress1(address1);
        consignee.setCity(city);
        consignee.setStateProvince(state.length() > 0 ? state : ".");
        consignee.setPostalCode(zip);



        log.debug("dorate3");
        if (country.toUpperCase().contains("CHINA")) {
            consignee.setCountrySymbol("CHINA");
        } else {
            //log.debug("getting country for "+order.shipping.shipAddress.country);
            consignee.setCountrySymbol(ChoiceListManager.getChoiceListManager().getList("Country ID").getRefForValue(country));
            //getCountryCode(order.shipping.shipAddress.country));//order.shipperAddress.country);
            //log.debug("got country symbol "+cons.getCOUNTRYSYMBOL());
        }
        if(consignee.getCountrySymbol()==null)
        {
            consignee.setCountrySymbol(country);
        }

        consignee.setResidential(!isCommercial);



        consignee.setPhone("605-845-7172");

        rateReq.getDefaults().setConsignee(consignee);
        //   rateReq.getDefaults().setOriginAddress(facilityAddress);



        // create a new package list
        DataDictionaryList packages = new DataDictionaryList();

        // create first package dictionary
        DataDictionary pkg = new DataDictionary();
        Weight wgt = new Weight();

        wgt.setAmount(new BigDecimal(weight_lbs));
        wgt.setUnit("lb");
        pkg.setWeight(wgt);


        //no dimensional support yet
        /*
        Dimensions dim = new Dimensions();
        dim.setHeight(new BigDecimal(w));
        dim.setWidth(new BigDecimal(d));
        dim.setLength(new BigDecimal(h));

        pkg.setDimension(dim);
        */


        pkg.setDispositionMethod("1");


        if (!(consignee.getCountrySymbol().equals("UNITED_STATES"))) {



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

        rateReq.getDefaults().setSaturdayDelivery(saturdayDelivery);



        log.debug("Rater got past intl");


        log.debug("Initial Weight Lbs: " + wgt.getAmount());
        //pkg.setPACKMAX("30");
        double ratedPackages = 1.00;
        if(wgt.getAmount().doubleValue()>40.00)
        {
            double totalWeight = wgt.getAmount().doubleValue();
            while(totalWeight>40.00)
            {
                ratedPackages++;
                totalWeight -= 40.00;
            }
            wgt.setAmount(BigDecimal.valueOf(40.00));
        }
        log.debug("Final Weight Lbs: " + wgt.getAmount() + " for " + ratedPackages + " package(s)");


        // add first package to list
        packages.getItem().add(pkg);
        rateReq.setPackages(packages);
        rateReq.setSortType("rate");



        for(String svcCode:(serviceCodes==null?getRateableServicesMapByShipper(shipper).keySet():serviceCodes))
        {
            if(svcCode.equals("TANDATA_FEDEXFSMS.FEDEX.SP_PS"))
            {
                log.debug("FEDEX OK");
                proofNoSigRequiredServiceList.getService().add(svcCode);

            }  else  if (!(consignee.getCountrySymbol().equals("UNITED_STATES")) && (svcCode.startsWith("CONNECTSHIP_UPS") || svcCode.startsWith("TANDATA_FEDEX")||svcCode.startsWith("CONNECTSHIP_DHL"))) {
                dduServiceList.getService().add(svcCode);

            }   else
            {
                standardServiceList.getService().add(svcCode);
            }
        }
        System.out.println("The standard" + standardServiceList.getService());
        pkg.setProof(false);
        List<PackageRate> rates = getRatedPackages(rateReq,standardServiceList,ratedPackages);
        pkg.setProof(true);
        pkg.setProofRequireSignature(false);
        rates.addAll(getRatedPackages(rateReq, proofNoSigRequiredServiceList, ratedPackages));
        pkg.setProofRequireSignature(true);
        rates.addAll(getRatedPackages(rateReq, proofRequiredServiceList, ratedPackages));
        pkg.setProof(false);
        pkg.setProofRequireSignature(false);
        pkg.setDispositionMethod("0");
        rateReq.getDefaults().setTerms("DDU");

        rates.addAll(getRatedPackages(rateReq, dduServiceList, ratedPackages));

        //add errors

        sortByRate(rates);

        return rates;
    }





    public static synchronized List getServiceCodes(String theLocation) {

        return new ArrayList(getRateableServicesMapByLocation(theLocation).keySet());

    }


    private static TreeMap<String,TreeMap> locationServices = new TreeMap<String,TreeMap>();


    public static synchronized TreeMap<String,String> getRateableServicesMapByShipper(String shipper) {
        return getRateableServicesMapByLocation(locationRaterMap.get(shipper));
    }


    public static synchronized TreeMap<String,String> getRateableServicesMap() {
        return getRateableServicesMapByLocation("DC1");


    }
    public static synchronized TreeMap<String,String> getRateableServicesMapByLocation(String location){


        if (locationServices.get(location) == null) {

            locationServices.put(location,new TreeMap());


            try{
                ListServicesRequest servReq = new ListServicesRequest();
                servReq.setShipper(getShipperForLocation(location));

                log.debug("Using shipper " + getShipperForLocation(location));
                ListServicesResponse services = amp.listServices(servReq);
                for(Identity service:services.getResult().getResultData().getItem())
                {


                    try{



                        log.debug(service.getName() + " : " + service.getSymbol() +
                                " : " + ChoiceListManager.getChoiceListManager().getList("Service").getValues().contains(service.getName().replaceAll("\\ \\(Book\\)", "").replaceAll("\\ \\(US\\)", "").replaceAll("\\ \\(Consolidated\\)","").replaceAll("[()]", "")));

                        //log.debug(service.getName().replaceAll("\\ \\(Book\\)", "").replaceAll("\\ \\(US\\)", "").replaceAll("\\ \\(Consolidated\\)","").replaceAll("[()]", ""));

                        if (ChoiceListManager.getChoiceListManager().getList("Service").getValues().contains(service.getName().replaceAll("\\ \\(Book\\)","").replaceAll("\\ \\(US\\)","").replaceAll("\\ \\(Consolidated\\)","").replaceAll("[()]", "").replaceAll("UPS Standard", "UPS Standard Canada").replaceAll("Ground Guaranteed", "Ground").replaceAll("UPS Worldwide Saver", "UPS Worldwide Express Saver").replaceAll("USPS Parcel Select Ground","USPS Parcel Select Nonpresort"))) {
                            //   log.debug(service.getSymbol()+":"+service.getName().replaceAll("[()]", "").replaceAll("USPS Express Mail Addressee", "USPS Expr Mail-Addressee"));

                            if (!service.getSymbol().contains("TANDATA_UPS.")) {

                                locationServices.get(location).put(service.getSymbol(), service.getName()
                                        .replaceAll("\\ \\(Book\\)", "").replaceAll("\\ \\(US\\)", "")
                                        .replaceAll("USPS Intl Airmail Letter-post", "USPS Int'l Letter-Post Air")
                                        .replaceAll("USPS Intl Airmail Parcel Post", "USPS Int'l Parcel Post Air")
                                        .replaceAll("USPS Intl Economy Letter-post", "USPS Intl Economy Letter-Post")
                                        .replaceAll("Ground Guaranteed", "Ground")
                                        .replaceAll("Standard to Canada", "Standard Canada")
                                        .replaceAll("USPS Intl Economy Parcel Post", "USPS Int'l Parcel Post Economy")
                                        .replaceAll("UPS Worldwide Saver", "UPS Worldwide Express Saver")
                                        .replaceAll("\\ \\(Consolidated\\)",""));
                            }
                        }





                    } catch (Exception ex) {

                        // //log.debug("RESP EX");

                        ex.printStackTrace();

                    }


                }
                //add in Extra services
              //  locationServices.get(location).put("OWD.BPD.STANDARD","BPD Standard");
               // locationServices.get(location).put("OWD.OSM.DOM","OSM Domestic");
                locationServices.get(location).put("COM_OWD_FLATRATE_GROUND","Ground");
                locationServices.get(location).put("COM_OWD_FLATRATE_2DA","2 Day");
                locationServices.get(location).put("COM_OWD_FLATRATE_NDA","Overnight");
                locationServices.get(location).put("COM_OWD_FLATRATE_INTL_STND","International Standard");
                locationServices.get(location).put("COM_OWD_FLATRATE_INTL_EXPD","International Expedited");
                locationServices.get(location).put("COM_OWD_FLATRATE_ECONOMY","Economy");
                locationServices.get(location).put("COM_OWD_FLATRATE_DHL_MAX","Ground Max");
                locationServices.get(location).put("COM_OWD_FLATRATE_STANDARD_GROUND","Standard Priority");
                locationServices.get(location).put("COM_OWD_FLATRATE_INTL_ECONOMY","International Economy");
                locationServices.get(location).put("TANDATA_LTL.LTL","LTL");
                locationServices.get(location).put("EASYPOST.PUROLATOR","Purolator");
                locationServices.get(location).put("COM_OWD_FLATRATE_INTL_PRIDDP","International Priority DDP");
                locationServices.get(location).put("COM_OWD_FLATRATE_INTL_PRIDDU","International Priority DDU");
                locationServices.get(location).put("COM_OWD_FLATRATE_MM","Media Mail");

            } catch (Exception ex) {

                // //log.debug("RESP EX");

                ex.printStackTrace();

            }
        }

        //locationServices.get(location).put("", "");

        //  //log.debug("services:"+services);
        return locationServices.get(location);



    }



    public static String getNewServiceCode(String oldCode) throws Exception {

        if (oldSvcCodeToNewCodeMap == null)
        {
            updateServiceMaps();
        }
        log.debug("looking up svc code :" + oldCode + ":");

        if (oldSvcCodeToNewCodeMap.containsKey(oldCode)) {

            log.debug("got new code " + (String) (oldSvcCodeToNewCodeMap.get(oldCode)));

            return (String) (oldSvcCodeToNewCodeMap.get(oldCode));

        } else {

            return ChoiceListManager.getTranslatedString(oldCode);

            //throw new Exception("Can't find carrier service for code " + oldCode);

        }

    }


    public static String getOldServiceCode(String newCode) throws Exception {

        if (newSvcCodeToOldMap == null)

            updateServiceMaps();


        if (newSvcCodeToOldMap.containsKey(newCode)) {

            return (String) (newSvcCodeToOldMap.get(newCode));

        } else {

            throw new Exception("Can't find legacy carrier service for code " + newCode);

        }

    }

    public static String getSafeServiceCode(String code) {


        if (newSvcCodeToOldMap == null)  {
            updateServiceMaps();
        }

        if (newSvcCodeToOldMap.containsKey(code)) {
            //is a new code, return old code
            return (String) (newSvcCodeToOldMap.get(code));

        } else if (oldSvcCodeToNewCodeMap.containsKey(code)) {
            //is an old code
            return code;
        } else {
            //is not recognized
            return code;
        }

    }


    private static synchronized void updateServiceMaps() {


        Connection cxn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            oldSvcCodeToNewCodeMap = new TreeMap();

            newSvcCodeToOldMap = new TreeMap();

            cxn = com.owd.core.managers.ConnectionManager.getConnection();


            String sql = "select reference_num,td_reference from owd_lists where list_name = \'Service\' and is_inactive=0";

            stmt = cxn.prepareStatement(sql);


            stmt.executeQuery();


            rs = stmt.getResultSet();


            while (rs.next()) {

                if (!(rs.getString(1).trim().equals(""))) {

                    log.debug("OLD-" + rs.getString(1) + ":NEW-" + rs.getString(2));
                    oldSvcCodeToNewCodeMap.put(rs.getString(1), rs.getString(2));

                    newSvcCodeToOldMap.put(rs.getString(2), rs.getString(1));

                }

            }


            rs.close();

            stmt.close();

            cxn.close();


        } catch (Throwable th) {

            th.printStackTrace();

        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }


    }


    public static String getCountryCode(String countryName) {

        if (countryMap == null) {

            countryMap = new TreeMap();

            try{
                ListCountriesRequest servReq = new ListCountriesRequest();

                ListCountriesResponse countries = amp.listCountries(servReq);



                for (Identity country:countries.getResult().getResultData().getItem()) {

                    log.debug(country.getName().toUpperCase() + ":" + country.getSymbol());
                    countryMap.put(country.getName().toUpperCase(), country.getSymbol());


                }


            } catch (Exception ex) {

                //log.debug("RESP EX");

                ex.printStackTrace();

            }


            countryMap.put("USA", "UNITED_STATES");

            countryMap.put("CHINA PEOPLES REPUBLIC OF", "CHINA");

            countryMap.put("KOREA SOUTH", "KOREA_SOUTH");

            countryMap.put("ENGLAND", "UNITED_KINGDOM");


        }


        String countryCode = (String) countryMap.get(countryName.toUpperCase());

        //  //log.debug("Searching for " + countryName + "->" + countryCode);
      //  log.debug(countryMap);

        if (countryCode == null) {

            countryCode = "UNITED_STATES";

        }

        return countryCode;

    }


    private static Map oldSvcCodeToNewCodeMap = null;

    private static Map newSvcCodeToOldMap = null;


    private static Map countryMap = null;


    public static void sortByRate(List<PackageRate> list) {
        Collections.sort(list, new Comparator<PackageRate>() {
            public int compare(PackageRate s1, PackageRate s2) {
                double s1Rate = s1.getFinalRate();
                double s2Rate = s2.getFinalRate();
                if(s1Rate==0){s1Rate=999999;}
                if(s2Rate==0){s2Rate=999999;}

                return (s1Rate == s2Rate ? 0 : s1Rate > s2Rate ? 1 : -1);
            }
        });
    }


}

