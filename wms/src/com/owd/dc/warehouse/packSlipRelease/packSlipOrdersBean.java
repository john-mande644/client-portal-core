package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 16, 2009
 * Time: 4:13:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class packSlipOrdersBean {

    private String fingerprint;
    private String printer;
    private boolean print;
    private int count;
    private int todayCount;

    public int getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(int todayCount) {
        this.todayCount = todayCount;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
