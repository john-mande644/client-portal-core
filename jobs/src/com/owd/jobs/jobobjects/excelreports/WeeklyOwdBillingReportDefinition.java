package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class WeeklyOwdBillingReportDefinition extends MonthlyReportDefinition {


    public static void main(String[] args) {


        /*Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.DATE, -3);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        Date firstDatePastPeriod = firstDateOfPreviousMonth;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       String  periodTitle = df.format(firstDateOfPreviousMonth);
        System.out.println("Billing Report for " + periodTitle);*/
       // ExcelUtils.deliverReports(Arrays.asList(new WeeklyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C10CULRFT"))));

    }


    public WeeklyOwdBillingReportDefinition() {
        
        try {
            Date firstSaturday;
            Date lastSaturday;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR ,0) ;
            today.set(Calendar.HOUR_OF_DAY ,0)  ;
            today.set(Calendar.MINUTE ,0)  ;
            today.set(Calendar.SECOND ,0)    ;
            today.set(Calendar.MILLISECOND ,0)    ;

            if(today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                today.add(Calendar.DATE,-7);
            }else{
                while(today.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
                {
                    today.add(Calendar.DATE, -1);
                }
            }

            today.add(Calendar.DATE, 1);
            lastSaturday = today.getTime();
            today.add(Calendar.DATE, -7);

            firstSaturday = today.getTime();

            System.out.println(df.format(lastSaturday));
            System.out.println(df.format(firstSaturday));

            periodTitle = df.format(firstSaturday);
            subject = "Billing Report for Week Starting "+periodTitle ;


            clientName = "OWD";
            SheetDefinition sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Pick Pack Billing Report" ;
            sheet.sheetName=clientName+" Pick Pack " ;
            sheet.query=  "execute bill_weekly_pickpack_dateRange '"+df.format(firstSaturday) + "', '" + df.format(lastSaturday) + "'" ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Receive Billing Report" ;
            sheet.sheetName=clientName+" Receive " ;
            sheet.query=  "execute bill_weekly_receive_dateRange '"+df.format(firstSaturday) + "', '" + df.format(lastSaturday) + "'" ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Return Billing Report" ;
            sheet.sheetName=clientName+" Return " ;
            sheet.query=  "execute bill_weekly_return_dateRange '"+df.format(firstSaturday) + "', '" + df.format(lastSaturday) + "'" ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Storage Billing Report" ;
            sheet.sheetName=clientName+" Storage " ;
            Calendar cal = Calendar.getInstance();

            sheet.query="execute bill_weekly_cubic_storage " + (cal.get(Calendar.WEEK_OF_YEAR)-1) +"," + (cal.get(Calendar.YEAR));


            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " Dunnage Report";
            sheet.sheetName = clientName + "  Dunnage ";
            sheet.query = "SELECT\n" +
                    "    dbo.owd_client.company_name,\n" +
                    "    dbo.owd_order.group_name,\n" +
                    "    dbo.owd_order.facility_code,\n" +
                    "    dbo.package.dunnage_factor,\n" +
                    "    COUNT(dbo.package.id) as \"Packages\",\n" +
                    "    sum(case dunnage_factor\n" +
                    "             when 0 then 0\n" +
                    "             when 1 then .08\n" +
                    "             when 2 then .17\n" +
                    "             when 3 then .33\n" +
                    "             when 4 then 1.68\n" +
                    "             end  \n" +
                    "    ) as \"Charges\"\n" +
                    "FROM\n" +
                    "    dbo.owd_order\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.owd_client\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.client_fkey = dbo.owd_client.client_id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "WHERE\n" +
                    "  owd_client.bill_weekly = 1 and  owd_order.shipped_on >= '" + df.format(firstSaturday) + "' and owd_order.shipped_on < '"+ df.format(lastSaturday) +"'  and dunnage_factor > 0\n" +
                    "GROUP BY\n" +
                    "    dbo.owd_client.company_name,\n" +
                    "    dbo.owd_order.group_name,\n" +
                    "    dbo.owd_order.facility_code,\n" +
                    "    dbo.package.dunnage_factor ;";
            sheets.add(sheet);

            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " Greeting Cards Report";
            sheet.sheetName = clientName + "  Greeting Cards ";
            sheet.query=  "SELECT * from vw_bill_greetingcard_daily" +
                    "                WHERE\n" +
                    "                  [bill date]>='" + df.format(firstSaturday) +"' and [bill date] < '" + df.format(lastSaturday) + "' order by client;";

            sheets.add(sheet);

            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " Personalization Report";
            sheet.sheetName = clientName + "  Personalization ";
            sheet.query=  "SELECT * from vw_bill_personalization_daily" +
                    "                WHERE\n" +
                    "                  [bill date]>='" + df.format(firstSaturday) +"' and [bill date] < '" + df.format(lastSaturday) + "' order by client;";

            sheets.add(sheet);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
