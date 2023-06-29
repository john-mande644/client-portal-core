package com.owd.dc.warehouse.orderInfo;

import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdOrderTrack;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 29, 2010
 * Time: 9:35:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class packageBean {
    private String id;
    private String boxId;
    private OwdBoxtypes box;
    private String packBarcode;
    private String weight;
    private String estimatedWeight = "";
    private String shipTime;
    private OwdOrderTrack tracking;
    private String ipAddress;
    private boolean isGSS;
    private String packageNumber;
    private String boxWidth;
    private String boxHeight;
    private String boxDepth;



    private List<packageLineBean> packedLines;
    public float getBoxWeight(){
        return box.getWeight().floatValue();

    }
    public float getEstimatedTotalWeight(){
        if (estimatedWeight.equals("")){
            getEstimatedWeight();
        }
        return getBoxWeight() + Float.parseFloat(estimatedWeight);
    }
     public String getEstimatedWeight() {
         float w = 0.0f;
         for (packageLineBean p:packedLines){
             w = w + p.getTotalWeight();
         }

         estimatedWeight = w + "";

        return estimatedWeight;
    }

    public void setEstimatedWeight(String estimatedWeight) {
        this.estimatedWeight = estimatedWeight;
    }


    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(String boxWidth) {
        this.boxWidth = boxWidth;
    }

    public String getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(String boxHeight) {
        this.boxHeight = boxHeight;
    }

    public String getBoxDepth() {
        return boxDepth;
    }

    public void setBoxDepth(String boxDepth) {
        this.boxDepth = boxDepth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<packageLineBean> getPackedLines() {
        return packedLines;
    }

    public void setPackedLines(List<packageLineBean> packedLines) {
        this.packedLines = packedLines;
    }

    public OwdBoxtypes getBox() {
        return box;
    }

    public void setBox(OwdBoxtypes box) {
        this.box = box;
    }

    public String getPackBarcode() {
        return packBarcode;
    }

    public void setPackBarcode(String packBarcode) {
        this.packBarcode = packBarcode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public OwdOrderTrack getTracking() {
        return tracking;
    }

    public void setTracking(OwdOrderTrack tracking) {
        this.tracking = tracking;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isGSS() {
        return isGSS;
    }

    public void setGSS(boolean GSS) {
        isGSS = GSS;
    }
}
