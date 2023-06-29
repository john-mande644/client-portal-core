package com.owd.jobs.jobobjects.easypost;

import com.amazonaws.services.elasticmapreduce.util.StepFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdEasypostTrackEvents;
import com.owd.hibernate.generated.OwdOrderTrackCurrentStatus;
import com.owd.hibernate.generated.OwdOrderTrackEvents;
import com.owd.jobs.jobobjects.easypost.tracking.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.swing.text.html.HTMLDocument;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

public class ProcessEasypostTracker {
    private final static Logger log =  LogManager.getLogger();
    private ArrayList<Message> messages = new ArrayList<>();
    private static HashMap<String,Boolean> seenEntries;
    private static HashMap<String,OwdOrderTrackCurrentStatus> statusMap;

    private static int records_count;
    public static void main(String[] args){

        int i = 0;
        while (i < 8) {
            EasypostTrackerProcess();
            i++;
        }

    }

    public static void EasypostTrackerProcess (){
        ArrayList<OwdOrderTrackEvents> messages = new ArrayList<>();
        ArrayList<OwdOrderTrackEvents> result_events = new ArrayList<>();
        Set<OwdOrderTrackEvents> events = new LinkedHashSet<>();
        List<OwdEasypostTrackEvents> results;
        statusMap = new HashMap<>();
        seenEntries = new HashMap<>();
        System.out.println("ProcessEasypostTracker start");
        Gson gson = new GsonBuilder().create();
        int records = 0, reads = 0, inserts = 0, commits = 0, messages_count =0, exception = 0;
        Instant start = Instant.now();
        try {
//            Criteria cr = HibernateSession.currentSession().createCriteria(OwdEasypostTrackEvents.class);
//            cr.add(Restrictions.eq("processed", 0));
//            cr.setFirstResult(0);
//            cr.setMaxResults(1000);
//            cr.addOrder(Order.asc("id"));
//            List<OwdEasypostTrackEvents> results = cr.list();
            String sql ="SELECT top(5000) * FROM owd_easypost_track_events where processed=0  ORDER BY id ASC ";
            SQLQuery query = HibernateSession.currentSession().createSQLQuery(sql);
            query.addEntity(OwdEasypostTrackEvents.class);
            results = query.list();
            records_count = messages_count = results.size();
            reads++;
            for(OwdEasypostTrackEvents tracker: results){
                log.debug("Doing: "+tracker.getId());
                Message message = gson.fromJson(tracker.getRawJson(),Message.class);

             if(message.getDescription().contains("tracker")) {
                 result_events = message.getOwdOrderTrackingEvents();
                 for (OwdOrderTrackEvents event : result_events) {
                     if (isUnique(event)) {
                         events.add(event);
                     }
                 }
             }else{
                 log.debug("Not a easypost tracker event.");
                 log.debug(tracker.getRawJson());
             }
       //         messages.addAll(result_events);
            }

            records= events.size();
            for(OwdOrderTrackEvents message:events){
//                Criteria check = HibernateSession.currentSession().createCriteria(OwdOrderTrackEvents.class);
//                check.add(Restrictions.eq("trackingId",message.getTrackingId()));
//                check.add(Restrictions.eq("eventAt",message.getEventAt()));
//                check.add(Restrictions.eq("message",message.getMessage()));
//                check.add(Restrictions.eq("status",message.getStatus()));
//                List<OwdOrderTrackEvents> res = check.list();
             //   reads++;
//                if(res.size() == 0){
                    try {
                        //Save event to database
                        HibernateSession.currentSession().save(message);
                        HibUtils.commit(HibernateSession.currentSession());
                        commits++;
                        System.out.println("message fired a save event");
                        inserts++;
                        HibernateSession.currentSession().flush();
                        HibernateSession.currentSession().clear();
                    }catch(Exception ex){
                        exception++;
                        HibernateSession.currentSession().clear();

                    }
                    updateTrackingStatus(message);

//                }
            }
            updateStatusBatch();
            updateTrackerStatus(results);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                HibernateSession.closeSession();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Duration time = Duration.between(start,Instant.now());
        System.out.println("Time: " + time.getSeconds() + " Seconds");
        System.out.println("Messages: " + messages_count);
        System.out.println("Events: " + records);
        System.out.println("Reads: " + reads);
        System.out.println("Inserts: " + inserts);
        System.out.println("Commits: " + commits);
    }

    private static boolean isUnique(OwdOrderTrackEvents event){
        try {
            String key = event.getTrackingId() + event.getEventAt().toString() + event.getMessage() + event.getStatus();
            if (isNull(seenEntries.get(key))) {
                seenEntries.put(key, true);
                return true;
            }
            return false;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private static void updateTrackingStatus(OwdOrderTrackEvents event){
        String key = event.getTrackingId();
        OwdOrderTrackCurrentStatus record = statusMap.get(key);
        if(isNull(record)){
            //Key not found yet in current Status update Map. Create record
            record = getCurrentTrackStatusFromTrackEvent(event);
            statusMap.put(key,record);
        }else {
            //Key has been found, update stored record in map to get latest event
            updateCurrentTrackStatusFromTrackEvent(record, event);
        }
    }

    private static OwdOrderTrackCurrentStatus getCurrentTrackStatusFromTrackEvent(OwdOrderTrackEvents event){
        OwdOrderTrackCurrentStatus status = new OwdOrderTrackCurrentStatus();
        status.setCarrierCode(event.getCarrierCode());
        status.setCreatedAt(event.getCreatedAt());
        status.setMessage(event.getMessage());
        status.setSource(event.getSource());
        status.setStatus(event.getStatus());
        status.setTrackingId(event.getTrackingId());
        status.setEventAt(event.getEventAt());
        status.setUpdateTried(false);
        if(event.getStatus().contains("delivered")||event.getStatus().equalsIgnoreCase("available_for_pickup")) {
            status.setDeliveredAt(event.getEventAt());
        }
        return status;
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

    
    private static void updateStatusBatch(){
        Iterator it = statusMap.entrySet().iterator();
        ExecutorService exec = Executors.newFixedThreadPool(4);
        while(it.hasNext()){
            try {
                Map.Entry<String,OwdOrderTrackCurrentStatus> entry = (Map.Entry) it.next();
                OwdOrderTrackCurrentStatus record = (OwdOrderTrackCurrentStatus) entry.getValue();
                exec.submit(new UpdateCurrentStatusTask(record));

               /* Map.Entry<String,OwdOrderTrackCurrentStatus> entry = (Map.Entry) it.next();
                OwdOrderTrackCurrentStatus record = (OwdOrderTrackCurrentStatus) entry.getValue();
                Criteria cr = HibernateSession.currentSession().createCriteria(OwdOrderTrackCurrentStatus.class);
                cr.add(Restrictions.eq("trackingId", record.getTrackingId()));
                List<OwdOrderTrackCurrentStatus> results = cr.list();
                if(results.size() > 0) {
                    if(record.getEventAt().compareTo(results.get(0).getEventAt()) > 0) {
                        OwdOrderTrackCurrentStatus update = record;
                        record = results.get(0);
                        record.setMessage(update.getMessage());
                        record.setDeliveredAt(update.getDeliveredAt());
                        record.setEstDeliveryDate(update.getEstDeliveryDate());
                        record.setStatus(update.getStatus());
                        HibernateSession.currentSession().saveOrUpdate(record);
                        HibUtils.commit(HibernateSession.currentSession());
                        System.out.println("Update Tracking Status");
                    }else{
                        System.out.println("Skip old tracking Status");
                    }
                }else{
                    HibernateSession.currentSession().saveOrUpdate(record);
                    HibUtils.commit(HibernateSession.currentSession());
                    System.out.println("Create tracking Status");
                }*/
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        exec.shutdown();
        System.out.println("waiting executor");
        try {
            exec.awaitTermination(1, TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("done");

    }

    private static void updateTrackerStatus(List<OwdEasypostTrackEvents> trackers) {
        for (OwdEasypostTrackEvents tracker : trackers) {
            try{
                try{
                    tracker.setProcessed(1);
                    HibernateSession.currentSession().update(tracker);
                    HibUtils.commit(HibernateSession.currentSession());
                    HibernateSession.currentSession().flush();
                    HibernateSession.currentSession().clear();
                    System.out.println("marking message as processed");
                }catch(Exception ex){
                    ex.printStackTrace();
                    HibernateSession.currentSession().clear();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}


