package com.owd.dc.warehouse.statusScroller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/16/14
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class statusScrollerAction extends ActionSupport {
    private String facility ="";
    private statusScrollerData data;


    public String exec(){
        try{
            System.out.println("hello");
         if (facility.length()>0){
           data = statusScrollerUtils.loadData(facility) ;

         } else{
             addActionError("Please set facility in url ?facility=XXX");
         }
        }catch (Exception e){
            addActionError(e.getMessage());
            e.printStackTrace();
        }

        return "success";
    }


    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public statusScrollerData getData() {
        return data;
    }

    public void setData(statusScrollerData data) {
        this.data = data;
    }
}
