package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class DailyOWDBillingSpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
     //run();


       // ExcelUtils.deliverReports(Arrays.asList(new DailyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C10CULRFT"))));
        ExcelUtils.deliverReports(Arrays.asList(new DailyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));


    }

    public void internalExecute() {

        try {


            ExcelUtils.deliverReports(Arrays.asList(new DailyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C10CULRFT"))));
          //  ExcelUtils.deliverReports(Arrays.asList(new DailyLotErrorReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));
            ExcelUtils.deliverReports(Arrays.asList(new DailyUnlocatedInventory()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C08CW5KDM"))));
            DailySingleClientBillingReportDefinition report = new DailySingleClientBillingReportDefinition("622","Deliverr");


            ExcelUtils.deliverReports(Arrays.asList(report), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("alaframboise@owd.com"))));




        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "55", "Daily Billing Reports", LogableException.errorTypes.INTERNAL);
        }

    }
}
