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
public class InventorySalesReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();

    String prepareSQL = "select l.inventory_num as 'SKU',\n" +
            "                                    convert(varchar,post_date,101) as 'Order Date',\n" +
            "                                    convert(int,sum(ISNULL(quantity_actual,0))) as 'Units', \n" +
            "                                    round(l.price,2) as 'Unit Price', \n" +
            "                                    round(sum(ISNULL(quantity_actual*l.price,0.00)),2) as 'Line Total'\n" +
            "         from  owd_line_item l (NOLOCK) \n" +
            "                    join owd_order (NOLOCK) on order_id=order_fkey and  is_void=0 and post_date is not null and post_date >= ? \n" +
            "and post_date < dateadd(day,1,?) \n" +
            "            and client_fkey=? and quantity_actual>0\n" +
            "            group by l.inventory_num,l.price,convert(varchar,post_date,101)\n" +
            "order by l.inventory_num,convert(varchar,post_date,101)";

    public InventorySalesReport() {

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
