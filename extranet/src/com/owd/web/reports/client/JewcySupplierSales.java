package com.owd.web.reports.client;

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
 * Date: Mar 4, 2004
 * Time: 10:20:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class JewcySupplierSales extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    /* request per Allison
First Name
>Last Name
>Address 1
>Address 2
>City
>State
>Zip
>Phone
>email
>Order Date
>Order Number (OWD's number)
>Client order reference
>Payment Method
>Order Sub-Total
>Shipping
>Tax
>Discount
>
    */
    List paramList = new ArrayList();
    String prepareSQL = "select case when len(ISNULL(mfr_part_num,company_name))<1 THEN company_name ELSE  ISNULL(mfr_part_num,company_name) END as 'Supplier', l.inventory_num as 'SKU',l.description as 'Description',sum(quantity_actual) as 'Units',sum(quantity_actual*l.price) as 'Sales Total'\n" +
            "from owd_inventory i (NOLOCK) \n" +
            "join owd_line_item l (NOLOCK) \n" +
            "    join owd_order o (NOLOCK) \n" +
            "on o.order_id=l.order_fkey and o.is_void=0 and post_date >= ? and post_date < dateadd(day,1,?)\n" +
            "on l.inventory_id=i.inventory_id and quantity_actual>0\n" +
            "join owd_client (NOLOCK) on client_id=i.client_fkey\n" +
            "where i.client_fkey=?\n" +
            "group by ISNULL(mfr_part_num,company_name), l.inventory_num,l.description,company_name,mfr_part_num\n" +
            "order by case when len(ISNULL(mfr_part_num,company_name))<1 THEN company_name ELSE  ISNULL(mfr_part_num,company_name) END";


    public JewcySupplierSales() {

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
