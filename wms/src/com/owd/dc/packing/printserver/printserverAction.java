package com.owd.dc.packing.printserver;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.packing.printserver.beans.printRequest;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/14/14
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class printserverAction extends ActionSupport {
    private String facility;
    private String printerName;
    private String qty;
    private String printID;
    private String orderID;


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPrintID() {
        return printID;
    }

    public void setPrintID(String printID) {
        this.printID = printID;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    public String getFacilities(){
        printRequest pr = new printRequest();
        try{
          pr = printserverUtils.getFacilities();
            pr.setStatus("Success");
        }catch(Exception e){
            e.printStackTrace();
            pr.setStatus("Error");
            pr.setMessage(e.getMessage());
        }
        inputStream = new StringBufferInputStream(printRequest.getXStream().toXML(pr));
        return "success";
    }
    public String getFileForOrderId(){
      try{
           byte[] file = printserverUtils.getFileOrderID(orderID);
          String file64 = Base64.encodeBase64String(file);

          inputStream = new StringBufferInputStream(file64);

      } catch(Exception e){
          e.printStackTrace();
          inputStream = new StringBufferInputStream((e.getMessage()));

      }

        return "success";
    }
    public String reportPrinted(){
                         try{
                             if(printID.length()>0){
                                printserverUtils.reportPackSlipPrinted(printID);
                             }
                             inputStream= new StringBufferInputStream("Success");
                         } catch (Exception e){
                             e.printStackTrace();
                             inputStream = new StringBufferInputStream(e.getMessage());

                         }

        return "success";
    }
    public String getOrders(){
        printRequest pr = new printRequest();
        try{
           System.out.println("In Action get orders with printer of "+ printerName);
            System.out.println(qty);

        pr = printserverUtils.loadPrintRequest(facility,printerName,qty);
        pr.setStatus("Success");
        }catch(Exception e){
          pr.setStatus("Error");
            pr.setMessage(e.getMessage());
        }

        inputStream = new StringBufferInputStream(printRequest.getXStream().toXML(pr));
        return "success";
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

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
