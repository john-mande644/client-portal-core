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
public class BarcodeStatusSummaryReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();


    String prepareSQL = "select company_name as 'Client',count(*) as 'Total SKUs', sum(isnumeric(upc_code)) as 'Total UPC',sum(isnumeric(isbn_code)) as 'Total ISBN'\n" +
            ", sum(qty_on_hand) as 'Total Units' from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey\n" +
            "join owd_client (NOLOCK) on client_id=client_fkey and owd_client.is_active=1\n" +
            "where owd_inventory.is_active=1 \n" +
            "and inventory_num not like 'KIT-%' and inventory_num not like 'KITITEM%'\n" +
            "and qty_on_hand>= ? and ?<>-1\n" +
            "group by company_name";

    public BarcodeStatusSummaryReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Exclude zero quantity SKUs?");
        param.setDescription("Check to indicate that SKUs with zero quantities on hand should be excluded from the report");
        param.setDefaultValue("0");
        param.setCurrentValue("1");
        param.setFormValueName("exclude_zero_quantity_skus");
        param.setParamDataType(ReportParameter.kParamTypeCheckbox);
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
