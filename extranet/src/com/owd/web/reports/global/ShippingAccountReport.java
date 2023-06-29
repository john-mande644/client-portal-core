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
public class ShippingAccountReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select company_name as Client, " +
            "pickupdate as \'Activity Date\'," +
            "billdate as \'Invoice Date\'," +
            "trackingNumber as \'Tracking Number\',ISNULL(carr_service,servicedescription) as \'Ship Method\', quantity as \'Package Count\'," +
            " ISNULL(list_value,servicedescription) as \'Type\',convert(money,netcharges)+convert(money,incentive) as \'Amount\'," +
            " client_incentive as \'Discount\',client_incentive_pct as \'Discount Pct\',item_value as \'Predicted Rate\',convert(money,netcharges)+convert(money,incentive)-client_incentive-item_value as \'Total Amount\'," +
            " reference1 as \'Reference1\',reference2 as \'Reference2\'" +
            " from ups_ebill u (NOLOCK) " +
            " left outer join owd_order (NOLOCK) on order_fkey=order_id " +
            " left outer join owd_order_ship_info s (NOLOCK) on s.order_fkey=u.order_fkey" +
            " join owd_client (NOLOCK) on client_id = u.client_fkey" +
            " left outer join owd_lists (NOLOCK) on list_name = \'UPSCodes\' and reference_num=transactioncode" +
            " where needs_review=0 and bill_client=1 and parent_ups_key is null and convert(money,netcharges)+convert(money,incentive)-client_incentive-item_value <> 0.00" +
            " and importdate >= ? and importdate < dateadd(day,1,?) and client_id=?";

    public ShippingAccountReport() {


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to actual date the carrier invoice was processed for this report.");
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
