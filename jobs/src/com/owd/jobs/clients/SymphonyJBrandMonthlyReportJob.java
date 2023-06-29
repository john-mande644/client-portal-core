package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.DailySymphonyOrderStatusReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.ExcelReportSender;
import com.owd.jobs.jobobjects.excelreports.MonthlySymphonyJBrandMovementsReportDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class SymphonyJBrandMonthlyReportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {

            ExcelUtils.deliverReports(Arrays.asList(new MonthlySymphonyJBrandMovementsReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList(
                    "Waqas.Mir@jbrandjeans.com",
                    "Arin.Shahnazarian@jbrandjeans.com",
                    "gabriel.rodriguez@owd.com",
                    "sheena.bascara@jbrandjeans.com",
                    "Jessica.manosa@jbrandjeans.com",
                    "Thai.Nguyen@jbrandjeans.com",
                    "Bridgette.Marsh@jbrandjeans.com\n"
            ))));


        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    ""+625,
                    this.getClass().getName(),
                    LogableException.errorTypes.INTERNAL);
        }
    }
}
