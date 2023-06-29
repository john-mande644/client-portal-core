package com.owd.jobs.jobobjects.easypost;

import com.easypost.EasyPost;

import com.easypost.model.*;
import com.easypost.model.TrackingDetail;
import com.google.gson.*;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderTrack;

import com.owd.hibernate.generated.OwdOrderTrackCurrentStatus;
import com.owd.hibernate.generated.OwdOrderTrackEvents;
import com.owd.jobs.jobobjects.easypost.tracking.*;

import com.owd.jobs.jobobjects.omx.OMXUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

/**
 * Created by danny on 6/9/2017.
 */
public class trackingUtilites {
    protected final static Logger log = LogManager.getLogger();


    public static void main(String[] args) {

        try {

           // log.debug(updateCurrentStatusWithLatestTrackingEventData("trk_38181b5863c54c188ca8002f91100982"));

          /*  int i = 0;

            while(i<8) {
                catchOrderTrackCurrentStatusUpToLatestEvents(100);
i++;
            }
*/
          //  updateStalledRecords();
           /* String s = "{\"result\":{\"id\":\"trk_9749a83698194a4aa0d1f099aef8a7e8\",\"object\":\"Tracker\",\"mode\":\"production\",\"tracking_code\":\"92001901074187213380405267\",\"status\":\"delivered\",\"status_detail\":\"arrived_at_destination\",\"created_at\":\"2018-06-07T19:36:21Z\",\"updated_at\":\"2018-06-07T19:36:21Z\",\"signed_by\":null,\"weight\":null,\"est_delivery_date\":\"2018-06-06T00:00:00Z\",\"shipment_id\":null,\"carrier\":\"USPS\",\"tracking_details\":[{\"object\":\"TrackingDetail\",\"message\":\"June 4 Pre-Shipment Info Sent to USPS, USPS Awaiting Item\",\"description\":\"Pre-Shipment Info Sent to USPS, USPS Awaiting Item\",\"status\":\"pre_transit\",\"status_detail\":\"status_update\",\"datetime\":\"2018-06-04T00:00:00Z\",\"source\":\"USPS\",\"carrier_code\":\"MA\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":null,\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 4 8:24 pm Accepted at USPS Regional Origin Facility in CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"description\":\"Accepted at USPS Regional Origin Facility\",\"status\":\"in_transit\",\"status_detail\":\"received_at_origin_facility\",\"datetime\":\"2018-06-04T20:24:00Z\",\"source\":\"USPS\",\"carrier_code\":\"OA\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 4 9:39 pm Arrived at USPS Regional Origin Facility in CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"description\":\"Arrived at USPS Regional Origin Facility\",\"status\":\"in_transit\",\"status_detail\":\"arrived_at_facility\",\"datetime\":\"2018-06-04T21:39:00Z\",\"source\":\"USPS\",\"carrier_code\":\"10\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 4 9:40 pm Departed USPS Regional Origin Facility in CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"description\":\"Departed USPS Regional Origin Facility\",\"status\":\"in_transit\",\"status_detail\":\"departed_origin_facility\",\"datetime\":\"2018-06-04T21:40:00Z\",\"source\":\"USPS\",\"carrier_code\":\"10\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 5 In Transit to Next Facility\",\"description\":\"In Transit to Next Facility\",\"status\":\"in_transit\",\"status_detail\":\"in_transit\",\"datetime\":\"2018-06-05T00:00:00Z\",\"source\":\"USPS\",\"carrier_code\":\"NT\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":null,\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 6 12:05 am Arrived at USPS Regional Destination Facility in PITTSBURGH PA NETWORK DISTRIBUTION CENTER\",\"description\":\"Arrived at USPS Regional Destination Facility\",\"status\":\"in_transit\",\"status_detail\":\"arrived_at_facility\",\"datetime\":\"2018-06-06T00:05:00Z\",\"source\":\"USPS\",\"carrier_code\":\"10\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH PA NETWORK DISTRIBUTION CENTER\",\"state\":null,\"country\":null,\"zip\":null}},{\"object\":\"TrackingDetail\",\"message\":\"June 6 8:00 am Arrived at Post Office in PITTSBURGH, PA\",\"description\":\"Arrived at Post Office\",\"status\":\"in_transit\",\"status_detail\":\"arrived_at_facility\",\"datetime\":\"2018-06-06T08:00:00Z\",\"source\":\"USPS\",\"carrier_code\":\"07\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH\",\"state\":\"PA\",\"country\":null,\"zip\":\"15233\"}},{\"object\":\"TrackingDetail\",\"message\":\"June 6 8:14 am Out for Delivery in PITTSBURGH, PA\",\"description\":\"Out for Delivery\",\"status\":\"out_for_delivery\",\"status_detail\":\"out_for_delivery\",\"datetime\":\"2018-06-06T08:14:00Z\",\"source\":\"USPS\",\"carrier_code\":\"59\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH\",\"state\":\"PA\",\"country\":null,\"zip\":\"15212\"}},{\"object\":\"TrackingDetail\",\"message\":\"June 6 8:20 am Sorting Complete in PITTSBURGH, PA\",\"description\":\"Sorting Complete\",\"status\":\"in_transit\",\"status_detail\":\"status_update\",\"datetime\":\"2018-06-06T08:20:00Z\",\"source\":\"USPS\",\"carrier_code\":\"PC\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH\",\"state\":\"PA\",\"country\":null,\"zip\":\"15212\"}},{\"object\":\"TrackingDetail\",\"message\":\"June 6 2:27 pm Delivered, In/At Mailbox in PITTSBURGH, PA\",\"description\":\"Delivered, In/At Mailbox\",\"status\":\"delivered\",\"status_detail\":\"arrived_at_destination\",\"datetime\":\"2018-06-06T14:27:00Z\",\"source\":\"USPS\",\"carrier_code\":\"01\",\"tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH\",\"state\":\"PA\",\"country\":null,\"zip\":\"15212\"}}],\"carrier_detail\":{\"object\":\"CarrierDetail\",\"service\":\"First-Class Package Service\",\"container_type\":null,\"est_delivery_date_local\":null,\"est_delivery_time_local\":null,\"origin_location\":\"CITY OF INDUSTRY CA, 91715\",\"origin_tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"CITY OF INDUSTRY CA DISTRIBUTION CENTER\",\"state\":null,\"country\":null,\"zip\":null},\"destination_location\":\"PITTSBURGH PA, 15212\",\"destination_tracking_location\":{\"object\":\"TrackingLocation\",\"city\":\"PITTSBURGH\",\"state\":\"PA\",\"country\":null,\"zip\":\"15212\"},\"guaranteed_delivery_date\":null,\"alternate_identifier\":null,\"initial_delivery_attempt\":\"2018-06-06T08:20:00Z\"},\"finalized\":true,\"is_return\":false,\"public_url\":\"https://track.easypost.com/djE6dHJrXzk3NDlhODM2OTgxOTRhNGFhMGQxZjA5OWFlZjhhN2U4\",\"fees\":[{\"object\":\"Fee\",\"type\":\"TrackerFee\",\"amount\":\"0.00000\",\"charged\":true,\"refunded\":false}]},\"description\":\"tracker.created\",\"mode\":\"production\",\"previous_attributes\":{},\"created_at\":\"2018-06-07T19:36:51Z\",\"pending_urls\":[\"http://testing.api.owd.com/easyposttracker\"],\"completed_urls\":[],\"updated_at\":\"2018-06-07T19:36:51Z\",\"id\":\"evt_8d625ac4408f4becb2a88f7408b1595a\",\"user_id\":\"user_15c413d4c8434983a29697d2e59f5a78\",\"status\":\"in_queue\",\"object\":\"Event\"}";

            EventDeserializer d = new EventDeserializer();
            JsonElement el = new JsonParser().parse(s);

            JsonObject res = el.getAsJsonObject();

            JsonElement resul = res.get("result");
            System.out.println(resul);
            System.out.println(resul.getAsJsonObject().get("est_delivery_date"));




            Event e = d.deserialize(el, Event.class, new JsonDeserializationContext() {
                @Override
                public <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
                    return null;
                }
            });

           // System.out.println(e.prettyPrint());


            for(TrackingDetail td : e.getResult().getTrackingDetails()){
                System.out.println(td.getMessage());



            }
*/


           /* EasyPost.apiKey ="hl0OMczdmtkyyCHY8dRxtQ";

            Map<String, Object> trackerMap = new HashMap<String, Object>();
            trackerMap.put("tracking_code", "EZ2000000002 ");
           // trackerMap.put("carrier", "USPS");

            Tracker tracker = Tracker.create(trackerMap);
            System.out.println(tracker.prettyPrint());
 //updateOwdTracker(12614989);*/
/*           EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";
          Tracker t = Tracker.retrieve("trk_34377bb0d60148699c4f5c1aaa12202b");
System.out.println(t.prettyPrint());

            log.debug("hi");
            */
          //  pullUpdateFromEasyPostForTrackingId("trk_32daac086c024672aa49dae16149b842");
//catchUpTrackingDataFromEasypost();
           // pullUpdateFromEasyPostForTrackingId("trk_b87386bb4b49473a868fd6641e7a65f8");

           // updateOwdTracker(17901397);
         /*   System.out.println(t.prettyPrint());

            log.debug(LocalDateTime.now().getHour());


            Gson gson = new GsonBuilder().create();
            Message message = gson.fromJson(t.,Message.class);
            ArrayList<OwdOrderTrackEvents> result_events = new ArrayList<>();
            result_events = message.getOwdOrderTrackingEvents();


            log.debug(result_events.size());*/
        updateOwdTracker(20189442);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void loodThroughRefunds(int letter) {

        try {
            Query q = HibernateSession.currentSession().createSQLQuery(" select shipmentID from zzLyftDuplicates WHERE LetterSent = :letter  and trackerStatus = 'pre_transit' and refundStatus is null");
            q.setParameter("letter", letter);
            List results = q.list();
            for (Object row : results) {
                refundShipment(row.toString());
            }
            for (Object row : results) {
                updaterefundShipment(row.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void refundShipment(String shipmentId) {

        try {
            EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";
            Shipment ship = Shipment.retrieve(shipmentId);
            ship.refund();

            String sql = "update zzLyftDuplicates set refundStatus = :status where shipmentID = :shipmentId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            ship.refresh();
            System.out.println(ship.prettyPrint());
            q.setParameter("status", ship.getRefundStatus());
            q.setParameter("shipmentId", shipmentId);
            int i = q.executeUpdate();
            if (i > 0) {
                System.out.println("Success");

                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updaterefundShipment(String shipmentId) {

        try {
            EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";
            Shipment ship = Shipment.retrieve(shipmentId);
            // ship.refund();

            String sql = "update zzLyftDuplicates set refundStatus = :status where shipmentID = :shipmentId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            ship.refresh();
            System.out.println(ship.prettyPrint());
            q.setParameter("status", ship.getRefundStatus());
            q.setParameter("shipmentId", shipmentId);
            int i = q.executeUpdate();
            if (i > 0) {
                System.out.println("Success");

                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loopThroughLyftTrackers() {
        EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";

        try {
            String sql = "select trackerId from zzLyftDuplicates where trackerId is not null and (trackerStatus <> 'delivered' or trackerStatus is null)";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            ExecutorService exec = Executors.newFixedThreadPool(9);
            for (Object data : l) {
                exec.submit(new LyftTrackerUpdater(data.toString()));


            }
            exec.shutdown();
            System.out.println("waiting executor");
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            System.out.println("done");


        } catch (Exception e) {


            e.printStackTrace();
        }
    }

    public static void loopThroughNullTrackers() {
        try {
            String sql = "SELECT\n" +
                    "    dbo.owd_order_track.order_track_id\n" +
                    "FROM\n" +
                    "    dbo.owd_order_track\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order_track.order_fkey = dbo.owd_order.order_id)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order_track.tracking_id IS NULL\n" +
                    "AND dbo.owd_order.group_name = 'CDLYFT'\n" +
                    "AND dbo.owd_order_track.created_date < '2017-05-22'" +
                    "\n" +
                    "AND dbo.owd_order_track.created_date > '2017-05-01'\n" +
                    "AND dbo.owd_order_track.is_void = 0 ;";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            for (Object row : l) {
                updateOwdTracker(Integer.parseInt(row.toString()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void updateOwdTracker(Integer trackingId) {
        EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";

        try {

            OwdOrderTrack track = (OwdOrderTrack) HibernateSession.currentSession().load(OwdOrderTrack.class, trackingId);
            log.debug(track.getOrderTrackId());
            log.debug("loaded owd tracker");

            if (track.getTrackingNo().length() > 0) {
                log.debug("We have a tracking greater than 0");
                Tracker t;


                    log.debug("No current tracker, we need to create one");
                    if (null == track.getServiceCode() || track.getServiceCode().length() == 0) {
                        // do a lookup to get ship method.
                        t = createNewTracker(track.getTrackingNo(), translateCode(getShipMethodFromTrackingId(track.getOrderTrackId()+"")));

                    } else {
                        t = createNewTracker(track.getTrackingNo(), translateCode(track.getServiceCode()));
                    }


                    log.debug("We have a tracker. Updating the id now");

                    track.setStatusUrl(t.getPublicUrl());

                log.debug("Set status");

                track.setStatus(t.getStatus());
                log.debug("set trcking id");
                track.setTrackingId(t.getId());


                HibernateSession.currentSession().saveOrUpdate(track);
                log.debug("Saved");
                HibUtils.commit(HibernateSession.currentSession());
                log.debug("Committed");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static Tracker createNewTracker(String trackingNum, String carrier) throws Exception {
        EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";


        Map<String, Object> trackerMap = new HashMap<String, Object>();
        trackerMap.put("tracking_code", trackingNum);
        trackerMap.put("carrier", carrier);
        log.debug("Creating new tracker");

        System.out.println(trackerMap);

        Tracker tracker = Tracker.create(trackerMap);
       // log.debug(tracker.prettyPrint());


        return tracker;


    }


    public static String translateCode(String code) {

        code = code.toUpperCase();
        if(code.equals("CONNECTSHIP_GLOBAL.APC.PRIDDPDC")){
            return "Passport";
        }
        if(code.equals("ONTRAC.GROUND")){
            return "OnTrac";
        }
        if(code.equals("BWTI_UPS.UPS.MIE")){
            return "UPSMailInnovations";
        }
        if (code.contains("OSM")) {
            code = "USPS";
        }
        if (code.contains("USPS")) {
            code = "USPS";
        }
        if (code.contains("FEDEX")||code.equals("BWTI_FXRS.FXRS.2DAI")||code.equals("BWTI_FXRS.FXRS.STD")) {
            code = "FEDEX";
        }
        if (code.contains("UPS")) {
            code = "UPS";
        }
        if (code.toUpperCase().contains("DHL EXPRESS")||code.toUpperCase().contains("DHL.DHL.WPX")) {
            code = "DHLExpress";
        }
        if(code.toUpperCase().contains("DHL SMART") || code.toUpperCase().contains("DHL GLOBAL")||code.toUpperCase().contains("DHL PARCEL INTERNATIONAL")||code.contains("CONNECTSHIP_DHLGLOBALMAIL")){
            code = "DHLGlobalMail";
        }
        return code;

    }


    public static String getShipMethodFromTrackingId(String trackingId) throws Exception {
        log.debug("Getting method for id: " + trackingId);

        String sql = "SELECT\n" +
                "    isnull(method_name,dbo.owd_order_ship_info.carr_service)\n" +
                "FROM\n" +
                "    dbo.owd_order_track\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order_track.order_fkey = dbo.owd_order_ship_info.order_fkey)" +
                "Left join\n" +
                "owd_ship_methods\n" +
                "on\n" +
                "(\n" +
                " owd_order_track.service_code = owd_ship_methods.method_code\n" +
                ")     \n" +
                "WHERE\n" +
                "    dbo.owd_order_track.order_track_id = :trackingId ;";


        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("trackingId", trackingId);

        return q.list().get(0).toString();


    }

    /**
     * This method will fix stalled records that did not got properly updated and now new tracking is coming in.
     * Finds records with delivered events and updates to delivered
     * Finds records with out for delivery records and updates to out for delivery
     * Finds return to sender records and sets return to sender flag
     */

    public static void updateStalledRecords(){
       // String DeliveredSql = "update s set s.message = e.message, s.status = e.status, s.delivered_at = e.event_at from owd_order_track_current_status s join owd_order_track_events e on s.tracking_id = e.tracking_id and e.status = 'delivered'  where s.delivered_at  is null and return_to_sender = 0";
      //  String OutForDeliverySql  ="update s set s.message = e.message, s.status = e.status, s.delivered_at = e.event_at from owd_order_track_current_status s join owd_order_track_events e on s.tracking_id = e.tracking_id and e.status = 'out_for_delivery'  where s.delivered_at  is null and return_to_sender = 0";
        String ReturnToSenderSql = "update s set s.message ='Returned to Sender' , s.status = 'return_to_sender', return_to_sender = 1 from owd_order_track_current_status s join owd_order_track_events e on s.tracking_id = e.tracking_id and e.status = 'return_to_sender'  where s.delivered_at  is null and return_to_sender = 0";
        String ReturningSql = "update owd_order_track_current_status set return_to_sender = 1 where message like '%Returning package to shipper%' and return_to_sender = 0";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(ReturnToSenderSql);
            log.debug("Return to Sender: " + q.executeUpdate());
            HibUtils.commit(HibernateSession.currentSession());

            q = HibernateSession.currentSession().createSQLQuery(ReturningSql);
            log.debug("Return to Sender: " + q.executeUpdate());
            HibUtils.commit(HibernateSession.currentSession());
          /*  q = HibernateSession.currentSession().createSQLQuery(DeliveredSql);
            log.debug("Delivered updated : " + q.executeUpdate());
            HibUtils.commit(HibernateSession.currentSession());*/
          // only run this once day. Allow for delivered updates

         /* if(LocalDateTime.now().getHour() == 23) {
              q = HibernateSession.currentSession().createSQLQuery(OutForDeliverySql);
              log.debug("Out for Delivery: " + q.executeUpdate());
              HibUtils.commit(HibernateSession.currentSession());
          }*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void updateTransitTimes(){
        String TransitSql = "update owd_order_track_current_status set transit_days = dbo.getTransitDaysforTrackingId(tracking_id) WHERE (status = 'delivered' or status = 'out_for_delivery' or status = 'available_for_pickup' ) and transit_days is null ;\n";
       try {
           Query q = HibernateSession.currentSession().createSQLQuery(TransitSql);
           log.debug("Transit updated : " + q.executeUpdate());
           HibUtils.commit(HibernateSession.currentSession());
       }catch (Exception e){
           e.printStackTrace();
       }

    }


    /**
     * This method will give you the latest event stored in owd_order_track_events
     * @param trackingId tracking id to look up
     * @return OwdOrderTrackEvents
     * @throws Exception if no results found
     */
    public static OwdOrderTrackEvents getLatestTrackEventFromTrackingId(String trackingId) throws Exception{
        Criteria cr = HibernateSession.currentSession().createCriteria(OwdOrderTrackEvents.class);
        cr.add(Restrictions.eq("trackingId", trackingId));
        cr.addOrder(org.hibernate.criterion.Order.desc("eventAt"));
        cr.setMaxResults(1);

        List<OwdOrderTrackEvents> results = cr.list();

        if(results.size()>0){
            return results.get(0);
        }else{
            throw new Exception("Unable to load Events for tracking_id: "+trackingId);
        }


    }

    public static OwdOrderTrackCurrentStatus getTrackCurrentStatusFromTrackingId(String trackingId) throws Exception{

        Criteria cr = HibernateSession.currentSession().createCriteria(OwdOrderTrackCurrentStatus.class);
        cr.add(Restrictions.eq("trackingId",trackingId));
        List<OwdOrderTrackCurrentStatus> results = cr.list();

        if(results.size()>0){
            return results.get(0);
        }else{
            throw new Exception("Unable to load Current Status for tracking_id: "+trackingId);
        }

    }


    public static boolean updateCurrentStatusWithLatestTrackingEventData(String trackingId,boolean setUpdateFlag){
        boolean success = false;
        try{
            OwdOrderTrackCurrentStatus status = trackingUtilites.getTrackCurrentStatusFromTrackingId(trackingId);
            OwdOrderTrackEvents event = trackingUtilites.getLatestTrackEventFromTrackingId(trackingId);
            updateCurrentTrackStatusFromTrackEvent(status,event);
            log.debug("Setting flag: "+setUpdateFlag);
            status.setUpdateTried(setUpdateFlag);
            HibernateSession.currentSession().saveOrUpdate(status);
            HibUtils.commit(HibernateSession.currentSession());

            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }

    private static void updateCurrentTrackStatusFromTrackEvent(OwdOrderTrackCurrentStatus status,OwdOrderTrackEvents event){
        if(event.getEventAt().compareTo(status.getEventAt()) > 0) {
            status.setCarrierCode(event.getCarrierCode());
            status.setMessage(event.getMessage());
            status.setSource(event.getSource());
            status.setStatus(event.getStatus());
            status.setEventAt(event.getEventAt());
            if (event.getStatus().contains("delivered")||event.getStatus().equalsIgnoreCase("available_for_pickup")) {
                status.setDeliveredAt(event.getEventAt());
            }
        }
    }


    public static void catchOrderTrackCurrentStatusUpToLatestEvents(int numberToDo){

        String sql = "select top 1000 t.tracking_id from owd_order_track_current_status t where  tracking_id is not null and delivered_at is null and return_to_sender = 0 " +
                "and created_at > '2018-07-01' and event_at < (select max(event_at) from owd_order_track_events where tracking_id = t.tracking_id)";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
          //  q.setParameter("numberToDo",numberToDo);
            List l = q.list();

            for(Object data: l){
                log.debug("Doing: " + data.toString());
                log.debug(updateCurrentStatusWithLatestTrackingEventData(data.toString(),false));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public static void catchUpTrackingDataFromEasypost(){
        String sql = "select top 4000 t.tracking_id from owd_order_track_current_status t where  tracking_id is not null and delivered_at is null and return_to_sender = 0 and transit_days is null\n" +
                "and created_at > '2018-07-01' and updateTried = 0 and datediff(wk,created_at,getDate()) >2 and event_at = (select max(event_at) from owd_order_track_events where tracking_id = t.tracking_id)";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            //  q.setParameter("numberToDo",numberToDo);
            List l = q.list();

            ExecutorService exec = Executors.newFixedThreadPool(10);
            for (Object data : l) {
                exec.submit(new UpdateEventsFromEasyPostTask(data.toString()));


            }
            exec.shutdown();
            System.out.println("waiting executor");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static boolean pullUpdateFromEasyPostForTrackingId(String trackingId){

        boolean success = false;
        EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";
        try {
            Tracker t = Tracker.retrieve(trackingId);
            ArrayList<OwdOrderTrackEvents> events = getOwdOrderTrackingEvents(t);

            for(OwdOrderTrackEvents event:events){
                try {
                    HibernateSession.currentSession().save(event);
                    HibUtils.commit(HibernateSession.currentSession());

                }catch (Exception e){
                  //  log.debug(e.getMessage());
                    HibernateSession.currentSession().clear();
                }

            }


            updateCurrentStatusWithLatestTrackingEventData(trackingId,true);




        }catch (Exception  e){
            e.printStackTrace();
        }



        return true;
    }







    public static ArrayList<OwdOrderTrackEvents> getOwdOrderTrackingEvents(Tracker tracker) {
        ArrayList<OwdOrderTrackEvents> events = new ArrayList<>();
        DateFormat DF = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
        for(TrackingDetail detail: tracker.getTrackingDetails()){

            OwdOrderTrackEvents event = new OwdOrderTrackEvents();
           // com.owd.jobs.jobobjects.easypost.tracking.TrackingDetail detail = this.getResult().getTrackingDetails().get(x);
            try {
                if(!isNull(tracker.getCreatedAt())){
                    event.setCreatedAt(tracker.getCreatedAt());
                }else{
                    event.setCreatedAt(null);
                }
                if(!isNull(tracker.getUpdateAt())){
                    event.setUpdatedAt(tracker.getUpdateAt());
                }else{
                    event.setUpdatedAt(null);
                }
                if(!isNull(detail.getDatetime())){
                    event.setEventAt(detail.getDatetime());
                }else{
                    event.setEventAt(null);
                }
                if(!isNull(tracker.getEstDeliveryDate())){
                    event.setEstDeliveryDate(tracker.getEstDeliveryDate());
                }else{
                    event.setEstDeliveryDate(null);
                }
            }catch(Exception e){

                e.printStackTrace();
            }
            event.setTrackingId(tracker.getId());
            event.setMessage(detail.getMessage());
            event.setStatus(detail.getStatus());
           /* event.setSource(detail.getSource());
            event.setCarrierCode(detail.getCarrierCode());
          */  event.setCity(detail.getTrackingLocation().getCity());
            event.setState(detail.getTrackingLocation().getState());
            event.setCountry(detail.getTrackingLocation().getCountry());
            event.setZip(detail.getTrackingLocation().getZip());
            events.add(event);
        }
        return events;
    }

    private static Date getDateFromInstant(Instant instant){
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }




}
