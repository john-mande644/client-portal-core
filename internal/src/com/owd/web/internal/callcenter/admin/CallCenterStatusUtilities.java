package com.owd.web.internal.callcenter.admin;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/17/11
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterStatusUtilities {
private final static Logger log =  LogManager.getLogger();




    public static void recordOrderReferenceForCallId(String orderReference, String callId) throws Exception
    {
        PreparedStatement stmt = HibernateSession.getPreparedStatement("insert into cc_order_contact (order_ref,contact_id) VALUES(?,?)");

        stmt.setString(1, orderReference);
        stmt.setString(2,callId);
        stmt.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
    }
}
