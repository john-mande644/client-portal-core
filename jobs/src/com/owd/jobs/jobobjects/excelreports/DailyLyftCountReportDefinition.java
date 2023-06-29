package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class DailyLyftCountReportDefinition extends ReportDefinition {


    public DailyLyftCountReportDefinition(String locationCode) {

           this(locationCode,-1);
    }
        public DailyLyftCountReportDefinition(String locationCode, int daysOffset) {

        super();

        clientName = "Lyft "+locationCode;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar aCalendar = Calendar.getInstance();
        //  aCalendar.add(Calendar.MONTH, -1);
        aCalendar.add(Calendar.DATE, daysOffset);
        Date endDate = aCalendar.getTime();

      //  aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();

        periodTitle = " For "+df.format(startDate)+" through EOD "+df.format(endDate);
        subject = "Daily Count Check "+df.format(endDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle="Daily LYFT Count SKU QA Check" ;
        sheet.sheetName= df.format(endDate) ;
        sheet.query=  "select SKU,Title,Facility,[Starting Quantity],Received,Adjusted,Returned,Shipped,Damaged,[Ending Quantity] from udf_getinventorymovementsummarybygroupfacility('"+df.format(startDate)+"','"+df.format(endDate)+"',529,'','"+locationCode+"')" +
                " where SKU in ('LYFT150','LYFT158') order by SKU;";
        sheets.add(sheet);

    }
}
