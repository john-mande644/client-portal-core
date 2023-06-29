package com.owd.core.business.order.preMailingEfficiency;

import com.owd.hibernate.generated.OwdOrderShipInfo;

public class ErrorCondition {

    private String errorType = "";

    public ErrorCondition (){

    }

    public ErrorCondition(String errorType){
        this.errorType = errorType;
    }

    public boolean checkError(OwdOrderShipInfo info){
        return false;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
