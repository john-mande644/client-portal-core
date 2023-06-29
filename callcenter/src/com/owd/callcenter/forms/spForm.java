package com.owd.callcenter.forms;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 1, 2007
 * Time: 2:25:48 PM
 * To ch this template use File | Settings | File Templates.
 */
public class spForm extends ActionForm {
private String doScript;
private String callId;
private String urlEntry;
private String urlStatus;
private String urlService;
private String campaign;
private String kb;
private String quickRef;
private String agentid;
private String agentfname;
    private String script;
    private String clientFkey;
    private List extras;


    public List getExtras() {
        return extras;
    }

    public void setExtras(List extras) {
        this.extras = extras;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getDoScript() {
        return doScript;
    }

    public void setDoScript(String doScript) {
        this.doScript = doScript;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getUrlEntry() {
        return urlEntry;
    }

    public void setUrlEntry(String urlEntry) {
        this.urlEntry = urlEntry;
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus;
    }

    public String getUrlService() {
        return urlService;
    }

    public void setUrlService(String urlService) {
        this.urlService = urlService;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getKb() {
        return kb;
    }

    public void setKb(String kb) {
        this.kb = kb;
    }

    public String getQuickRef() {
        return quickRef;
    }

    public void setQuickRef(String quickRef) {
        this.quickRef = quickRef;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getAgentfname() {
        return agentfname;
    }

    public void setAgentfname(String agentfname) {
        this.agentfname = agentfname;
    }
}
