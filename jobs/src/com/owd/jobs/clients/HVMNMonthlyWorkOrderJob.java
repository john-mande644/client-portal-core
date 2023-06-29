package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 * Created by Sean Chen on 5/9/2019 to send HVMN daily lot code count report
 */
public class HVMNMonthlyWorkOrderJob extends OWDStatefulJob{

    private final static Logger log =  LogManager.getLogger();

    public void internalExecute() {


        try{
            CasetrackerAPI.injectWorkOrder(("HVMN WO - Check all SKUs for Lots expiring in or within a month"), (
                    "Please Check all HVMN SKUs for Lots expiring in or within a month. Set the units aside and " +
                            "remove from inventory. Please send the SKU name, LOT, and number of units adjusted out " +
                            "back to the FS.\n\n Thank you"),("HVMN"),("DC6"),576);
            log.debug("done");

        }catch(Exception ex)
        {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, "Error creating  work order","TS:"+Calendar.getInstance().getTimeInMillis(), "576", "casetracker", LogableException.errorTypes.INTERNAL);

        }finally
        {
        }
    }
    public static void main(String[] args) throws Exception{


        run();


    }

}