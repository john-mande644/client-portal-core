package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class SymphonyDaily7pmReportsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {

            ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","alaframboise@owd.com","fops@symphonycommerce.com","kunjal@symphonycommerce.com"));


            sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","thoven@owd.com","alaframboise@owd.com","fops@symphonycommerce.com"));
            ExcelUtils.deliverReports(Arrays.asList(new  DailySymphonyOWDOrderStatusReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));



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
