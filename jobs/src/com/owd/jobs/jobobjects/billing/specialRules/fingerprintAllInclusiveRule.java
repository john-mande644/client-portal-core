package com.owd.jobs.jobobjects.billing.specialRules;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class fingerprintAllInclusiveRule extends specialRulesBase {
    private final static Logger log =  LogManager.getLogger();



    public List<String> loadIds(pricingBean pricing,String theDate) throws Exception{
        List<String> l = new ArrayList<>();
        String lookup = "select sp_bill_pickpack.id \n" +
                " from w_order_attributes inner join sp_bill_pickpack\n" +
                " on(w_order_attributes.order_fkey = sp_bill_pickpack.oid)\n" +
                " where\n" +
                "     dbo.w_order_attributes.fingerprint like :fingerprint\n" +
                "AND dbo.sp_bill_pickpack.[client ID] = :clientId\n" +
                "AND shipped_on = :shippedOn\n";

        Query q = HibernateSession.currentSession().createSQLQuery(lookup);
        q.setParameter("fingerprint",pricing.getRuleLookup());
        q.setParameter("clientId",pricing.getClientFkey());
        q.setParameter("shippedOn",theDate);

        List ll = q.list();

        for(Object o:ll){
            l.add(o.toString());

        }

        return l;

    }




    public void processShipping(String shippedOn,pricingBean prices) throws Exception{
        log.debug("Doing fingerpintAll Shipping");

        PreparedStatement stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set original_amount = amount, amount = ? where id in (\n" +
                "\n" +
                "SELECT\n" +
                "    dbo.owdbill_shipping_trans.id\n" +

                "FROM\n" +
                "    dbo.w_order_attributes\n" +
                "INNER JOIN\n" +
                "    dbo.owdbill_shipping_trans\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_order_attributes.order_fkey = dbo.owdbill_shipping_trans.order_fkey)\n" +
                "WHERE\n" +
                "    dbo.w_order_attributes.fingerprint = ?\n" +
                "AND dbo.owdbill_shipping_trans.client_Fkey = ?\n" +
                "AND dbo.owdbill_shipping_trans.recorded_date = ? and activity_type_fkey = 2)");

        stmt.setString(1,prices.getAllInclusive());
        stmt.setString(2,prices.getRuleLookup());
        stmt.setInt(3,prices.getClientFkey());
        stmt.setString(4,shippedOn);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }


}
