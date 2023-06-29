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
 * Date: May 4, 2004
 * Time: 8:28:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShipmentsReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select order_refnum as 'Order Reference', " +
            "order_num as 'OWD Reference'," +
            "ship_date as 'Ship Date'," +
            "ISNULL(tracking_no,'') as 'Tracking Number', " +
            "ISNULL(weight,0.00) as 'Weight-lbs', " +
            "ISNULL(total_billed,0.00) as 'Cost' " +
            " from owd_order_track t (NOLOCK) " +
            " join owd_order (NOLOCK) on order_fkey=order_id " +
            " where ship_date >= ? and ship_date < dateadd(day,1,?) " +
            " and client_fkey=? order by ship_date asc, order_id asc, line_index asc";

    public ShipmentsReport() {

        //init parameter list
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to actual date the shipment was processed for this report.");
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
