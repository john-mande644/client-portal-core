package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class SymphonyDailyReportsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

     //   ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","dnickels@owd.com","thoven@owd.com"));
      //  ExcelUtils.deliverReports(Arrays.asList(new DailySymphonyOWDOrderStatusReportDefinition()), Arrays.asList(new ExcelReportSender[]{sender}));
run();

    }

    public void internalExecute() {

        try {

          //  ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","alaframboise@owd.com","fops@symphonycommerce.com","kunjal@symphonycommerce.com"));

          //  ExcelUtils.deliverReports(Arrays.asList(new DailySymphonyOrderStatusReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));

            ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","diane.gutentag@halston.com","rachel.liu@halston.com","armando.arce@halston.com","benjamin.hwang@halston.com","Steve@symphonycommerce.com","Narissal@symphonycommerce.com"));
            ExcelUtils.deliverReports(Arrays.asList(new DailyHalstonInventoryReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));


           // sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","dnickels@owd.com","thoven@owd.com"));
          //  ExcelUtils.deliverReports(Arrays.asList(new  DailySymphonyOWDOrderStatusReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));



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
