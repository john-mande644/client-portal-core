package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.HVMNWeeklyCountReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Sean Chen on 5/9/2019 to send HVMN daily lot code count report
 */
public class HVMNWeeklyLotCodeJob extends OWDStatefulJob{

    private final static Logger log =  LogManager.getLogger();
    public static void main(String[] args) {
        run();
    }

    public void internalExecute() {

        String pattern1 = "yyyy-MM-dd";
        String pattern2 = "yyyy-MM-dd-HH-mm-ss";

        try{

            ExcelUtils.deliverReports(Arrays.asList(new HVMNWeeklyCountReportDefinition()),
                    Arrays.asList(new ExcelReportEmailSender(
                            Arrays.asList("chrissy@hvmn.com", "max@hvmn.com" ))));


        }catch (Exception e){
            throw new LogableException(e, e.getMessage(), "GENERIC", ""+576, this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);
        }
    }

}