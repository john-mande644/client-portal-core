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
 * Date: Mar 8, 2004
 * Time: 8:31:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryHistoryReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select "
            + "TransactionType as 'Transaction Type',TransactionDate as 'Transaction Date'"
            + ",QuantityChange as 'Quantity Change', OWDReference as 'OWD Reference', ClientReference as 'Client Reference'"
            + "from vw_item_history (NOLOCK) "
            + "where inventory_num = ? and client_fkey=? "
            + "  order by TransactionDate desc";


    public InventoryHistoryReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Inventory SKU");
        param.setDescription("<B>Required</B> Enter the SKU to report on");
        param.setDefaultValue("");
        param.setCurrentValue("");
        param.setFormValueName("sku");
        param.setParamDataType(ReportParameter.kParamTypeText);
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

