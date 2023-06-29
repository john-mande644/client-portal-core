package com.owd.jobs;

import com.owd.LogableException;
import com.owd.jobs.clients.GildanWeeklyFtpFilesJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class WeeklyActivitySpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
       // run();



       // System.out.println(today);
       GildanReportDefinition report =  new GildanReportDefinition();
      //  ExcelUtils.deliverReports(Arrays.asList(report),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));


    }

    /**
     * Sean updated on 7/5/2019
     * Gildan Financial Activity Report Week
     */
    public void internalExecute() {

        try {



            ExcelUtils.deliverReports(Arrays.asList(new GildanReportDefinition()),
                    Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","SGibbs@gildan.com",
                            "SHowle@gildan.com", "sherman@gildan.com","bgettinger@gildan.com","apwilkinson@gildan" +
                                    ".com","sstribling@gildan.com","bmorris@gildan.com","smickle@gildan.com","sporter" +
                                    "@gildan.com","TKMcHale@gildan.com","SCates@gildan.com","RArce@gildan.com",
                            "JHoward@gildan.com","rarce@gildan.com","APeters@gildan.com","MGadd@gildan.com",
                            "HAnderson@gildan.com", "DKammerer@gildan.com", "MKonstanczer@gildan.com"))));
          // ExcelUtils.deliverReports(Arrays.asList(new GildanReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));
            ExcelUtils.deliverReports(Arrays.asList(new GildanSkuExemptionReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","kcreel@peds.com"))));


            GildanWeeklyFtpFilesJob gildan = new GildanWeeklyFtpFilesJob();
            gildan.internalExecute();

        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "55", "Weekly Financial Reports", LogableException.errorTypes.INTERNAL);
        }

    }
}
