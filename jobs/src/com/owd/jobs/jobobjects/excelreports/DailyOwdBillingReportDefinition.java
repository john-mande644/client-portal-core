package com.owd.jobs.jobobjects.excelreports;

import com.owd.core.managers.FacilitiesManager;
import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailyOwdBillingReportDefinition extends MonthlyOwdBillingReportDefinition {


    public static void main(String[] args) {


        /*Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.DATE, -3);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        Date firstDatePastPeriod = firstDateOfPreviousMonth;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       String  periodTitle = df.format(firstDateOfPreviousMonth);
        System.out.println("Billing Report for " + periodTitle);*/
        ExcelUtils.deliverReports(Arrays.asList(new DailyOwdBillingReportDefinition()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C10CULRFT"))));

    }


    public DailyOwdBillingReportDefinition() {
        
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.add(Calendar.DATE, -1);
            firstDateOfPreviousMonth = aCalendar.getTime();
            firstDatePastPeriod = firstDateOfPreviousMonth;

            periodTitle = df.format(firstDateOfPreviousMonth);
            subject = "Billing Report for " + periodTitle;


            clientName = "OWD";
            sheets = new ArrayList<>();
            SheetDefinition sheet = null;
            for (String facility : FacilitiesManager.getActivePublicFacilityCodes()) {
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Pick Pack Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Pick Pack ";
                sheet.query = "SELECT * from vw_bill_pickpack_daily" +
                        "                WHERE\n" +
                        "          facility='"+facility+"' and        [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];";
                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Dunnage Report";
                sheet.sheetName = clientName + " "+facility+ " Dunnage ";
                sheet.query = "execute bill_dunnage_daily '"+ df.format(firstDateOfPreviousMonth) +"', '"+facility+"'";


                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+ " Receive Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Receive ";
                sheet.query = "SELECT * from vw_bill_receive_daily" +
                        "                WHERE\n" +
                        "            facility='"+facility+"' and           [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];;";
                sheets.add(sheet);
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+ " Return Billing Report";
                sheet.sheetName = clientName + " "+facility+ " Return ";
                sheet.query = "SELECT * from vw_bill_return_daily" +
                        "                WHERE\n" +
                        "            facility='"+facility+"' and           [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];;";
                sheets.add(sheet);
            }

            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All DC Pick Pack Billing Report";
            sheet.sheetName = clientName + " All DC Pick Pack ";
            sheet.query = "SELECT * from vw_bill_pickpack_daily" +
                    "                WHERE\n" +
                    "                  [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];";
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All Dunnage Report";
            sheet.sheetName = clientName + " All DC Dunnage ";
            sheet.query = "execute bill_dunnage_daily_all '"+ df.format(firstDateOfPreviousMonth) +"'";

            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All DC Receive Billing Report";
            sheet.sheetName = clientName + " All DC Receive ";
            sheet.query = "SELECT * from vw_bill_receive_daily" +
                    "                WHERE\n" +
                    "                   [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];;";
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All DC Return Billing Report";
            sheet.sheetName = clientName + " All DC Return ";
            sheet.query = "SELECT * from vw_bill_return_daily" +
                    "                WHERE\n" +
                    "                      [bill date]='" + df.format(firstDateOfPreviousMonth) + "' order by client,[group];;";
            sheets.add(sheet);

            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All DC Greeting Cards Report";
            sheet.sheetName = clientName + " All DC Greeting Cards ";
            sheet.query=  "SELECT * from vw_bill_greetingcard_daily" +
                    "                WHERE\n" +
                    "                  [bill date]='" + df.format(firstDateOfPreviousMonth) +"' order by client;";

            sheets.add(sheet);

            sheet = new SheetDefinition();
            sheet.sheetTitle = clientName + " All DC Personalization Report";
            sheet.sheetName = clientName + " All DC Personalization";
            sheet.query=  "SELECT * from vw_bill_personalization_daily" +
                    "                WHERE\n" +
                    "                  [bill date]='" + df.format(firstDateOfPreviousMonth) +"' order by client;";

            sheets.add(sheet);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
