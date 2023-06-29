package com.owd.OWDShippingAPI;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.LogableException;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Services.OWDShippingAPIServiceGenerator;
import com.owd.OWDShippingAPI.Services.ShipService;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import com.owd.OWDShippingAPI.tracking.TrackingBean;
import com.owd.OWDShippingAPI.tracking.TrackingInfo;
import com.owd.connectship.xml.api.OWDShippingResponse.INTLDOCDATA;
import com.owd.core.business.order.Event;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by danny on 11/16/2019.
 */
public class ShippingUtilities {
    private final static Logger log = LogManager.getLogger();
    private static Map<String,shipServiceModel> proShipServiceToOwd = new HashMap<>();

    public ShippingUtilities() {
        loadProShipMap();
    }

    private static void loadProShipMap(){
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
        proShipServiceToOwd.put("BWTI_UPS.UPS.SPP",new shipServiceModel("CONNECTSHIP_UPS.UPS.SPPS","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.SPS",new shipServiceModel("CONNECTSHIP_UPS.UPS.SPSTD","CONNECTSHIP_UPS.UPS"));
        proShipServiceToOwd.put("BWTI_UPS.UPS.MIE",new shipServiceModel("BWTI_UPS.UPS.MIE","CONNECTSHIP_UPS.UPS"));

        proShipServiceToOwd.put("BWTI_DHL.DHL.WWP",new shipServiceModel("CONNECTSHIP_DHL.DHL.WPX","CONNECTSHIP_DHL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM81",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM82",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM631",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM631",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM36",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLAMP.SM83",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND","CONNECTSHIP_DHLGLOBALMAIL.DHL"));
        proShipServiceToOwd.put("PS_ALF.DHLGMI.PLY",new shipServiceModel("CONNECTSHIP_DHLGLOBALMAIL.DHL.PC_PRI","CONNECTSHIP_DHLGLOBALMAIL.DHL"));

        proShipServiceToOwd.put("BWTI_USPS.USPS.FCSP",new shipServiceModel("TANDATA_USPS.USPS.FIRST","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.PRIORITY","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.EXPPC",new shipServiceModel("TANDATA_USPS.USPS.EXPR","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PS",new shipServiceModel("TANDATA_USPS.USPS.PS_NONPRESORT","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.FCSP",new shipServiceModel("TANDATA_USPS.USPS.I_FIRST","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.PRIORITY_CUBIC","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.EXPPC",new shipServiceModel("TANDATA_USPS.USPS.I_EXP_DMND","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.PM",new shipServiceModel("TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS"));
        proShipServiceToOwd.put("BWTI_USPS.USPS.MM",new shipServiceModel("TANDATA_USPS.USPS.SPCL","TANDATA_USPS.USPS"));

        proShipServiceToOwd.put("CONNECTSHIP_GLOBAL.APC.PRIDDPDC",new shipServiceModel("CONNECTSHIP_GLOBAL.APC.PRIDDPDC","CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("CONNECTSHIP_GLOBAL.APC.PRIDDUDC",new shipServiceModel("CONNECTSHIP_GLOBAL.APC.PRIDDUDC","CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("COM_OWD_FLATRATE_INTL_PRIDDP",new shipServiceModel( "CONNECTSHIP_GLOBAL.APC.PRIDDPDC", "CONNECTSHIP_GLOBAL.APC"));
        proShipServiceToOwd.put("COM_OWD_FLATRATE_INTL_PRIDDU",new shipServiceModel( "CONNECTSHIP_GLOBAL.APCPRIDDUDC", "CONNECTSHIP_GLOBAL.APC"));

        proShipServiceToOwd.put("PS_ALF.ONTRAC.CT",new shipServiceModel("ONTRAC.GROUND","ONTRAC"));

    }


    public shipServiceModel getOwdShipCodesFromProShipCodes(String proShip, double weight,String country) throws Exception{

        if(proShip.equals("BWTI_USPS.USPS.FCSP")){
            if(country.equals("US")){
                return new shipServiceModel("TANDATA_USPS.USPS.FIRST","TANDATA_USPS.USPS");
            }else{
                return new shipServiceModel("TANDATA_USPS.USPS.I_FIRST","TANDATA_USPS.USPS");
            }
        }
        if(proShip.equals("BWTI_USPS.USPS.PM")){
            if(country.equals("US")){
                return new shipServiceModel("TANDATA_USPS.USPS.PRIORITY","TANDATA_USPS.USPS");
            }else{
                return new shipServiceModel("TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS");
            }
        }
        if(proShip.equals("BWTI_USPS.USPS.EXPPC")){
            if(country.equals("US")){
                return new shipServiceModel("TANDATA_USPS.USPS.EXPR","TANDATA_USPS.USPS");
            }else{
                return new shipServiceModel("TANDATA_USPS.USPS.I_EXP_DMND","TANDATA_USPS.USPS");
            }
        }
        if(proShip.equals("BWTI_FXRS.SP.SM")){
            if(weight>1){
                return new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.SP_PS","TANDATA_FEDEXFSMS.FEDEX");
            }else{
                return new shipServiceModel("TANDATA_FEDEXFSMS.FEDEX.SP_STD","TANDATA_FEDEXFSMS.FEDEX");
            }
        }

        if(proShipServiceToOwd.size()==0) {
            loadProShipMap();
        }
        if(proShipServiceToOwd.containsKey(proShip)){
            return proShipServiceToOwd.get(proShip);
        }else{
            Exception lex = new LogableException("Unable to find ProShipMethod",proShip,"55","ProShip Method Map", LogableException.errorTypes.UNDEFINED_ERROR);
            throw new Exception("Unable to find mapping: just record normal and IT will fix");
        }

    }

    public void findIssueWithNoLineLoad(String packageBarcode, String orderBarcode)throws Exception{

        String orderNum = orderBarcode.replaceAll("\\*|[rR].*", "");
        Criteria criteria = HibernateSession.currentSession().createCriteria(OwdOrder.class);
        criteria.add(Restrictions.eq("orderNum", orderNum));
        List<OwdOrder> orders = criteria.list();

        if(orders.size()>0){
            OwdOrder order = orders.get(0);
            if(!orderBarcode.equals(order.getOrderNumBarcode())) throw new Exception("Order has been edited since picked. You have an invalid packing slip. Please follow procedure");

            if(order.getShipinfo().getCarrService().equalsIgnoreCase("LTL")) throw new Exception("This is an LTL order. Please follow standard procedures");
        }else{
            throw new Exception("Error loading Order data for shipping. Please contact IT");
        }


    }


    /**
     *
     * @param packages List of Packages with ID and Weight
     * @param facility Facility the shipment is made in
     * @return Filled ShipShipment object to be send to API
     * @throws Exception any data loading errors
     */

    public ShipShipment LoadDataFromPackages(List<Package> packages, String facility) throws Exception{


        ShipShipment shipment = new ShipShipment();
        shipment.setPackages(new ArrayList<Package>());
        shipment.setFacility(facility);
        Double insuranceAmount = 0d;

        for(Package p : packages) {

            //Load data, list is Object for each line in the package
            List l = getPackageDataFromDatabse(p.getPackageReference());
            if (l.size() > 0){
                //Load First line to get Order and Package info
                Map data = (Map) l.get(0);
            //load the shipment data
            loadShipmentDataFromMap(shipment, data,p.getWeight());

            //Load package Data
            loadPackageData(shipment, data, p.getWeight(), l);


            if (Double.parseDouble(data.get(xORDER_DECLARED_VALUE).toString()) > 0) {
                insuranceAmount = Double.parseDouble(data.get(xORDER_DECLARED_VALUE).toString());
            }
        }else{
                //There is an error loading the data, find it and throw an error.
                String packBarcode = p.getPackageReference();
                findIssueWithNoLineLoad(p.getPackageReference(),packBarcode.substring(packBarcode.indexOf("*"), packBarcode.lastIndexOf("*") + 1));
            }




        }

        //if there is an insuredAmount split it bwtween all packates if needed
        if(insuranceAmount>0){
            if(shipment.getPackages().size()>1){
                for(int i = 0;i<shipment.getPackages().size();i++){
                    shipment.getPackages().get(i).setInsuranceAmount((insuranceAmount/shipment.getPackages().size()));

                    /*for (int j = 0 ; j<shipment.getPackages().get(i).getLineInfo().size();j++){
                        log.debug("item  " + i + " value = " + shipment.getPackages().get(i).getLineInfo().get(j));
                    }*/

                }

                // TODO Sean calc and set insurance for each individual package
                /*shipment.getPackages().get(0).setInsuranceAmount(504d);
                shipment.getPackages().get(1).setInsuranceAmount(504d);
                shipment.getPackages().get(2).setInsuranceAmount(252d);*/

            }else{
                shipment.getPackages().get(0).setInsuranceAmount(insuranceAmount);
            }
        }


        return shipment;
    }

    /**
     *
     * @param p Package to add line item info to
     * @param items List of items in Map form
     * @throws Exception when declared value is 0
     */
    private  void loadLineInfo(Package p, List items,boolean isAPO) throws Exception{
        p.setLineInfo(new ArrayList<LineItemInfo>());
        for(Object o:items){
            //Transform line data to map

            Map m = (Map) o;
            //Only add line if IsInsert is 0
            if(m.get(xiIS_INSERT).toString().equals("0")) {
                LineItemInfo info = new LineItemInfo();
                info.setCountryOfOrigin(getIANACountry(m.get(xiORIGIN_COUNTRY).toString(), ""));
                info.setDescription(m.get(xiITEM_CUSTOM_DESCRIPTION).toString());

                info.setQuantity(Integer.parseInt(m.get(xiPACK_QUANTITY).toString()));
                info.setQuantityUnitMeasure("EA");
                info.setSku(m.get(xiINVENTORY_NUM).toString());
                info.setSingleUnitWeight(Double.parseDouble(m.get(xiITEMWEIGHT).toString()));
                //todo double check Value lookup and Adjust if needed
                //check Item declared value first if that is 0 the inventory declared, other wise inventory price
                double itemDeclared = Double.parseDouble(m.get(xiITEM_CUSTOMS_VALUE).toString());
                double inventoryDeclared = Double.parseDouble(m.get(xiINVENTORYITEM_CUSTOMS_VALUE).toString());
                double itemPrice = Double.parseDouble(m.get(xiPRICE).toString());
                log.debug("Loading Data for line item: " + info.getSku() + "|" + itemDeclared + "|" + inventoryDeclared);
                if (itemDeclared > 0) {
                    info.setSingleUnitValue(itemDeclared / info.getQuantity());
                } else if (inventoryDeclared > 0) {
                    info.setSingleUnitValue(inventoryDeclared);
                } else if (itemPrice > 0 || isAPO) {
                    info.setSingleUnitValue(itemPrice);
                } else {
                    throw new Exception("Invalid customs value for item: " + info.getSku());
                }

                Clob desc = (Clob) m.get(xiITEM_CUSTOM_DESCRIPTION);

                info.setDescription(convertClobToString(desc));
                if (info.getDescription().length() == 0) {
                    info.setDescription(m.get(xiINVENTORYITEM_CUSTOMS_DESCRIPTION).toString());
                }
                if (info.getDescription().length() == 0) {
                    info.setDescription(m.get(xiDESCRIPTION).toString());
                }

                p.getLineInfo().add(info);
            }


        }
    }


    /**
     *
     * @param shipment ShipShipment object to add Package to
     * @param m package and
     * @param weight weight for the package
     * @throws Exception when data cannot be loaded properly
     */
    private  void loadPackageData(ShipShipment shipment, Map m,Double weight,List items) throws Exception{
        Package p = new Package();
        p.setPackageReference(m.get(xpPACK_BARCODE).toString());
        //We have to have a weight when we ship. If the weight is 0 or negative, throw error
        if(weight<=0){
            throw new Exception("Invalid weight. Weight must be greater than 0 for package: "+p.getPackageReference());

        }
        p.setWeight(weight);
        //todo box lookup for FlatRate for proper codes
        p.setPackagingType("CUSTOM");
        p.setDimension(m.get(xpDIM_DEPTH).toString()+"x"+m.get(xpDIM_WIDTH).toString()+"x"+m.get(xpDIM_HEIGHT).toString());
        if(null == shipment.getPackages()){
            shipment.setPackages(new ArrayList<Package>() );
        }
        //if International we need to load lines for customs
        if(isInternational(shipment)||isAPOFPO(shipment)){
            //Check for Postal and Puerto Rico
            if(shipment.getShipMethod().contains("USPS")&&!shipment.getShipMethod().contains("International")){

              if(!shipment.getShipToCountry().equals("PR")){
                  loadLineInfo(p,items,isAPOFPO(shipment));
              }

            }else{
                loadLineInfo(p,items,isAPOFPO(shipment));
            }

        }
        shipment.getPackages().add(p);

    }

    // ====================================================
    /**
     * Sean 2020/01/17
     * @param m
     * pass shipment , Map loop thru skus in list if exist, then return a string that needs to be appended
     */
    public static String getSuffix (String clientID, Map m){
        String suffix = "";
        if(clientID.equals("529")){
            for (int i=0; i<m.size();i++){
                if(isSkuAddingSuffix(m.get(xiINVENTORY_NUM).toString(), LyftAMPSkus)){
                    suffix = "-AMP";
                }
                if(isSkuAddingSuffix(m.get(xiINVENTORY_NUM).toString(), oneKs)){
                    suffix = "-1KJkt";
                }
            }
        }
        return suffix;
    }

    private static boolean isSkuAddingSuffix(String targetSku, List skuList){
        for (Object sku : skuList) {
            if(targetSku.toUpperCase().contains((CharSequence) sku)){
                return true;
            }
        }
        return false;
    }

    private static List<String> LyftAMPSkus = new ArrayList<>(Arrays.asList(
            "LYFT150", // old request
            "LYFT350", // following are requested on 2019/12/26
            "LYFT351",
            "LYFT352",
            "LYFT154",
            "LYFT155",
            "LYFT156RF"));

    private static List<String> oneKs = new ArrayList<>(Arrays.asList("LYFT126-KITM4XL","LYFT126-KITM3XL","LYFT126" +
                    "-KITM2XL",
            "LYFT126-KITMXL",
            "LYFT126-KITML",
            "LYFT126-KITMM",
            "LYFT126-KITMS",
            "LYFT126-KITW2XL",
            "LYFT126-KITWXL",
            "LYFT126-KITWL",
            "LYFT126-KITWM",
            "LYFT126-KITWS"));

    // ====================================================
    /**
     *
     * @param shipment Emtpy ShipShipment object to be filled out
     * @param m values for the package
     * @throws Exception when data cannot be loaded properly
     */
    private  void loadShipmentDataFromMap(ShipShipment shipment, Map m,Double weight)throws Exception{

        //Set Terms to SHIPPER. If third party info is present it will get over written.
        if(null==shipment.getShipTerms()||shipment.getShipTerms().length()==0) {
            shipment.setShipTerms("SHIPPER");
        }

        //ShipDate Handle Changing in API because methods might change with Flat Rate
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        shipment.setShipDate(format.format(Calendar.getInstance().getTime()));

        //Ship To INFO
        shipment.setShipToContact(m.get(xCONTACT).toString());

        shipment.setShipToCompany(m.get(xSHIP_COMPANY_NAME).toString());
        if(shipment.getShipToContact().length()==0||shipment.getShipToContact().equals(" ")){
            shipment.setShipToContact(shipment.getShipToCompany());
        }
        shipment.setShipToAddress1(m.get(xSHIP_ADDRESS_ONE).toString());
        shipment.setShipToAddress2(m.get(xSHIP_ADDRESS_TWO).toString());
        shipment.setShipToCity(m.get(xSHIP_CITY).toString());

        //catch Virgin Islands state and change to 2 digit
        if(m.get(xSHIP_STATE).toString().equalsIgnoreCase("Virgin Islands")){
            shipment.setShipToState("VI");
        }else{
            shipment.setShipToState(m.get(xSHIP_STATE).toString());
        }

        shipment.setShipToPostalCode(m.get(xSHIP_ZIP).toString());
        shipment.setShipToCountry(getIANACountry(m.get(xSHIP_COUNTRY).toString(),shipment.getShipToCity()));


        shipment.setShipToPhone(m.get(xSHIP_PHONE).toString());
        if(shipment.getShipToPhone().length()==0){
            shipment.setShipToPhone("605-845-4299");
        }
        shipment.setShipToEmail(m.get(xSHIPTOEMAIL).toString());
        shipment.setPoNumber(m.get(xPO_NUM).toString());

        shipment.setShipperReference(m.get(xSHIPPER_REFERENCE).toString());

        // =============================================
        //Needs to update (Sean)
        String suffix = getSuffix(m.get(xCLIENT_FKEY).toString(), m);
        shipment.setClientReference(m.get(xCLIENT_REFERENCE).toString()+ suffix);
        // =============================================

        shipment.setResidential(m.get(xSS_RESIDENTIAL).toString().equals("true"));





        //Other info
        shipment.setOrderId(m.get(xORDER_ID).toString());
        shipment.setClientId(m.get(xCLIENT_FKEY).toString());
        shipment.setGroupName(m.get(xGROUP_NAME).toString());
        shipment.setShippingHold(m.get(xON_SHIPPING_HOLD).toString().equals("1"));
        shipment.setDcHold(m.get(xDC_HOLD).toString().equals("1"));

        shipment.setShippedPacks(Integer.parseInt(m.get(xpSHIPPED_PACKS).toString()));
        shipment.setOrderTrackFkey(m.get(xpORDER_TRACK_FKEY).toString());

        //Ship Method
        if(m.get(xCARR_SERVICE_REF_NUM).toString().toUpperCase().contains("OWD_FLATRATE")){

            //Overide for 2day and overnight with PObox and FPO
            if(isPOAPOFPO(shipment)&&(m.get(xCARR_SERVICE_REF_NUM).toString().toUpperCase().contains("2DA")||m.get(xCARR_SERVICE_REF_NUM).toString().toUpperCase().contains("NDA"))){
                shipment.setShipMethod("USPS Priority Mail Express");
                shipment.setShipService("TANDATA_USPS.USPS.EXPR");
                shipment.setFlatRate(true);


            }else if(isPOAPOFPO(shipment)&&(m.get(xCARR_SERVICE_REF_NUM).toString().equalsIgnoreCase("COM_OWD_FLATRATE_GROUND")||m.get(xCARR_SERVICE_REF_NUM).toString().equalsIgnoreCase("COM_OWD_FLATRATE_ECONOMY")
                    ||m.get(xCARR_SERVICE_REF_NUM).toString().equalsIgnoreCase("COM_OWD_FLATRATE_STANDARD_GROUND"))){

                //Force DC6 economy < 1lb to main innovations Per Karen 8-15-2020
                //on check 1st package
                if(m.get(xCARR_SERVICE_REF_NUM).toString().equalsIgnoreCase("COM_OWD_FLATRATE_ECONOMY")
                        &&shipment.getFacility().equalsIgnoreCase("DC6")
                        && weight<1){
                    shipment.setShipMethod("UPS Mail Innovations PS Lightweight");
                    shipment.setShipService("BWTI_UPS.UPS.MIE");
                    shipment.setFlatRate(true);

                }else {

                   

                    shipment.setShipMethod("USPS First-Class Mail");
                    shipment.setShipService("TANDATA_USPS.USPS.FIRST");
                    shipment.setFlatRate(true);
                }


            }else {


                List<Double> dims = new ArrayList<>();
                dims.add(Double.parseDouble(m.get(xpDIM_DEPTH).toString()));
                dims.add(Double.parseDouble(m.get(xpDIM_WIDTH).toString()));
                dims.add(Double.parseDouble(m.get(xpDIM_HEIGHT).toString()));

                String bestwayGroup = lookupFlatRateServiceCode(m.get(xCARR_SERVICE_REF_NUM).toString(), Integer.parseInt(shipment.getClientId()), shipment.getGroupName(), shipment.getFacility(), dims);
                if (bestwayGroup.length() > 0) {
                    if (null != shipment.getShipService() && !shipment.getShipService().endsWith("LARGE")) {
                        shipment.setShipMethod(m.get(xCARR_SERVICE).toString());
                        shipment.setShipService(bestwayGroup);
                    } else {
                        shipment.setShipMethod(m.get(xCARR_SERVICE).toString());
                        shipment.setShipService(bestwayGroup);
                    }
                } else {
                    //lookup did not work throw error
                    throw new Exception("Unable to load flatRate method " + m.get(xCARR_SERVICE_REF_NUM).toString() + " please verify if this is a valid service for this facility.");
                }
                shipment.setFlatRate(true);
            }

        }else{
            shipment.setShipMethod(m.get(xCARR_SERVICE).toString());
            shipment.setShipService(m.get(xCARR_SERVICE_REF_NUM).toString());
            shipment.setFlatRate(false);
        }



        //Set Shipping Account from Lookup.
        shipment.setShippingAccountName(getShippingAccount(shipment));
        //Return Info
        shipment.setReturnContact(m.get(xRETURN_NAME).toString());
        shipment.setReturnCompany(m.get(xRETURN_COMPANY).toString());
        shipment.setReturnAddress1(m.get(xRETURN_ADDRESS1).toString());
        shipment.setReturnAddress2(m.get(xRETURN_ADDRESS2).toString());
        shipment.setReturnCity(m.get(xRETURN_CITY).toString());
        shipment.setReturnState(m.get(xRETURN_STATE).toString());
        shipment.setReturnPostalCode(m.get(xRETURN_POSTALCODE).toString());
        shipment.setReturnPhone(m.get(xRETURN_PHONE).toString());
        shipment.setReturnCountry(getIANACountry(m.get(xRETURN_COUNTRY).toString(),shipment.getReturnCity()));

        //Set AES if available
        if(m.get(xAES_ITN).toString().length()>0){
            shipment.setAesTransactionNumber(m.get(xAES_ITN).toString());
        }

        //Third Party Info
        if(m.get(xSHIPPER_THIRDPARTY_ACCOUNT).toString().length()>0){
            log.debug("We have a Third party account: "+ m.get(xSHIPPER_THIRDPARTY_ACCOUNT).toString());
            ThirdPartyBilling thirdPartyBilling = new ThirdPartyBilling();
            thirdPartyBilling.setAccount(m.get(xSHIPPER_THIRDPARTY_ACCOUNT).toString());



            if(m.get(xSHIPPER_THIRDPARTY_COMPANY).toString().length()>1) {
                thirdPartyBilling.setCompany(m.get(xSHIPPER_THIRDPARTY_COMPANY).toString());
                thirdPartyBilling.setAddress1(m.get(xSHIPPER_THIRDPARTY_ADDRESS1).toString());

                thirdPartyBilling.setCity(m.get(xSHIPPER_THIRDPARTY_CITY).toString());
                thirdPartyBilling.setState(m.get(xSHIPPER_THIRDPARTY_STATE).toString());
                thirdPartyBilling.setPostalCode(m.get(xSHIPPER_THIRDPARTY_POSTALCODE).toString());
                thirdPartyBilling.setCountry(getIANACountry(m.get(xSHIPPER_THIRDPARTY_COUNTRY).toString(),thirdPartyBilling.getCity()));
            }else{
                //No third party data, default to Ship to
                if(shipment.getShipToCompany().length()>1) {
                    thirdPartyBilling.setCompany(shipment.getShipToCompany());
                }else{
                    thirdPartyBilling.setCompany("none");
                }
                thirdPartyBilling.setAddress1(shipment.getShipToAddress1());
                thirdPartyBilling.setCity(shipment.getShipToCity());
                thirdPartyBilling.setState(shipment.getShipToState());
                thirdPartyBilling.setPostalCode(shipment.getShipToPostalCode());
                thirdPartyBilling.setCountry(shipment.getShipToCountry());
            }


            //add billing to shipment
            shipment.setThirdPartyBilling(thirdPartyBilling);
            //set terms to THIRD_PARTY
            shipment.setShipTerms("THIRD_PARTY");
        }

        //todo Skip for when Vendor ships on own Account setup under Shipping System
        //Check for Client third party accounts

        if(null == shipment.getThirdPartyBilling()) {
            if (m.get(xCLIENT_UPS_ACCOUNT).toString().length() > 0 && shipment.getShipMethod().toUpperCase().contains("UPS")) {
                ThirdPartyBilling thirdPartyBilling = new ThirdPartyBilling();
                thirdPartyBilling.setAccount(m.get(xCLIENT_UPS_ACCOUNT).toString());
                thirdPartyBilling.setCompany(shipment.getReturnCompany());
                thirdPartyBilling.setAddress1(shipment.getReturnAddress1());
                thirdPartyBilling.setCity(shipment.getReturnCity());
                thirdPartyBilling.setState(shipment.getReturnState());
                thirdPartyBilling.setPostalCode(shipment.getReturnPostalCode());
                thirdPartyBilling.setCountry(shipment.getReturnCountry());
                //add billing to shipment
                shipment.setThirdPartyBilling(thirdPartyBilling);
                //set terms to THIRD_PARTY
                shipment.setShipTerms("THIRD_PARTY");
            }
            if (m.get(xCLIENT_FEDEX_ACCOUNT).toString().length() > 0 && shipment.getShipMethod().toUpperCase().contains("FEDEX")) {
                ThirdPartyBilling thirdPartyBilling = new ThirdPartyBilling();
                thirdPartyBilling.setAccount(m.get(xCLIENT_FEDEX_ACCOUNT).toString());
                thirdPartyBilling.setCompany(shipment.getReturnCompany());
                thirdPartyBilling.setAddress1(shipment.getReturnAddress1());
                thirdPartyBilling.setCity(shipment.getReturnCity());
                thirdPartyBilling.setState(shipment.getReturnState());
                thirdPartyBilling.setPostalCode(shipment.getReturnPostalCode());
                thirdPartyBilling.setCountry(shipment.getReturnCountry());
                //add billing to shipment
                shipment.setThirdPartyBilling(thirdPartyBilling);
                //set terms to THIRD_PARTY
                shipment.setShipTerms("THIRD_PARTY");
            }
        }

        //LYFT_DC6 is only used for SmartPost and we need to clear the third party info.
        if(shipment.getShippingAccountName().equals("LYFT_DC6")){
            shipment.setShipTerms("SHIPPER");
            shipment.setThirdPartyBilling(null);
        }
        //LYFT_DC6 is only used for SmartPost and we need to clear the third party info.
        if(shipment.getShippingAccountName().equals("DC7_QVC_JB")){
            shipment.setShipTerms("SHIPPER");
            shipment.setThirdPartyBilling(null);
        }

        //Shipments for Bumbleride going International Economy get this account. Clients wants all Duties and Taxes paid
        if(shipment.getShippingAccountName().equals("BUMBLE_DC6")){
            DutiesAndTaxes dutiesAndTaxes = new DutiesAndTaxes();
            dutiesAndTaxes.setTerms("SHIPPER");
            shipment.setDutiesAndTaxes(dutiesAndTaxes);
            shipment.setShipTerms("SHIPPER");
            shipment.setThirdPartyBilling(null);
        }

        //Duties and Taxes
        if(m.get(xDTPFLAG).toString().equals("1")){
            DutiesAndTaxes dutiesAndTaxes = new DutiesAndTaxes();
            dutiesAndTaxes.setTerms("SHIPPER");
            shipment.setDutiesAndTaxes(dutiesAndTaxes);
        }

        //Delivery Options
        if(m.get(xSS_SATURDAY).toString().equals("true")){
            log.debug("Setting Saturday Delivery");
            DeliveryOptions deliveryOptions = new DeliveryOptions();
            deliveryOptions.setSaturdayDelivery(true);
            shipment.setDeliveryOptions(deliveryOptions);
        }
        if(m.get(xSIGNATURE_REQUIRED).toString().equals("true")){
            log.debug("Setting Signature Required");
            if(null == shipment.getDeliveryOptions()){
                DeliveryOptions deliveryOptions = new DeliveryOptions();
                deliveryOptions.setSignatureRequired(true);
                shipment.setDeliveryOptions(deliveryOptions);
            }else{
                shipment.getDeliveryOptions().setSignatureRequired(true);
            }
        }
        if(m.get(xSS_CALL_TAG).toString().equals("true")){
            log.debug("Setting Call Tag");
            if(null == shipment.getDeliveryOptions()){
                DeliveryOptions deliveryOptions = new DeliveryOptions();
                deliveryOptions.setCallTag(true);
                shipment.setDeliveryOptions(deliveryOptions);
            }else{
                shipment.getDeliveryOptions().setCallTag(true);
            }
        }


        //set Electronic invoice
        if(isInternational(shipment)){
            shipment.setElectronicCommercialInvoice(1);
        }




    }

    public boolean oversize(List<Double> dims) throws Exception{
       if(dims.size()<3) throw new Exception("Invalid Number of dims");
        Collections.sort(dims);
        if(dims.get(2)+(2*dims.get(1))+(2*dims.get(0))>51) return true;
        if((dims.get(0)*dims.get(1)+dims.get(2))>=1728) return true;

        return false;
    }

    public String lookupFlatRateServiceCode(String levelCode, int clientId, String groupName, String facility,List<Double> dims) throws Exception{
        String method = "";
        if(levelCode.equalsIgnoreCase("COM_OWD_FLATRATE_INTL_PRIDDP")) return("COM_OWD_FLATRATE_INTL_PRIDDP");
        if(levelCode.equalsIgnoreCase("COM_OWD_FLATRATE_INTL_PRIDDU")) return("COM_OWD_FLATRATE_INTL_PRIDDU");
//        if(levelCode.equalsIgnoreCase("COM_OWD_FLATRATE_MM")) return("COM_OWD_FLATRATE_MM");
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("execute owd_ship_service_flatrate_groupLookup :levelCode,:clientId,:groupName,:facility");
            q.setParameter("levelCode",levelCode);
            q.setParameter("clientId",clientId);
            q.setParameter("groupName",groupName);
            q.setParameter("facility", facility);
            List l = q.list();
            if(l.size()>0){
                method = l.get(0).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //removed for advanced
        /*if(method.contains("GROUND")||method.contains("ECONOMY")){
            if(!method.contains("INTL")&&!method.contains("MAX")&&!method.contains("STANDARD")) {
                if (oversize(dims)) {
                    method = method + "_LARGE";
                }
            }
        }*/


        return method;
    }

    private static String LookupShipError(String e,ShipShipment shipment){
        log.debug(e);
        String finalError = "";
        boolean fixApplied = false;
        //USPS First Class to Priority
        log.debug(shipment.getShipService());
        log.debug(shipment.getShipMethod());
        if(e.equals("Rate Error: Max weight exceeded for this service.")&& shipment.getShipMethod().equals("USPS First-Class Mail")){
            shipment.setShipMethod("USPS Priority Mail");
            shipment.setShipService("TANDATA_USPS.USPS.PRIORITY");
            shipment.getEvents().add("System updated Ship Method to Priority Mail");
            fixApplied = true;
        }
        if(e.toLowerCase().contains("the shipping weight is invalid for the fedex ground residential service")){
            shipment.setShipMethod("FedEx Home Delivery");
            shipment.setShipService("FDX.HD");
            shipment.getEvents().add("System updated Ship Method to FedEx Home Delivery");
            fixApplied = true;
        }
        if(e.contains("Invalid service type. (Area not serviced?)") && shipment.getShipMethod().equalsIgnoreCase("FedEx Standard Overnight")){
            shipment.setShipMethod("FedEx Priority Overnight");
            shipment.setShipService("FDX.PRI");
            shipment.getEvents().add("Update Ship Method from Standard to Priority because area not supported");
            fixApplied = true;
        }
        if(e.contains("Route Error: WWV:Service not available to this destination (as per WWVCX137)") && shipment.getShipMethod().equalsIgnoreCase("Overnight")){
            shipment.setShipMethod("UPS Next Day Air");
            shipment.setShipService("CONNECTSHIP_UPS.UPS.NDA");
           // shipment.getEvents().add("Update Ship Method from Next Day Air Saver to Next Day Air because area not supported");
            fixApplied = true;
        }
        if(e.contains("WWV:Saturday Service not available to destination (as per WWVCX139)")){
            shipment.getEvents().add("Removed Saturday Deliver Flag because serivce is not available to Destination");
            shipment.getDeliveryOptions().setSaturdayDelivery(false);
            fixApplied = true;
        }




        if(!fixApplied && finalError.length()==0) return e;
        return finalError;
    }



    public ShipShipmentResponse processShipmentAndAttemptFixes(ShipShipment shipment)throws Exception{
        ShipShipmentResponse response = new ShipShipmentResponse();
        boolean success = false;
        int tries = 0;

        while (!success){
            try{
                tries++;
                //send Shipment off
                response = SendShipment(shipment);

                success = true;
            }catch (Exception e){
                //Check for Errors, if the returned String has a value, throw that as final error
                String finalError = LookupShipError(e.getMessage(),shipment);
                if(finalError.length()>0){
                    throw new Exception(finalError);
                }
                if(tries>10){
                    throw new Exception("Too Many retries");
                }
            }

        }



        return response;
    }

    private static ShipShipmentResponse SendShipment(ShipShipment shipment) throws Exception{
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        log.debug(gson.toJson(shipment));
        String token= OWDShippingAPIToken.grabNewToken();
        ShipService sService = OWDShippingAPIServiceGenerator.createService(ShipService.class, token);

        Call<ShipShipmentResponse> callSync = sService.sendShipment(shipment);
        Response<ShipShipmentResponse> response = callSync.execute();
        log.debug(response.code());
        ShipShipmentResponse shipmentResponse;
        if(response.code() == 200) {
             shipmentResponse = response.body();
            //copy relavant info to response
            shipmentResponse.setOrderId(shipment.getOrderId());
            shipmentResponse.setClientId(shipment.getClientId());
            shipmentResponse.setFacility(shipment.getFacility());
            shipmentResponse.setGroupName(shipment.getGroupName());

            if( null!= shipmentResponse.getShipError() && shipmentResponse.getShipError().length() > 0) {
                throw new Exception(shipmentResponse.getShipError());
            }



            log.debug(gson.toJson(shipmentResponse));
            log.debug("about to post Shipments");
            //If international or APO we need to set customs to 1 for billing
            int customs = 0;
            if(isInternational(shipmentResponse)||isAPOFPO(shipment)){
             customs = 1;
            }

            PostShipments(shipmentResponse,shipment.isFlatRate(),customs);
            log.debug("About to look for events");
            AddEvents(shipment);
        }else{
            log.debug(response.message());
            //todo better error handling with proper messages
            throw new Exception(response.message());
        }

        return shipmentResponse;
    }

    private static void AddEvents(ShipShipment shipShipment){
        log.debug("Checking for Events to post");
        if(null!= shipShipment.getEvents()&& shipShipment.getEvents().size()>0) {
            for (String s : shipShipment.getEvents()) {
                try {
                    Event.addOrderEvent(new Integer(shipShipment.getOrderId()), Event.kEventTypeHandling, s, "Manifesting Server");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private static void PostShipments(ShipShipmentResponse response,boolean flatRate,int customs)throws Exception{
        if(null!= response.getShipService()&&response.getShipService().equalsIgnoreCase("BWTI_UPS.UPS.MIR")){
            return;
        }
        int lineIndex = 1;
        for(Package p:response.getPackages()){
            TrackingBean tb = new TrackingBean();
            tb.setOrderFkey(Integer.parseInt(response.getOrderId()));
            tb.setCustoms(customs);
            tb.setLineIndex(lineIndex);
            tb.setExternalId(p.getVoidPackageId());
            tb.setLocation(response.getFacility());

            //When terms are third party set the flag
            if(response.getShipTerms().equals("THIRD_PARTY")){
                tb.setThirdParty("1");
            }else{
                //If it is not an OWD Shipper, set Third Party Flag
                if(response.getShippingAccountName().startsWith("OWD")){
                    tb.setThirdParty("0");
                }else{
                    tb.setThirdParty("1");
                }

            }
            if(response.isSaturdayDelivery()){
                tb.setIsSaturday("1");
            }else{
                tb.setIsSaturday("0");
            }
            if(p.isSignatureRequested()){
                tb.setIsSignature("1");
            }else{
                tb.setIsSignature("0");
            }

            if(isPO(response)&&(null!= response.getShipService() && response.getShipService().equals("BWTI_USPS.USPS.EXPPC"))){

                    tb.setIsExpressPO("1");

            }else{
                tb.setIsExpressPO("0");
            }


            if(null!= p.getInsuranceAmount()){
                tb.setInsuredAmount(p.getInsuranceAmount()+"");
            }else{
                tb.setInsuredAmount("0");
            }

            if(null!=p.getPricing()){
                if(null!=p.getPricing().getInsuranceCost()) {
                    tb.setInsuranceCost(p.getPricing().getInsuranceCost());
                }
            }else{
                tb.setInsuranceCost("0");
            }

            //check for prcing Third party may not have any 
            //Also don't record pricing when flat rate
            //We record amount if ThirdParty is set to 0 above. Actual accounts like QVC will have an amount but still need to be 0.
            if(null!= p.getPricing() && !flatRate && tb.getThirdParty().equals("0")) {
                if(response.shipMethod.contains("DHL Express Worldwide")){
                    tb.setTotalBilled(Double.parseDouble(p.getPricing().getTotal()));
                }else {
                    tb.setTotalBilled(Double.parseDouble(p.getPricing().getBasePrice()));
                }
            }else{
                tb.setTotalBilled(0);
            }


            tb.setPackBarcode(p.getPackageReference());
            tb.setTrackingNumber(p.getTrackingNumber());
            tb.setWeight(p.getWeight());
            ShippingUtilities shippingUtilities = new ShippingUtilities();
            shipServiceModel method = shippingUtilities.getOwdShipCodesFromProShipCodes(response.getShipService(),tb.getWeight(), response.getShipToCountry());
            if (flatRate) {
                Integer dimWeight = getCustomDimWeightForClientByPackage(p.getPackageReference(),method.getMethodCode());
                if (dimWeight > 0) {
                    if (dimWeight > Math.ceil(p.getWeight())) {
                        tb.setBilledWeight(dimWeight);
                    }
                } else {
                    if (p.getBilledWeight() > Math.ceil(p.getWeight())) {
                        tb.setBilledWeight(p.getBilledWeight());
                    }
                }
            } else {
                if (p.getBilledWeight() > Math.ceil(p.getWeight())) {
                    tb.setBilledWeight(p.getBilledWeight());
                }

            }
            GsonBuilder builder = new GsonBuilder();

            Gson gson = builder.create();

            //Store Labels as Json object because there can be multiple label elements.
            tb.setLabel(gson.toJson(p.getLabel()));

            //Store detailed pricing as Json to be used later
            if(null!= p.getPricing()&&null!=p.pricing.getDetailedPricing()){
                tb.setDetailedPricing(gson.toJson(p.pricing.getDetailedPricing()));
            }
            //todo lookup Methods/Codes
            tb.setCarrierCode(response.getShipService());
            tb.setServiceCode(response.getShipService());
            try {
                TrackingInfo ti = new TrackingInfo();
                ti.postTrackingInfo(tb, "OWDShipAPI", response.getShipToCountry());
            }catch (Exception e){
                e.printStackTrace();
                new LogableException(gson.toJson(tb),"Error posting Tracking",response.getShipperReference(),response.getClientId(), LogableException.errorTypes.UNDEFINED_ERROR);
                throw new Exception(e.getMessage());
            }

            lineIndex++;
        }
        HibUtils.commit(HibernateSession.currentSession());


    }

    public static Integer getCustomDimWeightForClientByPackage(String packageBarcode,String methodCode){
        Integer dimWeight = 0;
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("execute getClientsCustomDimWeightFromPackageBarcodeMethod :packageBarcode, :methodCode");
            q.setParameter("packageBarcode",packageBarcode);
            q.setParameter("methodCode",methodCode);

            List results = q.list();
            if(results.size()>0) {
                dimWeight = Integer.parseInt(results.get(0).toString());

            }


        }catch (Exception e){
           e.printStackTrace();
        }
        return dimWeight;
    }


    /**
     * Checks for errors that can be caught before we send the shipment to the server.
     *
     *
     * @param shipment Shipment to check for Errors
     * @throws Exception when errors found, like order has already been shipped
     */
    public void CheckShipmentForErrors(ShipShipment shipment) throws Exception{

        if(null!= shipment.getOrderTrackFkey()&& shipment.getOrderTrackFkey().length()>1){
            throw new Exception("This package has already been shipped! Verify package ID and barcode. ");
        }
        if(shipment.isDcHold()){
            throw new Exception("This order is on DC Hold - DC Hold must be cleared before shipping!");
        }
        if (shipment.isShippingHold()) {
            throw new Exception("This client is on shipping hold and cannot ship! Place in shipping hold area.");
        }

        // Sean 2020/02/27 case 765287
        if (isInternational(shipment)){
            if(calcOrderTotal(shipment) > 2500 ){
                if (null == shipment.getAesTransactionNumber()|| shipment.getAesTransactionNumber().isEmpty()){
                    throw new Exception ("The order value is greater than $2500. Please obtain AES number");
                }
            }
        }


        //For certain vendors for clients we need to set residential to false to prevent fedex Home Delivery.

        if(shipment.getGroupName().equalsIgnoreCase("DSG")){
            if(shipment.getClientId().equalsIgnoreCase("680")) {
                if(shipment.getShipMethod().equalsIgnoreCase("FedEx Ground")){
                    shipment.setResidential(false);
                }

            }
        }
        //End FedEx Residential Change

    }

    /**
     * Sean 2020/03/02
     * loop thru each item to add all values (loadLineInfo line 216)
     * @param shipment
     * @return order total value
     */
    public static double calcOrderTotal(ShipShipment shipment){

        double orderValue =0;
        int qty = 0;
        String sku="";
        double unitPrice = 0;
        for(Package p:shipment.getPackages()){
            if(null!=p.getLineInfo()){
            for(LineItemInfo li : p.getLineInfo()) {
                qty = li.getQuantity();
                unitPrice = li.getSingleUnitValue();
                orderValue = orderValue + (unitPrice * qty);
            }
            }

        }
        log.debug("Sum: " + orderValue);
        return orderValue;
    }


    /**
     *
     * @param packBarcode Barcode of the package to load data for
     * @return return a list of Objects that can be loaded as a Map to pull data from
     * @throws Exception Sql Exceptions
     */
    public List getPackageDataFromDatabse(String packBarcode) throws Exception{
        if(packBarcode.toUpperCase().startsWith("P")) {
            String orderBarcode = packBarcode.substring(packBarcode.indexOf("*"), packBarcode.lastIndexOf("*") + 1);
            Query q = HibernateSession.currentSession().createSQLQuery("execute sp_loadShipDataFromPackage :packageBarcode, :orderBarcode");
            q.setParameter("packageBarcode", packBarcode);
            q.setParameter("orderBarcode", orderBarcode);
            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            log.debug("Loading Data for: " + packBarcode + " / " + orderBarcode);

            return q.list();
        }else{

            if(!packBarcode.startsWith("*")) packBarcode = "*"+packBarcode+"*";
            Query callTag = HibernateSession.currentSession().createSQLQuery("exec dbo.sp_virtualPackCalltag :orderBarcode");
            callTag.setParameter("orderBarcode",packBarcode);
            callTag.executeUpdate();

            Query q = HibernateSession.currentSession().createSQLQuery("execute sp_loadShipDataFromOrder :orderBarcode");

            q.setParameter("orderBarcode", packBarcode);
            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            log.debug("Loading Data for: " + packBarcode + " / via orderNum" );
            return q.list();
        }


    }

    /**
     *
     * @param country Country to lookup Code for
     * @param city City that shipment is going to. Certain cities need a different IANA code
     * @return 2 character IANA country code
     * @throws Exception when country cannot be found
     */
    public String getIANACountry(String country,String city) throws Exception{
        log.debug("getting countires For " + country);
        //fix funky country stuff
        if(country.equals("KYRGHYZSTAN")) country = "KYRGYZSTAN";
        if(country.equalsIgnoreCase("NETHERLANDS_ANTILLES")){
            if(city.equalsIgnoreCase("Curacao")){
                return "CW";
            }
        }

        String sql = " select country_code from countries where owdName = :name";
        try{

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("name",country.toUpperCase());
            List results = q.list();
            if (results.size()>0){

                country =  (String) results.get(0);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (country.length()>2) throw new Exception("Unable to find 2 digit country code please contact IT");

        return country;

    }

    public List<INTLDOCDATA> loadNeededInternationalDocs(ShipShipmentResponse response){

        List<INTLDOCDATA> docs = new ArrayList<INTLDOCDATA>();
        if(isInternational(response)) {
            if (null!= response.getElectronicCommercialInvoiceStatus()&&!response.getElectronicCommercialInvoiceStatus().equals("1")) {
                try {
                    byte[] pdfData = JasperReportPrintManager.getCommInvoicePdf(response.getShipperReference());
                    INTLDOCDATA data = new INTLDOCDATA();
                    data.setCopies_Needed("3");
                    data.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(pdfData));
                    docs.add(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return docs;
    }

    /**
     * Returns true is the shipment is not going to the US
     * @param shipment Shipment to check if it is international
     * @return boolean
     */
    public static boolean isInternational(ShipShipmentResponse shipment){

        return !shipment.getShipToCountry().equals("US");
    }
    /**
     * Returns true is the shipment is not going to the US
     * @param shipment Shipment to check if it is international
     * @return boolean
     */
    public static boolean isInternational(ShipShipment shipment){

        return !shipment.getShipToCountry().equals("US");
    }

    public static boolean isPO(ShipShipmentResponse response) throws Exception{
        Pattern p = Pattern.compile("[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]");
        Matcher m = p.matcher(response.getShipToAddress1());
        if (m.find()) {

            return true;
        }
        m = p.matcher(response.getShipToAddress2());
        if (m.find()) {

            return true;
        }


        return false;

    }

    public static boolean isPOAPOFPO(ShipShipment shipment) throws Exception{
        if(isAPOFPO(shipment)){
            return true;
        }

        Pattern p = Pattern.compile("[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]");
        Matcher m = p.matcher(shipment.getShipToAddress1());
        if (m.find()) {

            return true;
        }
        m = p.matcher(shipment.getShipToAddress2());
        if (m.find()) {

            return true;
        }


        return false;

    }

    public static boolean isAPOFPO(ShipShipment shipment) throws Exception {

        String stater = shipment.getShipToState();

        String city = shipment.getShipToCity();
        String country = shipment.getShipToCountry();
        log.debug("In is apo this is country: " + country);
        log.debug(stater);
        log.debug(city);


        if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES")|| country.equalsIgnoreCase("US")) {
            //////log.debug("got cs::"+city+"::"+stater+"::");

            if (stater.equalsIgnoreCase("AE") || stater.equalsIgnoreCase("AA") || stater.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO") || city.equalsIgnoreCase("DPO")) {


                return true;

            } else {

                return false;

            }
        }
        return false;

    }

    public String getShippingAccount(ShipShipment shipment) throws Exception{
         /*if(clientId == 471 && groupName.equalsIgnoreCase("walmartdsv")&&shipMethod.contains("FEDEX.SP")){
           return "TEST";
       }*/
        if(shipment.getClientId().equals("529") &&shipment.getShipService().contains("FEDEX.SP")){
            return "LYFT_DC6";
        }
        if(shipment.getClientId().equals("626") && shipment.getGroupName().equalsIgnoreCase("qvc")&&shipment.getShipService().contains("UPS")){
            return "DC7_QVC_JB";
        }
        if(shipment.getClientId().equals("575")&&shipment.getShipService().equals("TANDATA_FEDEXFSMS.FEDEX.IECO")){
            return "BUMBLE_DC6";
        }


        if(shipment.getFacility().equalsIgnoreCase("DC1")) return "OWD_DC1";
        if(shipment.getFacility().equalsIgnoreCase("DC6")) return "OWD_DC6";
        if(shipment.getFacility().equalsIgnoreCase("DC7")) return "OWD_DC7";

        throw new Exception("Unable to find mapping for Facility: " + shipment.getFacility());
    }

    public String convertClobToString(Clob clob) throws IOException, SQLException {
        Reader reader = clob.getCharacterStream();
        int c = -1;
        StringBuilder sb = new StringBuilder();
        while((c = reader.read()) != -1) {
            sb.append(((char)c));
        }

        return sb.toString();
    }

    //region shipment columns

    private static final String xCONTACT = "contact";
    private static final String xSHIP_COMPANY_NAME = "ship_company_name";
    private static final String xSHIP_ADDRESS_ONE = "ship_address_one";
    private static final String xSHIP_ADDRESS_TWO = "ship_address_two";
    private static final String xSHIP_CITY = "ship_city";
    private static final String xSHIP_STATE = "ship_state";
    private static final String xSHIP_ZIP = "ship_zip";
    private static final String xSHIP_COUNTRY = "ship_country";
    private static final String xSHIP_PHONE = "ship_phone";
    private static final String xRETURN_NAME = "return_name";
    private static final String xRETURN_COMPANY = "return_company";
    private static final String xRETURN_ADDRESS1 = "return_address1";
    private static final String xRETURN_ADDRESS2 = "return_address2";
    private static final String xRETURN_CITY = "return_city";
    private static final String xRETURN_STATE = "return_state";
    private static final String xRETURN_POSTALCODE = "return_postalCode";
    private static final String xRETURN_COUNTRY = "return_country";
    private static final String xRETURN_PHONE = "return_phone";
    private static final String xSHIPPER_THIRDPARTY_NAME = "shipper_thirdParty_name";
    private static final String xSHIPPER_THIRDPARTY_COMPANY = "shipper_thirdParty_company";
    private static final String xSHIPPER_THIRDPARTY_ADDRESS1 = "shipper_thirdParty_address1";
    private static final String xSHIPPER_THIRDPARTY_ADDRESS2 = "shipper_thirdParty_address2";
    private static final String xSHIPPER_THIRDPARTY_CITY = "shipper_thirdParty_city";
    private static final String xSHIPPER_THIRDPARTY_STATE = "shipper_thirdParty_state";
    private static final String xSHIPPER_THIRDPARTY_POSTALCODE = "shipper_thirdParty_postalCode";
    private static final String xSHIPPER_THIRDPARTY_COUNTRY = "shipper_thirdParty_country";
    private static final String xSHIPPER_THIRDPARTY_PHONE = "shipper_thirdParty_phone";
    private static final String xSHIPPER_THIRDPARTY_ACCOUNT = "shipper_thirdParty_Account";
    private static final String xSS_RESIDENTIAL = "ss_residential";
    private static final String xAES_ITN = "aes_itn";

    private static final String xSS_SATURDAY = "ss_saturday";
    private static final String xSHIPPER_REFERENCE = "shipper_reference";
    private static final String xCLIENT_REFERENCE = "client_reference";
    private static final String xCARR_SERVICE = "carr_service";
    private static final String xCARR_SERVICE_REF_NUM = "carr_service_ref_num";
    private static final String xCARR_FREIGHT_TERMS_REF_NUM = "carr_freight_terms_ref_num";
    private static final String xORDER_ID = "order_id";
    private static final String xCLIENT_UPS_ACCOUNT = "client_UPS_account";
    private static final String xCLIENT_FEDEX_ACCOUNT = "client_FedEx_Account";
    private static final String xSS_COD = "ss_cod";
    private static final String xCOD_CHARGE = "cod_charge";
    private static final String xSS_CALL_TAG = "ss_call_tag";
    private static final String xCALL_TAG = "call_tag";
    private static final String xCLIENT_FKEY = "client_fkey";
    private static final String xRET_ADDR_1 = "ret_addr_1";
    private static final String xRET_ADDRE_2 = "ret_addre_2";
    private static final String xUSEDC_FIRSTCLASS = "useDC_firstclass";
    private static final String xON_SHIPPING_HOLD = "on_shipping_hold";
    private static final String xORDER_TOTAL = "order_total";
    private static final String xDC_HOLD = "dc_hold";
    private static final String xSIGNATURE_REQUIRED = "signature_required";
    private static final String xFEDEXCUSTOMSACCOUNT = "fedexcustomsaccount";
    private static final String xPO_NUM = "po_num";
    private static final String xGROUP_NAME = "group_name";
    private static final String xSHIPTOEMAIL = "shipToEmail";
    private static final String xDTPFLAG = "DTPFlag";
    private static final String xORDERDISCOUNT = "orderDiscount";
    private static final String xORDER_DECLARED_VALUE = "order_declared_value";

    //endregion
    //region Item columns


    private static final String xiITEM_CUSTOMS_VALUE = "item_customs_value";
    private static final String xiITEM_CUSTOM_DESCRIPTION = "item_custom_description";
    private static final String xiDEFAULT_CUSTOMS_DESC = "default_customs_desc";
    private static final String xiINVENTORY_NUM = "inventory_num";
    private static final String xiPACK_QUANTITY = "pack_quantity";
    private static final String xiPRICE = "price";
    private static final String xiDESCRIPTION = "description";
    private static final String xiINVENTORYITEM_CUSTOMS_DESCRIPTION = "inventoryItem_customs_description";
    private static final String xiINVENTORYITEM_CUSTOMS_VALUE = "inventoryItem_customs_value";
    private static final String xiDOCUMENTS_ONLY = "documents_only";
    private static final String xiORIGIN_COUNTRY = "origin_country";
    private static final String xiITEMWEIGHT = "itemWeight";
    private static final String xiIS_INSERT="is_insert";

    //endregion
    //region package columns
    private static final String xpPACKTYPE = "packType";
    private static final String xpPACKS_SHIPPEDADD = "packs_shippedAdd";
    private static final String xpNUMBER_OF_PACKAGES = "number_of_packages";
    private static final String xpDIM_DEPTH = "dim_depth";
    private static final String xpDIM_HEIGHT = "dim_height";
    private static final String xpDIM_WIDTH = "dim_width";
    private static final String xpPACK_BARCODE = "pack_barcode";
    private static final String xpSHIPPED_PACKS = "shipped_packs";
    private static final String xpORDER_TRACK_FKEY = "order_track_fkey";
    private static final String xpRID = "RID";
    private static final String xpRBOX = "RBOX";
    private static final String xpRCID = "RCID";
    private static final String xpBOXID = "BoxID";
    private static final String xpEXTERNALPACKAGE = "ExternalPackage";
    private static final String xpEXTERNALAPIKEY = "ExternalAPIKey";
//endregion
}
