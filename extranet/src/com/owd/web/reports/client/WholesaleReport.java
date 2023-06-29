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
 * Date: May 4, 2004
 * Time: 8:28:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class WholesaleReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select bill_company_name as 'BILL_COMPANY',bill_first_name+' '+bill_last_name as 'BILL_NAME',bill_address_one as 'BILL_ADDRESS_1',bill_address_two as 'BILL_ADDRESS_2',bill_city as 'BILL_CITY',bill_state as 'BILL_STATE',"
            + " bill_zip as 'BILL_ZIP',bill_phone_num as 'BILL_PHONE',ship_company_name as 'SHIP_COMPANY',ship_first_name+' '+ship_last_name as 'SHIP_NAME',ship_address_one as 'SHIP_ADDRESS_1',ship_address_two as 'SHIP_ADDRESS_2',"
            + " ship_city as 'SHIP_CITY',ship_state as 'SHIP_STATE',ship_zip as 'SHIP_ZIP',order_num as 'ORDER_NUMBER',o.created_date as 'ORDER_DATE',shipped_on as 'SHIP_DATE',carr_service as 'SHIP_VIA',"
            + " cc_type as 'TERMS',CASE WHEN ss_cod=1 THEN 1 ELSE 0 END as 'COD' ,salesperson as 'SALES_REP',po_num as 'PO_NUMBER',inventory_num as 'SKU',l.description as 'SKU_DESC',quantity_actual as 'SKU_QTY',price as 'SKU_UNIT_PRICE',total_price as 'LINE_TOTAL',"
            + " ship_handling_fee as 'SHIP_FEES',order_total as 'ORDER_TOTAL'"
            + " from owd_order o (NOLOCK) join owd_line_item l (NOLOCK) on l.order_fkey=order_id"
            + " join owd_order_ship_info s (NOLOCK) on s.order_fkey=order_id"
            + " where o.is_void=0"
            + " and shipped_on >= ? and shipped_on < dateadd(day,1,?) "
            + " and salesperson <> 'MONSTERCOMMERCE'  and o.client_fkey=?";


    public WholesaleReport() {

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
