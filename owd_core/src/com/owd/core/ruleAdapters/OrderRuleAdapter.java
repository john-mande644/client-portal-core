package com.owd.core.ruleAdapters;


import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/2/13
 * Time: 10:05 AM
 *
 *  Interface for Using Rule Engine, (Drools), against an order object.
 */
public interface OrderRuleAdapter extends BaseRuleAdapter {

    public static String clientID = "";
    public static String[]skus = new String[0];
    /**
     * Returns client ID as String
     *
     * @return
     */
    @DroolsDescriptor(
            description = "Returns client ID as String",
            parameterType = "None",
            returnType = "String"
    )
    public String getClientID();

    /**
     * Returns tag value as String. Returns null if tag key does not exist
     *
     * @return
     */
    @DroolsDescriptor(
            description = "returns sum of line item customs values",
            parameterType = "None",
            returnType = "float"
    )
    public float getTotalCustomsValue();

    /**
     * Returns tag value as String. Returns null if tag key does not exist
     *
     * @return
     */
    @DroolsDescriptor(
            description = "Returns tag value as String. Returns null if tag key does not exist.",
            parameterType = "None",
            returnType = "String"
    )
    public String getTagValue(String key);

    /**
     * Returns test for presence of requested tag key as boolean.
     *
     * @return
     */
    @DroolsDescriptor(
            description = "Flags presence of requested tag key",
            parameterType = "String",
            returnType = "boolean"
    )
    public boolean tagExists(String key);

    public Map<String,String> getTagMap();

    /**
     * Determines if Order contains any of the Sku's within the array.
     *
     * @param sku
     * @return
     */
    @DroolsDescriptor(
            description = "Contains any of the passed Skus",
            parameterType = "String...",
            returnType = "boolean"
    )
    public boolean containsAnySKU(List<String> sku);

    /**
     * Determines if Order contains all of the Sku's within the array.
     *
     * @param sku
     * @return
     */

    @DroolsDescriptor(
            description = "Contains all of the passed Skus",
            parameterType = "String...",
            returnType = "boolean"
    )
    public boolean containsAllSKU(List<String> sku);

    /**
     * Negative test if Order contains any of the Sku's within the array.
     * This is to simplify Rule design so a positive result is tested for.
     *
     * @param sku
     * @return
     */
    @DroolsDescriptor(
            description = "Does not contain any of the passed Skus",
            parameterType = "String...",
            returnType = "boolean"
    )
    public boolean notContainsAnySKU(List<String> sku);





    /**
     * Negative test if Order contains all of the Sku's within the array.
     * This is to simplify Rule design so a positive result is tested for.
     *
     * @param sku
     * @return
     */
    @DroolsDescriptor(
            description = "Does not contain all of the passed Skus",
            parameterType = "String...",
            returnType = "boolean"
    )
    public boolean notContainsAllSKU(List<String> sku);

    /**
     * Returns boolean of Auto Delivery.
     *
     * @return
     */
    @DroolsDescriptor(
            description = "Returns boolean of Auto Delivery.",
            parameterType = "None",
            returnType = "boolean"
    )
    public boolean isAutoDelivery();

    /**
     * Returns recurrence count.
     *
     * @return
     */
    @DroolsDescriptor(
            description = "Returns the recurrence count.",
            parameterType = "None",
            returnType = "int"
    )
    public int getRecurrenceCount();
//
//    /**
//     *
//     * @return
//     */
//    public Vector<String> skuList();


    /**
     * Inserts Item if available, it will continue to add each sku as long as the previous was
     * added successfully.
     *
     * @param sku
     */
    @DroolsDescriptor(
            description = "Inserts Item if available, it will continue to add each sku as long as the previous was " +
                    " added successfully.",
            parameterType = "String...",
            returnType = "None"
    )
    public void addInsertItemIfAvailableRecursive(List<String> sku);

    /**
     *
     * @param sku
     * @param quantity
     */
    public void addInsertItemIfAvailableDrools(String sku, int quantity);


    /**
     *
     * @param iid
     * @return
     */
    public long getStockLevelForInventoryId(int iid, String facilityCode);
    /**
     *
     * @param sku
     * @return
     */
    public long getQuantityForSKU(String sku);

    /**
     *
     * @param sku
     * @param quantity
     * @param itemPrice
     * @param linePrice
     * @param description
     * @param color
     * @param size
     */
    public void addLineItemWithInventory(String sku, int quantity, float itemPrice, float linePrice, String description, String color, String size) throws Exception;

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressOne(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressTwo(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressState(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressZip(String addressType);

    public String getFacilityCode();

    public boolean isInternationalShipping();

    public void addNote(String note);
    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressCompanyName(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressCity(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressCountry(String addressType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getContactEmail(String contactType);


    /**
     *
     * @param addressType
     * @return
     */
    public String getContactName(String contactType);

    /**
     *
     * @param addressType
     * @return
     */
    public String getContactPhone(String contactType);



    /**
     *
     * @return
     */
    public String getGroupName();

    public void setGroupName(String groupName);


    /**
     *
     * @return
     */
    public void setPackingInstructions(String instructions);


    /**
     *
     * @return
     */
    public void mergeItemPackingInstructionsToOrder();



    /**
     *
     * @return
     */
    public String getClientOrderReference();


    /**
     * Returns count by named field within inventory object connected to a line item. It will check the field
     * with case independent contains method. The equals attribute when true will add if the field contains
     * the value and will add if it doesn't contain when false.
     *
     * @param field
     * @param value
     * @param containsValue
     * @return
     */
    public int getCountByLineItemInventoryField(String field, String value, boolean containsValue);

    /**
     * Sets whether signature is required on delivery.
     *
     * @param required
     */
    public void setSignatureRequired(boolean required);


    /**
     * Sets whether signature is required on delivery.
     *
     * @param value
     */
    public void setInsuredValue(float value);

    /**
     * Sets order on hold at order creation time.
     *
     */
    public void holdNewOrder();

    /**
     * Returns the total order cost.
     *
     * @return
     */
    public float getTotalOrderCost();

    public boolean hasGiftMessage();

    /**
     *
     * @param operator
     * @param value
     * @return
     */
    public int getItemByWeightCount(String operator, float value);


    public void setTemplate(String templateName);

    public String getShippingMethodName();

    public void setShippingMethodName(String newMethod)  throws Exception ;

    public void setThirdPartyBilling(String account)  throws Exception;

    public void setThirdPartyBillingContact(String contact, String company, String address1, String address2, String city, String state, String zip, String phone);

    /**
     * Returns the number of units in the order, in total, for all SKUs in the given List.
     *
     * @param skus
     * @return
     */
    @DroolsDescriptor(
            description = "Returns total units requested of the passed Skus",
            parameterType = "String...",
            returnType = "int"
    )
    public int getRequestedUnitsofSkusInList(List<String> skus);


    public int getRequestedUnitsofSkusWithInventoryTagValue(String tagName, String tagValue);

    public void setLTLHold();

    public float getEstimatedTotalLineItemWeight();

    public void updateShipMethodAfterTime(String time, String newMethod);

    public void updateShipMethodAfterTimeShift(String time, String newMethod,int hourShift);

    public void updateShipCompanyOverride(String name);
    public void updateShipNameOverride(String name);
    public void updateShipPhoneOverride(String name);
    public void updateShipAddress1Override(String name);
    public void updateShipAddress2Override(String name);
    public void updateShipCityOverride(String name);
    public void updateShipStateOverride(String name);
    public void updateShipZipOverride(String name);
    public void setExternalShippingKey(String key);
    public String getPONumber();

    public void setSmartPostReturnFlag() throws Exception;
    public void setMailInnovationsReturnFlag() throws Exception;

    public void setDDPFlag(int flag);

    public String getOrderType();

    public boolean onlyContainsTheseSKUs(List<String> skus);

    public void setBackorderRule(String rule);

    public void setBusinessOrder(boolean businessOrder);

    public boolean getBusinessOrder();

    // Sean 2020/2/5
    public String getComments();
    public void setGiftMessage(String messager);
    public void setPostDateHoursDelay(int hours);
    public void setOrderType(String orderType);

    public long getPhysicalUnitsCountOnOrder();

    public void addInsertItemIfInventoryAvailable(String sku, int quantity) throws Exception;

    public void addTagToOrder(String name, String value)throws Exception;

    public boolean isPurchasedBefore(String client_id, String fullName, String addr1, String city) ;
    public boolean isReceivedAnItemBefore(String client_id, String fullName, String addr1, String city,String lineitem) ;

}
