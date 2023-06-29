package com.owd.dc.receiving.forms;
import org.apache.struts.action.ActionForm;

public class asnIDForm extends ActionForm {
    public String getAsnid() {
        return asnid;
    }

    public void setAsnid(String asnid) {
        this.asnid = asnid;
    }

    private String asnid="";

}
