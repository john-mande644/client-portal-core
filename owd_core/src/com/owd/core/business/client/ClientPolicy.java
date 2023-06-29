package com.owd.core.business.client;

import com.owd.core.business.Client;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 16, 2003
 * Time: 11:02:36 AM
 * To change this template use Options | File Templates.
 */
public interface ClientPolicy {


public Client getClient();

    public void setSignatureRequired(String remoteSourceKey, Order order); 
    void setSignatureRequired(Order order) throws Exception;
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order);

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity, String longdesc) throws Exception;

    boolean allowSalesTaxEditing(Order order);

    float getSalesTaxValue(Order order) throws Exception;

    public void setClient(Client client);

    public void setManualEntryMode(boolean yes);

    public boolean allowManualDiscountEntry(Order order);

    public boolean allowUnitPriceEditing(Order order);

    public void sendFlexPayDeclineEmail(OrderStatus order);

    public boolean hasManualEntryMode();

    boolean allowShipPriceEditing(Order order);

    public void updateLineItemsAfterItemChange(Order anOrder);

    Map getPaymentOptions() throws Exception;

    String getShipOptionName(String optionType, List shipOptions);

    String getShippingMethod(Order order, String shipType, List shipOptions) throws Exception;

    float getShippingCost(Order order, String shipType, List shipOptions) throws Exception;

    List getShipOptions(Order order, float defaultCost) throws Exception;

    public Order createInitializedOrder();

    public void sendNotificationMessage(Order order, String subject, String message);

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception;

    public void saveNewOrder(Order order, boolean testing) throws Exception;

    List getCustomOrderFields(Order order);

    void handleCustomOrderFields(Order order, List fields);

    void updateCustomOrderFields(Order order, HttpServletRequest request, List fields);

    public String getFlexPayStatement(Order order);
    
       public  void sendCustomerEmailConfirmation(Order order);
    public String translateRemoteSkuToOwdSku(String remoteSourceKey,String remoteSku);
}
