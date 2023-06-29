package com.owd.web.reports.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.reports.ReportDefinition;
import com.owd.web.reports.ReportParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 4, 2004
 * Time: 10:12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class AVSalesHistoryReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select inventory_num as 'SKU',datepart(month,post_date) as 'Month',datepart(year,post_date) as 'Year', sum(quantity_actual) as 'Unit Sales'\n" +
            "from owd_line_item l (NOLOCK) join owd_order (NOLOCK) on l.order_fkey=order_id\n" +
            "where post_date is not null  and inventory_num like ? and client_fkey=?\n" +
            "group by inventory_num,datepart(month,post_date),datepart(year,post_date)\n" +
            "order by inventory_num,datepart(year,post_date),datepart(month,post_date) ";


    public AVSalesHistoryReport() {


        //init parameter list
        ReportParameter param = new ReportParameter();

        param = new ReportParameter();
        param.setDisplayName("SKU");
        param.setDefaultValue("");
        param.setCurrentValue("");
        param.setFormValueName("sku");
        param.setParamDataType(ReportParameter.kParamTypeText);

        param.setDescription("SKU to retrieve sales history for - you may use the percent character as a wildcard<BR>Example: 'XX%' would find all SKUs starting with 'XX'");
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
