package com.owd.core.business.order.preMailingEfficiency.conditions;

import com.owd.core.business.order.preMailingEfficiency.ErrorCondition;
import com.owd.hibernate.generated.OwdOrderShipInfo;

public class UspsConsignee extends ErrorCondition {

    public UspsConsignee(String errorType) {
        super(errorType);
    }

    public UspsConsignee() {
       super("USPS First-Class Mail is not supported with Consignee Address (");
    }

    @Override
    public boolean checkError(OwdOrderShipInfo info) {
        if (info.getCarrServiceRefNum().equals("OWD.1ST.LETTER") || info.getCarrServiceRefNum().equals("OWD.1ST.PRIORITY")) {
            if (!info.getShipCountryRefNum().equals("UNITED_STATES") && !info.getShipCountryRefNum().equals("PUERTO_RICO")) {
                setErrorType(getErrorType() + info.getShipCountryRefNum() + ") (1001)");
                return true;
            }
        } else if (info.getCarrServiceRefNum().equals("OWD_USPS_I_FIRST") || info.getCarrServiceRefNum().equals("OWD_USPS_I_PRIORITY")) {
            if (info.getShipCountryRefNum().equals("UNITED_STATES") || info.getShipCountryRefNum().equals("PUERTO_RICO")) {
                this.setErrorType("USPS First-Class Mail is not supported with Consignee Address (" + info.getShipCountryRefNum() + ") (1001)");
                return true;
            }
        }
        return false;
    }
}

