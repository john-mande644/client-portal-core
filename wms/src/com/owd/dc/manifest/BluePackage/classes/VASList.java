
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VASList {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Amount")
    @Expose
    private Integer amount;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
