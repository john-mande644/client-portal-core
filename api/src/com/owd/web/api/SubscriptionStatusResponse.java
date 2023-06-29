package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import com.owd.hibernate.generated.OwdOrderAutoHistory;
import com.owd.hibernate.generated.OwdOrder;

import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:46:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionStatusResponse extends APIResponse {
private final static Logger log =  LogManager.getLogger();

    public static final String shipInterval = "shipInterval";
    public static final String calendarUnit = "calendarUnit";
    public static final String requirePayment = "requirePayment";
    public static final String status = "status";
    public static final String nextShipmentDate = "nextShipmentDate";

    public static final String billName = "billName";
    public static final String billAddressOne = "billAddressOne";
    public static final String billAddressTwo = "billAddressTwo";
    public static final String billCity = "billCity";
    public static final String billState = "billState";
    public static final String billZip = "billZip";
    public static final String billCountry = "billCountry";
    public static final String ccNum = "ccNum";
    public static final String ccExpMon = "ccExpMon";
    public static final String ccExpYear = "ccExpYear";
    public static final String shipName = "shipName";
    public static final String shipAddressOne = "shipAddressOne";
    public static final String shipAddressTwo = "shipAddressTwo";
    public static final String shipCity = "shipCity";
    public static final String shipState = "shipState";
    public static final String shipZip = "shipZip";
    public static final String shipCountry = "shipCountry";
    public static final String billPhone = "billPhone";
    public static final String shipPhone = "shipPhone";
    public static final String shipMethod = "shipMethod";
    public static final String salesTax = "salesTax";
    public static final String shipCost = "shipCost";
    public static final String created = "created";
    public static final String billEmail = "billEmail";
    public static final String shipEmail = "shipEmail";

    public static final String cancelDate = "cancelDate";
    public static final String orderSource = "orderSource";

    //make attributes
    public static final String createdBy = "createdBy";
    public static final String autoShipId = "autoShipId";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public static final String kRootTag = "OWD_SUBSCRIPTION_STATUS_RESPONSE";
    private List<OwdOrderAuto> autoList = new ArrayList<OwdOrderAuto>();

    public SubscriptionStatusResponse(double api_v)

    {

        super(api_v);

    }

    public void setSubscriptionList(List<OwdOrderAuto> autos)

    {
        if (autos != null) {
            autoList = autos;

        }


    }

    protected String getXMLFromSubscription(OwdOrderAuto auto) {

        StringBuffer sb = new StringBuffer();
        sb.append("<OWD_SUBSCRIPTION_STATUS "+autoShipId+"=\""+auto.getAutoShipId()+"\" "+created+"=\""+df.format(auto.getCreated())+"\" "+createdBy+"=\""+auto.getCreatedBy()+"\" >");
        //billing
        sb.append(API.buildTextElement(billName,auto.getBillName()));
        sb.append(API.buildTextElement(billAddressOne,auto.getBillAddressOne()));
        sb.append(API.buildTextElement(billAddressTwo,auto.getBillAddressTwo()));
        sb.append(API.buildTextElement(billCity,auto.getBillCity()));
        sb.append(API.buildTextElement(billState,auto.getBillState()));
        sb.append(API.buildTextElement(billZip,auto.getBillZip()));
        sb.append(API.buildTextElement(billCountry,auto.getBillCountry()));
        sb.append(API.buildTextElement(ccNum,auto.getCcNum()));
        sb.append(API.buildTextElement(ccExpMon,""+auto.getCcExpMon()));
        sb.append(API.buildTextElement(ccExpYear,""+auto.getCcExpYear()));
        sb.append(API.buildTextElement(requirePayment,""+auto.getRequirePayment()));
        sb.append(API.buildTextElement(billPhone,auto.getBillPhone()));
        sb.append(API.buildTextElement(billEmail,auto.getBillEmail()));

        //shipping
        sb.append(API.buildTextElement(shipName,auto.getShipName()));
        sb.append(API.buildTextElement(shipAddressOne,auto.getShipAddressOne()));
        sb.append(API.buildTextElement(shipAddressTwo,auto.getShipAddressTwo()));
        sb.append(API.buildTextElement(shipCity,auto.getShipCity()));
        sb.append(API.buildTextElement(shipState,auto.getShipState()));
        sb.append(API.buildTextElement(shipZip,auto.getShipZip()));
        sb.append(API.buildTextElement(shipCountry,auto.getShipCountry()));
        sb.append(API.buildTextElement(shipPhone,auto.getShipPhone()));
        sb.append(API.buildTextElement(shipEmail,auto.getShipEmail()));
        sb.append(API.buildTextElement(shipMethod,auto.getShipMethod()));
        sb.append(API.buildTextElement(shipCost,""+auto.getShipCost()));
        sb.append(API.buildTextElement(requirePayment,""+auto.getRequirePayment()));
        sb.append(API.buildTextElement(salesTax,""+auto.getSalesTax()));
        sb.append(API.buildTextElement(nextShipmentDate,auto.getNextShipmentDate()==null?"":df.format(auto.getNextShipmentDate())));

        //general
        sb.append(API.buildTextElement(shipInterval,""+auto.getShipInterval()));
        sb.append(API.buildTextElement(calendarUnit,auto.getCalendarUnit()));
        sb.append(API.buildTextElement(status,""+auto.getStatus()));
        sb.append(API.buildTextElement(cancelDate,auto.getCancelDate()==null?"":df.format(auto.getCancelDate())));
        sb.append(API.buildTextElement(orderSource,auto.getOrderSource()));

    //items
        for(OwdOrderAutoItem item:auto.getOwdOrderAutoItems())
        {
            sb.append("<ITEM>");
          sb.append(API.buildTextElement("sku",item.getSku()));
          sb.append(API.buildTextElement("quantity",item.getQuantity()+""));
          sb.append(API.buildTextElement("unitPrice",item.getUnitPrice()+""));
            sb.append("</ITEM>");
        }

    //optional history
        for(OwdOrderAutoHistory item:auto.getOwdOrderAutoHistories())
        {
          sb.append("<HISTORY>");
          sb.append(API.buildTextElement("created",df.format(item.getCreated())));
          sb.append(API.buildTextElement("message",item.getMessage()+""));
            sb.append("</HISTORY>");
        }
    //optional order sources
        for(OwdOrder item:auto.getSourceOrders())
        {
              sb.append("<SOURCE>");
          sb.append(API.buildTextElement("created",df.format(item.getCreatedDate())));
          sb.append(API.buildTextElement("status",item.getOrderStatus()));
          sb.append(API.buildTextElement("clientReference",item.getOrderRefnum()));
          sb.append(API.buildTextElement("owdReference",item.getOrderNum()));
            sb.append("</SOURCE>");
        }
        //optional orders created
         for(OwdOrder item:auto.getCreatedOrders())
        {
             sb.append("<CREATED>");
          sb.append(API.buildTextElement("created",df.format(item.getCreatedDate())));
          sb.append(API.buildTextElement("clientReference",item.getOrderRefnum()));
          sb.append(API.buildTextElement("owdReference",item.getOrderNum()));
          sb.append(API.buildTextElement("status",item.getOrderStatus()));
            sb.append("</CREATED>");
        }
        
        
        sb.append("</OWD_SUBSCRIPTION_STATUS>");
        return sb.toString();
    }

    public String getXML()

    {

        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<OWD_SUBSCRIPTION_STATUS_RESPONSE>");
        responseBuffer.append("<COUNT>" + autoList.size() + "</COUNT>");
        for (OwdOrderAuto auto : autoList) {
            responseBuffer.append(getXMLFromSubscription(auto));
        }
        responseBuffer.append("</OWD_SUBSCRIPTION_STATUS_RESPONSE>");
        return responseBuffer.toString();


    }

}
