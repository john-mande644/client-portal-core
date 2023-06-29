package com.owd.jobs.jobobjects.easypost;

import com.owd.jobs.OWDStatefulJob;

/**
 * Created by danny on 7/23/2018.
 */
public class TrackingTransitCalculationsAndCleanupJob extends OWDStatefulJob{


    public static void main(String[] args){
        run();
    }

    public void internalExecute(){

        try{

            trackingUtilites.updateStalledRecords();



            trackingUtilites.updateTransitTimes();

        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
