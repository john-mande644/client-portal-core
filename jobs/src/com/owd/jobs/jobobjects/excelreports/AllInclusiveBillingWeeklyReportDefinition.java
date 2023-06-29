package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by danny on 2/2/2018.
 */
public class AllInclusiveBillingWeeklyReportDefinition extends WeeklyReportDefinition{



    public Integer clientId = 0;

    public AllInclusiveBillingWeeklyReportDefinition(Integer clientFkey, String client){
        clientId     = clientFkey;
        clientName = client;

         Date firstSaturday;
        Date lastSaturday;

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

        System.out.println(lastSaturday);
        System.out.println(firstSaturday);
        subject =  "AllInclusive Billing Week Ending " + lastSaturday;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");




        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey= clientId;
        sheet.sheetName=clientName + "Pick-Pack" ;
        sheet.sheetTitle=clientName+ "Pick-Pack" ;
        sheet.query=  "select * from sp_bill_pickpack WHERE shipped_on >= '"+df.format(firstSaturday)+"' and [Client ID] ="+clientId+"and shipped_on< '"+df.format(lastSaturday)+"'";
        sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey= clientId;
        sheet.sheetName=clientName + "Shipping" ;
        sheet.sheetTitle=clientName+ "Shipping" ;
        sheet.query=  "select sp_bill_shipping.*, isnull(convert(varchar,owd_tags.value),0) as 'Method From Cart' from sp_bill_shipping\n" +
                "left join owd_tags on order_id = external_id and name = 'com.owd.shipping.AllInclusive' " +
                "WHERE recorded_date >= '"+df.format(firstSaturday)+"' and [Client ID] ="+clientId+" and recorded_date< '"+df.format(lastSaturday)+"' and activity = 'Freight Charges'";
        sheets.add(sheet);

    }
}
