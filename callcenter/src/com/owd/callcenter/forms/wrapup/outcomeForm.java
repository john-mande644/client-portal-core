package com.owd.callcenter.forms.wrapup;

import com.owd.callcenter.ccClientList;
import org.apache.struts.action.ActionForm;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 18, 2006
 * Time: 3:08:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class outcomeForm extends ActionForm {
   private String campaign;
    private String type;
    private String typeAdd;
    private String outcome;
    private String outcomeAdd;
    private List clientList;
    private List callTypeList;
    private List outcomeList;
    private String numOfCallType;
    private String numOfOutcomes;
    private String defaultText;
    private String doOrder;


    public String getDoOrder() {

        //Integer doOrder = Integer.valueOf(this.doOrder);
        return this.doOrder;
    }

    public void setDoOrder(String doOrderValue) {
       /* if(("0"!=doOrderValue) || ("1"!=doOrderValue)){
            this.doOrder="0";
        }else{*/
        this.doOrder = doOrderValue;
        
           
    }


    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

       public String getNumOfOutcomes() {
        if(null==numOfOutcomes||"0"==numOfOutcomes)
                this.numOfOutcomes="1";

        return numOfOutcomes;
    }

    public void setNumOfOutcomes(String numOfOutcomes) {
        this.numOfOutcomes = numOfOutcomes;
    }


    public String getNumOfCallType() {
        if(null==numOfCallType||"0"==numOfCallType){
            this.numOfCallType="1";
        }
        
        return numOfCallType;
    }

    public void setNumOfCallType(String numOfCallType) {
        this.numOfCallType = numOfCallType;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeAdd() {
        return typeAdd;
    }

    public void setTypeAdd(String typeAdd) {
        this.typeAdd = typeAdd;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcomeAdd() {
        return outcomeAdd;
    }

    public void setOutcomeAdd(String outcomeAdd) {
        this.outcomeAdd = outcomeAdd;
    }

    public List getClientList() {
      if(null==clientList){
          setClientList();
      }
        return clientList;
    }

    public void setClientList() {
        this.clientList = ccClientList.getInstance().getclients();
    }

    public List getCallTypeList() {
        return callTypeList;
    }

    public void setCallTypeList(List callTypeList) {
        this.callTypeList = callTypeList;
    }

    public List getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List outcomeList) {
        this.outcomeList = outcomeList;
    }

    
}
