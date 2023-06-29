
package com.owd.dc.manifest.BluePackage.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;

public class BluePackagePackage {

    @SerializedName("HazmatContentList")
    @Expose
    private List<HazmatContentList> hazmatContentList = null;
    @SerializedName("ExportLineItemList")
    @Expose
    private List<ExportLineItemList> exportLineItemList = null;
    @SerializedName("Base64LabelString1")
    @Expose
    private String base64LabelString1;
    @SerializedName("Base64LabelString2")
    @Expose
    private String base64LabelString2;
    @SerializedName("Base64LabelString3")
    @Expose
    private String base64LabelString3;
    @SerializedName("Base64LabelString4")
    @Expose
    private String base64LabelString4;
    @SerializedName("Base64LabelString5")
    @Expose
    private String base64LabelString5;
    @SerializedName("ShipToAttn")
    @Expose
    private String shipToAttn;
    @SerializedName("Labels")
    @Expose
    private List<Label> labels = null;
    @SerializedName("PackagingType")
    @Expose
    private String packagingType;
    @SerializedName("AddlPackageReference1")
    @Expose
    private String addlPackageReference1;
    @SerializedName("AddlPackageReference2")
    @Expose
    private String addlPackageReference2;
    @SerializedName("AddlPackageReference3")
    @Expose
    private String addlPackageReference3;
    @SerializedName("AddlPackageReference4")
    @Expose
    private String addlPackageReference4;
    @SerializedName("AddlPackageReference5")
    @Expose
    private String addlPackageReference5;
    @SerializedName("AddlPackageReference6")
    @Expose
    private String addlPackageReference6;
    @SerializedName("AddlPackageReference7")
    @Expose
    private String addlPackageReference7;
    @SerializedName("AddlPackageReference8")
    @Expose
    private String addlPackageReference8;
    @SerializedName("ReferenceNumber")
    @Expose
    private String referenceNumber;
    @SerializedName("TrackingNumber")
    @Expose
    private String trackingNumber;
    @SerializedName("PackageId")
    @Expose
    private String packageId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Length")
    @Expose
    private Integer length;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Width")
    @Expose
    private Integer width;
    @SerializedName("Weight")
    @Expose
    private Double weight;
    @SerializedName("BaseRate")
    @Expose
    private Integer baseRate;
    @SerializedName("TotalRate")
    @Expose
    private Integer totalRate;
    @SerializedName("BillWeight")
    @Expose
    private Double billWeight;
    @SerializedName("VasFees")
    @Expose
    private List<VasFee> vasFees = null;

    public List<HazmatContentList> getHazmatContentList() {
        return hazmatContentList;
    }

    public void setHazmatContentList(List<HazmatContentList> hazmatContentList) {
        this.hazmatContentList = hazmatContentList;
    }

    public List<ExportLineItemList> getExportLineItemList() {
        return exportLineItemList;
    }

    public void setExportLineItemList(List<ExportLineItemList> exportLineItemList) {
        this.exportLineItemList = exportLineItemList;
    }

    public String getBase64LabelString1() {
        return base64LabelString1;
    }

    public void setBase64LabelString1(String base64LabelString1) {
        this.base64LabelString1 = base64LabelString1;
    }

    public String getBase64LabelString2() {
        return base64LabelString2;
    }

    public void setBase64LabelString2(String base64LabelString2) {
        this.base64LabelString2 = base64LabelString2;
    }

    public String getBase64LabelString3() {
        return base64LabelString3;
    }

    public void setBase64LabelString3(String base64LabelString3) {
        this.base64LabelString3 = base64LabelString3;
    }

    public String getBase64LabelString4() {
        return base64LabelString4;
    }

    public void setBase64LabelString4(String base64LabelString4) {
        this.base64LabelString4 = base64LabelString4;
    }

    public String getBase64LabelString5() {
        return base64LabelString5;
    }

    public void setBase64LabelString5(String base64LabelString5) {
        this.base64LabelString5 = base64LabelString5;
    }

    public String getShipToAttn() {
        return shipToAttn;
    }

    public void setShipToAttn(String shipToAttn) {
        this.shipToAttn = shipToAttn;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public String getAddlPackageReference1() {
        return addlPackageReference1;
    }

    public void setAddlPackageReference1(String addlPackageReference1) {
        this.addlPackageReference1 = addlPackageReference1;
    }

    public String getAddlPackageReference2() {
        return addlPackageReference2;
    }

    public void setAddlPackageReference2(String addlPackageReference2) {
        this.addlPackageReference2 = addlPackageReference2;
    }

    public String getAddlPackageReference3() {
        return addlPackageReference3;
    }

    public void setAddlPackageReference3(String addlPackageReference3) {
        this.addlPackageReference3 = addlPackageReference3;
    }

    public String getAddlPackageReference4() {
        return addlPackageReference4;
    }

    public void setAddlPackageReference4(String addlPackageReference4) {
        this.addlPackageReference4 = addlPackageReference4;
    }

    public String getAddlPackageReference5() {
        return addlPackageReference5;
    }

    public void setAddlPackageReference5(String addlPackageReference5) {
        this.addlPackageReference5 = addlPackageReference5;
    }

    public String getAddlPackageReference6() {
        return addlPackageReference6;
    }

    public void setAddlPackageReference6(String addlPackageReference6) {
        this.addlPackageReference6 = addlPackageReference6;
    }

    public String getAddlPackageReference7() {
        return addlPackageReference7;
    }

    public void setAddlPackageReference7(String addlPackageReference7) {
        this.addlPackageReference7 = addlPackageReference7;
    }

    public String getAddlPackageReference8() {
        return addlPackageReference8;
    }

    public void setAddlPackageReference8(String addlPackageReference8) {
        this.addlPackageReference8 = addlPackageReference8;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Integer baseRate) {
        this.baseRate = baseRate;
    }

    public Integer getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Integer totalRate) {
        this.totalRate = totalRate;
    }

    public Double getBillWeight() {
        return billWeight;
    }

    public void setBillWeight(Double billWeight) {
        this.billWeight = billWeight;
    }

    public List<VasFee> getVasFees() {
        return vasFees;
    }

    public void setVasFees(List<VasFee> vasFees) {
        this.vasFees = vasFees;
    }

    public BluePackagePackage(AMPConnectShipShipment ship){
        try {
            String dimensions[] = ship.getValueAsString(ShipConfig.DIMENSION).split("x");

            this.setWidth(Double.valueOf(dimensions[0]).intValue());
            this.setHeight(Double.valueOf(dimensions[1]).intValue());
            this.setLength(Double.valueOf(dimensions[2]).intValue());
            this.setWeight(Double.valueOf(ship.getValueAsString(ShipConfig.WEIGHT)));
            this.setPackagingType(ship.getValueAsString(ShipConfig.PKG_TYPE));
            this.setAddlPackageReference1(ship.getValueAsString(ShipConfig.CONSIGNEE_REFERENCE));
            this.setAddlPackageReference2(ship.getValueAsString(ShipConfig.SHIPPER_REFERENCE));
            this.setReferenceNumber(ship.getValueAsString("PACKAGE_BARCODE"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
