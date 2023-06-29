
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Label {

    @SerializedName("Base64")
    @Expose
    private String base64;
    @SerializedName("LabelType")
    @Expose
    private Integer labelType;
    @SerializedName("LabelSize")
    @Expose
    private Integer labelSize;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public Integer getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(Integer labelSize) {
        this.labelSize = labelSize;
    }

}
