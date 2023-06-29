package com.owd.jobs.jobobjects.excelreports;

import com.owd.core.managers.FacilitiesManager;
import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailySingleClientBillingReportDefinition extends MonthlyOwdBillingReportDefinition {


    public static void main(String[] args) {


        /*Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.DATE, -3);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        Date firstDatePastPeriod = firstDateOfPreviousMonth;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       String  periodTitle = df.format(firstDateOfPreviousMonth);
        System.out.println("Billing Report for " + periodTitle);*/
     //   ExcelUtils.deliverReports(Arrays.asList(new DailySingleClientBillingReportDefinition()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C10CULRFT"))));

        DailySingleClientBillingReportDefinition report = new DailySingleClientBillingReportDefinition("266","Algecal");


        ExcelUtils.deliverReports(Arrays.asList(report), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));


    }


    public DailySingleClientBillingReportDefinition(String Id, String Name) {
        clientId = Id;
        clientName = Name;
        
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.add(Calendar.DATE, -1);
            firstDateOfPreviousMonth = aCalendar.getTime();
            firstDatePastPeriod = firstDateOfPreviousMonth;

            periodTitle = df.format(firstDateOfPreviousMonth);
            subject = "Billing Report for " + periodTitle;



            sheets = new ArrayList<>();
            SheetDefinition sheet = null;
            for (String facility : FacilitiesManager.getActivePublicFacilityCodes()) {
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Pick Pack Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Pick Pack ";
                sheet.query = "SELECT * from vw_bill_pickpack_daily" +
                        "                WHERE\n" +
                        "          facility='"+facility+"' and        [bill date]='" + df.format(firstDateOfPreviousMonth) + "'  and  [Client ID] = "+clientId+" order by client,[group];";
                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Dunnage Report";
                sheet.sheetName = clientName + " "+facility+ " Dunnage ";
                sheet.query = "execute bill_dunnage_daily_client '"+ df.format(firstDateOfPreviousMonth) +"', '"+facility+"', "+clientId;

                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+ " Receive Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Receive ";
                sheet.query = "SELECT * from vw_bill_receive_daily" +
                        "                WHERE\n" +
                        "            facility='"+facility+"' and           [bill date]='" + df.format(firstDateOfPreviousMonth) + "' and  [Client ID] = "+clientId+" order by client,[group];;";
                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+ " Return Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Return ";
                sheet.query = "SELECT * from vw_bill_return_daily" +
                        "                WHERE\n" +
                        "            facility='"+facility+"' and           [bill date]='" + df.format(firstDateOfPreviousMonth) + "' and  [Client ID] = "+clientId+" order by client,[group];;";
                sheets.add(sheet);

            }





        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
