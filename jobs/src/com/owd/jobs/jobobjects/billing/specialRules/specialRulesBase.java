package com.owd.jobs.jobobjects.billing.specialRules;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 9/13/2016.
 */
public class specialRulesBase implements specialRulesInterface {

    public String sql ="";
    public static String kFlatOrderGroup ="flat_order_group";
    public static String kBypassAllInsertRule = "bypass_all_insert";
    public static String kFingerprintAllInclusive = "fingerprint_allInclusive";
    public static String kUnitAllInclusive = "units_allInclusive";
    private final static Logger log =  LogManager.getLogger();


    public List<String> loadIds(pricingBean pricing,String theDate) throws Exception{
        List<String> ids = new ArrayList<>();

        if(sql.length()==0){
            throw new Exception("Please set the sql lookup for the rule");
        }


        try{
            System.out.println(sql);
          Query q = HibernateSession.currentSession().createSQLQuery(sql);
            if(sql.contains(":clientId")){
                q.setParameter("clientId",pricing.getClientFkey());
            }
            if(sql.contains(":ruleLookup")){
                q.setParameter("ruleLookup",pricing.getRuleLookup());
            }
            if(sql.contains(":colorprint")){
                q.setParameter("colorprint",pricing.getColorprint());

            }
            if(sql.contains(":internationalFees")){
                q.setParameter("internationalFees",pricing.getInternationalFees());
            }
            if(sql.contains(":packageCost")){
                q.setParameter("packageCost",pricing.getPackageCost());
            }
            if(sql.contains(":orderFees")){
                q.setParameter("orderFees",pricing.getOrderFees());
            }
            if(sql.contains(":additionalPickFees")){
                q.setParameter("additionalPickFees",pricing.getAdditionalPickFees());
            }
            if(sql.contains(":insertFees")){
                q.setParameter("insertFees",pricing.getInsertFees());
            }
            if(sql.contains(":effectiveDate")){
                q.setParameter("effectiveDate",pricing.getEffectiveDate());
            }
            List l = q.list();

            for(Object row:l){

                ids.add(row.toString());
            }


        }catch (Exception e){
            e.printStackTrace();

        }
        return ids;
    }

    public void processIds(List<String> ids,pricingBean prices) throws Exception{
        log.debug("Processing "+ids.size()+" for "+ prices.getRuleName());
        for(String id:ids){
            //update the id to the pricing bean info.
            String sql = "update sp_bill_pickpack set [International Fees] = :InternationalFees, colorprint = :colorprint, packageCost = :packageCost, " +
                    "[Order Fees] = :orderFees, [Additional Pick Fees] = :additionalPickFees, [Insert Fees] = :insertFees where id = :id";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("InternationalFees",prices.getInternationalFees());
            q.setParameter("colorprint",prices.getColorprint());
            q.setParameter("packageCost",prices.getPackageCost());
            q.setParameter("orderFees",prices.getOrderFees());
            q.setParameter("additionalPickFees",prices.getAdditionalPickFees());
            q.setParameter("insertFees",prices.getInsertFees());
            q.setParameter("id",id);

            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }

    }



    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void processShipping(String shippedOn,pricingBean prices) throws Exception{

    };
}
