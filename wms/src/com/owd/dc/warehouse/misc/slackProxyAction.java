package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.managers.SlackManager;

/**
 * Created by danny on 11/4/2016.
 */
public class slackProxyAction extends ActionSupport {
    private String endpoint;
    private String caseNumber;
    private String theAction;
    private String otherData;
    private String title;





    public String execute(){
        if(endpoint.equals("SymphonyCase")){
            try{
                SlackManager.notifySymphonyCase(caseNumber,theAction,title);


            }catch (Exception e){
                e.printStackTrace();
            }
        }



        return SUCCESS;
    }


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getTheAction() {
        return theAction;
    }

    public void setTheAction(String theAction) {
        this.theAction = theAction;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
