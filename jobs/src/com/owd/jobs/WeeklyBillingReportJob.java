package com.owd.jobs;

import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.AllInclusiveBillingWeeklyReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.WeeklyOwdBillingReportDefinition;

import java.util.Arrays;

/**
 * Created by danny on 8/22/2018.
 */
public class WeeklyBillingReportJob extends OWDStatefulJob {


    public static void main(String[] args){
        run();
    }


    public void internalExecute(){

        ExcelUtils.deliverReports(Arrays.asList(new WeeklyOwdBillingReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("accounting@owd.com", "mrhoades@owd.com"))));


    }

}
