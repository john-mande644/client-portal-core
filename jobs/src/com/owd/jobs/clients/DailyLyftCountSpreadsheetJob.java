package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.DailyFijiCountReportDefinition;
import com.owd.jobs.jobobjects.excelreports.DailyLyftCountReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class DailyLyftCountSpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
     run();


      //  ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC7")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));




    }

    public void internalExecute() {

        try {

            ExcelUtils.deliverReports(Arrays.asList(new DailyLyftCountReportDefinition("DC6")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("mesquibel@owd.com","malexander@owd.com","mhernandez@owd.com","jacosta@owd.com"))));






        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "529", "Daily Lyft Count Reports", LogableException.errorTypes.INTERNAL);
        }

    }
}
