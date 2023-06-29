package com.owd.jobs

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 10/23/12
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
class ShippingHoldJob extends OWDStatefulJob {

    public static void main(String... args)
    {
        run();
    }

    @Override
    void internalExecute() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ResultSet rs = HibernateSession.getResultSet("\n" +
                "select client_id,company_name,round(shipping_balance-shipping_unbilled,2),is_on_shipping_hold from owd_client\n" +
                "where round(shipping_balance-shipping_unbilled,2)<0 and is_on_shipping_hold=0 and is_active=1");
        PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_client set is_on_shipping_hold=1 where client_id=?");

        int holders=0;
        StringBuffer clients = new StringBuffer();

        while(rs.next())
        {
            holders++;
            clients.append(rs.getString(2)+" ("+rs.getInt(1)+") : "+rs.getFloat(3)+"\r\n");
        //    ps.setInt(1,rs.getInt(1));
        //    ps.executeUpdate();
        //    HibUtils.commit(HibernateSession.currentSession()) ;
        }

        String subject = "Clients Qualified for Shipping Hold " +df.format(Calendar.getInstance().getTime());
        String message = """
The following clients qualify to be placed on shipping hold due to insufficient funds - holds must be applied manually if desired:\r\n\r\nClient : Current Balance\r\n"""


        String[] tomails = [  "owditadmin@owd.com"]//,"clientholds@owd.com"]
         if(holders>0)
         {
             Mailer.sendMail(subject,message+clients.toString(),tomails,"donotreply@owd.com");
         }
    }
}
