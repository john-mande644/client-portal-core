package com.owd.dc.warehouse.packSlipRelease;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by danny on 3/17/2015.
 */
public class printInfoCheckAction extends ActionSupport{


    printErrorBean info = new printErrorBean();

    public printErrorBean getInfo() {
        return info;
    }

    public void setInfo(printErrorBean info) {
        this.info = info;
    }

    public String execute(){
        try {
            info = packSlipUtilities.loadPrintErrorBean();

        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }



        return "success";
    }
}
