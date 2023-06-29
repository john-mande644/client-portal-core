package com.owd.core.business.order.preMailingEfficiency;

import com.owd.core.business.Address;
import com.owd.core.business.order.WarehouseHold.holdUtilities;
import com.owd.core.business.order.preMailingEfficiency.conditions.AddressChecks;
import com.owd.core.business.order.preMailingEfficiency.conditions.OrderChecks;
import com.owd.core.business.order.preMailingEfficiency.conditions.UspsConsignee;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.hibernate.generated.PreMailingEfficiencyTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.jfree.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * modular validator routine to check for errors
 *
 * statuc boolean member debug can be accessed by ErrorConditions that have order correction routines so that while
 * debug is true those ErrorConditions will no change the order and just flag that the condition was mett by setting an
 * errorType and returning false
 *
 */
public class PreMailingValidator {
    private final static Logger log =  LogManager.getLogger();

    static private boolean debug = false;

    /**
     * Currently this function creates a default list of ErrorConditions that it will iterate the order through providing it
     * has completed the previous condition. If a condition fails the class level function setHold is called.
     *
     * In the future as we create more checks and are able to group more checks together we can eliminate some checks if
     * they are not needed for a certain order.
     *
     * @param info  An OwdOrderShipInfo object used as an entry point to get conditional values
     * @return      Boolean value if the conditions passed
     */

    static public void validate(OwdOrderShipInfo info){
        List<ErrorCondition> conditions = new ArrayList<ErrorCondition>();
        conditions.add(new UspsConsignee());
        conditions.add(new AddressChecks());
//        conditions.add(new OrderChecks());

        for (ErrorCondition errCon: conditions ){
            log.debug("Testing: "+ errCon.getErrorType());
            if (errCon.checkError(info)){
                setHold(info.getOrder().getOrderNum(),errCon.getErrorType());
                return;
            }
        }
        try {
            OrderRater.checkAddress(new Address(
                    info.getShipCompanyName(),
                    info.getShipAddressOne(),
                    info.getShipAddressTwo(),
                    info.getShipCity(),
                    info.getShipState(),
                    info.getShipZip(),
                    info.getShipCountry()
            ),info.getCarrServiceRefNum());
        }catch(Exception ex){
            setHold(info.getOrder().getOrderNum(),ex.getMessage());
        }
        return;
    }

    /**
     * function wrapper for setting a DC Hold this is used to allow debugging by running the
     * class level test hold function instead of the holdUtilities setOrderShippingDCHold function
     * @param orderNum  order number of the order to put on hold
     * @param reason    text explanation of what condition failed
     */
    static private void setHold(String orderNum, String reason) {
        log.debug("Doing set Hold: "+ orderNum + ": " + reason);
        if (debug) {
            testHold(orderNum, "PreMailingValidator", reason);
        }else{
            holdUtilities.setOrderShippingDCHold(orderNum,"PreMailingValidator",reason);
        }
    }

    /**
     * saves info to testing table instead of actually placing the order on dc hold
     * @param orderNum  Order number of order flagged
     * @param name      Name of person/application creating the hold
     * @param reason    test explanation of what condition failed
     */
    static private void testHold(String orderNum, String name, String reason){
        try {
            Session session = HibernateSession.currentSession();
            PreMailingEfficiencyTest record = new PreMailingEfficiencyTest();
            record.setOrderNum(orderNum);
            record.setName(name);
            record.setDetails(reason);
            record.setDateFlagged(new Date());
            session.saveOrUpdate(record);
            session.flush();
            HibUtils.commit(session);
        }
        catch( Exception ex ){
            Log.info("preMailingValidator Exception:");
            Log.info(ex.getStackTrace().toString());
        }
    }
    static public void setDebug (boolean debug_flag){
        debug = debug_flag;
    }
    static public boolean getDebug (){
        return debug;
    }
}
