package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class DailyFijiCountReportDefinition extends ReportDefinition {


    public DailyFijiCountReportDefinition(String locationCode) {

           this(locationCode,-1);
    }
        public DailyFijiCountReportDefinition(String locationCode, int daysOffset) {

        super();

        clientName = "Symphony Fiji "+locationCode;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar aCalendar = Calendar.getInstance();
        //  aCalendar.add(Calendar.MONTH, -1);
        aCalendar.add(Calendar.DATE, daysOffset);
        Date endDate = aCalendar.getTime();

      //  aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();

        periodTitle = " For "+df.format(startDate)+" through EOD "+df.format(endDate);
        subject = "Daily Lot Count Check "+df.format(endDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle="Daily Fiji Lot Count SKU QA Check" ;
        sheet.sheetName= df.format(endDate) ;
        sheet.query=  "select SKU,Title,Facility,[Starting Quantity],Received,Adjusted,Returned,Shipped,Damaged,[Ending Quantity] from udf_getinventorymovementsummarybygroupfacility('"+df.format(startDate)+"','"+df.format(endDate)+"',489,'G_fijiwaterV2134','"+locationCode+"')" +
                " where SKU in ('P206029','P206030','P206031','P206032','P223275','P223276','P223277','P223278','P224574','P228652','P666329') order by SKU;";
        sheets.add(sheet);

    }
}
