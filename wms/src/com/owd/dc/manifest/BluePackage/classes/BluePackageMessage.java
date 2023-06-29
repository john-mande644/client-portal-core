package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BluePackageMessage {

    @SerializedName("MessageId")
    @Expose
    private String messageId;

    @SerializedName("Consignment")
    @Expose
    private BluePackageRequest consignment;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public BluePackageRequest getConsignment() {
        return consignment;
    }

    public void setConsignment(BluePackageRequest consignment) {
        this.consignment = consignment;
    }
}
