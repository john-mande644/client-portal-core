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
public class JewcyCustomers extends ReportDefinition {
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
    String prepareSQL = "select "
            + "ISNULL(bill_first_name,\'\') as 'First Name',"
            + "ISNULL(bill_last_name,'') as 'Last Name',"
            + "ISNULL(bill_company_name,'') as 'Company Name',"
            + "ISNULL(bill_address_one,'') as 'Address 1',"
            + "ISNULL(bill_address_two,'') as 'Address 2',"
            + "ISNULL(bill_city,'') as 'City',"
            + "ISNULL(bill_state,'') as 'State',"
            + "ISNULL(bill_zip,'') as 'Zip',"
            + "ISNULL(bill_phone_num,'') as 'Phone',"
            + "ISNULL(bill_email_address,'') as 'Email',"
            + "ISNULL(ship_first_name,\'\') as 'Ship First Name',"
            + "ISNULL(ship_last_name,'') as 'Ship Last Name',"
            + "ISNULL(ship_company_name,'') as 'Ship Company Name',"
            + "ISNULL(ship_address_one,'') as 'Ship Address 1',"
            + "ISNULL(ship_address_two,'') as 'Ship Address 2',"
            + "ISNULL(ship_city,'') as 'Ship City',"
            + "ISNULL(ship_state,'') as 'Ship State',"
            + "ISNULL(ship_zip,'') as 'Ship Zip',"
            + "ISNULL(ship_phone_num,'') as 'Ship Phone',"
            + "ISNULL(ship_email_address,'') as 'Ship Email',"
            + "post_date as 'Released Date',"
            + "order_num as 'OWD Order Reference',"
            + "order_refnum as 'Order Reference',"
            + "cc_type as 'Payment Method',"
            + "order_sub_total as 'Order Subtotal',"
            + "ship_handling_fee as 'Shipping',"
            + "tax_amount as 'Tax',"
            + "discount as 'Discount' from owd_order (NOLOCK) join owd_order_ship_info (NOLOCK) on order_id=order_fkey "
            + " where is_void=0 and post_date is not null"
            + " and post_date >= ? and post_date < dateadd(day,1,?)  and client_fkey=? ";


    public JewcyCustomers() {

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
