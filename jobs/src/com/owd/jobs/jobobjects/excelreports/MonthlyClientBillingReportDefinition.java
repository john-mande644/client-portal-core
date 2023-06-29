package com.owd.jobs.jobobjects.excelreports;

import java.time.LocalDate;
import java.util.Calendar;

public class MonthlyClientBillingReportDefinition extends MonthlyReportDefinition {
    public MonthlyClientBillingReportDefinition(int clientId, String customerName) {
        try {
            LocalDate lastMonth = LocalDate.now().minusMonths(1);
            LocalDate firstSaturday = lastMonth.withDayOfMonth(1);
            LocalDate lastSaturday = lastMonth.withDayOfMonth(lastMonth.getMonth().length(lastMonth.isLeapYear()));

            System.out.println(lastSaturday);
            System.out.println(firstSaturday);

            periodTitle = firstSaturday.toString();
            subject = "Billing Report for " + customerName + " Starting " + periodTitle ;

            clientName = "OWD";
            SheetDefinition sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Pick Pack Billing Report" ;
            sheet.sheetName=clientName+" Pick Pack " ;
            sheet.query=  "execute bill_weekly_pickpack_dateRange_client '"+firstSaturday + "', '" + lastSaturday + "', "+clientId ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Receive Billing Report" ;
            sheet.sheetName=clientName+" Receive " ;
            sheet.query=  "execute bill_weekly_receive_dateRange_client '"+ firstSaturday + "', '" + lastSaturday + "', "+clientId ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Return Billing Report" ;
            sheet.sheetName=clientName+" Return " ;
            sheet.query=  "execute bill_weekly_return_dateRange_client '" + firstSaturday + "', '" + lastSaturday + "', "+clientId ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle=clientName+" Storage Billing Report" ;
            sheet.sheetName=clientName+" Storage " ;
            Calendar cal = Calendar.getInstance();
            sheet.query="execute bill_weekly_cubic_storage_dateRange_client '"+ firstSaturday + "', '" + lastSaturday + "', "+clientId ;
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " Dunnage Report";
            sheet.sheetName = clientName + "  Dunnage ";
            sheet.query = "SELECT\n" +
                    "    owd_order.order_refnum as \"Client Reference\",\n" +
                    "    order_num as \"OWD Reference\"," +
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
                    "  owd_client.bill_weekly = 1 and  owd_order.shipped_on >= '" + firstSaturday + "' and owd_order.shipped_on <= '" + lastSaturday +"'  and dunnage_factor > 0 and dbo.owd_order.client_fkey = "+clientId +"\n" +
                    "GROUP BY\n" +
                    "    owd_order.order_refnum,\n" +
                    "    order_num," +
                    "    dbo.owd_order.facility_code,\n" +
                    "    dbo.package.dunnage_factor order by facility_code,order_refnum;";
            sheets.add(sheet);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}