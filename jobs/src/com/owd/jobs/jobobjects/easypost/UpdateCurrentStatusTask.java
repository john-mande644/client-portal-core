package com.owd.jobs.jobobjects.easypost;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderTrackCurrentStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

/**
 * Created by danny on 12/18/2018.
 */
public class UpdateCurrentStatusTask  implements Runnable{



    private OwdOrderTrackCurrentStatus theRecord;


    public UpdateCurrentStatusTask(OwdOrderTrackCurrentStatus toProcess){
        theRecord = toProcess;
    }
    public void run(){

        try{
            processRecord(theRecord);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                HibernateSession.closeSession();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void processRecord(OwdOrderTrackCurrentStatus record) throws Exception{



        Criteria cr = HibernateSession.currentSession().createCriteria(OwdOrderTrackCurrentStatus.class);
        cr.add(Restrictions.eq("trackingId", record.getTrackingId()));
        List<OwdOrderTrackCurrentStatus> results = cr.list();
        if(results.size() > 0) {
            if(record.getEventAt().compareTo(results.get(0).getEventAt()) > 0) {
                OwdOrderTrackCurrentStatus update = record;
               //reset record to one currently in the database.
                record = results.get(0);
                record.setMessage(update.getMessage());
                record.setDeliveredAt(update.getDeliveredAt());
                record.setEstDeliveryDate(update.getEstDeliveryDate());
                record.setStatus(update.getStatus());
                record.setEventAt(update.getEventAt());
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
        }




    }
}
