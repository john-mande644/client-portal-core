package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailySymphonyOrderStatusReportDefinition extends ReportDefinition {


    public static void main(String[] args) {


        ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"));

        ExcelUtils.deliverReports(Arrays.asList(new DailySymphonyOrderStatusReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));

    }
    public DailySymphonyOrderStatusReportDefinition() {
        
        super();


        clientName = "Symphony";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfperiod = new SimpleDateFormat("MMMM yyyy");

        Calendar aCalendar = Calendar.getInstance();
        Date endDate = aCalendar.getTime();
        aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();


        periodTitle = ""+df.format(startDate)+" - "+df.format(endDate);
        subject = "Daily OWD Order Status "+df.format(endDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Late Ships "+df.format(startDate) ;
        sheet.sheetName=df.format(startDate)+" Shipments" ;
        sheet.query=  "select order_id as 'OWD Order Id',order_num as 'OWD Order Reference',\n" +
                " order_refnum as 'Symphony Shipment Id',\n" +
                " group_name as 'Symphony Brand',\n" +
                " facility_code as 'Facility',\n" +
                " carr_service as 'Ship Method',\n" +
                " clientunits as 'Original Item Units',\n" +
                " insertunits as 'OWD Added Units',\n" +
                "  order_status as 'Order Status',\n" +
                " created_date as 'Shipment Creation Date',\n" +
                "convert(datetime,convert(varchar,post_date,101)) as 'Release Date',\n" +
                "o.shipped_on as 'Ship Date',\n" +
                "dbo.bdatediff(convert(datetime,convert(varchar,created_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101)))) as  'Days Since Creation',\n" +
                "dbo.bdatediff(convert(datetime,convert(varchar,post_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101)))) as 'SLA Days',\n" +
                "case when carr_service='LTL' then 'LTL' else case when clientunits>10 then 'Over 10 Units' else case when insertunits>0 then 'Special Packaging' else '' end end end as 'SLA Exception Reason'\n" +
                "from owd_order o join owd_order_ship_info s on order_id=order_fkey \n" +
                "join (select \n" +
                "sum(case when cust_refnum<>''  then quantity_actual else 0 end) as clientunits,\n" +
                "sum(case when isnull(cust_refnum,'')=''  then quantity_actual else 0 end) as insertunits,\n" +
                "order_fkey from owd_line_item l  group by order_fkey) as units on units.order_fkey=order_id\n" +
                "where (o.shipped_on=dateadd(day,-1,convert(datetime,convert(varchar,getdate(),101))) or o.shipped_on is null)\n" +
                "and client_fkey=489 and dbo.bdatediff(convert(datetime,convert(varchar,post_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101))))>1\n" +
                "and order_status<>'void' and order_status in ('shipped') order by order_id desc";

          sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Late Unshipped as of "+df.format(endDate) ;
        sheet.sheetName=df.format(endDate)+" Late Unshipped" ;
        sheet.query=  "select order_id as 'OWD Order Id',order_num as 'OWD Order Reference',\n" +
                " order_refnum as 'Symphony Shipment Id',\n" +
                " group_name as 'Symphony Brand',\n" +
                " facility_code as 'Facility',\n" +
                " carr_service as 'Ship Method',\n" +
                " clientunits as 'Original Item Units',\n" +
                " insertunits as 'OWD Added Units',\n" +
                "  order_status as 'Order Status',\n" +
                " created_date as 'Shipment Creation Date',\n" +
                "convert(datetime,convert(varchar,post_date,101)) as 'Release Date',\n" +
                "o.shipped_on as 'Ship Date',\n" +
                "dbo.bdatediff(convert(datetime,convert(varchar,created_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101)))) as  'Days Since Creation',\n" +
                "dbo.bdatediff(convert(datetime,convert(varchar,post_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101)))) as 'SLA Days',\n" +
                "case when carr_service='LTL' then 'LTL' else case when clientunits>10 then 'Over 10 Units' else case when insertunits>0 then 'Special Packaging' else '' end end end as 'SLA Exception Reason'\n" +
                "from owd_order o join owd_order_ship_info s on order_id=order_fkey \n" +
                "join (select \n" +
                "sum(case when cust_refnum<>''  then quantity_actual else 0 end) as clientunits,\n" +
                "sum(case when isnull(cust_refnum,'')=''  then quantity_actual else 0 end) as insertunits,\n" +
                "order_fkey from owd_line_item l  group by order_fkey) as units on units.order_fkey=order_id\n" +
                "where (o.shipped_on=dateadd(day,-1,convert(datetime,convert(varchar,getdate(),101))) or o.shipped_on is null)\n" +
                "and client_fkey=489 and dbo.bdatediff(convert(datetime,convert(varchar,post_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101))))>1\n" +
                "and order_status<>'void' and order_status in ('at warehouse') order by order_id desc";

        sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Pending Unreleased as of "+df.format(endDate) ;
        sheet.sheetName=df.format(endDate)+" Pending Unreleased" ;
        sheet.query=  "select order_id as 'OWD Order Id',order_num as 'OWD Order Reference',\n" +
                " order_refnum as 'Symphony Shipment Id',\n" +
                " group_name as 'Symphony Brand',\n" +
                " facility_code as 'Facility',\n" +
                " carr_service as 'Ship Method',\n" +
                " clientunits as 'Original Item Units',\n" +
                " insertunits as 'OWD Added Units',\n" +
                "  order_status as 'Order Status',\n" +
                " created_date as 'Shipment Creation Date',\n" +
                "dbo.bdatediff(convert(datetime,convert(varchar,created_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101)))) as  'Days Since Creation',\n" +
                "case when carr_service='LTL' then 'LTL' else case when clientunits>10 then 'Over 10 Units' else case when insertunits>0 then 'Special Packaging' else '' end end end as 'SLA Exception Reason'\n" +
                "from owd_order o join owd_order_ship_info s on order_id=order_fkey \n" +
                "join (select \n" +
                "sum(case when cust_refnum<>''  then quantity_request else 0 end) as clientunits,\n" +
                "sum(case when isnull(cust_refnum,'')=''  then quantity_request else 0 end) as insertunits,\n" +
                "order_fkey from owd_line_item l  group by order_fkey) as units on units.order_fkey=order_id\n" +
                "where o.shipped_on is null\n" +
                "and client_fkey=489 and dbo.bdatediff(convert(datetime,convert(varchar,created_date,101)),isnull(o.shipped_on,convert(datetime,convert(varchar,getdate(),101))))>1\n" +
                "and group_name is not null and order_status<>'void' and order_status not in ('void','at warehouse','shipped') order by order_id desc";

        sheets.add(sheet);

    }
}
