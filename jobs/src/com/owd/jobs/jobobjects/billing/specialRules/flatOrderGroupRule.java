package com.owd.jobs.jobobjects.billing.specialRules;

/**
 * Created by danny on 9/13/2016.
 */
public class flatOrderGroupRule extends specialRulesBase{


   public flatOrderGroupRule(){

       setSql("select id from sp_bill_pickpack WHERE group_name = :ruleLookup and [Client ID] = :clientId " +
               "and ([International Fees] <> :internationalFees or colorprint <> :colorprint " +
               "or packageCost <> :packageCost or [Order Fees] <> :orderFees or [Additional Pick Fees] <> :additionalPickFees " +
               "or [Insert Fees]<> :insertFees) and shipped_on > :effectiveDate");
   }


}
