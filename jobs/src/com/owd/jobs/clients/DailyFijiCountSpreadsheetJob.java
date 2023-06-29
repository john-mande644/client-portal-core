package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class DailyFijiCountSpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
     run();


      //  ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC7")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));


      //  ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC6")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));




    }

    public void internalExecute() {

        try {

            ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC7")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("jbissett@owd.com","dc7receiving@owd.com"))));


            ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC6")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("mesquibel@owd.com","rdelatorre@owd.com","harreola@owd.com","malexander@owd.com"))));




        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "489", "Daily Fiji Count Reports", LogableException.errorTypes.INTERNAL);
        }

    }
}
