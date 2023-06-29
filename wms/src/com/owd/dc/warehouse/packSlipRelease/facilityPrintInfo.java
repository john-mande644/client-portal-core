package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 2/24/15
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class facilityPrintInfo {
     private String name;
    private String downloadTime;
    private String printCheckTime;
    private int downloadDifference;
    private int printCheckDifference;
    private int toVerify;
    private int toCreate;
    private int toDownload;
    private int printing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(String downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getPrintCheckTime() {
        return printCheckTime;
    }

    public void setPrintCheckTime(String printCheckTime) {
        this.printCheckTime = printCheckTime;
    }

    public int getDownloadDifference() {
        return downloadDifference;
    }

    public void setDownloadDifference(int downloadDifference) {
        this.downloadDifference = downloadDifference;
    }

    public int getPrintCheckDifference() {
        return printCheckDifference;
    }

    public void setPrintCheckDifference(int printCheckDifference) {
        this.printCheckDifference = printCheckDifference;
    }

    public int getToVerify() {
        return toVerify;
    }

    public void setToVerify(int toVerify) {
        this.toVerify = toVerify;
    }

    public int getToCreate() {
        return toCreate;
    }

    public void setToCreate(int toCreate) {
        this.toCreate = toCreate;
    }

    public int getToDownload() {
        return toDownload;
    }

    public void setToDownload(int toDownload) {
        this.toDownload = toDownload;
    }

    public int getPrinting() {
        return printing;
    }

    public void setPrinting(int printing) {
        this.printing = printing;
    }
}
