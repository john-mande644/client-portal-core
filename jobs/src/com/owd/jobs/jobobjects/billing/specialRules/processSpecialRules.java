package com.owd.jobs.jobobjects.billing.specialRules;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 9/13/2016.
 */
public class processSpecialRules {
    private final static Logger log =  LogManager.getLogger();

    public static void main(String args[]){

    try{
        processRules("2019-12-05");
      //  processShippingRules("2019-12-05");
    }catch (Exception e){
        e.printStackTrace();
    }

    }



    public static void processShippingRules(String theDate) throws Exception{
        List<pricingBean> rulesToProcess = loadRules();
        log.debug(rulesToProcess.size());
        for(pricingBean pb:rulesToProcess){
            log.debug("Processing: "+pb.getRuleName());
            //load the rule bean
            specialRulesBase theRule = loadTheSpecificRule(pb);
            //loadid and process
            theRule.processShipping(theDate,pb);


        }
    }

    public static void processRules(String theDate) throws Exception{

        //grab all the rules and loop through by type

        List<pricingBean> rulesToProcess = loadRules();
        log.debug(rulesToProcess.size());
        for(pricingBean pb:rulesToProcess){
            log.debug("Processing: "+pb.getRuleName());
            //load the rule bean
           specialRulesBase theRule = loadTheSpecificRule(pb);
            //loadid and process
            theRule.processIds(theRule.loadIds(pb,theDate),pb);


        }



    }

    public static specialRulesBase loadTheSpecificRule(pricingBean pb){
        specialRulesBase spb;
        if(pb.getRuleType().equals(specialRulesBase.kFlatOrderGroup)){
            log.debug("Setting ruls type: "+ specialRulesBase.kFlatOrderGroup);
            spb = new flatOrderGroupRule();
        }else if(pb.getRuleType().equals(specialRulesBase.kBypassAllInsertRule)) {
            spb = new BypassAllInsertRule();

        }else if(pb.getRuleType().equals(specialRulesBase.kFingerprintAllInclusive)){
            spb = new fingerprintAllInclusiveRule();

        }else if(pb.getRuleType().equals(specialRulesBase.kUnitAllInclusive)){
            spb = new unitsAllInclusiveRule();
        }else{
            spb = new specialRulesBase();
        }

        return spb;
    }

    public static List<pricingBean> loadRules() throws Exception{
        List<pricingBean> rules = new ArrayList<pricingBean>();
         String sql = "select * from owd_client_specialBillRules where active = 1";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List results = q.list();

        for (Object row : results) {
            Map data = (Map) row;
            pricingBean pb = new pricingBean();
            pb.setClientFkey(Integer.parseInt(data.get("clientFkey").toString()));
            pb.setRuleType(data.get("ruleType").toString());
            pb.setRuleName(data.get("ruleName").toString());
            pb.setRuleLookup(data.get("ruleLookup").toString());
            pb.setColorprint(data.get("colorprint").toString());
            pb.setInternationalFees(data.get("International Fees").toString());
            pb.setAdditionalPickFees(data.get("Additional Pick Fees").toString());
            pb.setPackageCost(data.get("packageCost").toString());
            pb.setOrderFees(data.get("Order Fees").toString());
            pb.setInsertFees(data.get("Insert Fees").toString());
            pb.setEffectiveDate(data.get("effectiveDate").toString());
            pb.setAllInclusive(data.get("allInclusive").toString());
            pb.setShipMethod(data.get("shipMethod").toString());
            rules.add(pb);

        }

        return rules;
    }
}
