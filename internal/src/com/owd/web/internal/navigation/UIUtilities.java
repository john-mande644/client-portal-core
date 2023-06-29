package com.owd.web.internal.navigation;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 23, 2005
 * Time: 1:22:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIUtilities {
private final static Logger log =  LogManager.getLogger();
                                          
    //Main Area Name Constants
    public static String kUserAreaName = "Users";
    public static String kWarehouseAreaName = "Warehouse";
    public static String kUPSAreaName = "Carrier Billing";
    public static String kBillingAreaName = "Client Billing";
    public static String kBillingAdminAreaName = "Billing Admin";
    public static String kContactAreaName = "Contact Center";

    public static String kClientAreaName = "Clients";
    public static String kClientServicesAreaName = "Client Services";

    public static String kReportAreaName = "Reports";
    public static String kHomeAreaName = "Home";
    public static String kITAreaName = "IT";
    protected static Map areaCommandMap = new TreeMap();

    static {

        List contactAreaCommandList = new ArrayList();

        contactAreaCommandList.add("Home;/internal/callcenter/");
       // contactAreaCommandList.add("Screen Pops;/internal/callcenter/admin/callcenter_config.jsp");
        contactAreaCommandList.add("Screen Pops;/internal/five9screenpops/list.action");
        areaCommandMap.put(kContactAreaName, contactAreaCommandList);

        List billingAreaCommandList = new ArrayList();

        billingAreaCommandList.add("Contracts;/owd/contract/list.htm");
        billingAreaCommandList.add("Invoices;/owd/invoice/list.htm");
        areaCommandMap.put(kBillingAreaName, billingAreaCommandList);

        List billingAdminAreaCommandList = new ArrayList();

        billingAdminAreaCommandList.add("Customers;/owd/customer/list.htm");
        billingAdminAreaCommandList.add("Contracts;/owd/contract/list.htm");
        billingAdminAreaCommandList.add("Billable Type Group;/owd/billable_group/list.htm");
        billingAdminAreaCommandList.add("Billable Types;/owd/billable_type/list.htm");
        billingAdminAreaCommandList.add("Context Attribute Type;/owd/attribute_type/list.htm");
        billingAdminAreaCommandList.add("Contexts;/owd/context/list.htm");
        billingAdminAreaCommandList.add("Context Attribute;/owd/context_attribute/list.htm");
        billingAdminAreaCommandList.add("Billable Activity;/owd/billable_activity/list.htm");
        billingAdminAreaCommandList.add("Invoice;/owd/invoice/list.htm");


        areaCommandMap.put(kBillingAdminAreaName, billingAdminAreaCommandList);


        List userAreaCommandList = new ArrayList();

        userAreaCommandList.add("Search Users;/internal/user/edit");
        userAreaCommandList.add("Create User;/internal/user/edit?act=create");
        areaCommandMap.put(kUserAreaName, userAreaCommandList);

        List upsAreaCommandList = new ArrayList();


        upsAreaCommandList.add("Carrier Billing Home;/internal/intravexbills");
        upsAreaCommandList.add("Upload Intravex/StrategIQ Invoice;/internal/intravexbills/upload.jsp");
        areaCommandMap.put(kUPSAreaName, upsAreaCommandList);

        List clientAreaCommandList = new ArrayList();
        clientAreaCommandList.add("Clients Home;/internal/client/edit");

        clientAreaCommandList.add("Search Clients;/internal/client/edit?act=client-search");
        clientAreaCommandList.add("Create Client;/internal/client/edit?act=create");
        areaCommandMap.put(kClientAreaName, clientAreaCommandList);

        List clientServicesAreaCommandList = new ArrayList();
        clientServicesAreaCommandList.add("Services Home;/internal/services/edit");

        clientServicesAreaCommandList.add("Adjustments;/internal/service/edit?act=client-search");
        clientServicesAreaCommandList.add("Print Packing Slip;/internal/services/edit?act=create");
        areaCommandMap.put(kClientServicesAreaName, clientServicesAreaCommandList);


        List warehouseAreaCommandList = new ArrayList();

        warehouseAreaCommandList.add("Home;/internal/warehouse/admin");
        warehouseAreaCommandList.add("Manual Ship Entry;/internal/warehouse/admin/shipments");
        warehouseAreaCommandList.add("Pending Receives;/internal/warehouse/admin/receives");
        warehouseAreaCommandList.add("Inventory Counts;/internal/warehouse/admin/counts");
        warehouseAreaCommandList.add("Box Types;/internal/boxcodes/list.action");
        warehouseAreaCommandList.add("PPS History;/internal/orderhistory/lookup.action");
        warehouseAreaCommandList.add("VC Labels;/internal/zplutils/label_printer");
        // warehouseAreaCommandList.add("Sku Adjustments;/internal/warehouse/admin/adjustments");
       //   warehouseAreaCommandList.add("Returns;/internal/warehouse/returns");
       // warehouseAreaCommandList.add("Adjustments;/internal/warehouse/adjusts");
        //  warehouseAreaCommandList.add("Work Orders;/internal/warehouse/workorders");
        areaCommandMap.put(kWarehouseAreaName, warehouseAreaCommandList);

        List homeAreaCommandList = new ArrayList();

        homeAreaCommandList.add("Home;/internal/clienttools/");
        areaCommandMap.put(kHomeAreaName, homeAreaCommandList);

        List itAreaCommandList = new ArrayList();

        itAreaCommandList.add("IT;/internal/it/status.action");
        areaCommandMap.put(kITAreaName, itAreaCommandList);

    }


    public static void setRequestNavigationElements(String areaName, String currentContextName, HttpServletRequest request) {
        request.setAttribute("lf_CURRMANAGER", areaName);
        request.setAttribute("lf_commandList", areaCommandMap.get(areaName));

        request.setAttribute("lf_navLocation", currentContextName);


    }


}
