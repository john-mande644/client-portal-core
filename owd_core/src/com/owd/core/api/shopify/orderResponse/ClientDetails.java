
package com.owd.core.api.shopify.orderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientDetails {

    @SerializedName("browser_ip")
    @Expose
    private String browserIp;
    @SerializedName("accept_language")
    @Expose
    private String acceptLanguage;
    @SerializedName("user_agent")
    @Expose
    private String userAgent;
    @SerializedName("session_hash")
    @Expose
    private String sessionHash;
    @SerializedName("browser_width")
    @Expose
    private Integer browserWidth;
    @SerializedName("browser_height")
    @Expose
    private Integer browserHeight;

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }

    public Integer getBrowserWidth() {
        return browserWidth;
    }

    public void setBrowserWidth(Integer browserWidth) {
        this.browserWidth = browserWidth;
    }

    public Integer getBrowserHeight() {
        return browserHeight;
    }

    public void setBrowserHeight(Integer browserHeight) {
        this.browserHeight = browserHeight;
    }

}
