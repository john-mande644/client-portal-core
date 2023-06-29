package com.owd.callcenter.forms.calls;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 2, 2006
 * Time: 4:37:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class callSearchForm extends ActionForm {
    private String prevDate;
    private String callId;
    private String date;
    private List campaigns;
    private List agents;
   private String campaign;
           private String agent;
    private String notes;


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrevDate() {
        return prevDate;
    }

    public void setPrevDate(String prevDate) {
        this.prevDate = prevDate;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public List getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List campaigns) {
        this.campaigns = campaigns;
    }

    public List getAgents() {
        return agents;
    }

    public void setAgents(List agents) {
        this.agents = agents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
