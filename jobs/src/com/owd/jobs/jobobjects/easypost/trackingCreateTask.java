package com.owd.jobs.jobobjects.easypost;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 6/11/2018.
 */
public class trackingCreateTask implements Runnable{
    private final static Logger log =  LogManager.getLogger();
    private Integer trackingId;

    trackingCreateTask(Integer trackId){
        trackingId = trackId;
    }

    public void run(){

        try{
            log.debug("Doing tracker for : " +trackingId);
        trackingUtilites.updateOwdTracker(trackingId);

        }catch (Exception e){
            e.printStackTrace();

        }


    }
}
