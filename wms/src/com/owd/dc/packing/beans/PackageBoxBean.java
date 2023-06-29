package com.owd.dc.packing.beans;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 23, 2009
 * Time: 4:59:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class PackageBoxBean{
       private String id;
       private String barcode;
    private String weight;
    private String packageOrderFkey ;
    private String orderTrackId ;


    public String getOrderTrackId() {
        return orderTrackId;
    }

    public void setOrderTrackId(String orderTrackId) {
        this.orderTrackId = orderTrackId;
    }

    public String getPackageOrderFkey() {
        return packageOrderFkey;
    }

    public void setPackageOrderFkey(String packageOrderFkey) {
        this.packageOrderFkey = packageOrderFkey;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getBarcode() {
           return barcode;
       }

       public void setBarcode(String barcode) {
           this.barcode = barcode;
       }
   }
