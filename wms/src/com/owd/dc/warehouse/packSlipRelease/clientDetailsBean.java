package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 3, 2009
 * Time: 2:45:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class clientDetailsBean {

   private String lines;
    private String orders;
    private String method;
    private String sla;

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
