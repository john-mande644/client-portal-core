package com.owd.jobs.jobobjects.excelreports;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class GildanReportDefinition extends WeeklyReportDefinition {


    public GildanReportDefinition(Date firstDatePastPeriod, Date firstDateOfPeriod) {

        super(firstDatePastPeriod,firstDateOfPeriod);

    }

    public GildanReportDefinition() {
        
        super();
            //For when you need to run a specific week
           /* Date beginWeek = new Date();
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR ,0) ;
            today.set(Calendar.HOUR_OF_DAY ,0)  ;
            today.set(Calendar.MINUTE ,0)  ;
            today.set(Calendar.SECOND ,0)    ;
            today.set(Calendar.MILLISECOND ,0);
            today.set(Calendar.MONTH,Calendar.DECEMBER);
            today.set(Calendar.YEAR,2018);
            today.set(Calendar.DAY_OF_MONTH,8);
            beginWeek = today.getTime();

            today.add(Calendar.DATE,7);
            firstDatePastPeriod = today.getTime();
            firstDateOfPeriod = beginWeek;*/

        clientName = "Gildan";
        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Gildan Online Summary" ;
        sheet.sheetTitle="Gildan Online Order Summary" ;
        sheet.query=  "SELECT\n" +
                "                    replace(order_type,'Magento','Gildan Online') AS 'Order Type',\n" +
                "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END) AS 'Total SD Tax',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END)                  AS 'Total Other Tax',\n" +
                "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                "                FROM\n" +
                "                    owd_order\n" +
                "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                "                LEFT OUTER JOIN vw_return_credits v\n" +
                "                ON\n" +
                "                    order_id=v.order_fkey\n" +
                "                WHERE\n" +
                "                    client_fkey in ("+sheet.clientFkey+")\n" +
                "                AND (order_type like '%magento%' or order_type like '%bigcommerce%' or order_type like '%Gildan.com%') and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                "                GROUP BY\n" +
                "                   replace(order_type,'Magento','Gildan Online') ";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Gildan Online SKU Report" ;
        sheet.sheetTitle="Gildan Online SKUs Shipped" ;
        sheet.query=  "SELECT\n" +
                "    i.inventory_num                AS 'SKU',\n" +
                "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                "FROM\n" +
                "    owd_inventory i (NOLOCK)\n" +
                "LEFT OUTER JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM([Qty Restocked]) AS rts,\n" +
                "            SUM([Qty Received])  AS ret,\n" +
                "            [Inventory ID]       AS inventory_id\n" +
                "        FROM\n" +
                "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and (order_type like '%magento%' or order_type like '%bigcommerce%' or order_type like '%Gildan.com%')\n" +
                "        WHERE\n" +
                "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                "        GROUP BY\n" +
                "            [Inventory ID]\n" +
                "    ) AS returnees\n" +
                "ON\n" +
                "    returnees.inventory_id=i.inventory_id\n" +
                "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                "JOIN owd_order (NOLOCK)\n" +
                "ON\n" +
                "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                "AND order_id=order_fkey\n" +
                "ON\n" +
                "    i.inventory_id=l.inventory_id\n" +
                "WHERE\n" +
                "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and (order_type like '%magento%' or order_type like '%bigcommerce%' or order_type like '%Gildan.com%' )\n" +
                "GROUP BY\n" +
                "    i.inventory_num,\n" +
                "    i.price,\n" +
                "    supp_cost,\n" +
                "    ret,\n" +
                "    rts";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Walmart Online Summary" ;
        sheet.sheetTitle="Walmart Order Summary" ;
        sheet.query=  "SELECT\n" +
                "                    order_type AS 'Order Type',\n" +
                "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END) AS 'Total SD Tax',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END)                  AS 'Total Other Tax',\n" +
                "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                "                FROM\n" +
                "                    owd_order\n" +
                "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                "                LEFT OUTER JOIN vw_return_credits v\n" +
                "                ON\n" +
                "                    order_id=v.order_fkey\n" +
                "                WHERE\n" +
                "                    client_fkey in ("+sheet.clientFkey+")\n" +
                "                AND order_type like '%walmart%' and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                "                GROUP BY\n" +
                "                   order_type";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Walmart.com SKU Report" ;
        sheet.sheetTitle="Walmart.com SKUs Shipped" ;
        sheet.query=  "SELECT\n" +
                "    i.inventory_num                AS 'SKU',\n" +
                "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                "FROM\n" +
                "    owd_inventory i (NOLOCK)\n" +
                "LEFT OUTER JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM([Qty Restocked]) AS rts,\n" +
                "            SUM([Qty Received])  AS ret,\n" +
                "            [Inventory ID]       AS inventory_id\n" +
                "        FROM\n" +
                "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and order_type like '%walmart%'\n" +
                "        WHERE\n" +
                "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                "        GROUP BY\n" +
                "            [Inventory ID]\n" +
                "    ) AS returnees\n" +
                "ON\n" +
                "    returnees.inventory_id=i.inventory_id\n" +
                "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                "JOIN owd_order (NOLOCK)\n" +
                "ON\n" +
                "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                "AND order_id=order_fkey\n" +
                "ON\n" +
                "    i.inventory_id=l.inventory_id\n" +
                "WHERE\n" +
                "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and order_type like '%walmart%'\n" +
                "GROUP BY\n" +
                "    i.inventory_num,\n" +
                "    i.price,\n" +
                "    supp_cost,\n" +
                "    ret,\n" +
                "    rts";
        sheets.add(sheet);

            //Ebay
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="Ebay Online Summary" ;
            sheet.sheetTitle="Ebay Order Summary" ;
            sheet.query=  "SELECT\n" +
                    "                    order_type AS 'Order Type',\n" +
                    "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                    "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                    "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                    "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                    "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END) AS 'Total SD Tax',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END)                  AS 'Total Other Tax',\n" +
                    "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                    "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                    "                FROM\n" +
                    "                    owd_order\n" +
                    "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                    "                LEFT OUTER JOIN vw_return_credits v\n" +
                    "                ON\n" +
                    "                    order_id=v.order_fkey\n" +
                    "                WHERE\n" +
                    "                    client_fkey in ("+sheet.clientFkey+")\n" +
                    "                AND order_type like '%ebay%' and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                    "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                    "                GROUP BY\n" +
                    "                   order_type";
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="Ebay SKU Report" ;
            sheet.sheetTitle="Ebay SKUs Shipped" ;
            sheet.query=  "SELECT\n" +
                    "    i.inventory_num                AS 'SKU',\n" +
                    "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                    "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                    "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                    "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                    "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                    "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                    "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                    "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                    "FROM\n" +
                    "    owd_inventory i (NOLOCK)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM([Qty Restocked]) AS rts,\n" +
                    "            SUM([Qty Received])  AS ret,\n" +
                    "            [Inventory ID]       AS inventory_id\n" +
                    "        FROM\n" +
                    "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and order_type like '%ebay%'\n" +
                    "        WHERE\n" +
                    "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "        GROUP BY\n" +
                    "            [Inventory ID]\n" +
                    "    ) AS returnees\n" +
                    "ON\n" +
                    "    returnees.inventory_id=i.inventory_id\n" +
                    "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                    "JOIN owd_order (NOLOCK)\n" +
                    "ON\n" +
                    "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "AND order_id=order_fkey\n" +
                    "ON\n" +
                    "    i.inventory_id=l.inventory_id\n" +
                    "WHERE\n" +
                    "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and order_type like '%ebay%'\n" +
                    "GROUP BY\n" +
                    "    i.inventory_num,\n" +
                    "    i.price,\n" +
                    "    supp_cost,\n" +
                    "    ret,\n" +
                    "    rts";
            sheets.add(sheet);

            //Ebay end
        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="JCPenney Online Summary" ;
        sheet.sheetTitle="JCPenney Order Summary" ;
        sheet.query=  "SELECT\n" +
                "                    order_type AS 'Order Type',\n" +
                "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END) AS 'Total SD Tax',\n" +
                "                    SUM(\n" +
                "                        CASE\n" +
                "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                "                            THEN tax_amount\n" +
                "                            ELSE 0.00\n" +
                "                        END)                  AS 'Total Other Tax',\n" +
                "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                "                FROM\n" +
                "                    owd_order\n" +
                "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                "                LEFT OUTER JOIN vw_return_credits v\n" +
                "                ON\n" +
                "                    order_id=v.order_fkey\n" +
                "                WHERE\n" +
                "                    client_fkey in ("+sheet.clientFkey+")\n" +
                "                AND order_type like '%jcpenney%' and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                "                GROUP BY\n" +
                "                   order_type ";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="JCPenney Online SKU Report" ;
        sheet.sheetTitle="JCPenney SKUs Shipped" ;
        sheet.query=  "SELECT\n" +
                "    i.inventory_num                AS 'SKU',\n" +
                "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                "FROM\n" +
                "    owd_inventory i (NOLOCK)\n" +
                "LEFT OUTER JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            SUM([Qty Restocked]) AS rts,\n" +
                "            SUM([Qty Received])  AS ret,\n" +
                "            [Inventory ID]       AS inventory_id\n" +
                "        FROM\n" +
                "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and order_type like '%jcpenney%'\n" +
                "        WHERE\n" +
                "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                "        GROUP BY\n" +
                "            [Inventory ID]\n" +
                "    ) AS returnees\n" +
                "ON\n" +
                "    returnees.inventory_id=i.inventory_id\n" +
                "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                "JOIN owd_order (NOLOCK)\n" +
                "ON\n" +
                "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                "AND order_id=order_fkey\n" +
                "ON\n" +
                "    i.inventory_id=l.inventory_id\n" +
                "WHERE\n" +
                "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and order_type like '%jcpenney%'\n" +
                "GROUP BY\n" +
                "    i.inventory_num,\n" +
                "    i.price,\n" +
                "    supp_cost,\n" +
                "    ret,\n" +
                "    rts";
        sheets.add(sheet);

        ///start dicks sporting goods
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="DSG Summary" ;
            sheet.sheetTitle="DSG Order Summary" ;
            sheet.query=  "SELECT\n" +
                    "                    replace(order_type,'Magento','Gildan Online') AS 'Order Type',\n" +
                    "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                    "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                    "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                    "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                    "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END) AS 'Total SD Tax',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END)                  AS 'Total Other Tax',\n" +
                    "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                    "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                    "                FROM\n" +
                    "                    owd_order\n" +
                    "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                    "                LEFT OUTER JOIN vw_return_credits v\n" +
                    "                ON\n" +
                    "                    order_id=v.order_fkey\n" +
                    "                WHERE\n" +
                    "                    client_fkey in ("+sheet.clientFkey+")\n" +
                    "                AND  order_type = ':EDI:' and owd_order.group_name = 'dicks' and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                    "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                    "                GROUP BY\n" +
                    "                   replace(order_type,'Magento','Gildan Online') ";
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="DSG SKU Report" ;
            sheet.sheetTitle="DSG SKUs Shipped" ;
            sheet.query=  "SELECT\n" +
                    "    i.inventory_num                AS 'SKU',\n" +
                    "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                    "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                    "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                    "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                    "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                    "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                    "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                    "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                    "FROM\n" +
                    "    owd_inventory i (NOLOCK)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM([Qty Restocked]) AS rts,\n" +
                    "            SUM([Qty Received])  AS ret,\n" +
                    "            [Inventory ID]       AS inventory_id\n" +
                    "        FROM\n" +
                    "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and  order_type = ':EDI:' and oo.group_name = 'dicks'\n" +
                    "        WHERE\n" +
                    "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "        GROUP BY\n" +
                    "            [Inventory ID]\n" +
                    "    ) AS returnees\n" +
                    "ON\n" +
                    "    returnees.inventory_id=i.inventory_id\n" +
                    "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                    "JOIN owd_order (NOLOCK)\n" +
                    "ON\n" +
                    "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "AND order_id=order_fkey\n" +
                    "ON\n" +
                    "    i.inventory_id=l.inventory_id\n" +
                    "WHERE\n" +
                    "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and order_type = ':EDI:' and owd_order.group_name = 'dicks'\n" +
                    "GROUP BY\n" +
                    "    i.inventory_num,\n" +
                    "    i.price,\n" +
                    "    supp_cost,\n" +
                    "    ret,\n" +
                    "    rts";
            sheets.add(sheet);




        ///end dicks


        ///amazon
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="Amazon Summary" ;
            sheet.sheetTitle="Amazon Order Summary" ;
            sheet.query=  "SELECT\n" +
                    "                    replace(order_type,'Magento','Gildan Online') AS 'Order Type',\n" +
                    "                    COUNT(DISTINCT(order_id))            AS 'Shipped Orders',\n" +
                    "                    SUM(shipped_units)                   AS 'Shipped Units',\n" +
                    "                    SUM(order_sub_total)                 AS 'Total Base Product Revenue',\n" +
                    "                    SUM(-1.00*ABS(discount))                        AS 'Total Discount',\n" +
                    "                    SUM(ship_handling_fee)               AS 'Total Shipping Revenue',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END) AS 'Total SD Tax',\n" +
                    "                    SUM(\n" +
                    "                        CASE\n" +
                    "                            WHEN ship_state NOT IN ('SD','South Dakota')\n" +
                    "                            THEN tax_amount\n" +
                    "                            ELSE 0.00\n" +
                    "                        END)                  AS 'Total Other Tax',\n" +
                    "                    SUM(order_total)          AS 'Total Net Sales',\n" +
                    "                    SUM(ISNULL(credits,0.00)) AS 'Total Return Credit'\n" +
                    "                FROM\n" +
                    "                    owd_order\n" +
                    "                JOIN owd_order_ship_info s on s.order_fkey=order_id\n" +
                    "                LEFT OUTER JOIN vw_return_credits v\n" +
                    "                ON\n" +
                    "                    order_id=v.order_fkey\n" +
                    "                WHERE\n" +
                    "                    client_fkey in ("+sheet.clientFkey+")\n" +
                    "                AND  order_type = ':EDI:' and owd_order.group_name = 'amazon' and post_date>='" + df.format(firstDateOfPeriod) + "'\n" +
                    "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                    "                GROUP BY\n" +
                    "                   replace(order_type,'Magento','Gildan Online') ";
            sheets.add(sheet);
            sheet = new SheetDefinition();
            sheet.clientFkey=471;
            sheet.sheetName="Amazon SKU Report" ;
            sheet.sheetTitle="Amazon SKUs Shipped" ;
            sheet.query=  "SELECT\n" +
                    "    i.inventory_num                AS 'SKU',\n" +
                    "    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
                    "    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
                    "    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
                    "    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
                    "        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
                    "    ISNULL(ret,0)                   AS 'Returned Units',\n" +
                    "    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
                    "    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
                    "FROM\n" +
                    "    owd_inventory i (NOLOCK)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM([Qty Restocked]) AS rts,\n" +
                    "            SUM([Qty Received])  AS ret,\n" +
                    "            [Inventory ID]       AS inventory_id\n" +
                    "        FROM\n" +
                    "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and  order_type = ':EDI:' and oo.group_name = 'amazon'\n" +
                    "        WHERE\n" +
                    "            [RETURN DATE] >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "        GROUP BY\n" +
                    "            [Inventory ID]\n" +
                    "    ) AS returnees\n" +
                    "ON\n" +
                    "    returnees.inventory_id=i.inventory_id\n" +
                    "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                    "JOIN owd_order (NOLOCK)\n" +
                    "ON\n" +
                    "    post_date >='" + df.format(firstDateOfPeriod) + "'\n" +
                    "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                    "AND order_id=order_fkey\n" +
                    "ON\n" +
                    "    i.inventory_id=l.inventory_id\n" +
                    "WHERE\n" +
                    "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') and order_type = ':EDI:' and owd_order.group_name = 'amazon'\n" +
                    "GROUP BY\n" +
                    "    i.inventory_num,\n" +
                    "    i.price,\n" +
                    "    supp_cost,\n" +
                    "    ret,\n" +
                    "    rts";
            sheets.add(sheet);

            //amazon ends



        sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Gildan Combined Inventory Count" ;
        sheet.sheetTitle="Gildan Combined Inventory Levels and Transactions" ;
            Calendar c = Calendar.getInstance();
            c.setTime(firstDatePastPeriod);
            c.add(Calendar.DATE,-1);
            firstDatePastPeriod = c.getTime();
          //  System.out.println(df.format(firstDatePastPeriod));
            sheet.query = "execute sp_getinventorymovementsummarybygroupfacility '" + df.format(firstDateOfPeriod) + "','"+ df.format(firstDatePastPeriod) +"',"+sheet.clientFkey+",'','DC1'";
        /*sheet.query=  "select SKU,max(descr) as Title,max(startcnt) as 'Start Count',sum(a) as Damaged, sum(b) as 'Shipped', sum(c) as 'Returned',sum(d) as 'Received',sum(e) as 'Adjusted',max(endcnt) as 'End Count' from\n" +
                "(select sku,(descr) as descr,ISNULL(startcnt,0) as startcnt,ISNULL(endcnt,0) as endcnt,\n" +
                "case when type='Damaged' then count else 0 end as a,\n" +
                "case when type='Posted Order' then count else 0 end as b,\n" +
                "case when type='Returned' then count else 0 end as c,\n" +
                "case when type='Received' then count else 0 end as d,\n" +
                "case when type not in ('Posted Order','Returned','Received','Damaged') then count else 0 end as e\n" +
                "from(\n" +
                "select sku,descr,type,isnull(startcnt,0) as startcnt,ISNULL(endcnt,0) as endcnt,t.cid,count,transactions,qoh from\n" +
                "\n" +
                "(select i.inventory_num as sku,i.inventory_id as iid,i.description as descr,transactiontype as type,\n" +
                "(sum(quantitychange))   as 'count',i.client_fkey as cid, count(*) as transactions,qty_on_hand as qoh from vw_item_history v\n" +
                "                      join owd_inventory i\n" +
                "                      join owd_inventory_oh h on h.inventory_fkey=i.inventory_id\n" +
                "                      on i.inventory_num=v.inventory_num and i.client_fkey=v.client_fkey\n" +
                "                      where v.client_fkey in ("+sheet.clientFkey+") and\n" +
                "                      transactiondate>='" + df.format(firstDateOfPeriod) + "' and transactiondate<'" + df.format(firstDatePastPeriod) + "'\n" +
                "                      and i.inventory_num not in ('Mail','PPS') and is_auto_inventory=0\n" +
                "                      group by qty_on_hand,i.inventory_id,i.client_fkey,i.description,transactiontype, i.inventory_num) as t\n" +
                "left outer join\n" +
                "\n" +
                "(select  i.inventory_num, i.inventory_id as iid,ISNULL(case when (qty_on_hand+sum(-1*ISNULL(quantitychange,0)))<0 then 0 else (qty_on_hand+sum(-1*ISNULL(quantitychange,0))) end,qty_on_hand)  as 'startcnt',i.client_fkey as cid\n" +
                "from owd_inventory i\n" +
                "left outer join vw_item_history v on i.inventory_num=v.inventory_num and i.client_fkey=v.client_fkey " +
                "and transactiondate>='" + df.format(firstDateOfPeriod) + "'\n" +
                "join owd_inventory_oh h on h.inventory_fkey=i.inventory_id\n" +
                "                     where i.client_fkey in ("+sheet.clientFkey+")\n" +
                "                     and i.inventory_num not in ('Mail','PPS') and is_auto_inventory=0\n" +
                "                     group by qty_on_hand,i.inventory_num,i.inventory_id,i.client_fkey,i.description) as c1\n" +
                "\n" +
                "on c1.iid=t.iid\n" +
                "left outer join\n" +
                "(select i.inventory_num,i.inventory_id as iid,\n" +
                "ISNULL(qty_on_hand+sum(-1*quantitychange),qty_on_hand)  as 'endcnt',\n" +
                "i.client_fkey as cid , qty_on_hand\n" +
                "from owd_inventory i\n" +
                "left outer join vw_item_history v on i.inventory_num=v.inventory_num and i.client_fkey=v.client_fkey " +
                "and transactiondate>='" + df.format(firstDatePastPeriod) + "'\n" +
                "join owd_inventory_oh h on h.inventory_fkey=i.inventory_id\n" +
                "                     where i.client_fkey in ("+sheet.clientFkey+")\n" +
                "                     and i.inventory_num not in ('Mail','PPS') and is_auto_inventory=0\n" +
                "                     group by qty_on_hand,i.inventory_num,i.inventory_id,i.client_fkey,i.description) as c2\n" +
                "on c2.iid=t.iid\n" +
                ") as hello\n" +
                ") as bob\n" +
                "group by sku";*/
        sheets.add(sheet);

    }
}
