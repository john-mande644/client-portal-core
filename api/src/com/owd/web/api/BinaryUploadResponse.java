package com.owd.web.api;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 2/7/14
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */

public class BinaryUploadResponse extends APIResponse {

    boolean success = false;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BinaryUploadResponse(double api_v) {
        super(api_v);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setError(String errorType, String errorResponse) {
        super.setError(errorType, errorResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getXML() {

        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<OWD_BINARY_UPLOAD_RESPONSE>");
        responseBuffer.append("<SUCCESS>"+(isSuccess()?1:0)+"</SUCCESS>");
        responseBuffer.append("</OWD_BINARY_UPLOAD_RESPONSE>");
        // log.debug(""+responseBuffer);
        return responseBuffer.toString();
    }
}
