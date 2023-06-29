package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class LyftDailyShippedReportDefinition extends ReportDefinition {

    public LyftDailyShippedReportDefinition() {

        super();

        clientName = "Lyft";

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         DateFormat dfperiod = new SimpleDateFormat("MMMM yyyy");

        Calendar aCalendar = Calendar.getInstance();
        //  aCalendar.add(Calendar.MONTH, -1);
        aCalendar.add(Calendar.DATE, -1);
        Date endDate = aCalendar.getTime();

      //  aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();

        periodTitle = " For "+df.format(startDate);
        subject = "Shipped Orders "+df.format(startDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=529;
        sheet.sheetTitle="Daily Lyft Orders Shipped Report" ;
        sheet.sheetName= df.format(endDate) ;
        sheet.query=  "select bill_first_name+' '+bill_last_name as 'Customer Name',bill_company_name as 'Customer Company',bill_address_one as 'Customer Address 1',\n" +
                "bill_address_two as 'Customer Address 2',bill_city as 'Customer City',bill_state as 'Customer State',bill_zip as 'Customer Zip',o.tracking_nums as 'Tracking',\n" +
                "order_refnum as 'Client Reference',o.shipped_on as 'Shipped',OWD.dbo.udf_getOrderShippedSkuList(o.order_id) as 'Shipped SKUs',carr_service as 'Ship Method',o.shipped_cost as 'Estimated Ship Cost',\n" +
                "round(isnull((select sum(amount) from owdbill_shipping_trans where order_fkey=order_id),0),2) as 'Final Shipping Charges'\n" +
                "from owd_order o join owd_order_ship_info s on s.order_fkey=order_id\n" +
                "where client_fkey="+sheet.clientFkey+" and o.shipped_on='"+df.format(startDate)+"' ";
        sheets.add(sheet);

    }
}
