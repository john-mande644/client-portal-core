package com.owd.jobs;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.SqlUtils;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class MonthlyActivitySpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
       // ExcelUtils.deliverReports(Arrays.asList(new GildanReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));




    }

    public void internalExecute() {

        try {


            ExcelUtils.deliverReports(Arrays.asList(new LifespanReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","robyn@xendurance.com"))));

            //ExcelUtils.deliverReports(Arrays.asList(new EverythingDacorReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","amedina@dacor.com","cchoy@dacor.com"))));

           // ExcelUtils.deliverReports(Arrays.asList(new RHLReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("gina.george@pharmacareus.com","maria.paulino@pharmacareus.com","art.cerveny@pharmacareus.com","julian.chang@pharmacare.com.au","patricia.mccauley@pharmacare.com.au"))));



            ExcelUtils.deliverReports(Arrays.asList(new MonthlyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com","alaframboise@owd.com","accounting@owd.com"))));



        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "55", "Monthly Financial Reports", LogableException.errorTypes.INTERNAL);
        }

    }
}
