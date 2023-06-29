package com.owd.web.reports.global;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.reports.ReportDefinition;
import com.owd.web.reports.ReportParameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class DailySalesReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    /*String prepareSQL = "select i.inventory_num as 'SKU',\n" +
            "                        case when convert(int,sum(ISNULL(quantity_actual,0))) >= max(qty_on_hand) THEN 1 ELSE 0 END as 'Sales>=Stock?', \n" +
            "                        max(ISNULL(i.description,'')) as 'Description', \n" +
            "                        max(qty_on_hand) as 'On Hand',\n" +
            "                        convert(int,sum(ISNULL(quantity_actual,0))) as 'Units Posted', \n" +
            "                        sum(ISNULL(quantity_back,0)) as 'Backordered',\n" +
            "                        max(ISNULL(mfr_part_num,'')) as 'Vendor/Supplier'\n" +
            "from owd_inventory i  \n" +
            "    join owd_inventory_oh h on h.inventory_fkey=i.inventory_id \n" +
            "    left outer join owd_line_item l \n" +
            "        join owd_order on order_id=order_fkey and  is_void=0 and post_date is not null and post_date >= ? and post_date < dateadd(day,1,?) \n" +
            "    on i.inventory_id = l.inventory_id\n" +
            "where i.client_fkey=? and i.is_active=1\n" +
            "group by i.inventory_num order by i.inventory_num asc";*/

    String prepareSQL = "\n" +
            "select i.inventory_num as 'SKU',i.harm_code as 'Supplier SKU',upc_code as 'UPC',isbn_code as 'ISBN',\n" +
            "                                    max(ISNULL(i.description,'')) as 'Description', supp_cost as 'Cost Price',\n" +
            "                                    max(qty_on_hand) as 'On Hand',\n" +
            "                                    convert(int,sum(ISNULL(quantity_actual,0))) as 'Units Posted', \n" +
            "                                    ISNULL((select sum(ISNULL(quantity_request,0)) from owd_line_item l1 join owd_order o2 on o2.order_id=l1.order_fkey and o2.is_void=0 and o2.is_future_ship=0 and is_backorder=1 \n" +
            "            where l1.inventory_id=i.inventory_id),0) as 'Backordered',\n" +
            "                                    round(i.price,2) as 'Unit Price', \n" +
            "                                    round(convert(money,sum(convert(money,ISNULL(quantity_actual,0))*i.price)),2) as 'Total Sales',\n" +
            "                                    max(ISNULL(mfr_part_num,'')) as 'Vendor/Supplier', max(ISNULL(keyword,'')) as 'Keyword' \n" +
            "            from owd_inventory i  (NOLOCK) \n" +
            "                join owd_inventory_oh h (NOLOCK) on h.inventory_fkey=i.inventory_id \n" +
            "                left outer join owd_line_item l (NOLOCK) \n" +
            "                    join owd_order (NOLOCK) on order_id=order_fkey and  is_void=0 and post_date is not null and post_date >= ? and post_date < dateadd(day,1,?) \n" +
            "                on i.inventory_id = l.inventory_id\n" +
            "            where i.client_fkey=? and i.is_active=1\n" +
            "            group by i.inventory_num,i.price,i.inventory_id,upc_code,harm_code,isbn_code,supp_cost order by i.inventory_num asc";

    public DailySalesReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to actual date the order was released to warehouse for this report.");
        param.setDefaultValue(formatter.format(now.getTime()));
        param.setCurrentValue(formatter.format(now.getTime()));
        param.setFormValueName("start_date");
        param.setParamDataType(ReportParameter.kParamTypeDate);
        paramList.add(param);


        param = new ReportParameter();
        param.setDisplayName("End Date");
        param.setDefaultValue(formatter.format(now.getTime()));
        param.setCurrentValue("");
        param.setFormValueName("end_date");
        param.setParamDataType(ReportParameter.kParamTypeDate);

        param.setDescription("Date report period ends (leave blank or use same value as start date for one day report)");
        paramList.add(param);

    }

    //customized methods
    public List getParameters() {
        return paramList;

    }

    public String getPrepareSQL() {
        return prepareSQL;

    }


}
