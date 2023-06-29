package com.owd.core.ruleAdapters;

import java.util.List;
import java.util.Map;

/**
 * Created by stewartbuskirk1 on 3/23/16.
 */
public interface OrderPostCreateRuleAdapter extends BaseRuleAdapter {

    public String getClientReference();

    public String getOWDReference();

    public String getShipMethodName();

    public String getClientID();

    public String getGroupName();

    public boolean tagExists(String name);

    public String getTagValue(String tagName);

    public void injectWorkOrder(String subject, String body) ;

    public String getDamagedReturnItemsString(String orderRefNum, Integer clientId);

    public String getPONumber();

    public boolean containsAnySKU(List<String> sku);

    public String getAddressCountry(String addressType);

    public String getAddressCompanyName(String addressType);

    public void setThirdPartyBilling(String account)  throws Exception;

    public void setThirdPartyBillingContact(String contact, String company, String address1, String address2, String city, String state, String zip, String phone);

    public String getFacilityCode();

    public void updateOrderThirdPartyAccountInfo(String account);

    public void updateOrderThirdPartyConatacInfo(String contact, String company, String address1, String address2,
                                                 String city, String state, String zip, String phone );
}
