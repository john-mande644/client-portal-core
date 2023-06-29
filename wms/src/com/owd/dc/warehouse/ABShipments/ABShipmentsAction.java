package com.owd.dc.warehouse.ABShipments;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;

import java.io.InputStream;
import java.io.StringBufferInputStream;

/**
 * Created by danny on 9/28/2015.
 */
public class ABShipmentsAction extends ActionSupport {

    private String orderId;
    private String boxType;
    private String weight;
    private String packageBarcode;
    private InputStream inputStream;
    private String locationCode;


    public String packPreShipPackagePerUnit(){
        String message = "";
        try {
            if (null == orderId | orderId.length() == 0) {
                inputStream = new StringBufferInputStream("Error: Please enter order number");
            }
            message = ABUtils.packAndPreShipPackagePerUnitOrder(Integer.parseInt(orderId));

            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";
    }

    public String materialHandlingShip(){
        String message = "";
        try {

            message = ABUtils.materialHandlingShipPackage(packageBarcode,weight,locationCode);

            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";
    }

    public String packShip(){
        String message = "";
        try {
            if (null == orderId | orderId.length() == 0) {
                inputStream = new StringBufferInputStream("Error: Please enter order number");
            }
            message = ABUtils.packAndPreshipOrder(orderId);

            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


      return "success";
    }
    public String packShipWithBoxAndWeight(){
        String message = "";
        try {
            if (null == orderId || orderId.length() == 0) {
                System.out.println("in orderID null");
                inputStream = new StringBufferInputStream("Error: Please enter order number");
            }
            if (null == boxType || boxType.length() == 0) {
                inputStream = new StringBufferInputStream("Error: Please enter a valid Box Type");
            }
            if (null == weight || weight.length() == 0) {
                inputStream = new StringBufferInputStream("Error: Please enter weight");
            }
            message = ABUtils.packAndShipOrderSinglePackage(orderId,boxType,weight);

            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            System.out.println(e.getMessage());

            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";
    }
    public String processPrintedLabels(){
        String message = "";
        try {

            boolean success =  PackingABUtils.pullPrintedAndMarkShipped();
                              if(success){
                                  message = "success";
                              }else{
                                  message = "error";
                              }
            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";
    }

    public String voidShip(){
        String message = "";
        try {
            if (null == orderId | orderId.length() == 0) {
                inputStream = new StringBufferInputStream("Error: Please enter order number");
            }
            message = ABUtils.voidWVOrder(orderId);

            inputStream = new StringBufferInputStream(message);




        }catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";


    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPackageBarcode() {
        return packageBarcode;
    }

    public void setPackageBarcode(String packageBarcode) {
        this.packageBarcode = packageBarcode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
