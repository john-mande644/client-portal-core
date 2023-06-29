package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailyFredericksOWDOrderStatusReportDefinition extends ReportDefinition {


    public static void main(String[] args) {


        ExcelReportEmailSender sender = new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"));

        ExcelUtils.deliverReports(Arrays.asList(new DailyFredericksOWDOrderStatusReportDefinition()),Arrays.asList(new ExcelReportSender[]{sender}));

    }
    public DailyFredericksOWDOrderStatusReportDefinition() {
        
        super();


        clientName = "Fredericks";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfperiod = new SimpleDateFormat("MMMM yyyy");

        Calendar aCalendar = Calendar.getInstance();
        Date endDate = aCalendar.getTime();
        aCalendar.add(Calendar.DATE, -1);
        Date startDate = aCalendar.getTime();


        periodTitle = ""+df.format(startDate)+" - "+df.format(endDate);
        subject = "Daily Updated OWD Order Status "+df.format(endDate);

        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Late Ships "+df.format(startDate) ;
        sheet.sheetName=df.format(startDate)+" Shipments" ;
        sheet.query=  "SELECT\n" +
                "    order_id                                         AS 'OWD Order Id',\n" +
                "    order_num                                        AS 'OWD Order Reference',\n" +
                "    order_refnum                                     AS 'Symphony Shipment Id',\n" +
                "    group_name                                       AS 'Symphony Brand',\n" +
                "    facility_code                                    AS 'Facility',\n" +
                "    carr_service                                     AS 'Ship Method',\n" +
                "    clientunits                                      AS 'Original Item Units',\n" +
                "    insertunits                                      AS 'OWD Added Units',\n" +
                "    order_status                                     AS 'Order Status',\n" +
                "    o.created_date                                     AS 'Shipment Creation Date',\n" +
                "    CONVERT(DATETIME,CONVERT(VARCHAR,post_date,101)) AS 'Release Date',\n" +
                "    post_date                                        AS 'OWD Posted',\n" +
                "                    sla,\n" +
                "    o.shipped_on                                     AS 'Ship Date',\n" +
                "    dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,o.created_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101)))) AS 'Days Since Creation',\n" +
                "    dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,post_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101)))) AS 'SLA Days',\n" +
                "    CASE\n" +
                "        WHEN carr_service='LTL'\n" +
                "        THEN 'LTL'\n" +
                "        ELSE\n" +
                "            CASE\n" +
                "                WHEN clientunits>10\n" +
                "                THEN 'Over 10 Units'\n" +
                "                ELSE\n" +
                "                    CASE\n" +
                "                        WHEN insertunits>0\n" +
                "                        THEN 'Special Packaging'\n" +
                "                        ELSE ''\n" +
                "                    END\n" +
                "            END\n" +
                "    END AS 'SLA Exception Reason',\n" +
                "     h.is_on_wh_hold   AS 'ON Warehouse Hold',\n" +
                "                    wh_hold_reason    AS 'Hold Reason',\n" +
                "                    h.created_date    AS 'Hold Created',\n" +
                "                    h.resolution_date AS 'Hold Resolved On',\n" +
                "                    convert(varchar(5000),h.wh_hold_notes)   AS 'Hold Notes'\n" +
                "FROM\n" +
                "    owd_order o\n" +
                "JOIN\n" +
                "    owd_order_ship_info s\n" +
                "ON\n" +
                "    order_id=order_fkey\n" +
                "JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN cust_refnum<>''\n" +
                "                    THEN quantity_actual\n" +
                "                    ELSE 0\n" +
                "                END) AS clientunits,\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN ISNULL(cust_refnum,'')=''\n" +
                "                    THEN quantity_actual\n" +
                "                    ELSE 0\n" +
                "                END) AS insertunits,\n" +
                "            order_fkey\n" +
                "        FROM\n" +
                "            owd_line_item l\n" +
                "        GROUP BY\n" +
                "            order_fkey) AS units\n" +
                "ON\n" +
                "    units.order_fkey=order_id\n" +
                "    LEFT OUTER JOIN\n" +
                "                    owd_order_ship_holds AS h\n" +
                "                ON\n" +
                "                    o.order_id = h.order_fkey\n" +

                "  outer apply (select top 1 * from owd_order_track t\n" +
                "        where o.order_id = t.order_fkey and t.is_void = 0\n" +
                "        ) T    \n" +
                "    \n" +
                "    \n" +
                "WHERE\n" +
                "    ( o.shipped_on = CONVERT(DATETIME,CONVERT(VARCHAR,GETDATE(),101)))\n" +
                "AND client_fkey=489\n" +
                "and group_name = 'fredericks'"+
                "and sla < t.modified_date\n" +
                "AND order_status<>'void'\n" +
                "AND order_status IN ('shipped')\n" +
                "ORDER BY\n" +
                "    order_id DESC";

          sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Late Unshipped as of "+df.format(endDate) ;
        sheet.sheetName=df.format(endDate)+" Late Unshipped" ;
        sheet.query=  "SELECT\n" +
                "    order_id                                         AS 'OWD Order Id',\n" +
                "    order_num                                        AS 'OWD Order Reference',\n" +
                "    order_refnum                                     AS 'Symphony Shipment Id',\n" +
                "    group_name                                       AS 'Symphony Brand',\n" +
                "    facility_code                                    AS 'Facility',\n" +
                "    carr_service                                     AS 'Ship Method',\n" +
                "    clientunits                                      AS 'Original Item Units',\n" +
                "    insertunits                                      AS 'OWD Added Units',\n" +
                "    order_status                                     AS 'Order Status',\n" +
                "    o.created_date                                   AS 'Shipment Creation Date',\n" +
                "    CONVERT(DATETIME,CONVERT(VARCHAR,post_date,101)) AS 'Release Date',\n" +
                "    post_date                                        AS 'OWD Posted',\n" +
                "    sla,\n" +
                "    o.shipped_on AS 'Ship Date',\n" +
                "    dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,o.created_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101)))) AS 'Days Since Creation',\n" +
                "    dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,post_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101)))) AS 'SLA Days',\n" +
                "    CASE\n" +
                "        WHEN carr_service='LTL'\n" +
                "        THEN 'LTL'\n" +
                "        ELSE\n" +
                "            CASE\n" +
                "                WHEN clientunits>10\n" +
                "                THEN 'Over 10 Units'\n" +
                "                ELSE\n" +
                "                    CASE\n" +
                "                        WHEN insertunits>0\n" +
                "                        THEN 'Special Packaging'\n" +
                "                        ELSE ''\n" +
                "                    END\n" +
                "            END\n" +
                "    END               AS 'SLA Exception Reason',\n" +
                "    h.is_on_wh_hold   AS 'ON Warehouse Hold',\n" +
                "    wh_hold_reason    AS 'Hold Reason',\n" +
                "    h.created_date    AS 'Hold Created',\n" +
                "    h.resolution_date AS 'Hold Resolved On',\n" +
                "    convert(varchar(5000),h.wh_hold_notes)   AS 'Hold Notes'\n" +
                "FROM\n" +
                "    owd_order o\n" +
                "JOIN\n" +
                "    owd_order_ship_info s\n" +
                "ON\n" +
                "    order_id=order_fkey\n" +
                "JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN cust_refnum<>''\n" +
                "                    THEN quantity_actual\n" +
                "                    ELSE 0\n" +
                "                END) AS clientunits,\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN ISNULL(cust_refnum,'')=''\n" +
                "                    THEN quantity_actual\n" +
                "                    ELSE 0\n" +
                "                END) AS insertunits,\n" +
                "            order_fkey\n" +
                "        FROM\n" +
                "            owd_line_item l\n" +
                "        GROUP BY\n" +
                "            order_fkey) AS units\n" +
                "ON\n" +
                "    units.order_fkey=order_id\n" +
                "LEFT OUTER JOIN\n" +
                "    owd_order_ship_holds AS h\n" +
                "ON\n" +
                "    o.order_id = h.order_fkey\n" +
                "outer apply (select top 1 * from owd_order_track t\n" +
                "        where o.order_id = t.order_fkey and t.is_void = 0\n" +
                "        ) T    \n" +
                "    \n" +
                "    \n" +
                "WHERE\n" +
                "    ( t.modified_date > sla or\n" +
                "      o.shipped_on IS NULL)\n" +
                "AND client_fkey=489\n" +
                "and group_name = 'fredericks'"+
                "and sla < getDate()\n" +
                "AND order_status<>'void'\n" +
                "AND order_status IN ('at warehouse')\n" +
                "ORDER BY\n" +
                "    order_id DESC";

        sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey=489;
        sheet.sheetTitle=clientName+" Pending Unreleased as of "+df.format(endDate) ;
        sheet.sheetName=df.format(endDate)+" Pending Unreleased" ;
        sheet.query=  "SELECT\n" +
                "    order_id      AS 'OWD Order Id',\n" +
                "    order_num     AS 'OWD Order Reference',\n" +
                "    order_refnum  AS 'Symphony Shipment Id',\n" +
                "    group_name    AS 'Symphony Brand',\n" +
                "    facility_code AS 'Facility',\n" +
                "    carr_service  AS 'Ship Method',\n" +
                "    clientunits   AS 'Original Item Units',\n" +
                "    insertunits   AS 'OWD Added Units',\n" +
                "    order_status  AS 'Order Status',\n" +
                "    dbo.getNewestOrderHoldReason(order_id) as 'Hold Reason',\n" +
                "    created_date  AS 'Shipment Creation Date',\n" +
                "    dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,created_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101)))) AS 'Days Since Creation',\n" +
                "    CASE\n" +
                "        WHEN carr_service='LTL'\n" +
                "        THEN 'LTL'\n" +
                "        ELSE\n" +
                "            CASE\n" +
                "                WHEN clientunits>10\n" +
                "                THEN 'Over 10 Units'\n" +
                "                ELSE\n" +
                "                    CASE\n" +
                "                        WHEN insertunits>0\n" +
                "                        THEN 'Special Packaging'\n" +
                "                        ELSE ''\n" +
                "                    END\n" +
                "            END\n" +
                "    END AS 'SLA Exception Reason'\n" +
                "FROM\n" +
                "    owd_order o\n" +
                "JOIN\n" +
                "    owd_order_ship_info s\n" +
                "ON\n" +
                "    order_id=order_fkey\n" +
                "JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN cust_refnum<>''\n" +
                "                    THEN quantity_request\n" +
                "                    ELSE 0\n" +
                "                END) AS clientunits,\n" +
                "            SUM(\n" +
                "                CASE\n" +
                "                    WHEN ISNULL(cust_refnum,'')=''\n" +
                "                    THEN quantity_request\n" +
                "                    ELSE 0\n" +
                "                END) AS insertunits,\n" +
                "            order_fkey\n" +
                "        FROM\n" +
                "            owd_line_item l\n" +
                "        GROUP BY\n" +
                "            order_fkey) AS units\n" +
                "ON\n" +
                "    units.order_fkey=order_id\n" +
                "WHERE\n" +
                "    o.shipped_on IS NULL\n" +
                "AND client_fkey=489\n" +
                "and group_name = 'fredericks'"+
                "AND dbo.bdatediff(CONVERT(DATETIME,CONVERT(VARCHAR,created_date,101)),ISNULL(o.shipped_on,CONVERT\n" +
                "    (DATETIME,CONVERT(VARCHAR,GETDATE(),101))))>1\n" +
                "AND group_name IS NOT NULL\n" +
                "AND order_status<>'void'\n" +
                "AND order_status NOT IN ('void',\n" +
                "                         'at warehouse',\n" +
                "                         'shipped')\n" +
                "ORDER BY\n" +
                "    order_id DESC";

        sheets.add(sheet);

    }
}
