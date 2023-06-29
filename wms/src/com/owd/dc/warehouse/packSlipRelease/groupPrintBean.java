package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/25/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class groupPrintBean {

    private String clientId;
    private String clientName;
    private String count;
    private String oldCount;

    public String getOldCount() {
        return oldCount;
    }

    public void setOldCount(String oldCount) {
        this.oldCount = oldCount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
