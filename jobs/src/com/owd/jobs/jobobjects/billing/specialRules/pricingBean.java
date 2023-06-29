package com.owd.jobs.jobobjects.billing.specialRules;

/**
 * Created by danny on 9/13/2016.
 */
public class pricingBean {

    private int clientFkey;
    private String ruleType;
    private String ruleLookup;
    private String ruleName;
    private String colorprint;
    private String internationalFees;
    private String packageCost;
    private String orderFees;
    private String additionalPickFees;
    private String insertFees;
    private String effectiveDate;
    private String allInclusive;
    private String shipMethod;

    public int getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(int clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleLookup() {
        return ruleLookup;
    }

    public void setRuleLookup(String ruleLookup) {
        this.ruleLookup = ruleLookup;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getColorprint() {
        return colorprint;
    }

    public void setColorprint(String colorprint) {
        this.colorprint = colorprint;
    }

    public String getInternationalFees() {
        return internationalFees;
    }

    public void setInternationalFees(String internationalFees) {
        this.internationalFees = internationalFees;
    }

    public String getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(String packageCost) {
        this.packageCost = packageCost;
    }

    public String getOrderFees() {
        return orderFees;
    }

    public void setOrderFees(String orderFees) {
        this.orderFees = orderFees;
    }

    public String getAdditionalPickFees() {
        return additionalPickFees;
    }

    public void setAdditionalPickFees(String additionalPickFees) {
        this.additionalPickFees = additionalPickFees;
    }

    public String getInsertFees() {
        return insertFees;
    }

    public void setInsertFees(String insertFees) {
        this.insertFees = insertFees;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(String allInclusive) {
        this.allInclusive = allInclusive;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }
}
