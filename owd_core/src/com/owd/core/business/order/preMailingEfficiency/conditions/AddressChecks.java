package com.owd.core.business.order.preMailingEfficiency.conditions;

import com.owd.core.business.order.preMailingEfficiency.ErrorCondition;
import com.owd.core.business.order.preMailingEfficiency.PreMailingValidator;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.hibernate.Session;

public class AddressChecks extends ErrorCondition {

    public AddressChecks() {
        super();
    }
    public AddressChecks(String errorType) {
        super(errorType);
    }

    @Override
    public boolean checkError(OwdOrderShipInfo info) {

        if (info.getShipFirstName().length() < 2) {
            if(info.getShipFirstName().length() == 0 && info.getShipLastName().length() == 0){
                if(info.getShipCompanyName().length() == 0) {
                    this.setErrorType("First name, last name, and Company name are all blank");
                    return true;
                }
            }else {
                if(info.getShipFirstName().length() == 0) {
                    this.setErrorType("First Name minimum 2 characters");
                    return true;
                }
                try {
                    if(PreMailingValidator.getDebug()) {
                        this.setErrorType("First name has length of one. Debug set to true record not modified");
                        return true;
                    }else{
                        info.setShipFirstName(info.getShipFirstName() + ".");
                        Session session = HibernateSession.currentSession();
                        session.saveOrUpdate(info);
                        session.flush();
                        HibUtils.commit(session);
                    }

                }catch (Exception e){
                    this.setErrorType(e.getMessage());
                    return true;
                }
            }
        }

        if (info.getShipCity().equals("") || info.getShipCity()==null){
            this.setErrorType("Recipient city cannot be blank. (1001)");
            return true;
        }

        return false;
    }
}

