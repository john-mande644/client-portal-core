package com.owd.core.ruleAdapters;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/12/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SubscriptionRuleAdapter extends BaseRuleAdapter {

    /**
     *
     * @param shipCost
     */
    public void setShipCost(float shipCost);

    /**
     *
     * @return
     */
    public float getShipCost();

    /**
     *
     * @param itemName
     * @param cost
     * @param quantity
     */
    public void addItemToList(String itemName, float cost, int quantity);

    /**
     *
     * @param part
     */
    public void setVariableFirstShipmentDate(String part);

    /**
     *
     * @param campaignName
     */
    public void setCampaignName(String campaignName);

    /**
     *
     */
    public void setFirstShipmentDate();

    /**
     *
     * @param itemName
     * @param clientID
     * @param quantity
     */
    public void addItemToAvailability(String itemName, String clientID, int quantity);

    /**
     *
     * @param part
     * @param qty
     * @param cost
     * @param totalCost
     * @param description
     * @param itemDeclaredValue
     * @param itemCustomsDesc
     */
    public void addOrderLineItem(String part, int qty, float cost, float totalCost, String description, float itemDeclaredValue, String itemCustomsDesc);

}
