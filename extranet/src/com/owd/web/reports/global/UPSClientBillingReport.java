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
public class UPSClientBillingReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();


    String prepareSQL = "select company_name,sum(netcharges) as 'OWD Billed',sum(incentive) as 'OWD Incentive Received',\n" +
            "sum(client_incentive) as 'Client Incentive',sum(item_value) as 'Predicted Billing',\n" +
            "sum(convert(money,netcharges)+convert(money,incentive)-client_incentive-item_value) as 'Total SA Adjustment',\n" +
            "sum(convert(money,incentive)-client_incentive) as 'OWD Profit'\n" +
            " from ups_ebill (NOLOCK) join owd_client (NOLOCK) on client_fkey=client_id\n" +
            "where pickupdate >= ? and pickupdate < dateadd(day,1,?) and -1 <> ?\n" +
            "group by company_name";

    public UPSClientBillingReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to actual date the billed activity occurred for this report.");
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

        param.setDescription("Date report period ends (leave blank to end on today's date or use same value as start date for one day report)");
        paramList.add(param);
        setDatasource(ReportDefinition.kDataSourceOWD);

    }

    //customized methods
    public List getParameters() {
        return paramList;

    }

    public String getPrepareSQL() {
        return prepareSQL;

    }


}
