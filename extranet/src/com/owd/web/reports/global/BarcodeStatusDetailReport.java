package com.owd.web.reports.global;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.reports.ReportDefinition;
import com.owd.web.reports.ReportParameter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeStatusDetailReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();


    String prepareSQL = "select company_name as 'Client',inventory_id as 'OWD ID',inventory_num as 'Client SKU',upc_code as 'UPC',isbn_code as 'ISBN',Description,\n" +
            "qty_on_hand as 'On Hand',ISNULL( dbo.udf_getlocationlist(inventory_id) ,'') as 'Locations'\n" +
            "from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey\n" +
            "join owd_client (NOLOCK) on client_id=client_fkey and owd_client.is_active=1\n" +
            "where owd_inventory.is_active=1 \n" +
            "and inventory_num not like 'KIT-%' and inventory_num not like 'KITITEM%'\n" +
            "and qty_on_hand>= ? and client_id=?\n" +
            "group by inventory_id,inventory_num,company_name,upc_code,isbn_code,Description,qty_on_hand";

    public BarcodeStatusDetailReport() {


        Calendar now = Calendar.getInstance();

        //init parameter list
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
