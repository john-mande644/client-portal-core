package com.owd.web.warehouse.admin;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jfree.chart.JFreeChart;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 18, 2003
 * Time: 1:17:16 PM
 * To change this template use Options | File Templates.
 */
public class WarehouseStatus {
private final static Logger log =  LogManager.getLogger();


    public static final int kClientOrderList = 1;
    public static final int kMethodOrderList = 2;

    protected static long creationTime = 0;

    List clientStatusList = new ArrayList();
    //com.owd.api.client - posted - shipped

    List methodStatusList = new ArrayList();

    Map methodDetailsMap = new TreeMap();
    Map clientDetailsMap = new TreeMap();

    public static void setOldStatus(WarehouseStatus oldStatus) {
        WarehouseStatus.oldStatus = oldStatus;
    }

    public JFreeChart getOldChart() {
        return oldChart;
    }

    public void setOldChart(JFreeChart oldChart) {
        this.oldChart = oldChart;
    }

    public JFreeChart getOldChart2() {
        return oldChart2;
    }

    public void setOldChart2(JFreeChart oldChart2) {
        this.oldChart2 = oldChart2;
    }

    public JFreeChart oldChart = null;
    public JFreeChart oldChart2 = null;

    static private WarehouseStatus oldStatus = null;

    static {
        //	oldStatus = new WarehouseStatus();
    }

    int postedOrders = 0;
    int shippedOrders = 0;
    int pickedOrders = 0;

    public int getPickedUnshippedOrders() {
        return pickedUnshippedOrders;
    }

    int pickedUnshippedOrders = 0;
    int ordersWaitingToPrint = 0;

    public static long getCreationTime() {
        //	synchronized(oldStatus)
        //	{
        return creationTime;
        //	}
    }

    public static WarehouseStatus getOldStatus() {

        //	synchronized(oldStatus)
        //	{
        return oldStatus;
        //	}
    }

    public List getClientStatusList() {
        return clientStatusList;
    }

    public List getMethodStatusList() {
        return methodStatusList;
    }

    public int getPostedOrders() {
        return postedOrders;
    }

    public int getShippedOrders() {
        return shippedOrders;
    }

    public int getOrdersWaitingToPrint() {
        return ordersWaitingToPrint;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public List getPickHistory() {
        return picklist;
    }

    List picklist = new ArrayList();
    Date referenceDate = new Date();
    static String getCurrentDateTimeSQL = "select getdate()";

    /*
    order_num, ship_country,post_date,ship_first_name+\' \'+ship_last_name, "
        +"carr_service

        7 1 select order_num,
        8 2 ship_country,
        9 3 post_date,
        10 4 ship_first_name+\' \'+ship_last_name,

         carr_service,
        CASE WHEN ship_date is NULL then 0 else 1 END,
        7 CASE WHEN ship_date >= \'" + OWDUtilities.getSQLDateNoTimeForToday() + "\'  THEN 1 else 0 END,
         company_name

        */


    public WarehouseStatus() {}

    public List getOrderInfoList(int listType, String listQualifier) {
        List aList = null;

        if (listType == kClientOrderList) {
            aList = (List) clientDetailsMap.get(listQualifier);
        } else {
            aList = (List) methodDetailsMap.get(listQualifier);
        }

        if (aList == null) return new ArrayList();

        return aList;
    }

    public static void main (String[] args)
    {
        WarehouseStatus status = new WarehouseStatus();
        status = new WarehouseStatus();
    }

}
