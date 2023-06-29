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
public class DailyOrdersReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select "
            + "ship_date as 'Ship Date',"
            + "order_num as 'OWD Order Reference',"
            + "order_refnum as 'Order Reference',"
            + "carr_service as 'Shipping Method',"
            + "round(order_sub_total,2) as 'Subtotal',"
            + "round(tax_amount,2) as 'Sales Tax',"
            + "round(discount,2) as 'Discount',"
            + "round(ship_handling_fee,2) as 'Ship Handling Fee' from owd_order o (NOLOCK) join "
            + "owd_order_ship_info s (NOLOCK) on order_id=s.order_fkey join owd_order_track v (NOLOCK) on v.order_fkey=order_id and v.is_void=0 and line_index=1 where o.is_void=0 and post_date is not null"
            + " and ship_date >= ? and ship_date < dateadd(day,1,?)  and client_fkey=? "
            + "  order by ship_date asc";


    public DailyOrdersReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to actual ship date of order for this report.");
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
