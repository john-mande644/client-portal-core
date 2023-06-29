package com.owd.jobs.TimeFrameJobs;

import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.CalifiaLotWeeklyReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;

import java.util.Arrays;

/**
 * Created by danny on 9/2/2017.
 */
public class FridayNoonJob extends OWDStatefulJob {


    public static void main(String[] args){

        run();

    }


    public void internalExecute(){

        try {

            ExcelUtils.deliverReports(Arrays.asList(new CalifiaLotWeeklyReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("leonard@califiafarms.com","jessel@califiafarms.com"," dmorales@califiafarms.com"))));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
