package com.owd.dc.packing.printserver.beans;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/13/14
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class printRequest {
   private  String status;
   private  String message;
   private printSummary summary;
   private List<printRequestOrders> ordersToPrint;
    private facilities facilityList;


    public facilities getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(facilities facilityList) {
        this.facilityList = facilityList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public printSummary getSummary() {
        return summary;
    }

    public void setSummary(printSummary summary) {
        this.summary = summary;
    }

    public List<printRequestOrders> getOrdersToPrint() {
        return ordersToPrint;
    }

    public void setOrdersToPrint(List<printRequestOrders> orderToPrint) {
        this.ordersToPrint = orderToPrint;
    }

    public static class  printSummary{
        private String printQueueTotal;
        private List<detailedPrintSummary> printDetails;

        public String getPrintQueueTotal() {
            return printQueueTotal;
        }

        public void setPrintQueueTotal(String printQueueTotal) {
            this.printQueueTotal = printQueueTotal;
        }

        public List<detailedPrintSummary> getPrintDetails() {
            return printDetails;
        }

        public void setPrintDetails(List<detailedPrintSummary> printDetails) {
            this.printDetails = printDetails;
        }
    }

    public static class detailedPrintSummary{
        private String printerName;
        private String qty;

        public String getPrinterName() {
            return printerName;
        }

        public void setPrinterName(String printerName) {
            this.printerName = printerName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }

    public static class printRequestOrders{
        private String queueId;
        private String orderId;
        private String printerName;
        private String fileName;


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getQueueId() {
            return queueId;
        }

        public void setQueueId(String queueId) {
            this.queueId = queueId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPrinterName() {
            return printerName;
        }

        public void setPrinterName(String printerName) {
            this.printerName = printerName;
        }

    }
    public static XStream getXStream(){
        XStream x = new XStream();
        x.alias("detailedSummary",printRequest.detailedPrintSummary.class);
        x.alias("printOrder",printRequest.printRequestOrders.class) ;
        x.alias("printRequest",printRequest.class);
        x.alias("location",String.class);
        return x;

    }
}
