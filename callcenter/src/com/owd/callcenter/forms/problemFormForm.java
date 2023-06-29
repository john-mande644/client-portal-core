package com.owd.callcenter.forms;


import com.owd.callcenter.beans.selectList;
import org.apache.struts.validator.ValidatorForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 15, 2006
 * Time: 10:47:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class problemFormForm extends ValidatorForm {
   private String subject;
    private String owdOrder;
    private String clientReference;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String issue;
    private String client;
    private List issueList;
    private String csa;
    private String dbody;
    private String dto;
    private String dfrom;
    private String callId;
    private String high;
    private String request;
    private String needs;
    private String campaign;
    private String type;
    private String contactMethod;

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getHigh() {
        if(null == high){
            high = "no";
        }
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getDbody() {
        return dbody;
    }

    public void setDbody(String dbody) {
        this.dbody = dbody;
    }

    public String getDto() {
        return dto;
    }

    public void setDto(String dto) {
        this.dto = dto;
    }

    public String getDfrom() {
        return dfrom;
    }

    public void setDfrom(String dfrom) {
        this.dfrom = dfrom;
    }

    public String getCsa() {
        return csa;
    }

    public void setCsa(String csa) {
        this.csa = csa;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOwdOrder() {
        return owdOrder;
    }

    public void setOwdOrder(String owdOrder) {
        this.owdOrder = owdOrder;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }



    public List getIssueList() {
        if(null==issueList){
            setIssueList();
        }
        return issueList;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public  void setIssueList() {
        List issue= new ArrayList();
        List them=new ArrayList();

          issue.add("Other(make note below)");
        issue.add("Miskey");
        issue.add("Mispick");
        issue.add("Omit");
        issue.add("Reship Defective");
        issue.add("Reship Lost");
        for(int i=0;i<issue.size();i++){
             selectList btn = new selectList();
            btn.setAction(issue.get(i).toString());
            btn.setDisplay(issue.get(i).toString());
            them.add(i,btn);
        }
       this.issueList = them;
    }
 public static void main(String[] args){
             problemFormForm p = new problemFormForm();
     p.setIssueList();

        }
}
