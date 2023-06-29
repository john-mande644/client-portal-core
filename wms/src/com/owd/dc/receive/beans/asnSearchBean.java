package com.owd.dc.receive.beans;

/**
 * Created by danny on 3/18/2017.
 */
public class asnSearchBean {

    private String asnId;
    private String clientRef;
    private String clientPo;
    private String companyName;
    private String expectDate;
    private String createdDate;
    private String groupName;
    private String allInventoryNum;
    private String allTitle;
    private String allDescription;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getClientPo() {
        return clientPo;
    }

    public void setClientPo(String clientPo) {
        this.clientPo = clientPo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }

    public String getAllInventoryNum() {
        return allInventoryNum;
    }

    public void setAllInventoryNum(String allInventoryNum) {
        this.allInventoryNum = allInventoryNum;
    }

    public String getAllTitle() {
        return allTitle;
    }

    public void setAllTitle(String allTitle) {
        this.allTitle = allTitle;
    }

    public String getAllDescription() {
        return allDescription;
    }

    public void setAllDescription(String allDescription) {
        this.allDescription = allDescription;
    }
}
