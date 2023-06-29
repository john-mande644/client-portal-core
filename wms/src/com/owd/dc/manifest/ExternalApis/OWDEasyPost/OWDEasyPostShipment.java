package com.owd.dc.manifest.ExternalApis.OWDEasyPost;

import com.easypost.EasyPost;
import com.easypost.model.*;
import com.owd.connectship.soap.NameAddress;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;

import com.owd.dc.manifest.Dimension;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.Addresses.EasypostAddressUtilities;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.Customs.EasypostItemCustoms;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jfree.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 7/12/2017.
 *
 */
public class OWDEasyPostShipment {


    protected final static Logger log = LogManager.getLogger();


    private Order order;
    Map<String,Object> carriers = new HashMap<String, Object>();
    private boolean thirdParty = false;


    public static void main(String[] args){

        try {
            AMPConnectShipShipment ship = new AMPConnectShipShipment();

            ship.loadOrderFromOrderReference("p10330382*21861542*b1", true, "DC6");

           ship.setValue(ShipConfig.WEIGHT, new Double("29"));
            OwdClient cl = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, 55);
            List<AMPConnectShipShipment> l = new ArrayList<AMPConnectShipShipment>();
            l.add(ship);
            ship.loadOrderFromOrderReference("p10330382*21861542*b2", true, "DC6");
            ship.setValue(ShipConfig.WEIGHT, new Double("1"));
            l.add(ship);
            ship.loadOrderFromOrderReference("p10330382*21861542*b3", true, "DC6");
            ship.setValue(ShipConfig.WEIGHT, new Double("1"));
            l.add(ship);

            ship.loadOrderFromOrderReference("p10330382*21861542*b4", true, "DC6");
            ship.setValue(ShipConfig.WEIGHT, new Double("1"));
            l.add(ship);

            OWDEasyPostShipment easy = new OWDEasyPostShipment(l,HibernateSession.currentSession(),cl,"DC6","RnAfn900njkaEDr0J6zmBQ",false);


            List<SHIPMENT> done = easy.shipPackages(HibernateSession.currentSession(),"DC6");

            for(SHIPMENT s : done){

                for(LABELDATA ld:s.getLABELDATA()){
                    log.debug(ld.getValue());
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public OWDEasyPostShipment(List<AMPConnectShipShipment> packlist, Session sess,OwdClient client,String facility,String apiKey, boolean thirdPartyAccount)throws Exception{

        if(null == apiKey || apiKey.length()==0){

            if(client.getClientId() == 489||client.getClientId() == 575){

                AMPConnectShipShipment ship = packlist.get(0);
                apiKey = EasyPostAccountsAndUsers.getAndSetApiKeyForShipment(ship,client);
                if(apiKey.length()==0){
                    throw new Exception("This client is not allowed to ship this method");
                }

            }else {
                //todo lookup api key by facility method etc..
                throw new Exception("This client is not allowed to ship this method");
            }
        }
        EasyPost.apiKey = apiKey;
        thirdParty = thirdPartyAccount;


        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        Map<String, Object> toAddressMap = new HashMap<String, Object>();
        Map<String, Object> returnAddressMap = new HashMap<String, Object>();
        List<Map<String, Object>> shipments = new ArrayList<Map<String, Object>>();
        try{
            for (AMPConnectShipShipment ship : packlist) {

                if(fromAddressMap.size() == 0) {
                    carriers = EasyShipmentUtils.getEasyPostCarrier(ship.getAssignedServiceCode(), ship.getAssignedServiceName());
                    EasyShipmentUtils.checkForAllowedShipMethod(ship,carriers,facility,client.getClientId());

                    log.debug("Setting From Address");

                    fromAddressMap = EasypostAddressUtilities.loadFromAddress(ship,carriers,client);


                }

                if(toAddressMap.size()==0) {
                    log.debug("Setting To Address");

                    toAddressMap = EasypostAddressUtilities.loadToAddress(ship,carriers);

                }
                if(returnAddressMap.size()==0){
                    returnAddressMap = EasypostAddressUtilities.loadReturnAddress(ship,carriers,client);
                }
                log.debug("Doing parcel");
                Dimension dim = new Dimension(ship.getValueAsString("DIMENSION"));

                Map<String, Object> parcelMap = new HashMap<String, Object>();
                log.debug(ship.getValueAsString("WEIGHT"));
                parcelMap.put("weight", Float.parseFloat(ship.getValueAsString("WEIGHT"))*16);
                parcelMap.put("height", dim.getHeight());
                parcelMap.put("width", dim.getWidth());
                parcelMap.put("length", dim.getLength());
                log.debug("Creating parcels1");
                Parcel p1 = Parcel.create(parcelMap);

                Map<String, Object> shipmentMap = new HashMap<String, Object>();

                //add parcel to shipmentMap
                shipmentMap.put("parcel", p1);

                //load options
                Map<String, Object> optionsMap = EasyShipmentUtils.loadOptionsForShipment(ship,carriers);




                //todo thirdparty
                //todo ddu etc..
               if(!toAddressMap.get("country").equals("US")&&!carriers.get("carrier").equals("Purolator")){
                   shipmentMap.put("customs_info", EasypostItemCustoms.loadCustomsInfo(ship,client.getClientId()));
               }


                shipmentMap.put("options",optionsMap);



                shipments.add(shipmentMap);



            }

            Map<String, Object> orderMap = new HashMap<String, Object>();
            // orderMap.put("reference","testOrder");
            Address fromAddress = Address.create(fromAddressMap);
            Address toAddress = Address.create(toAddressMap);
            if(returnAddressMap.size()>0){
                log.debug("We have a return address");
                Address returnAddress = Address.create(returnAddressMap);
                orderMap.put("return_address",returnAddress);
            }
            orderMap.put("to_address", toAddress);
            orderMap.put("from_address",fromAddress);
            orderMap.put("shipments",shipments);
            orderMap.put("is_return",false);
            Map<String,String> accounts = EasyPostAccountsAndUsers.getCarrierAccounts(packlist.get(0),carriers,facility,client);
           if(accounts.size()>0) {
               orderMap.put("carrier_accounts", accounts);
           }

            log.debug("Creating order");
            order = Order.create(orderMap);
            log.debug("Created order");




        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error loading order: " + e.getMessage());
        }







    }

    public List<SHIPMENT> shipPackages(Session sess,String location) throws Exception{
        log.debug(order.prettyPrint());

        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        if(order.getShipments().size()>0){
            if(carriers.size()>0){
                order.buy(carriers);
                System.out.println(order.prettyPrint());

                if(order.getMessages().size()>0){
                    log.debug("We have messages, check them for errors");
                    String error ="";
                    for(ShipmentMessage sm: order.getMessages()){
                        if(!sm.getType().equals("rate_message")) {
                            if (error.length() == 0) {
                                error = sm.getMessage().toString();
                            } else {
                                error = error + " / " + sm.getMessage();
                            }
                        }
                    }
                    if (error.length()>0){
                        throw new Exception(error);
                    }


                }




                    log.debug("Record tracking and get label into shipment element");

                    int i = 1;
                    for(Shipment shipment:order.getShipments()){
                        postTrackingInfo(shipment,sess,i,location);



                       i++;
                        try {


                            SHIPMENT CSshipment = new SHIPMENT();
                            LABELDATA ld = new LABELDATA();
                            ld.setCopies_Needed("1");


                            if(shipment.getPostageLabel().getLabelUrl().endsWith(".png")){
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("file_format", "ZPL");

                                shipment.label(params);
                                ld.setValue(shipment.getPostageLabel().getLabelZplUrl());
                            }else{
                                ld.setValue(shipment.getPostageLabel().getLabelUrl());
                            }

                            CSshipment.getLABELDATA().add(ld);

                            shippedPackages.add(CSshipment);

                        }catch (Exception e){
                            e.printStackTrace();
                            log.debug("Error getting label, most likely multi package");
                        }
                    }

                HibUtils.commit(sess);










            }else{
                throw new Exception("No carriers selected");
            }




        }else{
            throw new Exception("There are not any shipments loaded. Please retry");
        }
        return shippedPackages;

    }
    private void postTrackingInfo(Shipment shipment, Session sess, int index,String location) throws Exception{

       // String updatePackageSql = "update package set gss_shipment = 0, customs_docs = :customs, ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, external_id = :externalId where pack_barcode=:pack_barcode";
        String updatePackageSql = "execute sp_updatePackageShipAppExternal :customs, :trackid, :cost,:insured,:externalId,:pack_barcode";

        String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=:orderfkey and is_void=0";
        String updateOrdersql   ="exec update_order_shipment_info :orderfkey";
        String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,external_id, weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent) VALUES ( :order_fkey, :line_index, :tracking_no, :external_id,  :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0)";



        Query q = sess.createSQLQuery(createTrackingRecord);
        q.setParameter("order_fkey", shipment.getOptions().get("invoice_number"));
        q.setParameter("line_index",index);
        q.setParameter("tracking_no",shipment.getTracker().getTrackingCode());
        q.setParameter("external_id",shipment.getTracker().getId());
        q.setParameter("weight",shipment.getParcel().getWeight()/16);
        //todo check for Client api and do not record dollar amount e
        if(thirdParty){
            q.setParameter("total_billed","0.0");
        }else{
            q.setParameter("total_billed",shipment.getSelectedRate().getListRate());
        }

        q.setParameter("location",location);

        log.debug("Creating Tracking");
        int results = q.executeUpdate();
        if(results<0) throw new Exception("Unable to create tracking for " + shipment.getTracker().getTrackingCode());


        //tracking has been updated.

        String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
        Query  qq = sess.createSQLQuery(sql);
        qq.setString("fkey",  shipment.getOptions().get("invoice_number").toString());
        log.debug(shipment.getOptions().get("invoice_number"));
        qq.setString("track",shipment.getTracker().getTrackingCode());
        log.debug(shipment.getTracker().getTrackingCode());
        List l = qq.list();
        log.debug(l);
        log.debug("Got order track Id");
        String trackId = l.get(0).toString();
        log.debug("Tracking Id: "+ trackId);

        //grabbed tracking id

        //update package info

        q = sess.createSQLQuery(updatePackageSql);
        q.setString("trackid",trackId);
        q.setString("pack_barcode",shipment.getOptions().get("print_custom_3").toString());
       
       //todo figure out insurance 
        q.setString("cost","0");
        q.setString("insured", "0");
        
       
            q.setParameter("customs",0);
        q.setParameter("externalId",shipment.getId());
       

        log.debug("Right before updateing package");
        results = q.executeUpdate();
        log.debug("Done updateing pacakge with trackid");
        if (results < 1) throw new Exception("update for Package returned 0");


        q = sess.createSQLQuery(updatePackageOrdersql);
        q.setString("orderfkey",shipment.getOptions().get("invoice_number").toString());
        results = q.executeUpdate();
        System.out.println("Done updateing package order ");
        if (results < 1) throw new Exception("update for Package ORder returned 0");
        if(!ABUtils.isWeightVerifiedPacked(shipment.getOptions().get("invoice_number").toString())) {
            q = sess.createSQLQuery(updateOrdersql);
            q.setString("orderfkey", shipment.getOptions().get("invoice_number").toString());
            results = q.executeUpdate();
            System.out.println("Done updateting order sql");
            if (results < 1) throw new Exception("update Order returned 0");
        }



    }







}
