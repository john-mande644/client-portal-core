package com.owd.jobs.clients;

import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.OWDInternalAPI.ApiUtils;
import com.owd.jobs.jobobjects.easypost.ProcessEasypostTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasypostTrackerProcessingJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();
    }

    @Override
    public void internalExecute() {

        log.debug("starting");


            ProcessEasypostTracker.EasypostTrackerProcess();

    }
}
