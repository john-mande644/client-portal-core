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
public class WarehouseWorkOrderReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();


    String prepareSQL = "select dtopened as 'Opened',stitle as 'Title',\n" +
            "         sStatus as 'Status', sCategory as 'Category',b.sComputer as 'Client ID',datediff(day,dtOpened,dtClosed) as 'Days Open' \n" +
            "        ,sProject  as 'Project',hrsOrigEst as 'Original Estimate Hours', +\n" +
            "hrsCurrEst as 'Actual Hours', +\n" +
            "ROUND(hrsElapsed,2) as 'Billable Dollars', ROUND(case when hrsCurrEst = 0 then 0.00 else hrsElapsed/hrsCurrEst end,2) as 'Revenue per Labor Hour',\n" +
            "ixBug as 'Case ID', sArea as 'Area'  from bug b  (NOLOCK)      \n" +
            "join category c (NOLOCK) on c.ixCategory=b.ixCategory \n" +
            "join person p (NOLOCK) on p.ixPerson=b.ixPersonAssignedTo  \n" +
            "join status s (NOLOCK) on s.ixStatus=b.ixStatus \n" +
            "join area a (NOLOCK) on a.ixArea=b.ixArea \n" +
            "join project pro (NOLOCK) on pro.ixProject=b.ixProject \n" +
            "where fopen=0 and sProject in ('IT Work Orders','Fulfillment Work Orders') and dtClosed >= ? and dtClosed < dateadd(day,1,?)  and b.sComputer = ?";


    public WarehouseWorkOrderReport() {

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
        setDatasource(ReportDefinition.kDataSourceFogBugz);

    }

    //customized methods
    public List getParameters() {
        return paramList;

    }

    public String getPrepareSQL() {
        return prepareSQL;

    }


}
