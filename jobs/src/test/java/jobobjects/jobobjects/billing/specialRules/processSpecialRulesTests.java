package jobobjects.jobobjects.billing.specialRules;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.billing.specialRules.processSpecialRules;
import org.hibernate.Query;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class processSpecialRulesTests {
    @Test
    public void  When_Rule_Is_Economy_Than_International_Should_Be_Ignored() throws Exception {
        int orderId = 20727520;
        String processDate = "2020-07-07";
        makeOrderInternationalEconomy(orderId);
        int internationalFee = 14;
        int additionalPickFee = 25;
        int packageCost = 14;
        int allInclusive = 33;
        setBillingPickPackFee(orderId, internationalFee, additionalPickFee, packageCost);
        setSpecialRulesForOrderClient(orderId, internationalFee + 1, additionalPickFee + 1, packageCost + 1, allInclusive);


        processSpecialRules.processRules(processDate);

        int currentInternationalFee = getCurrentInternationalFee(orderId);
        int currentAdditionalPickFee = getCurrentAdditionalFee(orderId);
        int currentPackageCost = getCurrentPackageCost(orderId);
        assertEquals(currentInternationalFee, internationalFee);
        assertEquals(currentAdditionalPickFee, additionalPickFee);
        assertEquals(currentPackageCost, packageCost);
    }


    @Test
    public void  When_Rule_Is_Economy_Than_Shipping_Amount_For_International_Should_Be_Ignored() throws Exception {
        int orderId = 20727520;
        String processDate = "2020-07-07";
        makeOrderInternationalEconomy(orderId);
        int internationalFee = 14;
        int additionalPickFee = 25;
        int packageCost = 14;
        int amount = 33;

        setAmount(orderId, amount);
        setBillingPickPackFee(orderId, internationalFee, additionalPickFee, packageCost);
        setSpecialRulesForOrderClient(orderId, internationalFee + 1, additionalPickFee + 1, packageCost + 1, amount + 1);


        processSpecialRules.processShippingRules(processDate);


        int currentAmount = getCurrentAmount(orderId);
        assertEquals(amount, currentAmount);
    }

    @Test
    public void  When_Rule_Is_Economy_Than_Domestic_Should_Be_Taken() throws Exception {
        int orderId = 20727520;
        String processDate = "2020-07-07";
        makeOrderEconomy(orderId);
        int internationalFee = 14;
        int additionalPickFee = 25;
        int packageCost = 14;
        int allInclusive = 33;
        setBillingPickPackFee(orderId, internationalFee, additionalPickFee, packageCost);
        setSpecialRulesForOrderClient(orderId, internationalFee + 1, additionalPickFee + 1, packageCost + 1,allInclusive);


        processSpecialRules.processRules(processDate);

        int currentInternationalFee = getCurrentInternationalFee(orderId);
        int currentAdditionalPickFee = getCurrentAdditionalFee(orderId);
        int currentPackageCost = getCurrentPackageCost(orderId);
        assertEquals(currentInternationalFee, internationalFee + 1);
        assertEquals(currentAdditionalPickFee, additionalPickFee + 1);
        assertEquals(currentPackageCost, packageCost + 1);
    }

    @Test
    public void  When_Rule_Is_Economy_Than_Domestic_All_Inclusive_Shipping_Should_Be_Taken() throws Exception {
        int orderId = 20727520;
        String processDate = "2020-07-07";
        makeOrderEconomy(orderId);
        int internationalFee = 14;
        int additionalPickFee = 25;
        int packageCost = 14;
        int amount = 33;

        setAmount(orderId, amount);
        setBillingPickPackFee(orderId, internationalFee, additionalPickFee, packageCost);
        setSpecialRulesForOrderClient(orderId, internationalFee + 1, additionalPickFee + 1, packageCost + 1, amount + 1);


        processSpecialRules.processShippingRules(processDate);

        int currentAmount = getCurrentAmount(orderId);
        assertEquals(amount + 1, currentAmount);
    }

    private int getCurrentAmount(int orderId) throws  Exception {
        String lookup = "select amount from [dbo].[owdbill_shipping_trans] where order_fkey = :orderId";

        Query q = HibernateSession.currentSession().createSQLQuery(lookup);
        q.setParameter("orderId",orderId);
        List ll = q.list();
        Integer result = 0;
        for(Object res :ll){
            result =  ((BigDecimal)res).intValue();
        }

        return result;
    }

    private void makeOrderEconomy(int orderId) throws  Exception {
        PreparedStatement stmt = HibernateSession.getPreparedStatement("UPDATE dbo.w_order_attributes SET fingerprint = N'BUNDLE-4:1, DBMED:1, DBSMSWAD:1 | Economy' \n" +
                "WHERE order_fkey =  ?;");

        stmt.setInt(1, orderId);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    private void makeOrderInternationalEconomy(int orderId) throws  Exception {
        PreparedStatement stmt = HibernateSession.getPreparedStatement("\n" +
                "UPDATE dbo.w_order_attributes SET fingerprint = N'VX-10-010-BLK-MD:1, VX-85-020-BLK-OS:1, VX-85-022-BLK-OS:1 | International Economy' \n" +
                "WHERE order_fkey =  ?;");

        stmt.setInt(1, orderId);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    private int getCurrentPackageCost(int orderId) throws  Exception {
        String lookup = "select packageCost\n" +
                "from w_order_attributes inner join sp_bill_pickpack\n" +
                "on(w_order_attributes.order_fkey = sp_bill_pickpack.oid)\n" +
                "where order_fkey = :orderId";

        Query q = HibernateSession.currentSession().createSQLQuery(lookup);
        q.setParameter("orderId",orderId);
        List ll = q.list();
        Integer result = 0;
        for(Object res :ll){
            result =  ((BigDecimal)res).intValue();
        }

        return result;
    }

    private int getCurrentAdditionalFee(int orderId) throws  Exception {
        String lookup = "select [Additional Pick Fees] as additionalFees\n" +
                "from w_order_attributes inner join sp_bill_pickpack\n" +
                "on(w_order_attributes.order_fkey = sp_bill_pickpack.oid)\n" +
                "where order_fkey = :orderId";

        Query q = HibernateSession.currentSession().createSQLQuery(lookup);
        q.setParameter("orderId",orderId);
        List ll = q.list();
        Integer result = 0;
        for(Object res :ll){
            result =  ((BigDecimal)res).intValue();
        }

        return result;
    }

    private int getCurrentInternationalFee(int orderId) throws  Exception {
        String lookup = "select [International Fees] as intlFees\n" +
                "from w_order_attributes inner join sp_bill_pickpack\n" +
                "on(w_order_attributes.order_fkey = sp_bill_pickpack.oid)\n" +
                "where order_fkey = :orderId";

        Query q = HibernateSession.currentSession().createSQLQuery(lookup);
        q.setParameter("orderId",orderId);
        Integer internationalFee = q.getFirstResult();

        List ll = q.list();
        Integer result = 0;
        for(Object res :ll){
            result =  ((BigDecimal)res).intValue();
        }

        return result;
    }

    private void setSpecialRulesForOrderClient(int orderId, int internationalFees, int additionalPickFees, int packageCost, int allInclusive) throws  Exception {

        PreparedStatement stmt = HibernateSession.getPreparedStatement("WITH ORDER_CLIENT_INFO AS\n" +
                "(\n" +
                "\tSELECT client_fkey, total_physical_units FROM w_order_attributes where order_fkey = ?\n" +
                ")\n" +
                "UPDATE owd_client_specialBillRules SET\n" +
                "\towd_client_specialBillRules.[International Fees]  = ?,\n" +
                "\towd_client_specialBillRules.[Additional Pick Fees] = ?,\n" +
                "\towd_client_specialBillRules.packageCost = ?,\n" +
                "\towd_client_specialBillRules.allInclusive = ?\n" +
                "from owd_client_specialBillRules  AS BR\n" +
                "join ORDER_CLIENT_INFO  AS CI on\n" +
                "BR.clientFkey = CI.client_fkey\n" +
                "AND BR.ruleLookup = CI.total_physical_units \n" +
                "where active = 1 and ruleType = 'units_allInclusive' and ShipMethod = 'ECONOMY';");

        stmt.setInt(1, orderId);
        stmt.setInt(2, internationalFees);
        stmt.setInt(3, additionalPickFees);
        stmt.setInt(4, packageCost);
        stmt.setInt(5, allInclusive);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    private  void setAmount(int orderId, int amount) throws  Exception {

        PreparedStatement stmt = HibernateSession.getPreparedStatement(
                "update [dbo].[owdbill_shipping_trans] set amount = ?  where order_fkey = ?;");

        stmt.setInt(1, amount);
        stmt.setInt(2, orderId);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    private void setBillingPickPackFee(int orderId, int internationalFees, int additionalPickFees, int packageCost) throws  Exception {

        PreparedStatement stmt = HibernateSession.getPreparedStatement(
                "update sp_bill_pickpack set [International Fees] = ?, [Additional Pick Fees] = ?, packageCost = ? \n" +
                "from w_order_attributes inner join sp_bill_pickpack\n" +
                "on(w_order_attributes.order_fkey = sp_bill_pickpack.oid)\n" +
                "where order_fkey = ?;");

        stmt.setInt(1, internationalFees);
        stmt.setInt(2, additionalPickFees);
        stmt.setInt(3, packageCost);
        stmt.setInt(4, orderId);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }
}
