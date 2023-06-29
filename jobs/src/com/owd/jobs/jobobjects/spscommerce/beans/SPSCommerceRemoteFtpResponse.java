package com.owd.jobs.jobobjects.spscommerce.beans;

public class SPSCommerceRemoteFtpResponse {
    private final String fileName;
    private final String responseMessage;
    private final int responseCode;

    public SPSCommerceRemoteFtpResponse(String fileName, String responseMessage, int responseCode) {
        this.fileName = fileName;
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
    }

    public String getFileName() {
        return fileName;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

}
