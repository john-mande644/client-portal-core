package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailyHalstonInventoryReportDefinition extends ReportDefinition {


    public static void main(String[] args) {


        ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"));

        ExcelUtils.deliverReports(Arrays.asList(new DailyHalstonInventoryReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));

    }
    public DailyHalstonInventoryReportDefinition() {
        
        super();


        clientName = "Halston";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfperiod = new SimpleDateFormat("MMMM yyyy");

        Calendar aCalendar = Calendar.getInstance();
        Date endDate = aCalendar.getTime();
        aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();


        periodTitle = ""+df.format(startDate);
        subject = "OWD Stock In Warehouse "+df.format(startDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+"Inventory "+df.format(startDate) ;
        sheet.sheetName="stock" ;
        sheet.query=  "execute sp_getinventorymovementsummarybygroupfacility '"+df.format(startDate)+"','"+df.format(startDate)+"',489,'G_halstonV2224','DC6'";

          sheets.add(sheet);



    }
}
