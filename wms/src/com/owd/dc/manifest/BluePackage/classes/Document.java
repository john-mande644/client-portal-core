
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("Base64")
    @Expose
    private String base64;
    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}
