package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class SoldThroughWeeklyWorkOrderJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    public void internalExecute() {


        try{
            CasetrackerAPI.injectWorkOrder(("SoldThrough WO - Check damaged items for the week"), (
                    "Hello\n\n" +
                            "Please provide a photo along with the order number of each of the\n" +
                            "damaged items from this week.\n\n Thank you"),("SoldThrough"),("DC6"),709);
            log.debug("done");

        }catch(Exception ex)
        {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, "Error creating  work order","TS:"+ Calendar.getInstance().getTimeInMillis(), "709", "casetracker", LogableException.errorTypes.INTERNAL);
        }
    }
}
