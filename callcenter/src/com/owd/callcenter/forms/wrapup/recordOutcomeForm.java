package com.owd.callcenter.forms.wrapup;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 22, 2006
 * Time: 10:35:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class recordOutcomeForm extends ValidatorForm {
    private String agentid;
    private String campaign;
    private String callId;
    private String callTypeId;
    private String outcome;
    private String notes;
    private String callType;
    private String result;
    private List typeList;
    private List outcomeList;
    private String customerId;
    private String orderForm;
    private String orderId;
    private String subtotal;
    private String orderFormv;
    private String crosssell;
    private String source;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCrosssell() {
        return crosssell;
    }

    public void setCrosssell(String crosssell) {
        this.crosssell = crosssell;
    }

    public String getOrderFormv() {
        return orderFormv;
    }

    public void setOrderFormv(String orderFormv) {
        this.orderFormv = orderFormv;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(String orderForm) {
        this.orderForm = orderForm;
    }

    public String getCustomerId() {
        if(null == customerId) setCustomerId("");
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List getTypeList() {
        return typeList;
    }

    public void setTypeList(List typeList) {
        this.typeList = typeList;
    }

    public List getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List outcomeList) {
        this.outcomeList = outcomeList;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(String callTypeId) {
        this.callTypeId = callTypeId;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
 
}
