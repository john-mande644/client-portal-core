package com.owd.jobs.jobobjects.easypost;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 5/25/2019.
 */
public class UpdateEventsFromEasyPostTask implements Runnable {


    private final static Logger log =  LogManager.getLogger();
    private String trackingId;

    UpdateEventsFromEasyPostTask(String trackId){
        trackingId = trackId;
    }

    public void run(){

        try{
            log.debug("Doing tracker for : " +trackingId);
            trackingUtilites.pullUpdateFromEasyPostForTrackingId(trackingId);

        }catch (Exception e){
            e.printStackTrace();

        }


    }
}
