package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by danny on 5/19/2017.
 */
public class MonthlyBrangevityInventoryReport extends  MonthlyReportDefinition {


    public static void main(String[] args){


        ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"));

        ExcelUtils.deliverReports(Arrays.asList(new MonthlyBrangevityInventoryReport()), Arrays.asList(new ExcelReportSender[]{sender}));

    }


    public MonthlyBrangevityInventoryReport(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super(firstDatePastPeriod,firstDateOfPreviousMonth);
    }

    public MonthlyBrangevityInventoryReport(){
        super();


        clientName = "Coggevity";




        periodTitle = ""+df.format(lastDateOfPreviousMonth);
        subject = "OWD Stock In Warehouse "+df.format(lastDateOfPreviousMonth);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Inventory "+df.format(lastDateOfPreviousMonth) ;
        sheet.sheetName="stock" ;
        sheet.query=  "execute sp_getinventorymovementsummarybygroupfacility '"+df.format(firstDateOfPreviousMonth)+"','"+df.format(lastDateOfPreviousMonth)+"',489,'G_braingevityV2208','DC1'";

        sheets.add(sheet);




    }
}
