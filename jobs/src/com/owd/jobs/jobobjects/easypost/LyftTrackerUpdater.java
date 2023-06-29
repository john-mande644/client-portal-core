package com.owd.jobs.jobobjects.easypost;
import com.easypost.EasyPost;
import com.easypost.model.Tracker;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

/**
 * Created by danny on 6/24/2017.
 */
public class LyftTrackerUpdater implements Runnable {

    private String trackerId;

    LyftTrackerUpdater(String id){
        trackerId = id;
    }

    public void run(){
        try {
            EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";
            Tracker t = Tracker.retrieve(trackerId);
            System.out.println(t.getStatus());

            String sql2 = "update zzLyftDuplicates set trackerStatus = :status where trackerId = :tracker";
            Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
            qq.setParameter("status", t.getStatus());
            qq.setParameter("tracker", t.getId());
            qq.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception e){

        }

    }
}
