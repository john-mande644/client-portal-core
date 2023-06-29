package com.owd.jobs.jobobjects.excelreports;

import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class LifespanReportDefinition extends MonthlyReportDefinition {



    public LifespanReportDefinition(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super(firstDatePastPeriod,firstDateOfPreviousMonth);
    }

    public LifespanReportDefinition() {
        
        super();

        clientName = "Lifespan";
        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=320;
        sheet.sheetTitle=clientName+" Order Source Financial Report" ;
        sheet.sheetName=clientName+" Orders Report" ;
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
                "                AND  post_date>='" + df.format(firstDateOfPreviousMonth) + "'\n" +
                "                AND post_date<'" + df.format(firstDatePastPeriod) +"'\n" +
                "                GROUP BY\n" +
                "                   order_type ";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.clientFkey=320;
        sheet.sheetName=clientName+" SKU Report" ;
        sheet.sheetTitle=clientName+" SKU-based Financial Report" ;
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
                "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] \n" +
                "        WHERE\n" +
                "            [RETURN DATE] >='" + df.format(firstDateOfPreviousMonth) + "'\n" +
                "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                "        GROUP BY\n" +
                "            [Inventory ID]\n" +
                "    ) AS returnees\n" +
                "ON\n" +
                "    returnees.inventory_id=i.inventory_id\n" +
                "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                "JOIN owd_order (NOLOCK)\n" +
                "ON\n" +
                "    post_date >='" + df.format(firstDateOfPreviousMonth) + "'\n" +
                "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                "AND order_id=order_fkey\n" +
                "ON\n" +
                "    i.inventory_id=l.inventory_id\n" +
                "WHERE\n" +
                "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') \n" +
                "GROUP BY\n" +
                "    i.inventory_num,\n" +
                "    i.price,\n" +
                "    supp_cost,\n" +
                "    ret,\n" +
                "    rts";
        sheets.add(sheet);

        sheet = new SheetDefinition();
        sheet.clientFkey=320;
        sheet.sheetName=clientName+" Amazon SKU Report" ;
        sheet.sheetTitle=clientName+" Amazon SKU-based Financial Report" ;
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
                "            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_type='AMAZON' and oo.order_id=[Order Id] \n" +
                "        WHERE\n" +
                "            [RETURN DATE] >='" + df.format(firstDateOfPreviousMonth) + "'\n" +
                "        AND [RETURN DATE]<'" + df.format(firstDatePastPeriod) + "'\n" +
                "        GROUP BY\n" +
                "            [Inventory ID]\n" +
                "    ) AS returnees\n" +
                "ON\n" +
                "    returnees.inventory_id=i.inventory_id\n" +
                "LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
                "JOIN owd_order (NOLOCK)\n" +
                "ON\n" +
                "  order_type='AMAZON' and  post_date >='" + df.format(firstDateOfPreviousMonth) + "'\n" +
                "AND post_date<'" + df.format(firstDatePastPeriod) + "'\n" +
                "AND order_id=order_fkey\n" +
                "ON\n" +
                "    i.inventory_id=l.inventory_id\n" +
                "WHERE\n" +
                "    i.client_fkey in ("+sheet.clientFkey+") and i.inventory_num not in ('PPS','MAIL') \n" +
                "GROUP BY\n" +
                "    i.inventory_num,\n" +
                "    i.price,\n" +
                "    supp_cost,\n" +
                "    ret,\n" +
                "    rts";
        sheets.add(sheet);


        sheet = new SheetDefinition();
        sheet.clientFkey=320;
        sheet.sheetName=clientName+" Inventory Count" ;
        sheet.sheetTitle=clientName+" Inventory Levels and Transactions" ;
        sheet.query=  "select SKU,max(descr) as Title,max(startcnt) as 'Start Count',sum(a) as Damaged, sum(b) as 'Shipped', sum(c) as 'Returned',sum(d) as 'Received',sum(e) as 'Adjusted',max(endcnt) as 'End Count' from\n" +
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
                "                      transactiondate>='" + df.format(firstDateOfPreviousMonth) + "' and transactiondate<'" + df.format(firstDatePastPeriod) + "'\n" +
                "                      and i.inventory_num not in ('Mail','PPS') and is_auto_inventory=0\n" +
                "                      group by qty_on_hand,i.inventory_id,i.client_fkey,i.description,transactiontype, i.inventory_num) as t\n" +
                "left outer join\n" +
                "\n" +
                "(select  i.inventory_num, i.inventory_id as iid,ISNULL(case when (qty_on_hand+sum(-1*ISNULL(quantitychange,0)))<0 then 0 else (qty_on_hand+sum(-1*ISNULL(quantitychange,0))) end,qty_on_hand)  as 'startcnt',i.client_fkey as cid\n" +
                "from owd_inventory i\n" +
                "left outer join vw_item_history v on i.inventory_num=v.inventory_num and i.client_fkey=v.client_fkey " +
                "and transactiondate>='" + df.format(firstDateOfPreviousMonth) + "'\n" +
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
                "group by sku";
        sheets.add(sheet);

    }
}
