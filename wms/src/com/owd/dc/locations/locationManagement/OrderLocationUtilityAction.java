package com.owd.dc.locations.locationManagement;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.apiUtils.OwdInternalApi;

/**
 * Created by danny on 10/9/2017.
 */
public class OrderLocationUtilityAction extends ActionSupport {

    private String locationValue ="";



    public String clearOrderFromLocation(){


        if(locationValue.length()>0){
            String s = locationValue;
            if(locationValue.startsWith("//")){
                locationValue = locationValue.substring(locationValue.indexOf("-")+1,locationValue.length());
            }
            boolean cleared = OwdInternalApi.clearToteById(locationValue);
            if(!cleared){
                addActionError("Unable to clear tote");

            }else{
              addActionMessage("Success. Cleared : "+ s);
            }




        }else{
            System.out.println("no location ");
        }





        return "success";
    }




    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }
}
