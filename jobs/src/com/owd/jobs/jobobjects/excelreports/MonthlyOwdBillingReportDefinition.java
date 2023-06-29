package com.owd.jobs.jobobjects.excelreports;

import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class MonthlyOwdBillingReportDefinition extends MonthlyReportDefinition {


    public static void main(String[] args) {

    }
    public MonthlyOwdBillingReportDefinition(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super(firstDatePastPeriod,firstDateOfPreviousMonth);
    }


    String groupCode = "" ;
    String groupName = "" ;



    public MonthlyOwdBillingReportDefinition() {
        
        super();


        periodTitle = dfperiod.format(firstDateOfPreviousMonth);
        subject = "Billing Report for Month Starting "+periodTitle ;


        clientName = "OWD";
        SheetDefinition sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Pick Pack Billing Report" ;
        sheet.sheetName=clientName+" Pick Pack " ;
        sheet.query=  "SELECT * from vw_bill_pickpack_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client,[group];";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Receive Billing Report" ;
        sheet.sheetName=clientName+" Receive " ;
        sheet.query=  "SELECT * from vw_bill_receive_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client,[group];";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Return Billing Report" ;
        sheet.sheetName=clientName+" Return " ;
        sheet.query=  "SELECT * from vw_bill_return_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client,[group];";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Storage Billing Report" ;
        sheet.sheetName=clientName+" Storage " ;
        sheet.query=  "SELECT * from vw_bill_storage_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client,[group];";


        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle = clientName + " Dunnage Report";
        sheet.sheetName = clientName + "  Dunnage ";
        sheet.query = "execute bill_dunnage_monthly '"+ df.format(firstDateOfPreviousMonth) +"'";

        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle = clientName + " Greeting Cards Report";
        sheet.sheetName = clientName + "  Greeting Cards ";
        sheet.query=  "SELECT * from vw_bill_greetingcard_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client;";

        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle = clientName + " Personalization Report";
        sheet.sheetName = clientName + "  Personalization ";
        sheet.query=  "SELECT * from vw_bill_personalization_monthly" +
                "                WHERE\n" +
                "                  [month start]='" + df.format(firstDateOfPreviousMonth) +"' order by client;";

        sheets.add(sheet);
          /*
         select  CONVERT(DATETIME,CONVERT(VARCHAR,DATEPART(YEAR,dtclosed))+'-'+RIGHT('00'+CAST(DATEPART(MONTH,dtclosed) AS VARCHAR(2)),2 ) +'-01') AS 'Month Start',b.ixbug as 'Case ID', dtopened as 'Opened',dtclosed as 'Closed',stitle as 'Title',scustomeremail as 'Contact Email',slatesttextsummary as 'Last Message',billedxminutesxxonlyxminutesxxX57 as 'Billed Minutes',
symphonyxbrands3b as 'Brand Code',billingXdepartmentX63 as 'Department', workxorderxlocationm7a as 'Location'
from FOGBUGZ.FogBugz.dbo.bug b join FOGBUGZ.FogBugz.dbo.plugin_6_custombugdata p on b.ixbug=p.ixbug
where b.ixproject=310 and billingXdepartmentX63<>'not billed'
and CONVERT(DATETIME,CONVERT(VARCHAR,DATEPART(YEAR,dtclosed))+'-'+RIGHT('00'+CAST(DATEPART(MONTH,dtclosed) AS VARCHAR(2)),2 ) +'-01')='2016-02-01'
and billedxminutesxxonlyxminutesxxX57>0
order by b.ixbug desc
           */
    }
}
