
package com.owd.jobs.jobobjects.easypost.tracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Fee {

    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("charged")
    @Expose
    private Boolean charged;
    @SerializedName("refunded")
    @Expose
    private Boolean refunded;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getCharged() {
        return charged;
    }

    public void setCharged(Boolean charged) {
        this.charged = charged;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("object", object).append("type", type).append("amount", amount).append("charged", charged).append("refunded", refunded).toString();
    }

}
