package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class FOHStatusReprotsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {


            ExcelReportEmailSender  sender = new ExcelReportEmailSender(Arrays.asList("matthew.schiff@bendon.com","marianne.johns@fredericks.com"));
            ExcelUtils.deliverReports(Arrays.asList(new FOHSLAReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));

            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)==22){
                ExcelUtils.deliverReports(Arrays.asList(new DailyFredericksOWDOrderStatusReportDefinition()), Arrays.asList(new ExcelReportSender[]{sender}));

            }


        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    ""+489,
                    this.getClass().getName(),
                    LogableException.errorTypes.INTERNAL);
        }
    }
}
