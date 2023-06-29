package com.owd.jobs.jobobjects.excelreports;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by danny on 8/12/2016.
 */
public class SymphonyDailyOrderIssueReportDefinition extends ReportDefinition{





    public  SymphonyDailyOrderIssueReportDefinition(String group) {

        super();


            System.out.println(group + " Da group ");


            clientName = group;

            ReportDefinition rd = new ReportDefinition();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Calendar aCalendar = Calendar.getInstance();
            //  aCalendar.add(Calendar.MONTH, -1);
           // aCalendar.add(Calendar.DATE, daysOffset);
            Date endDate = aCalendar.getTime();

            //  aCalendar.add(Calendar.DATE, -1);
            Date startDate = aCalendar.getTime();

            //  rd.emails.add("ktorevell@owd.com");
            periodTitle = " Stockout Report for "+df.format(startDate);
            subject = "Stockout Report";
           // rd.clientName = client;
            SheetDefinition sheet = new SheetDefinition();
            sheet.sheetTitle= "Orders still in stockout";
            sheet.sheetName= group ;
            sheet.query="SELECT\n" +
                    "    dbo.owd_order.order_refnum,\n" +
                    "    dbo.owd_order_ship_holds.wh_hold_reason," +
                    "dbo.owd_order_ship_holds.created_date,\n" +
                    "   CAST(dbo.owd_order_ship_holds.wh_hold_notes AS VARCHAR(500)) AS wh_hold_notes  ,\n" +
                    "    dbo.owd_order.facility_code,\n" +
                    "    dbo.owd_order.group_name\n" +
                    "FROM\n" +
                    "    dbo.owd_order_ship_holds\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.owd_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order_ship_holds.order_fkey = dbo.owd_order.order_id)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order.client_fkey = 489\n" +
                    "AND dbo.owd_order_ship_holds.is_on_wh_hold = 1\n" +
                    "AND dbo.owd_order_ship_holds.wh_hold_reason = 'Inventory Stockout' " +
                    "AND group_name = '"+group+"';";
            sheets.add(sheet);



    }
}
