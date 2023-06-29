package com.owd.dc.warehouse.packSlipRelease;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 16, 2009
 * Time: 4:12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class packSlipClientBean {

    private boolean print;
    private String clientName;
    private String printer;
    private int orderCount;
    private int todayCount;
    private int otherCount;
    private String clientId;
    private boolean setCounts = false;

    public int getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(int otherCount) {
        this.otherCount = otherCount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    Map<String, packSlipOrdersBean> orders;


    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public int getTodayCount() {
        if(!setCounts) setCounts();

        return todayCount;
    }

    public void setTodayCount(int todayCount) {
        this.todayCount = todayCount;
    }

    public int getOrderCount() {
      

        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Map<String, packSlipOrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, packSlipOrdersBean> orders) {
        this.orders = orders;
    }

    public void setCounts(){
       orderCount = 0;

        for (String key :orders.keySet()){
            packSlipOrdersBean ob = orders.get(key);
            orderCount += ob.getCount();

        }

       todayCount = 0 ;
         for (String key :orders.keySet()){
            packSlipOrdersBean ob = orders.get(key);
           todayCount += ob.getTodayCount();

        }
        
      otherCount = orderCount - todayCount;
    }
}
