package com.owd.web.reports.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.reports.ReportDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 4, 2004
 * Time: 10:20:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class JewcySuppliers extends ReportDefinition {
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
            + "isnull(supp_name,company_name) as 'Supplier',"
            + "inventory_num as 'SKU',"
            + "ISNULL(supp_cost,0) as 'Item Cost',"
            + "qty_on_hand as 'In Stock'"
            + " from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey join owd_client (NOLOCK) on client_fkey=client_id left outer join owd_supplier (NOLOCK) on supp_fkey=supplier_id"
            + " where owd_inventory.client_fkey=? order by isnull(supp_name,company_name),inventory_num";


    public JewcySuppliers() {


    }

    //customized methods
    public List getParameters() {
        return paramList;

    }

    public String getPrepareSQL() {
        return prepareSQL;

    }

}
