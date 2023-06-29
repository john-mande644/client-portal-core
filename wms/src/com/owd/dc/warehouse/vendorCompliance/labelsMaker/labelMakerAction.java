package com.owd.dc.warehouse.vendorCompliance.labelsMaker;

import com.opensymphony.xwork2.ActionSupport;

import java.util.Map;

/**
 * Created by danny on 10/19/2016.
 */
public class labelMakerAction  extends ActionSupport {
    private String loadVendor;
    private Map vendors;
    private Map clients;
    private String loadClient;
    private String gridData;
    private String printId;
    private String qty;
    private String printer;

    private Map printers;





    public String labelMaker(){
        System.out.println("label maker, load vendor list and lets begin");
      try {


      }catch (Exception e){
          addActionError(e.getMessage());
          return ERROR;
      }


        return SUCCESS;

    }

    public String loadVendors(){
        System.out.println("Hello, loading the vendor");
        try {
            if (loadVendor.length() > 0) {
                clients = labelMakerUtils.loadClients(loadVendor);
            } else {
                addActionError("No vendor was selected please try again");
                return ERROR;
            }
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public String loadClients(){
        System.out.println("grabbing the sku's from client :"+loadClient);

        try{
            if (loadVendor.length() > 0) {
                clients = labelMakerUtils.loadClients(loadVendor);
            } else {
                addActionError("No vendor was selected please try again");
                return ERROR;
            }
            printers = labelMakerUtils.getPrinterMapForFacilitySize("","");
            gridData = jsonUtilties.getJsonFromObject(labelMakerUtils.getSkusForClientAndVendor(loadClient,loadVendor));

        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public String getLoadVendor() {
        return loadVendor;
    }

    public void setLoadVendor(String loadVendor) {
        this.loadVendor = loadVendor;
    }

    public Map getVendors() {

        return labelMakerUtils.loadVendors();
    }

    public void setVendors(Map vendors) {
        this.vendors = vendors;
    }

    public Map getClients() {
        return clients;
    }

    public void setClients(Map clients) {
        this.clients = clients;
    }

    public String getLoadClient() {
        return loadClient;
    }

    public void setLoadClient(String loadClient) {
        this.loadClient = loadClient;
    }

    public String getGridData() {
        return gridData;
    }

    public void setGridData(String gridData) {
        this.gridData = gridData;
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Map getPrinters() {
        return printers;
    }

    public void setPrinters(Map printers) {
        this.printers = printers;
    }
}
