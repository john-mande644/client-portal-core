package com.owd.jobs;

import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.WeeklyDeliverrBillingReportDefinition;


import java.util.Arrays;

public class WeeklyDeliverrBillingReportJob extends OWDStatefulJob{

    public static void main(String[] args){
        ExcelUtils.deliverReports(Arrays.asList(new WeeklyDeliverrBillingReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));

    }


    public void internalExecute(){

        ExcelUtils.deliverReports(Arrays.asList(new WeeklyDeliverrBillingReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("accounting@owd.com","tawna@owd.com"))));


    }
}
