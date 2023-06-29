package com.owd.core.business.order.preMailingEfficiency.conditions;

import com.owd.core.business.order.preMailingEfficiency.ErrorCondition;
import com.owd.hibernate.generated.OwdOrderShipInfo;

import java.math.BigDecimal;

public class OrderChecks extends ErrorCondition {

    public OrderChecks(String errorType) {
        super(errorType);
    }
    public OrderChecks() {
        super();
    }

    @Override
    public boolean checkError(OwdOrderShipInfo info) {

        if (info.getCustomsDesc().length() < 1 || info.getCustomsDesc().length() > 50){
            this.setErrorType("Problem Shipping :  Error shipping Package: Package Element Name =CustomsDescription, " +
                    info.getCustomsDesc() +
                    ": Element value is not in the specified MinMax range 1 : 50.");
            return true;
        }
        return false;
    }
}

