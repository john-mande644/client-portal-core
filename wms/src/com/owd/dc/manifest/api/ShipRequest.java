package com.owd.dc.manifest.api;

import com.owd.LogableException;
import com.owd.OWDShippingAPI.OWDShipRequest;
import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPRESPONSE;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.connectship.xml.api.OWDShippingResponse.INTLDOCDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingRequest.SHIPREQUEST;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGE;
import com.owd.core.TagUtilities;
import com.owd.core.business.order.packing.DimensionalWeight;
import com.owd.core.business.order.zoneUtilities.FlatRateShipping;
import com.owd.core.business.order.zoneUtilities.ZoneLookup;
import com.owd.core.business.order.zoneUtilities.shipMethodUpdate;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.manifest.BluePackage.BluePackageApi;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.OWDEasyPostShipment;
import com.owd.dc.manifest.ExternalApis.deliverr.DeliverrApi;
import com.owd.dc.manifest.OSMWorldwide.OSMShipment;
import com.owd.dc.manifest.OSMWorldwide.OSMUtils;
import com.owd.dc.manifest.api.internal.ConnectShipWebServices;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.manifest.api.internal.CarrierInfo;
import com.owd.dc.manifest.gssShipment;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OWDPackage;
import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdShipMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 8:52:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShipRequest {
    protected final static Logger log = LogManager.getLogger();

    public static SHIPRESPONSE handle(SHIPREQUEST shipRequest, boolean testing, double api_version, String User) throws Exception

    {
        OwdClient client;
        int clientId = 55;
        SHIPRESPONSE response = new SHIPRESPONSE();
        String shipMethod = "";
        String shipcode = "";
        String orderId = "";
        String apiKey = "";

        int packageCount = shipRequest.getPACKAGELIST().getPACKAGE().size();
        System.out.println("This is the package Count: " + packageCount);
        List<AMPConnectShipShipment> packlist = new ArrayList<AMPConnectShipShipment>();
        //System.out.println("got ship request");
        int packIndex = 1;
        boolean apo = false;
        String facility = shipRequest.getLOCATIONCODE();

        if (shipRequest.getSHIPPER().length() == 3 & shipRequest.getSHIPPER().startsWith("D")) {
            shipRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(shipRequest.getSHIPPER()));
        }

        log.debug(shipRequest.getPACKAGELIST().getPACKAGE().get(0).getBARCODE());
        //todo Lookup for New System or To continue with Current
        //Currenlty we don't do Deliverr labels and Passport through new API
        Query q = HibernateSession.currentSession().createSQLQuery("execute differentShipSystemLookup :packBarcode");
        q.setParameter("packBarcode",shipRequest.getPACKAGELIST().getPACKAGE().get(0).getBARCODE());
        List l = q.list();
        if(l.size()>0&&shipRequest.getPACKAGELIST().getPACKAGE().get(0).getBARCODE().toUpperCase().startsWith("P")){


                if(l.get(0).toString().equals("0")){
                    //We need to use the new OWD Shipping Api.

                    OWDShipRequest OWDship = new OWDShipRequest();
                    List<SHIPMENT> shipmentsList = OWDship.processShipmentReturnXML(shipRequest);
                    response.getSHIPMENT().addAll(shipmentsList);
                    return response;


                }


        }else{
            if (!shipRequest.getPACKAGELIST().getPACKAGE().get(0).getBARCODE().toUpperCase().startsWith("P")){
                OWDShipRequest OWDship = new OWDShipRequest();
                List<SHIPMENT> shipmentsList = OWDship.processShipmentReturnXML(shipRequest);
                response.getSHIPMENT().addAll(shipmentsList);
                return response;
            }
        }

        for (PACKAGE pack : shipRequest.getPACKAGELIST().getPACKAGE()) {

            AMPConnectShipShipment shipment = new AMPConnectShipShipment();
            System.out.println("Barcode sent to shipRequest: " + pack.getBARCODE());
            System.out.println("This is the shipper" + shipRequest.getSHIPPER() + "XXXXXXXXXXXXXXXXXXXXXXXXXX");
            shipment.loadOrderFromOrderReference(pack.getBARCODE(), true, shipRequest.getLOCATIONCODE());

            shipRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(facility, shipment.getClientID(), Integer.parseInt((String) shipment.getValue("BOX_ID")), shipment.getAssignedServiceCode(), shipment.getValueAsString("GROUPNAME")));
            apiKey = shipment.getValueAsString(ShipConfig.EXTERNAL_API_KEY);
            shipment.setValue("CURRENT_FACILITY", shipRequest.getLOCATIONCODE());
            shipment.setValue(ShipConfig.CURRENT_SHIPPER, shipRequest.getSHIPPER());
            shipment.setValue(ShipConfig.SHIPPER, shipRequest.getSHIPPER());
            shipment.setValue("LABELPRINTER_CODE", shipRequest.getLABELPRINTER());
            shipment.setValue(ShipConfig.WEIGHT, new Double(pack.getWEIGHT_LBS()));
            shipment.setValue(ShipConfig.NOFN_SEQUENCE, ((String) shipment.getValue("PACKAGE_BARCODE")).substring(((String) shipment.getValue("PACKAGE_BARCODE")).indexOf("b") + 1));
            shipment.setValue(ShipConfig.NOFN_TOTAL, packageCount);
            packlist.add(shipment);
            clientId = shipment.getClientID();
            shipMethod = shipment.getAssignedCarrierCode();
            shipcode = shipment.getAssignedServiceCode();
            orderId = shipment.getValueAsString("OWDORDERID");
            if (shipcode.equals("TANDATA_USPS.USPS.EXPR_ADDR")) {
                shipment.setAssignedServiceCode("TANDATA_USPS.USPS.EXPR");
            }
            apo = shipment.isAPOFPO();
        }
        // multipiece smartpost shipments look for packages and weights set to same ship method
        if (packlist.size() > 1) {
            boolean changeMethod = false;
            if (packlist.get(0).getAssignedServiceCode().contains("FEDEX.SP_")) {
                System.out.println("Multipiece smartpost");
                int overPound = 0;
                for (AMPConnectShipShipment ship : packlist) {
                    if (Double.parseDouble(ship.getValueAsString(ShipConfig.WEIGHT)) > 1d) {
                        System.out.println("over a pound");
                        overPound++;

                    }
                }

                if (overPound == 0 || overPound == packlist.size()) {
                    System.out.println("All under or over nothing to do");
                } else {
                    System.out.println("Not matching up change shipmethods");
                    for (int i = 0; i < packlist.size(); i++) {
                        packlist.get(i).setAssignedServiceName("FEDEX SMARTPOST PARCEL SELECT");
                        packlist.get(i).setAssignedServiceCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                    }
                }
            }

        }

        /*if(packlist.get(0).getAssignedServiceCode().contains("TANDATA_USPS.USPS.I_FIRST")){
            boolean overFour = false;
            for(AMPConnectShipShipment ship:packlist){
                if(Double.parseDouble(ship.getValueAsString(ShipConfig.WEIGHT))>4d){
                    System.out.println("over a 4 pound");
                    overFour = true;

                }
            }
            if (overFour){
                for(int i = 0;i<packlist.size();i++){
                    packlist.get(i).setAssignedServiceName("USPS Priority Mail International");
                    packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.I_PRIORITY");
                }
            }
        }*/
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        System.out.println();
        System.out.printf("this is the shipMethod: %s \r\n", shipMethod);
        System.out.printf("This is the Shipping method code we are using %s\r\n", shipcode);
        packIndex = 1;
        Map<String, MultiPackageShipment> pendingShipmentsMap = new TreeMap<String, MultiPackageShipment>();
        List<String> gssShipMethodList = new ArrayList<String>();
        client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientId);
        log.debug("APIKey", apiKey);
        //check for external api key
        if (apiKey.length() > 0 || shipcode.contains("EASYPOST") || shipcode.contains("PUROLATOR")) {

            if (!shipcode.contains("USPS")) {
                OWDEasyPostShipment easy = new OWDEasyPostShipment(packlist, HibernateSession.currentSession(), client, shipRequest.getLOCATIONCODE(), apiKey, true);


                response.getSHIPMENT().addAll(easy.shipPackages(HibernateSession.currentSession(), shipRequest.getLOCATIONCODE()));
                HibUtils.commit(HibernateSession.currentSession());
                return response;
            }

        }



        List<String> DHLShip = new ArrayList<>();
        DHLShip.add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP");
        DHLShip.add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND");
        DHLShip.add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP");
        DHLShip.add("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND");

        if (DHLShip.contains(shipcode)) {
            shipMethodUpdate smu = ZoneLookup.checkForShipMethodUpdateByZone(orderId, clientId, packlist.get(0).getValue(ShipConfig.WEIGHT).toString(), packlist.get(0).isPOAPOFPO());
            if (smu.isChange()) {
                shipcode = smu.getCode();
                log.debug("Updating Method per Shipping Matrix");
                for (int i = 0; i < packlist.size(); i++) {

                    packlist.get(i).setAssignedServiceCode(smu.getCode());
                }
            }
        }

        if(shipcode.contains("COM_OWD_FLATRATE")){
            String group = packlist.get(0).getValueAsString("GROUPNAME");
            double weight = Double.parseDouble(packlist.get(0).getValue(ShipConfig.WEIGHT).toString());
            int rweight = 0;
            if(weight > 1d){
                rweight = (int) Math.ceil(weight);
            }
            boolean apoShip = packlist.get(0).isPOAPOFPO();
            if(apo&&shipcode.equalsIgnoreCase("COM_OWD_FLATRATE_NDA")){
                log.debug("We have overnight and apo. Logging for later verification");
                StringBuilder sb = new StringBuilder();
                sb.append("Address 1: "+packlist.get(0).getConsigneeNA().getAddress1()+"\r\n");
                sb.append("Address 2 "+packlist.get(0).getConsigneeNA().getAddress2()+"\r\n");
                sb.append("State: "+packlist.get(0).getConsigneeNA().getStateProvince()+"\r\n");
                sb.append("City: "+packlist.get(0).getConsigneeNA().getCity()+"\r\n");
                log.debug(sb.toString());
                new LogableException(sb.toString(),orderId,clientId+"","FlatRate Overnight apo check", LogableException.errorTypes.INTERNAL);

            }
            int zone = ZoneLookup.lookupZoneFromOrderId(orderId);
            int serviceLevel = FlatRateShipping.getServiceLevelIdFromCode(shipcode);
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse(clientId+"",group,facility,zone,rweight,serviceLevel,apoShip);
            if(rweight == 0 && (smu.getCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP")||smu.getCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND") )){
                for (int i = 0; i < packlist.size(); i++) {
                    packlist.get(i).setAssignedServiceCode(smu.getCode());
                    packlist.get(i).setOriginalAssignedServiceCode(shipcode);
                }
            }else {
                smu = getFlatRateMethod(Integer.parseInt(orderId), packlist.get(0), clientId + "", group, facility, rweight, serviceLevel, apoShip);
                for (int i = 0; i < packlist.size(); i++) {

                    packlist.get(i).setAssignedServiceCode(smu.getCode());
                    packlist.get(i).setOriginalAssignedServiceCode(shipcode);
                    int dimWeight = DimensionalWeight.getDimensionalWeight(
                            orderId,
                            Double.parseDouble(packlist.get(i).getValueAsString("HEIGHT_INCHES")),
                            Double.parseDouble(packlist.get(i).getValueAsString("WIDTH_INCHES")),
                            Double.parseDouble(packlist.get(i).getValueAsString("DEPTH_INCHES")),
                            smu.getCode()
                    );
                    if (DimensionalWeight.checkForDimEligability(Double.parseDouble(packlist.get(i).getValueAsString(ShipConfig.WEIGHT)), smu.getCode(), dimWeight)) {
                        Criteria crit = HibernateSession.currentSession().createCriteria(OWDPackage.class).add(Restrictions.eq("packBarcode", packlist.get(i).getValueAsString("SHIPMENTBARCODE")));
                        Object obj = crit.uniqueResult();
                        if (obj == null)
                            throw new Exception("Unable to find package in database to update dim_weight_lbs");
                        OWDPackage pack = (OWDPackage) obj;
                        pack.setDimWeightLbs(new BigDecimal(dimWeight));
                        HibernateSession.currentSession().saveOrUpdate(pack);
                        HibUtils.commit(HibernateSession.currentSession());
                    }
                }
            }
            shipcode = smu.getCode();

            shipRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(facility, clientId, 1, packlist.get(0).getAssignedServiceCode(), group));

        }

        //GSS shipment begin
        gssShipMethodList.add("TANDATA_USPS.USPS.I_EXP_DMND");
        gssShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
        gssShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
        gssShipMethodList.add("TANDATA_USPS.USPS.EXPR");
        //Removal of GSS ship methods. This will allow deliver labels for Express and everything else goes through OwdShippingAPI
              /*  if (gssShipMethodList.contains(shipcode)) {

            System.out.println("Doing gss Shipment yeah haw");

            gssShipment gss = new gssShipment(packlist, HibernateSession.currentSession(), client, shipRequest.getLOCATIONCODE(), false);
            response.getSHIPMENT().addAll(gss.shipGSSPackages(HibernateSession.currentSession(), "hi"));
            HibUtils.commit(HibernateSession.currentSession());
            return response;
        }*/

        // Deliverr label acquisition code
        // get labels from the tag utility
        if(clientId == 622) {
            Map<String, String> customValues = TagUtilities.getTagMap("ORDER", Integer.parseInt(orderId));
            // check it tage SHIPPING_LABEL exitst and equals DELIVERR_APO
            if(customValues.containsKey("SHIPPING_LABEL")) {
                if (customValues.get("SHIPPING_LABEL").equals("DELIVERR_API")) {
                    log.debug("we have a Deliverr purchased label");
                    // this needs to return a response object which then needs to be end the method by[ returning that response object
                    response.getSHIPMENT().addAll(DeliverrApi.submitOrder(packlist, facility));
                    HibUtils.commit(HibernateSession.currentSession());
                    return response;
                }
            }
        }
        //GSS shipment end

        //OSM Shipment Begin
//        List<String>osmShipMethodList = new ArrayList<String>();
//        osmShipMethodList.add("OWD.OSM.DOM");
//        osmShipMethodList.add("OWD.OSM.INTL");
//        Double lower = new Double(1), upper = new Double(70);

//        boolean bpShip = false;
//        if(facility.equals("DC6") && shipcode.equals("OWD.BPD.STANDARD")){
//            Double weight = (Double) packlist.get(0).getValue(ShipConfig.WEIGHT);
//            if(weight >= lower && weight < upper ) {
//                if (clientId == 488 || clientId == 607) {
//                    List<SHIPMENT> shipment = BluePackageApi.submitOrders(packlist, facility);
//                    if (shipment.size() > 0) {
//                        response.getSHIPMENT().addAll(shipment);
//                        HibUtils.commit(HibernateSession.currentSession());
//                        return response;
//                    } else {
//                        shipcode = "TANDATA_USPS.USPS.PRIORITY";
//                        for(int i = 0;i<packlist.size();i++){
//                            packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
//                        }
//                    }
//                }
//            }else{
//                if(weight < lower) {
//                    shipcode = "TANDATA_USPS.USPS.FIRST";
//                    for (int i = 0; i < packlist.size(); i++) {
//                        packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.FIRST");
//                    }
//                }
//            }
//        } else if (shipcode.equals("OWD.BPD.STANDARD")) {
//            shipcode = "TANDATA_USPS.USPS.FIRST";
//            for(int i = 0;i<packlist.size();i++){
//                packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.FIRST");
//            }
//        }


        // Original BPD LOGIC
/*        if(facility.equals("DC6") && shipcode.equals("OWD.BPD.STANDARD")){
            Double weight = (Double) packlist.get(0).getValue(ShipConfig.WEIGHT);
            if(weight >= lower && weight < upper ) {
                if (clientId == 488 || clientId == 607) {
                    List<SHIPMENT> shipment = BluePackageApi.submitOrders(packlist, facility);
                    if (shipment.size() > 0) {
                        response.getSHIPMENT().addAll(shipment);
                        HibUtils.commit(HibernateSession.currentSession());
                        return response;
                    } else {
                        shipcode = "OWD.OSM.DOM";
                        for(int i = 0;i<packlist.size();i++){
                            packlist.get(i).setOriginalAssignedServiceCode(packlist.get(i).getOriginalAssignedServiceCode());
                            packlist.get(i).setAssignedServiceCode("OWD.OSM.DOM");
                        }
                    }
                }
            }else{
                if(weight < lower) {
                    shipcode = "OWD.OSM.DOM";
                    for (int i = 0; i < packlist.size(); i++) {
                        packlist.get(i).setOriginalAssignedServiceCode(packlist.get(i).getOriginalAssignedServiceCode());
                        packlist.get(i).setAssignedServiceCode("OWD.OSM.DOM");
                    }
                }
            }
        } else if (shipcode.equals("OWD.BPD.STANDARD")) {
            shipcode = "TANDATA_USPS.USPS.FIRST";
            for(int i = 0;i<packlist.size();i++){
                packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.FIRST");
            }
        }
*/
        boolean osmShip = false;


        //Check for zone 7 or 8 and switch for MNML to Priority Mail
        //removed 5-15-2018 per Tom
        /*if(osmShipMethodList.contains(shipcode)&&(clientId==607 || clientId == 55)){
            if(ZoneLookup.lookupZoneFromOrderId(orderId)>6|| OSMUtils.OSMUnserveable(packlist.get(0).getConsigneeNA().getPostalCode())){
                log.debug("We are swapping to USPS");
                shipcode = "TANDATA_USPS.USPS.PRIORITY";
                for(int i = 0;i<packlist.size();i++){
                    packlist.get(i).setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                }


            }
        }*/


        /*if(osmShipMethodList.contains(shipcode)||osmShip){
            System.out.println("Doing osm shipment");
            OSMShipment osm = new OSMShipment(packlist,client,shipRequest.getLOCATIONCODE());
            response.getSHIPMENT().addAll(osm.shipOSMPackages());
            HibUtils.commit(HibernateSession.currentSession());
            return response;
        }*/

        //OSM shipment End
        if (shipMethod.equals("OWD.NOBOX.PICKEDUP") || shipcode.equals("OWD.NOBOX.PICKEDUP")) {

            System.out.println("Doing Picked up Package");
            AMPConnectShipShipment ship = packlist.get(0);


            response.getSHIPMENT().add(shipPickedUpOrderReturnLabelForZebra(ship.getValueAsString("OWDORDERID"),
                    ship.getConsigneeNA().getContact(), ship.getClientNA().getCompany(), packlist.size(),
                    HibernateSession.currentSession()));
            return response;

        }
        AMPConnectShipShipment doShip = packlist.get(0);
        doShip.ship(1, packageCount, packlist, shipRequest.getSHIPPER(), shipRequest.getLOCATIONCODE());
       /* for (AMPConnectShipShipment ship : packlist) {
            System.out.println("shipping");
              ship.printDictionaryValues();
            ship.ship(packIndex++, packageCount, pendingShipmentsMap, shipRequest.getSHIPPER(),shipRequest.getLOCATIONCODE());

         //   System.out.println("PackIndex: "+packIndex);
          //  System.out.println("pacakgeCount: "+ packageCount);
          //  System.out.println("code index "+ ship.getAssignedServiceCode().indexOf("USPS."));
            //todo loop here
           *//* if (packIndex > packageCount  || ship.getAssignedServiceCode().indexOf("USPS.") >= 0){
            System.out.println("shipped");
            System.out.println();

            SHIPMENT shipped = new SHIPMENT();
            shipped.setSHIPMETHOD(ship.getAssignedServiceName());
            shipped.setMSN(ship.getValueAsString(ShipConfig.MSN));
            System.out.println(ship.getValueAsString(ShipConfig.MSN));
            System.out.println(ship.getValue(ShipConfig.NOFN_TOTAL));

            System.out.println("posting");
            ship.postShipment(Integer.parseInt("" + ship.getValue(ShipConfig.NOFN_SEQUENCE)), Integer.parseInt("" + ship.getValue(ShipConfig.NOFN_TOTAL)));
            System.out.println("printing");
            List<byte[]> labels = ConnectShipWebServices.printThermalLabels(ship.getValueAsString("PACKAGE_BARCODE"), ship.getValueAsString("LABELPRINTER_CODE"));

            for (byte[] labeldata : labels) {
                LABELDATA ld = new LABELDATA();
                ld.setCopies_Needed("1");
                ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(labeldata));
                shipped.getLABELDATA().add(ld);
            }
            response.getSHIPMENT().add(shipped);
            if (ship.isInternational() || ship.isAPOFPO()) {
                List<byte[]> pdfList = ConnectShipWebServices.printIntlDocPDF(ship);
                for (byte[] pdfdata : pdfList) {
                    INTLDOCDATA idd = new INTLDOCDATA();

                    if (shipMethod.indexOf("USPS") >= 0) {
                        idd.setCopies_Needed("4");
                    } else {
                        idd.setCopies_Needed("3");
                    }
                    idd.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(pdfdata));
                    shipped.getINTLDOCDATA().add(idd);
                }
            }


            }*//*
        }*/
        System.out.println("shipped");
        for (AMPConnectShipShipment ship : packlist) {

            System.out.println("Time to post traking info back");
            System.out.println(ship.getValue(ShipConfig.TRACKING_NUMBER));
            ship.postShipment(Integer.parseInt("" + ship.getValue(ShipConfig.NOFN_SEQUENCE)), Integer.parseInt("" + ship.getValue(ShipConfig.NOFN_TOTAL)), User);
            System.out.println("posting");
        }

        for (AMPConnectShipShipment ship : packlist) {
            System.out.println();

            SHIPMENT shipped = new SHIPMENT();
            shipped.setSHIPMETHOD(ship.getAssignedServiceName());
            shipped.setMSN(ship.getValueAsString(ShipConfig.MSN));
            System.out.println(ship.getValueAsString(ShipConfig.MSN));
            System.out.println(ship.getValue(ShipConfig.NOFN_TOTAL));
            System.out.println("Sequence " + ship.getValue(ShipConfig.NOFN_SEQUENCE));

            List<byte[]> labels = new ArrayList<byte[]>();

            System.out.println("printing");
            if (!("TRUE".equals(ship.getValue(ShipConfig.CALLTAG)))) {
                labels = ConnectShipWebServices.printThermalLabels(ship.getValueAsString("PACKAGE_BARCODE"), ship.getValueAsString("LABELPRINTER_CODE"), ship.isInternational());
            }

            System.out.println("Got labels fro mprintthermal labels");
            for (byte[] labeldata : labels) {
                LABELDATA ld = new LABELDATA();
                ld.setCopies_Needed("1");
                ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(labeldata));
                shipped.getLABELDATA().add(ld);
            }
            response.getSHIPMENT().add(shipped);
            boolean needsCustomsAnyway = USTerritoryNeedingCustoms(ship);
            System.out.println(needsCustomsAnyway + "  needs customs stuff");
            if (ship.isInternational() || ship.isAPOFPO() || needsCustomsAnyway) {


                if ((ship.isAPOFPO() || needsCustomsAnyway)) {
                    System.out.println("Doing gss Shipment yeah haw");
                    System.out.println(shipMethod);

                    if (!ship.getAssignedServiceCode().contains("UPS.SP") && !ship.getAssignedServiceCode().contains("DHL")) {
                        gssShipment gss = new gssShipment(packlist, HibernateSession.currentSession(), client, shipRequest.getLOCATIONCODE(), true);
                        shipped.getINTLDOCDATA().addAll(gss.getAPOCustomsForms("customs", facility));
                    }


                } else {
                    if (ship.isPaperless() == false || shipMethod.indexOf("FEDEX") >= 0 ||ship.getValueAsString("OWDSERVICECODE").contains("FEDEX")) {
                        List<byte[]> pdfList = ConnectShipWebServices.printIntlDocPDF(ship);
                        for (byte[] pdfdata : pdfList) {
                            INTLDOCDATA idd = new INTLDOCDATA();

                            if (shipMethod.indexOf("USPS") >= 0) {
                                idd.setCopies_Needed("4");
                            } else {
                                idd.setCopies_Needed("3");
                            }
                            idd.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(pdfdata));
                            shipped.getINTLDOCDATA().add(idd);
                        }
                    }
                }
            }


        }
        System.out.println("Done with the shipping sending back shipment");
        return response;
    }

    private static shipMethodUpdate getFlatRateMethod(int orderId, AMPConnectShipShipment pack, String clientId, String groupName, String facility, int weight,int serviceLevel,boolean isAPO) throws Exception{
        shipMethodUpdate smu;
        try {
            int zone = ZoneLookup.lookupZoneFromOrderId(orderId);
            boolean isDimmable = false;
            double height = Double.parseDouble(pack.getValueAsString("HEIGHT_INCHES"));
            double width = Double.parseDouble(pack.getValueAsString("WIDTH_INCHES"));
            double depth = Double.parseDouble(pack.getValueAsString("DEPTH_INCHES"));
            ArrayList<OwdShipMethod> shipMethodCodes = FlatRateShipping.getFlatRateShipMethodCodesForClient(Integer.parseInt(clientId));
            for(OwdShipMethod method: shipMethodCodes) {
                int dimWeight = DimensionalWeight.getDimensionalWeight(orderId, height, width, depth, method);
                if (dimWeight > weight) {
                    isDimmable = true;
                    smu = FlatRateShipping.getShipMethodToUse(clientId + "", groupName, facility, zone, dimWeight, serviceLevel, isAPO);
                    if (smu.getCode().equals(method.getMethodCode())) {
                        return smu;
                    }
                }
            }

            if (isDimmable) {
                //run the middle weight if one exixts
                int dimWeight = DimensionalWeight.getDimensionalWeight(orderId, height,width,depth,shipMethodCodes.get(shipMethodCodes.size()-1));
                return FlatRateShipping.getShipMethodToUse(clientId+"",groupName,facility,zone,(int) dimWeight,serviceLevel, isAPO);
            } else {
                return FlatRateShipping.getShipMethodToUse(clientId + "", groupName, facility, zone, weight, serviceLevel, isAPO);
            }

        }catch(Exception ex){
            throw ex;
        }
    }

    private static boolean USTerritoryNeedingCustoms(AMPConnectShipShipment ship) {
        boolean needed = false;
        try {
            System.out.println(ship.getAssignedServiceName() + "oooooooooooooooooooooooooooooooooooooooo");
            if (ship.getAssignedServiceName().equalsIgnoreCase("USPS PRIORITY MAIL")) {
                System.out.println(ship.getValueAsString(ShipConfig.WEIGHT));
                if (Float.parseFloat(ship.getValueAsString(ShipConfig.WEIGHT)) >= 1) {
                    System.out.println(ship.getConsigneeNA().getPostalCode());
                    if (ship.getConsigneeNA().getPostalCode().startsWith("969") || ship.getConsigneeNA().getPostalCode().startsWith("967")) {
                        List<String> l = new ArrayList<String>();
                        l.add("96910");
                        l.add("96911");
                        l.add("96912");
                        l.add("96913");
                        l.add("96914");
                        l.add("96915");
                        l.add("96916");
                        l.add("96917");
                        l.add("96918");
                        l.add("96919");
                        l.add("96920");
                        l.add("96921");
                        l.add("96922");
                        l.add("96923");
                        l.add("96924");
                        l.add("96925");
                        l.add("96926");
                        l.add("96927");
                        l.add("96928");
                        l.add("96929");
                        l.add("96930");
                        l.add("96931");
                        l.add("96932");
                        l.add("96933");
                        l.add("96934");
                        l.add("96935");
                        l.add("96936");
                        l.add("96937");
                        l.add("96938");
                        l.add("96939");
                        l.add("96940");
                        l.add("96941");
                        l.add("96942");
                        l.add("96943");
                        l.add("96944");
                        l.add("96950");
                        l.add("96951");
                        l.add("96952");
                        l.add("96960");
                        l.add("69670");
                        l.add("96799");

                        if (l.contains(ship.getConsigneeNA().getPostalCode())) {
                            needed = true;
                        }
                    }


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return needed;
    }

    private static SHIPMENT shipPickedUpOrderReturnLabelForZebra(String orderId, String customer, String client, int numPacks, Session sess) throws Exception {
        SHIPMENT shipped = new SHIPMENT();
        shipped.setSHIPMETHOD("Picked UP");
        StringBuffer label = new StringBuffer();
        String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent) VALUES ( :orderId, 1 ,'',0,0,0,convert(datetime,convert(varchar,getdate(),101)),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0)";
        Query q = sess.createSQLQuery(updateSQL);
        q.setParameter("orderId", orderId);
        int count = q.executeUpdate();

        if (count > 0) {

            Query update = sess.createSQLQuery("exec update_order_shipment_info " + orderId);
            update.executeUpdate();
            HibUtils.commit(sess);


            for (int i = 1; i < numPacks + 1; i++) {


                label.append("^XA");

                label.append("^PR7");
                label.append("^FO600,0");
                label.append("^FB900,2,5,C,");
                label.append("^ABr,40,12^FD");
                label.append(customer);
                label.append(" ^FS");
                label.append("^FO500,0");
                label.append("^FB900,2,5,C,");
                label.append("^ABr,40,12^FD");
                label.append(client);
                label.append(" ^FS");
                label.append("^FO400,0");
                label.append("^FB900,2,5,C,");
                label.append("^ABr,40,12^FD");
                label.append(orderId);
                label.append(" ^FS");
                label.append("^FO200,0");
                label.append("^FB1100,2,5,C,");
                label.append("^ABr,52,52^FD");
                label.append("Pick Up Order");
                label.append(" ^FS");
                label.append(" ^FS");
                label.append("^FO000,0");
                label.append("^FB1600,2,5,C,");
                label.append("^ABr,40,40^FD");
                label.append("Package " + i + " of " + numPacks);
                label.append(" ^FS");
                label.append("^XZ");

                LABELDATA ld = new LABELDATA();
                ld.setCopies_Needed("1");
                ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(label.toString().getBytes()));
                shipped.getLABELDATA().add(ld);
            }

        } else {
            throw new Exception("Unable to update tracker for Picked up order");

        }


        return shipped;
    }


}
